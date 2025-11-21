<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
    append-to-body
    top="300px"
  >

      <el-form label-width="80px" :model="queryForm">    
          <el-form-item label="审核结果">
            <el-select v-model="queryForm.username" class="w">
              <el-option label="同意" value="0"></el-option>
              <el-option label="驳回" value="1"></el-option>            
            </el-select>
          </el-form-item> 
          <el-form-item label="认定额度">
            <el-input v-model.trim="queryForm.username" autocomplete="off"></el-input>
          </el-form-item>   
          <el-form-item label="审核意见">
            <el-input v-model.trim="queryForm.username" type="textarea" :rows="6" />
          </el-form-item>       
      </el-form>


    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button @click="save" type="primary">确 定</el-button>
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
        this.title = '批量审核'
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