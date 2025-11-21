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
          <el-form-item label="机构名称" prop="fixmedins_name">
            <el-input
              v-model.trim="form.fixmedins_name"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="信用编码" prop="fixmedins_code" >
            <el-input
                    :disabled="isDisabled"
              v-model.trim="form.fixmedins_code"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="行政区划" prop="fix_blng_admdvs">
            <el-select v-model.trim="form.fix_blng_admdvs" class="w">
              <el-option
                      v-for="item in admdvs"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="机构类型" prop="fixmedins_type">
            <el-select v-model.trim="form.fixmedins_type" class="w" @change="getLevelList">
              <el-option value="2" label="非定点医疗机构"></el-option>
              <el-option value="3" label="非定点零售药店"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="协议等级">
            <el-select v-model="form.aggrement_lv" style="width: 100%" @change="queryData" clearable>
              <el-option label="请选择" value=""></el-option>
              <el-option
                      v-for="item in levelList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="经营性质" prop="manage_quality">
            <el-select v-model.trim="form.manage_quality" class="w">
              <el-option value="1" label="营利性"></el-option>
              <el-option value="2" label="民办非营利"></el-option>
              <el-option value="3" label="政府非营利"></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="联系人" prop="legrep_person" >
            <el-input
                    v-model.trim="form.legrep_person"
                    autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="联系电话" prop="legrep_mobile" >
            <el-input
                    v-model.trim="form.legrep_mobile"
                    autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="地址" prop="address" >
            <el-input
                    v-model.trim="form.address"
                    autocomplete="off"
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
import { addUnfixedMechanism,updateUnfixedMechanism,info } from '@/api/fddjg'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
import {getDicts} from '@/api/dictManagement'
export default {
  name: 'UserManagementEdit',
  data() {
    return {
      aList: [
        { id: 1, name: '一级' },
        { id: 2, name: '二级' },
        { id: 3, name: '三级' },
        { id: 9, name: '未定级' },
      ],
      bList: [
        { id: 4, name: 'A级' },
        { id: 5, name: 'B级' },
        { id: 6, name: 'C级' },
      ],
      levelList: [],
      options: regionDataPlus,
      form: {
        emp_address:"",
        emp_name:"",
        emp_no:"",
        emp_type:"0",
        admdvs:"",
        emp_address:"",
        aggrement_lv:"",
      },
      admdvs: [],
      rules: {
        fixmedins_name: [
          { required: true, trigger: 'blur', message: '请输入' },
        ],
        fixmedins_code: [{ required: true, trigger: 'blur', message: '请输入' }],
        admdvs: [
          { required: false, trigger: 'blur', message: '请选择行政区划' },
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
        this.levelList = [];
        this.title = '添加';
        this.id="" ;
        this.isDisabled=false ;
      } else {
        this.id = row.id ;
        this.title = '编辑';
        this.isDisabled=true ;
        // this.getInfo(this.id) ;

        info(row.id).then((res) => {
          this.form = res.data ;
          if (res.data.fixmedins_type == 2) {
            this.levelList = this.aList;
            this.form.aggrement_lv = parseInt(res.data.aggrement_lv) ;
          } else if (res.data.fixmedins_type == 3) {
            this.levelList = this.bList;
            this.form.aggrement_lv = parseInt(res.data.aggrement_lv) ;
          }
        })
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
            updateUnfixedMechanism(that.form).then((res) => {
              if(res.code == 0){
                that.$emit('fetch-data')
                that.$baseMessage("操作成功", 'success')
              }else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          }else {
            addUnfixedMechanism(that.form).then((res) => {
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
      const res = await getDicts({"type": "admdvs-area"});
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

    },
    getLevelList() {
      var that = this ;

      if (that.form.fixmedins_type == 2) {
        that.levelList = that.aList;
        that.form.aggrement_lv ="" ;
      } else if (that.form.fixmedins_type == 3) {
        that.levelList = that.bList;
        that.form.aggrement_lv ="" ;
      } else {
        that.levelList = []
      }

      if(that.form.aggrement_lv != undefined){
        that.form.aggrement_lv ="" ;
      }
      this.queryData() ;
    },
  },
}
</script>
