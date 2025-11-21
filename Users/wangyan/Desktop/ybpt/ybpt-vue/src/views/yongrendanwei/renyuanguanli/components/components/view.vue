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
          <el-form-item label="姓名" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="性别" prop="password">
            <el-select v-model.trim="form.username" class="w" disabled>
              <el-option value="0" label="男"></el-option>
              <el-option value="1" label="女"></el-option>           
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="年龄" prop="username">
           <el-input
              v-model.trim="form.username"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="身份证号" prop="username">
           <el-input
              v-model.trim="form.username"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="统筹区" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="所属区" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="单位名称" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="单位编号" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="单位类型" prop="username">
            <el-select v-model.trim="form.username" class="w" disabled>
              <el-option value="0" label="男"></el-option>
              <el-option value="1" label="女"></el-option>           
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="参保险种" prop="username">
            <el-select v-model.trim="form.username" class="w" disabled>
              <el-option value="0" label="男"></el-option>
              <el-option value="1" label="女"></el-option>           
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="参保状态" prop="username">
            <el-select v-model.trim="form.username" class="w" disabled>
              <el-option value="0" label="男"></el-option>
              <el-option value="1" label="女"></el-option>           
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="异地就医" prop="username">
            <el-select v-model.trim="form.username" class="w" disabled>
              <el-option value="0" label="男"></el-option>
              <el-option value="1" label="女"></el-option>           
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="死记标志" prop="username">
            <el-select v-model.trim="form.username" class="w" disabled>
              <el-option value="0" label="有"></el-option>
              <el-option value="1" label="无"></el-option>           
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="体检资格" prop="username">
            <el-select v-model.trim="form.username" class="w" disabled>
              <el-option value="0" label="有"></el-option>
              <el-option value="1" label="无"></el-option>           
            </el-select>
          </el-form-item>
        </el-col>
       
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <!-- <el-button type="primary" @click="save">确 定</el-button> -->
    </div>
  </el-dialog>
</template>

<script>
import { doEdit } from '@/api/userManagement'
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
        this.title = '详情'
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
    save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          const { msg } = await doEdit(this.form)
          this.$baseMessage(msg, 'success')
          this.$emit('fetch-data')
          this.close()
        } else {
          return false
        }
      })
    },
  },
}
</script>
