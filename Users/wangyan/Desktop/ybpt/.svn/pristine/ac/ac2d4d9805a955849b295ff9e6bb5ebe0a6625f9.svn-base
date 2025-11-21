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
                <el-form-item label="开始时间" prop="beginTime">
                  <el-date-picker type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                  v-model="queryForm.beginTime"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="结束时间" prop="endTime">
                  <el-date-picker type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                                  v-model="queryForm.endTime"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="统筹区">
                  <el-select v-model="queryForm.insu_admdvs" placeholder="统筹区" clearable style="width: 100%">
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
                <el-form-item label="人员类别">
                  <el-select multiple v-model="queryForm.psn_type" clearable placeholder="人员类别" style="width: 100%" >
                    <el-option v-for="item in psnTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="生育类别">
                  <el-select multiple v-model="queryForm.matn_type" clearable placeholder="生育类别" style="width: 100%" >
                    <el-option v-for="item in matnTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="险种类别">
                  <el-select multiple v-model="queryForm.insutype" clearable placeholder="险种类别" style="width: 100%" >
                    <el-option v-for="item in insuTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
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
            <span class="tips">生育医疗费结算</span>
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
            <el-table-column show-overflow-tooltip label="医院编码" align="center" prop="fixmedins_code" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="fixmedins_name" width="250px" label="医院名称" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="matn_type" label="生育类别" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="hosp_lv" label="医院等级" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="insutype" label="险种类别" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="count" label="人次" align="center" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="num" label="人数" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="medfee_sumamt" align="center" label="医疗费总额"></el-table-column>
            <el-table-column show-overflow-tooltip prop="hifp_pay" align="center" label="统筹基金支出" width="80px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="hifob_pay" align="center" label="大额医疗补助基金" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="cvlserv_pay" align="center" label="公务员医疗补助" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="acct_pay" align="center" label="个人账户支出" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="cash_payamt" align="center" label="现金支付金额" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="ownpay_hosp_part" align="center" label="自费" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="maf_pay" align="center" label="医疗救助" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="insu_admdvs" align="center" label="统筹区" width="80px"></el-table-column>
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
import { getBirthList_org , exportBirthsettlement_org} from '@/api/reimbursement'
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
      psnTypes:[],//人员类别
      matnTypes:[],//生育类别
      insuTypes:[],//险种类别
      queryForm: {
        pageNo: 1,
        pageSize: 10,
      },
      rules: {
        beginTime: [{ required: true, trigger: 'blur', message: '请选择开始时间' }],
        endTime: [{ required: true, trigger: 'blur', message: '请选择结束时间' }]
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
            const res = await exportBirthsettlement_org(this.queryForm);
            let fileName = "生育医疗费结算汇总.xls";
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
      upData('PSN_TYPE').then((res) => {
        if(res.code == 0){
          that.psnTypes = res.data ;
        }
      })
      upData('MATN_TYPE').then((res) => {
        if(res.code == 0){
          that.matnTypes = res.data ;
        }
      })
      upData('INSUTYPE').then((res) => {
        if(res.code == 0){
          that.insuTypes = res.data ;
        }
      })
    },
    async fetchData() {
      this.listLoading = true
      const res = await getBirthList_org(this.queryForm)
      this.list = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
