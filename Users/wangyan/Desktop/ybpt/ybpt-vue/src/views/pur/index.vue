<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">查询条件</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>&nbsp;&nbsp;
            </div>
          </div>
          <el-form label-width="auto">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="isAdmin">
                <el-form-item label="医药机构名称" class="custemitem">
                  <el-input v-model.trim="queryForm.org_name" placeholder="请输入" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="isAdmin">
                <el-form-item label="医药机构省平台编码" class="custemitem">
                  <el-input v-model.trim="queryForm.org_code" placeholder="请输入" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="isAdmin">
                <el-form-item label="省阳光采购平台产品编码" class="custemitem">
                  <el-input v-model.trim="queryForm.codeList" placeholder="请输入" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="缺货分类">
                  <el-select v-model="queryForm.stockout_type" style="width: 100%" @change="queryData()" clearable>
                    <el-option label="药品" value="1"></el-option>
                    <el-option label="耗材" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="线索类型">
                  <el-select v-model="queryForm.clues" style="width: 100%" @change="queryData()" clearable>
                    <el-option label="无正当理由，不响应采购订单" value="无正当理由，不响应采购订单"></el-option>
                    <el-option label="以高于挂网价线下供应或配送" value="以高于挂网价线下供应或配送"></el-option>
                    <el-option label="不接受网上议价，但实际供货价低于挂网价" value="不接受网上议价，但实际供货价低于挂网价"></el-option>
                    <el-option label="同一企业以过评后价格销售过评前同一产品" value="同一企业以过评后价格销售过评前同一产品"></el-option>
                    <el-option label="未按规定签订带量采购合同" value="未按规定签订带量采购合同"></el-option>
                    <el-option label="未按合同约定及时供应产品" value="未按合同约定及时供应产品"></el-option>
                    <el-option label="不按中选清单供应产品" value="不按中选清单供应产品"></el-option>
                    <el-option label="未按带量采购合同约定提供伴随服务" value="未按带量采购合同约定提供伴随服务"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="创建时间">
                  <el-date-picker
                      v-model="queryForm.queryDate"
                      style="width: 100%"
                      format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                      type="daterange"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="userinfo.user_type == '1' && (userinfo.org_code ==='320399' || userinfo.org_name =='admin')">
                <el-form-item label="统筹区">
                  <el-select v-model="queryForm.fix_blng_admdvs" clearable
                             style="width: 100%" @change="queryData()">
                    <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                               :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">阳光采购药品（耗材）列表</span>
            <div class="right">
              <el-button type="success" icon="el-icon-plus" @click="handleOpen"
                         v-if="user_type == '2' || user_type == '3'">
                新增
              </el-button>
              <el-button icon="el-icon-upload2" type="primary" @click="handleExport" v-if="isAdmin">
                导出
              </el-button>
            </div>
          </div>
          <el-table v-loading="listLoading" ref="listTable" :data="tableData"
                    :element-loading-text="elementLoadingText" highlight-current-row border
                    @current-change="handleCurrentChange" height="calc(100vh - 570px)" style="width: 100%">
            <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="org_name" label="医药机构名称" align="center"
                             width="280px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="org_code" label="医药机构省平台编码" align="center"
                             width="180px"></el-table-column>
<!--            <el-table-column show-overflow-tooltip prop="codeList" label="省阳光采购平台产品编码" align="center"-->
<!--                             width="180px"></el-table-column>-->
            <el-table-column show-overflow-tooltip prop="fix_blng_admdvs_name" label="统筹区"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="createTime" label="创建时间" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="stockout_type_name" label="缺货分类" align="center">
              <!--<template #default="{ row }">-->
              <!--<el-tag v-if="row.type==1">药品</el-tag>-->
              <!--<el-tag v-else-if="row.type==2">耗材</el-tag>-->
              <!--</template>-->
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="clues" label="线索类型" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="250" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handleEdit(row,'detail')" type="primary" size="mini">
                  查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                         :layout="layout" :total="total" @size-change="handleSizeChange"
                         @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <edit ref="edit" @fetch-data="fetchData"></edit>

    <el-dialog title="阳光采购药品（耗材）新增" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="缺货分类" :label-width="formLabelWidth">
          <el-radio v-model="form.stockout_type" label="1">药品</el-radio>
          <el-radio v-model="form.stockout_type" label="2">耗材</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleEdit(null,'add')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<style>
.el-table .warning-row {
  background: oldlace;
}
</style>

<script>
import {getPage, purStockoutExport, selectOne} from '@/api/purStockout'
import {getDicts} from "@/api/dictManagement";
import Edit from "@/views/pur/components/edit.vue";
import {getFixmedinsB} from "@/api/sbApply";

export default {
  name: 'informProcess',
  components: {
    Edit,
  },
  data() {
    return {
      user_type: '',
      fixmedinsB: '',
      checked: false,
      isShow: false,
      isAdmin: false,
      userinfo: {},
      username: "",
      org_code: "",
      org_name: "",
      list: null,
      dialogFormVisible: false,
      formLabelWidth: '100px',
      listLoading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      admdvs: null,
      elementLoadingText: '请稍等...',
      form: {
        stockout_type: '1'
      },
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        org_name: '',
        org_code: '',
        codeList: '',
        type: '',
        status: '',
        fix_blng_admdvs: '',
        queryDate: [],
      },
      tableData: [],
      roles: []
    }
  },
  created() {
    this.roles = JSON.parse(localStorage.getItem("roles"));
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
    this.user_type = this.userinfo.user_type;
    this.username = this.userinfo.username;
    this.org_code = this.userinfo.org_code;
    console.log(this.userinfo);
    if (this.userinfo.user_type == '1') {
      this.queryForm.fix_blng_admdvs = this.userinfo.org_code;
    } else {
      this.queryForm.org_code = this.userinfo.org_code;
    }
    if (this.user_type == "1") {
      this.isAdmin = true;
      this.isShow = true;
    } else {
      this.isAdmin = false;
    }
    this.getAdmdvs()
    this.fetchData();
  },
  methods: {
    handleExport(row) {
      this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
        this.listLoading = true
        if(this.queryForm.queryDate.length > 0){
          this.queryForm.startTime = this.queryForm.queryDate[0]
          this.queryForm.endTime = this.queryForm.queryDate[1]
          this.queryForm.queryDate = []
        }

        await purStockoutExport(this.queryForm).then((res) => {
          let fileName = "阳光采购药品（耗材）列表.xls";
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(fileName)
          link.href = objectUrl
          link.click()
          this.listLoading = false;
          this.$baseMessage("导出成功！", 'success')
        })

        if(this.queryForm.startTime != '' && this.queryForm.endTime != ''){
          this.queryForm.queryDate.push(this.queryForm.startTime)
          this.queryForm.queryDate.push(this.queryForm.endTime)
        }

      })
    },
    handleOpen() {
      this.dialogFormVisible = true
    },
    async handleEdit(row, sign) {
      if (sign == 'add') {
        await getFixmedinsB().then((res) => {
          this.fixmedinsB = res.data
        })
        row = {
          org_name: this.fixmedinsB.fixmedins_name,
          org_code: this.fixmedinsB.fixmedins_code,
          fix_blng_admdvs: this.fixmedinsB.fixmedins_code,
          stockout_type: this.form.stockout_type,
          sign: sign
        }
      }
      if (sign == 'detail') {
        await selectOne({id: row.id}).then((res) => {
          row = res.data
        })
        row.sign = sign
      }
      this.dialogFormVisible = false
      this.$refs['edit'].showEdit(row)
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    async fetchData() {
      this.listLoading = true
      getPage(this.queryForm).then((res) => {
        if (res.code == 0) {
          this.tableData = res.data.records
          this.total = res.data.total
          this.listLoading = false
        }
      })
    },
    reseat() {
      this.queryForm.org_name = "";
      this.queryForm.org_code = "";
      this.queryForm.codeList = "";
      this.queryForm.stockout_type = "";
      this.queryForm.fix_blng_admdvs = "";
      this.queryForm.queryDate = [];
      this.queryData()
    },
    async getAdmdvs() {
      const res = await getDicts({"type": "admdvs-area"});
      if (res.code == "0") {
        this.admdvs = res.data;
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