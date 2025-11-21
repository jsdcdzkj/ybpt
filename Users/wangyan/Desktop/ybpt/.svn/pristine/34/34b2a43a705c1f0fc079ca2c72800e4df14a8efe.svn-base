<template>
  <el-drawer
    :title="title"
    :before-close="handleClose"
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
              <span>人员基本信息</span>
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
                  <el-form-item label="证件号码">
                    <el-input
                      v-model.trim="queryForm.certno"
                      clearable
                      class="input-with-select"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="姓名">
                    <el-input
                      v-model.trim="queryForm.psn_name"
                      placeholder="姓名"
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>定点医药机构基本信息</span>
              <vab-icon
                :icon="['fas', 'angle-up']"
                v-if="isShow1"
                @click="moreQuery1"
              ></vab-icon>
              <vab-icon
                :icon="['fas', 'angle-down']"
                v-else
                @click="moreQuery1"
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20" v-if="isShow1">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医药机构代码" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fixmedins_code"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医药机构名称" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.fixmedins_name"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗机构简称" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.medins_abbr"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="零售药店简称" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.rtal_phac_abbr"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医药机构服务类型" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fixmedins_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="行政区" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.poolarea_no"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="地址" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.yyaddr"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="地址" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.ydaddr"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点归属医保区划" >
                    <el-input
                            v-model.trim="queryForm.fix_blng_admdvs"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医药机构等级" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.hosp_lv"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="限价医院等级" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.lmtpric_hosp_lv"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="起付线医院等级" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.dedc_hosp_lv"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="国家异地平台机构编号" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.nat_plaf_code"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="省内异地平台机构编号" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.prov_plaf_code"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="异地医疗机构类型" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.out_fixmedins_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点联网开通标志" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fix_onln_open_flag"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="异地联网开通类型">
                    <el-input
                            v-model.trim="queryForm.out_onln_open_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保办负责人姓名">
                    <el-input
                            v-model.trim="queryForm.hi_resper_name"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保办负责人证件类型" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.hi_resper_cert_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保办负责人身份证号码">
                    <el-input
                            v-model.trim="queryForm.hi_resper_certno"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保办负责人联系电话">
                    <el-input
                            v-model.trim="queryForm.hi_resper_tel"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="开始日期">
                    <el-input
                            v-model.trim="queryForm.begntime"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="结束日期">
                    <el-input
                            v-model.trim="queryForm.endtime"
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
        <!--<el-button-->
          <!--type="primary"-->
          <!--@click="$refs.drawer.closeDrawer()"-->
          <!--:loading="loading"-->
        <!--&gt;-->
          <!--{{ loading ? '打印中 ...' : '打 印' }}-->
        <!--</el-button>-->
      </div>
    </div>
    <hospital ref="hospital" @fetch-data="form.name"></hospital>
    <doctor ref="doctor" @fetch-data="form.name"></doctor>
    <medical ref="medical" @fetch-data="form.name"></medical>
    <bingzhong ref="bingzhong" @fetch-data="form.name"></bingzhong>
  </el-drawer>
</template>

<script>
import Hospital from '@/components/hospital'
import Doctor from '@/components/doctor'
import Medical from '@/components/medical'
import Bingzhong from '@/components/bingzhong'
import { chargeDetails } from '@/api/settlementCenter.js'
export default {
  name: 'edit',
  components: { Hospital, Doctor, Medical, Bingzhong },
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
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        mdtrt_id: '',
      },
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
      },
      formLabelWidth: '100px',
      timer: null,
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      fileList: [],
      insutypeList: [],
      natyList: [],
      list: [],
      total: '',
    }
  },
  created() {
    this.updata();
    this.natyData();
  },
  mounted() {},
  methods: {
    showDia(row) {
      this.queryForm = row ;
      this.queryForm.pageNo = '1' ;
      this.queryForm.pageSize = '10' ;
      console.log(row);
      this.fetchData();
      if (!row) {
        this.title = '详情';
        // this.fetchData();
      }
      this.dialog = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialog = false
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
      this.close()
    },
    openwin() {
      this.$refs['hospital'].showDia()
    },
    openwin1() {
      this.$refs['doctor'].showDia()
    },
    openwin2() {
      this.$refs['bingzhong'].showDia()
    },
    openwin3() {
      this.$refs['medical'].showDia()
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
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleDownload(file) {
      console.log(file)
    },
    handleClose(done) {

    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
    },
    updata() {
      var that = this ;
      upData('PSN_CERT_TYPE').then((res) => {
        if(res.code == 0){
          that.insutypeList = res.data ;
        }
      })
    },
    natyData() {
      var that = this ;
      upData('NATY').then((res) => {
        if(res.code == 0){
          that.natyList = res.data ;
        }
      })
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    fetchData() {
      var that = this ;
      chargeDetails(that.queryForm).then((res) => {
        if(res.code == 0){
          that.tableData = res.data.records ;
          that.total =res.data.total
        }
        that.loading = false;
      })
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },

  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-upload--picture-card {
    display: none!important;
    opacity:0!important;
  }
  .el-dialog__body {
    border-top: 0;
  }
}
</style>