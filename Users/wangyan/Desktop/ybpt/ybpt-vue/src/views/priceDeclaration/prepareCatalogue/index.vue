<template>
	<div class="main-container" v-loading="loading" element-loading-text="请稍等······"
		element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">


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
					<el-form label-width="160px">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="国家医疗机构制剂代码">
									<el-input v-model.trim="queryForm.nationalFormulaCode"
										@keyup.enter.native="queryData" placeholder="请输入国家医疗机构制剂代码" />
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="制剂名称">
									<el-input v-model.trim="queryForm.formulaName" @keyup.enter.native="queryData"
										placeholder="请输入制剂名称" />
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="制剂注册单位">
									<el-input v-model.trim="queryForm.registerCompanyName"
										@keyup.enter.native="queryData" placeholder="请输入制剂注册单位" />
								</el-form-item>
							</el-col>
						</el-row>
					</el-form>


				</el-card>
			</el-col>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">


					<div slot="header">

						<span class="tips">制剂目录列表</span>
						<div>
							<el-upload ref="upfile" :auto-upload="false" :show-file-list=false accept=".xlsx"
								:on-change="handleChange" :on-success="handleSuccess" action="#" :multiple="false">
								<el-button type="primary" icon="el-icon-upload">
									数据导入
								</el-button>
							</el-upload>
							<el-button class="ml2" icon="el-icon-download" type="primary" @click="handleDownLoad">
								数据导出
							</el-button>
						</div>
					</div>
					<el-table v-loading="listLoading" ref="listTable" stripe :data="tableData"
						:element-loading-text="elementLoadingText" highlight-current-row border
						@current-change="handleCurrentChange" height="calc(100vh - 530px)">
						<!-- <template slot="empty">
							<el-empty :image-size="200">暂无数据</el-empty>
						</template> -->
						<el-table-column show-overflow-tooltip type="index" label="序号" align="center"
							width="80px"></el-table-column>
						<el-table-column show-overflow-tooltip label="分类编码" align="center" prop="categoryCode"
							width="120px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="genericNameCode" label="药品通用名编码" align="center"
							width="160px">
						</el-table-column>
						<el-table-column show-overflow-tooltip prop="productNameCode" label="产品名编码" align="center"
							width="160px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="formulaName" label="制剂名称" align="center"
							width="240px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="goodsName" label="商品名" align="center"
							width="240px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="payType" label="支付类别" align="center"
							width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="dosageForm" label="剂型" align="center"
							width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="specs" label="规格" align="center"
							width="160px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="minPriceUnit" label="最小计价单位" align="center"
							width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="unit" label="单位" align="center"
							width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="minPackage" label="最小包装" align="center"
							width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="approvalNo" label="制剂批准文号" align="center"
							width="240px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="remark" label="备注" align="center"
							width="240px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="catalogCode" label="目录编号" align="center"
							width="160px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="registerCompanyName" label="制剂注册单位" align="center"
							width="300px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="selfPayRatio" label="个人先行自付比例" align="center"
							width="120px"></el-table-column>
						<el-table-column show-overflow-tooltip prop="nationalFormulaCode" label="国家医疗机构制剂代码"
							align="center" width="300px"></el-table-column>
					</el-table>

					<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
						:layout="layout" :total="total" @size-change="handleSizeChange"
						@current-change="handleCurrentChange2"></el-pagination>
				</el-card>
			</el-col>
		</el-row>
	</div>
</template>

<script>

import { getCatalogList, pcatalogimportData, catalogExport } from '@/api/prepareCatalogue'
export default {
	name: 'PrepareCatalogue',
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
				nationalFormulaCode: '',
				formulaName: '',
				registerCompanyName: '',
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
	beforeDestroy() { },
	mounted() { },
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

		async handleChange(file, fileList) {
			console.log('file', file);
			this.loading = true
			this.fileList = fileList;
			let fd = new FormData();
			fd.append("file", file.raw);
			var result = await pcatalogimportData(fd);
			if (result.data.code == 0) {
				this.queryData()
				this.$refs.upfile.clearFiles();
				this.$baseMessage("导入成功", 'success')
			} else {
				this.$refs.upfile.clearFiles();
				this.$baseMessage(result.data.msg, 'error')
			}
			this.dialogFormVisible = false
			this.loading = false
		},
		handleSuccess() {
			this.queryData()
		},

		//导出数据
		handleDownLoad() {
			this.$baseConfirm('确定要导出当前全部信息？', '提示', async () => {
				this.loading = true
				await catalogExport(this.queryForm).then((res) => {
					let fileName = "制剂目录导出.xlsx";
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

		//查询
		queryData() {
			this.queryForm.pageNo = 1
			this.fetchData()
		},
		//重置
		reseat() {
			this.queryForm.nationalFormulaCode = "";
			this.queryForm.formulaName = "";
			this.queryForm.registerCompanyName = "";
			this.queryForm.status = "";
			this.queryData()
		},
		//获取制剂目录列表数据
		async fetchData() {
			this.listLoading = true
			getCatalogList(this.queryForm).then((res) => {
				if (res.code == 0) {
					this.tableData = res.data.records
					this.total = res.data.total
					this.listLoading = false
				}
			})
		},

	},
}
</script>
<style scoped>
.ml2 {
	margin-left: 10px;
}
</style>