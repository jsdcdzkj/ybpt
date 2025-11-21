<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :lg="24" :md="24" :sm="24" :xl="24" :xs="24">

                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">查询条件</span>
                        <vab-icon v-if="isShow" :icon="['fas', 'angle-up']" @click="moreQuery"></vab-icon>
                        <vab-icon v-else :icon="['fas', 'angle-down']" @click="moreQuery"></vab-icon>
                    </div>

                    <el-form ref="queryForm" :model="queryForm" label-width="160px">
                        <el-row :gutter="20">
                            <!--                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医保电子凭证"><el-input placeholder="医保电子凭证" v-model.trim="queryForm.medins_code" @keyup.enter.native="queryData"/></el-form-item></el-col>-->
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item label="身份证号">
                                    <el-input v-model.trim="queryForm.certno" placeholder="身份证号"
                                              @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item class="custemitem" label="开始日期">
                                    <el-date-picker
                                            v-model="queryForm.begndate"
                                            type="date"
                                    ></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item class="custemitem" label="结束日期">
                                    <el-date-picker
                                            v-model="queryForm.enddate"
                                            type="date"
                                    ></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item class="custemitem" label="申请时间">
                                    <el-date-picker
                                            v-model="queryForm.crte_time"
                                            type="date"
                                    ></el-date-picker>
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

            <el-col :lg="24" :md="24" :sm="24" :xl="24" :xs="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">医师信息</span>
                    </div>
                    <el-table
                            ref="listTable"
                            v-loading="listLoading"
                            :data="list"
                            :element-loading-text="elementLoadingText"
                            border
                            height="420px"
                            highlight-current-row
                            stripe
                            @current-change="handleCurrentChange"
                    >
                        <template slot="empty">
                            <el-empty :image-size="200"></el-empty>
                        </template>
                        <el-table-column align="center" label="序号" prop="id" show-overflow-tooltip width="80px">
                            <template #default="scope">
                                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                            </template>
                        </el-table-column>

                        <el-table-column align="center" label="姓名" prop="psn_name"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="身份证号" prop="certno"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="医保区划" prop="insu_admdvs"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="人员类型" prop="psn_cert_type_name"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="转院备案类别" prop="refl_fil_type_name"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="险种类型" prop="insutype_name"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="就医地行政区划" prop="mdtrtarea_admdvs"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="开始日期" prop="begndate"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="结束日期" prop="enddate"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="申请时间" prop="crte_time"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="复核标志" prop="rchk_flag_name"
                                         show-overflow-tooltip></el-table-column>

                    </el-table>
                    <el-pagination
                            :current-page="queryForm.pageNo"
                            :layout="layout"
                            :page-size="queryForm.pageSize"
                            :total="total"
                            background
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange2"
                    ></el-pagination>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import {reflAppyEvtC} from '@/api/query'

export default {
    name: 'Index',
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
                certno: '',
                begndate: '',
                enddate: '',
                crte_time: '',
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
        queryData() {
            this.queryForm.pageNo = 1
            this.fetchData()
        },
        moreQuery() {
            this.isShow = !this.isShow
        },
        async fetchData() {
            this.listLoading = true
            const res = await reflAppyEvtC(this.queryForm)
            this.list = res.data.records;
            this.total = res.data.total;
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        resetForm() {
            this.queryForm.certno = ''
            this.queryForm.begndate = ''
            this.queryForm.enddate = ''
            this.queryForm.crte_time = ''
            this.queryData();
        },
    },
}
</script>
