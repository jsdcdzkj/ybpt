<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary">查 询</el-button>
              <el-button icon="el-icon-refresh-left">重 置</el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="套餐年份">
                  <el-date-picker v-model.trim="queryForm.username" type="year"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="体检机构">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="套餐名称">
                  <el-select v-model="queryForm.username" class="w">
                    <el-option label="套餐1" vvalue="1"></el-option>
                    <el-option label="套餐1" vvalue="2"></el-option>
                    <el-option label="套餐1" vvalue="3"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="体检进度">
                  <el-select v-model="queryForm.username" class="w">
                    <el-option label="套餐1" vvalue="1"></el-option>
                    <el-option label="套餐1" vvalue="2"></el-option>
                    <el-option label="套餐1" vvalue="3"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="体检时间">
                  <el-date-picker v-model.trim="queryForm.username" type="date"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="报告上传时间">
                  <el-date-picker v-model.trim="queryForm.username" type="date"></el-date-picker>
                </el-form-item>
              </el-col>

            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">预约统计列表</span>
            <div class="right">
              <el-button type="primary" icon="el-icon-upload2" @click="handleDelete">
                导出
              </el-button>

            </div>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" highlight-current-row border
            @current-change="handleCurrentChange" height="420px" @selection-change="setSelectRows">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" width="80px" fixed="left">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="姓名" align="center" prop="username" width="120px" fixed="left">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="truename" width="80px" label="性别" fixed="left" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="bumen" label="身份证号" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="phone" label="单位名称" align="left" minWidth="220px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="套餐年份" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="体检机构" align="left" width="220px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="套餐" align="left" width="220px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="预约时间" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="体检进度" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="体检时间" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="报告上传时间" align="center" width="160px">
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
      if (row.id) {
        this.$baseConfirm('你确定要删除当前项吗', null, async () => {
          const { msg } = await doDelete({ ids: row.id })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        if (this.selectRows != '' && this.selectRows != null) {
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
