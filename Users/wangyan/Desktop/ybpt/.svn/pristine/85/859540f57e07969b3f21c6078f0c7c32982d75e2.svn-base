<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-card class="card" shadow="never">
            <div slot="header">
              <span class="tips">信息查询</span>
              <vab-icon :icon="['fas', 'angle-up']" v-if="isShow" @click="moreQuery"></vab-icon>
              <vab-icon :icon="['fas', 'angle-down']" v-else @click="moreQuery"></vab-icon>
            </div>
            <el-form label-width="160px">
              <el-row :gutter="20">

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="病种名称">
                    <el-input v-model.trim="queryForm.dise_name" placeholder="病种名称" clearable class="input-with-icon"
                              @keyup.enter.native="queryData">
                      <!--<vab-icon :icon="['fas', 'search']" slot="suffix" style="margin-right:10px;" @click="openwin"></vab-icon>-->
                      <!--<vab-icon :icon="['fas', 'id-card']" slot="suffix" style="margin-right:10px;"></vab-icon>-->
                      <!--<vab-icon :icon="['fas', 'barcode']" slot="suffix" style="margin-right:10px;"></vab-icon>-->
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="病种代码">
                    <el-input v-model.trim="queryForm.dise_code" placeholder="病种代码" @keyup.enter.native="queryData"/>
                  </el-form-item>
                </el-col>
              </el-row>

              <vab-query-form>
                <vab-query-form-right-panel :span="24">
                  <el-form-item class="mr0">
                    <el-button icon="el-icon-refresh-left" @click="reseat()">重 置</el-button>
                    <el-button icon="el-icon-search" type="primary" @click="querry()">
                      查 询
                    </el-button>
                  </el-form-item>
                </vab-query-form-right-panel>
              </vab-query-form>
            </el-form>
          </el-card>
        </el-col>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">病种互斥列表</span>
            <el-button
              type="success"
              class="right"
              icon="el-icon-plus"
              @click="handleAdd"
            >
              新增
            </el-button>
          </div>
          <el-table
            v-loading="listLoading"
            ref="listTable"
            stripe
            :data="tableData"
            :element-loading-text="elementLoadingText"
            highlight-current-row
            border
            @current-change="handleCurrentChange"
            height="420px"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column
              show-overflow-tooltip
              type="index"
              label="序号"
              align="center"
              width="80px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="登记信息"
              align="center"
              prop="aaa"
            >
              <el-table-column
                show-overflow-tooltip
                prop="dise_name"
                label="登记病种"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="dise_code"
                label="登记病种代码"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="insured_type"
                label="参保类型"
                align="center"
              ></el-table-column>

            </el-table-column>
            
            <el-table-column
              show-overflow-tooltip
              label="互斥信息"
              align="center"
              prop="bbb"
            >
              <el-table-column
                show-overflow-tooltip
                prop="dise_mutex_name"
                label="互斥病种"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="dise_mutex_code"
                label="互斥病种代码"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="insured_mutex_type"
                label="参保类型"
                align="center"
              ></el-table-column>

            </el-table-column>

            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="160"
              align="center"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handleAdd(row)"
                  type="primary"
                  size="mini"
                >
                  编辑
                </el-button>
                <el-button
                  plain
                  @click="handlecancel(row)"
                  type="danger"
                  size="mini"
                >
                  删除
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
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
import Edit from './components/edit'
import { getDiseaseMutexList, delDiseaseMutex } from '@/api/mmmtDisease'
export default {
  name: 'bingzhonghc',
  components: { Edit },
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
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
      tableData: []
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
    
    handleAdd(row) {
      if (row.id) {
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
    
    handlecancel(row) {
      if (row.id) {
        this.$baseConfirm('确认删除吗？', null, async () => {
          const { msg } = await delDiseaseMutex({ id: row.id })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
      }
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
      const { data } = await getDiseaseMutexList(this.queryForm)
      console.info(data);
      this.tableData = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    reseat(){
      this.queryForm.dise_name = "" ;
      this.queryForm.dise_code = "" ;
      this.queryData() ;
    }
  },
}
</script>
