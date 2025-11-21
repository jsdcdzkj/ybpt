<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogFormVisible"
            width="660px"
            @close="close"
            :close-on-click-modal = "false"
    >
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="单位名称" prop="imp_name">
                        <el-input
                                v-model.trim="form.imp_name"
                                autocomplete="off"
                                disabled
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="申请年份" prop="apply_year">
                        <el-date-picker
                                v-model="form.apply_year"
                                type="year" value-format="yyyy"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <el-form-item label="财务账户" prop="finance_account">
                  <el-input
                      v-model.trim="form.finance_account"
                      autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <el-form-item label="开户银行" prop="deposit_bank">
                  <el-input
                      v-model.trim="form.deposit_bank"
                      autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <el-form-item label="银行账户" prop="bank_account">
                  <el-input
                      v-model.trim="form.bank_account"
                      autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <el-form-item label="联系人" prop="contacts">
                  <el-input
                      v-model.trim="form.contacts"
                      autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <el-form-item label="联系方式" prop="contacts_phone">
                  <el-input
                      v-model.trim="form.contacts_phone"
                      autocomplete="off"
                  ></el-input>
                </el-form-item>
              </el-col>
<!--                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
<!--                    <el-form-item label="存续状态" prop="item_state">-->
<!--                        <el-switch-->
<!--                                v-model="form.exist_state"-->
<!--                                active-text="存续"-->
<!--                                inactive-text="终止"-->
<!--                                active-value="1"-->
<!--                                inactive-value="0"-->
<!--                        ></el-switch>-->
<!--                    </el-form-item>-->
<!--                </el-col>-->
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="申请原由" prop="apply_reason">
                        <el-input
                                v-model.trim="form.apply_reason"
                                autocomplete="off"
                                type="textarea"
                                :rows="5"
                        ></el-input>
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
import {edit} from '@/api_check/zztijian'

export default {
    name: 'UserManagementEdit',
    data() {
        return {
            loading: false,
            setSelectRows(val) {
                this.selectRows = val
            },
            form: {
                imp_name: '',//申请单位名称
                apply_year: '',//申请年份
                apply_reason: '',//申请原由
                status: '0',//审核状态 (待审:0，通过:1， 驳回:2)
                exist_state: '0',//存续状态 （终止:0，存续:1）
                is_del: '0',//删除标志
                finance_account:'',// 财务账户
                deposit_bank:'', // 开户银行
                bank_account:'',// 银行账户
                contacts:'',// 联系人
                contacts_phone:'',// 联系方式
            },
            rules: {
                apply_year: [{required: true, trigger: 'blur', message: '请选择申请年份'}],
                apply_reason: [{required: true, trigger: 'blur', message: '请输入申请原由'}],
                finance_account: [{required: true, trigger: 'blur', message: '请输入财务账户'}],
                deposit_bank: [{required: true, trigger: 'blur', message: '请输入开户银行'}],
                bank_account: [{required: true, trigger: 'blur', message: '请输入银行账户'}],
                contacts: [{required: true, trigger: 'blur', message: '请输入联系人'}],
                contacts_phone: [{required: true, trigger: 'blur', pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误'}],
            },
            title: '',
            dialogFormVisible: false,
        }
    },
    created() {
    },
    methods: {
        showDia(row, employingInfo) {
            this.form.imp_id = employingInfo.id
            this.form.imp_no = employingInfo.emp_no
            this.form.imp_name = employingInfo.emp_name
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
                    const {code, msg} = await edit(this.form)
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
