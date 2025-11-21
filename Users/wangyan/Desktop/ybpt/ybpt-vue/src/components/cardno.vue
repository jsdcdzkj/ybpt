<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1020px" @close="close">
    <div class="box_card">
      <div class="box_header">信息查询</div>
      <div class="box_content">
        <vab-query-form>
          <el-form :inline="true" :model="queryForm">
            <vab-query-form-left-panel :span="24">
              <el-form-item label="证件类型">
                <el-select v-model="queryForm.username" placeholder="证件类型" style="width: 100%">
                  <el-option label="居民身份证（户口薄）" value="1"></el-option>
                  <el-option label="中国人民解放军军官证" value="2"></el-option>
                  <el-option label="中国人民武装警察警官证" value="3"></el-option>
                  <el-option label="香港特区护照/港澳居民来往内地通行证" value="4"></el-option>
                  <el-option label="奥门特区护照/港澳居民来往内地通行证" value="5"></el-option>
                  <el-option label="台湾居民来往内地通行证" value="6"></el-option>
                  <el-option label="外国人永久居留证" value="7"></el-option>
                  <el-option label="外国人护照" value="8"></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="证件号码">
                <el-input v-model.trim="queryForm.username" placeholder="证件号码" clearable />
              </el-form-item>
              <el-form-item>
                <el-button @click="queryData">重 置</el-button>
                <el-button type="primary" @click="queryData">查 询</el-button>
                <el-button type="primary" @click="openwin">
                  医保电子凭证
                </el-button>
                <el-button disabled>身份证</el-button>
              </el-form-item>
            </vab-query-form-left-panel>
          </el-form>
        </vab-query-form>
      </div>
    </div>
    <div class="box_card">
      <div class="box_header">人员信息</div>
      <div class="box_content">
        <el-table v-loading="listLoading" ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
          highlight-current-row border @current-change="handleCurrentChange" height="260px">
          <template slot="empty">
            <el-empty :image-size="150"></el-empty>
          </template>
          <el-table-column label="选择" width="80" align="center">
            <template slot-scope="scope">
              <el-radio class="radio" v-model="radio" :label="scope.$index" @change.native="getCurrentRow(scope.$index)"
                :disabled="scope.row.Enable == 1 ? false : true"></el-radio>
            </template>
          </el-table-column>
          <el-table-column label="序号" width="80" align="center" show-overflow-tooltip prop="id"></el-table-column>
          <el-table-column label="证件类型" align="center" show-overflow-tooltip prop="username"></el-table-column>
          <el-table-column label="证件号码" align="center" show-overflow-tooltip prop="username"></el-table-column>
          <el-table-column label="人员姓名" align="center" show-overflow-tooltip prop="username"></el-table-column>
          <el-table-column label="性别" align="center" show-overflow-tooltip prop="username"></el-table-column>
          <el-table-column label="出生日期" align="center" show-overflow-tooltip prop="username"></el-table-column>
          <el-table-column label="联系电话" align="center" show-overflow-tooltip prop="username"></el-table-column>
          <el-table-column label="生存状态" align="center" show-overflow-tooltip prop="username"></el-table-column>
        </el-table>

        <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
          :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="save">确 认</el-button>
    </div>
    <scan ref="scan" @data="value1"></scan>
  </el-dialog>
</template>
<script>
import { getList } from '@/api/userManagement'
import Scan from '@/components/scanybpz'
export default {
  name: 'Index',
  components: { Scan },
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
        username: '',
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
  beforeDestroy() { },
  mounted() { },
  methods: {
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['scan'].showDia()
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
      const { data, totalCount } = await getList(this.queryForm)
      this.list = data
      this.total = totalCount
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    showDia(row) {
      if (!row) {
        this.title = '查询'
      } else {
        this.title = '查询'
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
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