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
                @click="queryData"
              >
                查 询
              </el-button>
              <!--<el-button icon="el-icon-refresh-left">重 置</el-button>-->
            </div>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="国家编码">
                  <el-input
                    v-model="queryForm.fixmedins_code"
                    @keydown.enter.native="queryData"
                    clearable
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="机构名称">
                  <el-input
                    v-model="queryForm.fixmedins_name"
                    @keydown.enter.native="queryData"
                    clearable
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="属地">
                  <el-select
                    v-model="queryForm.admdvs"
                    @change="queryData"
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
                <el-form-item label="机构类型">
                  <el-select
                    v-model="queryForm.fixmedins_type"
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
                <el-form-item label="协议等级">
                  <el-select
                    v-model="queryForm.aggrement_lv"
                    style="width: 100%"
                    @change="queryData"
                    clearable
                  >
                    <el-option label="请选择" value=""></el-option>
                    <el-option
                      v-for="item in levelList"
                      :key="item.id"
                      :label="item.name"
                      :value="item.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="4">
                <el-form-item label="机构类别">
                  <el-select
                    v-model="queryForm.category"
                    @change="queryData"
                    style="width: 100%"
                    clearable
                  >
                    <el-option label="门诊" value="1"></el-option>
                    <el-option label="住院" value="2"></el-option>
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
            <span class="tips">历史记录</span>
            <div class="right">
              <el-button
                type="primary"
                icon="el-icon-sort"
                @click="handleCategory"
              >
                类别维护
              </el-button>
              <el-button
                type="primary"
                icon="el-icon-sort"
                @click="handleAggrement"
              >
                协议等级维护
              </el-button>
            </div>
          </div>
          <el-table
            @current-change="handleCurrentChange"
            @selection-change="setSelectRows"
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
              label="类别"
              align="center"
              prop="category_name"
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
    <el-dialog
      title="类别维护"
      :visible.sync="dialogFormCategoryVisible"
      width="730px"
      @closeChildDialog="close"
      @close="close"
    >
      <el-form ref="form" label-width="110px">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="类别" prop="category">
              <el-select v-model.trim="category" class="w">
                <el-option value="1" label="门诊"></el-option>
                <el-option value="2" label="住院"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeCategory">取 消</el-button>
        <el-button type="primary" @click="saveCategory">确定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      title="协议等级维护"
      :visible.sync="dialogFormVisible"
      width="730px"
      @closeChildDialog="close"
      @close="close"
    >
      <el-form ref="form" :model="form" label-width="110px">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="机构类别" prop="jgType">
              <el-select
                v-model.trim="aggrement.jgType"
                class="w"
                @change="changeLevel"
              >
                <el-option value="1" label="医疗机构"></el-option>
                <el-option value="2" label="零售药店"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="机构等级" prop="jgLevel">
              <el-select
                v-model="aggrement.jgLevel"
                collapse-tags
                placeholder="请选择"
                class="w"
              >
                <el-option
                  v-for="item in options"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="配置" prop="jgIds">
              <el-transfer
                ref="transfer"
                filterable
                filter-placeholder="请输入关键字"
                :titles="[' ', ' ']"
                :filter-method="filterMethod"
                @mouseover.native="addTitle"
                v-model="aggrement.medicalCodeList"
                :data="orgs"
              ></el-transfer>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="save" :disabled="this.isDisableSave">
          确定
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getPage } from '@/api_net/netOrg'
import { editAggrementLevel, editData, selectAll } from '@/api/fixmedins.js'
import { getXzqh } from '@/api_net/netTagMechanism'

export default {
  name: 'jgglist',
  data() {
    return {
      aList: [
        { id: 1, name: '一级' },
        { id: 2, name: '二级' },
        { id: 3, name: '三级' },
        { id: 9, name: '未定级' },
      ],
      bList: [
        { id: 4, name: 'A级' },
        { id: 5, name: 'B级' },
        { id: 6, name: 'C级' },
      ],
      levelList: [],
      queryForm: {
        pageIndex: 1,
        pageSize: 10,
        total: 0,
        fixmedins_code: '',
        fixmedins_name: '',
      },
      selectRows: '',
      category: '',
      listLoading: false,
      form: {},
      elementLoadingText: '加载数据中...',
      list: [],
      isDisableSave: false,
      dialogFormVisible: false,
      dialogFormCategoryVisible: false,
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
      var that = this

      if (that.queryForm.fixmedins_type == 1) {
        that.levelList = that.aList
        that.queryForm.aggrement_lv = ''
      } else if (that.queryForm.fixmedins_type == 2) {
        that.levelList = that.bList
        that.queryForm.aggrement_lv = ''
      } else {
        that.levelList = []
      }

      if (that.queryForm.aggrement_lv != undefined) {
        that.queryForm.aggrement_lv = ''
      }
      this.queryData()
    },
    queryData() {
      this.queryForm.pageIndex = 1
      this.fetchData()
    },
    filterMethod(query, item) {
      return item.key.indexOf(query) > -1
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    getXzqh() {
      getXzqh().then((res) => {
        if (res.code == 0) {
          this.xzqhs = res.data
        }
      })
    },
    fetchData() {
      getPage(this.queryForm).then((res) => {
        if (res.code == 0) {
          this.list = res.data.records
          this.queryForm.total = res.data.total
        }
      })
    },
    addTitle(e) {
      const target = e.target
      if (target.title) return
      target.title = target.innerText
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageIndex = val
      this.fetchData()
    },
    handleCategory() {
      if (this.selectRows.cred_lv_name == '医疗机构') {
        this.category = this.selectRows.category
        this.dialogFormCategoryVisible = true
      } else {
        this.$baseMessage('请选择类型为【医疗机构】', 'error')
      }
    },
    // 协议等级维护
    handleAggrement() {
      this.dialogFormVisible = true
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
    closeCategory() {
      this.dialogFormCategoryVisible = false
    },
    close() {
      this.clearTransfer()
    },
    clearTransfer() {
      this.isDisableSave = false
      this.aggrement.medicalCodeList = []
      this.aggrement.jgType = ''
      this.orgs = []
      this.options = []
      this.aggrement.jgLevel = ''
      this.$refs.transfer.clearQuery('left')
      this.$refs.transfer.clearQuery('right')
      this.dialogFormVisible = false
    },
    saveCategory() {
      if (
        null == this.category ||
        '' == this.category ||
        undefined == this.category
      ) {
        this.$baseMessage('请选择类别', 'error')
        return false
      }
      editData({
        id: this.selectRows.id,
        category: this.category,
      }).then((res) => {
        if (res.code == 0) {
          this.$baseMessage('操作成功', 'success')
          this.dialogFormCategoryVisible = false
          this.clearTransfer()
          this.fetchData()
        } else {
          this.$baseMessage('同步失败', 'error')
        }
      })
    },
    save() {
      this.isDisableSave = true
      if (
        null == this.aggrement.medicalCodeList ||
        undefined == this.aggrement.medicalCodeList ||
        this.aggrement.medicalCodeList.length < 1
      ) {
        that.$baseMessage('请选择单位', 'error')
        this.isDisableSave = false
        return false
      }
      editAggrementLevel({
        level: this.aggrement.jgLevel,
        ids: this.aggrement.medicalCodeList,
      }).then((res) => {
        if (res.code == 0) {
          this.$baseMessage('操作成功', 'success')
          this.dialogFormVisible = false
          this.fetchData()
        } else {
          this.$baseMessage('同步失败', 'error')
        }
        this.isDisableSave = false
      })
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