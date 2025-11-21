<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button
                icon="el-icon-search"
                type="primary"
                @click="queryData()"
              >
                查 询
              </el-button>
              <el-button icon="el-icon-refresh-left " @click="fetchData1()">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="体检机构">
                  <el-input
                    v-model.trim="queryForm.uo_name"
                    placeholder="体检机构"
                    @keyup.enter.native="fetchData()"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="套餐名称">
                  <el-input
                    v-model.trim="queryForm.pack_name"
                    placeholder="套餐名称"
                    @keyup.enter.native="fetchData()"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">线上批量预约列表</span>
            <!-- <el-upload class="upload-demo" action="https://jsonplaceholder.typicode.com/posts/"
              :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3"
              :on-exceed="handleExceed" :file-list="fileList">
              <el-button size="small" type="primary">点击上传</el-button>
              <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>
            </el-upload> -->
            <el-button
              class="right"
              type="primary"
              icon="el-icon-upload2"
              @click="handleExport"
            >
              <!-- <a style="color:aliceblue"></a> -->
              导出
            </el-button>

            <el-button
              type="success"
              class="right"
              style="right: 100px"
              icon="el-icon-plus"
              @click="handleAdd"
            >
              新增
            </el-button>
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
            height="calc(100% - 50px)"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
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
              label="套餐年份"
              align="center"
              prop="pack_year"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="pack_name"
              width="200px"
              label="套餐名称"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="oname"
              label="体检机构"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="start_time"
              label="预约时间"
              align="center"
              width="260px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="is_personal"
              label="预约方式"
              align="center"
              width="260px"
              :formatter="isPersonal"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="240"
              align="right"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handleAdd(row)"
                  type="success"
                  size="mini"
                  v-show="show(row)"
                >
                  继续添加
                </el-button>
                <el-button
                  plain
                  @click="handlecancel(row)"
                  type="warning"
                  size="mini"
                  v-show="show(row)"
                >
                  撤销
                </el-button>
                <el-button
                  plain
                  @click="handlechuli(row)"
                  type="primary"
                  size="mini"
                >
                  详情
                </el-button>
                <!-- <el-button plain @click="handleAdd(row)" type="success" size="mini" icon="el-icon-edit">
                </el-button> -->
                <!-- <el-button plain @click="handleDelete(row)" type="danger" size="mini" icon="el-icon-delete">
                </el-button> -->
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
    <views ref="views" @fetch-data="fetchData" :list4="list4"></views>
    <!-- <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views> -->
  </div>
</template>

<script>
  import {
    backoutSubscribe,
    findCivilworkerNotIn,
    findEmpSubscribeRecord,
    yyExport,
  } from '@/api_check/empSubscribeRecord'
  import { findCiviWorkerByRid } from '@/api_check/personSubscribeRecord'
  import { getList } from '@/api_check/taocan'
  import Cardnum from '@/components/cardno'
  import config from '../../../config/setting.config'
  import Edit from './components/edit'
  import Views from './components/view'
  export default {
    name: 'Index',
    components: { Cardnum, Edit, Views },
    data() {
      return {
        value1: '',
        checked: false,
        isShow: false,
        list: null,
        list1: null,
        list2: null,
        list3: null,
        list4: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        fileList: [],
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          pack_name: null,
          rid: '',
          uo_id: '',
          uo_name: null,
        },
        sourceForm: {
          pack_source: 2,
          if_open: 1,
          status: 1,
        },
        workerForm: {
          id: '',
          pageNo: 1,
          pageSize: 1000,
        },
      }
    },
    created() {
      this.fetchData()
      // this.fetchData2()
      // this.fetchData3()
      // this.fetchData4()
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      show(row, index) {
        if (row.is_personal == 1) {
          return false
        } else {
          return true
        }
      },
      isPersonal(row, index) {
        if (row.is_personal == 1) {
          return '个人'
        } else {
          return '单位'
        }
      },
      handleExport() {
        console.log(config)
        var userinfo = JSON.parse(localStorage.getItem('userinfo'))
        if (userinfo.id != 1) {
          this.queryForm.uo_id = userinfo.org_code
        }
        this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
          this.listLoading = true
          // const res = await yyExport(this.queryForm);
          await yyExport(this.queryForm).then((res) => {
            let fileName = '线上批量预约列表导出.xls'
            let objectUrl = URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.download = decodeURI(fileName)
            link.href = objectUrl
            link.click()
            this.listLoading = false
            this.$baseMessage('导出成功！', 'success')
          })
          // this.fetchData()
        })
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      openwin() {
        this.$refs['cardnum'].showDia()
      },
      handleAdd(row) {
        this.$refs['edit'].showDia(row)
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
        this.queryForm.rid = row.id
        findCiviWorkerByRid(this.queryForm).then((res) => {
          this.list4 = res.data
          console.log(this.list4)
        })
        this.$refs['views'].showDia()
      },
      handlecancel(row) {
        console.log(row)
        if (row.id) {
          //年
          let year = new Date().getFullYear()
          //月份是从0月开始获取的，所以要+1;
          let month = new Date().getMonth() + 1
          //日
          let day = new Date().getDate()
          let time = year + '-' + month + '-' + day
          if (this.dateDiff(time, row.start_time) < 3) {
            this.$baseConfirm(
              '距离体检时间少于三天，无法进行撤销！',
              null,
              async () => {
                this.fetchData()
              }
            )
          } else {
            this.$baseConfirm('确认进行撤消？', null, async () => {
              const { msg } = await backoutSubscribe(row.id)
              this.$baseMessage(msg, 'success')
              this.fetchData()
              // this.fetchData4()
            })
          }
        } else {
        }
      },
      handleDelete(row) {
        if (row.id) {
          this.$baseConfirm('确认要删除吗？', null, async () => {
            const { msg } = await doDelete({ ids: row.id })
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
        }
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      async fetchData() {
        this.listLoading = true
        var userinfo = JSON.parse(localStorage.getItem('userinfo'))
        if (userinfo.id != 1) {
          this.queryForm.uo_id = userinfo.org_code
        }
        const { data, totalCount } = await findEmpSubscribeRecord(
          this.queryForm
        )
        this.list = data.records
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async fetchData1() {
        this.queryForm.pack_name = ''
        this.queryForm.uo_name = ''
        this.queryForm.pageNo = 1
        this.queryForm.pageSize = 10
        this.listLoading = true
        const { data, totalCount } = await findEmpSubscribeRecord(
          this.queryForm
        )
        this.list = data.records
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      // async fetchData2() {
      //   this.listLoading = true
      //   const { data, totalCount } = await getList(this.sourceForm)
      //   this.list1 = data.records
      //   setTimeout(() => {
      //     this.listLoading = false
      //   }, 300)
      // },
      // async fetchData3() {
      //   this.listLoading = true
      //   this.sourceForm.pack_source = 1
      //   const { data, totalCount } = await getList(this.sourceForm)
      //   this.list2 = data.records
      //   this.total = totalCount
      //   setTimeout(() => {
      //     this.listLoading = false
      //   }, 300)
      // },

      // 修改
      // async fetchData4() {
      //   this.listLoading = true
      //   const a = {
      //     id: JSON.parse(localStorage.getItem('userinfo')).org_code,
      //     year: this.year,
      //     index: 1,
      //     size: 10,
      //   }
      //   const { data } = await findCivilworkerNotIn(a)
      //   this.list3 = data.records
      //   setTimeout(() => {
      //     this.listLoading = false
      //   }, 300)
      // },
      // 计算时间差
      dateDiff(date1, date2) {
        let date1Str = date1.split('-') //将日期字符串分隔为数组,数组元素分别为年.月.日
        //根据年 . 月 . 日的值创建Date对象
        let date1Obj = new Date(date1Str[0], date1Str[1] - 1, date1Str[2])
        let date2Str = date2.split('-')
        let date2Obj = new Date(date2Str[0], date2Str[1] - 1, date2Str[2])
        let t1 = date1Obj.getTime()
        let t2 = date2Obj.getTime()
        let dateTime = 1000 * 60 * 60 * 24 //每一天的毫秒数
        let minusDays = Math.floor((t2 - t1) / dateTime) //计算出两个日期的天数差
        let days = Math.abs(minusDays) //取绝对值
        return days
      },
      handleRemove(file, fileList) {
        console.log(file, fileList)
      },
      handlePreview(file) {
        console.log(file)
      },
      handleExceed(files, fileList) {
        this.$message.warning(
          `当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${
            files.length + fileList.length
          } 个文件`
        )
      },
      beforeRemove(file, fileList) {
        return this.$confirm(`确定移除 ${file.name}？`)
      },
    },
  }
</script>
