<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="750px"
    @close="close"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="任务名称" prop="task_name">
        <el-input v-model="form.task_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="机构类型" prop="org_type">
      	<el-select v-model="form.org_type" placeholder="请选择机构类型" class="w" @change="getLevelList">
      		<el-option value="1" label="医疗机构"></el-option>
      		<el-option value="2" label="零售药店"></el-option>
      	</el-select>
      </el-form-item>
      <el-form-item label="类别" prop="category" v-if="categoryShow">
        <el-select v-model="form.category" placeholder="请选择类别" class="w" @change="getLevelList">
          <el-option label="请选择" value=""></el-option>
          <el-option value="1" label="门诊"></el-option>
          <el-option value="2" label="住院"></el-option>
        </el-select>
      </el-form-item>
	  <el-form-item label="协议等级" prop="aggrement_lv" v-if="isshowlv">
	  	<el-select v-model="form.aggrement_lv" placeholder="请选择协议等级" class="w" @change="getSelectBy">
			<el-option label="请选择" value=""></el-option>
			<el-option
					v-for="item in levelList"
					:key="item.id"
					:label="item.name"
					:value="item.id"
			></el-option>

	  	</el-select>

	  </el-form-item>


	  <el-form-item label="考核年度" prop="year_of_assessment">
	  	<el-date-picker v-model="form.year_of_assessment"
						type="year"
						placeholder="选择年"
						@change="getSelectBy"
						format="yyyy"
						value-format="yyyy"
		>
	  	</el-date-picker>
	  </el-form-item>
		<el-form-item label="过期时间" prop="expiration_time">
			<el-date-picker v-model="form.expiration_time"
							type="date"
							value-format="yyyy-MM-dd"
							placeholder="选择日期"
							:picker-options="pickerOptions">
			</el-date-picker>
		</el-form-item>
	  <el-form-item label="考核单" prop="assessment_id">
	  	<el-select v-model="form.assessment_id"  class="w" filterable disabled>
			<el-option
					v-for="item in list"
					:key="item.id"
					:label="item.assess_name"
					:value="item.id"
			></el-option>
	  	</el-select>
	  </el-form-item>


		<el-form-item label="" prop="jgIds" v-if="isshow">
			<el-transfer
					filterable
					:filter-method="filterMethod"
					filter-placeholder="请输入关键字"
					v-model="medicalCodeList"
					@mouseover.native="addTitle"
					:titles="['默认考核机构', '剔除机构']"
					:data="data"
					v-loading="loading"
			></el-transfer>
		</el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {addTaskMange, checkListDropDownData, getAll, info, selectBy, updTaskMange} from '@/api/assessment.js'

export default {
  name: 'MenuManagementEdit',
  data() {
    return {
		pickerOptions:{
			disabledDate(time) {
				return time.getTime() < Date.now();
			},
		},
		isshow: false,
    categoryShow:false,
		data: [],
		filterMethod(query, item) {
			return item.key.indexOf(query) > -1
		},
		medicalCodeList: [],
      form: {
		  task_name:'',
		  org_type:'',
      category:'',
		  aggrement_lv:'',
		  year_of_assessment:'',
		  expiration_time:'',
		  assessment_id:'',
        redirect: '',
        hidden: '',
        alwaysShow: '',
      },
      rules: {
        'task_name': [{ required: true, trigger: 'blur', message: '请输入任务名称' }],
        'org_type': [{ required: true, trigger: 'blur', message: '请选择机构类型' }],
        'category': [{ required: true, trigger: 'blur', message: '请选择类别' }],
        'aggrement_lv': [{ required: true, trigger: 'blur', message: '请选择协议等级' }],
        'year_of_assessment': [{ required: true, trigger: 'blur', message: '请选择考核年度' }],
        'assessment_id': [{ required: true, trigger: 'blur', message: '未查询到相关考核单,请先设计' }],
        'expiration_time': [{ required: true, trigger: 'blur', message: '请选择过期时间' }],
      },
      title: '',
      dialogFormVisible: false,
		aList: [
			{ id: "1", name: '一级' },
			{ id: "2", name: '二级' },
			{ id: "3", name: '三级' },
			{ id: "9", name: '未定级' },
		],
		bList: [
			{ id: "4", name: 'A级' },
			{ id: "5", name: 'B级' },
			{ id: "6", name: 'C级' },
		],
		levelList: [],
		list: [],
		isshowlv: false,
		loading: true
    }
  },
  created() {

  },
  methods: {
    showEdit(row) {
    this.categoryShow = false
		if (!row) {
        this.title = '添加'
		  this.isshowlv = false ;
			this.loading = true ;
		  this.getCheckListDropDownData() ;
			this.isshow = false ;
			this.data = [] ;
			this.medicalCodeList = [] ;
			this.form.id="" ;
      } else {
			var that = this ;
			that.loading = true ;
			that.title = '编辑'
			that.isshowlv = true ;
			that.isshow = true ;

			that.getCheckListDropDownData() ;
			  info(row.id).then((res) => {
				  that.form = res.data ;
				  that.medicalCodeList = res.data.medicalCodeList ;
				  if (res.data.org_type == 1) {
            that.categoryShow = true
					  that.levelList = that.aList;
					  // that.form.aggrement_lv = parseInt(res.data.aggrement_lv) ;
				  } else if (res.data.org_type == 2) {
					  that.levelList = that.bList;
					  // that.form.aggrement_lv = parseInt(res.data.aggrement_lv) ;
				  }



				  getAll(that.form).then((res) => {
					  if (res.code == 0) {
						  const data = []
						  const cities = res.data
						  cities.forEach((city, index) => {
							  data.push({
								  label: city.label,
								  key: city.key,
								  pinyin: city.label,
							  })
						  })
						  that.data = data
							that.loading = false ;
					  }
				  })





			  })
      }
      this.dialogFormVisible = true
    },
    close() {
		this.isshow = false ;
		this.data = [] ;
		this.medicalCodeList = [] ;
      this.$refs['form'].resetFields()
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
        	if(this.form.id ==null || this.form.id =="" || this.form.id ==undefined){
				this.form.medicalCodeList = this.medicalCodeList ;
				addTaskMange(this.form).then((res) => {
					if(res.code == 0){
						this.$baseMessage("新增成功", 'success')
						this.$emit('fetch-data')
						this.close()
					}
				})
			}else {
				this.form.medicalCodeList = this.medicalCodeList ;
				updTaskMange(this.form).then((res) => {
					this.$baseMessage("修改成功", 'success')
					this.$emit('fetch-data')
					this.close()
				})
			}

        } else {
          return false
        }
      })
    },
	  getLevelList() {
    	var that = this ;
    	that.data = [] ;
		  if (that.form.org_type == 1) {
        that.categoryShow = true
		  	that.isshowlv = true ;
			  that.levelList = that.aList;
			  that.form.aggrement_lv ="" ;
			that.getSelectBy() ;
		  } else if (that.form.org_type == 2) {
        that.categoryShow = false
			  that.isshowlv = true ;
			  that.levelList = that.bList;
			  that.form.aggrement_lv ="" ;
			  that.getSelectBy() ;
		  } else {
			  that.isshowlv = false ;
			  that.levelList = []
		  }

		  if(that.form.aggrement_lv != undefined){
			  that.form.aggrement_lv ="" ;
		  }


	  },
	  getCheckListDropDownData(){
		  checkListDropDownData().then((res) => {
				this.list = res.data ;
		  })
	  },
	  getSelectBy(){

		  if(this.form.org_type == 1 ){
			  if(this.form.aggrement_lv != null && this.form.aggrement_lv != undefined && this.form.aggrement_lv !="" &&
					  this.form.category != null && this.form.category != undefined && this.form.category !=""
			  ){
				  this.isshow = true ;
				  this.loading = true ;
				  getAll(this.form).then((res) => {
					  if (res.code == 0) {
						  const data = []
						  const cities = res.data
						  cities.forEach((city, index) => {
							  data.push({
								  label: city.label,
								  key: city.key,
								  pinyin: city.label,
							  })
						  })
						  this.data = data
						  this.loading = false ;
					  }
				  })
			  }
		  }else {
			  if(this.form.aggrement_lv != null && this.form.aggrement_lv != undefined && this.form.aggrement_lv !=""
			  ){
				  this.isshow = true ;
				  this.loading = true ;
				  getAll(this.form).then((res) => {
					  if (res.code == 0) {
						  const data = []
						  const cities = res.data
						  cities.forEach((city, index) => {
							  data.push({
								  label: city.label,
								  key: city.key,
								  pinyin: city.label,
							  })
						  })
						  this.data = data
						  this.loading = false ;
					  }
				  })
			  }
		  }

		  selectBy(this.form).then((res) => {
			  if(res.data != null){
				  this.form.assessment_id =res.data.id ;
			  }else {
				  this.form.assessment_id = "" ;
			  }

		  })
	  },
	  addTitle(e) {
		  const target = e.target
		  if (target.title) return
		  target.title = target.innerText
	  },


  },
}
</script>
