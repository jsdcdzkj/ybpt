<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">查询条件</span>
            <el-button icon="el-icon-search" type="primary" @click="queryData" class="right">
              查 询
            </el-button>
          </div>
          <el-form label-width="90px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="考核单名称">
                  <el-input v-model.trim="queryForm.assess_name" placeholder="请输入"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="机构类型">
                  <el-select
                      v-model="queryForm.org_type"
                      style="width: 100%"
                      @change="getLevelList"
                      clearable
                  >
                    <el-option
                        label="医疗机构"
                        value="1"
                    ></el-option>
                    <el-option
                        label="零售药店"
                        value="2"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="协议等级">
                  <el-select
                      v-model="queryForm.aggrement_lv"
                      style="width: 100%"
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
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                <el-form-item label="考核年度">
                  <el-date-picker
                      v-model="queryForm.year_of_assessment"
                      type="year"
                      placeholder="选择年"
                      format="yyyy"
                      value-format="yyyy">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">考核单列表</span>
            <el-button
                type="success"
                class="right"
                icon="el-icon-plus"
                @click="handleAdd()"
            >
              新增
            </el-button>
          </div>
          <el-table
              v-loading="listLoading"
              ref="listTable"
              stripe
              :data="tableData"
              :element-loading-text="elementLoadingText"
              highlight-current-row
              border
              @current-change="handleCurrentChange"
              height="calc(100% - 50px)"
          >
            <el-table-column
                show-overflow-tooltip
                type="index"
                label="序号"
                align="center"
                width="80px"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                label="考核单名称"
                align="center"
                prop="assess_name"
            >
            </el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="org_type"
                label="机构类型"
                align="center"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="category_name"
                label="类别"
                align="center"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="aggrement_lv"
                label="协议等级"
                align="center"
            ></el-table-column>
            <el-table-column
                show-overflow-tooltip
                prop="year_of_assessment"
                label="考核年度"
                align="center"
            ></el-table-column>

            <el-table-column
                show-overflow-tooltip
                label="操作"
                width="230"
                align="center"
            >
              <template #default="{ row }">
                <el-button
                    plain
                    @click="handleAdd(row)"
                    type="primary"
                    size="mini"
                >
                  编辑
                </el-button>
                <el-button
                    plain
                    @click="handlecancel(row)"
                    type="danger"
                    size="mini"
                >
                  删除
                </el-button>
                <el-button
                    plain
                    @click="handleAdd(row,'copy')"
                    type="primary"
                    size="mini"
                >
                  复制
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
    <edit ref="edit" @fetch-data="queryData"></edit>
  </div>
</template>

<script>
import Edit from './components/edit'
import {delAssess, getAssessDetail, getAssessList} from '@/api/assessment'

export default {
  name: 'bingzhonghc',
  components: {Edit},
  data() {
    return {
      value1: '',
      value3: '',
      checked: false,
      isShow: false,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      levelList: [],
      aList: [
        {id: "1", name: '一级'},
        {id: "2", name: '二级'},
        {id: "3", name: '三级'},
        {id: "9", name: '未定级'},
      ],
      bList: [
        {id: "4", name: 'A级'},
        {id: "5", name: 'B级'},
        {id: "6", name: 'C级'},
      ],
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
      tableData: []
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
    handleCurrentChange(val) {
      this.selectRows = val
    },

    handleAdd(row,type) {
      if(row){
        getAssessDetail({"id":row.id}).then((res) => {
          if (res.code == 0) {
            this.$refs['edit'].showDia(res.data,type)
          }
        })
      }else{
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

    handlecancel(row) {
      var that = this;
      if (row.id) {
        this.$baseConfirm('确认要删除吗？', null, async () => {
          await delAssess({"assessment_id": row.id}).then((res) => {
            if (res.code == 0) {
              that.$baseMessage("删除成功！", 'success')
              that.queryData()
            }else{
              that.$baseMessage(res.msg, 'error')
            }
          })

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
      const res = await getAssessList(this.queryForm)
      this.tableData = res.data.records;
      this.total = res.data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    getLevelList() {
      var that = this;
      if (that.queryForm.org_type == "1") {
        that.levelList = that.aList;
        that.queryForm.aggrement_lv = "";
      } else if (that.queryForm.org_type == "2") {
        that.levelList = that.bList;
        that.queryForm.aggrement_lv = "";
      } else {
        that.levelList = []
      }

      if (that.queryForm.aggrement_lv != undefined) {
        that.queryForm.aggrement_lv = "";
      }
    }
  },
}
</script>
