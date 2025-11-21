<template>
    <el-dialog
        :title="title"
        :visible.sync="dialogFormVisible"
        width="500px"
        @close="close"
        append-to-body
    >
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="部门编码" prop="dept_no">
                        <el-input
                            v-model.trim="form.dept_no"
                            autocomplete="off"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="部门名称" prop="dept_name">
                        <el-input
                            v-model.trim="form.dept_name"
                            autocomplete="off"
                        ></el-input>
                    </el-form-item>
                </el-col>

            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
        </div>
    </el-dialog>
</template>

<script>

import {saveDepartment} from "@/api_check/EmployingInfo";

export default {
    name: 'deptadd',
    data() {
        return {
            form: {},
            originalForm: {},
            rules: {
                dept_no: [
                    {required: true, trigger: 'blur', message: '请输入部门编码'},
                ],
                dept_name: [{required: true, trigger: 'blur', message: '请输入部门名称'}],
            },
            title: '',
            dialogFormVisible: false,
        }
    },
    created() {
    },
    methods: {
        showEdit(row) {
            console.log(row)
            if (row.id != null) {
                this.title = '编辑'
                this.form = Object.assign({}, row);
            } else {
                this.title = '添加'
                this.form = {};
                this.form.emp_code = row;
                this.form.is_del = "0";
            }
            this.dialogFormVisible = true
        },
        close() {
            this.dialogFormVisible = false
        },
        save() {
            this.$refs['form'].validate(async (valid) => {
                try {
                    if (valid) {
                        const {msg} = await saveDepartment(this.form)
                        this.$baseMessage(msg, 'success')
                        this.$emit('fetch-data', this.form.emp_code)
                        this.close()
                    }
                } catch (e) {
                }
            })
        },
    },
}
</script>
<style lang="scss" scoped>
::v-deep {
    .el-date-editor.el-input {
        width: 100%
    }
}


</style>
