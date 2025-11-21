<template>
  <div class="index-container">
    <vab-query-form>
      <vab-query-form-left-panel :span="6" style="min-height: 1px;">
        <el-button icon="el-icon-plus" type="primary" @click="handleEdit" v-if="userinfo.user_type == 1 || (userinfo.user_type == 2 && userinfo.isAdmin == 1)|| (userinfo.user_type == 3 && userinfo.isAdmin == 1)">
          添加
        </el-button>
        <!--        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">-->
        <!--          批量删除-->
        <!--        </el-button>-->
      </vab-query-form-left-panel>
      <vab-query-form-right-panel :span="18">
        <el-form :inline="true" :model="queryForm" @submit.native.prevent>
          <el-form-item>
            <el-input v-model.trim="queryForm.username" placeholder="登录账号/用户名" clearable
              @keyup.enter.native="queryData" />
          </el-form-item>
          <el-form-item>
            <el-input v-model.trim="queryForm.idNumber" placeholder="身份证号码" clearable
                      @keyup.enter.native="queryData" />
          </el-form-item>
          <el-form-item label="" prop="user_type">
            <el-select clearable placeholder="账号类型" v-model.trim="queryForm.user_type" class="w" v-if="userinfo.user_type == '1' && (userinfo.org_code ==='320399' || userinfo.org_name =='admin')">
              <el-option value="1" label="市直单位"></el-option>
              <el-option value="2" label="定点医疗机构"></el-option>
              <el-option value="3" label="定点零售药店"></el-option>
              <el-option value="4" label="用人单位"></el-option>
              <el-option value="5" label="体检机构"></el-option>
              <el-option value="6" label="银行"></el-option>
              <el-option value="7" label="非定点医疗机构"></el-option>
              <el-option value="8" label="非定点零售药店"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="userinfo.user_type == '1' && (userinfo.org_code ==='320399' || userinfo.org_name =='admin')">
            <el-select v-model="queryForm.org_code" clearable placeholder="统筹区"
                       style="width: 100%" @change="queryData()">
              <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" type="primary" @click="queryData">
              查询
            </el-button>
            <el-button v-if="userinfo.user_type == 1 && userinfo.org_code == 320399" icon="el-icon-refresh" type="primary" @click="getSynchronization">
              统一门户同步信息
            </el-button>
          </el-form-item>
        </el-form>
      </vab-query-form-right-panel>
    </vab-query-form>

    <el-table v-loading="listLoading" :data="list" border :element-loading-text="elementLoadingText"
      @selection-change="setSelectRows" height="calc(100vh - 360px)">
      <el-table-column show-overflow-tooltip type="index" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="username" label="登录账号" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="name" label="用户名" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="telephone" label="联系方式" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="mailbox" label="邮箱" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="org_name" label="所属机构" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="dept_name" label="所属科室" align="center"></el-table-column>
      <el-table-column show-overflow-tooltip prop="lockFlag" label="是否锁定" align="center">
        <template #default="{ row }">
          <el-tag v-if="row.lockFlag == 1" type="danger">
            锁定
          </el-tag>
          <el-tag v-else type="success">
            未锁定
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip label="权限" align="center">
        <template #default="{ row }">
          <el-tag v-for="(item, index) in row.permissions" :key="index">
            {{ item }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="lockFlag" label="是否管理员" align="center">
        <template #default="{ row }">
          <el-tag v-if="row.isAdmin == 1" type="danger">
            是
          </el-tag>
          <el-tag v-else type="success">
            否
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column  show-overflow-tooltip label="操作" width="240" align="center">
        <template #default="{ row }">
          <el-button size="mini" plain type="primary" @click="handleEdit(row)" v-if="userinfo.user_type == 1 || (userinfo.user_type == 2 && userinfo.isAdmin == 1)|| (userinfo.user_type == 3 && userinfo.isAdmin == 1)">
            编辑
          </el-button>
          <el-button size="mini" plain type="danger" @click="handleDelete(row)" v-if="userinfo.user_type == 1 || (userinfo.user_type == 2 && userinfo.isAdmin == 1)|| (userinfo.user_type == 3 && userinfo.isAdmin == 1)">
            删除
          </el-button>
          <el-button v-if="row.lockFlag == 1" size="mini" plain type="success" @click="handleUnlock(row)">
            解锁
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
      :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
import {doDelete, unlock, getList,synchronization} from '@/api/userManagement'
import Edit from './components/UserManagementEdit'
import {getDicts} from "@/api/dictManagement";

export default {
  name: 'UserManagement',
  components: { Edit },
  data() {
    return {
      list: null,
      admdvs: null,
      userinfo: {},
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
    }
  },
  created() {
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"))
    this.getAdmdvs()
    this.fetchData()
  },
  methods: {
    async getAdmdvs() {
      const res = await getDicts({"type": "admdvs-area"});
      if (res.code == "0") {
        this.admdvs = res.data;
      }
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleEdit(row) {
      if (row.id) {
        this.$refs['edit'].showEdit(row)
      } else {
        this.$refs['edit'].showEdit()
      }
    },
    handleDelete(row) {
      if (row.id) {
        this.$baseConfirm('你确定要删除当前项吗', null, async () => {
          const res = await doDelete({ userId: row.id })
          if (res.code == '0') {
            this.$baseMessage('操作成功！', 'success')
          } else {
            this.$baseMessage(res.msg, 'error')
          }
          this.fetchData()
        })
      } else {
        // if (this.selectRows.length > 0) {
        //   const ids = this.selectRows.map((item) => item.id).join()
        //   this.$baseConfirm('你确定要删除选中项吗', null, async () => {
        //     const { msg } = await doDelete({ ids })
        //     this.$baseMessage(msg, 'success')
        //     this.fetchData()
        //   })
        // } else {
        //   this.$baseMessage('未选中任何行', 'error')
        //   return false
        // }
      }
    },
    handleUnlock(row) {
      this.$baseConfirm('你确定要解锁当前项吗', null, async () => {
          const res = await unlock({ userId: row.id })
          if (res.code == '0') {
            this.$baseMessage('操作成功！', 'success')
          } else {
            this.$baseMessage(res.msg, 'error')
          }
          this.fetchData()
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
      this.listLoading = true
      const res = await getList(this.queryForm)
      if ((res.code = '0')) {
        this.list = res.data.records
        this.total = res.data.total
      }
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    getSynchronization(){
      this.listLoading = true
      synchronization().then((res) => {
        if(res.code == 0 ){
          this.listLoading = false
          this.$baseMessage("操作成功", 'success')
        }else {
          this.listLoading = false
          this.$baseMessage(res.msg, 'error')
        }

      })
    }
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  min-height: calc(#{$base-app-main-height} - 50px) !important;
}
</style>