<template>
    <el-dialog :title="title" :visible.sync="dialogFormVisible" width="1000px" @close="close"
        :close-on-click-modal="false">
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="机构名称" prop="org_name">
                        <el-input v-model.trim="form.org_name" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="医保编码" prop="medical_insurance_num">
                        <el-input v-model.trim="form.medical_insurance_num" autocomplete="off"
                            :disabled="medical_insurance_num_input"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="授权码" prop="authorizationCode">
                        <el-input v-model.trim="form.authorizationCode" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="机构联系人" prop="org_linkman">
                        <el-input v-model.trim="form.org_linkman" autocomplete="off"></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="机构联系电话" prop="org_phone">
                        <el-input oninput="value=value.replace(/[^0-9]/g,'')" v-model.trim="form.org_phone"
                            autocomplete="off"></el-input>
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
import { getDicts } from "@/api/dictManagement";
import { edit, toEdit } from "@/api_check/yiliaojigou";

export default {
    name: 'edit',
    data() {
        return {
            loading: false,
            administrative_divisions_options: null,
            admdvs_options: null,
            credit_level_options: null,
            national_info_level_options: null,
            ownership_options: null,
            medical_insurance_num_input: false,
            form: {
                org_name: '',//机构名称
                org_linkman: '',//机构联系人
                org_phone: '',//联系电话
                medical_insurance_num: '',//医保编码
                credit_level: '',//医保信用等级 （A级:1，B级:2，C级:3）
                credit_level_name: '',//医保信用等级 （A级:1，B级:2，C级:3）
                national_info_level: '',//国家医保信息编码 (一级:1，二级:2，三级:3，未定级:4)
                national_info_level_name: '',//国家医保信息编码 (一级:1，二级:2，三级:3，未定级:4)
                administrative_divisions: '',//所在行政区 (行政区划字典表)
                administrative_divisions_name: '',//所在行政区 (行政区划字典表)
                detail_address: '',//详细地址
                ownership: '',//所有制形式 (公立:1，非公立:2)
                ownership_name: '',//所有制形式 (公立:1，非公立:2)
                credit_code: '',//社会统一信用代码
                admdvs: '',//所属统筹区 (统筹区)
                admdvs_name: '',//所属统筹区 (统筹区)
                is_del: '0',//删除标志
                administrative_unit: '',//父级行政单位
                authorizationCode: '',//授权码
            },
            rules: {
                org_name: [{ required: true, trigger: 'blur', message: '请输入机构名称' },],
                authorizationCode: [{ required: true, trigger: 'blur', message: '请输入授权码' },],
                medical_insurance_num: [{ required: true, trigger: 'blur', message: '请输入医保编码' },],
                org_linkman: [{ required: true, trigger: 'blur', message: '请输入机构联系人' },],
                org_phone: [
                    {
                        pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/,
                        message: '请输入正确的手机号',
                    },
                ],
            },
            title: '',
            dialogFormVisible: false,
        }
    },
    created() {
    },
    methods: {
        getProjectNum() {
            const projectTime = new Date() // 当前中国标准时间
            const Year = projectTime.getFullYear() // 获取当前年份 支持IE和火狐浏览器.
            const Month = projectTime.getMonth() + 1 // 获取中国区月份
            const Day = projectTime.getDate() // 获取几号
            var CurrentDate = Year
            if (Month >= 10) { // 判断月份和几号是否大于10或者小于10
                CurrentDate += Month
            } else {
                CurrentDate += '0' + Month
            }
            if (Day >= 10) {
                CurrentDate += Day
            } else {
                CurrentDate += '0' + Day
            }
            return CurrentDate
        },
        showDia(row) {

            if (!row) {
                this.getDict()
                // 调用获取当前日期的方法加四位随机数  赋值表单中的项目编号
                // this.form.medical_insurance_num = 'JG' + this.getProjectNum() + Math.floor(Math.random() * 1000000)  // 如果是6位或者8位随机数，相应的 *1000000或者 *100000000就行了
                this.medical_insurance_num_input = true
                this.title = '添加'
            } else {
                console.log(111)
                this.medical_insurance_num_input = true
                this.title = '编辑'
                this.form = Object.assign({}, row)
            }
            this.dialogFormVisible = true
        },
        async getDict() {
            const { data } = await toEdit();
            this.form.medical_insurance_num = data.data
            const admdvs = await getDicts({ "type": "ADMDVS" })
            this.administrative_divisions_options = admdvs.data
            this.admdvs_options = admdvs.data
            const credit_level = await getDicts({ "type": "credit_level" })
            this.credit_level_options = credit_level.data
            const national_info_level = await getDicts({ "type": "national_info_level" })
            this.national_info_level_options = national_info_level.data
            const ownership = await getDicts({ "type": "ownership" })
            this.ownership_options = ownership.data
        },
        administrative_divisions(value) {
            const administrative_divisions = this.administrative_divisions_options.find((item) => {
                return item.value == value
            });
            this.form.administrative_divisions_name = administrative_divisions.label
        },
        admdvs(value) {
            const admdvs = this.admdvs_options.find((item) => {
                return item.value == value
            });
            this.form.admdvs_name = admdvs.label
        },
        credit_level(value) {
            const credit_level = this.credit_level_options.find((item) => {
                return item.value == value
            });
            this.form.credit_level_name = credit_level.label
        },
        national_info_level(value) {
            const national_info_level = this.national_info_level_options.find((item) => {
                return item.value == value
            });
            this.form.national_info_level_name = national_info_level.label
        },
        ownership(value) {
            const ownership = this.ownership_options.find((item) => {
                return item.value == value
            });
            this.form.ownership_name = ownership.label
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
