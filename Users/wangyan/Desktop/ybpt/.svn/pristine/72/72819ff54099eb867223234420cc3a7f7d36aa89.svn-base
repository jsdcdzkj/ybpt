<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">查询条件</span>
            <vab-icon
              :icon="['fas', 'angle-up']"
              v-if="isShow"
              @click="moreQuery"
            ></vab-icon>
            <vab-icon
              :icon="['fas', 'angle-down']"
              v-else
              @click="moreQuery"
            ></vab-icon>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="门慢门特病种名称">
                  <el-input
                    v-model.trim="queryForm.opsp_dise_code"
                    placeholder="门慢门特病种目录编码"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>、
              </el-col>
            </el-row>
            <vab-query-form>
              <vab-query-form-right-panel :span="24">
                <el-form-item class="mr0">
                  <el-button icon="el-icon-refresh-left">重 置</el-button>
                  <el-button icon="el-icon-search" @click="queryData" type="primary">
                    查 询
                  </el-button>
                </el-form-item>
              </vab-query-form-right-panel>
            </vab-query-form>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">门慢门特病种目录表</span>
<!--            <el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">新增</el-button>-->
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
            height="420px"
          >
            <template slot="empty">
                <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
            <el-table-column
              show-overflow-tooltip
              type="index"
              label="序号"
              align="center"
              width="80px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip              
              label="门慢门特病种编码"
              align="center"
              prop="opsp_dise_code"
              width="120px"
            >              
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="门慢门特病种名称"
              align="center"
              prop="opsp_dise_name"
              width="250px"
            >
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="opsp_dise_majcls_name"
              width="200px"
              label="门慢门特病种大类名称"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="opsp_dise_subd_clss_name"
              label="门慢门特病种细分类名称"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="admdvs"
              label="医保区划"
              align="center"
              width="200px"
            ></el-table-column>
<!--            <el-table-column-->
<!--              show-overflow-tooltip-->
<!--              prop="vali_flag"-->
<!--              label="有效标志"-->
<!--              align="center"-->
<!--              width="200px"-->
<!--            ></el-table-column>-->
<!--            <el-table-column-->
<!--              show-overflow-tooltip-->
<!--              prop="selfpay_prop_type"-->
<!--              label="数据唯一记录号"-->
<!--              align="center"-->
<!--              width="180px"-->
<!--            ></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="crte_time"
              label="数据创建时间"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="updt_time"
              align="center"
              label="数据更新时间"
            ></el-table-column>
<!--            <el-table-column-->
<!--                    show-overflow-tooltip-->
<!--                    prop="crter_id"-->
<!--                    align="center"-->
<!--                    label="创建人ID"-->
<!--            ></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="160"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handlechuli(row)"
                  type="primary"
                  size="mini"
                >
                  查看
                </el-button> 
<!--                <el-button-->
<!--                  plain-->
<!--                  @click="handlecancel(row)"-->
<!--                  type="primary"-->
<!--                  size="mini"-->
<!--                >-->
<!--                  撤消-->
<!--                </el-button>               -->
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
   <!-- <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>-->
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
/*import Cardnum from '@/components/cardno'*/
import Views from './components/view'
import { OpspdiselistBQueryAll } from '@/api/personinfo'
import { upData } from '@/api/common.js'
export default {
  name: 'Index',
  components: {Views},
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      cardTypes:[],//证件类型
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
        opsp_dise_code: '',
      },
    }
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin(){
      this.$refs['cardnum'].showDia()
    },

    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handlechuli(row){
      this.$refs['views'].showDia(row)
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData(this.queryForm)
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async fetchData() {
      this.listLoading = true
      const res = await OpspdiselistBQueryAll(this.queryForm)
      this.list = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
