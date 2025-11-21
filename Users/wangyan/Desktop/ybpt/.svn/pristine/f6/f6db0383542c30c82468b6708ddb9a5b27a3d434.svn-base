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
              <el-form-item label="追溯流水号" prop="traceTheSerialNumber">
                <el-input
                  v-model.trim="form.traceTheSerialNumber"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="定点医药机构编号" prop="fixmedins_code">
                <el-input
                  v-model.trim="form.fixmedins_code"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="定点医药机构名称" prop="fixmedins_name">
                <el-input
                  v-model.trim="form.fixmedins_name"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="医疗目录编码" prop="med_list_codg">
                <el-input
                  v-model.trim="form.med_list_codg"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="医药机构目录编码" prop="medins_list_codg">
                <el-input
                  v-model.trim="form.medins_list_codg"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item
                label="定点医药机构批次流水号"
                prop="batchSerialNumber"
                class="custemitem"
              >
                <el-input
                  v-model.trim="form.batchSerialNumber"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item
                label="定点医药机构商品销售流水号"
                prop="salesSerialNumber"
                class="custemitem"
              >
                <el-input
                  v-model.trim="form.salesSerialNumber"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="就诊ID" prop="mdtrt_id">
                <el-input
                  v-model.trim="form.mdtrt_id"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="就诊结算类型" prop="settlementType">
                <el-input
                  v-model.trim="form.settlementType"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="记账流水号" prop="account_seria_number">
                <el-input
                  v-model.trim="form.account_seria_number"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="药品追溯码" prop="drugTracingCode">
                <el-input
                  v-model.trim="form.drugTracingCode"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="经办人ID" prop="opter_id">
                <el-input
                  v-model.trim="form.opter_id"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="经办人姓名" prop="opter_name">
                <el-input
                  v-model.trim="form.opter_name"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="经办时间" prop="opt_time">
                <el-date-picker
                  v-model="form.opt_time"
                  type="datetime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  placeholder="选择月"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="经办机构编号" prop="optins_no">
                <el-input
                  v-model.trim="form.optins_no"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="统筹区编号" prop="poolarea_no">
                <el-input
                  v-model.trim="form.poolarea_no"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="拆零标志" prop="dismantlingMark">
                <el-input
                  v-model.trim="form.dismantlingMark"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="人员编号" prop="psn_no">
                <el-input
                  v-model.trim="form.psn_no"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="人员证件类型" prop="psn_cert_type">
                <el-input
                  v-model.trim="form.psn_cert_type"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="证件号码" prop="certno">
                <el-input
                  v-model.trim="form.certno"
                  autocomplete="off"
                  placeholder="请输入"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="人员姓名" prop="name">
                <el-input
                  v-model.trim="form.name"
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
  import { updateInfoAssessment } from '@/api_check/traceability'

  export default {
    name: 'examineEdit',
    components: {},
    data() {
      return {
        selectRows: '',
        dynamicTags: [],
        inputValue: '',
        form: {
          id: '',
          traceTheSerialNumber: '',
          fixmedins_code: '',
          fixmedins_name: '',
          med_list_codg: '',
          medins_list_codg: '',
          medins_list_name: '',
          batchSerialNumber: '',
          salesSerialNumber: '',
          mdtrt_id: '',
          settlementType: '',
          account_seria_number: '',
          drugTracingCode: '',
          opter_id: '',
          opter_name: '',
          opt_time: '',
          optins_no: '',
          poolarea_no: '',
          dismantlingMark: '',
          psn_no: '',
          psn_cert_type: '',
          certno: '',
          name: '',
        },
        rules: {},
        title: '',
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
                  message: '数据编辑成功！',
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
