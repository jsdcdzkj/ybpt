<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="660px"
    @close="close"
    append-to-body
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="本地码" prop="base_code">
            <el-input
              v-model.trim="form.base_code"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="国家码" prop="org_code">
            <el-input
                    v-model.trim="form.org_code"
                    autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="名称" prop="org_name">
           <el-input
              v-model.trim="form.org_name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="应付金额" prop="payable_amount">
           <el-input
              v-model.trim="form.payable_amount"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="已付金额" prop="paid_amount">
            <el-input
              v-model.trim="form.paid_amount"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="年度清算需结付金额" prop="payment_amont" class="custemitem">
            <el-input
              v-model.trim="form.payment_amont"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="医疗机构承担金额" prop="org_borne_amount" class="custemitem">
            <el-input
              v-model.trim="form.org_borne_amount"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="发生总金额" prop="sum_amount" class="custemitem">
            <el-input
              v-model.trim="form.sum_amount"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="总额预算" prop="budget_amount">
            <el-input
                    v-model.trim="form.budget_amount"
                    autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="不予支付" prop="withhold_payment">
            <el-input
                    v-model.trim="form.withhold_payment"
                    autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="save" :loading="loading" :disabled="form.disabled">
        {{ loading ? '确定中 ...' : '确定' }}
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import { oneInfo,edit } from '@/api/qs'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
export default {
  name: 'UserManagementEdit',
  data() {
    return {
      options: regionDataPlus,
      form: {
        username: '',
        password: '',
        email: '',
        permissions: [],
      },
      rules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入用户名' },
        ],
        password: [{ required: true, trigger: 'blur', message: '请输入密码' }],
        email: [{ required: true, trigger: 'blur', message: '请输入邮箱' }],
        permissions: [
          { required: true, trigger: 'blur', message: '请选择权限' },
        ],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {},
  methods: {
    showDia(row) {      
      if (!row) {
        this.title = '添加'
      } else {
        this.title = '修改'
        console.log(row);
        oneInfo(row).then((res) => {
          if (res.code == 0) {
            this.form = res.data ;
          }
        })
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
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          edit(this.form).then((res) => {
            if (res.code == 0) {
              this.$emit('fetch-data')
              this.$baseMessage("上传成功", 'success')
              this.close()
            }
          })
        } else {
          return false
        }
      })
    },
  },
}
</script>
