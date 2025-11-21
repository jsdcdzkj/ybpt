<template>
  <div class="main-container" v-loading="listLoading">
    <el-form label-width="62px">
      <el-row :gutter="20">
        <el-col :xs="18" :sm="18" :md="18" :lg="18" :xl="18">
          <el-card class="card" shadow="never">

            <div class="fill-title">
              <h2>{{ khManage.year }}{{ khManage.fixmedins_name }}{{ khManage.task_name }}</h2>
              <p>
                <span>机构类型：{{ khManage.org_type }}</span>
                <span v-if="khManage.org_type == '医疗机构'">类别：{{ khManage.category_name }}</span>
                <span>协议等级：{{ khManage.aggrement_lv }}</span>
                <span>年度：{{ khManage.year }}</span>
              </p>
            </div>
            <div class="main-fill">
              <el-row :gutter="20" v-for="(item,index) in assessmentDetails" :key="item.id">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <div class="filling-des-box"><b>{{ index + 1 }}、</b>
                    <p class="filling-des">{{ item.assess_question }}<span>【{{ item.full_score }}分】</span></p></div>
                  <el-form-item label="描述" v-if="item.is_text == 1">
                    <el-input type="textarea" rows="2" v-model="item.assess_contentl"
                              placeholder="请输入"/>
                  </el-form-item>
                  <el-form-item label="文件" v-if="item.is_file == 1">
                    <el-upload
                        class="upload-demo"
                        ref="upload"
                        action=""
                        accept=".pdf"
                        :show-file-list="true"
                        :on-change="(file, fileList) => {fileChange(file, fileList, index, item.id)}"
                        :file-list="item.files"
                        :auto-upload="false"
                        :on-remove="(file, fileList) => {handleRemove(file, fileList, index, item.files)}"
                    >
                      <el-button type="primary">选择文件</el-button>
                    </el-upload>
                  </el-form-item>
                </el-col>
              </el-row>
              <div class="end-tips">
                友情提示：考核填报信息到底啦~
              </div>
            </div>
            <div class="fill-bot">
              <el-button @click="closeAll">关 闭</el-button>
              <el-button type="primary" @click="save">暂 存</el-button>
              <el-button type="primary" @click="handleSub">提交审核</el-button>
            </div>

          </el-card>
        </el-col>
        <el-col :xs="6" :sm="6" :md="6" :lg="6" :xl="6">
          <el-card class="card" shadow="never">
            <div class="box_card">
              <div class="box_header">机构信息</div>
              <div class="box_content">
                <el-form-item label="机构名称:" class="mb0"><p>{{ khManage.fixmedins_name }}</p></el-form-item>
                <el-form-item label="机构编码:" class="mb0"><p>{{ khManage.fixmedins_code }}</p></el-form-item>
              </div>
              <div class="box_header">
                <span>日志信息</span>
              </div>
              <div class="box_content box_content-timeline">
                <el-timeline>
                  <el-timeline-item size='large' v-for="(item) in logs"
                                    :type='item.content != null ? "danger" : "primary"'
                                    :timestamp="item.submit_time" :key="item.id">
                    <div class="time-line-des">
                      <h4>{{ item.title }}</h4>
                      <p>{{ item.content }}</p>
                    </div>
                  </el-timeline-item>
                </el-timeline>
              </div>
            </div>
          </el-card>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import {assessmentDetails, assessmentFill, submitReview,} from '@/api/assessment'
import {fileURL} from "@/config/setting.config";

export default {
  name: 'filling',
  components: {},
  data() {
    return {
      listLoading: true,
      isShow: false,
      assessmentDetails: [],
      khManage: {},
      logs: {},
      fileList: [],
      fileRemoveIds: [],
      fileMap: new Map(),
    }
  },
  created() {
    this.showDia()
  },
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {
    async showDia(row) {
      this.listLoading = true
      var id = this.$route.params.id;
      if (id) {
        const {data} = await assessmentDetails({id: this.$route.params.id})
        for (let i = 0; i < data.assessmentDetails.length - 1; i++) {
          data.assessmentDetails[i].files.forEach(function (e) {
            e.url = fileURL + e.url
          });
        }
        this.assessmentDetails = data.assessmentDetails
        this.khManage = data.khManage
        this.logs = data.logs
      }
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    // 上传开始
    fileChange(file, fileList, index, id) {
      const isPDf = file.raw.type === 'application/pdf'
      // const isLt =
      //     file.raw.size / 1024 / 1 >= 1 && file.raw.size / 1024 / 1024 / 20 <= 1;
      // if (!isLt) {
      //   for (let i = 0; i < fileList.length; i++) {
      //     if (fileList[i].uid == file.uid) {
      //       fileList.splice(i, 1)
      //     }
      //   }
      //   this.$baseMessage("上传文件大小不得小于1KB,不得大于20MB!", 'error');
      //   return;
      // }
      if (!isPDf) {
        for (let i = 0; i < fileList.length; i++) {
          if (fileList[i].uid == file.uid) {
            fileList.splice(i, 1)
          }
        }
        this.$baseMessage("请上传PDF文件！", 'error')
        return
      }
      // 这是关键一句
      if (fileList.length > 0) {
        this.fileMap.set(id, fileList);
      }
    },
    handleRemove(file, fileList, index, files){
      this.fileRemoveIds.push(file.id)
    },
    uploadFile() {
      this.$refs.upload.submit()
    },

    // 上传结束
    handleChange(value) {
      let cityNames = []
      value.forEach((e) => {
        cityNames.push(CodeToText[e])
      })
      this.citys = cityNames.join('/')
    },
    closeAll() {
      // 调用全局挂载的方法
      this.$store.dispatch('tabsBar/delRoute', this.$route)
      // 返回上一步路由
      this.$router.go(-1)
    },
    async save(status) {
      this.listLoading = true ;
      let formData = new FormData()
      formData.append('id', this.$route.params.id)
      formData.append('log_title', status == 1 ? '提交审核' : '填报')
      //考核单详情id+考核描述+考核得分
      let assessmentDetails = ''
      for (let i = 0; i < this.assessmentDetails.length; i++) {
        assessmentDetails +=
            'assessment_detail_id='
            + this.assessmentDetails[i].id
            + '&assess_contentl='
            + this.assessmentDetails[i].assess_contentl
            + '&scorel=null'
        assessmentDetails += 'jsdc'
      }
      if(assessmentDetails == ''){
        this.$baseMessage("请配置考核单！", 'error')
        return
      }
      formData.append('assessmentDetails', assessmentDetails)
      formData.append('fileRemoveIds', this.fileRemoveIds)
      //文件
      for (let [key] of this.fileMap) {
        for (let i = 0; i < this.fileMap.get(key).length; i++) {
          if(!this.fileMap.get(key)[i].id){
            formData.append('files', this.fileMap.get(key)[i].raw, key + '&' + this.fileMap.get(key)[i].raw.name)
          }
        }
      }
      const {data} = await assessmentFill(formData);
      if (data.code == 0) {
        if(status == 1){
          submitReview(this.$route.params.id)
        }
        this.$baseMessage("操作成功", 'success')
        // 调用全局挂载的方法
        this.closeAll()
      } else {
        this.$baseMessage("请尝试刷新页面！", 'error')
      }

    },
    handleSub() {
      this.$baseConfirm('确认提交审核？', null, async () => {
        await this.save(1)
      })
    },

  },
}
</script>
<style lang="scss" scoped>
.fill-title {
  h2 {
    margin-top: 10px;
    margin-bottom: 10px;
    font-size: 1.5rem;
    text-align: center;
  }

  p {
    margin-top: 10px;
    font-size: 14px;
    color: #999;
    text-align: center;

    span {
      display: inline-block;
      margin: 0 10px;
    }
  }
}

.filling-des-box {
  display: flex;
}

.filling-des {
  margin-top: 0px;
  font-size: 15px;
  color: #183371;

  span {
    color: #66738e;
  }
}

.main-fill {
  height: calc(100vh - 324px);
  padding: 0 20px;
  overflow-y: auto;
}

.box_card {
  height: calc(100vh - 198px);
}
.box_content-timeline{
  height: calc(100% - 200px);
  overflow-y: auto;
}
.fill-bot {
  padding-top: 14px;
  text-align: center;
  border-top: 1px solid #efefef;
}

.end-tips {
  padding: 30px 0;
  margin-left: 20px;
  color: #b4b9bd;
}

.time-line-des {
  h4 {
    margin: 0 6px 6px 0;
    font-size: 14px;
  }

  p {
    margin: 6px 6px 6px 0;
    font-size: 14px;
  }
}

::v-deep {
  .el-timeline {
    padding-left: 0;
  }

  .mb0 {
    .el-form-item__label {
      width: auto !important;
    }
    p{
      padding: 6px 0;
      margin: 0;
      line-height: 22px;
    }
  }

  .el-card {
    margin-bottom: 0;
  }

  .upload-demo {
    /*display: flex;*/
  }

  .el-list-enter-active,
  .el-list-leave-active {
    transition: none;
  }

  .el-list-enter,
  .el-list-leave-active {
    opacity: 0;
  }

  .el-upload-list {
    line-height: 0;
  }

  .el-upload-list__item {
    display: inline-block;
    width: auto;
    margin-top: 6px;
    margin-right: 10px;
    background-color: #f1f5f9;
  }

  .up-tips {
    position: absolute;
    padding: 0 6px;
    margin: 10px 0 20px 0;
    color: #ed6a0c;
    background-color: #fffbe8;
  }
}
</style>