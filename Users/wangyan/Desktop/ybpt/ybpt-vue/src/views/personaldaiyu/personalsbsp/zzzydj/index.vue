<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">信息查询</span>
                        <vab-icon :icon="['fas', 'angle-up']" v-if="isShow" @click="moreQuery"></vab-icon>
                        <vab-icon :icon="['fas', 'angle-down']" v-else @click="moreQuery"></vab-icon>
                    </div>
                    <el-form label-width="160px" ref="formId">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="证件类型">
                                    <el-select v-model="queryForm.PSN_CERT_TYPE" @change="PSN_CERT_TYPE" clearable
                                        placeholder="请选择">
                                        <el-option v-for="item in PSN_CERT_TYPE_OPTIOINS" :key="item.NAT_DIC_VAL_CODE"
                                            :label="item.NAT_DIC_VAL_NAME" :value="item.NAT_DIC_VAL_CODE">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="身份证号">
                                    <el-input v-model.trim="queryForm.CERTNO" placeholder="身份证号(回车查询)" clearable
                                        class="input-with-icon" @keyup.enter.native="handleEnter">
                                        <!--                                        <vab-icon :icon="['fas', 'search']" slot="suffix" style="margin-right:10px;"-->
                                        <!--                                                  @click="openwin"></vab-icon>-->
                                        <!--                                        <vab-icon :icon="['fas', 'id-card']" slot="suffix"-->
                                        <!--                                                  style="margin-right:10px;"></vab-icon>-->
                                        <!--                                        <vab-icon :icon="['fas', 'barcode']" slot="suffix"-->
                                        <!--                                                  style="margin-right:10px;"></vab-icon>-->
                                    </el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="姓名">
                                    <el-input v-model.trim="queryForm.PSN_NAME" placeholder="姓名" disabled />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row :gutter="20" v-if="isShow">
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="性别">
                                    <el-select v-model="queryForm.GEND_NAME" style="width: 100%" disabled>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="民族">
                                    <el-select v-model="queryForm.NATY_NAME" style="width: 100%" disabled>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="出生日期">
                                    <el-date-picker v-model.trim="queryForm.BRDY" disabled type="date" class="w">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="电话号码">
                                    <el-input v-model.trim="queryForm.MOB" disabled />
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <vab-query-form>
                            <vab-query-form-right-panel :span="24">
                                <el-form-item class="mr0">
                                    <el-button icon="el-icon-refresh-left" @click="reseat()">重 置</el-button>
                                    <el-button icon="el-icon-search" type="primary" @click="handleSearch">
                                        查 询
                                    </el-button>
                                </el-form-item>
                            </vab-query-form-right-panel>
                        </vab-query-form>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card table_card" shadow="never">
                    <div slot="header">
                        <span class="tips">本地转院登记列表</span>
                        <el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">新增</el-button>
                    </div>
                    <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
                        :element-loading-text="elementLoadingText" highlight-current-row border
                        @current-change="handleCurrentChange"  height="calc(100% - 50px)">
                        <template slot="empty">
                            <el-empty :image-size="200"></el-empty>
                        </template>
                        <el-table-column label="序号" width="150px" align="center">
                            <template #default="scope">
                                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="审核状态" align="center" prop="rchk_flag_name"
                            width="120px">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="vali_flag_name" width="120px" label="有效状态"
                            align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="serv_matt_inst_id" label="业务流水号" align="center"
                            width="160px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="reflin_medins_name" label="转往医院名称" align="center"
                            width="160px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="psn_name" label="人员姓名" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="psn_cert_type_name" label="证件类型" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="certno" label="证件号码" align="center" width="120px">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="diag_name" label="诊断名称" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="insutype_name" label="参保险种" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="emp_name" align="center" label="单位名称"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="opter_id" align="center" label="经办人工号"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="opter_name" align="center" label="经办人名称"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="opt_time" align="center" label="经办时间"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="begndate" align="center" label="开始时间"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="enddate" align="center" label="结束时间" width="120px">
                        </el-table-column>

                        <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="handlechuli(row)" type="primary" size="mini">
                                    查看
                                </el-button>
                                <el-button v-if="row.rchk_flag == 0" plain @click="handleCancel(row)" type="primary"
                                    size="mini">
                                    撤消
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                        :layout="layout" :total="total" @size-change="handleSizeChange"
                        @current-change="handleCurrentChange2"></el-pagination>
                </el-card>
            </el-col>
        </el-row>
        <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
        <edit ref="edit" @fetch-data="fetchData"></edit>
        <views ref="views" @fetch-data="fetchData"></views>
    </div>
</template>

<script>
import {anticon, click, enterCertno, menu, openMSg, reload} from '@/api/injectionScript'
import {cancelReflAppyEvtC, getMedinsInfoB, getNatDataDicA, getPsnInfoB, getReflAppyEvtC} from '@/api/reflAppyEvtC'
import Cardnum from '@/components/cardno'
import axios from 'axios'
import Edit from './components/edit'
import Views from './components/view'

export default {
    name: 'Index',
    components: {Cardnum, Edit, Views},
    data() {
        return {
            isShow: false,
            list: null,
            listLoading: false,
            layout: 'total, sizes, prev, pager, next, jumper',
            total: 0,
            selectRows: '',
            elementLoadingText: '正在加载...',
            PSN_INFO_B_INFO: null,
            queryNatDataDicA: {
                DIC_TYPE_CODE: '',//字典类型代码
                PRNT_DIC_VAL_CODE: '',//字典类型代码父类
            },
            PSN_CERT_TYPE_OPTIOINS: null,
            queryForm: {
                PSN_CERT_TYPE: '01',//证件类型
                PSN_CERT_TYPE_NAME: '',//证件类型
                CERTNO: '',//证件号码
                // CERTNO: '320302195502051227',//证件号码
                PSN_NAME: '',//人员姓名
                GEND: '',//性别
                GEND_NAME: '',//性别
                NATY: '',//民族
                NATY_NAME: '',//民族
                BRDY: '',//出生日期
                MOB: '',//手机号码

                /**医疗机构信息*/
                fixmedins_code: '',//医疗机构代码
                fixmedins_name: '',//医疗机构名称
                medins_type: '',//定点医疗服务机构类型
                medins_type_name: '',//定点医疗服务机构类型
                uscc: '',//统一社会信用代码
                medinslv: '',//医疗机构等级
                medinslv_name: '',//医疗机构等级
                lmtpric_hosp_lv: '',//限价医院等级
                lmtpric_hosp_lv_name: '',//限价医院等级
                dedc_hosp_lv: '',//起付线医院等级
                dedc_hosp_lv_name: '',//起付线医院等级
                nat_plaf_code: '',//国家异地平台机构编号
                out_fixmedins_type: '',//医药机构类型
                out_fixmedins_type_name: '',//医药机构类型
                prov_plaf_code: '',//省内异地平台机构编号
                fix_onln_open_flag: '',//定点联网开通标志
                fix_onln_open_flag_name: '',//定点联网开通标志
                out_onln_open_type: '',//异地联网开通类型
                out_onln_open_type_name: '',//异地联网开通类型
                hi_resper_cert_type: '',//医院负责人证件类型
                hi_resper_cert_type_name: '',//医院负责人证件类型
                hi_resper_certno: '',//医保办负责人证件号码
                hi_resper_name: '',//医保办负责人姓名
                hi_resper_tel: '',//医保办负责人联系电话

                pageNo: 1,
                pageSize: 10,
            },
        }
    },
    created() {
        click('home', '.el-icon-close')
    },
    beforeDestroy() {
    },
    mounted() {
        this.getNatDataDicA()
    },
    methods: {
        async getNatDataDicA() {
            this.queryNatDataDicA.DIC_TYPE_CODE = 'PSN_CERT_TYPE'
            const PSN_CERT_TYPE = await getNatDataDicA(this.queryNatDataDicA)
            this.PSN_CERT_TYPE_OPTIOINS = PSN_CERT_TYPE.data
        },
        PSN_CERT_TYPE(val) {
            var PSN_CERT_TYPE = this.PSN_CERT_TYPE_OPTIOINS.find((item) => {
                return item.NAT_DIC_VAL_CODE == val
            })
            this.queryForm.PSN_CERT_TYPE = PSN_CERT_TYPE.NAT_DIC_VAL_CODE
            this.queryForm.PSN_CERT_TYPE_NAME = PSN_CERT_TYPE.NAT_DIC_VAL_NAME
            console.log(this.queryForm)
        },
        handleCurrentChange(val) {
            this.selectRows = val
        },
        openwin() {
            this.$refs['cardnum'].showDia()
        },
        async handleEnter() {
            if (this.queryForm.PSN_CERT_TYPE == null || this.queryForm.PSN_CERT_TYPE == '' ||
                this.queryForm.CERTNO == null || this.queryForm.CERTNO == '') {
                this.$baseMessage('请输入证件号码和证件类型!', 'error')
                return;
            }
            const PSN_INFO_B = await getPsnInfoB(this.queryForm)
            if (PSN_INFO_B.data == null || JSON.stringify(PSN_INFO_B.data) == '{}') {
                this.$baseMessage('人员不存在，请确认身份信息!', 'error')
                return;
            }
            var PSN_CERT_TYPE = this.PSN_CERT_TYPE_OPTIOINS.find((item) => {
                return item.NAT_DIC_VAL_CODE == this.queryForm.PSN_CERT_TYPE;
            })
            this.queryForm.PSN_CERT_TYPE_NAME = PSN_CERT_TYPE.NAT_DIC_VAL_NAME;
            this.queryForm.PSN_NAME = PSN_INFO_B.data.PSN_NAME
            this.queryForm.GEND_NAME = PSN_INFO_B.data.GEND_NAME
            this.queryForm.NATY_NAME = PSN_INFO_B.data.NATY_NAME
            this.queryForm.BRDY = PSN_INFO_B.data.BRDY
            this.queryForm.MOB = PSN_INFO_B.data.MOB
            console.log(this.queryForm)
        },
        handleSearch() {
            var that = this;
            that.listLoading = false
            if (that.queryForm.PSN_NAME == null || that.queryForm.PSN_NAME == '') {
                that.$baseMessage('请先查询人员信息!', 'error')
                return;
            }
            axios.post(that.$api, menu('转诊转院登记')).then(res => {
                setTimeout(function () {
                    axios.post(that.$api, anticon("N040207")).then(res => {
                        if (res == 0) {
                            reload()
                        }
                        setTimeout(function () {
                            axios.post(that.$api, enterCertno('N040207', that.queryForm.CERTNO)).then(res => {
                                if (res.data.code == 0) {
                                    setTimeout(function () {
                                        click('N040207', '.search-btn .ant-btn-primary')
                                        that.fetchData()
                                    }, 1500)
                                }
                            })
                        }, 1000)
                    })
                }, 1000)
            })

        },
        async handleAdd() {
            let that = this;
            console.log(that.queryForm.PSN_NAME)
            if (that.queryForm.PSN_NAME == null || that.queryForm.PSN_NAME == '') {
                that.$baseMessage('请先查询人员信息（输入身份证号回车）', 'error')
                return;
            }
            //判断当前登录用户 医疗机构
            let userinfo = JSON.parse(localStorage.getItem("userinfo"));
            if (userinfo.user_type == null || userinfo.user_type == '' || userinfo.user_type != 2) {
                that.$baseMessage('请先登录【医疗机构】账户操作', 'error')
                return;
            }
            let org_code = userinfo.org_code;
            const MedinsInfoBList = await getMedinsInfoB({'MEDINS_CODE':org_code})
            if (MedinsInfoBList.data.records == null || MedinsInfoBList.data.records.length == 0) {
                this.$baseMessage('请先配置【用户管理-所属机构】', 'error')
                return;
            }
            const MedinsInfoB = MedinsInfoBList.data.records[0];
            console.log(MedinsInfoB)
            this.queryForm.fixmedins_code = org_code//医疗机构编码
            this.queryForm.fixmedins_name = MedinsInfoB.FIXMEDINS_NAME//医疗机构名称
            this.queryForm.medins_type = "01"//定点医疗服务机构类型
            this.queryForm.medins_type_name = "定点医疗机构"//定点医疗服务机构类型
            this.queryForm.uscc = MedinsInfoB.USCC//统一社会信用代码
            this.queryForm.medinslv = MedinsInfoB.MEDINSLV//医疗机构等级
            this.queryForm.lmtpric_hosp_lv = MedinsInfoB.LMTPRIC_HOSP_LV//限价医院等级
            this.queryForm.lmtpric_hosp_lv_name = MedinsInfoB.LMTPRIC_HOSP_LV_NAME//限价医院等级
            this.queryForm.medinslv_name = MedinsInfoB.MEDINSLV_NAME//限价医院等级
            this.queryForm.dedc_hosp_lv = MedinsInfoB.DEDC_HOSP_LV//起付线医院等级
            this.queryForm.dedc_hosp_lv_name = MedinsInfoB.DEDC_HOSP_LV_NAME//起付线医院等级
            this.queryForm.nat_plaf_code = MedinsInfoB.NAT_PLAF_CODE//国家异地平台机构编号
            this.queryForm.out_fixmedins_type = MedinsInfoB.OUT_FIXMEDINS_TYPE//医药机构类型
            this.queryForm.out_fixmedins_type_name = MedinsInfoB.OUT_FIXMEDINS_TYPE_NAME//医药机构类型
            this.queryForm.prov_plaf_code = MedinsInfoB.PROV_PLAF_CODE//省内异地平台机构编号
            this.queryForm.fix_onln_open_flag = MedinsInfoB.FIX_ONLN_OPEN_FLAG//定点联网开通标志
            this.queryForm.fix_onln_open_flag_name = MedinsInfoB.FIX_ONLN_OPEN_FLAG_NAME//定点联网开通标志
            this.queryForm.out_onln_open_type = MedinsInfoB.OUT_ONLN_OPEN_TYPE//异地联网开通类型
            this.queryForm.out_onln_open_type_name = MedinsInfoB.OUT_ONLN_OPEN_TYPE_NAME//异地联网开通类型
            this.queryForm.hi_resper_cert_type = MedinsInfoB.HI_RESPER_CERT_TYPE//医院负责人证件类型
            this.queryForm.hi_resper_cert_type_name = MedinsInfoB.HI_RESPER_CERT_TYPE_NAME//医院负责人证件类型
            this.queryForm.hi_resper_name = MedinsInfoB.HI_RESPER_NAME//医保办负责人姓名
            this.queryForm.hi_resper_tel = MedinsInfoB.HI_RESPER_TEL//医保办负责人联系电话

            axios.post(that.$api, menu('转诊转院登记')).then(res => {
                if (res.data.code == 0) {
                    setTimeout(function () {
                        axios.post(that.$api, anticon("N040207")).then(res => {
                            if(res == 0){
                                reload()
                            }
                            setTimeout(function () {
                                axios.post(that.$api, enterCertno("N040207", that.queryForm.CERTNO)).then(res => {
                                    setTimeout(function () {
                                        axios.post(that.$api, openMSg()).then(res => {
                                            console.log(res)
                                            if (res.data.data == 0) {
                                                that.$refs['edit'].showDia(that.queryForm)
                                            } else {
                                                that.$confirm(res.data.data)
                                                    .then((_) => {
                                                        click('N040207', '.ant-modal-footer .ant-btn-primary')
                                                        that.$refs['edit'].showDia(that.queryForm)
                                                    })
                                                    .catch((_) => {
                                                        click('N040207', '.ant-modal-footer .ant-btn-default')
                                                    })
                                            }
                                        })
                                    }, 1500)
                                })
                            }, 1000)
                        })

                    }, 500)
                }
            })

        },
        handleSizeChange(val) {
            this.queryForm.pageSize = val
            this.fetchData()
        },
        handleCurrentChange2(val) {
            this.queryForm.pageNo = val
            this.fetchData()
        },
        async handlechuli(row) {
            /**人员基本信息*/
            row.psn_cert_type_name = this.queryForm.PSN_CERT_TYPE_NAME;
            row.psn_name = this.queryForm.PSN_NAME
            row.gend_name = this.queryForm.GEND_NAME
            row.naty_name = this.queryForm.NATY_NAME
            row.brdy = this.queryForm.BRDY
            row.mob = this.queryForm.MOB
            /**医疗机构信息*/
            const MedinsInfoBList = await getMedinsInfoB({"MEDINS_CODE": row.fixmedins_code})
            const MedinsInfoB = MedinsInfoBList.data.records[0];
            row.medins_name = MedinsInfoB.MEDINS_NAME//医疗机构名称
            row.medins_type = MedinsInfoB.MEDINS_TYPE//定点医疗服务机构类型
            row.medins_type_name = MedinsInfoB.MEDINS_TYPE_NAME//定点医疗服务机构类型
            row.uscc = MedinsInfoB.USCC//统一社会信用代码
            row.medinslv = MedinsInfoB.MEDINSLV//医疗机构等级
            row.lmtpric_hosp_lv = MedinsInfoB.LMTPRIC_HOSP_LV//限价医院等级
            row.lmtpric_hosp_lv_name = MedinsInfoB.LMTPRIC_HOSP_LV_NAME//限价医院等级
            row.medinslv_name = MedinsInfoB.MEDINSLV_NAME//限价医院等级
            row.dedc_hosp_lv = MedinsInfoB.DEDC_HOSP_LV//起付线医院等级
            row.dedc_hosp_lv_name = MedinsInfoB.DEDC_HOSP_LV_NAME//起付线医院等级
            row.nat_plaf_code = MedinsInfoB.NAT_PLAF_CODE//国家异地平台机构编号
            row.out_fixmedins_type = MedinsInfoB.OUT_FIXMEDINS_TYPE//医药机构类型
            row.out_fixmedins_type_name = MedinsInfoB.OUT_FIXMEDINS_TYPE_NAME//医药机构类型
            row.prov_plaf_code = MedinsInfoB.PROV_PLAF_CODE//省内异地平台机构编号
            row.fix_onln_open_flag = MedinsInfoB.FIX_ONLN_OPEN_FLAG//定点联网开通标志
            row.fix_onln_open_flag_name = MedinsInfoB.FIX_ONLN_OPEN_FLAG_NAME//定点联网开通标志
            row.out_onln_open_type = MedinsInfoB.OUT_ONLN_OPEN_TYPE//异地联网开通类型
            row.out_onln_open_type_name = MedinsInfoB.OUT_ONLN_OPEN_TYPE_NAME//异地联网开通类型
            row.hi_resper_cert_type = MedinsInfoB.HI_RESPER_CERT_TYPE//医院负责人证件类型
            row.hi_resper_cert_type_name = MedinsInfoB.HI_RESPER_CERT_TYPE_NAME//医院负责人证件类型
            row.hi_resper_name = MedinsInfoB.HI_RESPER_NAME//医保办负责人姓名
            row.hi_resper_tel = MedinsInfoB.HI_RESPER_TEL//医保办负责人联系电话
            this.$refs['views'].showDia(row)
        },
        handleCancel(row) {
            if (row.id) {
                this.$baseConfirm('确认进行撤消？', null, async () => {
                    click('N040207', '.table-operate a', 1)
                    setTimeout(function () {
                        click('N040207', '.ant-modal-content .ant-btn-primary')
                        setTimeout(function () {
                            click('N040207', '.ant-modal-content .ant-btn-primary')
                        }, 500)
                    }, 500)

                    await cancelReflAppyEvtC({id: row.id})
                    this.$baseMessage('撤消成功', 'success')
                    this.fetchData()
                })
            }
        },
        queryData() {
            this.queryForm.pageNo = 1
            this.fetchData()
        },
        moreQuery() {
            this.isShow = !this.isShow
        },
        async fetchData() {
            this.listLoading = true
            const {data} = await getReflAppyEvtC(this.queryForm)
            this.list = data.records
            this.total = data.total
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        reseat() {
            var that = this;
            that.queryForm.PSN_NAME = "";
            that.queryForm.GEND = "";
            that.queryForm.NATY = "";
            that.queryForm.BRDY = "";
            that.queryForm.MOB = "";
            that.queryForm.NATY_NAME = "";
            that.queryForm.GEND_NAME = "";
            that.queryForm.CERTNO = "";
        },
    },
}
</script>
<style lang="scss" scoped>
.index-container {
    padding: 0 !important;
    margin: 0 !important;
    background: #f5f7f8 !important;

    .bottom {
        padding-top: 20px;
        margin-top: 5px;
        color: #595959;
        text-align: left;
        border-top: 1px solid $base-border-color;
    }

    .bottom-btn {
        button {
            margin: 5px 10px 15px 0;
        }
    }
}
</style>
<style lang="scss" scoped>
.main-container{
::v-deep{  
  .table_card{  
      .el-card__body{
       height:calc(100vh - #{$base-nav-bar-height} - #{$base-tabs-bar-height} - #{$base-padding} - #{$base-padding} - 390px)
      }    
    }
    }
    }
</style>
