<template>
	<el-drawer title="查看详情" :visible.sync="dialog" direction="rtl" :with-header="false" custom-class="box_drawer"
			   size="80%" ref="drawer">
		<div class="drawer_content">
			<el-form :model="form" :label-width="formLabelWidth">
				<div class="drawer_main">
					<div class="box_card">
						<div class="box_header">
							<span>基本信息</span>
						</div>
						<div class="box_content">
							<el-row :gutter="20">
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="考核任务名称">
										<el-input v-model.trim="detailInfo.taskName" disabled></el-input>
									</el-form-item>
								</el-col>
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="机构类型">
										<el-input v-model.trim="detailInfo.orgTypeName" disabled></el-input>
									</el-form-item>
								</el-col>
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="考核年度">
										<el-input v-model.trim="detailInfo.year" disabled></el-input>
									</el-form-item>
								</el-col>
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="考核单">
										<el-input v-model.trim="detailInfo.assessmentName" disabled />
									</el-form-item>
								</el-col>
								<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
									<el-form-item label="考核结束时间">
										<el-input v-model.trim="detailInfo.expirationTime" disabled />
									</el-form-item>
								</el-col>
								<!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
								<!--<el-form-item label="保证金文件">-->
								<!--<el-input v-model.trim="detailInfo.brdy" disabled />-->
								<!--<a href="##" download="wenjian.excel">点击下载查看</a>-->
								<!--</el-form-item>-->
								<!--</el-col>-->
							</el-row>
						</div>
					</div>
					<div class="box_card">
						<div class="box_header">
							<span>考核单信息</span>
						</div>
						<el-table :data="tableList" border style="margin-top: 20px;" height="calc(100vh - 440px)"
								  :span-method="arraySpanMethod" show-summary :summary-method="getSummaries">
							<el-table-column  label="评价项目" align="" prop="name" min-width="200px">
								<template #default="{ row }">
									<span v-if="row.level==1">{{row.name}}</span>
									<span style="margin-left: 20px;width: calc(100% - 20px);" v-else>{{row.title}}</span>
								</template>
							</el-table-column>
							<el-table-column show-overflow-tooltip label="分值" prop="score1" align="center" width="80px">
							</el-table-column>
							<el-table-column  prop="content" label="评价内容" min-width="200px"
											  align="">
							</el-table-column>
							<el-table-column  label="评分办法" align="" width="500px">
								<template #default="{ row }">
									<div v-if="row.level!==1">
										<span>{{row.name}}({{row.score}}分)</span>
									</div>
								</template>
							</el-table-column>
							<el-table-column show-overflow-tooltip label="负责人" align="center" width="200px">
								<template #default="{ row }">
									<div>
										<span>{{row.userName}}</span>
									</div>
								</template>
							</el-table-column>
						</el-table>
					</div>
				</div>
			</el-form>
			<div class="drawer_footer">
				<el-button @click="close">关 闭</el-button>
			</div>
		</div>
	</el-drawer>
</template>

<script>

	import { getEntity, saveOrUploadEval,designList,getMedinsDeptB,getUserList,addTaskManage,info,getUser } from '@/api/eval';

	export default {
		name: 'detail',
		data() {
			return {
				dialog: false,
				userData:[],
				detailInfo: {
					taskName:"",
					orgType:"",
					year:"",
					assessmentId:"",
					expirationTime:"",
					orgTypeName:"",
					assessmentName:"",
				},
				tableList: [],
			}
		},
		mounted() {
			this.tableList = this.flatData(this.tableList);
		},
		methods: {
			flatData(data){
				var arr = [];
				data.map((el,index)=>{
					arr.push(Object.assign(el,{indexs:[index],level:1}));
					if(el.evalCategoryStardards){
						el.evalCategoryStardards.map((item,idx)=>{
							arr.push(Object.assign(item,{indexs:[index,idx],level:2},item.evalStardardMethods.shift(),{score1:item.score}));
							if(item.evalStardardMethods){
								item.evalStardardMethods.map((itm,idxs)=>{
									arr.push(Object.assign(itm,{indexs:[index,idx,idxs],level:3}));
								})
							}
						})
					}
				})
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
				console.log(row)
				info(row).then((res) => {
					if(res.code == 0){
						that.detailInfo = res.data ;
						that.dialog = true


						getUser(row).then(res => {
							that.userData = res.data ;
						})

						getEntity({ id: that.detailInfo.assessmentId }).then(res => {
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
						})
						console.log(that.tableList)

					}

				})

			},
			close() {
				this.dialog = false
			},
			getPersonInfo(cardno) {
				var that = this;
				getDetail({
					"cardno": cardno
				}).then((res) => {
					if (res.code == 0) {
						that.queryForm = res.data;
					}
				})
			}
		}
	}
</script>
<style lang="scss" scoped>
	::v-deep {
		.el-upload--picture-card {
			display: none !important;
			opacity: 0 !important;
		}

		.el-dialog__body {
			border-top: 0;
		}
	}
</style>