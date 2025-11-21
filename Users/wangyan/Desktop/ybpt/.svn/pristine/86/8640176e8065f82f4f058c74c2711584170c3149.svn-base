<template>
	<div class="main-container">
		<el-row :gutter="20">
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">信息查询</span>
					</div>
					<el-form label-width="160px">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="考核单名称">
									<el-input v-model.trim="queryForm.title" placeholder="请输入"
										@keyup.enter.native="queryData" clearable/>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="机构类型">
									<el-select v-model="queryForm.orgType" style="width: 100%" @change="getLevelList" clearable >
										<el-option value="1" label="医疗机构"></el-option>
                      <el-option value="2" label="零售药店"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="协议等级">
									<el-select v-model="queryForm.aggrementLv" placeholder="请选择" style="width: 100%" clearable
										>
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
								<el-form-item label="机构类别">
									<el-select v-model="queryForm.category" placeholder="请选择" style="width: 100%" clearable
										>
										<el-option label="门诊" value="1"></el-option>
                    <el-option label="住院" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="精神专科">
									<el-select v-model="queryForm.spirit" placeholder="请选择" style="width: 100%" clearable
										>
										<el-option label="非精神专科" value="0"></el-option>
                    <el-option label="精神专科" value="1"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="经营性质">
									<el-select v-model="queryForm.natures" placeholder="请选择" style="width: 100%" clearable
										>
										<el-option label="公立" value="1"></el-option>
                    <el-option label="私立" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="考核年度">
									<el-date-picker v-model="queryForm.year" type="year" placeholder="选择年" format="yyyy"
										value-format="yyyy" clearable>
									</el-date-picker>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<vab-query-form>
									<vab-query-form-right-panel :span="24">
										<el-form-item class="mr0">
											<el-button icon="el-icon-refresh-left" @click="resetForm">重 置</el-button>
											<el-button icon="el-icon-search" @click="queryData" type="primary">
												查 询
											</el-button>
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
						<span class="tips">考核单信息</span>
						<el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">新增</el-button>
					</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="list"
						element-loading-text="正在加载..." highlight-current-row border
						height="calc(100vh - 570px)">
						<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
							<template #default="{ $index }">
								{{(queryForm.pageNo-1)*queryForm.pageSize + $index+1}}
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="考核单名称" align="center" prop="title">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="机构类型" align="center" prop="orgType" width="180px"  :formatter="getOrgType">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="category" width="150px" label="机构类别" align="center"  :formatter="getCategory">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aggrementLv" label="协议等级" align="center" width="120px"  :formatter="getAggrementLv">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="spirit" label="精神专科" align="center" width="180px"  :formatter="getSpirit">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="natures" label="经营性质" align="center" width="150px"  :formatter="getNatures">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="year" label="考核年度" align="center"
							width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="操作" width="280" align="center" fixed="right">
							<template #default="{ row }">
								<el-button plain @click="handlechuli(row,0)" type="primary" size="mini">
									编辑
								</el-button>
								<el-button plain @click="handlecancel(row)" type="danger" size="mini" v-show="row.isFlag==0">
									删除
								</el-button>
								<el-button plain @click="handlechuli(row,1)" type="success" size="mini">
									复制
								</el-button>
							</template>
						</el-table-column>
					</el-table>
					<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
						:layout="layout" :total="queryForm.total" @size-change="handleSizeChange"
						@current-change="handleCurrentChange2"></el-pagination>
				</el-card>
			</el-col>
		</el-row>
		<edit ref="edit" @fetch-data="fetchData" ></edit>
	</div>
</template>

<script>
	import {
upData
} from '@/api/common.js'
import { del, getPageList } from '@/api/eval'
import Edit from './components/edit'
	export default {
		name: 'Criterion',
		components: {
			Edit
		},
		data() {
			return {
				levelList: [],
				aList: [
					{ id: 1, name: '一级' },
					{ id: 2, name: '二级' },
					{ id: 3, name: '三级' },
					{ id: 9, name: '未定级' },
				],
				bList: [
					{ id: 4, name: 'A级' },
					{ id: 5, name: 'B级' },
					{ id: 6, name: 'C级' },
				],
				list: [{},{},{},{}],
				listLoading: false,
				layout: 'total, sizes, prev, pager, next, jumper',
				cardTypes: [], //证件类型
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					total: 0,
					title: '',
					aggrementLv:'',
					username: '',
					cardType: '',
					natures:'',
					category: '',
					year:'',
					orgType: '',
					spirit:''
				},
			}
		},
		created() {
			this.fetchData()
			// this.getCarType();
		},
		mounted() {},
	methods: {
		getOrgType(row,column){
      var statusW;
      switch (row.orgType) {
        case "1":statusW= "医疗机构";break;
        case "2":statusW= "零售药店";break;
      }
      return statusW
		},
		getNatures(row,column){
      var statusW;
      switch (row.natures) {
        case "2":statusW= "私立";break;
        case "1":statusW= "公立";break;
      }
      return statusW
		},
		getSpirit(row,column){
      var statusW;
      switch (row.spirit) {
        case "0":statusW= "非精神专科";break;
        case "1":statusW= "精神专科";break;
      }
      return statusW
		},
		getAggrementLv(row,column){
      var statusW;
      switch (row.aggrementLv) {
        case "9":statusW= "未定级";break;
		  case "1": statusW = "一级"; break;
		  case "2": statusW = "二级"; break;
		  case "3": statusW = "三级"; break;
		  case "4": statusW = "A级"; break;
		  case "5": statusW = "B级"; break;
		  case "6": statusW = "C级";break;
      }
      return statusW
		},
		getCategory(row,column){
      var statusW;
      switch (row.category) {
        case "1":statusW= "门诊";break;
        case "2":statusW= "住院";break;
      }
      return statusW
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
				// this.queryData() ;
			},
			handleAdd() {
				this.$refs['edit'].showDia();
			},
			handleSizeChange(val) {
				this.queryForm.pageSize = val
				this.fetchData()
			},
			handleCurrentChange2(val) {
				this.queryForm.pageNo = val
				this.fetchData()
			},
		handlechuli(row,index) {
			row.isCopy = index
			
				this.$refs['edit'].showDia(row);
			},
			handlecancel(row) {
				this.$baseConfirm('确认进行删除？', '温馨提示', async () => {
					const {
						msg
					} = await del({
						id: row.id
					})
					this.$baseMessage(msg, 'success')
					this.queryData()
				})
			},
			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			getCarType() {
				var that = this;
				upData('PSN_CERT_TYPE').then((res) => {
					if (res.code == 0) {
						that.cardTypes = res.data;
					}
				})
			},
			async fetchData() {
				this.listLoading = true
				const res = await getPageList(this.queryForm)
				this.list = res.data.records;
				this.queryForm.total = res.data.total;
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
			resetForm() {
				this.queryForm = this.$options.data().queryForm;
				this.queryData();
			}
		},
	}
</script>