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
              <span>人员参保信息</span>
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
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="人员姓名"><el-input v-model.trim="form.psn_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="电子凭证号"><el-input v-model.trim="form.hsecfc" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="证件号"><el-input v-model.trim="form.certno" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="单位编号"><el-input v-model.trim="form.emp_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="人员编号"><el-input v-model.trim="form.psn_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="险种类型"><el-input v-model.trim="form.insutype_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="本次参保日期"><el-input v-model.trim="form.crt_insu_date" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="暂停参保日期"><el-input v-model.trim="form.paus_insu_date" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="人员参保状态"><el-input v-model.trim="form.psn_insu_stas_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="险种离退休"><el-input v-model.trim="form.insutype_retr_flag_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="人员类别"><el-input v-model.trim="form.psn_type_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="征收方式"><el-input v-model.trim="form.clct_way_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="用工形式"><el-input v-model.trim="form.emp_fom_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="编制类型"><el-input v-model.trim="form.quts_type_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="最大做账期号"><el-input v-model.trim="form.max_acctprd" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="账户建立年月"><el-input v-model.trim="form.acct_crtn_ym" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="首次参保年月"><el-input v-model.trim="form.fst_insu_ym" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="首次参保日期"><el-input v-model.trim="form.psn_insu_date" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="征缴类型编码"><el-input v-model.trim="form.clct_rule_type_codg" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="基数核定编码"><el-input v-model.trim="form.clctstd_crtf_rule_codg" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医保类型"><el-input v-model.trim="form.hi_type_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="参保医保区划"><el-input v-model.trim="form.insu_admdvs" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="统筹区编号"><el-input v-model.trim="form.poolarea_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办渠道"><el-input v-model.trim="form.opt_chnl_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办机构编号"><el-input v-model.trim="form.optins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办人姓名"><el-input v-model.trim="form.opter_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办时间"><el-input v-model.trim="form.opt_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="创建机构编号"><el-input v-model.trim="form.crte_optins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="创建人姓名"><el-input v-model.trim="form.crter_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="数据创建时间"><el-input v-model.trim="form.crte_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="数据更新时间"><el-input v-model.trim="form.updt_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="退休待遇开始"><el-input v-model.trim="form.retr_trt_begn_date" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="退休待遇享受"><el-input v-model.trim="form.retr_trt_enjymnt_flag_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
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