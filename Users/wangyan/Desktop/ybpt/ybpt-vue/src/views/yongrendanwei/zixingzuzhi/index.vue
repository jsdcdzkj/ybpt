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
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-show="isShow">
                <el-form-item label="单位名称">
                  <el-input v-model.trim="queryForm.imp_name" @keyup.enter.native="queryData" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="申请时间">
                  <el-date-picker v-model="queryForm.apply_year" type="year" value-format="yyyy"></el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">自行组织列表</span>
            <div class="right">
              <el-button type="success" icon="el-icon-plus" @click="handleAdd" v-if="sysUser.user_type == 4">
                新增
              </el-button>
            </div>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" border @current-change="handleCurrentChange"
            height="calc(100% - 50px)" @selection-change="setSelectRows">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column label="序号" width="80" align="center" show-overflow-tooltip>
              <template #default="scope">
                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="单位名称" align="center" prop="imp_name" width="320px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="apply_year" width="120px" label="申请年份" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="imp_count" label="申请单位总人数" width="140px" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="apply_reason" label="申请原由" align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="存续状态" align="center">
              <template #default="scope">
                {{ scope.row.exist_state == 0 ? '终止' : '' }}
                {{ scope.row.exist_state == 1 ? '存续' : '' }}
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="审核状态" align="center" width="120px">
              <template #default="scope">
                <el-tag type="info" v-if="scope.row.status == 0">待审</el-tag>
                <el-tag type="success" v-else-if="scope.row.status == 1">通过</el-tag>
                <el-tag type="danger" v-else>驳回</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="审核时间" align="center" prop="verify_time" min-width="185"></el-table-column>
            <el-table-column show-overflow-tooltip label="审核人" align="center" prop="verify_user"></el-table-column>
            <el-table-column show-overflow-tooltip label="审核意见" align="center" prop="verify_reason"></el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="220" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handleAdd(row)" type="primary" size="mini"
                  v-show="row.status != 1 && sysUser.user_type == 4">编辑
                </el-button>
                <el-button plain @click="handleDelete(row)" type="danger" size="mini"
                  v-show="row.status != 1 && sysUser.user_type == 4">删除
                </el-button>
                <el-button plain @click="handleSh(row, 1)" type="success" size="mini"
                  v-show="row.status == 0 && sysUser.user_type == 1">同意
                </el-button>
                <el-button plain @click="handleSh(row, 2)" type="danger" size="mini"
                  v-show="row.status == 0 && sysUser.user_type == 1">驳回
                </el-button>
                <el-button plain @click="handleView(row)" type="info" size="mini"
                           >详情
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <shenhe ref="shenhe" @fetch-data="fetchData"></shenhe>
    <views ref="views" @fetch-data="fetchData"></views>

  </div>
</template>

<script>
import { edit, getList, toEdit } from "@/api_check/zztijian";
import Edit from './components/edit';
import Shenhe from './components/shenhe';
import Views from './components/view';

export default {
  name: 'Index',
  components: { Edit, Shenhe,Views },
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
      sysUser: [],
      employingInfo: [],
      formVerify: {
        status: '',//审核状态 (待审:0，通过:1， 驳回:2)
        verify_ids: '',//审核ids
      },
      queryForm: {
        imp_name: '',
        apply_year: '',
        pageNo: 1,
        pageSize: 10,
      },
    }
  },
  created() {
    var userinfo = JSON.parse(localStorage.getItem("userinfo"));
    console.log(userinfo);
    if (userinfo.user_type == 1) {
      this.isShow = true
    }
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
    async handleAdd(row) {
      if (row.id) {
        this.$refs['edit'].showDia(row, this.employingInfo)
      } else {
        this.$refs['edit'].showDia(null, this.employingInfo)
      }
    },
    async handleView(row) {

      if (row.id) {
        this.$refs['views'].showDia(row)
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
    handleSh(row, type) {
      if (type == 1) {
        this.formVerify.verify_ids = row.id
        this.formVerify.status = '1'
        this.$baseConfirm('你确定要【审核通过】当前项吗', null, async () => {
          const { code, msg } = await edit(this.formVerify)
          if (code == -1) {
            this.$baseMessage(msg, 'error')
          } else {
            this.$baseMessage(msg, 'success')
          }
          await this.fetchData()
        })
      } else {
        this.$refs['shenhe'].showDia(row.id)
      }
    },

    handleDelete(row) {
      if (row.id) {
        this.$baseConfirm('你确定要删除当前项吗', null, async () => {
          const { msg } = await edit({ id: row.id, is_del: '1' })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        if (this.selectRows != '' && this.selectRows != null) {
          const ids = this.selectRows.map((item) => item.id).join()
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const { msg } = await edit({ ids })
            this.$baseMessage(msg, 'success')
            this.fetchData()
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
      const { data } = await getList(this.queryForm)
      this.list = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async getInit() {
      const { data } = await toEdit()
      this.sysUser = data.data.sysUser
      this.employingInfo = data.data.employingInfo
      console.log(this.employingInfo)
    },
    reseat() {
      this.queryForm.imp_name = ''
      this.queryForm.apply_year = ''
      this.queryData()
    }
  },
}
</script>
