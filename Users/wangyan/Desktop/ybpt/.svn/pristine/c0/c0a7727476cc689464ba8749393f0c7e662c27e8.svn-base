<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
    append-to-body
    top="300px"
  >

      <el-form label-width="100px" :model="queryForm">    
          <el-form-item label="医保电子凭证" class="mb0">
            <el-input v-model.trim="queryForm.username" clearable />
          </el-form-item>      
      </el-form>


    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>
<script>
export default {
  name: 'Index',
  components: {},
  data() {
    return {
      title: '',
      dialogFormVisible: false,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
    }
  },
  mounted() {},
  methods: {
    showDia(row) {
      if (!row) {
        this.title = '请扫描医保电子凭证'
      } else {
        this.title = '查询'
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
      this.close()
    },
  },
}
</script>