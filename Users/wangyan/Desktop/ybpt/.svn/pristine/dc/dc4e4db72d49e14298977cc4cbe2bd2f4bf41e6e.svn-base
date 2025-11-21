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
              <span>医保目录</span>
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
                  <el-form-item label="医保目录编码">
                    <el-input v-model.trim="form.hilist_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="开始日期">
                    <el-input v-model.trim="form.begndate" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="参保所属医保区划">
                    <el-input v-model.trim="form.insu_admdvs" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保目录名称">
                    <el-input v-model.trim="form.hilist_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="结束日期">
                    <el-input v-model.trim="form.enddate" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗收费项目类别">
                    <el-input v-model.trim="form.med_chrgitm_tYPE" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="收费项目等级">
                    <el-input v-model.trim="form.chrgitm_lv" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="限制使用标志">
                    <el-input v-model.trim="form.lmt_used_flag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="目录类别">
                    <el-input v-model.trim="form.list_type" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗使用标志">
                    <el-input v-model.trim="form.med_use_flag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="生育使用标志">
                    <el-input v-model.trim="form.matn_used_flaG" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保目录使用类别">
                    <el-input v-model.trim="form.hilist_use_tyPE" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医师专业技术职务名称">
                    <el-input v-model.trim="form.dr_pro_tech_duty_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>-->
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="限复方使用类型">
                    <el-input v-model.trim="form.lmt_cpnd_type" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="五笔助记码">
                    <el-input v-model.trim="form.wubi" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="拼音助记码">
                    <el-input v-model.trim="form.pinyin" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="创建人姓名">
                    <el-input v-model.trim="form.crter_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
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
                  <el-form-item label="有效标志">
                    <el-input v-model.trim="form.vali_flag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="数据唯一记录号">
                    <el-input v-model.trim="form.rid" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="数据更新时间">
                    <el-input v-model.trim="form.updt_time" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="创建人ID">
                    <el-input v-model.trim="form.crter_id" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="创建人姓名">
                    <el-input v-model.trim="form.crter_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
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
                  <el-form-item label="创建机构编号">
                    <el-input v-model.trim="form.crte_optins_nO" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办人ID">
                    <el-input v-model.trim="form.opter_id" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办人姓名">
                    <el-input v-model.trim="form.opter_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办时间">
                    <el-input v-model.trim="form.opt_time" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办机构编号">
                    <el-input v-model.trim="form.optins_no" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="统筹区编号">
                    <el-input v-model.trim="form.poolarea_no" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保区划">
                    <el-input v-model.trim="form.admdvs" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>-->
              </el-row>
<!--              <el-row :gutter="20">-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="人员状态">-->
<!--                    <el-input v-model.trim="form.psn_stas" clearable class="input-with-select" disabled></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="信用等级">-->
<!--                    <el-input v-model.trim="form.cred_lv" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="医师执业级别">-->
<!--                    <el-input v-model.trim="form.dr_prac_lv" clearable class="input-with-select" disabled></el-input>-->
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