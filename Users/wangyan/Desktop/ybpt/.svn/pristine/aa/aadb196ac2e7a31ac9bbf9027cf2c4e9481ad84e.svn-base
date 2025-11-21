<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构编码">
                  <el-input v-model.trim="queryForm.fixmedins_code" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构名称">
                  <el-input v-model.trim="queryForm.fixmedins_name" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">申请贷款列表</span>
            <!--<div class="right">-->
            <!--<el-button type="success" icon="el-icon-plus" @click="handleAdd">-->
            <!--贷款申请-->
            <!--</el-button>-->
            <!--</div>-->
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row
                    border @current-change="handleCurrentChange" @selection-change="setSelectRows" height="calc(100% - 50px)">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column
              show-overflow-tooltip
              type="selection"
            ></el-table-column> -->
            <!--<el-table-column-->
            <!--show-overflow-tooltip-->
            <!--prop="id"-->
            <!--label="序号"-->
            <!--align="center"-->
            <!--width="80px"-->
            <!--&gt;<template slot-scope="scope">-->
            <!--<span v-text="getIndex(scope.$index)"> </span>-->
            <!--</template></el-table-column>-->
            <el-table-column show-overflow-tooltip label="机构名称" prop="fixmedins_name" align="center" width="350px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="fixmedins_code" width="220px" label="机构编码" align="center">
            </el-table-column>
            <!--<el-table-column-->
            <!--show-overflow-tooltip-->
            <!--prop="emp_type"-->
            <!--label="机构类型"-->
            <!--align="center"-->
            <!--width="120px"-->
            <!--:formatter="statusFormat"-->
            <!--&gt;</el-table-column>-->
            <el-table-column show-overflow-tooltip prop="apply_bank_id_name" label="申请银行" align="center" width="280px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="application_quota" label="申请金额" align="center" width="200px"></el-table-column>

            <el-table-column show-overflow-tooltip prop="apply_time" label="申请时间" align="center" width="200px" >
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="violationRecord" label="违规记录" align="center" width="280px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="determineTheAmount" label="确定额度" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="reviewer" label="审核人" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="reviewTime" label="审核时间" align="center" width="200px" >
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="reason" label="医保审核意见" align="center" width="200px" > </el-table-column>

            <el-table-column show-overflow-tooltip prop="apply_satus" label="医保审批状态" align="center" width="200px" fixed="right">
              <template #default="scope">
                <el-tag type="info" v-show="scope.row.apply_satus == 0">医保待审核</el-tag>
                <el-tag type="success" v-show="scope.row.apply_satus == 1">医保审核通过</el-tag>
                <el-tag type="danger" v-show="scope.row.apply_satus == 2">医保审核驳回</el-tag>
                <el-tag type="info" v-show="scope.row.apply_satus == 3">已撤销</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="bank_satus" label="银行审批状态" align="center" width="200px" fixed="right">
              <template #default="scope">
                <el-tag type="info" v-show="scope.row.bank_satus == 0">银行待审核</el-tag>
                <el-tag type="success" v-show="scope.row.bank_satus == 1">银行审核通过</el-tag>
                <el-tag type="danger" v-show="scope.row.bank_satus == 2">银行审驳回</el-tag>
                <el-tag type="info" v-show="scope.row.bank_satus == 3 && scope.row.apply_satus != 2">超时,医保重新审核</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="200" align="center" fixed="right">
              <template #default="{ row }">

                <el-button plain @click="handleAdd(row)" type="primary" size="mini" v-if="row.bank_satus == 0 && row.apply_bank_id == org_code">审核</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                         :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <shenhe ref="shenhe" @fetch-data="fetchData"></shenhe>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
  import { bankCkeckList,timeOut } from '@/api/loan.js'
import Shenhe from '@/components/allshenhe'
import Cardnum from '@/components/cardno'
import Views from '@/components/fileview'
import Edit from './components/edit'
export default {
  name: 'Index',
  components: { Cardnum, Edit,Shenhe,Views },
  data() {
    return {
      tableData: [],
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: true,
      isCheck: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      org_code: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        fixmedins_code: '',
        fixmedins_name: '',
      },
    }
  },
  created() {
    var that  = this ;
    that.fetchData();
    var userinfo = JSON.parse(localStorage.getItem("userinfo"));
    that.user_type = userinfo.user_type;
    that.org_code = userinfo.org_code;
    // 2:医疗机构 3:零售药店
    if ( that.user_type == '6') {
      that.isShow = false;
      that.querryShow = true;
    }else if(that.user_type == '1'){
      that.isAdmin = true ;
    }
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    openwin1(row) {
      this.$refs['views'].showDia(row)
    },
    handleAdd(row) {
      this.$refs['edit'].showDia(row)
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleSh() {
      if (this.selectRows.length > 0) {
        const ids = this.selectRows.map((item) => item.id).join()
        this.$baseConfirm('你确定要审核选中项吗', null, async () => {
          // const { msg } = await doDelete({ ids })
          this.$refs['shenhe'].showDia()
          //this.$baseMessage('模拟审核成功', 'success')
          //this.fetchData()
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    fetchData() {
      var that = this;
      var userinfo = JSON.parse(localStorage.getItem("userinfo"));


      bankCkeckList(that.queryForm).then((res) => {
        if (res.code == 0) {

          that.list = res.data.records;
          that.total = res.data.total
          //超时
          // for(var i = 0 ;i<that.list.length;i++){
          //   var nowDate = this.nowDate();
          //   var countDaysNum = this.NoABSDateDiff(nowDate,that.list[i].ybreviewTime);
          //
          //   if(countDaysNum == 4 ){
          //     this.$baseMessage('机构编号为:'+that.list[i].fixmedins_code+'的贷款申请即将超时', 'error') ;
          //   }else if(countDaysNum >= 5 ){
          //     //银行审核过的除外
          //     if(that.list[i].bank_satus == 0 ){
          //       timeOut(that.list[i].id).then((res) => {
          //
          //       })
          //     }
          //
          //   }
          // }

        }
      })
    },
    NoABSDateDiff(sDate1,sDate2){
      var  aDate,  oDate1,  oDate2,  iDays
      aDate  =  sDate1.split("-")
      oDate1  =  new  Date(aDate[0]+'-'+aDate[1]+'-'+aDate[2])
      aDate  =  sDate2.split("-")
      oDate2  =  new  Date(aDate[0]+'-'+aDate[1]+'-'+aDate[2])
      iDays  =  parseInt((oDate1  -  oDate2)  /  1000  /  60  /  60  /24)    //把相差的毫秒数转换为天数
      return  iDays
    },
    nowDate: function () {

      let date = new Date();
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
    reseat() {
      this.queryForm.fixmedins_code = "";
      this.queryForm.fixmedins_name = "";
      this.fetchData();
    }
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  padding: 0 !important;
  margin: 0 !important;
  background: #f5f7f8 !important;

  .bottom {
    padding-top: 20px;
    margin-top: 5px;
    color: #595959;
    text-align: left;
    border-top: 1px solid $base-border-color;
  }

  .bottom-btn {
    button {
      margin: 5px 10px 15px 0;
    }
  }
}
</style>
