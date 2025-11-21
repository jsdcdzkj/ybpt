<template>
	<div class="main-container">
		<el-row :gutter="20">
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">查询条件</span>
						<div class="right">
						  <el-button icon="el-icon-search" type="primary">查 询</el-button>
						  <el-button icon="el-icon-refresh-left">重 置</el-button>
						</div>
					</div>
					<el-form label-width="80px">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="审核状态">
									<el-select v-model="queryForm.username" style="width: 100%">
										<el-option label="审核中" value="1"></el-option>
										<el-option label="已审核" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="国家编码">
									<el-input v-model.trim="queryForm.pageNo" placeholder="请输入" />
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="申报类型">
									<el-select v-model="queryForm.username" style="width: 100%">
										<el-option label="医疗服务" value="1"></el-option>
										<el-option label="药品" value="2"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="统筹区" prop="username">
								  <el-cascader
								            :options="options"
								            class="w"
								            placeholder="可搜索"
								            filterable
								            clearable
								            @change="handleChange"
								          ></el-cascader>
								</el-form-item>
							</el-col>
						</el-row>
					</el-form>
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">告知手续审核列表</span>
					</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="tableData"
						:element-loading-text="elementLoadingText" highlight-current-row border
						@current-change="handleCurrentChange" height="420px">
						<template slot="empty">
							<el-empty :image-size="200"></el-empty>
						</template>

						<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="申报类型" align="center" prop="aaa1">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aaa1" label="国家编码" align="center">
					    </el-table-column>
						<el-table-column show-overflow-tooltip prop="aaa2" label="机构名称" align="center">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aaa3" label="申请时间" align="center">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="审核状态" align="center">
							<template #default="{ row }">
								<el-tag type="danger" v-if="row.bbb1==0" >未审核</el-tag>
								<el-tag type="warning" v-else-if="row.bbb1==1" >已初审</el-tag>
								<el-tag type="warning" v-else-if="row.bbb1==2" >已复审</el-tag>
								<el-tag type="warning" v-else-if="row.bbb1==3" >已终审</el-tag>
								<el-tag type="success" v-else="row.bbb1==4" >已审核</el-tag>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aaa1" label="审核人" align="center">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aaa1" label="审核时间" align="center">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="aaa2" label="驳回理由" align="center">
						</el-table-column>					
						<el-table-column show-overflow-tooltip label="操作" width="300" align="center">
							<template #default="{ row }"> 
								<el-button plain @click="handleView(row)" type="primary" size="mini">
									查看
								</el-button>
								<el-button plain @click="handleRelease(row)" type="primary" size="mini">
									通过
								</el-button>
								<el-button plain @click="handleReject(row)" type="primary" size="mini">
									驳回
								</el-button>
								<el-button plain @click="handleDownload(row)" type="primary" size="mini">
									下载
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
		<el-dialog
		  title="驳回"
		  :visible.sync="dialogVisible"
		  width="30%"
		  append-to-body>
		  <el-form :model="reasonForm" ref="reasonForm" :rules="rules" label-width="80px">
		    <el-form-item label="驳回原因" prop="name">
		      <el-input v-model="reasonForm.name" type="textarea" rows="4" placeholder="请输入驳回原因" />
		    </el-form-item>
		    
		  </el-form>
		  <span slot="footer" class="dialog-footer">
		    <el-button @click="dialogVisible = false">取 消</el-button>
		    <el-button type="primary" @click="rejectSave">确 定</el-button>
		  </span>
		</el-dialog>
		<detail ref="detail" @fetch-data="fetchData"></detail>
		<pass ref="pass" @fetch-data="fetchData"></pass>
		
	</div>
</template>

<script>
	import Detail from './components/detail'
	import Pass from './components/pass'
	import { regionDataPlus, CodeToText } from 'element-china-area-data'
	import {
		getList,
		doDelete,
		doEdit
	} from '@/api/userManagement'
	export default {
		name: 'processExamine',
		components: {
			Detail,
			Pass
		},
		data() {
			return {
				dialogVisible: false,
				reasonForm: {
					name: '测试',
				},
				 rules: {
				  name: [
					{ required: true, message: '请输入', trigger: 'blur' }
				  ]
				},
				options: regionDataPlus,
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
					username: '',
				},
				tableData: [{
					id: 1,
					aaa1: 'aaa1',
					aaa2: 'aaa2',
					aaa3: 'aaa3',
					bbb1: 0,
					bbb2: 'bbb2',
					bbb3: 'bbb3'
				}, {
					id: 2,
					aaa1: 'aaa1',
					aaa2: 'aaa2',
					aaa3: 'aaa3',
					bbb1: 1,
					bbb2: 'bbb2',
					bbb3: 'bbb3'
				},{
					id: 3,
					aaa1: 'aaa1',
					aaa2: 'aaa2',
					aaa3: 'aaa3',
					bbb1: 2,
					bbb2: 'bbb2',
					bbb3: 'bbb3'
				},{
					id: 4,
					aaa1: 'aaa1',
					aaa2: 'aaa2',
					aaa3: 'aaa3',
					bbb1: 3,
					bbb2: 'bbb2',
					bbb3: 'bbb3'
				},{
					id: 5,
					aaa1: 'aaa1',
					aaa2: 'aaa2',
					aaa3: 'aaa3',
					bbb1: 4,
					bbb2: 'bbb2',
					bbb3: 'bbb3'
				},]
			}
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
			
			handleSizeChange(val) {
				this.queryForm.pageSize = val
				this.fetchData()
			},
			handleCurrentChange2(val) {
				this.queryForm.pageNo = val
				this.fetchData()
			},
			handleReject(row){
				this.dialogVisible = true
			},
			handleView(row){
				this.$refs['detail'].showDia()
			},
			rejectSave() {
				this.dialogVisible = false
				this.$baseMessage('驳回成功！', 'success')
				this.dialog = false
				this.fetchData()
			},
			

			handleRelease(row) {
				if (row.bbb1 == 3) {
					this.$refs['pass'].showEdit()
				} else{
					this.$baseConfirm('确认审核通过？', null, async () => {
						const {
							msg
						} = await doEdit({
							ids: row.id
						})
						this.$baseMessage(msg, 'success')
						this.fetchData()
					})
				}
				
			},
			handleDownload(row) {
				this.$baseConfirm('确认下载？', null, async () => {
					this.$baseMessage('已下载，请稍后！', 'success')
				})
			},
			handleChange(value) {
			  let cityNames = []
			  value.forEach((e) => {
			    cityNames.push(CodeToText[e])
			  })
			  this.citys = cityNames.join('/')
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
