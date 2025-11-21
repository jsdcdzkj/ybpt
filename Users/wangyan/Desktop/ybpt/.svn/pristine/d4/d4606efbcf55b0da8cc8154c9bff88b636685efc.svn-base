<template>
  <el-dialog
      :close-on-click-modal="false"
      :title="title"
      :visible.sync="dialogFormVisible"
      append-to-body
      top="300px"
      width="500px"
      @close="close"
  >

    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="审核结果" prop="status">
        <el-select v-model="form.status" style="width:100%;">
          <el-option label="通过" value="1"></el-option>
          <el-option label="驳回" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="审核意见" prop="verify_reason">
        <el-input v-model.trim="form.verify_reason" :rows="6" type="textarea"/>
      </el-form-item>
    </el-form>


    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import {updateConsumables} from "@/api/consumables";

export default {
  name: 'Index',
  components: {},
  data() {
    return {
      title: '',
      dialogFormVisible: false,
      form: {
        pageNo: 1,
        pageSize: 10,
        status: '',//审核状态 (待审:0，通过:1， 驳回:2)
        verify_reason: '',//审核意见
        id: '',//审核id
      },
      rules: {
        status: [{required: true, trigger: 'blur', message: '请选择审核结果'}],
        // verify_reason: [{required: true, trigger: 'blur', message: '请输入审核意见'}],
      },
    }
  },
  mounted() {
  },
  methods: {
    showDia(row) {
      this.title = '审核'
      this.form.id = row
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
          const {code, msg} = await updateConsumables(this.form)
          if (code == -1) {
            this.$baseMessage(msg, 'error')
          } else {
            this.$baseMessage(msg, 'success')
          }
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