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
                      v-model="queryForm.username"
                      placeholder="证件类型"
                      style="width: 100%"
                      disabled
                    >
                      <el-option
                        label="居民身份证（户口薄）"
                        value="1"
                      ></el-option>
                      <el-option
                        label="中国人民解放军军官证"
                        value="2"
                      ></el-option>
                      <el-option
                        label="中国人民武装警察警官证"
                        value="3"
                      ></el-option>
                      <el-option
                        label="香港特区护照/港澳居民来往内地通行证"
                        value="4"
                      ></el-option>
                      <el-option
                        label="奥门特区护照/港澳居民来往内地通行证"
                        value="5"
                      ></el-option>
                      <el-option
                        label="台湾居民来往内地通行证"
                        value="6"
                      ></el-option>
                      <el-option label="外国人永久居留证" value="7"></el-option>
                      <el-option label="外国人护照" value="8"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="证件号码">
                    <el-input
                      v-model.trim="queryForm.username"
                      clearable
                      class="input-with-select"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="姓名">
                    <el-input
                      v-model.trim="queryForm.username"
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
                      v-model="queryForm.username"
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
                      v-model="queryForm.username"
                      style="width: 100%"
                      disabled
                    >
                      <el-option label="汉族" value="1"></el-option>
                      <el-option label="少数民族" value="2"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="出生日期">
                    <el-date-picker
                      v-model.trim="queryForm.username"
                      disabled
                      type="date"
                      class="w"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="电话号码">
                    <el-input v-model.trim="queryForm.username" disabled />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>异地就医登记信息</span>
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
                  <el-form-item label="异地安置类别">
                    <el-select v-model="queryForm.username" class="w">
                      <el-option label="户口在本地在外地居住" value="0"></el-option>
                      <el-option label="户口在异地在外地居住" value="1"></el-option>
                      <el-option label="公派人员" value="2"></el-option>
                      <el-option label="单位长期外派工作" value="3"></el-option>
                      <el-option label="异地安置退休人员" value="4"></el-option>
                      <el-option label="异地长期居住人员" value="5"></el-option>
                      <el-option label="单位长期外派学习" value="6"></el-option>
                      <el-option label="异地购房长期居住" value="7"></el-option>
                      <el-option label="迁移户口在原籍长期居住" value="8"></el-option>
                      <el-option label="其他原因异地长期居住" value="9"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="安置地所属行政区" class="custemitem">
                    <el-cascader
                      :options="options"
                      class="w"
                      placeholder="可搜索"
                      filterable
                      clearable
                      @change="handleChange"
                    ></el-cascader>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="异地备案上报状态" class="custemitem">
                    <el-select v-model="queryForm.username" class="w" disabled>
                      <el-option label="未上报" value="0"></el-option>
                      <el-option label="已上报" value="1"></el-option>                    
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="转出地定点医药机构编号" class="custemitem">
                    <el-input v-model.trim="queryForm.username" @click.native="openwin">
                      <el-button
                        slot="append"
                        icon="el-icon-search"
                        @click="openwin"
                      ></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="转出地定点医药机构名称" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.username"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="开始日期">
                    <el-date-picker
                      v-model="queryForm.username"
                      type="date"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="结束日期">
                    <el-date-picker
                      v-model="queryForm.username"
                      type="date"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="安置地(就医地)医保机构名称" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.username"                                         
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="安置地(就医地)医保机构联系人" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.username"                                         
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="安置地(就医地)医保机构联系电话" class="custemitem">
                    <el-input
                      v-model.trim="queryForm.username"                                         
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="安置(就医)区类型" class="custemitem">
                    <el-select v-model="queryForm.username" class="w" disabled>
                      <el-option label="省内" value="0"></el-option>
                      <el-option label="省外" value="1"></el-option>                    
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="异地联网方式" class="custemitem">
                    <el-select v-model="queryForm.username" class="w" disabled>
                      <el-option label="全额自付回统筹区报销" value="0"></el-option>
                      <el-option label="国家异地就医结算" value="1"></el-option>   
                      <el-option label="省内异地就医结算" value="2"></el-option>                         
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="申报来源">
                    <el-select v-model="queryForm.username" class="w" disabled>
                      <el-option label="中心经办系统" value="0"></el-option>
                      <el-option label="网上经办系统" value="1"></el-option>   
                      <el-option label="APP" value="2"></el-option>
                      <el-option label="一体机" value="3"></el-option>
                      <el-option label="其他" value="4"></el-option>                                           
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="电话">
                    <el-input
                      v-model.trim="queryForm.username"                                         
                    ></el-input>
                  </el-form-item>
                </el-col>                
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="居外地址">
                    <el-input
                      v-model.trim="queryForm.username"
                      type="textarea"
                      :rows="5"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="备注">
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
          <div class="box_card">
            <div class="box_header" style="position:relative;">
              <span>医疗机构列表</span>   
              <div style="position:absolute;right:15px;top:-10px;"><el-button type="primary">添 加</el-button></div>
            </div>
            <div class="box_content">
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
                <el-table-column
                  prop="date"
                  label="序号"
                  width="80"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column prop="name" label="定点医药机构编号"></el-table-column>
                <el-table-column
                  prop="address"
                  label="定点医药机构名称"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  prop="address"
                  label="开始日期"
                  show-overflow-tooltip
                ></el-table-column>
                <el-table-column
                  prop="address"
                  label="结束日期"
                  show-overflow-tooltip
                ></el-table-column>              
                <el-table-column
                  show-overflow-tooltip
                  label="操作"
                  width="100"
                  align="center"
                  fixed="right"
                >
                  <template #default="{ row }">
                    <el-button
                      plain
                      @click="handlechuli(row)"
                      type="primary"
                      size="mini"
                    >
                      删除
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>代办人信息</span>
              <vab-icon
                :icon="['fas', 'angle-up']"
                v-if="isShow2"
                @click="moreQuery2"
              ></vab-icon>
              <vab-icon
                :icon="['fas', 'angle-down']"
                v-else
                @click="moreQuery2"
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20" v-if="isShow2">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="代办人姓名">
                    <el-input v-model.trim="queryForm.username">                     
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="代办人证件类型" class="custemitem">
                    <el-select v-model="queryForm.username" class="w">
                      <el-option label="中心经办系统" value="0"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="代办人证件号码" class="custemitem">
                    <el-input v-model.trim="queryForm.username">                     
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="代办人联系方式" class="custemitem">
                    <el-input v-model.trim="queryForm.username">                    
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="代办人关系">
                    <el-input
                      v-model.trim="queryForm.username"                    
                    ></el-input>
                  </el-form-item>
                </el-col>             
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="代办人联系地址" class="custemitem">
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
          <div class="box_card">
            <div class="box_header">
              <span>附件上传</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="" class="tsitem">
                    <el-upload
                      action="#"
                      list-type="picture-card"
                      :auto-upload="false"
                      :file-list="fileList"                      
                    >
                      <i slot="default" class="el-icon-plus"></i>
                      <div slot="file" slot-scope="{ file }">
                        <img
                          class="el-upload-list__item-thumbnail"
                          :src="file.url"
                          alt=""
                        />
                        <span class="el-upload-list__item-actions">
                          <span
                            class="el-upload-list__item-preview"
                            @click="handlePictureCardPreview(file)"
                          >
                            <i class="el-icon-zoom-in"></i>
                          </span>                          
                          <span
                            v-if="!disabled"
                            class="el-upload-list__item-delete"
                            @click="handleRemove(file)"
                          >
                            <i class="el-icon-delete"></i>
                          </span>
                        </span>
                      </div>
                    </el-upload>
                    <el-dialog :visible.sync="dialogVisible" append-to-body>
                      <img width="100%" :src="dialogImageUrl" alt="" />
                    </el-dialog>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
        <el-button @click="cancelForm">重 置</el-button>
        <el-button @click="cancelForm" disabled>受理单</el-button>
        <el-button
          type="primary"
          @click="$refs.drawer.closeDrawer()"
          :loading="loading"
        >
          {{ loading ? '提交中 ...' : '提 交' }}
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
import { regionDataPlus, CodeToText } from 'element-china-area-data'
export default {
  name: 'edit',
  components: { Hospital,Doctor,Medical,Bingzhong },
  data() {
    return {
      options: regionDataPlus,
      citys: '',
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
      isShow2: false,
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
      formLabelWidth: '140px',
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
        this.title = '编辑'
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
    openwin(){
      this.$refs['hospital'].showDia()
    },
    openwin1(){
      this.$refs['doctor'].showDia()
    },
    openwin2(){
      this.$refs['bingzhong'].showDia()
    },
    openwin3(){
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
    handleRemove(file) {
      console.log(file)
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url
      this.dialogVisible = true
    },
    handleChange(value) {
      let cityNames = []
      value.forEach((e) => {
        cityNames.push(CodeToText[e])
      })
      this.citys = cityNames.join('/')
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
  .el-dialog__body {
    border-top: 0;
  }
}
</style>