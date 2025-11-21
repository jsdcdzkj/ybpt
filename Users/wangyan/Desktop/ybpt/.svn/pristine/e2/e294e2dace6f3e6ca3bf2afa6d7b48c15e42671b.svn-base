<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :lg="24" :md="24" :sm="24" :xl="24" :xs="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">查询条件</span>
                        <vab-icon
                                v-if="isShow"
                                :icon="['fas', 'angle-up']"
                                @click="moreQuery"
                        ></vab-icon>
                        <vab-icon
                                v-else
                                :icon="['fas', 'angle-down']"
                                @click="moreQuery"
                        ></vab-icon>
                    </div>
                    <el-form label-width="160px">
                        <el-row :gutter="20">
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item label="统筹区">
                                    <el-select v-model="queryForm.poolarea_no" clearable
                                               style="width: 100%">
                                        <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                                                   :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item label="开始日期" prop="begntime">
                                    <el-date-picker
                                            v-model="queryForm.begntime"
                                            clearable
                                            type="date"
                                            value-format="yyyy-MM-dd">
                                        >
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item label="结束日期" prop="endtime">
                                    <el-date-picker
                                            v-model="queryForm.endtime"
                                            clearable
                                            type="date"
                                            value-format="yyyy-MM-dd">
                                        >
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                <el-form-item label="超时时间" prop="endtime">
                                    <el-date-picker
                                            v-model="queryForm.endtime"
                                            clearable
                                            type="date"
                                            value-format="yyyy-MM-dd">
                                        >
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <vab-query-form>
                            <vab-query-form-right-panel :span="24">
                                <el-form-item class="mr0">
                                    <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
                                    <el-button icon="el-icon-search" type="primary" @click="queryData">
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
                        <span class="tips">零报办件数据查询</span>
                        <div class="right">
                            <el-button icon="el-icon-upload2" type="primary" @click="handleExport">
                                导出
                            </el-button>
                        </div>
                    </div>
                    <el-table
                            ref="listTable"
                            v-loading="loading"
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
                        <el-table-column
                                align="center"
                                label="序号"
                                prop="id"
                                show-overflow-tooltip
                                width="80px"
                        >
                            <template slot-scope="scope">
                                <span v-text="getIndex(scope.$index)"> </span>
                            </template>
                        </el-table-column>
                        <el-table-column align="center" label="开始时间" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="终止时间" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="统筹区" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="受理号" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="受理时间" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="超时时间" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="结算时间" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="结算人员" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="一审时间" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="一审人员" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="二审时间" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="二审人员" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="身份证号码" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="姓名" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="医疗类别" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>
                        <el-table-column align="center" label="发票金额" prop="poolarea_no"
                                         show-overflow-tooltip></el-table-column>

                        <!--                        <el-table-column-->
                        <!--                                align="center"-->
                        <!--                                fixed="right"-->
                        <!--                                label="操作"-->
                        <!--                                show-overflow-tooltip-->
                        <!--                                width="160"-->
                        <!--                        >-->
                        <!--                            <template #default="{ row }">-->
                        <!--                                <el-button-->
                        <!--                                        plain-->
                        <!--                                        size="mini"-->
                        <!--                                        type="primary"-->
                        <!--                                        @click="handlechuli(row)"-->
                        <!--                                >-->
                        <!--                                    详情-->
                        <!--                                </el-button>-->
                        <!--                            </template>-->
                        <!--                        </el-table-column>-->
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
        <!--        <views ref="views" @fetch-data="fetchData"></views>-->
    </div>
</template>

<script>
// import Views from './components/view'
import {sporadicHandle, sporadicHandleExport} from '@/api/query'
import {getNatDataDicA} from "@/api/reflAppyEvtC";
import {getDicts} from "@/api/dictManagement";

export default {
    name: 'Index',
    // components: {Views},
    data() {
        return {
            value1: '',
            checked: false,
            isShow: false,
            loading: false,
            list: null,
            listLoading: true,
            layout: 'total, sizes, prev, pager, next, jumper',
            total: 0,
            selectRows: '',
            elementLoadingText: '正在加载...',
            INSUTYPE_OPTIONS: null,
            PSN_CERT_TYPE_OPTIONS: null,
            MED_TYPE_OPTIONS: null,
            admdvs: null,
            queryForm: {
                pageNo: 1,
                pageSize: 10,
                begntime: '',
                endtime: '',
                poolarea_no: '',
                med_type: '',
                insutype: '',
                psn_cert_type: '',
            },
            insutypeList: []
        }
    },
    created() {
        // this.fetchData();
        this.getNatDataDicA()
        this.getAdmdvs()
        this.getCurrentMonthFirst()
        this.getCurrentMonthLast()
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
        handleExport(row) {
            this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
                this.listLoading = true
                await sporadicHandleExport(this.queryForm).then((res) => {
                    let fileName = "零报办件数据查询导出.xls";
                    let objectUrl = URL.createObjectURL(new Blob([res.data]))
                    const link = document.createElement('a')
                    link.download = decodeURI(fileName)
                    link.href = objectUrl
                    link.click()
                    this.listLoading = false;
                    this.$baseMessage("导出成功！", 'success')
                })

            })
        },
        getCurrentMonthFirst() {
            const date = new Date();
            date.setDate(1)
            let month = parseInt(date.getMonth() + 1)
            let day = date.getDate()
            if (month < 10) month = '0' + month
            if (day < 10) day = '0' + day
            this.queryForm.begntime = date.getFullYear() + '-' + month + '-' + day
        },
        getCurrentMonthLast() {
            const date = new Date()
            const year = date.getFullYear()
            let month = date.getMonth() + 1
            month = month < 10 ? '0' + month : month
            const day = new Date(year, month, 0)
            this.queryForm.endtime = year + '-' + month + '-' + day.getDate()
        },
        async getNatDataDicA() {
            const MED_TYPE = await getNatDataDicA({'DIC_TYPE_CODE': 'MED_TYPE', 'DIC_SOUC_ADMDVS': '320300'})
            this.MED_TYPE_OPTIONS = MED_TYPE.data
            const INSUTYPE = await getNatDataDicA({'DIC_TYPE_CODE': 'INSUTYPE'})
            this.INSUTYPE_OPTIONS = INSUTYPE.data
        },
        async getAdmdvs() {
            const res = await getDicts({"type": "ADMDVS"});
            if (res.code == "0") {
                this.admdvs = res.data;
            }
        },
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
        fetchData() {
            var that = this;
            that.loading = true;
            sporadicHandle(that.queryForm).then((res) => {
                if (res.code == 0) {
                    that.list = res.data.records;
                    that.total = res.data.total
                }
                that.loading = false;
            })
        },
        //获取表格序号
        getIndex($index) {
            //表格序号
            return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
        },
        reseat() {
            this.queryForm.begntime = ""
            this.queryForm.endtime = ""
            this.queryForm.poolarea_no = ""
            this.queryForm.med_type = ""
            this.queryForm.insutype = ""
        },
        formatDate: function (row, column) {
            let data = row[column.property]
            if (data == null) {
                return null
            }
            let date = new Date(data);
            var o = {
                "M+": date.getMonth() + 1,                 //月份
                "d+": date.getDate(),                    //日
                "h+": date.getHours(),                   //小时
                "m+": date.getMinutes(),                 //分
                "s+": date.getSeconds(),                 //秒
                "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                "S": date.getMilliseconds()             //毫秒
            };
            var fmt = "yyyy-MM-dd";
            if (/(y+)/.test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
            }
            for (var k in o) {
                if (new RegExp("(" + k + ")").test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                }
            }
            return fmt;
        },
    },
}
</script>
