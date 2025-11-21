<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="700px"
    @closeChildDialog="close"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="130px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="国家编码">
            <el-input
              v-model.trim="form.fixmedins_code"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="医保编码">
            <el-input
              v-model.trim="form.medins_mgtcode"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="机构名称">
            <el-input
              v-model.trim="form.fixmedins_name"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="医保信用等级">
            <el-input
              v-model.trim="form.cred_lv_name"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="机构类型">
            <el-input
              v-model.trim="form.medins_type_name"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="社会统一信用代码">
            <el-input
              v-model.trim="form.uscc"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="执业许可证">
            <el-input
              v-model.trim="form.license"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="法定代表人">
            <el-input
              v-model.trim="form.legrep_name"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="地址">
            <el-input
              v-model.trim="form.address"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="所有制形式">
            <el-select
              v-model="form.ownership"
              placeholder="请选择"
              :disabled="true"
            >
              <el-option value="1" label="公立"></el-option>
              <el-option value="2" label="非公立"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="经营性质">
            <el-select
              v-model="form.manage_quality"
              placeholder="请选择"
              :disabled="true"
            >
              <el-option value="1" label="营利性"></el-option>
              <el-option value="2" label="民办非营利"></el-option>
              <el-option value="3" label="政府非营利"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="属地">
            <el-select
              v-model="form.possession"
              placeholder="请选择"
              :disabled="true"
            >
              <el-option value="320312" label="徐州市医保中心"></el-option>
              <el-option value="320312" label="铜山区"></el-option>
              <el-option value="320305" label="贾汪区"></el-option>
              <el-option value="320321" label="丰县"></el-option>
              <el-option value="320321" label="丰县"></el-option>
              <el-option value="320322" label="沛县"></el-option>
              <el-option value="320324" label="睢宁县"></el-option>
              <el-option value="320382" label="邳州市"></el-option>
              <el-option value="320381" label="新沂市"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="所属街道">
            <el-input
              v-model.trim="form.possession_street"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="联系人">
            <el-input
              v-model.trim="form.legrep_person"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="联系电话">
            <el-input
              v-model.trim="form.legrep_mobile"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="签署日期" prop="signDate">
            <el-date-picker
              v-model="form.signDate"
              type="date"
              placeholder="选择签署日期"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading">
        {{ loading ? '确定中 ...' : '确 定' }}
      </el-button>

      <el-button
        type="danger"
        @click="maintain"
        :disabled="loading"
        v-show="hasMaintained"
      >
        保 存
      </el-button>
    </div>
    <views ref="views" @fetch-data="fetchData"></views>
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </el-dialog>
</template>

<script>
import {
  getOrganizationInfo,
  generatePDF,
  orgInfoMaintain,
  checkMaintained,
} from '@/api_net/netTagMechanism'
import { regionDataPlus, CodeToText } from 'element-china-area-data'
import Views from './shensu'
import Edit from './shenqing'

export default {
  name: 'wqsqEdit',
  components: { Views, Edit },
  data() {
    return {
      loading: false,
      options: regionDataPlus,
      hasMaintained: true,
      form: {
        fixmedins_code: '',
        fixmedins_name: '',
        cred_lv_name: '',
        tel: '',
        addr: '',
        drug_biz_lic_no: '',
        legrep_name: '',
        biz_way: '',
        biznat: '',
        uscc: '',
        pdf_id: '',
        signDate: '',
      },
      rules: {
        username: [
          { required: true, trigger: 'blur', message: '请输入用户名' },
        ],
        signDate: [
          { required: true, trigger: 'blur', message: '请选择签署日期' },
        ],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {},
  methods: {
    showDia(row) {
      this.checkMaintained()
      if (!row) {
        this.title = '网签申请管理'
        this.fetchData()
      } else {
        this.title = '网签申请管理'
        // this.form = Object.assign({}, row)
        this.fetchData()
      }
      this.dialogFormVisible = true
    },
    async fetchData() {
      const { data } = await getOrganizationInfo()
      console.log(data)
      this.form = data
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    shensu() {
      this.$refs['views'].showDia()
    },
    async save() {
      this.loading = true
      if (
        this.form.signDate == '' ||
        undefined == this.form.signDate ||
        null == this.form.signDate
      ) {
        this.$baseMessage('请选择签署日期', 'error')
        this.loading = false
      } else {
        const data = await generatePDF(this.form)
        if (data.code == 0) {
          this.$baseMessage('成功', 'success')
          this.form.pdf_id = data.data
          this.$refs['edit'].showDia(this.form)
          this.loading = false
        } else {
          this.$baseMessage(data.msg, 'error')
          this.loading = false
        }
      }
    },
    maintain() {
      this.$baseConfirm('机构信息只能维护一次，确认提交？', null, async () => {
        orgInfoMaintain(this.form).then((res) => {
          if (res.code == 0) {
            this.$baseMessage('保存成功', 'success')
            this.hasMaintained = false
          } else {
            this.$baseMessage(res.msg, 'error')
          }
        })
      })
    },
    checkMaintained() {
      checkMaintained().then((res) => {
        if (res.code == 0) {
          if (res.data == '1') {
            this.hasMaintained = false
          }
        }
      })
    },
  },
}
</script>
