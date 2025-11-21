<template>
	<div class="main-container">
		<el-row :gutter="20">
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">查询条件</span>
						<el-button icon="el-icon-search" type="primary" class="right" @click="queryData">
							查 询
						</el-button>
					</div>
					<el-form label-width="80px">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="任务名称">
									<el-input v-model.trim="queryForm.task_name" placeholder="请输入" @keyup.enter.native="queryData"/>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="机构类型">
									<el-select v-model="queryForm.org_type" style="width: 100%" @change="getLevelList" clearable >
										<el-option value="1" label="医疗机构"></el-option>
										<el-option value="2" label="零售药店"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="协议等级">
									<el-select v-model="queryForm.aggrement_lv" style="width: 100%" @change="queryData" clearable>
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
									<el-date-picker v-model="queryForm.year_of_assessment" type="year" placeholder="选择年" format="yyyy"
													value-format="yyyy" @change="queryData">
									</el-date-picker>
								</el-form-item>
							</el-col>
						</el-row>
					</el-form>
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card tablecard" shadow="never">
					<div slot="header">
						<span class="tips">任务发布列表</span>
						<el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">
							新增
						</el-button>
					</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="list"
						:element-loading-text="elementLoadingText" highlight-current-row border
						@current-change="handleCurrentChange" height="calc(100% - 50px)">

						<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="任务名称" min-width="150px" align="center" prop="task_name">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="org_type" label="机构类型" align="center">
					    </el-table-column>
            <el-table-column show-overflow-tooltip prop="category_name" label="类别" align="center">
            </el-table-column>
						<el-table-column show-overflow-tooltip prop="aggrement_lv" label="协议等级" align="center">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="year_of_assessment" label="考核年度" align="center">
						</el-table-column>

						<el-table-column show-overflow-tooltip label="发布状态" align="center">
							<template #default="{ row }">
								<el-tag type="success"  :key="index" v-if="row.publish_status == 0">
								待发布
							</el-tag>
								<el-tag type="warning"  :key="index" v-else-if="row.publish_status == 1 ">
									更新发布
								</el-tag>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="expiration_time" label="过期时间" align="center">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="bbb2" label="考核单" align="center" min-width="50px">
							<template #default="{ row }">
								<el-link icon='el-icon-document' @click="handleDetail(row)">{{ row.bbb2 }}</el-link>
							</template>
						</el-table-column>

						<el-table-column show-overflow-tooltip label="操作" width="350" fixed="right" align="center">
							<template #default="{ row }">

								<el-button plain @click="handleRelease(row)" type="primary" size="mini" v-if="row.publish_status == 0 ">
								发布
							</el-button>
								<el-button plain @click="handleRelease(row)" type="primary" size="mini" v-if="row.publish_status == 1 ">
									更新发布
								</el-button>
								<el-button plain @click="handlecancel(row)" type="primary" size="mini" v-if="row.publish_status == 1">
									撤销
								</el-button>
								<el-button plain @click="handleEdit(row)" type="primary" size="mini" v-if="row.publish_status == 0">
									编辑
								</el-button>
								<el-button plain @click="handleDelete(row)" type="danger" size="mini" v-if="row.publish_status == 0">
									删除
								</el-button>
								<el-button plain @click="handleExport(row)" type="primary" size="mini" >
									导出机构
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
		<detail ref="detail" @fetch-data="fetchData"></detail>
	</div>
</template>

<script>
import Edit from './components/edit'
import Detail from './components/detail'
import {delTaskMange, exportData, publishTaskMange, selectTaskManageList, undoPublication} from '@/api/assessment.js'

export default {
		name: 'renwu',
		components: {
			Edit,
			Detail
		},
		data() {
			return {
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
				levelList: [],
				value1: '',
				value3: '',
				checked: false,
				isShow: false,
				list: null,
				listLoading: true,
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				selectRows: '',
				elementLoadingText: '正在加载...',
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					task_name: '',
					org_type: '',
					aggrement_lv: '',
					year_of_assessment: '',
				},
				tableData: [{
					id: 1,
					aaa1: 'aaa1',
					aaa2: 'aaa2',
					aaa3: 'aaa3',
					bbb1: '待发布',
					bbb2: 'bbb2',
					bbb3: 'bbb3'
				}, {
					id: 2,
					aaa1: 'aaa1',
					aaa2: 'aaa2',
					aaa3: 'aaa3',
					bbb1: ['待发布'],
					bbb2: 'bbb2',
					bbb3: 'bbb3'
				}],
			}
		},
		created() {
			this.fetchData();
		},
		beforeDestroy() {},
		mounted() {},
		methods: {
			handleCurrentChange(val) {
				this.selectRows = val
			},

			handleAdd() {
				this.$refs['edit'].showEdit()
			},
			handleEdit(row) {
				this.$refs['edit'].showEdit(row)
			},
			handleDetail(row) {
				this.$refs['detail'].showDia(row)
			},
			handleSizeChange(val) {
				this.queryForm.pageSize = val
				this.fetchData()
			},
			handleCurrentChange2(val) {
				this.queryForm.pageNo = val
				this.fetchData()
			},

			handlecancel(row) {
				var that = this ;
				if (row.id) {
					that.$confirm('确认进行撤消？', '提示', {
						confirmButtonText: '确定',
						cancelButtonText: '取消',
						type: 'warning'
					}).then(() => {
						that.listLoading = true

						undoPublication(row.id).then((res) => {
							if (res.code == 0) {
								this.listLoading = false ;
								this.$baseMessage("撤销成功", 'success')
								this.fetchData()
							}else {
								this.listLoading = false ;
								this.$baseMessage(res.msg, 'error')
								this.fetchData()
							}
						})


					}).catch(() => {
						that.$message({
							type: 'info',
							message: '已取消撤销'
						});
					});
				} else {

				}
			},

			handleRelease(row) {
				this.$baseConfirm('确认进行发布？', null, async () => {
					this.listLoading = true
					publishTaskMange(row.id).then((res) => {
						if (res.code == 0) {
							this.listLoading = false ;
							this.$baseMessage("发布成功", 'success')
							this.fetchData()
						}
					})

				})
			},
			handleDelete(row) {
				if (row.id) {
					this.$baseConfirm('你确定要删除当前项吗', null, async () => {
						delTaskMange(row.id).then((res) => {
							if (res.code == 0) {
								this.listLoading = false ;
								this.$baseMessage("删除成功", 'success')
								this.fetchData()
							}
						})
						this.$baseMessage(msg, 'success')
						this.fetchData()
					})
				} else {}
			},
			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			moreQuery() {
				this.isShow = !this.isShow
			},
			async fetchData() {
				this.listLoading = true
				selectTaskManageList(this.queryForm).then((res) => {
					if (res.code == 0) {
						this.list = res.data.records
						this.total = res.data.total
					}

				})
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
			getLevelList() {
				var that = this ;

				if (that.queryForm.org_type == 1) {
					that.levelList = that.aList;
					that.queryForm.aggrement_lv ="" ;
				} else if (that.queryForm.org_type == 2) {
					that.levelList = that.bList;
					that.queryForm.aggrement_lv ="" ;
				} else {
					that.levelList = []
				}

				if(that.queryForm.aggrement_lv != undefined){
					that.queryForm.aggrement_lv ="" ;
				}
				this.queryData() ;
			},
			handleExport(row) {
				this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
					this.listLoading = true
					await exportData(row.id).then((res) => {
						let fileName = '考核机构数据导出.xlsx'
						let objectUrl = URL.createObjectURL(new Blob([res.data]))
						const link = document.createElement('a')
						link.download = decodeURI(fileName)
						link.href = objectUrl
						link.click()
						this.listLoading = false
						this.$baseMessage('导出成功！', 'success')
					})
				})
			},
			formatDate: function (row, column) {
				let data = row[column.property]
				console.log(data);
				if (data == null || data =="") {
					return null
				}
				let date = new Date(data);
				var o = {
					"M+": date.getMonth() + 1,                 //月份
					"d+": date.getDate(),                    //日
					"h+": date.getHours(),                   //小时
					"m+": date.getMinutes(),                 //分
					"s+": date.getSeconds(),                 //秒
					"q+": Math.floor((date.getMonth() + 3) / 3), //季度
					"S": date.getMilliseconds()             //毫秒
				};
				var fmt = "yyyy-MM-dd";
				if (/(y+)/.test(fmt)) {
					fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
				}
				for (var k in o) {
					if (new RegExp("(" + k + ")").test(fmt)) {
						fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
					}
				}
				return fmt;
			},
		},

	}
</script>
