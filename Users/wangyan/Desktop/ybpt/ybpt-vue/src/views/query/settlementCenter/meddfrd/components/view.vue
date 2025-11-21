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
              <span>医疗拨付明细</span>
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
                <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                  <!--<el-form-item label="证件类型">-->
                    <!--<el-select-->
                      <!--v-model="queryForm.psn_cert_Type"-->
                      <!--placeholder="证件类型"-->
                      <!--style="width: 100%"-->
                      <!--disabled-->
                    <!--&gt;-->
                      <!--<el-option-->
                              <!--v-for="item in insutypeList"-->
                              <!--:key="item.nat_dic_val_code"-->
                              <!--:label="item.nat_dic_val_name"-->
                              <!--:value="item.nat_dic_val_code">-->
                      <!--</el-option>-->
                    <!--</el-select>-->
                  <!--</el-form-item>-->
                <!--</el-col>-->

                <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                  <!--<el-form-item label="证件号码">-->
                    <!--<el-input-->
                            <!--v-model.trim="queryForm.psn_cert_type"-->
                            <!--clearable-->
                            <!--class="input-with-select"-->
                            <!--disabled-->
                    <!--&gt;</el-input>-->
                  <!--</el-form-item>-->
                <!--</el-col>-->
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="拨付通知类型">
                    <el-input
                      v-model.trim="queryForm.dfr_notc_type"
                      clearable
                      class="input-with-select"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="待遇发放方式">
                    <el-input
                      v-model.trim="queryForm.trt_issu_way"
                      placeholder="待遇发放方式"
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="拨付对象类型">
                    <el-input
                            v-model.trim="queryForm.dfr_obj"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="拨付对象名称">
                    <el-input
                            v-model.trim="queryForm.dfr_obj_name"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="拨付总金额">
                    <el-input
                            v-model.trim="queryForm.dfr_sumamt"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="银行行号">
                    <el-input
                            v-model.trim="queryForm.bankcode"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="银行账号">
                    <el-input
                            v-model.trim="queryForm.bankacct"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="银行卡户名">
                    <el-input
                            v-model.trim="queryForm.acctname"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="拨付来源">
                    <el-input
                            v-model.trim="queryForm.dfr_souc"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单据号">
                    <el-input
                            v-model.trim="queryForm.docno"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="来源流水号">
                    <el-input
                            v-model.trim="queryForm.souc_sn"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="财务处理标志">
                    <el-input
                            v-model.trim="queryForm.fin_dspo_flag"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="支付失败描述">
                    <el-input
                            v-model.trim="queryForm.pay_fail_desc"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="财务登账时间">
                    <el-input
                            v-model.trim="queryForm.fin_acen_time"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="人员类别">
                    <el-input
                            v-model.trim="queryForm.psn_type"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="特殊人员类型">
                    <el-input
                            v-model.trim="queryForm.sp_psn_type"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经济类型">
                    <el-input
                            v-model.trim="queryForm.econ_type"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗类别">
                    <el-input
                            v-model.trim="queryForm.med_type"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="清算类别">
                    <el-input
                            v-model.trim="queryForm.clr_type"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="二级清算类别">
                    <el-input
                            v-model.trim="queryForm.clr_type_lv2"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="编制类型">
                    <el-input
                            v-model.trim="queryForm.quts_type"
                            disabled
                    />
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