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
                                <el-form-item label="医疗机构代码">
                                    <el-input v-model.trim="queryForm.medins_code" placeholder="医疗机构代码"
                                              @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item label="医疗机构名称">
                                    <el-input v-model.trim="queryForm.medins_name" placeholder="医疗机构名称"
                                              @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item label="医师姓名">
                                    <el-input placeholder="医师姓名" v-model.trim="queryForm.dr_name"
                                              @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="医师代码">
                                    <el-input placeholder="医师代码" v-model.trim="queryForm.dr_code"
                                              @keyup.enter.native="queryData"/>
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
                        <span class="tips">医师信息</span>
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

                        <el-table-column align="center" label="医疗机构代码" prop="medins_code"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="医疗机构名称" prop="medins_name"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="人员编号" prop="psn_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column label="医师代码" prop="dr_code" align="center"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column label="人员编号" prop="psn_no" align="center"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column label="医师姓名" prop="dr_name" align="center"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column label="执业地区" prop="prac_regn" align="center"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column label="医师执业类别名称" prop="dr_prac_type_name" align="center"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column label="医师执业范围名称" prop="dr_prac_scp_name" align="center"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column label="主执业机构名称" prop="main_pracins_name" align="center"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column label="主执业机构地址" prop="main_pracins_addr" align="center"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="医师执业证书编号" prop="dr_prac_cert_no"
                                         show-overflow-tooltip></el-table-column>

                        <!--                        <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">-->
                        <!--                            <template #default="{ row }">-->
                        <!--                                <el-button plain @click="handlechuli(row)" type="primary" size="mini">详情</el-button>-->
                        <!--                            </template>-->
                        <!--                        </el-table-column>-->
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
import {drInfoB} from '@/api/query'

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
                psn_name: '',
                certno: '',
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
            const res = await drInfoB(this.queryForm)
            this.list = res.data.records;
            this.total = res.data.total;
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        resetForm() {
            this.queryForm.medins_code = ''
            this.queryForm.medins_name = ''
            this.queryForm.dr_code = ''
            this.queryForm.dr_name = ''
            this.queryForm.certno = ''
            this.queryForm.psn_name = ''
            this.queryData();
        },
    },
}
</script>
