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
              <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                <el-form-item label="信用编码">
                  <el-input v-model.trim="queryForm.fixmedins_code" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构名称">
                  <el-input v-model.trim="queryForm.fixmedins_name" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构类型">
                  <el-select v-model.trim="queryForm.fixmedins_type" class="w" @change="queryData" clearable="">
                    <el-option value="2" label="非定点医疗机构"></el-option>
                    <el-option value="3" label="非定点零售药店"></el-option>
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
            <span class="tips">非定点机构列表</span>
            <div class="right">
              <el-button type="success" icon="el-icon-plus" @click="handleAdd">
                新增
              </el-button>
            </div>
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row
            border @current-change="handleCurrentChange" @selection-change="setSelectRows" height="calc(100% - 50px)">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column
                    label="序号"
                    width="80"
                    show-overflow-tooltip
                    align="center"
            >
              <template slot-scope="scope">
                <span v-text="getIndex(scope.$index)"> </span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="信用编码" prop="fixmedins_code" align="center" width="280px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="fixmedins_name" width="220px" label="机构名称" align="center">
            </el-table-column>

            <el-table-column show-overflow-tooltip prop="fix_blng_admdvs" label="行政区划" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="fixmedins_type" label="机构类型" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="aggrement_lv" label="协议等级" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="manage_quality" label="经营性质" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="legrep_person" label="联系人" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="legrep_mobile" label="联系电话" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="address" label="地址" align="center"></el-table-column>


            <el-table-column show-overflow-tooltip label="操作" width="200" align="center" fixed="right">
              <template #default="{ row }">

                <el-button plain @click="handleAdd(row)" type="primary" size="mini">编辑</el-button>
                <el-button plain @click="handleDelete(row)" type="danger" size="mini">删除</el-button>

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
import { delUnfixedMechanism, selectList } from '@/api/fddjg.js'
import Edit from './components/edit'
import Usermana from './components/usermana'
export default {
  name: 'Index',
  components: { Edit, Usermana },
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
        pageNo: 1,
        pageSize: 10,
        emp_name: '',
      },
    }
  },
  created() {
    this.fetchData()
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
        that.$baseConfirm('你确定要删除当前项吗', null, async () => {
          delUnfixedMechanism(row).then((res) => {
            that.fetchData()
          })

        })
      } else {
        if (that.selectRows != '' && that.selectRows != null) {
          const ids = that.selectRows.map((item) => item.id).join()
          that.$baseConfirm('你确定要删除选中项吗', null, async () => {
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
      selectList(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = res.data.records;
          console.log(that.list);
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
      this.queryForm.fixmedins_type = "";
      this.fetchData();
    }
  },
}
</script>
