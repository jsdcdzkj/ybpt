<template>
	<div class="main-container" v-loading="loading" element-loading-text="请稍等······"
		 element-loading-spinner="el-icon-loading"
		 element-loading-background="rgba(0, 0, 0, 0.8)">


		<el-row :gutter="20">
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">信息查询</span>
						<div class="right">
							<el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
							<el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>&nbsp;&nbsp;

						</div>
					</div>
					<el-form label-width="100px">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="省项目编码" >
									<el-input v-model.trim="queryForm.provincialProjectCode" @keyup.enter.native="queryData"/>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="医保目录名称" >
									<el-input v-model.trim="queryForm.directoryName" @keyup.enter.native="queryData"/>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="状态">
									<el-select v-model="queryForm.status" class="w" ref="dcla" @change="queryData" clearable >
										<el-option label="已生效" value="0"></el-option>
										<el-option label="待生效" value="1"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
						</el-row>
					</el-form>


				</el-card>
			</el-col>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">


					<div slot="header">

						<span class="tips">诊疗项目列表</span>
						<div>
							<el-button type="primary"  @click="upload">
								上传数据
							</el-button>
							<el-button class="ml2" icon="el-icon-download" type="primary" @click="handleAdd">
								备份数据导出
							</el-button>
							<el-button class="ml2" icon="el-icon-download" type="primary" @click="handleAdd2">
								导出
							</el-button>
						</div>
					</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="tableData"
							  :element-loading-text="elementLoadingText" highlight-current-row border
							  @current-change="handleCurrentChange" height="calc(100vh - 530px)">
						<template slot="empty">
							<el-empty :image-size="200"></el-empty>
						</template>



							<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px" ></el-table-column>
							<el-table-column show-overflow-tooltip label="省项目编码" align="center" prop="provincialProjectCode" width="120px">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="directoryName" label="医保目录名称" align="center" width="300px">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="chargeUnit" label="计价单位" align="center" width="120px"></el-table-column>
							<el-table-column show-overflow-tooltip prop="projectConnotation" label="项目内涵" align="center" width="300px"></el-table-column>
							<el-table-column show-overflow-tooltip prop="excludedContent" label="除外内容" align="center" width="300px"></el-table-column>
							<el-table-column show-overflow-tooltip prop="explain" label="说明" align="center" width="300px"></el-table-column>
							<el-table-column show-overflow-tooltip prop="chargeType" label="收费类别" align="center" width="120px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="levelOfChargeItem" label="收费项目等级" align="center" width="120px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="restrictedUseSign" label="限制使用标志" align="center" width="120px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="is_autonomy" label="是否自主项目" align="center" width="120px" >
								<template slot-scope="scope">
									<span v-show="scope.row.is_autonomy==0">否</span>
									<span v-show="scope.row.is_autonomy==1">是</span>
								</template>
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="nonChildOne" label="非儿童一级限价" align="center" width="120px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="nonChildTwo" label="非儿童二级限价" align="center" width="120px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="nonChildThree" label="非儿童三级限价" align="center" width="120px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="childOne" label="儿童一级限价（六周岁及以下）" align="center" width="220px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="childTwo" label="儿童二级限价（六周岁及以下）" align="center" width="220px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="childThree" label="儿童三级限价（六周岁及以下）" align="center" width="220px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="disabledSoldier" label="一至六级残疾军人二次限价" align="center" width="220px" ></el-table-column>
							<el-table-column show-overflow-tooltip prop="contentOfChange" label="变更内容" align="center" width="120px" ></el-table-column>
							<!--<el-table-column show-overflow-tooltip prop="addType" label="新增类型" align="center" width="120px" ></el-table-column>-->
					</el-table>

					<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
								   :layout="layout" :total="total" @size-change="handleSizeChange"
								   @current-change="handleCurrentChange2"></el-pagination>
				</el-card>
			</el-col>
		</el-row>
		<edit ref="edit" @fetch-data="fetchData"></edit>
		<upload ref="upload" @fetch-data="fetchData"></upload>
	</div>
</template>

<script>
	import Edit from './components/edit'
	import upload from './components/upload'
	import {
		zlProjectMedicineList,nloodList,nonProjectMedicineList,projectimportData,projectzcExport
	} from '@/api/drug'
	export default {
		name: 'drugsManage',
		components: {
			Edit,
			upload
		},
		data() {
			return {
				list: null,
				listLoading: false,
				loading: false,
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				selectRows: '',
				elementLoadingText: '正在加载...',
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					provincialProjectCode: '',
					directoryName: '',
				},
				tableData: []
			}
		},
		computed: {
			height() {
				return this.$baseTableHeight()
			},
		},
		created() {
			this.queryData()
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
			handleAdd2() {
				this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
					this.loading = true
					await projectzcExport(this.form).then((res) => {
						let fileName = "诊疗数据导出.xlsx";
						let objectUrl = URL.createObjectURL(new Blob([res.data]))
						const link = document.createElement('a')
						link.download = decodeURI(fileName)
						link.href = objectUrl
						link.click()
						this.loading = false;
						this.$baseMessage("导出成功！", 'success')
					})

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
			reseat(){
				this.queryForm.provincialProjectCode = "" ;
				this.queryForm.directoryName = "" ;
				this.queryForm.status = "" ;
				this.queryData()
			},
			async fetchData() {

				this.listLoading = true
					zlProjectMedicineList(this.queryForm).then((res) => {
						if (res.code == 0) {
							this.tableData = res.data.records
							this.total = res.data.total
							this.listLoading = false
						}
					})

			},
			async handleChange2(file, fileList) {
				this.loading = true
				this.fileList = fileList;
				let fd = new FormData();
				this.fileList.forEach(item => {
					//文件信息中raw才是真的文件
					fd.append("file", item.raw);
				})

				var result = await projectimportData(fd);
				if (result.data.code == 0) {
					this.queryData()
					this.$refs.upfile.clearFiles();
					this.$baseMessage("上传成功", 'success')
				} else {
					this.$refs.upfile.clearFiles();
					this.$baseMessage(result.data.msg, 'error')
				}
				this.loading = false
			},
			handleSuccess() {
				this.queryData()
			},
			upload(row) {
				this.$refs['upload'].showDia()
			},
		},
	}
</script>
<style scoped>
	.ml2{
		margin-left: 10px;
	}
</style>