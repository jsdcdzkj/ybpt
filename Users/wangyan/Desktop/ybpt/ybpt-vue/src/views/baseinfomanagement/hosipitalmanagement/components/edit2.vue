<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
    :close-on-click-modal = "false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="授权码" prop="authorizationCode">
            <el-input
              v-model.trim="form.authorizationCode"
              autocomplete="off"
            ></el-input>
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
import { editData } from '@/api/fixmedins.js'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
export default {
  name: 'UserManagementEdit',
  data() {
    return {
      options: regionDataPlus,
      form: {
        authorizationCode:""
      },
      admdvs: [],
      rules: {
        authorizationCode: [
          { required: true, trigger: 'blur', message: '请输入授权码' },
        ]
      },
      title: '',
      dialogFormVisible: false,
      id: "",
      isDisabled: false,
    }
  },
  created() {
    this.getAdmdvs();

  },
  methods: {
    showDia(row) {
      console.log(row)
      if (!row) {
        this.title = '添加';
        this.id="" ;
        this.isDisabled=false ;
      } else {
        this.id = row.id ;
        this.title = '编辑';
        this.isDisabled=true ;
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
      var that = this ;
      that.$refs['form'].validate(async (valid) => {
        if (valid) {
          if(that.id != ''){
            editData(that.form).then((res) => {
              if(res.code == 0){
                that.$emit('fetch-data')
              }else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          }
          // that.$baseMessage(msg, 'success')

          that.close()
        } else {
          return false
        }
      })
    },

  },
}
</script>
