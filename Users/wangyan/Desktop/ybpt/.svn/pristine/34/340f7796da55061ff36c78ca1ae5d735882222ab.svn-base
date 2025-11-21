<template>
  <el-drawer
      :title="title"
      :visible.sync="dialog"
      direction="rtl"
      :with-header="false"
      :before-close="handleClose"
      custom-class="box_drawer"
      size="1060px"
      ref="drawer"
      :close-on-click-modal="true"
      append-to-body
  >
    <!-- pdf 内嵌 -->
    <iframe
        :src="PDFsrc"
        frameborder="0"
        style="width: 100%; height: 100%"
    ></iframe>
    <div class="drawer_content">
      <div class="drawer_footer">
        <el-button type="primary" @click="downExcel" v-if="showExcel">下载明细</el-button>
        <el-button type="primary" @click="download" v-if="showDown">附件下载</el-button>
        <el-button type="primary" @click="downPdf">下载PDF</el-button>
        <el-button @click="cancelForm">关 闭</el-button>
      </div>
    </div>
    <el-dialog
        title="驳回理由"
        :visible.sync="remarkVisible"
        append-to-body
        width="700px"
    >
      <el-form ref="form" :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="说明" prop="regeditType">
              <el-input
                  type="textarea"
                  v-model="form.approval_opinion"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeRemark">取 消</el-button>
        <!--                <el-button type="primary" @click="reject">确 定</el-button>-->
      </div>
    </el-dialog>
  </el-drawer>
</template>
<style lang="scss" scoped>
.file-main {
  padding: 0 30px;
  h2 {
    font-size: 20px;
    font-weight: bold;
  }
  .jj {
    font-size: 16px;
    line-height: 25px;
    margin-top: 30px;
    p {
      text-align: center;
    }
  }
  .doc-content {
    font-size: 16px;
    margin-top: 30px;
    padding: 0 0px;
    line-height: 30px;
    text-indent: 2em;
  }
  .doc-footer {
    margin-top: 30px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;
    .box {
      flex: 1;
      line-height: 36px;
      font-size: 16px;
    }
  }
}
</style>
<script>
import Views from './shensu'
import {down, downloadDetailFile} from "@/api/sbApply";
import {baseURL} from "@/config/setting.config";
import {bedViewPdf} from "@/api/sbApplyDrug";

export default {
  name: 'edit',
  components: {Views},
  data() {
    return {
      status: '',
      remark: '',
      qsId: '',
      remarkVisible: false,
      downPath: "",
      PDFsrc: '',
      filePath: '',
      title: '',
      dialog: false,
      showFlag:true,
      showDown:true,
      showExcel:true,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        id: '',
      },
      form: {
        id: '',
        status: '',
        contract_id: '',
      },
      formLabelWidth: '100px',
      timer: null,
    }
  },
  mounted() {
  },
  methods: {
    showEdit(row,type) {
      this.showDown = true
      this.showExcel = true
      if (!row) {
        this.title = '新增'
      } else {
        console.info(row);
        this.title = '查看'
        this.form = Object.assign({}, row)
        this.form.id = row.id
        this.qsId = row.id;
        this.status = row.status
        if(this.status != "3"){
          this.showFlag = true;
        }else{
          this.showFlag = false;
        }
        this.details(row,type)
        console.log(this.showExcel)
      }
    },
    close() {
      this.dialog = false
      this.$emit('update:visible', false)
    },
    closeRemark() {
      this.remarkVisible = false
    },
    downExcel(){
      // bedViewPdf(this.form.id).then((res) => {
      //   if (res.code == 0) {
      //     this.listLoading = false;
      //     this.queryData();
      //     this.$baseMessage('成功', 'success')
      //   } else {
      //     this.$baseMessage(res.msg, 'error')
      //   }
      // })
      down(this.form).then((res) => {
        let objectUrl = URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.download = decodeURI("明细下载.xlsx")
        link.href = objectUrl
        link.click()
        // window.location.href = baseURL+"common/exceldownload?fileName=" + encodeURI(res.data) + "&delete=" + true + "&type=sbApply" + "&name=" + "明细.xlsx";
      })
    },
    async download() {
      downloadDetailFile(this.form).then((res) => {
        let fileName = "附件下载.zip";
        if(res.data.size == 0){
          this.$baseMessage("没有附件，请上传后下载！", 'error')
          return
        }
        let objectUrl = URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.download = decodeURI(fileName)
        link.href = objectUrl
        link.click()
        this.listLoading = false;

      })
    },
    handleClose(done) {
    },
    downPdf(){
      self.location.href=this.downPath;
    },

    cancelForm() {
      this.loading = false
      this.dialog = false
      this.close()
      this.$emit('closeChildDialog')
      this.$emit('fetch-data')
      this.PDFsrc = "";
      clearTimeout(this.timer)
    },
    async details(row,type) {
      var that = this
      that.dialog = true
      that.showExcel = false
      if("1" == type){//查看详情
        that.PDFsrc = row.pdf_path
        that.downPath = row.down_pdf_path

      }else if("2" == type){//查看授权书
        //申报类型 1.西药 2.中成药 3.中药饮片 4.医疗服务 5. 耗材 6.自设项目
        if (row.type != '1' && row.type != '2' && row.type != '3') {
          that.showExcel = true
        }
        that.showDown = false
        that.PDFsrc = row.auth_pdf_path
        that.downPath = row.auth_down_pdf_path
      }


    }

  }
}
</script>