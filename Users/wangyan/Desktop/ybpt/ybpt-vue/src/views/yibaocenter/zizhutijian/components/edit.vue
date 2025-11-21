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
              <span>自主体检基本信息</span>
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
                  <el-form-item label="申请单位名称">
                    <el-input v-model.trim="queryForm.username" disabled>                     
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="申请时间">
                    <el-input
                      v-model.trim="queryForm.username"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="申请理由">
                    <el-input
                      v-model.trim="queryForm.username"
                      type="textarea"
                      :rows="5"
                      disabled
                    ></el-input>                 
                  </el-form-item>
                </el-col>               
              </el-row>
            </div>
          </div>         
         
          <div class="box_card">
            <div class="box_header">
              <span>审核信息</span>
              <vab-icon
                :icon="['fas', 'angle-up']"
                v-if="isShow3"
                @click="moreQuery3"
              ></vab-icon>
              <vab-icon
                :icon="['fas', 'angle-down']"
                v-else
                @click="moreQuery3"
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20" v-if="isShow3">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="审核结果">
                     <el-select v-model="queryForm.username" class="w">
                      <el-option label="通过" value="0"></el-option>
                      <el-option label="驳回" value="0"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="审核意见">
                    <el-input
                      v-model.trim="queryForm.username"
                      type="textarea"
                      :rows="5"
                     
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
        <el-button
          type="primary"
          @click="$refs.drawer.closeDrawer()"
          :loading="loading"
        >
          {{ loading ? '保存中 ...' : '保 存' }}
        </el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
export default {
  name: 'edit',
  components: {},
  data() {
    return {
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄',
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄',
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄',
        },
      ],
      title: '',
      dialog: false,
      loading: false,
      isShow: false,
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
      fileList: [
        {
          name: 'food.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
        },
        {
          name: 'food2.jpeg',
          url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
        },
      ],
    }
  },
  mounted() {},
  methods: {
    showDia(row) {
      if (!row) {
        this.title = '新增'
      } else {
        this.title = '审核'
        this.form = Object.assign({}, row)
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
    handleRemove(file) {
      console.log(file)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
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
      this.$confirm('确定要提交表单吗？')
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