<template>
	<el-drawer :title="title" :before-close="cancelForm" :visible.sync="dialog" direction="rtl" :with-header="false"
		custom-class="box_drawer" size="70%" ref="drawer" >
		<div class="drawer_content">
			<el-form :model="form" :label-width="formLabelWidth">
				<div class="drawer_main">
					<div class="box_card">
						<div class="box_header">
							<span>基本信息</span>
						</div>
						<div class="box_content">
							<el-row :gutter="20">
								<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
									<el-form-item label="考核单名称" prop="assess_name">
										<el-input v-model="form.assess_name" placeholder="考核单名称" autocomplete="off"
											readonly ></el-input>
									</el-form-item>
								</el-col>
								<el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
									<el-form-item label="机构类型" prop="org_type">
										<el-select v-model="form.org_type" placeholder="请选择机构类型" class="w"  clearable disabled>
											<el-option value="1" label="医疗机构"></el-option>
											<el-option value="2" label="零售药店"></el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
									<el-form-item label="协议等级" prop="aggrement_lv">
										<el-select v-model="form.aggrement_lv" placeholder="请选择协议等级" class="w" clearable disabled>
											<el-option label="请选择" value=""></el-option>
											<el-option
													v-for="item in aList"
													:key="item.id"
													:label="item.name"
													:value="item.id"
											></el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
									<el-form-item label="考核年度" prop="year_of_assessment">
										<el-date-picker v-model="form.year_of_assessment" type="year" placeholder="选择年" format="yyyy"
														value-format="yyyy" disabled>
										</el-date-picker>
									</el-form-item>
								</el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8" v-if="form.org_type == 1">
                  <el-form-item label="类别" prop="category">
                    <el-select v-model="form.category" placeholder="请选择类别" class="w" clearable disabled>
                      <el-option value="1" label="门诊"></el-option>
                      <el-option value="2" label="住院"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
							</el-row>
						</div>
					</div>
					<div class="box_card">
						<div class="box_header mb2">
							<span>考核项</span>
						</div>
						<el-table v-loading="listLoading" ref="listTable" stripe :data="tableData"
							:element-loading-text="elementLoadingText" highlight-current-row border
							>
							<template slot="empty">
								<el-empty :image-size="200"></el-empty>
							</template>
							<el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="assess_question" label="考核内容" align="center">
							</el-table-column>
							<el-table-column show-overflow-tooltip label="答复方式" align="center">
								<template #default="{ row }">
								  <el-tag v-for="(item, index) in row.formOfReply" :key="index">
								    {{ item }}
								  </el-tag>
								</template>
							</el-table-column>
							<el-table-column show-overflow-tooltip prop="full_score" label="分值" align="center">
							</el-table-column>
							
						</el-table>
					</div>
				</div>
			</el-form>
			<div class="drawer_footer">
				<el-button @click="cancelForm">关 闭</el-button>
			</div>
		</div>
	</el-drawer>
</template>

<script>
import {assesDetail} from '@/api/assessment.js'

export default {
		name: 'detail',
		components: {
			
		},
		data() {
			return {
				aList: [
					{ id: "1", name: '一级' },
					{ id: "2", name: '二级' },
					{ id: "3", name: '三级' },
					{ id: "9", name: '未定级' },
					{ id: "4", name: 'A级' },
					{ id: "5", name: 'B级' },
					{ id: "6", name: 'C级' },
				],
				levelList: [],
				value3: '',
				title: '',
				dialog: false,
				loading0: false,
				loading: false,
				isShow: false,
				isShow1: true,
				isShow2: false,
				queryForm: {
					pageNo: 1,
					pageSize: 10,
					username: '',
				},
				form: {
					assess_name:"",
					org_type:"",
          category:"",
					aggrement_lv:"",
					year_of_assessment:"",
				},
				formLabelWidth: '100px',
				timer: null,
				tableData: []
			}
		},
		mounted() {},
		methods: {
			showDia(row) {
				this.title = '考核单详情'
				assesDetail(row.id).then((res) => {
					this.form = res.data.khAssessment ;
					this.tableData = res.data.list ;
				})
				this.dialog = true
			},
			
			cancelForm() {
				this.tableData = [] ;
				this.loading = false
				this.dialog = false
				clearTimeout(this.timer)
			},
		},

	}
</script>
<style scoped>
	.mb2{
		margin-bottom: 20px;
	}
</style>