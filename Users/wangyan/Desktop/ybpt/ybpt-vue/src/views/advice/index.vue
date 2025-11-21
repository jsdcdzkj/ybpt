<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">查询条件</span>
            <el-button
              icon="el-icon-search"
              type="primary"
              class="right"
              @click="queryData"
            >
              查 询
            </el-button>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="isAdmin">
                <el-form-item label="机构编码">
                  <el-input
                    v-model.trim="queryForm.org_code"
                    placeholder="请输入"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="isAdmin">
                <el-form-item label="机构名称">
                  <el-input
                    v-model.trim="queryForm.org_name"
                    placeholder="请输入"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item
                  label="统筹区"
                  v-if="
                    isAdmin &&
                    (org_code === '320399' || userinfo.org_name == 'admin')
                  "
                >
                  <el-select
                    v-model="queryForm.fix_blng_admdvs"
                    clearable
                    style="width: 100%"
                    @change="queryData()"
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
                    v-model="queryForm.queryDate"
                    style="width: 100%"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    type="daterange"
                    range-separator="至"
                    start-placeholder="开始日期"
                    end-placeholder="结束日期"
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
            <span class="tips">价格调整列表</span>
            <el-button
              type="success"
              class="right"
              icon="el-icon-plus"
              @click="handleAdd"
              v-if="user_type == '2' || user_type == '3'"
            >
              新增
            </el-button>
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
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
            <el-table-column
              show-overflow-tooltip
              type="index"
              label="序号"
              align="center"
              width="80px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="org_name"
              label="机构名称"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="org_code"
              label="机构编码"
              align="center"
              width="180px"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              prop="fix_blng_admdvs_name"
              label="统筹区"
              align="center"
            ></el-table-column>
            <!--                        <el-table-column show-overflow-tooltip prop="aggrement_lv" label="协议等级" align="center"></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="natures"
              label="经营性质"
              align="center"
            ></el-table-column>
            <!--                        <el-table-column show-overflow-tooltip prop="user_type" label="定点类型" align="center"></el-table-column>-->
            <el-table-column
              show-overflow-tooltip
              prop="createTime"
              label="创建时间"
              align="center"
              width="180px"
            ></el-table-column>
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
                  @click="handleDetail(row)"
                  type="primary"
                  size="mini"
                >
                  详情
                </el-button>
                <el-button
                  plain
                  @click="handlecancel(row)"
                  type="danger"
                  size="mini"
                  v-if="user_type == '2' || user_type == '3'"
                >
                  删除
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
    <AddList ref="addList" @fetch-data="fetchData"></AddList>
    <views ref="views" @fetch-data="fetchData"></views>

    <add ref="add" @fetch-data="fetchData"></add>
    <pdf ref="pdf" @fetch-data="fetchData"></pdf>
  </div>
</template>

<script>
  import Add from './components/add.vue'
  import AddList from './components/addList.vue'
  import Views from './components/detail.vue'
  import Pdf from './components/pdfView.vue'
  import { getPage, doDelete } from '@/api/advice'
  import { getDicts } from '@/api/dictManagement'

  export default {
    name: 'Index',
    components: { AddList, Views, Add, Pdf },
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
        admdvs: [],
        isAdmin: false,
        userinfo: {},
        user_type: '',
        username: '',
        org_code: '',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          project_name: '',
          project_code: '',
          org_name: '',
          org_code: '',
          type: '',
          status: '',
          fix_blng_admdvs: '',
          queryDate: [],
        },
      }
    },
    created() {
      this.roles = JSON.parse(localStorage.getItem('roles'))
      this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
      console.log(this.userinfo)
      this.user_type = this.userinfo.user_type
      this.username = this.userinfo.username
      this.org_code = this.userinfo.org_code

      console.log(this.userinfo)
      if (this.userinfo.user_type == '1') {
        this.queryForm.fix_blng_admdvs = this.userinfo.org_code
      } else {
        this.queryForm.org_code = this.userinfo.org_code
      }
      if (this.user_type == '1') {
        this.isAdmin = true
        this.isShow = true
      } else {
        this.isAdmin = false
      }
      this.fetchData()
      this.getAdmdvs()
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      async getAdmdvs() {
        const res = await getDicts({ type: 'admdvs-area' })
        if (res.code == '0') {
          this.admdvs = res.data
        }
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      handleAdd(row) {
        if (row.id) {
          this.$refs['addList'].showDia(row)
        } else {
          this.$refs['addList'].showDia()
        }
      },
      // 详情查看
      handleDetail(row) {
        this.$refs['pdf'].showDia(row)
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
        this.$refs['views'].showDia(row.id)
      },
      handlecancel(row) {
        if (row.id) {
          this.$baseConfirm('确认进行删除？', null, async () => {
            await doDelete({ id: row.id })
            await this.fetchData()
            this.$baseMessage('删除成功', 'success')
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
      async fetchData() {
        this.listLoading = true
        getPage(this.queryForm).then((res) => {
          if (res.code == 0) {
            this.list = res.data.records
            this.total = res.data.total
          }
        })
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
    },
  }
</script>
