<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form label-width="120px">
                    <el-card class="card" shadow="never">

                        <div slot="header">
                            <span class="tips">信息查询</span>
                            <el-button icon="el-icon-search" @click="queryData" type="primary" class="right">
                                查 询
                            </el-button>
                        </div>

                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="结算月份">
                                    <el-date-picker type="month" format="yyyy-MM" value-format="yyyy-MM"
                                        v-model="queryForm.settle_date"></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="统筹区">
                                    <el-select clearable @change="admdvsChange" v-model="queryForm.insu_admdvs"
                                        style="width: 100%">
                                        <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                                            :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="对账类型">
                                    <el-select clearable v-model="queryForm.reconciliation_type" style="width: 100%">
                                        <el-option v-for="item in reconciliationTypes" :key="item.value"
                                            :label="item.label" :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="对账状态">
                                    <el-select clearable v-model="queryForm.check_status" style="width: 100%">
                                        <el-option label="对账通过" value="1"></el-option>
                                        <el-option label="对账不通过" value="2"></el-option>
                                        <el-option label="未对账" value="0"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>

                    </el-card>
                </el-form>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">基本信息</span>
                        <el-button icon="el-icon-notebook-2" @click="reconciliation" type="primary" class="right">
                            对 账
                        </el-button>
                    </div>
                    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="医疗机构编码" prop="fixmedins_code">
                                    <el-input v-model.trim="form.fixmedins_code" disabled />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="医疗机构名称" prop="fixmedins_name">
                                    <el-input v-model.trim="form.fixmedins_name" disabled />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="统筹区" prop="insu_admdvs">
                                    <el-select disabled clearable v-model="form.insu_admdvs" style="width: 100%">
                                        <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                                            :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="对账类型" prop="reconciliation_type">
                                    <el-select disabled clearable v-model="form.reconciliation_type"
                                        style="width: 100%">
                                        <el-option v-for="item in reconciliationTypes" :key="item.value"
                                            :label="item.label" :value="item.value">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="医疗费用总额" prop="medfee_sumamt">
                                    <el-input v-model.trim="form.medfee_sumamt" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="统筹基金" prop="hifp_pay">
                                    <el-input v-model.trim="form.hifp_pay" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="个人账户" prop="acct_pay">
                                    <el-input v-model.trim="form.acct_pay" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="现金支付" prop="cash_payamt">
                                    <el-input v-model.trim="form.cash_payamt" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="大额医疗补助基金" class="custemitem" prop="hifob_pay">
                                    <el-input v-model.trim="form.hifob_pay" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="公务员基金" prop="cvlserv_pay">
                                    <el-input v-model.trim="form.cvlserv_pay" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="账户共济支付金额" class="custemitem" prop="acct_mulaid_pay">
                                    <el-input v-model.trim="form.acct_mulaid_pay" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="居民大病医疗保险基金" class="custemitem" prop="hifmi_pay">
                                    <el-input v-model.trim="form.hifmi_pay" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="补充医疗保险基金" class="custemitem" prop="hifes_pay">
                                    <el-input v-model.trim="form.hifes_pay" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="医疗救助" prop="maf_pay">
                                    <el-input v-model.trim="form.maf_pay" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="医院垫付" prop="ownpay_hosp_part">
                                    <el-input v-model.trim="form.ownpay_hosp_part" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="4" :xl="4">
                                <el-form-item label="其他基金" prop="othfund_pay">
                                    <el-input v-model.trim="form.othfund_pay" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="人次" prop="person_count">
                                    <el-input v-model.trim="form.person_count" />
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                                <el-form-item label="人数" prop="person_num">
                                    <el-input v-model.trim="form.person_num" />
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">对账结果列表&nbsp;&nbsp;&nbsp;<span style="color: red">(双击选择需要对账的数据)</span></span>
                        <el-button icon="el-icon-upload" @click="excelDetails_month" type="primary" class="right">
                            导出
                        </el-button>
                    </div>
                    <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
                        :element-loading-text="elementLoadingText" highlight-current-row @row-dblclick="dblclick" border
                        @current-change="handleCurrentChange"  height="420px">
                        <template slot="empty">
                            <el-empty :image-size="200"></el-empty>
                        </template>
                        <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
                        <el-table-column show-overflow-tooltip prop="settle_date" label="结算日期" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip label="对账类型" align="center" prop="reconciliationName"
                            width="230px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="admdvsName" width="120px" label="统筹区"
                            align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="person_count" align="center" label="人次"
                            width="160px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="person_num" align="center" label="人数"
                            width="160px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="medfee_sumamt" label="医疗费总额" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="hifp_pay" label="统筹基金支出" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="cvlserv_pay" label="公务员医疗补助" align="center"
                            width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="hifob_pay" label="大额医疗补助基金" align="center"
                            width="180px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="hifmi_pay" label="大病补充医疗保险基金" align="center"
                            width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="hifes_pay" label="补充医疗保险基金" align="center"
                                         width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="acct_mulaid_pay" align="center" label="账户共济支付金额"
                            width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="maf_pay" align="center" label="医疗救助基金"
                            width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="othfund_pay" align="center" label="其他基金"
                            width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="acct_pay" align="center" label="个人账户支出"
                            width="160px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="cash_payamt" align="center" label="现金支付金额"
                            width="160px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="ownpay_hosp_part" align="center" label="医院垫付"
                            width="160px"></el-table-column>

                        <el-table-column show-overflow-tooltip label="对账结果" width="160" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-link v-if="row.check_status == '0'" type="primary">未对账</el-link>
                                <el-link v-else-if="row.check_status == '1'" type="primary" style="color: #2cc642">
                                    对账通过
                                </el-link>
                                <el-link v-else="row.check_status == '2'" type="primary" style="color: red">对账未通过
                                </el-link>
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="操作" fixed="right" width="280" align="center">
                            <template #default="{ row }">
                                <el-button size="mini" v-show="row.check_status == '2'" plain type="primary"
                                    @click="genNewRecord(row.id)">更新对账数据</el-button>
                                <el-button size="mini" v-show="row.check_status == '2'" plain type="primary"
                                    @click="excelDetails(row.id)">导出明细</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                        :layout="layout" :total="total" @size-change="handleSizeChange"
                        @current-change="handleCurrentChange2"></el-pagination>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import { getDicts } from '@/api/dictManagement'
import { checkReconciliation_month, dayDetailsExcel_month, genNewReconciliation_month, getList_month,monthListExcel } from '@/api/reconciliation'

    export default {
        name: 'Index',
        components: {},
        data() {
            return {
                value1: '',
                checked: false,
                isShow: false,
                list: [],
                form: {},
                reconciliationTypes: [],
                admdvs: [],
                listLoading: false,
                layout: 'total, sizes, prev, pager, next, jumper',
                total: 0,
                selectRows: '',
                elementLoadingText: '正在加载...',
                queryForm: {
                    pageNo: 1,
                    pageSize: 10,
                    fixmedins_code: "",
                    settle_date: "",
                    insu_admdvs: "",
                    reconciliation_type: ""
                },
                rules: {
                    fixmedins_code: [{required: true, trigger: 'blur', message: '请输入医疗机构编码'}],
                    // fixmedins_name: [{required: true, trigger: 'blur', message: '请输入医疗机构名称'}],
                    insu_admdvs: [{required: true, trigger: 'blur', message: '请输入统筹区'}],
                    reconciliation_type: [{required: true, trigger: 'blur', message: '请输入对账类型'}],
                    medfee_sumamt: [{required: true, trigger: 'blur', message: '请输入医疗费用总额'}],
                    hifp_pay: [{required: true, trigger: 'blur', message: '请输入统筹基金'}],
                    hifob_pay: [{required: true, trigger: 'blur', message: '请输入大额医疗补助基金'}],
                    cvlserv_pay: [{required: true, trigger: 'blur', message: '请输入公务员基金'}],
                    acct_pay: [{required: true, trigger: 'blur', message: '请输入个人账户'}],
                    cash_payamt: [{required: true, trigger: 'blur', message: '请输入现金支付'}],
                    acct_mulaid_pay: [{required: true, trigger: 'blur', message: '请输入账户共济支付金额'}],
                    hifmi_pay: [{required: true, trigger: 'blur', message: '请输入居民大病医疗保险基金'}],
                    hifes_pay: [{required: true, trigger: 'blur', message: '请输入补充医疗保险基金'}],
                    maf_pay: [{required: true, trigger: 'blur', message: '请输入医疗救助'}],
                    ownpay_hosp_part: [{required: true, trigger: 'blur', message: '请输入医院垫付'}],
                    othfund_pay: [{required: true, trigger: 'blur', message: '请输入其他基金'}],
                    person_count: [{required: true, trigger: 'blur', message: '请输入人次'}],
                    person_num: [{required: true, trigger: 'blur', message: '请输入人数'}],

                },
            }
        },
        created() {
            this.getReconciliationType()
            this.getAdmdvs()
            var userinfo = JSON.parse(localStorage.getItem("userinfo"));
            this.queryForm.fixmedins_code = userinfo.org_code;
            this.form.fixmedins_code = userinfo.org_code;
            this.form.fixmedins_name = userinfo.org_name;
            var date = new Date();
            date.setMonth(date.getMonth()-1);
            this.queryForm.settle_date = this.dateFormatter(date);
        },
        beforeDestroy() {
        },
        mounted() {
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
            admdvsChange (val){
                this.getReconciliationType(val)
            },
            async genNewRecord(orgId){
                // this.$baseMessage("res.msg", 'error')
                var that = this;
                this.listLoading = true
                const res = await genNewReconciliation_month({"orgReconciliationMonthId":orgId});
                if (res.code == "0") {
                    setTimeout(() => {
                        that.listLoading = false
                    }, 300)
                    this.$baseMessage("已同步最新数据，请重新尝试对账！", 'success')
                }else{
                    setTimeout(() => {
                        that.listLoading = false
                    }, 300)
                    this.$baseMessage(res.msg, 'error')
                }
            },
            queryData() {
                this.queryForm.pageNo = 1
                this.fetchData()
            },
            dateFormatter(str) {
                var hasTime = arguments[1] != false ? true : false;//可传第二个参数false，返回yyyy-MM-dd
                var d = new Date(str);
                var year = d.getFullYear();
                var month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1);
                var day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
                if (hasTime) {
                    return [year, month].join('-');
                } else {
                    return [year, month].join('-');
                }
            },
            async excelDetails(orgId){
                this.listLoading = true
                const res = await dayDetailsExcel_month({"orgReconciliationId":orgId});
                let fileName = "月对账明细.xls";
                let objectUrl = URL.createObjectURL(new Blob([res.data]))
                const link = document.createElement('a')
                link.download = decodeURI(fileName)
                link.href = objectUrl
                link.click()
                this.listLoading = false;
                this.$baseMessage("导出成功！", 'success')
            },
            async excelDetails_month() {
                this.$baseConfirm('确认导出吗？', null, async () => {
                    this.listLoading = true
                    const res = await monthListExcel(this.queryForm);
                    let fileName = "月对账信息.xls";
                    let objectUrl = URL.createObjectURL(new Blob([res.data]))
                    const link = document.createElement('a')
                    link.download = decodeURI(fileName)
                    link.href = objectUrl
                    link.click()
                    this.listLoading = false;
                    this.$baseMessage("导出成功！", 'success')
                })
            },
            moreQuery() {
                this.isShow = !this.isShow
            },
            dblclick(row) {
                this.form = row;
            },
            //获取对账类别
            async getReconciliationType(val) {
                if(val == '3' || val == '4'){
                    const res = await getDicts({"type": "reconciliationType_yd"});
                    if (res.code == "0") {
                        this.reconciliationTypes = res.data;
                    }
                }else{
                    const res = await getDicts({"type": "reconciliationType"});
                    if (res.code == "0") {
                        this.reconciliationTypes = res.data;
                    }
                }
            },
            //对账
            async reconciliation() {
                this.$refs['form'].validate(async (valid) => {
                    if (valid) {
                        const res = await checkReconciliation_month(this.form);
                        if (res.code == "0") {
                            this.$baseMessage("对账成功！", 'success')
                            await this.queryData();
                        }else{
                            this.$baseMessage(res.msg, 'error')
                            // await this.queryData();
                        }
                    } else {
                        return false
                    }
                })
            },
            //获取统筹区
            async getAdmdvs() {
                const res = await getDicts({"type": "ADMDVS"});
                if (res.code == "0") {
                    this.admdvs = res.data;
                }
            },
            async fetchData() {
                //H32031100043
                this.listLoading = true
                const res = await getList_month(this.queryForm)
                if (res.code == "0") {
                    this.list = res.data.records;
                    if (this.list.length == 1) {
                        this.dblclick(this.list[0]);
                    }
                    this.total = res.data.total;
                }

                setTimeout(() => {
                    this.listLoading = false
                }, 300)
            },
        },
    }
</script>
