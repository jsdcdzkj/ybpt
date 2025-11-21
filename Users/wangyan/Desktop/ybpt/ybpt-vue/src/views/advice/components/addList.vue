<template>
    <el-drawer
            :title="title"
            :before-close="handleClose"
            :visible.sync="dialog"
            direction="rtl"
            :with-header="false"
            custom-class="box_drawer"
            :close-on-click-modal="false"
            size="80%"
            ref="drawer"
    >
        <div class="drawer_content">
            <el-form :model="form" :label-width="formLabelWidth">
                <div class="drawer_main">
                    <div class="box_card">
                        <div class="box_content">
                            <h4 class="h4">徐州市医疗服务项目价格调整建议汇总表</h4>
                            <vab-query-form>
                                <vab-query-form-right-panel :span="24">
                                    <el-form-item>
                                        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">添 加
                                        </el-button>
                                    </el-form-item>
                                </vab-query-form-right-panel>
                            </vab-query-form>
                            <el-table
                                    :data="tableData"
                                    border
                                    stripe
                                    class="w"
                                    highlight-current-row
                            >
                                <template slot="empty">
                                    <el-empty :image-size="150"></el-empty>
                                </template>
                                <el-table-column
                                        type="index"
                                        label="序号"
                                        width="80"
                                        align="center"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column prop="project_code" align="center" label="收费项目编码"
                                                 min-width="100"></el-table-column>
                                <el-table-column
                                        prop="project_name"
                                        label="项目名称"
                                        min-width="120"
                                        align="center"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="projectConnotation"
                                        width="120"
                                        label="项目内涵"
                                        align="center"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="excludedContent"
                                        width="120"
                                        label="除外内容"
                                        align="center"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="unit"
                                        width="80"
                                        label="计价单位"
                                        align="center"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="levelOfChargeItem"
                                        width="80"
                                        label="医保支付类别"
                                        align="center"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="explain"
                                        min-width="180"
                                        label="项目说明"
                                        align="center"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="calculate"
                                        min-width="110"
                                        label="测算成本(元)"
                                        align="center"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="price"
                                        align="center"
                                        min-width="140"
                                        label="建议调整价格(元)"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="workload"
                                        align="center"
                                        min-width="180"
                                        label="上年工作量"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="memo"
                                        min-width="180"
                                        label="备注"
                                        align="center"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        show-overflow-tooltip
                                        label="成本/价格"
                                        width="120"
                                        align="center"
                                        fixed="right"
                                >
                                    <template #default="{ row  , $index}">
                                        <el-button v-if="row.detail_status != 1" type="warning" plain size="mini" @click="listEdit($index,row)">未填写
                                        </el-button>
                                         <el-button v-else type="success" plain size="mini" @click="listEdit($index,row)">已填写</el-button>
                                    </template>
                                </el-table-column>
                                <el-table-column
                                        show-overflow-tooltip
                                        label="操作"
                                        width="120"
                                        align="center"
                                        fixed="right"
                                >
                                    <template #default="{ row , $index}">
                                        <el-tooltip class="item" effect="dark" content="编辑" placement="top">
                                            <el-button type="primary" plain size="mini" icon="el-icon-edit"
                                                       @click="handleAdd(row)"></el-button>
                                        </el-tooltip>
                                        <el-tooltip class="item" effect="dark" content="删除" placement="top">
                                            <el-button type="danger" plain size="mini" icon="el-icon-delete"
                                                       @click="handleDelete($index, tableData)"></el-button>
                                        </el-tooltip>
                                    </template>
                                </el-table-column>
                            </el-table>
                        </div>
                    </div>
                </div>
            </el-form>
            <div class="drawer_footer">
                <el-button @click="cancelForm">关 闭</el-button>
                <el-button type="primary" @click="save" :loading="loading">
                    {{ loading ? '提交中 ...' : '提 交' }}
                </el-button>
            </div>
        </div>
        <Add ref="add" @fetch-data="fetchData"></Add>
        <Detail ref="detail" @fetch-data="fetchData"></Detail>
    </el-drawer>
</template>

<script>
import Add from './add.vue'
import Detail from './detail.vue'
import {insert} from "@/api/advice";
import {getFixmedinsB} from "@/api/sbApply";

export default {
    name: 'edit',
    components: {Detail, Add},
    data() {
        return {
            tableData: [],
            title: '',
            dialog: false,
            loading: false,
            isShow: false,
            isShow1: true,
            isShow2: false,
            queryForm: {
                pageNo: 1,
                pageSize: 10,
                username: '',
            },
            form: {
                natures: '',
            },
            formLabelWidth: '100px',
            timer: null,
        }
    },
    mounted() {
    },
    methods: {
        async showDia(row) {
            if (!row) {
                this.title = '新增'
                this.tableData = []
            } else {
                this.title = '编辑'
                this.form = Object.assign({}, row)
            }
            await getFixmedinsB().then((res) => {
                this.fixmedinsB = res.data
            })
            this.form.natures = this.biznet_typeFormat()
            this.dialog = true
        },
        fetchData(row){
            if(row.sign == 'edit'){
                this.tableData.splice(row.index, 1,Object.assign({}, row));
            }
            if(row.sign == 'add'){
                this.tableData.push(Object.assign({}, row))
            }
            if(row.sign == 'detail'){
                this.tableData.splice(row.index, 1,Object.assign({}, row));
            }
            console.log(row)
        },
        listEdit(index,row) {
            if (row.project_code) {
                this.$refs['detail'].showDia(index,row)
            } else {
                this.$refs['detail'].showDia()
            }
        },
        handleAdd(row) {
            if (row.project_code) {
                this.$refs['add'].showEdit(row)
            } else {
                this.$refs['add'].showEdit()
            }
        },
        close() {
            // this.$refs['form'].resetFields()
            // this.form = this.$options.data().form
            this.dialog = false
        },
        async save() {
            if(this.tableData.length <= 0){
                this.$baseMessage('请至少保留一条数据！', 'error')
                return
            }
            let sign = false;
            this.tableData.forEach(x => {
                if (x.detail_status == 0) {
                    sign = true
                }
            })
            if (sign) {
                this.$baseMessage('明细未填写,请完善信息！', 'error')
                return
            }
            this.form.details = this.tableData
            const loading = this.$loading({
                lock: true,
                text: '提交中，请稍等...',
                spinner: 'el-icon-loading',
                background: 'rgba(0, 0, 0, 0.7)'
            });
            const res = await insert(this.form)
            this.timer = setTimeout(() => {
                loading.close();
                if (res.code == 0) {
                    this.loading = false
                    this.dialog = false
                    this.$emit('fetch-data')
                    this.$baseMessage('操作成功', 'success')
                }else{

                    loading.close();
                    this.$baseMessage(res.msg, 'error')
                }
            }, 2000)
        },
        handleDelete(index, rows) {
            this.$baseConfirm('确认进行删除？', null, async () => {
                rows.splice(index, 1);
                this.$baseMessage('删除成功', 'success')
                // this.fetchData()
            })
        },
        handleClose(done) {
            if (this.loading) {
                return
            }
            let sign = false;
            this.tableData.forEach(x => {
                if (x.detail_status == 0) {
                    sign = true
                }
            })
            if (sign) {
                this.$baseMessage('明细未填写,请完善信息！', 'error')
                return
            }
        },
        cancelForm() {
            this.loading = false
            this.dialog = false
            clearTimeout(this.timer)
        },
        biznet_typeFormat() {
            let statusW = ''
            switch (this.fixmedinsB.biznet) {
                case '1':
                    statusW = '营利性'
                    break
                case '2':
                    statusW = '民办非营利'
                    break
                case '3':
                    statusW = '政府非营利'
                    break
            }
            return statusW
        },
    },
}
</script>
<style scoped lang="scss">
.h4 {
  font-size: 24px;
  text-align: center;
  margin: 0 auto;
  padding: 0;
  letter-spacing: 1px;
}
</style>