<template>
  <div class="index-container" :visible.sync="dialog" v-loading="loading" element-loading-text="数据同步中"
       element-loading-spinner="el-icon-loading"
       element-loading-background="rgba(0, 0, 0, 0.8)">
    <vab-query-form>
      <vab-query-form-left-panel :span="12">
        <el-upload ref="upfile" v-show="userinfo.user_type == 1 && userinfo.org_code == 320399"
                   :auto-upload="false" :show-file-list=false accept=".xlsx"
                   :on-change="handleChange"
                   :on-success="handleSuccess" action="#">
          <el-button icon="el-icon-upload2" type="success">上传文件</el-button>
        </el-upload>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="24">
        <el-form :inline="true" :model="queryForm" :rules="rules" @submit.native.prevent>
          <el-form-item label=" " prop="upload_no">
            <el-select clearable v-model.trim="queryForm.upload_no" placeholder="请选择批次" class="w">
              <el-option v-for="item in uploadNoList" :key="item" :label="item"
                         :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-show="userinfo.user_type == 1">
            <el-input v-model.trim="queryForm.org_code" placeholder="请输入药店编码" clearable
                      @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-download" type="primary" @click="handleExport">
              导出
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" :data="list" border :element-loading-text="elementLoadingText"
              @selection-change="setSelectRows" height="calc(100vh - 360px)">
      <el-table-column show-overflow-tooltip type="index" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="serial_number" label="流水号" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="org_code" label="药店编码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="org_name" label="药店名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="quantity" label="数量" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="price" label="单价" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="fixmedins_code" label="国家码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="drug_name" label="药品名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="upload_time" label="上传时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="upload_no" label="上传批次" align="center"></el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>
  </div>
</template>

<script>
import {
  exportSettleErrorDataData,
  getSettleErrorDataList,
  getUploadNo,
  importData,
} from '@/api/settleErrorData'

export default {
  name: 'UserManagement',
  data() {
    return {
      dialog: false,
      loading: false,
      userinfo: '',
      list: null,
      listLoading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      uploadNoList: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        area: '',
        upload_no: '',
        if_upload: '',
      },
      rules: {
        upload_no: [{required: true, trigger: 'blur', message: '请选择批次'}],
      },
    }
  },
  created() {
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
    this.getUploadNoList()
  },
  methods: {
    async getUploadNoList() {
      const res = await getUploadNo()
      if ((res.code = '0')) {
        this.uploadNoList = res.data
      }
    },
    async handleChange(file, fileList) {
      this.dialog = true
      this.loading = true
      this.fileList = fileList;
      let fd = new FormData();
      fd.append("id", this.selectRows.id);
      this.fileList.forEach(item => {
        //文件信息中raw才是真的文件
        fd.append("file", item.raw);
      })

      var result = await importData(fd);
      if (result.data.code == 0) {
        this.$refs.upfile.clearFiles();
        await this.getUploadNoList()
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
    setSelectRows(val) {
      this.selectRows = val
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    getArea(){
      if(this.userinfo.user_type == 1 && this.userinfo.org_code != 320399){
        const orgCode = this.userinfo.org_code;
        if(orgCode == 320399){
          this.queryForm.area = '市本级';
        }
        if(orgCode == 320305){
          this.queryForm.area = '贾汪区';
        }
        if(orgCode == 320382){
          this.queryForm.area = '邳州市';
        }
        if(orgCode == 320381){
          this.queryForm.area = '新沂市';
        }
        if(orgCode == 320324){
          this.queryForm.area = '睢宁县';
        }
        if(orgCode == 320322){
          this.queryForm.area = '沛县';
        }
        if(orgCode == 320321){
          this.queryForm.area = '丰县';
        }
        if(orgCode == 320312){
          this.queryForm.area = '铜山区';
        }
      }
    },
    handleExport(row) {
      if (!this.queryForm.upload_no) {
        this.$baseMessage('请选择批次号', 'error')
        return;
      }
      this.getArea()
      this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
        this.listLoading = true
        await exportSettleErrorDataData(this.queryForm).then((res) => {
          let fileName = "结算错误数据导出.xlsx";
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
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    async fetchData() {
      if (!this.queryForm.upload_no) {
        this.$baseMessage('请选择批次号', 'error')
        return;
      }
      this.getArea()
      this.listLoading = true
      const res = await getSettleErrorDataList(this.queryForm)
      if ((res.code = '0')) {
        this.list = res.data.records
        this.total = res.data.total
      }
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  min-height: calc(#{$base-app-main-height} - 50px) !important;
}
</style>
