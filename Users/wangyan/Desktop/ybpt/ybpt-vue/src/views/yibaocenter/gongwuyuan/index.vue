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
                @click="queryData"
              >
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
                <el-form-item label="姓名">
                  <el-input
                    v-model.trim="queryForm.name"
                    @keyup.enter.native="queryData()"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="证件类型">
                  <el-select v-model="queryForm.cardType" clearable class="w">
                    <el-option label="居民身份证" value="1"></el-option>
                    <el-option
                      label="澳门特区护照/港澳居民来往内地通行证"
                      value="2"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="证件号">
                  <el-input
                    v-model.trim="queryForm.certno"
                    @keyup.enter.native="queryData()"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="单位名称" v-if="isCheck">
                  <el-input
                    v-model.trim="queryForm.emp_name"
                    @keyup.enter.native="queryData()"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="单位编码" v-if="isCheck">
                  <el-input
                    v-model.trim="queryForm.emp_code"
                    @keyup.enter.native="queryData()"
                  />
                </el-form-item>
              </el-col>
              <el-col
                :xs="24"
                :sm="24"
                :md="6"
                :lg="6"
                :xl="6"
                v-show="userinfo.org_code == '320399'"
              >
                <el-form-item label="参保统筹区">
                  <el-select
                    v-model="queryForm.insu_admdvs"
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
            <span class="tips">公务员信息列表</span>
            <div class="right">
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
                新增
              </el-button>
              <!--<el-button-->
              <!--type="danger"-->
              <!--icon="el-icon-delete"-->
              <!--@click="handleDelete"-->
              <!--&gt;-->
              <!--全部删除-->
              <!--</el-button>-->
              <el-button
                icon="el-icon-upload2"
                @click="handleTb()"
                v-if="isCheck"
              >
                人员信息同步
              </el-button>
              <el-button
                icon="el-icon-upload2"
                @click="updateMain()"
                v-if="isCheck"
              >
                关键信息更新
              </el-button>
              <!-- <el-button icon="el-icon-circle-plus-outline" @click="handleTb(2)">
                增量同步
              </el-button> -->
              <!--<el-button icon="el-icon-user" @click="handleUser">-->
              <!--用人单位-->
              <!--</el-button>-->
              <el-badge
                :value="num"
                class="item badge-item"
                v-if="isCheck"
                :max="99"
              >
                <el-button icon="el-icon-check" @click="handleShenhe">
                  单位人员审核
                </el-button>
              </el-badge>
              <el-badge
                :value="num"
                class="item badge-item"
                v-else="isCheck"
                :max="99"
              >
                <el-button size="mini" @click="handleShenhe">
                  单位人员审核记录
                </el-button>
              </el-badge>
              <el-button
                size="mini"
                plain
                type="primary"
                @click="excelDetails()"
              >
                信息导出
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
            @selection-change="setSelectRows"
            height="calc(100% - 50px)"
            v-loading="listLoading"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!--<el-table-column-->
            <!--show-overflow-tooltip-->
            <!--type="selection"-->
            <!--fixed="left"-->
            <!--&gt;</el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="id"
              label="序号"
              align="center"
              width="80px"
              fixed="left"
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
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="sex"
              width="120px"
              label="性别"
              align="center"
              :formatter="sexFormat"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="age"
              label="年龄"
              align="center"
              width="120px"
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
              width="200px"
              prop="certno"
              label="证件号"
              align="center"
            ></el-table-column>
            <el-table-column
              width="180px"
              prop="telephone"
              label="手机号"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="insu_admdvs"
              label="参保统筹区"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="admdvs"
              label="所属统筹区"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="emp_name"
              label="单位名称"
              width="280px"
              align="center"
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
              prop="emp_code"
              align="center"
              label="单位编码"
              width="280px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="emp_type"
              align="center"
              label="单位类型"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="insutype"
              align="center"
              label="参保险种"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="insu_state"
              align="center"
              label="参保状态"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="outside_flag"
              align="center"
              label="异地就医"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="death_flag"
              align="center"
              label="生存状态"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="qualifications"
              align="center"
              label="体检资格"
              width="120px"
            ></el-table-column>
            <!--<el-table-column-->
            <!--show-overflow-tooltip-->
            <!--align="center"-->
            <!--label="体检资格"-->
            <!--width="120px"-->
            <!--&gt;-->
            <!--<template #default="{ row }">-->
            <!--<el-switch-->
            <!--v-model="row.zhuangtai"-->
            <!--active-color="#13ce66"-->
            <!--inactive-color="#ff4949"-->
            <!--&gt;</el-switch>-->
            <!--</template>-->
            <!--</el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="280"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handleAdd(row)"
                  type="primary"
                  size="mini"
                  v-if="isShows(row)"
                  style="left: 0px"
                >
                  编辑
                </el-button>
                <el-button
                  plain
                  type="primary"
                  @click="handleDept(row)"
                  size="mini"
                  v-if="user_type == 4 && row.info_source == 1"
                  style="left: 0px"
                >
                  部门管理
                </el-button>
                <!-- <el-button plain @click="handleDelete(row)" type="danger" size="mini" v-if="isShows(row)">删除</el-button> -->
                <el-button
                  plain
                  @click="handleDelete(row)"
                  type="danger"
                  size="mini"
                >
                  删除
                </el-button>
                <el-button
                  plain
                  type="success"
                  size="mini"
                  icon="el-icon-plus"
                  @click="handleAddEd(row)"
                  v-show="isOpen"
                >
                  异地安置
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
    <usermana ref="usermana" @fetch-data="fetchData"></usermana>
    <usershenhe ref="usershenhe" @fetch-data="fetchData"></usershenhe>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <dept ref="dept" @fetch-data="fetchData"></dept>
    <edit1 ref="edit1" @fetch-data="fetchData"></edit1>
  </div>
</template>

<script>
  import {
    civInfoExcel,
    delCiviWorkerInfo,
    mainCivilData,
    selectList,
    shList,
    syncCivilData,
    getXzqh,
  } from '@/api_check/civilworker_info.js'
  import { relocatedConfig } from '@/api_check/relocatedInfo'
  import edit1 from '../../yongrendanwei/yidianzhi/components/edit'
  import Dept from './components/dept'
  import Edit from './components/edit'
  import Usermana from './components/usermana'
  import Usershenhe from './components/usershenhe'
  export default {
    name: 'Index',
    components: { Edit, Usermana, Usershenhe, edit1, Dept },
    data() {
      return {
        xzqhs: [],
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
          administrative_unit: '',
          pageNo: 1,
          pageSize: 10,
          emp_name: '',
          emp_code: '',
          status: '0',
        },
        user_type: '',
        isOpen: true,
        form: {
          isOpen: '0',
        },
        show: false,
        isCheck: false,
        userinfo: {},
        num: '',
        isShowTCQ: false,
      }
    },
    created() {
      this.getTotal()
      var userinfo = JSON.parse(localStorage.getItem('userinfo'))
      this.userinfo = userinfo
      var that = this
      that.fetchData()
      this.isConfig()
      that.user_type = userinfo.user_type
      if (that.user_type == 1) {
        that.isCheck = true
      } else {
        that.isCheck = false
      }
      this.getXzqhsApi()
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      async getXzqhsApi() {
        const { data } = await getXzqh()
        this.xzqhs = data
      },

      handleAddEd(row) {
        if (row.id) {
          this.$refs['edit1'].showDia(row, 'add')
        } else {
          this.$refs['edit1'].showDia()
        }
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd(row) {
        if (row.id) {
          this.$refs['edit'].showDia(row)
        } else {
          this.$refs['edit'].showDia()
        }
      },
      handleDept(row) {
        this.$refs['dept'].showDia(row)
      },
      handleUser() {
        this.$refs['usermana'].showDia()
      },
      handleShenhe() {
        this.$refs['usershenhe'].showDia()
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      handleDelete(row) {
        var that = this
        if (row.id) {
          that.$baseConfirm('你确定要删除当前项吗', null, async () => {
            delCiviWorkerInfo(row).then((res) => {
              if (res.code == 0) {
                that.fetchData()
                that.$baseMessage('操作成功', 'success')
              } else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          })
        } else {
          if (that.selectRows != '' && that.selectRows != null) {
            const ids = that.selectRows.map((item) => item.id).join()
            that.$baseConfirm('你确定要删除选中项吗', null, async () => {
              const { msg } = await doDelete({ ids })
              that.$baseMessage(msg, 'success')
              that.fetchData()
            })
          } else {
            that.$baseMessage('未选中任何行', 'error')
            return false
          }
        }
      },
      async isConfig() {
        this.listLoading = true
        const { data } = await relocatedConfig()
        this.form = data
        if (data.isOpen == '1' && this.userinfo.user_type != 1) {
          this.isOpen = false
        } else {
          this.isOpen = true
        }
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      updateMain() {
        var that = this
        this.$baseConfirm('你确定更新关键信息吗？', null, async () => {
          const loading = this.$loading({
            lock: true,
            text: '更新关键信息中...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          mainCivilData().then((res) => {
            loading.close()
            if (res.code == 0) {
              that.fetchData()
              this.$baseMessage('同步成功', 'success')
            } else {
              that.$baseMessage(res.msg, 'error')
            }
          })
        })
      },
      handleTb(val) {
        var that = this
        if (val == 1) {
          this.$baseConfirm('你确定要初始化同步数据吗？', null, async () => {
            const loading = this.$loading({
              lock: true,
              text: '数据初始化同步中...',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            syncCivilData({ type: '1' }).then((res) => {
              loading.close()
              if (res.code == 0) {
                that.fetchData()
                this.$baseMessage('同步成功', 'success')
              } else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          })
        } else {
          this.$baseConfirm('你确定要同步数据吗？', null, async () => {
            const loading = this.$loading({
              lock: true,
              text: '数据同步中...',
              spinner: 'el-icon-loading',
              background: 'rgba(0, 0, 0, 0.7)',
            })
            syncCivilData().then((res) => {
              loading.close()
              if (res.code == 0) {
                that.fetchData()
                this.$baseMessage('同步成功', 'success')
              } else {
                that.$baseMessage(res.msg, 'error')
              }
            })
          })
        }
      },
      fetchData() {
        var that = this
        selectList(that.queryForm).then((res) => {
          if (res.code == 0) {
            that.list = res.data.records
            that.total = res.data.total
            that.getTotal()
          }
        })
      },
      //获取表格序号
      getIndex($index) {
        //表格序号
        return (
          (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
        )
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
      isShows(row) {
        var that = this
        //医保中心
        if (that.user_type == 1) {
          return true
        } else if (that.user_type == 4) {
          if (row.info_source == 3) {
            return true
          }
        }
      },
      reseat() {
        this.queryForm.name = ''
        this.queryForm.certno = ''
        this.queryForm.cardType = ''
        this.queryForm.emp_name = ''
        this.queryForm.insu_admdvs = ''
        this.fetchData()
      },
      async excelDetails(orgId) {
        this.listLoading = true
        const res = await civInfoExcel(this.queryForm)
        let fileName = '公务员信息导出.xls'
        let objectUrl = URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.download = decodeURI(fileName)
        link.href = objectUrl
        link.click()
        this.listLoading = false
        this.$baseMessage('导出成功！', 'success')
      },
      getTotal() {
        var that = this
        shList(that.queryForm).then((res) => {
          if (res.code == 0) {
            that.num = res.data.total
          }
        })
      },
    },
  }
</script>
<style>
  .item {
    margin-right: 20px;
    margin-left: 20px;
  }

  .badge-item .el-badge__content.is-fixed {
    top: 2px;
  }
</style>
