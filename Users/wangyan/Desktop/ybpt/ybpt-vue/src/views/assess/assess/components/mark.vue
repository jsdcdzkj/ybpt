<template>
	<el-drawer :visible.sync="show" direction="rtl" :with-header="false" custom-class="box_drawer" size="80%"
		ref="drawer" append-to-body :wrapperClosable="false">
		<div class="drawer_content">
			<div class="drawer_main">
				<div class="box_card">
					<div class="box_header">
						<span>评价内容</span>
					</div>
					<el-form label-width="auto">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="国家编码">
									<el-input v-model.trim="queryForm.orgCode" placeholder="请输入"
										@keyup.enter.native="queryData" clearable />
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
								<el-form-item label="考核状态">
									<el-select v-model="queryForm.status" placeholder="请选择" style="width: 100%"
										@change="queryData" clearable>
										<el-option v-if="form.status == 2" label="待初审" value="1"></el-option>
										<el-option v-if="form.status == 2" label="已初审" value="2"></el-option>
										<el-option v-if="form.status == 4" label="待复审" value="2"></el-option>
										<el-option v-if="form.status == 4" label="已复审" value="3"></el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="10" :lg="10" :xl="10" :offset="2">
								<vab-query-form>
									<vab-query-form-right-panel :span="24">
										<el-form-item class="mr0">
											<el-button icon="el-icon-refresh-left" @click="resetForm()">重 置</el-button>
											<el-button icon="el-icon-search" @click="queryData" type="primary">
												查 询
											</el-button>
                      <el-button type="primary" icon="el-icon-bottom" @click="downScope">分值导出</el-button>
                      <el-button type="primary" icon="el-icon-bottom" @click="todow">下载导入模版</el-button>
											<el-upload accept=".xls,.xlsx"
												style="display: inline-block;margin-left: 10px;" :show-file-list=false
												:on-change="(file, fileList) => { return handleScore(file, fileList)}"
												:auto-upload="false" action="#">
												<el-button type="primary">分值导入</el-button>
											</el-upload>
											<el-upload accept=".xls,.xlsx"
												v-if="tableRow.configuration == 1 && form.status != 4"
												style="display: inline-block;margin-left: 10px;" :show-file-list=false
												:on-change="(file, fileList) => { return handleTargetScore(file, fileList)}"
												:auto-upload="false" action="#">
												<el-button type="primary">指标导入</el-button>
											</el-upload>
											<el-button style="margin-left: 10px" type="success"
												@click="batchAudit">批量确认</el-button>
										</el-form-item>
									</vab-query-form-right-panel>
								</vab-query-form>
							</el-col>
						</el-row>
					</el-form>
					<div class="contentLabel" ref="labelHeight">
						{{ tableRow.name }}({{ tableRow.methodsScore }}分)
					</div>
					<el-table :data="tableList" v-loading="listLoading" ref="listTable" border style="margin-top: 20px;"
						:height="tableHeight" @selection-change="handleSelectionChange">
						<el-table-column type="selection" align="center"></el-table-column>
						<el-table-column show-overflow-tooltip label="机构名称" align="" prop="orgName" width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="机构代码" prop="orgCode" align="center" width="200px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="指标值" align="center" width="150px">
							<template slot-scope="scope">
								<el-input v-if="tableRow.configuration == 1 && form.status != 4" type="number" min="0"
									v-model="scope.row.targetScore"></el-input>
								<span
									v-if="tableRow.configuration == 1 && form.status == 4">{{ scope.row.targetScore }}</span>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="比例排名" prop="rankRate" align="center"
							width="150px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="分值" width="100px" align="center">
							<template slot-scope="scope">
								<el-input type="number" min="0" v-model="scope.row.score"></el-input>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="附件" align="center" width="200px">
							<template #default="{ row }">
								<el-dropdown style="cursor: pointer;" @command="downloadFile($event,row.files)"
									v-if="row.files&&row.files.length">
									<span class="el-dropdown-link">
										下载文件<i class="el-icon-arrow-down el-icon--right"></i>
									</span>
									<el-dropdown-menu slot="dropdown">
										<el-dropdown-item icon="el-icon-download" :command="index"
											v-for="item,index in row.files" :key="index">{{ item.name }}
										</el-dropdown-item>
									</el-dropdown-menu>
								</el-dropdown>
							</template>

						</el-table-column>
						<el-table-column show-overflow-tooltip prop="memo" label="描述">
						</el-table-column>
						<el-table-column label="反馈文件" align="center" width="200">
							<template #default="{ row }">
								<el-link type="primary" v-if="row.appealFiles&&row.appealFiles[0]" @click="downloadFile(0,row.appealFiles)">{{row.appealFiles[0].name}}</el-link>
							</template>
						</el-table-column>
						<el-table-column label="反馈描述" align="center" width="200" prop="appealCount"></el-table-column>
						<el-table-column show-overflow-tooltip label="状态" align="" width="100px">
							<template #default="{ row }">
								<el-tag v-if="row.status == -1">暂存</el-tag>
								<el-tag v-if="form.status == 2 && row.status == 1">待初审</el-tag>
								<el-tag type="success" v-if="form.status == 2 && row.status == 2">已初审</el-tag>
								<el-tag v-if="form.status == 4 && row.status == 2">待复审</el-tag>
								<el-tag type="success" v-if="form.status == 4 && row.status == 3">已复审</el-tag>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="日志" align="center" width="120px">
							<template #default="{ row }">
								<i class="el-icon-tickets" style="font-size: 20px;cursor: pointer;"
									@click="getLogList(row)"></i>
							</template>
						</el-table-column>
						<el-table-column show-overflow-tooltip label="负责人" prop="userName" align="center" width="150px">
						</el-table-column>
						<el-table-column show-overflow-tooltip label="操作" align="center" width="120px">
							<template #default="{ row }">
								<el-button plain type="success" size="mini" @click="confirmEval(row)">确认考核</el-button>
							</template>
						</el-table-column>
					</el-table>
					<el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
						layout="total, sizes, prev, pager, next, jumper" :total="total" @size-change="handleSizeChange"
						@current-change="handleCurrentChange2"></el-pagination>
				</div>
			</div>
			<el-dialog title="日志信息" :visible.sync="showLog" width="800px" append-to-body>
				<el-table :data="logList" border max-height="600px">
					<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="50">
					</el-table-column>
					<el-table-column show-overflow-tooltip label="分数" align="center" prop="score">
					</el-table-column>
					<el-table-column show-overflow-tooltip label="操作人" align="center" prop="createUserName">
					</el-table-column>
					<el-table-column show-overflow-tooltip label="操作时间" align="center" prop="createTime">
					</el-table-column>
				</el-table>
			</el-dialog>
			<div class="drawer_footer">
				<el-button @click="close">关 闭</el-button>
			</div>
		</div>
	</el-drawer>
</template>

<script>
import {
  audit, evalOrgDetailExport, evalOrgTaskExport,
  getOrgDetailLog,
  getOrgDetailPage,
  importScore,
  importTargetScore,
  setOrgDetail,
} from "@/api/eval";
	import {
		fileURL
	} from "@/config/setting.config";

	export default {
		name: 'taskMark',
		props: {
			show: {
				type: Boolean,
				default: false
			},
			selectRows: '',
			row: {},
			id: {
				type: Number,
				default: 0
			}
		},
		data() {
			return {
				listLoading: false,
				userinfo: {},
				tableList: [],
				logList: [],
				cardTypes: [],
				tableHeight: '100%',
				showLog: false,
				total: 100,
				form: {},
				tableRow: {},
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					taskManageId: '',
					earnestMoneyId: '',
					orgCode: '',
					status: '',
				},
				multipleSelection: []
			}
		},
		mounted() {
			this.userinfo = JSON.parse(localStorage.getItem("userinfo"))
		},
		watch: {
			show(val) {
				if (val) {
					this.setHeight();
				}
			}
		},
		methods: {
			async showDia(form, tableRow) {
				this.form = Object.assign({}, form)
				this.tableRow = Object.assign({}, tableRow)
				console.log('form %o', this.form)
				console.log('tableRow %o', tableRow)
				this.queryForm.orgCode = ''
				this.queryForm.status = ''
				this.queryForm.taskManageId = form.id
				this.queryForm.earnestMoneyId = tableRow.id
				await this.fetchData()
			},
			async handleScore(file, fileList) {
				let fd = new FormData()
				fd.append("file", file.raw)
				fd.append("taskManageId", this.form.id)
				fd.append("score", this.tableRow.methodsScore)
				fd.append("evalStardardMethodId", this.tableRow.id)
				fd.append("fromStatus", this.form.status)

				//全屏遮罩
				const loading = this.$loading({
					lock: true,
					text: '提交中，请稍等...',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
				await importScore(fd).then((res => {
					if (res.data.code == 0) {
						this.$emit('fetch-data')
						this.fetchData()
					} else {
						this.$message({
							showClose: true,
							message: res.data.msg,
							type: 'error'
						});
					}
					setTimeout(() => {
						loading.close();
					}, 1000);
				}))
			},
      downScope(){
        this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
          this.listLoading = true
          await evalOrgDetailExport(this.queryForm).then((res) => {
            let fileName = "分值信息导出.xlsx";
            let objectUrl = URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.download = decodeURI(fileName)
            link.href = objectUrl
            link.click()
            this.listLoading = false;
            this.$baseMessage("导出成功！", 'success')
          })

        })
      },
      todow(){
        window.open(fileURL + "/file/template/绩效考核分值导入模板.xlsx", "_blank");
        // self.location.href = fileURL + "/file/template/绩效考核分值导入模板.xlsx" ;
      },
			async handleTargetScore(file, fileList) {
				let fd = new FormData()
				fd.append("file", file.raw)
				fd.append("taskManageId", this.form.id)
				fd.append("score", this.tableRow.score)
				fd.append("evalStardardMethodId", this.tableRow.id)
				fd.append("fromStatus", this.form.status)

				//全屏遮罩
				const loading = this.$loading({
					lock: true,
					text: '提交中，请稍等...',
					spinner: 'el-icon-loading',
					background: 'rgba(0, 0, 0, 0.7)'
				});
				await importTargetScore(fd).then((res => {
					if (res.data.code == 0) {
						this.$emit('fetch-data')
						this.fetchData()
					} else {
						this.$message({
							showClose: true,
							message: res.data.msg,
							type: 'error'
						});
					}
					setTimeout(() => {
						loading.close();
					}, 1000);
				}))

			},
			close() {
				this.$emit('update:show', false);
			},
			setSelectRows(val) {
				this.selectRows = val
			},
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
			queryData() {
				this.queryForm.pageNo = 1
				this.fetchData()
			},
			async fetchData() {
				this.listLoading = true
				console.log('queryForm %o', this.queryForm)
				const res = await getOrgDetailPage(this.queryForm)
				this.tableList = res.data.records
				this.total = res.data.total
				setTimeout(() => {
					this.listLoading = false
				}, 300)
			},
			async getLogList(row) {
				const that = this
				getOrgDetailLog({
					id: row.id
				}).then(res => {
					that.logList = res.data
				})
				that.showLog = true
			},
			resetForm() {
				this.queryForm.orgCode = ''
				this.queryForm.status = ''
				this.fetchData()
			},
			confirmEval(row) {
				const that = this
        if(row.status == 4){
          if(!(row.score && parseFloat(row.score) <= parseFloat(this.tableRow.methodsScore))){
            that.$baseMessage("输入分值不得大于评分办法最大分值，请重新输入分值！", 'error')
            return;
          }
        }else{
          if(row.targetScore || row.score){
            if(!(row.score && parseFloat(row.score) <= parseFloat(this.tableRow.methodsScore))){
              that.$baseMessage("输入分值不得大于评分办法最大分值，请重新输入分值！", 'error')
              return;
            }
          }else{
            that.$baseMessage("自定义类型的指标，指标值或分值需要填写其中一个", 'error')
            return;
          }
        }

				//考核任务管理 0未发布  1已发布  2初审开始 3初审结束 4开启复审 5复审结束 6待计算保证金 7完成
				//考核任务 -1暂存 1待初审 2已初审 3已复审
				const detail = {
					id: row.id,
					userName: row.userName,
					userId: row.userId,
					evalOrgTaskId: row.evalOrgTaskId,
					taskManageId: row.taskManageId,
					orgName: row.orgName,
					orgCode: row.orgCode,
					status: that.form.status == 2 ? 2 : 3,
					score: row.score,
					targetScore: row.targetScore,
					memo: row.memo,
				}
				setOrgDetail(detail).then(res => {
					if (res.code == 0) {
						if (that.form.status == 2) {
							row.status = 2
						}
						if (that.form.status == 4) {
							row.status = 3
						}
						that.$baseMessage("操作成功", 'success')
					} else {
						that.$baseMessage("网络失败，请尝试刷新重试", 'error')
					}
				})
			},
			handleSelectionChange(val) {
				this.multipleSelection = val;
				console.log(this.multipleSelection)
			},
			batchAudit() {
				const that = this
				if (that.multipleSelection.length == 0) {
					that.$baseMessage('请先勾选', 'error')
				} else {
					that.$baseConfirm('确认批量通过？', null, async () => {
						const that = this
						let arr = []
            for (let i = 0; i < that.multipleSelection.length; i++) {
              let item = that.multipleSelection[i]
              if(item.status == 4){
                if(!(item.score && parseFloat(item.score) <= parseFloat(this.tableRow.methodsScore))){
                  that.$baseMessage("输入分值不得大于评分办法最大分值，请重新输入分值！", 'error')
                  return
                }else{
                  const detail = {
                    id: item.id,
                    score: item.score,
                    status: that.form.status == 2 ? '2' : '3',
                    targetScore: item.targetScore,
                  }
                  arr.push(detail)
                }
              }else{
                if(item.targetScore || item.score){
                  if(!(item.score && parseFloat(item.score) <= parseFloat(this.tableRow.methodsScore))){
                    that.$baseMessage("输入分值不得大于评分办法最大分值，请重新输入分值！", 'error')
                    return
                  }
                }else{
                  that.$baseMessage("自定义类型的指标，指标值或分值需要填写其中一个", 'error')
                  return
                }
                const detail = {
                  id: item.id,
                  score: item.score,
                  status: that.form.status == 2 ? '2' : '3',
                  targetScore: item.targetScore,
                }
                arr.push(detail)
              }
            }

						//全屏遮罩
						const loading = that.$loading({
							lock: true,
							text: '提交中，请稍等...',
							spinner: 'el-icon-loading',
							background: 'rgba(0, 0, 0, 0.7)'
						});
						audit({
							evalOrgDetailList: arr
						}).then((res) => {
							if (res.code == 0) {
								this.queryData();
								this.$refs.listTable.clearSelection();
								this.$baseMessage('成功', 'success')
							} else {
								this.$baseMessage(res.msg, 'error')
							}

							setTimeout(() => {
								loading.close();
							}, 1000);
						})
					})
				}
			},
			//下载文件
			downloadFile(index, row) {
				window.open(fileURL + row[index].url + '?n=' + row[index].name + '&name=' + row[index].name + '&download=1')
        // self.location.href = fileURL + row[index].url + '?n=' + row[index].name + '&name=' + row[index].name + '&download=1'
        console.log(row[index].url + '?n=' + row[index].name + '&name=' + row[index].name + '&download=1')
			},
			setHeight() {
				this.$nextTick(() => {
					let height = this.$refs.labelHeight.offsetHeight;
					this.tableHeight = `calc(100vh - 280px - ${height}px)`;
				})
			}
		}
	}
</script>

<style lang="scss" scoped>
	.contentLabel {
		font-size: 18px;
		margin: 10px 0;
		font-weight: bold;
		width: 50%;
		padding: 10px 16px;
		background: #E8F4FE;
		color: rgba(12, 60, 98, 1);
		border: 1px dashed #0C3C62;
	}
</style>