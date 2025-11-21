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
                                <el-form-item label="名称">
                                    <el-input v-model.trim="queryForm.standard_name" @keyup.enter.native="queryData" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="年份">
                                    <el-date-picker v-model.trim="queryForm.year" type="year" value-format="yyyy">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card tablecard" shadow="never">
                    <div slot="header">
                        <span class="tips">体检标准管理列表</span>
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
                        <el-table-column show-overflow-tooltip label="标准名称" align="center" prop="standard_name">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="year" width="120px" label="标准年份" align="center">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="standard_money" label="标准金额" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="examination_num" label="年体检次数" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="cancel_num" label="撤销次数" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="createTime" label="创建时间" align="center"
                            width="230px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="createUser" label="创建人" align="center">
                        </el-table-column>

                        <el-table-column show-overflow-tooltip label="操作" width="200" align="center" fixed="right">
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
import { edit, getList } from "@/api_check/tijianbiaozhun";
import Edit from './components/edit';

export default {
    name: 'Index',
    components: { Edit },
    data() {
        return {
            value1: '',
            checked: false,
            isShow: false,
            list: null,
            listLoading: true,
            layout: 'total, sizes, prev, pager, next, jumper',
            total: 0,
            selectRows: '',
            elementLoadingText: '正在加载...',
            queryForm: {
                standard_name: '',
                year: '',
                administrative_unit: '',
                pageNo: 1,
                pageSize: 10,
            },
        }
    },
    created() {
        var userinfo = JSON.parse(localStorage.getItem("userinfo"));
        console.log(userinfo);
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
                    const { code, msg } = await edit({ id: row.id, is_del: '1' })
                    if (code == -1) {
                        this.$baseMessage(msg, 'error')
                    } else {
                        this.$baseMessage(msg, 'success')
                    }
                    this.fetchData()
                })
            } else {
                if (this.selectRows != '' && this.selectRows != null) {
                    const ids = this.selectRows.map((item) => item.id).join()
                    this.$baseConfirm('你确定要删除选中项吗', null, async () => {
                        const { msg } = await edit({ ids })
                        this.$baseMessage(msg, 'success')
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
        },
        reseat() {
            this.queryForm.standard_name = ''
            this.queryForm.year = ''
            this.queryData()
        }
    },
}
</script>
