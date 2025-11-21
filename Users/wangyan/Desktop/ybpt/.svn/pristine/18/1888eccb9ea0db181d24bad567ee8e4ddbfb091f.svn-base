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
              <span>临床检查报告影像信息</span>
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
                  <el-form-item label="就诊流水号" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.mdtrt_sn"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="临床检查报告影像id" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.clnc_exam_rpot_img_id"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="报告单号" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.rpotc_no"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="全量唯一记录号" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.ful_rid"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="检查id" class="medfee_sumamt">
                    <el-input
                            v-model.trim="queryForm.exam_id"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="患者姓名">
                    <el-input
                            v-model.trim="queryForm.patn_name"
                            disabled

                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="图像流水号" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.img_sn"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="检查时间" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.exam_time"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="检查类别" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.exam_type"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="存储路径" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.stog_path"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="序列数量" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.sn_cnt"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="图像数量" class="custemitem">
                    <el-input
                            v-model.trim="queryForm.img_cnt"
                            disabled
                    ></el-input>
                  </el-form-item>
                </el-col>



              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
        <!--<el-button-->
          <!--type="primary"-->
          <!--@click="$refs.drawer.closeDrawer()"-->
          <!--:loading="loading"-->
        <!--&gt;-->
          <!--{{ loading ? '打印中 ...' : '打 印' }}-->
        <!--</el-button>-->
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
import { chargeDetails } from '@/api/settlementCenter.js'
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
      layout: 'total, sizes, prev, pager, next, jumper',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        mdtrt_id: '',
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
      list: [],
      total: '',
    }
  },
  created() {
    // this.updata();
    // this.natyData();
  },
  mounted() {},
  methods: {
    showDia(row) {
      this.queryForm = row ;
      if(this.queryForm.skintst_dicm != "" && this.queryForm.skintst_dicm != null){
        if(this.queryForm.skintst_dicm ==0){
          this.queryForm.skintst_dicm = "否"
        }else {
          this.queryForm.skintst_dicm = "是"
        }
      }
      if(this.queryForm.main_medc_flag != "" && this.queryForm.main_medc_flag != null){
        if(this.queryForm.main_medc_flag ==0){
          this.queryForm.main_medc_flag = "否"
        }else {
          this.queryForm.main_medc_flag = "是"
        }
      }

      if(this.queryForm.urgt_flag != "" && this.queryForm.urgt_flag != null){
        if(this.queryForm.urgt_flag ==0){
          this.queryForm.urgt_flag = "否"
        }else {
          this.queryForm.urgt_flag = "是"
        }
      }

      if(this.queryForm.bas_medn_flag != "" && this.queryForm.bas_medn_flag != null){
        if(this.queryForm.bas_medn_flag ==0){
          this.queryForm.bas_medn_flag = "否"
        }else {
          this.queryForm.bas_medn_flag = "是"
        }
      }

      this.queryForm.pageNo = '1' ;
      this.queryForm.pageSize = '10' ;
      console.log(row);
      this.fetchData();
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

    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
    },

    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    fetchData() {
      var that = this ;
      chargeDetails(that.queryForm).then((res) => {
        if(res.code == 0){
          that.tableData = res.data.records ;
          that.total =res.data.total
        }
        that.loading = false;
      })
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
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