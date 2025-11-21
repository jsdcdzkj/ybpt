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
              <el-button icon="el-icon-refresh-left" @click="resetData">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构类别">
                  <el-select
                    v-model="queryForm.category_id"
                    @change="getLevelList"
                    style="width: 100%"
                  >
                    <el-option label="请选择" value=""></el-option>
                    <el-option label="医疗机构" value="1"></el-option>
                    <el-option label="零售药店" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构等级">
                  <el-select
                    v-model="queryForm.net_grade_id"
                    collapse-tags
                    placeholder="请选择"
                    class="w"
                  >
                    <el-option label="请选择" value=""></el-option>
                    <el-option
                      v-for="item in levelList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="网签年份">
                  <el-date-picker
                    v-model="queryForm.year"
                    type="year"
                    value-format="yyyy"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">协议管理列表</span>
            <div class="right">
              <el-dropdown @command="handleCommandmore">
                <el-button type="primary">
                  新增
                  <i class="el-icon-arrow-down el-icon--right"></i>
                </el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="a">一级</el-dropdown-item>
                  <el-dropdown-item command="b">二级</el-dropdown-item>
                  <el-dropdown-item command="c">三级</el-dropdown-item>
                  <el-dropdown-item command="d">A级</el-dropdown-item>
                  <el-dropdown-item command="e">B级</el-dropdown-item>
                  <el-dropdown-item command="f">C级</el-dropdown-item>
                  <el-dropdown-item command="g">未定级</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
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
            height="calc(100vh - 540px)"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column
              show-overflow-tooltip
              label="机构类别"
              align="center"
              prop="category_id"
            >
              <template slot-scope="scope">
                <span v-if="scope.row.category_id == 1">医疗机构</span>
                <span v-else>零售药店</span>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="net_grade_id"
              label="机构等级"
              align="center"
            >
              <!-- 0一级 1二级三级 2未定级 3A级别 4B级别 5C级别 -->
              <template slot-scope="scope">
                <span v-if="scope.row.net_grade_id == '1'">一级</span>
                <span v-else-if="scope.row.net_grade_id == '2'">二级</span>
                <span v-else-if="scope.row.net_grade_id == '3'">三级</span>
                <span v-else-if="scope.row.net_grade_id == '4'">A级</span>
                <span v-else-if="scope.row.net_grade_id == '5'">B级</span>
                <span v-else-if="scope.row.net_grade_id == '6'">C级</span>
                <span v-else-if="scope.row.net_grade_id == '9'">未定级</span>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="year"
              label="网签年份"
              align="center"
            ></el-table-column>
            <!--            <el-table-column show-overflow-tooltip prop="createUser" label="创建人" align="center" width="120px"></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="status"
              label="协议状态"
              align="center"
            >
              <template #default="{ row }">
                <el-button
                  size="mini"
                  plain
                  type="success"
                  v-if="row.status == 0"
                  @click="showStatus(row)"
                >
                  已上线
                </el-button>
                <el-button
                  size="mini"
                  plain
                  type="danger"
                  v-else-if="row.status == 1"
                  @click="showStatus(row)"
                >
                  已下线
                </el-button>
                <el-button
                  size="mini"
                  plain
                  type="warning"
                  v-else-if="row.status == 2"
                  @click="showStatus(row)"
                >
                  部分下线
                </el-button>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="createTime"
              label="创建日期"
              align="center"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              label="操作"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <div v-if="row.status == '0'">
                  <el-button plain @click="handleView(row)" size="mini">
                    查看
                  </el-button>
                  <!--                  <el-button plain @click="handleAdd(row)" type="primary" size="mini">修改</el-button>-->
                </div>
                <div v-if="row.status != '0'">
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
                </div>
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
    <status ref="status" @fetch-data="fetchData"></status>
  </div>
</template>

<script>
import Cardnum from '@/components/cardno'
import Edit from './components/edit'
import Views from './components/view'
import Status from './components/status'
import { geAgreementtList } from '@/api_net/tagAgreement'
import { selectOrganizationLevel } from '@/api_net/netTagMechanism'

export default {
  name: 'xygl',
  components: { Cardnum, Edit, Views, Status },
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
      aList: [
        { id: 1, name: '一级' },
        { id: 2, name: '二级' },
        { id: 3, name: '三级' },
        { id: 9, name: '未定级' },
      ],
      bList: [
        { id: 4, name: 'A级' },
        { id: 5, name: 'B级' },
        { id: 6, name: 'C级' },
      ],
      levelList: [],
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        net_grade_id: '',
        category_id: '',
        year: '',
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
    handlecancel(row) {
      if (row.id) {
        this.$baseConfirm('确认进行撤消？', null, async () => {
          const { msg } = await doDelete({ ids: row.id })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
      }
    },
    handleCommandmore(command) {
      let row = {}
      switch (command) {
        case 'a':
          // 一级
          row.category_id = 1
          row.net_grade_id = 1
          break
        case 'b':
          // 二级
          row.category_id = 1
          row.net_grade_id = 2
          break
        case 'c':
          //三级
          row.category_id = 1
          row.net_grade_id = 3
          break
        case 'd':
          // A级
          row.category_id = 2
          row.net_grade_id = 4
          break
        case 'e':
          // B级
          row.category_id = 2
          row.net_grade_id = 5
          break
        case 'f':
          // C级
          row.category_id = 2
          row.net_grade_id = 6
          break
        case 'g':
          // 未定级
          row.category_id = 1
          row.net_grade_id = 9
          break
        default:
          row.net_grade_id = -1
          //这里是没有找到对应的值处理
          break
      }
      if (row.net_grade_id != -1) {
        this.$refs['edit'].showDia(row)
      }
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    resetData() {
      this.queryForm = {
        pageNo: 1,
        pageSize: 10,
        net_grade_id: '',
        category_id: '',
        year: '',
      }
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    showStatus(row) {
      console.log(row)
      this.$refs['status'].showDia(row)
      // this.$baseMessage('状态修改成功', 'success')
    },
    getLevelList() {
      if (this.queryForm.category_id == 1) {
        this.levelList = this.aList
      } else if (this.queryForm.category_id == 2) {
        this.levelList = this.bList
      } else {
        this.levelList = []
      }
    },
    async fetchData() {
      this.listLoading = true
      const { data, totalCount } = await geAgreementtList(this.queryForm)
      this.list = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
