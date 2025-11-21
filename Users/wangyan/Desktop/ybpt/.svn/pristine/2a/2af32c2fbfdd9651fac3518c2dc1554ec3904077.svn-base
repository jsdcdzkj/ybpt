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
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="银行编码">
                                    <el-input v-model.trim="queryForm.bankNo" @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="银行名称">
                                    <el-input v-model.trim="queryForm.bankName" @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card tablecard" shadow="never">
                    <div slot="header">
                        <span class="tips">银行列表</span>
                        <div class="right">
                            <el-button type="success" icon="el-icon-plus" @click="handleAdd">
                                新增
                            </el-button>
                        </div>
                    </div>
                    <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
                              highlight-current-row
                              border @current-change="handleCurrentChange" @selection-change="setSelectRows"
                              height="calc(100% - 50px)">
                        <template slot="empty">
                            <el-empty :image-size="200"></el-empty>
                        </template>
                        <el-table-column show-overflow-tooltip label="银行名称" prop="bankName" align="center"
                                         width="280px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="bankNo" width="220px" label="银行编码" align="center">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="address" label="详细地址"
                                         align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip label="操作" width="250" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="opendktj(row)" type="primary" size="mini">贷款统计</el-button>
                                <el-button plain @click="handleAdd(row)" type="primary" size="mini">编辑</el-button>
                                <el-button plain @click="handleDelete(row)" type="danger" size="mini">删除</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                                   :layout="layout"
                                   :total="total" @size-change="handleSizeChange"
                                   @current-change="handleCurrentChange2"></el-pagination>
                </el-card>
            </el-col>
        </el-row>
        <edit ref="edit" @fetch-data="fetchData"></edit>
        <dktj ref="dktj" @fetch-data="fetchData"></dktj>
        <usermana ref="usermana" @fetch-data="fetchData"></usermana>

    </div>
</template>

<script>
    import {delBank, selectList} from '@/api/bank.js'
    import Edit from './components/edit'
    import Dktj from './components/dktj'
    import Usermana from './components/usermana'

    export default {
        name: 'Index',
        components: {Edit, Usermana, Dktj},
        data() {
            return {
                value1: '',
                checked: false,
                isShow: false,
                list: [],
                listLoading: true,
                layout: 'total, sizes, prev, pager, next, jumper',
                total: 0,
                selectRows: '',
                tableHeight: '100%',
                elementLoadingText: '正在加载...',
                queryForm: {
                    pageNo: 1,
                    pageSize: 10,
                    bankName: '',
                    bankNo: '',
                },
            }
        },
        created() {
            this.fetchData()
        },
        beforeDestroy() {
        },
        mounted() {
            // getHeight()
            // //增加监听事件，窗口变化时得到高度。
            // window.addEventListener('resize',this.getHeight,false)
        },
        methods: {
            // getHeight(){
            // //获取浏览器高度并计算得到表格所用高度。
            //     this.tableHeight=document.documentElement.clientHeight-140
            //   },
            setSelectRows(val) {
                this.selectRows = val
            },
            handleCurrentChange(val) {
                this.selectRows = val
            },
            openwin() {
                this.$refs['cardnum'].showDia()
            },
            handleAdd(row) {
                if (row.id) {
                    this.$refs['edit'].showDia(row)
                } else {
                    this.$refs['edit'].showDia()
                }
            },
            opendktj(row) {
                this.$refs['dktj'].showDia(row)
            },
            handleUser(row) {
                this.$refs['usermana'].showDia(row.id)
            },
            handleSizeChange(val) {
                this.queryForm.pageSize = val
                this.fetchData()
            },
            handleCurrentChange2(val) {
                this.queryForm.pageNo = val
                this.fetchData()
            },
            handlechuli(row) {
                this.$refs['views'].showDia(row.id)
            },
            handleDelete(row) {
                var that = this;
                if (row.id) {
                    that.$baseConfirm('你确定要删除当前项吗', null, async () => {
                        delBank(row).then((res) => {
                            that.fetchData()
                        })

                    })
                } else {
                    if (that.selectRows != '' && that.selectRows != null) {
                        const ids = that.selectRows.map((item) => item.id).join()
                        that.$baseConfirm('你确定要删除选中项吗', null, async () => {
                            const {msg} = await doDelete({ids})
                            that.$baseMessage(msg, 'success')
                            that.fetchData()
                        })
                    } else {
                        that.$baseMessage('未选中任何行', 'error')
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
            fetchData() {
                var that = this;
                selectList(that.queryForm).then((res) => {
                    if (res.code == 0) {
                        that.list = res.data.records;
                        console.log(that.list);
                        that.total = res.data.total
                    }
                })
            },
            //获取表格序号
            getIndex($index) {
                //表格序号
                return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
            },
            reseat() {
                this.queryForm.bankNo = "";
                this.queryForm.bankName = "";
                this.fetchData();
            }
        },
    }
</script>
