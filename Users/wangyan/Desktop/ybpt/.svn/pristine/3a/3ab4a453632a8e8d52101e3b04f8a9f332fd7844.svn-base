<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="730px"
    @close="close"
    :close-on-click-modal="false"
  >
    <el-form
      ref="queryForm"
      :model="queryForm"
      :rules="rules"
      :label-width="formLabelWidth"
    >
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="名称" prop="title">
            <el-input v-model="queryForm.title" />
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="协议失效日期" prop="invalid_date">
            <el-date-picker
              v-model="queryForm.invalid_date"
              type="date"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="文件上传" prop="file_id">
            <el-upload
              ref="uploadPicture"
              class="upload-demo"
              action=""
              accept=".docx"
              :auto-upload="false"
              :on-remove="handleRemovePicture"
              :before-remove="beforeRemove"
              :on-change="onChangePicture"
              :multiple="true"
              :limit="1"
              :on-exceed="handleExceed"
              :file-list="fileList"
            >
              <el-button size="small" icon="el-icon-upload2" type="primary">
                文件上传
              </el-button>
              <span slot="tip" class="el-upload__tip ml-1">
                ( 请上传.docx文件 )
              </span>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm('form')" :loading="loading">
        {{ loading ? '确定中 ...' : '确 定' }}
      </el-button>
      <el-button @click="cancelForm">关 闭</el-button>
    </div>
  </el-dialog>
</template>
<style lang="scss" scoped>
.file-main {
  padding: 20px 30px 0;
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
    padding-left: 70px;
    .box {
      flex: 1;
      line-height: 36px;
      font-size: 14px;
    }
  }
}
</style>
<script>
import { editSupp, uploadFile, getFileInfo } from '@/api_net/netTagSupp'
import { selectNetTagAgreementByLogin } from '@/api_net/tagAgreement'

export default {
  name: 'edit',
  components: {},
  data() {
    return {
      fileList: [],
      title: '',
      dialog: false,
      loading: false,

      queryForm: {
        id: '',
        title: '',
        invalid_date: '',
        medicalName: '',
        file_id: '',
        address: '',
      },
      // form: {
      //   id: '',
      //   title: '',
      //   invalid_date: '',
      //   medicalName: '',
      //   file_id: '',
      // },
      rules: {
        title: [{ required: true, message: '请填写标题', trigger: 'blur' }],
        file_id: [{ required: true, message: '请上传文件', trigger: 'blur' }],
        invalid_date: [
          { required: true, message: '请选择写过期时间', trigger: 'blur' },
        ],
      },
      formLabelWidth: '140px',
      timer: null,
      dialogFormVisible: false,
    }
  },
  created() {},
  mounted() {},
  methods: {
    onChangePicture(file, fileList) {
      this.loading = true
      this.fileList = fileList

      let fd = new FormData()
      this.fileList.forEach((item) => {
        //文件信息中raw才是真的文件
        fd.append('file', item.raw)
      })
      uploadFile(fd).then((result) => {
        console.log(result.data.data)
        this.queryForm.file_id = result.data.data
        this.loading = false
      })
    },
    handleRemovePicture(file, fileList) {
      this.fileList = fileList
    },

    showDia(row) {
      if (!row) {
        this.title = '新增'
      } else {
        this.title = '修改'
        selectNetTagAgreementByLogin().then((res) => {
          console.log(res)
          let user = res.data.user

          row.medicalName = user.org_name
          let medicalDept = res.data.medicalDept
          row.party_a_name = user.org_name
          this.queryForm = Object.assign({}, row)
          if (
            this.queryForm.file_id != null &&
            this.queryForm.file_id != '' &&
            this.queryForm.file_id != undefined
          ) {
            this.getFile(this.queryForm.file_id)
          }
        })
      }

      this.dialogFormVisible = true
    },

    //修改页面获取已经上传得文件
    async getFile(id) {
      var x = {}
      x.id = id
      const { data } = await getFileInfo(x)
      this.fileList = data
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.fileList = []
      this.dialogFormVisible = false

      // this.$emit("update:visible", false);
    },
    save() {
      this.$baseMessage('保存成功', 'success')
      this.queryForm = {}
      this.fileList = []
      this.$emit('fetch-data')
      this.loading = false
      this.close()
    },
    handleClose(done) {
      if (this.loading) {
        return
      }
      if (
        this.queryForm.title == null ||
        this.queryForm.title == undefined ||
        this.queryForm.title == ''
      ) {
        this.$message.warning('请填写必填信息')
        return
      }
      if (
        this.queryForm.invalid_date == null ||
        this.queryForm.invalid_date == undefined ||
        this.queryForm.invalid_date == ''
      ) {
        this.$message.warning('请填写必填信息')
        return
      }
      if (
        this.queryForm.file_id == null ||
        this.queryForm.file_id == undefined ||
        this.queryForm.file_id == ''
      ) {
        this.$message.warning('请填写必填信息')
        return
      }
      this.$confirm('确定要提交吗？')
        .then((_) => {
          this.saveOK()
          this.loading = true
          this.timer = setTimeout(() => {
            done()
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false
            }, 400)
          }, 2000)
          this.save()
        })
        .catch((_) => {})
    },

    async saveOK() {
      const { data } = await editSupp(this.queryForm)
      console.log(data)
      this.$emit('fetch-data')
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      this.fileList = []
      this.queryForm = {}
      this.close()
      this.$emit('closeChildDialog')
      clearTimeout(this.timer)
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      )
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    submitForm() {
      // 设置遮罩层
      this.loading = true

      if (
        this.queryForm.title == null ||
        this.queryForm.title == undefined ||
        this.queryForm.title == ''
      ) {
        this.loading = false
        this.$message.warning('请填写必填信息')
        return
      }
      if (
        this.queryForm.invalid_date == null ||
        this.queryForm.invalid_date == undefined ||
        this.queryForm.invalid_date == ''
      ) {
        this.loading = false
        this.$message.warning('请填写必填信息')
        return
      }
      if (
        this.queryForm.file_id == null ||
        this.queryForm.file_id == undefined ||
        this.queryForm.file_id == ''
      ) {
        this.loading = false
        this.$message.warning('请填写必填信息')
        return
      }
      this.$confirm('确定要提交吗？')
        .then((_) => {
          this.saveOK()
          this.loading = true
          this.save()
        })
        .catch((_) => {})
    },
  },
}
</script>
<style lang="scss" scoped>
.ml-1 {
  margin-left: 16px;
  color: #999;
}
</style>
