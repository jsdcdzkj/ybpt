<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">查询条件</span>
            <div class="right">
              <el-button
                icon="el-icon-search"
                type="primary"
                @click="queryData"
              >
                查 询
              </el-button>
              <!--<el-button icon="el-icon-refresh-left">重 置</el-button>-->
            </div>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="网签状态">
                  <el-select
                    v-model="queryForm.status"
                    style="width: 100%"
                    clearable
                  >
                    <el-option label="待审核" value="0"></el-option>
                    <el-option label="已签章" value="1"></el-option>
                    <el-option label="已解约" value="2"></el-option>
                    <el-option label="已过期" value="3"></el-option>
                    <el-option label="已驳回" value="4"></el-option>
                    <el-option label="已撤销" value="5"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="年份">
                  <el-date-picker
                    v-model="queryForm.year"
                    type="year"
                    value-format="yyyy"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">历史记录</span>
            <div class="right">
              <el-button
                type="success"
                icon="el-icon-plus"
                :disabled="regeditFlag"
                @click="handleAuthentication"
              >
                {{ regeditStatus }}
              </el-button>
              <el-button
                type="success"
                icon="el-icon-plus"
                :disabled="regeditCode != 3 && !regeditFlag"
                @click="changeCompanyInfo"
              >
                法人变更
              </el-button>
              <el-button
                type="success"
                icon="el-icon-plus"
                @click="handleAdd"
                :disabled="regeditCode != 3 && !regeditFlag"
              >
                申请
              </el-button>
            </div>
          </div>
          <el-table
            v-loading="listLoading"
            ref="listTable"
            stripe
            :data="list"
            :element-loading-text="elementLoadingText"
            highlight-current-row
            border
            @current-change="handleCurrentChange"
            height="calc(100vh - 540px)"
          >
            <!-- <template slot="empty">
                          <el-empty image-size="200"></el-empty>
                        </template> -->
            <!--            <el-table-column-->
            <!--              show-overflow-tooltip-->
            <!--              type="selection"-->
            <!--              align="center"-->
            <!--            ></el-table-column>-->
            <!-- <el-table-column
              show-overflow-tooltip
              prop="cred_lv_type"
              label="机构类型"
              align="center"
              width="120px"
            ></el-table-column> -->
            <el-table-column
              show-overflow-tooltip
              label="年份"
              align="center"
              prop="year"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="国家编码"
              align="center"
              prop="mechanism_code"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fixmedins_name"
              label="机构名称"
              align="center"
            ></el-table-column>
            <!--            <el-table-column show-overflow-tooltip prop="medical_code" label="医保编码" align="center" width="120px"></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="cred_lv_name"
              label="机构等级"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="legrep_name"
              label="法定代表人"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="legrep_person"
              label="联系人"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="dept_resper_tel"
              label="联系电话"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="signDate"
              label="签署日期"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="invalid_date"
              label="到期日期"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="status_name"
              label="网签状态"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="approval_opinion"
              label="审批意见"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="operate_user_name"
              label="审核人"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="240px"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handleView(row)"
                  type="primary"
                  size="mini"
                >
                  查看
                </el-button>
                <el-button
                  plain
                  @click="handlecancel(row, 5)"
                  v-if="row.status == 0"
                  type="danger"
                  size="mini"
                >
                  撤消
                </el-button>
                <el-button
                  plain
                  @click="handleRenew(row)"
                  v-if="row.status == 3 && row.is_agreement == '0'"
                  type="warning"
                  size="mini"
                >
                  续签
                </el-button>
                <el-button
                  plain
                  @click="downPdf(row)"
                  type="primary"
                  size="mini"
                  v-if="row.status == 1"
                >
                  下载
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination
            background
            :current-page="queryForm.pageNo"
            :page-size="queryForm.pageSize"
            :layout="layout"
            :total="total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange2"
          ></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>
<script>
import Cardnum from '@/components/cardno'
import Edit from './components/edit'
import Views from './components/view'
import {
  getPageList,
  revoke,
  selectOrganizationLevel,
  updJy,
} from '@/api_net/netTagMechanism'
import {
  checkAuthentication,
  regedit,
  authAutoSign,
  personalAuthentication,
  changeCompanyInfo,
  getRegisterInfo,
} from '@/api_net/netTagSeal'

export default {
  name: 'wqsqList',
  components: { Cardnum, Edit, Views },
  data() {
    return {
      dialogImageUrl: '',
      regeditVisible: false,
      uploadSealVisible: false,
      regeditFlag: true,
      regeditStatus: 0,
      regeditType: '1',
      regeditCode: '0',
      value1: '',
      start: '',
      checked: false,
      isShow: false,
      list: null,
      levelList: [],
      fileList: [],
      company_seal_content: '',
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      form: {},
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        type: '', //协议
        type_status: '', //历史网签
        fixmedins_name: '', //机构名称
        startTime: '',
        endTime: '',
        year: '',
        status: '',
      },
      rules: {
        company_seal_content: [
          { required: true, trigger: 'blur', message: '请填写印章内容' },
        ],
      },
    }
  },
  created() {
    this.fetchData()
  },

  beforeDestroy() {},

  mounted() {
    this.checkAuthentications()
  },
  methods: {
    async onChangePicture(file, fileList) {
      this.fileList = fileList
    },
    handleRemovePicture(file, fileList) {
      this.fileList = fileList
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    handleExceed(files, fileList) {
      this.$message.warning(
        `当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
          files.length + fileList.length
        } 个文件`
      )
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    handleAuthentication() {
      if (this.regeditCode == 0) {
        this.regedits()
      } else if (this.regeditCode == 1) {
        this.authAutoSign()
      } else if (this.regeditCode == 2) {
        this.personalAuthentication()
      } else if (this.regeditCode == 3) {
        getRegisterInfo().then((res) => {
          if (res.code == 0) {
            window.open(res.data.auth_pdf_download)
          } else {
            this.$baseMessage(res.msg, 'error')
          }
        })
      }
    },
    changeCompanyInfo() {
      this.$baseConfirm('确认进行变更？', null, async () => {
        changeCompanyInfo().then((res) => {
          if (res.code == 0) {
            this.$baseMessage('变更成功', 'success')
            this.checkAuthentications()
          }
        })
      })
    },
    async regedits() {
      var res = await regedit()
      if (res.code == 0) {
        this.regeditFlag = false
        const loading = this.$loading({
          lock: true,
          text: '请在企业认证完成后刷新本页面',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)',
        })
        window.open(res.data)
      } else {
        this.$baseMessage(res.msg, 'error')
      }
    },
    authAutoSign() {
      authAutoSign().then((res) => {
        if (res.code == 0) {
          const loading = this.$loading({
            lock: true,
            text: '请在签章完成后刷新本页面',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          window.open(res.data)
        }
      })
    },
    personalAuthentication() {
      personalAuthentication().then((res) => {
        if (res.code == 0) {
          const loading = this.$loading({
            lock: true,
            text: '请在个人认证完成后刷新本页面',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          window.open(res.data)
        } else {
          this.$baseMessage(res.msg, 'error')
        }
      })
    },
    close() {
      this.regeditVisible = false
    },
    checkAuthentications() {
      checkAuthentication().then((res) => {
        if (res.code == 0) {
          switch (res.data) {
            case 0:
              this.regeditFlag = false
              this.regeditStatus = '企业认证'
              break
            case 1:
              this.regeditFlag = false
              this.regeditStatus = '授权自动签章'
              break
            case 2:
              this.regeditFlag = false
              this.regeditStatus = '个人认证'
              break
            case 3:
              this.regeditFlag = false
              this.regeditStatus = '查看授权协议'
              break
            default:
              this.regeditFlag = false
              this.regeditStatus = '企业认证'
              break
          }
          this.regeditCode = res.data
        } else {
          this.regeditFlag = false
        }
      })
    },
    handleAddSign() {},
    handlecancel(row, status) {
      if (row.id) {
        this.$baseConfirm('确认进行撤消？', null, async () => {
          const { msg } = await revoke({ id: row.id, status: status })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
      }
    },
    handleAdd(row) {
      if (row) {
        this.$refs['edit'].showDia(row)
      } else {
        this.$refs['edit'].showDia()
      }
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handleView(row) {
      this.$refs['views'].showDia(row.id)
    },
    handleRenew(row) {
      this.handleAdd()
    },
    handleExport() {
      this.$baseConfirm('确认进行导出？', null, async () => {
        this.$baseMessage('导出成功', 'success')
      })
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    formatDate: function (row, column) {
      let data = row[column.property]
      if (data == null || data == '') {
        return null
      }
      let date = new Date(data)
      var o = {
        'M+': date.getMonth() + 1,
        'd+': date.getDate(),
        'h+': date.getHours(),
        'm+': date.getMinutes(),
        's+': date.getSeconds(),
        'q+': Math.floor((date.getMonth() + 3) / 3),
        S: date.getMilliseconds(),
      }
      var fmt = 'yyyy-MM-dd'
      if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(
          RegExp.$1,
          (date.getFullYear() + '').substr(4 - RegExp.$1.length)
        )
      }
      for (var k in o) {
        if (new RegExp('(' + k + ')').test(fmt)) {
          fmt = fmt.replace(
            RegExp.$1,
            RegExp.$1.length == 1
              ? o[k]
              : ('00' + o[k]).substr(('' + o[k]).length)
          )
        }
      }
      return fmt
    },
    getLevelList() {
      if (this.queryForm.category_id) {
        selectOrganizationLevel(this.queryForm.category_id).then((res) => {
          if (res.code == 0) {
            this.levelList = res.data
          }
        })
      } else {
        console.log('机构类别联动机构等级空')
      }
    },
    getQueryDate() {
      if (
        null == this.queryForm.start ||
        [] == this.queryForm.start ||
        '' == this.queryForm.start
      ) {
        this.queryForm.startTime = ''
        this.queryForm.endTime = ''
      } else {
        this.queryForm.startTime = this.dateFormat(this.queryForm.start[0])
        this.queryForm.endTime = this.dateFormat1(this.queryForm.start[1])
        console.log(this.queryForm.startTime)
      }
    },
    dateFormat(time) {
      return `${time.getFullYear()}-${
        time.getMonth() + 1 >= 10
          ? time.getMonth() + 1
          : '0' + (time.getMonth() + 1)
      }-${time.getDate() >= 10 ? time.getDate() : '0' + time.getDate()}`
    },
    dateFormat1(time) {
      return `${time.getFullYear()}-${
        time.getMonth() + 1 >= 10
          ? time.getMonth() + 1
          : '0' + (time.getMonth() + 1)
      }-${time.getDate() >= 10 ? time.getDate() + 1 : '0' + time.getDate() + 1}`
    },
    async fetchData(id) {
      this.listLoading = true
      const { data } = await getPageList(this.queryForm)
      console.log(data)
      this.list = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    downPdf(row) {
      console.log(row)
      window.open(row.download_url)
    },
  },
}
</script>