<template>
  <el-dialog
	:show-close="false"
	:close-on-click-modal="false"
    :visible.sync="dialogFormVisible"
	top="0"
    width="1000px"
	append-to-body
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="110px">
	  <h4 class="inform-title">非公立医疗服务自设项目自主定价说明表</h4>
	  <el-row :gutter="20">
	  	<el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
	  		<el-form-item label="单位名称:">
				{{ form.sm.org_name }}
	  		</el-form-item>
	  	</el-col>
	  	<el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
	  		<el-form-item label="单位医保编码:">
				{{ form.sm.org_code }}
	  		</el-form-item>
	  	</el-col>
		<el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
			<el-form-item label="经营性质:">
				{{form.sm.natures}}
			</el-form-item>
		</el-col>
		<el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-form-item label="项目名称" prop="sm.project_name">
				<el-input v-model="form.sm.project_name" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<!--<el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">-->
			<!--<el-form-item label="自设编码" prop="sm.project_code">-->
				<!--<el-input v-model="form.sm.project_code" placeholder="请输入" autocomplete="off"></el-input>-->
			<!--</el-form-item>-->
		<!--</el-col>-->
		<el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-form-item label="除外内容" prop="sm.except_content">
				<el-input v-model="form.sm.except_content" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-form-item label="计价单位" prop="sm.unit">
				<el-input v-model="form.sm.unit" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
			<el-form-item label="自定价格" prop="sm.price">
				<el-input v-model="form.sm.price" type="number" min="0" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<el-form-item label="项目说明" prop="sm.explain">
				<el-input v-model="form.sm.explain" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<el-form-item label="是否为《全国医疗服务价格项目规范2012年版》项目" prop="sm2.is_norm" class="custemitem">
				<el-radio-group v-model="form.sm2.is_norm">
				    <el-radio :label="0">否</el-radio>
				    <el-radio :label="1">是</el-radio>
				  </el-radio-group>
			</el-form-item>
		</el-col>
		<el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12" v-if="form.sm2.is_norm==1">
			<el-form-item label="《全国医疗服务价格项目规范2012年版》项目编码" prop="sm2.norm_code" class="custemitem">
				<el-input v-model="form.sm2.norm_code" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12" v-if="form.sm2.is_norm==1">
			<el-form-item label="《全国医疗服务价格项目规范2012年版》项目名称" prop="sm2.norm_name" class="custemitem">
				<el-input v-model="form.sm2.norm_name" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<el-form-item label="项目内涵" prop="sm.project_content" class="custemitem">
				<el-input v-model="form.sm.project_content" type="textarea" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<el-form-item label="项目临床意义" prop="sm2.sense" class="custemitem">
				<el-input v-model="form.sm2.sense" type="textarea" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<el-form-item label="项目基本人力消耗及耗时" prop="sm2.base" class="custemitem">
				<el-input v-model="form.sm2.base" type="textarea" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<el-form-item label="项目操作规范" prop="sm2.norm" class="custemitem">
				<el-input v-model="form.sm2.norm" type="textarea" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<el-form-item label="项目适用范围及可能产生的技术风险" prop="sm2.risk" class="custemitem">
				<el-input v-model="form.sm2.risk" type="textarea" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<el-form-item label="应用单位" prop="sm2.apply_unit" class="custemitem">
				<el-input v-model="form.sm2.apply_unit" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
			<el-form-item label="结果" prop="sm2.result" class="custemitem">
				<el-input v-model="form.sm2.result" type="textarea" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
			<el-form-item label="申报科室负责人" prop="sm2.declare" class="custemitem">
				<el-input v-model="form.sm2.declare" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
			<el-form-item label="医务科室负责人" prop="sm2.matters" class="custemitem">
				<el-input v-model="form.sm2.matters" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		<el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
			<el-form-item label="价格管理科室负责人" prop="sm2.price_manager" class="custemitem">
				<el-input v-model="form.sm2.price_manager" placeholder="请输入" autocomplete="off"></el-input>
			</el-form-item>
		</el-col>
		  <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
			  <el-form-item label="法定代表人签字" prop="sm2.legal" class="custemitem">
				  <el-input v-model="form.sm2.legal" placeholder="请输入" autocomplete="off"></el-input>
			  </el-form-item>
		  </el-col>
	  </el-row>
	  
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">保存</el-button>
    </div>
	
  </el-dialog>
</template>

<script>
export default {
  name: 'definite2',
  components: {
  	
  },
  data() {
    return {
      form: {
        resource: 0,
		  sm:{
			  project_name:"",
			  project_code:"",
			  org_name:"",
			  org_code:"",
			  index:"",
			  except_content:"",
			  unit:"",
			  price:"",
			  explain:"",
			  project_content:"",
		  },
		  sm2:{
			  is_norm:"",
			  norm_code:"",
			  norm_name:"",
			  sense:"",
			  base:"",
			  norm:"",
			  risk:"",
			  apply_unit:"",
			  result:"",
			  declare:"",
			  matters:"",
			  price_manager:"",
			  is_write:"",
			  legal:"",
		  }



      },
      rules: {
		  ['sm.project_name']:[{ required: true, message: "请填写", trigger: "submit" }],
		  // ['sm.project_code']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm.org_name']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm.org_code']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm.except_content']:[{ required: false, message: "请填写", trigger: "submit" }],
		  ['sm.unit']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm.price']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm.explain']:[{ required: false, message: "请填写", trigger: "submit" }],
		  ['sm.project_content']:[{ required: false, message: "请填写", trigger: "submit" }],
		  ['sm2.norm_code']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm2.norm_name']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm2.sense']:[{ required: false, message: "请填写", trigger: "submit" }],
		  ['sm2.base']:[{ required: false, message: "请填写", trigger: "submit" }],
		  ['sm2.norm']:[{ required: false, message: "请填写", trigger: "submit" }],
		  ['sm2.risk']:[{ required: false, message: "请填写", trigger: "submit" }],
		  ['sm2.apply_unit']:[{ required: false, message: "请填写", trigger: "submit" }],
		  ['sm2.result']:[{ required: false, message: "请填写", trigger: "submit" }],
		  ['sm2.declare']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm2.matters']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm2.price_manager']:[{ required: true, message: "请填写", trigger: "submit" }],
		  ['sm2.is_norm']:[{ required: true, message: "请选择", trigger: "submit" }],
		  ['sm2.legal']:[{ required: true, message: "请选择", trigger: "submit" }],
      },
      title: '',
      dialogFormVisible: false,
		
    }
  },
  created() {},
  methods: {
    showDia(row) {
      this.title = '明细'
      this.dialogFormVisible = true
		console.log(row);
		if(row.sm2){
			this.form.sm2 = row.sm2
		}else {
			this.form.sm2={
				is_norm:"",
				norm_code:"",
				norm_name:"",
				sense:"",
				base:"",
				norm:"",
				risk:"",
				apply_unit:"",
				result:"",
				declare:"",
				matters:"",
				price_manager:"",
				legal:"",
			}
		}
		this.form.sm.org_name=row.org_name ;
		this.form.sm.fileInfoId=row.fileInfoId ;
		this.form.sm.file=row.file ;
		this.form.sm.child_or_not=row.child_or_not ;
		this.form.sm.org_code=row.org_code ;
		this.form.sm.natures=row.natures ;
		this.form.sm.project_name=row.project_name ;
		// this.form.sm.two_code=row.two_code ;
		this.form.sm.project_code=row.project_code ;
		this.form.sm.fourcode=row.fourcode ;
		this.form.sm.except_content=row.except_content ;
		this.form.sm.unit=row.unit ;
		this.form.sm.price=row.price ;
		this.form.sm.project_content=row.project_content ;
		this.form.sm.explain=row.explain ;
		this.form.sm.index=row.index ;
		this.form.sm.type=row.type ;
		this.form.sm.typeName=row.typeName ;
		this.form.sm.service_price=row.service_price ;
		this.form.sign = row.sign
		//
		this.form.sm2.is_norm = row.is_norm ;
		this.form.sm2.legal = row.legal ;
		this.form.sm2.norm_code = row.norm_code ;
		this.form.sm2.norm_name = row.norm_name ;
		this.form.sm2.sense = row.sense ;
		this.form.sm2.base = row.base ;
		this.form.sm2.norm = row.norm ;
		this.form.sm2.risk = row.risk ;
		this.form.sm2.apply_unit = row.apply_unit ;
		this.form.sm2.result = row.result ;
		this.form.sm2.declare = row.declare ;
		this.form.sm2.matters = row.matters ;
		this.form.sm2.price_manager = row.price_manager ;
    },
	close() {
		this.$confirm('取消后将丢失已填信息！确定要取消表单吗？',{
			confirmButtonText: '是',
			cancelButtonText: '否'
		})
			.then((_) => {
				this.$refs['form'].resetFields()
				//this.form = this.$options.data().form
				this.dialogFormVisible = false
			})
			.catch((_) => {})
			
      
    },
    save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
			console.log("明细保存数据");
			console.log(this.form);
			this.form.sm2.is_write = 1 ;
			this.$emit('fetch-data', this.form)
		  this.dialogFormVisible = false
        } else {
		  this.$baseMessage('请填写必填项的信息！', 'warning')
          return false
        }
      })
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
	.el-dialog__header{
		padding: 0;
	}
	.inform-title{
		width: 90%;
		margin: -10px auto 20px;
		padding: 0 0 10px;
		line-height: 24px;
		font-size: 18px;
		font-weight: bold;
		text-align: center;
	}
  .custemitem {
      label {
        line-height: 16px !important;
      }
  }
  .box_card{
	  h5{
		  font-size: 14px;
		  font-weight: bold;
		  border-left: 3px solid #1890ff;
		  padding-left: 10px;
		  color: #666;
		  display: flex;
		  flex-direction: row;
		  margin: 0 0 14px;
	  }
	  .right-add-btn {
	    margin-top: -40px;
	    margin-bottom: 6px;
		text-align: right;
	  }
	  
  }
  .box_card.mt2{
  		margin-top: 20px;
  }
  .total-num{
	  display: flex;
	  justify-content: space-between;
	  align-items: center;
	  font-size: 14px;
	  color: #666;
	  padding: 10px 16px;
	  background-color: #f5f7fa;
	  p{
		  padding: 0;
		  margin: 0;
		  span{
		  		  font-size: 20px;
		  		  font-weight: bold;
		  		  color: #1890ff;
		  		  text-decoration: underline;
		  		  margin: 0 6px;
		  }
	  }  
  }
  .explain-text{
	  margin-top: 14px;
	  padding: 10px 0;
	  font-size: 14px;
	  color: #999;
	  line-height: 20px;
	  p{
		  padding: 0;
		  margin: 0;
	  }
  }
}
</style>