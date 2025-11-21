<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
    append-to-body
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">     
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="账号" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="密码" prop="password">
            <el-input
              v-model.trim="form.password"
              type="password"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="单位名称" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="当前状态" prop="username">
            <el-select v-model.trim="form.username" class="w">
              <el-option value="0" label="是"></el-option>
              <el-option value="1" label="否"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="创建时间" prop="username">
           <el-date-picker v-model.trim="form.username" type="date"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="创建人" prop="username">
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
      <el-button type="primary" @click="save" :loading="loading" :disabled="disabled">
        {{ loading ? '确定中 ...' : '确定' }}
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import { doEdit } from '@/api/userManagement'

export default {
  name: 'UserManagementEdit',
  data() {
    return {
      loading: false,
      disabled:false,
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
    showEdit(row) {
      if (!row) {
        this.title = '添加'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
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
          }, 400)
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
<style lang="scss" scoped>
::v-deep{
  .el-date-editor.el-input{width:100%}
}


</style>
