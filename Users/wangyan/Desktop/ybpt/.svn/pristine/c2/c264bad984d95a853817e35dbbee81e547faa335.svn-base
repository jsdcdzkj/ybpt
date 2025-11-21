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
                  <el-form-item label="人员编号">
                    <el-input
                            v-model.trim="queryForm.psn_no"
                            clearable
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="人员姓名">
                    <el-input
                            v-model.trim="queryForm.psn_name"
                            clearable
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="证件号">
                    <el-input
                            v-model.trim="queryForm.certno"
                            clearable
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单位编号">
                    <el-input
                            v-model.trim="queryForm.emp_no"
                            clearable
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="单位名称">
                    <el-input
                      v-model.trim="queryForm.emp_name"
                      clearable
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="费款所属期">
                    <el-input
                      v-model.trim="queryForm.cashym"
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20" v-if="isShow">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="对应费款所属期起始" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.accrym_begn"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="对应费款所属期结束" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.accrym_end"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="险种类型">
                    <el-input
                            v-model.trim="queryForm.insutype"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="人员类别">
                    <el-input
                            v-model.trim="queryForm.psn_type"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="人员身份类别">
                    <el-input
                            v-model.trim="queryForm.psn_idet_type"
                            disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="参保身份">
                    <el-input
                            v-model.trim="queryForm.insu_idet"
                            disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>缴费信息</span>
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
                  <el-form-item label="个人缴费金额">
                    <el-input v-model.trim="queryForm.psn_clct_amt" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="财政补助金额">
                    <el-input v-model.trim="queryForm.finsubs_amt" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="其他缴费金额">
                    <el-input v-model.trim="queryForm.oth_clct_amt" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="缴费总金额">
                    <el-input v-model.trim="queryForm.clct_sumamt" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定额征缴标志">
                    <el-input v-model.trim="queryForm.quot_clct_flag" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="缴费标志">
                    <el-input v-model.trim="queryForm.clct_flag" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="缴费类型">
                    <el-input v-model.trim="queryForm.clct_type" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="缴费时间">
                    <el-input v-model.trim="queryForm.clct_time" disabled />
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
                  <el-form-item label="核销标志">
                    <el-input v-model.trim="queryForm.revs_flag" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="险种离退休标志">
                    <el-input v-model.trim="queryForm.insutype_retr_flag" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="入国库时间">
                    <el-input
                      v-model.trim="queryForm.intsury_time"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="上解时间">
                    <el-input
                            v-model.trim="queryForm.ursn_time"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="电子税票号码">
                    <el-input
                            v-model.trim="queryForm.elec_taxrpt_no"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="申报周期">
                    <el-input
                            v-model.trim="queryForm.dcla_prd"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="统筹区">
                    <el-input
                            v-model.trim="queryForm.poolarea_no"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="主管税务部门代码" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.taxdept_code"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
<!--              <el-table-->
<!--                :data="tableData"-->
<!--                border-->
<!--                stripe-->
<!--                class="w"-->
<!--                highlight-current-row-->
<!--                height="300px"-->
<!--              >-->
<!--                <template slot="empty">-->
<!--                  <el-empty :image-size="150"></el-empty>-->
<!--                </template>-->
<!--                <el-table-column-->
<!--                  prop="date"-->
<!--                  label="序号"-->
<!--                  width="80"-->
<!--                  show-overflow-tooltip-->
<!--                ></el-table-column>-->
<!--                <el-table-column prop="name" label="病种代码"></el-table-column>-->
<!--                <el-table-column-->
<!--                  prop="address"-->
<!--                  label="病种名称"-->
<!--                  show-overflow-tooltip-->
<!--                ></el-table-column>-->
<!--                <el-table-column-->
<!--                  prop="address"-->
<!--                  label="定点医药机构编号"-->
<!--                  show-overflow-tooltip-->
<!--                ></el-table-column>-->
<!--                <el-table-column-->
<!--                  prop="address"-->
<!--                  label="定点医药机构名称"-->
<!--                  show-overflow-tooltip-->
<!--                ></el-table-column>-->
<!--                <el-table-column-->
<!--                  prop="address"-->
<!--                  label="定点医药机构类别"-->
<!--                  show-overflow-tooltip-->
<!--                ></el-table-column>-->
<!--                <el-table-column-->
<!--                  prop="address"-->
<!--                  label="医药机构等级"-->
<!--                  show-overflow-tooltip-->
<!--                ></el-table-column>-->
<!--                <el-table-column-->
<!--                  prop="date"-->
<!--                  label="医药机构开始日期"-->
<!--                  show-overflow-tooltip-->
<!--                ></el-table-column>-->
<!--                <el-table-column-->
<!--                  show-overflow-tooltip-->
<!--                  label="操作"-->
<!--                  width="100"-->
<!--                  align="center"-->
<!--                  fixed="right"-->
<!--                >-->
<!--                  <template #default="{ row }">-->
<!--                    <el-button-->
<!--                      plain-->
<!--                      @click="handlechuli(row)"-->
<!--                      type="primary"-->
<!--                      size="mini"-->
<!--                    >-->
<!--                      删除-->
<!--                    </el-button>-->
<!--                  </template>-->
<!--                </el-table-column>-->
<!--              </el-table>-->
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
import { getDetail } from '@/api/personinfo'

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
        // this.getPersonInfo(row);
        this.queryForm = Object.assign({}, row)
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
    getPersonInfo(cardno){
      var that = this;
      getDetail({"cardno":cardno}).then((res) => {
        if(res.code == 0){
          that.queryForm = res.data ;
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