<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="500px"
      @close="close"
      :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="国家编码" prop="org_code">
            {{ form.org_code }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="机构名称" prop="org_name">
            {{ form.org_name }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="协议等级" prop="aggrement_lv">
            {{ form.aggrement_lv }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="经营性质" prop="natures">
            {{ form.natures }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="同级限价" prop="limit_price">
            {{ form.limit_price }}
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="申报价格" prop="price">
            <el-input
                type="number"
                min="0"
                v-model.trim="form.price"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {dentalInsertOrUpdate, getFixmedinsB} from '@/api/sbApply.js'

export default {
  name: 'UserManagementEdit',
  data() {
    return {
      form: {
        org_code: "",
        org_name: "",
        aggrement_lv: "",
        natures: "",
        limit_price: "",
        price: "",
      },
      rules: {
        price: [
          {required: true, trigger: 'blur', message: '请输入申报价格'},
        ],
      },
      fixmedinsB: '',
      title: '',
      dialogFormVisible: false,
      id: "",
      loading: false
    }
  },
  created() {

  },
  methods: {
    async showDia(row) {
      await getFixmedinsB().then((res) => {
        this.fixmedinsB = res.data
      })
      if (this.fixmedinsB.biznet != '1' && this.fixmedinsB.biznet != '2' && this.fixmedinsB.biznet != '3') {
        this.$baseMessage('未维护经营性质，请联系医保中心进行维护', 'error')
        return
      }
      if (this.fixmedinsB.aggrement_lv != '1'
          && this.fixmedinsB.aggrement_lv != '2'
          && this.fixmedinsB.aggrement_lv != '3'
          && this.fixmedinsB.aggrement_lv != '3'
          && this.fixmedinsB.aggrement_lv != '4'
          && this.fixmedinsB.aggrement_lv != '5'
          && this.fixmedinsB.aggrement_lv != '6'
          && this.fixmedinsB.aggrement_lv != '9') {
        this.$baseMessage('未维护协议等级，请联系医保中心进行维护', 'error')
        return
      }
      let limit_price = '0'
      switch (this.fixmedinsB.biznet) {
        case '1':
          if (this.fixmedinsB.aggrement_lv == 3) {
            limit_price = '4171'
          } else {
            limit_price = '3735'
          }
          break
        case '2':
          if (this.fixmedinsB.aggrement_lv == 3) {
            limit_price = '4171'
          } else {
            limit_price = '3735'
          }
          break
        case '3':
          if (this.fixmedinsB.aggrement_lv == 3) {
            limit_price = '4300'
          } else {
            limit_price = '3850'
          }
          break
      }
      const rowNew = {
        org_code: this.fixmedinsB.fixmedins_code,
        org_name: this.fixmedinsB.fixmedins_name,
        aggrement_lv: this.aggrement_lvFormat(),
        natures: this.biznet_typeFormat(),
        limit_price: limit_price,
        status: '0',
        drugs: this.form.drugs,
        medical_type: this.form.medical_type
      }
      console.log(rowNew)

      this.loading = false
      if (!row) {
        this.title = '单颗常规种植牙医疗服务价格全流程费用告知';
        this.id = "";
        this.form = Object.assign({}, rowNew)
      } else {
        this.id = row.id;
        this.title = '单颗常规种植牙医疗服务价格全流程费用告知';
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    aggrement_lvFormat() {
      let statusW = ''
      switch (this.fixmedinsB.aggrement_lv) {
        case '1':
          statusW = '一级'
          break
        case '2':
          statusW = '二级'
          break
        case '3':
          statusW = '三级'
          break
        case '4':
          statusW = 'A级'
          break
        case '5':
          statusW = 'B级'
          break
        case '6':
          statusW = 'C级'
          break
        case '9':
          statusW = '未定级'
          break
      }
      return statusW
    },
    biznet_typeFormat() {
      let statusW = ''
      switch (this.fixmedinsB.biznet) {
        case '1':
          statusW = '营利性'
          break
        case '2':
          statusW = '民办非营利'
          break
        case '3':
          statusW = '政府非营利'
          break
      }
      return statusW
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      var that = this;
      if (that.loading) {
        return
      }
      that.$refs['form'].validate(async (valid) => {
        if (valid) {
          that.loading = true;
          // 动画关闭需要一定的时间
          setTimeout(() => {
            this.loading = false
          }, 1000)
          if (parseInt(this.form.price) > parseInt(this.form.limit_price)) {
            this.$baseMessage('申报价格不能大于限定价格', 'error')
            return
          }
          if (that.id != '') {
            dentalInsertOrUpdate(that.form).then((res) => {
              if (res.code == 0) {
                that.$emit('fetch-data')
              } else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          } else {
            dentalInsertOrUpdate(that.form).then((res) => {
              if (res.code == 0) {
                that.$emit('fetch-data')
              } else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          }
          // that.$baseMessage(msg, 'success')

          that.close()
        } else {
          return false
        }
      })
    },
  },
}
</script>
