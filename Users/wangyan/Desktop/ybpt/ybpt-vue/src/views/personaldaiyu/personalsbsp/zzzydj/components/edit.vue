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
        <div class="drawer_content" v-loading="loading" element-loading-text="数据同步中" element-loading-spinner="el-icon-loading"
             element-loading-background="rgba(0, 0, 0, 0.8)">
            <el-form ref="form" :model="form" :rules="rules" :label-width="formLabelWidth">
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
                                    <el-form-item label="医疗机构编号" prop="fixmedins_code">
                                        <el-input
                                                v-model.trim="form.fixmedins_code"
                                                disabled
                                        >
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗机构名称" prop="fixmedins_name">
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
                                            prop="mdtrtarea_admdvs"
                                    >
                                        <el-cascader
                                                v-model="form.mdtrtarea_admdvs"
                                                :options="ADMDVS_OPTIOINS"
                                                class="w"
                                                placeholder="可搜索"
                                                filterable
                                                clearable
                                                @change="admdvs_optioins"
                                        ></el-cascader>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="诊断代码" prop="diag_code">
                                        <el-input
                                                v-model.trim="form.diag_code"
                                                @click.native="zhenduan"
                                        >
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
                                                v-model.trim="form.diag_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="就诊代码">
                                        <el-input
                                                v-model.trim="form.mdtrt_id"
                                                @click.native="openwin_mdtrt_d"
                                        >
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                                    @click="openwin_mdtrt_d"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="开始日期" prop="begndate">
                                        <el-date-picker
                                                v-model="form.begndate"
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="结束日期" prop="enddate">
                                        <el-date-picker
                                                v-model="form.enddate"
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医院同意转院标志" class="custemitem" prop="hosp_agre_refl_flag">
                                        <el-select v-model="form.hosp_agre_refl_flag" @change="hosp_agre_refl_flag"
                                                   class="w">
                                            <el-option
                                                    v-for="item in HOSP_AGRE_REFL_FLAG_OPTIOINS"
                                                    :key="item.NAT_DIC_VAL_CODE"
                                                    :label="item.NAT_DIC_VAL_NAME"
                                                    :value="item.NAT_DIC_VAL_CODE">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="转往医疗机构编号" class="custemitem">
                                        <el-input
                                                v-model.trim="form.reflin_medins_no"
                                                @click.native="openwin_to_hospital"
                                        >
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                                    @click="openwin_to_hospital"
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
                                    <el-form-item label="转院日期" prop="refl_date">
                                        <el-date-picker
                                                v-model="form.refl_date"
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="是否转院结算" prop="refl_setl_flag">
                                        <el-select v-model="form.refl_setl_flag" @change="refl_setl_flag" class="w">
                                            <el-option
                                                    v-for="item in REFL_SETL_FLAG_OPTIOINS"
                                                    :key="item.NAT_DIC_VAL_CODE"
                                                    :label="item.NAT_DIC_VAL_NAME"
                                                    :value="item.NAT_DIC_VAL_CODE">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="申报来源">
                                        <el-select v-model="form.dcla_souc" @change="dcla_souc" class="w">
                                            <el-option
                                                    v-for="item in DCLA_SOUC_OPTIOINS"
                                                    :key="item.NAT_DIC_VAL_CODE"
                                                    :label="item.NAT_DIC_VAL_NAME"
                                                    :value="item.NAT_DIC_VAL_CODE">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="转院备案类别">
                                        <el-select v-model="form.refl_fil_type" @change="refl_fil_type" class="w">
                                            <el-option
                                                    v-for="item in REFL_FIL_TYPE_OPTIOINS"
                                                    :key="item.NAT_DIC_VAL_CODE"
                                                    :label="item.NAT_DIC_VAL_NAME"
                                                    :value="item.NAT_DIC_VAL_CODE">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="电话">
                                        <el-input v-model.trim="form.tel"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="异地备案上报状态" class="custemitem">
                                        <el-select v-model="form.out_fil_upld_stas" @change="out_fil_upld_stas"
                                                   class="w" disabled="">
                                            <el-option
                                                    v-for="item in OUT_FIL_UPLD_STAS_OPTIOINS"
                                                    :key="item.NAT_DIC_VAL_CODE"
                                                    :label="item.NAT_DIC_VAL_NAME"
                                                    :value="item.NAT_DIC_VAL_CODE">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="医嘱">
                                        <el-input
                                                v-model.trim="form.drord"
                                                type="textarea"
                                                :rows="5"
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="疾病病情描述">
                                        <el-input
                                                v-model.trim="form.dise_cond_dscr"
                                                type="textarea"
                                                :rows="5"
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="转院原因">
                                        <el-input
                                                v-model.trim="form.refl_rea"
                                                type="textarea"
                                                :rows="5"
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
                                        <el-input v-model.trim="form.agnter_name"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人证件类型" class="custemitem">
                                        <el-select v-model="form.agnter_cert_type" @change="agnter_cert_type" class="w">
                                            <el-option
                                                    v-for="item in AGNTER_CERT_TYPE_OPTIOINS"
                                                    :key="item.NAT_DIC_VAL_CODE"
                                                    :label="item.NAT_DIC_VAL_NAME"
                                                    :value="item.NAT_DIC_VAL_CODE">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人证件号码" class="custemitem" prop="agnter_certno">
                                        <el-input v-model.trim="form.agnter_certno"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人联系方式" class="custemitem" prop="agnter_tel">
                                        <el-input v-model.trim="form.agnter_tel"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人关系">
                                        <el-select v-model="form.agnter_rlts" @change="agnter_rlts" class="w">
                                            <el-option
                                                    v-for="item in AGNTER_RLTS_OPTIOINS"
                                                    :key="item.NAT_DIC_VAL_CODE"
                                                    :label="item.NAT_DIC_VAL_NAME"
                                                    :value="item.NAT_DIC_VAL_CODE">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="代办人联系地址" class="custemitem">
                                        <el-input
                                                v-model.trim="form.agnter_addr"
                                                type="textarea"
                                                :rows="5"
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>

                    <div class="box_card" v-show="cameraShow">
                        <div class="box_header">
                            <span>高拍仪</span>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                                    <el-form-item label="主摄像头" class="custemitem">
                                        <el-button type="primary" @click="view1_scan">拍 照</el-button>
                                        <img width="100%" src="http://127.0.0.1:38088/video=stream&camidx=0" alt=""/>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                                    <el-form-item label="主摄像头" class="custemitem">
                                        <el-button type="primary" @click="view2_scan">拍 照</el-button>
                                        <img width="100%" src="http://127.0.0.1:38088/video=stream&camidx=1" alt=""/>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="" class="custemitem" id="view">
                                    </el-form-item>
                                </el-col>
                                <el-dialog :visible.sync="dialogVisible" append-to-body>
                                    <img width="100%" :src="dialogImageUrl" alt=""/>
                                </el-dialog>
                            </el-row>
                        </div>
                    </div>

                    <div class="box_card">
                        <div class="box_header">
                            <span>附件上传</span>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="" class="tsitem">
                                        <el-upload
                                                ref="dataFormFile"
                                                list-type="picture-card"
                                                :auto-upload="false"
                                                :file-list="fileList"
                                                :on-change="fileListChange"
                                                :http-request="uploadFile"
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
                          <span
                                  v-if="!disabled"
                                  class="el-upload-list__item-delete"
                                  @click="handleRemove(file)"
                          >
                            <i class="el-icon-delete"></i>
                          </span>
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
<!--                    <div class="box_card">-->
<!--                        <div class="box_header">-->
<!--                            <span>摄像头</span>-->
<!--                        </div>-->
<!--                        <div class="box_content">-->
<!--                            <el-row :gutter="20">-->
<!--                                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
<!--                                    <el-form-item label="主摄像头" class="custemitem">-->
<!--                                        <el-button type="primary" @click="view1_scan">拍 照</el-button>-->
<!--                                        <img width="100%" src="http://127.0.0.1:38088/video=stream&camidx=0" alt=""/>-->
<!--                                    </el-form-item>-->
<!--                                </el-col>-->
<!--                                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
<!--                                    <el-form-item label="主摄像头" class="custemitem">-->
<!--                                        <el-button type="primary" @click="view2_scan">拍 照</el-button>-->
<!--                                        <img width="100%" src="http://127.0.0.1:38088/video=stream&camidx=1" alt=""/>-->
<!--                                    </el-form-item>-->
<!--                                </el-col>-->
<!--                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
<!--                                    <el-form-item label="" class="custemitem" id="view">-->
<!--                                    </el-form-item>-->
<!--                                </el-col>-->
<!--                                <el-dialog :visible.sync="dialogVisible" append-to-body>-->
<!--                                    <img width="100%" :src="dialogImageUrl" alt=""/>-->
<!--                                </el-dialog>-->
<!--                            </el-row>-->
<!--                        </div>-->
<!--                    </div>-->
                </div>
                <div class="drawer_footer">
                    <el-button @click="cancelForm">关 闭</el-button>
                    <el-button @click="reseat">重 置</el-button>
<!--                    <el-button @click="sub" >提 交</el-button>-->
                    <el-button
                            type="primary"
                            @click="$refs.drawer.closeDrawer()"
                            :loading="loading"
                    >
                        {{ loading ? '提交中 ...' : '提 交' }}
                    </el-button>
                </div>
            </el-form>
        </div>
        <hospital ref="hospital" @fetch-data="hospital_data"></hospital>
        <zhenduan ref="zhenduan" @fetch-data="zhenduan_data"></zhenduan>
        <tohospitalquery ref="tohospitalquery" @fetch-data="to_hospital_data"></tohospitalquery>
        <mdtrt_d ref="mdtrt_d" @fetch-data="mdtrt_d_data"></mdtrt_d>
        <camera ref="camera" @fetch-data="camera_data"></camera>
    </el-drawer>
</template>

<script>
import hospital from '@/components/hospitalquery'
import zhenduan from '@/components/zhenduan'
import tohospitalquery from '@/components/tohospitalquery'
import mdtrt_d from '@/components/mdtrt_d'
import Camera from '@/components/camera'
import {click, setSubmit} from '@/api/injectionScript'
import {getMedinsInfoB, getNatDataDicA, getNatDataDicAByAdmdvs, subReflAppyEvt, upload} from "@/api/reflAppyEvtC";
import axios from 'axios';

export default {
    name: 'edit',
    components: {hospital, zhenduan, tohospitalquery, mdtrt_d,Camera},
    data() {
        return {
            title: '',
            dialog: false,
            loading: false,
            cameraShow: false,
            isShow: false,
            isShow1: false,
            isShow2: true,
            seshow1: false,
            seshow2: false,
            seshow3: false,
            seshow4: false,
            seshow5: false,
            seshow6: false,
            queryForm: {
                MEDINS_CODE: '',//医疗机构代码
            },
            dialogVisible: false,
            disabled: false,
            OUT_FIL_UPLD_STAS_OPTIOINS: null,
            REFL_FIL_TYPE_OPTIOINS: null,
            DCLA_SOUC_OPTIOINS: null,
            REFL_SETL_FLAG_OPTIOINS: null,
            HOSP_AGRE_REFL_FLAG_OPTIOINS: null,
            ADMDVS_OPTIOINS: null,
            ADMDVS: [
                {
                    children: [
                        {
                            children: []
                        }
                    ]
                }
            ],
            AGNTER_CERT_TYPE_OPTIOINS: null,
            AGNTER_RLTS_OPTIOINS: null,
            form: {
                /**隐藏信息*/
                rchk_flag: "0",//复核标志
                rchk_flag_name: "未审核",
                vali_flag: "1",//有效标志
                vali_flag_name: "有效",
                evt_type: "01",//事件类型
                evt_type_name: "新增",
                /**人员基本信息*/
                psn_cert_type: '',//证件类型
                psn_cert_type_name: '',//证件类型
                certno: '',//证件号码
                psn_name: '',//人员姓名
                gend: '',//性别
                gend_name: '',//性别
                naty: '',//民族
                naty_name: '',//民族
                brdy: '',//出生日期
                mob: '',//手机号码
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

                /**本地转院登记信息*/
                mdtrtarea_admdvs: '',//参保所属医保区划
                mdtrtarea_admdvs_name: '',//参保所属医保区划
                refl_setl_flag: '1',//是否转院结算 1
                refl_setl_flag_name: '是',//是否转院结算 是
                dcla_souc: '02',//申报来源
                dcla_souc_name: '中心经办系统',//申报来源
                refl_fil_type: '',//转院备案类别 9
                refl_fil_type_name: '',//转院备案类别 地方扩展转院备案类别
                out_fil_upld_stas: '0',//异地备案上报状态
                out_fil_upld_stas_name: '未上报',//异地备案上报状态

                refl_date: '',//转院日期
                reflin_medins_no: '',//转往定点医药机构编号
                reflin_medins_name: '',//转往医院名称
                hosp_agre_refl_flag: '1',//医院同意转院标志 1
                hosp_agre_refl_flag_name: '是',//医院同意转院标志 是
                enddate: '',//结束日期
                begndate: '',//开始日期
                mdtrt_id: '',//就诊ID 20210601200431914148
                diag_code: '',//诊断代码 A27.800x001
                diag_name: '',//诊断名称 流感伤寒型钩端螺旋体病
                diag_type: '',//诊断类型 流感伤寒型钩端螺旋体病
                tel: '',//电话

                drord: '',//医嘱
                dise_cond_dscr: '',//疾病病情描述
                refl_rea: '',//转诊转院原因

                /**代办人信息*/
                agnter_name: '',//代办人姓名
                agnter_cert_type: '',//代办人证件类型 01
                agnter_cert_type_name: '',//代办人证件类型 居民身份证（户口簿）
                agnter_certno: '',//代办人证件号码 342222199603206025
                agnter_tel: '',//代办人联系方式 13880893840
                agnter_addr: '',//代办人联系地址
                agnter_rlts: '',//代办人关系 4
                agnter_rlts_name: '',//代办人关系 孙子、孙女，或外孙子、外孙女

            },
            rules: {
                fixmedins_code: [{required: true, trigger: 'blur', message: '请输入医疗机构编码'}],
                fixmedins_name: [{required: true, trigger: 'blur', message: '请输入医疗机构名称'}],
                mdtrtarea_admdvs: [{required: true, trigger: 'blur', message: '请输入就医地行政区划'}],
                diag_code: [{required: true, trigger: 'blur', message: '请输入诊断信息'}],
                begndate: [{required: true, trigger: 'blur', message: '请输入开始日期'}],
                enddate: [{required: true, trigger: 'blur', message: '请输入结束日期'}],
                hosp_agre_refl_flag: [{required: true, trigger: 'blur', message: '请输入医院同意转院标志'}],
                refl_date: [{required: true, trigger: 'blur', message: '请输入转院日期'}],
                refl_setl_flag: [{required: true, trigger: 'blur', message: '请输入是否转院结算'}],
                agnter_certno: [{
                    required: false,
                    trigger: 'blur',
                    pattern: /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[0-2])(([0-2][1-9])|10|20|30|31)\d{3}(\d|X|x)$/,
                    message: '身份证号格式不正确'
                }],
                agnter_tel: [{required: false, trigger: 'blur', pattern: /^1[3-9]\d{9}$/, message: '手机号格式错误'}],
            },
            formLabelWidth: '100px',
            timer: null,
            /*文件上传*/
            dialogImageUrl: '',
            fileList: [],
            formDate: ""
        }
    },
    mounted() {
    },
    methods: {
        async showCamera() {
            axios.post("http://127.0.0.1:38088/device=isconnect").then((res)=>{
                console.log("设备连接数：" + res.data.data)
                if(res.data.data > 0){
                    this.cameraShow = true
                }
            })
        },
        async getNatDataDicAByAdmdvs() {
            const ADMDVS = await getNatDataDicAByAdmdvs()
            this.ADMDVS_OPTIOINS = ADMDVS.data
        },
        async getNatDataDicA() {
            const OUT_FIL_UPLD_STAS = await getNatDataDicA({'DIC_TYPE_CODE':'OUT_FIL_UPLD_STAS'})
            this.OUT_FIL_UPLD_STAS_OPTIOINS = OUT_FIL_UPLD_STAS.data
            const REFL_FIL_TYPE = await getNatDataDicA({'DIC_TYPE_CODE':'REFL_FIL_TYPE'})
            this.REFL_FIL_TYPE_OPTIOINS = REFL_FIL_TYPE.data
            const DCLA_SOUC = await getNatDataDicA({'DIC_TYPE_CODE':'DCLA_SOUC'})
            this.DCLA_SOUC_OPTIOINS = DCLA_SOUC.data
            const REFL_SETL_FLAG = await getNatDataDicA({'DIC_TYPE_CODE':'REFL_SETL_FLAG'})
            this.REFL_SETL_FLAG_OPTIOINS = REFL_SETL_FLAG.data
            const HOSP_AGRE_REFL_FLAG = await getNatDataDicA({'DIC_TYPE_CODE':'HOSP_AGRE_REFL_FLAG'})
            this.HOSP_AGRE_REFL_FLAG_OPTIOINS = HOSP_AGRE_REFL_FLAG.data
            const AGNTER_CERT_TYPE = await getNatDataDicA({'DIC_TYPE_CODE':'PSN_CERT_TYPE'})
            this.AGNTER_CERT_TYPE_OPTIOINS = AGNTER_CERT_TYPE.data
            const AGNTER_RLTS = await getNatDataDicA({'DIC_TYPE_CODE':'AGNTER_RLTS'})
            this.AGNTER_RLTS_OPTIOINS = AGNTER_RLTS.data
        },
        admdvs_optioins(value) {
            let cityNames = []
            value.forEach((v) => {
                var ADMDVS = this.ADMDVS_OPTIOINS.find((item) => {
                    if(item.value == v){
                        this.ADMDVS = item
                        this.form.mdtrtarea_admdvs = item.value
                        cityNames.push(item.label)
                        return item
                    }
                })

                if(ADMDVS == undefined || ADMDVS == null || ADMDVS == ''){
                    var ADMDVS = this.ADMDVS.children.find((item) => {
                        if(item.value == v){
                            this.ADMDVS = item
                            this.form.mdtrtarea_admdvs = item.value
                            cityNames.push(item.label)
                            return item
                        }
                    })

                    if(ADMDVS == undefined || ADMDVS == null || ADMDVS == ''){
                        var ADMDVS = this.ADMDVS.children.children.find((item) => {
                            if(item.value == v){
                                this.ADMDVS = item
                                this.form.mdtrtarea_admdvs = item.value
                                cityNames.push(item.label)
                                return item
                            }
                        })
                    }
                }
            })
            this.form.mdtrtarea_admdvs_name = cityNames.join('/')
            console.log(this.form.mdtrtarea_admdvs_name)
        },
        out_fil_upld_stas(val) {
            var dic = this.OUT_FIL_UPLD_STAS_OPTIOINS.find((item) => {
                return item.NAT_DIC_VAL_CODE == val
            })
            this.form.out_fil_upld_stas = dic.NAT_DIC_VAL_CODE
            this.form.out_fil_upld_stas_name = dic.NAT_DIC_VAL_NAME
        },
        refl_fil_type(val) {
            var dic = this.REFL_FIL_TYPE_OPTIOINS.find((item) => {
                return item.NAT_DIC_VAL_CODE == val
            })
            this.form.refl_fil_type = dic.NAT_DIC_VAL_CODE
            this.form.refl_fil_type_name = dic.NAT_DIC_VAL_NAME
        },
        dcla_souc(val) {
            var dic = this.DCLA_SOUC_OPTIOINS.find((item) => {
                return item.NAT_DIC_VAL_CODE == val
            })
            this.form.dcla_souc = dic.NAT_DIC_VAL_CODE
            this.form.dcla_souc_name = dic.NAT_DIC_VAL_NAME
        },
        refl_setl_flag(val) {
            var dic = this.REFL_SETL_FLAG_OPTIOINS.find((item) => {
                return item.NAT_DIC_VAL_CODE == val
            })
            this.form.refl_setl_flag = dic.NAT_DIC_VAL_CODE
            this.form.refl_setl_flag_name = dic.NAT_DIC_VAL_NAME
        },
        hosp_agre_refl_flag(val) {
            var dic = this.HOSP_AGRE_REFL_FLAG_OPTIOINS.find((item) => {
                return item.NAT_DIC_VAL_CODE == val
            })
            this.form.hosp_agre_refl_flag = dic.NAT_DIC_VAL_CODE
            this.form.hosp_agre_refl_flag_name = dic.NAT_DIC_VAL_NAME
        },
        agnter_cert_type(val) {
            var dic = this.AGNTER_CERT_TYPE_OPTIOINS.find((item) => {
                return item.NAT_DIC_VAL_CODE == val
            })
            this.form.agnter_cert_type = dic.NAT_DIC_VAL_CODE
            this.form.agnter_cert_type_name = dic.NAT_DIC_VAL_NAME
        },
        agnter_rlts(val) {
            var dic = this.AGNTER_RLTS_OPTIOINS.find((item) => {
                return item.NAT_DIC_VAL_CODE == val
            })
            this.form.agnter_rlts = dic.NAT_DIC_VAL_CODE
            this.form.agnter_rlts_name = dic.NAT_DIC_VAL_NAME
        },
        showDia(form) {
            this.getNatDataDicA()
            this.getNatDataDicAByAdmdvs()
            this.showCamera()
            this.title = '新增'
            this.form.psn_cert_type = form.PSN_CERT_TYPE;
            this.form.psn_cert_type_name = form.PSN_CERT_TYPE_NAME;
            this.form.certno = form.CERTNO;
            this.form.psn_name = form.PSN_NAME;
            this.form.gend = form.GEND;
            this.form.gend_name = form.GEND_NAME;
            this.form.naty = form.NATY;
            this.form.naty_name = form.NATY_NAME;
            this.form.brdy = form.BRDY;
            this.form.mob = form.MOB;

            //默认日期
            let date = new Date();
            date.setDate(date.getDate());
            this.form.begndate = date
            this.form.enddate = date
            this.form.refl_date = date

            this.form.fixmedins_code = form.fixmedins_code//医疗机构名称
            this.form.fixmedins_name = form.fixmedins_name//医疗机构名称
            this.form.medins_type = "01"//定点医疗服务机构类型
            this.form.medins_type_name = "定点医疗机构"//定点医疗服务机构类型
            this.form.uscc = form.uscc//统一社会信用代码
            this.form.medinslv = form.medinslv//医疗机构等级
            this.form.lmtpric_hosp_lv = form.lmtpric_hosp_lv//限价医院等级
            this.form.lmtpric_hosp_lv_name = form.lmtpric_hosp_lv_name//限价医院等级
            this.form.medinslv_name = form.medinslv_name//限价医院等级
            this.form.dedc_hosp_lv = form.dedc_hosp_lv//起付线医院等级
            this.form.dedc_hosp_lv_name = form.dedc_hosp_lv_name//起付线医院等级
            this.form.nat_plaf_code = form.nat_plaf_code//国家异地平台机构编号
            this.form.out_fixmedins_type = form.out_fixmedins_type//医药机构类型
            this.form.out_fixmedins_type_name = form.out_fixmedins_type_name//医药机构类型
            this.form.prov_plaf_code = form.prov_plaf_code//省内异地平台机构编号
            this.form.fix_onln_open_flag = form.fix_onln_open_flag//定点联网开通标志
            this.form.fix_onln_open_flag_name = form.fix_onln_open_flag_name//定点联网开通标志
            this.form.out_onln_open_type = form.out_onln_open_type//异地联网开通类型
            this.form.out_onln_open_type_name = form.out_onln_open_type_name//异地联网开通类型
            this.form.hi_resper_cert_type = form.hi_resper_cert_type//医院负责人证件类型
            this.form.hi_resper_cert_type_name = form.hi_resper_cert_type_name//医院负责人证件类型
            this.form.hi_resper_name = form.hi_resper_name//医保办负责人姓名
            this.form.hi_resper_tel = form.hi_resper_tel//医保办负责人联系电话

            // this.form = Object.assign({}, row)
            this.dialog = true
        },
        async selectByMedinsCode() {
            if (this.form.fixmedins_code == null || this.form.fixmedins_code == '') {
                this.$baseMessage('请输入医疗机构编码!', 'error')
                return;
            }
            this.queryForm.MEDINS_CODE = this.form.fixmedins_code;
            const MedinsInfoBList = await getMedinsInfoB(this.queryForm)
            if (MedinsInfoBList.data.records == null || MedinsInfoBList.data.records.length == 0) {
                this.$baseMessage('没有查到任何信息!', 'error')
                return;
            }
            const MedinsInfoB = MedinsInfoBList.data.records[0];
            console.log(MedinsInfoB)
            this.form.fixmedins_name = MedinsInfoB.FIXMEDINS_NAME//医疗机构名称
            this.form.medins_type = "01"//定点医疗服务机构类型
            this.form.medins_type_name = "定点医疗机构"//定点医疗服务机构类型
            this.form.uscc = MedinsInfoB.USCC//统一社会信用代码
            this.form.medinslv = MedinsInfoB.MEDINSLV//医疗机构等级
            this.form.lmtpric_hosp_lv = MedinsInfoB.LMTPRIC_HOSP_LV//限价医院等级
            this.form.lmtpric_hosp_lv_name = MedinsInfoB.LMTPRIC_HOSP_LV_NAME//限价医院等级
            this.form.medinslv_name = MedinsInfoB.MEDINSLV_NAME//限价医院等级
            this.form.dedc_hosp_lv = MedinsInfoB.DEDC_HOSP_LV//起付线医院等级
            this.form.dedc_hosp_lv_name = MedinsInfoB.DEDC_HOSP_LV_NAME//起付线医院等级
            this.form.nat_plaf_code = MedinsInfoB.NAT_PLAF_CODE//国家异地平台机构编号
            this.form.out_fixmedins_type = MedinsInfoB.OUT_FIXMEDINS_TYPE//医药机构类型
            this.form.out_fixmedins_type_name = MedinsInfoB.OUT_FIXMEDINS_TYPE_NAME//医药机构类型
            this.form.prov_plaf_code = MedinsInfoB.PROV_PLAF_CODE//省内异地平台机构编号
            this.form.fix_onln_open_flag = MedinsInfoB.FIX_ONLN_OPEN_FLAG//定点联网开通标志
            this.form.fix_onln_open_flag_name = MedinsInfoB.FIX_ONLN_OPEN_FLAG_NAME//定点联网开通标志
            this.form.out_onln_open_type = MedinsInfoB.OUT_ONLN_OPEN_TYPE//异地联网开通类型
            this.form.out_onln_open_type_name = MedinsInfoB.OUT_ONLN_OPEN_TYPE_NAME//异地联网开通类型
            this.form.hi_resper_cert_type = MedinsInfoB.HI_RESPER_CERT_TYPE//医院负责人证件类型
            this.form.hi_resper_cert_type_name = MedinsInfoB.HI_RESPER_CERT_TYPE_NAME//医院负责人证件类型
            this.form.hi_resper_name = MedinsInfoB.HI_RESPER_NAME//医保办负责人姓名
            this.form.hi_resper_tel = MedinsInfoB.HI_RESPER_TEL//医保办负责人联系电话
        },
        hospital() {
            this.$refs['hospital'].showDia()
        },
        hospital_data(MedinsInfoB) {
            console.log(MedinsInfoB)
            this.form.fixmedins_name = MedinsInfoB.FIXMEDINS_NAME//医疗机构名称
            this.form.fixmedins_code = MedinsInfoB.FIXMEDINS_CODE//医疗机构代码
            this.form.medins_type = "01"//定点医疗服务机构类型
            this.form.medins_type_name = "定点医疗机构"//定点医疗服务机构类型
            this.form.uscc = MedinsInfoB.USCC//统一社会信用代码
            this.form.medinslv = MedinsInfoB.MEDINSLV//医疗机构等级
            this.form.lmtpric_hosp_lv = MedinsInfoB.LMTPRIC_HOSP_LV//限价医院等级
            this.form.lmtpric_hosp_lv_name = MedinsInfoB.LMTPRIC_HOSP_LV_NAME//限价医院等级
            this.form.medinslv_name = MedinsInfoB.MEDINSLV_NAME//限价医院等级
            this.form.dedc_hosp_lv = MedinsInfoB.DEDC_HOSP_LV//起付线医院等级
            this.form.dedc_hosp_lv_name = MedinsInfoB.DEDC_HOSP_LV_NAME//起付线医院等级
            this.form.nat_plaf_code = MedinsInfoB.NAT_PLAF_CODE//国家异地平台机构编号
            this.form.out_fixmedins_type = MedinsInfoB.OUT_FIXMEDINS_TYPE//医药机构类型
            this.form.out_fixmedins_type_name = MedinsInfoB.OUT_FIXMEDINS_TYPE_NAME//医药机构类型
            this.form.prov_plaf_code = MedinsInfoB.PROV_PLAF_CODE//省内异地平台机构编号
            this.form.fix_onln_open_flag = MedinsInfoB.FIX_ONLN_OPEN_FLAG//定点联网开通标志
            this.form.fix_onln_open_flag_name = MedinsInfoB.FIX_ONLN_OPEN_FLAG_NAME//定点联网开通标志
            this.form.out_onln_open_type = MedinsInfoB.OUT_ONLN_OPEN_TYPE//异地联网开通类型
            this.form.out_onln_open_type_name = MedinsInfoB.OUT_ONLN_OPEN_TYPE_NAME//异地联网开通类型
            this.form.hi_resper_cert_type = MedinsInfoB.HI_RESPER_CERT_TYPE//医院负责人证件类型
            this.form.hi_resper_cert_type_name = MedinsInfoB.HI_RESPER_CERT_TYPE_NAME//医院负责人证件类型
            this.form.hi_resper_name = MedinsInfoB.HI_RESPER_NAME//医保办负责人姓名
            this.form.hi_resper_tel = MedinsInfoB.HI_RESPER_TEL//医保办负责人联系电话
        },
        openwin_to_hospital() {
            this.$refs['tohospitalquery'].showDia()
        },
        to_hospital_data(MedinsInfoB) {
            this.form.reflin_medins_no = MedinsInfoB.FIXMEDINS_CODE//医疗机构名称
            this.form.reflin_medins_name = MedinsInfoB.FIXMEDINS_NAME//定点医疗服务机构类型
        },
        openwin_mdtrt_d() {
            if (this.form.fixmedins_code == null || this.form.fixmedins_code == '') {
                this.$baseMessage('请先查询医疗机构编号!', 'error')
                return;
            }
            this.$refs['mdtrt_d'].showDia(this.form.certno, this.form.fixmedins_code)
        },
        mdtrt_d_data(row) {
            this.form.mdtrt_id = row.MDTRT_ID
        },
        camera(row) {
            this.$refs['camera'].showDia()
        },
        camera_data(row) {
        },
        zhenduan() {
            this.$refs['zhenduan'].showDia()
        },
        zhenduan_data(row,DIAG_TYPE) {
            this.form.diag_code = row.DIAG_CODE
            this.form.diag_name = row.DIAG_NAME
            this.form.diag_type = DIAG_TYPE
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
                    this.submit()
                })
        },
        submit() {
            this.loading = true
            var that = this;
            that.timer = setTimeout(() => {
                // 动画关闭需要一定的时间
                setTimeout(() => {
                    that.$refs['form'].validate(async (valid) => {
                        if (valid) {
                            that.form.refl_date = that.dateFormatter(that.form.refl_date)
                            that.form.begndate = that.dateFormatter(that.form.begndate)
                            that.form.enddate = that.dateFormatter(that.form.enddate)

                            console.log(setSubmit(that.form))

                            axios.post(that.$api, setSubmit(that.form)).then(async res => {
                                console.log(res.data.data)
                                if (res.data.data == 0) {
                                    setTimeout(async function () {
                                        const reflAppyEvt = await subReflAppyEvt(that.form)
                                        that.formDate = new FormData();
                                        that.$refs.dataFormFile.submit()
                                        that.formDate.append("id", reflAppyEvt.data);
                                        console.log(that.formDate)
                                        upload(that.formDate)

                                        that.$baseMessage('成功', 'success')
                                        that.reseat()
                                        that.cancelForm()

                                        setTimeout(async function () {
                                            click('home', '.el-icon-close')
                                        }, 3000)
                                    },1500)
                                } else {
                                    that.$message({
                                        duration: 0,
                                        showClose: true,
                                        message: res.data.data,
                                        type: 'error'
                                    });
                                    that.loading = false
                                }
                            })

                        } else {
                            that.loading = false
                            console.log('error submit!!');
                        }
                    });
                }, 1500)
            }, 2000)
        },
        dataURLtoFile (dataurl, filename, src) {
            var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while(n--){
                u8arr[n] = bstr.charCodeAt(n);
            }
            let file = new File([u8arr], filename, {type:mime});
            return file
        },
        dateFormatter(str) {
            var hasTime = arguments[1] != false ? true : false;//可传第二个参数false，返回yyyy-MM-dd
            var d = new Date(str);
            var year = d.getFullYear();
            var month = (d.getMonth() + 1) < 10 ? '0' + (d.getMonth() + 1) : (d.getMonth() + 1);
            var day = d.getDate() < 10 ? '0' + d.getDate() : d.getDate();
            if (hasTime) {
                return [year, month, day].join('-');
            }else {
                return [year, month, day].join('-');
            }
        },
        cancelForm() {
            // click('N040207','.ant-row-flex-end .ant-btn-default')
            // this.$emit('fetch-data')
            this.loading = false
            this.dialog = false
            clearTimeout(this.timer)
        },
        reseat() {
            click('N040207', '.footer .ant-btn-default', 1);
            var that = this;
            /**医疗机构信息*/
            that.form.fixmedins_code = "";
            that.form.fixmedins_name = "";
            that.form.medins_type = "";
            that.form.medins_type_name = "";
            that.form.uscc = "";
            that.form.medinslv = "";
            that.form.medinslv_name = "";
            that.form.lmtpric_hosp_lv = "";
            that.form.lmtpric_hosp_lv_name = "";
            that.form.dedc_hosp_lv = "";
            that.form.dedc_hosp_lv_name = "";
            that.form.nat_plaf_code = "";
            that.form.out_fixmedins_type = "";
            that.form.out_fixmedins_type_name = "";
            that.form.prov_plaf_code = "";
            that.form.fix_onln_open_flag = "";
            that.form.fix_onln_open_flag_name = "";
            that.form.out_onln_open_type = "";
            that.form.out_onln_open_type_name = "";
            that.form.hi_resper_cert_type = "";
            that.form.hi_resper_cert_type_name = "";
            that.form.hi_resper_certno = "";
            that.form.hi_resper_name = "";
            that.form.hi_resper_tel = "";
            /**本地转院登记信息*/
            that.form.mdtrtarea_admdvs = "";
            that.form.mdtrtarea_admdvs_name = "";
            /*that.form.refl_setl_flag = "";
            that.form.refl_setl_flag_name = "";*/
            // that.form.dcla_souc = "";
            // that.form.dcla_souc_name = "";
            that.form.refl_fil_type = "";
            that.form.refl_fil_type_name = "";
            // that.form.out_fil_upld_stas = "";
            // that.form.out_fil_upld_stas_name = "";
            that.form.refl_date = "";
            that.form.reflin_medins_name = "";
            that.form.reflin_medins_no = "";
            /*that.form.hosp_agre_refl_flag = "";
            that.form.hosp_agre_refl_flag_name = "";*/
            that.form.enddate = "";
            that.form.begndate = "";
            that.form.diag_name = "";
            that.form.mdtrt_id = "";
            that.form.diag_code = "";
            that.form.tel = "";
            that.form.drord = "";
            that.form.dise_cond_dscr = "";
            that.form.refl_rea = "";
            /**代办人信息*/
            that.form.agnter_name = "";
            that.form.agnter_cert_type = "";
            that.form.agnter_cert_type_name = "";
            that.form.agnter_certno = "";
            that.form.agnter_tel = "";
            that.form.agnter_addr = "";
            that.form.agnter_rlts = "";
            that.form.agnter_rlts_name = "";
            //附件信息
            that.$refs['dataFormFile'].clearFiles();
            that.fileList = []
        },
        /*文件上传*/
        handleRemove(file, fileList) {
            let Arr = this.$refs['dataFormFile'].uploadFiles
            let index = Arr.indexOf(file)
            this.$refs['dataFormFile'].uploadFiles.splice(index, 1);
            this.fileList.splice(index,1);
            // let num =0;
            // this.$refs['dataFormFile'].uploadFiles.map(item => {
            //     if (item.uid = file.uid) {
            //         this.$refs['dataFormFile'].uploadFiles.splice(num, 1);
            //     }
            //     num++;
            // });
        },
        handlePictureCardPreview(file) {
            this.dialogImageUrl = file.url
            this.dialogVisible = true
        },
        uploadFile(file) {
            this.formDate.append("file", file.file);
        },
        fileListChange(file) {
            this.fileList.push({
                url: file.url
            })
        },
        // 主头拍照
        view1_scan(){
            var that = this;
            let data = {
                "filepath": "base64",
                "rotate": "0",
                "cutpage": "0",
                "camidx": "0",
                "ColorMode": "0",
                "quality": "3"
            }
            axios.post("http://127.0.0.1:38088/video=grabimage", JSON.stringify(data)).then((res)=>{
                // that.add_image(res.data.photoBase64)
                this.fileList.push({
                    url: "data:image/jpg;base64," + res.data.photoBase64
                })
            })
        },
        // 副头拍照
        view2_scan(){
            let data = {
                "filepath": "base64",
                "rotate": "0",
                "cutpage": "0",
                "camidx": "1",
                "ColorMode": "0",
                "quality": "3"
            }
            axios.post("http://127.0.0.1:38088/video=grabimage", JSON.stringify(data)).then((res)=>{
                // this.add_image(res.data.photoBase64)
                this.fileList.push({
                    url: "data:image/jpg;base64," + res.data.photoBase64
                })
            })
        },
        // 添加缩略图
        add_image(img_base64){
            let img = document.createElement('img');
            img.src = "data:image/jpg;base64," + img_base64;
            console.log(img.src)
            img.style.width = '80px';
            img.style.height = '80px';
            document.getElementById('view').appendChild(img)
        }
    },
}
</script>

<style lang="scss" scoped>
::v-deep {
  .el-dialog__body {
    border-top: 0;
  }
}
</style>