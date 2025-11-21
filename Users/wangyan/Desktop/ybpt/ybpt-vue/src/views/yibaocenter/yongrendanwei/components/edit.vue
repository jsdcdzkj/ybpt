<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="单位编号" prop="username">
            <el-input
              v-model.trim="form.emp_no"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="单位名称" prop="username">
            <el-input
              v-model.trim="form.emp_name"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="单位地址" prop="username">
            <el-input
              v-model.trim="form.emp_address"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="市直单位" prop="username">
            <el-select v-model.trim="form.parentOrgCode" class="w" clearable>
              <el-option
                v-for="item in admdvs"
                :key="item.emp_no"
                :label="item.emp_name"
                :value="item.emp_no"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="省属单位" prop="isDirectly">
            <el-radio-group v-model="form.isDirectly">
              <el-radio label="1">是</el-radio>
              <el-radio label="0">否</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { selectListAll } from '@/api/administrative_unit.js'
  import { info, updateEmployingInfo } from '@/api_check/EmployingInfo.js'
  export default {
    name: 'UserManagementEdit',
    data() {
      return {
        form: {
          emp_name: '',
          emp_no: '',
          emp_address: '',
          parentOrgCode: '',
          isDirectly: 1,
        },
        data: {
          emp_name: '',
        },
        rules: {},
        title: '',
        dialogFormVisible: false,
        id: '',
        admdvs: [],
      }
    },
    created() {},
    methods: {
      showDia(row) {
        if (!row) {
          this.title = '添加'
        } else {
          this.getInfo(row.id)
          this.getList()
          this.title = '编辑'
        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {
        var that = this
        updateEmployingInfo(that.form).then((res) => {
          if (res.code == 0) {
            that.close()
            that.$emit('fetch-data')
          }
        })
      },
      getInfo(id) {
        var that = this
        info(id).then((res) => {
          if (res.code == 0) {
            that.form = res.data
          }
        })
      },
      getList() {
        var that = this
        selectListAll(that.data).then((res) => {
          if (res.code == 0) {
            that.admdvs = res.data
          }
        })
      },
    },
  }
</script>
