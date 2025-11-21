<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="700px"
    @closeChildDialog="close"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="医保编码" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="医保信用等级" prop="password" class="custemitem">
            <el-input
              v-model.trim="form.password"             
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="国家医保信息编码" prop="username" class="custemitem">
           <el-select v-model.trim="form.username" class="w">
              <el-option value="0" label="医保人员"></el-option>
              <el-option value="1" label="医院"></el-option>
              <el-option value="2" label="零售药店"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="药店名称" prop="username">
            <el-cascader
                      :options="options"
                      class="w"
                      placeholder="可搜索"
                      filterable
                      clearable
                      @change="handleChange"
                    ></el-cascader>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="地址" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="经营方式" prop="username">
            <el-select v-model.trim="form.username" class="w">
              <el-option value="0" label="是"></el-option>
              <el-option value="1" label="否"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="所有制形式" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="经营性质" prop="username">
             <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col> 
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="所在行政区" prop="username">
             <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col> 
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="社会统一信用代码" prop="username" class="custemitem">
             <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>  
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="药品经营许可证" prop="username" class="custemitem">
             <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>  
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="法定代表人" prop="username">
             <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>  
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="药店联系人" prop="username">
             <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="联系电话" prop="username">
             <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>    
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
      <el-button type="danger" @click="shensu">申 诉</el-button>
    </div>
    <views ref="views" @fetch-data="form.password"></views>
    <edit ref="edit" @fetch-data="form.password"></edit>
  </el-dialog>
</template>

<script>
import { doEdit } from '@/api/userManagement'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
import Views from './shensu'
import Edit from './shenqing'
export default {
  name: 'UserManagementEdit',
  components: {Views,Edit},
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
        this.title = '信息自审'
      } else {
        this.title = '信息自审'
        this.form = Object.assign({}, row)
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
    shensu() {
      this.$refs['views'].showDia()
    },
    save() {
      this.$refs['edit'].showDia()
    },
  },
}
</script>
