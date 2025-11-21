<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">补充协议管理列表</span>
            <div class="right">
              <el-button type="success" icon="el-icon-plus" @click="handleAdd">
                添加补充协议
              </el-button>
            </div>
          </div>
          <el-table
            v-loading="listLoading"
            ref="listTable"
            stripe
            :data="list"
            :element-loading-text="elementLoadingText"
            highlight-current-row
            border
            @current-change="handleCurrentChange"
            height="calc(100vh - 378px)"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!--            <el-table-column show-overflow-tooltip type="selection"></el-table-column>           -->
            <el-table-column
              show-overflow-tooltip
              label="补充协议名称"
              align="center"
              prop="title"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="createName"
              label="创建人"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="createTime"
              label="创建日期"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="invalid_date"
              label="到期日期"
              align="center"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              label="操作"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button plain @click="handleView(row)" size="mini">
                  查看
                </el-button>
                <el-button
                  plain
                  @click="handleAdd(row)"
                  type="primary"
                  size="mini"
                >
                  修改
                </el-button>
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
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import Cardnum from '@/components/cardno'
import Edit from './components/edit'
import Views from './components/view'
import { getList } from '@/api_net/netTagSupp'
export default {
  name: 'Index',
  components: { Cardnum, Edit, Views },
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
  beforeDestroy() {},
  mounted() {},
  methods: {
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    handleAdd(row) {
      if (row) {
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
    handleView(row) {
      this.$refs['views'].showDia(row)
    },
    handlecancel(row) {
      if (row.id) {
        this.$baseConfirm('确认进行撤消？', null, async () => {
          // const { msg } = await doDelete({ ids: row.id })
          // this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
      }
    },
    handleExport() {
      this.$baseConfirm('确认进行导出？', null, async () => {
        this.$baseMessage('导出成功', 'success')
      })
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
      console.log(data)
      this.list = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
