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
                @click="fetchData"
              >
                查 询
              </el-button>
              <el-button
                icon="el-icon-search"
                type="primary"
                @click="exportExcel"
              >
                导 出
              </el-button>
              <!--<el-button icon="el-icon-refresh-left">重 置</el-button>-->
            </div>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="国家编码">
                  <el-input
                    v-model="queryForm.mechanism_code"
                    @keydown.enter.native="fetchData"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="机构名称">
                  <el-input
                    v-model="queryForm.fixmedins_name"
                    @keydown.enter.native="fetchData"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="机构类别">
                  <el-select
                    v-model="queryForm.category_id"
                    @change="getLevelList"
                    style="width: 100%"
                    clearable
                  >
                    <el-option label="医疗机构" value="1"></el-option>
                    <el-option label="零售药店" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="机构等级">
                  <el-select
                    v-model="queryForm.net_grade_id"
                    collapse-tags
                    placeholder="请选择"
                    class="w"
                    clearable
                  >
                    <el-option
                      v-for="item in levelList"
                      :key="item.cred_lv"
                      :label="item.cred_lv_name"
                      :value="item.cred_lv"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="属地">
                  <el-select
                    v-model="queryForm.admdvs"
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
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="网签年份">
                  <el-date-picker
                    v-model="queryForm.year"
                    type="year"
                    value-format="yyyy"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">历史记录</span>
          </div>
          <el-table
            v-loading="listLoading"
            ref="listTable"
            stripe
            :data="list"
            :element-loading-text="elementLoadingText"
            highlight-current-row
            border
            height="calc(100vh - 540px)"
          >
            <el-table-column
              show-overflow-tooltip
              label="国家编码"
              align="center"
              prop="fixmedins_code"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="医保编码"
              align="center"
              prop="medins_mgtcode"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="类型"
              align="center"
              prop="cred_lv_name"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fixmedins_name"
              label="机构名称"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="aggrement_lv_name"
              label="协议等级"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fix_blng_admdvs"
              label="属地"
              align="center"
            >
              <template #default="scope">
                <el-tag
                  size="small"
                  type="success"
                  v-if="scope.row.fix_blng_admdvs == '320399'"
                >
                  徐州市区
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.fix_blng_admdvs == '320305'"
                >
                  贾汪区
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.fix_blng_admdvs == '320312'"
                >
                  铜山区
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.fix_blng_admdvs == '320321'"
                >
                  丰县
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.fix_blng_admdvs == '320322'"
                >
                  沛县
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.fix_blng_admdvs == '320324'"
                >
                  睢宁县
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.fix_blng_admdvs == '320382'"
                >
                  邳州市
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.fix_blng_admdvs == '320381'"
                >
                  新沂市
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="address"
              label="地址"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="type"
              label="类型"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="biznet"
              label="经营性质"
              align="center"
            >
              <template #default="scope">
                <el-tag
                  size="small"
                  type="success"
                  v-if="scope.row.biznet == '1'"
                >
                  营利性
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.biznet == '2'"
                >
                  民办非营利
                </el-tag>
                <el-tag
                  size="small"
                  type="success"
                  v-else-if="scope.row.biznet == '3'"
                >
                  政府非营利
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="license"
              label="许可证登记号"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="legrep_name"
              label="法定代表人"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="legrep_person"
              label="联系人"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="legrep_mobile"
              label="联系电话"
              align="center"
            ></el-table-column>
          </el-table>
          <el-pagination
            background
            :current-page="queryForm.pageIndex"
            :page-size="queryForm.pageSize"
            :total="queryForm.total"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange2"
          ></el-pagination>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import { getPage } from '@/api_net/netOrg'
import { selectAll, editAggrementLevel } from '@/api/fixmedins.js'
import {
  getXzqh,
  getOrgUnSeal,
  selectOrganizationLevel,
  exportUnSeal,
} from '@/api_net/netTagMechanism'
export default {
  name: 'jgglist',
  data() {
    return {
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        total: 0,
        fixmedins_code: '',
        fixmedins_name: '',
        net_grade_id: '',
      },
      listLoading: false,
      form: {},
      elementLoadingText: '加载数据中...',
      list: [],
      levelList: [],
      isDisableSave: false,
      dialogFormVisible: false,
      loading: false,
      options: [],
      aggrement: {},
      orgs: [],
      xzqhs: [],
    }
  },
  created() {
    this.fetchData()
    this.getXzqh()
  },

  beforeDestroy() {},

  mounted() {},
  methods: {
    getLevelList() {
      if (this.queryForm.category_id) {
        selectOrganizationLevel(this.queryForm.category_id).then((res) => {
          if (res.code == 0) {
            this.queryForm.net_grade_id = ''
            this.levelList = res.data
          }
        })
      } else {
        this.levelList = [] //清空级联下来数据
      }
    },
    getXzqh() {
      getXzqh().then((res) => {
        if (res.code == 0) {
          this.xzqhs = res.data
        }
      })
    },
    fetchData() {
      getOrgUnSeal(this.queryForm).then((res) => {
        if (res.code == 0) {
          this.list = res.data.records
          this.queryForm.total = res.data.total
        }
      })
    },
    exportExcel() {
      exportUnSeal(this.queryForm).then((res) => {
        let fileName = '未网签机构.xls'
        let objectUrl = URL.createObjectURL(new Blob([res.data]))
        const link = document.createElement('a')
        link.download = decodeURI(fileName)
        link.href = objectUrl
        link.click()
        this.listLoading = false
        this.$baseMessage('导出成功！', 'success')
      })
    },

    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageIndex = val
      this.fetchData()
    },

    changeLevel() {
      if (this.aggrement.jgType == 1) {
        this.options = [
          { id: '9', name: '未定级' },
          { id: '1', name: '一级' },
          { id: '2', name: '二级' },
          { id: '3', name: '三级' },
        ]
      } else if (this.aggrement.jgType == 2) {
        this.options = [
          { id: '4', name: 'A级' },
          { id: '5', name: 'B级' },
          { id: '6', name: 'C级' },
        ]
      }
      this.getOrgs()
    },
    getOrgs() {
      console.log(this.aggrement.jgType)
      selectAll({ fixmedins_type: this.aggrement.jgType }).then((res) => {
        if (res.code == 0) {
          this.orgs = res.data
        }
      })
    },
    close() {
      this.dialogFormVisible = false
    },
  },
}
</script>
<style scoped>
::v-deep .el-input {
  width: auto !important;
}
::v-deep .el-tree-node__label {
  overflow: hidden;
  word-break: keep-all;
  white-space: nowrap;
  text-overflow: ellipsis;
}
</style>