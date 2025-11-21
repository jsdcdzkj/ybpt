<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">

                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">查询条件</span>
                        <vab-icon :icon="['fas', 'angle-up']" v-if="isShow" @click="moreQuery"></vab-icon>
                        <vab-icon :icon="['fas', 'angle-down']" v-else @click="moreQuery"></vab-icon>
                    </div>

                    <el-form :model="queryForm" ref="queryForm" label-width="160px">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="技师姓名">
                                    <el-input placeholder="技师姓名" v-model.trim="queryForm.med_tecn_name" @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="技师代码">
                                    <el-input placeholder="技师代码" v-model.trim="queryForm.med_tecn_code" @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <vab-query-form>
                            <vab-query-form-right-panel :span="24">
                                <el-form-item class="mr0">
                                    <el-button icon="el-icon-refresh-left" @click="resetForm()">重 置
                                    </el-button>
                                    <el-button icon="el-icon-search" type="queryForm" @click="fetchData">
                                        查 询
                                    </el-button>
                                </el-form-item>
                            </vab-query-form-right-panel>
                        </vab-query-form>
                    </el-form>
                </el-card>
            </el-col>

            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">医疗技师</span>
                    </div>
                    <el-table
                            v-loading="listLoading"
                            ref="listTable"
                            stripe
                            :data="list"
                            :element-loading-text="elementLoadingText"
                            highlight-current-row
                            border
                            @current-change="handleCurrentChange"
                            height="420px"
                    >
                        <template slot="empty">
                            <el-empty :image-size="200"></el-empty>
                        </template>
                        <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" width="80px">
                            <template #default="scope">
                                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column label="技师代码" prop="med_tecn_code" align="center" show-overflow-tooltip></el-table-column>
                        <el-table-column label="技师姓名" prop="med_tecn_name" align="center" show-overflow-tooltip></el-table-column>
                        <el-table-column label="人员编号" prop="psn_no" align="center" show-overflow-tooltip></el-table-column>
                        <el-table-column label="执业类别" prop="phar_prac_type_name" align="center" show-overflow-tooltip></el-table-column>
                        <el-table-column label="执业范围" prop="phar_prac_scp_name" align="center" show-overflow-tooltip></el-table-column>
                        <el-table-column label="执业机构编号" prop="pracins_no" align="center" show-overflow-tooltip></el-table-column>
                        <el-table-column label="执业机构名称" prop="pracins_name" align="center" show-overflow-tooltip></el-table-column>
                        <el-table-column label="执业地区" prop="prac_regn" align="center" show-overflow-tooltip></el-table-column>

                        <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="handlechuli(row)" type="primary" size="mini">详情</el-button>
                            </template>
                        </el-table-column>
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
                </el-card>
            </el-col>
        </el-row>
        <views ref="views" @fetch-data="fetchData"></views>
    </div>
</template>

<script>
import Views from './components/view'
import {medTecnInfoB} from '@/api/query'

export default {
    name: 'Index',
    components: {Views},
    data() {
        return {
            value1: '',
            checked: false,
            isShow: false,
            list: null,
            listLoading: false,
            layout: 'total, sizes, prev, pager, next, jumper',
            total: 0,
            selectRows: '',
            elementLoadingText: '正在加载...',
            queryForm: {
                med_tecn_name: '',
                med_tecn_code: '',
                pageNo: 1,
                pageSize: 10,
            },
        }
    },
    methods: {
        handleCurrentChange(val) {
            this.selectRows = val
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
            this.$refs['views'].showDia(row)
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
            const res = await medTecnInfoB(this.queryForm)
            this.list = res.data.records;
            this.total = res.data.total;
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        resetForm() {
            this.queryForm.med_tecn_code = ''
            this.queryForm.med_tecn_name = ''
            this.queryData();
        },
    },
}
</script>
