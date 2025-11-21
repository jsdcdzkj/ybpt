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
              <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                <!--<el-form-item label="业务申请类型">-->
                  <!--<el-select-->
                        <!--v-model="queryForm.biz_appy_type"-->
                        <!--placeholder="业务申请类型"-->
                        <!--style="width: 100%"-->
                        <!--@change="queryData"-->
                <!--&gt;-->
                    <!--<el-option-->
                            <!--v-for="item in insutypeList"-->
                            <!--:key="item.nat_dic_val_code"-->
                            <!--:label="item.nat_dic_val_name"-->
                            <!--:value="item.nat_dic_val_code">-->
                    <!--</el-option>-->
                <!--</el-select>-->
                <!--</el-form-item>-->
              <!--</el-col>-->
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
                <el-form-item label="定点医药机构编号">
                  <el-input
                          v-model.trim="queryForm.fixmedins_code"
                          placeholder="定点医药机构编号"
                          @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>

              <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                <!--<el-form-item label="医保电子凭证/身份证号">-->
                  <!--<el-input-->
                    <!--v-model.trim="queryForm.username"-->
                    <!--placeholder="身份证号"-->
                    <!--clearable                    -->
                    <!--class="input-with-icon"-->
                  <!--&gt;                  -->
                    <!--<vab-icon :icon="['fas', 'search']" slot="suffix" style="margin-right:10px;" @click="openwin"></vab-icon>-->
                    <!--<vab-icon :icon="['fas', 'id-card']" slot="suffix" style="margin-right:10px;"></vab-icon>-->
                    <!--<vab-icon :icon="['fas', 'barcode']" slot="suffix" style="margin-right:10px;"></vab-icon>-->
                  <!--</el-input>-->
                <!--</el-form-item>-->
              <!--</el-col>-->

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
            <span class="tips">结算信息</span>
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
              prop="setl_time"
              label="结算时间"
              align="center"
              width="200px"
              :formatter="formatDate"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="med_type"
              align="center"
              label="医疗类别"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="setl_type"
                    align="center"
                    label="结算类别"
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
import Cardnum from '@/components/cardno'
import Views from './components/view'
import { setlList } from '@/api/settlementCenter.js'
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
        biz_appy_type: '',
        certno: '',
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
       setlList(that.queryForm).then((res) => {
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
