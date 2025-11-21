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
          <el-form ref="queryForm" :model="queryForm" label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
                <el-form-item label="国家编码">
                  <el-input
                    v-model="queryForm.mechanism_code"
                    @keydown.enter.native="seachEnterFun"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
                <el-form-item label="机构类别">
                  <el-select
                    v-model="queryForm.category_id"
                    @change="getLevelList"
                    style="width: 100%"
                    clearable
                  >
                    <el-option label="医疗机构" value="1"></el-option>
                    <el-option label="零售药店" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="6">
                <el-form-item label="机构等级">
                  <el-select
                    v-model="queryForm.net_grade_id"
                    collapse-tags
                    placeholder="请选择"
                    class="w"
                    clearable
                  >
                    <el-option
                      v-for="item in levelList"
                      :key="item.cred_lv"
                      :label="item.cred_lv_name"
                      :value="item.cred_lv"
                    ></el-option>
                  </el-select>
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
            height="calc(100vh - 540px)"
          >
            <!-- <template slot="empty">
              <el-empty image-size="200"></el-empty>
            </template> -->

            <el-table-column
              show-overflow-tooltip
              prop="cred_lv_type"
              label="机构类别"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="年份"
              align="center"
              prop="agreemen_year"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
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
            <!--            <el-table-column show-overflow-tooltip prop="medical_code" label="医保编码" align="center" width="120px"></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="cred_lv_name"
              label="机构等级"
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
              prop="legrep_name"
              label="法人代表人"
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
              prop="status_name"
              label="网签状态"
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
                  @click="handleView(row)"
                  type="primary"
                  size="mini"
                >
                  审核
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
  getXzqh,
} from '@/api_net/netTagMechanism'
export default {
  name: 'Index',
  components: { Cardnum, Edit, Views },
  data() {
    return {
      checked: false,
      isShow: false,
      list: null,
      levelList: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      xzqhs: [],
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        status: '0',
        type: '0', //协议
        fixmedins_name: '', //机构名称
        category_id: '',
        net_grade_id: '',
        mechanism_code: '',
        type_status: 1, //网签审核
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
    ResetClick() {
      this.queryForm.mechanism_code = ''
      this.queryForm.net_grade_id = ''
      this.queryForm.category_id = ''
      this.queryForm.admdvs = ''
      this.levelList = []
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
        this.levelList = [] //清空级联下来数据
      }
    },
    async fetchData(id) {
      this.listLoading = true
      console.log(this.queryForm)
      const { data } = await pageList(this.queryForm)
      console.log(data)
      this.list = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    seachEnterFun() {
      this.fetchData(null)
    },
  },
}
</script>