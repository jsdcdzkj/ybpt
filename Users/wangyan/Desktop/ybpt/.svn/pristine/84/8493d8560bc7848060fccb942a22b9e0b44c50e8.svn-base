<template>
  <div
    class="main-container"
    v-loading="loading"
    element-loading-text="数据同步中"
    element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <el-button
              icon="el-icon-search"
              type="primary"
              class="right"
              @click="fetchData"
            >
              查 询
            </el-button>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构编号">
                  <el-input v-model.trim="queryForm.fixmedins_code" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="医疗机构类型">
                  <el-select
                    v-model="queryForm.fixmedins_type"
                    style="width: 100%"
                  >
                    <el-option label="请选择" value=""></el-option>
                    <el-option label="定点医疗机构" value="1"></el-option>
                    <el-option label="定点零售药店" value="2"></el-option>
                    <!--<el-option label="工伤定点康复机构" value="3"></el-option>-->
                    <!--<el-option label="辅助器具配置机构" value="4"></el-option>-->
                    <!--<el-option label="计划生育服务机构" value="5"></el-option>-->
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构名称">
                  <el-input v-model.trim="queryForm.fixmedins_name" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">机构列表</span>
            <div class="right">
              <el-button type="success" icon="el-icon-plus" @click="handleAdd">
                新增
              </el-button>
              <el-button
                type="primary"
                icon="el-icon-refresh"
                @click="synchronous"
              >
                同步所有机构
              </el-button>
              <el-button
                  type="primary"
                  icon="el-icon-sort"
                  @click="handleBiznet"
              >
                经营性质维护
              </el-button>
                <el-button
                        type="primary"
                        icon="el-icon-sort"
                        @click="handleSbApply"
                >
                    告知手续统筹区维护
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
            height="calc(100% - 50px)"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
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
              label="定点医疗机构代码"
              align="center"
              prop="fixmedins_code"
              width="150px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fixmedins_name"
              width="340px"
              label="定点医疗机构名称"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fixmedins_type"
              width="120px"
              label="医疗机构类型"
              align="center"
              :formatter="fixmedins_typeFormat"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="biznet"
                width="120px"
                label="经营性质"
                align="center"
                :formatter="biznet_typeFormat"
            ></el-table-column>
              <el-table-column
                show-overflow-tooltip
                prop="fix_blng_admdvs_sbApply_name"
                width="120px"
                label="告知手续统筹区"
                align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="uscc"
              label="统一社会信用代码"
              align="center"
              width="150px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="hosp_lv"
              label="医院等级"
              align="center"
              width="120px"
              :formatter="hosp_lvFormat"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="lmtpric_hosp_lv"
              label="限价医院等级"
              align="center"
              width="200px"
              :formatter="lmtpric_hosp_lvFormat"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="nat_plaf_code"
              label="国家异地平台机构编号"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="prov_plaf_code"
              label="省内异地平台机构编号"
              align="center"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fix_onln_open_flag"
              align="center"
              label="定点联网开通标志"
              width="160px"
              :formatter="fix_onln_open_flagFormat"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="dedc_hosp_lv"
              align="center"
              label="起付线医院等级"
              width="160px"
              :formatter="dedc_hosp_lvFormat"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="hi_resper_name"
              align="center"
              label="医院负责人姓名"
              width="160px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="hi_resper_tel"
              align="center"
              label="医院负责人联系电话"
              width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="authorizationCode"
              align="center"
              label="授权码"
              width="200px"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="160"
              align="center"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handlechuli(row)"
                  type="primary"
                  size="mini"
                >
                  更新授权码
                  <!--</el-button>-->
                  <!--<el-button-->
                  <!--plain-->
                  <!--@click="handlecancel(row)"-->
                  <!--type="danger"-->
                  <!--size="mini"-->
                  <!--&gt;-->
                  <!--删除-->
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

    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <edit2 ref="edit2" @fetch-data="fetchData"></edit2>



    <el-dialog
        title="类别维护"
        :visible.sync="dialogFormVisible"
        width="730px"
        @closeChildDialog="close"
        @close="close"
    >
      <el-form ref="form" label-width="110px">
        <el-row :gutter="20">
          <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
            <el-form-item label="类别" prop="biznet">
              <el-select v-model.trim="biznet" class="w">
                <el-option value="1" label="营利性"></el-option>
                <el-option value="2" label="民办非营利"></el-option>
                <el-option value="3" label="政府非营利"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <el-button type="primary" @click="saveBiznet">确定</el-button>
      </div>
    </el-dialog>

      <el-dialog
              title="告知手续统筹区维护"
              :visible.sync="sbApplyFormVisible"
              width="730px"
              @closeChildDialog="close"
              @close="close"
      >
          <el-form ref="form" label-width="110px">
              <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="统筹区" prop="fix_blng_admdvs_sbApply">
                        <el-select clearable v-model="fix_blng_admdvs_sbApply" style="width: 100%">
                          <el-option v-for="item in admdvs" :key="item.value" :label="item.label" :value="item.value">
                          </el-option>
                        </el-select>
                      </el-form-item>
                  </el-col>
              </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
              <el-button @click="close">取 消</el-button>
              <el-button type="primary" @click="saveSbApply">确定</el-button>
          </div>
      </el-dialog>

  </div>
</template>

<script>
import {editAggrementLevel, editData, selectAll, selectList, synchronousAll,} from '@/api/fixmedins.js'
import Cardnum from '@/components/cardno'
import Edit from './components/edit'
import Edit2 from './components/edit2'
import {getDicts} from "@/api/dictManagement";

export default {
  name: 'Index',
  components: { Cardnum, Edit, Edit2 },
  data() {
    return {
      biznet: '',
      admdvs: [],
      fix_blng_admdvs_sbApply: '',
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      form: {},
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        fixmedins_code: '',
        fixmedins_type: '',
        fixmedins_name: '',
      },
      dialogFormVisible: false,
      sbApplyFormVisible: false,
      loading: false,
      options: [],
      aggrement: {},
      orgs: [],
    }
  },
  created() {
    this.fetchData()
    this.getAdmdvs()
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    //获取统筹区
    async getAdmdvs() {
        const res = await getDicts({"type": "admdvs-area"});
        if (res.code == "0") {
            this.admdvs = res.data;
        }
    },
    close() {
      this.dialogFormVisible = false
      this.sbApplyFormVisible = false
    },
    addTitle(e) {
      const target = e.target
      if (target.title) return
      target.title = target.innerText
    },

    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    handleAdd() {
      this.$refs['edit'].showDia()
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handlechuli(row) {
      this.$refs['edit2'].showDia(row)
    },
    handlecancel(row) {
      if (row.id) {
        this.$baseConfirm('确认进行撤消？', null, async () => {
          const { msg } = await doDelete({ ids: row.id })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
      }
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    fetchData() {
      var that = this
      selectList(that.queryForm).then((res) => {
        that.list = res.data.records
        that.total = res.data.total
      })
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    hosp_lvFormat(row, column) {
      var statusW
      switch (row.hosp_lv) {
        case '01':
          statusW = '三级特等'
          break
        case '02':
          statusW = '三级甲等'
          break
        case '03':
          statusW = '三级乙等'
          break
        case '04':
          statusW = '三级丙等'
          break
        case '05':
          statusW = '二级甲等'
          break
        case '06':
          statusW = '二级乙等'
          break
        case '07':
          statusW = '二级丙等'
          break
        case '08':
          statusW = '一级甲等'
          break
        case '09':
          statusW = '一级乙等'
          break
        case '10':
          statusW = '一级丙等'
          break
        case '11':
          statusW = '无等级'
          break
      }
      return statusW
    },
    lmtpric_hosp_lvFormat(row, column) {
      var statusW
      switch (row.lmtpric_hosp_lv) {
        case '1':
          statusW = '一级及以下'
          break
        case '2':
          statusW = '二级'
          break
        case '3':
          statusW = '三级'
          break
      }
      return statusW
    },
    fix_onln_open_flagFormat(row, column) {
      var statusW
      switch (row.fix_onln_open_flag) {
        case '0':
          statusW = '暂停联网'
          break
        case '1':
          statusW = '正常联网'
          break
        case '2':
          statusW = '取消定点'
          break
      }
      return statusW
    },
    dedc_hosp_lvFormat(row, column) {
      var statusW
      switch (row.dedc_hosp_lv) {
        case '1':
          statusW = '一级及以下'
          break
        case '2':
          statusW = '二级'
          break
        case '3':
          statusW = '三级'
          break
        case '9':
          statusW = '未定级'
          break
      }
      return statusW
    },
    fixmedins_typeFormat(row, column) {
      var statusW
      switch (row.fixmedins_type) {
        case '0':
          statusW = '医保中心'
          break
        case '1':
          statusW = '定点医疗机构'
          break
        case '2':
          statusW = '定点零售药店'
          break
      }
      return statusW
    },
    biznet_typeFormat(row, column) {
      var statusW
      switch (row.biznet) {
        case '1':
          statusW = '营利性'
          break
        case '2':
          statusW = '民办非营利'
          break
        case '3':
          statusW = '政府非营利'
          break
      }
      return statusW
    },
    synchronous() {
      var that = this
      that.loading = true
      synchronousAll().then((res) => {
        if (res.code == 0) {
          that.loading = false
          that.$baseMessage('操作成功', 'success')
          that.fetchData()
        } else {
          that.loading = false
          that.$baseMessage('同步失败', 'error')
        }
      })
    },
    // 经营性质维护
    handleBiznet() {
      if (this.selectRows != null && this.selectRows != '') {
        this.biznet = this.selectRows.biznet
        this.dialogFormVisible = true
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleSbApply() {
      if (this.selectRows != null && this.selectRows != '') {
        this.fix_blng_admdvs_sbApply = this.selectRows.fix_blng_admdvs_sbApply
        this.sbApplyFormVisible = true
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    clearBiznet() {
      this.biznet = ''
    },
    saveBiznet(){
      editData({
        id: this.selectRows.id,
        biznet: this.biznet,
      }).then((res) => {
        if (res.code == 0) {
          this.$baseMessage('操作成功', 'success')
          this.dialogFormVisible = false
          this.clearBiznet()
          this.fetchData()
        } else {
          this.$baseMessage('维护失败', 'error')
        }
      })
    },
    saveSbApply(){
      editData({
        id: this.selectRows.id,
        fix_blng_admdvs_sbApply: this.fix_blng_admdvs_sbApply,
      }).then((res) => {
        if (res.code == 0) {
          this.$baseMessage('操作成功', 'success')
          this.sbApplyFormVisible = false
          this.fix_blng_admdvs_sbApply = ''
          this.fetchData()
        } else {
          this.$baseMessage('维护失败', 'error')
        }
      })
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
    save() {
      if (
        null == this.aggrement.medicalCodeList ||
        undefined == this.aggrement.medicalCodeList ||
        this.aggrement.medicalCodeList.length < 1
      ) {
        that.$baseMessage('请选择单位', 'error')
        return false
      }
      editAggrementLevel({
        level: this.aggrement.jgLevel,
        ids: this.aggrement.medicalCodeList,
      }).then((res) => {
        if (res.code == 0) {
          this.$baseMessage('操作成功', 'success')
          this.dialogFormVisible = false
        } else {
          this.$baseMessage('同步失败', 'error')
        }
      })
    },
    getOrgs() {
      console.log(this.aggrement.jgType)
      selectAll({ type: this.aggrement.jgType }).then((res) => {
        if (res.code == 0) {
          this.orgs = res.data
        }
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