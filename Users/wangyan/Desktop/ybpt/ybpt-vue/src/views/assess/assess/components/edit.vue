<template>
	<el-drawer :visible.sync="dialog" direction="rtl" :with-header="false" custom-class="box_drawer" size="80%"
		ref="drawer">
		<div class="drawer_content">
			<div class="drawer_main">
				<div class="box_card">
					<div class="box_header">
						<span>责任指标列表</span>
					</div>
					<el-table :data="tableList" border style="margin-top: 20px;" height="calc(100vh - 170px)"
						:span-method="arraySpanMethod" show-summary :summary-method="getSummaries" ref="orderTable">
						<el-table-column label="评价项目" align="" prop="name" min-width="200px">
							<template #default="{ row }">
								<span v-if="row.level==1">{{ row.name }}</span>
								<span style="margin-left: 20px;width: calc(100% - 20px);" v-else>{{ row.title }}</span>
							</template>
						</el-table-column>
						<el-table-column label="分值" align="center" width="80px">
							<template #default="{ row }">
								<span v-if="row.level==1">{{ row.score }}</span>
								<span v-else>{{ row.categoryScore }}</span>
							</template>
						</el-table-column>
						<el-table-column prop="content" label="评价内容" min-width="200px" align="">
						</el-table-column>
						<el-table-column label="评分办法" width="500px">
							<template #default="{ row }">
								<span v-if="row.level!==1">{{ row.name }}({{ row.methodsScore }}分)</span>
							</template>
						</el-table-column>
						<el-table-column label="负责人" align="center" width="150px">
							<template #default="{ row }">
								<div v-if="row.level!==1">
									<span>{{ row.userName }}</span>
								</div>
							</template>
						</el-table-column>
						<el-table-column label="操作" align="center" width="200px" fixed="right">
							<template #default="{ row }">
								<el-button plain type="success" size="mini" @click="handlechuli(row,2)"
									v-if="row.userId == userinfo.id && row.level!=1 && form.status == 2">执行考核
								</el-button>
								<el-button plain type="success" size="mini" @click="handlechuli(row,3)"
									v-if="row.userId == userinfo.id && row.level!=1 && form.status == 4">考核复审
								</el-button>
							</template>
						</el-table-column>
					</el-table>
				</div>
			</div>
			<div class="drawer_footer">
				<el-button @click="close">关 闭</el-button>
			</div>
			<taskMark ref="taskMark" :show.sync="showMark"></taskMark>
		</div>
	</el-drawer>
</template>

<script>
	import taskMark from './mark'
	import {
		getEvalTaskManage
	} from "@/api/eval";

	export default {
		name: 'edit',
		components: {
			taskMark
		},
		data() {
			return {
				userinfo: {},
				showMark: false,
				tableList: [],
				form: {},
				dialog: false,
				loading: false
			}
		},
		mounted() {
			this.userinfo = JSON.parse(localStorage.getItem("userinfo"))
		},
		methods: {
			flatData(data) {
				var arr = [];
				data.map((el, index) => {
					arr.push(Object.assign(el, {
						indexs: [index],
						level: 1
					}));
					if (el.evalCategoryStardards) {
						el.evalCategoryStardards.map((item, idx) => {
							const userId = item.userId;
							const source = {
								indexs: [index, idx],
								userId,
								level: 2,
								categoryScore: item.score == null ? '' : item.score,
								methodsScore: item.evalStardardMethods[0].score == null ? '' : item
									.evalStardardMethods[0].score,
								score: item.evalStardardMethods[0].evalOrgDetail == null ? '' : item
									.evalStardardMethods[0].evalOrgDetail.score,
							}
							arr.push(Object.assign(item, source, item.evalStardardMethods.shift()));
							if (item.evalStardardMethods) {
								item.evalStardardMethods.map((itm, idxs) => {
									const source = {
										indexs: [index, idx, idxs],
										userId,
										level: 3,
										methodsScore: itm.score == null ? '' : itm.score,
										score: itm.evalOrgDetail == null ? '' : itm
											.evalOrgDetail.score,
									}
									arr.push(Object.assign(itm, source));
								})
							}
						})
					}
				})
				console.log(arr)
				return arr;
			},
			// 合并单元格
			arraySpanMethod({
				row,
				columnIndex
			}) {
				if (row.level === 1) {
					return [1, 1];
				}
				if (row.level === 2) {
					if ([0, 1, 2, 4].includes(columnIndex)) {
						return {
							colspan: 1,
							rowspan: row.evalStardardMethods.length + 1
						};
					} else if ([3, 5].includes(columnIndex)) {
						return [1, 1];
					} else {
						return [1, 0];
					}
				} else {
					if ([0, 1, 2, 4].includes(columnIndex)) {
						return {
							colspan: 1,
							rowspan: 0
						};
					} else {
						return [1, 1];
					}
				}
			},
			//计算总分数
			getSummaries(params) {
				const {
					columns,
					data
				} = params;
				var sums = [];
				columns.forEach((column, index) => {
					if (index === 0) {
						sums[index] = '总计';
						return;
					} else if (index === 1) {
						sums[index] = data.reduce((a, b) => {
							if (b.level === 1) {
								return a + Number(b.score);
							} else {
								return a;
							}
						}, 0)
						sums[index] += '分';
					} else {
						sums[index] = '';
					}
				})
				return sums;
			},
			showDia(row) {
				this.tableList = []
				this.dialog = true
				this.form = Object.assign({}, row)
				console.log('form %o', this.form)
				this.getDetail()
			},
			handlechuli(row) {
				this.showMark = true
				this.$refs['taskMark'].showDia(this.form, row)
			},
			async getDetail() {
				await getEvalTaskManage({
					taskManageId: this.form.id,
					assessmentId: this.form.assessmentId
				}).then(res => {
					const data = JSON.parse(JSON.stringify(res.data));
					console.log('后台办法请求数据 %o', data.evalDesign.evalDesignCategorys)
					this.tableList = this.flatData(data.evalDesign.evalDesignCategorys);
					this.$nextTick(() => {
						this.$refs.orderTable.doLayout();
					})
				})
			},
			close() {
				this.dialog = false
			},
			cancelForm() {
				this.loading = false
				this.dialog = false
			}
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