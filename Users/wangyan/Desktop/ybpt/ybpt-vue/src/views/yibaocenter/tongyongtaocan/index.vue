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
                                <el-form-item label="套餐名称">
                                    <el-input v-model.trim="queryForm.pack_name" @keyup.enter.native="queryData" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="套餐年份">
                                    <el-date-picker v-model.trim="queryForm.pack_year" type="year" value-format="yyyy">
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
                        <span class="tips">通用套餐列表</span>
                        <div class="right">
                            <el-button type="success" icon="el-icon-plus" @click="handleAdd"
                                v-show="sysUser.user_type == 1">
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
                        <el-table-column show-overflow-tooltip prop="pack_name" label="套餐名称" align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="pack_year" label="套餐年份" align="center" width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="pack_money" label="套餐金额" align="center" width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="applicable_scope_name" label="适用范围" align="center" width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="memo" label="套餐描述" align="center" width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="createTime" label="创建时间" align="center" width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="createUser" label="创建人" align="center" width="120px"></el-table-column>

                        <el-table-column show-overflow-tooltip label="操作" width="220" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="handleAdd(row)" type="primary" size="mini">编辑
                                </el-button>
                                <el-button plain @click="handleDelete(row)" type="danger" size="mini">删除
                                </el-button>
                                <el-button plain @click="handleView(row)" type="primary" size="mini">详情
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
        <views ref="views" @fetch-data="fetchData"></views>
        <shenhe ref="shenhe" @fetch-data="fetchData"></shenhe>
    </div>
</template>

<script>
import Edit from './components/edit'
import Views from './components/views'
import Shenhe from './components/shenhe'
import { edit, getList, toEdit } from "@/api_check/taocan";

export default {
    name: 'Index',
    components: { Edit, Views, Shenhe },
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
            sysUser: [],
            queryForm: {
                pack_name: '',
                pack_year: '',
                pageNo: 1,
                pageSize: 10,
                pack_source: '2',//套餐来源 (机构:1，通用:2)
            },
        }
    },
    created() {
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
        async handleAdd(row) {
            if (row.id) {
                row.item_to_meal = await this.toEditId(row.id)//体检项目
                this.$refs['edit'].showDia(row)
            } else {
                this.$refs['edit'].showDia()
            }
        },
        async handleView(row) {
            if (row.id) {
                row = await this.toEditName(row.id)
                this.$refs['views'].showDia(row)
            } else {
                this.$refs['views'].showDia()
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
        handleSh(row) {
            if (row.id) {
                this.$baseConfirm('你确定要审核当前项吗', null, async () => {
                    this.$refs['shenhe'].showDia(row.id)
                    await this.fetchData()
                })
            } else {
                if (this.selectRows.length > 0) {
                    const ids = this.selectRows.map((item) => item.id).join()
                    this.$baseConfirm('你确定要审核选中项吗', null, async () => {
                        // const { msg } = await doDelete({ ids })
                        this.$refs['shenhe'].showDia(ids)
                        //this.$baseMessage('模拟审核成功', 'success')
                        //this.fetchData()
                    })
                } else {
                    this.$baseMessage('未选中任何行', 'error')
                    return false
                }
            }
        },

        handleDelete(row) {
            if (row.id) {
                this.$baseConfirm('你确定要删除当前项吗', null, async () => {
                    const { msg } = await edit({ id: row.id, is_del: '1' })
                    this.$baseMessage(msg, 'success')
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
            await this.getInit();
            this.listLoading = true
            const { data } = await getList(this.queryForm)
            this.list = data.records
            this.total = data.total
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        async getInit() {
            const { data } = await toEdit()
            this.sysUser = data.data.sysUser
        },
        async toEditId(id) {
            const { data } = await toEdit({ id: id })
            const item_to_meal = []
            if (data.data.item_to_meal) {
                for (let i = 0; i < data.data.item_to_meal.length; i++) {
                    item_to_meal.push(data.data.item_to_meal[i].id)
                }
            }
            return item_to_meal
        },
        async toEditName(id) {
            const { data } = await toEdit({ id: id })
            const item_to_meal = []
            if (data.data.item_to_meal) {
                for (let i = 0; i < data.data.item_to_meal.length; i++) {
                    item_to_meal.push({
                        name: data.data.item_to_meal[i].item_name,
                        code: data.data.item_to_meal[i].item_no
                    })
                }
            }
            return item_to_meal
        },
        reseat() {
            this.queryForm.pack_name = ''
            this.queryForm.pack_year = ''
            this.queryData()
        }
    },
}
</script>
