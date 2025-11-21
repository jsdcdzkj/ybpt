<template>
	<el-drawer :title="title" :before-close="cancelForm" :visible.sync="dialog" direction="rtl" :with-header="false"
		custom-class="box_drawer" size="500px" ref="drawer">
		<div class="drawer_content">
			<div class="drawer_main">
				<div class="box_card">
					<div class="box_header">
						<span>日志信息</span>
					</div>
					<div class="box_content">
						<el-timeline>
							<el-timeline-item size='large' :type='activity.type' :timestamp="activity.submit_time" v-for="(activity, index) in list" :key="index">
								<div class="time-line-des" >
									<h4>{{activity.title}}</h4>
									<p>{{activity.content}}</p>
								</div>
							</el-timeline-item>
							<!--<el-timeline-item  size='large' type='danger' timestamp="2018/4/2 20:46">-->
								<!--<div class="time-line-des">-->
									<!--<h4>医保审核（驳回）</h4>-->
									<!--<p>提交信息错误，需重新提交！</p>-->
								<!--</div>-->
							<!--</el-timeline-item>-->
						</el-timeline>
					</div>
				</div>
			</div>
			
			<div class="drawer_footer">
				<el-button @click="cancelForm">关 闭</el-button>
			</div>
		</div>
	</el-drawer>
</template>

<script>

	import {
		logList
	} from '@/api/assessment'
	export default {
		name: 'detail',
		components: {
			
		},
		data() {
			return {
				str: '',
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
					name: '测试',
					region: '',
					date1: '',
					date2: '',
					delivery: false,
					type: [],
					resource: '',
					desc: '',
				},
				formLabelWidth: '100px',
				timer: null,
				tableData: [{
				    aaa1: '年度资质评审报告',
				    aaa2: ['文本描述'],
				    aaa3: '8分',
				  }, {
				    aaa1: '年度资质评审报告',
				    aaa2: ['文本描述', '文件上传'],
				    aaa3: '8分',
				  }],
				list:[]
			}
		},
		mounted() {},
		methods: {
			showDia(row) {
				this.title = '日志详情'

				this.dialog = true
				logList(row.id).then((res) => {
					if (res.code == 0) {
						this.list = res.data ;

					}
				})

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
</style>