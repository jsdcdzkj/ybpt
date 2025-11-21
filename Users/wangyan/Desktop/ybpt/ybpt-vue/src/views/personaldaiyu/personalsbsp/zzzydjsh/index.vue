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
                    <el-form label-width="160px">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="证件类型">
                                    <el-select v-model="queryForm.PSN_CERT_TYPE" @change="PSN_CERT_TYPE" clearable
                                        placeholder="请选择" class="w">
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
                        <!--                        <el-row :gutter="20">-->
                        <!--                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                        <!--                                <el-form-item label="事件类型">-->
                        <!--                                    <el-select v-model="queryForm.EVT_TYPE" style="width: 100%">-->
                        <!--                                        <el-option-->
                        <!--                                                v-for="item in EVT_TYPE_OPTIOINS"-->
                        <!--                                                :key="item.NAT_DIC_VAL_CODE"-->
                        <!--                                                :label="item.NAT_DIC_VAL_NAME"-->
                        <!--                                                :value="item.NAT_DIC_VAL_CODE">-->
                        <!--                                        </el-option>-->
                        <!--                                        <el-option label="外国人永久居留证" value="7"></el-option>-->
                        <!--                                        <el-option label="外国人护照" value="8"></el-option>-->
                        <!--                                    </el-select>-->
                        <!--                                </el-form-item>-->
                        <!--                            </el-col>-->
                        <!--                        </el-row>-->
                        <vab-query-form>
                            <vab-query-form-right-panel :span="24">
                                <el-form-item class="mr0">
                                    <el-button icon="el-icon-refresh-left">重 置</el-button>
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
                    <!--                    <div slot="header">-->
                    <!--                        <span class="tips">本地转院待审核列表</span>-->
                    <!--                        <el-button-->
                    <!--                                type="primary"-->
                    <!--                                class="right"-->
                    <!--                                icon="el-icon-document-checked"-->
                    <!--                                @click="handleSh"-->
                    <!--                        >-->
                    <!--                            批量审核-->
                    <!--                        </el-button>-->
                    <!--                    </div>-->
                    <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
                        :element-loading-text="elementLoadingText" highlight-current-row border
                        @selection-change="setSelectRows"  height="calc(100% - 50px)">
                        <!--                        <template slot="empty">-->
                        <!--                            <el-empty :image-size="200"></el-empty>-->
                        <!--                        </template>-->
                        <!--                        <el-table-column-->
                        <!--                                show-overflow-tooltip-->
                        <!--                                type="selection"-->
                        <!--                                align="center"-->
                        <!--                                fixed="left"-->
                        <!--                                width="80px"-->
                        <!--                        ></el-table-column>-->
                        <el-table-column show-overflow-tooltip label="序号" align="center" fixed="left" width="180px">
                            <template #default="scope">
                                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="审核状态" width="120px" align="center"
                            prop="rchk_flag_name"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="psn_name" label="人员名称" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="psn_cert_type_name" label="证件类型" align="center"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="certno" align="center" label="证件号码" width="180px">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="数据来源" width="120px" align="center"
                            prop="dcla_souc_name"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="serv_matt_inst_id" width="220px" label="业务流水号"
                            align="center"></el-table-column>
                        <!--                        <el-table-column-->
                        <!--                                show-overflow-tooltip-->
                        <!--                                prop="rchk_flag_name"-->
                        <!--                                label="当前环节"-->
                        <!--                                width="120px"-->
                        <!--                                align="center"-->
                        <!--                        ></el-table-column>-->
                        <el-table-column show-overflow-tooltip prop="mdtrtarea_admdvs_name" label="就医地医保区划"
                            width="200px" align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="reflin_medins_name" label="转往医院名称" align="center"
                            width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="insu_admdvs_name" align="center" label="参保所属医保区划"
                            width="220px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="diag_name" align="center" label="诊断名称"
                            width="220px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="insutype_name" align="center" label="参保险种"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="emp_name" align="center" label="单位名称"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="evt_type_name" align="center" label="事件类型"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="opter_name" align="center" label="经办人姓名"
                            width="120px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="opt_time" align="center" label="经办时间"
                            width="120px"></el-table-column>

                        <el-table-column show-overflow-tooltip label="操作" width="100" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="handlechuli(row)" type="primary" size="mini">
                                    审核
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
        <views ref="views" @fetch-data="fetchData"></views>
    </div>
</template>

<script>
import { menu } from "@/api/injectionScript";
import { getMedinsInfoB, getNatDataDicA, getPsnInfoB, getReflAppyEvtC } from '@/api/reflAppyEvtC';
import Views from './components/view';

export default {
    name: 'Index',
    components: {Views},
    data() {
        return {
            value1: '',
            checked: false,
            isShow: false,
            list: null,
            listLoading: false,
            layout: 'total, sizes, prev, pager, next, jumper',
            total: 0,
            selectRows: '',
            elementLoadingText: '正在加载...',
            queryNatDataDicA: {
                DIC_TYPE_CODE: '',//字典类型代码
                PRNT_DIC_VAL_CODE: '',//字典类型代码父类
            },
            PSN_CERT_TYPE_OPTIOINS: null,
            EVT_TYPE_OPTIOINS: null,
            queryForm: {
                RCHK_FLAG: '0',//复核标志 未审核
                PSN_CERT_TYPE: '01',//证件类型
                PSN_CERT_TYPE_NAME: '',//证件类型
                EVT_TYPE: '01',//事件类型 新增
                // CERTNO: '320324194405173728',//证件号码
                CERTNO: '',//证件号码
                PSN_NAME: '',//人员姓名
                GEND: '',//性别
                GEND_NAME: '',//性别
                NATY: '',//民族
                NATY_NAME: '',//民族
                BRDY: '',//出生日期
                MOB: '',//手机号码
                pageNo: 1,
                pageSize: 10,
            },
        }
    },
    created() {
    },
    beforeDestroy() {
    },
    mounted() {
        menu('转诊转院登记登记')
        this.getNatDataDicA()
    },
    methods: {
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
        async getNatDataDicA() {
            this.queryNatDataDicA.DIC_TYPE_CODE = 'PSN_CERT_TYPE'
            const PSN_CERT_TYPE = await getNatDataDicA(this.queryNatDataDicA)
            this.PSN_CERT_TYPE_OPTIOINS = PSN_CERT_TYPE.data
            this.queryNatDataDicA.DIC_TYPE_CODE = 'EVT_TYPE'
            const EVT_TYPE = await getNatDataDicA(this.queryNatDataDicA)
            this.EVT_TYPE_OPTIOINS = EVT_TYPE.data
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
        setSelectRows(val) {
            this.selectRows = val
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
        handleSh() {
            if (this.selectRows.length > 0) {
                const ids = this.selectRows.map((item) => item.id).join()
                this.$baseConfirm('你确定要审核选中项吗', null, async () => {
                    // const { msg } = await doDelete({ ids })
                    this.$refs['shenhe'].showDia()
                    //this.$baseMessage('模拟审核成功', 'success')
                    //this.fetchData()
                })
            } else {
                this.$baseMessage('未选中任何行', 'error')
                return false
            }
        },
        handleSizeChange(val) {
            this.queryForm.pageSize = val
            this.fetchData()
        },
        handleCurrentChange2(val) {
            this.queryForm.pageNo = val
            this.fetchData()
        },
        queryData() {
            this.queryForm.pageNo = 1
            this.fetchData()
        },
        moreQuery() {
            this.isShow = !this.isShow
        },
        handleSearch() {
            var that = this;
            // axios.post(that.$api, menu('转诊转院登记审核')).then(res => {
            //     if (res.data.code == 0) {
            //         if (that.queryForm.CERTNO){
            //             axios.post(that.$api, enterCertno("N040207_01",that.queryForm.CERTNO)).then(res => {
            //                 if (res.data.code == 0) {
            //                     setTimeout(function () {
            //                         click('N040207_01', '.search-btn .ant-btn-primary')
            //                         that.fetchData()
            //                     }, 500)
            //                 }
            //             })
            //         }else{
            //             setTimeout(function () {
            //                 click('N040207_01', '.search-btn .ant-btn-primary')
            //                 that.fetchData()
            //             }, 500)
            //         }
            //     }
            // })
            that.fetchData()
        },
        async fetchData() {
            var that = this;
            that.listLoading = true
            const {data} = await getReflAppyEvtC(this.queryForm)
            that.list = data.records
            that.total = data.total
            setTimeout(() => {
                that.listLoading = false
            }, 300)
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
       height:calc(100vh - #{$base-nav-bar-height} - #{$base-tabs-bar-height} - #{$base-padding} - #{$base-padding} - 330px)
      }  
      .el-table__body-wrapper{
        height: calc(100% - 40px)!important;
      }    
    }
    }
    }
</style>
