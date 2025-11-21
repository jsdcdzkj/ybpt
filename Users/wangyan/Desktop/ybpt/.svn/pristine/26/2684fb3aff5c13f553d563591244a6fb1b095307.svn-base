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
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="套餐名称">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="套餐年份">
                  <el-date-picker v-model.trim="queryForm.username" type="year"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">通用套餐列表</span>
            <div class="right">
              <el-button type="success" icon="el-icon-plus" @click="handleAdd">
                新增
              </el-button>

            </div>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" highlight-current-row border
            @current-change="handleCurrentChange" height="420px" @selection-change="setSelectRows">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>

            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" width="80px"></el-table-column>
            <el-table-column show-overflow-tooltip label="套餐名称" align="left" prop="username"></el-table-column>
            <el-table-column show-overflow-tooltip prop="truename" width="120px" label="套餐年份" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="bumen" label="套餐金额" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="bumen" label="标准金额" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="套餐内容" align="center" width="120px">
              <template #default="{ row }">
                <el-button plain @click="handleView(row)" type="primary" size="mini">查看
                </el-button>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="phone" label="套餐状态" align="center" width="120px">
              <template #default="{ row }">
                <el-switch v-model="queryForm.delivery"></el-switch>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="审核状态" align="left" width="120px">
            </el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handleAdd(row)" type="primary" size="mini" icon="el-icon-edit">
                </el-button>
                <el-button plain @click="handleDelete(row)" type="danger" size="mini" icon="el-icon-delete">
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
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import { doDelete, getList } from '@/api/userManagement'
import Edit from './components/edit'
import Views from './components/views'
export default {
  name: 'Index',
  components: {Edit,Views},
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
        delivery: false,
      },
    }
  },
  created() {
    this.fetchData()
  },
  beforeDestroy() {},
  mounted() {},
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
    handleView(row) {
      if (row.id) {
        this.$refs['views'].showDia(row)
      } else {
        this.$refs['views'].showDia()
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
      if (row.id) {
        this.$baseConfirm('你确定要删除当前项吗', null, async () => {
          const { msg } = await doDelete({ ids: row.id })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        if (this.selectRows != ''&&this.selectRows != null) {
          const ids = this.selectRows.map((item) => item.id).join()
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const { msg } = await doDelete({ ids })
            this.$baseMessage(msg, 'success')
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
