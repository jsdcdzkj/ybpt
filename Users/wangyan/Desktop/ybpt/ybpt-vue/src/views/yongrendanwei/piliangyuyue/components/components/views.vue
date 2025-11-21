<template>
  <div>
    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close" append-to-body>
      <el-table :data="tableData" border stripe class="w" highlight-current-row height="500px"
        @selection-change="setSelectRows" :row-class-name="changeColor">
        <el-table-column prop="NAME" label="项目名称" align="center">
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="ITEM_NO" label="项目编号" align="center"> </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="close">取 消</el-button>
        <!-- <el-button type="primary" @click="save">确 定</el-button> -->
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { findItemToMealByPid } from "@/api_check/personSubscribeRecord";
export default {
  name: 'UserManagementEdit',
  data() {
    return {
      title: '',
      dialogFormVisible: false,
      tableData: [],
    }
  },
  created() { },
  methods: {
    changeColor(row) {
      if (row.row.GENERIC == "0") {
        console.log("开始");
        return 'statistics-warning-row'
      }
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    showDia(row) {
      findItemToMealByPid(row.id).then(res => {
        this.tableData = res.data
      })
      for (const item of this.tableData) {
        if (item.GENERIC == "0") {
          item.NAME = item.NAME + "---赠送项目"
        }
      }
      if (!row) {
        this.title = '套餐项目清单'
      } else {
        this.title = '套餐项目清单'
      }
      this.dialogFormVisible = true
    },
    close() {
      this.dialogFormVisible = false
    },
  },
}
</script >
<style>
.el-table .statistics-warning-row td .cell {
  color: #ef0606 !important;
}
</style>
