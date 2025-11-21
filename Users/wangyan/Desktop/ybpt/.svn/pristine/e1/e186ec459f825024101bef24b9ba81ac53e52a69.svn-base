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
              <span>单位参保信息</span>
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
                  <el-form-item label="单位编号">
                    <el-input v-model.trim="form.emp_no" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="险种类型">
                    <el-input v-model.trim="form.insutype_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保类型">
                    <el-input v-model.trim="form.hi_type_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单位参保状态">
                    <el-input v-model.trim="form.emp_insu_stas_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="征收方式">
                    <el-input v-model.trim="form.clct_way_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单位参保日期">
                    <el-input v-model.trim="form.emp_insu_date" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="征缴规则类型编码">
                    <el-input v-model.trim="form.clct_rule_type_codg" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="基数核定规则类型编码">
                    <el-input v-model.trim="form.tax_begn_clct_ym" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="最大做账期号">
                    <el-input v-model.trim="form.max_acctprd" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="参保所属医保区划">
                    <el-input v-model.trim="form.insu_admdvs" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="统筹区编号">
                    <el-input v-model.trim="form.poolarea_no" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="备注">
                    <el-input v-model.trim="form.memo" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="创建人姓名">
                    <el-input v-model.trim="form.crter_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="创建机构编号">
                    <el-input v-model.trim="form.crte_optins_no" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="数据创建时间">
                    <el-input v-model.trim="form.crte_time" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="数据更新时间">
                    <el-input v-model.trim="form.updt_time" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办机构编号">
                    <el-input v-model.trim="form.optins_no" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
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
                  <el-form-item label="经办人姓名">
                    <el-input v-model.trim="form.opter_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办渠道">
                    <el-input v-model.trim="form.opt_chnl" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
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