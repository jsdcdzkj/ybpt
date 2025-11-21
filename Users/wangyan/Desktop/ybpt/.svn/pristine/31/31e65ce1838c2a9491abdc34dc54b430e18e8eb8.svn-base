<template>
  <el-drawer
    :title="title"
    :before-close="handleClose"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="80%"
    ref="drawer"
  >
    <div class="drawer_content">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <div class="box_card">
            <div class="box_header">
              <span>基本信息</span>
              <vab-icon
                :icon="['fas', 'angle-up']"
                v-if="isShow"
              ></vab-icon>
              <vab-icon
                :icon="['fas', 'angle-down']"
                v-else
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="姓名">
                    <el-input
                            v-model.trim="queryForm.psn_name"
                            clearable
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="身份证号">
                    <el-input
                      v-model.trim="queryForm.certno"
                      clearable
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="年度">
                    <el-input
                      v-model.trim="queryForm.year"
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20" v-if="isShow">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="险种类型" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.insutype"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗类别" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.med_type"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保区划">
                    <el-input
                            v-model.trim="queryForm.insu_admdvs"
                            disabled
                    />
                  </el-form-item>
                </el-col>

              </el-row>
              <el-row :gutter="20" v-if="isShow">

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点医药机构代码" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fixmedins_code"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点医药机构名称" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fixmedins_name"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医药机等级" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.hosp_lv"
                            disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20" v-if="isShow">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="支付地点类别" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.pay_loc"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="就医开始时间" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.begndate"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="就医结束时间">
                    <el-input
                            v-model.trim="queryForm.enddate"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="结算时间">
                    <el-input
                            v-model.trim="queryForm.setl_time"
                            disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>基金待遇信息</span>
              <vab-icon
                :icon="['fas', 'angle-up']"
                v-if="isShow1"
              ></vab-icon>
              <vab-icon
                :icon="['fas', 'angle-down']"
                v-else
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20" v-if="isShow1">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗费总额">
                    <el-input v-model.trim="queryForm.medfee_sumamt" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="全自费金额" class="custemitem">
                    <el-input v-model.trim="queryForm.fulamt_ownpay_amt" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="超限价自付金额" class="custemitem">
                    <el-input v-model.trim="queryForm.overlmt_selfpay" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="先行自付金额" class="custemitem">
                    <el-input v-model.trim="queryForm.preselfpay_amt" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="符合范围金额" class="custemitem">
                    <el-input v-model.trim="queryForm.inscp_amt" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="起付标准">
                    <el-input v-model.trim="queryForm.dedc_std" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="本次起付线" class="custemitem">
                    <el-input v-model.trim="queryForm.crt_dedc" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="实际支付起付线" class="custemitem">
                    <el-input v-model.trim="queryForm.act_pay_dedc" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="基本医疗基金支出" class="custemitem">
                    <el-input v-model.trim="queryForm.hifp_pay" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="基本医疗统筹支付比例" class="custemitem">
                    <el-input v-model.trim="queryForm.pool_prop_selfpay" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="公务员医疗补助基金支出" class="custemitem">
                    <el-input v-model.trim="queryForm.cvlserv_pay" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="大病补充医疗保险基金支出" class="custemitem">
                    <el-input v-model.trim="queryForm.hifmi_pay" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="大额医疗补助基金支出" class="custemitem">
                    <el-input v-model.trim="queryForm.hifob_pay" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="伤残人员医疗保障基金支出" class="custemitem">
                    <el-input v-model.trim="queryForm.hifdm_pay" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医疗救助基金支出" class="custemitem">
                    <el-input v-model.trim="queryForm.maf_pay" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="其他基金支出">
                    <el-input v-model.trim="queryForm.othfund_pay" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="基金支付总额">
                    <el-input v-model.trim="queryForm.fund_pay_sumamt" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="个人支付总额">
                    <el-input v-model.trim="queryForm.psn_pay" disabled />
                  </el-form-item>
                </el-col>

              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>其他信息</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="退费结算标志">
                    <el-input v-model.trim="queryForm.refd_setl_flag" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="创建人" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.crter_name"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办人" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.opter_name"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="经办时间" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.opt_time"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

              </el-row>
              <el-table
                :data="tableData"
                border
                stripe
                class="w"
                highlight-current-row
                height="300px"
              >
                <template slot="empty">
                  <el-empty :image-size="150"></el-empty>
                </template>
                <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
                </el-table-column>
                <el-table-column prop="fee_ocur_time" label="费用发生时间"></el-table-column>
                <el-table-column
                  prop="rx_drord_no"
                  label="处方/医嘱号"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  prop="hilist_code"
                  label="医保目录编码"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  prop="hilist_name"
                  label="医保目录名称"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  prop="medins_list_name"
                  label="医疗机构目录名称"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  prop="list_type"
                  label="目录类别"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  prop="med_chrgitm_type"
                  label="医疗收费项目类别"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column prop="pric" label="单价"></el-table-column>
                <el-table-column prop="cnt" label="数量"></el-table-column>
                <el-table-column prop="det_item_fee_sumamt" label="明细项目费用总额"></el-table-column>
                <el-table-column prop="fulamt_ownpay_amt" label="全自费金额"></el-table-column>
                <el-table-column prop="pric_uplmt_amt" label="定价上限金额"></el-table-column>
                <el-table-column prop="overlmt_selfpay" label="超限价自费费用"></el-table-column>
                <el-table-column prop="selfpay_prop" label="自付比例"></el-table-column>
                <el-table-column prop="preselfpay_amt" label="先行自付金额"></el-table-column>
                <el-table-column prop="reim_prop" label="报销比例"></el-table-column>
                <el-table-column prop="inscp_amt" label="符合范围金额"></el-table-column>
                <el-table-column prop="cvlserv_bedfee_amt" label="公务员床位费金额"></el-table-column>
                <el-table-column prop="medins_disc_amt" label="医院减免金额"></el-table-column>

              </el-table>
            </div>
          </div>
<!--          <div class="box_card">-->
<!--            <div class="box_header">-->
<!--              <span>代办人信息</span>-->
<!--              <vab-icon-->
<!--                :icon="['fas', 'angle-up']"-->
<!--                v-if="isShow2"-->
<!--                @click="moreQuery2"-->
<!--              ></vab-icon>-->
<!--              <vab-icon-->
<!--                :icon="['fas', 'angle-down']"-->
<!--                v-else-->
<!--                @click="moreQuery2"-->
<!--              ></vab-icon>-->
<!--            </div>-->
<!--            <div class="box_content">-->
<!--              <el-row :gutter="20" v-if="isShow2">-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="代办人姓名">-->
<!--                    <el-input-->
<!--                      v-model.trim="queryForm.username"-->
<!--                      disabled-->
<!--                    ></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="代办人证件类型" class="custemitem">-->
<!--                    <el-select v-model="queryForm.username" class="w" disabled>-->
<!--                      <el-option label="中心经办系统" value="0"></el-option>-->
<!--                    </el-select>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="代办人证件号码" class="custemitem">-->
<!--                    <el-input-->
<!--                      v-model.trim="queryForm.username"-->
<!--                      disabled-->
<!--                    ></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="代办人联系方式" class="custemitem">-->
<!--                    <el-input-->
<!--                      v-model.trim="queryForm.username"-->
<!--                      disabled-->
<!--                    ></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="代办人关系">-->
<!--                    <el-input-->
<!--                      v-model.trim="queryForm.username"-->
<!--                      disabled-->
<!--                    ></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                  <el-form-item label="代办人联系地址" class="custemitem">-->
<!--                    <el-input-->
<!--                      v-model.trim="queryForm.username"-->
<!--                      type="textarea"-->
<!--                      :rows="5"-->
<!--                      disabled-->
<!--                    ></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--              </el-row>-->
<!--            </div>-->
<!--          </div>-->
<!--          <div class="box_card">-->
<!--            <div class="box_header">-->
<!--              <span>经办信息</span>-->
<!--              <vab-icon-->
<!--                :icon="['fas', 'angle-up']"-->
<!--                v-if="isShow3"-->
<!--                @click="moreQuery3"-->
<!--              ></vab-icon>-->
<!--              <vab-icon-->
<!--                :icon="['fas', 'angle-down']"-->
<!--                v-else-->
<!--                @click="moreQuery3"-->
<!--              ></vab-icon>-->
<!--            </div>-->
<!--            <div class="box_content">-->
<!--              <el-row :gutter="20" v-if="isShow3">-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="审核人姓名">-->
<!--                    <el-input-->
<!--                      v-model.trim="queryForm.username"-->
<!--                      disabled-->
<!--                    ></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="审核时间">-->
<!--                    <el-date-picker-->
<!--                      v-model="queryForm.username"-->
<!--                      type="date"-->
<!--                      disabled-->
<!--                    ></el-date-picker>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="经办人姓名">-->
<!--                    <el-input-->
<!--                      v-model.trim="queryForm.username"-->
<!--                      disabled-->
<!--                    ></el-input>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                  <el-form-item label="经办时间">-->
<!--                    <el-date-picker-->
<!--                      v-model="queryForm.username"-->
<!--                      type="date"-->
<!--                      disabled-->
<!--                    ></el-date-picker>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--              </el-row>-->
<!--            </div>-->
<!--          </div>-->
<!--          <div class="box_card">-->
<!--            <div class="box_header">-->
<!--              <span>附件信息</span>-->
<!--            </div>-->
<!--            <div class="box_content">-->
<!--              <el-row :gutter="20">-->
<!--                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                  <el-form-item label="" class="tsitem">-->
<!--                    <el-upload-->
<!--                      action="#"-->
<!--                      list-type="picture-card"-->
<!--                      :auto-upload="false"-->
<!--                      :file-list="fileList"-->
<!--                    >-->
<!--                      <i slot="default" class="el-icon-plus"></i>-->
<!--                      <div slot="file" slot-scope="{ file }">-->
<!--                        <img-->
<!--                          class="el-upload-list__item-thumbnail"-->
<!--                          :src="file.url"-->
<!--                          alt=""-->
<!--                        />-->
<!--                        <span class="el-upload-list__item-actions">-->
<!--                          <span-->
<!--                            class="el-upload-list__item-preview"-->
<!--                            @click="handlePictureCardPreview(file)"-->
<!--                          >-->
<!--                            <i class="el-icon-zoom-in"></i>-->
<!--                          </span>-->
<!--                          <span-->
<!--                            v-if="!disabled"-->
<!--                            class="el-upload-list__item-delete"-->
<!--                            @click="handleDownload(file)"-->
<!--                          >-->
<!--                            <i class="el-icon-download"></i>-->
<!--                          </span>-->
<!--                        </span>-->
<!--                      </div>-->
<!--                    </el-upload>-->
<!--                    <el-dialog :visible.sync="dialogVisible" append-to-body>-->
<!--                      <img width="100%" :src="dialogImageUrl" alt="" />-->
<!--                    </el-dialog>-->
<!--                  </el-form-item>-->
<!--                </el-col>-->
<!--              </el-row>-->
<!--            </div>-->
<!--          </div>-->
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
<!--        <el-button-->
<!--          type="primary"-->
<!--          @click="$refs.drawer.closeDrawer()"-->
<!--          :loading="loading"-->
<!--        >-->
<!--          {{ loading ? '打印中 ...' : '打 印' }}-->
<!--        </el-button>-->
      </div>
    </div>

  </el-drawer>
</template>

<script>
import Hospital from '@/components/hospital'
import Doctor from '@/components/doctor'
import Medical from '@/components/medical'
import Bingzhong from '@/components/bingzhong'
import { selectMzSettleDetails } from '@/api/personinfo'

export default {
  name: 'edit',
  components: { Hospital, Doctor, Medical, Bingzhong },
  data() {
    return {
      title: '',
      dialog: false,
      loading: false,
      isShow: true,
      isShow1: true,
      isShow2: true,
      isShow3: true,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
      },
      formLabelWidth: '100px',
      timer: null,
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,

    }
  },
  mounted() {},
  methods: {
    showDia(row) {
      if (!row) {
        this.title = '新增'
      } else {
        this.title = '查看'
        this.queryForm = Object.assign({}, row)
        this.getDetails(row.setl_id);
      }
      this.dialog = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialog = false
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
      this.close()
    },

    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleDownload(file) {
      console.log(file)
    },
    getDetails(setlId){
      var that = this;
      selectMzSettleDetails({"setlId":setlId}).then((res) => {
        if(res.code == 0){
          that.tableData = res.data ;
        }
      })
    },
    handleClose(done) {
      this.close();
      // if (this.loading) {
      //   return
      // }
      // this.$confirm('确定要打印吗？')
      //   .then((_) => {
      //     this.loading = true
      //     this.timer = setTimeout(() => {
      //       done()
      //       // 动画关闭需要一定的时间
      //       setTimeout(() => {
      //         this.loading = false
      //       }, 400)
      //     }, 2000)
      //   })
      //   .catch((_) => {})
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-upload--picture-card {
    display: none!important;
    opacity:0!important;
  }
  .el-dialog__body {
    border-top: 0;
  }
}
</style>