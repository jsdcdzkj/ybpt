<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">信息查询</span>
                        <div class="right">
                            <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
                            <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
                        </div>
                    </div>
                    <el-form label-width="100px">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="机构名称">
                                    <el-input v-model.trim="queryForm.org_name" @keyup.enter.native="queryData" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="医保编码">
                                    <el-input v-model.trim="queryForm.medical_insurance_num"
                                        @keyup.enter.native="queryData" />
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card tablecard" shadow="never">
                    <div slot="header">
                        <span class="tips">医疗机构列表</span>
                        <div class="right">
                            <el-button type="success" icon="el-icon-plus" @click="handleAdd">
                                新增
                            </el-button>
                        </div>
                    </div>
                    <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
                        :element-loading-text="elementLoadingText" highlight-current-row border
                        @current-change="handleCurrentChange" height="calc(100% - 50px)"
                        @selection-change="setSelectRows">
                        <template slot="empty">
                            <el-empty :image-size="200"></el-empty>
                        </template>
                        <el-table-column label="序号" width="80" align="center" show-overflow-tooltip>
                            <template #default="scope">
                                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="机构名称" align="center" width="200px"
                            prop="org_name"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="medical_insurance_num" label="医保编码" align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="org_linkman" width="120px" label="机构联系人"
                            align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="authorizationCode" width="120px" label="授权码"
                                         align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="org_phone" label="机构联系电话" align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="createTime" label="创建时间" align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip label="操作" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="handleAdd(row)" type="primary" size="mini">编辑
                                </el-button>
                                <el-button plain @click="handleDelete(row)" type="danger" size="mini">删除
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                        :layout="layout" :total="total" @size-change="handleSizeChange"
                        @current-change="handleCurrentChange2"></el-pagination>
                </el-card>
            </el-col>
        </el-row>
        <edit ref="edit" @fetch-data="fetchData"></edit>

    </div>
</template>

<script>
import { getDicts } from "@/api/dictManagement";
import { doDelete } from '@/api/userManagement';
import { edit, getList } from "@/api_check/yiliaojigou";
import Edit from './components/edit';

export default {
    name: 'Index',
    components: { Edit },
    data() {
        return {
            credit_level_options: null,
            checked: false,
            isShow: false,
            list: null,
            listLoading: true,
            layout: 'total, sizes, prev, pager, next, jumper',
            total: 0,
            selectRows: '',
            elementLoadingText: '正在加载...',
            queryForm: {
                org_name: '',
                medical_insurance_num: '',
                credit_level: '',
                administrative_unit: '',
                pageNo: 1,
                pageSize: 10,
            },
        }
    },
    created() {
        var userinfo = JSON.parse(localStorage.getItem("userinfo"));
        console.log(userinfo);
        if (userinfo.org_code != 320399) {
            this.queryForm.administrative_unit = userinfo.org_code;
        }
        this.fetchData()
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
        setSelectRows(val) {
            this.selectRows = val
        },
        handleCurrentChange(val) {
            this.selectRows = val
        },
        handleAdd(row) {
            if (row.id) {
                this.$refs['edit'].showDia(row)
            } else {
                this.$refs['edit'].showDia()
            }
        },
        handleSizeChange(val) {
            this.queryForm.pageSize = val
            this.fetchData()
        },
        handleCurrentChange2(val) {
            this.queryForm.pageNo = val
            this.fetchData()
        },
        handleDelete(row) {
            if (row.id) {
                this.$baseConfirm('你确定要删除当前项吗', null, async () => {
                    const { code, msg } = await edit({
                        ids: row.id,
                        is_del: '1',
                        medical_insurance_num: row.medical_insurance_num
                    })
                    if (code == -1) {
                        this.$baseMessage(msg, 'error')
                    } else {
                        this.$baseMessage(msg, 'success')
                    }
                    await this.fetchData()
                })
            } else {
                if (this.selectRows != '' && this.selectRows != null) {
                    const ids = this.selectRows.map((item) => item.id).join()
                    this.$baseConfirm('你确定要删除选中项吗', null, async () => {
                        const { msg } = await edit({ ids })
                        this.$baseMessage(msg, 'success')
                        await this.fetchData()
                    })
                } else {
                    this.$baseMessage('未选中任何行', 'error')
                    return false
                }
            }
        },
        handleExport(row) {
            if (row.id) {
                this.$baseConfirm('你确定要导出当前项吗', null, async () => {
                    const { msg } = await doDelete({ ids: row.id })
                    this.$baseMessage('导出成功', 'success')
                    this.fetchData()
                })
            } else {
                if (this.selectRows != '' && this.selectRows != null) {
                    const ids = this.selectRows.map((item) => item.id).join()
                    this.$baseConfirm('你确定要导出选中项吗', null, async () => {
                        const { msg } = await doDelete({ ids })
                        this.$baseMessage('导出成功', 'success')
                        this.fetchData()
                    })
                } else {
                    this.$baseMessage('未选中任何行', 'error')
                    return false
                }
            }
        },
        queryData() {
            this.queryForm.pageNo = 1
            this.fetchData()
        },
        moreQuery() {
            this.isShow = !this.isShow
        },
        async fetchData() {
            await this.getDict();
            this.listLoading = true
            const { data } = await getList(this.queryForm)
            this.list = data.records
            this.total = data.total
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        async getDict() {
            const credit_level = await getDicts({ "type": "credit_level" });
            this.credit_level_options = credit_level.data
        },
        reseat() {
            this.queryForm.org_name = ''
            this.queryForm.medical_insurance_num = ''
            this.queryForm.credit_level = ''
            this.queryData()
        }
    },
}
</script>
