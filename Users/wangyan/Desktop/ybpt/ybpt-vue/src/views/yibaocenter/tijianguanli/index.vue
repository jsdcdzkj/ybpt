<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="项目名称">
                  <el-input v-model.trim="queryForm.item_name" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="项目编号">
                  <el-input v-model.trim="queryForm.item_no" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="项目类型">
                  <el-select clearable v-model="queryForm.item_type" style="width: 100%">
                    <el-option v-for="item in item_type_option" :key="item.value" :label="item.label" :value="item.value">
                    </el-option>
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
            <span class="tips">体检项管理列表</span>
            <div class="right">
              <el-button type="success" icon="el-icon-plus" @click="handleAdd">
                新增
              </el-button>
            </div>
          </div>
          <el-table
              v-loading="listLoading"
              ref="listTable"
              stripe
              :data="list"
              :element-loading-text="elementLoadingText"
              highlight-current-row
              border
              @current-change="handleCurrentChange"
              height="calc(100% - 50px)"
              @selection-change="setSelectRows"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column
                label="序号"
                width="80"
                align="center"
                show-overflow-tooltip
            >
              <template #default="scope">
                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column
                show-overflow-tooltip
                label="项目名称"
                align="center"
                prop="item_name"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="item_no"
                label="项目编号"
                align="center"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="item_type_name"
                label="项目类型"
                align="center"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="is_generic"
                label="是否通用"
                align="center"
            >
              <template #default="scope">
                {{ scope.row.is_generic == 1 ? '是' : '否' }}
              </template>
            </el-table-column>
            <el-table-column
                show-overflow-tooltip
                label="当前状态"
                align="center"
            >
              <template #default="scope">
                {{ scope.row.item_state == 1 ? '启用' : '禁用' }}
              </template>
            </el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="year"
                label="所属年份"
                align="center"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="createTime"
                label="创建时间"
                align="center"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="createUser"
                label="创建人"
                align="center"
            ></el-table-column>

            <el-table-column
                show-overflow-tooltip
                label="操作"
                width="200"
                align="center"
                fixed="right"
            >
              <template #default="{ row }">
                <el-button v-show="sysUser.org_code == row.org_id"
                    plain
                    @click="handleAdd(row)"
                    type="primary"
                    size="mini"
                >编辑
                </el-button>
                <el-button v-show="sysUser.org_code == row.org_id"
                    plain
                    @click="handleDelete(row)"
                    type="danger"
                    size="mini"
                >删除
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
    <edit ref="edit" @fetch-data="fetchData"></edit>

  </div>
</template>

<script>
import Edit from './components/edit'
import {edit, getList, toEdit} from "@/api_check/tijianguanli";
import {getDicts} from "@/api/dictManagement";

export default {
  name: 'Index',
  components: {Edit},
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      item_type_option: '',
      sysUser: [],
      queryForm: {
        item_name: '',
        item_no: '',
        item_type: '',
        pageNo: 1,
        pageSize: 10,
      },
    }
  },
  created() {
    this.fetchData()
  },
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {
    setSelectRows(val) {
      this.selectRows = val
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleAdd(row) {
      if (row.id) {
        this.$refs['edit'].showDia(row)
      } else {
        this.$refs['edit'].showDia()
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
    handleDelete(row) {
      if (row.id) {
        this.$baseConfirm('你确定要删除当前项吗', null, async () => {
          const {code, msg} = await edit({ids: row.id, is_del: '1'})
          if (code == -1) {
            this.$baseMessage(msg, 'error')
          } else {
            this.$baseMessage(msg, 'success')
          }
          await this.fetchData()
        })
      } else {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.map((item) => item.id).join()
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await edit({ids})
            this.$baseMessage(msg, 'success')
            await this.fetchData()
          })
        } else {
          this.$baseMessage('未选中任何行', 'error')
          return false
        }
      }
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async fetchData() {
      await this.getInit();
      this.listLoading = true
      const {data} = await getList(this.queryForm)
      this.list = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getInit() {
      const {data} = await toEdit()
      this.sysUser = data.data
      const res = await getDicts({"type": "item_type"});
      this.item_type_option = res.data;
    },
    reseat() {
      this.queryForm.item_name = ''
      this.queryForm.item_no = ''
      this.queryForm.item_type = ''
      this.queryData()
    }
  },
}
</script>

