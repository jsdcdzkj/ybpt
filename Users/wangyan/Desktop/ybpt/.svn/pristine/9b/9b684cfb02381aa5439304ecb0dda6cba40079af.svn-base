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
              <span>举证附件上传</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="图片上传">
                        <el-upload
                                ref="upload"
                                list-type="picture-card"
                                accept=".jpg, .png, .JPG, .PNG"
                                :auto-upload="true"
                                :file-list="fileList"
                                :on-change="fileListChange"
                                :http-request="uploadFile"
                                action="dsadas"
                        >
                            <i slot="default" class="el-icon-plus"></i>
                            <div slot="file" slot-scope="{ file }">
                                <img
                                        class="el-upload-list__item-thumbnail"
                                        :src="file.url"
                                        alt=""
                                />
                                <span class="el-upload-list__item-actions">
                          <span
                                  class="el-upload-list__item-preview"
                                  @click="handlePictureCardPreview(file)"
                          >
                            <i class="el-icon-zoom-in"></i>
                          </span>
                          <span

                                  class="el-upload-list__item-delete"
                                  @click="handleRemove(file)"
                          >
                            <i class="el-icon-delete"></i>
                          </span>
                        </span>
                            </div>
                        </el-upload>
                        <el-dialog :visible.sync="dialogVisible" append-to-body>
                            <img width="100%" :src="dialogImageUrl" alt=""/>
                        </el-dialog>
                    </el-form-item>
				  <el-form-item label="文档上传">
					  <el-upload
                       ref="upload2"
					    class="upload-demo"
                        :auto-upload="true"
						accept=".pdf, .doc, .docx"
					    action="#"
					    :on-change="handleChange"
                       :on-remove="handleRemoveAttachment"
                        :http-request="uploadFile2"
					    :file-list="fileList2">
					    <el-button size="small" type="primary">点击上传</el-button>
					  </el-upload>
				  </el-form-item>
                    <el-form-item label="描述" prop="regeditType">
                        <el-input
                                type="textarea"
                                v-model="form.describe"
                        ></el-input>
                    </el-form-item>
                </el-col>

              </el-row>
            </div>
          </div>
		  
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>

          <el-button
                  type="primary"
                  @click="sub"
                  :loading="loading"
          >
              {{ loading ? '提交中 ...' : '提 交' }}
          </el-button>
      </div>
    </div>
    
  </el-drawer>
</template>

<script>
    import axios from 'axios';
    import {baseURL,fileURL} from "@/config/setting.config";
    import {lookFileInfo} from '@/api/checkSuspicions'
export default {
  name: 'edit',
  components: {  },
  data() {
    return {
      title: '',
        formDate: new FormData(),
        formDate2: new FormData(),
      dialog: false,
      loading: false,
      form: {
          rid:"",
          describe:"",
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
      },
      formLabelWidth: '80px',
      timer: null,
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      fileList: [],
      fileListOld: [],
	  fileList2:[],
        fcf:false,
       checkSuspicionsVo:{

       },
    checkSuspicionsVoList: [],
    filecheckSuspicionsVoList: [],
    oldRids: []
    }
  },
  mounted() {},
  methods: {
    showDia(row) {
        if (!row) {
            this.title = '新增'
            this.fileList = [];
            this.fileList2 = [];

        } else {
            var that = this ;
            that.title = '编辑'
            that.form = Object.assign({}, row)
            that.fileList = [];
            that.fileList2 = [];
            that.checkSuspicionsVoList = [];
            that.filecheckSuspicionsVoList = [];
            that.oldRids=[] ;
            lookFileInfo({rid: row.rid}).then((res) => {
                for(var i = 0;i<res.data.picList.length;i++){
                    that.fileList.push({
                        url: fileURL+res.data.picList[i].fileUrl,
                        fileMd5: res.data.picList[i].id
                    })

                }
                for(var i = 0;i<res.data.docList.length;i++){
                    that.fileList2.push({
                        name: res.data.docList[i].fileName,
                        url: res.data.docList[i].fileUrl,
                        fileMd5: res.data.docList[i].id
                    })

                }

            })

            that.formDate = new FormData();

        }
        this.dialog = true
    },
      async sub() {
        //图片上传
        var that = this ;
          that.$refs.upload.submit();
          that.$refs.upload2.submit();
        that.formDate.append("rid", that.form.rid);
        if(that.form.describe != "" && that.form.describe!= undefined && that.form.describe!= null){
            that.formDate.append("describe", that.form.describe);
        }
          for(var i = 0;i<that.fileList.length;i++){
              if(that.fileList[i].fileMd5 != null && that.fileList[i].fileMd5!=undefined){
                  if(that.fileList[i] != null ){
                      that.oldRids.push(that.fileList[i].fileMd5)
                  }
              }
          }

          console.log("that.fileList21111");
          console.log(that.fileList2);
          for(var m = 0;m<that.fileList2.length;m++){
              if(that.fileList2[m].fileMd5 != null && that.fileList2[m].fileMd5!=undefined){
                  that.oldRids.push(that.fileList2[m].fileMd5)
                  ;
              }
          }
          if(that.oldRids.length!=0){
              that.formDate.append("oldFileIds", that.oldRids.join(","));
          }

          for(var j = 0;j<that.checkSuspicionsVoList.length;j++){
              if(that.checkSuspicionsVoList[j]!=null ){
                  if(that.checkSuspicionsVoList[j].fileMd5 == ""){
                      that.formDate.append("picfile", that.checkSuspicionsVoList[j].picfile);
                  }
              }

          }
          console.log("that.filecheckSuspicionsVoList+++++");
          console.log(that.filecheckSuspicionsVoList);
          console.log(that.filecheckSuspicionsVoList.length);

          for(var k = 0;k<that.filecheckSuspicionsVoList.length;k++){
              if(that.filecheckSuspicionsVoList[k].fileMd5 == ""){
                  that.formDate.append("file", that.filecheckSuspicionsVoList[k].file);
              }
          }
          // that.formDate.append("picfile", that.checkSuspicionsVoList);
          // that.formDate.append("file", that.filecheckSuspicionsVoList);
          that.loading = true ;


          var config = {
              headers: {
                  'Content-Type': 'multipart/form-data',
                  'accessToken': localStorage.getItem("accessToken")
              }
          }
          // 添加请求头
          await axios.post(baseURL+'checkSuspicions/attachmentUploading', that.formDate, config)
              .then(res => {

                  if(res.data.code == 0){
                      that.loading = false ;
                      that.cancelForm() ;
                  }else {
                      that.$baseMessage(res.data.msg, 'error') ;
                  }

              });



    },
      handleRemove(file, fileList) {

          var that = this;
          let Arr = that.$refs['upload'].uploadFiles
          let index = Arr.indexOf(file)
          that.fileList.splice(index,1);
      },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleChange(file, fileList) {
	  this.fileList2 = fileList.slice(-3);

    },
      handleRemoveAttachment(file, fileList){
          this.fileList2 = fileList.slice(-3);
      },
    handleClose(done) {
        this.cancelForm()
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
        this.$refs.upload.clearFiles();
        this.fileList = [];
        this.fileList2 = [];
      clearTimeout(this.timer)
    },
      uploadFile(file) {
          var that = this;
          // that.formDate.append("picfile", file.file);
          that.checkSuspicionsVoList.push({
              fileMd5:"",
              picfile:file.file
          });

      },
      async uploadFile2(file) {
          var that = this;
        setTimeout(function () {
            that.filecheckSuspicionsVoList.push({
                fileMd5:"",
                file:file.file
            });
            console.info(that.filecheckSuspicionsVoList);
        },300)

      },
      fileListChange(file) {
          var that = this;
          that.fileList.push({
              url: file.url
          })


      },

  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-dialog__body {
    border-top: 0;
  }
  .el-form-item__content{
	  display: flex;
  }
  .el-upload-list.el-upload-list--text{
	  margin-left: 0px;
  }
}
</style>