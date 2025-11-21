<template>
  <div class="index-container">
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
                <el-form-item label="机构名称">
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
            <span class="tips">自主体检审核列表</span>
            <el-button type="primary" class="right" icon="el-icon-document-checked" @click="handleSh">
              批量审核
            </el-button>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" highlight-current-row border @selection-change="setSelectRows"
            height="calc(100% - 50px)">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column show-overflow-tooltip type="selection" align="center" fixed="left" width="80px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" fixed="left" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="审核状态" width="120px" align="center" prop="username">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="truename" width="220px" label="审核时间" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="bumen" label="存续状态" width="120px" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="phone" label="终止时间" width="120px" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="phone" label="审核人" width="120px" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="申请单位名称" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="weixin" label="申请时间" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="leixing" label="申请理由" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="创建时间" width="120px">
            </el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="200" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handleAdd(row)" type="primary" size="mini">
                  审核
                </el-button>
                <el-button plain @click="handleUser(row)" type="primary" size="mini">
                  人员信息
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <views ref="views" @fetch-data="fetchData"></views>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <shenhe ref="shenhe" @fetch-data="fetchData"></shenhe>

  </div>
</template>

<script>
import { getList } from '@/api/userManagement'
import Shenhe from '@/components/allshenhe'
import Edit from './components/edit'
import Views from './components/usermana'
export default {
  name: 'Index',
  components: {Views,Edit,Shenhe },
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
    handleUser(row) {
      if (row.id) {
        this.$refs['views'].showDia(row)
      } else {
        this.$refs['views'].showDia()
      }
    },
    handleAdd(row) {
      this.$refs['edit'].showDia(row)
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleSh() {
      if (this.selectRows.length > 0) {
        const ids = this.selectRows.map((item) => item.id).join()
        this.$baseConfirm('你确定要审核选中项吗', null, async () => {
          // const { msg } = await doDelete({ ids })
          this.$refs['shenhe'].showDia()
          //this.$baseMessage('模拟审核成功', 'success')
          //this.fetchData()
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
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
<style lang="scss" scoped>
.index-container {
  padding: 0 !important;
  margin: 0 !important;
  background: #f5f7f8 !important;

  .bottom {
    padding-top: 20px;
    margin-top: 5px;
    color: #595959;
    text-align: left;
    border-top: 1px solid $base-border-color;
  }

  .bottom-btn {
    button {
      margin: 5px 10px 15px 0;
    }
  }
}
</style>
