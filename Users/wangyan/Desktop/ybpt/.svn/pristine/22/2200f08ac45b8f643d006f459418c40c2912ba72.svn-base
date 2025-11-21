<template >
  <el-drawer
    :title="title"
    :before-close="handleClose"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="80%"
    ref="drawer"
    v-loading="inputloading" element-loading-text="正在统计结算信息,请稍等" element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <div class="drawer_content" >
      <el-form ref="form" :model="form" :label-width="formLabelWidth" :rules="rules">
        <div class="drawer_main">
          <div class="box_card">
            <div class="box_header">
              <span>医疗机构基本信息</span>
              
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="机构编号" prop="fixmedins_code">
                    <el-input
                            v-model.trim="form.fixmedins_code"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保信用等级" prop="cred_lv_name">
                    <el-input
                            v-model.trim="form.cred_lv_name"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="机构名称" prop="fixmedins_name">
                    <el-input
                            v-model.trim="form.fixmedins_name"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="isShow">
                  <el-form-item label="医疗机构等级" prop="medinslv">
                    <el-input
                            v-model.trim="form.medinslv"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="isShow">
                  <el-form-item label="医院等级" prop="hosp_lv">
                    <el-input
                            v-model.trim="form.hosp_lv"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="地址" prop="addr">
                    <el-input
                            v-model.trim="form.addr"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"  v-if="!isShow">
                  <el-form-item label="经营状态" prop="year">
                    <el-input
                            v-model.trim="form.biz_stas"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="isShow">
                  <el-form-item label="经营性质" prop="biznat">
                    <el-input
                            v-model.trim="form.biznat"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="所在行政区" prop="fix_blng_admdvs">
                    <el-input
                            v-model.trim="form.fix_blng_admdvs"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item
                          label="统一社会信用代码"
                          prop="uscc"
                          class="custemitem"
                  >
                    <el-input
                            v-model.trim="form.uscc"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"  v-if="!isShow">
                  <el-form-item label="药品经营许可证" prop="drug_biz_lic_no">
                    <el-input
                            v-model.trim="form.drug_biz_lic_no"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item
                          label="法人名称"
                          prop="legent_name"
                          class="custemitem"
                  >
                    <el-input
                            v-model.trim="form.legent_name"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item
                          label="法定代表人（负责人）"
                          prop="year"
                          class="custemitem"
                  >
                    <el-input
                            v-model.trim="form.legrep_name"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"  v-if="isShow">
                  <el-form-item label="传真号码" prop="fax_no">
                    <el-input
                            v-model.trim="form.fax_no"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"  v-if="!isShow">
                  <el-form-item label="联系电话" prop="fax_no">
                    <el-input
                            v-model.trim="form.fax_no"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"  v-if="isShow">
                  <el-form-item label="分管医疗机构负责人姓名" prop="inchg_hosp_resper_name" class="custemitem">
                    <el-input
                            v-model.trim="form.inchg_hosp_resper_name"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"  v-if="isShow" >
                  <el-form-item label="分管医疗机构负责人姓名" prop="inchg_hosp_resper_tel" class="custemitem">
                    <el-input
                            v-model.trim="form.inchg_hosp_resper_tel"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="开户银行" prop="depositaryBank">
                    <el-input placeholder="开户银行" v-model="form.depositaryBank" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="户名" prop="bankName">
                    <el-input placeholder="户名" v-model="form.bankName" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="结算账户" prop="settlementAccount">
                    <el-input placeholder="结算账户" v-model="form.settlementAccount" disabled></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="申请金额" prop="application_quota">
                    <el-input
                            v-model.trim="form.application_quota"
                            autocomplete="off"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                  <!--                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" >-->
                  <!--                  <el-form-item label="违规记录" prop="violationRecord">-->
                  <!--                    <el-input-->
                  <!--                            v-model.trim="form.violationRecord"-->
                  <!--                            type="textarea"-->
                  <!--                            :rows="5"-->
                  <!--                            :disabled="true"-->
                  <!--                    ></el-input>-->
                  <!--                  </el-form-item>-->
                  <!--                </el-col>-->
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-if="form.apply_satus == 1">
                  <el-form-item label="申请银行" prop="apply_bank_id">
                    <el-select v-model.trim="form.apply_bank_id" class="w" disabled>
                      <el-option
                              v-for="item in natyList"
                              :key="item.bankNo"
                              :label="item.bankName"
                              :value="item.bankNo"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>

              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>结算信息</span>
              <vab-icon
                      :icon="['fas', 'angle-up']"
                      v-if="isShow3"
                      @click="moreQuery3"
              ></vab-icon>
              <vab-icon
                      :icon="['fas', 'angle-down']"
                      v-else
                      @click="moreQuery3"
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20" v-if="isShow3">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item :label="year" prop="year">
                    <el-input
                            v-model.trim="yearData"
                            placeholder=""
                            disabled

                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item :label="oldyear" prop="oldyear">
                    <el-input
                            v-model.trim="oldyearData"
                            placeholder=""
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                  <!--<el-form-item :label="oldoldyear" prop="oldoldyear">-->
                    <!--<el-input-->
                            <!--v-model.trim="oldoldyearData"-->
                            <!--placeholder=""-->
                            <!--disabled-->

                    <!--&gt;</el-input>-->
                  <!--</el-form-item>-->
                <!--</el-col>-->
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>申贷记录</span>
            </div>
            <div class="box_content">
              <el-table
                      :data="list"
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
                        label="序号"
                        width="80"
                        show-overflow-tooltip
                >
                  <template slot-scope="scope">
                    <span v-text="getIndex(scope.$index)"> </span>
                  </template>
                </el-table-column>
                <el-table-column
                        prop="fixmedins_code"
                        label="机构编号"
                        show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                        prop="fixmedins_name"
                        label="机构名称"
                        width="350"
                        show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                        prop="application_quota"
                        label="申请额度"
                        show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                        prop="determineTheAmount"
                        label="确定额度"
                        show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                        prop="apply_bank_id_name"
                        label="申请银行"
                        show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                        prop="bank_satus"
                        label="银行审核状态"
                        show-overflow-tooltip
                ><template #default="scope">
                  <el-tag type="info" v-show="scope.row.bank_satus == 0">银行待审核</el-tag>
                  <el-tag type="success" v-show="scope.row.bank_satus == 1">银行审核通过</el-tag>
                  <el-tag type="danger" v-show="scope.row.bank_satus == 2">银行审驳回</el-tag>
                  <el-tag type="danger" v-show="scope.row.bank_satus == 3">超时,医保重新审核</el-tag>
                </template></el-table-column>
                <el-table-column
                        prop="reviewTime"
                        label="银行审核时间"
                        show-overflow-tooltip
                ></el-table-column>

              </el-table>
              <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                             :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>审核信息</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20" v-if="isShow3">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="审核结果" prop="bank_satus">
                    <el-select v-model="form.bank_satus" class="w">
                      <el-option label="审核通过" value="1"></el-option>
                      <el-option label="审核驳回" value="2"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="form.bank_satus == 1">
                  <el-form-item label="确定额度" prop="determineTheAmount">
                    <el-input
                            v-model.trim="form.determineTheAmount"
                            placeholder=""
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-if="form.bank_satus == 2">
                  <el-form-item label="审核意见" prop="bank_reason">
                    <el-input
                            v-model.trim="form.bank_reason"
                            type="textarea"
                            :rows="5"

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
        <el-button type="primary" @click="save" :loading="loading" :disabled="disabled">{{ loading ? '确定中 ...' : '确定' }}</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import {bankApplyList, bankCheck, billingData, getUserInfo, info} from '@/api/loan.js'
import {selectListAll} from '@/api/bank.js'

export default {
    name: 'edit',
    components: {},
    data() {
        return {
            tableData: [],
            title: '',
            dialog: false,
            loading: false,
      inputloading:false,
      isShow: false,
      isShow1: true,
      isShow2: true,
      isShow3: true,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        id: '',
        fixmedins_code: '',
      },
      total: 0,
      layout: 'total, sizes, prev, pager, next, jumper',
      form: {
        fixmedins_code: '',
        cred_lv_name: '',
        nat_plaf_code: '',
        fixmedins_name: '',
        addr: '',
        biznat: '',
        fix_blng_admdvs: '',
        uscc: '',
        drug_biz_lic_no: '',
        legrep_name: '',
        main_resper: '',
        fax_no: '',
        apply_bank_id: '',
        application_quota: '',
        biz_stas: '',
        apply_satus: '',
        reason: '',
        bank_satus: '',
        bank_reason: '',
        determineTheAmount: '',
      },
      formLabelWidth: '140px',
      timer: null,
      dialogImageUrl: '',
      fixmedins_code: "",
      year: "",
      yearData: "",
      oldyear: "",
      oldyearData: "",
      oldoldyear: "",
      oldoldyearData: "",
      id: "",
      dialogVisible: false,
      disabled: false,
      fileList: [],
      rules: {
        bank_satus: [
          { required: true, trigger: 'blur', message: '请输入正确选项' },
        ],
        bank_reason: [{ required: true, trigger: 'blur', message: '请输入正确选项' }],
        determineTheAmount: [{ required: true, trigger: 'blur', message: '请输入正确选项' }],
      },
      natyList: [],
      list: [],
    }
  },
  mounted() {},
  methods: {
    showDia(row) {
      if (!row) {
        this.title = '新增'
      } else {
        this.title = '审核'
        this.id = row.id ;
        this.fixmedins_code = row.fixmedins_code ;
        // this.form = Object.assign({}, row)
        this.getInfo(this.id) ;
        this.getUserInfoData(this.fixmedins_code) ;
        this.billing(this.fixmedins_code);
        this.natyData();
        this.queryForm.id = row.id ;
        this.queryForm.fixmedins_code = row.fixmedins_code ;
        this.getApplyList();
        var date = new Date()
        this.year =  date.getFullYear()+'年总金额'
        this.oldyear =  date.getFullYear()-1+'年总金额'
        this.oldoldyear =  date.getFullYear()-2+'年总金额'
      }
      this.dialog = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialog = false
    },
    save() {
      var that = this ;
      if (that.loading) {
        return
      }
      that.$refs['form'].validate(async (valid) => {
        if (valid) {
          that.loading = true
          that.disabled = true
          // 动画关闭需要一定的时间
          setTimeout(() => {
            that.loading = false
            that.disabled = false
          }, 1000)
          if(that.id != ''){
            bankCheck(that.form).then((res) => {
              if(res.code == 0){
                that.$emit('fetch-data')
              }else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          }
          // that.$baseMessage(msg, 'success')

          that.close()
        } else {
          return false
        }
      })
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
      // if (this.loading) {
      //   return
      // }
      // this.$confirm('确定要提交表单吗？')
      //   .then((_) => {
      //     this.loading = true
      //     this.timer = setTimeout(() => {
      //       done()
      //       // 动画关闭需要一定的时间
      //       setTimeout(() => {
      //         this.loading = false
      //       }, 400)
      //     }, 2000)
      //   })
      //   .catch((_) => {})
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
    },
    getInfo(id){
      var that = this ;
      info(id).then((res) => {
        if(res.code == 0){
          that.form = res.data ;
          that.form.bank_satus = "" ;
        }
      })

    },
    getUserInfoData(fixmedins_code){
      var that = this ;
      getUserInfo(fixmedins_code).then((res) => {
        if(res.code == 0){
          if (res.data.user_type == 2) {
            that.isShow = true;
          } else if (res.data.user_type == 3){
            that.isShow = false;
          }else {
            that.isShow = false;
          }
        }
      })

    },
    async billing(fixmedins_code){
      var that = this ;
      that.inputloading = true ;
      billingData(fixmedins_code).then((res) => {
        if(res.code == 0){
          that.inputloading =false ;
            that.yearData = res.data.year ;
          that.oldyearData = res.data.oldyear ;
            that.oldoldyearData = res.data.oldoldyear ;
        }
      })
    },
    natyData() {
      var that = this ;
      selectListAll().then((res) => {
        if(res.code == 0){
          that.natyList = res.data ;
        }
      })
    },
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    getApplyList() {
      var that = this;
      bankApplyList(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = res.data.records;
          that.total = res.data.total
        }
      })
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.getApplyList()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.getApplyList()
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