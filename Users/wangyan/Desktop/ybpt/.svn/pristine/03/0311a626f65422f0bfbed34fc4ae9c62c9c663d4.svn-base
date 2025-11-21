<template>
  <el-drawer
    :title="title"
    :before-close="handleClose"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="70%"
    ref="drawer"
  >
    <div class="drawer_content">
      <el-form :model="form"   :label-width="formLabelWidth" :rules="rules" ref="form">
        <div class="drawer_main">
          <div class="box_card">
            <div class="box_header">
              <span>登记病种</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="登记病种" prop="dise_name">
                    <!--<el-input v-model.trim="form.dise_name" placeholder="请选择" @click.native="openwin('1')" readonly na>-->
                      <!--<el-button-->
                        <!--slot="append"-->
                        <!--icon="el-icon-search"-->
                         <!--@click="openwin('1')"-->
                      <!--&gt;</el-button>-->
                    <!--</el-input>-->
                    <el-input
                            v-model.trim="form.dise_name"
                            placeholder="病种名称"
                            autocomplete="off"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="登记病种代码" prop="dise_code" class="custemitem">
                    <el-input
                      v-model.trim="form.dise_code"
                      placeholder="病种代码"             
                      autocomplete="off"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="参保类型" prop="insured_type">
                   <el-select v-model.trim="form.insured_type" class="w">
                     <!--<el-option v-for="item in insuTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"-->
                                <!--:value="item.nat_dic_val_code">-->
                     <!--</el-option>-->
                     <el-option label="外国人永久居留证" value="7"></el-option>

                   </el-select>
                    <el-option></el-option>
                  </el-form-item>
                </el-col>
                
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>互斥病种</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="互斥病种" prop="dise_mutex_name">
                    <!--<el-input v-model.trim="form.dise_mutex_name" placeholder="请选择" @click.native="openwin('2')" readonly>-->
                      <!--<el-button-->
                        <!--slot="append"-->
                        <!--icon="el-icon-search"-->
                         <!--@click="openwin('2')"-->
                      <!--&gt;</el-button>-->
                    <!--</el-input>-->
                    <el-input
                            v-model.trim="form.dise_mutex_name"
                            autocomplete="off"
                            placeholder="病种代码"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="互斥病种代码" prop="dise_mutex_code" class="custemitem">
                    <el-input
                      v-model.trim="form.dise_mutex_code"
                      autocomplete="off"
                      placeholder="病种代码"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="参保类型" prop="insured_mutex_type">
                   <el-select v-model.trim="form.insured_mutex_type" class="w">
                     <!--<el-option v-for="item in insuTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"-->
                                <!--:value="item.nat_dic_val_code">-->
                     <!--</el-option>-->
                     <el-option label="外国人永久居留证" value="7"></el-option>

                   </el-select>
                  </el-form-item>
                </el-col>
                
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
<!--        <el-button @click="cancelForm">重 置</el-button>-->
        <el-button
          type="primary"
          @click="save"
          :loading="loading"
        >
          {{ loading ? '提交中 ...' : '提 交' }}
        </el-button>
      </div>
    </div>
    <diseasetype ref="diseasetype" @fetch-data="getSelect"></diseasetype>
  </el-drawer>
</template>

<script>
import Diseasetype from './diseasetype'
import {getDiseList, saveDiseaseMutex} from '@/api/mmmtDisease'
import { upData } from '@/api/common.js'
export default {
  name: 'edit',
  components: { Diseasetype },
  data() {
    return {
      title: '',
      dialog: false,
      loading: false,
      type:"",
      id:"",
      form: {
        dise_code:"",
        dise_name:"",
        insured_type:"",
        dise_mutex_code:"",
        dise_mutex_name:"",
        insured_mutex_type:"",
        type:"2",
      },
      formLabelWidth: '100px',
      timer: null,
      insuTypes:[],
      rules: {
        dise_name: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
        dise_code: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
        dise_mutex_name: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
        dise_mutex_code: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
        insured_type: [{required: true, trigger: 'submit', message: '请输入正确的信息'}],
        insured_mutex_type: [{required: true, trigger: 'submit', message: '请输入正确的信息'}],
      },
    }
  },
  mounted() {},
  created() {
    this.getInsuType() ;

  },
  methods: {
    showDia(row) {

      if (!row) {
        this.title = '新增'
        this.form.dise_code = "" ;
        this.form.dise_name = "" ;
        this.form.insured_type = "" ;
        this.form.dise_mutex_code = "" ;
        this.form.dise_mutex_name = "" ;
        this.form.insured_mutex_type = "" ;
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
        console.log(row);
        this.id = row.id ;
      }
      this.dialog = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialog = false
    },
    async save() {
      var that = this ;

      that.$refs['form'].validate(async (valid) => {
        if (valid) {
          that.form.id = that.id ;
          const data = await saveDiseaseMutex(that.form)
          if (data.code == 0) {
            that.$baseMessage('保存成功', 'success')
            that.$emit('fetch-data')
            that.close()
          } else {
            that.$baseMessage(data.msg, 'error')
          }


        }
      });






    },
    openwin(type){
      this.type=type;
      this.$refs['diseasetype'].showDia()
    },
    getSelect(row){
      console.info(this.type);
      if(this.type == '1'){
        this.form.dise_code=row.diseCode;
        this.form.dise_name=row.diseName;
      }else if(this.type == '2'){
        this.form.dise_mutex_code=row.diseCode;
        this.form.dise_mutex_name=row.diseName;
      }
    },
    handleClose(done) {
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)

    },
    getInsuType() {
      var that = this ;
      upData('INSUTYPE').then((res) => {
        if(res.code == 0){
          that.insuTypes = res.data ;
        }
      })
    },
  },
}
</script>