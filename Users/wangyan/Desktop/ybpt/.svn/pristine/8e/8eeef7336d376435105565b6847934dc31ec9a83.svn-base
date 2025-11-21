<template>
    <el-dialog
        :title="title"
        :visible.sync="dialogFormVisible"
        width="700px"
        @close="close"
        append-to-body
        top="300px"
    >

        <el-table
            :data="tableData"
            style="width: 100%">
            <el-table-column
                prop="dept_no"
                align="center"
                label="部门编码"
                width="180">
            </el-table-column>
            <el-table-column
                prop="dept_name"
                align="center"
                label="部门名称">
            </el-table-column>
            <el-table-column
                width="150"
                align="center"
                label="操作">
                <template #default="{ row }">
                    <el-button plain @click="deptAdd(row)" type="primary" size="mini">编辑</el-button>
                    <el-button plain @click="deptDel(row)" type="danger" size="mini">删除</el-button>
                </template>
            </el-table-column>
        </el-table>
        <deptadd ref="deptadd" @fetch-data="getDepartmentListData"></deptadd>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">关 闭</el-button>
            <el-button @click="deptAdd" type="success" icon="el-icon-circle-plus-outline">新 增</el-button>
        </div>
    </el-dialog>
</template>
<script>
import Deptadd from './components/deptadd'
import {getDepartmentListByEmpCode, saveDepartment} from "@/api_check/EmployingInfo";

export default {
    name: 'Index',
    components: {Deptadd},
    data() {
        return {
            title: '',
            dialogFormVisible: false,
            // tableData: [{
            //     id: '1',
            //     dept_no: '14234234',
            //     dept_name: '部门名称1',
            // }, {
            //     id: '2',
            //     dept_no: '20160504',
            //     dept_name: '部门名称2',
            // },]
            tableData: [],
            empCode: "",

        }
    },
    mounted() {
    },
    methods: {
        showDia(row) {
            // 通过emp_code获取部门信息
            this.title = '部门信息维护'
            this.dialogFormVisible = true
            this.empCode = row.emp_no;
            this.getDepartmentListData(this.empCode)
        },
        getDepartmentListData(empCode) {
            getDepartmentListByEmpCode(empCode).then(res => {
                if (res.code === 0) {
                    this.tableData = res.data;
                }
            })
        },
        deptAdd(row) {
            if (row.id) {
                this.$refs['deptadd'].showEdit(row)
            } else {
                this.$refs['deptadd'].showEdit(this.empCode)
            }
        },

        //  deptDel(row) {
        //     row.is_del = '1';
        //      saveDepartment(row).then(res => {
        //         if (res.code === 0) {
        //             this.$baseMessage(res.msg, 'success')
        //         }
        //     })
        // },

        async deptDel(row) {
            row.is_del = '1';
            const res = await saveDepartment(row);
            console.log(res)
            if (res.code === 0) {
                this.$baseMessage(res.msg, 'success')
                this.getDepartmentListData(row.emp_code);
            } else {
                this.$baseMessage(res.msg, 'error')
                this.getDepartmentListData(row.emp_code);
            }
        },
        close() {
            this.dialogFormVisible = false
        },
    },
}
</script>