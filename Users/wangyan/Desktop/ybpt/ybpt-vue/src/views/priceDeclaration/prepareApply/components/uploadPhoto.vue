<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="500px"
      @close="close"
      append-to-body
      :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
<!--      <el-form-item label="模板下载">-->
<!--        <el-button size="small" type="warning" @click="downloadTemplate">模板下载</el-button>-->
<!--      </el-form-item>-->
      <el-form-item label="上传附件" prop="fileInfoList">
        <el-upload
            class="upload-demo"
            ref="upload"
            action=""
            multiple
            :show-file-list="false"
            :before-upload="beforeUpload"
            :on-change="fileChange"
            :on-remove="fileRemove"
            :file-list="form.fileInfoList"
            :http-request="handleUpload"
        >
          <div slot="tip" class="el-upload__tip">请按照模板文件格式要求上传附件内容</div>
          <el-button size="small" type="primary">上传附件</el-button>
        </el-upload>
      </el-form-item>
      <el-scrollbar style="height: 200px; overflow: hidden">
        <div style="padding:0;">
          <div v-for="file in form.fileInfoList" :key="file.id" style="" class="file-list-item">
            <div style="display: flex; line-height: 30px;vertical-align: center;">
              <i class="el-icon-document" style="line-height: 30px;margin-right: 3px;"></i>
              <el-tooltip effect="dark" :content="file.fileName" placement="top" disabled>
                <div style="font-size: 14px;" class="file-item-name">{{file.fileName}}</div>
              </el-tooltip>
            </div>
            <!--              <label class="el-upload-list__item-status-label">-->
            <i class="el-icon-circle-check file-item-check"></i>
            <!--              </label>-->
            <i class="file-item-delete el-icon-close" @click="deleteFile(file)"></i>
          </div>
        </div>
      </el-scrollbar>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import {uploadDetailFile, download, deleteDetailFile} from '@/api/prepareApply'

  export default {
    name: 'photo',
    data() {
      return {
        form: {
          sign: 'photo',
          fileInfoList: [] // 上传的文件列表
        },
        title: '',
        dialogFormVisible: false,
        rules: {
          fileInfoList: [{required: true, trigger: 'blur', message: '请上传附件！'}],
        },
      }
    },
    created() {
    },
    methods: {
      showEdit(row) {
        this.title = '附件上传';

        this.form = {fileInfoList:[],...row,};
        this.dialogFormVisible = true;
        this.$set(this.form, 'fileInfoList', row.fileInfoList != null ? row.fileInfoList.map(item => {
          this.$set(item, 'name', item.fileName);
          return item
        }) : []);
        // console.log('上传医疗许可证',this.form,row,'this.form');
      },
      beforeUpload(file) {
        const isLt1M = file.size / 1024 / 1024 < 10
        console.log(file,this.form.fileInfoList);
        if(this.form.fileInfoList.filter(x=>x.fileName == file.name).length>0){
          this.$message.warning('请勿上传重复文件！');
          return false
        }
        if (isLt1M) {
          this.file = file
          return true
        };
        this.$message.warning('请上传不超过10M的文件.');
        return false
      },
      fileRemove(file, fileList) {
        // this.form.fileInfoList = [];
        // console.log('请说你奥克拉丽莎',fileList);
        // console.log(file,'file');
        let arr = this.form.fileInfoList.filter(item => file.id !== item.id);
        this.$set(this.form, 'fileInfoList', arr);
      },
      fileChange(file, fileList) {
        // if (fileInfoList.length > 0) {
        //   this.form.fileInfoList = fileList
        // }
      },

      uploadFile() {
        this.$refs.upload.submit()
      },
      close() {
        this.$refs['form'].resetFields();
        this.dialogFormVisible = false
      },
      async handleUpload(file) {
        // console.log(file,'aaaaaaaaaaaaaaaaaaaaaaaaaaa');
        let fd = new FormData();
        fd.append('file', file.file)
        // console.log(fd, fd.get("file"));
        let res = await uploadDetailFile(fd);
        if (res.data.code == "0") {
          this.$set(res.data.data, 'name', res.data.data.fileName)
          this.form.fileInfoList.push(res.data.data);
          let fileInfoList = this.form.fileInfoList;
          this.$set(this.form,'fileInfoList',fileInfoList);
          this.$refs['form'].clearValidate();
          // this.$baseMessage("操作成功", 'success')
        } else {
          // this.$baseMessage("操作失败", 'error')
        }
      },
      async save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            // let fd = new FormData()
            // this.form.fileInfoList.forEach((item) => {
            //   //文件信息中raw才是真的文件
            //   fd.append('file', item.raw)
            // })
            // const res = await uploadDetailFile(fd)
            // if (res.code == "0") {
            //   this.$baseMessage("操作成功", 'success')
            this.form.sign = 'photo';
            console.log(this.form,'????????????');
            this.$emit('fetch-data', this.form)
            this.close()
            // } else {
            //   this.$baseMessage("请尝试刷新页面", 'error')
            // }

          } else {
            return false
          }
        })
      },
      downloadTemplate() {
        download().then(res => {
          let fileName = '医疗机构制剂价格告知报告.docx';
          let objectUrl = URL.createObjectURL(new Blob([res.data]));
          const link = document.createElement('a');
          link.download = decodeURI(fileName);
          link.href = objectUrl;
          link.click();
          this.listLoading = false;
          this.$baseMessage("导出成功！", 'success')
        })
      },
      deleteFile(file) {
      //console.log(file);
        let arr = this.form.fileInfoList.filter(item => file.id !== item.id);
        this.$set(this.form, 'fileInfoList', arr);
        // deleteDetailFile({fileInfoId:file.id})
      },
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .upload-demo {
      position: relative;

      .el-upload__tip {
        position: absolute;
        top: 0;
        left: 90px;
        color: #e6a23c;
      }
    }
  }

  .file-list-item {
    display: flex;
    justify-content: space-between;
    line-height: 30px;
    vertical-align: center;
    cursor: pointer;
    /*width: 350px;*/
    padding: 0 5px;
    .file-item-name {
      overflow: hidden;
      width: 310px;
      padding-left: 4px;
      text-overflow: ellipsis;
      transition: color 0.3s;
      white-space: nowrap;
    }

    .file-item-delete {
      display: none;
      line-height: 30px;
      vertical-align: center;
      background-color: #f5f7fa;
    }

    .file-item-check {
      color: #13ce66;
      line-height: 30px;
      vertical-align: center;
    }

    &:hover {
      background-color: #f5f7fa;

      .file-item-delete {
        display: block;
      }

      .file-item-check {
        display: none;
      }
    }
  }
</style>
