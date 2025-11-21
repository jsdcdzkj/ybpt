<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary">查 询</el-button>
              <el-button icon="el-icon-refresh-left">重 置</el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="姓名">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="身份证号">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>             
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="参保状态">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="异地就医">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="体检资格">
                  <el-input v-model.trim="queryForm.username" />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">人员信息列表</span>
            <div class="right">
              <el-button type="primary" icon="el-icon-plus" @click="handleAdd">
                新增
              </el-button>
              <el-button
                type="danger"
                icon="el-icon-delete"
                @click="handleDelete"
              >
                全部删除
              </el-button>
             
              <el-button icon="el-icon-user" @click="handleUser">
                单位人员增减
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
            height="420px"
            @selection-change="setSelectRows"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column
              show-overflow-tooltip
              type="selection"
              fixed="left"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="id"
              label="序号"
              align="center"
              width="80px"
              fixed="left"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="姓名"
              align="center"
              prop="username"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="truename"
              width="120px"
              label="性别"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="bumen"
              label="年龄"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="phone"
              label="身份证号"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="email"
              label="统筹区"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="weixin"
              label="所属区"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="leixing"
              label="单位名称"
              align="center"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="zhuangtai"
              align="center"
              label="单位编码"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="zhuangtai"
              align="center"
              label="单位类型"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="zhuangtai"
              align="center"
              label="参保险种"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="zhuangtai"
              align="center"
              label="参保状态"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="zhuangtai"
              align="center"
              label="异地就医"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="zhuangtai"
              align="center"
              label="死亡标志"
              width="120px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              align="center"
              label="体检资格"
              width="120px"
            >
              <template #default="{ row }">
                <el-switch
                  v-model="row.zhuangtai"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                ></el-switch>
              </template>
            </el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="操作"
              width="160"
              align="center"
              fixed="right"
            >
              <template #default="{ row }">
                <el-button
                  plain
                  @click="handleAdd(row)"
                  type="primary"
                  size="mini"
                  icon="el-icon-edit"
                ></el-button>
                <el-button
                  plain
                  @click="handleDelete(row)"
                  type="danger"
                  size="mini"
                  icon="el-icon-delete"
                ></el-button>
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
    <usermana ref="usermana" @fetch-data="fetchData"></usermana>
    <usershenhe ref="usershenhe" @fetch-data="fetchData"></usershenhe>
    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
import Edit from './components/edit'
import Usermana from './components/adminuser'
import Usershenhe from './components/usershenhe'
import { getList, doDelete } from '@/api/userManagement'
export default {
  name: 'Index',
  components: {Edit,Usermana,Usershenhe },
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
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
    }
  },
  created() {
    this.fetchData()
  },
  beforeDestroy() {},
  mounted() {},
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
    handleUser() {
      this.$refs['usermana'].showDia()
    },
    handleShenhe() {
      this.$refs['usershenhe'].showDia()
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
          const { msg } = await doDelete({ ids: row.id })
          this.$baseMessage(msg, 'success')
          this.fetchData()
        })
      } else {
        if (this.selectRows != ''&&this.selectRows != null) {
          const ids = this.selectRows.map((item) => item.id).join()
          this.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const { msg } = await doDelete({ ids })
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
    handleTb(val) {
      if (val==1) {
        this.$baseConfirm('你确定要初始化同步数据吗？', null, async () => {    
          const loading = this.$loading({
          lock: true,
          text: '数据初始化同步中...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        setTimeout(() => {
          loading.close();
          this.$baseMessage('初始化成功', 'success')
        }, 2000);      
                    
        })
      }else{
        this.$baseConfirm('你确定要增量同步数据吗？', null, async () => {   
           const loading = this.$loading({
          lock: true,
          text: '数据增加同步中...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        setTimeout(() => {
          loading.close();
          this.$baseMessage('增量同步成功', 'success')
        }, 2000);          
        })
      }
    },
    async fetchData() {
      this.listLoading = true
      const { data, totalCount } = await getList(this.queryForm)
      this.list = data
      this.total = totalCount
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
