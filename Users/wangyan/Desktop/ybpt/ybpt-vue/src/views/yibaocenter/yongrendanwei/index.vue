<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button
                icon="el-icon-search"
                type="primary"
                @click="queryData"
              >
                查 询
              </el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="单位名称">
                  <el-input
                    v-model.trim="queryForm.emp_name"
                    @keyup.enter.native="queryData()"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="单位编码">
                  <el-input
                    v-model.trim="queryForm.emp_no"
                    @keyup.enter.native="queryData()"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="统筹区">
                  <el-select
                    v-model="queryForm.admdvs"
                    @keyup.enter.native="queryData"
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
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">用人单位列表</span>
            <div class="right">
              <!--<el-button type="primary" icon="el-icon-document-checked" @click="handleSh">-->
              <!--批量审核-->
              <!--</el-button>             -->
              <!--<el-button icon="el-icon-upload2" @click="handleTb(1)">-->
              <!--数据同步-->
              <!--</el-button>             -->
            </div>
          </div>
          <el-table
            ref="listTable"
            stripe
            :data="list"
            :element-loading-text="elementLoadingText"
            highlight-current-row
            border
            @current-change="handleCurrentChange"
            height="calc(100% - 50px)"
            @selection-change="setSelectRows"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!--<el-table-column-->
            <!--show-overflow-tooltip-->
            <!--type="selection"-->
            <!--fixed="left"-->
            <!--align="center"-->
            <!--&gt;</el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="id"
              label="序号"
              align="center"
              width="80px"
              fixed="left"
            >
              <template slot-scope="scope">
                <span v-text="getIndex(scope.$index)"></span>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="单位名称"
              align="center"
              prop="emp_name"
              fixed="left"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="emp_no"
              width="280px"
              label="单位编码"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="单位类型"
              align="center"
              prop="emp_type"
              width="150px"
              fixed="left"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="emp_address"
              label="单位地址"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="admdvs"
              label="所属统筹区"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="parentOrgCode"
              label="市直单位"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="200"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="deptEdit(row)"
                  type="primary"
                  size="mini"
                >
                  部门维护
                </el-button>
                <el-button
                  plain
                  @click="handleAdd(row)"
                  type="primary"
                  size="mini"
                >
                  修改
                </el-button>
                <!--<el-button-->
                <!--plain-->
                <!--@click="handleShenhe(row)"-->
                <!--type="primary"-->
                <!--size="mini"                 -->
                <!--&gt;审核</el-button>-->
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
    <usermana ref="usermana" @fetch-data="fetchData"></usermana>
    <usershenhe ref="usershenhe" @fetch-data="fetchData"></usershenhe>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <shenhe ref="shenhe" @fetch-data="fetchData"></shenhe>
    <department ref="department" @fetch-data="fetchData"></department>
  </div>
</template>

<script>
  import Edit from './components/edit'
  import Usermana from './components/usermana'
  import Usershenhe from './components/singlesh'
  import Shenhe from './components/shenhe'
  import Department from './components/department'
  import { selectList } from '@/api_check/EmployingInfo.js'
  import { getXzqh } from '@/api_check/civilworker_info.js'
  export default {
    name: 'Index',
    components: { Edit, Usermana, Usershenhe, Shenhe, Department },
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
        xzqhs: [],
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          emp_name: '',
          emp_no: '',
          admdvs: '',
        },
      }
    },
    created() {
      this.fetchData()
      this.getXzqhsApi()
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      async getXzqhsApi() {
        const { data } = await getXzqh()
        this.xzqhs = data
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd(row) {
        if (row.id) {
          this.$refs['edit'].showDia(row)
        } else {
          this.$refs['edit'].showDia()
        }
      },
      deptEdit(row) {
        console.log(row)
        this.$refs['department'].showDia(row)
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
      handleUser() {
        this.$refs['usermana'].showDia()
      },
      handleShenhe(row) {
        this.$refs['usershenhe'].showDia(row)
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
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
      handleTb(val) {
        if (val == 1) {
          this.$baseConfirm('你确定要初始化同步数据吗？', null, async () => {
            const loading = this.$loading({
              lock: true,
              text: '数据初始化同步中...',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            setTimeout(() => {
              loading.close()
              this.$baseMessage('初始化成功', 'success')
            }, 2000)
          })
        } else {
          this.$baseConfirm('你确定要增量同步数据吗？', null, async () => {
            const loading = this.$loading({
              lock: true,
              text: '数据增加同步中...',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            setTimeout(() => {
              loading.close()
              this.$baseMessage('增量同步成功', 'success')
            }, 2000)
          })
        }
      },
      fetchData() {
        var that = this
        selectList(that.queryForm).then((res) => {
          if (res.code == 0) {
            that.list = res.data.records
            that.total = res.data.total
          }
        })
      },
      //获取表格序号
      getIndex($index) {
        //表格序号
        return (
          (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
        )
      },
      reseat() {
        var that = this
        that.queryForm.emp_name = ''
        that.queryForm.emp_no = ''
        that.queryForm.admdvs = ''
        that.fetchData()
      },
    },
  }
</script>
