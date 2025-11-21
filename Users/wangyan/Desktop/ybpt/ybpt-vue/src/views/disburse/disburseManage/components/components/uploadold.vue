<template>
  <div>
    <el-dialog v-if="dialogFormVisible" title="上传数据" :visible.sync="dialogFormVisible" width="600px" @close="close"
      append-to-body class="dialogs" :close-on-click-modal="false" v-loading="loading" element-loading-text="上传中······"
      element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)">
      <el-scrollbar style="height: 60vh">
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
          <el-col :span="23" v-if="!allowUpload">
            <span class="tixing">{{ testT }}</span>
            <!-- <div class="tixing">当前年月拨付数据已上传，不可再上传！</div> -->
          </el-col>

          <el-col :span="23">
            <span class="tx1">请按要求上传以下文档（仅支持上传excel文件）</span>
            <!-- <div class="tixing">当前年月拨付数据已上传，不可再上传！</div> -->
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表一:发生数 :" prop="occurFile">
              <el-upload class="upload-demo" ref="Upload" :auto-upload="false" action="" :on-change="occurUploadFile"
                accept=".xls,.xlsx" :on-remove="occurHandleRemove" :file-list="occurFileList">
                <el-button size="small" type="primary" icon="el-icon-upload">
                  点击上传
                </el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表二:应结算 :" prop="settleFile">
              <el-upload class="upload-demo" ref="settleUpload" :auto-upload="false" action="" accept=".xls,.xlsx"
                :on-change="settleUploadFile" :on-remove="settleHandleRemove" :file-list="settleFileList">
                <el-button size="small" type="primary" icon="el-icon-upload">
                  点击上传
                </el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表三:居民大病保险实际支付统计表 :" prop="jmdbbxsjzfFile">
              <el-upload class="upload-demo" ref="jmdUpload" :auto-upload="false" action="" :on-change="jmdUploadFile"
                accept=".xls,.xlsx" :on-remove="jmdHandleRemove" :file-list="jmdFileList">
                <el-button size="small" type="primary" icon="el-icon-upload">
                  点击上传
                </el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表四:月结算表 :" prop="monthSettleFile">
              <el-upload class="upload-demo" ref="jmdbbxsjzfFile" :auto-upload="false" action="" accept=".xls,.xlsx"
                :on-change="monthSetUploadFile" :on-remove="monthSetHandleRemove" :file-list="monthSetFileList">
                <el-button size="small" type="primary" icon="el-icon-upload">
                  点击上传
                </el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表五:DRG表 :" prop="drgFile">
              <el-upload class="upload-demo" ref="drgUpload" :auto-upload="false" action="" :on-change="drgUploadFile"
                accept=".xls,.xlsx" :on-remove="drgHandleRemove" :file-list="orgFileList">
                <el-button size="small" type="primary" icon="el-icon-upload">
                  点击上传
                </el-button>
              </el-upload>
            </el-form-item>
          </el-col>
          <!-- </el-row> -->
        </el-form>
      </el-scrollbar>

      <span slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="downLoads">模版下载</el-button>
        <el-button type="primary" @click="preview">预 览</el-button>
      </span>
    </el-dialog>
    <uploadInfo ref="dataInfo" @closes="close"></uploadInfo>
  </div>
</template>

<script>
import { approValidUpload, approNoticePreview, getTempAddress } from '@/api/disburse'
import { importData, oneInfo } from '@/api/qs'
import { regionDataPlus } from 'element-china-area-data'
import { getDicts } from '@/api/dictManagement'
import { getFixmedinsB } from '@/api/sbApply'
import uploadInfo from './uploadInfo'
export default {
  name: 'UserManagementEdit',
  components: { uploadInfo },
  data() {
    var occurFileVar = (rule, value, callback) => {
      if (value == undefined) {
        // if (this.uploadForm.occurFile !== undefined) {
        //   this.$refs.uploadForm.validateField('occurFileVar');
        // }
        callback(new Error('请上传文件'))
      } else {
        callback()
      }
    }

    var settleFileVar = (rule, value, callback) => {
      if (value == undefined) {
        callback(new Error('请上传文件'))
      } else {
        // if (this.uploadForm.settleFile !== undefined) {
        //   this.$refs.uploadForm.validateField('settleFileVar');
        // }
        callback()
      }
    }

    var jmdFileVar = (rule, value, callback) => {
      if (value == undefined) {
        callback(new Error('请上传文件'))
      } else {
        // if (this.uploadForm.jmdbbxsjzfFile !== undefined) {
        //   this.$refs.uploadForm.validateField('jmdFileVar');
        // }
        callback()
      }
    }
    var monthFileVar = (rule, value, callback) => {
      if (value == undefined) {
        callback(new Error('请上传文件'))
      } else {
        // if (this.uploadForm.monthSettleFile !== undefined) {
        //   this.$refs.uploadForm.validateField('monthFileVar');
        // }
        callback()
      }
    }
    var drgFileVar = (rule, value, callback) => {
      if (value == undefined) {
        callback(new Error('请上传文件'))
      } else {
        // if (this.uploadForm.drgFile !== undefined) {
        //   this.$refs.uploadForm.validateField('drgFile');
        // }
        callback()
      }
    }
    return {
      testT: '',
      fullscreenLoading: false,
      uploadForm: {
        year: '',
        month: '',
        occurFile: undefined,
        settleFile: undefined,
        jmdbbxsjzfFile: undefined,
        monthSettleFile: undefined,
        drgFile: undefined,
      },
      allowUpload: true,
      occurFileList: [],
      settleFileList: [],
      jmdFileList: [],
      monthSetFileList: [],
      orgFileList: [],

      options: regionDataPlus,
      admdvs: null,
      fixmedinsB: '',
      form: {
        admdvs: '',
        year: '',
      },
      rules: {
        year: [{ required: true, trigger: 'change', message: '请选择年份' }],
        month: [{ required: true, trigger: 'change', message: '请选择月份' }],
        occurFile: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: occurFileVar,
          },
        ],
        settleFile: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: settleFileVar,
          },
        ],
        jmdbbxsjzfFile: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: jmdFileVar,
          },
        ],
        monthSettleFile: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: monthFileVar,
          },
        ],
        drgFile: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: drgFileVar,
          },
        ],
      },
      title: '',
      dialogFormVisible: false,
      loading: false,
      fileList: [],
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
    }
  },
  created() { },
  methods: {
    // 模版下载
    downLoads() {
      getTempAddress().then((res) => {
        console.log(res);
        const link = document.createElement('a');
        // 设置a标签的href属性为文件的URL
        link.href = res.data;
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
    blurYear() {
      let obj = {
        year: this.uploadForm.year,
        month: this.uploadForm.month,
      }
      if (this.uploadForm.year && this.uploadForm.month) {
        approValidUpload(obj).then((res) => {
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
    blurMonth() {
      let obj = {
        year: this.uploadForm.year,
        month: this.uploadForm.month,
      }
      if (this.uploadForm.year && this.uploadForm.month) {
        approValidUpload(obj).then((res) => {
          if (res.code == 0) {
            this.allowUpload = true
          } else {
            this.allowUpload = false
            this.testT = res.msg
          }
        })
      }
    },
    // 发生数
    occurHandleRemove(file, fileList) {
      this.uploadForm.occurFile = undefined
    },

    occurUploadFile(file) {
      this.occurFileList = [{ name: file.name }]
      this.uploadForm.occurFile = file.raw
      this.$refs.uploadForm.validateField('occurFile')
    },

    // occurBeforeUploadFile(file) {
    //   console.log(this.uploadForm.occurFile,'this.uploadForm.occurFile');
    //   if (this.uploadForm.occurFile != undefined) {
    //     const { uploadFiles } = this.$refs.Upload
    //     console.log(this.$refs.Upload,'this.$refs.Upload');
    //     if (uploadFiles && uploadFiles.length) {
    //       uploadFiles.splice(0, 1)
    //     }
    //   }
    // },
    // 应结算
    settleHandleRemove(file, fileList) {
      // this.$refs.settleUpload.clearFiles()
      this.uploadForm.settleFile = undefined
    },

    settleUploadFile(file) {
      this.settleFileList = [{ name: file.name }]
      this.uploadForm.settleFile = file.raw
      this.$refs.uploadForm.validateField('settleFile')
    },

    // 居民大病保险实际支付统计表
    jmdHandleRemove(file, fileList) {
      // this.$refs.jmdUpload.clearFiles()
      this.uploadForm.jmdbbxsjzfFile = undefined
    },

    jmdUploadFile(file) {
      this.jmdFileList = [{ name: file.name }]
      this.uploadForm.jmdbbxsjzfFile = file.raw
      this.$refs.uploadForm.validateField('jmdbbxsjzfFile')
    },

    // 月结算表
    monthSetHandleRemove(file, fileList) {
      // this.$refs.monthSetUpload.clearFiles()
      this.uploadForm.monthSettleFile = undefined
    },

    monthSetUploadFile(file) {
      this.monthSetFileList = [{ name: file.name }]
      this.uploadForm.monthSettleFile = file.raw
      this.$refs.uploadForm.validateField('monthSettleFile')
    },

    // DRG表
    drgHandleRemove(file, fileList) {
      // this.$refs.drgUpload.clearFiles()
      this.uploadForm.drgFile = undefined
    },

    drgUploadFile(file) {
      this.orgFileList = [{ name: file.name }]
      this.uploadForm.drgFile = file.raw
      this.$refs.uploadForm.validateField('drgFile')
    },
    preview() {
      this.$refs['uploadForm'].validate((valid) => {
        if (valid) {
          if (this.allowUpload) {
            let formData = new FormData()
            formData.append('occurFile', this.uploadForm.occurFile)
            formData.append('settleFile', this.uploadForm.settleFile)
            formData.append('jmdbbxsjzfFile', this.uploadForm.jmdbbxsjzfFile)
            formData.append('monthSettleFile', this.uploadForm.monthSettleFile)
            formData.append('drgFile', this.uploadForm.drgFile)
            let param = {
              year: this.uploadForm.year,
              month: this.uploadForm.month,
            }
            let json = JSON.stringify(param)
            const blob = new Blob([json], { type: 'application/json' })
            formData.append('param', blob)
            const loading = this.$loading({
              lock: true,
              text: '预览加载中',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            approNoticePreview(formData).then((res) => {
              if (res.data.code == 0) {
                loading.close();
                this.$refs['dataInfo'].showDia(res.data.data, this.uploadForm)
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

    // ----------------------------------
    async showDia(row) {
      this.dialogFormVisible = true
    },
    close() {
      this.dialogFormVisible = false
      this.uploadForm = {
        year: '',
        month: '',
        occurFile: undefined,
        settleFile: undefined,
        jmdbbxsjzfFile: undefined,
        monthSettleFile: undefined,
        drgFile: undefined,
      }
      this.occurFileList = []
      this.settleFileList = []
      this.jmdFileList = []
      this.monthSetFileList = []
      this.orgFileList = []
      this.allowUpload = true
      this.$emit('fetch-data')
    },

    getAdmdvs() {
      getDicts({ type: 'admdvs-area' }).then((res) => {
        if (res.code == 0) {
          this.admdvs = res.data
        }
      })
    },
    handleSuccess() {
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
  top: 83px;
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
