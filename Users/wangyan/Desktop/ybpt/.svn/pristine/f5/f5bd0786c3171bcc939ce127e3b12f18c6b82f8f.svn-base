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
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="userinfo.user_type == 1 ">
                <el-form-item label="类型" >
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.type"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="userinfo.user_type == 1 ">
                <el-form-item label="国家医保信息编码">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.fixmedins_code"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="userinfo.user_type == 1 ">
                <el-form-item label="名称">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.fixmedins_name"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="级别">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.aggrement_lv"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="地区">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.admdvs"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="月份">
                  <el-date-picker
                    style="width: 100%"
                    v-model="value1"
                    type="monthrange"
                    value-format="yyyy-MM"
                    @change="printValue1"
                    range-separator="至"
                    start-placeholder="开始月份"
                    end-placeholder="结束月份"
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
            <span class="tips">考核列表</span>
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
              label="类型"
              prop="type"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="国家医保信息编码"
              width="160"
              prop="fixmedins_code"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              width="280"
              label="名称"
              prop="fixmedins_name"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="级别"
              prop="aggrement_lv"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="地区"
              prop="admdvs"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="月份"
              prop="month"
              align="center"
            ></el-table-column>
            <el-table-column label="医保码结算率" align="center">
              <el-table-column
                show-overflow-tooltip
                label="医保码结算率"
                prop="medicareCodeSettlementRate"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="奖惩金额（万元）"
                prop="medicareCodeMoney"
                align="center"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="医保码全流程应用" align="center">
              <el-table-column
                show-overflow-tooltip
                label="全流程应用情况"
                prop="applyCondition"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="奖惩金额（万元）"
                prop="applyMoney"
                align="center"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="移动支付应用" align="center">
              <el-table-column
                show-overflow-tooltip
                width="140"
                label="奖惩金额（万元）"
                prop="mobilePaymentMoney"
                align="center"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="电子处方流转平台接入" align="center">
              <el-table-column
                show-overflow-tooltip
                width="170"
                label="奖惩金额（万元）"
                prop="electronicPrescriptionMoney"
                align="center"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="国家编码贯标（非标率）" align="center">
              <el-table-column
                show-overflow-tooltip
                label="开单医师"
                prop="nationBillingPhysician"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="手术操作"
                prop="nationOperation"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="西药中成药"
                prop="westernMedicine"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="中药饮片"
                prop="chinesePiece"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="自制剂"
                prop="selfPreparation"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="医用耗材"
                prop="medicalConsumables"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="医疗服务项目"
                prop="medicalServiceItem"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="奖惩金额（万元）"
                prop="nationMoney"
                align="center"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="药品追溯码采集" align="center">
              <el-table-column
                show-overflow-tooltip
                width="140"
                label="奖惩金额（万元）"
                prop="traceableCodeMoney"
                align="center"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="其他接口改造和数据上传任务" align="center">
              <el-table-column
                show-overflow-tooltip
                width="210"
                label="奖惩金额（万元）"
                prop="uploadPortMoney"
                align="center"
              ></el-table-column>
            </el-table-column>
            <el-table-column label="工作试点示范" align="center">
              <el-table-column
                show-overflow-tooltip
                label="奖惩金额（万元）"
                prop="workPilotMoney"
                align="center"
              ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                label="备注"
                prop="remark"
                align="center"
              ></el-table-column>
            </el-table-column>
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
    selectPageList,
    importFile,
    delInfoAssessment,
    down,
  } from '@/api_check/examine'

  export default {
    name: 'examineIndex',
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
          type: '',
          fixmedins_code: '',
          fixmedins_name: '',
          aggrement_lv: '',
          admdvs: '',
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
        this.queryForm.type = ''
        this.queryForm.fixmedins_code = ''
        this.queryForm.fixmedins_name = ''
        this.queryForm.aggrement_lv = ''
        this.queryForm.admdvs = ''
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
        const { data } = await selectPageList(this.queryForm)
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
          fileURL + '/file/template/信息化标准化考核奖惩模板.xlsx'
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
          await down(this.queryForm).then((res) => {
            let fileName = '标准化信息化考核数据导出.xlsx'
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
          that.$baseConfirm('确认删除当前考核数据？', '提示', async () => {
            delInfoAssessment(row).then((res) => {
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
