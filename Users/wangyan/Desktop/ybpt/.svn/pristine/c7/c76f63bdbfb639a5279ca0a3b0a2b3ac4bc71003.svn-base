<template>
  <el-drawer :title="title" :visible.sync="dialog" direction="rtl" custom-class="box_drawer" size="80%" ref="drawer"
    append-to-body>
    <div class="drawer_content" v-loading="loading" element-loading-text="下载中。。。"
      element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.6)">

      <el-tabs v-model="activeName" @tab-click="handleClick">

        <el-tab-pane label="汇总分析" name="first">
          <div style="font-size: 18px;font-weight: bold;text-align: center;margin-bottom: 10px;">职工医保拨付分析（单位：万元）</div>
          <el-table :data="zgSummaries" border stripe class="w" v-loading="listLoading"
            :element-loading-text="elementLoadingText" height="35vh">
            <el-table-column show-overflow-tooltip prop="tcq" label="医疗机构归属地" width="160px" align="center" fixed="left">
            </el-table-column>
            <el-table-column label="医疗总费用" align="center">
              <el-table-column show-overflow-tooltip prop="ylzfyDqljfse" label="当期累计发生额" width="160px"
                align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="ylzfyTqljfse" label="上年同期累计发生额" align="center"
                width="160px"></el-table-column>
              <el-table-column prop="ylzfyTb" label="同比" width="160px" align="center">
                <template #default="{ row }">
                  <span v-if="row.ylzfyTb != null">{{ row.ylzfyTb }}%</span>
                </template>
              </el-table-column>
              <el-table-column prop="ylzfyBnzb" label="本年占比" width="160px" align="center">
                <template #default="{ row }">
                  <span v-if="row.ylzfyBnzb != null">{{ row.ylzfyBnzb }}%</span>
                </template>
              </el-table-column>

            </el-table-column>
            <el-table-column label="统筹基金" align="center">
              <el-table-column show-overflow-tooltip prop="tcjjDqljfse" label="当期累计发生额" width="160px"
                align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="tcjjTqljfse" label="上年同期累计发生额" align="center"
                width="160px"></el-table-column>
              <el-table-column prop="tcjjTb" label="同比" width="160px" align="center">
                <template #default="{ row }">
                  <span v-if="row.tcjjTb != null">{{ row.tcjjTb }}%</span>
                </template>
              </el-table-column>
              <el-table-column prop="tcjjBnzb" label="本年占比" width="160px" align="center">
                <template #default="{ row }">
                  <span v-if="row.tcjjBnzb != null">{{ row.tcjjBnzb }}%</span>
                </template>
              </el-table-column>

            </el-table-column>
            <el-table-column label="应结付金额" align="center" prop="yjfje" width="160px"></el-table-column>
            <el-table-column label="已结付额" align="center" prop="yjfe" width="160px"> </el-table-column>

          </el-table>

          <div style="margin-top: 30px;font-size: 18px;font-weight: bold;text-align: center;margin-bottom: 10px;">
            居民医保拨付分析（单位：万元）</div>
          <el-table :data="jmSummaries" border stripe class="w" v-loading="listLoading"
            :element-loading-text="elementLoadingText" height="35vh">
            <el-table-column show-overflow-tooltip prop="tcq" label="医疗机构归属地" width="160px" align="center" fixed="left">
            </el-table-column>
            <el-table-column label="医疗总费用" align="center">

              <el-table-column show-overflow-tooltip prop="ylzfyDqljfse" label="当期累计发生额" width="160px"
                align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="ylzfyTqljfse" label="上年同期累计发生额" align="center"
                width="160px"></el-table-column>
              <el-table-column prop="ylzfyTb" label="同比" width="160px" align="center">
                <template #default="{ row }">
                  <span v-if="row.ylzfyTb != null">{{ row.ylzfyTb }}%</span>
                </template>
              </el-table-column>
              <el-table-column prop="ylzfyBnzb" label="本年占比" width="160px" align="center">
                <template #default="{ row }">
                  <span v-if="row.ylzfyBnzb != null">{{ row.ylzfyBnzb }}%</span>
                </template>
              </el-table-column>

            </el-table-column>
            <el-table-column label="统筹基金" align="center">
              <el-table-column show-overflow-tooltip prop="tcjjDqljfse" label="当期累计发生额" width="160px"
                align="center"></el-table-column>
              <el-table-column show-overflow-tooltip prop="tcjjTqljfse" label="上年同期累计发生额" align="center"
                width="160px"></el-table-column>
              <el-table-column prop="tcjjTb" label="同比" width="160px" align="center">
                <template #default="{ row }">
                  <span v-if="row.tcjjTb != null">{{ row.tcjjTb }}%</span>
                </template>
              </el-table-column>
              <el-table-column prop="tcjjBnzb" label="本年占比" width="160px" align="center">
                <template #default="{ row }">
                  <span v-if="row.tcjjBnzb != null">{{ row.tcjjBnzb }}%</span>
                </template>
              </el-table-column>

            </el-table-column>
            <el-table-column label="应结付金额" align="center" prop="yjfje" width="160px"></el-table-column>
            <el-table-column label="已结付额" align="center" prop="yjfe" width="160px"> </el-table-column>

          </el-table>

          <div style="height: 60px;"></div>


        </el-tab-pane>

        <el-tab-pane label="明细分析" name="second">
          <div class="drawer_main">
            <vab-query-form>
              <vab-query-form-left-panel :span="24">
                <el-form :inline="true" :model="queryForm" @submit.native.prevent label-width="120px">
                  <el-form-item label="医疗机构编码">
                    <el-input v-model.trim="queryForm.orgCode" placeholder="请输入医疗机构编码" clearable
                      @keyup.enter.native="queryData" />
                  </el-form-item>
                  <el-form-item label="医疗机构名称">
                    <el-input v-model.trim="queryForm.orgName" placeholder="请输入医疗机构名称" clearable
                      @keyup.enter.native="queryData" />
                  </el-form-item>
                  <el-form-item>
                    <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
                    <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
                  </el-form-item>
                </el-form>
              </vab-query-form-left-panel>
            </vab-query-form>

            <el-table :data="list" border stripe class="w" v-loading="listLoading"
              :element-loading-text="elementLoadingText" height="calc(100vh - 320px)">
              <el-table-column show-overflow-tooltip label="序号" align="center" width="80px" fixed="left">
                <template slot-scope="scope">
                  <span v-text="getIndex(scope.$index)"> </span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="tcq" label="所属地区" width="140px" align="center"
                fixed="left"></el-table-column>
              <el-table-column show-overflow-tooltip prop="orgCode" label="医疗机构编码" width="140px" align="center"
                fixed="left">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="orgName" label="医疗机构名称" width="200px" align="center"
                fixed="left">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="jb" label="级别" width="80px" align="center" fixed="left">
              </el-table-column>
              <el-table-column label="职工（单位：万元）" align="center" :class-name="'add-table-title'">
                <el-table-column label="医疗总费用" align="center">

                  <el-table-column show-overflow-tooltip prop="zgYlzfyDqljfse" label="当期累计发生额" width="160px"
                    align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgYlzfyTqljfse" label="上年同期累计发生额" align="center"
                    width="160px"></el-table-column>
                  <el-table-column prop="zgYlzfyTb" label="同比" width="160px" align="center">
                    <template #default="{ row }">
                      <span v-if="row.zgYlzfyTb != null">{{ row.zgYlzfyTb }}%</span>
                    </template>
                  </el-table-column>
                  <el-table-column prop="zgYlzfyBnzb" label="本年占比" width="160px" align="center">
                    <template #default="{ row }">
                      <span v-if="row.zgYlzfyBnzb != null">{{ row.zgYlzfyBnzb }}%</span>
                    </template>
                  </el-table-column>

                </el-table-column>
                <el-table-column label="统筹基金" align="center">
                  <el-table-column show-overflow-tooltip prop="zgTcjjDqljfse" label="当期累计发生额" width="160px"
                    align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgTcjjTqljfse" label="上年同期累计发生额" align="center"
                    width="160px"></el-table-column>
                  <el-table-column prop="zgTcjjTb" label="同比" width="160px" align="center">
                    <template #default="{ row }">
                      <span v-if="row.zgTcjjTb != null">{{ row.zgTcjjTb }}%</span>
                    </template>
                  </el-table-column>
                  <el-table-column prop="zgTcjjBnzb" label="本年占比" width="160px" align="center">
                    <template #default="{ row }">
                      <span v-if="row.zgTcjjBnzb != null">{{ row.zgTcjjBnzb }}%</span>
                    </template>
                  </el-table-column>

                </el-table-column>
                <el-table-column label="应结付金额" align="center" prop="zgYjfje" width="160px"></el-table-column>
                <el-table-column label="已结付额" align="center" prop="zgYjfe" width="160px"> </el-table-column>
              </el-table-column>
              <el-table-column label="居民（单位：万元）" align="center" :class-name="'current-table-title'">
                <el-table-column label="医疗总费用" align="center">

                  <el-table-column show-overflow-tooltip prop="jmYlzfyDqljfse" label="当期累计发生额" width="160px"
                    align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmYlzfyTqljfse" label="上年同期累计发生额" align="center"
                    width="160px"></el-table-column>
                  <el-table-column prop="jmYlzfyTb" label="同比" width="160px" align="center">
                    <template #default="{ row }">
                      <span v-if="row.jmYlzfyTb != null">{{ row.jmYlzfyTb }}%</span>
                    </template>
                  </el-table-column>
                  <el-table-column prop="jmYlzfyBnzb" label="本年占比" width="160px" align="center">
                    <template #default="{ row }">
                      <span v-if="row.jmYlzfyBnzb != null">{{ row.jmYlzfyBnzb }}%</span>
                    </template>
                  </el-table-column>

                </el-table-column>
                <el-table-column label="统筹基金" align="center">
                  <el-table-column show-overflow-tooltip prop="jmTcjjDqljfse" label="当期累计发生额" width="160px"
                    align="center"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmTcjjTqljfse" label="上年同期累计发生额" align="center"
                    width="160px"></el-table-column>
                  <el-table-column prop="jmTcjjTb" label="同比" width="160px" align="center">
                    <template #default="{ row }">
                      <span v-if="row.jmTcjjTb != null">{{ row.jmTcjjTb }}%</span>
                    </template>
                  </el-table-column>
                  <el-table-column prop="jmTcjjBnzb" label="本年占比" width="160px" align="center">
                    <template #default="{ row }">
                      <span v-if="row.jmTcjjBnzb != null">{{ row.jmTcjjBnzb }}%</span>
                    </template>
                  </el-table-column>

                </el-table-column>
                <el-table-column label="应结付金额" align="center" prop="jmYjfje" width="160px"></el-table-column>
                <el-table-column label="已结付额" align="center" prop="jmYjfe" width="160px"> </el-table-column>

              </el-table-column>


            </el-table>
            <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
              :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

          </div>
        </el-tab-pane>
      </el-tabs>

      <div class="drawer_footer">
        <el-button type="primary" @click="handleDownLoad">下载数据</el-button>
        <el-button @click="close">关 闭</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import { approNoticeViewSummaryAnalyse, approNoticeViewDetailAnalyse, approNoticeDownloadSummaryAnalyse, approNoticeDownloadDetailAnalyse } from '@/api/disburse.js';
export default {
  name: 'useredit',
  components: {},
  data() {
    return {
      list: [],
      zgSummaries: [], //职工
      jmSummaries: [],//居民
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        orgCode: "",
        orgName: '',
        appropNoticeId: '',
        tcq: '',
        year: '',
        month: ''
      },
      title: '',
      dialog: false,
      loading: false,
      activeName: 'first',
      rowData: {}
    }
  },
  created() {

  },
  mounted() { },
  methods: {
    showDia(row) {
      var str = '1-'
      if (row.month == 1) {
        str = ''
      }
      this.title = '拨付分析' + '(' + row.year + '年' + str + row.month + '月累计分析)'
      this.activeName = 'first'
      this.rowData = row
      this.fetchData()
      this.dialog = true
    },
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    close() {
      this.dialog = false
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    fetchData() {
      var that = this
      this.listLoading = true
      if (this.activeName == 'first') {//汇总数据
        approNoticeViewSummaryAnalyse({ appropNoticeId: this.rowData.id, tcq: this.rowData.tcq, year: this.rowData.year, month: this.rowData.month }).then((res) => {
          this.listLoading = false
          if (res.code == 0) {
            that.zgSummaries = res.data.zgSummaryAnalyses
            that.jmSummaries = res.data.jmSummaryAnalyses
          }
        })
      } else {//数据明细
        this.queryForm.appropNoticeId = this.rowData.id
        this.queryForm.tcq = this.rowData.tcq
        this.queryForm.year = this.rowData.year
        this.queryForm.month = this.rowData.month
        approNoticeViewDetailAnalyse(that.queryForm).then((res) => {
          this.listLoading = false
          if (res.code == 0) {
            that.list = res.data.records
            that.total = res.data.total
          }
        })
      }
    },

    reseat() {
      this.queryForm = this.$options.data().queryForm
      this.fetchData()
    },

    handleClick(tab, event) {
      this.fetchData()
    },
    handleDownLoad() {
      this.loading = true
      if (this.activeName == 'first') {//汇总数据下载
        approNoticeDownloadSummaryAnalyse({ appropNoticeId: this.rowData.id, tcq: this.rowData.tcq, year: this.rowData.year, month: this.rowData.month }).then((res) => {
          let fileNames = res.headers['content-disposition'] // 获取到Content-Disposition;filename
          let regFileNames = fileNames.match(/=(.*)$/)[1] // 文件名称  截取=后面的文件名称
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(regFileNames)
          link.href = objectUrl
          link.click()
          this.loading = false;
        })
      } else {//数据明细下载
        approNoticeDownloadDetailAnalyse({ appropNoticeId: this.rowData.id, tcq: this.rowData.tcq, year: this.rowData.year, month: this.rowData.month, orgCode: this.queryForm.orgCode, orgName: this.queryForm.orgName }).then((res) => {
          let fileNames = res.headers['content-disposition'] // 获取到Content-Disposition;filename
          let regFileNames = fileNames.match(/=(.*)$/)[1] // 文件名称  截取=后面的文件名称
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(regFileNames)
          link.href = objectUrl
          link.click()
          this.loading = false;

        })
      }
    },
  },
}
</script>
<style lang="scss" scoped>
.drawer_content {
  padding: 20px;
}

.drawer_main {
  padding: 20px 0;
}



::v-deep {
  .el-table {
    th.el-table__cell {
      background-color: #f2f8fd !important;
    }
  }

  .el-table th.add-table-title {
    background-color: #fff1e5 !important;
  }

  .el-table th.current-table-title {
    background-color: #fffee5 !important;
  }

  .el-table th.current-table-title-parent {
    background-color: #e6ecf9 !important;
  }

  // tab切换时出现蓝色边框问题解决
  .el-tabs__item:focus.is-active.is-focus:not(:active) {
    box-shadow: none !important; //切换阴影
    border-radius: 0 !important;
  }

  .box_drawer .el-drawer__header {
    margin-bottom: 0px;
    font-size: 18px;
  }
}

::v-deep .el-table__fixed {
  height: auto !important;
  bottom: 16px; // 不加这个会看不到表头
}
</style>