<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="考核单名称">
                  <el-input v-model.trim="queryForm.taskName" placeholder="请输入"
                            clearable
                            @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="机构类型">
                  <el-select
                      v-model="queryForm.orgType"
                      @change="getLevelList"
                      style="width: 100%"
                      clearable
                  >
                    <el-option label="医疗机构" value="1"></el-option>
                    <el-option label="零售药店" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="协议等级">
                  <el-select
                      v-model="queryForm.aggrement_lv"
                      style="width: 100%"
                      @change="queryData"
                      clearable
                  >
                    <el-option
                        v-for="item in levelList"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="机构类别">
                  <el-select
                      v-model="queryForm.category"
                      @change="queryData"
                      style="width: 100%"
                      clearable
                  >
                    <el-option label="门诊" value="1"></el-option>
                    <el-option label="住院" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="状态">
                  <el-select
                      v-model="queryForm.status"
                      @change="queryData"
                      style="width: 100%"
                      clearable
                  >
                    <el-option label="初审开始" value="2"></el-option>
                    <el-option label="待复审" value="3"></el-option>
                    <el-option label="复审开始" value="4"></el-option>
                    <el-option label="待计算保证金" value="6"></el-option>
                    <el-option label="完成" value="7"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="考核年度">
                  <el-date-picker v-model="queryForm.year"
                                  @change="queryData"
                                  clearable
                                  type="year"
                                  placeholder="选择年"
                                  format="yyyy" value-format="yyyy">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="经营性质">
                  <el-select v-model="queryForm.natures"
                             @change="queryData"
                             clearable
                             placeholder="请选择" style="width: 100%"
                  >
                    <el-option label="公立" value="1"></el-option>
                    <el-option label="私立" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="统筹区"
                              v-if="userinfo.user_type == 1 && (userinfo.org_code ==='320399' || userinfo.org_name =='admin')">
                  <el-select v-model="queryForm.admdvs" clearable
                             style="width: 100%" @change="queryData()">
                    <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                               :value="item.value">
                    </el-option>
                  </el-select>
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
            <span class="tips">考核任务信息</span>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
                    :element-loading-text="elementLoadingText" highlight-current-row border height="420px">
            <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
				<template #default="{ $index }">
					{{(queryForm.pageNo-1)*queryForm.pageSize + $index+1}}
				</template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="考核任务名称" align="center" prop="taskName">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="机构类型" align="center" prop="orgType" width="120px">
              <template #default="{ row }">
                <el-tag v-if="row.orgType==1">医疗机构</el-tag>
                <el-tag v-else-if="row.orgType==2">零售药店</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="category" width="120px" label="机构类别" align="center">
              <template #default="{ row }">
                <el-tag v-if="row.category==1">门诊</el-tag>
                <el-tag v-else-if="row.category==2">住院</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="aggrementLv" label="协议等级" align="center" width="120px">
              <template #default="{ row }">
                <el-tag v-if="row.aggrementLv==1">一级</el-tag>
                <el-tag v-else-if="row.aggrementLv==2">二级</el-tag>
                <el-tag v-else-if="row.aggrementLv==3">三级</el-tag>
                <el-tag v-else-if="row.aggrementLv==4">A级</el-tag>
                <el-tag v-else-if="row.aggrementLv==5">B级</el-tag>
                <el-tag v-else-if="row.aggrementLv==6">C级</el-tag>
                <el-tag v-else-if="row.aggrementLv==9">未定级</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="natures" label="经营性质" align="center" width="120px">
              <template #default="{ row }">
                <el-tag v-if="row.natures==1">公立</el-tag>
                <el-tag v-else-if="row.natures==2">私立</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="statusName" label="状态" align="center" width="120px">
              <template #default="{ row }">
                <el-tag v-if="row.status==0">未发布</el-tag>
                <el-tag v-else-if="row.status==2">初审开始</el-tag>
                <el-tag v-else-if="row.status==3">待复审</el-tag>
                <el-tag v-else-if="row.status==4">复审开始</el-tag>
                <el-tag v-else-if="row.status==6">待计算保证金</el-tag>
                <el-tag v-else-if="row.status==7">完成</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="year" label="考核年度" align="center"
                             width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="expirationTime" label="结束日期" align="center" width="180px">
            </el-table-column>
            <el-table-column label="操作" width="200" align="center">
              <template #default="{ row }">
                <el-button v-if="row.status != 7" plain @click="handlechuli(row)" type="success" size="mini">
                  执行考核
                </el-button>
                <el-button v-if="row.status == 7" plain @click="handlechuli(row)" type="primary" size="mini">
                  详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                         :layout="layout" :total="total" @size-change="handleSizeChange"
                         @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
import {
  getList
} from '@/api/personinfo'
import {getDicts} from "@/api/dictManagement";
import {evalTaskManagePage} from "@/api/eval";
import Edit from './components/edit'

export default {
  name: 'Index',
  components: {
    Edit,
  },
  data() {
    return {
      userinfo: {
        id:'',
        user_type:'',
        user_name:'',
        org_code:'',
      },
      checked: false,
      isShow: false,
      list: [{}, {}, {}],
      listLoading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      aList: [
        {id: 1, name: '一级'},
        {id: 2, name: '二级'},
        {id: 3, name: '三级'},
        {id: 9, name: '未定级'},
      ],
      bList: [
        {id: 4, name: 'A级'},
        {id: 5, name: 'B级'},
        {id: 6, name: 'C级'},
      ],
      levelList: [],
      admdvs: [],
      queryForm: {
        pageNo: 1,
        pageSize: 10,
      },
    }
  },
  created() {
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"))
    if(this.userinfo.user_type == 1){
      this.queryForm.admdvs = this.userinfo.org_code
    }else{
      this.queryForm.orgCode = this.userinfo.org_code
    }

    this.getAdmdvs()
    this.queryData();
  },
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {
    async getAdmdvs() {
      const res = await getDicts({"type": "admdvs-area"});
      if (res.code == "0") {
        this.admdvs = res.data;
      }
    },
    getLevelList() {
      var that = this

      if (that.queryForm.orgType == 1) {
        that.levelList = that.aList
        that.queryForm.aggrement_lv = ''
      } else if (that.queryForm.orgType == 2) {
        that.levelList = that.bList
        that.queryForm.aggrement_lv = ''
      } else {
        that.levelList = []
      }

      if (that.queryForm.aggrement_lv != undefined) {
        that.queryForm.aggrement_lv = ''
      }
      this.queryData()
    },
    changeLevel() {
      if (this.aggrement.jgType == 1) {
        this.options = [
          {id: '9', name: '未定级'},
          {id: '1', name: '一级'},
          {id: '2', name: '二级'},
          {id: '3', name: '三级'},
        ]
      } else if (this.aggrement.jgType == 2) {
        this.options = [
          {id: '4', name: 'A级'},
          {id: '5', name: 'B级'},
          {id: '6', name: 'C级'},
        ]
      }
      this.getOrgs()
    },
    setSelectRows(val) {
      this.selectRows = val
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
      this.$refs['edit'].showDia(row)
    },
    handlecancel(row) {
      if (row.id) {
        this.$baseConfirm('确认进行撤消？', null, async () => {
          const {
            msg
          } = await doDelete({
            ids: row.id
          })
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
    async fetchData() {
      this.listLoading = true
      const res = await evalTaskManagePage(this.queryForm)
      this.list = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    resetForm() {
      this.queryForm.taskName = ''
      this.queryForm.orgType = ''
      this.queryForm.category = ''
      this.queryForm.status = ''
      this.queryForm.aggrement_lv = ''
      this.queryForm.year = ''
      this.queryForm.natures = ''
      this.queryData();
    }
  },
}
</script>