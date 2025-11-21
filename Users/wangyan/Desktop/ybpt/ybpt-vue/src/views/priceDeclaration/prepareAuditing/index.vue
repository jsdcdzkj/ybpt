<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :lg="24" :md="24" :sm="24" :xl="24" :xs="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
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
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="统筹区" v-if="orgCode == 320399">
                  <el-select v-model="queryForm.fixBlngAdmdvs" clearable style="width: 100%" @change="queryData()"
                    placeholder="请选择统筹区">
                    <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                      :value="item.value"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="医保编码">
                  <el-input v-model.trim="queryForm.orgCode" placeholder="请输入医保编码" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="单位名称">
                  <el-input v-model.trim="queryForm.orgName" placeholder="请输入单位名称" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="国家医疗机构制剂代码">
                  <el-input v-model.trim="queryForm.nationalFormulaCode" placeholder="请输入国家医疗机构制剂代码"
                    @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="制剂注册单位">
                  <el-input v-model.trim="queryForm.registerCompanyName" placeholder="请输入制剂注册单位"
                    @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="审核状态">
                  <el-select v-model="queryForm.status" clearable style="width: 100%" placeholder="请选择审核状态">
                    <el-option label="待初审" value="0"></el-option>
                    <el-option label="待复审" value="1"></el-option>
                    <el-option label="待终审" value="2"></el-option>
                    <el-option label="待生成受理书" value="3"></el-option>
                    <el-option label="完成" value="4"></el-option>
                    <el-option label="驳回" value="5"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="制剂名称">
                  <el-input v-model.trim="queryForm.formulaName" placeholder="请输入制剂名称"
                    @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :lg="24" :md="24" :sm="24" :xl="24" :xs="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">制剂告知审核列表</span>
            <div class="right">
              <el-button type="primary" @click="batch">
                批量审核
              </el-button>
              <el-button type="primary" @click="handleExport">
                数据导出
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
            <el-table-column align="center" label="单位名称" prop="orgName" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column align="center" label="单位医保编码" prop="orgCode" show-overflow-tooltip
              width="180px"></el-table-column>
            <el-table-column align="center" label="统筹区" prop="fixBlngAdmdvsName"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="协议等级" prop="aggrementLv" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="经营性质" prop="biznet" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="国家医疗机构制剂代码" prop="nationalFormulaCode" show-overflow-tooltip
              width="180"></el-table-column>
            <el-table-column align="center" label="制剂名称" prop="formulaName" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="制剂批准文号" prop="approvalNo" width="120"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="制剂注册单位" prop="registerCompanyName" width="120"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="剂型" prop="dosageForm" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="规格" prop="specs" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="最小包装" prop="minPackage" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="单位" prop="unit" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="价格（元）" width="120" prop="price"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="上次申报价格" width="150" prop="lastApplyPrice"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="是否在医保制剂目录" width="180" prop="isInCategoryDesc" show-overflow-tooltip>
              <template #default="{ row }">
                <el-tag v-if="row.isInCategoryDesc == '是'" type="">是</el-tag>
                <el-tag v-if="row.isInCategoryDesc == '否'" type="danger">否</el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="审核状态" show-overflow-tooltip width="200px">
              <template #default="{ row }">
                <el-tag v-if="row.status == 0" type="info">待初审</el-tag>
                <el-tag v-if="row.status == 1" type="warning">待复审</el-tag>
                <el-tag v-if="row.status == 2" type="warning">待终审</el-tag>
                <el-tag v-if="row.status == 3" type="">待生成受理书</el-tag>
                <el-tag v-if="row.status == 4" type="success">完成</el-tag>
                <el-tag v-if="row.status == 5" type="danger">驳回</el-tag>
              </template>
            </el-table-column>
            <el-table-column align="center" label="驳回原因" prop="rejectReason" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="创建时间" prop="createtime" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="初审时间" prop="firstCheckTime" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="初审负责人" width="120" prop="firstCheckUser"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="复审时间" prop="secondCheckTime" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="复审负责人" width="120" prop="secondCheckUser"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="终审时间" prop="finishCheckTime" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="终审负责人" width="120" prop="finishCheckUser"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="生成受理书时间" width="150" prop="generalAcceptLetterTime"
              show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="驳回时间" prop="rejectTime" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="驳回负责人" width="120" prop="rejectUser"
              show-overflow-tooltip></el-table-column>


            <el-table-column align="center" fixed="right" label="操作" show-overflow-tooltip width="450">
              <template #default="{ row }">
                <!-- <el-button plain size="mini" type="primary" @click="handleView(row)">初审</el-button>
                <el-button plain size="mini" type="primary" @click="handleView(row)">复审</el-button>
                <el-button plain size="mini" type="primary" @click="handleView(row)">终审</el-button>
                <el-button plain size="mini" type="primary" @click="handleLookStateHospital(row, '1')">查看</el-button>
                <el-button plain size="mini" type="primary" @click="handleSls(row)">生成受理书</el-button>
                <el-button plain size="mini" type="primary" @click="handleLookStateHospital(row, '2')">查看受理书</el-button>
                <el-button plain size="mini" type="primary" @click="handleLookFirst(row, '2')">查看许可证</el-button> -->
                <el-button v-if="roles.indexOf('prepare_first') > -1 &&
      row.status == '0' &&
      row.isAudit == 'true'
      " plain size="mini" type="primary" @click="handleView(row)">
                  初审
                </el-button>
                <el-button v-if="roles.indexOf('prepare_recheck') > -1 &&
      row.status == '1' &&
      row.isAudit == 'true'
      " plain size="mini" type="primary" @click="handleView(row)">
                  复审
                </el-button>
                <el-button v-if="roles.indexOf('prepare_final') > -1 &&
      row.status == '2' &&
      row.isAudit == 'true'
      " plain size="mini" type="primary" @click="handleView(row)">
                  终审
                </el-button>

                <el-button plain size="mini" type="primary" @click="handleLookStateHospital(row, '1')">
                  查看
                </el-button>

                <el-button v-if="row.status == '3' &&
      roles.indexOf('prepare_final') > -1
      " plain size="mini" type="primary" @click="handleSls(row)">
                  生成受理书
                </el-button>
                <el-button v-if="row.status == '4'" plain size="mini" type="primary"
                  @click="handleLookStateHospital(row, '2')">
                  查看受理书
                </el-button>
                <el-button v-if="user_type == '1'" plain size="mini" type="primary" @click="handleLookFirst(row, '2')">
                  查看许可证
                </el-button>
                <el-button v-if="row.status == '3' &&
      roles.indexOf('prepare_final') > -1
      " plain size="mini" type="primary" @click="handReject(row)">
                  驳回
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination :current-page="queryForm.pageNo" :layout="layout" :page-size="queryForm.pageSize"
            :total="total" background @size-change="handleSizeChange"
            @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <!-- 驳回 -->
    <el-dialog title="提示" :visible.sync="rejectVisible" width="420px" style="margin-top: 20vh;" @close="closeReject"
      :close-on-click-modal="false">
      <el-form ref="rejectForm" :model="rejectForms" label-width="80px" :rules="rules">
        <el-form-item label="驳回原因" prop="rejectReason">
          <el-input v-model.trim="rejectForms.rejectReason" type="textarea" :rows="3" resize="none"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="rejectVisible = false">取 消</el-button>
        <el-button type="primary" @click="rejectSubmit">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 批量驳回 -->
    <el-dialog title="提示" :visible.sync="allRejectVisible" width="420px" style="margin-top: 20vh;" @close="closeReject"
      :close-on-click-modal="false">
      <el-form ref="allRejectForm" :model="allRejectForms" label-width="80px" :rules="allRules">
        <el-form-item label="驳回原因" prop="rejectReason">
          <el-input v-model.trim="allRejectForms.rejectReason" type="textarea" :rows="3" resize="none"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="allRejectVisible = false">取 消</el-button>
        <el-button type="primary" @click="allRejectSubmit">确 定</el-button>
      </span>
    </el-dialog>

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
  sbApplyList,
  audit,
  batchAudit,
  bedViewPdf,
  sbApplyExport,
  accAcceptanceLetter,
  soloBedViewPdf,
  viewPdf,
} from '@/api/prepareAuditing'
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
      allRejectVisible: false,
      allRejectForms: {
        rejectReason: ''
      },
      rejectVisible: false,
      rejectForms: {
        rejectReason: ''
      },
      rules: {
        rejectReason: [
          { required: true, message: '驳回原因不能为空', trigger: 'blur' }
        ],
      },
      allRules: {
        rejectReason: [
          { required: true, message: '驳回原因不能为空', trigger: 'blur' }
        ],
      },
      user_type: '',
      options: regionDataPlus,
      value1: '',
      value3: '',
      checked: false,
      isShow: false,
      isAdmin: false,
      userinfo: {},
      username: '',
      orgCode: '',
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
        orgName: '',
        orgCode: '',
        nationalFormulaCode: '',
        registerCompanyName: '',
        status: '',
        formulaName: '',
      },
      tableData: [],
      saveData: {
        rejectReason: '',
        status: '',
      },
      roles: [],
      multipleSelection: [],
    }
  },
  created() {
    this.roles = JSON.parse(localStorage.getItem('roles'))
    this.userinfo = JSON.parse(localStorage.getItem('userinfo'))

    this.user_type = this.userinfo.user_type
    this.username = this.userinfo.username
    this.orgCode = this.userinfo.org_code
    if (this.userinfo.user_type == '1') {
      this.queryForm.fixBlngAdmdvs = this.userinfo.org_code
    } else {
      this.queryForm.orgCode = this.userinfo.org_code
    }
    // if (this.user_type == '1') {
    //   this.isAdmin = true
    //   this.isShow = true
    // } else {
    //   this.isAdmin = false
    // }
    this.fetchData()
    this.getAdmdvs()
  },
  beforeDestroy() { },
  mounted() { },
  methods: {
    closeReject() {
      this.rejectForms.rejectReason = ''
      this.allRejectForms.rejectReason = ''
    },
    handleExport(row) {
      // if (this.queryForm.queryDate.length > 0) {
      //   this.queryForm.startTime = this.queryForm.queryDate[0]
      //   this.queryForm.endTime = this.queryForm.queryDate[1]
      //   this.queryForm.queryDate = []
      // }
      // if (this.queryForm.authDate.length > 0) {
      //   this.queryForm.startTimeAuth = this.queryForm.authDate[0]
      //   this.queryForm.endTimeAuth = this.queryForm.authDate[1]
      //   this.queryForm.authDate = []
      // }
      this.$baseConfirm('确定要导出当前全部信息', null, async () => {
        this.listLoading = true
        await sbApplyExport(this.queryForm).then((res) => {
          let fileName = '制剂告知审核列表.xls'
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
      // if (row.user_type == '非定点') {

      // }
      // if (row.acceptLetterPdfPath) {
      //   row.acceptLetterPdfPath =
      //     fileURL +
      //     row.acceptLetterPdfPath +
      //     '?n=' +
      //     row.acceptLetterPdfPath.substring(
      //       row.acceptLetterPdfPath.lastIndexOf('/'),
      //       row.acceptLetterPdfPath.lastIndexOf('.')
      //     ) +
      //     '&download=0'
      //   row.acceptLetterDownPdfPath =
      //     fileURL +
      //     row.acceptLetterDownPdfPath +
      //     '?n=' +
      //     row.acceptLetterDownPdfPath.substring(
      //       row.acceptLetterDownPdfPath.lastIndexOf('/'),
      //       row.acceptLetterDownPdfPath.lastIndexOf('.')
      //     ) +
      //     '&download=1'
      // }
      // console.log(row);
      this.$refs['lookStateHospital'].showEdit(row, type)
    },
    handleSls(row) {
      this.$confirm('确认生成受理书？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        center: true
      }).then(() => {
        accAcceptanceLetter(row.id).then((res) => {
          if (res.code == 0) {
            this.listLoading = false
            this.queryData()
            this.$baseMessage('成功', 'success')
          } else {
            this.$baseMessage(res.msg, 'error')
          }
        })
      }).catch(() => {

      });
      // this.$refs['sls'].showDia(row)
      // this.$baseConfirm('确认生成受理书？', null, async () => {
      //   this.listLoading = true
      //   accAcceptanceLetter(row.id).then((res) => {
      //     if (res.code == 0) {
      //       this.listLoading = false
      //       this.queryData()
      //       this.$baseMessage('成功', 'success')
      //     } else {
      //       this.$baseMessage(res.msg, 'error')
      //     }
      //   })
      //   console.log(row.type)
      //   if (row.type == 9) {
      //     bedViewPdf(row.id).then((res) => {
      //       if (res.code == 0) {
      //         this.listLoading = false
      //         this.queryData()
      //         this.$baseMessage('成功', 'success')
      //       } else {
      //         this.$baseMessage(res.msg, 'error')
      //       }
      //     })
      //   } else if (row.type == 10) {
      //     soloBedViewPdf(row.id).then((res) => {
      //       if (res.code == 0) {
      //         this.listLoading = false
      //         this.queryData()
      //         this.$baseMessage('成功', 'success')
      //       } else {
      //         this.$baseMessage(res.msg, 'error')
      //       }
      //     })
      //   } else {
      //     viewPdf(row.id).then((res) => {
      //       if (res.code == 0) {
      //         this.listLoading = false
      //         this.queryData()
      //         this.$baseMessage('成功', 'success')
      //       } else {
      //         this.$baseMessage(res.msg, 'error')
      //       }
      //     })
      //   }
      // })
    },
    handReject(row) {
      this.$confirm('驳回此条记录，是否继续?', '提示', {
        closeOnPressEscape: false,
        closeOnClickModal: false,
        confirmButtonText: '驳回',
        showCancelButton: false,
        type: 'warning',
        center: true,
      }).then(() => {
        this.saveData.id = row.id
        this.saveData.status = '5'
        this.rejectVisible = true
      })
    },
    handleView(row) {
      this.saveData.rejectReason = ''
      this.saveData.status = ''
      console.log(row);
      if (row.id) {
        this.$confirm('审核通过此条记录, 是否继续?', '提示', {
          distinguishCancelAndClose: true,
          closeOnPressEscape: false,
          closeOnClickModal: false,
          confirmButtonText: '通过',
          cancelButtonText: '驳回',
          type: 'warning',
          center: true,
        })
          .then(() => {
            this.saveData.id = row.id
            this.saveData.rejectReason = ''
            audit(this.saveData).then((res) => {
              if (res.code == 0) {
                this.fetchData()
                this.$emit('fetch-data')
              }
            })
          })
          .catch((action) => {
            if (action === 'cancel') {
              this.saveData.id = row.id
              this.saveData.status = '5'
              this.rejectVisible = true
            }
            // if (action === 'cancel') {
            //   this.$prompt('请输入驳回原因', '提示', {
            //     confirmButtonText: '确定',
            //     cancelButtonText: '取消',
            //     inputErrorMessage: '格式不正确',
            //   })
            //     .then(({ value }) => {
            //       this.saveData.id = row.id
            //       this.saveData.rejectReason = value
            //       this.saveData.status = '5'
            //       audit(this.saveData).then((res) => {
            //         if (res.code == 0) {
            //           this.fetchData()
            //           this.$emit('fetch-data')
            //         }
            //       })
            //       this.$message({
            //         type: 'success',
            //         message: '已驳回，驳回原因是: ' + value,
            //       })
            //     })
            //     .catch(() => {
            //       this.$message({
            //         type: 'info',
            //         message: '取消输入',
            //       })
            //     })
            // }
          })
      }
    },
    rejectSubmit() {
      this.$refs['rejectForm'].validate((valid) => {
        if (valid) {
          this.saveData.rejectReason = this.rejectForms.rejectReason
          audit(this.saveData).then((res) => {
            if (res.code == 0) {
              this.fetchData()
              this.rejectVisible = false
              this.$emit('fetch-data')
            }
          })
        }
      });
    },
    allRejectSubmit() {
      this.$refs['allRejectForm'].validate((valid) => {
        if (valid) {
          var newWeightArr = this.multipleSelection.map((item, index) => {
            return item.id
          })
          let obj = {
            ids: newWeightArr.toString(),
            rejectReason: this.allRejectForms.rejectReason
          }
          console.log(obj);
          batchAudit(obj).then((res) => {
            if (res.code == 0) {
              this.queryData()
              this.$refs.listTable.clearSelection()
              this.allRejectVisible = false
              this.$baseMessage('成功', 'success')
            } else {
              this.$baseMessage(res.msg, 'error')
            }
          })
        }
      });
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
      // this.isShow = !this.isShow
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
      this.queryForm = {
        pageNo: 1,
        pageSize: 10,
        orgName: '',
        orgCode: '',
        nationalFormulaCode: '',
        registerCompanyName: '',
        status: '',
        formulaName: '',
      }
      this.fetchData()
    },
    async getAdmdvs() {
      const res = await getDicts({ type: 'admdvs-area' })
      if (res.code == '0') {
        this.admdvs = res.data
      }
    },
    tableRowClassName({ row }) {
      // if (row.high_price == '1') {
      //   return 'warning-row'
      // }
      // return ''
    },
    getRowKey(row) {
      return row.id
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    batch() {
      if (this.multipleSelection.length == 0) {
        this.$baseMessage('请先勾选数据', 'error')
      } else {
        this.$confirm('审核通过已选中记录，是否继续？', '提示', {
          distinguishCancelAndClose: true,
          closeOnPressEscape: false,
          closeOnClickModal: false,
          confirmButtonText: '通过',
          cancelButtonText: '驳回',
          type: 'warning',
          center: true,
        })
          .then(() => {
            var newWeightArr = this.multipleSelection.map((item, index) => {
              return item.id
            })
            console.log(newWeightArr);
            let obj = {
              ids: newWeightArr.toString(),
            }
            batchAudit(obj).then((res) => {
              if (res.code == 0) {
                this.queryData()
                this.$refs.listTable.clearSelection()
                this.$baseMessage('成功', 'success')
              } else {
                this.$baseMessage(res.msg, 'error')
              }
            })
          })
          .catch((action) => {
            if (action === 'cancel') {
              this.allRejectVisible = true
            }
          })
        // this.$baseConfirm('确认批量通过？', null, async () => {
        //   var newWeightArr = this.multipleSelection.map((item, index) => {
        //     return item.id
        //   })
        //   console.log(newWeightArr);
        //   batchAudit(newWeightArr.toString()).then((res) => {
        //     if (res.code == 0) {
        //       this.queryData()
        //       this.$refs.listTable.clearSelection()
        //       this.$baseMessage('成功', 'success')
        //     } else {
        //       this.$baseMessage(res.msg, 'error')
        //     }
        //   })
        // })
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

::v-deep .el-dialog__footer {
  border-top: none;
}
</style>
