import { editSupp, uploadFile, getFileInfo } from '@/api_net/netTagSupp'
import { importData } from '@/api/fileDelivery'
export default {
  data() {
    return {
      fileList: [],
      fileIds: [],
    }
  },
  methods: {
    handleRemovePicture(file, fileList) {
      this.fileList = fileList
    },
    beforeRemove(file, fileList) {
      this.form.files.splice(
        this.form.files.findIndex((item) => item.uid === file.uid),
        1
      )

      if (this.$refs.form) this.$refs.form.validateField('files')
      return true
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      )
    },
    onChangePicture(file, fileList) {
      this.loading = true
      this.form.files = fileList
      if (this.$refs.form) this.$refs.form.validateField('files')
    },
  },
}
