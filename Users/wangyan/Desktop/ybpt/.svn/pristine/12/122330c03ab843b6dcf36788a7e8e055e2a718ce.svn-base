<template>
    <el-dialog :title="title" :visible.sync="dialogFormVisible" :append-to-body="true" width="500px" @close="close">
        <el-table :data="tableData" border stripe class="w" highlight-current-row height="500px">
            <el-table-column prop="item_name" label="项目名称" align="center">
                <template v-slot="scope">
                    <span v-if="scope.row.is_generic == '0'" style="color: red">{{scope.row.item_name}}</span>
                    <span v-if="scope.row.is_generic == '1'" >{{scope.row.item_name}}</span>
                </template>
            </el-table-column>
            <el-table-column prop="item_no" label="项目编码" align="center">
                <template v-slot="scope">
                    <span v-if="scope.row.is_generic == '0'" style="color: red">{{scope.row.item_no}}</span>
                    <span v-if="scope.row.is_generic == '1'" >{{scope.row.item_no}}</span>
                </template>
            </el-table-column>
        </el-table>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {doEdit} from '@/api/userManagement'
    import {getEntity} from "@/api_check/taocan";

    export default {
        name: 'UserManagementEdit',
        data() {
            return {
                form: {
                    id: '',
                },
                tableData: [],
                title: '',
                dialogFormVisible: false,
            }
        },
        created() {
        },
        methods: {
            showDia(row) {
                this.form.id = row.id;
                if (!row) {
                    this.title = '套餐项目清单'
                } else {
                    this.title = '套餐项目清单'
                }
                this.dialogFormVisible = true
                this.changeTable();
            },
            async changeTable() {
                const {data} = await getEntity(this.form);
                this.tableData = data

            },
            close() {
                this.dialogFormVisible = false
            },
        },
    }
</script>
