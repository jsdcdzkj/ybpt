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
              <el-form-item label="医保目录编码">
              <el-input
              v-model.trim="queryForm.hilist_code"
              placeholder="医保目录编码"
              @keyup.enter.native="queryData"
              />
              </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="医疗目录编码">
                  <el-input
                          v-model.trim="queryForm.med_list_codg"
                          placeholder="医疗目录编码"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="医疗机构编码">
                  <el-input
                          v-model.trim="queryForm.fixmedins_code"
                          placeholder="医疗机构编码"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="时间段">
                  <el-date-picker v-model="queryForm.times" type="daterange" align="right"
                                  unlink-panels range-separator="至" start-placeholder="开始日期"
                                  end-placeholder="结束日期" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="医疗类别">
                  <el-select v-model="queryForm.med_type" placeholder="医疗类别" style="width: 100%" @change="queryData">
                    <el-option v-for="item in cardTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="医疗类别">
                  <el-select v-model="queryForm.med_type" placeholder="医疗类别" style="width: 100%" @change="queryData">
                    <el-option v-for="item in admdvs" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
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
            <span class="tips">人员定点登记信息</span>
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
              prop="psn_name"
              width="120px"
            >              
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="certno"
              width="180px"
              label="证件号码"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fixmedins_code"
              label="定点医药机构编号"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fixmedins_name"
              label="定点医药机构名称"
              align="center"
              width="220px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="hilist_code"
              label="医保目录编码"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="med_list_codg"
              align="center"
              label="医疗目录编码"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="fee_ocur_time"
                    align="center"
                    label="费用发生时间"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="pric"
                    align="center"
                    label="单价"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="cnt"
                    align="center"
                    label="数量"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="det_item_fee_sumamt"
                    align="center"
                    label="明细项目费用总额"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="med_type"
                    align="center"
                    label="医疗类别"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="insu_admdvs"
                    align="center"
                    label="参保所属医保区划"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="pay_loc"
                    align="center"
                    label="支付地点类别"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="inscp_amt"
                    align="center"
                    label="符合范围金额"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="hifmi_pay"
                    align="center"
                    label="大病补充医疗保险基金支出"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="hifob_pay"
                    align="center"
                    label="大额医疗补助基金支出"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="maf_pay"
                    align="center"
                    label="医疗救助基金支出"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="fund_pay_sumamt"
                    align="center"
                    label="基金支付总额"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="fulamt_ownpay_amt"
                    align="center"
                    label="全自费金额"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="psn_pay"
                    align="center"
                    label="个人支付金额"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="acct_pay"
                    align="center"
                    label="个人账户支出"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="cash_payamt"
                    align="center"
                    label="现金支付金额"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="bilg_dept_name"
                    align="center"
                    label="开单科室名称"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="bilg_dr_name"
                    align="center"
                    label="开单医师姓名"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="dosform_name"
                    align="center"
                    label="剂型名称"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="spec"
                    align="center"
                    label="规格"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="med_chrgitm_type"
                    align="center"
                    label="医疗收费项目类别"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="chrgitm_lv"
                    align="center"
                    label="收费项目等级"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="160"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handlechuli(row)"
                  type="primary"
                  size="mini"
                >
                  详情
                </el-button> 
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
import { selectCatalogItem } from '@/api/query.js'
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
       selectCatalogItem(that.queryForm).then((res) => {
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


  },
}
</script>
