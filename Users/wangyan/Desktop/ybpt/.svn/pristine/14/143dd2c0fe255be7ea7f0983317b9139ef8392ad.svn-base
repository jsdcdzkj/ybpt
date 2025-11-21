<template>
  <div class="main-container">
    <el-row :gutter="20">
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
                <el-form-item label="证件类型">
                  <el-select v-model="queryForm.cardType" placeholder="证件类型" style="width: 100%" @change="queryData">
                    <el-option v-for="item in cardTypes" :key="item.nat_dic_val_code" :label="item.nat_dic_val_name"
                      :value="item.nat_dic_val_code">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="姓名">
                  <el-input v-model.trim="queryForm.psn_name" placeholder="人员姓名" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="编号">
                  <el-input v-model.trim="queryForm.psn_no" placeholder="人员编号" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="证件号码">
                  <el-input v-model.trim="queryForm.certno" placeholder="证件号码" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="手机号">
                  <el-input v-model.trim="queryForm.mob" placeholder="手机号" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
            </el-row>
            <vab-query-form>
              <vab-query-form-right-panel :span="24">
                <el-form-item class="mr0">
                  <el-button icon="el-icon-refresh-left" @click="resetForm()">重 置</el-button>
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
            <span class="tips">人员基本信息</span>
            <!--            <el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">新增</el-button>-->
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" highlight-current-row border
            @current-change="handleCurrentChange" height="420px">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
            <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="姓名" align="center" prop="psn_name" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="编号" align="center" prop="psn_no" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="gend" width="120px" label="性别" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="brdy" label="出生日期" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="psn_cert_type" label="证件类型" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="certno" label="证件号码" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="naty" label="民族" align="center" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="resd_loc_admdvs" label="参保统筹区" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="surv_stas" align="center" label="生存状态"></el-table-column>
            <el-table-column show-overflow-tooltip prop="retr_type" align="center" label="退休状态"></el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handlechuli(row)" type="primary" size="mini">
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
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import {upData} from '@/api/common.js'
import {getList} from '@/api/personinfo'
import Cardnum from '@/components/cardno'
import Edit from './components/edit'
import Views from './components/view'

export default {
  name: 'Index',
  components: {Cardnum,Edit,Views},
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
        cardType: '01',
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
    handleAdd(){
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
    handlechuli(row){
      this.$refs['views'].showDia(row.certno)
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
      upData('PSN_CERT_TYPE').then((res) => {
        if(res.code == 0){
          that.cardTypes = res.data ;
        }
      })
    },
    async fetchData() {
      this.listLoading = true
      const res = await getList(this.queryForm)
      this.list = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    resetForm() {
      this.queryForm.cardType = ''
      this.queryForm.psn_name = ''
      this.queryForm.psn_no = ''
      this.queryForm.certno = ''
      this.queryForm.mob = ''
      this.queryData();
    }
  },
}
</script>
