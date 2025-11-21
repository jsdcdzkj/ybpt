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
              <el-button icon="el-icon-refresh-left" @click="reset">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="定点编号">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.fixedPointNumber"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="定点名称">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.fixedPointName"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="个人编号">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.personal_number"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="姓名">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.name"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="医疗类别">
                  <el-select
                    v-model="queryForm.medicalCategory"
                    placeholder="请选择"
                    style="width: 100%"
                    clearable
                  >
                    <el-option
                      v-for="item in levelList"
                      :key="item"
                      :label="item"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="下发时间">
                  <el-date-picker
                    style="width: 100%"
                    v-model="value1"
                    type="datetimerange"
                    value-format="yyyy-MM-dd HH:mm:ss"
                    :show-second="false"
                    @change="printValue1"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="批次号">
                  <el-select
                    v-model="queryForm.upload_no"
                    placeholder="请选择"
                    style="width: 100%"
                    clearable
                  >
                    <el-option
                      v-for="item in uploadList"
                      :key="item"
                      :label="item"
                      :value="item"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item
                  label="统筹区"
                  v-if="
                    userinfo.user_type == 1 &&
                    (userinfo.org_code === '320399' ||
                      userinfo.org_name == 'admin')
                  "
                >
                  <el-select
                    v-model="queryForm.admdvs"
                    clearable
                    style="width: 100%"
                    @change="queryData()"
                  >
                    <el-option
                      v-for="item in admdvs"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card table_card" shadow="never">
          <div slot="header">
            <span class="tips">非标数据列表</span>
            <div class="right">
              <el-button
                type="success"
                icon="el-icon-document"
                @click="downloadModel"
                v-if="userinfo.user_type == 1 && userinfo.org_code == 320399"
              >
                模板下载
              </el-button>
              <el-upload
                style="margin: 0 10px"
                ref="upfile"
                :auto-upload="false"
                :show-file-list="false"
                accept=".xlsx"
                :on-change="handleChange"
                :on-success="handleSuccess"
                action="#"
                :multiple="false"
                v-if="userinfo.user_type == 1 && userinfo.org_code == 320399"
              >
                <el-button type="success" icon="el-icon-download">
                  数据导入
                </el-button>
              </el-upload>
              <el-button
                type="success"
                icon="el-icon-upload2"
                @click="handleExport"
              >
                导出
              </el-button>
            </div>
          </div>
          <el-table
            ref="listTable"
            stripe
            :data="list"
            :element-loading-text="elementLoadingText"
            highlight-current-row
            border
            @current-change="handleCurrentChange"
            height="calc(100% - 50px)"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column label="序号" width="80" fixed align="center">
              <template #default="scope">
                {{
                  (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1
                }}
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="定点编号"
              min-width="160"
              prop="fixedPointNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="所处表名"
              width="160"
              prop="nameTable"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              width="280"
              label="定点名称"
              prop="fixedPointName"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="个人编号"
              min-width="160"
              prop="personal_number"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="姓名"
              width="120"
              prop="name"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="身份证号"
              width="160"
              prop="idNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="创建时间"
              prop="dataTime"
              width="160"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="就诊ID"
              min-width="160"
              prop="visitID"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="结算ID"
              min-width="160"
              prop="settlementID"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="错误代码"
              min-width="160"
              prop="errorCode"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="规则名称"
              min-width="160"
              prop="ruleName"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="医疗类别"
              min-width="160"
              prop="medicalCategory"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="批次号"
              min-width="160"
              prop="upload_no"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="下发时间"
              width="160"
              prop="deliveryTime"
              align="center"
            ></el-table-column>

            <el-table-column
              v-if="userinfo.user_type == 1 && userinfo.org_code == 320399"
              show-overflow-tooltip
              label="操作"
              width="100"
              fixed="right"
              align="center"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handleDelete(row)"
                  :loading="loading"
                  type="danger"
                  size="mini"
                >
                  {{ loading ? '删除中 ...' : '删除' }}
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
  </div>
</template>

<script>
  import { fileURL } from '@/config/setting.config'
  import {
    selectList,
    importFile,
    delInfoAssessment,
    down,
    templateDownload,
    getMedicalCategory,
    getUploadNo,
  } from '@/api_check/administer'
  import { getDicts } from '@/api/dictManagement'
  export default {
    name: 'administerIndex',
    components: {},

    data() {
      return {
        loading: false,
        checked: false,
        isShow: false,
        tableData: [],
        list: [],
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          fixedPointNumber: '',
          fixedPointName: '',
          personal_number: '',
          name: '',
          medicalCategory: '',
          start_time: '',
          end_time: '',
          upload_no: '',
        },
        value1: [],
        userinfo: {},
        isShowEdit: true,
        levelList: [],
        uploadList: [],
        admdvs: [],
      }
    },
    created() {
      // this.toolBarInit()

      this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
      if (this.userinfo.user_type == 1) {
        this.queryForm.admdvs = this.userinfo.org_code
      } else {
        this.queryForm.orgCode = this.userinfo.org_code
      }
      this.getAdmdvs()
      this.fetchData()
    },
    beforeDestroy() {},
    mounted() {
      // getHeight()
      // //增加监听事件，窗口变化时得到高度。
      // window.addEventListener('resize',this.getHeight,false)
    },
    methods: {
      reset() {
        this.queryForm.pageNo = 1
        this.queryForm.pageSize = 10
        this.queryForm.fixedPointNumber = ''
        this.queryForm.fixedPointName = ''
        this.queryForm.personal_number = ''
        this.queryForm.name = ''
        this.queryForm.medicalCategory = ''
        this.queryForm.start_time = ''
        this.queryForm.end_time = ''
        this.queryForm.upload_no = ''
        this.value1 = []
        this.fetchData()
      },
      printValue1() {
        if (null != this.value1) {
          this.queryForm.start_time = this.value1[0]
          this.queryForm.end_time = this.value1[1]
        } else {
          this.queryForm.start_time = ''
          this.queryForm.end_time = ''
        }
      },

      toolBarInit() {
        // this.userinfo = JSON.parse(localStorage.getItem('userinfo'));
        // console.log(this.userinfo)
        // if (this.userinfo.name === '管理员') {
        //     this.isShowEdit = true;
        // }
      },

      async fetchData() {
        this.listLoading = true
        const { data } = await selectList(this.queryForm)
        this.list = data.records
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },

      setSelectRows(val) {
        this.selectRows = val
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },

      async handleChange(file, fileList) {
        console.log('file', file)
        this.loading = true
        this.fileList = fileList
        let fd = new FormData()
        fd.append('file', file.raw)
        var result = await importFile(fd)
        if (result.data.code == 0) {
          this.queryData()
          this.$refs.upfile.clearFiles()
          this.$baseMessage('导入成功', 'success')
        } else {
          this.$refs.upfile.clearFiles()
          this.$baseMessage(result.data.msg, 'error')
        }
        this.dialogFormVisible = false
        this.loading = false
      },
      handleSuccess() {
        this.fetchData()
      },
      // downloadModel() {
      //   self.location.href =
      //     fileURL + '/file/template/信息化标准化考核奖惩模板.xlsx'
      // },
      downloadModel() {
        templateDownload(this.queryForm).then((res) => {
          let fileName = '数据治理非标数据模版.xlsx'
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(fileName)
          link.href = objectUrl
          link.click()
          this.$baseMessage('模版下载成功！', 'success')
        })
      },
      handleExport(row) {
        this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
          this.listLoading = true
          await down(this.queryForm).then((res) => {
            let fileName = '数据治理非标数据导出.xlsx'
            let objectUrl = URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.download = decodeURI(fileName)
            link.href = objectUrl
            link.click()
            this.listLoading = false
            this.$baseMessage('导出成功！', 'success')
          })
        })
      },

      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      handlechuli(row) {
        this.$refs['views'].showDia(row.id)
      },
      handleDelete(row) {
        var that = this
        if (row.id) {
          that.$baseConfirm('确认删除当前数据吗？', null, async () => {
            const { msg } = await delInfoAssessment({ id: row.id })
            that.$baseMessage(msg, 'success')
            that.fetchData()
          })
        }
      },

      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      async getAdmdvs() {
        const res = await getDicts({ type: 'admdvs-area' })
        if (res.code == '0') {
          this.admdvs = res.data
        }
        const levelListArry = await getMedicalCategory()
        this.levelList = levelListArry.data

        const uploadListArry = await getUploadNo()
        this.uploadList = uploadListArry.data
      },
    },
  }
</script>
<style lang="scss" scoped>
  .main-container {
    ::v-deep {
      .table_card {
        .el-card__body {
          height: calc(
            100vh - #{$base-nav-bar-height} - #{$base-tabs-bar-height} - #{$base-padding} -
              #{$base-padding} - 390px
          );
        }
      }
    }
  }
</style>
