<template>
    <el-drawer
            :title="title"
            :before-close="close"
            :visible.sync="dialog"
            direction="rtl"
            :with-header="false"
            custom-class="box_drawer"
            size="80%"
            ref="drawer"
    >
        <div class="drawer_content">
            <el-form :model="form" :label-width="formLabelWidth">
                <div class="drawer_main">
                    <div class="box_card">
                        <div class="box_header">
                            <span>医疗机构信息</span>
                            <vab-icon
                                    :icon="['fas', 'angle-up']"
                                    v-if="isShow"
                                    @click="moreQuery"
                            ></vab-icon>
                            <vab-icon
                                    :icon="['fas', 'angle-down']"
                                    v-else
                                    @click="moreQuery"
                            ></vab-icon>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医疗机构名称"><el-input v-model.trim="form.medins_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医疗机构代码"><el-input v-model.trim="form.medins_code" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医疗机构简称"><el-input v-model.trim="form.medins_abbr" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="社会信用代码"><el-input v-model.trim="form.uscc" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="传真号码"><el-input v-model.trim="form.fax_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="主要负责人"><el-input v-model.trim="form.main_resper" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医保区划"><el-input v-model.trim="form.admdvs" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="联系地址"><el-input v-model.trim="form.addr" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医疗服务类型"><el-input v-model.trim="form.medins_type_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="登记状态"><el-input v-model.trim="form.reg_stas" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="在编职工数"><el-input v-model.trim="form.enrd_staf_psncnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医院科室数"><el-input v-model.trim="form.hosp_dept_cnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="重点科室数"><el-input v-model.trim="form.hosp_key_dept_cnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="正高职称人数"><el-input v-model.trim="form.senr_profttl_psncnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="中级职称人数"><el-input v-model.trim="form.mid_profttl_psncnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="技术人员人数"><el-input v-model.trim="form.pro_techstf_psncnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="副高职称人数"><el-input v-model.trim="form.depsenr_profttl_psncnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="批准床位数量"><el-input v-model.trim="form.aprv_bed_cnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="营业人数"><el-input v-model.trim="form.biz_psncnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="其他人数"><el-input v-model.trim="form.oth_psncnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经营面积"><el-input v-model.trim="form.biz_area" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医疗机构等级"><el-input v-model.trim="form.medinslv_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医院等级"><el-input v-model.trim="form.hosp_lv_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经度"><el-input v-model.trim="form.lnt" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="纬度"><el-input v-model.trim="form.lat" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="开始时间"><el-input v-model.trim="form.begntime" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="结束时间"><el-input v-model.trim="form.endtime" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="备注"><el-input v-model.trim="form.memo" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="基层医院标志"><el-input v-model.trim="form.grst_hosp_flag" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="信用等级"><el-input v-model.trim="form.cred_lv_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="定点服务类型"><el-input v-model.trim="form.fixmedins_type" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="机构性质"><el-input v-model.trim="form.medins_natu_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="创建人姓名"><el-input v-model.trim="form.crter_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="数据创建时间"><el-input v-model.trim="form.crte_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="数据更新时间"><el-input v-model.trim="form.updt_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="创建机构编号"><el-input v-model.trim="form.crte_optins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办人姓名"><el-input v-model.trim="form.opter_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办时间"><el-input v-model.trim="form.opt_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办机构编号"><el-input v-model.trim="form.optins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="版本号"><el-input v-model.trim="form.ver" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="同步上级标志"><el-input v-model.trim="form.sync_prnt_flag_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医疗机构等次"><el-input v-model.trim="form.medins_grade_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="版本唯一编号"><el-input v-model.trim="form.ver_rid" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="版本"><el-input v-model.trim="form.ver_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="登记号"><el-input v-model.trim="form.medins_prac_lic_regno" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="法人名称"><el-input v-model.trim="form.legent_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="法定代表姓名"><el-input v-model.trim="form.legrep_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经营性质"><el-input v-model.trim="form.biznat_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经济类型"><el-input v-model.trim="form.econ_type_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="隶属关系"><el-input v-model.trim="form.afil_rlts_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="诊疗项目"><el-input v-model.trim="form.trtitem" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="有效期限"><el-input v-model.trim="form.medins_prac_lic_expy" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="开户银行名称"><el-input v-model.trim="form.bank_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="银行账号"><el-input v-model.trim="form.bankacct" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="开户银行"><el-input v-model.trim="form.bank" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="分管负责姓名"><el-input v-model.trim="form.inchg_hosp_resper_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="分管负责电话"><el-input v-model.trim="form.inchg_hosp_resper_tel" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医保办姓名"><el-input v-model.trim="form.hi_resper_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医保办联系"><el-input v-model.trim="form.hi_resper_tel" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医保办电话"><el-input v-model.trim="form.hi_tel" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                            </el-row>
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医保办邮箱"><el-input v-model.trim="form.hi_email" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
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
                title: '',
                dialog: false,
                loading: false,
                isShow: true,
                isShow1: true,
                isShow2: true,
                isShow3: true,
                form: {},
                formLabelWidth: '100px',
                timer: null,
                disabled: false,
            }
        },
        methods: {
            showDia(row) {
                console.log(row)
                this.form = row;
                this.title = '详情';
                this.dialog = true
            },
            close() {
                this.dialog = false
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
            cancelForm() {
                this.loading = false
                this.dialog = false
                clearTimeout(this.timer)
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