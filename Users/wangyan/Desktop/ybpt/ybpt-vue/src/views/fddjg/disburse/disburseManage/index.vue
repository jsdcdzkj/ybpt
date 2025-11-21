<template>
  <div class="main-container" v-loading="loading" element-loading-text="生成中。。。"
    element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.6)">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="年份">
                  <el-date-picker clearable type="year" format="yyyy" value-format="yyyy" v-model="queryForm.year"
                    placeholder="请选择年份"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="月份">
                  <el-select v-model="queryForm.month" clearable style="width: 100%" @change="queryData()"
                    placeholder="请选择月份">
                    <el-option label="一月" value="1"></el-option>
                    <el-option label="二月" value="2"></el-option>
                    <el-option label="三月" value="3"></el-option>
                    <el-option label="四月" value="4"></el-option>
                    <el-option label="五月" value="5"></el-option>
                    <el-option label="六月" value="6"></el-option>
                    <el-option label="七月" value="7"></el-option>
                    <el-option label="八月" value="8"></el-option>
                    <el-option label="九月" value="9"></el-option>
                    <el-option label="十月" value="10"></el-option>
                    <el-option label="十一月" value="11"></el-option>
                    <el-option label="十二月" value="12"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="生成时间">
                  <el-date-picker v-model="createTime" style="width: 100%" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                    type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="状态">
                  <el-select v-model="queryForm.status" clearable style="width: 100%" @change="queryData()"
                    placeholder="请选择状态">
                    <el-option label="未发送" value="0"></el-option>
                    <el-option label="已发送" value="1"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="发送时间">
                  <el-date-picker v-model="sendTime" style="width: 100%" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                    type="daterange" range-separator="至" start-placeholder="开始日期" end-placeholder="结束日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>

            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">拨付通知管理列表</span>
            <div class="right" v-if="roles.indexOf('generateNotice') > -1">
              <el-button type="primary" @click="createData">
                生成数据
              </el-button>
            </div>
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row
            @current-change="handleCurrentChange" @selection-change="setSelectRows" height="calc(100% - 50px)"
            v-loading="listLoading">
            <!-- <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template> -->
            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" width="80px" fixed="left">
              <template slot-scope="scope">
                <span v-text="getIndex(scope.$index)"> </span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="年份" align="center" prop="year">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="月份" align="center" prop="month">
              <template #default="{ row }">
                <span>{{ row.month }}月</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="createTime" label="生成时间" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="sendTime" label="发送时间" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="status" label="状态" align="center">
              <template #default="{ row }">
                <el-tag type="warning" v-if="row.status == 0">
                  未发送
                </el-tag>
                <el-tag type="success" v-else-if="row.status == 1">
                  已发送
                </el-tag>
              </template>
            </el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="280" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handleSend(row)" type="warning" size="mini" style="left:0px ;"
                  v-if="row.status == 0 && roles.indexOf('generateNotice') > -1">发送
                </el-button>
                <el-button plain @click="handleCreateAnalysis(row)" type="primary" size="mini" style="left:0px ;"
                  v-if="row.status == 1 && row.isGenerAnaly == 0 && roles.indexOf('generateNotice') > -1 && userinfo.org_code == '320399'">生成拨付分析
                </el-button>
                <el-button plain @click="handleAnalysis(row)" type="primary" size="mini" style="left:0px ;"
                  v-if="row.status == 1 && row.isGenerAnaly == 1">拨付分析
                </el-button>
                <el-button plain @click="handleView(row)" type="primary" size="mini" style="left:0px ;">查看
                </el-button>
                <el-button plain @click="handleDelete(row)" type="danger" size="mini"
                  v-if="row.status == 0 && roles.indexOf('generateNotice') > -1">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>

      </el-col>
    </el-row>
    <dataInfo ref="dataInfo" @fetch-data="fetchData"></dataInfo>
    <analysisInfo ref="analysisInfo" @fetch-data="fetchData"></analysisInfo>
    <upload ref="upload" @fetch-data="fetchData"></upload>
  </div>


</template>

<script>
import {
  approNoticeList, approNoticeDel, approNoticeGenerate, approNoticeSend, approNoticeGenerateApproAnalyse
} from '@/api/disburse.js'
import upload from './components/components/upload'
import dataInfo from './components/dataInfo'
import analysisInfo from './components/analysisInfo'
import { getDicts } from "@/api/dictManagement";
export default {
  name: 'DisburseManage',
  components: { dataInfo, upload, analysisInfo },
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: false,
      loading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      admdvs: null,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        year: "",
        month: "",
        beginCreateTime: "",
        endCreateTime: "",
        beginSendTime: "",
        endSendTime: "",
        status: "",
      },
      createTime: [],
      sendTime: [],
      user_type: "",
      isOpen: true,
      form: {
        isOpen: '0'
      },
      show: false,
      isCheck: false,
      userinfo: {},
      num: "",
      roles: [],


    }
  },
  watch: {
    createTime(newData, oldData) {
      if (newData && newData.length > 0) {
        this.queryForm.beginCreateTime = newData[0]
        this.queryForm.endCreateTime = newData[1]
      }
      if (newData == null) {
        this.queryForm.beginCreateTime = ''
        this.queryForm.endCreateTime = ''
      }
    },
    sendTime(newData, oldData) {
      if (newData && newData.length > 0) {
        this.queryForm.beginSendTime = newData[0]
        this.queryForm.endSendTime = newData[1]
      }
      if (newData == null) {
        this.queryForm.beginSendTime = ''
        this.queryForm.endSendTime = ''
      }
    },
  },
  created() {
    var that = this;
    this.roles = JSON.parse(localStorage.getItem('roles'))
    this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
    console.log('this.roles ', this.roles);
    console.log('userinfo', localStorage.getItem('userinfo'));
    that.fetchData();
  },
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {

    setSelectRows(val) {
      this.selectRows = val
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },

    //分页查询列表数据
    fetchData() {
      var that = this;
      approNoticeList(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = res.data.records;
          that.total = res.data.total
        }
      })
    },
    //上传数据
    createData(row) {
      this.$refs['upload'].showDia()
    },

    //生成拨付分析
    handleCreateAnalysis(row) {
      var that = this;
      that.$baseConfirm('确定要生成拨付分析？', '提示', async () => {
        that.loading = true
        approNoticeGenerateApproAnalyse({ appropNoticeId: row.id, year: row.year, month: row.month }).then((res) => {
          that.loading = false
          if (res.code == 0) {
            that.fetchData()
            that.$baseMessage('生成成功', 'success')
          } else {
            that.$baseMessage(res.msg, 'error')
          }
        })
      })
    },
    //查看拨付分析
    handleAnalysis(row) {
      if (row.id) {
        this.$refs['analysisInfo'].showDia(row)
      }
    },

    //查看
    handleView(row) {
      console.log(row);
      if (row.id) {
        this.$refs['dataInfo'].showDia(row)
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

    //删除数据
    handleDelete(row) {
      var that = this;
      if (row.id) {
        that.$baseConfirm('确认删除当前拨付数据？', '提示', async () => {
          approNoticeDel(row).then((res) => {
            if (res.code == 0) {
              that.fetchData()
              that.$baseMessage('操作成功', 'success')
            } else {
              that.$baseMessage(res.msg, 'error')
            }
          })
        })
      }
    },


    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },

    //搜索
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },

    //重置
    reseat() {
      this.createTime = []
      this.sendTime = []
      this.queryForm = this.$options.data().queryForm
      this.fetchData();
    },

    //发送数据
    handleSend(row) {
      this.$baseConfirm('确定要发送当前拨付数据？', '提示', async () => {
        approNoticeSend(row).then((res) => {
          if (res.code == 0) {
            this.fetchData()
            this.$baseMessage('发送成功', 'success')
          }
        })
      })
    },
    async handleChange2(file, fileList) {
      this.dialog = true
      this.loading = true
      this.fileList = fileList;
      let fd = new FormData();
      fd.append("admdvs", this.selectRows.id);
      this.fileList.forEach(item => {
        //文件信息中raw才是真的文件
        fd.append("file", item.raw);
      })

      var result = await importData2(fd);
      if (result.data.code == 0) {
        this.$baseMessage("上传成功", 'success')
      } else {
        this.$baseMessage(result.data.msg, 'error')

      }
      this.dialog = false
      this.loading = false
    },
    handleSuccess() {
      this.fetchData()
    },

  },
}
</script>
<style>
.item {
  margin-right: 20px;
  margin-left: 20px;
}

.badge-item .el-badge__content.is-fixed {
  top: 2px;
}
</style>