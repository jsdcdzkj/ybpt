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
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="机构名称" v-if="userinfo.user_type == 1">
									<el-input v-model.trim="queryForm.orgName" placeholder="请输入"
										@keyup.enter.native="queryData" clearable />
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="机构编码" v-if="userinfo.user_type == 1">
									<el-input v-model.trim="queryForm.orgCode" placeholder="请输入"
										@keyup.enter.native="queryData" clearable />
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="统筹区"
									v-if="userinfo.user_type == 1 && (userinfo.org_code ==='320399' || userinfo.org_name =='admin')">
									<el-select v-model="queryForm.admdvs" clearable style="width: 100%"
										@change="queryData()">
										<el-option v-for="item in admdvs" :key="item.value" :label="item.label"
											:value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="考核任务名称">
									<el-input v-model.trim="queryForm.taskName" placeholder="请输入"
										@keyup.enter.native="queryData" clearable />
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="审核状态">
									<el-select v-model="queryForm.status" @change="queryData" clearable
										placeholder="请选择" style="width: 100%">
										<el-option label="暂存" value="-1"></el-option>
										<el-option label="填报中" value="0"></el-option>
										<el-option label="初审中" value="1"></el-option>
										<el-option label="待复审" value="2"></el-option>
										<el-option label="待计算保证金" value="3"></el-option>
										<el-option label="完成" value="4"></el-option>
										<el-option label="待反馈" value="5"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="考核年度">
									<el-date-picker v-model="queryForm.year" @change="queryData" type="year"
										placeholder="选择年" format="yyyy" value-format="yyyy" clearable>
									</el-date-picker>
								</el-form-item>
							</el-col>
						</el-row>
						<vab-query-form>
							<vab-query-form-right-panel :span="24">
								<el-form-item class="mr0">
									<el-button icon="el-icon-refresh-left" @click="resetForm()">重 置</el-button>
									<el-button icon="el-icon-search" @click="queryData" type="primary">
										查 询
									</el-button>
								</el-form-item>
							</vab-query-form-right-panel>
						</vab-query-form>
					</el-form>
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">考核任务信息</span>
					</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="list" element-loading-text="正在加载..."
						highlight-current-row border height="420px">
						<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
							<template #default="{ $index }">
								{{(queryForm.pageNo-1)*queryForm.pageSize + $index+1}}
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="机构名称" align="center" prop="orgName"
							min-width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="机构编码" align="center" prop="orgCode"
							min-width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="考核任务名称" align="center" prop="taskName"
							min-width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="机构类型" align="center" prop="orgType" width="120px">
							<template #default="{ row }">
								<el-tag v-if="row.orgType==1">医疗机构</el-tag>
								<el-tag v-else-if="row.orgType==2">零售药店</el-tag>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="category" width="120px" label="机构类别"
							align="center">
							<template #default="{ row }">
								<el-tag v-if="row.category==1">门诊</el-tag>
								<el-tag v-else-if="row.category==2">住院</el-tag>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="精神类医院" align="center" width="120px">
							<template #default="{ row }">
								<el-tag v-if="row.spirit==1">精神专科</el-tag>
								<el-tag v-else-if="row.spirit==0">非精神专科</el-tag>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aggrementLv" label="协议等级" align="center"
							width="120px">
							<template #default="{ row }">
								<el-tag v-if="row.aggrementLv==1">一级</el-tag>
								<el-tag v-else-if="row.aggrementLv==2">二级</el-tag>
								<el-tag v-else-if="row.aggrementLv==3">三级</el-tag>
								<el-tag v-else-if="row.aggrementLv==4">A级</el-tag>
								<el-tag v-else-if="row.aggrementLv==5">B级</el-tag>
								<el-tag v-else-if="row.aggrementLv==6">C级</el-tag>
								<el-tag v-else-if="row.aggrementLv==9">未定级</el-tag>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="status" label="审核状态" align="center">
							<template #default="{ row }">
								<el-tag v-if="row.status==-1">暂存</el-tag>
								<el-tag v-else-if="row.status==0">填报中</el-tag>
								<el-tag v-else-if="row.status==1">初审中</el-tag>
								<el-tag v-else-if="row.status==2">待复审</el-tag>
								<el-tag v-else-if="row.status==3">待计算保证金</el-tag>
								<el-tag v-else-if="row.status==4">完成</el-tag>
								<el-tag v-else-if="row.status==5">待反馈</el-tag>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="year" label="考核年度" align="center" width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="expirationTime" label="过期时间" align="center"
							width="100px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="score" label="得分" align="center" width="60px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="averageScore" label="平均分" align="center"
							width="80px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="orgRate" label="指数" align="center" width="80px">
						</el-table-column>
            <el-table-column show-overflow-tooltip prop="totalScore" label="任务总分" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="totalPercent" label="百分制得分" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="averagePercent" label="百分制平均分" align="center" width="120px">
            </el-table-column>
						<el-table-column show-overflow-tooltip prop="marginStaff" label="保证金（职工）" align="center"
							width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="refundStaff" label="返还保证金（职工）" align="center"
							width="160px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="staffRewards" label="奖惩金额（职工）" align="center"
							width="140px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="marginResident" label="保证金（居民）" align="center"
							width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="refundResident" label="返还保证金（居民）" align="center"
							width="160px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="residentRewards" label="奖惩金额（居民）" align="center"
							width="140px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="marginMedical" label="保证金（医疗救助）" align="center"
							width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="refundMedical" label="返还保证金（医疗救助）" align="center"
							width="160px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="medRewards" label="奖惩金额（医疗救助）" align="center"
							width="140px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="操作" width="120px" align="center" fixed="right">
							<template #default="{ row }">
								<!-- -1暂存 或 0生成初始数据-->
								<el-button
									v-if="(row.status == -1 || row.status == 0) && userinfo.user_type != 1 && row.isExpiration == 1"
									plain @click="handlechuli(row,0)" type="primary" size="mini">
									考核反馈
								</el-button>
								<el-button v-if="row.status != -1 && row.status != 0  && row.isExpiration == 1" plain
									@click="handlechuli(row,1)" type="primary" size="mini">
									详情
								</el-button>
								<el-button v-if="row.isExpiration != 1" plain @click="handlechuli(row,1)" type="primary"
									size="mini">
									详情
								</el-button>
							</template>
						</el-table-column>
					</el-table>
					<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
						:layout="layout" :total="total" @size-change="handleSizeChange"
						@current-change="handleCurrentChange2"></el-pagination>
				</el-card>
			</el-col>
		</el-row>
		<edit ref="edit" @fetch-data="fetchData"></edit>
	</div>
</template>

<script>
	import edit from "./components/edit"
	import {
		evalOrgTaskPage
	} from "@/api/eval";
	import {
		getDicts
	} from "@/api/dictManagement";

	export default {
		name: 'Index',
		data() {
			return {
				userinfo: {
					id: '',
					user_type: '',
					user_name: '',
					org_code: '',
				},
				admdvs: [],
				isShow: false,
				list: [{}, {}, {}],
				listLoading: false,
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					orgName: '',
					orgCode: '',
					taskName: '',
					year: '',
				},
			}
		},
		components: {
			edit
		},
		created() {
			this.userinfo = JSON.parse(localStorage.getItem("userinfo"))
			if (this.userinfo.user_type == 1) {
				this.queryForm.admdvs = this.userinfo.org_code
			} else {
				this.queryForm.orgCode = this.userinfo.org_code
			}
			this.getAdmdvs()
			this.fetchData()
		},
		mounted() {},
		methods: {
			async getAdmdvs() {
				const res = await getDicts({
					"type": "admdvs-area"
				});
				if (res.code == "0") {
					this.admdvs = res.data;
				}
			},
			handleSizeChange(val) {
				this.queryForm.pageSize = val
				this.fetchData()
			},
			handleCurrentChange2(val) {
				this.queryForm.pageNo = val
				this.fetchData()
			},
			//查看详情
			handlechuli(row, type) {
				this.$refs.edit.showDia(row, type);
			},
			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			async fetchData() {
				this.listLoading = true
				const res = await evalOrgTaskPage(this.queryForm)
				this.list = res.data.records;
				this.total = res.data.total;
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
			resetForm() {
				this.queryForm.taskName = ''
				this.queryForm.status = ''
				this.queryForm.orgName = ''
				this.queryForm.taskName = ''
				this.queryForm.year = ''
				this.queryData();
			}
		},
	}
</script>