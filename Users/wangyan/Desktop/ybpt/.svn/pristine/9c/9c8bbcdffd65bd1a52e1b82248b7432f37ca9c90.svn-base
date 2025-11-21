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
          <el-form-item label="银行名称" prop="emp_name">
            <el-input
              v-model.trim="form.emp_name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="银行地址" prop="emp_no" >
            <el-input
                    :disabled="isDisabled"
              v-model.trim="form.emp_no"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <!--<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
          <!--<el-form-item label="银行类型" prop="emp_type">-->
           <!--<el-select v-model.trim="form.emp_type" class="w">-->
              <!--<el-option value="0" label="医保人员"></el-option>-->
            <!--</el-select>-->
          <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="联系人" prop="admdvs">
            <el-input
                    :disabled="isDisabled"
              v-model.trim="form.emp_no"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="联系电话" prop="emp_address">
            <el-input
              v-model.trim="form.emp_address"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="账号" prop="emp_address">
            <el-input
              v-model.trim="form.emp_address"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="密码" prop="emp_address">
            <el-input
              v-model.trim="form.emp_address"
              autocomplete="off"
              type="password"
              show-password
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { addAu,updateAu,info } from '@/api/administrative_unit.js'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
import {getDicts} from '@/api/dictManagement'
export default {
  name: 'UserManagementEdit',
  data() {
    return {
      options: regionDataPlus,
      form: {
        emp_address:"",
        emp_name:"",
        emp_no:"",
        emp_type:"0",
        admdvs:"",
        emp_address:"",
      },
      admdvs: [],
      rules: {
        emp_name: [
          { required: true, trigger: 'blur', message: '请输入银行名称' },
        ],
        emp_no: [{ required: true, trigger: 'blur', message: '请输入银行编码' }],
        admdvs: [
          { required: true, trigger: 'blur', message: '请选择行政区划' },
        ],
      },
      title: '',
      dialogFormVisible: false,
      id: "",
      isDisabled: false,
      loading: false
    }
  },
  created() {
    this.getAdmdvs();
  },
  methods: {
    showDia(row) {
      this.loading = false ;
      console.log(row)
      if (!row) {
        this.title = '添加';
        this.id="" ;
        this.isDisabled=false ;
      } else {
        this.id = row.id ;
        this.title = '编辑';
        this.isDisabled=true ;
        this.getInfo(this.id) ;
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
      if (that.loading) {
        return
      }
      that.$refs['form'].validate(async (valid) => {
        if (valid) {
          that.loading = true ;
          // 动画关闭需要一定的时间
          setTimeout(() => {
            this.loading = false
          }, 1000)
          if(that.id != ''){
            updateAu(that.form).then((res) => {
              if(res.code == 0){
                that.$emit('fetch-data')
              }else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          }else {
            addAu(that.form).then((res) => {
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
    //获取统筹区
    async getAdmdvs() {
      const res = await getDicts({"type": "ADMDVS"});
      if (res.code == "0") {
        this.admdvs = res.data;
      }
    },
    getInfo(id){
      var that = this ;
      info(id).then((res) => {
        if(res.code == 0){
          that.form = res.data ;
        }
      })

    }
  },
}
</script>
