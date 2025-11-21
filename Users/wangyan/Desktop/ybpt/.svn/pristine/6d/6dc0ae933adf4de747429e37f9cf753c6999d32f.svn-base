<template>
  <el-drawer :title="title" :visible.sync="dialog" direction="rtl" :with-header="false" custom-class="box_drawer"
    size="80%" ref="drawer" append-to-body>
    <div class="drawer_content">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <vab-query-form>
            <vab-query-form-left-panel :span="24">
              <el-form :inline="true" :model="queryForm" @submit.native.prevent>

                <el-form-item label="类型">
                  <el-select v-model="queryForm.type" style="width: 100%" @change="queryData()">
                    <el-option label="汇总" value="0"></el-option>
                    <el-option label="职工门诊" value="1"></el-option>
                    <el-option label="职工住院" value="2"></el-option>
                    <el-option label="居民门诊" value="3"></el-option>
                    <el-option label="居民住院" value="4"></el-option>
                    <el-option label="伤残门诊" value="5"></el-option>
                    <el-option label="伤残住院" value="6"></el-option>
                    <el-option label="职工、灵活就业人员生育" value="7"></el-option>
                    <el-option label="居民生育" value="8"></el-option>
                    <el-option label="离休" value="9"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item >
                  <el-input v-model.trim="queryForm.org_code" placeholder="请输入机构编码" clearable
                            @keyup.enter.native="queryData"/>
                </el-form-item>
                <el-form-item >
                  <el-input v-model.trim="queryForm.org_name" placeholder="请输入机构名称" clearable
                            @keyup.enter.native="queryData"/>
                </el-form-item>
                <el-form-item>
                  <el-button icon="el-icon-search" type="primary" @click="queryData">
                    查询
                  </el-button>
                  <el-button type="primary" @click="reseat">重置</el-button>
                </el-form-item>
              </el-form>
            </vab-query-form-left-panel>
          </vab-query-form>
          <!-- <el-row :span="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24"> -->
          <el-table :data="list" border stripe class="w" :element-loading-text="elementLoadingText"
            height="calc(100vh - 230px)" @selection-change="handleSelectionChange">
            <el-table-column type="selection" align="center" width="55" v-if="isCheck">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="base_code" label="本地码" width="120px" align="center" fixed="left">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="org_code" label="国家码" width="120px" fixed="left" align="center"
             ></el-table-column>
            <el-table-column show-overflow-tooltip prop="org_name" label="名称" align="center" width="200px"></el-table-column>
            <el-table-column prop="payable_amount" label="应付金额" width="190px" align="center"></el-table-column>
            <el-table-column prop="paid_amount" label="已付金额" width="190px" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="payment_amont" label="年度清算需结付金额" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="org_borne_amount" label="医疗机构承担金额" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="sum_amount" label="发生总金额" width="200px" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="budget_amount" label="总额预算" width="200px" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="withhold_payment" label="不予支付" width="200px" align="center">
            </el-table-column>


            <el-table-column show-overflow-tooltip label="操作" width="120" align="center" fixed="right" >
              <template #default="{ row }">
                <el-button size="mini" plain type="success" @click="handleView(row)"  >
                  修改
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
      </div>
    </div>
    <views ref="views" @fetch-data="fetchData"></views>
  </el-drawer>
</template>

<script>
import { detailInfo } from '@/api/qs.js';
import Views from './components/view';
export default {
  name: 'useredit',
  components: { Views },
  data() {
    return {
      list: [],
      form: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        qs_info_id:""
      },
      title: '',
      user_type: '',
      dialog: false,
      loading: false,
      isCheck: false,
      formLabelWidth: '100px',
      timer: null,
      multipleSelection: [],
      saveData: {},
    }
  },
  created() {

  },
  mounted() { },
  methods: {
    showDia(row) {

      if (!row) {
        this.title = '用人单位'
      } else {
        this.queryForm.type="" ;
        this.queryForm.year="" ;
        this.title = '编辑'
        this.queryForm.qs_info_id = row.id ;
        this.fetchData()
        // this.form = Object.assign({}, row)
      }
      this.dialog = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialog = false
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
      this.close()
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleAddEd(row) {
      this.$refs['edit'].showDia()
    },
    handleView(row) {
      this.$refs['views'].showDia(row.id)
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    fetchData() {
      var that = this
      detailInfo(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = res.data.records
          that.total = res.data.total
        }
      })
    },
    handleClose(done) {
      if (this.loading) {
        return
      }
      this.$confirm('确定要保存信息吗？')
        .then((_) => {
          this.loading = true
          this.timer = setTimeout(() => {
            done()
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false
            }, 400)
          }, 2000)
        })
        .catch((_) => { })
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
    },
    sexFormat(row, column) {
      var statusW
      switch (row.sex) {
        case '1':
          statusW = '男'
          break
        case '2':
          statusW = '女'
          break
      }
      return statusW
    },
    typeFormat(row, column) {
      var statusW
      switch (row.verify_type) {
        case '1':
          statusW = '新增'
          break
        case '2':
          statusW = '删除'
          break
        case '3':
          statusW = '修改'
          break
      }
      return statusW
    },
    statusFormat(row, column) {
      var statusW
      switch (row.status) {
        case '0':
          statusW = '待审核'
          break
        case '1':
          statusW = '审核通过'
          break
        case '2':
          statusW = '审核驳回'
          break
      }
      return statusW
    },
    reseat() {
      this.queryForm.type = ''
      this.queryForm.org_code = ''
      this.queryForm.org_name = ''
      this.fetchData()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    }
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-table {
    th.el-table__cell {
      background-color: #f2f8fd !important;
    }
  }
}
</style>