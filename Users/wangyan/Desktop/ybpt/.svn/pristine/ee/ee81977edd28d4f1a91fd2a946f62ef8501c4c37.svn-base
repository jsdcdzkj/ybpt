<template>
	<el-drawer :title="title" :visible.sync="dialog" direction="rtl" :with-header="false" 
		custom-class="box_drawer" size="80%" ref="drawer" :wrapperClosable="false">
		<div class="drawer_content">
			<el-form :model="queryForm" ref="queryForm" label-width="auto" :rules="rules">
				<div class="drawer_main">
					<div class="box_card">
						<div class="box_header">
							<span>基本信息</span>
						</div>
						<div class="box_content">
							<el-row :gutter="20">
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="考核任务名称" prop="taskName">
										<el-input v-model.trim="queryForm.taskName" placeholder="考核任务名称"  maxlength="100" type="text" show-word-limit/>
									</el-form-item>
								</el-col>
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="机构类型" prop="orgType">
										<el-select v-model="queryForm.orgType" placeholder="请选择" style="width: 100%" @change="getDesignList" clearable>
											<el-option label="医疗机构" value="1"></el-option>
											<el-option label="零售药店" value="2"></el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="考核年度" prop="year">
										<el-date-picker v-model="queryForm.year" type="year" placeholder="选择年" @change="getDesignList"
											format="yyyy" value-format="yyyy">
										</el-date-picker>
									</el-form-item>
								</el-col>
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="考核单" prop="assessmentId">
										<el-select v-model="queryForm.assessmentId" placeholder="请选择" style="width: 100%" @change="getDetail" clearable>
											<el-option
													v-for="item in assessmentList"
													:key="item.id"
													:label="item.title"
													:value="item.id"
											></el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="填报结束日期" prop="expirationTime">
										<el-date-picker v-model="queryForm.expirationTime" placeholder="选择日期"
											value-format="yyyy-MM-dd">
										</el-date-picker>
									</el-form-item>
								</el-col>
							</el-row>
						</div>
					</div>
					<div class="box_card">
						<div class="box_header">
							<span>考核单内容</span>
						</div>
						<div class="box_content">
							<el-table :data="tableList" border style="margin-top: 20px;" height="calc(100vh - 440px)"
								:span-method="arraySpanMethod" show-summary :summary-method="getSummaries" ref="orderTable">
								<el-table-column show-overflow-tooltip label="评价项目" align="" prop="name" min-width="200px">
									<template #default="{ row }">
										<span v-if="row.level==1">{{row.name}}</span>
										<span style="margin-left: 20px;width: calc(100% - 20px);" v-else>{{row.title}}</span>
									</template>
								</el-table-column>
								<el-table-column show-overflow-tooltip label="分值" prop="score1" align="center" width="80px">
								</el-table-column>
								<el-table-column show-overflow-tooltip prop="content" label="评价内容" min-width="200px"
									align="">
								</el-table-column>
								<el-table-column show-overflow-tooltip label="评分办法" align="" width="500px">
									<template #default="{ row }">
										<span v-if="row.level!==1">{{row.name}}({{row.score}}分)</span>
									</template>
								</el-table-column>
								<el-table-column show-overflow-tooltip label="负责人" align="center" width="200px" fixed="right">
									<template #default="{ row,$index }">
										<div v-if="row.level==2">
											<span style="margin-right: 10px;">{{row.userName}}</span>
											<el-button plain @click="openPeople($index)" type="primary" size="mini">
												选择
											</el-button>
										</div>
									</template>
								</el-table-column>
							</el-table>
						</div>
					</div>
				</div>
			</el-form>
			<!-- 选择负责人 -->
			<el-dialog title="选择负责人" width="500px" :visible.sync="peopleDialog" append-to-body :close-on-click-modal="false">
				<el-form label-width="auto" style="max-height: 800px;overflow: hidden;" :rules="rules2" ref="peopleInfo" :model="peopleInfo">
					<el-form-item label="科室"  prop="deptCode">
						<el-select v-model="peopleInfo.deptCode" placeholder="请选择" style="width: 100%" @change="getUserInfo">
							<el-option
									v-for="item in medinsDeptList"
									:key="item.dept_no"
									:label="item.dept_name"
									:value="item.dept_no"
							></el-option>
						</el-select>
					</el-form-item>
					<el-form-item label="负责人"  prop="userId">
						<el-select v-model="peopleInfo.userId" placeholder="请选择" style="width: 100%" >
							<el-option
									v-for="item in userList"
									@click.native="labelClick(item)"
									:key="item.id"
									:label="item.name"
									:value="item.id"
							></el-option>
						</el-select>
					</el-form-item>
				</el-form>
				<div style="text-align: right;">
					<el-button @click="peopleDialog=false">取消</el-button>
					<el-button type="primary" @click="sub">确定</el-button>
				</div>
			</el-dialog>
			<div class="drawer_footer">
				<el-button @click="cancelForm">关 闭</el-button>
				<!--<el-button @click="cancelForm">重 置</el-button>-->
				<el-button type="primary" @click="submit" :loading="loading">
					{{ loading ? '提交中 ...' : '提 交' }}
				</el-button>
			</div>
		</div>
	</el-drawer>
</template>

<script>
	import { getEntity, saveOrUploadEval,designList,getMedinsDeptB,getUserList,addTaskManage,info,getUser,updTaskManage } from '@/api/eval';

	export default {
		name: 'edit',
		data() {
			return {
				expireTimeOPtion: {
					disabledDate(time) {
						return time.getTime() < Date.now() - 8.64e7;  //如果没有后面的-8.64e7就是不可以选择今天的
					}
				},
				expireTimeOPtion2: {
					disabledDate(time) {
						return time.getTime() < Date.now() - 1 * 8.64e7;
					}
				},
				peopleDialog:false,
				id:"",
				peopleInfo:{
					deptCode:"",
					userId:""
				},
				tableList: [],
				title: '',
				dialog: false,
				loading: false,
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					username: '',
					taskName: '',
					assessmentId: '',
					orgType: '',
					year: '',
					expirationTime: '',
				},
				form: {
					name: '',
					region: '',
					date1: '',
					date2: '',
					delivery: false,
					type: [],
					resource: '',
					desc: '',
				},
				chooseIndexs:'',
				assessmentList:[],
				list:[],
				medinsDeptList:[],
				userList:[],
				userData:[],
				userName:"",
				userId:"",
				userName2:"",
				data:{
					userId:"",
					userName:""
				},
                rules: {
                    taskName: [
                        {required: true, trigger: 'submit', message: '请输入考核任务名称'},
                    ],
                    orgType: [
                        {required: true, trigger: 'submit', message: '请选择机构类型'},
                    ],
                    year: [
                        {required: true, trigger: 'submit', message: '请选择年份'},
                    ],
                    assessmentId: [
                        {required: true, trigger: 'submit', message: '请选择考核单'},
                    ],
                    expirationTime: [
                        {required: true, trigger: 'submit', message: '请选择填报日期'},
                    ],

                },
                rules2: {
                    deptCode: {required: true, trigger: 'submit', message: '请选择科室'},
                    userId: {required: true, trigger: 'submit', message: '请选择负责人'},
                },
			}
		},
		mounted() {
			// this.tableList = this.flatData(this.tableList);
		},
		methods: {
			flatData(data){
				var arr = [];
				data.map((el,index)=>{
					arr.push(Object.assign(el,{indexs:[index],level:1}));
					if(el.evalCategoryStardards){
						el.evalCategoryStardards.map((item,idx)=>{
							arr.push(Object.assign({userName:''},item,{indexs:[index,idx],level:2},item.evalStardardMethods.shift(),{score1:item.score}));
							if(item.evalStardardMethods){
								item.evalStardardMethods.map((itm,idxs)=>{
									arr.push(Object.assign(itm,{indexs:[index,idx,idxs],level:3}));
								})
							}
						})
					}
				})
				console.log(arr)
				return arr;
			},

			// 合并单元格
			arraySpanMethod({row, column, rowIndex, columnIndex}){
				if(row.level===1){
					return [1,1];
				}
				if(row.level===2){
					if([0,1,2,4].includes(columnIndex)){
						return {
						  colspan: 1,
						  rowspan: row.evalStardardMethods.length+1
						};
					}else if([3].includes(columnIndex)){
						return [1,1];
					}else{
						return [1,0];
					}
				}else{
					if([0,1,2].includes(columnIndex)){
						return {
						  colspan: 1,
						  rowspan: 0
						};
					}else{
						return [1,1];
					}
				}
			},
			//计算总分数
			getSummaries(params){
				const { columns, data } = params;
				var sums = [];
				columns.forEach((column,index)=>{
					if(index===0){
						sums[index] = '总计';
						return;
					}else if(index===1){
						sums[index] = data.reduce((a,b)=>{
							if(b.level===1){
								return a+Number(b.score);
							}else{
								return a;
							}
						},0)
						sums[index] += '分';
					}else{
						sums[index] = '';
					}
				})
				return sums;
			},
			showDia(row) {
			    var that = this ;
				if (!row) {
                    that.title = '新增'
					that.id = ""
					that.queryForm.id = ""
                    that.getMedinsDeptB() ;
                    that.tableList = [] ;

                    that.queryForm.taskName = "";
                    that.queryForm.assessmentId = "";
                    that.queryForm.orgType = "";
                    that.queryForm.year = "";
                    that.queryForm.expirationTime = "";
                    that.peopleInfo.userId=""
                    that.peopleInfo.deptCode=""
                    setTimeout(() => {
                        that.$refs.queryForm.clearValidate();
                    }, 100)

				} else {
					that.id = row.id ;
					console.log(that.id)
                    that.title = '编辑'
					that.getMedinsDeptB() ;
                    setTimeout(() => {
                        that.$refs.queryForm.clearValidate();
                    }, 100)

					console.log(row.id)
                    info(row.id).then((res) => {
                        if(res.code == 0){
							that.queryForm = res.data ;

							that.dialog = true
							that.getDesignList()


                            getUser(row.id).then(res => {
								that.userData = res.data ;
                            })
							console.log("===========")
							console.log(that.queryForm.assessmentId)
                            getEntity({ id: that.queryForm.assessmentId }).then(res => {
                                let data = JSON.parse(JSON.stringify(res.data));
								that.list = data.evalDesignCategorys;
								that.tableList = that.flatData(that.list);
                                console.log(that.tableList)

                                for(var i = 0 ;i<that.tableList.length;i++){

                                    if(that.tableList[i].level == 2){
										for(var t = 0 ;t<that.userData.length;t++){
											if(that.userData[t].stardardId == that.tableList[i].stardardId ){
												that.tableList[i].userName=that.userData[t].userName;
												that.tableList[i].userId=that.userData[t].userId;
											}

										}

                                    }
                                }
								this.$nextTick(()=>{
									this.$refs.orderTable.doLayout();
								})
                            })

                        }

                    })
				}
                that.dialog = true
			},
			close() {
				this.dialog = false
			},
			handleClose(done) {
				if (this.loading) {
					return
				}
				this.$confirm('确定要提交表单吗？')
					.then((_) => {
						this.loading = true
					})
			},
			cancelForm() {
				this.loading = false
				this.dialog = false
			},
			getDesignList(){


				designList(this.queryForm).then((res) => {
					if(res.code == 0){
						this.assessmentList = res.data ;
					}

				})

			},
			getDetail(){
				getEntity({ id: this.queryForm.assessmentId }).then(res => {
					let data = JSON.parse(JSON.stringify(res.data));
					this.list = data.evalDesignCategorys;
					this.tableList = this.flatData(this.list);
					this.$nextTick(()=>{
						this.$refs.orderTable.doLayout();
					})
				})
			}
,
			getMedinsDeptB(){
				getMedinsDeptB().then((res) => {
					if(res.code == 0){
						this.medinsDeptList = res.data ;
					}
				})

			},
			getUserInfo(){
				if(this.peopleInfo.userId != null && this.peopleInfo.userId != undefined && this.peopleInfo.userId!= ""){
					this.peopleInfo.userId = "" ;
				}

				getUserList(this.peopleInfo.deptCode).then((res) => {
					if(res.code == 0){
						this.userList = res.data ;
					}
				})
			},
			openPeople(indexs){

                this.userName2 = "" ;
                this.peopleInfo.userId = "" ;
                this.peopleInfo.deptCode = "" ;
                this.userList = []
                // if(this.peopleInfo.userId != null && this.peopleInfo.userId != undefined && this.peopleInfo.userId!= ""){
				// 	this.peopleInfo.userId = "" ;
				// }
				// if(this.peopleInfo.deptCode != null && this.peopleInfo.deptCode != undefined && this.peopleInfo.deptCode!= ""){
				// 	this.peopleInfo.deptCode = "" ;
				// }

				this.chooseIndexs = indexs;
				this.peopleDialog = true;

                this.$refs['peopleInfo'].resetFields()

            },
			labelClick(data){

				this.userName2 = data.name ;
				this.userId = data.id ;
			},
			sub(){
                var that = this ;
                that.$refs['peopleInfo'].validate(async (valid) => {
                    if (valid) {
                        let indexs = that.chooseIndexs;
                        that.tableList[indexs].userName=that.userName2;
                        that.tableList[indexs].userId=that.userId;
                        that.peopleDialog = false ;
                    } else {
                        return false
                    }
                })

			},
			submit(){
                this.$refs['queryForm'].validate(async (valid) => {
                    if (valid) {
						const loading = this.$loading({
							lock: true,
							text: '提交中，请稍等...',
							spinner: 'el-icon-loading',
							background: 'rgba(0, 0, 0, 0.7)'
						});
                        console.log(this.tableList);
                        this.queryForm.evalStardardUserList = []
                        for(var i = 0 ;i<this.tableList.length;i++){
							this.data = {}
                            if(this.tableList[i].level == 2){
                                if(this.tableList[i].userId == "" || this.tableList[i].userId == null ||this.tableList[i].userId == undefined){
                                    this.$baseMessage("请选择负责人！", 'error')
									setTimeout(() => {
										loading.close();
									}, 300)

                                    return ;

                                }
                                console.log(this.tableList[i].userId )
                                this.data.stardardId = this.tableList[i].stardardId ;
                                this.data.designId = this.queryForm.assessmentId ;
                                this.data.userId = this.tableList[i].userId ;
                                this.queryForm.evalStardardUserList.push(this.data) ;
                            }
                        }
                        console.log(this.queryForm);

						if(this.id == ''){
							addTaskManage(this.queryForm).then((res) => {
								if(res.code == 0){

									this.cancelForm() ;
									this.$emit('fetch-data')



								}
							}).finally(()=>{
								setTimeout(() => {
									loading.close();
								}, 300)
							})
						}else {
							updTaskManage(this.queryForm).then((res) => {
								if(res.code == 0){

									this.cancelForm() ;
									this.$emit('fetch-data')
								}
							}).finally(()=>{
								setTimeout(() => {
									loading.close();
								}, 300)
							})

						}

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
		.el-dialog__body {
			border-top: 0;
		}
	}
</style>