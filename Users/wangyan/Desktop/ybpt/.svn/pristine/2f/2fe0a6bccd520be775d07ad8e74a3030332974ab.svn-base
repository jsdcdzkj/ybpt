<template>
	<div class="main-container" :visible.sync="dialogFormVisible" v-loading="loading" element-loading-text="请稍等······" element-loading-spinner="el-icon-loading"
		 element-loading-background="rgba(0, 0, 0, 0.8)">
		<el-row :gutter="20">
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">信息查询</span>
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
								<el-form-item label="机构类型">
									<el-select v-model="queryForm.orgType" style="width: 100%" @change="getLevelList" clearable>
										<el-option label="医疗机构" value="1"></el-option>
										<el-option label="零售药店" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="queryForm.orgType == 1">
								<el-form-item label="机构类别">
									<el-select v-model="queryForm.category" style="width: 100%" clearable>
										<el-option label="门诊" value="1"></el-option>
										<el-option label="住院" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="协议等级">
									<el-select v-model="queryForm.aggrementLv" style="width: 100%" clearable>
										<el-option label="请选择" value=""></el-option>
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
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
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
						<span class="tips">考核任务信息</span>
						<div class="right">
							<el-button type="primary" icon="el-icon-upload" @click="todow2">下载机构模版</el-button>
							<el-button type="primary" icon="el-icon-bottom" @click="todow">下载保证金模版</el-button>
							<el-button type="success" icon="el-icon-plus" @click="handleAdd">新增</el-button>

						</div>
					</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="list" element-loading-text="正在加载..." highlight-current-row border height="calc(100vh - 570px)">
						<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
							<template #default="{ $index }">
								{{(queryForm.pageNo-1)*queryForm.pageSize + $index+1}}
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="考核任务名称" align="center" prop="taskName" min-width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="统筹区" align="center" prop="admdvsName" min-width="200px">
						</el-table-column>

						<el-table-column show-overflow-tooltip label="机构类型" align="center" prop="orgTypeName" width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="categoryName" width="120px" label="类别" align="center">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aggrementLvName" label="协议等级" align="center" width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="naturesName" label="经营性质" align="center"
							width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="spiritName" label="精神专科" align="center" width="180px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="year" label="考核年度" align="center" width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="expirationTime" label="填报结束日期" align="center" width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="statusName" label="状态" align="center" width="200px">
						</el-table-column>


						<el-table-column show-overflow-tooltip prop="staffSumEarnestMoney" label="保证金总额(职工)" align="center" width="200px">
						</el-table-column>
							<el-table-column show-overflow-tooltip prop="residentSumEarnestMoney" label="保证金总额(居民)" align="center" width="200px">
							</el-table-column>
								<el-table-column show-overflow-tooltip prop="medSumEarnestMoney" label="保证金总额(医疗救助)" align="center" width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="staffRewards" label="奖励金(职工)" align="center" width="200px">
						</el-table-column>
							<el-table-column show-overflow-tooltip prop="residentRewards" label="奖励金(居民)" align="center" width="200px">
							</el-table-column>
								<el-table-column show-overflow-tooltip prop="medRewards" label="奖励金(医疗救助)" align="center" width="200px">
						</el-table-column>

						<el-table-column show-overflow-tooltip prop="earnestMoneyShow" label="保证金展示" align="center" fixed="right"
										 width="120px">
							<template slot-scope="scope">
								<el-switch  active-value="1"
											inactive-value="0" v-model="scope.row.earnestMoneyShow" @change="switchChange(scope.row)"></el-switch>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="操作" width="370" align="center" fixed="right">
							<template #default="{ row,$index }">
								<el-button plain @click="handlechuli(row)" type="success" size="mini" v-if="row.status == 0 ">
									发布
								</el-button>
								<el-button plain @click="start(row)" type="success" size="mini" v-if="row.status == 2 && roles.indexOf('eval-first')>-1">
                                    结束初审
								</el-button>
                <el-button plain @click="startEnd(row)" type="success" size="mini" v-if="row.status == 3 && roles.indexOf('eval-end')>-1">
                  开始复审
                </el-button>
                                <el-button plain @click="end(row)" type="success" size="mini" v-if="row.status == 4 && roles.indexOf('eval-end')>-1">
                                    结束复审
                                </el-button>


								<el-upload
										style="display: inline-block;margin-left: 10px;"
										ref="upfile_bzj"
										:auto-upload="false" :show-file-list=false accept=".xlsx"
										:on-change="(file, fileList) => { return handleChange3(file, fileList, row.id)}"
										:on-success="handleSuccess" action="#">
									<el-button plain  type="primary" size="mini" v-if="row.status == 6 ">
										保证金导入
									</el-button>
								</el-upload>

                                <el-button style="margin-left: 10px;" plain @click="calculate(row)" type="success" size="mini" v-if="row.status == 6 ">
                                    计算保证金
                                </el-button>


								<el-upload
								  style="display: inline-block;margin-left: 10px;"
								  ref="upfile"
								  :auto-upload="false" :show-file-list=false accept=".xlsx"
								  :on-change="(file, fileList) => { return handleChange2(file, fileList, row.id)}"
								  :on-success="handleSuccess" action="#">
								  <el-button size="mini" plain type="primary" v-if="row.status == 0 || row.status == 2 ">机构导入</el-button>
								</el-upload>
								<el-dropdown style="margin-left: 10px;cursor: pointer;"  @command="seeIndex($event,row)">
								  <span class="el-dropdown-link">
								    更多操作<i class="el-icon-arrow-down el-icon--right"></i>
								  </span>
								  <el-dropdown-menu slot="dropdown">
									  <!--<el-dropdown-item icon="el-icon-tickets" command="toLead" >下载导入模版</el-dropdown-item>-->

									  <el-dropdown-item icon="el-icon-view" command="show" >查看</el-dropdown-item>
								    <el-dropdown-item icon="el-icon-edit-outline" command="edit"  v-if="row.status == 0">编辑</el-dropdown-item>
								    <el-dropdown-item icon="el-icon-delete" command="del" v-if="row.status == 0">删除</el-dropdown-item>
									  <el-dropdown-item icon="el-icon-download" command="download2" >机构数据导出</el-dropdown-item>

									  <el-dropdown-item icon="el-icon-download" command="download" >保证金导出</el-dropdown-item>

								  </el-dropdown-menu>
								</el-dropdown>
							</template>
						</el-table-column>
					</el-table>
					<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
						:layout="layout" :total="total" @size-change="handleSizeChange"
						@current-change="handleCurrentChange2"></el-pagination>
				</el-card>
			</el-col>
		</el-row>
		<!-- 结束初审未提交审核的列表 -->
		<el-dialog title="提示" width="800px" :visible.sync="showDialog" append-to-body :close-on-click-modal="false">
			<div style="margin-bottom: 10px;">以下是未填写<span style="color: red">分数</span>的机构，请注意</div>
			<el-table :data="unsubmitList" max-height="800" border stripe>
				<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
				</el-table-column>
				<el-table-column show-overflow-tooltip label="国家编码" align="center" prop="orgCode">
				</el-table-column>
				<el-table-column show-overflow-tooltip label="评分指标" align="center" prop="title">
				</el-table-column>
				<el-table-column show-overflow-tooltip label="负责人" align="center" prop="name">
				</el-table-column>
			</el-table>
			<el-pagination background :current-page="queryForm2.pageNo" :page-size="queryForm2.pageSize"
						    :total="total2" @size-change="handleSizeChange3"
						   @current-change="handleCurrentChange3"></el-pagination>
			<div style="text-align: right;margin-top: 20px;">
				<el-button @click="showDialog=false">取消</el-button>
				<!--<el-button type="primary" @click="startSub">确定结束初审</el-button>-->
			</div>
		</el-dialog>
		<el-dialog title="提示" width="800px" :visible.sync="showDialog2"  append-to-body :close-on-click-modal="false">
			<div style="margin-bottom: 10px;">以下是未填写<span style="color: red">指标值</span>的机构，请注意</div>
			<el-table :data="unsubmitList2" max-height="500" border stripe >
				<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
				</el-table-column>
				<el-table-column show-overflow-tooltip label="国家编码" align="center" prop="orgCode">
				</el-table-column>
				<el-table-column show-overflow-tooltip label="评分指标" align="center" prop="title">
				</el-table-column>
				<!--<el-table-column show-overflow-tooltip label="评分办法" align="center" prop="name">-->
				<!--</el-table-column>-->
			</el-table>
			<!--<el-pagination background :current-page="queryForm2.pageNo" :page-size="queryForm2.pageSize"-->
						   <!--:total="total2" @size-change="handleSizeChange3"-->
						   <!--@current-change="handleCurrentChange3"></el-pagination>-->
			<div style="text-align: right;margin-top: 20px;">
				<el-button @click="showDialog2=false">取消</el-button>
				<!--<el-button type="primary" @click="startSub">确定结束初审</el-button>-->
			</div>
		</el-dialog>
		<edit ref="edit" @fetch-data="queryData"></edit>
		<views ref="views" @fetch-data="fetchData"></views>
	</div>
</template>

<script>
	import {
		selectList,updateMoneyShow,publishTaskMange,uploadOrgFile,uploadMoneyFile,getFile,getFile2,delEvalTaskManage,calculateScore,updateStatus,calculate,noScore,updatecsStatus,startFinish,zbList
	} from '@/api/eval'
	import Edit from './components/edit'
	import Views from './components/view'
	import {fileURL} from "@/config/setting.config";
	import {getDicts} from "@/api/dictManagement";

	export default {
		name: 'Index',
		components: {
			Edit,
			Views
		},
		data() {
			return {
				showDialog:false,
				showDialog2:false,
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
				userinfo: {
					org_code:""
				},
				levelList: [],
				unsubmitList:[],
				unsubmitList2:[],
				list: [],
				listLoading: false,
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				total2:0,
				cardTypes: [], //证件类型
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
				admdvs: [],
				form:{
					id:"",
					earnestMoneyShow:"",

				},
				dialogFormVisible: false,
				loading: false,
				queryForm2: {
					pageNo: 1,
					pageSize: 10,
					taskManageId: "",
				},
				id:"",

			}
		},
		created() {
			this.getAdmdvs()
			// this.getTaskManageDropDown()
			var userinfo = JSON.parse(localStorage.getItem("userinfo"));
			this.queryForm.admdvs =userinfo.org_code

			this.userinfo.org_code = userinfo.org_code
			console.log(this.userinfo.org_code)
			this.roles = JSON.parse(localStorage.getItem("roles"));
			this.queryData();
		},
		beforeDestroy() {},
		mounted() {},
		methods: {
			seeIndex(e,row){
				if(e == "download"){
					var that =this ;
					that.loading = true
					getFile(row.id).then((res) => {
						that.getBlob(fileURL + res.data.fileUrl).then((blob) => {
							that.saveAs(blob, res.data.fileName);
						});
						that.loading = false
					})
					that.loading = false
				} else if(e == "download2"){
					var that =this ;
					that.loading = true
					getFile2(row.id).then((res) => {
						that.getBlob(fileURL + res.data.fileUrl).then((blob) => {
							that.saveAs(blob, res.data.fileName);
						});
						that.loading = false
					})
					that.loading = false

				}else if(e == "del"){
					this.$baseConfirm('您确定要删除当前项吗', null, async () => {
						delEvalTaskManage(row.id).then((res) => {
							if(res.code == 0){
								this.queryData() ;
							}

						})

					})
				}else if(e == "edit"){
					this.$refs['edit'].showDia(row)

				}else if(e == "show"){
                    this.handlechuli2(row)
                }


			},
			handleAdd() {
				this.$refs['edit'].showDia()
			},
			 fetchData2(fc) {
				noScore(this.queryForm2).then((res) => {
					this.unsubmitList = res.data.records;
					this.total2 = res.data.total;
					fc&&fc();
				})
			},
			handleSizeChange(val) {
				this.queryForm.pageSize = val
				this.fetchData()
			},
			handleSizeChange3(val) {
				this.queryForm2.pageSize = val
				this.fetchData2()
			},

			handleCurrentChange2(val) {
				this.queryForm.pageNo = val
				this.fetchData()
			},
			handleCurrentChange3(val) {
				this.queryForm2.pageNo = val
				this.fetchData2()
			},
			handlechuli(row) {
				this.$baseConfirm('您确定要发布考核任务吗', null, async () => {
                    this.loading = true
					publishTaskMange(row.id).then((res) => {
						if (res.code == 0) {
							this.queryData();
							this.$baseMessage('成功', 'success')

						} else {
							this.$baseMessage(res.msg, 'error')

						}

					}).finally(()=>{
						this.loading = false
					})
				})

			},
			handlechuli2(row) {
				 this.$refs['views'].showDia(row.id)
				console.log(row.id)
			},
			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			async fetchData() {
				this.listLoading = true
				const res = await selectList(this.queryForm)
				this.list = res.data.records;
				this.total = res.data.total;
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},


			resetForm() {
				this.queryForm.taskName = ''
				this.queryForm.orgType = ''
				this.queryForm.categoryName = ''
				this.queryForm.aggrementLv = ''
				this.queryForm.year = ''
				this.queryForm.natures = ''
				this.queryForm.spirit = ''
				this.queryData();
			},
			switchChange(row) {
				var that = this ;
				console.log(row)
				that.form.id = row.id ;
				that.form.earnestMoneyShow = row.earnestMoneyShow ;
				updateMoneyShow(that.form).then((res) => {
					if(res.code == 0){
						that.$baseMessage("操作成功", 'success')
						that.$emit('fetch-data')
						this.close() ;
					}
					this.disabled = false;
					this.dialogFormVisible = false;
				})
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
			async handleChange2(file,fileList,id) {

				console.log(file)
				console.log(fileList)
				console.log(id)
						this.dialogFormVisible = true
						this.loading = true
						this.fileList = fileList;
						let fd = new FormData();
						fd.append("id", id);
				fd.append("file", file.raw);


						var result = await uploadOrgFile(fd);
						if (result.data.code == 0) {
							this.$emit('fetch-data')
              this.$nextTick(()=>{
                this.$refs.upfile.clearFiles();
              })
							this.$baseMessage("上传成功", 'success')
						} else {
							this.$baseMessage(result.data.msg, 'error')
						}
						this.dialogFormVisible = false
						this.loading = false


			},
			async handleChange3(file,fileList,id) {

				this.dialogFormVisible = true
				this.loading = true
				this.fileList = fileList;
				let fd = new FormData();
				fd.append("id", id);
				fd.append("file", file.raw);


				var result = await uploadMoneyFile(fd);
				if (result.data.code == 0) {
					this.$emit('fetch-data')
					this.$nextTick(()=>{
            this.$refs.upfile_bzj.clearFiles();
          })
					this.$baseMessage("上传成功", 'success')
				} else {
					this.$baseMessage(result.data.msg, 'error')
				}
				this.dialogFormVisible = false
				this.loading = false


			},
			handleSuccess() {
				this.queryData() ;
			},
			startSub(){
				updateStatus(this.queryForm2.taskManageId).then((res) => {
					if(res.code == 0){
						this.$baseMessage('操作成功', 'success')
						this.queryData() ;
					}
				})
			},
            start(row){
				var that = this ;
				that.$baseConfirm('你确定要结束初审吗', null, async () => {
					that.loading = true
					that.queryForm2.taskManageId = row.id
                    //查询是否有没写指标的
					zbList(row.id).then((res) => {
                        if(res.code == 0){
                        	if(res.data != null && res.data != ""&& res.data != undefined){
								that.showDialog2 = true;
								that.unsubmitList2 = res.data ;
							}else {
                        		//算指标分数
								updateStatus(row.id).then((res2) => {
									if(res2.code == 0){
										//查询是否有没分数的

										that.fetchData2(()=>{//查询是否有未填写分数的
											if(that.unsubmitList.length == 0 ){
												//算总分
												startFinish(row.id).then((res2) => {
													if(res2.code == 0){
														that.$baseMessage('操作成功', 'success')
														that.queryData() ;
													}
												}).finally(()=>{
													this.loading = false
												})
											}else {
												that.showDialog = true;
												that.loading = false
											}
										}) ;
									}
								}).finally(()=>{
									this.loading = false
								})

							}

                        }
                    }).finally(()=>{
						this.loading = false
					})
					// that.loading = false


                })
            },
      startEnd(row){
        this.$baseConfirm('您确定要开始复审吗', null, async () => {
          this.loading = true
          updatecsStatus(row.id).then((res) => {
            if(res.code == 0){
              this.$baseMessage('操作成功', 'success')
              this.queryData() ;
            }

          }).finally(()=>{
            this.loading = false
          })

        })
  },
            end(row){
                this.$baseConfirm('您确定要结束复审吗', null, async () => {
					this.loading = true
                    calculateScore(row.id).then((res) => {
                        if(res.code == 0){
                            this.$baseMessage('操作成功', 'success')
                            this.queryData() ;
                        }

                    }).finally(()=>{
						this.loading = false
					})

                })

            },
            calculate(row){
                this.$baseConfirm('您确定要计算保证金吗', null, async () => {
                    //计算保证金
					this.loading = true
                    calculate(row.id).then((res) => {
                        if(res.code == 0){
                            this.$baseMessage('操作成功', 'success')
                            this.queryData() ;
                        }else {
                            this.$baseMessage(res.msg, 'success')
                            this.queryData() ;
                        }

                    }).finally(()=>{
						this.loading = false
					})

                })

            },
			todow(){
				self.location.href = fileURL + "/file/template/绩效考核保证金导入模版.xlsx" ;
			},
			todow2(){
				self.location.href = fileURL + "/file/template/绩效考核机构导入模版.xlsx" ;
			},
			getBlob(url) {
				return new Promise((resolve) => {
					const xhr = new XMLHttpRequest();

					xhr.open('GET', url, true);
					xhr.responseType = 'blob';
					xhr.onload = () => {
						if (xhr.status === 200) {
							resolve(xhr.response);
						}
					};

					xhr.send();
				});
			},
			/**
			 * 保存
			 * @param  {Blob} blob
			 * @param  {String} filename 想要保存的文件名称
			 */
			saveAs(blob, filename) {
				if (window.navigator.msSaveOrOpenBlob) {
					navigator.msSaveBlob(blob, filename);
				} else {
					const link = document.createElement('a');
					const body = document.querySelector('body');

					link.href = window.URL.createObjectURL(blob);
					link.download = filename;
					// fix Firefox
					link.style.display = 'none';
					body.appendChild(link);

					link.click();
					body.removeChild(link);

					window.URL.revokeObjectURL(link.href);
				}
			},
			async getAdmdvs() {
				const res = await getDicts({"type": "admdvs-area"});
				if (res.code == "0") {
					this.admdvs = res.data;
				}
			},

		}
	}
</script>