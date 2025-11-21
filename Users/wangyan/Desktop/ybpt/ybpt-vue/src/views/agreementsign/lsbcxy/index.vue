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
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
                <el-form-item label="网签状态">
                  <el-select
                    v-model="queryForm.status"
                    style="width: 100%"
                    clearable
                    @change="getWithCondition"
                  >
                    <el-option label="已审核" value="1"></el-option>
                    <el-option label="已解约" value="2"></el-option>
                    <el-option label="已过期" value="3"></el-option>
                    <el-option label="已驳回" value="4"></el-option>
                    <el-option label="已撤销" value="5"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
                <el-form-item label="协议名称">
                  <el-input
                    v-model="queryForm.title"
                    @keydown.enter.native="seachEnterFun"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
                <el-form-item label="国家编码">
                  <el-input
                    v-model="queryForm.mechanism_code"
                    @keydown.enter.native="seachEnterFun"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
                <el-form-item label="属地">
                  <el-select
                    v-model="queryForm.admdvs"
                    style="width: 100%"
                    clearable
                  >
                    <el-option
                      v-for="i in xzqhs"
                      :label="i.emp_name"
                      :key="i.id"
                      :value="i.admdvs"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">历史补充协议申请记录列表</span>
            <!-- <div class="right">
              <el-button type="success" icon="el-icon-plus" @click="handleAdd">
                新增补充协议
              </el-button>
            </div> -->
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
            <!--                        <el-table-column show-overflow-tooltip type="selection"></el-table-column>-->
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
              label="协议名称"
              align="center"
              prop="title"
            ></el-table-column>
            <!--                        <el-table-column show-overflow-tooltip prop="legrep_name" label="法定代表人" align="center"-->
            <!--                                         width="120px">-->
            <!--                        </el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="dept_resper_name"
              label="联系人"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="dept_resper_tel"
              label="联系电话"
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
              label="到期日期"
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
              label="网签状态"
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
                  @click="handlecancel(row, 2)"
                  type="danger"
                  size="mini"
                  v-show="row.status == '1'"
                >
                  解约
                </el-button>
                <el-button
                  plain
                  @click="handlecancel(row, 5)"
                  type="danger"
                  size="mini"
                  v-show="row.status == '0' && row.has_sign == '1'"
                >
                  撤销
                </el-button>
                <el-button
                  plain
                  @click="handleVerify(row)"
                  type="primary"
                  size="mini"
                  v-show="row.status == '0' && row.has_sign == '2'"
                >
                  审核
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
    <verify ref="verify" @fetch-data="fetchData"></verify>
  </div>
</template>

<script>
import Cardnum from '@/components/cardno'
import Edit from './components/edit'
import Views from './components/view'
import Verify from './components/verify'
import { getAllNetTagMechanism } from '@/api_net/netTagSupp'
import { updJy, getXzqh } from '@/api_net/netTagMechanism'

export default {
  name: 'Index',
  components: { Cardnum, Edit, Views, Verify },
  data() {
    return {
      value1: [],
      realYear: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      xzqhs: [],
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        status: '',
        start_time: '',
        end_time: '',
        year: '',
        mechanism_code: '',
        title: '',
      },
    }
  },
  created() {
    this.fetchData()
    this.getXzqh()
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    getXzqh() {
      getXzqh().then((res) => {
        if (res.code == 0) {
          this.xzqhs = res.data
        }
      })
    },
    reset() {
      this.queryForm = {}
      this.queryForm.pageSize = 10
      this.queryForm.pageNo = 1
      this.value1 = []
      this.realYear = ''
      this.fetchData()
    },
    getWithCondition() {
      console.log(this.value1)

      if (
        null == this.realYear ||
        undefined == this.realYear ||
        '' == this.realYear
      ) {
      } else {
        this.queryForm.year = this.realYear.getFullYear()
      }

      if (false == this.value1) {
      } else {
        var realtime1 = this.getTimeToString(this.value1[0])
        var realtime2 = this.getTimeToString1(this.value1[1])
        this.queryForm.start_time = realtime1
        this.queryForm.end_time = realtime2
      }
      this.fetchData()
    },

    getTimeToString1(time, sign) {
      if (!sign) {
        sign = '-'
      }
      let year = time.getFullYear()
      let month = time.getMonth() + 1
      let sun = time.getDate() + 1
      let hours = time.getHours()
      let minutes = time.getMinutes()
      let seconds = time.getSeconds()

      return (
        year +
        sign +
        this.mendZero(month) +
        sign +
        this.mendZero(sun) +
        ' ' +
        this.mendZero(hours) +
        ':' +
        this.mendZero(minutes) +
        ':' +
        this.mendZero(seconds)
      )
    },

    getTimeToString(time, sign) {
      if (!sign) {
        sign = '-'
      }
      let year = time.getFullYear()
      let month = time.getMonth() + 1
      let sun = time.getDate()
      let hours = time.getHours()
      let minutes = time.getMinutes()
      let seconds = time.getSeconds()

      return (
        year +
        sign +
        this.mendZero(month) +
        sign +
        this.mendZero(sun) +
        ' ' +
        this.mendZero(hours) +
        ':' +
        this.mendZero(minutes) +
        ':' +
        this.mendZero(seconds)
      )
    },

    //时间转换补零
    mendZero(num) {
      return (num = num < 10 ? '0' + num : num)
    },

    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    handleAdd() {
      this.$refs['edit'].showDia()
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
      console.log(row)
      this.$refs['views'].showDia(row.id)
    },
    handleVerify(row) {
      this.$refs['verify'].showDia(row.id)
    },
    handlecancel(row, status) {
      if (row.id) {
        if (row.status == 2) {
          this.$baseMessage('已解约无需重复解约', 'error')
        } else {
          this.$baseConfirm('确认进行解约？', null, async () => {
            const { msg } = await updJy({ id: row.id, status: status })
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        }
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
      const { data, totalCount } = await getAllNetTagMechanism(this.queryForm)
      this.list = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    seachEnterFun() {
      this.fetchData()
    },
    downPdf(row) {
      console.log(row)
      window.open(row.download_url)
    },
  },
}
</script>
