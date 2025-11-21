<template>

  <el-drawer ref="drawer" 
  :title="title" 
  :visible.sync="dialogFormVisible" 
  :with-header="false"           
  :close-on-click-modal="false"       
  custom-class="box_drawer" 
  @close="close" 
  direction="rtl" 
  size="70%">
      <div class="drawer_content">

        <div class="drawer_main">
          <h5 class="inform-title">{{ title }}</h5>
          <div class="box_card base-info">
            <el-form ref="form" :model="form" :rules="rules" label-width="100px">
              <el-row :gutter="20">
                
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单位名称" prop="org_name">
                    {{ form.org_name }}
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单位医保编码" prop="org_code">
                    {{ form.org_code }}
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经营性质" prop="natures">
                    {{ form.natures }}
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="药品类别" prop="type">
                    <el-select v-model="form.type" style="width: 100%;">
                      <el-option label="西药" value="西药"></el-option>
                      <el-option label="中成药" value="中成药"></el-option>
                      <el-option label="中药饮片" value="中药饮片"></el-option>
                      <el-option label="中药配方颗粒" value="中药配方颗粒"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="加价率" prop="premium">
                    <el-input type="number" v-model="form.premium">
                      <template slot="append">%</template>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="备注" prop="memo">
                    <el-input
                        v-model.trim="form.memo"
                        type="textarea"
                        :rows="5"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>

          </div>
        </div>
      <div class="drawer_footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
      </div>
    </div>

  </el-drawer>
</template>

<script>
import {getFixmedinsB} from '@/api/sbApply.js'
import {insert} from '@/api/sbApplyDrug.js'

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
        premium: "",
        memo: "",
      },
      rules: {
        type: [
          {required: true, trigger: 'blur', message: '请选择药品类别'},
        ],
        premium: [
          {required: true, trigger: 'blur', message: '请输入加价率'},
        ],
        memo: [
          {required: true, trigger: 'blur', message: '请输入备注'},
        ],
      },
      fixmedinsB: '',
      title: '',
      dialogFormVisible: false,
      id: "",
      loading: false,
      timer: null
    }
  },
  created() {

  },
  methods: {
    async showDia(row) {
      await getFixmedinsB().then((res) => {
        this.fixmedinsB = res.data
      })
      if (this.fixmedinsB.biznet == '3') {
        this.$baseMessage('政府非营利，无法添加药品加价率告知手续', 'error')
        return
      }
      if (this.fixmedinsB.biznet != '1' && this.fixmedinsB.biznet != '2' && this.fixmedinsB.biznet != '3') {
        this.$baseMessage('未维护经营性质，请联系医保中心进行维护', 'error')
        return
      }
      // if (this.fixmedinsB.aggrement_lv != '1'
      //     && this.fixmedinsB.aggrement_lv != '2'
      //     && this.fixmedinsB.aggrement_lv != '3'
      //     && this.fixmedinsB.aggrement_lv != '3'
      //     && this.fixmedinsB.aggrement_lv != '4'
      //     && this.fixmedinsB.aggrement_lv != '5'
      //     && this.fixmedinsB.aggrement_lv != '6'
      //     && this.fixmedinsB.aggrement_lv != '9') {
      //   this.$baseMessage('未维护协议等级，请联系医保中心进行维护', 'error')
      //   return
      // }
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
        this.title = '非公立医疗机构药品加价率告知明细表';
        this.id = "";
        this.form = Object.assign({}, rowNew)
      } else {
        this.id = row.id;
        this.title = '非公立医疗机构药品加价率告知明细表';
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
    async save() {
      var that = this;
      if (that.loading) {
        return
      }
      that.$refs['form'].validate(async (valid) => {
        if (valid) {
          that.loading = true;
          // 动画关闭需要一定的时间
          // setTimeout(() => {
          //   this.loading = false
          // }, 1000)

          const loading2 = this.$loading({
            lock: true,
            text: '提交中，请稍等...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });

          if (parseInt(this.form.price) > parseInt(this.form.limit_price)) {
            this.$baseMessage('申报价格不能大于限定价格', 'error')
            return
          }
          if (that.id != '') {
            insert(that.form).then((res) => {
              if (res.code == 0) {
                // that.$emit('fetch-data')
                this.timer = setTimeout(async () => {
                  loading2.close();
                  this.loading = false
                  that.close()
                  this.$emit('fetch-data')
                  this.$baseMessage('操作成功', 'success')
                }, 1000)
                
              } else {
                // that.$baseMessage(res.msg, 'error')
                this.timer = setTimeout(async () => {
                  loading2.close();
                  this.loading = false
                  that.close()
                  this.$emit('fetch-data')
                  this.$baseMessage(res.msg, 'error')
                }, 500)
              }
            })
          } else {
            insert(that.form).then((res) => {
              if (res.code == 0) {
                // that.$emit('fetch-data')
                this.timer = setTimeout(async () => {
                  loading2.close();
                  this.loading = false
                  that.close()
                  this.$emit('fetch-data')
                  this.$baseMessage('操作成功', 'success')
                }, 1000)
              } else {
                // that.$baseMessage(res.msg, 'error')
                this.timer = setTimeout(async () => {
                  loading2.close();
                  this.loading = false
                  that.close()
                  this.$emit('fetch-data')
                  this.$baseMessage(res.msg, 'error')
                }, 500)
              }
            })
          }
          // that.$baseMessage(msg, 'success')
          

        } else {
          return false
        }
      })
    },
  },
}
</script>
<style scoped lang="scss">
.inform-title {
  width: 90%;
  margin: 0 auto 20px;
  padding: 10px 0;
  line-height: 24px;
  font-size: 18px;
  font-weight: bold;
  text-align: center;
}
</style>