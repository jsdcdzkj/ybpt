<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="12">
        <el-button type="primary" icon="el-icon-bottom" @click="todow" v-show="userinfo.user_type == 1 && userinfo.org_code == 320399">下载导入模版</el-button>
        <el-upload ref="upfile" v-show="userinfo.user_type == 1 && userinfo.org_code == 320399"
                   :auto-upload="false" :show-file-list=false accept=".xlsx"
                   :on-change="handleChange"
                   :on-success="handleSuccess" action="#">
          <el-button icon="el-icon-upload2" type="success">上传文件</el-button>
        </el-upload>
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="24">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent :rules="rules">
          <el-form-item label=" " prop="upload_no">
            <el-select clearable v-model.trim="queryForm.upload_no" placeholder="请选择批次" class="w">
              <el-option v-for="item in uploadNoList" :key="item" :label="item"
                         :value="item">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.status" placeholder="请选择稽核状态" clearable style="width: 100%">
              <el-option label="待审核" value="0"></el-option>
              <el-option label="审核通过" value="1"></el-option>
              <el-option label="审核不通过" value="2"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item prop="if_upload">
            <el-select
                v-model.trim="queryForm.isUpload"
                clearable
                placeholder="请选择是否举证"
                class="w"
            >
              <el-option value="1" label="已举证"></el-option>
              <el-option value="0" label="未举证"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-show="userinfo.user_type == 1 && userinfo.org_code == 320399">
            <el-select v-model="queryForm.area" placeholder="请选择行政区划" clearable style="width: 100%">
              <el-option label="市本级" value="市本级"></el-option>
              <el-option label="贾汪区" value="贾汪区"></el-option>
              <el-option label="邳州市" value="邳州市"></el-option>
              <el-option label="新沂市" value="新沂市"></el-option>
              <el-option label="睢宁县" value="睢宁县"></el-option>
              <el-option label="沛县" value="沛县"></el-option>
              <el-option label="丰县" value="丰县"></el-option>
              <el-option label="铜山区" value="铜山区"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-show="userinfo.user_type == 1">
            <el-input v-model.trim="queryForm.org_code" placeholder="请输入机构编码" clearable
                      @keyup.enter.native="queryData"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
            <el-button icon="el-icon-import" type="primary" @click="handleExport">
              导出
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table
        v-loading="listLoading"
        :data="list"
        border
        :element-loading-text="elementLoadingText"
    >
      <el-table-column
          show-overflow-tooltip
          type="index"
          width="80"
          fixed="left"
          label="序号"
          align="center"
      ></el-table-column>
      <el-table-column show-overflow-tooltip prop="rid" label="RID" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="psn_name" label="人员姓名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="cert_no" label="身份证号" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="gend" label="性别" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="age" label="年龄" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="setl_time" label="结算时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="fixmedins_name" label="定点医药机构名称" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="fixmedins_code" label="定点医药机构编号" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="genericNameOfTheDrug" label="药品通用名名称"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="drugProvinceCode" label="药品省编码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="nationalDrugCode" label="国家药品码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="mdtrt_id" label="就诊ID" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="setl_id" label="结算ID" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="pric" label="单价" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="cnt" label="数量" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="det_item_fee_sumamt" label="明细项目费用总额"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="psn_selfpay_amt" label="个人自付比例" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="lmt_used_flag" label="限制使用标志" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="med_type" label="医疗类别" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="medfee_sumamt" label="医疗费总额" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="hifp_pay" label="统筹基金支出" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="pool_prop_selfpay" label="基本医疗统筹支付比例"
                       align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="cvlserv_pay" label="公务员医疗补助资金支出" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="hifes_pay" label="补充医疗保险基金支出" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="hifmi_pay" label="大病补充医疗保险基金支出" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="hifob_pay" label="大额医疗补助基金支出" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="maf_pay" label="医疗救助基金支出" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="acct_pay" label="个人账户支出" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="cash_payamt" label="现金支付金额" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="insu_admdvs_name" label="参保所属医保区划" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="common_name_code" label="通用名编码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="lmt_usescp" label="限制使用范围" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="reg" label="REG" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="psn_type" label="人员类别" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="timeOfPrescription" label="处方时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="dept_code" label="所属科室" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="inpatientWard" label="病区" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="doctorSCode" label="医生编码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="nameOfDoctor" label="医生姓名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="admissionTime" label="入院时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="numberOfAdmittedDiseases" label="入院病种编码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="admittingDiagnosis" label="入院诊断" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="timeOfDischarge" label="出院时间" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="numberOfDischargedDiseases" label="出院病种编码" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="dischargeDiagnosis" label="出院诊断" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip label="稽核状态" align="center" fixed="right">
        <template #default="{ row }">
          <el-tag v-if="row.status==0" type="info">
            待审核
          </el-tag>
          <el-tag v-if="row.status==1" type="success">
            审核通过
          </el-tag>
          <el-tag v-if="row.status==2" type="error">
            审核不通过
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="举证操作" width="200" align="center" fixed="right">
        <template #default="{ row }">
          <el-button size="mini" plain type="primary" v-if="row.status == 0 && (userinfo.user_type == 2 || userinfo.user_type == 3)"
                     @click="handleEdit(row)">举证上传
          </el-button>
          <el-button size="mini" plain type="info"
                     v-if="(row.status == 1 || row.status == 2) && (userinfo.user_type == 2|| userinfo.user_type == 3)" disabled>举证上传
          </el-button>

          <el-button size="mini" plain type="warning" v-if="row.isUpload == 1" @click="handleView(row)">查看</el-button>
          <el-button size="mini" plain type="primary" v-if="row.isUpload == 0 || row.isUpload == null" @click="handleView(row)">查看</el-button>
          <el-button size="mini" plain type="primary" v-if="row.status == 0 && userinfo.user_type == 1"
                     @click="handleSh(row,1)">审核
          </el-button>
          <el-button size="mini" plain type="info"
                     v-if="(row.status == 1 || row.status == 2) && userinfo.user_type == 1" disabled>审核
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
        @current-change="handleCurrentChange"
    ></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
    <shenhe ref="shenhe" @fetch-data="fetchData"></shenhe>
  </div>
</template>

<script>
import {audit, exportCheckSuspicionsData, getList, getUploadNo, importData} from '@/api/checkSuspicions'
import Edit from './components/edit'
import Views from './components/view'
import Shenhe from './components/shenhe'
import {MessageBox} from 'element-ui'
import {fileURL} from "@/config/setting.config";

export default {
  name: 'UserManagement',
  components: {Edit, Views,Shenhe},
  data() {
    return {
      list: null,
      listLoading: false,
      userinfo: null,
      uploadNoList: '',
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        isUpload: '',
        area: '',
        status: '',
        upload_no: '',
        if_upload: '',
        insu_admdvs_name: '',
      },
      rules: {
        upload_no: [{required: true, trigger: 'blur', message: '请选择批次'}],
      },
      value: '',
    }
  },
  created() {
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
    this.getUploadNoList()
  },
  methods: {
    async getUploadNoList() {
      const res = await getUploadNo()
      if ((res.code = '0')) {
        this.uploadNoList = res.data
      }
    },
    handleSh(row, type) {
      if (type == 1) {
        MessageBox.confirm('你确定要【审核通过】当前项吗?', '提示', {
          distinguishCancelAndClose: true,
          confirmButtonText: '通过',
          cancelButtonText: '不通过',
          type: 'warning',
          center: true,
        })
            .then(() => {
              audit({rid: row.rid, status: 1}).then((res) => {
                if (res.code == 0) {
                  this.fetchData();
                  this.$baseMessage("审核通过", 'success')
                  this.$emit('fetch-data');
                }
              })
            })
            .catch(action => {
              if (action === 'cancel') {
                this.$refs['shenhe'].showDia(row.rid)
              }
            })
      } else {
        this.$refs['shenhe'].showDia(row.id)
      }
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleEdit(row) {
      console.log(row);
      if (row.rid) {

        this.$refs['edit'].showDia(row)
      } else {
        this.$refs['edit'].showDia()
      }
    },
    handleView(row) {
      this.$refs['views'].showDia(row)
    },
    handleExport(row) {
      if (!this.queryForm.upload_no) {
        this.$baseMessage('请选择批次号', 'error')
        return;
      }
      this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
        this.listLoading = true
        this.queryForm.insu_admdvs_name = this.queryForm.area;
        this.queryForm.fixmedins_code = this.queryForm.org_code;
        await exportCheckSuspicionsData(this.queryForm).then((res) => {
          let fileName = "稽查数据.xlsx";
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(fileName)
          link.href = objectUrl
          link.click()
          this.listLoading = false;
          this.$baseMessage("导出成功！", 'success')
        })

      })
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
    async fetchData() {
      if (!this.queryForm.upload_no) {
        this.$baseMessage('请选择批次号', 'error')
        return;
      }
      this.getArea()
      this.listLoading = true
      const res = await getList(this.queryForm)
      this.list = res.data.records
      this.total = res.data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async handleChange(file, fileList) {
      this.dialog = true
      this.loading = true
      this.fileList = fileList;
      let fd = new FormData();
      fd.append("id", this.selectRows.id);
      this.fileList.forEach(item => {
        //文件信息中raw才是真的文件
        fd.append("file", item.raw);
      })

      var result = await importData(fd);
      if (result.data.code == 0) {
        this.$baseMessage("上传成功", 'success')
      } else {
        this.$baseMessage(result.data.msg, 'error')
      }
      this.dialog = false
      this.loading = false
    },
    handleSuccess() {
      this.fetchData()
    },
    todow(){
      self.location.href = fileURL + "/file/template/稽查数据导入模板.xlsx" ;
    },
    getArea() {
      if (this.userinfo.user_type == 1 && this.userinfo.org_code != 320399) {
        const orgCode = this.userinfo.org_code;
        if (orgCode == 320399) {
          this.queryForm.area = '市本级';
        }
        if (orgCode == 320305) {
          this.queryForm.area = '贾汪区';
        }
        if (orgCode == 320382) {
          this.queryForm.area = '邳州市';
        }
        if (orgCode == 320381) {
          this.queryForm.area = '新沂市';
        }
        if (orgCode == 320324) {
          this.queryForm.area = '睢宁县';
        }
        if (orgCode == 320322) {
          this.queryForm.area = '沛县';
        }
        if (orgCode == 320321) {
          this.queryForm.area = '丰县';
        }
        if (orgCode == 320312) {
          this.queryForm.area = '铜山区';
        }
      }
    },
  },
}
</script>
