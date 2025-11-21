<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="1000px"
    @close="close"
    :close-on-click-modal = "false"
  >
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
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" >
          <el-form-item label="违规记录" prop="violationRecord">
            <el-input
                    v-model.trim="form.violationRecord"
                    type="textarea"
                    :rows="5"

            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="审核状态" prop="apply_satus">
            <el-select v-model.trim="form.apply_satus" class="w">
              <el-option label="审核通过" value="1"></el-option>
              <el-option label="审核驳回" value="2"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-if="form.apply_satus == 1">
          <el-form-item label="申请银行" prop="apply_bank_id">
            <el-select v-model.trim="form.apply_bank_id" class="w">
              <el-option
                      v-for="item in natyList"
                      :key="item.bankNo"
                      :label="item.bankName"
                      :value="item.bankNo"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-if="form.apply_satus == 2">
          <el-form-item label="审核意见" prop="reason">
            <el-input
                    v-model.trim="form.reason"
                    type="textarea"
                    :rows="5"

            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading" :disabled="disabled">{{ loading ? '确定中 ...' : '确定' }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { info,getUserInfo,check } from '@/api/loan.js'
import { selectListAll} from '@/api/bank.js'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
export default {
  name: 'UserManagementEdit',
  data() {
    return {
      options: regionDataPlus,
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
        violationRecord: '',
      },
      admdvs: [],
      rules: {
        apply_bank_id: [
          { required: true, trigger: 'blur', message: '请输入正确选项' },
        ],
        apply_satus: [{ required: true, trigger: 'blur', message: '请输入正确选项' }],
      },
      title: '',
      dialogFormVisible: false,
      id: "",
      fixmedins_code: "",
      isDisabled: false,
      loading: false,
      disabled:false,
      isShow: true,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
      },
      natyList: [],
    }
  },
  created() {

  },
  methods: {
    showDia(row) {
      this.loading = false ;
      console.log(row)
      if (!row) {
        this.title = '贷款申请';
        this.id="" ;
        this.isDisabled=false ;
      } else {
        this.id = row.id ;
        this.fixmedins_code = row.fixmedins_code ;
        this.title = '编辑';
        this.isDisabled=true ;
        this.getInfo(this.id) ;
        this.getUserInfoData(this.fixmedins_code) ;
        this.natyData();
      }
      this.dialogFormVisible = true
    },
    handleChange(value) {
      let cityNames = []
      value.forEach((e) => {
        cityNames.push(CodeToText[e])
      })
      this.citys = cityNames.join('/')
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
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
            check(that.form).then((res) => {
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
    natyData() {
      var that = this ;
      selectListAll().then((res) => {
        if(res.code == 0){
          that.natyList = res.data ;
        }
      })
    },
    getInfo(id){
      var that = this ;
      info(id).then((res) => {
        if(res.code == 0){
          that.form = res.data ;
          that.form.apply_satus = "" ;
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
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },

  },
}
</script>
