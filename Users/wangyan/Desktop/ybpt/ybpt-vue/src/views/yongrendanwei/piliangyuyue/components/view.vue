<template>
  <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1000px" @close="close" append-to-body>
    <el-table :data="list4" border stripe class="w" highlight-current-row height="500px">

      <el-table-column prop="name" label="人员名单" align="center">
        <el-table-column show-overflow-tooltip prop="NAME" label="姓名" align="center" width="80px"></el-table-column>
        <el-table-column show-overflow-tooltip label="身份证号" align="center" prop="CERTNO" width="220px">
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="SEX" width="120px" label="性别" align="center" :formatter="sex">
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="AGE" label="年龄" align="center"></el-table-column>
        <el-table-column show-overflow-tooltip prop="APPLY_DATE" label="预约时间" align="center">
        </el-table-column>
      </el-table-column>


    </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <!-- <el-button type="primary" @click="save">确 定</el-button> -->
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'UserManagementEdit',
  props: {
    list4: []
  },
  data() {
    return {
      tableData: [
      ],
      title: '',
      dialogFormVisible: false,
      list: null,
      queryForm: {
        rid: ''
      }
    }
  },
  created() {
    // this.fetchData()
  },
  methods: {
    // async fetchData() {
    //   this.queryForm.rid = this.value1
    //   console.log(this.value1)
    //   this.listLoading = true
    //   console.log(this.queryForm)
    //   const { data } = await findCiviWorkerByRid(this.queryForm)
    //   this.list = data
    //   setTimeout(() => {
    //     this.listLoading = false
    //   }, 300)
    // },
    sex(row, index) {
      if (row.SEX == 1) {
        return '男'
      } else {
        return '女'
      }
    },
    showDia(row) {
      this.title = '人员名单'
      this.dialogFormVisible = true
    },
    close() {
      this.dialogFormVisible = false
    },
  },
}
</script>
