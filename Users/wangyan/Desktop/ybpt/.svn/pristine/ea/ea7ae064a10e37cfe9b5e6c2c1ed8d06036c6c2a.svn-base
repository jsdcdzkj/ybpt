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
                @click="queryData"
              >
                查 询
              </el-button>
              <el-button icon="el-icon-refresh-left" @click="ResetClick">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="网签状态">
                  <el-select
                    v-model="queryForm.status"
                    style="width: 100%"
                    clearable
                  >
                    <el-option label="已签章" value="1"></el-option>
                    <el-option label="已解约" value="2"></el-option>
                    <el-option label="已过期" value="3"></el-option>
                    <el-option label="已驳回" value="4"></el-option>
                    <el-option label="已撤销" value="5"></el-option>

                    <!--                    <el-option label="已驳回" value="4"></el-option>-->
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="网签年份">
                  <el-date-picker
                    v-model="queryForm.year"
                    type="year"
                    value-format="yyyy"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                  ></el-date-picker>
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
            <span class="tips">申请记录</span>
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
            height="calc(100vh - 590px)"
          >
            <!-- <template slot="empty">
              <el-empty image-size="200"></el-empty>
            </template> -->
            <!-- <el-table-column
              show-overflow-tooltip
              type="selection"
              align="center"
            ></el-table-column> -->
            <el-table-column
              show-overflow-tooltip
              prop="cred_lv_type"
              label="机构类别"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              width="120px"
              label="网签年份"
              align="center"
              prop="agreemen_year"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              width="120px"
              label="国家编码"
              align="center"
              prop="mechanism_code"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="fixmedins_name"
              label="机构名称"
              align="center"
            ></el-table-column>
            <!--            <el-table-column-->
            <!--              show-overflow-tooltip-->
            <!--              prop="medical_code"-->
            <!--              label="医保编码"-->
            <!--              align="center"-->
            <!--              width="120px"-->
            <!--            ></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="cred_lv_name"
              label="机构等级"
              align="center"
              width="120px"
            ></el-table-column>

            <!-- <el-table-column
              show-overflow-tooltip
              prop="biz_way"
              label="经营性质"
              align="center"
              width="180px"
            ></el-table-column> -->
            <el-table-column
              show-overflow-tooltip
              prop="dept_resper_tel"
              label="联系电话"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="legrep_name"
              label="法人代表人"
              align="center"
              width="200px"
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
              prop="status_name"
              label="网签状态"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="approval_opinion"
              label="审批意见"
              align="center"
              width="180px"
            ></el-table-column>
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
              width="240"
              align="center"
              fixed="right"
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
                  @click="handlecancel(row)"
                  type="danger"
                  size="mini"
                  v-if="row.status == 1"
                >
                  解约
                </el-button>
                <el-button
                  plain
                  disabled
                  type="danger"
                  size="mini"
                  v-if="row.status != 1"
                >
                  解约
                </el-button>
                <el-button
                  plain
                  @click="downPdf(row)"
                  type="primary"
                  size="mini"
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
import {
  pageList,
  selectOrganizationLevel,
  updJy,
  getXzqh,
} from '@/api_net/netTagMechanism'
export default {
  name: 'Index',
  components: { Cardnum, Edit, Views },
  data() {
    return {
      start: '',
      checked: false,
      isShow: false,
      list: null,
      levelList: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      xzqhs: [],
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        type: 0, //协议
        type_status: 2, //历史网签
        fixmedins_name: '', //机构名称
        startTime: '',
        endTime: '',
        year: '',
        status: '',
        mechanism_code: '',
      },
    }
  },
  created() {
    this.fetchData()
    this.getXzqh()
  },
  methods: {
    getXzqh() {
      getXzqh().then((res) => {
        if (res.code == 0) {
          this.xzqhs = res.data
        }
      })
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    handlecancel(row) {
      if (row.id) {
        this.$baseConfirm('确认进行解约？', null, async () => {
          const { data } = await updJy({ id: row.id })
          console.log(data)
          if (data.code == 0) {
            this.$baseMessage('操作成功', 'success')
            this.fetchData()
          } else {
            this.$baseMessage('操作失败', 'error')
          }
        })
      }
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
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    ResetClick() {
      this.queryForm.startTime = ''
      this.queryForm.start = ''
      this.queryForm.endTime = ''
      this.queryForm.year = ''
      this.queryForm.status = ''
      this.queryForm.mechanism_code = ''
      this.queryForm.admdvs = ''
      this.queryForm.pageSize = 10
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    formatDate: function (row, column) {
      let data = row[column.property]
      if (data == null || data == '') {
        return null
      }
      let date = new Date(data)
      var o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds(),
        'q+': Math.floor((date.getMonth() + 3) / 3),
        S: date.getMilliseconds(),
      }
      var fmt = 'yyyy-MM-dd'
      if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(
          RegExp.$1,
          (date.getFullYear() + '').substr(4 - RegExp.$1.length)
        )
      }
      for (var k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
          fmt = fmt.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ('00' + o[k]).substr(('' + o[k]).length)
          )
        }
      }
      return fmt
    },
    getLevelList() {
      if (this.queryForm.category_id) {
        selectOrganizationLevel(this.queryForm.category_id).then((res) => {
          if (res.code == 0) {
            this.levelList = res.data
          }
        })
      } else {
        console.log('机构类别联动机构等级空')
      }
    },
    getQueryDate() {
      if (
        null == this.queryForm.start ||
        [] == this.queryForm.start ||
        '' == this.queryForm.start
      ) {
        this.queryForm.startTime = ''
        this.queryForm.endTime = ''
      } else {
        this.queryForm.startTime = this.dateFormat(this.queryForm.start[0])
        this.queryForm.endTime = this.dateFormat1(this.queryForm.start[1])
        console.log(this.queryForm.startTime)
      }
    },
    dateFormat(time) {
      return `${time.getFullYear()}-${
        time.getMonth() + 1 >= 10
          ? time.getMonth() + 1
          : '0' + (time.getMonth() + 1)
      }-${time.getDate() >= 10 ? time.getDate() : '0' + time.getDate()}`
    },
    dateFormat1(time) {
      return `${time.getFullYear()}-${
        time.getMonth() + 1 >= 10
          ? time.getMonth() + 1
          : '0' + (time.getMonth() + 1)
      }-${time.getDate() >= 10 ? time.getDate() + 1 : '0' + time.getDate() + 1}`
    },
    async fetchData() {
      this.listLoading = true
      const { data } = await pageList(this.queryForm)
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