<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">查询条件</span>
            <div class="right">
              <el-button
                icon="el-icon-search"
                type="primary"
                @click="getWithCondition"
              >
                查 询
              </el-button>
              <el-button icon="el-icon-refresh-left" @click="reset">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="协议状态">
                  <el-select
                    v-model="queryForm.status"
                    style="width: 100%"
                    clearable
                  >
                    <el-option label="未确认" value="0"></el-option>
                    <el-option label="已确认" value="1"></el-option>
                    <el-option label="已解约" value="2"></el-option>
                    <el-option label="已过期" value="3"></el-option>
                    <el-option label="已驳回" value="4"></el-option>
                    <el-option label="已撤销" value="5"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="协议名称">
                  <el-input
                    v-model="queryForm.title"
                    @keydown.enter.native="seachEnterFun"
                  ></el-input>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">历史记录</span>
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
            height="calc(100vh - 540px)"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!--            <el-table-column-->
            <!--              show-overflow-tooltip-->
            <!--              type="selection"-->
            <!--              align="center"-->
            <!--            ></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              label="机构名称"
              align="center"
              prop="fixmedins_name"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="mechanism_code"
              label="国家编码"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="title"
              label="协议名称"
              align="center"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="createTime"
              label="发起时间"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="signDate"
              label="签署日期"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="invalid_date"
              label="过期时间"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="rescind"
              label="解约时间"
              align="center"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="revoke_time"
              label="撤销时间"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="revoke_reason"
              label="撤销理由"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="approval_time"
              label="驳回时间"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="approval_opinion"
              label="驳回理由"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="status"
              label="协议状态"
              align="center"
            >
              <template #default="scope">
                <el-tag
                  size="small"
                  type="danger"
                  v-if="scope.row.status == '0' && scope.row.has_sign == '1'"
                >
                  待确认
                </el-tag>
                <el-tag
                  size="small"
                  type="warning"
                  v-if="scope.row.status == '0' && scope.row.has_sign == '2'"
                >
                  待审核
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.status == '1'"
                >
                  已审核
                </el-tag>
                <el-tag
                  size="small"
                  type="danger"
                  v-else-if="scope.row.status == '2'"
                >
                  已解约
                </el-tag>
                <el-tag
                  size="small"
                  type="danger"
                  v-else-if="scope.row.status == '3'"
                >
                  已过期
                </el-tag>
                <el-tag
                  size="small"
                  type="danger"
                  v-else-if="scope.row.status == '4'"
                >
                  已驳回
                </el-tag>
                <el-tag
                  size="small"
                  type="danger"
                  v-else-if="scope.row.status == '5'"
                >
                  已撤销
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="operate_user_name"
              label="审核人"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="操作"
              align="center"
              fixed="right"
              width="250"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handleView(row)"
                  type="primary"
                  size="mini"
                >
                  查看
                </el-button>
                <el-button
                  plain
                  @click="handleSure(row)"
                  type="primary"
                  size="mini"
                  v-show="row.has_sign == '1' && row.status == '0'"
                >
                  确认
                </el-button>
                <el-button
                  plain
                  @click="downPdf(row)"
                  type="primary"
                  size="mini"
                  v-show="row.status == '1'"
                >
                  下载
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
import { getMechanismList, mechanismSure } from '@/api_net/netTagSupp'
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
        status: '',
        title: '',
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
      this.$refs['views'].showDia(row.id)
    },
    async handleSure(row) {
      const { msg, code, data } = await mechanismSure(row)
      if (code == 0) {
        const loading = this.$loading({
          lock: true,
          text: '请在签章完成后刷新本页面',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)',
        })
        window.open(data)
      } else {
        this.$baseMessage(msg, 'error')
      }
    },
    handlecancel(row) {
      if (row.id) {
        this.$baseConfirm('确认进行撤消？', null, async () => {
          // const { msg } = await doDelete({ ids: row.id })
          // this.$baseMessage(msg, 'success')
          // this.fetchData()
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
      const { data, totalCount } = await getMechanismList(this.queryForm)
      console.log(data)
      this.list = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    getWithCondition() {
      this.queryForm.pageNo = 1
      this.queryForm.pageSize = 10
      this.fetchData()
    },

    reset() {
      this.queryForm = {}
      this.queryForm.pageSize = 10
      this.queryForm.pageNo = 1
      this.queryForm.status = ''
      this.fetchData()
    },
    seachEnterFun() {
      this.fetchData(null)
    },
    downPdf(row) {
      console.log(row)
      window.open(row.download_url)
    },
  },
}
</script>
