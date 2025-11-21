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
        <el-col :xs="24" :sm="24" :md="14" :lg="14" :xl="14">
          <el-form-item label="机构类型" prop="user_type">
            <el-select v-model.trim="form.user_type" class="w" @change="dropDown">
              <el-option value="2" label="定点医疗机构"></el-option>
              <el-option value="3" label="定点零售药店"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="20" :lg="20" :xl="20">
          <el-form-item label="机构编码" prop="fixmedins_code">
            <el-input v-model="form.fixmedins_code" @click.native="openwin">
              <el-button slot="append" icon="el-icon-search" @click="openwin"></el-button>
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="20" :lg="20" :xl="20">
          <el-form-item label="机构名称" prop="fixmedins_name">
            <el-input v-model="form.fixmedins_name" readonly="">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
    </div>
    <hospital ref="userhospital" @fetch-data="hospital"></hospital>
  </el-dialog>
</template>

<script>
import { updateFileDelivery } from '@/api/fileDelivery.js'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
import {getDicts} from '@/api/dictManagement'
import Hospital from '@/components/userhospital'

export default {
  name: 'UserManagementEdit',
  components: {Hospital},
  data() {
    return {
      options: regionDataPlus,
      form: {
        fixmedins_code:"",
        fixmedins_name:"",
        user_type:"",
        id:""
      },
      admdvs: [],
      rules: {
        user_type: [
          { required: true, trigger: 'blur', message: '请输入' },
        ],
        fixmedins_code: [{ required: true, trigger: 'submit', message: '请输入' }],
      },
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
      this.loading = false ;
      if (!row) {
        this.title = '添加';
        this.id="" ;
        this.isDisabled=false ;
      } else {
        this.form.id = row.id ;
        this.title = '编辑';
        this.isDisabled=true ;

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
          if(that.form.id != ''){
            updateFileDelivery(that.form).then((res) => {
              if(res.code == 0){
                that.$emit('fetch-data')
              }else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          }else {

          }
          // that.$baseMessage(msg, 'success')

          that.close()
        } else {
          return false
        }
      })
    },
    openwin() {
      var that = this
      if (
              that.form.user_type == '' ||
              that.form.user_type == null ||
              that.form.user_type == undefined
      ) {
        that.$baseMessage('请先选择机构类型', 'error')
      } else {
        that.$refs['userhospital'].showDia(that.form.user_type)
      }
    },
    hospital(data) {
      var that = this
      that.form.fixmedins_code = data.medins_code  ;
      that.form.fixmedins_name = data.medins_name ;
      console.log(that.form.org_code);
    },
  },
}
</script>
