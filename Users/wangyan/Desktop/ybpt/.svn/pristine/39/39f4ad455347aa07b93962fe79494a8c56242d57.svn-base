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
      <el-form-item label="药品(耗材)名称" prop="name" class="custemitem">
        <el-input v-model="form.name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="规格" prop="unit" class="custemitem">
        <el-input v-model="form.unit" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="省阳光采购平台产品编码" prop="code" class="custemitem">
        <el-input v-model="form.code" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="缺货开始采购日期" prop="start_date" class="custemitem">
        <el-date-picker type="date" v-model="form.start_date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                        autocomplete="off"></el-date-picker>
      </el-form-item>
      <el-form-item label="缺货后最近一次采购订单日期" prop="end_date" class="custemitem">
        <el-date-picker type="date" v-model="form.end_date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                        autocomplete="off"></el-date-picker>
      </el-form-item>
      <el-form-item label="缺货订单提交次数" prop="sub_quantity" class="custemitem">
        <el-input type="number" min="0" v-model="form.sub_quantity" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="数量" prop="quantity" class="custemitem">
        <el-input type="number" min="0" v-model="form.quantity" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="金额" prop="price" class="custemitem">
        <el-input type="number" min="0" v-model="form.price" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="生产企业" prop="enterprise" class="custemitem">
        <el-input v-model="form.enterprise" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="配送公司" prop="company" class="custemitem">
        <el-input v-model="form.company" autocomplete="off"></el-input>
      </el-form-item>
<!--      <el-form-item label="线索类型" prop="note" class="custemitem">-->
<!--        <el-select v-model="form.note" filterable style="width: 100%">-->
<!--          <el-option v-for="(item, index) in options" :key="index" :label="item.label"-->
<!--                     :value="item.value">-->
<!--          </el-option>-->
<!--        </el-select>-->
<!--      </el-form-item>-->
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>


export default {
  name: 'examine',
  components: {},
  data() {
    return {
      options:[
        {label: "无正当理由，不响应采购订单", value:"无正当理由，不响应采购订单"},
        {label: "以高于挂网价线下供应或配送", value:"以高于挂网价线下供应或配送"},
        {label: "不接受网上议价，但实际供货价低于挂网价", value:"不接受网上议价，但实际供货价低于挂网价"},
        {label: "同一企业以过评后价格销售过评前同一产品", value:"同一企业以过评后价格销售过评前同一产品"},
        {label: "未按规定签订带量采购合同", value:"未按规定签订带量采购合同"},
        {label: "未按合同约定及时供应产品", value:"未按合同约定及时供应产品"},
        {label: "不按中选清单供应产品", value:"不按中选清单供应产品"},
        {label: "未按带量采购合同约定提供伴随服务", value:"未按带量采购合同约定提供伴随服务"},
      ],
      form: {
        name: '',
        unit: '',
        code: '',
        start_date: '',
        end_date: '',
        sub_quantity: '',
        quantity: '',
        price: '',
        enterprise: '',
        company: '',
        note: '',
      },
      rules: {
        'name': [{required: true, trigger: 'blur', message: '请输入名称'}],
        'unit': [{required: true, trigger: 'blur', message: '请输入规格'}],
        'code': [{required: true, trigger: 'blur', message: '请输入省阳光采购平台产品编码'}],
        'start_date': [{required: true, trigger: 'blur', message: '请选择缺货开始采购日期'}],
        'end_date': [{required: true, trigger: 'blur', message: '请选择缺货后最近一次采购订单日期'}],
        'sub_quantity': [{required: true, trigger: 'blur', message: '请输入缺货订单提交次数'}],
        'quantity': [{required: true, trigger: 'blur', message: '请输入数量'}],
        'price': [{required: true, trigger: 'blur', message: '请输入金额'}],
        'enterprise': [{required: true, trigger: 'blur', message: '请输入生产企业'}],
        'company': [{required: true, trigger: 'blur', message: '请输入配送公司'}],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {
  },
  methods: {
    showEdit(row) {
      if (!row.code) {
        this.title = '添加'
        this.form = {
          name: '',
              code: '',
              start_date: '',
              end_date: '',
              sub_quantity: '',
              quantity: '',
              price: '',
              enterprise: '',
              company: '',
              note: '',
        }
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.dialogFormVisible = false
    },
    fetchData(row) {

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
