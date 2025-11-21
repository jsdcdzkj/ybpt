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
              <el-button icon="el-icon-refresh-left" @click="resetForm">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构名称">
                  <el-input
                    v-model.trim="queryForm.org_name"
                    placeholder="请输入"
                    @keyup.enter.native="queryData"
                    clearable
                  />
                </el-form-item>
              </el-col>
              <!-- <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="国家医保编码">
                  <el-input
                    v-model.trim="queryForm.title"
                    placeholder="请输入"
                    @keyup.enter.native="queryData"
                    clearable
                  />
                </el-form-item>
              </el-col> -->
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="医保编码">
                  <el-input
                    v-model.trim="queryForm.medical_code"
                    placeholder="请输入"
                    @keyup.enter.native="queryData"
                    clearable
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="机构等级">
                  <el-select
                    v-model="queryForm.cred_lv"
                    placeholder="请选择"
                    style="width: 100%"
                    clearable
                  >
                    <el-option
                      v-for="item in nationalList"
                      :key="item.id"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="运营商">
                  <el-select
                    v-model="queryForm.operator"
                    placeholder="请选择"
                    style="width: 100%"
                    clearable
                  >
                    <el-option
                      v-for="item in levelList"
                      :key="item.id"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="区域">
                  <el-select
                    v-model="queryForm.area"
                    style="width: 100%"
                    placeholder="请选择"
                    clearable
                  >
                    <el-option
                      v-for="item in admdvs"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="创建时间">
                  <el-date-picker
                    v-model="create_time"
                    @change="changeDate"
                    style="width: 100%"
                    type="monthrange"
                    format="yyyy-MM"
                    value-format="yyyy-MM"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
                    clearable
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
            <span class="tips">终端信息</span>
            <div class="right">
              <el-button
                type="success"
                icon="el-icon-plus"
                v-if="!examine"
                @click="handleAdd"
              >
                新增
              </el-button>
              <el-button
                type="success"
                icon="el-icon-upload2"
                @click="handleExport"
              >
                导出
              </el-button>
            </div>
          </div>
          <el-table
            v-loading="listLoading"
            ref="listTable"
            stripe
            :data="list"
            element-loading-text="正在加载..."
            highlight-current-row
            border
            height="calc(100vh - 570px)"
          >
            <el-table-column
              show-overflow-tooltip
              type="index"
              label="序号"
              align="center"
              width="80px"
            >
              <template #default="{ $index }">
                {{ (queryForm.pageNo - 1) * queryForm.pageSize + $index + 1 }}
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="机构名称"
              align="center"
              prop="org_name"
              min-width="200px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="medical_code"
              label="医保编码"
              align="center"
              width="120px"
            ></el-table-column>

            <el-table-column
              show-overflow-tooltip
              prop="contacts"
              label="联系人"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="contact_number"
              label="联系人手机号"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="cred_lv_name"
              label="机构等级"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="operator_name"
              label="运营商"
              align="center"
              width="90px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="area_name"
              label="行政区域"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="address"
              label="所在街道"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="terminalUserNumber"
              label="开通账号数量"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="is_network_line"
              label="是否专网专线"
              align="center"
              width="120px"
            >
              <!-- 0 否 1 是 -->
              <template #default="{ row }">
                <span v-if="row.is_network_line == 0">否</span>
                <span v-if="row.is_network_line == 1">是</span>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="terminalNetworkNumber"
              label="访问医保网的终端数量"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="terminalNetworkOtherNumber"
              label="同时访问医保网与其他网络的终端数量"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="dumb_number"
              label="哑终端数量"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="antivirus"
              label="使用的杀毒软件"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="status"
              label="审核状态"
              align="center"
              width="120px"
            >
              <template #default="{ row }">
                <!-- 0.未提交 1.审核中 2.已审核 3.已驳回 -->
                <span class="info" v-if="row.status == 0">未提交</span>
                <span class="info" v-if="row.status == 1">审核中</span>
                <span class="info" v-if="row.status == 2">已审核</span>
                <el-popover
                  placement="top-end"
                  v-if="row.status == 3"
                  title="驳回原因："
                  width="200"
                  trigger="hover"
                  :content="row.verify_reason"
                >
                  <el-link slot="reference" type="danger">已驳回</el-link>
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="250"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  v-if="examine && row.status == 1"
                  plain
                  @click="handleshenhe(row)"
                  type="primary"
                  size="mini"
                >
                  审核
                </el-button>
                <el-button
                  plain
                  @click="handleSubmitSub(row, 1)"
                  v-if="row.status != 1 && row.status != 2 && !examine"
                  type="primary"
                  size="mini"
                >
                  提交
                </el-button>
                <el-button
                  plain
                  v-if="row.status != 1 && row.status != 2 && !examine"
                  @click="handlechuli(row)"
                  type="primary"
                  size="mini"
                >
                  编辑
                </el-button>
                <el-button
                  plain
                  @click="handleView(row)"
                  type="primary"
                  size="mini"
                >
                  查看详情
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
    <edit
      ref="edit"
      :levelList="levelList"
      :nationalList="nationalList"
      :admdvs="admdvs"
      @fetch-data="fetchData"
    ></edit>
    <views
      ref="views"
      :levelList="levelList"
      :nationalList="nationalList"
      :admdvs="admdvs"
      @fetch-data="fetchData"
    ></views>
    <!-- 审核的弹窗 -->

    <el-dialog
      title="审核"
      :visible.sync="dialogVisible"
      width="500px"
      :before-close="handleClose"
    >
      <el-form ref="form" :model="ruleForm" :rules="rules" label-width="80px">
        <el-form-item label="审核状态">
          <el-select
            v-model="ruleForm.aggrementLv"
            placeholder="请选择"
            style="width: 100%"
            clearable
          >
            <el-option value="2" label="通过"></el-option>
            <el-option value="3" label="驳回"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item
          label="驳回原因"
          prop="reason"
          v-if="ruleForm.aggrementLv == 3"
        >
          <el-input
            type="textarea"
            v-model="ruleForm.reason"
            placeholder="请输入"
            clearable
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="handleClose">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { selectList, audit, exportExel } from '@/api/terminal'
  import Edit from './components/edit'
  import Views from './components/detail'
  import { getDicts } from '@/api/dictManagement'
  export default {
    name: 'Criterion',
    components: {
      Edit,
      Views,
    },
    data() {
      return {
        levelList: [],
        list: [{}, {}, {}, {}],
        listLoading: false,
        layout: 'total, sizes, prev, pager, next, jumper',
        cardTypes: [], //证件类型
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          org_name: '',
          medical_code: '',
          cred_lv: '',
          operator: '',
          area: '',
          start_time: '',
          end_time: '',
        },
        total: 0,
        admdvs: [],
        ruleForm: {
          aggrementLv: '',
        },
        rules: {
          aggrementLv: [
            { required: true, message: '请选择审核状态', trigger: 'blur' },
          ],
          reason: [
            { required: true, message: '请输入驳回原因', trigger: 'blur' },
          ],
        },
        dialogVisible: false,
        nationalList: [],
        create_time: [],
        status: '',
        examine: false,
      }
    },
    created() {
      const arryRole = JSON.parse(localStorage.getItem('roles'))
      const terminalInfo = 'terminalInfo'
      if (arryRole.includes(terminalInfo)) {
        this.examine = true
      } else {
        this.examine = false
      }
      this.fetchData()
      this.getAdmdvs()
    },
    mounted() {},
    methods: {
      handleshenhe(row, index) {
        this.ruleForm.id = row.id
        this.dialogVisible = true
      },
      handleClose() {
        this.$refs['form'].resetFields()
        this.ruleForm = {
          aggrementLv: '',
          reason: '',
        }
        this.dialogVisible = false
      },
      handleSubmit() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const res = await audit({
              id: this.ruleForm.id,
              status: this.ruleForm.aggrementLv,
              verify_reason: this.ruleForm.reason,
            })
            this.handleClose()
            this.$baseMessage(res.msg, 'success')
            this.fetchData()
          } else {
            return false
          }
        })
      },
      handleSubmitSub(row, index) {
        // 确定要提交吗
        this.$baseConfirm('你确定要提交吗', null, async () => {
          this.listLoading = true
          const { msg } = await audit({
            id: row.id,
            status: index,
          })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      },
      changeDate(val) {
        if (null != this.create_time) {
          this.queryForm.start_time = this.create_time[0]
          this.queryForm.end_time = this.create_time[1]
        } else {
          this.queryForm.start_time = ''
          this.queryForm.end_time = ''
        }
      },
      handleAdd() {
        this.$refs['edit'].showDia()
      },
      handleExport(row) {
        this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
          this.listLoading = true
          await exportExel(this.queryForm).then((res) => {
            let fileName = '终端信息数据导出.xlsx'
            let objectUrl = URL.createObjectURL(new Blob([res.data]))
            const link = document.createElement('a')
            link.download = decodeURI(fileName)
            link.href = objectUrl
            link.click()
            this.listLoading = false
            this.$baseMessage('导出成功！', 'success')
          })
        })
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      handlechuli(row, index) {
        this.$refs['edit'].showDia(row)
      },
      handleView(row, index) {
        this.$refs['views'].showDiaView(row)
      },
      handlecancel(row) {
        this.$baseConfirm('确认进行删除？', '温馨提示', async () => {
          const { msg } = await del({
            id: row.id,
          })
          this.$baseMessage(msg, 'success')
          this.queryData()
        })
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },

      async fetchData() {
        this.listLoading = true
        const res = await selectList(this.queryForm)
        this.list = res.data.records
        this.total = res.data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      resetForm() {
        this.queryForm = this.$options.data().queryForm
        this.create_time = []
        this.queryData()
      },
      async getAdmdvs() {
        const res = await getDicts({ type: 'admdvs-area' })
        if (res.code == '0') {
          this.admdvs = res.data
        }
        const national_info_level = await getDicts({
          type: 'LMTPRIC_HOSP_LV',
        })
        this.nationalList = national_info_level.data
        const levelListArry = await getDicts({ type: 'operator' })
        this.levelList = levelListArry.data
      },
    },
  }
</script>
<style scoped lang="scss">
  ::v-deep {
    .el-popover .el-popover__title {
      font-size: 14px !important;
      font-weight: bold;
    }
  }
</style>
