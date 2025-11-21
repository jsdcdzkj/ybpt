<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      append-to-body
      width="60%"
      @close="close"
  >
    <vab-query-form>
      <el-form :inline="true" :model="queryForm">
        <vab-query-form-left-panel :span="20">
          <el-form-item label="国家药品代码">
            <el-input v-model.trim="queryForm.nationalDrugCode" clearable/>
          </el-form-item>
          <el-form-item label="医保药品名称">
            <el-input v-model.trim="queryForm.drugNames" clearable/>
          </el-form-item>
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="4">
          <el-form-item>
            <el-button type="primary" @click="queryData">查 询</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </el-form>
    </vab-query-form>
    <el-table
        ref="listTable"
        v-loading="listLoading"
        :data="list"
        :element-loading-text="elementLoadingText"
        border
        height="260px"
        highlight-current-row
        stripe
        @current-change="handleCurrentChange"
    >
      <template slot="empty">
        <el-empty :image-size="150"></el-empty>
      </template>
      <el-table-column label="选择" width="80" align="center">
        <template slot-scope="scope">
          <el-radio
              v-model="radio"
              :label="scope.row.nationalDrugCode"
              class="radio"
          ></el-radio>
        </template>
      </el-table-column>
      <el-table-column
          align="center"
          label="国家药品代码"
          prop="nationalDrugCode"
          show-overflow-tooltip
          width="250px"
      ></el-table-column>
      <el-table-column
          align="center"
          label="医保药品名称"
          prop="drugNames"
          show-overflow-tooltip
          width="250px"
      ></el-table-column>
      <el-table-column
          show-overflow-tooltip
          prop="purchaseCeilingPrice"
          label="省集中采购上限价"
          align="center"
          width="120px"
      ></el-table-column>
      <el-table-column
          align="center"
          label="单位"
          prop="unit"
          show-overflow-tooltip
          width="140px"
      ></el-table-column>
      <el-table-column
          align="center"
          label="医保支付类别"
          prop="paymentCategory"
          show-overflow-tooltip
          width="140px"
      ></el-table-column>
      <el-table-column
          align="center"
          label="医保剂型"
          prop="dosageForm"
          show-overflow-tooltip
          width="140px"
      ></el-table-column>
      <el-table-column
          align="center"
          label="产品名编码"
          prop="productNameCoding"
          show-overflow-tooltip
          width="140px"
      ></el-table-column>
      <el-table-column
          align="center"
          label="注册名称"
          prop="registeredName"
          show-overflow-tooltip
          width="140px"
      ></el-table-column>
      <el-table-column
          align="center"
          label="商品名称"
          prop="productName"
          show-overflow-tooltip
          width="140px"
      ></el-table-column>
      <el-table-column
          align="center"
          label="实际剂型"
          prop="actualDosageForm"
          show-overflow-tooltip
          width="140px"
      ></el-table-column>
      <el-table-column
          align="center"
          label="实际规格"
          prop="actualSpecification"
          show-overflow-tooltip
          width="140px"
      ></el-table-column>
    </el-table>

    <el-pagination
        :current-page="queryForm.pageNo"
        :layout="layout"
        :page-size="queryForm.pageSize"
        :total="total"
        background
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
import {chinesePatentMedicineList} from "@/api/drug";

export default {
  name: 'Index',
  components: {},
  data() {
    return {
      currentRow: null,
      radio: '',
      tableData: [],
      title: '',
      dialogFormVisible: false,
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
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {
    handleCurrentChange(val) {
      this.radio = val.nationalDrugCode
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
      const res =  await chinesePatentMedicineList(this.queryForm)
      this.list = res.data.records
      this.total = res.data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    showDia(project_code) {
      if(project_code){
        this.radio = project_code
      }
      this.title = '中成药列表'
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