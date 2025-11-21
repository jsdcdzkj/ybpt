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
              <span>人员缴费基数信息</span>
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
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="人员编号">
                    <el-input v-model.trim="form.psn_no" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="流水号">
                    <el-input v-model.trim="form.psn_clctstd_sn" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单位编号">
                    <el-input v-model.trim="form.emp_no" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="险种类型">
                    <el-input v-model.trim="form.insutype_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="年度">
                    <el-input v-model.trim="form.year" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="险种离退休标志">-->
<!--                    <el-input v-model.trim="form.insutype_retr_flag" clearable class="input-with-select" disabled></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="数据更新时间">
                    <el-input v-model.trim="form.updt_time" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="1月工资">
                    <el-input v-model.trim="form.jan_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="1月基数规则编码">
                    <el-input v-model.trim="form.jan_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="1月人员缴费基数">
                    <el-input v-model.trim="form.jan_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="2月工资">
                    <el-input v-model.trim="form.feb_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="2月基数规则编码">
                    <el-input v-model.trim="form.feb_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="2月人员缴费基数">
                    <el-input v-model.trim="form.feb_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="3月工资">
                    <el-input v-model.trim="form.mar_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="3月基数规则编码">
                    <el-input v-model.trim="form.mar_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="3月人员缴费基数">
                    <el-input v-model.trim="form.mar_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="4月工资">
                    <el-input v-model.trim="form.apr_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="4月基数规则编码">
                    <el-input v-model.trim="form.apr_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="4月人员缴费基数">
                    <el-input v-model.trim="form.apr_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="5月工资">
                    <el-input v-model.trim="form.may_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="5月基数规则编码">
                    <el-input v-model.trim="form.may_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="5月人员缴费基数">
                    <el-input v-model.trim="form.may_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="6月工资">
                    <el-input v-model.trim="form.june_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="6月基数规则编码">
                    <el-input v-model.trim="form.june_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="6月人员缴费基数">
                    <el-input v-model.trim="form.june_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="7月工资">
                    <el-input v-model.trim="form.july_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="7月基数规则编码">
                    <el-input v-model.trim="form.july_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="7月人员缴费基数">
                    <el-input v-model.trim="form.july_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="8月工资">
                    <el-input v-model.trim="form.aug_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="8月基数规则编码">
                    <el-input v-model.trim="form.aug_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="8月人员缴费基数">
                    <el-input v-model.trim="form.aug_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="9月工资">
                    <el-input v-model.trim="form.sept_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="9月基数规则编码">
                    <el-input v-model.trim="form.sept_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="9月人员缴费基数">
                    <el-input v-model.trim="form.sept_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="10月工资">
                    <el-input v-model.trim="form.oct_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="10月基数规则编码">
                    <el-input v-model.trim="form.oct_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="10月人员缴费基数">
                    <el-input v-model.trim="form.oct_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="11月工资">
                    <el-input v-model.trim="form.nov_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="11月基数规则编码">
                    <el-input v-model.trim="form.nov_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="11月人员缴费基数">
                    <el-input v-model.trim="form.nov_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="12月工资">
                    <el-input v-model.trim="form.dec_wag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="12月基数规则编码">
                    <el-input v-model.trim="form.dec_clctstd_rule_codg" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="12月人员缴费基数">
                    <el-input v-model.trim="form.dec_psn_clctstd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="参保所属医保区划">
                    <el-input v-model.trim="form.insu_admdvs_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="统筹区编号">
                    <el-input v-model.trim="form.poolarea_no" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办渠道">
                    <el-input v-model.trim="form.opt_chnl" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办机构编号">
                    <el-input v-model.trim="form.bankacct" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办人姓名">
                    <el-input v-model.trim="form.opter_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办时间">
                    <el-input v-model.trim="form.opt_time" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="创建机构编号">
                    <el-input v-model.trim="form.crte_optins_no" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="创建人姓名">
                    <el-input v-model.trim="form.crter_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="数据创建时间">
                    <el-input v-model.trim="form.crte_time" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
<!--              <el-row :gutter="20">-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="数据更新时间">-->
<!--                    <el-input v-model.trim="form.updt_time" clearable class="input-with-select" disabled></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--              </el-row>-->
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