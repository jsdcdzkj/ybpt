<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogFormVisible"
            width="1000px"
            @close="close"
            :close-on-click-modal="false"
            append-to-body
    >
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
            <el-row :gutter="20">

                <el-form-item label="项目编码">
                    <el-input v-model.trim="form.project_code" placeholder="请选择"
                              @click.native="openwin(form)" readonly class="readonly-input">
                        <el-button
                                slot="append"
                                icon="el-icon-search"
                                @click="openwin"
                        ></el-button>
                    </el-input>
                </el-form-item>
                <el-form-item label="项目名称" prop="projectName" readonly>
                    <el-input v-model="form.project_name" placeholder="请输入" autocomplete="off" readonly></el-input>
                </el-form-item>
                <el-form-item label="项目内涵" prop="projectConnotation" readonly>
                    <el-input v-model="form.projectConnotation" placeholder="请输入" autocomplete="off"
                              readonly></el-input>
                </el-form-item>
                <el-form-item label="除外内容" prop="excludedContent" readonly>
                    <el-input v-model="form.excludedContent" placeholder="请输入" autocomplete="off"
                              readonly></el-input>
                </el-form-item>
                <el-form-item label="计价单位" prop="unit" readonly>
                    <el-input v-model="form.unit" placeholder="请输入" autocomplete="off" readonly></el-input>
                </el-form-item>
                <el-form-item label="医保支付类别" prop="levelOfChargeItem" readonly>
                    <el-input v-model="form.levelOfChargeItem" placeholder="请输入" autocomplete="off"
                              readonly></el-input>
                </el-form-item>
                <el-form-item label="项目说明" prop="explain" readonly>
                    <el-input v-model="form.explain" placeholder="请输入" autocomplete="off" readonly></el-input>
                </el-form-item>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="测算成本(元)" prop="calculate">
                        <el-input
                                placeholder="请输入"
                                v-model.trim="form.calculate"
                                autocomplete="off"
                                type="number"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="建议调整价格(元)" prop="price" class="custemitem">
                        <el-input
                                placeholder="请输入"
                                v-model.trim="form.price"
                                autocomplete="off"
                                type="number"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="上年工作量" prop="workload">
                        <el-input
                                placeholder="请输入"
                                v-model.trim="form.workload"
                                autocomplete="off"
                                type="textarea"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="备注" prop="memo">
                        <el-input
                                placeholder="请输入"
                                v-model.trim="form.memo"
                                autocomplete="off"
                                type="textarea"
                        ></el-input>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
        </div>

        <medicinal ref="medicinal" @fetch-data="fetchData"></medicinal>
    </el-dialog>
</template>

<script>
import {doEdit} from '@/api/userManagement'
import Medicinal from './medicinal'

export default {
    name: 'UserManagementEdit',
    components: {Medicinal},
    data() {
        return {
            form: {
                project_code: '',
                project_name: '',
                projectConnotation: '',
                excludedContent: '',
                unit: '',
                levelOfChargeItem: '',
                explain: '',
                calculate: '',
                price: '',
                workload: '',
                memo: '',
            },
            rules: {
                project_code: [{required: true, trigger: 'blur', message: '请输入'}],
                project_name: [{required: true, trigger: 'blur', message: '请输入'}],
                calculate: [{required: true, trigger: 'blur', message: '请输入'}],
                price: [{required: true, trigger: 'blur', message: '请输入'}],
                workload: [{required: true, trigger: 'blur', message: '请输入'}],
            },
            title: '',
            dialogFormVisible: false,
        }
    },
    created() {
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
        fetchData(row) {
            this.form.project_code = row.provincialProjectCode
            this.form.project_name = row.directoryName
            this.form.projectConnotation = row.projectConnotation
            this.form.excludedContent = row.excludedContent
            this.form.explain = row.explain
            this.form.levelOfChargeItem = row.levelOfChargeItem
            this.form.unit = row.chargeUnit
            this.form.detail_status = 0
            this.form.childOne = row.childOne
            this.form.childThree = row.childThree
            this.form.childTwo = row.childTwo
            this.form.nonChildOne = row.nonChildOne
            this.form.nonChildThree = row.nonChildThree
            this.form.nonChildTwo = row.nonChildTwo
            if (this.form.child_price == '0' || this.form.child_price == '1') {
                this.handleChildPrice(this.form.child_price)
            }
        },
        handleChildPrice(childPrice) {
            if (childPrice == 0) {
                if (this.form.aggrement_lv == '1' || this.form.aggrement_lv == '9' || this.form.aggrement_lv == '') {
                    this.form.org_price = this.form.nonChildOne
                } else if (this.form.aggrement_lv == '2') {
                    this.form.org_price = this.form.nonChildTwo
                } else if (this.form.aggrement_lv == '3') {
                    this.form.org_price = this.form.nonChildThree
                }
            } else if (childPrice == 1) {
                if (this.form.aggrement_lv == '1' || this.form.aggrement_lv == '9' || this.form.aggrement_lv == '') {
                    this.form.org_price = this.form.childOne
                } else if (this.form.aggrement_lv == '2') {
                    this.form.org_price = this.form.childTwo
                } else if (this.form.aggrement_lv == '3') {
                    this.form.org_price = this.form.childThree
                }
            }
        },
        openwin(form) {
            form.index_type = this.form.index_type
            this.$refs['medicinal'].showDia(form)
        },
        close() {
            this.$refs['form'].resetFields()
            this.form = this.$options.data().form
            this.dialogFormVisible = false
        },
        save() {
            if (this.title == '添加') {
                this.form.sign = 'add'
            }
            if (this.title == '编辑') {
                this.form.sign = 'edit'
            }
            this.$refs['form'].validate(async (valid) => {
                if (valid) {
                    this.$emit('fetch-data', this.form)
                    this.close()
                } else {
                    return false
                }
            })
        },
    },
}
</script>
<style scoped lang="scss">
::v-deep {
  .el-input__inner[readonly="readonly"] {
    background-color: #efefef;
  }
  .readonly-input .el-input__inner[readonly="readonly"] {
    background-color: #fff;
  }
}
</style>
