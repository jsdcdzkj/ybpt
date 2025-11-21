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
              <span>门慢门特病种目录信息</span>
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
                  <el-form-item label="门慢门特病种目录代码">
                    <el-input v-model.trim="form.opsp_dise_code" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="门慢门特病种大类名称">
                    <el-input v-model.trim="form.opsp_dise_majcls_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="门慢门特病种细分类名称">
                    <el-input v-model.trim="form.opsp_dise_subd_clss_name" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保区划">
                    <el-input v-model.trim="form.admdvs" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="备注">
                    <el-input v-model.trim="form.memo" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="有效标志">
                    <el-input v-model.trim="form.vali_flag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="数据唯一记录号">
                    <el-input v-model.trim="form.rid" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="数据创建时间">
                    <el-input v-model.trim="form.crte_time" clearable class="input-with-select" disabled></el-input>
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
                  <el-form-item label="创建机构编号">
                    <el-input v-model.trim="form.crte_optins_no" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办人ID">
                    <el-input v-model.trim="form.opter_id" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办人姓名">
                    <el-input v-model.trim="form.opter_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
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
                  <el-form-item label="经办机构编号">
                    <el-input v-model.trim="form.optins_no" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="创建人姓名">
                    <el-input v-model.trim="form.crter_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="版本号">
                    <el-input v-model.trim="form.ver" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="病种内涵">
                    <el-input v-model.trim="form.dise_cont" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="版本名称">
                    <el-input v-model.trim="form.ver_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="诊疗指南页码">
                    <el-input v-model.trim="form.trt_guide_pagen" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="诊疗指南电子档案">
                    <el-input v-model.trim="form.trt_guide_elecacs" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="门慢门特病种名称">
                    <el-input v-model.trim="form.opsp_dise_name" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="门慢门特病种大类代码">
                    <el-input v-model.trim="form.opsp_dise_majcls_code" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="下发标志">
                    <el-input v-model.trim="form.isu_flag" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="传输数据ID">
                    <el-input v-model.trim="form.tram_data_id" clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="生效时间">
                    <el-input v-model.trim="form.efft_time" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="失效时间">
                    <el-input v-model.trim="form.invd_time" show-overflow-tooltip clearable class="input-with-select" disabled></el-input>
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