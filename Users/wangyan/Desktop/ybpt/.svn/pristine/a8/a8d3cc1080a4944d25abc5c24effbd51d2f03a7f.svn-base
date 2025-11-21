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
                <el-form-item label="结算ID">
                  <el-input v-model.trim="queryForm.setl_id" placeholder="结算ID" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="清算经办机构">
                  <el-input v-model.trim="queryForm.clr_optins" placeholder="清算经办机构" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="人员编号">
                  <el-input v-model.trim="queryForm.psn_no" placeholder="人员编号" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="身份证">
                  <el-input v-model.trim="queryForm.certno" placeholder="身份证" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="姓名">
                  <el-input v-model.trim="queryForm.psn_name" placeholder="姓名" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="医疗类别">
                  <el-select multiple v-model="queryForm.med_type" clearable placeholder="医疗类别" style="width: 100%">
                    <el-option v-for="item in medTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="支付地点类别">
                  <el-select v-model="queryForm.pay_loc" clearable placeholder="支付地点类别" style="width: 100%">
                    <el-option v-for="item in paylocs" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="统筹区">
                  <el-select v-model="queryForm.insu_admdvs" placeholder="统筹区" clearable style="width: 100%" >
                    <el-option label="徐州市市本级" value="320399"></el-option>
                    <el-option label="邳州市" value="320382"></el-option>
                    <el-option label="新沂市" value="320381"></el-option>
                    <el-option label="睢宁县" value="320324"></el-option>
                    <el-option label="沛县" value="320322"></el-option>
                    <el-option label="丰县" value="320321"></el-option>
                    <el-option label="铜山区" value="320312"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="病种编码">
                  <el-input v-model.trim="queryForm.dise_no" placeholder="病种编码" clearable/>
                </el-form-item>
              </el-col>

            </el-row>
            <el-row :gutter="20" v-if="isShow">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="开始时间" prop="beginTime">
                  <el-date-picker clearable type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                  v-model="queryForm.beginDate"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="结束时间" prop="endTime">
                  <el-date-picker clearable type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                  v-model="queryForm.endDate"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构编码">
                  <el-input v-model.trim="queryForm.fixmedins_code" placeholder="机构编码" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构代码">
                  <el-input v-model.trim="queryForm.fixmedins_name" placeholder="机构代码" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="结算类别">
                  <el-select multiple v-model="queryForm.setl_type" clearable placeholder="结算类别" style="width: 100%" >
                    <el-option v-for="item in setlTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="险种类型">
                  <el-select v-model="queryForm.insutype" clearable placeholder="险种类型" style="width: 100%" >
                    <el-option v-for="item in insTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
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
            <el-table-column show-overflow-tooltip label="结算ID" align="center" prop="setl_id" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="人员编号" align="center" prop="psn_no" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="清算经办机构" align="center" prop="clr_optins" width="120px">
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
            <el-table-column show-overflow-tooltip prop="dise_no" align="center" label="病种编号" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="dise_name" align="center" label="病种名称" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="fulamt_ownpay_amt" align="center" label="全自费金额" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="overlmt_selfpay" align="center" label="超限价自费费用" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="preselfpay_amt" align="center" label="先行自付金额" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="dedc_std" align="center" label="起付标准" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="pay_loc" align="center" label="支付地点类别" width="100px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="pool_prop_selfpay" align="center" label="统筹支付比例" width="100px"></el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
  </div>
</template>

<script>
import { upData } from '@/api/common.js'
import { selectPersonalSettlement_page , selectPersonalSettlement_excel} from '@/api/personinfo'
import Cardnum from '@/components/cardno'
export default {
  name: 'Index',
  components: {Cardnum},
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
      paylocs:[],//支付地点类别
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
      this.$refs['views'].showDia(row.certno)
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
            const res = await selectPersonalSettlement_excel(this.queryForm);
            let fileName = "个人医疗费用结算信息.xls";
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
      upData('PAY_LOC').then((res) => {
        if(res.code == 0){
          that.paylocs = res.data ;
        }
      })
    },
    async fetchData() {
      this.listLoading = true
      const res = await selectPersonalSettlement_page(this.queryForm)
      this.list = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
