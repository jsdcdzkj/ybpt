<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="55%"
    @close="close"
    append-to-body
  >
    <vab-query-form>
      <el-form :inline="true" :model="queryForm">
        <vab-query-form-left-panel :span="20">
          <el-form-item label="病种">
            <el-input v-model.trim="queryForm.diseName" clearable />
          </el-form-item>
          <el-form-item label="病种代码">
            <el-input v-model.trim="queryForm.diseCode" clearable />
          </el-form-item>
            
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="4">
          <el-form-item>
            <el-button @click="queryData">重 置</el-button>
            <el-button type="primary" @click="queryData">查 询</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </el-form>
    </vab-query-form>
    <el-table
      v-loading="listLoading"
      ref="listTable"
      stripe
      :data="tableData"
      :element-loading-text="elementLoadingText"
      highlight-current-row
      border
      @current-change="handleCurrentChange"
      height="260px"
    >
      <template slot="empty">
        <el-empty :image-size="150"></el-empty>
      </template>
      <el-table-column label="选择" width="80" align="center">
        <template slot-scope="scope">
          <el-radio
            class="radio"
            v-model="radio"
            :label="scope.$index" 
          ></el-radio>
        </template>
      </el-table-column>
      <el-table-column
        label="序号"
        type="index"
        width="80"
        align="center"
        show-overflow-tooltip
      ></el-table-column>
      <el-table-column
        label="病种"
        align="center"
        show-overflow-tooltip
        prop="diseName"
      ></el-table-column>
      <el-table-column
        label="病种代码"
        align="center"
        show-overflow-tooltip
        prop="diseCode"
      ></el-table-column>
      <el-table-column
        label="病种类型"
        align="center"
        show-overflow-tooltip
        prop="diseClassName"
      ></el-table-column>
      
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

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
import { getDiseList } from '@/api/mmmtDisease'
export default {
  name: 'diseasetype',
  components: {},
  data() {
    return {
      tableData: [],
      currentRow: null,
      radio: '',
      title: '',
      dialogFormVisible: false,
      value1: '',
      checked: false,
      isShow: false,
      listLoading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        diseCode: '',
        diseName: '',
      },
    }
  },
  created() {
    this.fetchData()
  },
  porps: {
    //父组件传递过来的初始选中值，根据自己项目需求设置
    chooseData: {
      type: Object,
    },
  },
  watch: {
    //观察是否有父组件传递的初始值或者变化，重新选中
    chooseData(val) {
      if (val) {
        this.radio = false
        this.getInitChoose()
      }
    },
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    handleCurrentChange(val) {
      this.selectRows = val
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    getCurrentRow(index) {
      this.radio = index
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    //设置单选框选择状态
    getInitChoose() {
      if (this.chooseData) {
        let index = this.tableData.findIndex(
          (item) => item.userUuid == this.chooseData.id
        )
      }
      if (index > -1) {
        this.radio = index
      }
    },
    async fetchData() {
      this.listLoading = true
      const { data } = await getDiseList(this.queryForm)
      this.tableData = data.records
      this.total = data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    showDia(row) {
      if (!row) {
        this.title = '病种'
      } else {
        this.title = '查询'
        this.form = Object.assign({}, row)
      }
      this.queryForm.diseName = "";
      this.queryForm.diseCode = "";
      this.dialogFormVisible = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$emit('fetch-data',this.selectRows)
      this.close()
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-radio__label {
    display: none;
  }
}
</style>