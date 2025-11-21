<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1100px" @open="openMsg" top="8vh" append-to-body
    @close="close()" highlight-current-row :close-on-click-modal="false">
    <vab-query-form>
      <el-form :inline="true" :model="queryForm">
        <el-form-item>
          <el-input placeholder="请输入标题" style="width: 160px;" v-model.trim="queryForm.title" clearable
            @keyup.enter.native="queryData" />
        </el-form-item>
        <!--<el-form-item>-->
        <!--<el-date-picker-->
        <!--v-model="queryForm.launchTime"-->
        <!--type="daterange"-->
        <!--value-format="yyyy-MM-dd"-->

        <!--range-separator="至"-->
        <!--start-placeholder="开始日期"-->
        <!--end-placeholder="结束日期">-->
        <!--</el-date-picker>-->
        <!--</el-form-item>-->
        <el-form-item>
          <el-button type="primary" @click="queryData">查 询</el-button>
          <el-button @click="reseat">重 置</el-button>
        </el-form-item>
      </el-form>
    </vab-query-form>
    <div class="main-msg-box">
      <div class="left-msg">
        <el-table v-loading="listLoading" ref="listTable" stripe :data="tableData"
          :element-loading-text="elementLoadingText" highlight-current-row border @current-change="handleCurrentChange"
          height="calc(100vh - 16vh - 170px)" @row-click="handleEdit">
          <template slot="empty">
            <el-empty :image-size="150"></el-empty>
          </template>
          <el-table-column label="标题" align="center" show-overflow-tooltip>
            <template #default="{ row }">
              <el-link type="primary" @click="getEntityById(row.id)" v-if="row.is_read == 0" style="color: red">{{
    row.title }}</el-link>
              <el-link type="primary" @click="getEntityById(row.id)" v-else-if="row.is_read == 1">{{ row.title
                }}</el-link>
            </template>
          </el-table-column>
          <el-table-column label="发布时间" align="center" show-overflow-tooltip prop="date"><template #default="{ row }">
              <span v-if="row.is_read == 0" style="color: red">{{ row.launchTime }}</span>
              <span v-else-if="row.is_read == 1">{{ row.launchTime }}</span>
            </template></el-table-column>
        </el-table>
      </div>
      <div class="right-msg">
        <el-card shadow="never" style="background-color: #f8fbfd; height:calc(100% - 60px);">
          <div>
            <h3>{{ form.title }}</h3>
            <div class="des-box" v-html="form.content">

            </div>
          </div>
        </el-card>
        <div class="file-list">
          <span>附件： </span>
          <!--<el-link :underline="false" icon="el-icon-document">这里是附件名称.word</el-link>-->
          <div :key="item.id" class="fujian" v-for="item in form.fileInfoList">
            <el-link icon="el-icon-link" :href="fileURL + item.fileUrl">{{ item.fileName }}</el-link>
          </div>
        </div>

      </div>
    </div>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
      :pager-count="5" :total="total" @size-change="handleSizeChange"
      @current-change="handleCurrentChange2"></el-pagination>
  </el-dialog>
</template>
<script>
import { getTop2NoticeForAccepterAndTotalCountPage, setNoticeIsReadApi, getTop2NoticeForAccepterAndTotalCountApi } from "@/api_check/notice";
import { getEntityByIdApi } from "@/api_check/notice";
import { fileURL } from "@/config/setting.config";
export default {
  name: 'Index',
  components: {},
  data() {
    return {
      currentRow: null,
      radio: '',
      tableData: [],
      title: '',
      dialogFormVisible: false,
      value1: '',
      isShow: false,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
      form: {
        title: "",
        content: "",
        fileInfoList: [],
      },
      tableData: [],
      fileURL
    }
  },
  created() {
    // this.queryData()
  },
  beforeDestroy() { },
  mounted() {

  },
  methods: {
    openMsg() {
      //   this.$nextTick(()=>{
      //   this.$refs.listTable.setCurrentRow(this.tableData[0])
      // })
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
    queryData() {

      this.fetchData()
    },
    async fetchData() {
      this.listLoading = true
      getTop2NoticeForAccepterAndTotalCountPage(this.queryForm).then((res) => {
        if (res.code == 0) {
          this.tableData = res.data.top2NoticeList.records;
          this.total = res.data.top2NoticeList.total
          this.listLoading = false

        }
      })
    },
    showDia() {
      var that = this;
      that.title = '消息提醒'
      that.dialogFormVisible = true
      this.queryData();

    },
    closeDia() {
      this.dialogFormVisible = false

    },
    queryDia() {
      this.queryData();
    },
    async getEntityById(id) {
      const { data } = await getEntityByIdApi(id);
      console.log(this.fileURL)
      this.form.title = data.title
      this.form.content = data.content
      this.form.fileInfoList = data.fileInfoList;
      this.setNoticeIsRead(id);
    },
    async setNoticeIsRead(noticeId) {
      let res = await setNoticeIsReadApi(noticeId);
      if (res.code == 0) {
        this.queryData()
        window.top2()
      }
    },
    reseat() {
      this.queryForm.title = "";
      this.queryData();
    },
    close() {
      this.form.content = "";
      this.form.title = "";
      this.form.fileInfoList = [];
      getTop2NoticeForAccepterAndTotalCountApi().then(res => {
        if (res.code == 0) {

          if (res.data.totalNoticeCount > 0) {
            this.$baseMessage("您有消息未读！", 'error')
          }

        }
      });
    },
    handleEdit(row, column, event) {
      this.getEntityById(row.id);
    }

  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-dialog__body {
    padding: 12px 20px;
  }

  .el-form-item--small.el-form-item {
    margin-bottom: 2px;
  }

  .main-msg-box {
    display: flex;

    .left-msg {
      margin-right: -1px;
      width: 320px;
    }

    .right-msg {
      flex: 1;

      h3 {
        margin: 0 auto 20px;
        text-align: center;
        font-size: 18px;
        line-height: 1.4;
      }

      .des-box {
        height: calc(100vh - 16vh - 320px);
        overflow-y: auto;
      }

      .file-list {
        height: 61px;
        background-color: #f8fbfd;
        border: 1px solid #ebeef5;
        margin-top: -16px;
        box-sizing: border-box;
        padding: 4px 14px;
        color: #17436c;
        display: flex;
        flex-wrap: wrap;
        align-items: center;
        overflow-y: auto;

        .el-link {
          padding: 4px 8px;
          background-color: #ebf5ff;
          margin-right: 6px;
          margin-bottom: 4px;
        }
      }
    }
  }

  .el-pagination {
    text-align: left;
  }

  .el-card {
    box-sizing: border-box;
  }

}
</style>