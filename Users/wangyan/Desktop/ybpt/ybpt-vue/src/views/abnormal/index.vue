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
          <el-form-item v-show="userinfo.user_type == 1 && userinfo.org_code == 320399">
            <el-select v-model="queryForm.area" placeholder="请选择行政区划" clearable style="width: 100%">
              <el-option label="市本级" value="市本级"></el-option>
              <el-option label="贾汪区" value="贾汪区"></el-option>
              <el-option label="邳州市" value="邳州市"></el-option>
              <el-option label="新沂市" value="新沂市"></el-option>
              <el-option label="睢宁县" value="睢宁县"></el-option>
              <el-option label="沛县" value="沛县"></el-option>
              <el-option label="丰县" value="丰县"></el-option>
              <el-option label="铜山区" value="铜山区"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="if_upload">
            <el-select clearable v-model.trim="queryForm.if_upload" placeholder="请选择是否修改" class="w">
              <el-option value="1" label="已修改"></el-option>
              <el-option value="0" label="未修改"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-show="userinfo.user_type == 1">
            <el-input v-model.trim="queryForm.org_code" placeholder="请输入机构编码" clearable
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
          <el-form-item v-show="userinfo.user_type == 2 || userinfo.user_type == 3">
            <el-upload ref="upfile"
                       :auto-upload="false" :show-file-list=false accept=".xlsx"
                       :on-change="handleChange2"
                       :on-success="handleSuccess" action="#">
              <el-button icon="el-icon-upload2" type="success">更新上传</el-button>
            </el-upload>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" :data="list" border :element-loading-text="elementLoadingText"
              @selection-change="setSelectRows" height="calc(100vh - 360px)">
      <el-table-column show-overflow-tooltip type="index" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="org_code" label="机构编码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="org_name" label="机构名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="area" label="行政区划" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="carno" label="身份证号" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="name" label="人员姓名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="settle_time" label="费用发生时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="settle_type" label="结算类别" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="med_type" label="医疗类别" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="catalogue_type" label="目录大类" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="catalogue_code" label="目录编码(MED_LIST_CODG)" align="center"  width="115px"></el-table-column>
      <el-table-column show-overflow-tooltip prop="catalogue_code_new" label="修改后编码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="reason_one" label="原因1" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="reason_two" label="原因2" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="reason_three" label="原因3" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="reason_four" label="原因4" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="reason_five" label="申诉原因" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="insured_persons_no" label="参保人编号" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="mdtrt_id" label="就诊id" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="setl_id" label="结算id" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="project_name" label="项目名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="upload_time" label="上传时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="upload_no" label="上传批次" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="update_time" label="机构修改时间" align="center"></el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
                   :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>
  </div>
</template>

<script>
import {
  exportSettleAbnormalData,
  getSettleAbnormalList,
  getUploadNo,
  importData,
  importData2
} from '@/api/settleAbnormal'

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
        this.$baseMessage("上传成功", 'success')
      } else {
        this.$baseMessage(result.data.msg, 'error')
      }
      this.dialog = false
      this.loading = false
    },
    async handleChange2(file, fileList) {
      this.dialog = true
      this.loading = true
      this.fileList = fileList;
      let fd = new FormData();
      fd.append("id", this.selectRows.id);
      this.fileList.forEach(item => {
        //文件信息中raw才是真的文件
        fd.append("file", item.raw);
      })

      var result = await importData2(fd);
      if (result.data.code == 0) {
        this.$refs.upfile.clearFiles();
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
        await exportSettleAbnormalData(this.queryForm).then((res) => {
          let fileName = "徐州异常数据导出.xlsx";
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
      const res = await getSettleAbnormalList(this.queryForm)
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
