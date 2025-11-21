<template>
  <el-drawer
    :title="title"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="80%"
    ref="drawer"
    append-to-body
  >
    <div class="drawer_content">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <vab-query-form>
            <vab-query-form-left-panel :span="24">
              <el-form :inline="true" :model="queryForm" @submit.native.prevent >
                <el-form-item label="单位名称" v-if="isCheck">
                  <el-input
                    v-model.trim="queryForm.name"
                    placeholder="单位名称"
                    clearable
                    @keyup.enter.native="fetchData()"
                  />
                </el-form-item>
<!--                <el-form-item label="单位名称" v-if="isCheck">-->
<!--                  <el-input-->
<!--                    v-model.trim="queryForm.emp_name"-->
<!--                    placeholder="单位名称"-->
<!--                    clearable-->
<!--                    @keyup.enter.native="fetchData()"-->
<!--                  />-->
<!--                </el-form-item>-->
<!--                <el-form-item label="单位编码" v-if="isCheck">-->
<!--                  <el-input-->
<!--                    v-model.trim="queryForm.emp_code"-->
<!--                    placeholder="单位编码"-->
<!--                    clearable-->
<!--                    @keyup.enter.native="fetchData()"-->
<!--                  />-->
<!--                </el-form-item>-->
                <el-form-item label="证件号码">
                  <el-input
                    v-model.trim="queryForm.certno"
                    placeholder="证件号码"
                    clearable
                    @keyup.enter.native="fetchData()"
                  />
                </el-form-item>
                <el-form-item label="审批状态">
                  <el-select v-model="queryForm.status" style="width: 100%" @change="queryData()">
                    <el-option label="全部" value=""></el-option>
                    <el-option label="待审核" value="0"></el-option>
                    <el-option label="审核通过" value="1"></el-option>
                    <el-option label="审核驳回" value="2"></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button
                    icon="el-icon-search"
                    type="primary"
                    @click="queryData"
                  >
                    查询
                  </el-button>
                  <el-button type="primary" @click="reseat">重置</el-button>
                </el-form-item>
              </el-form>
            </vab-query-form-left-panel>
          </vab-query-form>
          <!-- <el-row :span="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24"> -->
              <el-table
                :data="list"
                border
                stripe
                class="w"
                :element-loading-text="elementLoadingText"
                height="calc(100vh - 230px)"
              >
                <!-- <el-table-column
              show-overflow-tooltip
              type="selection"
              align="center"
            ></el-table-column> -->
                <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="id"-->
                <!--label="id"-->
                <!--align="center"-->
                <!--width="80px"-->
                <!--fixed="left"-->
                <!--&gt;</el-table-column>-->
                <el-table-column
                  show-overflow-tooltip
                  prop="name"
                  label="姓名"
                  width="120px"
                  align="center"
                  fixed="left"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="sex"
                  label="性别"
                  width="120px"
                  fixed="left"
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
                  prop="certno"
                  label="身份证号码"
                  width="190px"
                  align="center"
                ></el-table-column>
                <el-table-column
                    prop="telephone"
                    label="手机号"
                    width="190px"
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
                  width="200px"
                  align="center"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="emp_code"
                  label="单位编码"
                  width="120px"
                  align="center"
                ></el-table-column>

                <el-table-column
                  show-overflow-tooltip
                  prop="insutype"
                  label="参保险种"
                  width="200px"
                  align="center"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="insu_state"
                  label="参保状态"
                  align="center"
                  width="120px"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="outside_flag"
                  label="异地就医"
                  align="center"
                  width="120px"
                ></el-table-column>
                <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="death_flag"-->
                <!--label="死亡标志"-->
                <!--width="120px"-->
                <!--&gt;</el-table-column>-->
                <el-table-column
                  show-overflow-tooltip
                  prop="qualifications"
                  label="体检资格"
                  align="center"
                  width="120px"
                ></el-table-column>
                <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="datatime"-->
                <!--label="体检资格"-->
                <!--align="center"-->
                <!--width="120px"-->
                <!--&gt;-->
                <!--<template #default="{ row }">-->
                <!--<el-switch-->
                <!--v-model="row.username"-->
                <!--active-color="#13ce66"-->
                <!--inactive-color="#ff4949"-->
                <!--&gt;</el-switch>-->
                <!--</template>-->
                <!--</el-table-column>-->
                <!--<el-table-column-->
                <!--show-overflow-tooltip-->
                <!--prop="status"-->
                <!--label="审核状态"-->
                <!--width="120px"-->
                <!--:formatter="checkFormat"-->
                <!--&gt;</el-table-column>-->
                <el-table-column
                  show-overflow-tooltip
                  prop="verify_type"
                  label="审核类型"
                  align="center"
                  width="120px"
                  :formatter="typeFormat"
                ></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="status"
                  label="审核状态"
                  align="center"
                  width="120px"

                ><template #default="scope">
                  <el-tag type="info" v-show="scope.row.status == 0">待审</el-tag>
                  <el-tag type="success" v-show="scope.row.status == 1">通过</el-tag>
                  <el-tag type="danger" v-show="scope.row.status == 2">驳回</el-tag>
                </template></el-table-column>
                <el-table-column
                  show-overflow-tooltip
                  prop="reason"
                  label="原因"
                  width="120px"
                ></el-table-column>
               
              </el-table>
              <el-pagination
                background
                :current-page="queryForm.pageNo"
                :page-size="queryForm.pageSize"
                :layout="layout"
                :total="total"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange"
              ></el-pagination>
       
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm" type="primary">关 闭</el-button>
        <!-- <el-button
          type="primary"
          @click="$refs.drawer.closeDrawer()"
          :loading="loading"
        >
          {{ loading ? '保存中 ...' : '保 存' }}
        </el-button> -->
      </div>
    </div>    
  </el-drawer>
</template>

<script>
import { shList, check } from '@/api_check/civilworker_info.js'
export default {
  name: 'useredit',
  components: {},
  data() {
    return {
      list: [],
      form: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        status: '0',
        name: '',
        emp_name: '',
        emp_code: '',
        certno: '',
      },
      title: '',
      user_type: '',
      dialog: false,
      loading: false,
      isCheck: false,
      formLabelWidth: '100px',
      timer: null,
      saveData: {},
    }
  },
  created() {
      var that = this ;
      var userinfo = JSON.parse(localStorage.getItem("userinfo"));
      that.user_type = userinfo.user_type;
      if(that.user_type == 1){
          that.isCheck = true ;
      }else {
          that.isCheck = false ;
      }
  },
  mounted() {},
  methods: {
    showDia(row) {
      this.fetchData()
      if (!row) {
        this.title = '流水明细'
      } else {
        this.title = '编辑'
        // this.form = Object.assign({}, row)
      }
      this.dialog = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialog = false
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
      this.close()
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleUser(row) {
      this.saveData.reason ="" ;
      if (row.id) {
        this.$confirm('审核通过此条记录, 是否继续?', '提示', {
          distinguishCancelAndClose: true,
          confirmButtonText: '通过',
          cancelButtonText: '驳回',
          type: 'warning',
          center: true,
        })
          .then(() => {
            this.saveData.id = row.id
            this.saveData.status = '1'
            check(this.saveData).then((res) => {
              if (res.code == 0) {
                this.fetchData();
                this.$emit('fetch-data');
              }
            })
          })
          .catch( action => {
            if (action === 'cancel'){
              this.$prompt('请输入驳回原因', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                //inputPattern: /[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/,
                inputErrorMessage: '格式不正确',
              })
              .then(({ value }) => {
                this.saveData.id = row.id
                this.saveData.status = '2'
                this.saveData.reason = value
                check(this.saveData).then((res) => {
                  if (res.code == 0) {
                    this.fetchData();
                    this.$emit('fetch-data');
                  }
                })
                this.$message({
                  type: 'success',
                  message: '已驳回，驳回原因是: ' + value,
                })
              })
              .catch(() => {
                this.$message({
                  type: 'info',
                  message: '取消输入',
                })
              })
            }


          })
      }
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    fetchData() {
      var that = this
      shList(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = res.data.records
          that.total = res.data.total
        }
      })
    },
    handleClose(done) {
      if (this.loading) {
        return
      }
      this.$confirm('确定要保存信息吗？')
        .then((_) => {
          this.loading = true
          this.timer = setTimeout(() => {
            done()
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false
            }, 400)
          }, 2000)
        })
        .catch((_) => {})
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
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
    typeFormat(row, column) {
      var statusW
      switch (row.verify_type) {
        case '1':
          statusW = '新增'
          break
        case '2':
          statusW = '删除'
          break
        case '3':
          statusW = '修改'
          break
      }
      return statusW
    },
    statusFormat(row, column) {
      var statusW
      switch (row.status) {
        case '0':
          statusW = '待审核'
          break
        case '1':
          statusW = '审核通过'
          break
        case '2':
          statusW = '审核驳回'
          break
      }
      return statusW
    },
    reseat() {
      this.queryForm.emp_name = ''
      this.queryForm.emp_code = ''
      this.queryForm.certno = ''
      this.queryForm.status = ''
      this.fetchData()
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-table {
    th.el-table__cell {
      background-color: #f2f8fd !important;
    }
  }
}
</style>