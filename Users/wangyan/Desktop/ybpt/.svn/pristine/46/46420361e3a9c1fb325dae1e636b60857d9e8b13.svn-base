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
          <el-form label-width="140px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="userinfo.user_type == 1 ">
                <el-form-item label="定点医药机构编号">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.fixmedins_code"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="userinfo.user_type == 1 ">
                <el-form-item label="定点医药机构名称">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.fixmedins_name"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="定点医药机构批次流水号" class="custemitem">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.batchSerialNumber"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item
                  label="定点医药机构商品销售流水号"
                  class="custemitem"
                >
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.salesSerialNumber"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="就诊ID">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.mdtrt_id"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="药品追溯码">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.drugTracingCode"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="经办时间">
                  <el-date-picker
                    style="width: 100%"
                    v-model="value1"
                    type="daterange"
                    value-format="yyyy-MM-dd"
                    @change="printValue1"
                    range-separator="至"
                    start-placeholder="开始时间"
                    end-placeholder="结束时间"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card table_card" shadow="never">
          <div slot="header">
            <span class="tips">追溯码列表</span>
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
              label="追溯流水号"
              width="180"
              prop="traceTheSerialNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="定点医药机构编号"
              width="160"
              prop="fixmedins_code"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              width="280"
              label="定点医药机构名称"
              prop="fixmedins_name"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="医疗目录编码"
              width="130"
              prop="med_list_codg"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="医药机构目录编码"
              width="160"
              prop="medins_list_codg"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="医药机构目录名称"
              width="160"
              prop="medins_list_name"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="定点医药机构批次流水号"
              width="210"
              prop="batchSerialNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="定点医药机构商品销售流水号"
              width="220"
              prop="salesSerialNumber"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="就诊ID"
              width="160"
              prop="mdtrt_id"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="就诊结算类型"
              width="120"
              prop="settlementType"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="记账流水号"
              width="120"
              prop="account_seria_number"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              width="120"
              label="药品追溯码"
              prop="drugTracingCode"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="经办人ID"
              prop="opter_id"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="经办人姓名"
              width="110"
              prop="opter_name"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="经办时间"
              width="160"
              prop="opt_time"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="经办机构编号"
              width="140"
              prop="optins_no"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="统筹区编号"
              width="140"
              prop="poolarea_no"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="拆零标志"
              prop="dismantlingMark"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="人员编号"
              width="160"
              prop="psn_no"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="人员证件类型"
              width="120"
              prop="psn_cert_type"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="证件号码"
              width="160"
              prop="certno"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="人员姓名"
              prop="name"
              align="center"
            ></el-table-column>
            <el-table-column
              v-if="userinfo.user_type == 1 && userinfo.org_code == 320399"
              show-overflow-tooltip
              label="操作"
              width="160"
              fixed="right"
              align="center"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handleEdit(row)"
                  type="primary"
                  size="mini"
                >
                  编辑
                </el-button>
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

    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
  import Edit from './components/edit'
  import { fileURL } from '@/config/setting.config'
  import {
    selectList,
    importFile,
    delTraceableCode,
    traceableCodeExport,
  } from '@/api_check/traceability'

  export default {
    name: 'traceability',
    components: { Edit },

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
          fixmedins_code: '',
          fixmedins_name: '',
          batchSerialNumber: '',
          salesSerialNumber: '',
          mdtrt_id: '',
          drugTracingCode: '',
          startTime: '',
          endTime: '',
        },
        value1: [],
        userinfo: {},
        isShowEdit: true,
      }
    },
    created() {
      // this.toolBarInit()
      this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
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
        this.queryForm.fixmedins_code = ''
        this.queryForm.fixmedins_name = ''
        this.queryForm.batchSerialNumber = ''
        this.queryForm.salesSerialNumber = ''
        this.queryForm.mdtrt_id = ''
        this.queryForm.drugTracingCode = ''
        this.queryForm.startTime = ''
        this.queryForm.endTime = ''
        this.value1 = []
        this.fetchData()
      },
      printValue1() {
        if (null != this.value1) {
          this.queryForm.startTime = this.value1[0]
          this.queryForm.endTime = this.value1[1]
        } else {
          this.queryForm.startTime = ''
          this.queryForm.endTime = ''
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
      handleEdit(row) {
        this.$refs['edit'].showDia(row)
      },
      downloadModel() {
        self.location.href =
          fileURL + '/file/template/追溯码重复上传情况模版.xlsx'
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

      handleExport(row) {
        this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
          this.listLoading = true
          await traceableCodeExport(this.queryForm).then((res) => {
            let fileName = '追溯码重复上传情况数据导出.xlsx'
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
          that.$baseConfirm('确认删除当前数据？', '提示', async () => {
            delTraceableCode(row).then((res) => {
              if (res.code == 0) {
                that.fetchData()
                that.$baseMessage('操作成功', 'success')
              } else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          })
        }
      },

      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
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
