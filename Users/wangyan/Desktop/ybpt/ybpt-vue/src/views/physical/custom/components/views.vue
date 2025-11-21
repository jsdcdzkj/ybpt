<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-table
                :data="tableData"
                border
                stripe
                class="w"
                highlight-current-row
                height="500px"
              >               
               
                <el-table-column prop="name" label="项目名称" align="center"></el-table-column>
              
              </el-table>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <!-- <el-button type="primary" @click="save">确 定</el-button> -->
    </div>
  </el-dialog>
</template>

<script>
import { doEdit } from '@/api/userManagement'
export default {
  name: 'UserManagementEdit',
  data() {    
    return {         
      tableData: [
        {
          date: '2016-05-02',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1518 弄',
        },
        {
          date: '2016-05-04',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1517 弄',
        },
        {
          date: '2016-05-01',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1519 弄',
        },
        {
          date: '2016-05-03',
          name: '王小虎',
          address: '上海市普陀区金沙江路 1516 弄',
        },
      ],
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {},
  methods: {
    showDia(row) {
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
</script>
