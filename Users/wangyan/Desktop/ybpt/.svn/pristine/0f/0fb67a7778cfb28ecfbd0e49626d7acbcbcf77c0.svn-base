<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <vab-icon :icon="['fas', 'angle-up']" v-if="isShow" @click="moreQuery"></vab-icon>
            <vab-icon :icon="['fas', 'angle-down']" v-else @click="moreQuery"></vab-icon>
          </div>
          <el-form ref="queryForm" :model="queryForm" label-width="160px" :rules="rules">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="身份证">
                  <el-input v-model.trim="queryForm.certno" placeholder="身份证" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="年份" prop="year">
                  <el-date-picker clearable type="year" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                  v-model="queryForm.year"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="结算开始时间" prop="beginTime">
                  <el-date-picker clearable type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                  v-model="queryForm.beginDate"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="结算结束时间" prop="endTime">
                  <el-date-picker clearable type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                  v-model="queryForm.endDate"></el-date-picker>
                </el-form-item>
              </el-col>



            </el-row>
            <el-row :gutter="20" v-if="isShow">


            </el-row>
            <vab-query-form>
              <vab-query-form-right-panel :span="24">
                <el-form-item class="mr0">
<!--                  <el-button icon="el-icon-refresh-left">重 置</el-button>-->
                  <el-button icon="el-icon-search" @click="queryData" type="primary">
                    查 询
                  </el-button>
                  <el-button icon="el-icon-upload" @click="excelDetails" type="primary">
                    导 出
                  </el-button>
                </el-form-item>
              </vab-query-form-right-panel>
            </vab-query-form>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">人员基本信息</span>
            <!--            <el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">新增</el-button>-->
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" highlight-current-row border
            @current-change="handleCurrentChange" height="420px">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
            <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="机构编号" align="center" prop="fixmedins_code" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="fixmedins_name" width="250px" label="机构名称" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="psn_name" label="姓名" align="center" width="80px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="certno" label="身份证号" align="center" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="med_type" label="医疗类别" align="center" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="setl_type" label="结算类别" align="center" width="80px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="setl_time" label="结算时间" align="center" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="medfee_sumamt" align="center" label="医疗费总额"></el-table-column>
            <el-table-column show-overflow-tooltip prop="hifp_pay" align="center" label="统筹基金支出" width="80px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="hifob_pay" align="center" label="大额医疗补助基金支出" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="cvlserv_pay" align="center" label="公务员医疗补助资金支出" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="acct_pay" align="center" label="个人账户支出" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="cash_payamt" align="center" label="现金支付金额" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="ownpay_hosp_part" align="center" label="医院垫付" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="maf_pay" align="center" label="医疗救助基金支出" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="hifes_pay" align="center" label="补充医疗保险基金支出" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="hifmi_pay" align="center" label="大病补充医疗保险基金支出" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="othfund_pay" align="center" label="其它基金支付" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handlechuli(row)" type="primary" size="mini">
                  查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import { upData } from '@/api/common.js'
import { selectMzSettlement_page , selectMzSettlement_excel} from '@/api/personinfo'
import Views from './components/view_mz'

export default {
  name: 'Index',
  components: {Views},
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      medTypes:[],//医疗类别
      setlTypes:[],//结算类别
      insTypes:[],//险种类型
      queryForm: {
        pageNo: 1,
        pageSize: 10,
      },
    }
  },
  created() {
    // this.fetchData()
    this.getCarType();
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin(){
      this.$refs['cardnum'].showDia()
    },
    handleAdd(){
      this.$refs['edit'].showDia()
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handlechuli(row){
      this.$refs['views'].showDia(row)
    },
    handlecancel(row){
      if (row.id) {
          this.$baseConfirm('确认进行撤消？', null, async () => {
            const { msg } = await doDelete({ ids: row.id })
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          
        }
    },
    queryData() {
      this.$refs['queryForm'].validate(async (valid) => {
        if (valid) {
          this.queryForm.pageNo = 1
          this.fetchData()
        }else {
          return false
        }

      })

    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async excelDetails() {
      this.$refs['queryForm'].validate(async (valid) => {
        if (valid) {
          this.$baseConfirm('确认导出吗？', null, async () => {
            this.listLoading = true
            const res = await selectMzSettlement_excel(this.queryForm);
            let fileName = "个人门诊结算信息.xls";
            let objectUrl = URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.download = decodeURI(fileName)
            link.href = objectUrl
            link.click()
            this.listLoading = false;
            this.$baseMessage("导出成功！", 'success')
          })
        }else {
          return false
        }

      });
    },
    getCarType() {
      var that = this ;
      upData('MED_TYPE').then((res) => {
        if(res.code == 0){
          that.medTypes = res.data ;
        }
      })

      upData('SETL_TYPE').then((res) => {
        if(res.code == 0){
          that.setlTypes = res.data ;
        }
      })

      upData('INSUTYPE').then((res) => {
        if(res.code == 0){
          that.insTypes = res.data ;
        }
      })
    },
    async fetchData() {
      this.listLoading = true
      const res = await selectMzSettlement_page(this.queryForm)
      this.list = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
