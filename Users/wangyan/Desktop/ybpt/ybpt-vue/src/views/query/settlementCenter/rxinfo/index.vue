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
              <el-form-item label="定点医药机构编号">
              <el-input
              v-model.trim="queryForm.fixmedins_code"
              placeholder="定点医药机构编号"
              @keyup.enter.native="queryData"
              />
              </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="处方明细名称">
                  <el-input
                          v-model.trim="queryForm.rx_detl_name"
                          placeholder="处方明细名称"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="处方明细名称">
                  <el-input
                          v-model.trim="queryForm.rx_detl_name"
                          placeholder="处方明细名称"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="处方号">
                  <el-input
                          v-model.trim="queryForm.rxno"
                          placeholder="处方号"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="就诊号">
                  <el-input
                          v-model.trim="queryForm.mdtrt_sn"
                          placeholder="处方号"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="就诊人姓名">
                  <el-input
                          v-model.trim="queryForm.psn_name"
                          placeholder="就诊人姓名"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="住院号">
                  <el-input
                          v-model.trim="queryForm.ipt_otp_no"
                          placeholder="住院号"
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
            <span class="tips">定点医疗机构门急诊诊疗处方信息</span>
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
                    label="人员姓名"
                    align="center"
                    prop="psn_name"
                    width="120px"
            >
            </el-table-column>

            <el-table-column
              show-overflow-tooltip              
              label="定点医药机构编号"
              align="center"
              prop="fixmedins_code"
              width="120px"
            >              
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="mdtrt_sn"
              width="180px"
              label="就诊流水号"
              align="center"
            ></el-table-column>
              <el-table-column
                      show-overflow-tooltip
                      prop="ipt_otp_no"
                      width="180px"
                      label="住院/门诊号"
                      align="center"
              ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="rx_id"
              label="处方id"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="rxno"
              label="处方号"
              align="center"
              width="220px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="rx_prsc_time"
              label="处方开方时间"
              align="center"
              width="200px"
              :formatter="formatDate"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="rx_detl_id"
              align="center"
              label="处方明细id"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="rx_detl_name"
                    align="center"
                    label="处方明细名称"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="tcmdrug_type_name"
                    align="center"
                    label="中药类别名称"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="tcmherb_foote"
                    align="center"
                    label="草药脚注"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="medn_type_name"
                    align="center"
                    label="药物类型"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="drug_dosform"
                    align="center"
                    label="药品剂型"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="drug_dosform_name"
                    align="center"
                    label="药品剂型名称"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="drug_spec"
                    align="center"
                    label="药品规格"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="drug_used_frqu"
                    align="center"
                    label="药物使用-频率"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="drug_used_idose"
                    align="center"
                    label="药物使用-总剂量"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="drug_used_sdose"
                    align="center"
                    label="药物使用-次剂量"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="drug_used_dosunt"
                    align="center"
                    label="药物使用-剂量单位"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="drug_medc_way"
                    align="center"
                    label="药物使用-途径"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="skintst_dicm"
                    align="center"
                    label="皮试判别"
            ><template slot-scope="scope">
              <span v-show="scope.row.main_medc_flag==0">否</span>
              <span v-show="scope.row.main_medc_flag==1">是</span>
            </template></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="medc_begntime"
                    align="center"
                    label="用药开始时间"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="medc_endtime"
                    align="center"
                    label="用药停止日期时间"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="medc_days"
                    align="center"
                    label="用药天数"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="main_medc_flag"
                    align="center"
                    label="主要用药标志"
            >
              <template slot-scope="scope">
                <span v-show="scope.row.main_medc_flag==0">否</span>
                <span v-show="scope.row.main_medc_flag==1">是</span>
              </template>
            </el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="urgt_flag"
                    align="center"
                    label="加急标志"
            ><template slot-scope="scope">
              <span v-show="scope.row.main_medc_flag==0">否</span>
              <span v-show="scope.row.main_medc_flag==1">是</span>
            </template></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="unif_purc_drug"
                    align="center"
                    label="统一采购药品"
            ><template slot-scope="scope">
              <span v-show="scope.row.main_medc_flag==0">否</span>
              <span v-show="scope.row.main_medc_flag==1">是</span>
            </template></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="bas_medn_flag"
                    align="center"
                    label="基本药物标志"
            ><template slot-scope="scope">
              <span v-show="scope.row.main_medc_flag==0">否</span>
              <span v-show="scope.row.main_medc_flag==1">是</span>
            </template></el-table-column>


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
import Cardnum from '@/components/cardno'
import Views from './components/view'
import { rxInfoList } from '@/api/settlementCenter.js'
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
        rx_detl_name: '',
        fixmedins_code: '',
      },
      insutypeList:[]
    }
  },
  created() {
    // this.fetchData();
    // this.updata();
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
       rxInfoList(that.queryForm).then((res) => {
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
      this.queryForm.rx_detl_name=""
      this.queryForm.fixmedins_code=""
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
  },
}
</script>
