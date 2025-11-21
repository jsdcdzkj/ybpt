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
                <el-form-item label="状态">
                  <el-select v-model="queryForm.status" clearable placeholder="状态" style="width: 100%">
                    <el-option label="待确认" value="0"></el-option>
                    <el-option label="通过" value="1"></el-option>
                    <el-option label="驳回" value="2"></el-option>
                    <el-option label="已同步" value="3"></el-option>
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
            <span class="tips">耗材申报列表</span>
            <div class="right">
              <el-button v-show="userinfo.user_type == 2 || userinfo.user_type == 3" icon="el-icon-plus" type="success"
                         @click="handleAdd">
                新增
              </el-button>
              <el-button size="mini" plain type="primary" @click="excelDetails()">信息导出</el-button>
            </div>
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
                    highlight-current-row
                    border @current-change="handleCurrentChange" @selection-change="setSelectRows"
                    height="calc(100% - 50px)">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column show-overflow-tooltip label="耗材国家编码" prop="mcs_code"
                             align="center"></el-table-column>
            <el-table-column label="省平台挂网产品编号" prop="product_num" show-overflow-tooltip
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="注册备案产品名称" prop="reg_fil_prod_name"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="注册备案号" prop="reg_fil_no"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="材质" prop="matl" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="特征" prop="characteristics"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="规格" prop="spec" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="型号" prop="mol" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="耗材企业" prop="mcs_entp"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="申诉说明" prop="verify_msg"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="状态" prop="status" align="center">
              <template #default="scope">
                <el-tag type="info" v-show="scope.row.status == 1">已受理</el-tag>
                <el-tag v-show="scope.row.status == 2" type="error">已驳回</el-tag>
              </template>
            </el-table-column>
<!--            <el-table-column show-overflow-tooltip label="操作" width="200" align="center" fixed="right">-->
<!--              <template #default="{ row }">-->
<!--                <el-button-->
<!--                    v-show="(userinfo.user_type == 2 || userinfo.user_type == 3) && row.status == 0"-->
<!--                    plain size="mini" type="primary"-->
<!--                    @click="handleAdd(row)">-->
<!--                  编辑-->
<!--                </el-button>-->
<!--                <el-button-->
<!--                    v-show="(userinfo.user_type == 2 || userinfo.user_type == 3) && (row.status == 0 || row.status == 2)"-->
<!--                    plain size="mini" type="danger"-->
<!--                    @click="handleDelete(row)">-->
<!--                  删除-->
<!--                </el-button>-->
<!--                <el-button v-show="userinfo.user_type == 1 && row.status == 0" plain size="mini"-->
<!--                           type="info"-->
<!--                           @click="handleShenHe(row)">审核-->
<!--                </el-button>-->
<!--              </template>-->
<!--            </el-table-column>-->
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                         :layout="layout"
                         :total="total" @size-change="handleSizeChange"
                         @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <shenhe ref="shenHe" @fetch-data="fetchData"></shenhe>

  </div>
</template>

<script>
import {delConsumables, excel, selectList} from '@/api/consumables'
import Edit from './components/edit'
import Shenhe from './components/shenhe'

export default {
  name: 'Index',
  components: {Edit, Shenhe},
  data() {
    return {
      userinfo: '',
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
        status: '',
        pageNo: 1,
        pageSize: 10,
      },
    }
  },
  created() {
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
    this.fetchData()
  },
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {
    setSelectRows(val) {
      this.selectRows = val
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleAdd(row) {
      if (row.id) {
        this.$refs['edit'].showDia(row)
      } else {
        this.$refs['edit'].showDia()
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
    handleDelete(row) {
      var that = this;
      if (row.id) {
        that.$baseConfirm('你确定要删除当前项吗', null, async () => {
          delConsumables(row).then((res) => {
            that.fetchData()
          })

        })
      } else {
        if (that.selectRows != '' && that.selectRows != null) {
          const ids = that.selectRows.map((item) => item.id).join()
          that.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await doDelete({ids})
            that.$baseMessage(msg, 'success')
            that.fetchData()
          })
        } else {
          that.$baseMessage('未选中任何行', 'error')
          return false
        }
      }
    },
    handleShenHe(row) {
      this.$refs['shenHe'].showDia(row.id)
    },
    async excelDetails() {
      this.listLoading = true
      const res = await excel(this.queryForm);
      let fileName = "耗材申报导出.xls";
      let objectUrl = URL.createObjectURL(new Blob([res.data]))
      const link = document.createElement('a')
      link.download = decodeURI(fileName)
      link.href = objectUrl
      link.click()
      this.listLoading = false;
      this.$baseMessage("导出成功！", 'success')
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
        case "0":
          statusW = "医保中心";
          break;
      }
      return statusW
    },
    reseat() {
      this.queryForm.mcs_code = "";
      this.queryForm.status = "";
      this.fetchData();
    }
  },
}
</script>
