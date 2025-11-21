<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary" @click="query">
                查 询
              </el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="证件号">
                  <el-input
                    placeholder="请输入"
                    v-model.trim="queryForm.certno"
                    @keyup.enter.native="queryData()"
                  />
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="套餐年份">
                  <el-date-picker
                    placeholder="请选择"
                    v-model.trim="queryForm.year"
                    type="year"
                    format="yyyy"
                    value-format="yyyy"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecardts" shadow="never">
          <div slot="header">
            <span class="tips">预约记录</span>
            <div class="right"></div>
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
            @selection-change="setSelectRows"
            v-loading="listLoading"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!--            <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="id"
              label="序号"
              align="center"
              width="80px"
            >
              <template slot-scope="scope">
                <span v-text="getIndex(scope.$index)"></span>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="姓名"
              align="center"
              prop="name"
              width="80px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="sex"
              width="120px"
              label="性别"
              align="center"
              v-if="!querryShow"
              :formatter="sexFormat"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="age"
              label="年龄"
              align="center"
              width="120px"
              v-if="!querryShow"
            ></el-table-column>
            <el-table-column
              width="200px"
              prop="cardType"
              label="证件类型"
              align="center"
            >
              <template slot-scope="scope">
                <!-- 1:居民身份证
                2:澳门特区护照/港澳居民来往内地通行证 -->
                <span
                  v-text="
                    scope.row.cardType == 2
                      ? '澳门特区护照/港澳居民来往内地通行证'
                      : '居民身份证'
                  "
                ></span>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="证件号"
              align="center"
              prop="certno"
              width="180px"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="emp_name"
              minWidth="300px"
              label="单位名称"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="emp_code"
              align="center"
              label="单位编码"
              width="280px"
              v-if="!querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="dept_name"
              label="部门名称"
              width="120px"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="year"
              label="套餐年份"
              align="center"
              v-if="querryShow"
              width="80px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="org_name"
              label="体检机构"
              align="center"
              width="220px"
              v-if="querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="pack_name"
              label="套餐"
              align="center"
              width="200px"
              v-if="querryShow"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="apply_date"
              label="预约时间"
              align="left"
              width="100px"
              v-if="querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="sched"
              label="体检进度"
              align="center"
              width="100px"
              :formatter="Format"
              v-if="querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="isSettlement"
              label="是否结算"
              align="center"
              width="100px"
              :formatter="FormatJs"
              v-if="querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="settlementTime"
              label="结算时间"
              align="center"
              width="150px"
              v-if="querryShow"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="chargePhone"
              label="负责人电话"
              align="center"
              width="150px"
              v-if="querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="phone"
              label="个人手机号"
              align="center"
              width="150px"
              v-if="querryShow"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="checkup_time"
              label="体检时间"
              align="center"
              width="180px"
              v-if="querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="upload_time"
              label="上传时间"
              align="center"
              width="180px"
              v-if="querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="civil_is_del"
              label="备注"
              align="center"
              min-width="120px"
              :formatter="formatCivilIsDel"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="insu_admdvs"
              label="参保统筹区"
              align="center"
              width="120px"
              v-if="!querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="admdvs"
              label="所属统筹区"
              align="center"
              width="120px"
              v-if="!querryShow"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="insutype"
              align="center"
              label="参保险种"
              width="120px"
              v-if="!querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="insu_state"
              align="center"
              label="参保状态"
              width="120px"
              v-if="!querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="outside_flag"
              align="center"
              label="异地就医"
              width="120px"
              v-if="!querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="death_flag"
              align="center"
              label="生存状态"
              width="120px"
              v-if="!querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="qualifications"
              align="center"
              label="体检资格"
              width="120px"
              v-if="!querryShow"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop=""
              label="操作"
              align="center"
              width="200px"
              v-if="querryShow"
              fixed="right"
            >
              <template #default="scope" v-if="isShows()">
                <!--只能体检机构才展示操作按钮 -->
                <div v-if="isTJJG()">
                  <el-button
                    v-if="scope.row.sched == 0 || scope.row.sched == 2"
                    plain
                    @click="handleConfirm(scope.row)"
                    type="primary"
                    size="mini"
                  >
                    确认
                  </el-button>
                  <!--icon="el-icon-edit" -->

                  <el-upload
                    ref="upfile"
                    v-if="scope.row.sched == '1'"
                    style="display: inline"
                    :auto-upload="false"
                    :show-file-list="false"
                    accept=".pdf"
                    :on-change="handleChange"
                    on-error="handleError"
                    :on-success="handleSuccess"
                    action="#"
                  >
                    <el-button type="success">上传文件</el-button>
                  </el-upload>
                </div>
                <!--只能医保才展示操作按钮 -->
                <div>
                  <el-button
                    v-if="scope.row.sched == 3 && scope.row.isSettlement == 0"
                    plain
                    @click="handleJSConfirm(scope.row)"
                    type="primary"
                    size="mini"
                  >
                    确认结算
                  </el-button>
                </div>
                <!-- 只有管理员并且1.状态且2.未体检才显示撤销按钮 -->
                <el-button
                  plain
                  @click="handleBackOut(scope.row)"
                  type="danger"
                  size="mini"
                >
                  撤销
                </el-button>
                <!--<el-button  type="success" @click="upload">点击上传</el-button>-->
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
            v-if="querryShow"
          ></el-pagination>
          <el-pagination
            background
            :current-page="queryForm.pageNo"
            :page-size="queryForm.pageSize"
            :layout="layout"
            :total="total"
            @size-change="handleSizeChange2"
            @current-change="handleCurrentChange3"
            v-if="!querryShow"
          ></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
  import { findDeptInfo } from '@/api_check/dept_info.js'
  import {
    backOutByAdmin,
    conformById,
    jsConfirm,
    noAppointment,
    uploadFile,
    yyExport,
    yyselectList,
    settlementMore,
    backOut,
  } from '@/api_check/personSubscribeRecord.js'
  import Edit from './components/edit'

  export default {
    name: 'Index',
    components: { Edit },
    data() {
      return {
        name: '',
        fileList: [],
        value1: '',
        checked: false,
        isShow: false,
        list: null,
        listLoading: false,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        elementLoadingText: '正在加载...',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          name: '',
          emp_name: '',
          year: '',
          org_name: '',
          pack_name: '',
          sched: '',
          apply_date: '',
          times: null,
          certno: '',
          cardType: '',
          dept_no: '',
          isyy: '0',
          isSettlement: '', //是否结算
        },
        deptList: [],
        user_type: '',
        querryShow: true,
        dept: {
          dept_no: '',
          emp_code: '',
        },
        // isShow: true,
        showtime: false,
        userInfoYc: '',
      }
    },
    created() {
      console.log('localStorage.getItem("userinfo")------->')
      console.log(localStorage.getItem('userinfo'))
      console.log('JSON.parse(localStorage.getItem("userinfo"))------->')
      console.log(JSON.parse(localStorage.getItem('userinfo')))

      var userinfo = JSON.parse(localStorage.getItem('userinfo'))
      this.userInfoYc = JSON.parse(localStorage.getItem('userinfo'))
      var that = this
      that.user_type = userinfo.user_type
      if (that.user_type == '5') {
        that.isShow = false
      }
      this.fetchData()
      this.findDeptInfo()
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      formatCivilIsDel(row, column, cellValue) {
        if (cellValue === '1') {
          return '人员被删除'
        } else if (cellValue === '0') {
          return ''
        }
      },
      selectOrg() {
        let obj = {}
        obj = this.deptList.find((item) => {
          return item.id === this.form.dept_id
        })
        this.form.dept_name = obj.dept_name
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      openwin() {
        this.$refs['cardnum'].showDia()
      },
      handleAdd(row) {
        if (row.id) {
          this.$refs['edit'].showDia(row)
        } else {
          this.$refs['edit'].showDia()
        }
      },
      handleConfirm(row) {
        this.$baseConfirm('你确定要体检吗', null, async () => {
          const { msg } = conformById({ id: row.id }).then((res) => {
            if (res.code == 0) {
              this.$baseMessage('确认成功', 'success')
              this.fetchData()
            } else {
              this.$baseMessage(res.msg, 'success')
            }
          })

          // const { msg } = await doDelete({ ids: row.id })
          // this.fetchData()
        })
      },
      handleJSConfirm(row) {
        this.$baseConfirm('你确定要结算吗', null, async () => {
          const { msg } = jsConfirm({ id: row.id }).then((res) => {
            if (res.code == 0) {
              this.$baseMessage('确认成功', 'success')
              this.fetchData()
            } else {
              this.$baseMessage(res.msg, 'success')
            }
          })

          // const { msg } = await doDelete({ ids: row.id })
          // this.fetchData()
        })
      },
      handleUser(row) {
        this.$refs['usermana'].showDia(row.id)
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleSizeChange2(val) {
        this.queryForm.pageSize = val
        this.fetchData2()
      },

      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      handleCurrentChange3(val) {
        this.queryForm.pageNo = val
        this.fetchData2()
      },
      handlechuli(row) {
        this.$refs['views'].showDia(row.id)
      },
      handleDelete(row) {
        if (row.id) {
          this.$baseConfirm('你确定要删除当前项吗', null, async () => {
            const { msg } = await doDelete({ ids: row.id })
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          if (this.selectRows != '' && this.selectRows != null) {
            const ids = this.selectRows.map((item) => item.id).join()
            this.$baseConfirm('你确定要删除选中项吗', null, async () => {
              const { msg } = await doDelete({ ids })
              this.$baseMessage(msg, 'success')
              this.fetchData()
            })
          } else {
            this.$baseMessage('未选中任何行', 'error')
            return false
          }
        }
      },
      handleBackOut(row) {
        this.$baseConfirm('你确定要撤销吗', null, async () => {
          let object = { id: row.id }
          const { code, msg } = await backOut(object)
          if (code === 0) {
            this.$baseMessage(msg, 'success')
            this.fetchData()
          } else if (code === 1) {
            this.$baseMessage(msg, 'error')
            this.fetchData()
          }
        })
      },
      isShows(row) {
        var that = this
        //体检机构或者admin
        if (that.user_type == 5 || that.user_type == 1) {
          return true
        } else {
          return false
        }
      },
      isTJJG() {
        var that = this
        //体检机构
        if (that.user_type == 5) {
          return true
        } else {
          return false
        }
      },
      isYB() {
        var that = this
        //医保
        if (that.user_type == 1) {
          return true
        } else {
          return false
        }
      },

      isAdmin(sched) {
        if (this.userInfoYc.username === 'admin' && sched == 0) {
          return true
        } else if (this.userInfoYc.username === 'admin' && sched == 2) {
          return true
        } else {
          return false
        }
      },

      query() {
        var that = this
        if (that.queryForm.isyy == 1) {
          //未预约
          that.queryData2()
        } else {
          that.queryData()
        }
      },
      //批量结算
      moreSettlement() {
        var that = this
        if (that.queryForm.sched == 3) {
          this.$baseConfirm(
            '是否按照当前搜索条件进行批量结算?',
            null,
            async () => {
              settlementMore(that.queryForm).then((res) => {
                console.log(res)
                if (res.code == 0) {
                  this.$baseMessage(res.msg, 'success')
                  this.fetchData()
                } else {
                  this.$baseMessage(res.msg, 'error')
                  this.fetchData()
                }
              })
            }
          )
        } else {
          this.$baseMessage('请选择体检进度为已上传报告', 'error')
        }
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      queryData2() {
        this.queryForm.pageNo = 1
        this.fetchData2()
      },
      async handleExport() {
        this.listLoading = true
        // const res = await yyExport(this.queryForm);
        // const res = await yyExport(this.queryForm)
        await yyExport(this.queryForm).then((res) => {
          let fileName = '预约统计导出.xls'
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(fileName)
          link.href = objectUrl
          link.click()
          this.listLoading = false
          this.$baseMessage('导出成功！', 'success')
        })
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      check_id_card(code) {
        code = code.toUpperCase()

        // 前2位城市码
        let city = {
          11: '北京',
          12: '天津',
          13: '河北',
          14: '山西',
          15: '内蒙古',
          21: '辽宁',
          22: '吉林',
          23: '黑龙江',
          31: '上海',
          32: '江苏',
          33: '浙江',
          34: '安徽',
          35: '福建',
          36: '江西',
          37: '山东',
          41: '河南',
          42: '湖北',
          43: '湖南',
          44: '广东',
          45: '广西',
          46: '海南',
          50: '重庆',
          51: '四川',
          52: '贵州',
          53: '云南',
          54: '西藏',
          61: '陕西',
          62: '甘肃',
          63: '青海',
          64: '宁夏',
          65: '新疆',
          71: '台湾',
          81: '香港',
          82: '澳门',
          91: '国外 ',
        }
        // 加权因子
        let factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
        // 校验位
        let parity = [1, 0, 'X', 9, 8, 7, 6, 5, 4, 3, 2]
        // 身份证简单正则
        let Reg = /^\d{6}(18|19|20)?\d{2}((0[1-9])|(1[0-2]))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$/
        // 身份证号码数组
        let arr_code = code.split('')

        // 校验编码为空，简单正则，城市编码
        if (
          !code ||
          !Reg.test(code) ||
          !city[code.substr(0, 2)] ||
          code.length !== 18
        ) {
          return false
        }

        // 校验18位身份证需要验证最后一位校验位
        //∑(ai×Wi)(mod 11)
        let sum = 0
        for (let i = 0; i < 17; i++) {
          sum += arr_code[i] * factor[i]
        }
        if (parity[sum % 11] != arr_code[17]) {
          return false
        }

        return true
      },
      async fetchData() {
        var that = this
        // if (
        //   '' == that.queryForm.certno ||
        //   null == that.queryForm.certno ||
        //   (null != that.queryForm.certno &&
        //     that.check_id_card(that.queryForm.certno))
        // ) {
        //   yyselectList(that.queryForm).then((res) => {
        //     if (res.code == 0) {
        //       console.log(res)
        //       that.list = res.data.records
        //       that.total = res.data.total
        //     }
        //   })
        // } else {
        //   this.$baseMessage('身份证不符合验证规则！', 'error')
        // }
        yyselectList(that.queryForm).then((res) => {
          if (res.code == 0) {
            that.list = res.data.records
            that.total = res.data.total
          }
        })
      },
      findDeptInfo() {
        var userinfo = JSON.parse(localStorage.getItem('userinfo'))
        if (userinfo.user_type == 4) {
          this.dept.emp_code = userinfo.org_code
        }
        findDeptInfo(this.dept).then((res) => {
          this.deptList = res.data
        })
      },
      //获取表格序号
      getIndex($index) {
        //表格序号
        return (
          (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
        )
      },
      Format(row, column) {
        var statusW
        switch (row.sched) {
          case '0':
            statusW = '待体检'
            break
          case '1':
            statusW = '已体检'
            break
          case '2':
            statusW = '已过期'
            break
          case '3':
            statusW = '已上传报告'
            break
        }
        return statusW
      },
      FormatJs(row, column) {
        var statusW
        switch (row.isSettlement) {
          case '0':
            statusW = '未结算'
            break
          case '1':
            statusW = '已结算'
            break
        }
        return statusW
      },
      reseat() {
        var that = this
        that.queryForm.apply_date = ''
        that.queryForm.certno = ''
        that.queryForm.cardType = ''
        that.queryForm.emp_name = ''
        that.queryForm.year = ''
        that.queryForm.org_name = ''
        that.queryForm.sched = ''
        that.queryForm.times = null
      },
      async handleChange(file, fileList) {
        this.fileList = fileList
        // this.$refs.upfile.submit()
        // 限制上传文件的大小
        const isLt =
          file.size / (1024 * 5) >= 1 && file.size / (1024 * 1024 * 10) <= 1
        if (!isLt) {
          this.$message.error('上传文件大小不得小于5KB,不得大于10MB!')
          for (var i = 0; i < fileList.length; i++) {
            if (fileList[i].uid == uid) {
              fileList.splice(i, 1)
            }
          }
        }
        let fd = new FormData()
        fd.append('id', this.selectRows.id)
        this.fileList.forEach((item) => {
          //文件信息中raw才是真的文件
          fd.append('file', item.raw)
        })

        var result = await uploadFile(fd)
        if (result.data.code == 0) {
          this.$baseMessage('上传成功', 'success')
          console.info(
            '=========2342343242====success================================='
          )
          await this.fetchData()
          this.$refs.upfile.clearFiles()
        } else {
          this.$baseMessage(result.data.msg, 'error')
          console.info(
            '=========2342343242======error==============================='
          )
          this.fileList = []
        }
        console.info('界面返回 ')
        console.info(result)
        //
        // this.$baseMessage("上传成功", 'success')
        //
        // setTimeout(() => {
        //     this.fetchData()
        // }, 2000)
      },
      handleSuccess(res, file, filelist) {
        console.info(
          '=========2342343242=============success2========================'
        )
        this.fetchData()
      },
      handleError(res, file, filelist) {
        console.info(
          '=========2342343242=============error2========================'
        )
        this.fetchData()
      },
      async fetchData2() {
        var that = this
        if (
          '' == that.queryForm.year ||
          null == that.queryForm.year ||
          undefined == that.queryForm.year
        ) {
          that.$baseMessage('请选择年份', 'error')
        } else {
          noAppointment(that.queryForm).then((res) => {
            if (res.code == 0) {
              that.list = res.data.records
              that.total = res.data.total
            }
          })
        }
      },
      sexFormat(row, column) {
        var statusW
        switch (row.sex) {
          case '1':
            statusW = '男'
            break
          case '2':
            statusW = '女'
            break
        }
        return statusW
      },
      getSelectData() {
        var that = this
        that.reseat()
        if (that.queryForm.isyy == 1) {
          //未预约
          that.list = []
          that.total = 0
          that.querryShow = false
          that.showtime = false
        } else {
          that.querryShow = true

          that.queryData()
        }
      },
      isshowtime() {
        var that = this
        if (
          (that.queryForm.sched == 0 ||
            that.queryForm.sched == 1 ||
            that.queryForm.sched == 3) &&
          that.queryForm.isyy == 0
        ) {
          that.showtime = true
        } else {
          that.showtime = false
        }
      },
    },
  }
</script>
