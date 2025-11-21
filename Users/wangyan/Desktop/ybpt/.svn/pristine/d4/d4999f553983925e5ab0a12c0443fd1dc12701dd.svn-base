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
              <span>医疗保障基金结算清单信息</span>
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
                  <el-form-item label="定点医药机构编号" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fixmedins_code"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点医药机构名称" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.fixmedins_name"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保目录编码" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.hilist_code"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗目录编码" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.med_list_codg"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="费用发生时间" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fee_ocur_time"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单价" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.pric"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="数量" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.cnt"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="明细项目费用总额" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.det_item_fee_sumamt"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗类别" >
                    <el-input
                            v-model.trim="queryForm.med_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="参保所属医保区划" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.insu_admdvs"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="支付地点类别" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.pay_loc"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="符合范围金额" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.inscp_amt"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="大病补充医疗保险基金支出" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.hifmi_pay"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="大额医疗补助基金支出" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.hifob_pay"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗救助基金支出" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.maf_pay"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="基金支付总额" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fund_pay_sumamt"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="全自费金额">
                    <el-input
                            v-model.trim="queryForm.fulamt_ownpay_amt"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="个人支付金额">
                    <el-input
                            v-model.trim="queryForm.psn_pay"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="个人账户支出" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.acct_pay"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="现金支付金额">
                    <el-input
                            v-model.trim="queryForm.cash_payamt"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="开单科室名称">
                    <el-input
                            v-model.trim="queryForm.bilg_dept_name"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="开单医师姓名">
                    <el-input
                            v-model.trim="queryForm.bilg_dr_name"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="剂型名称">
                    <el-input
                            v-model.trim="queryForm.dosform_name"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="规格">
                    <el-input
                            v-model.trim="queryForm.spec"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗收费项目类别" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.med_chrgitm_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="收费项目等级">
                    <el-input
                            v-model.trim="queryForm.chrgitm_lv"
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