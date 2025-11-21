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

      <el-form-item label="项目编码">
        <el-input v-model.trim="form.project_code" placeholder="请选择" @click.native="openwin(form)"
                  readonly>
          <el-button
              slot="append"
              icon="el-icon-search"
              @click="openwin"
          ></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="项目名称" prop="projectName" readonly>
        <el-input v-model="form.project_name" placeholder="请输入" autocomplete="off" readonly></el-input>
      </el-form-item>
      <el-form-item label="计价单位" prop="unit" readonly>
        <el-input v-model="form.unit" placeholder="请输入" autocomplete="off" readonly></el-input>
      </el-form-item>
      <el-form-item label="儿童限价" prop="child_price" class="custemitem">
        <el-radio-group v-model="form.child_price" @input="handleChildPrice(form.child_price)">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="价格" prop="price">
        <el-input v-model.trim="form.price" placeholder="请输入" type="number" autocomplete="off" min="0"
                  @input="handlePrice(form.price)"></el-input>
      </el-form-item>
      <el-form-item label="费用类型" prop="cost_type" class="custemitem">
        <el-select v-model="form.cost_type" filterable style="width: 100%">
          <el-option v-for="(item, index) in options" :key="index" :label="item.label"
                     :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="本市同级公立医疗机构价格" prop="org_price" class="custemitem">
        <el-input v-model.trim="form.org_price" placeholder="请输入" type="number" autocomplete="off" readonly></el-input>
      </el-form-item>
      <el-form-item label="是否高于公立医疗机构价格" prop="high_price" class="custemitem">
        <el-radio-group v-model="form.high_price">
          <el-radio :label="0" disabled>否</el-radio>
          <el-radio :label="1" disabled>是</el-radio>
          <el-radio :label="-1" disabled>无</el-radio>
        </el-radio-group>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>

    <medicinal ref="medicinal" @fetch-data="fetchData"></medicinal>

  </el-dialog>
</template>

<script>
import Medicinal from './medicinal'
import {verifySbZlProject} from "@/api/drug";


export default {
  name: 'examine2',
  components: {
    Medicinal
  },
  data() {
    return {
      options: [
        {label: "11 一般医疗服务", value: "11 一般医疗服务"},
        {label: "12 一般检查治疗", value: "12 一般检查治疗 "},
        {label: "13 社区卫生及预防保健项目", value: "13 社区卫生及预防保健项目"},
        {label: "14 其它医疗服务项目", value: "14 其它医疗服务项目 "},
        {label: "15 非医疗服务项目", value: "15 非医疗服务项目 "},
        {label: "16 家庭医生签约服务费", value: "16 家庭医生签约服务费 "},
        {label: "17 特需服务项目", value: "17 特需服务项目"},
        {label: "21 医学影像", value: "21 医学影像"},
        {label: "22 超声检查", value: "22 超声检查"},
        {label: "23 核医学", value: "23 核医学"},
        {label: "24 放射治疗", value: "24 放射治疗"},
        {label: "25 检验", value: "25 检验"},
        {label: "26 血型与配血", value: "26 血型与配血"},
        {label: "27 病理检查", value: "27 病理检查 "},
        {label: "31 临床各系统诊疗", value: "31 临床各系统诊疗"},
        {label: "32 经血管介入诊疗", value: "32 经血管介入诊疗"},
        {label: "33 手术治疗", value: "33 手术治疗"},
        {label: "34 物理治疗与康复", value: "34 物理治疗与康复"},
        {label: "36 疼痛诊疗类", value: "36 疼痛诊疗类"},
        {label: "41 中医外治", value: "41 中医外治"},
        {label: "42 中医骨伤", value: "42 中医骨伤"},
        {label: "43 针刺", value: "43 针刺"},
        {label: "44 灸法", value: "44 灸法"},
        {label: "45 推拿疗法", value: "45 推拿疗法"},
        {label: "46 中医肛肠", value: "46 中医肛肠"},
        {label: "47 中医特殊疗法", value: "47 中医特殊疗法"},
        {label: "48 中医综合", value: "48 中医综合"},
        {label: "70 单病种项目", value: "70 单病种项目"},
        {label: "7 日间手术病", value: "7 日间手术病"},
      ],
      form: {
        project_code: '',
        project_name: '',
        unit: '',
        price: '',
        org_price: '',
        high_price: '',
        child_price: '',
        aggrement_lv: '',
        sign: ''
      },
      rules: {
        'price': [{required: true, trigger: 'blur', message: '请输入价格'}],
        'child_price': [{required: true, trigger: 'blur', message: '请确认儿童限价'}],
        'cost_type': [{required: true, trigger: 'blur', message: '请选择费用类型'}],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {
  },
  methods: {
    showEdit(row) {
      if (!row.project_code) {
        this.title = '添加'
        this.form.project_code = ''
        this.form.project_name = ''
        this.form.unit = ''
        this.form.org_price = ''
        this.form.nonChildOne = ''
        this.form.nonChildTwo = ''
        this.form.nonChildThree = ''
        this.form.childOne = ''
        this.form.childTwo = ''
        this.form.childThree = ''
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
      }
      this.form.index_natures = row.index_natures
      this.form.index_type = row.index_type
      this.form.aggrement_lv = row.aggrement_lv
      this.form.detail_status = 0
      this.dialogFormVisible = true
    },
    openwin(form) {
      form.index_type = this.form.index_type
      form.index_natures = this.form.index_natures
      this.$refs['medicinal'].showDia(form)
    },
    handlePrice() {
      if (this.form.org_price == undefined || this.form.org_price == null || this.form.org_price == '') {
        this.form.high_price = -1
        return
      }
      if (Number(this.form.price) > this.form.org_price) {
        this.form.high_price = 1
      } else {
        this.form.high_price = 0
      }
    },
    handleChildPrice(childPrice) {
      if (childPrice == 0) {
        if (this.form.aggrement_lv == '1' || this.form.aggrement_lv == '9' || this.form.aggrement_lv == '') {
          this.form.org_price = this.form.nonChildOne
        } else if (this.form.aggrement_lv == '2') {
          this.form.org_price = this.form.nonChildTwo
        } else if (this.form.aggrement_lv == '3') {
          this.form.org_price = this.form.nonChildThree
        }
      } else if (childPrice == 1) {
        if (this.form.aggrement_lv == '1' || this.form.aggrement_lv == '9' || this.form.aggrement_lv == '') {
          this.form.org_price = this.form.childOne
        } else if (this.form.aggrement_lv == '2') {
          this.form.org_price = this.form.childTwo
        } else if (this.form.aggrement_lv == '3') {
          this.form.org_price = this.form.childThree
        }
      }
      this.handlePrice()
    },
    close() {
      this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    fetchData(row) {
      verifySbZlProject({project_code: row.provincialProjectCode, type: this.form.index_type}).then((res) => {
        if (res.code == 0) {
          if (res.data != "") {
            this.$baseMessage(res.data, 'error')
            return;
          } else {

            this.form.project_code = row.provincialProjectCode
            this.form.project_name = row.directoryName
            this.form.unit = row.chargeUnit
            this.form.detail_status = 0
            this.form.childOne = row.childOne
            this.form.childThree = row.childThree
            this.form.childTwo = row.childTwo
            this.form.nonChildOne = row.nonChildOne
            this.form.nonChildThree = row.nonChildThree
            this.form.nonChildTwo = row.nonChildTwo
            if (this.form.child_price == '0' || this.form.child_price == '1') {
              this.handleChildPrice(this.form.child_price)
            }

          }
        } else {
          // that.dialog = false
          this.$baseMessage(res.msg, 'error')
        }
      })


      // var code  = this.form.project_code.substring(0,2);
      // for(var i = 0;i<this.options.length;i++){
      //   if(this.options[i].value.replace(/[^0-9]/g,"") == code){
      //     console.log(this.options[i].value)
      //     this.form.cost_type = this.options[i].value
      //   }
      // }

    },
    save() {
      if (this.title == '添加') {
        this.form.sign = 'add'
      }
      if (this.title == '编辑') {
        this.form.sign = 'edit'
      }
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.$emit('fetch-data', this.form)
          this.close()
        } else {
          return false
        }
      })
    },
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
