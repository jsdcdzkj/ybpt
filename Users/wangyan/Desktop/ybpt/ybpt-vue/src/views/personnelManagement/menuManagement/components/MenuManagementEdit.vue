<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogFormVisible"
            width="500px"
            :close-on-click-modal = "false"
            @close="close"
    >
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-form-item label="标题" prop="meta.title">
                <el-input v-model="form.meta.title" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="路由名称" prop="name">
                <el-input v-model="form.name" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="路径" prop="path">
                <el-input v-model="form.path" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="资源路径" prop="component">
                <el-input v-model="form.component" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="父级菜单" prop="is_show">
                <el-select v-model="form.parent_id" filterable placeholder="请选择" class="w">
                    <el-option
                            v-for="item in pMenuOptions"
                            :key="item.id"
                            :label="item.title"
                            :value="item.id">
                    </el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="是否显示" prop="is_show">
                <el-select v-model="form.is_show" class="w">
                    <el-option value="1" label="是"></el-option>
                    <el-option value="0" label="否"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="重定向类型">
                <el-select v-model="form.redirect" class="w">
                    <el-option value="index" label="index"></el-option>
                    <el-option value="noRedirect" label="noRedirect"></el-option>
                </el-select>
            </el-form-item>
            <el-form-item label="图标">
                <el-input v-model="form.meta.icon" autocomplete="off"></el-input>
            </el-form-item>
            <el-form-item label="排序">
                <el-input v-model="form.sort" autocomplete="off"></el-input>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="save" :loading="loading" :disabled="disabled">
                {{ loading ? '确定中 ...' : '确 定' }}
            </el-button>
        </div>
    </el-dialog>
</template>

<script>
    import {saveMenu, getLayoutMenus} from '@/api/menuManagement'

    export default {
        name: 'MenuManagementEdit',
        data() {
            return {
                loading: false,
                disabled:false,
                form: {
                    id: '',
                    name: '',
                    path: '',
                    component: '',
                    parent_id:'',
                    meta: {
                        title: '',
                        icon: '',
                    },
                    redirect: '',
                    is_show: '',
                    sort: '',
                },
                rules: {
                    'meta.title': [{required: true, trigger: 'blur', message: '请输入标题'}],
                    name: [{required: true, trigger: 'blur', message: '请输入路由名称'}],
                    path: [{required: true, trigger: 'blur', message: '请输入路径'}],
                    component: [{required: true, trigger: 'blur', message: '请输入资源地址'}],
                    is_show: [{required: true, trigger: 'blur', message: '请选择是否显示'}],
                },
                title: '',
                pMenuOptions: [],//父类菜单
                dialogFormVisible: false,
            }
        },
        created() {
            this.getPmenus();
        },
        methods: {
            showEdit(row) {
                if (!row) {
                    this.title = '添加'
                } else {
                    this.title = '编辑'
                    this.form = Object.assign({}, row)
                }
                this.dialogFormVisible = true
            },
            close() {
                this.$refs['form'].resetFields()
                this.form = this.$options.data().form
                this.dialogFormVisible = false
            },
            async getPmenus() {
                const res = await getLayoutMenus()
                if (res.code == "0") {
                    this.pMenuOptions = res.data;
                }
            },
            save() {
                if (this.loading) {
                    return
                }
                this.$refs['form'].validate(async (valid) => {
                    if (valid) {
                        this.loading = true
                        this.disabled = true
                        // 动画关闭需要一定的时间
                        setTimeout(() => {
                            this.loading = false
                            this.disabled = false
                        }, 1000)
                        var formdata_ = this.form;
                        var formData = {
                            id: formdata_.id,
                            parent_id: formdata_.parent_id,
                            title: formdata_.meta.title,
                            router_name: formdata_.name,
                            router_url: formdata_.path,
                            vue_url: formdata_.component,
                            redirect_type: formdata_.redirect,
                            icon: formdata_.meta.icon,
                            is_show: formdata_.is_show,
                            sort: formdata_.sort,
                        }
                        const res = await saveMenu(formData)
                        if (res.code == "0") {
                            this.$baseMessage("保存成功", 'success')
                            this.$emit('fetch-data')
                            this.close()
                        } else {
                            this.$baseMessage(res.msg, 'error')
                        }
                    } else {
                        return false
                    }
                })
            },
        },
    }
</script>
