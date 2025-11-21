<template>
    <el-drawer
            ref="drawer"
            :before-close="handleClose"
            :title="title"
            :visible.sync="dialog"
            :with-header="false"
            custom-class="box_drawer"
            direction="rtl"
            size="80%"
    >
        <div class="drawer_content">
            <el-form :label-width="formLabelWidth" :model="form">
                <div class="drawer_main">
                    <div class="box_card">
                        <div class="box_header">
                            <span>人员基本信息</span>
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
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="证件类型">
                                        <el-input
                                                v-model.trim="form.psn_cert_type"
                                                class="input-with-select"
                                                clearable
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="证件号码">
                                        <el-input
                                                v-model.trim="form.certno"
                                                class="input-with-select"
                                                clearable
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="姓名">
                                        <el-input
                                                v-model.trim="form.psn_name"
                                                disabled
                                                placeholder="姓名"
                                        />
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="出生日期">
                                        <el-date-picker
                                                v-model.trim="form.brdy"
                                                class="w"
                                                disabled
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="性别">
                                        <el-input
                                                v-model.trim="form.gend"
                                                disabled
                                                placeholder="性别"
                                        />
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                    <div class="box_card">
                        <div class="box_header">
                            <span>结算信息</span>
                            <vab-icon
                                    v-if="isShow1"
                                    :icon="['fas', 'angle-up']"
                                    @click="moreQuery1"
                            ></vab-icon>
                            <vab-icon
                                    v-else
                                    :icon="['fas', 'angle-down']"
                                    @click="moreQuery1"
                            ></vab-icon>
                        </div>
                        <div class="box_content">
                            <el-row v-if="isShow1" :gutter="20">
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="定点医药机构编号">
                                        <el-input
                                                v-model.trim="form.fixmedins_code"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="定点医药机构编号">
                                        <el-input
                                                v-model.trim="form.fixmedins_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="结算时间">
                                        <el-input
                                                v-model.trim="form.setl_time"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="医疗类别">
                                        <el-input
                                                v-model.trim="form.med_type"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>

                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="结算类别">
                                        <el-input
                                                v-model.trim="form.setl_type"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="medfee_sumamt" label="医疗费总额">
                                        <el-input
                                                v-model.trim="form.medfee_sumamt"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="统筹基金支出">
                                        <el-input
                                                v-model.trim="form.hifp_pay"
                                                disabled

                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="公务员医疗补助资金支出">
                                        <el-input
                                                v-model.trim="form.cvlserv_pay"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="补充医疗保险基金支出">
                                        <el-input
                                                v-model.trim="form.hifes_pay"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="大病补充医疗保险基金支出">
                                        <el-input
                                                v-model.trim="form.hifmi_pay"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="大额医疗补助基金支出">
                                        <el-input
                                                v-model.trim="form.hifob_pay"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="医疗救助基金支出">
                                        <el-input
                                                v-model.trim="form.maf_pay"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="其它基金支付">
                                        <el-input
                                                v-model.trim="form.othfund_pay"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="个人账户支出">
                                        <el-input
                                                v-model.trim="form.acct_pay"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="现金支付金额">
                                        <el-input
                                                v-model.trim="form.cash_payamt"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item class="custemitem" label="自费中医院负担部分">
                                        <el-input
                                                v-model.trim="form.ownpay_hosp_part"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>

                            </el-row>
                        </div>
                    </div>
                </div>
            </el-form>
            <div class="drawer_footer">
                <el-button @click="cancelForm">关 闭</el-button>
            </div>
        </div>
    </el-drawer>
</template>

<script>
export default {
    name: 'edit',
    data() {
        return {
            tableData: [],
            title: '',
            dialog: false,
            loading: false,
            isShow: true,
            isShow1: true,
            isShow2: true,
            isShow3: true,
            layout: 'total, sizes, prev, pager, next, jumper',
            form: {},
            formLabelWidth: '100px',
            timer: null,
            dialogImageUrl: '',
            dialogVisible: false,
            disabled: false,
            list: [],
            total: '',
        }
    },
    created() {
    },
    mounted() {
    },
    methods: {
        showDia(row) {
            this.form = row;
            this.fetchData();
            if (!row) {
                this.title = '详情';
            }
            this.dialog = true
        },
        close() {
            this.dialog = false
        },
        save() {
            this.$baseMessage('模拟保存成功', 'success')
            this.$emit('fetch-data')
            this.close()
        },
        moreQuery() {
            this.isShow = !this.isShow
        },
        moreQuery1() {
            this.isShow1 = !this.isShow1
        },
        moreQuery2() {
            this.isShow2 = !this.isShow2
        },
        moreQuery3() {
            this.isShow3 = !this.isShow3
        },
        handleClose(done) {

        },
        cancelForm() {
            this.loading = false
            this.dialog = false
            clearTimeout(this.timer)
        },
        fetchData() {
            var that = this;
            that.loading = false;
        },
    },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-upload--picture-card {
    display: none !important;
    opacity: 0 !important;
  }

  .el-dialog__body {
    border-top: 0;
  }
}
</style>