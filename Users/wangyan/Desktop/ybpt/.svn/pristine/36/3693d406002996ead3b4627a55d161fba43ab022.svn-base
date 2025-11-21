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

      <el-form-item label="编码" prop="project_code">
        <el-input v-model.trim="form.project_code" placeholder="请选择" @click.native="openwin(form.project_code,form.drugs)" readonly>
          <el-button
              slot="append"
              icon="el-icon-search"
              @click="openwin"
          ></el-button>
        </el-input>
      </el-form-item>
      <el-form-item label="名称" prop="project_name" readonly>
        <el-input v-model="form.project_name" placeholder="请输入" autocomplete="off" readonly></el-input>
      </el-form-item>
      <el-form-item label="单位" prop="unit" readonly>
        <el-input v-model="form.unit" placeholder="请输入" autocomplete="off" readonly></el-input>
      </el-form-item>
      <el-form-item label="实际销售价格" prop="sale_price">
        <el-input v-model.trim="form.sale_price" placeholder="请输入" type="number" autocomplete="off" min="0"></el-input>
      </el-form-item>
      <el-form-item label="实际采购价格" prop="purchase_price">
        <el-input v-model.trim="form.purchase_price" placeholder="请输入" type="number" autocomplete="off" min="0"></el-input>
      </el-form-item>
      <el-form-item label="加成率" prop="rate">
        <el-input v-model="form.rate" placeholder="请输入" type="number" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="公立医疗机构价格" prop="org_price" class="custemitem" >
        <el-input v-model="form.org_price" placeholder="" type="number" autocomplete="off" min="0" readonly = ture ></el-input>
      </el-form-item>
      <el-form-item label="是否在省平台上采购" prop="is_purchase" class="custemitem">
        <el-radio-group v-model="form.is_purchase">
          <el-radio :label="0">否</el-radio>
          <el-radio :label="1">是</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="备注" prop="remark">
        <el-input v-model="form.remark" placeholder="请输入" autocomplete="off"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>

    <chinesePatentMedicine ref="chinesePatentMedicine" @fetch-data="fetchData"></chinesePatentMedicine>
    <westernMedicine ref="westernMedicine" @fetch-data="fetchData"></westernMedicine>

  </el-dialog>
</template>

<script>
import ChinesePatentMedicine from './chinesePatentMedicine'
import WesternMedicine from './westernMedicine'


export default {
  name: 'examine',
  components: {
    ChinesePatentMedicine,
    WesternMedicine,
  },
  data() {
    return {
      form: {
        project_code: '',
        project_name: '',
        unit: '',
        sale_price: '',
        purchase_price: '',
        rate: '',
        org_price: '',
        is_purchase: '',
        remark: '',
        drugs: '',
        sign: '',
      },
      rules: {
        'sale_price': [{required: true, trigger: 'blur', message: '请输入实际销售价格'}],
        'purchase_price': [{required: true, trigger: 'blur', message: '请输入实际采购价格'}],
        'rate': [{required: true, trigger: 'blur', message: '请输入加成率'}],
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
        this.form.purchase_price=''
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
      }
      this.form.drugs = row.drugs
      this.form.detail_status = 0
      this.dialogFormVisible = true
    },
    openwin(project_code,drugs) {
      if(drugs == 1){// 西药
        this.$refs['westernMedicine'].showDia(project_code)
      }else if(drugs == 2){//中成药
        this.$refs['chinesePatentMedicine'].showDia(project_code)
      }
    },
    close() {
      this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    fetchData(row) {
      this.form.project_code = row.nationalDrugCode
      this.form.project_name = row.drugNames
      this.form.unit = row.unit
      this.form.org_price = row.purchaseCeilingPrice
      this.form.detail_status = 0
    },
    save() {
      if (this.title == '添加') {
        this.form.sign = 'add'
      }
      if (this.title == '编辑') {
        this.form.sign = 'edit'
      }
      if(Number(this.form.sale_price) > Number(this.form.purchase_price) && Number(this.form.rate) == 0){
        this.$baseMessage('销售价格高于采购价格,加成率不能为0', 'error')
        return
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