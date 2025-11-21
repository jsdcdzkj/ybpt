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
              <el-form-item label="医药机构代码">
              <el-input
              v-model.trim="queryForm.fixmedins_code"
              placeholder="医药机构代码"
              @keyup.enter.native="queryData"
              />
              </el-form-item>
              </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="医药机构名称">
                        <el-input
                                v-model.trim="queryForm.fixmedins_name"
                                placeholder="医药机构名称"
                                @keyup.enter.native="queryData"
                        />
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="定点联网开通标志">
                        <el-select v-model="queryForm.fix_onln_open_flag" placeholder="定点联网开通标志" style="width: 100%" @change="queryData">
                            <el-option v-for="item in fixOnlnOpenFlag" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                                       :value="item.nat_dic_val_code">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>

            </el-row>
              <el-row :gutter="20" v-if="isShow">
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                      <el-form-item label="医药机构服务类型">
                          <el-select v-model="queryForm.fixmedins_type" placeholder="医药机构服务类型" style="width: 100%" @change="queryData">
                              <el-option v-for="item in fixmedinsType" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                                         :value="item.nat_dic_val_code">
                              </el-option>
                          </el-select>
                      </el-form-item>
                  </el-col>

                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                      <el-form-item label="行政区">
                          <el-select v-model="queryForm.poolarea_no" class="w">
                              <el-option label="徐州市市本级" value="320399"></el-option>
                              <el-option label="铜山区" value="320312"></el-option>
                              <el-option label="丰县" value="320321"></el-option>
                              <el-option label="沛县" value="320322"></el-option>
                              <el-option label="睢宁县" value="320324"></el-option>
                              <el-option label="新沂市" value="320381"></el-option>
                              <el-option label="邳州市" value="320382"></el-option>
                              <el-option label="贾汪区" value="320305"></el-option>
                          </el-select>
                      </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                      <el-form-item label="定点归属医保区划">
                          <el-select v-model="queryForm.fix_blng_admdvs" class="w" >
                              <el-option label="徐州市市本级" value="320399"></el-option>
                              <el-option label="铜山区" value="320312"></el-option>
                              <el-option label="丰县" value="320321"></el-option>
                              <el-option label="沛县" value="320322"></el-option>
                              <el-option label="睢宁县" value="320324"></el-option>
                              <el-option label="新沂市" value="320381"></el-option>
                              <el-option label="邳州市" value="320382"></el-option>
                              <el-option label="贾汪区" value="320305"></el-option>
                          </el-select>
                      </el-form-item>
                  </el-col>

                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                      <el-form-item label="医药机构等级">
                          <el-select v-model="queryForm.hosp_lv" placeholder="医药机构等级" style="width: 100%" @change="queryData">
                              <el-option v-for="item in hospLv" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                                         :value="item.nat_dic_val_code">
                              </el-option>
                          </el-select>
                      </el-form-item>
                  </el-col>

                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                      <el-form-item label="限价医院等级">
                          <el-select v-model="queryForm.lmtpric_hosp_lv" placeholder="限价医院等级" style="width: 100%" @change="queryData">
                              <el-option v-for="item in lmtpricHospLv" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                                         :value="item.nat_dic_val_code">
                              </el-option>
                          </el-select>
                      </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                      <el-form-item label="起付线医院等级">
                          <el-select v-model="queryForm.dedc_hosp_lv" placeholder="起付线医院等级" style="width: 100%" @change="queryData">
                              <el-option v-for="item in dedcHospLv" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
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
            <span class="tips">定点医药机构基本信息</span>
              <div class="right">
                  <el-button type="primary" icon="el-icon-upload2" @click="handleExport">
                      导出
                  </el-button>

              </div>
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
              label="医药机构代码"
              align="center"
              prop="fixmedins_code"
              width="120px"
            >              
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fixmedins_name"
              width="180px"
              label="医药机构名称"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="medins_abbr"
              label="医疗机构简称"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="rtal_phac_abbr"
              label="零售药店简称"
              align="center"
              width="220px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fixmedins_type"
              label="医药机构服务类型"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="poolarea_no"
              align="center"
              label="行政区"
            ></el-table-column>
            <el-table-column

                    show-overflow-tooltip
                    prop="yyaddr"
                    align="center"
                    label="地址"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="ydaddr"
                    align="center"
                    label="地址"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="fix_blng_admdvs"
                    align="center"
                    label="定点归属医保区划"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="hosp_lv"
                    align="center"
                    label="医药机构等级"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="lmtpric_hosp_lv"
                    align="center"
                    label="限价医院等级"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="dedc_hosp_lv"
                    align="center"
                    label="起付线医院等级"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="nat_plaf_code"
                    align="center"
                    label="国家异地平台机构编号"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="prov_plaf_code"
                    align="center"
                    label="省内异地平台机构编号"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="out_fixmedins_type"
                    align="center"
                    label="异地医疗机构类型"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="fix_onln_open_flag"
                    align="center"
                    label="定点联网开通标志"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="out_onln_open_type"
                    align="center"
                    label="异地联网开通类型"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="hi_resper_name"
                    align="center"
                    label="医保办负责人姓名"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="hi_resper_cert_type"
                    align="center"
                    label="医保办负责人证件类型"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="hi_resper_certno"
                    align="center"
                    label="医保办负责人身份证号码"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="hi_resper_tel"
                    align="center"
                    label="医保办负责人联系电话"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="begntime"
                    align="center"
                    label="开始日期"
                    :formatter="formatDate"
            ></el-table-column>

            <el-table-column
                    show-overflow-tooltip
                    prop="endtime"
                    align="center"
                    label="结束日期"
                    :formatter="formatDate"
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
import { basicMedicalInfo,basicMedicalInfoExport } from '@/api/query.js'
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
          fix_blng_admdvs: '',
          fixmedins_code: '',
          fixmedins_name: '',
          medins_abbr: '',
          fixmedins_type: '',
          poolarea_no: '',
          fix_blng_admdvs: '',
      },
      insutypeList:[],
      cardTypes:[],
        fixmedinsType:[],
        admdvs:[],
        hospLv:[],
        lmtpricHospLv:[],
        dedcHospLv:[],
        fixOnlnOpenFlag:[],
    }
  },
  created() {
    this.getFixmedinsType() ;
    this.getHospLv() ;
    this.getLmtpricHospLv() ;
    this.getDedcHospLv() ;
    this.getFixOnlnOpenFlag() ;
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
       basicMedicalInfo(that.queryForm).then((res) => {
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
    getFixmedinsType (){
      var that = this ;
      upData('FIXMEDINS_TYPE').then((res) => {
        if(res.code == 0){
          that.fixmedinsType = res.data ;
        }
      })
    },
      getHospLv (){
          var that = this ;
          upData('HOSP_LV').then((res) => {
              if(res.code == 0){
                  that.hospLv = res.data ;
              }
          })
      },
      getLmtpricHospLv (){
          var that = this ;
          upData('LMTPRIC_HOSP_LV').then((res) => {
              if(res.code == 0){
                  that.lmtpricHospLv = res.data ;
              }
          })
      },
      getDedcHospLv (){
          var that = this ;
          upData('DEDC_HOSP_LV').then((res) => {
              if(res.code == 0){
                  that.dedcHospLv = res.data ;
              }
          })
      },
      getFixOnlnOpenFlag (){
          var that = this ;
          upData('FIX_ONLN_OPEN_FLAG').then((res) => {
              if(res.code == 0){
                  that.fixOnlnOpenFlag = res.data ;
              }
          })
      },
      async handleExport() {
          this.$baseConfirm('确认导出吗？', null, async () => {
              this.listLoading = true
              const res = await basicMedicalInfoExport(this.queryForm);
              let fileName = "医疗机构科室信息.xls";
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
