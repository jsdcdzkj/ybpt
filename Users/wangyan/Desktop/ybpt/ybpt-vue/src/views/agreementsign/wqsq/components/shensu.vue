<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogFormVisible"
            width="500px"
            @close="close"
            append-to-body
    >
        <el-form ref="form" :model="form" :rules="rules" style="padding-right:0">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="" prop="content">
                        <el-input
                                v-model.trim="form.content"
                                autocomplete="off"
                                type="textarea"
                                :rows="10"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="save" :loading="loading">{{ loading ? '提交中 ...' : '提 交' }}</el-button>
        </div>
    </el-dialog>
</template>

<script>
    import { onSave } from '@/api_net/netTagAppeal'

    export default {
        name: 'netTagAppealEdit',
        data() {
            return {
                loading: false,
                form: {
                    content: '',
                },
                rules: {
                    content: [
                        {required: true, trigger: 'blur', message: '请输入内容'},
                    ],
                },
                title: '',
                dialogFormVisible: false,
            }
        },
        created() {
        },
        methods: {
            showDia(row) {
                if (!row) {
                    this.title = '请填写申诉信息'
                } else {
                    this.title = '编辑';
                    this.form = Object.assign({}, row);
                }
                this.dialogFormVisible = true;
            },
            close() {
                this.$refs['form'].resetFields();
                this.form = this.$options.data().form;
                this.dialogFormVisible = false
            },
            save() {
                this.loading = true
                this.$refs['form'].validate(async (valid) => {
                    if (valid) {
                        const { data } = await onSave(this.form);
                        this.$baseMessage('申诉成功', 'success');
                        this.loading = false
                        this.close();
                    } else {
                        this.loading = false
                        return false;
                    }
                })
            },
        },
    }
</script>
