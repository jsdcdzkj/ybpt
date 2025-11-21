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
          <el-form ref="queryForm" :model="queryForm" label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构名称">
                  <el-input v-model.trim="queryForm.fixmedins_name" placeholder="机构名称" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构代码">
                  <el-input v-model.trim="queryForm.fixmedins_code" placeholder="机构代码" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="科室名称">
                  <el-input v-model.trim="queryForm.dept_name" placeholder="科室名称" clearable/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="科室编码" >
                  <el-input v-model.trim="queryForm.dept_no" placeholder="科室编码" clearable/>
                </el-form-item>
              </el-col>
            </el-row>
            <vab-query-form>
              <vab-query-form-right-panel :span="24">
                <el-form-item class="mr0">
<!--                  <el-button icon="el-icon-refresh-left">重 置</el-button>-->
                  <el-button icon="el-icon-search" @click="queryData" type="primary">
                    查 询
                  </el-button>
                  <el-button icon="el-icon-upload" @click="excelDetails" type="primary">
                    导 出
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
            <span class="tips">医疗机构科室信息</span>
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
            <el-table-column show-overflow-tooltip label="定点医疗机构代码" align="center" prop="fixmedins_code" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="fixmedins_name" width="250px" label="定点医疗机构名称" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="dept_no" label="科室编码" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="dept_name" label="科室名称" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="begntime" label="开始时间" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="endtime" label="结束时间" align="center" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="dept_resper_name" label="科室负责人姓名" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="dept_resper_tel" align="center" label="科室负责人电话"></el-table-column>
            <el-table-column show-overflow-tooltip prop="aprv_bed_cnt" align="center" label="批准床位数量" width="80px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="dr_psncnt" align="center" label="医师人数" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="phar_psncnt" align="center" label="药师人数" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="nurs_psncnt" align="center" label="护士人数" width="120px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="tecn_psncnt" align="center" label="技师人数" width="120px"></el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
  </div>
</template>

<script>
import { upData } from '@/api/common.js'
import { selectMedicalDeptList , exportMedicalDept} from '@/api/medicalOrg'
import Cardnum from '@/components/cardno'
export default {
  name: 'Index',
  components: {Cardnum},
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
      psnTypes:[],//证件类型
      queryForm: {
        pageNo: 1,
        pageSize: 10,
      },
      rules: {
        beginTime: [{ required: true, trigger: 'blur', message: '请输入密码' }],
        endTime: [{ required: true, trigger: 'blur', message: '请输入姓名' }]
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
      this.$refs['queryForm'].validate(async (valid) => {
        if (valid) {
          this.queryForm.pageNo = 1
          this.fetchData()
        }else {
          return false
        }

      })

    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async excelDetails() {
      this.$refs['queryForm'].validate(async (valid) => {
        if (valid) {
          this.$baseConfirm('确认导出吗？', null, async () => {
            this.listLoading = true
            const res = await exportMedicalDept(this.queryForm);
            let fileName = "医疗机构科室信息.xls";
            let objectUrl = URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.download = decodeURI(fileName)
            link.href = objectUrl
            link.click()
            this.listLoading = false;
            this.$baseMessage("导出成功！", 'success')
          })
        }else {
          return false
        }

      });
    },
    getCarType() {
      var that = this ;
      upData('PSN_TYPE').then((res) => {
        if(res.code == 0){
          that.psnTypes = res.data ;
        }
      })
    },
    async fetchData() {
      this.listLoading = true
      const res = await selectMedicalDeptList(this.queryForm)
      this.list = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
