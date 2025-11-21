<template>
  <el-drawer
    :title="title"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    :before-close="handleClose"
    custom-class="box_drawer"
    size="1060px"
    ref="drawer"
    :close-on-click-modal="true"
    append-to-body
  >
    <!-- pdf 内嵌 -->
    <iframe
      :src="PDFsrc"
      frameborder="0"
      style="width: 100%; height: 100%"
    ></iframe>
    <div class="drawer_content">
      <div class="drawer_footer">
        <el-button type="primary" @click="examineStatus('1')">签章</el-button>
        <el-button @click="examineStatus('4')" type="danger">撤 销</el-button>
        <el-button @click="cancelForm">关 闭</el-button>
      </div>
      <views ref="views" @fetch-data="form.name"></views>
    </div>
    <el-dialog
      title="撤销理由"
      :visible.sync="remarkVisible"
      append-to-body
      width="700px"
    >
      <el-form ref="form" :model="form" label-width="100px">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="理由" prop="regeditType">
              <el-input
                type="textarea"
                v-model="form.approval_opinion"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeRemark">取 消</el-button>
        <el-button type="primary" @click="reject">确 定</el-button>
      </div>
    </el-dialog>
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
import Views from './shensu'
import { wqshDetails, updateStatus } from '@/api_net/netTagMechanism'
export default {
  name: 'edit',
  components: { Views },
  data() {
    return {
      remark: '',
      remarkVisible: false,
      PDFsrc: '',
      title: '',
      dialog: false,
      loading: false,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        id: '',
      },
      form: {
        id: '',
        status: '',
        contract_id: '',
      },
      formLabelWidth: '100px',
      timer: null,
    }
  },
  mounted() {},
  methods: {
    showDia(row) {
      if (!row) {
        this.title = '新增'
      } else {
        this.title = '查看'
        this.form = Object.assign({}, row)
        this.form.id = row
        this.details(row)
      }
      this.dialog = true
    },
    bohui() {
      this.$refs['views'].showDia()
    },
    close() {
      this.dialog = false
      this.$emit('update:visible', false)
    },
    closeRemark() {
      this.remarkVisible = false
    },
    handleClose(done) {},
    async examineStatus(status) {
      this.form.status = status
      //审批通过|驳回方法
      if (status == 1) {
        const { data, code, msg } = await updateStatus(this.form)
        if (code == 0) {
          this.close()
          this.$emit('fetch-data')
        } else {
          this.$baseMessage(msg, 'error')
        }
      } else if (status == 4) {
        this.remarkVisible = true
      }
    },
    async reject() {
      this.form.status = 4
      const { data, code, msg } = await updateStatus(this.form)
      if (code == 0) {
        this.$baseMessage('驳回成功', 'success')
        this.remarkVisible = false
      } else {
        this.$baseMessage(msg, 'error')
      }
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      this.close()
      this.$emit('closeChildDialog')
      clearTimeout(this.timer)
    },
    async details(id) {
      const { data } = await wqshDetails({ id: id })
      this.PDFsrc = data.review_url //合同查看地址url
      this.form.contract_id = data.contract_id //合同id
    },
  },
}
</script>