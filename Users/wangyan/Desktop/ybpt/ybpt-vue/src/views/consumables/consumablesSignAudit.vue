<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="500px"
      @close="close"
      :close-on-click-modal="false"
      append-to-body
      top="300px"
  >

    <el-form label-width="80px" ref="form" :model="form" :rules="rules">
      <el-form-item label="审核结果" prop="status">
        <el-select v-model="form.status" style="width:100%;">
          <el-option label="审核通过" value="1"></el-option>
          <el-option label="审核驳回" value="2"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="驳回意见" prop="reason" v-if="form.status==2">
        <el-input v-model.trim="form.reason" type="textarea" :rows="6"/>
      </el-form-item>
    </el-form>


    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button @click="save" type="primary">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>

import {insertOrUpdate} from '@/api/consumables'

export default {
  name: 'Index',
  components: {},
  data() {
    return {
      title: '',
      dialogFormVisible: false,
      form: {
        status: '',//审核状态 (待审:0，通过:1， 驳回:2)
        reason: '',//驳回意见
        id: '',//审核id
      },
      rules: {
        status: [{required: true, trigger: 'blur', message: '请选择审核结果'}],
        reason: [{required: true, trigger: 'blur', message: '请输入驳回意见'}],
      },
    }
  },
  mounted() {
  },
  methods: {
    showDia(row) {
      this.title = '审核'
      this.form.id = row.id
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
          const {code, msg} = await insertOrUpdate(this.form)
          if (code == -1) {
            this.$baseMessage('请尝试刷新页面！', 'error')
          } else {
            this.$baseMessage('操作成功', 'success')
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