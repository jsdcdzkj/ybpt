<template>
	<div class="main-container">
		<el-row :gutter="20">
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">信息查询</span>
						<vab-icon :icon="['fas', 'angle-up']"></vab-icon>
					</div>
					<el-form label-width="160px">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="任务名称">
									<el-input v-model.trim="queryForm.taskName" clearable @keyup.enter.native="queryData"
											  class="input-with-select"></el-input>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-show="userinfo.org_code == '320399'">
								<el-form-item label="统筹区">
									<el-select v-model="queryForm.admdvs" style="width: 100%" @change="getLevelList" clearable>
										<el-option v-for="item in admdvs" :key="item.value" :label="item.label"
												   :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</el-col>

							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="机构编码">
									<el-input v-model.trim="queryForm.orgCode" clearable @keyup.enter.native="queryData"
											  class="input-with-select"></el-input>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="机构类别">
									<el-select v-model="queryForm.category" style="width: 100%" clearable>
										<el-option label="门诊" value="1"></el-option>
										<el-option label="住院" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="机构类型">
									<el-select v-model="queryForm.orgType" style="width: 100%" @change="getLevelList" clearable>
										<el-option label="医疗机构" value="1"></el-option>
										<el-option label="零售药店" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="协议等级">
									<el-select v-model="queryForm.aggrementLv" style="width: 100%" clearable>
										<el-option
												v-for="item in levelList"
												:key="item.id"
												:label="item.name"
												:value="item.id"
										></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="考核年度">
									<el-date-picker v-model="queryForm.year" type="year" placeholder="选择年" format="yyyy"
													value-format="yyyy">
									</el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="经营性质">
									<el-select v-model="queryForm.natures" style="width: 100%" clearable>
										<el-option label="公立" value="1"></el-option>
										<el-option label="私立" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="精神专科">
									<el-select v-model="queryForm.spirit" style="width: 100%" clearable>
										<el-option label="非精神专科" value="0"></el-option>
										<el-option label="精神专科" value="1"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" :offset="12">
								<vab-query-form>
									<vab-query-form-right-panel :span="24">
										<el-form-item class="mr0">
								
											<el-button icon="el-icon-search" @click="queryData" type="primary">
												查 询
											</el-button>
											<el-button icon="el-icon-refresh-left" @click="resetForm()">重 置</el-button>
										</el-form-item>
									</vab-query-form-right-panel>
								</vab-query-form>
							</el-col>
						</el-row>
					</el-form>
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">历史考核信息</span>
						<div class="right">
							<!--<el-button type="primary" icon="el-icon-plus" @click="dr">导入险种配置</el-button>-->
							<el-button type="primary" icon="el-icon-download" @click="exportData">导出</el-button>
						</div>
						</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="list"
						element-loading-text="正在加载..." highlight-current-row border
						height="420px">
						<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="50px">
							<template #default="{ $index }">
								{{(queryForm.pageNo-1)*queryForm.pageSize + $index+1}}
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="考核任务名称" align="center" prop="taskName" min-width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="机构类型" align="center" prop="orgTypeName" width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="categoryName" width="80px" label="机构类别" align="center">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aggrementLvName" label="协议等级" align="center" width="80px">
					</el-table-column>
						<el-table-column show-overflow-tooltip prop="naturesName" label="经营性质" align="center" width="80px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="spiritName" label="精神类医院" align="center" width="100px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="year" label="考核年度" align="center"
							width="100px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="orgName" label="机构名称" align="center" width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="orgCode" label="机构编码" align="center" width="150px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="statusName" label="审核状态" align="center"
							width="80px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="expirationTime" label="过期时间" align="center" width="100px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="score" label="得分" align="center" width="60px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="averageScore" label="平均分" align="center" width="80px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="orgRate" label="指数" align="center" width="80px">
						</el-table-column>
            <el-table-column show-overflow-tooltip prop="totalScore" label="任务总分" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="totalPercent" label="百分制得分" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="averagePercent" label="百分制平均分" align="center" width="120px">
            </el-table-column>
						<el-table-column show-overflow-tooltip prop="marginStaff" label="保证金（职工）" align="center" width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="refundStaff" label="返还保证金（职工）" align="center" width="160px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="staffRewards" label="奖惩金额（职工）" align="center" width="140px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="marginResident" label="保证金（居民）" align="center" width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="refundResident" label="返还保证金（居民）" align="center" width="160px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="residentRewards" label="奖惩金额（居民）" align="center" width="140px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="marginMedical" label="保证金（医疗救助）" align="center" width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="refundMedical" label="返还保证金（医疗救助）" align="center" width="160px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="medRewards" label="奖惩金额（医疗救助）" align="center" width="140px">
						</el-table-column>

						<el-table-column show-overflow-tooltip label="操作" width="200px" align="center" fixed="right">
							<template #default="{ row }">
								<el-button plain @click="handlechuli(row,1)" type="primary" size="mini">
									查看
								</el-button>
                <el-button v-if="userinfo.username == 'admin' && row.status == 1" plain @click="handleStatus(row)" type="primary" size="mini">
                  暂存
                </el-button>
								<!--<el-button plain @click="pz(row)" type="primary" size="mini">-->
									<!--险种配置-->
								<!--</el-button>-->
							</template>
						</el-table-column>
					</el-table>
					<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
						:layout="layout" :total="total" @size-change="handleSizeChange"
						@current-change="handleCurrentChange2"></el-pagination>
				</el-card>
			</el-col>

		</el-row>
		<el-dialog title="险种配置" width="500px" :visible.sync="showDialog" append-to-body :close-on-click-modal="false">
			<el-form label-width="auto" :model="insuranceForm" style="max-height: 800px;overflow: auto;" ref="insuranceForm" :rules="rules">
				<el-form-item label="职工占比" prop="staffProportion">
					<el-input maxlength="2" v-model="insuranceForm.staffProportion" clearable placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="居民占比" prop="residentProportion">
					<el-input maxlength="2" v-model="insuranceForm.residentProportion" clearable placeholder="请输入"></el-input>
				</el-form-item>
				<el-form-item label="医疗占比" prop="medicalTreatmentProportion">
					<el-input maxlength="2" v-model="insuranceForm.medicalTreatmentProportion" clearable placeholder="请输入"></el-input>
				</el-form-item>
			</el-form>
			<div style="text-align: right;">
				<el-button @click="showDialog=false">取消</el-button>
				<el-button type="primary" @click="sureInsurance" :loading="loading" :disabled="disabled"> {{ loading ? '确定中 ...' : '确定' }}</el-button>
			</div>
		</el-dialog>
		<!-- 上传文件弹窗 -->
		<el-dialog
				title="上传数据"
				:visible.sync="uploadDialog"
				width="660px"
				append-to-body
				v-loading="uploadLoading" element-loading-text="上传中······"
				element-loading-spinner="el-icon-loading"
				element-loading-background="rgba(0, 0, 0, 0.8)"
		>
			<el-form  ref="uploadForm" :model="uploadForm" label-width="100px" :rules="rules2">
				<el-row :gutter="20">
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
						<el-form-item label="考核任务" required prop="taskManageId">
							<el-select v-model="uploadForm.taskManageId" style="width: 100%" clearable @change="uploadDialog = true">
								<el-option
										v-for="item in taskManageDropDownData"
										:key="item.id"
										:label="item.taskName"
										:value="item.id"
								></el-option>
							</el-select>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
						<el-form-item label="模版下载">
							<el-button icon="el-icon-download" type="primary" @click="dowmb">下载</el-button>
						</el-form-item>
					</el-col>
					<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-show="uploadForm.taskManageId">
						<el-form-item label="文件上传" required>

							<el-upload ref="upfile"
									   :auto-upload="false"
									   :show-file-list="true"
									   accept=".xlsx"
									   :on-change="uploadChange"
									   :on-success="uploadSuccess"
									   action="#">
								<el-button icon="el-icon-upload2" type="success">文件上传</el-button>
							</el-upload>

						</el-form-item>

					</el-col>

					<el-col v-show="uploadForm.taskManageId">
						<el-form-item >
							<span style="color: red">(请上传07版的Excel文件)</span>
						</el-form-item>
					</el-col>
				</el-row>
			</el-form>
			<div style="text-align: right;margin-top: 20px;">
				<el-button @click="uploadDialog=false">取消</el-button>
				<el-button type="primary" @click="uploadSub">确定</el-button>
			</div>

		</el-dialog>
		<edit ref="edit" @fetch-data="fetchData"></edit>
	</div>
</template>

<script>
import {
  getHisPageList, updeById, evalOrgTaskExport, taskManageDropDown, uploadPzFile, updateOrgTaskManagerStatus
} from '@/api/eval'
	import {getDicts} from "@/api/dictManagement";
	import edit from "./components/edit"
	import {fileURL} from "@/config/setting.config";
  import {doDelete} from "@/api/userManagement";

	export default {
		name: 'Index',
		data() {
			var validateNumber = (rule, value, callback)=>{
				if(value===''){
					callback(new Error('不能为空'));
				}else if(value!='0'&&!Number(value)){
					callback(new Error('请输入数字值'));
				}else if(Number(value)>10){
					callback(new Error('单项值不能大于10'));
				}else{
					callback()
				}
			}
			return {
				loading: false,
				disabled: false,
				isShow: false,
				list: [],
				fileList: [],
				taskManageDropDownData: [],
				listLoading: false,
				showDialog:false,
				uploadDialog:false,
				uploadLoading:false,
				uploadForm:{
					order:'',
					files:'',
					taskManageId:''
				},
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				insuranceForm:{
					id:'',
					staffProportion:'',
					residentProportion:'',
					medicalTreatmentProportion:''
				},
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					username: '',
					cardType: '',
					taskName: '',
					orgType: '',
					categoryName: '',
					aggrementLv: '',
					year: '',
					natures: '',
					spirit: '',
					admdvs: '',
				},
				rules:{
					staffProportion: [{ validator: validateNumber, trigger: 'blur' }],
					residentProportion: [{validator: validateNumber, trigger: 'blur' }],
					medicalTreatmentProportion: [{validator: validateNumber, trigger: 'blur' }],
				},
				rules2:{
					taskManageId: {required: true, trigger: 'submit', message: '请选择考核任务'},
				},
				aList: [
					{ id: 1, name: '一级' },
					{ id: 2, name: '二级' },
					{ id: 3, name: '三级' },
					{ id: 9, name: '未定级'},
				],
				bList: [
					{ id: 4, name: 'A级' },
					{ id: 5, name: 'B级' },
					{ id: 6, name: 'C级' },
				],
				levelList: [],
				admdvs: [],
				fd :new FormData(),
				userinfo: {},
			}
		},
		components: {
			edit
		},
		created() {

			 this.getAdmdvs()
			 this.getTaskManageDropDown()
			var userinfo = JSON.parse(localStorage.getItem("userinfo"));
			 console.log(userinfo)
			 this.queryForm.admdvs =userinfo.org_code
			this.userinfo = userinfo
			this.queryData()
		},
		mounted() {},
		methods: {
			//确定配置险种
			sureInsurance(){
				this.$refs.insuranceForm.validate((valid) => {
					if(valid){
						this.loading = true
						this.disabled = true
						var info = JSON.parse(JSON.stringify(this.insuranceForm));
						if(Number(info.staffProportion)+Number(info.residentProportion)+Number(info.medicalTreatmentProportion)!=10){
							this.$baseMessage(`所有占比之和应为10`,'error');
							this.loading = false
							this.disabled = false
							return ;
						}
						//请求接口

						updeById(this.insuranceForm).then(res => {
							if(res.code == 0){
								this.showDialog=false ;
								setTimeout(() => {
									this.loading = false
									this.disabled = false
								}, 300)
								this.$baseMessage(`操作成功`,'success');
								this.queryData()
							}
						})

					}
				})
			},
			handleSizeChange(val) {
				this.queryForm.pageSize = val
				this.fetchData()
			},
			handleCurrentChange2(val) {
				this.queryForm.pageNo = val
				this.fetchData()
			},

			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			async fetchData() {
				this.listLoading = true
				const res = await getHisPageList(this.queryForm)
				this.list = res.data.records;
				this.total = res.data.total;
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
			resetForm() {
				this.queryForm.taskName = ''
				this.queryForm.orgType = ''
				this.queryForm.orgCode = ''
				this.queryForm.categoryName = ''
				this.queryForm.aggrementLv = ''
				this.queryForm.year = ''
				this.queryForm.natures = ''
				this.queryForm.spirit = ''
				this.queryForm.admdvs = ''
				this.queryData();
			},
			async getAdmdvs() {
				const res = await getDicts({"type": "admdvs-area"});
				if (res.code == "0") {
					this.admdvs = res.data;
				}
			},
			getLevelList() {
				var that = this ;

				if (that.queryForm.orgType == 1) {
					that.levelList = that.aList;
					that.queryForm.aggrementLv ="" ;
				} else if (that.queryForm.orgType == 2) {
					that.levelList = that.bList;
					that.queryForm.aggrementLv ="" ;
				} else {
					that.levelList = []
				}

				if(that.queryForm.aggrementLv != undefined){
					that.queryForm.aggrementLv ="" ;
				}
			},
			pz(row){
				console.log(row)

				this.insuranceForm.id = row.id

				if(row.staffProportion !=""){
					this.insuranceForm.staffProportion = row.staffProportion
				}

				if(row.residentProportion !=""){
					this.insuranceForm.residentProportion = row.residentProportion
				}

				if(row.medicalTreatmentProportion !=""){
					this.insuranceForm.medicalTreatmentProportion = row.medicalTreatmentProportion
				}
				this.showDialog=true
				this.$nextTick(()=>{
					this.$refs.insuranceForm.clearValidate();
				})
			},
			exportData(){
				this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
					this.listLoading = true
					await evalOrgTaskExport(this.queryForm).then((res) => {
						let fileName = "历史考核信息导出.xlsx";
						let objectUrl = URL.createObjectURL(new Blob([res.data]))
						const link = document.createElement('a')
						link.download = decodeURI(fileName)
						link.href = objectUrl
						link.click()
						this.listLoading = false;
						this.$baseMessage("导出成功！", 'success')
					})

				})
			},
			getTaskManageDropDown(){
				taskManageDropDown().then(res => {
					if(res.code == 0){
						this.taskManageDropDownData=res.data ;
					}
				})
			},
			uploadChange(file,fileList){
				console.log(fileList)
				this.fd =  new FormData();
				this.fd.append("file", file.raw);
				// this.fileList.name = fileList.name ;
				},
			uploadSub(){
				this.$refs['uploadForm'].validate(async (valid) => {
					if (valid) {

						if(this.fd.get("id") == "" || this.fd.get("id") ==null || this.fd.get("id") ==undefined){
							this.fd.append("id", this.uploadForm.taskManageId);
						}

						if(this.fd.get("file") =="" ||this.fd.get("file") ==null ){
							this.$baseMessage("请先上传文件！", 'error')
							return ;
						}
						var result = await uploadPzFile(this.fd);
						if (result.data.code == 0) {
							this.$refs.upfile.clearFiles();
							this.$baseMessage("上传成功", 'success')
							this.uploadDialog=false ;
							this.queryData() ;
						} else {
							this.$baseMessage(result.data.msg, 'error')
						}
						this.dialogFormVisible = false
						this.uploadDialog = false

					} else {
						return false
					}
				})
			},
			dr(){
				this.uploadDialog=true ;
				this.uploadForm.taskManageId = ""

				this.fd =  new FormData();
				this.$refs.upfile.clearFiles();
				this.$refs.uploadForm.resetFields()
			},
			//查看详情
			handlechuli(row, type) {
				this.$refs.edit.showDia(row, type);
			},
      handleStatus(row) {
        if (row.id) {
          this.$baseConfirm('确定回退到暂存吗', null, async () => {
            const { msg } = await updateOrgTaskManagerStatus({ id: row.id })
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        }
      },
			dowmb(){
				self.location.href = fileURL + "/file/template/绩效考核险种配置模版导入.xlsx" ;
			}
		},
	}
</script>