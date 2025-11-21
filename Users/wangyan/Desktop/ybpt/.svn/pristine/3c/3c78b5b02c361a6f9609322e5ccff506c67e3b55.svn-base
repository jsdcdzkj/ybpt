<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1000px" :close-on-click-modal="false"
    @close="close">
    <el-form ref="form" :model="form" :rules="rules" label-width="116px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="登录账号" prop="username">
            <el-input v-model.trim="form.username" autocomplete="off" :disabled="isDisabled"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="密码" prop="pass">
            <el-input v-model.trim="form.pass" type="password" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="用户名" prop="name">
            <el-input v-model.trim="form.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="姓名" prop="alias">
            <el-input v-model.trim="form.alias" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="身份证号" prop="idNumber">
            <el-input v-model.trim="form.idNumber" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="职位" prop="position">
            <el-input v-model.trim="form.position" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="电话" prop="telephone">
            <el-input v-model.trim="form.telephone" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="座机" prop="landline">
            <el-input v-model.trim="form.landline" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="邮箱" prop="mailbox">
            <el-input v-model.trim="form.mailbox" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="账号类型" prop="user_type">
            <el-select v-model.trim="form.user_type" class="w" @change="dropDown" :disabled="userinfo.user_type != 1" >
              <el-option value="1" label="市直单位"></el-option>
              <el-option value="2" label="定点医疗机构"></el-option>
              <el-option value="3" label="定点零售药店"></el-option>
              <el-option value="4" label="用人单位"></el-option>
              <el-option value="5" label="体检机构"></el-option>
              <el-option value="6" label="银行"></el-option>
              <el-option value="7" label="非定点医疗机构"></el-option>
              <el-option value="8" label="非定点零售药店"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="所属机构" prop="org_code">
            <el-input v-model.trim="form.org_name" :disabled="userinfo.user_type != 1" @click.native="openwin">
              <el-button slot="append" icon="el-icon-search" :disabled="userinfo.user_type != 1" @click="openwin"></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="机构/药店管理员" prop="isAdmin" >
            <el-radio-group v-model="form.isAdmin" :disabled="userinfo.user_type != 1">
                <el-radio label="0">否</el-radio>
                <el-radio label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="所属科室" prop="dept_code">
            <el-select v-model.trim="form.dept_code" class="w" @change="medinsDeptBChange" clearable>
              <el-option v-for="item in medinsDeptB" :key="item.dept_no" :label="item.dept_name" :value="item.dept_no">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="mac地址" prop="mac">
            <el-input v-model.trim="form.mac" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="机器人激活码" prop="cdkey">
            <el-input v-model.trim="form.cdkey" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="是否激活" prop="is_auth">
            <el-select v-model.trim="form.is_auth" class="w">
              <el-option value="1" label="是"></el-option>
              <el-option value="0" label="否"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="激活开始日期" prop="beginDate">
            <el-date-picker v-model.trim="form.beginDate" type="date"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="激活结束日期" prop="endDate">
            <el-date-picker v-model.trim="form.endDate" type="date"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col>
          <el-form-item label="角色选择" prop="checkedRoles">
            <el-select v-model="checkedRoles" filterable multiple placeholder="请选择" class="w">
              <el-option v-for="item in roleOptions" :key="item.id" :label="item.role_name" :value="item.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading" :disabled="disabled">
        {{ loading ? '确定中 ...' : '确定' }}
      </el-button>
    </div>
    <hospital ref="userhospital" @fetch-data="hospital"></hospital>
  </el-dialog>
</template>

<script>
import {getListAll} from '@/api/medinsDeptB'
import {getAllRoles, getRolesByUserId, saveUser} from '@/api/userManagement'
import Hospital from '@/components/userhospital'

export default {
  name: 'UserManagementEdit',
  components: { Hospital },
  data() {
    var validatePass = (rule, value, callback) => {
      if (this.checkedRoles.length === 0) {
        callback(new Error('请选择角色'))
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
    //验证密码
    const checkPass = (rule, value, callback) => {
      const IDCardReg = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[~!@#$%^&*()_+`\-={}:";'<>,.\/]).{8,16}$/
      // const sfzhReg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
      if (value) {
        if (IDCardReg.test(value)) {
          callback()
        } else {
          // callback()
          if(this.tempPass == value){
            callback()
          }else {
            callback(new Error('小写字母+大写字母+数字+特殊符号+长度为8-16位'))
          }

        }
      } else {
        callback(new Error('请输入密码'))
        callback()
      }
    }
    return {
      userinfo: '',
      loading: false,
      disabled: false,
      form: {
        username: '',
        pass: '',
        email: '',
        permissions: [],
        org_code: '',
        org_name: '',
        idNumber: '',
        isAdmin: '0'
      },
      medinsDeptB: null,
      isDisabled: false,
      roleOptions: [],
      checkedRoles: [],
      orgList: [],
      rules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入登录账号' },
        ],
        idNumber: { required: true, validator: checkIDCard, trigger: 'blur' },
        pass: [{ required: true, validator: checkPass, trigger: 'blur'}],
        name: [{ required: true, trigger: 'blur', message: '请输入姓名' }],
        org_code: [
          { required: true, trigger: 'submit', message: '请选择所属机构' },
        ],
        checkedRoles: [
          { required: true, validator: validatePass, trigger: 'blur' },
        ],
        user_type: [
          { required: true, trigger: 'blur', message: '请选择账号类型' },
        ],
        permissions: [
          { required: true, trigger: 'blur', message: '请选择权限' },
        ],
      },
      title: '',
      tempPass: '',
      dialogFormVisible: false,
      dropData: {},
    }
  },
  created() {
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
  },
  methods: {
    showEdit(row) {

      var that = this
      this.getRoles()
      if (!row) {
        this.isDisabled = false
        this.title = '添加'
        this.checkedRoles = []
        if (that.userinfo.org_code != 320399) {
          that.form.org_code = that.userinfo.org_code
          that.form.org_name = that.userinfo.org_name
          // that.form.isAdmin = that.userinfo.isAdmin || '0'
          that.form.user_type = that.userinfo.user_type
        }
      } else {
        this.isDisabled = true
        this.title = '编辑'
        this.tempPass = row.pass
        this.form = Object.assign({}, row)
        if (that.userinfo.org_code != 320399) {
          console.log(that.userinfo.isAdmin)
          // that.form.org_code = that.userinfo.org_code
          // that.form.org_name = that.userinfo.org_name
          // that.form.isAdmin = that.userinfo.isAdmin || '0'
          // that.form.user_type = that.userinfo.user_type
        }
        this.medinsDeptB = this.getMedinsDeptB()
        setTimeout(function () {
          that.getRolesByUid(row.id)
        }, 200)
      }
      this.dialogFormVisible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    async getRoles() {
      var that = this
      const res = await getAllRoles()
      if ((res.code = '0')) {
        that.roleOptions = res.data
      }
    },
    medinsDeptBChange(val) {
      var medinsDeptB = this.medinsDeptB.find((item) => {
        return item.dept_no == val
      })
      this.form.dept_code = medinsDeptB.dept_no
      this.form.dept_name = medinsDeptB.dept_name
    },
    async getRolesByUid(userId) {
      var that = this
      const res = await getRolesByUserId({ userId: userId })
      if ((res.code = '0')) {
        that.checkedRoles = res.data
      }
    },
    save() {
      if (this.loading) {
        return
      }
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.loading = true
          this.disabled = true
          // 动画关闭需要一定的时间
          setTimeout(() => {
            this.loading = false
            this.disabled = false
          }, 1000)


          this.form.roleIds = this.checkedRoles
          const res = await saveUser(this.form)
          if (res.code == '0') {
            this.$baseMessage('保存成功！', 'success')
            this.$emit('fetch-data')
            this.close()
          } else {
            this.$baseMessage(res.msg, 'error')
          }
        } else {
          return false
        }
      })
    },
    openwin() {
      var that = this
      if (
        that.form.user_type == '' ||
        that.form.user_type == null ||
        that.form.user_type == undefined
      ) {
        that.$baseMessage('请先选择账号类型', 'error')
      } else {
        that.$refs['userhospital'].showDia(that.form.user_type)
      }
    },
    hospital(data) {
      var that = this
      console.log(data)
      that.form.org_code = data.medins_code
      that.form.org_name = data.medins_name
      that.medinsDeptB = null
      that.getMedinsDeptB()
      // that.medins_name = data.medins_name;
    },
    async getMedinsDeptB() {
      if (
        this.form.user_type == '' ||
        this.form.user_type == null ||
        this.form.user_type == undefined
      ) {
        this.$baseMessage('请先选择账号类型', 'error')
        return
      }
      if (
        this.form.org_code == '' ||
        this.form.org_code == null ||
        this.form.org_code == undefined
      ) {
        this.$baseMessage('请先选择所属机构', 'error')
        return
      }
      const list = await getListAll({
        user_type: this.form.user_type,
        org_code: this.form.org_code,
      })
      this.medinsDeptB = list.data.data
    },
    dropDown() {
      var that = this
      that.form.org_code = ''
    },
  },
}
</script>
