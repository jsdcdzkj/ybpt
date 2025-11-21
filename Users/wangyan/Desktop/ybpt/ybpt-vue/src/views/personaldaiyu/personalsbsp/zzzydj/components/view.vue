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
            <el-form :model="form" :label-width="formLabelWidth">
                <div class="drawer_main">
                    <div class="box_card">
                        <div class="box_header">
                            <span>人员基本信息</span>
                            <vab-icon
                                    :icon="['fas', 'angle-up']"
                                    v-if="isShow"
                                    @click="moreQuery"
                            ></vab-icon>
                            <vab-icon
                                    :icon="['fas', 'angle-down']"
                                    v-else
                                    @click="moreQuery"
                            ></vab-icon>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="证件类型">
                                        <el-select
                                                v-model="form.psn_cert_type_name"
                                                placeholder="证件类型"
                                                style="width: 100%"
                                                disabled
                                        >
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="证件号码">
                                        <el-input
                                                v-model.trim="form.certno"
                                                clearable
                                                class="input-with-select"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="姓名">
                                        <el-input
                                                v-model.trim="form.psn_name"
                                                placeholder="姓名"
                                                disabled
                                        />
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row :gutter="20" v-if="isShow">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="性别">
                                        <el-select
                                                v-model="form.gend_name"
                                                style="width: 100%"
                                                disabled
                                        >
                                            <el-option label="男" value="1"></el-option>
                                            <el-option label="女" value="2"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="民族">
                                        <el-select
                                                v-model="form.naty_name"
                                                style="width: 100%"
                                                disabled
                                        >
                                            <el-option label="汉族" value="1"></el-option>
                                            <el-option label="少数民族" value="2"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="出生日期">
                                        <el-date-picker
                                                v-model.trim="form.brdy"
                                                disabled
                                                type="date"
                                                class="w"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="电话号码">
                                        <el-input v-model.trim="form.mob" disabled/>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                    <div class="box_card">
                        <div class="box_header">
                            <span>医疗机构信息</span>
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
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗机构编号">
                                        <el-input v-model.trim="form.fixmedins_code" disabled>
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗机构名称">
                                        <el-input
                                                v-model.trim="form.fixmedins_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗服务机构类型" class="custemitem">
                                        <el-select v-model="form.medins_type_name" class="w" disabled>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <el-row :gutter="20" v-if="isShow1">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="社会信用代码">
                                        <el-input
                                                v-model.trim="form.uscc"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗机构等级">
                                        <el-input
                                                v-model.trim="form.medinslv_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="限价医院等级">
                                        <el-input
                                                v-model.trim="form.lmtpric_hosp_lv_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="付钱医院等级">
                                        <el-input
                                                v-model.trim="form.dedc_hosp_lv_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="国家异地平台机构编号" class="custemitem">
                                        <el-input
                                                v-model.trim="form.nat_plaf_code"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="省内异地平台机构编号" class="custemitem">
                                        <el-input
                                                v-model.trim="form.prov_plaf_code"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医药机构类型">
                                        <el-input
                                                v-model.trim="form.out_fixmedins_type_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="定点联网开通标志" class="custemitem">
                                        <el-input
                                                v-model.trim="form.fix_onln_open_flag_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="异地联网开通类型" class="custemitem">
                                        <el-input
                                                v-model.trim="form.out_onln_open_type_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="院负责人姓名">
                                        <el-input
                                                v-model.trim="form.hi_resper_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医院负责人证件类型" class="custemitem">
                                        <el-input
                                                v-model.trim="form.hi_resper_cert_type_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医院负责人身份证号码" class="custemitem">
                                        <el-input
                                                v-model.trim="form.hi_resper_certno"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="责任人联系电话" class="custemitem">
                                        <el-input
                                                v-model.trim="form.hi_resper_tel"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                    <div class="box_card">
                        <div class="box_header">
                            <span>本地转院登记信息</span>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item
                                            label="就医地行政区划"
                                            class="custemitem"
                                    >
                                        <el-select v-model="form.mdtrtarea_admdvs_name" class="w" disabled>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="诊断代码">
                                        <el-input v-model.trim="form.username" disabled>
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="诊断名称">
                                        <el-input
                                                v-model.trim="form.username"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="就诊代码">
                                        <el-input v-model.trim="form.mdtrt_id" disabled>
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="开始日期">
                                        <el-date-picker
                                                v-model="form.begndate"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="结束日期">
                                        <el-date-picker
                                                v-model="form.enddate"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医院同意转院标志" class="custemitem">
                                        <el-select v-model="form.hosp_agre_refl_flag_name" class="w" disabled>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="转往医疗机构编号" class="custemitem">
                                        <el-input v-model.trim="form.reflin_medins_no" disabled>
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="转往医院名称">
                                        <el-input
                                                v-model.trim="form.reflin_medins_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="转院日期">
                                        <el-date-picker
                                                v-model="form.refl_date"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="是否转院结算">
                                        <el-select v-model="form.refl_setl_flag_name" class="w" disabled>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="申报来源">
                                        <el-select v-model="form.dcla_souc_name" class="w" disabled>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="转院备案类别">
                                        <el-select v-model="form.refl_fil_type_name" class="w" disabled>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="电话">
                                        <el-input
                                                v-model.trim="form.tel"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="异地备案上报状态" class="custemitem">
                                        <el-select v-model="form.out_fil_upld_stas_name" class="w" disabled>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="医嘱">
                                        <el-input
                                                v-model.trim="form.drord"
                                                type="textarea"
                                                :rows="5"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="疾病病情描述">
                                        <el-input
                                                v-model.trim="form.dise_cond_dscr"
                                                type="textarea"
                                                :rows="5"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="转院原因">
                                        <el-input
                                                v-model.trim="form.refl_rea"
                                                type="textarea"
                                                :rows="5"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                    <div class="box_card">
                        <div class="box_header">
                            <span>代办人信息</span>
                            <vab-icon
                                    :icon="['fas', 'angle-up']"
                                    v-if="isShow2"
                                    @click="moreQuery2"
                            ></vab-icon>
                            <vab-icon
                                    :icon="['fas', 'angle-down']"
                                    v-else
                                    @click="moreQuery2"
                            ></vab-icon>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20" v-if="isShow2">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人姓名">
                                        <el-input
                                                v-model.trim="form.agnter_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人证件类型" class="custemitem">
                                        <el-select v-model="form.agnter_cert_type_name" class="w" disabled>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人证件号码" class="custemitem">
                                        <el-input
                                                v-model.trim="form.agnter_certno"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人联系方式" class="custemitem">
                                        <el-input
                                                v-model.trim="form.agnter_tel"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人关系">
                                        <el-input
                                                v-model.trim="form.agnter_rlts_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="代办人联系地址" class="custemitem">
                                        <el-input
                                                v-model.trim="form.agnter_addr"
                                                type="textarea"
                                                :rows="5"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                    <div class="box_card">
                        <div class="box_header">
                            <span>附件信息</span>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="" class="tsitem">
                                        <el-upload
                                                list-type="picture-card"
                                                :auto-upload="false"
                                                :file-list="fileList"
                                                action="#"
                                        >
                                            <i slot="default" class="el-icon-plus"></i>
                                            <div slot="file" slot-scope="{ file }">
                                                <img
                                                        class="el-upload-list__item-thumbnail"
                                                        :src="file.url"
                                                        alt=""
                                                />
                                                <span class="el-upload-list__item-actions">
                          <span
                                  class="el-upload-list__item-preview"
                                  @click="handlePictureCardPreview(file)"
                          >
                            <i class="el-icon-zoom-in"></i>
                          </span>
                                                    <!--<span-->
                                                    <!--v-if="!disabled"-->
                                                    <!--class="el-upload-list__item-delete"-->
                                                    <!--@click="handleDownload(file)"-->
                                                    <!--&gt;-->
                                                    <!--<i class="el-icon-download"></i>-->
                                                    <!--</span>-->
                        </span>
                                            </div>
                                        </el-upload>
                                        <el-dialog :visible.sync="dialogVisible" append-to-body>
                                            <img width="100%" :src="dialogImageUrl" alt=""/>
                                        </el-dialog>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>

                <div class="drawer_footer">
                    <el-button @click="cancelForm">关 闭</el-button>
                    <el-button @click="print" v-show="form.rchk_flag_name == '未审核'">打 印</el-button>
                </div>
            </el-form>
        </div>
    </el-drawer>
</template>

<script>

import {picList, reflAppyEvtCPrint,reflAppyEvtCDownload} from "@/api/reflAppyEvtC";

export default {
    name: 'edit',
    data() {
        return {
            title: '',
            dialog: false,
            loading: false,
            isShow: true,
            isShow1: true,
            isShow2: true,
            form: {},
            formLabelWidth: '100px',
            timer: null,
            dialogVisible: false,
            disabled: false,
            dialogImageUrl: '',
            fileList: [],
        }
    },
    mounted() {
    },
    methods: {
        async showDia(row) {
            this.title = '查看'
            this.form = Object.assign({}, row)
            this.getImg(row.id);
            this.dialog = true
            console.log(this.form)
        },
        close() {
            this.dialog = false
        },
        save() {
            this.$baseMessage('模拟保存成功', 'success')
            this.$emit('fetch-data')
            this.close()
        },
        openwin() {
            this.$refs['hospital'].showDia()
        },
        openwin1() {
            this.$refs['medical'].showDia()
        },
        openwin2() {
            this.$refs['bingzhong'].showDia()
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
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url
            this.dialogVisible = true
        },
        handleDownload(file) {
            console.log(file)
        },
        handleChange(value) {
        },
        handleClose(done) {
            if (this.loading) {
                return
            }
            this.$confirm('确定要关闭表单吗？')
                .then((_) => {
                    done()
                    // this.loading = true
                    // this.timer = setTimeout(() => {
                    //     done()
                    //     // 动画关闭需要一定的时间
                    //     setTimeout(() => {
                    //         this.loading = false
                    //     }, 400)
                    // }, 2000)
                })
                .catch((_) => {
                })
        },
        cancelForm() {
            this.loading = false
            this.dialog = false
            clearTimeout(this.timer)
        },
        getImg(data) {
            var that = this;
            picList({id: data}).then((res) => {
                that.fileList = res.data;
            })

        },
        print() {
            reflAppyEvtCPrint(this.form).then((res) => {
                console.log(res)
                window.location.href = "http://localhost:8080/common/exceldownload?fileName=" + encodeURI(res.data) + "&delete=" + false + "&type=reflAppyEvtC" + "&name=" + "徐州市基本医疗保险转诊转院登记备案表.xlsx";
                // reflAppyEvtCDownload({'fileName': encodeURI(res.data), 'delete': false, 'name': '徐州市基本医疗保险转诊转院登记备案表.xlsx'})
            })
        }
    },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-upload--picture-card {
    display: none !important;
    opacity: 0 !important;
  }

  .el-dialog__body {
    border-top: 0;
  }
}
</style>