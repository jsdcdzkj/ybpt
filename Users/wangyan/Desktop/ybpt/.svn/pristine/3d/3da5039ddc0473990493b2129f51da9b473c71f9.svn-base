<template>
	<div class="main-container">
		<el-row :gutter="20">
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">耗材列表</span>
						<el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">
							新增
						</el-button>
					</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="tableData"
						:element-loading-text="elementLoadingText" highlight-current-row border
						@current-change="handleCurrentChange" :height="height">
						<template slot="empty">
							<el-empty :image-size="200"></el-empty>
						</template>

						<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="批次" align="center" prop="aaa1">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aaa1" label="创建时间" align="center">
					    </el-table-column>
						<el-table-column show-overflow-tooltip prop="aaa2" label="创建人" align="center">
						</el-table-column>
						
						<el-table-column show-overflow-tooltip label="操作" width="300" align="center">
							<template #default="{ row }">
								<el-button plain @click="handleRelease(row)" type="primary" size="mini">
									发布
								</el-button>
								<el-button plain @click="handleDownload(row)" type="primary" size="mini">
									下载
								</el-button>
								<el-button plain @click="handleDelete(row)" type="danger" size="mini">
									删除
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
	import Edit from './components/edit'
	import {
		getList,
		doDelete,
		doEdit
	} from '@/api/userManagement'
	export default {
		name: 'consumableManage',
		components: {
			Edit,
		},
		data() {
			return {
				list: null,
				listLoading: true,
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				selectRows: '',
				elementLoadingText: '正在加载...',
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					username: '',
				},
				tableData: [{
					id: 1,
					aaa1: 'aaa1',
					aaa2: 'aaa2',
					aaa3: 'aaa3',
					bbb1: ['待发布'],
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
				}]
			}
		},
		computed: {
		  height() {
		    return this.$baseTableHeight()
		  },
		},
		created() {
			this.fetchData()
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
			
			handleSizeChange(val) {
				this.queryForm.pageSize = val
				this.fetchData()
			},
			handleCurrentChange2(val) {
				this.queryForm.pageNo = val
				this.fetchData()
			},

			handleDownload(row) {
				if (row.id) {
					this.$baseConfirm('确认进行下载？', null, async () => {
						const {
							msg
						} = await doEdit({
							ids: row.id
						})
						this.$baseMessage(msg, 'success')
						this.fetchData()
					})
				} else {}
			},

			handleRelease(row) {
				this.$baseConfirm('确认进行发布？', null, async () => {
					const {
						msg
					} = await doEdit({
						ids: row.id
					})
					this.$baseMessage(msg, 'success')
					this.fetchData()
				})
			},
			handleDelete(row) {
				if (row.id) {
					this.$baseConfirm('你确定要删除当前项吗', null, async () => {
						const {
							msg
						} = await doDelete({
							ids: row.id
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
			async fetchData() {
				this.listLoading = true
				const {
					data,
					totalCount
				} = await getList(this.queryForm)
				this.list = data
				this.total = totalCount
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
		},
	}
</script>
