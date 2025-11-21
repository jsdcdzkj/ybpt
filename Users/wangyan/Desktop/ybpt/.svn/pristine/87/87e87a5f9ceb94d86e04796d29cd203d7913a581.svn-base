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
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="年份">
                  <el-date-picker
                    v-model="queryForm.year"
                    type="year"
                    placeholder="选择年"
                    clearable
                    value-format="yyyy"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="统筹区">
                  <el-select
                    v-model="queryForm.admdvs"
                    @keyup.enter.native="queryData"
                    style="width: 100%"
                    clearable
                  >
                    <el-option
                      v-for="i in xzqhs"
                      :label="i.emp_name"
                      :key="i.id"
                      :value="i.admdvs"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">通知列表</span>
            <div class="right">
              <!-- <el-button type="success" icon="el-icon-plus" @click="handleEdit">
                新增
              </el-button> -->
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
            @selection-change="setSelectRows"
            height="calc(100% - 50px)"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column
              label="序号"
              width="80"
              align="center"
              show-overflow-tooltip
            >
              <template #default="scope">
                {{
                  (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1
                }}
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="年份"
              prop="year"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="admdvsName"
              label="统筹区"
              width="300px"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="createTime"
              label="提交时间"
              width="160px"
              align="center"
            ></el-table-column>
            <!--                        <el-table-column show-overflow-tooltip prop="rangetype" label="范围类型" width="120px"-->
            <!--                            align="center"></el-table-column>-->

            <el-table-column
              show-overflow-tooltip
              prop="createName"
              label="提交人"
              width="120px"
              align="center"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="300"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="launch(row)"
                  :loading="loading"
                  type="success"
                  size="mini"
                >
                  查看
                </el-button>
                <el-button
                  plain
                  @click="downloadFile(row)"
                  type="primary"
                  size="mini"
                >
                  附件下载
                </el-button>
                <el-button plain @click="handleExport(row)" size="mini">
                  导出
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
  import {
    deleteById,
    selectPageList,
    launchApi,
    recallApi,
  } from '@/api_check/notice'
  import {
    getPageList2,
    expenseDetailExport,
    downLoad,
    expenseDetail,
  } from '@/api_check/expense'
  import { getXzqh } from '@/api_check/civilworker_info.js'
  import { fileURL } from '../../../config/setting.config'
  import { createAddDialog } from '../districtUnitReported/dialog'

  export default {
    name: 'noticeIndex',

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
        tableHeight: '100%',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          year: '',
          admdvs: '',
        },
        userinfo: {},
        isShowEdit: true,
        xzqhs: [],
      }
    },
    created() {
      // this.toolBarInit()
      this.fetchData()
      this.getXzqhsApi()
    },
    beforeDestroy() {},
    mounted() {
      // getHeight()
      // //增加监听事件，窗口变化时得到高度。
      // window.addEventListener('resize',this.getHeight,false)
    },
    methods: {
      async getXzqhsApi() {
        const { data } = await getXzqh()
        this.xzqhs = data
      },
      downloadFile(row) {
        downLoad({ id: row.id }).then((res) => {
          if (res.data) {
            const aEl = document.createElement('a')
            aEl.href = fileURL + res.data.fileUrl
            aEl.download = res.data.fileName
            aEl.click()
          }
        })
      },
      handleExport(row) {
        expenseDetailExport({ expenseId: row.id }).then((res) => {
          const url = URL.createObjectURL(res.data)
          const aEl = document.createElement('a')
          aEl.href = url
          aEl.download = '文件.xlsx'
          aEl.click()
          URL.revokeObjectURL(url)
        })
      },
      reset() {
        this.queryForm.pageNo = 1
        this.queryForm.year = ''
        this.queryForm.admdvs = ''
        this.fetchData()
      },
      printValue1() {
        if (null != this.queryForm.startEndTime) {
          this.queryForm.startTime =
            this.queryForm.startEndTime[0] + ' 00:00:00'
          this.queryForm.endTime = this.queryForm.startEndTime[1] + ' 23:59:59'
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
        const { data } = await getPageList2(this.queryForm)
        this.list = data.records
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },

      // getHeight(){
      // //获取浏览器高度并计算得到表格所用高度。
      //     this.tableHeight=document.documentElement.clientHeight-140
      //   },
      setSelectRows(val) {
        this.selectRows = val
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleEdit(row) {
        // createAddDialog({}, (form) => {
        //   console.log(form)
        //   saveExpense(form).then((res) => {
        //     this.fetchData()
        //   })
        // })
        // if (row.id) {
        //   this.$refs['edit'].showDia(row)
        // } else {
        //   this.$refs['edit'].showDia()
        // }
      },
      handleView(row) {
        //console.log(row.id)
        if (row.id) {
          this.$refs['dktj'].showDia(row)
        } else {
          this.$refs['dktj'].showDia()
        }
      },
      launch(row) {
        expenseDetail({ expenseId: row.id }).then((res) => {
          const it = {
            admdvs: '',
            name: '',
            unitId: 1,
            account: '',
            bank: '',
            cardNo: '',
            linkman: '',
            phone: '',
            year: '',
          }
          const form = {
            year: row.year,
            expenseDetails: res.data.map((item) => {
              return Object.keys(it).reduce((a, b) => {
                a[b] = item[b]
                return a
              }, {})
            }),
          }

          createAddDialog(
            { form, isDisabled: true, title: '区级查看详情' },
            async (form) => {}
          )
        })
      },
      handleRecall(row) {
        let that = this
        this.$baseConfirm('你确定要撤消发布吗', null, async () => {
          const loading = that.$loading({
            lock: true,
            text: '请稍等...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          let id = row.id
          let res = await recallApi(id)
          if (res.code == 0) {
            this.$baseMessage('成功撤销', 'success')
            this.fetchData()
            loading.close()
          }

          if (res.code == -1) {
            this.$baseMessage(res.msg, 'error')
            loading.close()
          }
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
      async handleDelete(row) {
        var that = this
        if (row.id) {
          that.$baseConfirm('你确定要删除当前项吗', null, async () => {
            const loading = that.$loading({
              lock: true,
              text: '请稍等...',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            let res = await deleteById(row.id)
            if (res.code == 0) {
              that.$baseMessage('成功删除', 'success')
              this.fetchData()
              loading.close()
            }
            if (res.code == -1) {
              that.$baseMessage(res.msg, 'error')
              loading.close()
            }
          })
        } else {
          if (that.selectRows != '' && that.selectRows != null) {
            const ids = that.selectRows.map((item) => item.id).join()
            that.$baseConfirm('你确定要删除选中项吗', null, async () => {
              const { msg } = await doDelete({ ids })
              that.$baseMessage(msg, 'success')
            })
          } else {
            that.$baseMessage('未选中任何行', 'error')
            return false
          }
        }
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
    },
  }
</script>
