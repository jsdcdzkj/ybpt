<template>
  <el-drawer
    :title="title"
    before-close="handleClose"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="1060px"
    ref="drawer"
    :close-on-click-modal="true"
    append-to-body
  >
    <iframe
      :src="PDFsrc"
      frameborder="0"
      style="width: 100%; height: 100%"
    ></iframe>
    <div class="drawer_content">
      <div class="drawer_footer">
        <el-button @click="cancelForm" type="primary">关 闭</el-button>
        <el-button
          type="primary"
          @click="save"
          :loading="loading"
          :disabled="loading"
          v-show="isShow"
        >
          {{ loading ? '申请中 ...' : '申 请' }}
        </el-button>
      </div>
    </div>
  </el-drawer>
</template>
<style lang="scss" scoped>
.file-main {
  padding: 0 30px;

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

    .box {
      flex: 1;
      line-height: 36px;
      font-size: 16px;
    }
  }
}
</style>
<script>
import { getFileUrl, validateApply } from '@/api_net/netTagSupp'
import {
  getOrganizationInfo,
  generatePDF,
  insertNetTagAgreement,
} from '@/api_net/netTagMechanism'

export default {
  name: 'edit',
  components: {},
  data() {
    return {
      isShow: true,
      PDFsrc: '',
      title: '',
      dialog: false,
      loading: false,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
      form: {
        pdf_id: '',
      },
      formLabelWidth: '100px',
      timer: null,
    }
  },
  mounted() {},
  methods: {
    showDia(row) {
      this.title = '新增'
      this.form.pdf_id = row.pdf_id
      this.form.signDate = row.signDate
      this.getById(row.pdf_id)
      this.dialog = true
      this.validateApply()
    },
    async getById(val) {
      var x = {}
      x.id = val
      const { data } = await getFileUrl(x)
      console.log(data)
      this.PDFsrc = data
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialog = false
      this.$emit('update:visible', false)
    },
    // 验证是否可以申请
    validateApply() {
      validateApply().then((res) => {
        console.log(res)
        if (res.code === 0) {
          this.isShow = true
        } else {
          this.$message.error(res.msg)
          this.isShow = false
        }
      })
    },
    async save() {
      this.loading = true

      const data = await insertNetTagAgreement(this.form)
      if (data.code == 0) {
        this.$baseMessage('成功', 'success')
        this.close()
        const loading = this.$loading({
          lock: true,
          text: '请在签章完成后刷新本页面',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)',
        })
        window.open(data.data)
      } else {
        this.$baseMessage(data.msg, 'error')
      }
      this.$emit('fetch-data')
    },
    handleClose(done) {
      if (this.loading) {
        return
      }
      // this.$confirm('确定要申请吗？')
      //     .then((_) => {
      //         this.loading = true;
      //         this.timer = setTimeout(() => {
      //             done();
      //             // 动画关闭需要一定的时间
      //             setTimeout(() => {
      //                 this.loading = false
      //             }, 400)
      //         }, 2000)
      //     })
      //     .catch((_) => {
      //     })
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      this.close()
      this.$emit('closeChildDialog')
      clearTimeout(this.timer)
    },
  },
}
</script>