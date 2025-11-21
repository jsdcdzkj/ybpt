<template>
  <el-drawer :title="title" :visible.sync="dialog" :before-close="handleClose" direction="rtl" :with-header="false"
    custom-class="box_drawer" size="80%" ref="drawer">
    <div class="drawer_content">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <vab-query-form>
            <vab-query-form-left-panel :span="24">
              <el-form :inline="true" :model="queryForm" @submit.native.prevent>
                <el-form-item label="单位名称">
                  <el-input v-model.trim="queryForm.username" placeholder="单位名称" clearable />
                </el-form-item>
                <el-form-item label="单位编码">
                  <el-input v-model.trim="queryForm.username" placeholder="单位编码" clearable />
                </el-form-item>
                <el-form-item label="单位类型">
                  <el-input v-model.trim="queryForm.username" placeholder="单位类型" clearable />
                </el-form-item>
                <el-form-item>
                  <el-button icon="el-icon-search" type="primary" @click="queryData">
                    查询
                  </el-button>
                </el-form-item>
              </el-form>
            </vab-query-form-left-panel>
          </vab-query-form>

          <el-table v-loading="listLoading" :data="list" border :element-loading-text="elementLoadingText"
            height="750px">
            <!-- <el-table-column
              show-overflow-tooltip
              type="selection"
              align="center"
            ></el-table-column> -->
            <!-- <el-table-column
              show-overflow-tooltip
              prop="id"
              label="id"
              align="center"
            ></el-table-column> -->
            <el-table-column show-overflow-tooltip prop="username" label="单位名称"></el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="单位编码"></el-table-column>

            <el-table-column show-overflow-tooltip prop="email" label="单位类型"></el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="单位地址"></el-table-column>

            <el-table-column show-overflow-tooltip prop="datatime" label="上级行政单位" align="center">
              <template #default="{ row }">
                <el-select v-model="row.username" class="w">
                  <el-option label="中心经办系统" value="0"></el-option>
                  <el-option label="网上经办系统" value="1"></el-option>
                  <el-option label="APP" value="2"></el-option>
                  <el-option label="一体机" value="3"></el-option>
                  <el-option label="其他" value="4"></el-option>
                </el-select>
              </template>
            </el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="100" align="center">
              <template #default="{ row }">
                <el-button size="mini" plain type="primary" @click="handleUser(row)" icon="el-icon-user"></el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- <el-pagination
            background
            :current-page="queryForm.pageNo"
            :page-size="queryForm.pageSize"
            :layout="layout"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          ></el-pagination> -->
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
        <el-button type="primary" @click="$refs.drawer.closeDrawer()" :loading="loading">
          {{ loading ? '保存中 ...' : '保 存' }}
        </el-button>
      </div>
    </div>
    <adminuser ref="adminuser" @fetch-data="fetchData"></adminuser>
  </el-drawer>
</template>

<script>
import { getList, doDelete } from '@/api/userManagement'
import Adminuser from './adminuser'
export default {
  name: 'useredit',
  components: { Adminuser },
  data() {
    return {
      list: null,
      form: [],
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
      title: '',
      dialog: false,
      loading: false,
      formLabelWidth: '100px',
      timer: null,
    }
  },
  created() {
    this.fetchData()
  },
  mounted() { },
  methods: {
    showDia(row) {
      if (!row) {
        this.title = '用人单位'
      } else {
        this.title = '编辑'
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
    handleUser(row) {
      this.$refs['adminuser'].showDia(row.id)
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
    async fetchData() {
      this.listLoading = true
      const { data, totalCount } = await getList(this.queryForm)
      this.list = data
      this.total = totalCount
      setTimeout(() => {
        this.listLoading = false
      }, 300)
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
  },
}
</script>