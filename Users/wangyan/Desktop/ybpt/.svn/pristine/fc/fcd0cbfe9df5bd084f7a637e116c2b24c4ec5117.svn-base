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
                <el-form-item label="证件号码">
                  <el-input v-model.trim="queryForm.certno" placeholder="个人身份信息" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="时间">
                  <el-date-picker
                      placeholder="年度"
                      v-model="queryForm.year"
                      clearable
                      type="year"
                      value-format="yyyy">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="类型">
                  <el-select v-model="queryForm.acct_incexpd_type" placeholder="账户收支类型" style="width: 100%"
                             @change="queryData">
                    <el-option v-for="item in cardTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                               :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <vab-query-form>
              <vab-query-form-right-panel :span="24">
                <el-form-item class="mr0">
                  <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
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
            <span class="tips">个人收入明细</span>
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
              label="人员姓名"
              align="center"
              prop="psn_name"
              width="120px"
            >
            </el-table-column>
            <el-table-column
                show-overflow-tooltip
                label="证件号"
                align="center"
                prop="certno"
            >
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="year"
              label="年度"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="acct_incexpd_type"
              label="账户收支类型"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="inc_sumamt"
              label="收入总金额"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="cvlserv_acct_crtyear_inc"
              label="公务员账户本年收入"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="insu_admdvs"
              label="参保所属医保区划"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="poolarea_no"
              align="center"
              label="统筹区编号"
              width="120px"
            ></el-table-column>
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
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import Cardnum from '@/components/cardno'
import Views from './components/view_account'
import {getAccountDetails} from '@/api/personinfo'
import {upData} from '@/api/common.js'

export default {
  name: 'Index',
  components: {Cardnum,Views},
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
        certno: '',
        acct_incexpd_type: '',
        year: '',
      },
    }
  },
  created() {
    // this.fetchData()
    this.getCarType();
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
    handlecancel(row){
      if (row.id) {
          this.$baseConfirm('确认进行撤消？', null, async () => {
            const { msg } = await doDelete({ ids: row.id })
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
    getCarType() {
      var that = this ;
      upData('ACCT_INCEXPD_TYPE').then((res) => {
        if(res.code == 0){
          that.cardTypes = res.data ;
        }
      })
    },
    async fetchData() {
      this.listLoading = true
      const res = await getAccountDetails(this.queryForm)
      this.list = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    reseat(){
      this.queryForm.certno = ''
      this.queryForm.year = ''
      this.queryForm.acct_incexpd_type = ''
      this.queryData()
    },
  },
}
</script>
