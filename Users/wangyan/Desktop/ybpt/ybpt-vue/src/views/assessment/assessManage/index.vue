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
              <el-button icon="el-icon-refresh-download" @click="exportData">
                导 出
              </el-button>
            </div>
          </div>
<!--          <div slot="header">-->
<!--            <span class="tips">查询条件</span>-->
<!--            <el-button icon="el-icon-search" type="primary" class="right" @click="fetchData">-->
<!--              查 询-->
<!--            </el-button>-->
<!--            <el-button icon="el-icon-download" @click="exportData">-->
<!--              导 出-->
<!--            </el-button>-->
<!--          </div>-->
          <el-form label-width="80px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="机构类型">
                  <el-select v-model="queryForm.org_type" clearable style="width: 100%" @change="getLevelList">
                    <el-option
                        v-for="item in org_type_list"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="协议等级">
                  <el-select v-model="queryForm.aggrement_lv" clearable style="width: 100%" @change="getLabel">
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
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="考核年度">
                  <el-date-picker v-model="queryForm.year" type="year" placeholder="选择年" format="yyyy"
                                  value-format="yyyy" @change="queryData">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="userinfo.user_type == 1">
                <el-form-item label="机构名称">
                  <el-input v-model.trim="queryForm.fixmedins_name" clearable placeholder="请输入"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="userinfo.user_type == 1">
                <el-form-item label="机构编码">
                  <el-input v-model.trim="queryForm.fixmedins_code" clearable placeholder="请输入"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="任务名称">
                  <el-input v-model.trim="queryForm.task_name" clearable placeholder="请输入"
                            @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="考核状态">
                  <el-select v-model="queryForm.status" clearable style="width: 100%" @change="queryData">
                    <el-option label="填报中" value="0"></el-option>
                    <el-option label="待审核" value="1"></el-option>
                    <el-option label="审核通过" value="2"></el-option>
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
            <span class="tips">考核任务列表</span>
            <el-button type="success" class="right" icon="el-icon-plus" @click="batch" v-if="userinfo.user_type == 1">
              批量审核
            </el-button>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
                    :element-loading-text="elementLoadingText" highlight-current-row border  height="calc(100vh - 600px)">
            <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="任务名称" min-width="280px" align="center" prop="task_name">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="org_type" label="机构类型" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="category_name" label="类别" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="aggrement_lv" label="协议等级" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="year" label="考核年度" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="fixmedins_name" min-width="280px" label="机构名称" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="fixmedins_code" min-width="160px" label="机构编码" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="得分" align="center">
              <template #default="{ row }">
                <span v-if="row.status == 2">{{row.score}}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="过期时间" min-width="160px" align="center">
              <template #default="{ row }">
                <span v-if="userinfo.user_type == 2 || userinfo.user_type == 3">{{row.expiration_time}}</span>
                <span v-if="userinfo.user_type == 1">
                  <el-date-picker
                      v-model="row.expiration_time"
                      @change="(value) => handleChangeDate(value, row, row.expiration_time)"
                      :picker-options="options"
                      type="date"
                      class="w"
                      value-format="yyyy-MM-dd"
                  ></el-date-picker>
                </span>
              </template>
            </el-table-column>

            <el-table-column show-overflow-tooltip label="考核状态" width="90px" align="center">
              <template #default="{ row }">
                <div style="text-align: center;width: 100%;">
                  <el-tag type="success" :key="row.id" v-if="row.status == 0">
                    填报中
                  </el-tag>
                  <el-tag :key="row.id" v-else-if="row.status == 1">
                    待审核
                  </el-tag>
                  <el-tag  type="warning" :key="row.id" v-else="row.status == 2">
                    审核通过
                  </el-tag>
                </div>

              </template>
            </el-table-column>
            <!--<el-table-column show-overflow-tooltip prop="bbb2" label="日志" align="center">
              <template #default="{ row }">
                <el-link icon='el-icon-document' @click="handleLog(row)"></el-link>
              </template>
            </el-table-column>-->

            <el-table-column show-overflow-tooltip label="操作" width="260" fixed="right" align="center">
              <template #default="{ row }">
                <el-button plain @click="handleFilling(row)" type="primary" size="mini" v-if="row.status == 0 && (userinfo.user_type == 2 || userinfo.user_type == 3)">
                  填报
                </el-button>
                <el-button plain @click="handleSub(row)" type="primary" size="mini" v-if="row.status == 0 && row.if_detail == 1  && (userinfo.user_type == 2 || userinfo.user_type == 3)">
                  提交审核
                </el-button>
                <el-button plain @click="handlecancel(row)" type="danger" size="mini" v-if="row.status == 1  && (userinfo.user_type == 2 || userinfo.user_type == 3)">
                  撤回提交
                </el-button>
                <el-button plain @click="handleCheck(row)" type="primary" size="mini" v-if="row.status == 1 && userinfo.user_type == 1">
                  抽查
                </el-button>
                <el-button plain @click="handleDetail(row)" size="mini">
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
    <log ref="log" @fetch-data="fetchData"></log>
    <detail ref="detail" @fetch-data="fetchData"></detail>
  </div>
</template>

<script>
import Edit from './components/edit'
import Log from './components/log'
import Detail from './components/detail'
import {
  assessmentExport,
  assessmentList,
  batchAudit,
  cancleSub,
  submitExpirationTime,
  submitForReview
} from '@/api/assessment'

export default {
  name: 'kaohemanageYb',
  components: {
    Edit,
    Log,
    Detail
  },
  data() {
    return {
      userinfo: null,
      org_type_label:'',
      level_label:'',
      org_type_list:[
        {id: 1, name: '医疗机构'},
        {id: 2, name: '零售药店'},
      ],
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
      value1: '',
      value3: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        task_name: '',
        org_type: '',
        aggrement_lv: '',
        year: '',
        fixmedins_name: '',
        fixmedins_code: '',
        status: '',
      },
      options: {
        // 时间不能大于当前时间
        disabledDate: date => {
          return date.getTime() <= Date.now()
        },
      },
    }
  },
  created() {
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
    this.fetchData()
  },
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {
    async handleChangeDate(val,row,expiration_time){
      const param = {
        id:row.id,
        expiration_time:val
      }
      const regExp = new RegExp(/^\d{4}-[0-1][0-9]-[0-3][0-9]/);
      if (regExp.test(val)) {
        const res = await submitExpirationTime(param);
        if(res.code == 0){
          this.$baseMessage("操作成功", 'success')
        }else{
          this.$baseMessage("请尝试刷新页面！", 'error')
        }
      }else {
        this.$baseMessage("请选择正确时间！", 'error')
      }

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

    handleFilling(row) {
      // this.$refs['filling'].showDia()
      if(row.expiration_time == "" || row.expiration_time == null || row.expiration_time == undefined || new Date(row.expiration_time + " 23:59:59") <= new Date()){
        this.$baseMessage("已到填报截止时间，无法进行此操作！", 'error')
        return
      }
      this.$router.push({path: 'assess/filling/' + row.id})

    },
    handleSub(row) {
      if (row.id) {
        this.$baseConfirm('确认提交审核？', null, async () => {
          this.listLoading = true;
          submitForReview(row.id).then((res) => {
            if (res.code == 0) {
              this.listLoading = false;
              this.$baseMessage("操作成功", 'success')
              this.fetchData()
            }
          })
        })
      } else {
      }
    },
    handlecancel(row) {
      if (row.id) {
        this.$baseConfirm('确认进行撤消？', null, async () => {

          this.listLoading = true;
          cancleSub(row.id).then((res) => {
            if (res.code == 0) {
              this.listLoading = false;
              this.$baseMessage("操作成功", 'success')
              this.fetchData()
            }
          })
        })
      } else {
      }
    },
    handleLog(row) {
      this.$refs['log'].showDia(row)
    },
    handleCheck(row) {
      this.$refs['edit'].showDia(row)

    },
    handleDetail(row) {
      this.$refs['detail'].showDia(row)
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    exportData(){
      this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
        this.listLoading = true
        await assessmentExport(this.queryForm).then((res) => {
          let fileName = "考核任务导出.xlsx";
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(fileName)
          link.href = objectUrl
          link.click()
          this.listLoading = false;
          this.$baseMessage("导出成功！", 'success')
        })
      })
    },
    async fetchData() {
      this.listLoading = true
      // this.getArea()
      const {data} = await assessmentList(this.queryForm)
      this.list = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async batch() {
      if (this.queryForm.org_type == "" || this.queryForm.org_type == null || this.queryForm.org_type == undefined) {
        this.$baseMessage("请选择机构类型", 'info')
        return
      }
      if (this.queryForm.aggrement_lv == "" || this.queryForm.aggrement_lv == null || this.queryForm.aggrement_lv == undefined) {
        this.$baseMessage("请选择协议等级", 'info')
        return
      }
      if (this.queryForm.year == "" || this.queryForm.year == null || this.queryForm.year == undefined) {
        this.$baseMessage("请选择考核年度", 'info')
        return
      }
      let msg = '确认审核'
          + this.queryForm.year
          + '年度，协议等级为' + this.level_label
          + '的' + this.org_type_label
          + '考核单吗？'
          + '（额外筛选条件：'
      if (this.queryForm.fixmedins_name != "" && this.queryForm.fixmedins_name != null && this.queryForm.fixmedins_name != undefined) {
         msg += '机构名称：' + this.queryForm.fixmedins_name + '、'
      }
      if (this.queryForm.fixmedins_code != "" && this.queryForm.fixmedins_code != null && this.queryForm.fixmedins_code != undefined) {
        msg += '机构编码：' + this.queryForm.fixmedins_code + '、'
      }
      if (this.queryForm.task_name != "" && this.queryForm.task_name != null && this.queryForm.task_name != undefined) {
        msg += '任务名称：' + this.queryForm.task_name + '、'
      }
      msg += '考核状态：待审核）'
      this.$baseConfirm(msg, null, async () => {
        const res = await batchAudit(this.queryForm)
        if (res.code == 0) {
          this.$baseMessage(res.data, 'success')
        } else {
          this.$baseMessage(res.msg, 'error')
        }
        this.queryData();
      })
    },
    getLabel(value) {
      const that = this;

      console.log(value);
      let obj = {};
      obj = that.levelList.find((item)=>{
        return item.id === value;
      });
      that.level_label = obj.name
      console.log(obj.name);
      this.queryData();
    },
    getLevelList(value) {
      const that = this;
      if (that.queryForm.org_type == 1) {
        that.levelList = that.aList;
        that.queryForm.aggrement_lv = "";
      } else if (that.queryForm.org_type == 2) {
        that.levelList = that.bList;
        that.queryForm.aggrement_lv = "";
      } else {
        that.levelList = []
      }
      if (that.queryForm.aggrement_lv != undefined) {
        that.queryForm.aggrement_lv = "";
      }

      console.log(value);
      let obj = {};
      obj = that.org_type_list.find((item)=>{
        return item.id === value;
      });
      that.org_type_label = obj.name
      console.log(obj.name);
      this.queryData();
    },
    getArea() {
      if (this.userinfo.user_type == 1 && this.userinfo.org_code != 320399) {
        const orgCode = this.userinfo.org_code;
        if (orgCode == 320399) {
          this.queryForm.area = '市本级';
        }
        if (orgCode == 320305) {
          this.queryForm.area = '贾汪区';
        }
        if (orgCode == 320382) {
          this.queryForm.area = '邳州市';
        }
        if (orgCode == 320381) {
          this.queryForm.area = '新沂市';
        }
        if (orgCode == 320324) {
          this.queryForm.area = '睢宁县';
        }
        if (orgCode == 320322) {
          this.queryForm.area = '沛县';
        }
        if (orgCode == 320321) {
          this.queryForm.area = '丰县';
        }
        if (orgCode == 320312) {
          this.queryForm.area = '铜山区';
        }
      }
    },
  },
}
</script>
