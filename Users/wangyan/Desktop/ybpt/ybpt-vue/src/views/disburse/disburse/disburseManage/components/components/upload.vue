<template>
  <div>
    <el-dialog v-if="dialogFormVisible" title="生成数据" :visible.sync="dialogFormVisible" width="600px" @close="close"
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
            <span class="tx1">请按要求添加以下文档</span>
            <!-- <div class="tixing">当前年月拨付数据已上传，不可再上传！</div> -->
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表一:发生数 :" prop="occurFileId">
              <el-button size="small" type="primary" icon="el-icon-upload" @click="handleAddFile('28')">
                <span v-if="uploadForm.occurFileId">重新添加</span>
                <span v-else>添加文档</span>
              </el-button>
              <el-input v-model.trim="uploadForm.occurFileId" style="display: none;" />
              <div v-if="uploadForm.occurFileId">{{ fileForm.occurData.docName }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表二:应结算 :" prop="settleFileId">
              <el-button size="small" type="primary" icon="el-icon-upload" @click="handleAddFile('29')">
                <span v-if="uploadForm.settleFileId">重新添加</span>
                <span v-else>添加文档</span>
              </el-button>
              <el-input v-model.trim="uploadForm.settleFileId" style="display: none;" />
              <div v-if="uploadForm.settleFileId">{{ fileForm.settleData.docName }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表三:居民大病保险实际支付统计表 :" prop="jmdbbxsjzfFileId">
              <el-button size="small" type="primary" icon="el-icon-upload" @click="handleAddFile('30')">
                <span v-if="uploadForm.jmdbbxsjzfFileId">重新添加</span>
                <span v-else>添加文档</span>
              </el-button>
              <el-input v-model.trim="uploadForm.jmdbbxsjzfFileId" style="display: none;" />
              <div v-if="uploadForm.jmdbbxsjzfFileId">{{ fileForm.jmdbbxsjzfData.docName }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表四:月结算表 :" prop="monthSettleFileId">
              <el-button size="small" type="primary" icon="el-icon-upload" @click="handleAddFile('31')">
                <span v-if="uploadForm.monthSettleFileId">重新添加</span>
                <span v-else>添加文档</span>
              </el-button>
              <el-input v-model.trim="uploadForm.monthSettleFileId" style="display: none;" />
              <div v-if="uploadForm.monthSettleFileId">{{ fileForm.monthSettleData.docName }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="23" style="height: 80px;">
            <el-form-item label="表五:DRG表 :" prop="drgFileId">
              <el-button size="small" type="primary" icon="el-icon-upload" @click="handleAddFile('32')">
                <span v-if="uploadForm.drgFileId">重新添加</span>
                <span v-else>添加文档</span>
              </el-button>
              <el-input v-model.trim="uploadForm.drgFileId" style="display: none;" />
              <div v-if="uploadForm.drgFileId">{{ fileForm.drgData.docName }}</div>
            </el-form-item>
          </el-col>
          <!-- </el-row> -->
        </el-form>
      </el-scrollbar>

      <span slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="preview">预 览</el-button>
      </span>
    </el-dialog>



    <el-dialog v-if="dialogFileVisible" title="添加拨付数据文档" :visible.sync="dialogFileVisible" width="1000px"
      @close="handleCancle" append-to-body class="dialogs" :close-on-click-modal="false">
      <el-table ref="listTable" stripe :data="filelist">
        <el-table-column show-overflow-tooltip label="年份" align="center" prop="year">
        </el-table-column>
        <el-table-column show-overflow-tooltip label="月份" align="center" prop="month">
          <template #default="{ row }">
            <span>{{ row.month }}月</span>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="docType" label="文档类型" align="center"></el-table-column>
        <el-table-column show-overflow-tooltip prop="docName" label="文档名称" align="center"></el-table-column>
        <el-table-column show-overflow-tooltip prop="createTime" label="创建时间" align="center"></el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleCancle">取 消</el-button>
        <el-button type="primary" @click="handleCheck">确定</el-button>
      </span>
    </el-dialog>
    <uploadInfo ref="dataInfo" @closes="close"></uploadInfo>
  </div>
</template>

<script>
import { approValidUpload, approNoticePreview, getTempAddress, appropDocumentDocList, appropDocumentPreview } from '@/api/disburse'
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
        // if (this.uploadForm.occurFileId !== undefined) {
        //   this.$refs.uploadForm.validateField('occurFileVar');
        // }
        callback(new Error('请添加文件'))
      } else {
        callback()
      }
    }

    var settleFileVar = (rule, value, callback) => {
      if (value == undefined) {
        callback(new Error('请添加文件'))
      } else {
        // if (this.uploadForm.settleFileId !== undefined) {
        //   this.$refs.uploadForm.validateField('settleFileVar');
        // }
        callback()
      }
    }

    var jmdFileVar = (rule, value, callback) => {
      if (value == undefined) {
        callback(new Error('请添加文件'))
      } else {
        // if (this.uploadForm.jmdbbxsjzfFileId !== undefined) {
        //   this.$refs.uploadForm.validateField('jmdFileVar');
        // }
        callback()
      }
    }
    var monthFileVar = (rule, value, callback) => {
      if (value == undefined) {
        callback(new Error('请添加文件'))
      } else {
        // if (this.uploadForm.monthSettleFileId !== undefined) {
        //   this.$refs.uploadForm.validateField('monthFileVar');
        // }
        callback()
      }
    }
    var drgFileVar = (rule, value, callback) => {
      if (value == undefined) {
        callback(new Error('请添加文件'))
      } else {
        // if (this.uploadForm.drgFileId !== undefined) {
        //   this.$refs.uploadForm.validateField('drgFileId');
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
        occurFileId: undefined,
        settleFileId: undefined,
        jmdbbxsjzfFileId: undefined,
        monthSettleFileId: undefined,
        drgFileId: undefined,
      },
      fileForm: {},
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
        occurFileId: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: occurFileVar,
          },
        ],
        settleFileId: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: settleFileVar,
          },
        ],
        jmdbbxsjzfFileId: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: jmdFileVar,
          },
        ],
        monthSettleFileId: [
          {
            required: true,
            trigger: ['change', 'blur'],
            validator: monthFileVar,
          },
        ],
        drgFileId: [
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
      dialogFileVisible: false,
      selectFileType: '',
      filelist: [],
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
  created() { },
  methods: {
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
      this.uploadForm.occurFileId = undefined
      this.uploadForm.settleFileId = undefined
      this.uploadForm.jmdbbxsjzfFileId = undefined
      this.uploadForm.monthSettleFileId = undefined
      this.uploadForm.drgFileId = undefined
      this.fileForm = {}
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
      this.uploadForm.occurFileId = undefined
      this.uploadForm.settleFileId = undefined
      this.uploadForm.jmdbbxsjzfFileId = undefined
      this.uploadForm.monthSettleFileId = undefined
      this.uploadForm.drgFileId = undefined
      this.fileForm = {}
    },

    //添加文档弹框
    handleAddFile(docType) {
      this.selectFileType = docType
      this.getDocList()
    },
    getDocList() {
      if (!this.uploadForm.year) {
        this.$message.error('请先选择年份')
        return
      }
      if (!this.uploadForm.month) {
        this.$message.error('请先选择月份')
        return
      }
      let obj = {
        year: this.uploadForm.year,
        month: this.uploadForm.month,
        docType: this.selectFileType
      }

      appropDocumentDocList(obj).then((res) => {
        this.filelist = res.data
        this.dialogFileVisible = true
      })
    },
    //关闭添加文档
    handleCancle() {
      this.dialogFileVisible = false
      this.selectFileType = ''
      this.filelist = []
    },
    //确认文档
    handleCheck() {
      if (!this.filelist || this.filelist.length == 0) {
        this.$message.error('暂未上传相关拨付数据文档，请先上传文档')
        return
      }
      if (this.selectFileType == '28') {
        this.uploadForm.occurFileId = this.filelist[0].id
        this.fileForm.occurData = this.filelist[0]
      }
      if (this.selectFileType == '29') {
        this.uploadForm.settleFileId = this.filelist[0].id
        this.fileForm.settleData = this.filelist[0]
      }
      if (this.selectFileType == '30') {
        this.uploadForm.jmdbbxsjzfFileId = this.filelist[0].id
        this.fileForm.jmdbbxsjzfData = this.filelist[0]
      }
      if (this.selectFileType == '31') {
        this.uploadForm.monthSettleFileId = this.filelist[0].id
        this.fileForm.monthSettleData = this.filelist[0]
      }
      if (this.selectFileType == '32') {
        this.uploadForm.drgFileId = this.filelist[0].id
        this.fileForm.drgData = this.filelist[0]
      }
      this.dialogFileVisible = false
    },

    preview() {
      this.$refs['uploadForm'].validate((valid) => {
        if (valid) {
          if (this.allowUpload) {
            const loading = this.$loading({
              lock: true,
              text: '数据量较大，计算处理需要些时间，请耐心等待！',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            appropDocumentPreview(this.uploadForm).then((res) => {
              if (res.code == 0) {
                loading.close();
                this.$refs['dataInfo'].showDia(res.data, this.fileForm)
              } else {
                setTimeout(() => {
                  loading.close();
                  this.$message.error(res.msg)
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
        occurFileId: undefined,
        settleFileId: undefined,
        jmdbbxsjzfFileId: undefined,
        monthSettleFileId: undefined,
        drgFileId: undefined,
      }
      this.occurFileList = []
      this.settleFileList = []
      this.jmdFileList = []
      this.monthSetFileList = []
      this.orgFileList = []
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
