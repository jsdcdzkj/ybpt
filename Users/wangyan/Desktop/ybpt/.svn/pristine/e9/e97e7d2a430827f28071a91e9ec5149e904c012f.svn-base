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
              <el-button icon="el-icon-refresh-left" @click="reseat">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                <el-form-item
                  label="现27位国家编码"
                  class="custemitem"
                  v-show="
                    userinfo.user_type == 1 && userinfo.org_code == 320399
                  "
                >
                  <el-input
                    v-model.trim="queryForm.mcs_code_new"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                <el-form-item
                  label="机构编码"
                  class=""
                  v-show="
                    userinfo.user_type == 1 && userinfo.org_code == 320399
                  "
                >
                  <el-input
                    v-model.trim="queryForm.createUser"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                <el-form-item label="审核状态">
                  <el-select
                    v-model.trim="queryForm.status"
                    class="w"
                    @change="queryData"
                  >
                    <el-option value="0" label="待审核"></el-option>
                    <el-option value="1" label="已受理"></el-option>
                    <el-option value="2" label="已驳回"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col
                :lg="8"
                :md="8"
                :sm="24"
                :xl="8"
                :xs="24"
                v-show="userinfo.user_type == 1 && userinfo.org_code == 320399"
              >
                <el-form-item label="行政区划">
                  <el-select
                    v-model="queryForm.admdvs"
                    placeholder="请选择"
                    clearable
                    style="width: 100%"
                    @change="queryData"
                  >
                    <el-option label="徐州市市本级" value="320399"></el-option>
                    <el-option label="邳州市" value="320382"></el-option>
                    <el-option label="新沂市" value="320381"></el-option>
                    <el-option label="睢宁县" value="320324"></el-option>
                    <el-option label="沛县" value="320322"></el-option>
                    <el-option label="丰县" value="320321"></el-option>
                    <el-option label="铜山区" value="320312"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">耗材收费标识库</span>
            <div class="right">
              <el-button
                type="warning"
                icon="el-icon-edit"
                @click="dialogFormVisible = true"
                v-show="userinfo.user_type == 1 && userinfo.org_code == 320399"
              >
                申诉开关
              </el-button>
              <el-button
                icon="el-icon-upload2"
                type="primary"
                @click="handleExport"
              >
                导出
              </el-button>
              <el-button
                type="success"
                icon="el-icon-plus"
                @click="handleAdd"
                v-show="userinfo.user_type == 2 || userinfo.user_type == 3"
              >
                新增
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
            @selection-change="setSelectRows"
            height="calc(100% - 50px)"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column
              align="center"
              label="现27位国家编码"
              prop="mcs_code_new"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="原27位国家编码"
              prop="mcs_code"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="国家编码调整变动时间"
              prop="mcs_code_time"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="注册备案号"
              prop="reg_fil_no"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="注册备案产品名称"
              prop="reg_fil_prod_name"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="单件产品名称"
              prop="name_individual_product"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="生产企业"
              prop="mcs_entp"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="规格"
              prop="spec"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="型号"
              prop="mol"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="省平台挂网编码"
              prop="product_num"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="定点机构联系人"
              prop="fixmedins_contacts"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="定点机构联系电话"
              prop="fixmedins_phone"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="申诉说明"
              prop="verify_reason"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="驳回原因"
              prop="reason"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="提交时间"
              prop="createTime"
              fixed="right"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="机构编码"
              prop="createUser"
              fixed="right"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="机构名称"
              prop="createUserName"
              fixed="right"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="审核状态"
              prop="status"
              width="120px"
              fixed="right"
              show-overflow-tooltip
            >
              <template #default="scope">
                <el-tag type="info" v-if="scope.row.status == 0">待审核</el-tag>
                <el-tag type="success" v-else-if="scope.row.status == 1">
                  已受理
                </el-tag>
                <el-tag type="danger" v-else="scope.row.status == 2">
                  已驳回
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="200"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handleAdd(row)"
                  type="primary"
                  size="mini"
                  v-if="
                    row.status == 0 &&
                    (userinfo.user_type == 2 || userinfo.user_type == 3)
                  "
                >
                  编辑
                </el-button>
                <el-button
                  plain
                  @click="handleViews(row)"
                  type="primary"
                  size="mini"
                >
                  详情
                </el-button>
                <el-button
                  size="mini"
                  plain
                  type="primary"
                  v-if="row.status == 0 && userinfo.user_type == 1"
                  @click="handleSh(row)"
                >
                  审核
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

    <consumablesSignEdit
      ref="consumablesSignEdit"
      @fetch-data="fetchData"
    ></consumablesSignEdit>
    <consumablesSignAudit
      ref="consumablesSignAudit"
      @fetch-data="fetchData"
    ></consumablesSignAudit>
    <consumablesSignViews
      ref="consumablesSignViews"
      @fetch-data="fetchData"
    ></consumablesSignViews>
    <el-dialog title="提交申诉信息开关" :visible.sync="dialogFormVisible">
      <el-form :model="form">
        <el-form-item label="是否开启" :label-width="formLabelWidth">
          <el-radio v-model="form.isOpen" label="1">开启</el-radio>
          <el-radio v-model="form.isOpen" label="0">关闭</el-radio>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleConfig()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  import { exportExcel, isOpen, isOpenEdit, selectAll } from '@/api/consumables'
  import ConsumablesSignEdit from '@/views/consumables/consumablesSignEdit'
  import ConsumablesSignAudit from '@/views/consumables/consumablesSignAudit'
  import ConsumablesSignViews from '@/views/consumables/consumablesSignViews'

  export default {
    name: 'Index',
    components: {
      ConsumablesSignEdit,
      ConsumablesSignAudit,
      ConsumablesSignViews,
    },
    data() {
      return {
        userinfo: null,
        form: {
          isOpen: '0',
        },
        formLabelWidth: '120px',
        dialogFormVisible: false,
        value1: '',
        checked: false,
        isShow: false,
        list: [],
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        tableHeight: '100%',
        elementLoadingText: '正在加载...',
        queryForm: {
          mcs_code_new: '',
          mcs_code: '',
          createUser: '',
          reg_fil_no: '',
          admdvs: '',
          pageNo: 1,
          pageSize: 10,
        },
      }
    },
    created() {
      this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
      this.fetchData()
      this.isConfig()
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      async handleAdd(row) {
        if (this.isOpen) {
          this.$refs['consumablesSignEdit'].showDia(row)
        } else {
          this.$baseMessage(
            '暂时无法提交申请,请联系医保中心咨询详情！',
            'error'
          )
        }
      },
      handleExport(row) {
        this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
          this.listLoading = true
          await exportExcel(this.queryForm).then((res) => {
            let fileName = '耗材收费标识库查询导出.xls'
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
      handleSh(row) {
        this.$refs['consumablesSignAudit'].showDia(row)
      },
      handleViews(row) {
        this.$refs['consumablesSignViews'].showDia(row)
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      async isConfig() {
        this.listLoading = true
        const { data } = await isOpen()
        this.form = data
        if (data.isOpen == '1') {
          this.isOpen = true
        } else {
          this.isOpen = false
        }
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async handleConfig() {
        this.dialogFormVisible = false
        this.listLoading = true
        const res = await isOpenEdit(this.form)
        if (res.code == 0) {
          this.$baseMessage('操作成功', 'success')
        } else {
          this.$baseMessage('请尝试刷新页面', 'error')
        }

        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      fetchData() {
        var that = this
        selectAll(this.queryForm).then((res) => {
          if (res.code == 0) {
            that.list = res.data.records
            that.total = res.data.total
          } else {
            that.$baseMessage(res.msg, 'error')
          }
        })
      },
      //获取表格序号
      getIndex($index) {
        //表格序号
        return (
          (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
        )
      },
      reseat() {
        this.queryForm.createUser = ''
        this.queryForm.mcs_code_new = ''
        this.queryForm.mcs_code = ''
        this.queryForm.status = ''
        this.queryForm.admdvs = ''
        this.fetchData()
      },
    },
  }
</script>
