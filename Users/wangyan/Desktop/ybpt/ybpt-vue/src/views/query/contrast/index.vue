<template>
	<div class="main-container">
		<el-row :gutter="20">
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">公共查询条件</span>
					</div>
					<el-form label-width="134px" :model="queryForm" :rules="rules" ref="queryForm">
						<el-row :gutter="20">
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="统筹区" >
									<el-select v-model.trim="queryForm.poolarea_no" class="w">
										<el-option v-for="item in admdvs" :key="item.value" :label="item.label" :value="item.value">
										</el-option>
									</el-select>
								</el-form-item>
							</el-col>

							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="医疗待遇类别">
									<el-select v-model="value1" multiple collapse-tags placeholder="请选择"
										style="width: 100%">
										<el-option v-for="item in medTypeData" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
												   :value="item.nat_dic_val_code">
										</el-option>

									</el-select>
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="结算医疗类别">
									<el-select v-model="queryForm.setl_type" style="width: 100%">
										<el-option v-for="item in setlTypeData" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
												   :value="item.nat_dic_val_code">
										</el-option>
									</el-select>
								</el-form-item>
							</el-col>
							<!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
								<!--<el-form-item label="报销标志">-->
									<!--<el-input v-model.trim="queryForm.username" />-->
								<!--</el-form-item>-->
							<!--</el-col>-->
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="定点医疗机构编码" prop="name">
									<el-input v-model.trim="queryForm.fixmedins_code" />
								</el-form-item>
							</el-col>
							<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
								<el-form-item label="病种编号">
									<el-input v-model.trim="queryForm.dise_no" />
								</el-form-item>
							</el-col>
							<!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
								<!--<el-form-item label="医院级别">-->
									<!--<el-select v-model="queryForm.username" style="width: 100%">-->
										<!--<el-option label="医院级别1" value="1"></el-option>-->
										<!--<el-option label="医院级别2" value="2"></el-option>-->
									<!--</el-select>-->
								<!--</el-form-item>-->
							<!--</el-col>-->
						</el-row>
					</el-form>
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">对比时间段查询</span>
						<div class="right">
						  <el-button type="success" icon="el-icon-document-copy" @click="getContrast">对比</el-button>
						</div>
					</div>
					<el-row :gutter="20">
						<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
							<div class="tips-title-back">
								<span class="tips-title">时间段（一）</span>
							</div>
							<el-form :inline="true" class="demo-form-inline">
								<el-form-item>
									<el-date-picker v-model="value2" type="daterange" range-separator="至"
										start-placeholder="开始日期" end-placeholder="结束日期">
									</el-date-picker>
								</el-form-item>
								<el-form-item>
									<el-button type="primary" @click="fetchData">搜索</el-button>
								</el-form-item>
							</el-form>
							<table class="des-table">
								<tr>
									<th class="des-cell des-label">统筹区</th>
									<td class="des-cell des-value">{{selectData.poolarea_no}}</td>
									<th class="des-cell des-label">定点医疗机构编码</th>
									<td class="des-cell ">{{selectData.fixmedins_code}}</td>
								</tr>
								<tr>
									<th class="des-cell des-label">定点医疗机构名称</th>
									<td class="des-cell des-value">{{selectData.fixmedins_name}}</td>
									<th class="des-cell des-label">就诊人次</th>
									<td class="des-cell "><el-tag size="small">{{selectData.person_count}}</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">医疗总费用</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData.medfee_sumamt}}元</el-tag></td>
									<th class="des-cell des-label">就诊人数</th>
									<td class="des-cell "><el-tag size="small">{{selectData.person_num}}</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">基本医疗基金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData.hifp_pay}}元</el-tag></td>
									<th class="des-cell des-label">大额医疗补助基金支出</th>
									<td class="des-cell "><el-tag size="small">{{selectData.hifob_pay}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">公务员医疗补助基金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData.cvlserv_pay}}元</el-tag></td>
									<th class="des-cell des-label">个人账户支出</th>
									<td class="des-cell "><el-tag size="small">{{selectData.acct_pay}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">个人现金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData.cash_payamt}}元</el-tag></td>
									<th class="des-cell des-label">自费中医院承担部分</th>
									<td class="des-cell "><el-tag size="small">{{selectData.ownpay_hosp_part}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">补充医疗保险基金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData.hifes_pay}}元</el-tag></td>
									<th class="des-cell des-label">大病保险基金支出</th>
									<td class="des-cell "><el-tag size="small">{{selectData.hifmi_pay}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">医疗救助基金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData.maf_pay}}元</el-tag></td>
									<th class="des-cell des-label">伤残人员医疗保障基金支出</th>
									<td class="des-cell "><el-tag size="small">{{selectData.hifdm_pay}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">次均费用</th>
									<td class="des-cell des-value" colspan="3"><el-tag size="small">{{selectData.perTimeCost}}元</el-tag></td>
								</tr>
							</table>
							<!-- 无查询条件时显示 -->
							<!--<div class="empty">-->
								<!--<img src="../../../assets/empty.png" alt="">-->
								<!--<p>暂无查询结果</p>-->
							<!--</div>-->
						</el-col>
						<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
							<div class="tips-title-back">
								<span class="tips-title">时间段（二）</span>
							</div>
							<el-form :inline="true" class="demo-form-inline">
								<el-form-item>
									<el-date-picker v-model="value3" type="daterange" range-separator="至"
										start-placeholder="开始日期" end-placeholder="结束日期">
									</el-date-picker>
								</el-form-item>
								<el-form-item>
									<el-button type="primary" @click="fetchData2">搜索</el-button>
								</el-form-item>
							</el-form>
							<table class="des-table">
								<tr>
									<th class="des-cell des-label">统筹区</th>
									<td class="des-cell des-value">{{selectData2.poolarea_no}}</td>
									<th class="des-cell des-label">定点医疗机构编码</th>
									<td class="des-cell ">{{selectData2.fixmedins_code}}</td>
								</tr>
								<tr>
									<th class="des-cell des-label">定点医疗机构名称</th>
									<td class="des-cell des-value">{{selectData2.fixmedins_name}}</td>
									<th class="des-cell des-label">就诊人次</th>
									<td class="des-cell "><el-tag size="small">{{selectData2.person_count}}</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">医疗总费用</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData2.medfee_sumamt}}元</el-tag></td>
									<th class="des-cell des-label">就诊人数</th>
									<td class="des-cell "><el-tag size="small">{{selectData2.person_num}}</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">基本医疗基金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData2.hifp_pay}}元</el-tag></td>
									<th class="des-cell des-label">大额医疗补助基金支出</th>
									<td class="des-cell "><el-tag size="small">{{selectData2.hifob_pay}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">公务员医疗补助基金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData2.cvlserv_pay}}元</el-tag></td>
									<th class="des-cell des-label">个人账户支出</th>
									<td class="des-cell "><el-tag size="small">{{selectData2.acct_pay}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">个人现金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData2.cash_payamt}}元</el-tag></td>
									<th class="des-cell des-label">自费中医院承担部分</th>
									<td class="des-cell "><el-tag size="small">{{selectData2.ownpay_hosp_part}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">补充医疗保险基金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData2.hifes_pay}}元</el-tag></td>
									<th class="des-cell des-label">大病保险基金支出</th>
									<td class="des-cell "><el-tag size="small">{{selectData2.hifmi_pay}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">医疗救助基金支出</th>
									<td class="des-cell des-value"><el-tag size="small">{{selectData2.maf_pay}}元</el-tag></td>
									<th class="des-cell des-label">伤残人员医疗保障基金支出</th>
									<td class="des-cell "><el-tag size="small">{{selectData2.hifdm_pay}}元</el-tag></td>
								</tr>
								<tr>
									<th class="des-cell des-label">次均费用</th>
									<td class="des-cell des-value" colspan="3"><el-tag size="small">{{selectData2.perTimeCost}}元</el-tag></td>
								</tr>
							</table>

							<!-- 无查询条件时显示 -->
							<!--<div class="empty">-->
								<!--<img src="../../../assets/empty.png" alt="">-->
								<!--<p>暂无查询结果</p>-->
							<!--</div>-->

						</el-col>
					</el-row>
				
				</el-card>
			</el-col>
			<el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
				<el-card class="card" shadow="never">
					<div slot="header">
						<span class="tips">对比结果</span>
					</div>
					<el-table :data="tableData" border style="width: 100%">
						<el-table-column fixed prop="name" label="标题" width="120">
						</el-table-column>
						<el-table-column prop="a" label="就诊人次" width="120">
						</el-table-column>
						<el-table-column prop="b" label="就诊人数" width="120">
						</el-table-column>
						<el-table-column prop="c" label="医疗总费用" width="120">
						</el-table-column>
						<el-table-column prop="d" label="基本医疗基金支出" width="120">
						</el-table-column>
						<el-table-column prop="e" label="大额医疗补助基金支出" width="120">
						</el-table-column>
						<el-table-column prop="f" label="公务员医疗补助基金支出" width="120">
						</el-table-column>
						<!--<el-table-column prop="g" label="参照公务员医疗补助基金支出" width="120">-->
						<!--</el-table-column>-->
						<el-table-column prop="h" label="个人账户支出" width="120">
						</el-table-column>
						<el-table-column prop="i" label="个人现金支出" width="120">
						</el-table-column>
						<el-table-column prop="j" label="自费中医院承担部分" width="120">
						</el-table-column>
						<el-table-column prop="k" label="补充医疗保险基金支出" width="120">
						</el-table-column>
						<el-table-column prop="l" label="大病保险基金支出" width="120">
						</el-table-column>
						<el-table-column prop="m" label="医疗救助基金支出" width="120">
						</el-table-column>
						<el-table-column prop="n" label="伤残人员医疗保障基金支出" width="120">
						</el-table-column>
						<el-table-column prop="o" label="次均费用" width="120">
						</el-table-column>

					</el-table>
					<div class="gz-tips">计算规则：<span>增减额 = 时段2 - 时段1</span> <span>增减幅度 = ( 时段2 - 时段1 ) / 时段1</span></div>
				</el-card>
			</el-col>
		</el-row>

	</div>
</template>

<script>
	import { billingComparison,contrast } from '@/api/query.js'
	import { getDicts } from '@/api/dictManagement'
	import { upData } from '@/api/common.js'
	export default {
		name: 'Index',
		components: {},
		data() {
			return {
				options: [],
				value1: [],
				value2: [],
				value3: [],
				admdvs: [],
				selectData:'',
				selectData2:'',
				medTypeData: [],
				setlTypeData: [],
				value: '',
				queryForm: {
					username: '',
					fixmedins_code: 'H32031100089',//定点医药机构编号
				    poolarea_no: '',//统筹区编号
					med_type: [],//医疗类别
					setl_type: '',//结算类别
					dise_no: '',//病种编号
					begndate: '',//开始日期
					enddate: '',//结束日期
				},
				rules: {
				  name: [
					{ required: false, message: '请输入编码', trigger: 'blur' },
				  ],
				},
				tableData: [{
					name: '增减额',
					a: '',
					b: '',
					c: '',
					d: '',
					e: '',
					f: '',
					g: '',
					h: '',
					i: '',
					j: '',
					k: '',
					l: '',
					m: '',
					n: '',
					o: '',
				}, {
					name: '增减幅度',
					a: '',
					b: '',
					c: '',
					d: '',
					e: '',
					f: '',
					g: '',
					h: '',
					i: '',
					j: '',
					k: '',
					l: '',
					m: '',
					n: '',
					o: '',
				}]

			}
		},
		created() {
			this.getAdmdvs();
			this.getMedType();
			this.getSetlType();
		},
		beforeDestroy() {},
		mounted() {},
		methods: {
			fetchData() {
				var that = this ;
				that.queryForm.med_type = that.value1.toString() ;
				this.queryForm.begndate = this.value2[0];
				this.queryForm.enddate = this.value2[1];
				billingComparison(that.queryForm).then((res) => {
					if(res.data != null){
						that.selectData = res.data ;
					}else {
						that.$baseMessage('暂无数据', 'error');
					}

				})
			},
			fetchData2() {
				var that = this ;
				that.queryForm.med_type = that.value1.toString() ;
				this.queryForm.begndate = this.value3[0];
				this.queryForm.enddate = this.value3[1];
				billingComparison(that.queryForm).then((res) => {
					if(res.data != null){
						that.selectData2 = res.data ;
					}else {
						that.$baseMessage('暂无数据', 'error');
					}
				})
			},
			getContrast() {
				var that = this ;
				that.tableData[0].a = that.selectData2.person_count-that.selectData.person_count;
				if(that.tableData[0].a != 0){
					if(that.selectData.person_count != 0){
						that.tableData[1].a = (that.selectData2.person_count-that.selectData.person_count)/that.selectData.person_count+'%';
					}
				}else {
					that.tableData[1].a = '0%'
				}

				that.tableData[0].b = that.selectData2.person_num-that.selectData.person_num;
				if(that.tableData[0].b != 0){
					if(that.selectData.person_num != 0){
						that.tableData[1].b = (that.selectData2.person_num-that.selectData.person_num)/that.selectData.person_num+'%';
					}
				}else {
					that.tableData[1].b = '0%'
				}
				that.tableData[0].c = that.selectData2.medfee_sumamt-that.selectData.medfee_sumamt;
				if(that.tableData[0].c != 0){
					if(that.selectData.medfee_sumamt != 0){
						that.tableData[1].c = (that.selectData2.medfee_sumamt-that.selectData.medfee_sumamt)/that.selectData.medfee_sumamt+'%';
					}
				}else {
					that.tableData[1].c = '0%'
				}
				that.tableData[0].d = that.selectData2.hifp_pay-that.selectData.hifp_pay;
				if(that.tableData[0].d != 0){
					if(that.selectData.hifp_pay != 0){
						that.tableData[1].d = (that.selectData2.hifp_pay-that.selectData.hifp_pay)/that.selectData.hifp_pay+'%';
					}
				}else {
					that.tableData[1].d = '0%'
				}
				that.tableData[0].e = that.selectData2.hifob_pay-that.selectData.hifob_pay;
				if(that.tableData[0].e != 0){
					if(that.selectData.hifob_pay != 0){
						that.tableData[1].e = (that.selectData2.hifob_pay-that.selectData.hifob_pay)/that.selectData.hifob_pay+'%';
					}
				}else {
					that.tableData[1].e = '0%'
				}
				that.tableData[0].f = that.selectData2.cvlserv_pay-that.selectData.cvlserv_pay;
				if(that.tableData[0].f != 0){
					if(that.selectData.cvlserv_pay != 0){
						that.tableData[1].f = (that.selectData2.cvlserv_pay-that.selectData.cvlserv_pay)/that.selectData.cvlserv_pay+'%';
					}
				}else {
					that.tableData[1].f = '0%'
				}
				that.tableData[0].h = that.selectData2.acct_pay-that.selectData.acct_pay;
				if(that.tableData[0].h != 0){
					if(that.selectData.acct_pay != 0){
						that.tableData[1].h = (that.selectData2.acct_pay-that.selectData.acct_pay)/that.selectData.acct_pay+'%';
					}
				}else {
					that.tableData[1].h = '0%'
				}
				that.tableData[0].i = that.selectData2.cash_payamt-that.selectData.cash_payamt;
				if(that.tableData[0].i != 0){
					if(that.selectData.cash_payamt != 0){
						that.tableData[1].i = (that.selectData2.cash_payamt-that.selectData.cash_payamt)/that.selectData.cash_payamt+'%';
					}
				}else {
					that.tableData[1].i = '0%'
				}
				that.tableData[0].j = that.selectData2.ownpay_hosp_part-that.selectData.ownpay_hosp_part;
				if(that.tableData[0].j != 0){
					if(that.selectData.ownpay_hosp_part != 0){
						that.tableData[1].j = (that.selectData2.ownpay_hosp_part-that.selectData.ownpay_hosp_part)/that.selectData.ownpay_hosp_part+'%';
					}
				}else {
					that.tableData[1].j = '0%'
				}
				that.tableData[0].k = that.selectData2.hifes_pay-that.selectData.hifes_pay;
				if(that.tableData[0].k != 0){
					if(that.selectData.hifes_pay != 0){
						that.tableData[1].k = (that.selectData2.hifes_pay-that.selectData.hifes_pay)/that.selectData.hifes_pay+'%';
					}
				}else {
					that.tableData[1].k = '0%'
				}
				that.tableData[0].l = that.selectData2.hifmi_pay-that.selectData.hifmi_pay;
				if(that.tableData[0].l != 0){
					if(that.selectData.hifmi_pay != 0){
						that.tableData[1].l = (that.selectData2.hifmi_pay-that.selectData.hifmi_pay)/that.selectData.hifmi_pay+'%';
					}
				}else {
					that.tableData[1].l = '0%'
				}
				that.tableData[0].m = that.selectData2.maf_pay-that.selectData.maf_pay;
				if(that.tableData[0].m != 0){
					if(that.selectData.maf_pay != 0){
						that.tableData[1].m = (that.selectData2.maf_pay-that.selectData.maf_pay)/that.selectData.maf_pay+'%';
					}
				}else {
					that.tableData[1].m = '0%'
				}
				that.tableData[0].n = that.selectData2.hifdm_pay-that.selectData.hifdm_pay;
				if(that.tableData[0].n != 0){
					if(that.selectData.hifdm_pay != 0){
						that.tableData[1].n = (that.selectData2.hifdm_pay-that.selectData.hifdm_pay)/that.selectData.hifdm_pay+'%';
					}
				}else {
					that.tableData[1].n = '0%'
				}
				that.tableData[0].o = that.selectData2.perTimeCost-that.selectData.perTimeCost;
				if(that.tableData[0].o != 0){
					if(that.selectData.perTimeCost != 0){
						that.tableData[1].o = (that.selectData2.perTimeCost-that.selectData.perTimeCost)/that.selectData.perTimeCost+'%';
					}
				}else {
					that.tableData[1].o = '0%'
				}







			},
			async getAdmdvs() {
				const res = await getDicts({ "type": "ADMDVS" });
				if (res.code == "0") {
					this.admdvs = res.data;
				}
			},
			async getMedType() {
				upData('MED_TYPE').then((res) => {
					if(res.code == 0){
						this.medTypeData = res.data ;
					}
				})
			},
			async getSetlType() {
				upData('SETL_TYPE').then((res) => {
					if(res.code == 0){
						this.setlTypeData = res.data ;
					}
				})
			},

		},
	}
</script>
<style lang="scss" scoped>
	::v-deep .el-descriptions__body .el-descriptions__table .desWidth {
		text-align: right;
		width: 130px;
	}

	.tips-title-back {
		margin-bottom: 14px;
		background-color: #f1f3f4;
	}

	.tips-title {
		margin-left: 10px;
		font-size: 14px;
		line-height: 34px;
		font-weight: bold;
		color: #333;
	}
	.gz-tips{
		font-size: 14px;
		color: #96a6b5;
		line-height: 36px;
		span{
			margin-right: 20px;
		}
	}
	.des-table{
		table-layout: auto;
		width: 100%;
		border-collapse: collapse;
		font-size: 12px;
		color: #606266;
	}
	.des-cell{
		box-sizing: border-box;
		text-align: left;
		font-weight: normal;
		line-height: 1.5;
		padding: 8px 10px;
		border: 1px solid #ebeef5;
		box-sizing: border-box;
		text-align: left;
		font-weight: normal;
		line-height: 1.5;
	}
	.des-cell.des-label{
		text-align: right;
		width: 130px;
		color: #909399;
		background: #fafafa;
	}
	.des-table td.des-cell{
		word-break: break-word;
		overflow-wrap: break-word;
	}
	.empty{
		margin: 80px auto;
		width: 100%;
		text-align: center;
	}
	.empty p{
		font-size: 14px;
		color: #5e6d82;
		line-height: 1.5em;
	}
</style>
