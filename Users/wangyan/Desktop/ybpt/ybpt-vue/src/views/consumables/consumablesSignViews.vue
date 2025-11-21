<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="860px"
      @close="close"
      :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" label-width="134px">
      <el-row :gutter="20">
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="现27位国家编码" prop="mcs_code_new">
            <el-input
                v-model.trim="form.mcs_code_new"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="原27位国家编码" prop="mcs_code">
            <el-input
                v-model.trim="form.mcs_code"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12" class="custemitem">
          <el-form-item label="国家编码调整变动时间" prop="mcs_code_time">
            <el-date-picker
                v-model="form.mcs_code_time"
                type="date"
                format="yyyyMMdd"
                value-format="yyyyMMdd"
                disabled
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="注册备案号" prop="reg_fil_no">
            <el-input
                v-model.trim="form.reg_fil_no"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="注册备案产品名称" prop="reg_fil_prod_name">
            <el-input
                v-model.trim="form.reg_fil_prod_name"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="单件产品名称" prop="name_individual_product">
            <el-input
                v-model.trim="form.name_individual_product"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="生产企业" prop="mcs_entp">
            <el-input
                v-model.trim="form.mcs_entp"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="规格" prop="spec">
            <el-input
                v-model.trim="form.spec"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="型号" prop="mol">
            <el-input
                v-model.trim="form.mol"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="省平台挂网编码" prop="product_num">
            <el-input
                v-model.trim="form.product_num"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="定点机构联系人" prop="fixmedins_contacts">
            <el-input
                v-model.trim="form.fixmedins_contacts"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="定点机构联系电话" prop="fixmedins_phone">
            <el-input
                v-model.trim="form.fixmedins_phone"
                autocomplete="off"
                disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="申诉说明" prop="verify_reason">
            <el-input
                v-model.trim="form.verify_reason"
                autocomplete="off"
                type="textarea"
                disabled
                :rows="5"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-show="form.reason != '' && form.status == 2">
          <el-form-item label="驳回原因" prop="reason">
            <el-input
                v-model.trim="form.reason"
                autocomplete="off"
                type="textarea"
                placeholder=""
                disabled
                :rows="5"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
    </div>
  </el-dialog>
</template>

<script>

export default {
  name: 'UserManagementEdit',
  data() {
    return {
      loading: false,
      title: '',
      form: '',
      dialogFormVisible: false,
    }
  },
  created() {
  },
  methods: {
    showDia(row) {
      this.title = '详情'
      this.form = Object.assign({}, row)
      this.dialogFormVisible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
  },
}
</script>
