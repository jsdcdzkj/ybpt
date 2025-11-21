<template>
  <div>
    <el-dialog v-if="dialogFormVisible" title="上传数据" :visible.sync="dialogFormVisible" width="600px" @close="close"
      append-to-body class="dialogs" :close-on-click-modal="false" v-loading="loading" element-loading-text="上传中······"
      element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
      <el-scrollbar style="height: 30vh">
        <el-form ref="uploadForm" :model="uploadForm" label-width="140px" :rules="rules">
          <!-- <el-row> -->
          <el-col :span="23">
            <el-form-item label="年份" prop="year">
              <el-date-picker v-model="uploadForm.year" type="year" placeholder="请选择年份" value-format="yyyy"
                :picker-options="pickerOptions" style="width: 300px" @blur="blurYear"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="23">
            <el-form-item label="月份" prop="month">
              <el-select v-model="uploadForm.month" clearable style="width: 300px" placeholder="请选择月份"
                @clear="clearSelect" @change="blurMonth">
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
          <el-col :span="23">
            <el-form-item label="文档类型" prop="docType">
              <el-select v-model="uploadForm.docType" clearable style="width: 300px" placeholder="请选择文档类型"
                @change="blurDocType">
                <el-option v-for="item in docTypeList" :key="item.code" :label="item.info" :value="item.code">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :span="23" style="height: 80px;">
            <el-form-item label="上传文档:" prop="file">
              <el-upload class="upload-demo" ref="Upload" :auto-upload="false" action="" :on-change="UploadFile"
                accept=".xls,.xlsx" :on-remove="HandleRemove" :file-list="fileList">
                <el-button size="small" type="primary" icon="el-icon-upload">
                  点击上传
                </el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="23" v-if="!allowUpload">
            <span class="tixing">{{ testT }}</span>
            <!-- <div class="tixing">当前年月拨付数据已上传，不可再上传！</div> -->
          </el-col>
          <!-- </el-row> -->
        </el-form>
      </el-scrollbar>

      <span slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="downLoads">模板下载</el-button>
        <el-button type="primary" @click="handleCheck">确定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
import { getTempAddress, appropDocumentAuthList, appropDocumentValidate, appropDocumentSave } from '@/api/disburse'
import { fileURL } from "@/config/setting.config";
export default {
  name: 'Upload',
  data() {
    //文件校验
    var fileVar = (rule, value, callback) => {
      if (value == undefined) {
        // if (this.uploadForm.occurFile !== undefined) {
        //   this.$refs.uploadForm.validateField('occurFileVar');
        // }
        callback(new Error('请上传文件'))
      } else {
        callback()
      }
    }
    return {
      testT: '',
      fullscreenLoading: false,
      uploadForm: {
        year: '',
        month: '',
        docType: '',
        file: undefined,
      },
      docTypeList: [],
      allowUpload: true,
      fileList: [],
      rules: {
        year: [{ required: true, trigger: 'change', message: '请选择年份' }],
        month: [{ required: true, trigger: 'change', message: '请选择月份' }],
        docType: [{ required: true, trigger: 'change', message: '请选择文档类型' }],
        file: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: fileVar,
          },
        ],
      },
      title: '',
      dialogFormVisible: false,
      loading: false,
      pickerOptions: {
        disabledDate(time) {
          // return time.getTime() > Date.now();
          return (
            time.getFullYear() < 2024 ||
            time.getTime() > Date.now()
          );
        }
      },
    }
  },
  created() {
    this.getDocTypeList()
  },
  methods: {
    // 弹框显示
    async showDia(row) {
      this.dialogFormVisible = true
    },
    getDocTypeList() {
      appropDocumentAuthList().then((res) => {
        this.docTypeList = res.data
      })
    },
    // 模版下载
    downLoads() {
      getTempAddress().then((res) => {
        console.log(res);
        const link = document.createElement('a');
        // 设置a标签的href属性为文件的URL
        link.href = fileURL + res.data;
        // 设置下载文件名
        // link.setAttribute('fileName', '附件下载');
        // 触发a标签的点击事件
        document.body.appendChild(link);
        link.click();
        // 下载完成后移除a标签
        document.body.removeChild(link);
        // window.open(res.data)
      })
    },
    clearSelect() {
      this.allowUpload = true
    },
    //选择年份
    blurYear() {
      let obj = {
        year: this.uploadForm.year,
        month: this.uploadForm.month,
        docType: this.uploadForm.docType
      }
      if (this.uploadForm.year && this.uploadForm.month && this.uploadForm.docType) {
        appropDocumentValidate(obj).then((res) => {
          if (res.code == 0) {
            this.allowUpload = true
          } else {
            this.allowUpload = false
            this.testT = res.msg
          }
        })
      } else {
        this.allowUpload = true
      }
    },
    //选择月份
    blurMonth() {
      let obj = {
        year: this.uploadForm.year,
        month: this.uploadForm.month,
        docType: this.uploadForm.docType
      }
      if (this.uploadForm.year && this.uploadForm.month && this.uploadForm.docType) {
        appropDocumentValidate(obj).then((res) => {
          if (res.code == 0) {
            this.allowUpload = true
          } else {
            this.allowUpload = false
            this.testT = res.msg
          }
        })
      } else {
        this.allowUpload = true
        this.testT = ''
      }
    },
    //选择月份
    blurDocType() {
      let obj = {
        year: this.uploadForm.year,
        month: this.uploadForm.month,
        docType: this.uploadForm.docType
      }
      if (this.uploadForm.year && this.uploadForm.month && this.uploadForm.docType) {
        appropDocumentValidate(obj).then((res) => {
          if (res.code == 0) {
            this.allowUpload = true
          } else {
            this.allowUpload = false
            this.testT = res.msg
          }
        })
      } else {
        this.allowUpload = true
        this.testT = ''
      }
    },
    // 文件删除
    HandleRemove(file, fileList) {
      this.uploadForm.file = undefined
    },
    // 文件上传
    UploadFile(file) {
      this.fileList = [{ name: file.name }]
      this.uploadForm.file = file.raw
      this.$refs.uploadForm.validateField('file')
    },

    //点击确定上传
    handleCheck() {
      this.$refs['uploadForm'].validate((valid) => {
        if (valid) {
          if (this.allowUpload) {
            let formData = new FormData()
            formData.append('file', this.uploadForm.file)
            let param = {
              year: this.uploadForm.year,
              month: this.uploadForm.month,
              docType: this.uploadForm.docType,
            }
            let json = JSON.stringify(param)
            const blob = new Blob([json], { type: 'application/json' })
            formData.append('param', blob)
            const loading = this.$loading({
              lock: true,
              text: '上传中',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            appropDocumentSave(formData).then((res) => {
              if (res.data.code == 0) {
                loading.close();
                this.close()
              } else {
                setTimeout(() => {
                  loading.close();
                  this.$message.error(res.data.msg)
                }, 1000);
              }
            })
          }
        }
      })
    },

    //关闭弹框
    close() {
      this.dialogFormVisible = false
      this.uploadForm = {
        year: '',
        month: '',
        docType: '',
        file: undefined,
      }
      this.fileList = []
      this.allowUpload = true
      this.$emit('fetch-data')
    },

  },
}
</script>

<style lang="scss" scoped>
.tixing {
  color: red;
  text-align: center;
  font-size: 12px;
  position: absolute;
  top: 135px;
  left: 140px;
}

.tx1 {
  color: #87888a;
  font-size: 14px;
  position: relative;
  top: -5px;
  left: 40px;
}

.uploadDiv {
  margin: 40px;
  display: flex;
}

// ::v-deep .el-form-item--small .el-form-item__error {
//   position: absolute;
//   top: 60% !important;
// }

// ::v-deep .el-form-item--small .el-form-item__content {
//   height: 70px;
// }

// ::v-deep .el-dialog {
//   margin-top: 10vh !important;
// }

::v-deep .el-dialog__footer {
  border-top: 0px !important;
}

::v-deep .el-table__fixed {
  height: auto !important;
  bottom: 16px; // 不加这个会看不到表头
}
</style>
