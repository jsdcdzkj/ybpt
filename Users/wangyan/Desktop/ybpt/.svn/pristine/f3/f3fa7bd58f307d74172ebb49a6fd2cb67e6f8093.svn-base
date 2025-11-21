<template>
  <el-dialog title="部门管理" :visible.sync="dialogFormVisible" width="500px" @close="close"
             :close-on-click-modal="false">
    <el-form ref="form" :model="form" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" style="margin-bottom: 20px;">
          <el-form-item label="所在部门" prop="dept_id">
            <el-select v-model="form.dept_id" placeholder="选择所在部门" clearable class="w" @change="selectOrg()">
              <el-option v-for="it in deptList" :key="it.id" :label="it.dept_name" :value="it.id">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading" :disabled="form.disabled">
        {{ loading ? '确定中 ...' : '确定' }}
      </el-button>
    </div>

  </el-dialog>
</template>

<script>


import { updateCiviWorkerInfo} from '@/api_check/civilworker_info.js'
import {findDeptInfo} from '@/api_check/dept_info.js'
import {CodeToText, regionDataPlus} from 'element-china-area-data'

export default {
  name: 'UserManagementEdit',
  components: {},
  data() {
    return {
      options: regionDataPlus,
      form: {
        loading: false,
        disabled: false,
        dept_id: '',
        id:'',
        dept_name: '',
        is_dept:"1"
      },
      dialogFormVisible: false,
      id: '',
      showDeath: false,
      dept: {
        dept_no: '',
        emp_code: '',
      },
      deptList: [],
      loading: false
    }
  },
  created() {
  },
  methods: {
    selectOrg() {
    },
    showDia(row) {
      this.loading = false;
      var userinfo = JSON.parse(localStorage.getItem("userinfo"));
      this.user_type = userinfo.user_type;
      this.form.id=row.id;
      this.form.dept_id=row.dept_id;
      this.dept.emp_code = userinfo.org_code;
      findDeptInfo(this.dept).then((res) => {
        this.deptList = res.data
      })
      this.dialogFormVisible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      // clearValidate()  清理某个字段的表单验证信息
      this.dialogFormVisible = false
    },
    save() {
      var that = this;
      if (this.loading) {
        return
      }
      that.$refs['form'].validate(async (valid) => {
        if (valid) {

          this.loading = true
          this.form.disabled = true
          // 动画关闭需要一定的时间
          setTimeout(() => {
            this.loading = false
            this.form.disabled = false
          }, 1000)

          updateCiviWorkerInfo(that.form).then((res) => {
            if (res.code == 0) {
              that.$emit('fetch-data')
              that.$baseMessage('操作成功', 'success')
            } else {
              that.$baseMessage(res.msg, 'error')
            }
          })

          that.close()
        } else {
          return false
        }
      })
    },

  },
}
</script>
