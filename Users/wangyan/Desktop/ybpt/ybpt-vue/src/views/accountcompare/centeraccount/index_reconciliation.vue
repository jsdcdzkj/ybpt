<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-form label-width="120px">
          <el-card class="card" shadow="never">
            <div slot="header">
              <span class="tips">信息查询</span>
              <el-button icon="el-icon-search" @click="queryData" type="primary" class="right">
                查 询
              </el-button>
            </div>
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="机构编码">
                  <el-input v-model.trim="queryForm.fixmedins_code">
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="统筹区">
                  <el-select clearable v-model="queryForm.insu_admdvs" style="width: 100%">
                    <el-option v-for="item in admdvs" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="对账类型">
                  <el-select clearable v-model="queryForm.reconciliation_type" style="width: 100%">
                    <el-option v-for="item in reconciliationTypes" :key="item.value" :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="4" :lg="4" :xl="4">
                <el-form-item label="结算月份">
                  <el-date-picker type="month" format="yyyy-MM" value-format="yyyy-MM" v-model="queryForm.settle_date">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-card>
        </el-form>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">对账结果列表</span>
            <el-button icon="el-icon-upload" @click="excelDetails" type="primary">
              导 出
            </el-button>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" highlight-current-row border
            @current-change="handleCurrentChange"  height="calc(100vh - 550px)">
            <!--<template slot="empty">-->
              <!--<el-empty :image-size="200"></el-empty>-->
            <!--</template>-->
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
            <el-table-column show-overflow-tooltip prop="settle_date" label="结算月份" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="机构编码" align="center" prop="fixmedins_code" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="机构名称" align="center" prop="fixmedins_name" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="对账类型" align="center" prop="reconciliationName" width="230px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="admdvsName" width="120px" label="统筹区" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="person_count" align="center" label="人次" width="60px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="person_num" align="center" label="人数" width="60px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="medfee_sumamt" label="医疗费总额" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="hifp_pay" label="统筹基金支出" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="cvlserv_pay" label="公务员医疗补助" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="hifob_pay" label="大额医疗补助基金" align="center" width="170px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="hifmi_pay" label="大病补充医疗保险基金" align="center" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="hifes_pay" label="补充医疗保险基金" align="center" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="acct_mulaid_pay" align="center" label="账户共济支付金额" width="140px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="maf_pay" align="center" label="医疗救助基金" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="othfund_pay" align="center" label="其他基金" width="110px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="acct_pay" align="center" label="个人账户支出" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="cash_payamt" align="center" label="现金支付金额" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="ownpay_hosp_part" align="center" label="医院垫付" width="100px">
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <!--    <hospital ref="hospital" @fetch-data="queryForm.username"></hospital>-->
  </div>
</template>

<script>
  import { getDicts } from '@/api/dictManagement'
import { getReconciliationMonth,exportReconciliationMont } from '@/api/reconciliation'
export default {
  name: 'Index',
  components: {  },
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: false,
      reconciliationTypes: [],
      admdvs: [],
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        fixmedins_code: '',//机构编码
        settle_date: '',//结算日期
        insu_admdvs: '',//统筹区
        reconciliation_type: '',//对账类型
        check_status: ''//对账状态
      },
    }
  },
  created() {
    this.getReconciliationType()
    this.getAdmdvs()
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    //获取对账类别
    async getReconciliationType() {
      const res = await getDicts({"type": "reconciliationType"});
      if (res.code == "0") {
        this.reconciliationTypes = res.data;
      }
    },
    //获取统筹区
    async getAdmdvs() {
      const res = await getDicts({"type": "ADMDVS"});
      if (res.code == "0") {
        this.admdvs = res.data;
      }
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
    openwin() {
      this.$refs['hospital'].showDia()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async fetchData() {
      this.listLoading = true
      const res = await getReconciliationMonth(this.queryForm)
      if (res.code == "0") {
        this.list = res.data.records;
        this.total = res.data.total;
      }
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async excelDetails() {
          this.$baseConfirm('确认导出吗？', null, async () => {
            this.listLoading = true
            const res = await exportReconciliationMont(this.queryForm);
            let fileName = "机构对账数据.xls";
            let objectUrl = URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.download = decodeURI(fileName)
            link.href = objectUrl
            link.click()
            this.listLoading = false;
            this.$baseMessage("导出成功！", 'success')
          })
    },
  },
}
</script>
