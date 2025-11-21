<template>
  <el-drawer
      :title="title"
      :before-close="handleClose"
      :visible.sync="dialog"
      direction="rtl"
      :with-header="false"
      custom-class="box_drawer"
      size="800px"
      ref="drawer"
  >
    <div class="drawer_content">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <div class="box_card">
            <div class="box_header">
              <span>人员基本信息</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="人员姓名">
                    <el-input
                        v-model.trim="form.psn_name"
                        disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="身份证号">
                    <el-input
                        v-model.trim="form.cert_no"
                        disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="个人账户支出" class="custemitem">
                    <el-input
                        v-model.trim="form.acct_pay"
                        disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="就诊ID">
                    <el-input
                        v-model.trim="form.mdtrt_id"
                        disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="结算ID">
                    <el-input v-model.trim="form.setl_id" disabled/>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="参保所属医保区划" class="custemitem">
                    <el-input v-model.trim="form.insu_admdvs_name" disabled/>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>举证附件上传</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="图片上传" class="">
                    <el-upload
                        ref="uploads"
                        action="#"
                        list-type="picture-card"
                        :auto-upload="false"
                        accept=".png, .jpg"
                        :file-list="picList"
                    >
                      <i slot="default" class="el-icon-plus"></i>
                      <div slot="file" slot-scope="{ file }">
                        <img
                            class="el-upload-list__item-thumbnail"
                            :src="file.fileUrl"
                            alt=""
                        />
                        <span class="el-upload-list__item-actions">
                          <span
                              class="el-upload-list__item-preview"
                              @click="handlePictureCardPreview(file)"
                          >
                            <i class="el-icon-zoom-in"></i>
                          </span>
                          <!--                          <span-->
                          <!--                              v-if="!disabled"-->
                          <!--                              class="el-upload-list__item-delete"-->
                          <!--                              @click="handleDownload(file)"-->
                          <!--                          >-->
                          <!--                            <i class="el-icon-download"></i>-->
                          <!--                          </span>-->
                        </span>
                      </div>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible" append-to-body>
                      <img width="100%" :src="dialogImageUrl" alt=""/>
                    </el-dialog>
                  </el-form-item>
                  <el-form-item label="文档上传" class="">
                    <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12"
                            v-for="item in docList"
                            :key="item.id"
                            :label="item.id"
                            :value="item.id">
                      <p class="file-box">
                        <el-link type="primary" :href="item.fileUrl" target="_blank"><i class="el-icon-document"></i>{{ item.fileName }}</el-link>
                      </p>
                    </el-col>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>其他</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="驳回意见">
                    <el-input
                        type="textarea" :rows="6"
                        v-model.trim="form.verify_reason"
                        disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="备注">
                    <el-input
                        type="textarea" :rows="6"
                        v-model.trim="form.describe"
                        disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>

        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button size="mini" icon="el-icon-download" type="success" v-show="isdow"
                   @click="dowload()">举证附件下载
        </el-button>
        <el-button @click="cancelForm">关 闭</el-button>
      </div>
    </div>

  </el-drawer>
</template>

<script>
import {lookFileInfo,downloadOfEvidence} from '@/api/checkSuspicions'
import {fileURL} from "@/config/setting.config";
import {updateStatus} from "@/api_net/netTagMechanism";

export default {
  name: 'edit',
  components: {},
  data() {
    return {
      title: '',
      dialog: false,
      loading: false,
      isdow: false,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
      },
      formLabelWidth: '90px',
      timer: null,
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      picList: [],
      docList: [],
      rid:""
    }
  },
  mounted() {
    // this.$nextTick(()=>{
    //   this.picList = []
    //   this.$refs.uploads.clearFiles();
    // })
  },
  methods: {
    async showDia(row) {

      if (!row) {
        this.title = '新增'
      } else {
        this.isdow = false ;
        this.title = '编辑'
        this.form = Object.assign({}, row)
        const res = await lookFileInfo({rid: row.rid})
        let picListTemp = res.data.picList;
        picListTemp.forEach(function (e){
          e.fileUrl = fileURL + e.fileUrl
        });
        this.picList = picListTemp
        let docListTemp = res.data.docList;
        docListTemp.forEach(function (e){
          e.fileUrl = fileURL + e.fileUrl
        });
        this.docList = docListTemp
        this.rid = row.rid ;
        if(this.picList .length!=0 || this.docList.length !=0 ){
          this.isdow = true ;
        }
      }
      this.dialog = true
    },
    handleClose(done) {
      this.cancelForm();
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
      this.close()
    },

    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.fileUrl
      this.dialogVisible = true
    },
    handleDownload(file) {
      console.log(file)
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      this.$refs.uploads.clearFiles();
      this.docList = []
      this.picList = []
      clearTimeout(this.timer)
    },
    dowload(){
      downloadOfEvidence(this.rid).then((res) => {

        let fileName = "举证证据.zip";
        let objectUrl = URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.download = decodeURI(fileName)
        link.href = objectUrl
        link.click()
        this.listLoading = false;
        this.$baseMessage("下载成功！", 'success')
      })
    }
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-upload--picture-card {
    display: none !important;
    opacity: 0 !important;
  }

  .el-dialog__body {
    border-top: 0;
  }

  .el-form-item__content {
    display: flex;
  }

  .el-upload-list.el-upload-list--text {
    margin-left: 0px;
  }

  .file-box {
    padding: 0 10px;
    background-color: #f7f9fb;
    margin-top: 0;
    margin-bottom: 6px;

    .el-link {
      line-height: 18px;
    }
  }
}
</style>