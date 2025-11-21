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
          <el-form-item label="省项目编码">
            <el-input v-model.trim="queryForm.provincialProjectCode" @keyup.enter.native="queryData" clearable/>
          </el-form-item>
          <el-form-item label="医保目录名称">
            <el-input v-model.trim="queryForm.directoryName" @keyup.enter.native="queryData" clearable/>
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
              :label="scope.row.provincialProjectCode"
              class="radio"
          ></el-radio>
        </template>
      </el-table-column>

      <el-table-column show-overflow-tooltip label="省项目编码" align="center" prop="provincialProjectCode" width="250px">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="directoryName" label="医保目录名称" align="center" width="250px">
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="chargeUnit" label="计价单位" align="center" width="120px"></el-table-column>
      <el-table-column show-overflow-tooltip prop="projectConnotation" label="项目内涵" align="center" width="300px"></el-table-column>
      <el-table-column show-overflow-tooltip prop="excludedContent" label="除外内容" align="center" width="300px"></el-table-column>
      <el-table-column show-overflow-tooltip prop="explain" label="说明" align="center" width="300px"></el-table-column>
      <el-table-column show-overflow-tooltip prop="chargeType" label="收费类别" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="levelOfChargeItem" label="收费项目等级" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="restrictedUseSign" label="限制使用标志" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="nonChildOne" label="非儿童一级限价" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="nonChildTwo" label="非儿童二级限价" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="nonChildThree" label="非儿童三级限价" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="childOne" label="儿童一级限价（六周岁及以下）" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="childTwo" label="儿童二级限价（六周岁及以下）" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="childThree" label="儿童三级限价（六周岁及以下）" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="disabledSoldier" label="一至六级残疾军人二次限价" align="center" width="120px" ></el-table-column>
      <el-table-column show-overflow-tooltip prop="contentOfChange" label="变更内容" align="center" width="120px" ></el-table-column>

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
import {zlProjectMedicineList} from "@/api/drug";

export default {
  name: 'Index',
  components: {},
  data() {
    return {
      currentRow: null,
      radio: '',
      form: {},
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
      if(val){
        this.radio = val.provincialProjectCode
        this.selectRows = val
      }
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
      if('政府非营利' != this.form.index_natures && '4' == this.form.index_type){
        this.queryForm.addType = '0'
      }
      if('政府非营利' != this.form.index_natures && '7' == this.form.index_type){
        this.queryForm.addType = '1'
      }

      const res =  await zlProjectMedicineList(this.queryForm)
      this.list = res.data.records
      this.total = res.data.total
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    showDia(form) {
      console.log(form)
      this.form.index_natures = form.index_natures
      this.form.index_type = form.index_type
      if(form.project_code){
        this.radio = form.project_code
      }
      this.title = '项目查询'
      this.dialogFormVisible = true
      this.fetchData()
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