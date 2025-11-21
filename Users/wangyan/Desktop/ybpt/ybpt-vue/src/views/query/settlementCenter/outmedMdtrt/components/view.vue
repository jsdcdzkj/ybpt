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

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="证件类型">
                    <el-input
                            v-model.trim="queryForm.psn_cert_type"
                            clearable
                            class="input-with-select"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
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
              <el-row :gutter="20" v-if="isShow">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="性别">
                    <el-input
                            v-model.trim="queryForm.gend"
                            placeholder="性别"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="民族">
                    <el-select
                      v-model="queryForm.naty"
                      style="width: 100%"
                      disabled
                    >
                      <el-option
                              v-for="item in natyList"
                              :key="item.nat_dic_val_code"
                              :label="item.nat_dic_val_name"
                              :value="item.nat_dic_val_code">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="出生日期">
                    <el-date-picker
                      v-model.trim="queryForm.brdy"
                      disabled
                      type="date"
                      class="w"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="电话号码">
                    <el-input v-model.trim="queryForm.tel" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="居住地址">
                    <el-input v-model.trim="queryForm.addr" disabled />
                  </el-form-item>
                </el-col>

              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>异地就医就诊信息</span>
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
                  <el-form-item label="险种类型">
                    <el-input
                      v-model.trim="queryForm.insutype"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="人员类别">
                    <el-input
                            v-model.trim="queryForm.psn_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="公务员标志">
                    <el-input
                            v-model.trim="queryForm.cvlserv_flag"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="公务员等级">
                    <el-input
                            v-model.trim="queryForm.cvlserv_lv"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="缴费档次" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.clct_grde"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单位名称">
                    <el-input
                            v-model.trim="queryForm.emp_name"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单位类型">
                    <el-input
                            v-model.trim="queryForm.emp_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点医药机构编号" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fixmedins_code"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点医药机构名称"  class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fixmedins_name"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="开始时间" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.begntime"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="结束时间">
                    <el-input
                            v-model.trim="queryForm.endtime"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="异地安置类别"  class="custemitem">
                    <el-input
                            v-model.trim="queryForm.rloc_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="跨年度住院标志"  class="custemitem">
                    <el-input
                            v-model.trim="queryForm.ars_year_ipt_flaG"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="先行支付标志"  class="custemitem">
                    <el-input
                            v-model.trim="queryForm.pre_pay_flag"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="年度">
                    <el-input
                            v-model.trim="queryForm.year"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="病种名称">
                    <el-input
                            v-model.trim="queryForm.dise_name"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="病种编号">
                    <el-input
                            v-model.trim="queryForm.dise_no"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="手术操作名称"  class="custemitem">
                    <el-input
                            v-model.trim="queryForm.oprn_oprt_name"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                  <!--<el-form-item label="在院状态">-->
                    <!--<el-input-->
                            <!--v-model.trim="queryForm.inhosp_stas"-->
                            <!--disabled-->
                    <!--&gt;</el-input>-->
                  <!--</el-form-item>-->
                <!--</el-col>-->
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="手术操作代码"  class="custemitem">
                    <el-input
                            v-model.trim="queryForm.oprn_oprt_code"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="门诊诊断信息"  class="custemitem">
                    <el-input
                            v-model.trim="queryForm.otp_diag_info"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

                <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                  <!--<el-form-item label="胎次">-->
                    <!--<el-input-->
                            <!--v-model.trim="queryForm.fetts"-->
                            <!--disabled-->
                    <!--&gt;</el-input>-->
                  <!--</el-form-item>-->
                <!--</el-col>-->

              </el-row>

            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>费用明细</span>
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
              <el-table
                      :data="tableData"
                      border
                      stripe
                      class="w"
                      highlight-current-row
                      height="300px"
              >
                <template slot="empty">
                  <el-empty :image-size="150"></el-empty>
                </template>
                <el-table-column
                        prop="date"
                        label="序号"
                        width="80"
                        show-overflow-tooltip
                ><template slot-scope="scope">
                  <span v-text="getIndex(scope.$index)"> </span>
                </template></el-table-column>
                <el-table-column prop="medins_list_codg" label="医药机构目录编码"></el-table-column>
                <el-table-column prop="medins_list_name" label="医药机构目录名称"></el-table-column>
                <el-table-column prop="prodname" label="商品名"></el-table-column>
                <el-table-column prop="med_type" label="医疗类别"></el-table-column>
                <el-table-column prop="pric" label="单价"></el-table-column>
                <el-table-column prop="cnt" label="数量"></el-table-column>
                <el-table-column prop="SPEC" label="规格"></el-table-column>
              </el-table>
              <el-pagination
                      background
                      :current-page="queryForm.pageNo"
                      :page-size="queryForm.pageSize"
                      :layout="layout"
                      :total="total"
                      @size-change="handleSizeChange"
                      @current-change="handleCurrentChange2"
              ></el-pagination>
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
import { chargeDetails,outmedFeeList } from '@/api/settlementCenter.js'
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
      outmedFeeList(that.queryForm).then((res) => {
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