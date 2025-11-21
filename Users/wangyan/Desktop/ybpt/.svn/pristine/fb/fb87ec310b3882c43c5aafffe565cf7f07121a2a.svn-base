<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="860px"
      @close="close"
      :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="134px">
      <el-row :gutter="20">
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-tooltip class="item" effect="dark" content="填报最新版医保耗材收费标识库中的“现27位码”" placement="top-start">
			  <el-form-item label="现27位国家编码" prop="mcs_code_new">
				<el-input
					v-model.trim="form.mcs_code_new"
					autocomplete="off"
					placeholder="必填"
				></el-input>
			  </el-form-item>
			</el-tooltip>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-tooltip class="item" effect="dark" content="填报前20位在《江苏省医保耗材分类与代码映射样本V2.1》的27位国家编码" placement="top-start">
			  <el-form-item label="原27位国家编码" prop="mcs_code">
			    <el-input
			        v-model.trim="form.mcs_code"
			        autocomplete="off"
			        placeholder="必填"
			    ></el-input>
			  </el-form-item>
			</el-tooltip>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12" class="custemitem">
			<el-tooltip class="item" effect="dark" content="填报国家医保局发布版本时间，如：20220419" placement="top-start">
			  <el-form-item label="国家编码调整变动时间" prop="mcs_code_time">
			    <el-date-picker
			        v-model="form.mcs_code_time"
			        type="date"
			        format="yyyyMMdd"
			        value-format="yyyyMMdd"
			        placeholder="必填"
			    ></el-date-picker>
			  </el-form-item>
			</el-tooltip>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-tooltip class="item" effect="dark" content="填报最新版医保耗材收费标识库中的相关信息" placement="top-start">
			  <el-form-item label="注册备案号" prop="reg_fil_no">
			    <el-input
			        v-model.trim="form.reg_fil_no"
			        autocomplete="off"
			        placeholder="必填"
			    ></el-input>
			  </el-form-item>
			</el-tooltip>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-tooltip class="item" effect="dark" content="填报最新版医保耗材收费标识库中的相关信息" placement="top-start">
			  <el-form-item label="注册备案产品名称" prop="reg_fil_prod_name">
			    <el-input
			        v-model.trim="form.reg_fil_prod_name"
			        autocomplete="off"
			        placeholder="必填"
			    ></el-input>
			  </el-form-item>
			</el-tooltip>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-tooltip class="item" effect="dark" content="填报最新版医保耗材收费标识库中的相关信息" placement="top-start">
			  <el-form-item label="单件产品名称" prop="name_individual_product">
			    <el-input
			        v-model.trim="form.name_individual_product"
			        autocomplete="off"
			        placeholder="必填"
			    ></el-input>
			  </el-form-item>
			</el-tooltip>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-tooltip class="item" effect="dark" content="填报最新版医保耗材收费标识库中的相关信息" placement="top-start">
			  <el-form-item label="生产企业" prop="mcs_entp">
			    <el-input
			        v-model.trim="form.mcs_entp"
			        autocomplete="off"
			        placeholder="必填"
			    ></el-input>
			  </el-form-item>
			</el-tooltip>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-tooltip class="item" effect="dark" content="填报最新版医保耗材收费标识库中的相关信息" placement="top-start">
			  <el-form-item label="规格" prop="spec">
			    <el-input
			        v-model.trim="form.spec"
			        autocomplete="off"
			        placeholder="必填"
			    ></el-input>
			  </el-form-item>
			</el-tooltip>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-tooltip class="item" effect="dark" content="填报最新版医保耗材收费标识库中的相关信息" placement="top-start">
			  <el-form-item label="型号" prop="mol">
			    <el-input
			        v-model.trim="form.mol"
			        autocomplete="off"
			        placeholder="必填"
			    ></el-input>
			  </el-form-item>
			</el-tooltip>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			  <el-form-item label="省平台挂网编码" prop="product_num">
			    <el-input
			        v-model.trim="form.product_num"
			        autocomplete="off"
			        placeholder="非必填"
			    ></el-input>
			  </el-form-item>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			  <el-form-item label="定点机构联系人" prop="fixmedins_contacts">
			    <el-input
			        v-model.trim="form.fixmedins_contacts"
			        autocomplete="off"
			        placeholder="必填"
			    ></el-input>
			  </el-form-item>
          
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			  <el-form-item label="定点机构联系电话" prop="fixmedins_phone">
			    <el-input
			        v-model.trim="form.fixmedins_phone"
			        autocomplete="off"
			        placeholder="必填"
			    ></el-input>
			  </el-form-item>
          
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			
			  <el-form-item label="申诉说明" prop="verify_reason">
				  <el-tooltip class="item" effect="dark" content="简要描述申诉情形" placement="top-start">
					<el-input
						v-model.trim="form.verify_reason"
						autocomplete="off"
						type="textarea"
						placeholder="必填"
						:rows="5"
					></el-input>
				  </el-tooltip>
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
import {insertOrUpdate} from '@/api/consumables'

export default {
  name: 'UserManagementEdit',
  data() {
    return {
      loading: false,
      setSelectRows(val) {
        this.selectRows = val
      },
      form: {
        mcs_code_new: '',
        mcs_code: '',
        mcs_code_time: '',
        reg_fil_no: '',
        reg_fil_prod_name: '',
        name_individual_product: '',
        mcs_entp: '',
        spec: '',
        mol: '',
        verify_reason: '',
        fixmedins_contacts: '',
        fixmedins_phone: '',
      },
      rules: {
        mcs_code_new: [
          {required: true, trigger: 'blur', message: '请输入现27位国家编码'},
          {required: true, trigger: 'blur', pattern: /^.{27}$/, message: '现27位国家编码为27位'},
        ],
        mcs_code: [
          {required: true, trigger: 'blur', message: '请输入原27位国家编码'},
          {required: true, trigger: 'blur', pattern: /^.{27}$/, message: '耗材国家编码为27位'},
        ],
        mcs_code_time: [{required: true, trigger: 'blur', message: '请选择国家编码调整变动时间'}],
        reg_fil_no: [{required: true, trigger: 'blur', message: '请输入注册备案号'}],
        reg_fil_prod_name: [{required: true, trigger: 'blur', message: '请输入注册备案产品名称'}],
        name_individual_product: [{required: true, trigger: 'blur', message: '请输入注册单件产品名称'}],
        mcs_entp: [{required: true, trigger: 'blur', message: '请输入生产企业'}],
        spec: [{required: true, trigger: 'blur', message: '请输入规格'}],
        mol: [{required: true, trigger: 'blur', message: '请输入型号'}],
        verify_reason: [{required: true, trigger: 'blur', message: '请输入申诉说明'}],
        fixmedins_contacts: [{required: true, trigger: 'blur', message: '请输入定点机构联系人'}],
        fixmedins_phone: [
          {required: true, trigger: 'blur', message: '请输入定点机构联系电话'},
          {required: true, trigger: 'blur', pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误'}
        ],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {
  },
  methods: {
    showDia(row, employingInfo) {
      if (!row) {
        this.title = '添加'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      if (this.loading) {
        return
      }
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.loading = true
          // 动画关闭需要一定的时间
          setTimeout(() => {
            this.loading = false
          }, 400)
          const {code, msg} = await insertOrUpdate(this.form)
          if (code == -1) {
            this.$baseMessage(msg, 'error')
          } else {
            this.$baseMessage(msg, 'success')
          }
          this.$emit('fetch-data')
          this.close()
        } else {
          return false
        }
      })
    },
  },
}
</script>
