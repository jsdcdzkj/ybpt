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
              <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                <el-form-item label="耗材国家编码">
                  <el-input v-model.trim="queryForm.mcs_code" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                <el-form-item label="版本号">
                  <el-input v-model.trim="queryForm.ver" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">耗材收费标识</span>
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
                    highlight-current-row
                    border @current-change="handleCurrentChange" @selection-change="setSelectRows"
                    height="calc(100% - 50px)">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column align="center" label="耗材国家编码" prop="MCS_CODE"
                             show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="产品名称" prop="PRODUCT_NAME"
                             show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="医保支付标准" prop="PRODUCT_PRICE"
                             show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="省平台挂网产品编号" prop="PRODUCT_NUM"
                             show-overflow-tooltip></el-table-column>
          </el-table>
          <!--                    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"-->
          <!--                                   :layout="layout"-->
          <!--                                   :total="total" @size-change="handleSizeChange"-->
          <!--                                   @current-change="handleCurrentChange2"></el-pagination>-->
        </el-card>
      </el-col>
    </el-row>

  </div>
</template>

<script>
import {consumables9003} from '@/api/consumables'

export default {
  name: 'Index',
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      list: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      tableHeight: '100%',
      elementLoadingText: '正在加载...',
      queryForm: {
        mcs_code: '',
        product_num: '',
        ver: "",
        pageNo: 1,
        pageSize: 10,
      },
    }
  },
  created() {
    // this.fetchData()
  },
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    fetchData() {
      let that = this;
      if (that.queryForm.mcs_code == undefined || that.queryForm.mcs_code == '' || that.queryForm.mcs_code == null) {
        that.$baseMessage('请输入耗材国家编码', 'error')
      }
      if (that.queryForm.ver == undefined || that.queryForm.ver == '' || that.queryForm.ver == null) {
        that.$baseMessage('请输入版本号', 'error')
      }
      consumables9003(this.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = []
          let data = JSON.parse(res.data);
          if (data.length > 0) {
            if (data[0].errinfo != null && data[0].errinfo != undefined && data[0].errinfo != '') {
              that.$baseMessage(data[0].errinfo, 'error')
              return
            }
          }
          that.list = data
          that.total = 1;
        } else {
          that.$baseMessage(res.msg, 'error')
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
        case "0":
          statusW = "医保中心";
          break;
      }
      return statusW
    },
    reseat() {
      this.queryForm.mcs_code = "";
      this.fetchData();
    }
  },
}
</script>
