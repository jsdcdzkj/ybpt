<template>
    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close"
        :close-on-click-modal="false">
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标准名称" prop="standard_name">
                        <el-input v-model.trim="form.standard_name" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标准年份" prop="year">
                        <el-date-picker v-model.trim="form.year" type="year" value-format="yyyy"></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="标准金额" prop="standard_money">
                        <el-input-number v-model.trim="form.standard_money" :min="0"></el-input-number>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="年体检次数" prop="examination_num">
                        <el-input-number v-model.trim="form.examination_num" :min="0"></el-input-number>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="撤销次数" prop="cancel_num">
                        <el-input-number v-model.trim="form.cancel_num" :min="0"></el-input-number>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
        </div>
    </el-dialog>
</template>

<script>
import { edit } from "@/api_check/tijianbiaozhun";

export default {
    name: 'edit',
    data() {
        return {
            loading: false,
            form: {
                year: '',//年份 (格式为 yyyy  例如2022)
                examination_num: '1',//年体检次数 (年体检次数)
                cancel_num: '',//撤销次数
                standard_name: '',//标准名称
                standard_money: '',//标准金额 (体检金额标准)
                is_del: '0',//删除标志
                administrative_unit: '',
            },
            rules: {
                year: [{ required: true, trigger: 'blur', message: '请输入年份' },],
                standard_name: [{ required: true, trigger: 'blur', message: '请输入标准名称' },],
                standard_money: [{ required: true, trigger: 'blur', message: '请输入标准金额' },],
                examination_num: [{ required: true, trigger: 'blur', message: '请输入年体检次数' },],
                cancel_num: [{ required: true, trigger: 'blur', message: '请输入撤销次数' },],
            },
            title: '',
            dialogFormVisible: false,
        }
    },
    created() {
    },
    methods: {
        showDia(row) {
            this.getDict()
            if (!row) {
                this.title = '添加'
            } else {
                this.title = '编辑'
                this.form = Object.assign({}, row)
            }
            this.dialogFormVisible = true
        },
        async getDict() {

        },
        close() {
            this.$refs['form'].resetFields()
            this.form = this.$options.data().form
            this.dialogFormVisible = false
        },
        save() {
            if (this.loading) {
                return
            }
            this.$refs['form'].validate(async (valid) => {
                if (valid) {
                    this.loading = true
                    // 动画关闭需要一定的时间
                    setTimeout(() => {
                        this.loading = false
                    }, 400)
                    const { code, msg } = await edit(this.form)
                    if (code == -1) {
                        this.$baseMessage(msg, 'error')
                    } else {
                        this.$baseMessage(msg, 'success')
                    }

                    this.$emit('fetch-data')
                    this.close()
                } else {
                    return false
                }
            })
        },
    },
}
</script>
