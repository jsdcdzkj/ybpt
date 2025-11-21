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
              <el-form-item label="病种名称">
              <el-input
              v-model.trim="queryForm.opsp_dise_name"
              placeholder="病种名称"
              @keyup.enter.native="queryData"
              />
              </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="审核状态">
                  <el-select v-model="queryForm.rchk_flag" placeholder="审核状态" style="width: 100%" @change="queryData">
                    <el-option v-for="item in cardTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="有效状态">
                  <el-select v-model="queryForm.vali_flag" style="width: 100%" >
                    <el-option label="无效" value="0"></el-option>
                    <el-option label="有效" value="1"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>


            </el-row>
            <el-row :gutter="20" v-if="isShow">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="统筹区">
                  <el-select v-model="queryForm.poolarea_no" placeholder="统筹区" style="width: 100%" @change="queryData">
                    <el-option label="徐州市市本级" value="320399"></el-option>
                    <el-option label="邳州市" value="320382"></el-option>
                    <el-option label="新沂市" value="320381"></el-option>
                    <el-option label="睢宁县" value="320324"></el-option>
                    <el-option label="沛县" value="320322"></el-option>
                    <el-option label="丰县" value="320321"></el-option>
                    <el-option label="铜山区" value="320312"></el-option>
                    <el-option label="贾汪区" value="320305"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="参保险种">
                  <el-select v-model="queryForm.insutype" placeholder="参保险种" style="width: 100%" @change="queryData">
                    <el-option v-for="item in admdvsData" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="病种类型">
                  <el-select v-model="queryForm.dise_type_code" placeholder="病种类型" style="width: 100%" @change="queryData">
                    <el-option v-for="item in diseTypeCodeData" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="离退休标志">
                  <el-select v-model="queryForm.retr_type" placeholder="离退休标志" style="width: 100%" @change="queryData">
                    <el-option v-for="item in retrTypeData" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="认定定点医药机构编号">
                  <el-input
                          v-model.trim="queryForm.ide_fixmedins_no"
                          placeholder="认定定点医药机构编号"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="鉴定定点医药机构名称">
                  <el-input
                          v-model.trim="queryForm.ide_fixmedins_name"
                          placeholder="鉴定定点医药机构名称"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="诊断医师代码">
                  <el-input
                          v-model.trim="queryForm.diag_dr_code"
                          placeholder="诊断医师代码"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="诊断医师姓名">
                  <el-input
                          v-model.trim="queryForm.diag_dr_name"
                          placeholder="诊断医师姓名"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="开始日期">
                  <el-date-picker
                          format="yyyy-MM-dd"
                          value-format="yyyy-MM-dd"
                          v-model.trim="queryForm.begndate"
                          type="date"
                          class="w"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="结束日期">
                  <el-date-picker
                          format="yyyy-MM-dd"
                          value-format="yyyy-MM-dd"
                          v-model.trim="queryForm.enddate"
                          type="date"
                          class="w"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="生存状态">
                  <el-select v-model="queryForm.surv_stas" placeholder="生存状态" style="width: 100%" @change="queryData">
                    <el-option v-for="item in survStasData" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="申报来源">
                  <el-select v-model="queryForm.dcla_souc" placeholder="申报来源" style="width: 100%" @change="queryData">
                    <el-option v-for="item in dclaSoucData" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
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
              label="统筹区编号"
              align="center"
              prop="poolarea_no"
              width="120px"
            >              
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="insutype"
              width="180px"
              label="参保险种"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="dise_type_code"
              label="病种类型代码"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="rchk_flag"
              label="审核状态"
              align="center"
              width="220px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="psn_name"
              label="人员姓名"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="psn_cert_type"
              align="center"
              label="证件类型"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="certno"
                    align="center"
                    label="证件号码"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="opsp_dise_name"
                    align="center"
                    label="病种名称"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="begndate"
                    align="center"
                    label="开始日期"
                    :formatter="formatDate"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="enddate"
                    align="center"
                    label="结束日期"
                    :formatter="formatDate"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="surv_stas"
                    align="center"
                    label="生存状态"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="vali_flag"
                    align="center"
                    label="有效标志"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="retr_type"
                    align="center"
                    label="离退休类型"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="ide_fixmedins_no"
                    align="center"
                    label="鉴定定点医药机构编号"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="ide_fixmedins_name"
                    align="center"
                    label="鉴定定点医药机构名称"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="diag_dr_code"
                    align="center"
                    label="诊断医师代码"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="diag_dr_name"
                    align="center"
                    label="诊断医师姓名"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="appy_date"
                    align="center"
                    label="申请日期"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="hosp_ide_date"
                    align="center"
                    label="医院鉴定日期"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="appy_rea"
                    align="center"
                    label="申请理由"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="tel"
                    align="center"
                    label="联系电话"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="emp_no"
                    align="center"
                    label="单位编号"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="emp_name"
                    align="center"
                    label="单位名称"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="memo"
                    align="center"
                    label="备注"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="opter_name"
                    align="center"
                    label="经办人姓名"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="opt_time"
                    align="center"
                    label="经办时间"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="dcla_souc"
                    align="center"
                    label="申报来源"
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
import { queryOpsp } from '@/api/query.js'
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
      admdvsData:[],
      diseTypeCodeData:[],
      retrTypeData:[],
      survStasData:[]
    }
  },
  created() {
    this.getCarType() ;
    this.getAdmdvs() ;
    this.getDiseTypeCodeData() ;
    this.getRetrTypeData() ;
    this.getSurvStasData() ;
    this.getDclaSoucData() ;
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
       queryOpsp(that.queryForm).then((res) => {
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
      upData('RCHK_FLAG').then((res) => {
        if(res.code == 0){
          that.cardTypes = res.data ;
        }
      })
    },
    getAdmdvs(){
      var that = this ;
      upData('INSUTYPE').then((res) => {
        if(res.code == 0){
          that.admdvsData = res.data ;
        }
      })
    },
    getDiseTypeCodeData(){
      var that = this ;
      upData('DISE_TYPE_CODE').then((res) => {
        if(res.code == 0){
          that.diseTypeCodeData = res.data ;
        }
      })
    },
    getRetrTypeData(){
      var that = this ;
      upData('RETR_TYPE').then((res) => {
        if(res.code == 0){
          that.retrTypeData = res.data ;
        }
      })
    },
    getSurvStasData(){
      var that = this ;
      upData('SURV_STAS').then((res) => {
        if(res.code == 0){
          that.survStasData = res.data ;
        }
      })
    },
    getDclaSoucData(){
      var that = this ;
      upData('DCLA_SOUC').then((res) => {
        if(res.code == 0){
          that.dclaSoucData = res.data ;
        }
      })
    },


  },
}
</script>
