<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="60%"
    @close="close"
    append-to-body
  >
   <el-form :model="form" label-width="120px">
       <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="电子档案编号">
                   <el-input
                      v-model.trim="queryForm.username"
                      clearable
                      class="input-with-select"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="业务流水号">
                    <el-input
                      v-model.trim="queryForm.username"                                  
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="归档类型">
                    <el-input
                      v-model.trim="queryForm.username"                  
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="上传时间">
                    <el-input
                      v-model.trim="queryForm.username"                  
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                  <el-form-item label="申请人名称">
                    <el-input
                      v-model.trim="queryForm.username"                  
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="备注">
                    <el-input
                      v-model.trim="queryForm.username" 
                      type="textarea"
                      :rows="5"                 
                      disabled
                    />
                  </el-form-item>
                </el-col>
                
              </el-row>
   </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>  
      <el-button type="primary" @click="openwin">查看详情</el-button>   
    </div>
    <views ref="views" @fetch-data="fetchData"></views>
  </el-dialog>
</template>
<script>
import Views from '@/components/fileview2'
import { getList, doDelete } from '@/api/userManagement'
export default {
  name: 'Index',
  components: {Views},
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
    openwin() {
      this.$refs['views'].showDia()
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
        this.title = '鉴定定点医药机构查询'
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