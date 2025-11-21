<template>
  <el-drawer
    :title="title"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="80%"
    ref="drawer"
  >
    <div class="drawer_content">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <!-- <vab-query-form>
           
            <vab-query-form-right-panel :span="12">
              <el-form :inline="true" :model="queryForm" @submit.native.prevent>
                <el-form-item>
                  <el-input
                    v-model.trim="queryForm.username"
                    placeholder="请输入姓名"
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
            </vab-query-form-right-panel>
          </vab-query-form> -->

          <el-table
            v-loading="listLoading"
            :data="list"
            border
            :element-loading-text="elementLoadingText"          
            height="700px"
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
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="username"
              label="姓名"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="性别"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="年龄"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="身份证号"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="datatime"
              label="单位名称"
              align="center"
            ></el-table-column>            
          </el-table>
          <el-pagination
            background
            :current-page="queryForm.pageNo"
            :page-size="queryForm.pageSize"
            :layout="layout"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          ></el-pagination>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
      </div>
    </div>    
  </el-drawer>
</template>

<script>
import { getList, doDelete } from '@/api/userManagement'
export default {
  name: 'useredit',
  components: {},
  data() {
    return {
      list: null,
      form:[],
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
        this.title = '新增'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
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
    handleEdit(row) {
      if (row.id) {
        this.$refs['edit'].showEdit(row)
      } else {
        this.$refs['edit'].showEdit()
      }
    },
    handleDelete(row) {
      if (row.id) {
        this.$baseConfirm('你确定要删除当前项吗', null, async () => {
          const { msg } = await doDelete({ ids: row.id })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        if (this.selectRows != '') {
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
      this.$confirm('确定要提交表单吗？')
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