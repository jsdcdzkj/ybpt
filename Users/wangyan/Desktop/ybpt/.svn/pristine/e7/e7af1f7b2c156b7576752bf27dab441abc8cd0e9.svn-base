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

            <el-table-column  prop="name" label="项目名称" align="center">
                <template v-slot="scope">
                    <span v-if="scope.row.is_generic == '0'" style="color: red">{{scope.row.name}}</span>
                    <span v-if="scope.row.is_generic == '1'" >{{scope.row.name}}</span>
                </template>
            </el-table-column>
            <el-table-column prop="code" label="项目编码" align="center">
                <template v-slot="scope">
                    <span v-if="scope.row.is_generic == '0'" style="color: red">{{scope.row.code}}</span>
                    <span v-if="scope.row.is_generic == '1'" >{{scope.row.code}}</span>
                </template>
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
    data() {
        return {
            tableData: [],
            title: '',
            dialogFormVisible: false,
        }
    },
    created() {
    },
    methods: {
        showDia(row) {
            if (!row) {
                this.title = '套餐项目清单'
            } else {
                console.log(row)
                this.tableData = row
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
