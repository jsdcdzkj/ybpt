<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="500px"
      @close="close"
      append-to-body
      :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="120px">


      <el-form-item label="项目名称" prop="project_name">
        <el-input v-model="form.project_name" placeholder="请输入" autocomplete="off"></el-input>
      </el-form-item>

      <!--<el-form-item label="二级分类编码" prop="two_code">-->
        <!---->
      <!--</el-form-item>-->
      <el-form-item label="费用类型" prop="type">
        <el-select v-model="form.type" clearable @change="getLabel"
                   style="width: 100%">
          <el-option v-for="item in codeNumber" :key="item.value" :label="item.label"
                     :value="item.value" >
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="项目编码后四位" prop="fourcode">
        <el-input  show-word-limit v-model="form.fourcode" placeholder="请输入" autocomplete="off" type="number"></el-input>
      </el-form-item>

      <!--<el-form-item label="项目编码" prop="project_code">-->
        <!--<el-input v-model="form.project_code" placeholder="请输入" autocomplete="off"></el-input>-->
      <!--</el-form-item>-->
      <el-form-item label="项目内涵" prop="project_content">
        <el-input v-model="form.project_content" placeholder="请输入" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="除外内容" prop="except_content">
        <el-input v-model="form.except_content" placeholder="请输入" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="计价单位" prop="unit">
        <el-input v-model="form.unit" placeholder="请输入" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="说明" prop="explain" class="custemitem">
        <el-input v-model="form.explain" placeholder="请输入" type="textarea" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="是否儿童" prop="child_or_not" class="custemitem">
        <el-radio-group v-model="form.child_or_not">
          <el-radio label='1'>是</el-radio>
          <el-radio label='0'>否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input v-model.trim="form.price" placeholder="请输入" type="number" autocomplete="off" min="0"></el-input>
      </el-form-item>

      <el-form-item label="是否在国家和省医疗服务价格项目规范之内" class="custemitem" prop="service_price">
        <el-radio-group v-model="form.service_price">
          <el-radio label='1'>是</el-radio>
          <el-radio label='0'>否</el-radio>
        </el-radio-group>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {getDicts} from "@/api/dictManagement";

export default {
  name: 'examine4',
  data() {
    return {
      form: {
        service_price: "",
        child_or_not: "",
        fourcode: "",
        price: "",
        project_name: "",
        project_code: "",
        two_code: "",
        project_content: "",
        except_content: "",
        unit: "",
        explain: "",
        type: "",
      },
      rules: {

        fourcode: [{required: true, trigger: 'blur', message: '请输入'},{ max: 4,min: 4, trigger: 'blur', message: '请输入4位编码' },],
        child_or_not: [{required: true, trigger: 'blur', message: '请输入'}],
        // two_code: [{required: true, trigger: 'blur', message: '请输入'}],
        project_name: [{required: true, trigger: 'blur', message: '请输入'}],
        project_content: [{required: false, trigger: 'blur', message: '请输入'}],
        except_content: [{required: false, trigger: 'blur', message: '请输入'}],
        unit: [{required: true, trigger: 'blur', message: '请输入'}],
        explain: [{required: false, trigger: 'blur', message: '请输入'}],
        price: [{required: true, trigger: 'blur', message: '请输入'}],
        type: [{required: true, trigger: 'blur', message: '请输入'}],
        service_price: [{required: true, message: '请选择', trigger: 'change'}],
      },
      title: '',
      dialogFormVisible: false,
      sign: '',
      codeNumber: [],
    }
  },
  created() {
  },
  methods: {
    showEdit(row) {
      this.getCodeNumber() ;
      if (!row) {
        this.title = '添加'
        this.form.service_price = ""
        this.form.child_or_not= "",
                this.form.fourcode= "",
                this.form.price= "",
                this.form.project_name= "",
                this.form.project_code= "",
                this.form.project_content= "",
                this.form.except_content= "",
                this.form.unit= ""
        this.form.explain= "",
                this.form.type= ""
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
      }
      this.form.detail_status = 0
      this.dialogFormVisible = true
    },

    close() {
      this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          if (this.title == '添加') {
            this.form.sign = 'add'
          }
          if (this.title == '编辑') {
            this.form.sign = 'edit'
          }
          if(this.form.is_write == null && this.form.is_write == undefined && this.form.is_write== ""){
            this.form.is_write = 0 ;
          }
          this.$emit('fetch-data', this.form)
          this.close()
        } else {
          return false
        }
      })
    },
    async getCodeNumber() {
      const res = await getDicts({"type": "sortingCodeNumber"});
      if (res.code == "0") {
        this.codeNumber = res.data;
      }
    },
    getLabel(value) {
      let opt= {};
      opt= this.codeNumber.find((item)=>{
        return item.value === value;
      });
      console.log(opt.label);
      this.form.typeName = opt.label
    }
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .custemitem {
    label {
      line-height: 16px !important;
    }
  }
}
</style>
