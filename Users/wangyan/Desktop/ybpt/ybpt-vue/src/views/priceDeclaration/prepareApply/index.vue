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
          <el-form label-width="180px">
            <el-row :gutter="20">
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="国家医疗机构制剂代码">
                  <el-input v-model.trim="queryForm.nationalFormulaCode" placeholder="请输入"
                            @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="制剂名称">
                  <el-input v-model.trim="queryForm.formulaName" placeholder="请输入" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="制剂注册单位">
                  <el-input v-model.trim="queryForm.registerCompanyName" placeholder="请输入"
                            @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :lg="6" :md="6" :sm="24" :xl="6" :xs="24">
                <el-form-item label="审核状态">
                  <!--                  //状态: 0 待初审 1.待复审 2.待终审  3.待生成受理书 4.完成 5.驳回-->
                  <el-select v-model="queryForm.status" clearable style="width: 100%" @change="queryData()">
                    <el-option label="待初审" value="0"></el-option>
                    <el-option label="待复审" value="1"></el-option>
                    <el-option label="待终审" value="2"></el-option>
                    <el-option label="待生成受理书" value="3"></el-option>
                    <el-option label="完成" value="4"></el-option>
                    <el-option label="驳回" value="5"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :lg="24" :md="24" :sm="24" :xl="24" :xs="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">制剂告知申请列表</span>
            <div class="right">
              <el-button class="ml2" icon="el-icon-download" type="primary" @click="handleExport">
                数据导出
              </el-button>
              <el-button icon="el-icon-plus" type="success" @click="handleAdd" v-if="userinfo.user_type == 2">
                制剂申请
              </el-button>
            </div>
          </div>
          <el-table ref="listTable" v-loading="listLoading" :data="tableData" :element-loading-text="elementLoadingText"
                    :row-class-name="tableRowClassName" :row-key="getRowKey" border height="calc(100vh - 570px)"
                    highlight-current-row style="width: 100%"
                   >
            <!--            nationalFormulaCode: undefined,//国家医疗机构制剂代码-->
            <!--            isInCategory: 1,//是否在医保制剂目录         1 是 0 否-->
            <!--            formulaName: undefined,//制剂名称-->
            <!--            categoryCode: undefined,//分类编码-->
            <!--            genericNameCode: undefined,//药品通用名编码-->
            <!--            productNameCode: undefined,//产品名编码-->
            <!--            goodsName: undefined,//商品名-->
            <!--            payType: undefined,//支付类别-->
            <!--            approvalNo: undefined,//批准文号-->
            <!--            registerCompanyName: undefined,//制剂注册单位-->
            <!--            dosageForm: undefined,//剂型-->
            <!--            specs: undefined,//规格-->
            <!--            minPriceUnit: undefined,//最小计价单位-->
            <!--            minPackage: undefined,//最小包装-->
            <!--            unit: undefined,//单位-->
            <!--            catalogCode: undefined,//目录编号-->
            <!--            selfPayRatio: undefined,//个人先行自付比例-->
            <!--            remark: undefined,//备注        (元)-->
            <!--            price: undefined,//价格        (元)-->
            <!--            localPmPrice: undefined,//本市同级公立医疗机构价格-->

            <el-table-column align="center" label="序号" show-overflow-tooltip type="index"
                             width="80px"></el-table-column>
            <el-table-column align="center" label="单位名称" prop="orgName" show-overflow-tooltip
                             width="180px"></el-table-column>
            <el-table-column align="center" label="单位医保编码" prop="orgCode" show-overflow-tooltip
                             width="180px"></el-table-column>
            <el-table-column align="center" label="统筹区" prop="fixBlngAdmdvsName"
                             show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="协议等级" prop="aggrementLv" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="经营性质" prop="biznet" show-overflow-tooltip>
              <template slot-scope="{row,$index}">
<!--                biznet 经营性质 -->
                {{{1:'营利性',2:'民办非营利',3:'政府非营利'}[row.biznet]}}
              </template>
            </el-table-column>
            <el-table-column align="center" label="国家医疗机构制剂代码" prop="nationalFormulaCode"
                             show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="制剂名称" prop="formulaName" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="制剂批准文号" prop="approvalNo" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="制剂注册单位" prop="registerCompanyName"
                             show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="剂型" prop="dosageForm" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="规格" prop="specs" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="最小包装" prop="minPackage" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="单位" prop="unit" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="价格（元）" prop="price" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="上次申报价格（元）" prop="lastApplyPrice" show-overflow-tooltip></el-table-column>
            <el-table-column align="center" label="是否在医保制剂目录" prop="isInCategory" show-overflow-tooltip>
              <template slot-scope="scope">
                {{{1:'是',0:'否'}[scope.row.isInCategory]}}
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
            <el-table-column align="center" label="驳回原因" prop="rejectReason" show-overflow-tooltip width="200px"></el-table-column>
            <el-table-column align="center" label="创建时间" prop="createtime" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" label="初审时间" prop="firstCheckTime" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" label="初审负责人" prop="firstCheckUser" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" label="复审时间" prop="secondCheckTime" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" label="复审负责人" prop="secondCheckUser" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" label="终审时间" prop="finishCheckTime" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" label="终审负责人" prop="finishCheckUser" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" label="生成受理书时间" prop="generalAcceptLetterTime" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" label="驳回时间" prop="rejectTime" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" label="驳回负责人" prop="rejectUser" show-overflow-tooltip width="180px"></el-table-column>
            <el-table-column align="center" fixed="right" label="操作" show-overflow-tooltip width="300px">
              <template #default="{ row }">
                <el-button v-if="row.status == 5" plain size="mini" type="primary" @click="handleUpdateApply(row)">
                  修改申请
                </el-button>
                <el-button plain size="mini" type="primary" @click="handleLookStateHospital(row, '1')">
                  查看
                </el-button>
                <el-button v-if="row.status == '4'" plain size="mini" type="primary"
                           @click="handleLookStateHospital(row, '2')">
                  查看受理书
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
    <lookStateHospital ref="lookStateHospital" @fetch-data="fetchData"></lookStateHospital>
    <add ref="add" @fetch-data="fetchData"></add>
    <add2 ref="add2" @fetch-data="fetchData"></add2>

  </div>
</template>

<style>
  .el-table .warning-row {
    background: oldlace;
  }
</style>

<script>
  import Add from './components/add'
  import Add2 from './components/add2'

  import LookStateHospital from './components/lookStateHospital'
  import {CodeToText, regionDataPlus} from 'element-china-area-data'

  import {
    handleSave,
    uploadDetailFile,
    download,
    handleExport,
    applyPage,
  } from '@/api/prepareApply'
  import {getDicts} from '@/api/dictManagement'
  import {fileURL} from '@/config/setting.config'

  export default {
    name: 'PrepareApply',
    components: {
      Add,
      Add2,
      LookStateHospital,
    },
    data() {
      return {
        user_type: '',
        options: regionDataPlus,
        value1: '',
        value3: '',
        checked: false,
        isShow: true,
        userinfo: {},
        listLoading: false,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        elementLoadingText: '请稍等...',
        queryForm: {
          pageNo: 1,
          pageSize: 20,
          //国家医疗机构制剂代码
          nationalFormulaCode: "",
          //制剂注册单位
          registerCompanyName: "",
          //状态: 0 待初审 1.待复审 2.待终审  3.待生成受理书 4.完成 5.驳回
          status: "",
          //制剂名称
          formulaName: "",
          orgCode:"",
        },
        tableData: [],
      }
    },
    created() {
      this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
      this.queryForm.orgCode = this.userinfo.org_code
      this.fetchData()
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
      handleAdd() {
        let userInfo = JSON.parse(window.localStorage.getItem('userinfo'));
        const row = {
          orgName: userInfo.org_name,
          orgCode: userInfo.org_code,
          type: '',
          title: '医疗机构制剂价格明细表',
          status: '0',
        }

        this.$refs['add'].showDia(row)
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

      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageNo = 1;
        this.fetchData()
      },
      fetchData() {
        this.listLoading = true
        applyPage(this.queryForm).then((res) => {
          //console.log(res);
          if (res.code == 0) {
            this.tableData = res.data.records
            this.total = res.data.total
            this.listLoading = false
          }
        })
      },
      reseat() {
        this.queryForm.nationalFormulaCode = ''
        this.queryForm.formulaName = ''
        this.queryForm.registerCompanyName = ''
        this.queryForm.status = ''
        this.queryData()
      },
      tableRowClassName({row}) {
        if (row.high_price == '1') {
          return 'warning-row'
        }
        return ''
      },
      getRowKey(row) {
        return row.id
      },
      handleUpdateApply(row){
        this.$refs['add2'].showDia(row)
      },
      handleExport(){
        handleExport({...this.queryForm}).then(res =>{
          console.log(res);
          let fileName = '制剂告知申请.xlsx';
          let objectUrl = URL.createObjectURL(new Blob([res.data]));
          const link = document.createElement('a');
          link.download = decodeURI(fileName);
          link.href = objectUrl;
          link.click();
          this.listLoading = false;
          this.$baseMessage("导出成功！", 'success')
        })
      }
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
