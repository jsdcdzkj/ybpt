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
              <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                <el-form-item label="文件名称">
                  <el-input v-model.trim="queryForm.fileName" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">耗材文件</span>
            <div class="right">
              <el-button v-show="userinfo.user_type == 1 && userinfo.org_code == 320399" type="primary" icon="el-icon-upload2" @click="handleFile()">
                耗材文件上传
              </el-button>
            </div>
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
                    highlight-current-row
                    border @current-change="handleCurrentChange" @selection-change="setSelectRows"
                    height="calc(100% - 50px)">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column align="center" label="文件名称" prop="fileName"
                             show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="上传时间" prop="uploadTime"
                             show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="文件大小" prop="fileSize"
                             show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="操作人" prop="fileMd5"
                             show-overflow-tooltip></el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="300" align="center">
              <template #default="{ row }">
                <el-button plain @click="handleDownload(row)" type="primary" size="mini">
                  下载
                </el-button>
                <el-button v-show="userinfo.user_type == 1 && userinfo.org_code == 320399" plain @click="handleDelete(row)" type="warning" size="mini">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                         :layout="layout"
                         :total="total" @size-change="handleSizeChange"
                         @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>

    <fileUpload ref="fileUpload" @fetch-data="fetchData"></fileUpload>

  </div>
</template>

<script>
import {fileDel, getFile} from '@/api/consumables'
import FileUpload from './uploadFile'
import {fileURL} from "@/config/setting.config";
import {doDelete} from "@/api/advice";

export default {
  name: 'Index',
  components: {
    FileUpload
  },
  data() {
    return {
      userinfo: '',
      value1: '',
      checked: false,
      isShow: false,
      list: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      tableHeight: '100%',
      elementLoadingText: '正在加载...',
      queryForm: {
        fileName: '',
        pageNo: 1,
        pageSize: 10,
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
    handleFile() {
      this.$refs['fileUpload'].showEdit()
    },
    handleDownload(row) {
      self.location.href = fileURL + row.fileUrl + '?n=' + row.fileName;
    },
    handleDelete(row){
      this.$baseConfirm('确认进行删除？', null, async () => {
        let that = this;
        fileDel({id:row.id}).then((res) => {
          if (res.code == 0) {
            that.$baseMessage('删除成功', 'success')
            that.queryData()
          } else {
            that.$baseMessage('请尝试刷新页面', 'error')
          }
        })

      })

    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    fetchData() {
      let that = this;
      getFile(this.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = []
          that.list = res.data.records
          that.total = res.data.total
        } else {
          that.$baseMessage('请尝试刷新页面', 'error')
        }
      })
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    reseat() {
      this.queryForm.fileName = "";
      this.fetchData();
    }
  },
}
</script>
