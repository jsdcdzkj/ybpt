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
      <el-form-item label="选择文件" prop="fileList">
        <el-upload
            class="upload-demo"
            ref="upload"
            action=""
            :show-file-list="true"
            :before-upload="beforeUpload"
            :on-change="fileChange"
            :on-remove="fileRemove"
            :file-list="form.fileList"
            accept=".png,.jpg,.bmp"
            :auto-upload="false"
            list-type="picture"
        >
          <div slot="tip" class="el-upload__tip">仅支持.jpg、.png和.bmp文件，且不超过10M</div>
          <el-button size="small" type="primary">点击上传</el-button>

        </el-upload>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {uploadLicence} from '@/api/sbApply'

export default {
  name: 'photo',
  data() {
    return {
      form: {
        fileList: [] // 上传的文件列表
      },
      title: '',
      dialogFormVisible: false,
      rules: {
        fileList: [{required: true, trigger: 'blur', message: '请选择文件！'}],
      },
    }
  },
  created() {
  },
  methods: {
    showEdit(row) {
      this.title = '上传医疗许可证'
      this.dialogFormVisible = true
    },
    beforeUpload(file) {
      const isLt1M = file.size / 1024 / 1024 < 10
      if (isLt1M) {
        this.file = file
        return true
      }
      this.$message.warning('请上传不超过10M的文件.')
      return false
    },
    fileRemove() {
      this.form.fileList = []
    },
    fileChange(file, fileList) {
      if (fileList.length > 0) {
        this.form.fileList = [fileList[fileList.length - 1]]
      }
    },

    uploadFile() {
      this.$refs.upload.submit()
    },
    close() {
      this.$refs['form'].resetFields()
      this.dialogFormVisible = false
    },
    async save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          let fd = new FormData()
          this.form.fileList.forEach((item) => {
            //文件信息中raw才是真的文件
            fd.append('file', item.raw)
          })
          const res = await uploadLicence(fd)
          if (res.data.code == "0") {
            this.$baseMessage("操作成功", 'success')
            this.$emit('fetch-data')
            this.close()
          } else {
            this.$baseMessage("请尝试刷新页面", 'error')
          }

        } else {
          return false
        }
      })
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
</style>
