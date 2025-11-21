<template>
    <el-drawer
            :title="title"
            :before-close="handleClose"
            :visible.sync="dialog"
            direction="rtl"
            :with-header="false"
            custom-class="box_drawer"
            size="80%"
            ref="drawer"
    >
        <div class="drawer_content">
            <el-form ref="form" :model="form" :rules="rules" :label-width="formLabelWidth">
                <div class="drawer_main">
                    <div class="box_card">
                        <div class="box_header">
                            <span>科室信息</span>
                            <vab-icon
                                    :icon="['fas', 'angle-up']"
                                    v-if="isShow1"
                                    @click="moreQuery1"
                            ></vab-icon>
                            <vab-icon
                                    :icon="['fas', 'angle-down']"
                                    v-else
                                    @click="moreQuery1"
                            ></vab-icon>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20" v-if="isShow1">
                                <!--                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                                <!--                                    <el-form-item label="科室类型">-->
                                <!--                                        <el-select v-model="form.dept_type" class="w">-->
                                <!--                                            <el-option label="医疗机构" value="医疗机构"></el-option>-->
                                <!--                                            <el-option label="药店机构" value="药店机构"></el-option>-->
                                <!--                                        </el-select>-->
                                <!--                                    </el-form-item>-->
                                <!--                                </el-col>-->
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="机构编号" prop="org_code">
                                        <el-input
                                                disabled
                                                v-model.trim="form.org_code"
                                        >
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="机构名称" prop="org_name">
                                        <el-input
                                                v-model.trim="form.org_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="科室编号" prop="dept_no">
                                        <el-input
                                                v-model.trim="form.dept_no"
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="科室名称" prop="dept_name">
                                        <el-input
                                                v-model="form.dept_name"
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="科室负责人" prop="dept_resper_name">
                                        <el-input
                                                v-model="form.dept_resper_name"
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
<!--                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                                    <el-form-item label="开始日期">-->
<!--                                        <el-date-picker-->
<!--                                                v-model="form.begntime"-->
<!--                                                type="date"-->
<!--                                                value-format="yyyy-MM-dd"-->
<!--                                        ></el-date-picker>-->
<!--                                    </el-form-item>-->
<!--                                </el-col>-->
<!--                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                                    <el-form-item label="结束日期">-->
<!--                                        <el-date-picker-->
<!--                                                v-model="form.endtime"-->
<!--                                                type="date"-->
<!--                                                value-format="yyyy-MM-dd"-->
<!--                                        ></el-date-picker>-->
<!--                                    </el-form-item>-->
<!--                                </el-col>-->
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="科室成立日期">
                                        <el-date-picker
                                                v-model="form.dept_estbdat"
                                                type="date"
                                                value-format="yyyy-MM-dd"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="备注">
                                        <el-input
                                                v-model.trim="form.memo"
                                                type="textarea"
                                                :rows="5"
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>
            </el-form>
            <div class="drawer_footer">
                <el-button @click="cancelForm">关 闭</el-button>
                <el-button @click="reseat">重 置</el-button>
                <el-button
                        type="primary"
                        @click="$refs.drawer.closeDrawer()"
                        :loading="loading"
                >
                    {{ loading ? '提交中 ...' : '提 交' }}
                </el-button>
            </div>
        </div>

    </el-drawer>
</template>

<script>
import {edit, toEdit} from '@/api/medinsDeptB'

export default {
    name: 'edit',
    data() {
        return {
            title: '',
            dialog: false,
            loading: false,
            isShow: false,
            isShow1: true,
            isShow2: false,
            form: {
                dept_type: '',//科室类型
                org_code: '',//机构编码
                org_name: '',//机构名称
                user_type: '',//账号类型
                dept_no: '',//科室编号
                dept_name: '',//科室名称
                begntime: '',//开始时间
                endtime: '',//结束时间
                dept_resper_name: '',//科室负责人姓名
                dept_estbdat: '',//科室成立日期
                memo: '',//备注
                vali_flag: '1',//有效标志
            },
            rules: {
                dept_no: [{required: true, trigger: 'blur', message: '请输入科室编号'}],
                dept_name: [{required: true, trigger: 'blur', message: '请输入科室名称'}],
                dept_resper_name: [{required: true, trigger: 'blur', message: '请输入科室负责人姓名'}],
            },
            formLabelWidth: '100px',
            timer: null,
        }
    },
    mounted() {
        this.getData()
    },
    methods: {
        showDia(row) {
            if (!row) {
                this.title = '新增'
              this.form = {
                dept_type: '',//科室类型
                    org_code: '',//机构编码
                    org_name: '',//机构名称
                    user_type: '',//账号类型
                    dept_no: '',//科室编号
                    dept_name: '',//科室名称
                    begntime: '',//开始时间
                    endtime: '',//结束时间
                    dept_resper_name: '',//科室负责人姓名
                    dept_estbdat: '',//科室成立日期
                    memo: '',//备注
                    vali_flag: '1',//有效标志
              }
            } else {
                this.title = '编辑'
                this.form = Object.assign({}, row)
            }
            this.getData()
            this.dialog = true
        },
        async getData() {
          const res = await toEdit();
          this.form.org_code = res.data.data.org_code
          this.form.org_name = res.data.data.org_name
          this.form.user_type = res.data.data.user_type
        },
        moreQuery() {
            this.isShow = !this.isShow
        },
        moreQuery1() {
            this.isShow1 = !this.isShow1
        },
        moreQuery2() {
            this.isShow2 = !this.isShow2
        },
        handleClose() {
            if (this.loading) {
                return
            }
            this.$confirm('确定要提交表单吗？')
                .then((_) => {
                    this.$refs['form'].validate(async (valid) => {
                        if (valid) {
                            const res = await edit(this.form)
                          if(res.code == 0 ){
                            this.$baseMessage('成功', 'success')
                            this.$emit('fetch-data');
                            this.cancelForm()
                          }else{
                            this.$baseMessage(res.msg, 'error')
                          }

                        }
                    });
                })
                .catch((_) => {
                })
        },
        dateFormatter(str) {
            var hasTime = arguments[1] != false ? true : false;//可传第二个参数false，返回yyyy-MM-dd
            var d = new Date(str);
            var year = d.getFullYear();
            var month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1);
            var day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
            if (hasTime) {
                return [year, month, day].join('-');
            } else {
                return [year, month, day].join('-');
            }
        },
        reseat() {
            this.form.dept_no = ""
            this.form.dept_name = ""
            this.form.begntime = ""
            this.form.endtime = ""
            this.form.dept_resper_name = ""
            this.form.dept_estbdat = ""
            this.form.memo = ""
        },
        cancelForm() {
            this.reseat()
            this.loading = false
            this.dialog = false
            clearTimeout(this.timer)
        },
    },
}
</script>