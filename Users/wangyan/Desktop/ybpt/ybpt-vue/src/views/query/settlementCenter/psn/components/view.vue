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
              <span>人员基本信息</span>
              <vab-icon
                :icon="['fas', 'angle-up']"
                v-if="isShow"
                @click="moreQuery"
              ></vab-icon>
              <vab-icon
                :icon="['fas', 'angle-down']"
                v-else
                @click="moreQuery"
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="证件类型">
                    <el-select
                      v-model="queryForm.psn_cert_Type"
                      placeholder="证件类型"
                      style="width: 100%"
                      disabled
                    >
                      <el-option
                              v-for="item in insutypeList"
                              :key="item.nat_dic_val_code"
                              :label="item.nat_dic_val_name"
                              :value="item.nat_dic_val_code">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="证件号码">
                    <el-input
                      v-model.trim="queryForm.certno"
                      clearable
                      class="input-with-select"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="姓名">
                    <el-input
                      v-model.trim="queryForm.psn_name"
                      placeholder="姓名"
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row :gutter="20" v-if="isShow">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="性别">
                    <el-select
                      v-model="queryForm.gend"
                      style="width: 100%"
                      disabled
                    >
                      <el-option label="男" value="1"></el-option>
                      <el-option label="女" value="2"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="民族">
                    <el-select
                      v-model="queryForm.naty"
                      style="width: 100%"
                      disabled
                    >
                      <el-option
                              v-for="item in natyList"
                              :key="item.nat_dic_val_code"
                              :label="item.nat_dic_val_name"
                              :value="item.nat_dic_val_code">
                      </el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="出生日期">
                    <el-date-picker
                      v-model.trim="queryForm.brdy"
                      disabled
                      type="date"
                      class="w"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="电话号码">
                    <el-input v-model.trim="queryForm.mob" disabled />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="居住地址">
                    <el-input v-model.trim="queryForm.live_addr" disabled />
                  </el-form-item>
                </el-col>

              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>人员定点登记信息</span>
              <vab-icon
                :icon="['fas', 'angle-up']"
                v-if="isShow1"
                @click="moreQuery1"
              ></vab-icon>
              <vab-icon
                :icon="['fas', 'angle-down']"
                v-else
                @click="moreQuery1"
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20" v-if="isShow1">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点医药机构编号" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.fixmedins_code"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点医药机构名称" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.fixmedins_name"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="业务申请类型">
                    <el-input
                      v-model.trim="queryForm.biz_appy_type_name"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="险种类型">
                    <el-input
                            v-model.trim="queryForm.insutypename"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="开始日期">
                    <el-date-picker
                      v-model="queryForm.begndate"
                      type="date"
                      disabled
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="结束日期">
                    <el-date-picker
                      v-model="queryForm.enddate"
                      type="date"
                      disabled
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
        <el-button
          type="primary"
          @click="$refs.drawer.closeDrawer()"
          :loading="loading"
        >
          {{ loading ? '打印中 ...' : '打 印' }}
        </el-button>
      </div>
    </div>
    <hospital ref="hospital" @fetch-data="form.name"></hospital>
    <doctor ref="doctor" @fetch-data="form.name"></doctor>
    <medical ref="medical" @fetch-data="form.name"></medical>
    <bingzhong ref="bingzhong" @fetch-data="form.name"></bingzhong>
  </el-drawer>
</template>

<script>
import Hospital from '@/components/hospital'
import Doctor from '@/components/doctor'
import Medical from '@/components/medical'
import Bingzhong from '@/components/bingzhong'
import { upData } from '@/api/common.js'
export default {
  name: 'edit',
  components: { Hospital, Doctor, Medical, Bingzhong },
  data() {
    return {
      tableData: [],
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
        psn_cert_Type: '',
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
      fileList: [],
      insutypeList: [],
      natyList: [],
    }
  },
  created() {
    this.updata();
    this.natyData();
  },
  mounted() {},
  methods: {
    showDia(row) {
      this.queryForm = row ;
      console.log(row)
      if (!row) {
        this.title = '详情';
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
    openwin() {
      this.$refs['hospital'].showDia()
    },
    openwin1() {
      this.$refs['doctor'].showDia()
    },
    openwin2() {
      this.$refs['bingzhong'].showDia()
    },
    openwin3() {
      this.$refs['medical'].showDia()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    moreQuery1() {
      this.isShow1 = !this.isShow1
    },
    moreQuery2() {
      this.isShow2 = !this.isShow2
    },
    moreQuery3() {
      this.isShow3 = !this.isShow3
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleDownload(file) {
      console.log(file)
    },
    handleClose(done) {
      if (this.loading) {
        return
      }
      this.$confirm('确定要打印吗？')
        .then((_) => {
          this.loading = true
          this.timer = setTimeout(() => {
            done()
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false
            }, 400)
          }, 2000)
        })
        .catch((_) => {})
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
    },
    updata() {
      var that = this ;
      upData('PSN_CERT_TYPE').then((res) => {
        if(res.code == 0){
          that.insutypeList = res.data ;
        }
      })
    },
    natyData() {
      var that = this ;
      upData('NATY').then((res) => {
        if(res.code == 0){
          that.natyList = res.data ;
        }
      })
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