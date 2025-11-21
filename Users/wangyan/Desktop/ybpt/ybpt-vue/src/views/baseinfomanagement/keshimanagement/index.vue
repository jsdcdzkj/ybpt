<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">信息查询</span>
                        <el-button icon="el-icon-search" type="primary" class="right" @click="fetchData">
                            查 询
                        </el-button>
                    </div>
                    <el-form label-width="160px">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="科室编号">
                                    <el-input v-model.trim="queryForm.dept_no" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="科室名称">
                                    <el-input v-model.trim="queryForm.dept_name" />
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card tablecard" shadow="never">
                    <div slot="header">
                        <span class="tips">科室列表</span>
                        <el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">
                            新增
                        </el-button>
                    </div>
                    <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
                        :element-loading-text="elementLoadingText" highlight-current-row border
                        @current-change="handleCurrentChange" height="calc(100% - 50px)">
                        <el-table-column label="序号" width="80px" align="center">
                            <template #default="scope">
                                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="机构编号" align="center" prop="org_code"></el-table-column>
                        <el-table-column show-overflow-tooltip label="机构名称" align="center" prop="org_name"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="dept_no" width="180px" label="科室编号" align="center">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="dept_name" label="科室名称" align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="dept_resper_name" label="科室负责人姓名" align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="dept_estbdat" label="科室成立日期" align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="memo" label="备注" align="center" width="300px"></el-table-column>
                        <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="handleEdit(row)" type="primary" size="mini">
                                    编辑
                                </el-button>
                                <el-button plain @click="handlecancel(row)" type="danger" size="mini">
                                    删除
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
import { edit, getList } from '@/api/medinsDeptB'
import Edit from './components/edit'

export default {
    name: 'Index',
    components: {Edit},
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
                pageNo: 1,
                pageSize: 10,
                dept_type: '',
                dept_no: '',
                dept_name: '',
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
        handleCurrentChange(val) {
            this.selectRows = val
        },
        handleAdd() {
            this.$refs['edit'].showDia()
        },
        handleSizeChange(val) {
            this.queryForm.pageSize = val
            this.fetchData()
        },
        handleCurrentChange2(val) {
            this.queryForm.pageNo = val
            this.fetchData()
        },
        handleEdit(row) {
            this.$refs['edit'].showDia(row)
        },
        handlecancel(row) {
            if (row.id) {
                this.$baseConfirm('确认进行要删除？', null, async () => {
                    const {msg} = await edit({id: row.id, vali_flag: 0, dept_no: row.dept_no})
                    this.$baseMessage(msg, 'success')
                    this.fetchData()
                })
            } else {
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
            this.listLoading = true
            const {data} = await getList(this.queryForm)
            this.list = data.records
            this.total = data.total
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
    },
}
</script>
