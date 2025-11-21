<template>
	<div class="main-container" v-loading="loading" element-loading-text="上传中······"
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
								<el-form-item label="国家药品代码">
									<el-input v-model.trim="queryForm.nationalDrugCode" @keyup.enter.native="queryData"/>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="药品名称">
									<el-input v-model.trim="queryForm.drugNames" @keyup.enter.native="queryData"/>
								</el-form-item>
							</el-col>
						</el-row>
					</el-form>
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">


					<div slot="header">

						<span class="tips">药品列表</span>

						<el-upload ref="upfile"
								   :auto-upload="false" :show-file-list=false accept=".xlsx"
								   :on-change="handleChange2"
								   :on-success="handleSuccess" action="#">
							<el-button icon="el-icon-upload2" type="success">上传/更新数据</el-button>

						</el-upload>
					</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="tableData"
							  :element-loading-text="elementLoadingText" highlight-current-row border
							  height="calc(100vh - 230px)"
							  @current-change="handleCurrentChange" :height="height">
						<template slot="empty">
							<el-empty :image-size="200"></el-empty>
						</template>

						<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px" fixed="left">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="分类编码" align="center" prop="sortingCodeNumber" fixed="left" width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="common_name_code" label="药品通用名编码" align="center" fixed="left" width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="drugNames" label="医保药品名称" align="center" fixed="left" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="paymentCategory" label="医保支付类别" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="actualDosageForm" label="实际剂型" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="productNameCoding" label="产品名编码" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="registeredName" label="注册名称" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="productName" label="商品名称" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="actualDosageForm" label="实际剂型" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="actualSpecification" label="实际规格" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="packagingMaterial" label="包装材质" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="minimumPackingQuantity" label="最小包装数量" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="unit" label="单位" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="valorize" label="政府定价（元）" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="purchaseCeilingPrice" label="省集中采购上限价" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="paymentCriteria" label="医保支付标准" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="approvalNumber" label="批准文号" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="medicineEnterprise" label="药品企业" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="limitPayment" label="医保限定支付范围" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="serialNumber" label="编号" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="bidDeclarationNumber" label="招标申报编号" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="remark" label="备注" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="nationalDrugCode" label="国家药品代码" align="center" width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="reasonsForChange" label="变更原因" align="center" width="120px"></el-table-column>
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
		chinesePatentMedicineList,patentMedicineimportData
	} from '@/api/drug'
	export default {
		name: 'drugsManage',
		components: {
			Edit,
		},
		data() {
			return {
				list: null,
				listLoading: true,
				loading: false,
				fileList:[],
				layout: 'total, sizes, prev, pager, next, jumper',
				total: 0,
				selectRows: '',
				elementLoadingText: '正在加载...',
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					nationalDrugCode: '',
					drugNames: '',
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
			reseat(){
				this.queryForm.nationalDrugCode = "" ;
				this.queryForm.drugNames = "" ;
				this.queryData()
			},
			async fetchData() {
				this.listLoading = true
				chinesePatentMedicineList(this.queryForm).then((res) => {
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

				var result = await patentMedicineimportData(fd);
				if (result.data.code == 0) {
					this.$refs.upfile.clearFiles();
					this.queryData()
					this.$baseMessage("上传成功", 'success')
				} else {
					this.$baseMessage(result.data.msg, 'error')
				}
				this.loading = false
			},
			handleSuccess() {
				this.queryData()
			},
		},
	}
</script>
