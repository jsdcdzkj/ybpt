view.vue

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
              <span>药师信息</span>
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
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="技师代码"><el-input v-model.trim="form.phar_code" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="技师姓名"><el-input v-model.trim="form.phar_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="人员编号"><el-input v-model.trim="form.psn_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="个人能力简介"><el-input v-model.trim="form.psn_itro" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="执业类别"><el-input v-model.trim="form.prac_type_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="执业范围"><el-input v-model.trim="form.prac_scp_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="执业地区"><el-input v-model.trim="form.prac_regn" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="执业机构编号"><el-input v-model.trim="form.pracins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="执业机构名称"><el-input v-model.trim="form.pracins_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="执业机构地址"><el-input v-model.trim="form.pracins_addr" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="技师技术职务"><el-input v-model.trim="form.tecn_pro_tech_duty_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="离职时间"><el-input v-model.trim="form.nemp_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="创建人姓名"><el-input v-model.trim="form.crter_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="创建机构编号"><el-input v-model.trim="form.crte_optins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办人姓名"><el-input v-model.trim="form.opter_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办时间"><el-input v-model.trim="form.opt_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="经办机构编号"><el-input v-model.trim="form.optins_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="医保区划"><el-input v-model.trim="form.admdvs" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="版本号"><el-input v-model.trim="form.ver" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="版本名称"><el-input v-model.trim="form.ver_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="同步上级标志"><el-input v-model.trim="form.sync_prnt_flag_name" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="违规行为"><el-input v-model.trim="form.vola_bhvr" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="注册时间"><el-input v-model.trim="form.reg_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="有效期"><el-input v-model.trim="form.expy_endtime" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="合同起始时间"><el-input v-model.trim="form.cntr_begntime" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="合同截止时间"><el-input v-model.trim="form.cntr_endtime" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="注册证号"><el-input v-model.trim="form.regcert_no" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="数据创建时间"><el-input v-model.trim="form.crte_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"><el-form-item label="数据更新时间"><el-input v-model.trim="form.updt_time" clearable class="input-with-select" disabled></el-input></el-form-item></el-col>
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
