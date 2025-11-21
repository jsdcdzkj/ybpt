<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="55%"
      @close="close"
      :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="150px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="耗材国家编码" prop="mcs_code">
            <el-input maxlength="27" minlength="27"
                      v-model.trim="form.mcs_code"
                      autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="省平台挂网产品编号" prop="product_num">
            <el-input
                v-model.trim="form.product_num"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="注册备案产品名称" prop="reg_fil_prod_name">
            <el-input
                v-model.trim="form.reg_fil_prod_name"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="注册备案号" prop="reg_fil_no">
            <el-input
                v-model.trim="form.reg_fil_no"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="材质" prop="matl">
            <el-input
                v-model.trim="form.matl"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="特征" prop="characteristics">
            <el-input
                v-model.trim="form.characteristics"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="规格" prop="spec">
            <el-input
                v-model.trim="form.spec"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="型号" prop="mol">
            <el-input
                v-model.trim="form.mol"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="耗材企业" prop="mcs_entp">
            <el-input
                v-model.trim="form.mcs_entp"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="申诉说明" prop="verify_reason">
            <el-input
                v-model.trim="form.verify_msg"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" :loading="loading" @click="save">{{ loading ? '确定中 ...' : '确定' }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {addConsumables, consumables9001, updateConsumables} from '@/api/consumables.js'

export default {
  name: 'UserManagementEdit',
  data() {
    return {
      loading: false,
      form: {
        id: "",
        // mcs_code: "C01020600102001049370000044",
        mcs_code: "",
        reg_fil_prod_name: "",
        reg_fil_no: "",
        matl: "",
        characteristics: "",
        spec: "",
        mol: "",
        mcs_entp: "",
        verify_msg: "",
        // product_num: "310902006-1",
        product_num: "",
      },
      admdvs: [],
      rules: {
        mcs_code: [
          {required: true, trigger: 'blur', message: '请输入耗材国家编码'},
          {required: true, trigger: 'blur', pattern: /^.{27}$/, message: '耗材国家编码为27位'},
        ],
        product_num: [{required: true, trigger: 'blur', message: '请输入省平台挂网产品编号'},],
        reg_fil_prod_name: [{required: true, trigger: 'blur', message: '请输入注册备案产品名称'},],
        reg_fil_no: [{required: true, trigger: 'blur', message: '请输入注册备案号'},],
        matl: [{required: true, trigger: 'blur', message: '请输入材质'},],
        characteristics: [{required: true, trigger: 'blur', message: '请输入特征'},],
        spec: [{required: true, trigger: 'blur', message: '请输入规格'},],
        mol: [{required: true, trigger: 'blur', message: '请输入型号'},],
        mcs_entp: [{required: true, trigger: 'blur', message: '请输入耗材企业'},],
      },
      title: '',
      dialogFormVisible: false,
      isDisabled: false,
    }
  },
  created() {

  },
  methods: {
    showDia(row) {
      console.log(row)
      if (!row) {
        this.title = '添加';
        this.isDisabled = false;
      } else {
        this.form.id = row.id;
        this.form.mcs_code = row.mcs_code;
        this.form.reg_fil_prod_name = row.reg_fil_prod_name;
        this.form.reg_fil_no = row.reg_fil_no;
        this.form.matl = row.matl;
        this.form.characteristics = row.characteristics;
        this.form.spec = row.spec;
        this.form.mol = row.mol;
        this.form.mcs_entp = row.mcs_entp;
        this.form.product_num = row.product_num;
        this.title = '编辑';
        this.isDisabled = true;
      }
      this.dialogFormVisible = true
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      if (this.loading) {
        return
      }
      var that = this;
      that.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.loading = true
          // 动画关闭需要一定的时间
          setTimeout(() => {
            this.loading = false
          }, 400)
          if (that.form.id != '') {
            updateConsumables(that.form).then((res) => {
              if (res.code == 0) {
                that.$emit('fetch-data')
              } else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          } else {
            consumables9001(this.form).then((res) => {
              if (res.code == 0) {
                let data = JSON.parse(res.data);
                if (data != undefined && data != '' && data.length != 0) {
                  if (data[0].APPCODE == 1) {
                    addConsumables(that.form).then((res) => {
                      if (res.code == 0) {
                        that.$emit('fetch-data')
                      } else {
                        that.$baseMessage(res.msg, 'error')
                      }
                    })
                  } else {
                    that.$baseMessage(data[0].ERRINFO, 'error')
                  }
                } else {
                  that.$baseMessage("已存在或数据不正确，请重新填写提交！", 'error')
                }
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
