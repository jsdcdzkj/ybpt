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
              <span>经办人员信息</span>
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
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办人编号"><el-input v-model.trim="form.opter_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="人员编号"><el-input v-model.trim="form.psn_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="人员姓名"><el-input v-model.trim="form.psn_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="归属经办机构"><el-input v-model.trim="form.afil_optins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="备注"><el-input v-model.trim="form.memo" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办人类型"><el-input v-model.trim="form.opter_type" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="证件号码"><el-input v-model.trim="form.certno" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="人员证件类型"><el-input v-model.trim="form.psn_cert_type_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="联系电话"><el-input v-model.trim="form.tel" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="手机号码"><el-input v-model.trim="form.mob" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="数据创建时间"><el-input v-model.trim="form.crte_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="数据更新时间"><el-input v-model.trim="form.updt_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="创建人姓名"><el-input v-model.trim="form.crter_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="创建机构编号"><el-input v-model.trim="form.crte_optins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办人姓名"><el-input v-model.trim="form.opter_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办时间"><el-input v-model.trim="form.opt_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办机构编号"><el-input v-model.trim="form.optins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="版本号"><el-input v-model.trim="form.ver" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医保区划"><el-input v-model.trim="form.admdvs" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="同步上级标志"><el-input v-model.trim="form.sync_prnt_flag_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="人员状态"><el-input v-model.trim="form.psn_stas" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="版本名称"><el-input v-model.trim="form.ver_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="单位名称"><el-input v-model.trim="form.emp_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="性别"><el-input v-model.trim="form.gend" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="民族"><el-input v-model.trim="form.naty" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="政治面貌"><el-input v-model.trim="form.polstas" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="学历"><el-input v-model.trim="form.educ" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="专业"><el-input v-model.trim="form.pro" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="职称"><el-input v-model.trim="form.profttl" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="部门"><el-input v-model.trim="form.dept" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="职务"><el-input v-model.trim="form.duty" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="职务职级"><el-input v-model.trim="form.duty_rank" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="参加工作时间"><el-input v-model.trim="form.patc_job_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
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