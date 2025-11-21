<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">

        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">查询条件</span>
            <vab-icon :icon="['fas', 'angle-up']" v-if="isShow" @click="moreQuery"></vab-icon>
            <vab-icon :icon="['fas', 'angle-down']" v-else @click="moreQuery"></vab-icon>
          </div>

          <el-form :model="queryForm" ref="queryForm" label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="专家姓名">
                  <el-input placeholder="专家姓名" v-model.trim="queryForm.prof_name" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="专家编码">
                  <el-input placeholder="专家编码" v-model.trim="queryForm.prof_no" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="现任职务">
                  <el-input placeholder="现任职务" v-model.trim="queryForm.curr_duty" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="专家类型">
                  <el-select v-model="queryForm.prof_type" placeholder="专家类型" style="width: 100%" @change="queryData">
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
                  <el-button icon="el-icon-refresh-left" @click="resetForm()">重 置
                  </el-button>
                  <el-button icon="el-icon-search" type="queryForm" @click="fetchData">
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
            <span class="tips">专家信息</span>
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
            <el-table-column show-overflow-tooltip prop="prof_no" label="序号" align="center" width="80px">
              <template #default="scope">
                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column label="专家编号" prop="psn_no" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="人员编号" prop="prof_name" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="专家姓名" prop="off_tel" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="办公电话" prop="gend" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="性别" prop="curr_duty" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="现任职务" prop="prof_type" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="专家类型" prop="medins_dept" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="医疗机构科室" prop="prof_doma" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="专家领域" prop="profarea" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="专业方向" prop="pro_tech_profttl" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="专业技术职称" prop="uscc" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="统一社会信用代码" prop="emp_name" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="单位名称" prop="profttl_job_begntime" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="职称工作开始日期" prop="itro" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="简介" prop="emp_addr" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="单位地址" prop="begntime" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="开始时间" prop="endtime" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="结束时间" prop="vali_flag" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="有效标志" prop="admdvs" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="医保区划" prop="empprof_file_addr" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="工作证明文件地址" prop="mob" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="手机号码" prop="email" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="电子邮箱" prop="poscode" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="邮政编码" prop="dcla_way" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="申报方式" prop="rid" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="数据唯一记录号" prop="crte_time" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="数据创建时间" prop="updt_time" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="数据更新时间" prop="crter_id" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="创建人ID" prop="crter_name" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="创建人姓名" prop="crte_optins_no" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="创建机构编号" prop="opter_id" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="经办人ID" prop="opter_name" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="经办人姓名" prop="opt_time" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="经办时间" prop="optins_no" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column label="经办机构编号" prop="ver" align="center" show-overflow-tooltip></el-table-column>

<!--            <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">-->
<!--              <template #default="{ row }">-->
<!--                <el-button plain @click="handlechuli(row)" type="primary" size="mini">详情</el-button>-->
<!--              </template>-->
<!--            </el-table-column>-->
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
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import Views from './components/view'
import {profInfoB} from '@/api/query'
import {upData} from "@/api/common";

export default {
  name: 'Index',
  components: {Views},
  data() {
    return {
      cardTypes:[],
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        prof_no: '',
        prof_name: '',
        curr_duty: '',
        prof_type: '',
        pageNo: 1,
        pageSize: 10,
      },
    }
  },
  created() {
    // this.fetchData()
    this.getCarType();
  },
  methods: {
    getCarType() {
      var that = this ;
      upData('PROF_TYPE').then((res) => {
        if(res.code == 0){
          that.cardTypes = res.data ;
        }
      })
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handlechuli(row) {
      this.$refs['views'].showDia(row)
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
      const res = await profInfoB(this.queryForm)
      this.list = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    resetForm() {
      this.queryForm.prof_no = ''
      this.queryForm.prof_name = ''
      this.queryForm.curr_duty = ''
      this.queryForm.prof_type = ''
      this.queryData();
    },
  },
}
</script>
