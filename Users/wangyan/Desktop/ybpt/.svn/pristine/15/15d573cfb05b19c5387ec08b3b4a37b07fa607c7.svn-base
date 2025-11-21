<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="730px"
    @close="close"
    :close-on-click-modal="false"
  >
    <el-form
      ref="form"
      :model="form"
      :rules="rules"
      :label-width="formLabelWidth"
    >
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="协议年份" prop="year">
            <el-date-picker
              v-model="form.year"
              type="year"
              value-format="yyyy"
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
                ( 附件大小上限：5.0MB )
              </span>
            </el-upload>
          </el-form-item>
        </el-col>
      </el-row>
      <el-input v-model="form.file_id" type="hidden" />
      <el-input v-model="form.net_grade_id" type="hidden"></el-input>
      <el-input v-model="form.category_id" type="hidden"></el-input>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="submitForm('form')" :loading="loading">
        {{ loading ? '确定中 ...' : '确 定' }}
      </el-button>
      <el-button @click="cancelForm">关 闭</el-button>
    </div>
  </el-dialog>
</template>
<script>
import {
  geAgreementtList,
  selectNetTagAgreementByLogin,
  editXygl,
  saveAgreement,
} from '@/api_net/tagAgreement'
import { editSupp, getFileInfo, uploadFile } from '@/api_net/netTagSupp'
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
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
      form: {
        net_grade_id: '',
        title: '',
        category_id: '',
        party_a_name: '',
        party_a_code: '',
        party_a_address: '',
        party_b_name: '',
        party_b_code: '',
        party_b_address: '',
        year: '2022',
        fileId: '',
        file_id: '',

        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
      },
      rules: {
        year: [{ required: true, message: '请填写年份', trigger: 'blur' }],
        file_id: [{ required: true, message: '请上传文件', trigger: 'blur' }],
      },
      formLabelWidth: '140px',
      timer: null,
      dialogFormVisible: false,
    }
  },
  created() {},
  mounted() {},
  methods: {
    //生成当前年份
    getYear() {
      var date = new Date()
      var year = date.getFullYear()
      return year
    },
    showDia(row) {
      this.form = {
        net_grade_id: '',
        title: '',
        category_id: '',
        party_a_name: '',
        party_a_code: '',
        party_a_address: '',
        party_b_name: '',
        party_b_code: '',
        party_b_address: '',
        year: '',
        fileId: '',
        file_id: '',
      }
      this.fileList = []
      if (!row) {
        this.title = '新增'
      } else {
        this.title = '协议管理'
        selectNetTagAgreementByLogin().then((res) => {
          let user = res.data.user
          let medicalDept = res.data.medicalDept
          row.party_a_name = user.org_name
          if (row.category_id == 1 && null != medicalDept) {
            row.party_a_address = medicalDept.addr
          }

          this.form = Object.assign({}, row)

          console.log(this.form)
          if (
            this.form.file_id != null &&
            this.form.file_id != '' &&
            this.form.file_id != undefined
          ) {
            this.getFile(this.form.file_id)
          }
          this.form.net_grade_id = row.net_grade_id + ''
          this.form.category_id = row.category_id + ''
          this.form.year = this.getYear() + ''
        })
      }
      this.dialogFormVisible = true
      // this.dialog = true
    },

    //修改获取已经上传得文件
    async getFile(id) {
      var x = {}
      x.id = id
      const { data } = await getFileInfo(x)
      this.fileList = data
    },

    handleRemovePicture(file, fileList) {
      this.fileList = fileList
    },
    async onChangePicture(file, fileList) {
      this.loading = true
      this.fileList = fileList
      let fd = new FormData()
      this.fileList.forEach((item) => {
        //文件信息中raw才是真的文件
        fd.append('file', item.raw)
      })
      var result = await uploadFile(fd)

      this.form.file_id = result.data.data
      this.loading = false
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialogFormVisible = false
      // this.$emit("update:visible", false);
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      // this.$emit('fetch-data')
      this.fileList = {}
      this.close()
    },
    handleClose(done) {
      if (this.loading) {
        return
      }
      this.$confirm('确定要提交吗？')
        .then((_) => {
          this.submitForm()
          this.loading = true
          this.timer = setTimeout(() => {
            done()
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false
            }, 400)
          }, 2000)
        })
        .catch((_) => {})
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
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

    submitForm(formName) {
      this.$confirm('确定要提交吗？').then((_) => {
        // 设置遮罩层
        this.loading = true
        this.$refs[formName].validate((valid) => {
          if (valid) {
            editXygl(this.form)
              .then((res) => {
                this.loading = false
                this.$baseMessage('保存成功', 'success')
                this.fileList = []
                this.$emit('fetch-data')
                this.close()
              })
              .catch((err) => {
                this.loading = false
              })
          } else {
            this.loading = false
            return false
          }
        })
      })
    },
  },
}
</script>
<style lang="scss" scoped>
.ml-1 {
  margin-left: 16px;
  color: #999;
}
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
