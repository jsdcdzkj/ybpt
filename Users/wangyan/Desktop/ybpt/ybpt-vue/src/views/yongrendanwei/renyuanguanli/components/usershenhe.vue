<template>
  <el-drawer
    :title="title"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="80%"
    ref="drawer"
    append-to-body
  >
    <div class="drawer_content">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <vab-query-form>
            <vab-query-form-left-panel :span="24">
              <el-form :inline="true" :model="queryForm" @submit.native.prevent>
                <el-form-item label="单位名称">
                  <el-input
                    v-model.trim="queryForm.username"
                    placeholder="单位名称"
                    clearable
                  />
                </el-form-item>
                <el-form-item label="单位编码">
                  <el-input
                    v-model.trim="queryForm.username"
                    placeholder="单位编码"
                    clearable
                  />
                </el-form-item>
                <el-form-item label="姓名">
                  <el-input
                    v-model.trim="queryForm.username"
                    placeholder="姓名"
                    clearable
                  />
                </el-form-item>
                <el-form-item>
                  <el-button
                    icon="el-icon-search"
                    type="primary"
                    @click="queryData"
                  >
                    查询
                  </el-button>
                </el-form-item>
              </el-form>
            </vab-query-form-left-panel>
          </vab-query-form>

          <el-table
            v-loading="listLoading"
            :data="list"
            border
            :element-loading-text="elementLoadingText"
            height="750px"
          >
            <!-- <el-table-column
              show-overflow-tooltip
              type="selection"
              align="center"
            ></el-table-column> -->
            <el-table-column
              show-overflow-tooltip
              prop="id"
              label="id"
              align="center"
              width="80px"
              fixed="left"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="username"
              label="姓名"
              width="120px"
              fixed="left"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="username"
              label="性别"
              width="120px"
              fixed="left"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="username"
              label="年龄"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="username"
              label="身份证号码"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="username"
              label="统筹区"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="username"
              label="所属区"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="username"
              label="单位名称"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="单位编码"
              width="120px"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="参保险种"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="参保状态"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="异地就医"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="死亡标志"
              width="120px"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="datatime"
              label="体检资格"
              align="center"
              width="120px"
            >
              <template #default="{ row }">
                <el-switch
                  v-model="row.username"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                ></el-switch>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="审核状态"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="审核类型"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="250"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  size="mini"
                  plain
                  type="success"
                  @click="handleUser(row)"
                  icon="el-icon-check"
                >
                  审核
                </el-button>
                <el-button
                  size="mini"
                  plain
                  type="primary"
                  @click="handleView(row)"
                  icon="el-icon-view"
                >
                  详情
                </el-button>
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
        <!-- <el-button
          type="primary"
          @click="$refs.drawer.closeDrawer()"
          :loading="loading"
        >
          {{ loading ? '保存中 ...' : '保 存' }}
        </el-button> -->
      </div>
    </div>
    <views ref="views" @fetch-data="fetchData"></views>
  </el-drawer>
</template>

<script>
import { getList, doDelete } from '@/api/userManagement'
import Views from './components/view'
export default {
  name: 'useredit',
  components: { Views },
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
  mounted() {},
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
      if (row.id) {
        this.$confirm('审核通过此条记录, 是否继续?', '提示', {
          confirmButtonText: '通过',
          cancelButtonText: '驳回',
          type: 'warning',
          center: true
        })
          .then(() => {
            this.$message({
              type: 'success',
              message: '审核成功!',
            })
          })
          .catch(() => {
            this.$prompt('请输入驳回原因', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              //inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
              inputErrorMessage: '格式不正确',
            })
              .then(({ value }) => {
                this.$message({
                  type: 'success',
                  message: '已驳回，驳回原因是: ' + value,
                })
              })
              .catch(() => {
                this.$message({
                  type: 'info',
                  message: '取消输入',
                })
              })
          })
      }
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
        .catch((_) => {})
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
    },
  },
}
</script>