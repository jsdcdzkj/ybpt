<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card reg-con" shadow="never">
          <div slot="header">
            <span class="tips">注册信息</span>
          </div>
          <el-card class="box-card reg-card">
            <el-form :model="ruleForm" status-icon :rules="rules" ref="ruleForm" label-width="100px" class="registForm">

              <!--  <el-form-item label="手机号" prop="phone">
                 <el-input v-model.trim="ruleForm.phone" placeholder="请输入手机号">
                 <vab-icon :icon="['fas', 'mobile']" slot="suffix"></vab-icon>
                 </el-input>
               </el-form-item>
              <el-form-item label="验证码" prop="verify">
                 <el-input v-model.trim="ruleForm.verify" placeholder="请输入验证码">
                   <el-button slot="append"
                              @click.native.prevent="bindforgetSendCode"
                              class="codebtn"
                              :disabled="disabled"
                   >{{ btnText }}</el-button>
                 </el-input>
               </el-form-item>-->
              <el-form-item label="身份证号" prop="certno">
                <el-input v-model.trim="ruleForm.certno" placeholder="请输入身份证号">
                  <vab-icon :icon="['fas', 'id-card']" slot="suffix"></vab-icon>
                </el-input>
              </el-form-item>
              <el-form-item label="手机号" prop="telephone">
                <el-input v-model.trim="ruleForm.telephone" placeholder="请输入手机号">
                </el-input>
              </el-form-item>
              <el-form-item label="登陆用户名" prop="login_Name">
                <el-input v-model.trim="ruleForm.login_Name" placeholder="登陆用户名">
                </el-input>
              </el-form-item>
              <el-form-item label="设置密码" prop="pwd">
                <el-input v-model.trim="ruleForm.pwd" placeholder="请输入密码" show-password>
                </el-input>
              </el-form-item>

              <div class="bot-btn-box">
                <el-button type="primary" @click="submitForm('ruleForm')">提交</el-button>
                <el-button @click="resetForm('ruleForm')">重置</el-button>
              </div>

            </el-form>
          </el-card>
        </el-card>
      </el-col>

    </el-row>


  </div>
</template>

<script>
import { getList, doDelete } from '@/api/userManagement'
import { getPhysicalAst, setRegist } from '@/api_check/physicalAst'

export default {
  name: 'astRegister',
  data() {
    return {
      listLoading: true,
      elementLoadingText: '正在加载...',
      ruleForm: {
        // phone: "",
        // verify: "",
        login_Name: "",
        telephone: "",
        pwd: "",
        certno: "",
        // delivery: false,
      },

      rules: {

        // phone: [
        //   { required: true, message: "请输入手机号", trigger: "blur" },
        //   { min: 11, max: 11, message: "请输入正确格式的手机号", trigger: "blur" },
        // ],
        certno: [

          { required: true, message: "请输入身份证号", trigger: "submit" },
          { min: 18, max: 18, message: "请输入身份证号", trigger: "submit" },
          {
            required: false,
            trigger: 'blur',
            pattern: /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[0-2])(([0-2][1-9])|10|20|30|31)\d{3}(\d|X|x)$/,
            message: '身份证号格式不正确'
          }
        ],
        telephone: [{ required: false, trigger: 'blur', pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误' }],
        login_Name: [
          { required: true, message: "请输入用户名", trigger: "submit" }
        ],
        pwd: [
          { required: true, message: "请输入密码", trigger: "submit" }
        ],
        // verify: [
        //   { required: true, message: "请输入验证码", trigger: "blur" },
        //   { min: 4, max: 4, message: "请输入正确的验证码", trigger: "blur" },
        // ],
      },

      //   验证码按钮
      type: "",
      btnText: "获取验证码",
      disabled: false,
      dialogFormVisible: false,
      dialogVisible: false,
    }
  },
  created() {
  },
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {
    // 发送验证码
    bindforgetSendCode() {
      this.$refs["ruleForm"].validateField("phone", (errorMessage) => {
        if (!errorMessage) {
          // captchaSms({phonenumber: this.ruleForm.phone}).then(res => {
          //   console.log(res);
          // })
          this.disabled = true;
          this.btnText = "请稍候...";
          setTimeout(() => {
            this.doLoop(60);
          }, 500);
        } else {
          return false;
        }
      });
    },
    // 手机验证码的倒计时
    doLoop: function (seconds) {
      seconds = seconds ? seconds : 60;
      this.btnText = seconds + "s后获取";
      let countdown = setInterval(() => {
        if (seconds > 0) {
          this.btnText = seconds + "s后获取";
          --seconds;
        } else {
          this.btnText = "获取验证码";
          this.disabled = false;
          clearInterval(countdown);
        }
      }, 1000);
    },
    submitForm(formName) {
      this.$refs[formName].validate(async (valid) => {
        if (valid) {
          // const {data} = await getPhysicalAst(this.ruleForm);
          const { data, msg } = await setRegist(this.ruleForm);
          if (data.id != null) {
            this.ruleForm.certno = '';
            this.ruleForm.login_Name = '';
            this.ruleForm.telephone = '';
            this.ruleForm.pwd = '';
            this.$message(msg);
            this.$router.push({ path: '/astOrder', query: { certno: this.ruleForm.certno } })
          } else {
            this.$message({
              message: msg,
              type: 'warning'
            });
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
  },
}
</script>
<style scoped>
::v-deep .reg-con {
  height: calc(100vh - 250px);
}

.registForm {
  width: 460px;
}

.reg-card {
  width: 510px;
  padding-top: 20px;
  margin: 0 auto;
}

.bot-btn-box {
  margin-top: 30px;
  padding-top: 20px;
  border-top: 1px solid #efefef;
  text-align: center;
}
</style>