<template>
  <el-dropdown @command="handleCommand">
    <span class="avatar-dropdown">
      <!--<el-avatar class="user-avatar" :src="avatar"></el-avatar>-->
      <!-- <img class="user-avatar" :src="avatar" alt="" /> -->
      <img class="user-avatar" src="~@/assets/avatar2.gif" alt="" />
      <div class="user-name">
        {{ username }}
        <i class="el-icon-arrow-down el-icon--right"></i>
      </div>
    </span>

    <el-dropdown-menu slot="dropdown">
      <!--<el-dropdown-item command="personalCenter">个人中心</el-dropdown-item>    -->
      <el-dropdown-item command="passwd">修改密码</el-dropdown-item>
      <el-dropdown-item command="logout" divided>退出登录</el-dropdown-item>
    </el-dropdown-menu>

    <el-dialog
      title="修改密码"
      :visible.sync="dialogFormVisible"
      width="600px"
      :show-close="false"
      :close-on-click-modal="false"
      :modal-append-to-body="false"
    >
      <el-form :model="form" :rules="rules" ref="form">
        <el-form-item label="旧密码" :label-width="formLabelWidth">
          <el-input
            type="password"
            v-model="form.tempPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item label="新密码" :label-width="formLabelWidth" prop="pass">
          <el-input
            type="password"
            v-model="form.pass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
        <el-form-item
          label="确认密码"
          :label-width="formLabelWidth"
          prop="checkPass"
        >
          <el-input
            type="password"
            v-model="form.checkPass"
            autocomplete="off"
          ></el-input>
        </el-form-item>
      </el-form>
      密码规则说明：小写字母+大写字母+数字+特殊符号+长度为8-16位。
      <div slot="footer" class="dialog-footer">
        <!--                <el-button v-show="userinfo.pass != 'e10adc3949ba59abbe56e057f20f883e'" @click="reseat">取 消</el-button>-->
        <el-button type="primary" @click="submitForm('form')">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      title="完善用户信息"
      :visible.sync="isCert"
      width="600px"
      :show-close="false"
      :close-on-click-modal="false"
      :modal-append-to-body="false"
    >
      <el-form :model="form2" :rules="rules2" ref="form2">
        <el-form-item
          label="身份证号码"
          :label-width="formLabelWidth"
          prop="idNumber"
        >
          <el-input v-model="form2.idNumber" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <!--                <el-button v-show="userinfo.pass != 'e10adc3949ba59abbe56e057f20f883e'" @click="reseat">取 消</el-button>-->
        <el-button type="primary" @click="submitForm2('form2')">
          确 定
        </el-button>
      </div>
    </el-dialog>
  </el-dropdown>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { recordRoute } from '@/config'
  import { pass, updateCert, isCert } from '@/api/userManagement'
  import { ws_url } from '@/config'

  import md5 from 'js-md5'
  import { addAu } from '@/api/administrative_unit'
  import user from '../../../store/modules/user'

  export default {
    name: 'VabAvatar',
    computed: {
      ...mapGetters({
        avatar: 'user/avatar',
        username: 'user/username',
      }),
    },
    data() {
      let patter = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>,.\/]).{8,16}$/
      var validatePass = (rule, value, callback) => {
        // console.log(patter.test(value))
        if (value === '') {
          callback(new Error('请输入密码'))
        } else if (!patter.test(value)) {
          callback(new Error('不符合密码设置规则'))
        } else {
          if (this.form.checkPass !== '') {
            this.$refs.form.validateField('checkPass')
          }
          callback()
        }
      }
      var validatePass2 = (rule, value, callback) => {
        if (value === '') {
          callback(new Error('请再次输入密码'))
        } else if (!patter.test(value)) {
          callback(new Error('不符合密码设置规则'))
        } else if (value !== this.form.pass) {
          callback(new Error('两次输入密码不一致!'))
        } else {
          callback()
        }
      }

      // 身份证验证
      const checkIDCard = (rule, value, callback) => {
        const IDCardReg = /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[0-2])(([0-2][1-9])|10|20|30|31)\d{3}(\d|X|x)$/
        // const sfzhReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
        if (value) {
          if (IDCardReg.test(value)) {
            callback()
          } else {
            callback(new Error('身份证号格式不正确'))
          }
        } else {
          callback(new Error('身份证号码不能为空'))
          callback()
        }
      }
      return {
        dialogFormVisible: false,
        isCert: false,
        isIdCard: false,
        formLabelWidth: '120px',
        userinfo: '',
        form: {
          tempPass: '',
          pass: '',
          checkPass: '',
        },
        form2: {
          idNumber: '',
        },
        rules2: {
          idNumber: { required: true, validator: checkIDCard, trigger: 'blur' },
        },
        rules: {
          pass: [{ validator: validatePass, trigger: 'blur' }],
          checkPass: [{ validator: validatePass2, trigger: 'blur' }],
        },
      }
    },
    created() {
      this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
      // // 用人单位或体检体检机构第一次登录强制修改密码
      // if((this.userinfo.user_type == 4 || this.userinfo.user_type == 5) && this.userinfo.pass == 'e10adc3949ba59abbe56e057f20f883e'){
      //     this.dialogFormVisible = true
      // }
      // console.log(localStorage.getItem('update_pwd_auto'))
      // console.log(localStorage.getItem('update_pwd'))

      let patter = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>,.\/]).{8,16}$/
      if (localStorage.getItem('update_pwd_auto') != 'auto_login') {
        if (!patter.test(localStorage.getItem('update_pwd'))) {
          this.dialogFormVisible = true
        }
      }

      isCert().then(async (res) => {
        this.isIdCard = res.data
        if (!this.isIdCard) {
          this.isCert = true
        }
      })
    },
    mounted() {},

    methods: {
      reseat() {
        // this.$refs['form'].validate()
        // this.$refs['form'].clearValidate('pass')
        this.$refs.form.resetFields()
        // this.$refs['form'].resetFields()
        this.dialogFormVisible = false
        this.form.tempPass = ''
        this.form.pass = ''
        this.form.checkPass = ''
      },
      handleCommand(command) {
        switch (command) {
          case 'logout':
            this.logout()
            break
          case 'passwd':
            this.showDialogFormVisible()
            break
          case 'personalCenter':
            this.personalCenter()
            break
        }
      },
      personalCenter() {
        this.$router.push('/personalCenter/personalCenter')
      },
      logout() {
        this.$baseConfirm(
          '您确定要退出' + this.$baseTitle + '吗?',
          null,
          async () => {
            await this.$store.dispatch('user/logout')
            if (recordRoute) {
              const fullPath = this.$route.fullPath
              this.$router.push(`/login?redirect=${fullPath}`)
            } else {
              localStorage.clear()
              this.$router.push('/login')
            }
          }
        )
      },
      showDialogFormVisible() {
        this.dialogFormVisible = true
      },
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var userinfo = JSON.parse(localStorage.getItem('userinfo'))
            userinfo.pass = md5(this.form.pass)
            userinfo.tempPass = this.form.tempPass
            // console.log(this.form.pass)
            // console.log(userinfo.pass)
            pass(userinfo).then(async (res) => {
              if (res.code == 0) {
                await this.$store.dispatch('user/logout')
                if (recordRoute) {
                  const fullPath = this.$route.fullPath
                  this.$router.push(`/login?redirect=${fullPath}`)
                } else {
                  this.$router.push('/login')
                }
              } else {
                this.$baseMessage(res.msg, 'error')
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      submitForm2(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            var userinfo = JSON.parse(localStorage.getItem('userinfo'))
            this.form2.id = userinfo.id
            updateCert(this.form2).then(async (res) => {
              if (res.code == 0) {
                this.$baseMessage('操作成功！', 'success')
                this.isCert = false
              }
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
    },
  }
</script>
<style lang="scss" scoped>
  .avatar-dropdown {
    display: flex;
    align-content: center;
    align-items: center;
    justify-content: center;
    justify-items: center;
    height: 50px;
    padding: 0;

    .user-avatar {
      width: 40px;
      height: 40px;
      cursor: pointer;
      border-radius: 50%;
    }

    .user-name {
      position: relative;
      margin-left: 5px;
      margin-left: 5px;
      cursor: pointer;
    }
  }
</style>
