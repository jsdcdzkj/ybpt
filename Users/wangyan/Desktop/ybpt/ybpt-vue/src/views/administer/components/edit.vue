<template>
  <!-- <el-dialog :title="title" :visible.sync="dialogFormVisible" width="60%" @close="close" :close-on-click-modal="false"> -->
  <el-drawer
    :title="title"
    :before-close="handleClose"
    :visible.sync="dialogFormVisible"
    direction="rtl"
    custom-class="box_drawer"
    size="60%"
    ref="drawer"
    :close-on-click-modal="true"
    append-to-body
  >
    <div class="drawer_content">
      <div class="drawer_main">
        <el-form ref="form" :model="form" :rules="rules" label-width="140px">
          <el-row :gutter="20">
            <!-- 分类 -->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="topic">基本信息</div>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="类型" prop="type">
                <el-input
                  v-model.trim="form.type"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="国家医保信息编码" prop="fixmedins_code">
                <el-input
                  v-model.trim="form.fixmedins_code"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="名称" prop="fixmedins_name">
                <el-input
                  v-model.trim="form.fixmedins_name"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="级别" prop="aggrement_lv">
                <el-input
                  v-model.trim="form.aggrement_lv"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="地区" prop="admdvs">
                <el-input
                  v-model.trim="form.admdvs"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="月份" prop="month">
                <el-date-picker
                  v-model="form.month"
                  type="month"
                  value-format="yyyy-MM"
                  placeholder="选择月"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <!-- 分类 -->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="topic">医保码结算率</div>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item
                label="医保码结算率"
                prop="medicareCodeSettlementRate"
              >
                <el-input
                  v-model.trim="form.medicareCodeSettlementRate"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="奖惩金额（万元）" prop="medicareCodeMoney">
                <el-input
                  v-model.trim="form.medicareCodeMoney"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 分类 -->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="topic">医保码全流程应用</div>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="全流程应用情况" prop="applyCondition">
                <el-input
                  v-model.trim="form.applyCondition"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="奖惩金额（万元）">
                <el-input
                  v-model.trim="form.applyMoney"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 分类 -->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="topic">移动支付应用</div>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="奖惩金额（万元）" prop="mobilePaymentMoney">
                <el-input
                  v-model.trim="form.mobilePaymentMoney"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 分类 -->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="topic">电子处方流转平台接入</div>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item
                label="奖惩金额（万元）"
                prop="electronicPrescriptionMoney"
              >
                <el-input
                  v-model.trim="form.electronicPrescriptionMoney"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 分类 -->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="topic">国家编码贯标（非标率）</div>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="开单医师" prop="nationBillingPhysician">
                <el-input
                  v-model.trim="form.nationBillingPhysician"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="手术操作" prop="nationOperation">
                <el-input
                  v-model.trim="form.nationOperation"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="西药中成药" prop="westernMedicine">
                <el-input
                  v-model.trim="form.westernMedicine"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="中药饮片" prop="chinesePiece">
                <el-input
                  v-model.trim="form.chinesePiece"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="自制剂" prop="selfPreparation">
                <el-input
                  v-model.trim="form.selfPreparation"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="医用耗材" prop="medicalConsumables">
                <el-input
                  v-model.trim="form.medicalConsumables"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="医疗服务项目" prop="medicalServiceItem">
                <el-input
                  v-model.trim="form.medicalServiceItem"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="奖惩金额（万元）" prop="nationMoney">
                <el-input
                  v-model.trim="form.nationMoney"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 分类 -->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="topic">药品追溯码采集</div>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="奖惩金额（万元）" prop="traceableCodeMoney">
                <el-input
                  v-model.trim="form.traceableCodeMoney"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 分类 -->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="topic">其他接口改造和数据上传任务</div>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="奖惩金额（万元）" prop="uploadPortMoney">
                <el-input
                  v-model.trim="form.uploadPortMoney"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- 分类 -->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
              <div class="topic">工作试点示范</div>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="奖惩金额（万元）" prop="workPilotMoney">
                <el-input
                  v-model.trim="form.workPilotMoney"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="备注" prop="remark">
                <el-input
                  v-model.trim="form.remark"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div class="drawer_footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="save" :loading="loading">
          {{ loading ? '确定中 ...' : '确 定' }}
        </el-button>
      </div>
    </div>
    <!-- <div slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
        </div> -->
  </el-drawer>
  <!-- </el-dialog> -->
</template>

<script>
  import { updateInfoAssessment } from '@/api_check/examine'

  export default {
    name: 'examineEdit',
    components: {},
    data() {
      return {
        selectRows: '',
        seloptions: [
          {
            value: 'j',
            label: '医疗机构',
          },
          {
            value: 'd',
            label: '零售药店',
          },
        ],
        dynamicTags: [],
        inputValue: '',
        form: {
          id: '',
          fixmedins_code: '',
          fixmedins_name: '',
          aggrement_lv: '',
          admdvs: '',
          month: '',
          medicareCodeSettlementRate: '',
          medicareCodeMoney: '',
          applyCondition: '',
          applyMoney: '',
          mobilePaymentMoney: '',
          electronicPrescriptionMoney: '',
          nationBillingPhysician: '',
          nationOperation: '',
          westernMedicine: '',
          chinesePiece: '',
          selfPreparation: '',
          medicalConsumables: '',
          medicalServiceItem: '',
          nationMoney: '',
          traceableCodeMoney: '',
          uploadPortMoney: '',
          workPilotMoney: '',
          remark: '',
        },
        rules: {
          type: [{ required: true, trigger: 'blur', message: '请输入类型' }],
          fixmedins_code: [
            {
              required: true,
              trigger: 'blur',
              message: '请输入国家医保信息编码',
            },
          ],
          fixmedins_name: [
            { required: true, trigger: 'blur', message: '请输入名称' },
          ],
          aggrement_lv: [
            { required: true, trigger: 'blur', message: '请输入级别' },
          ],
          admdvs: [{ required: true, trigger: 'blur', message: '请输入地区' }],
          month: [{ required: true, trigger: 'blur', message: '请选择年月' }],
        },
        title: 'www',
        dialogFormVisible: false,
        loading: false,
      }
    },
    created() {},
    methods: {
      showDia(row) {
        this.loading = false
        this.form = JSON.parse(JSON.stringify(row))
        this.title = '编辑'
        this.dialogFormVisible = true
      },
      handleClose() {
        let that = this
        if (this.loading) {
          return
        }
        this.$confirm('确定要提交吗？')
          .then((_) => {
            this.loading = true
            this.timer = setTimeout(() => {
              that.save()
              // 动画关闭需要一定的时间
              setTimeout(() => {
                this.loading = false
              }, 400)
            }, 2000)
          })
          .catch((_) => {})
      },

      async close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
        this.loading = false
      },

      save() {
        let that = this
        if (that.loading) {
          return
        }
        that.$refs['form'].validate(async (valid) => {
          if (valid) {
            that.loading = true
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false
            }, 1000)
            console.log(that.form)
            console.log(JSON.stringify(that.form))
            updateInfoAssessment(that.form).then((res) => {
              if (res.code == 0) {
                that.$message({
                  message: '考核数据编辑成功！',
                  type: 'success',
                })
                this.$emit('fetch-data')
                that.close()
              } else {
                this.$message.error(res.msg)
              }
            })
          } else {
            return false
          }
        })
      },
    },
  }
</script>
<style scoped lang="scss">
  .topic {
    margin-bottom: 12px;
    height: 16px;
    line-height: 16px;
    position: relative;
    font-size: 16px;
    font-weight: bold;
    padding-left: 10px;
    color: #000;
    &::before {
      content: '';
      display: inline-block;
      position: absolute;
      left: -2px;
      top: 0px;
      width: 4px;
      height: 16px;
      background-color: #1890ff;
    }
  }
</style>
