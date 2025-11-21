<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <el-button icon="el-icon-search" type="primary" class="right">
              查 询
            </el-button>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="姓名">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="身份证号">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="单位名称">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">异地安置人员列表</span>
            <el-button type="primary" class="right" icon="el-icon-download" @click="handleExport">导出</el-button>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" highlight-current-row border
            @current-change="handleCurrentChange" class="tableheighthaspage" @selection-change="setSelectRows">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column show-overflow-tooltip type="selection"></el-table-column>
            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" width="80px"></el-table-column>
            <el-table-column show-overflow-tooltip label="审核状态" align="center" prop="username" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="truename" width="120px" label="有效状态" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="bumen" label="人员姓名" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="phone" label="证件类型" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="证件号码" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="weixin" label="医保门慢门特病种名称" align="center" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="leixing" label="参保险种" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="单位名称"></el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="80" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handleAdd(row)" type="primary" size="mini" icon="el-icon-view">

                </el-button>

              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>

    <edit ref="edit" @fetch-data="fetchData"></edit>

  </div>
</template>

<script>
import Edit from './components/edit'
import { getList, doDelete } from '@/api/userManagement'
export default {
  name: 'Index',
  components: { Edit },
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
    }
  },
  created() {
    this.fetchData()
  },
  beforeDestroy() { },
  mounted() { },
  methods: {
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
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handleExport(row) {
      if (row.id) {
        this.$baseConfirm('你确定要导出当前项吗', null, async () => {
          const { msg } = await doDelete({ ids: row.id })
          this.$baseMessage('导出成功', 'success')
          this.fetchData()
        })
      } else {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.map((item) => item.id).join()
          this.$baseConfirm('你确定要导出选中项吗', null, async () => {
            const { msg } = await doDelete({ ids })
            this.$baseMessage('导出成功', 'success')
            this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
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
    async fetchData() {
      this.listLoading = true
      const { data, totalCount } = await getList(this.queryForm)
      this.list = data
      this.total = totalCount
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
