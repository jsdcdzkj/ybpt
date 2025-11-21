<template>
  <div class="main-container" >
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">查询条件</span>
            <vab-icon
              :icon="['fas', 'angle-up']"
              v-if="isShow"
              @click="moreQuery"
            ></vab-icon>
            <vab-icon
              :icon="['fas', 'angle-down']"
              v-else
              @click="moreQuery"
            ></vab-icon>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="证件号码">
              <el-input
              v-model.trim="queryForm.certno"
              placeholder="证件号码"
              @keyup.enter.native="queryData"
              />
              </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="年度">
                  <el-input
                          v-model.trim="queryForm.year"
                          placeholder="年度"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>

            </el-row>
            <vab-query-form>
              <vab-query-form-right-panel :span="24">
                <el-form-item class="mr0">
                  <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
                  <el-button icon="el-icon-search" type="primary" @click="queryData">
                    查 询
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
            <span class="tips">生育待遇结算进度</span>
            <el-button type="primary" icon="el-icon-upload2" @click="handleExport">
              导出
            </el-button>

            <!--<el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">新增</el-button>-->
          </div>
          <el-table
                  v-loading="loading"
            ref="listTable"
            stripe
            :data="list"
            :element-loading-text="elementLoadingText"
            highlight-current-row
            border
            @current-change="handleCurrentChange"
            height="420px"
          >
            <template slot="empty">
                <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
            <el-table-column
              show-overflow-tooltip
              prop="id"
              label="序号"
              align="center"
              width="80px"
            ><template slot-scope="scope">
              <span v-text="getIndex(scope.$index)"> </span>
            </template></el-table-column>
            <el-table-column
              show-overflow-tooltip              
              label="姓名"
              align="center"
              prop="fullName"
              width="120px"
            >              
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="identityNumber"
              width="180px"
              label="身份证号"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="gender"
              label="性别"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="companyName"
              label="单位名称"
              align="center"
              width="220px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="treatmentType"
              label="待遇类型"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fertilityCategory"
              align="center"
              label="生育类别"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="birthDate"
                    align="center"
                    label="计划生育手术或生育日期"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="totalMedicalExpenses"
                    align="center"
                    label="医疗费用总额"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="amountOfMedicalExpensesPaid"
                    align="center"
                    label="医疗费支付金额"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="maternityBenefits"
                    align="center"
                    label="生育津贴"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="oneTimeNutritionFee"
                    align="center"
                    label="一次性营养费"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="prenatalCheckupFee"
                    align="center"
                    label="产前检查费"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="currentBusinessSegment"
                    align="center"
                    label="当前业务环节"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="maleEmployeeCareAllowance"
                    align="center"
                    label="男职工护理津贴"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="followUpBusiness"
                    align="center"
                    label="后续业务环节"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="auditResults"
                    align="center"
                    label="审核结果"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="auditOpinion"
                    align="center"
                    label="审核意见"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="manager"
                    align="center"
                    label="经办人"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="handlingTime"
                    align="center"
                    label="经办时间"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="timeoutDays"
                    align="center"
                    label="超时天数"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="160"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <!--<el-button-->
                  <!--plain-->
                  <!--@click="handlechuli(row)"-->
                  <!--type="primary"-->
                  <!--size="mini"-->
                <!--&gt;-->
                  <!--详情-->
                <!--</el-button> -->
                <!--<el-button-->
                  <!--plain-->
                  <!--@click="handlecancel(row)"-->
                  <!--type="primary"-->
                  <!--size="mini"-->
                <!--&gt;-->
                  <!--撤消-->
                <!--</el-button>               -->
              </template>
            </el-table-column>
          </el-table>          
          <el-pagination
            background
            :current-page="queryForm.pageNo"
            :page-size="queryForm.pageSize"
            :layout="layout"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange2"
          ></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import { upData } from '@/api/common.js'
import Cardnum from '@/components/cardno'
import Views from './components/view'
import { fertilitySettlementProgressData,fertilitySettlementProgressDataExport } from '@/api/query.js'
export default {
  name: 'Index',
  components: {Cardnum,Views},
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      loading: false,
      list: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        hilist_code: '',
        med_list_codg: '',
        med_type: '',
      },
      insutypeList:[],
      cardTypes:[],
      admdvs:[],
    }
  },
  created() {
    this.getCarType() ;
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
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
     fetchData() {
      var that = this ;
       that.loading = true ;
       fertilitySettlementProgressData(that.queryForm).then((res) => {
        if(res.code == 0){
          that.list = res.data.records ;
          that.total =res.data.total
        }
         that.loading = false;
      })
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    formatDate: function (row, column) {
      let data = row[column.property]
      if (data == null) {
        return null
      }
      let date = new Date(data);
      var o = {
        "M+": date.getMonth() + 1,                 //月份
        "d+": date.getDate(),                    //日
        "h+": date.getHours(),                   //小时
        "m+": date.getMinutes(),                 //分
        "s+": date.getSeconds(),                 //秒
        "q+": Math.floor((date.getMonth() + 3) / 3), //季度
        "S": date.getMilliseconds()             //毫秒
      };
      var fmt = "yyyy-MM-dd";
      if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
      }
      for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
          fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
      }
      return fmt;
    },
    reseat(){
      this.queryForm.certno=""
      this.queryForm.fixmedins_code=""
    },
    getCarType() {
      var that = this ;
      upData('MED_TYPE').then((res) => {
        if(res.code == 0){
          that.cardTypes = res.data ;
        }
      })
    },
    async handleExport() {
      this.$baseConfirm('确认导出吗？', null, async () => {
        this.listLoading = true
        const res = await fertilitySettlementProgressDataExport(this.queryForm);
        let fileName = "生育待遇结算进度查询.xls";
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
