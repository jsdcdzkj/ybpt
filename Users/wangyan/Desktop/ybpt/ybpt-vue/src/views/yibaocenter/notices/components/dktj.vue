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
          <el-form-item label="审核通过数量" prop="bankName" class="custemitem">
            <el-input
              v-model.trim="form.passQuantity"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="审核驳回数量" prop="bankNo"  class="custemitem">
            <el-input
                    :disabled="isDisabled"
              v-model.trim="form.numberOfRejections"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="放款总额" prop="emp_address" class="custemitem">
            <el-input
              v-model.trim="form.loanAmount"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <!--<div slot="footer" class="dialog-footer">-->
      <!--<el-button @click="close">取 消</el-button>-->
      <!--<el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>-->
    <!--</div>-->
  </el-dialog>
</template>

<script>
import { bankTj } from '@/api/loan.js'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
import {setNoticeIsReadApi} from "@/api_check/notice";
export default {
  name: 'UserManagementEdit',
  data() {
    return {
      options: regionDataPlus,
      form: {
        passQuantity:'',
        numberOfRejections:'',
        loanAmount:'',
      },
      admdvs: [],
      title: '',
      dialogFormVisible: false,
      id: "",
      isDisabled: false,
      loading: false
    }
  },
  created() {

  },
  methods: {
    showDia(row) {
        debugger
      this.loading = false;
      if (!row) {
        this.title = '添加';
        this.id="" ;
        this.isDisabled=false ;
      } else {
        this.id = row.id ;
        this.title = '贷款统计';
        this.isDisabled=true ;
        this.getInfo(row.bankNo) ;
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
            addBank(that.form).then((res) => {
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
    getInfo(id){
      var that = this ;
      bankTj(id).then((res) => {
        if(res.code == 0){
          that.form = res.data ;
        }
      })

    }
  },
}
</script>
