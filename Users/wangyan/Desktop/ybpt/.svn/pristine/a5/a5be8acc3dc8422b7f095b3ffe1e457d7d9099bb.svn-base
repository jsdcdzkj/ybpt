<template>
  <el-drawer :title="title" :before-close="cancelForm" :visible.sync="dialog" direction="rtl" :with-header="false"
             custom-class="box_drawer" size="80%" ref="drawer">
    <div class="drawer_content">
      <div class="drawer_main">
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
                    <!-- 第一题 -->
                    <el-col :xs="18" :sm="18" :md="18" :lg="18" :xl="18">
                      <div class="filling-des-box"><b>{{ index + 1 }}、</b>
                        <p class="filling-des">{{ item.assess_question }}<span>【{{ item.full_score }}分】</span></p></div>
                    </el-col>
                    <el-col :xs="6" :sm="6" :md="6" :lg="6" :xl="6">
                      <el-form-item label="得分">
                        <el-input name="score" v-model="item.scorel" type="number" min="0" :max="item.full_score" v-on:input="countScore(arguments[0],item.full_score,index)"
                                  onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"
                                  onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^0-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}"/>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-if="item.is_text == 1">
                      <el-form-item label="描述">
                        <el-input type="textarea" disabled rows="2" v-model="item.assess_contentl"/>
                      </el-form-item>
                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-if="item.is_file == 1">
                      <el-form-item label="文件">
                        <p class="file-box" v-for="file in item.files" :key="file.id">
                          <el-link type="primary" :href="file.url" target="_blank">
                            <i class="el-icon-document"></i>
                            {{ file.name }}
                          </el-link>
                        </p>
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <div class="total-score">
                    <div class="end-tips">
                      友情提示：考核填报信息到底啦~
                    </div>
                    <div>
                      合计得分：<b>{{ count }}分</b>
                    </div>
                  </div>
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

      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
        <el-button type="primary" @click="save">暂 存</el-button>
        <el-button type="primary" @click="handleSub">通 过</el-button>
        <el-button type="danger" @click="dialogVisible = true">驳 回</el-button>
      </div>
    </div>
    <el-dialog
        title="驳回"
        :visible.sync="dialogVisible"
        width="30%"
        append-to-body>
      <el-form :model="reasonForm" ref="reasonForm" :rules="rules" label-width="80px">
        <el-form-item label="驳回原因" prop="approval_opinion">
          <el-input v-model="reasonForm.approval_opinion" type="textarea" rows="4" placeholder="请输入驳回原因"/>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
		    <el-button @click="dialogVisible = false">取 消</el-button>
		    <el-button type="primary" @click="rejectSave">确 定</el-button>
		  </span>
    </el-dialog>
  </el-drawer>
</template>

<script>
import {assessmentDetails, assessmentFill, submitReject, submitSuccess} from "@/api/assessment";
import {fileURL} from "@/config/setting.config";

export default {
  name: 'detail',
  components: {},
  data() {
    return {
      id: '',
      dialogVisible: false,
      assessmentDetails: [],
      khManage: {},
      logs: {},
      fileList: [],
      title: '',
      dialog: false,
      loading0: false,
      loading: false,
      isShow: false,
      isShow1: true,
      isShow2: false,
      reasonForm: {
        approval_opinion: '',
      },
      rules: {
        approval_opinion: [
          { required: true, message: '请输入驳回原因', trigger: 'blur' }
        ]
      },
      formLabelWidth: '100px',
      count: 0
    }
  },
  mounted() {
  },
  methods: {
    countScore(val,maxNum,index) {
      //比较输入的值和最大值，返回小的
      let num = Math.min(Number(val),maxNum)
      this.assessmentDetails[index].scorel = num
      const numRe = new RegExp(/^[0-9]*$/);
      this.count = 0
      for (let i = 0; i < this.assessmentDetails.length; i++) {
        if (this.assessmentDetails[i].scorel != '' && numRe.test(this.assessmentDetails[i].scorel)) {
          this.count += parseInt(this.assessmentDetails[i].scorel)
        }
      }
    },
    async showDia(row) {
      this.id = row.id
      this.title = '考核单详情'
      const {data} = await assessmentDetails({id: this.id})
      this.count = 0
      var numRe = new RegExp(/^[0-9]*$/)
      for (let i = 0; i < data.assessmentDetails.length; i++) {
        data.assessmentDetails[i].files.forEach(function (e) {
          e.url = fileURL + e.url
        })
        if (numRe.test(data.assessmentDetails[i].scorel)) {
          this.count += parseInt(data.assessmentDetails[i].scorel)
        }
      }
      this.assessmentDetails = data.assessmentDetails
      this.khManage = data.khManage
      this.logs = data.logs
      this.dialog = true
    },
    async rejectSave() {
      this.$refs['reasonForm'].validate(async (valid) => {
        if (valid) {
          await this.save(0)
        } else {
          return false
        }
      })
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
    },
    async save(status) {
      let formData = new FormData()
      formData.append('id', this.id)
      let log_tilte = '';
      if (status == 2) {
        log_tilte = '审核通过';
      } else if (status == 0) {
        log_tilte = '审核驳回';
      } else {
        log_tilte = '抽查'
      }
      formData.append('log_title', log_tilte)
      formData.append('approval_opinion', this.reasonForm.approval_opinion)
      //考核单详情id+考核描述+考核得分
      let assessmentDetails = ''
      for (let i = 0; i < this.assessmentDetails.length; i++) {
        assessmentDetails +=
            'assessment_detail_id='
            + this.assessmentDetails[i].id
            + '&assess_contentl='
            + this.assessmentDetails[i].assess_contentl
            + '&scorel=' + this.assessmentDetails[i].scorel
        assessmentDetails += 'jsdc'
      }
      formData.append('assessmentDetails', assessmentDetails)
      const {data} = await assessmentFill(formData);
      if (data.code == 0) {
        if (status == 2) {
          submitSuccess(this.id).then((res) => {
            if (res.code == 0) {
              this.listLoading = false;
            }
          })
        }
        if (status == 0) {
          submitReject(this.id).then((res) => {
            if (res.code == 0) {
              this.dialogVisible = false
              this.listLoading = false;
            }
          })
        }
        this.$baseMessage("操作成功", 'success')
        this.cancelForm()
        this.$emit('fetch-data')
      } else {
        this.$baseMessage("请尝试刷新页面！", 'error')
      }

    },
    handleSub() {
      this.$baseConfirm('确认提交审核？', null, async () => {
        await this.save(2)
        this.listLoading = true;
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
  height: calc(100vh - 253px);
  padding: 0 20px;
  overflow-y: auto;
}

.box_card {
  height: calc(100vh - 162px);
}
.box_content-timeline{
  height: calc(100% - 210px);
  overflow-y: auto;
}
.fill-bot {
  padding-top: 14px;
  text-align: center;
  border-top: 1px solid #efefef;
}

.end-tips {
  margin-left: 14px;
  color: #b4b9bd;
}

.file-box{
  display: inline-block;
  padding: 0 8px;
  margin-top: 0;
  margin-right: 6px;
  margin-bottom: 6px;
  line-height: 28px;
  background-color: #f1f5f9;
  .el-link{
    line-height: 18px;
  }
}

.score {
  margin: 0;
  font-weight: bold;
}

.total-score {
  display: flex;
  justify-content: space-between;
  padding: 14px 8px;
  margin-top: 40px;
  margin-bottom: 30px;
  color: #183371;
  background-color: #edf7fd;
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
  .el-dialog__body, .el-dialog__footer {
    border-top: none;
  }

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

}
</style>