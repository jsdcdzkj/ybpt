<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat" >重 置</el-button>
            </div>
          </div>
          <el-form label-width="100px" >
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="querryShow">
                <el-form-item label="机构编码">
                  <el-input v-model.trim="queryForm.fixmedins_code" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="querryShow">
                <el-form-item label="机构名称">
                  <el-input v-model.trim="queryForm.fixmedins_name" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="isShow">
                <el-form-item label="银行">
                  <el-select v-model.trim="queryForm.apply_bank_id" class="w" @change="queryData">
                    <el-option
                            v-for="item in natyList"
                            :key="item.bankNo"
                            :label="item.bankName"
                            :value="item.bankNo"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="isShow">
                <el-form-item label="医保审核状态">
                  <el-select v-model.trim="queryForm.apply_satus" class="w" @change="queryData">
                    <el-option value="0" label="待审核"></el-option>
                    <el-option value="1" label="审核通过"></el-option>
                    <el-option value="2" label="审核驳回"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="isShow">
                <el-form-item label="银行审核状态">
                  <el-select v-model.trim="queryForm.bank_satus" class="w" @change="queryData">
                    <el-option value="0" label="待审核"></el-option>
                    <el-option value="1" label="审核通过"></el-option>
                    <el-option value="2" label="审核驳回"></el-option>
                  </el-select>
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

            <el-table-column show-overflow-tooltip prop="determineTheAmount" label="确定额度" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="violationRecord" label="违规记录" align="center" width="280px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="reviewer" label="审核人" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="reviewTime" label="审核时间" align="center" width="200px" >
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="reason" label="医保审核意见" align="center" width="200px" > </el-table-column>
            <el-table-column show-overflow-tooltip prop="bank_reason" label="银行审核意见" align="center" width="200px" >
            </el-table-column>
            <!--<el-table-column-->
            <!--show-overflow-tooltip-->
            <!--prop="weixin"-->
            <!--label="当前状态"-->
            <!--align="center"-->
            <!--width="180px"-->
            <!--&gt;</el-table-column>-->
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
                <el-tag type="danger" v-show="scope.row.bank_satus == 3 && scope.row.apply_satus != 2">超时,医保重新审核</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="200" align="center" fixed="right" v-if="isShow">
              <template #default="{ row }">

                <el-button plain @click="handleAdd(row)" type="primary" size="mini" v-if="isAdmin && row.apply_satus == 0">审核</el-button>
                <el-button plain @click="handleDelete(row)" type="danger" size="mini" v-if="row.apply_satus == 0 && !isAdmin" >撤销申请</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <usermana ref="usermana" @fetch-data="fetchData"></usermana>
  </div>
</template>

<script>
import { loanList,del } from '@/api/loan.js'
import Edit from './components/edit'
import Usermana from './components/usermana'
import { selectListAll} from '@/api/bank.js'
export default {
  name: 'Index',
  components: { Edit, Usermana },
  data() {
    return {
      value1: '',
      checked: false,
      isShow: true,
      list: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      tableHeight: '100%',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        fixmedins_code: '',
        fixmedins_name: '',
        apply_bank_id: '',
      },
      querryShow:false,
      isAdmin:false,
      natyList: [],
    }
  },
  created() {
    var that  = this ;
    that.fetchData();
    this.natyData();
    var userinfo = JSON.parse(localStorage.getItem("userinfo"));
    that.user_type = userinfo.user_type;
    // 2:医疗机构 3:零售药店
    if ( that.user_type == '6') {
      that.isShow = false;
      that.querryShow = true;
    }else if(that.user_type == '1'){
      that.isAdmin = true ;
      that.querryShow = true;
    }else if(that.user_type == '2' || that.user_type == '3'){
      that.isAdmin = false ;
      that.querryShow = false;
    }
    console.log(that.querryShow);
  },
  beforeDestroy() { },
  mounted() {
    // getHeight()
    // //增加监听事件，窗口变化时得到高度。
    // window.addEventListener('resize',this.getHeight,false)
  },
  methods: {
    // getHeight(){
    // //获取浏览器高度并计算得到表格所用高度。
    //     this.tableHeight=document.documentElement.clientHeight-140
    //   }, 
    setSelectRows(val) {
      this.selectRows = val
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    handleAdd(row) {
      if (row.id) {
        this.$refs['edit'].showDia(row)
      } else {
        this.$refs['edit'].showDia()
      }
    },
    handleUser(row) {
      this.$refs['usermana'].showDia(row.id)
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handlechuli(row) {
      this.$refs['views'].showDia(row.id)
    },
    handleDelete(row) {
      var that = this;
      if (row.id) {
        that.$baseConfirm('你确定要撤销当前项吗', null, async () => {
          del(row.id).then((res) => {
            that.fetchData()
          })

        })
      } else {
        if (that.selectRows != '' && that.selectRows != null) {
          const ids = that.selectRows.map((item) => item.id).join()
          that.$baseConfirm('你确定要撤销当前项吗', null, async () => {
            const { msg } = await doDelete({ ids })
            that.$baseMessage(msg, 'success')
            that.fetchData()
          })
        } else {
          that.$baseMessage('未选中任何行', 'error')
          return false
        }
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
      var that = this;
      loanList(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = res.data.records;
          that.total = res.data.total
        }
      })
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    statusFormat(row, column) {
      var statusW;
      switch (row.emp_type) {
        case "0": statusW = "医保中心"; break;
      }
      return statusW
    },
    reseat() {
      this.queryForm.fixmedins_code = "";
      this.queryForm.fixmedins_name = "";
      this.queryForm.apply_bank_id = "" ;
      this.queryForm.apply_satus = "" ;
      this.queryForm.bank_satus = "" ;
      this.fetchData();
    },
    natyData() {
      var that = this ;
      selectListAll().then((res) => {
        if(res.code == 0){
          that.natyList = res.data ;
        }
      })
    },
  },
}
</script>
