<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :lg="24" :md="24" :sm="24" :xl="24" :xs="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">查询条件</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary" @click="queryData">
                查 询
              </el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat">
                重 置
              </el-button>
              &nbsp;&nbsp;
            </div>
          </div>
          <el-form label-width="80px">
            <el-row :gutter="20">
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item v-if="isAdmin &&
      (org_code === '320399' || userinfo.org_name == 'admin')
      " label="统筹区">
                  <el-select v-model="queryForm.fix_blng_admdvs" clearable style="width: 100%" @change="queryData()">
                    <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                      :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="医保编码">
                  <el-input v-model.trim="queryForm.org_code" placeholder="请输入" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="单位名称">
                  <el-input v-model.trim="queryForm.org_name" placeholder="请输入" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <!-- <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="项目名称">
                  <el-input v-model.trim="queryForm.project_name" placeholder="请输入" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col> -->
              <!-- <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="项目编码">
                  <el-input v-model.trim="queryForm.project_code" placeholder="请输入" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="审核状态">
                  <el-select v-model="queryForm.status" clearable style="width: 100%" @change="queryData()">
                    <el-option label="待初审" value="0"></el-option>
                    <el-option label="待复审" value="1"></el-option>
                    <el-option label="待终审" value="2"></el-option>
                    <el-option label="待生成受理书" value="3"></el-option>
                    <el-option label="完成" value="4"></el-option>
                    <el-option label="驳回" value="5"></el-option>
                  </el-select>
                </el-form-item>
              </el-col> -->
              <!-- <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="定点类型">
                  <el-select v-model="queryForm.user_type" clearable style="width: 100%" @change="queryData()">
                    <el-option label="定点" value="定点"></el-option>
                    <el-option label="非定点" value="非定点"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="申报类型">
                  <el-select v-model="queryForm.type" clearable style="width: 100%" @change="queryData()">
                    <el-option label="市场调节价项目" value="4"></el-option>
                    <el-option label="自设项目" value="6"></el-option>
                    <el-option label="新增医疗服务项目" value="7"></el-option>
                    <el-option label="市管未定价项目" value="8"></el-option>
                    <el-option label="其他病房床位" value="9"></el-option>
                    <el-option label="单人间、套房床位" value="10"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="医疗服务">
                  <el-select v-model="queryForm.natures" clearable style="width: 100%" @change="queryData()">
                    <el-option label="非公立自主定价" value="民办非营利"></el-option>
                    <el-option label="非公立营利性自设项目自主定价" value="营利性"></el-option>
                    <el-option label="公立医疗机构医疗服务项目自主定价" value="政府非营利"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="高于价格">
                  <el-select v-model="queryForm.high_price" clearable style="width: 100%" @change="queryData()">
                    <el-option label="是" value="1"></el-option>
                    <el-option label="否" value="0"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="创建时间">
         
                  <el-date-picker v-model="queryForm.queryDate" end-placeholder="结束日期" format="yyyy-MM-dd"
                    range-separator="至" start-placeholder="开始日期" style="width: 100%" type="daterange"
                    value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col v-if="isAdmin" :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item class="custemitem" label="受理书生成时间">

                  <el-date-picker v-model="queryForm.authDate" end-placeholder="结束日期" format="yyyy-MM-dd"
                    range-separator="至" start-placeholder="开始日期" style="width: 100%" type="daterange"
                    value-format="yyyy-MM-dd"></el-date-picker>
                </el-form-item>
              </el-col> -->
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :lg="24" :md="24" :sm="24" :xl="24" :xs="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">告知手续列表</span>
            <div class="right">
              <el-button v-if="user_type == '2' ||
      user_type == '3' ||
      user_type == '7' ||
      user_type == '8'
      " icon="el-icon-upload2" type="primary" @click="handlePhoto()">
                上传医疗许可证
              </el-button>
              <el-button v-if="user_type == '2' ||
      user_type == '3' ||
      user_type == '7' ||
      user_type == '8'
      " icon="el-icon-plus" type="success" @click="handleAdd">
                告知手续
              </el-button>
              <el-button v-if="isAdmin" icon="el-icon-upload2" type="primary" @click="handleExport">
                导出
              </el-button>
              <el-button v-if="isAdmin" icon="el-icon-s-check" type="primary" @click="batch">
                批量通过
              </el-button>
            </div>
          </div>
          <el-table ref="listTable" v-loading="listLoading" :data="tableData" :element-loading-text="elementLoadingText"
            :row-class-name="tableRowClassName" :row-key="getRowKey" border height="calc(100vh - 570px)"
            highlight-current-row style="width: 100%" @current-change="handleCurrentChange"
            @selection-change="handleSelectionChange">
            <el-table-column :reserve-selection="true" :selectable="selectable" type="selection"
              width="55"></el-table-column>
            <el-table-column align="center" label="序号" show-overflow-tooltip type="index"
              width="80px"></el-table-column>
            <el-table-column align="center" label="单位名称" prop="org_name" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column align="center" label="单位医保编码" prop="org_code" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column align="center" label="项目名称" prop="project_name" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column align="center" label="项目编码" prop="project_code" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column align="center" label="统筹区" prop="fix_blng_admdvs_name"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="协议等级" prop="aggrement_lv" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="经营性质" prop="natures" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="定点类型" prop="user_type" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="申报类型" prop="type" show-overflow-tooltip width="150px">
              <template #default="{ row }">
                <el-tag v-if="row.type == 1">西药</el-tag>
                <el-tag v-else-if="row.type == 2">中成药</el-tag>
                <el-tag v-else-if="row.type == 3">中药饮片</el-tag>
                <el-tag v-else-if="row.type == 4">市场调节价项目</el-tag>
                <el-tag v-else-if="row.type == 5">药品耗材</el-tag>
                <el-tag v-else-if="row.type == 6">自设项目</el-tag>
                <el-tag v-else-if="row.type == 7">新增医疗服务项目</el-tag>
                <el-tag v-else-if="row.type == 8">市管未定价项目</el-tag>
                <el-tag v-else-if="row.type == 9">其他病房床位</el-tag>
                <el-tag v-else-if="row.type == 10">单人间、套房床位</el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="审核状态" show-overflow-tooltip>
              <template #default="{ row }">
                <el-tag v-if="row.status == 0" type="info">待初审</el-tag>
                <el-tag v-else-if="row.status == 1">待复审</el-tag>
                <el-tag v-else-if="row.status == 2" type="warning">
                  待终审
                </el-tag>
                <el-tag v-else-if="row.status == 3" type="info">
                  待生成受理书
                </el-tag>
                <el-tag v-else-if="row.status == 4" type="success">完成</el-tag>
                <el-tag v-else-if="row.status == 5" type="danger">驳回</el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="是否高于公立医疗机构价格" show-overflow-tooltip width="200px">
              <template #default="{ row }">
                <el-tag v-if="row.high_price == 1" type="success">是</el-tag>
                <el-tag v-if="row.high_price == 0" type="danger">否</el-tag>
                <el-tag v-if="row.high_price == -1" type="info">无</el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="驳回原因" prop="reason" show-overflow-tooltip
              width="200px"></el-table-column>
            <el-table-column align="center" label="创建时间" prop="createTime" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column v-if="isShow" align="center" label="初审时间" prop="first_time" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column v-if="isShow" align="center" label="初审负责人" prop="first_trialer" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column v-if="isShow" align="center" label="复审时间" prop="second_time" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column v-if="isShow" align="center" label="复审负责人" prop="second_trialer" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column v-if="isShow" align="center" label="终审时间" prop="end_time" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column v-if="isShow" align="center" label="终审负责人" prop="end_trialer" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column align="center" fixed="right" label="操作" show-overflow-tooltip width="300px">
              <template #default="{ row }">
                <el-button v-if="roles.indexOf('examine-first') > -1 &&
      row.status == '0' &&
      row.isAudit == 'true'
      " plain size="mini" type="primary" @click="handleView(row)">
                  初审
                </el-button>
                <el-button v-if="roles.indexOf('examine-second') > -1 &&
      row.status == '1' &&
      row.isAudit == 'true'
      " plain size="mini" type="primary" @click="handleView(row)">
                  复审
                </el-button>
                <el-button v-if="roles.indexOf('examine-third') > -1 &&
      row.status == '2' &&
      row.isAudit == 'true'
      " plain size="mini" type="primary" @click="handleView(row)">
                  终审
                </el-button>

                <el-button plain size="mini" type="primary" @click="handleLookStateHospital(row, '1')">
                  查看
                </el-button>

                <el-button v-if="row.status == '3' &&
      roles.indexOf('examine-first') > -1 &&
      row.user_type != '非定点'
      " plain size="mini" type="primary" @click="handleSls(row)">
                  生成受理书
                </el-button>
                <el-button v-if="row.status == '4' && row.user_type != '非定点'" plain size="mini" type="primary"
                  @click="handleLookStateHospital(row, '2')">
                  查看受理书
                </el-button>
                <el-button v-if="user_type == '1'" plain size="mini" type="primary" @click="handleLookFirst(row, '2')">
                  查看许可证
                </el-button>
                <!--<el-button plain @click="handleDownload(row)" type="primary" size="mini">-->
                <!--下载-->
                <!--</el-button>-->
              </template>
            </el-table-column>
          </el-table>
          <el-pagination :current-page="queryForm.pageNo" :layout="layout" :page-size="queryForm.pageSize"
            :total="total" background @size-change="handleSizeChange"
            @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <first ref="first" @fetch-data="fetchData"></first>
    <lookFirst ref="lookFirst" @fetch-data="fetchData"></lookFirst>
    <add4 ref="add4" @fetch-data="queryData"></add4>
    <photo ref="photo" @fetch-data="fetchData"></photo>
    <detail ref="detail" @fetch-data="fetchData"></detail>
    <sls ref="sls" @fetch-data="queryData"></sls>
    <lookStateHospital ref="lookStateHospital" @fetch-data="fetchData"></lookStateHospital>
  </div>
</template>

<style>
.el-table .warning-row {
  background: oldlace;
}
</style>

<script>
import First from './components/first'
import LookFirst from './components/lookFirst'
import Add4 from './components/add4'
import Photo from './components/uploadPhoto'
import Detail from './components/detail'
import Sls from './components/sls'
import LookStateHospital from './components/lookStateHospital'
import { CodeToText, regionDataPlus } from 'element-china-area-data'
import {
  audit,
  batchAudit,
  bedViewPdf,
  sbApplyExport,
  sbApplyList,
  soloBedViewPdf,
  viewPdf,
} from '@/api/drug'
import { getDicts } from '@/api/dictManagement'
import { fileURL } from '@/config/setting.config'

export default {
  name: 'PrepareAuditing',
  components: {
    First,
    LookFirst,
    Add4,
    Photo,
    Detail,
    LookStateHospital,
    Sls,
  },
  data() {
    return {
      user_type: '',
      options: regionDataPlus,
      value1: '',
      value3: '',
      checked: false,
      isShow: false,
      isAdmin: false,
      userinfo: {},
      username: '',
      org_code: '',
      list: null,
      listLoading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      admdvs: null,
      elementLoadingText: '请稍等...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        project_name: '',
        project_code: '',
        org_name: '',
        org_code: '',
        type: '',
        status: '',
        fix_blng_admdvs: '',
        startTime: '',
        endTime: '',
        queryDate: [],
        authDate: [],
        startTimeAuth: '',
        endTimeAuth: '',
      },
      tableData: [],
      saveData: {
        reason: '',
        status: '',
      },
      roles: [],
      multipleSelection: [],
    }
  },
  created() {
    this.roles = JSON.parse(localStorage.getItem('roles'))
    this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
    console.log(this.userinfo)
    this.user_type = this.userinfo.user_type
    this.username = this.userinfo.username
    this.org_code = this.userinfo.org_code

    console.log(this.userinfo)
    if (this.userinfo.user_type == '1') {
      this.queryForm.fix_blng_admdvs = this.userinfo.org_code
    } else {
      this.queryForm.org_code = this.userinfo.org_code
    }
    if (this.user_type == '1') {
      this.isAdmin = true
      this.isShow = true
    } else {
      this.isAdmin = false
    }
    this.fetchData()
    this.getAdmdvs()
  },
  beforeDestroy() { },
  mounted() { },
  methods: {
    handleExport(row) {
      if (this.queryForm.queryDate.length > 0) {
        this.queryForm.startTime = this.queryForm.queryDate[0]
        this.queryForm.endTime = this.queryForm.queryDate[1]
        this.queryForm.queryDate = []
      }
      if (this.queryForm.authDate.length > 0) {
        this.queryForm.startTimeAuth = this.queryForm.authDate[0]
        this.queryForm.endTimeAuth = this.queryForm.authDate[1]
        this.queryForm.authDate = []
      }
      this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
        this.listLoading = true
        await sbApplyExport(this.queryForm).then((res) => {
          let fileName = '告知手续列表.xls'
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(fileName)
          link.href = objectUrl
          link.click()
          this.listLoading = false
          this.$baseMessage('导出成功！', 'success')
        })

        if (this.queryForm.startTime != '' && this.queryForm.endTime != '') {
          this.queryForm.queryDate.push(this.queryForm.startTime)
          this.queryForm.queryDate.push(this.queryForm.endTime)
        }

        if (
          this.queryForm.startTimeAuth != '' &&
          this.queryForm.endTime != ''
        ) {
          this.queryForm.queryDate.push(this.queryForm.startTimeAuth)
          this.queryForm.queryDate.push(this.queryForm.endTimeAuth)
        }
      })
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handlePhoto() {
      this.$refs['photo'].showEdit()
    },
    handleAdd() {
      this.$refs['first'].showEdit()
    },
    handleLookFirst(row) {
      this.$refs['lookFirst'].showEdit(row)
    },
    handleLookStateHospital(row, type) {
      if (row.user_type == '非定点') {
        row.pdf_path =
          fileURL +
          row.pdf_path +
          '?n=' +
          row.pdf_path.substring(
            row.pdf_path.lastIndexOf('/'),
            row.pdf_path.lastIndexOf('.')
          ) +
          '&download=0'
        row.down_pdf_path =
          fileURL +
          row.down_pdf_path +
          '?n=' +
          row.down_pdf_path.substring(
            row.down_pdf_path.lastIndexOf('/'),
            row.down_pdf_path.lastIndexOf('.')
          ) +
          '&download=1'
      }
      this.$refs['lookStateHospital'].showEdit(row, type)
    },
    handleSls(row) {
      // this.$refs['sls'].showDia(row)
      this.$baseConfirm('确认生成受理书？', null, async () => {
        this.listLoading = true
        console.log(row.type)
        if (row.type == 9) {
          bedViewPdf(row.id).then((res) => {
            if (res.code == 0) {
              this.listLoading = false
              this.queryData()
              this.$baseMessage('成功', 'success')
            } else {
              this.$baseMessage(res.msg, 'error')
            }
          })
        } else if (row.type == 10) {
          soloBedViewPdf(row.id).then((res) => {
            if (res.code == 0) {
              this.listLoading = false
              this.queryData()
              this.$baseMessage('成功', 'success')
            } else {
              this.$baseMessage(res.msg, 'error')
            }
          })
        } else {
          viewPdf(row.id).then((res) => {
            if (res.code == 0) {
              this.listLoading = false
              this.queryData()
              this.$baseMessage('成功', 'success')
            } else {
              this.$baseMessage(res.msg, 'error')
            }
          })
        }
      })
    },
    handleView(row) {
      this.saveData.reason = ''
      this.saveData.status = ''
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
            this.saveData.reason = ''
            audit(this.saveData).then((res) => {
              if (res.code == 0) {
                this.fetchData()
                this.$emit('fetch-data')
              }
            })
          })
          .catch((action) => {
            if (action === 'cancel') {
              this.$prompt('请输入驳回原因', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                inputErrorMessage: '格式不正确',
              })
                .then(({ value }) => {
                  this.saveData.id = row.id
                  this.saveData.reason = value
                  this.saveData.status = '5'
                  audit(this.saveData).then((res) => {
                    if (res.code == 0) {
                      this.fetchData()
                      this.$emit('fetch-data')
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
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },

    handleDownload(row) {
      this.$baseConfirm('确认下载？', null, async () => {
        this.$baseMessage('已下载，请稍后！', 'success')
      })
    },
    handleChange(value) {
      let cityNames = []
      value.forEach((e) => {
        cityNames.push(CodeToText[e])
      })
      this.citys = cityNames.join('/')
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
      sbApplyList(this.queryForm).then((res) => {
        if (res.code == 0) {
          this.tableData = res.data.records
          this.total = res.data.total
          this.listLoading = false
        }
      })
    },
    reseat() {
      this.queryForm.project_name = ''
      this.queryForm.project_code = ''
      this.queryForm.org_name = ''
      this.queryForm.org_code = ''
      this.queryForm.type = ''
      this.queryForm.status = ''
      this.queryForm.fix_blng_admdvs = ''
      this.queryForm.natures = ''
      this.queryForm.queryDate = ''
      this.queryData()
    },
    async getAdmdvs() {
      const res = await getDicts({ type: 'admdvs-area' })
      if (res.code == '0') {
        this.admdvs = res.data
      }
    },
    tableRowClassName({ row }) {
      if (row.high_price == '1') {
        return 'warning-row'
      }
      return ''
    },
    getRowKey(row) {
      return row.id
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      console.log(this.multipleSelection)
    },
    batch() {
      if (this.multipleSelection.length == 0) {
        this.$baseMessage('请先勾选', 'error')
      } else {
        this.$baseConfirm('确认批量通过？', null, async () => {
          var newWeightArr = this.multipleSelection.map((item, index) => {
            return item.id
          })

          batchAudit(newWeightArr.toString()).then((res) => {
            if (res.code == 0) {
              this.queryData()
              this.$refs.listTable.clearSelection()
              this.$baseMessage('成功', 'success')
            } else {
              this.$baseMessage(res.msg, 'error')
            }
          })
        })
      }
    },
    selectable(row, index) {
      if (row.status >= 3) {
        return false
      } else {
        return true
      }
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-form-item__content {
    line-height: 33px;
  }
}
</style>
