<template>
  <div class="main-container">
    <el-card class="card" shadow="never" style="height: calc(100vh - 250px)">
      <div slot="header">
        <span class="tips">申请贷款</span>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
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
              label="法定代表人"
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
          <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8"  v-if="!isShow" >
            <el-form-item label="联系电话" prop="fax_no" >
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

          <!--<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
            <!--<el-form-item label="申请银行" prop="apply_bank_id">-->
              <!--<el-select v-model.trim="form.apply_bank_id" class="w">-->
                <!--<el-option-->
                        <!--v-for="item in natyList"-->
                        <!--:key="item.bankNo"-->
                        <!--:label="item.bankName"-->
                        <!--:value="item.bankNo"></el-option>-->
              <!--</el-select>-->
            <!--</el-form-item>-->
          <!--</el-col>-->
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="开户银行" prop="depositaryBank">
              <el-input placeholder="开户银行" v-model="form.depositaryBank"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="户名" prop="bankName">
              <el-input placeholder="户名" v-model="form.bankName"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="结算账户" prop="settlementAccount">
              <el-input placeholder="结算账户" v-model="form.settlementAccount"></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="申请金额" prop="application_quota">
              <el-input placeholder="申请金额(元)" v-model="form.application_quota"></el-input>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item>
              <el-button type="primary" @click="save">
                我要申请
              </el-button>
               <!--<el-button type="primary" @click="save">-->
                <!--取消申请-->
              <!--</el-button>-->
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import {
  getAllOrgList,
  getBookingNum,
  setLoginOrg,
  commitBook,
} from '@/api_check/physicalAst'
import { dkApplySelect} from '@/api/fixmedins.js'
import { selectListAll} from '@/api/bank.js'
import { addLoanApplication} from '@/api/loan.js'

export default {
  name: 'order',
  components: {},
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
        },
      },
      pickerOptions2: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
              picker.$emit('pick', [start, end])
            },
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
              picker.$emit('pick', [start, end])
            },
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
              picker.$emit('pick', [start, end])
            },
          },
        ],
      },
      dynamicTags: ['项目一', '项目二', '项目三'],
      inputVisible: false,
      inputValue: '',
      value1: '',
      orgList: [],
      orgMenu: [],
      commonMenu: [],
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
        depositaryBank: '',
        bankName: '',
        settlementAccount: '',
        medinslv: '',
        hosp_lv: '',
        legent_name: '',
        inchg_hosp_resper_name: '',
        inchg_hosp_resper_tel: '',


      },
      queryForm: {
        pageNo: 1,
        pageSize: 10,
      },
      queryNumForm: {
        org_id: '',
        time: '',
      },
      orgQueryForm: {
        pageNo: 1,
        pageSize: 10,
        pack_year: '',
        ids: {},
        org_id: '',
        verify_ids: {},
        if_open: '1', //机构上架的数报
        pack_source: '1', //套餐来源 (机构:1，通用:2)
      },
      rules: {
        apply_bank_id: [
          { required: true, trigger: 'blur', message: '请输入正确选项' },
        ],
        application_quota: [
          { required: true, trigger: 'blur', message: '请输入正确选项' },
        ],
        depositaryBank: [
          { required: true, trigger: 'blur', message: '请输入正确选项' },
        ],
        bankName: [
          { required: true, trigger: 'blur', message: '请输入正确选项' },
        ],
        settlementAccount: [
          { required: true, trigger: 'blur', message: '请输入正确选项' },
        ],
        application_quota: [
          { required: true, message: "请输入内容", trigger: 'blur' },
          { pattern: /^\+?[1-9]\d*$/, message: '请输入大于0的正整数' }
        ],
      },
      title: '',
      dialogFormVisible: false,
      dialogVisible: false,
      active: -1,
      options: [
        {
          value: '选项1',
          label: '黄金糕',
        },
        {
          value: '选项2',
          label: '双皮奶',
        },
      ],
      value: '',
      spanIndex: [],
      selectedIds: [],
      isShow: true,
      natyList: [],
    }
  },
  created() {
    var that = this;
    that.fetchData();
    that.natyData();
    var userinfo = JSON.parse(localStorage.getItem("userinfo"));
    that.user_type = userinfo.user_type;
    // 2:医疗机构 3:零售药店
    if (that.user_type == 2) {
      that.isShow = true;
    } else if (that.user_type == 3){
      that.isShow = false;
      console.log(that.isCheck);
    }else {
      that.isShow = false;
    }
  },
  methods: {
    tcView(row) {
      this.$refs['views'].showDia(row)
    },
    selectName(index, packId) {
      this.active = index //this指向app
      let arrIndex = this.selectedIds.indexOf(packId)
      if (arrIndex > -1) {
        this.selectedIds.splice(arrIndex, 1)
      } else {
        //选中
        this.selectedIds = []
        this.selectedIds.push(packId)
      }
    },
    changeTypeVal(val) {},
    save() {
      var that = this ;
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          addLoanApplication(that.form).then((res) => {
            if (res.code == 0) {
              that.$baseMessage('操作成功!', 'success');
            }
          })
        } else {
          return false
        }
      })
    },

    handleClose(done) {
      done()
    },
    callOf(formName) {
      // console.log("sadad",formName)
      // this.$refs.formName.resetFields();
      // this.$refs['formName'].clearValidate()
      this.dialogVisible = false
    },

    changeYear() {
      this.fetchData()
      this.fetchOrgData()
    },
    changeOrgSelect() {
      this.fetchOrgData()
    },
    async getBookingNum() {
      this.queryNumForm.org_id = this.form.orgId
      this.queryNumForm.time = this.form.time
      const { data } = await getBookingNum(this.queryNumForm)
      if (data != null) {
        this.form.num = data.limit_person - data.booking_person
      } else {
        this.form.num = 0
      }
    },
    async fetchOrglist() {
      this.listLoading = true
      const { data } = await getAllOrgList()
      this.orgList = data
      setTimeout(() => {
        this.listLoading = false
      }, 200)
    },
    async fetchData() {

      var that = this;
      that.listLoading = true
      dkApplySelect().then((res) => {
        if (res.code == 0) {
          that.form = res.data ;
        }
      })
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    natyData() {
      var that = this ;
      selectListAll().then((res) => {
        if(res.code == 0){
          that.natyList = res.data ;
        }
      })
    },
  },
}
</script>
<style lang="scss" scoped>
.tc-con {
  border-radius: 4px;
  text-align: center;
  padding-left: 0 !important;
  padding-right: 20px !important;

  .tc-box {
    padding: 14px;
    position: relative;

    p {
      padding: 0;
      margin: 4px 0 8px 0;
      width: 100%;
      height: 24px;
      line-height: 24px;
      font-weight: bold;
      overflow: hidden;
      text-overflow: ellipsis;
    }

    span {
      display: inline-block;
      width: 100%;
    }

    .tc-delete {
      position: absolute;
      top: 6px;
      right: 6px;
      color: #e6a23c;
      font-size: 16px;
      cursor: pointer;
    }

    .check-btn {
      position: absolute;
      right: 0px;
      bottom: 0px;
      font-size: 24px;
      width: 0px;
      height: 0px;
      border-left: 46px solid transparent;
      border-bottom: 36px solid #ebeef5;

      i {
        color: #fff;
        position: absolute;
        top: 14px;
        right: 2px;
      }
    }
  }

  .active {
    .check-btn {
      border-bottom: 36px solid #1890ff;
    }
  }
}
.new-tc-con .el-card {
  border: 1px dashed #82a6fb;
}
</style>