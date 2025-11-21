<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="60%"
    @close="close"
    append-to-body
  >
    <vab-query-form>
      <el-form :inline="true" :model="queryForm">
        <vab-query-form-left-panel :span="20">
          <el-form-item label="门慢门特病种目录代码">
            <el-input v-model.trim="queryForm.opsp_dise_code" clearable style="width:150px;" />
          </el-form-item>
          <el-form-item label="门慢门特病种目录名称">
            <el-input v-model.trim="queryForm.opsp_dise_name" clearable style="width:200px;" />
          </el-form-item>
          <el-form-item label="病种类型">
            <el-select v-model="queryForm.dise_type_code"  style="width:150px;" @change="changeDiseType()">
              <el-option label="普通门慢门特病种" value="10"></el-option>
              <el-option label="门诊慢性病" value="11"></el-option>
              <el-option label="门诊特殊病" value="12"></el-option>
              <el-option label="特殊病种" value="3"></el-option>
            </el-select>
          </el-form-item>
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="4">
          <el-form-item>
            <el-button @click="reseat()">重 置</el-button>
            <el-button type="primary" @click="queryData">查 询</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </el-form>
    </vab-query-form>
    <el-table
      ref="listTable"
      stripe
      :data="list"
      :element-loading-text="elementLoadingText"
      border
      @current-change="handleCurrentChange"
      height="260px"
      highlight-current-row
    >
      <template slot="empty">
        <el-empty :image-size="150"></el-empty>
      </template>
      <el-table-column label="选择" width="80" align="center">
        <template slot-scope="scope">
          <el-radio :label="scope.row.opsp_dise_code" v-model="id"><span></span></el-radio>
        </template>
      </el-table-column>
      <el-table-column
        label="序号"
        width="80"
        align="center"
        show-overflow-tooltip
        prop="id"
        v-if="isShow"
      ><template slot-scope="scope">
        <span v-text="getIndex(scope.$index)"> </span>
      </template></el-table-column>

      <el-table-column
        label="门慢门特病种目录编码"
        align="center"
        show-overflow-tooltip
        prop="opsp_dise_code"
        v-if="isShow"
      ></el-table-column>
      <el-table-column
        label="门慢门特病种目录名称"
        align="center"
        show-overflow-tooltip
        prop="opsp_dise_name"
        v-if="isShow"
      ></el-table-column>
      <el-table-column
              label="门慢门特病种大类代码"
              align="center"
              show-overflow-tooltip
              prop="opsp_dise_majcls_code"
              v-if="isShow"
      ></el-table-column>
      <el-table-column
              label="门慢门特病种大类代码"
              align="center"
              show-overflow-tooltip
              prop="opsp_dise_majcls_name"
              v-if="isShow"
      ></el-table-column>
      <el-table-column
            label="险种类型"
            align="center"
            show-overflow-tooltip
            prop="insutype"
            v-if="isShow"
    ></el-table-column>
      <el-table-column
              label="特殊病种编码"
              align="center"
              show-overflow-tooltip
              prop="opsp_dise_code"
              v-if="!isShow"
      ></el-table-column>
      <el-table-column
              label="特殊病种名称"
              align="center"
              show-overflow-tooltip
              prop="opsp_dise_name"
              v-if="!isShow"
      ></el-table-column>
      <el-table-column
              label="病种内涵"
              align="center"
              show-overflow-tooltip
              prop="dise_cont"
              v-if="!isShow"
      ></el-table-column>
      <el-table-column
              label="险种类型"
              align="center"
              show-overflow-tooltip
              prop="insutype"
              v-if="!isShow"
      ></el-table-column>
      <el-table-column
              label="人员类别"
              align="center"
              show-overflow-tooltip
              prop="psn_type"
              v-if="!isShow"
      ></el-table-column>
      <el-table-column
              label="医疗类别"
              align="center"
              show-overflow-tooltip
              prop="med_type"
              v-if="!isShow"
      ></el-table-column>
      <el-table-column
              label="病种限额"
              align="center"
              show-overflow-tooltip
              prop="dise_lmt"
              v-if="!isShow"
      ></el-table-column>
      <el-table-column
              label="病种限额类型"
              align="center"
              show-overflow-tooltip
              prop="dise_lmt_type"
              v-if="!isShow"
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
import { OpspDiseList } from '@/api/opspdise.js'
import { diagList } from '@/api/diagListb.js'
export default {
  name: 'Index',
  components: {},
  data() {
    return {
      currentRow: null,
      id: '',
      tableData: [],
      title: '',
      dialogFormVisible: false,
      value1: '',
      checked: false,
      isShow: true,
      diag: false,
      list: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        opsp_dise_code: '',
        opsp_dise_name: '',
        dise_type_code: '10',
      },
      ishow:true
    }
  },
  created() {
    // this.fetchData()
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
      this.id=val.opsp_dise_code;
      this.selectRows = val ;
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
      var that = this ;
      if(that.queryForm.dise_type_code != 3){
        OpspDiseList(that.queryForm).then((res) => {
          that.list = res.data.records;
          that.total = res.data.total;
          if (res.data.total == 0) {
            that.$baseMessage('未查询到任何相关数据', 'error')
          }
        })
      }else {
        diagList(that.queryForm).then((res) => {
          that.list = res.data.records;
          that.total = res.data.total;
          if (res.data.total == 0) {
            that.$baseMessage('未查询到任何相关数据', 'error')
          }
        })
      }
    },
    showDia() {
      this.list = [];
      this.total = 0;
      this.queryForm.opsp_dise_code = "";
      this.queryForm.opsp_dise_name = "";
      this.dialogFormVisible = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      var that = this;
      that.$baseMessage('操作成功', 'success')
      console.log(that.selectRows);
      that.selectRows.dise_type_code = that.queryForm.dise_type_code ;
      this.$emit('fetch-data', that.selectRows)
      this.close()
    },
    changeDiseType(){
      var that = this ;
      that.queryData() ;
      if(that.queryForm.dise_type_code == 10 ||that.queryForm.dise_type_code == 11 || that.queryForm.dise_type_code == 12){
        that.isShow = true ;
      }else {
        that.isShow = false ;
      }
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    reseat(){
      this.queryForm.opsp_dise_code="" ;
      this.queryForm.opsp_dise_name="" ;
      this.queryForm.dise_type_code="" ;
    }
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-radio__label {
    display: none;
  }
  .el-table__body-wrapper{
    height: calc(100% - 40px)!important;
  }
}
</style>