<template>
	<el-drawer :title="title" :before-close="cancelForm" :visible.sync="dialog" direction="rtl" :with-header="false"
		custom-class="box_drawer" size="80%" ref="drawer">
		<div class="drawer_content">
			<div class="drawer_main">
				<el-form label-width="62px">
				<el-row :gutter="20">
					<el-col :xs="18" :sm="18" :md="18" :lg="18" :xl="18">
						<el-card class="card" shadow="never">
							
								<div class="fill-title">
                  <h2>{{ khManage.year }}{{ khManage.fixmedins_name }}{{ khManage.task_name }}</h2>
									<p>
                    <span>机构类型：{{ khManage.org_type }}</span>
                    <span v-if="khManage.org_type == '医疗机构'">类别：{{ khManage.category_name }}</span>
                    <span>协议等级：{{ khManage.aggrement_lv }}</span>
                    <span>年度：{{ khManage.year }}</span>
									</p>
								</div>
								<div class="main-fill">
									<el-row :gutter="20" v-for="(item,index) in assessmentDetails" :key="item.id">
										<!-- 第一题 -->
										<el-col :xs="18" :sm="18" :md="18" :lg="18" :xl="18">
											<div class="filling-des-box"><b>{{ index + 1 }}、</b><p class="filling-des">{{ item.assess_question }}<span>【{{ item.full_score }}分】</span></p></div>
										</el-col>
										<el-col :xs="6" :sm="6" :md="6" :lg="6" :xl="6" v-if="khManage.status == 2 || userinfo.user_type == 1">
											<p class="score">得分：{{ item.scorel }}分</p>
										</el-col>
										<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
											<el-form-item label="描述" v-if="item.is_text == 1">
												<el-input type="textarea" disabled rows="2" v-model="item.assess_contentl" />
											</el-form-item>
										</el-col>
										<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-if="item.is_file == 1">
											<el-form-item label="文件">
												<p class="file-box" v-for="file in item.files" :key="file.id">
													<el-link type="primary" :href="file.url" target="_blank">
														<i class="el-icon-document"></i>
														{{ file.name }}
													</el-link>
												</p>
											</el-form-item>
										</el-col>
									</el-row>
									
									<div class="total-score">
										<div class="end-tips">
											友情提示：考核填报信息到底啦~
										</div>
										<div v-if="khManage.status == 2 || userinfo.user_type == 1">
											合计得分：<b>{{ count }}分</b>
										</div>
									</div>
								</div>
								
							
						</el-card>
					</el-col>
					<el-col :xs="6" :sm="6" :md="6" :lg="6" :xl="6">
						<el-card class="card" shadow="never">
							<div class="box_card">
							  <div class="box_header">机构信息</div>
							  <div class="box_content">
								  <el-form-item label="机构名称:" class="mb0"><p>{{ khManage.fixmedins_name }}</p></el-form-item>
								  <el-form-item label="机构编码:" class="mb0"><p>{{ khManage.fixmedins_code }}</p></el-form-item>
							  </div>
							  <div class="box_header">
							  	<span>日志信息</span>
							  </div>
								<div class="box_content box_content-timeline">
								  <el-timeline>
									<el-timeline-item size='large' v-for="(item) in logs"
													  :type='item.content != null ? "danger" : "primary"'
													  :timestamp="item.submit_time" :key="item.id">
									  <div class="time-line-des">
										<h4>{{ item.title }}</h4>
										<p>{{ item.content }}</p>
									  </div>
									</el-timeline-item>
								  </el-timeline>
								</div>
							</div>
							
						</el-card>
					</el-col>
				</el-row>
				</el-form>
			</div>
			
			<div class="drawer_footer">
				<el-button @click="cancelForm">关 闭</el-button>
			</div>
		</div>
	</el-drawer>
</template>

<script>
import {assessmentDetails} from "@/api/assessment";
import {fileURL} from "@/config/setting.config";

export default {
		name: 'detail',
		components: {
			
		},
		data() {
			return {
        userinfo: null,
				title: '',
				dialog: false,
				loading0: false,
				loading: false,
				isShow: false,
				isShow1: true,
				isShow2: false,
				formLabelWidth: '100px',
				timer: null,
        assessmentDetails: [],
        khManage: {},
        logs: {},
        count: 0
			}
		},
  created() {
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
  },
		mounted() {},
		methods: {
			async showDia(row) {
				this.title = '考核单详情'
				this.dialog = true
        if (row.id) {
          const {data} = await assessmentDetails({id: row.id})
          this.count = 0
          var numRe = new RegExp(/^[0-9]*$/)
          for (let i = 0; i < data.assessmentDetails.length; i++) {
            data.assessmentDetails[i].files.forEach(function (e) {
              e.url = fileURL + e.url + '?n=' + e.name + '&download=0'
            })
            if(numRe.test(data.assessmentDetails[i].scorel)){
              this.count += parseInt(data.assessmentDetails[i].scorel)
            }
          }
          this.assessmentDetails = data.assessmentDetails
          this.khManage = data.khManage
          this.logs = data.logs
        }
			},
			
			cancelForm() {
				this.loading = false
				this.dialog = false
				clearTimeout(this.timer)
			},
		},
	}
</script>
<style lang="scss" scoped>
	.fill-title{
		h2{
			margin-top: 10px;
			margin-bottom: 10px;
			font-size: 1.5rem;
			text-align: center;
		}
		p{
			margin-top: 10px;
			font-size: 14px;
			color: #999;
			text-align: center;
			span{
				display: inline-block;
				margin: 0 10px;
			}
		}
	}
	.filling-des-box{
		display: flex;
	}
	.filling-des{
		margin-top: 0px;
		color: #183371;
		font-size: 15px;
		span{
			color: #66738e;
		}
	}
	.main-fill{
		height: calc(100vh - 253px);
		padding: 0 20px;
		overflow-y: auto;
	}
	.box_card{
		height: calc(100vh - 162px);
	}
	.box_content-timeline{
		height: calc(100% - 210px);
		overflow-y: auto;
	}
	.fill-bot{
		padding-top: 14px;
		text-align: center;
		border-top: 1px solid #efefef;
	}
	.end-tips{
    margin-left: 14px;
    color: #b4b9bd;
	}
	.file-box{
    margin-top: 0;
    display: inline-block;
    padding: 0 8px;
    background-color: #f1f5f9;
		  margin-bottom: 6px;
		  margin-right: 6px;
		  line-height: 28px;
		  .el-link{
			  line-height: 18px;
		  }
	}
	.score{
		padding-right: 8px;
		margin: 0;
		font-weight: bold;
		text-align: right;
		color: #183371;
	}
	.total-score{
		display: flex;
		justify-content: space-between;
		padding: 14px 8px;
		background-color: #edf7fd;
		margin-bottom: 30px;
		color: #183371;
		margin-top: 40px;
	}
	.time-line-des{
		h4{
			font-size: 14px;
			margin: 0 6px 6px 0;
		}
		p{
			font-size: 14px;
			margin: 6px 6px 6px 0;
		}
	}
	::v-deep{
		.el-timeline{
			padding-left: 0;
		}
		.mb0{
			.el-form-item__label{
				width: auto !important;
			}
			p{
				padding: 6px 0;
				margin: 0;
				line-height: 22px;
			}
			
		}
		.el-card{
			margin-bottom: 0;
		}
		
	}
</style>