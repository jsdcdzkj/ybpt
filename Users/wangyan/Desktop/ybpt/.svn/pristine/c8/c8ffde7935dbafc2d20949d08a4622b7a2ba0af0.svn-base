<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
    append-to-body
  >
    <el-form ref="form" :model="form" :rules="rules" style="padding-right:0">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="" prop="username">
            <el-input
              v-model.trim="form.username"
              autocomplete="off"
              type="textarea"
              :rows="10"
            ></el-input>
          </el-form-item>
        </el-col>       
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">提 交</el-button>     
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'UserManagementEdit',
  data() {
    return {     
      form: {
        username: '',        
      },
      rules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入用户名' },
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
        this.title = '请填写驳回原因'
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
      this.$refs['form'].validate(async (valid) => {
        if (valid) {         
          this.$baseMessage('申诉成功', 'success')          
          this.close()
        } else {
          return false
        }
      })
    },
  },
}
</script>
