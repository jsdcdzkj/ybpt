<template>
  <div class="main-container">
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
                                  @change="queryData()"
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
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">拨付通知列表</span>
            <!--            <div class="right">-->
            <!--              <el-button type="primary" @click="upload">-->
            <!--                上传数据-->
            <!--              </el-button>-->
            <!--            </div>-->
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row
            @current-change="handleCurrentChange" @selection-change="setSelectRows" height="calc(100% - 50px)"
            v-loading="listLoading">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
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
            <el-table-column show-overflow-tooltip prop="sendTime" label="发送时间" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="280" align="center" fixed="right">
              <template #default="{ row }">
                <!-- <el-button plain @click="handleSend(row)" type="warning" size="mini" style="left:0px ;">发送
                </el-button>
                <el-button plain @click="handleCreateAnalysis(row)" type="primary" size="mini" style="left:0px ;">生成拨付分析
                </el-button> -->
                <el-button plain @click="handleAnalysis(row)" type="primary" size="mini" style="left:0px ;"
                  v-if="row.isGenerAnaly == 1">拨付分析
                </el-button>
                <el-button plain @click="handleView(row)" type="primary" size="mini" style="left:0px ;">查看</el-button>
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
  selectList, delQs, createConfirming
} from '@/api/qs.js'
import upload from './components/components/upload'
import dataInfo from './components/dataInfo'
import analysisInfo from './components/analysisInfo'
import { getDicts } from "@/api/dictManagement";
import { pageByOrgCode, approNoticeViewDataDetail, approNoticeViewDetailAnalyse } from '@/api/disburse'
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
        status: 1,
      },
      user_type: "",
      isOpen: true,
      form: {
        isOpen: '0'
      },
      show: false,
      isCheck: false,
      userinfo: {},
      num: "",

    }
  },
  created() {
    var that = this;
    that.getAdmdvs();
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
    upload(row) {
      this.$refs['upload'].showDia()
    },

    //生成拨付分析
    handleCreateAnalysis(row) {
      var that = this;
      that.$baseConfirm('确定要生成拨付分析？', null, async () => {
        delQs(row).then((res) => {
          if (res.code == 0) {
            that.fetchData()
            that.$baseMessage('操作成功', 'success')
          } else {
            that.$baseMessage(res.msg, 'error')
          }
        })
      })
    },
    //查看拨付分析
    handleAnalysis(row) {
      if (row.id) {
        // approNoticeViewDetailAnalyse({
        //   appropNoticeId:row.id,
        //   tcq:row.tcq,
        //   year:row.year,
        //   month:row.month,
        //   pageNo:1,
        //   pageSize:10,
        // }).then((res) => {
        //   if (res.code == '-1') {
        //     this.$message.error(res.msg)
        //   }else{
        //     this.$refs['analysisInfo'].showDia(row)
        //   }
        // })
        this.$refs['analysisInfo'].showDia(row)

      }
    },
    handleView(row) {
      if (row.id) {
        approNoticeViewDataDetail({
          appropNoticeId: row.id,
          tcq: row.tcq,
          year: row.year,
          month: row.month,
          pageNo: 1,
          pageSize: 10,
        }).then((res) => {
          if (res.code == '-1') {
            this.$message.error(res.msg)
          } else {
            this.$refs['dataInfo'].showDia(row)
          }
        })
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
    handleDelete(row) {
      var that = this;
      if (row.id) {
        that.$baseConfirm('确认删除当前拨付数据？', null, async () => {
          delQs(row).then((res) => {
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
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    fetchData() {
      var that = this;
      pageByOrgCode(that.queryForm).then((res) => {
        if (res.code == 0) {
          let aaa = {
            "code": 0,
            "msg": "成功",
            "data": {
              "records": [
                {
                  "id": "d5a165b4058d4778b5c0981d1e429472",
                  "year": "2022",
                  month: '12',
                  sendTime: '2023-05-05 09:47:04',
                  "admdvs": "徐州市市本级",
                  "upload_time": "2023-05-05 09:47:04",
                  "pub_status": "2",
                  "pageNo": null,
                  "pageSize": null,
                  "createUser": "admin",
                  "createTime": "2023-05-05 09:47:04",
                  "updateUser": null,
                  "updateTime": null,
                  "is_del": "0"
                }
              ],
              "total": 1,
              "size": 10,
              "current": 1,
              "orders": [],
              "optimizeCountSql": true,
              "searchCount": true,
              "countId": null,
              "maxLimit": null,
              "pages": 1
            },
            "logMsg": null
          }
          that.list = res.data.records;
          that.total = res.data.total
        }
      })

    },
    getAdmdvs() {
      getDicts({ "type": "admdvs-area" }).then((res) => {
        if (res.code == 0) {
          this.admdvs = res.data;
          console.log(this.admdvs);
        }
      })

    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    reseat() {
      this.queryForm.year = "";
      this.queryForm.month = "";
      this.fetchData();
    },
    handleSend(row) {
      this.$baseConfirm('确定要发送当前拨付数据？', null, async () => {
        createConfirming(row.id).then((res) => {
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
