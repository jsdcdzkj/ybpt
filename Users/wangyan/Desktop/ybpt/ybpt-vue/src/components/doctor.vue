<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogFormVisible"
            width="55%"
            @close="close"
            append-to-body
    >
        <vab-query-form>
            <el-form :inline="true" :model="queryForm">
                <vab-query-form-left-panel :span="20">
                    <el-form-item label="医师编码">
                        <el-input v-model.trim="queryForm.phar_code" clearable/>
                    </el-form-item>
                    <el-form-item label="医师名称">
                        <el-input v-model.trim="queryForm.phar_name" clearable/>
                    </el-form-item>

                </vab-query-form-left-panel>
                <vab-query-form-right-panel :span="4">
                    <el-form-item>
                        <el-button @click="reseat()">重 置</el-button>
                        <el-button type="primary" @click="queryData">查 询</el-button>
                    </el-form-item>
                </vab-query-form-right-panel>
            </el-form>
        </vab-query-form>
        <el-table
                ref="listTable"
                stripe
                :data="list"
                :element-loading-text="elementLoadingText"
                highlight-current-row
                border
                @current-change="handleCurrentChange"
                height="260px"
        >
            <template slot="empty">
                <el-empty :image-size="150"></el-empty>
            </template>
            <el-table-column label="选择" width="80" align="center">
                <template slot-scope="scope">
                    <el-radio :label="scope.row.phar_code" v-model="id"><span></span></el-radio>
                </template>
            </el-table-column>
            <el-table-column
                    label="序号"
                    width="80"
                    align="center"
                    show-overflow-tooltip
                    prop="id"
            ><template slot-scope="scope">
                <span v-text="getIndex(scope.$index)"> </span>
            </template></el-table-column>
            <el-table-column
                    label="医师编码"
                    align="center"
                    show-overflow-tooltip
                    prop="phar_code"
            ></el-table-column>
            <el-table-column
                    label="医师姓名"
                    align="center"
                    show-overflow-tooltip
                    prop="phar_name"
            ></el-table-column>
        </el-table>

        <el-pagination
                background
                :current-page="queryForm.pageNo"
                :page-size="queryForm.pageSize"
                :layout="layout"
                :total="total"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange2"
        ></el-pagination>

        <div slot="footer" class="dialog-footer">
            <el-button @click="close">关 闭</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
        </div>
    </el-dialog>
</template>
<script>
    import {selectPharInfo} from '@/api/pharInfoB.js'

    export default {
        name: 'Index',
        components: {},
        data() {
            return {
                currentRow: null,
                id: '',
                tableData: [],
                title: '',
                dialogFormVisible: false,
                value1: '',
                checked: false,
                isShow: false,
                list: [],
                listLoading: true,
                layout: 'total, sizes, prev, pager, next, jumper',
                total: 0,
                selectRows: '',
                medins_code: '',
                elementLoadingText: '正在加载...',
                queryForm: {
                    pageNo: 1,
                    pageSize: 10,
                    phar_code: '',
                    phar_name: '',
                },
            }
        },
        created() {
            this.fetchData()
        },
        porps: {
            //父组件传递过来的初始选中值，根据自己项目需求设置
            chooseData: {
                type: Object,
            },
        },
        watch: {
            //观察是否有父组件传递的初始值或者变化，重新选中
            chooseData(val) {
                if (val) {
                    this.radio = false
                    this.getInitChoose()
                }
            },
        },
        beforeDestroy() {
        },
        mounted() {
        },
        methods: {
            handleCurrentChange(val) {
                this.id=val.phar_code;
                this.selectRows = val
            },
            handleSizeChange(val) {
                this.queryForm.pageSize = val
                this.fetchData()
            },
            getCurrentRow(index) {
                this.radio = index
            },
            handleCurrentChange2(val) {
                this.queryForm.pageNo = val
                this.fetchData()
            },
            queryData() {
                this.queryForm.pageNo = 1
                this.fetchData()
            },
            moreQuery() {
                this.isShow = !this.isShow
            },
            //设置单选框选择状态
            getInitChoose() {
                if (this.chooseData) {
                    let index = this.tableData.findIndex(
                        (item) => item.userUuid == this.chooseData.id
                    )
                }
                if (index > -1) {
                    this.radio = index
                }
            },
            async fetchData() {
                var that = this;
                if (that.medins_code == "") {
                    that.$baseMessage('请选择鉴定定点医药机构', 'error')
                }else {
                    if (that.queryForm.phar_code == "" && that.queryForm.phar_name == "") {
                        that.$baseMessage('请输入编号或名称', 'error')
                    } else {
                        selectPharInfo(that.queryForm).then((res) => {
                            that.list = res.data.records;
                            that.total = res.data.total;
                            if (res.data.total == 0) {
                                that.$baseMessage('未查询到任何相关数据', 'error')
                            }
                        })
                    }
                }
            },
            showDia(data) {
                this.list = [];
                this.total = 0;
                this.queryForm.phar_code = "";
                this.queryForm.phar_name = "";
                this.medins_code = data;

                this.dialogFormVisible = true
            },
            close() {
                // this.$refs['form'].resetFields()
                // this.form = this.$options.data().form
                this.dialogFormVisible = false
            },
            save() {
                var that = this;
                that.$baseMessage('操作成功', 'success')
                this.$emit('fetch-data', that.selectRows)
                this.close()
            },
            //获取表格序号
            getIndex($index) {
                //表格序号
                return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
            },
            reseat(){
                this.queryForm.phar_code="" ;
                this.queryForm.phar_name="" ;
            }
        },
    }
</script>
<style lang="scss" scoped>
    ::v-deep {
        .el-radio__label {
            display: none;
        }
    }
</style>