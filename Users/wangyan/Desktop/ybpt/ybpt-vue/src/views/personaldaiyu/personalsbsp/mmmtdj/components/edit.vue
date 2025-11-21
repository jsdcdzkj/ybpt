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
            <el-form :model="ruleForm" :label-width="formLabelWidth" :rules="rules" ref="ruleForm">
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

                                                v-model="queryForm.psn_cert_type"
                                                placeholder="证件类型"
                                                style="width: 100%"
                                                disabled
                                        >
                                            <el-option
                                                    label="居民身份证（户口薄）"
                                                    value="01"
                                            ></el-option>
                                            <el-option
                                                    label="中国人民解放军军官证"
                                                    value="02"
                                            ></el-option>
                                            <el-option
                                                    label="中国人民武装警察警官证"
                                                    value="03"
                                            ></el-option>
                                            <el-option
                                                    label="香港特区护照/港澳居民来往内地通行证"
                                                    value="04"
                                            ></el-option>
                                            <el-option
                                                    label="奥门特区护照/港澳居民来往内地通行证"
                                                    value="05"
                                            ></el-option>
                                            <el-option
                                                    label="台湾居民来往内地通行证"
                                                    value="06"
                                            ></el-option>
                                            <el-option label="外国人永久居留证" value="07"></el-option>
                                            <el-option label="外国人护照" value="08"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="证件号码">
                                        <el-input
                                                v-model.trim="queryForm.certno"
                                                clearable
                                                class="input-with-select"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="姓名">
                                        <el-input
                                                v-model.trim="queryForm.psn_name"
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
                                                v-model="queryForm.gend"
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
                                                v-model="queryForm.naty_name"
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
                                                v-model.trim="queryForm.brdy"
                                                disabled
                                                type="date"
                                                class="w"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="电话号码">
                                        <el-input v-model.trim="queryForm.mob" disabled/>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                    <div class="box_card">
                        <div class="box_header">
                            <span>门慢门特登记信息</span>
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
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="认定定点医药机构编号" class="custemitem" prop="medins_code">
                                        <el-input
                                                v-model.trim="ruleForm.medins_code"
                                                :disabled="isDise"
                                        >
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="认定定点医药机构名称" class="custemitem">
                                        <el-input
                                                v-model.trim="medins_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医院鉴定日期" prop="hospIdeDate" class="custemitem">
                                        <el-date-picker
                                                format="yyyy-MM-dd"
                                                value-format="yyyy-MM-dd"
                                                v-model="ruleForm.hospIdeDate"
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="诊断医师编码">
                                        <el-input
                                                v-model.trim="phar_code"
                                                @click.native="openwin1"
                                        >
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                                    @click="openwin1"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="诊断医师名称">
                                        <el-input
                                                v-model.trim="phar_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="开始日期" prop="begainDate">
                                        <el-date-picker
                                                format="yyyy-MM-dd"
                                                value-format="yyyy-MM-dd"
                                                v-model="ruleForm.begainDate"
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="结束日期" prop="enddate">
                                        <el-date-picker
                                                format="yyyy-MM-dd"
                                                value-format="yyyy-MM-dd"
                                                v-model="ruleForm.enddate"
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="申请日期" prop="appyDate">
                                        <el-date-picker
                                                format="yyyy-MM-dd"
                                                value-format="yyyy-MM-dd"
                                                v-model="ruleForm.appyDate"
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="申报来源" prop="dclaSouc">
                                        <el-select v-model="ruleForm.dclaSouc" class="w" ref="dcla">
                                            <el-option label="中心经办系统" value="0"></el-option>
                                            <el-option label="定点医药机构" value="1"></el-option>
                                            <el-option label="网上经办系统" value="2"></el-option>
                                            <el-option label="APP" value="3"></el-option>
                                            <el-option label="一体机" value="4"></el-option>
                                            <el-option label="其他" value="5"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="申请理由">
                                        <el-input
                                                v-model.trim="appyRea"
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
                            <span>登记信息</span>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="病种代码" prop="opsp_dise_code">
                                        <el-input
                                                v-model.trim="ruleForm.opsp_dise_code"
                                                @click.native="openwin2"
                                        >
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                                    @click="openwin2"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="病种名称" class="custemitem">
                                        <el-input
                                                v-model.trim="opsp_dise_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="病种类型代码">
                                        <el-select v-model="dise_type_code" class="w" disabled ref="diseType">
                                            <el-option label="普通门慢门特病种" value="10"></el-option>
                                            <el-option label="门诊慢性病" value="11"></el-option>
                                            <el-option label="门诊特殊病" value="12"></el-option>
                                            <el-option label="特殊病种" value="3"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医药机构编号">
                                        <el-input
                                                v-model.trim="medins.medins_code" @input="getmedins()" disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医药机构名称">
                                        <el-input
                                                v-model.trim="medins.medins_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                                <!--<el-form-item label="医药机构等级">-->
                                <!--<el-select v-model="medins.medinslv" class="w" disabled="">-->
                                <!--<el-option label="三级特等" value="01"></el-option>-->
                                <!--<el-option label="三级甲等" value="02"></el-option>-->
                                <!--<el-option label="三级乙等" value="03"></el-option>-->
                                <!--<el-option label="三级丙等" value="04"></el-option>-->
                                <!--<el-option label="二级甲等" value="05"></el-option>-->
                                <!--<el-option label="二级乙等" value="06"></el-option>-->
                                <!--<el-option label="二级丙等" value="07"></el-option>-->
                                <!--<el-option label="一级甲等" value="08"></el-option>-->
                                <!--<el-option label="一级乙等" value="09"></el-option>-->
                                <!--<el-option label="一级丙等" value="10"></el-option>-->
                                <!--<el-option label="无等级" value="11"></el-option>-->
                                <!--</el-select>-->
                                <!--</el-form-item>-->
                                <!--</el-col>-->
                                <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                                <!--<el-form-item label="医疗机构类型">-->
                                <!--<el-select class="w" disabled="" v-model="medins.fixmedins_type">-->
                                <!--<el-option label="定点医疗机构" value="1"></el-option>-->
                                <!--<el-option label="定点零售药店" value="2"></el-option>-->
                                <!--<el-option label="工伤定点康复机构" value="3"></el-option>-->
                                <!--<el-option label="辅助器具配置机构" value="4"></el-option>-->
                                <!--<el-option label="计划生育服务机构" value="5"></el-option>-->
                                <!--</el-select>-->
                                <!--</el-form-item>-->
                                <!--</el-col>-->
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗机构开始日期" class="custemitem" prop="beginDate">
                                        <el-date-picker
                                                v-model="ruleForm.beginDate"
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗机构结束日期" class="custemitem" prop="endDate">
                                        <el-date-picker
                                                v-model="ruleForm.endDate"
                                                type="date"
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                            <vab-query-form>
                                <vab-query-form-right-panel :span="24">
                                    <el-form-item>
                                        <el-button type="primary" @click="addOpsp()">添 加</el-button>
                                    </el-form-item>
                                </vab-query-form-right-panel>
                            </vab-query-form>
                            <el-table
                                    :data="tableData"
                                    border
                                    stripe
                                    class="w"
                                    highlight-current-row
                                    height="300px"
                            >
                                <template slot="empty">
                                    <el-empty :image-size="150"></el-empty>
                                </template>
                                <el-table-column
                                        label="序号"
                                        width="80"
                                        show-overflow-tooltip
                                >
                                    <template slot-scope="scope">
                                        <span v-text="getIndex(scope.$index)"> </span>
                                    </template>
                                </el-table-column>
                                <el-table-column  label="病种代码" prop="opsp_dise_code"></el-table-column>
                                <el-table-column
                                        prop="opsp_dise_name"
                                        label="病种名称"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="dise_type_code"
                                        label="病种类型"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="medins_code"
                                        label="定点医药机构编号"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="medins_name"
                                        label="定点医药机构名称"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop=""
                                        label="定点医药机构类别"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="medinslv"
                                        label="医药机构等级"
                                        show-overflow-tooltip
                                ></el-table-column>
                                <el-table-column
                                        prop="beginDate"
                                        label="医药机构开始日期"
                                        show-overflow-tooltip
                                        :formatter="formatDate"
                                ></el-table-column>
                                <el-table-column
                                        prop="endDate"
                                        label="医药机构结束日期"
                                        show-overflow-tooltip
                                        :formatter="formatDate"
                                ></el-table-column>
                                <el-table-column
                                        show-overflow-tooltip
                                        label="操作"
                                        width="100"
                                        align="center"
                                        fixed="right"
                                >
                                    <template #default="{ row }">
                                        <el-button
                                                plain
                                                @click="handlechuli(row)"
                                                type="primary"
                                                size="mini"
                                        >
                                            删除
                                        </el-button>
                                    </template>
                                </el-table-column>
                            </el-table>
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
                                        <el-input v-model.trim="agnterName"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人证件类型" class="custemitem">
                                        <el-select v-model="agnterCertType" class="w" ref="selectLabel">
                                            <el-option
                                                    label="居民身份证（户口薄）"
                                                    value="01"
                                            ></el-option>
                                            <el-option
                                                    label="中国人民解放军军官证"
                                                    value="2"
                                            ></el-option>
                                            <el-option
                                                    label="中国人民武装警察警官证"
                                                    value="3"
                                            ></el-option>
                                            <el-option
                                                    label="香港特区护照/港澳居民来往内地通行证"
                                                    value="4"
                                            ></el-option>
                                            <el-option
                                                    label="奥门特区护照/港澳居民来往内地通行证"
                                                    value="5"
                                            ></el-option>
                                            <el-option
                                                    label="台湾居民来往大陆通行证"
                                                    value="6"
                                            ></el-option>
                                            <el-option label="外国人永久居留证" value="7"></el-option>
                                            <el-option label="外国人护照" value="8"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人证件号码" class="custemitem">
                                        <el-input v-model.trim="agnterCertno"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人联系方式" class="custemitem">
                                        <el-input v-model.trim="agnterTel"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人关系">
                                        <el-select class="w" v-model="agnterRlts" ref="selectLabel2">
                                            <el-option label="配偶" value="1"></el-option>
                                            <el-option label="子女" value="2"></el-option>
                                            <el-option label="孙子、孙女，或外孙子、外孙女" value="3"></el-option>
                                            <el-option label="父母" value="4"></el-option>
                                            <el-option label="祖父母或外祖父母" value="5"></el-option>
                                            <el-option label="兄、弟、姐、妹" value="6"></el-option>
                                            <el-option label="其他亲属" value="7"></el-option>
                                            <el-option label="非亲属" value="8"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                                    <el-form-item label="代办人联系地址" class="custemitem">
                                        <el-input
                                                v-model.trim="agnterAddr"
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
                </div>
            </el-form>
            <div class="drawer_footer">
                <el-button @click="cancelForm">关 闭</el-button>
                <el-button @click="reseat">重 置</el-button>
                <el-button @click="sub" >提 交</el-button>
                <!--<el-button-->
                <!--type="primary"-->
                <!--@click="$refs.drawer.closeDrawer()"-->
                <!--:loading="loading"-->
                <!--&gt;-->
                <!--{{ loading ? '提交中 ...' : '提 交' }}-->
                <!--</el-button>-->
            </div>
        </div>
        <hospital ref="hospital" @fetch-data="hospital"></hospital>
        <doctor ref="doctor" @fetch-data="doctor"></doctor>
        <!--<medical ref="medical" @fetch-data="form.name"></medical>-->
        <bingzhong ref="bingzhong" @fetch-data="bingzhong"></bingzhong>
    </el-drawer>
</template>

<script>
    import Hospital from '@/components/hospital'
    import Doctor from '@/components/doctor'
    import Medical from '@/components/medical'
    import Bingzhong from '@/components/bingzhong'
    import {medinsInfoBVoPage} from '@/api/medinsInfoB.js'
    import {upload} from '@/api/common.js'
    import {add,checkAdd,mutuallyExclusiveOrNot,uploadPic} from '@/api/opspdise.js'
    import {
        opspDiseRabit,
        selectIdCard,
        agentAdd,
        selectMedinsCode,
        selectPharCode,
        time,
        reason,
        opspDise,
        saveData,
        tip,
        errTip,
        closeErrTip,
        successMed,
        closeAll
    } from '@/api/rabat.js'
    import {selectCompanyByIdCard} from '@/api/opspregd.js'

    import axios from 'axios';

    export default {
        name: 'edit',
        components: {Hospital, Doctor, Medical, Bingzhong},
        data() {
            return {
                tableData: [],
                table: [],
                title: '',
                formDate: "",
                dialog: false,
                loading: false,
                isShow: false,
                isShow1: true,
                isShow2: false,
                insutype:"",
                medins: {
                    medins_code: "",
                    medins_name: "",
                    medinslv: "",
                    beginDate: "",
                    endDate: "",
                    fixmedins_type: "",
                    pageNo: 1,
                    pageSize: 10
                },
                opspData: {
                    opsp_dise_code: "",
                    opsp_dise_name: "",
                    medins_code: "",
                    medins_name: "",
                    beginDate: "",
                    endDate: "",
                    insutype: "",
                },
                medins_name: '',
                phar_name: '',
                phar_code: '',



                appyRea: '',

                opsp_dise_name: '',

                queryForm: {
                    psn_cert_type: '01',
                    certno: '',
                    psn_name: '',
                    gend: '',
                    naty: '',
                    naty_name: '',
                    brdy: '',
                    mob: '1111',
                    emp_name: '',
                    live_addr: '',
                    insu_admdvs: '',
                    insutype: '',
                },
                ruleForm: {
                    medins_code: '',
                    hospIdeDate: new Date(),
                    begainDate: '',
                    enddate: '2999-12-31',
                    appyDate: "",
                    dclaSouc: '0',
                    opsp_dise_code: '',
                    beginDate: '',
                    endDate: '',
                },
                formLabelWidth: '100px',
                timer: null,
                dialogImageUrl: '',
                dialogVisible: false,
                disabled: false,
                fileList: [],
                fileData: [],
                agnterName: "",
                agnterCertType: "",
                agnterCertno: "",
                agnterTel: "",
                agnterRlts: "",
                agnterAddr: "",
                saveData: {},
                rabit: {},
                dise_type_code: {},
                loading: false,
                isDise: true,
                rules: {
                    hospIdeDate: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
                    begainDate: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
                    beginDate: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
                    enddate: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
                    endDate: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
                    appyDate: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
                    dclaSouc: [{required: true, trigger: 'blur', message: '请输入正确的信息'}],
                    opsp_dise_code: [{required: true, trigger: 'submit', message: '请输入正确的信息'}],
                },
            }
        },
        mounted() {

        },
        created(){
            // this.org();
        },
        methods: {
            showDia(data) {
                this.org();
                this.reseat();
                this.queryForm = Object.assign({}, data)
                this.dialog = true
                this.selectCompany()
            },
            close() {
                // this.$refs['form'].resetFields()
                // this.form = this.$options.data().form
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
                this.$refs['doctor'].showDia(this.queryForm.medins_code)
            },
            openwin2() {
                this.$refs['bingzhong'].showDia()
            },
            openwin3() {
                this.$refs['medical'].showDia()
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
            handleRemove(file, fileList) {
                let Arr = this.$refs['dataFormFile'].uploadFiles
                let index = Arr.indexOf(file) ;
                this.fileList.splice(index,1);
                this.fileData.splice(index,1);
                // this.$refs['dataFormFile'].uploadFiles.splice(index, 1);
                // this.fileList
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url
                this.dialogVisible = true
            },
            handleDownload(file) {
                console.log(file)
            },
            uploadFile(file) {
                var that = this;
                alert(1)
                that.formDate.append("file", file.file);
            },
            fileListChange(file) {
                this.fileList.push({
                    url: file.url
                })
                this.fileData.push(file) ;

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
            },
            sub() {
                //机器人请求
                var that = this;
                that.$refs['ruleForm'].validate((valid) => {
                    if (valid) {
                        //校验
                        if(that.tableData.length == 0 ){
                            that.$baseMessage('请添加病种信息!', 'error') ;
                            return ;
                        }


                        mutuallyExclusiveOrNot({opspDiseListBVos:that.tableData}).then((res) => {
                            if(res.data.ishc){
                                that.$baseMessage('病种存在互斥关系,禁止添加！', 'error') ;
                                return ;
                            }

                        })
                        that.saveData.certno = that.queryForm.certno;
                        that.saveData.informationList = that.tableData;
                        checkAdd(that.saveData).then((res) => {
                            if(res.code==500){
                                that.$baseMessage(res.msg, 'error')
                            }else {
                                that.loading = true ;
                                var jsonData = opspDiseRabit();
                                //选中菜单
                                axios.post(that.$api, jsonData)
                                    .then(res => {
                                        if (res.data.code == "0") {
                                            setTimeout(function () {


                                                var selectIdCardData = selectIdCard(that.queryForm.certno);
                                                //查询身份证信息
                                                setTimeout(function () {
                                                    axios.post(that.$api, selectIdCardData)
                                                        .then(res => {
                                                            if (res.data.code == "0") {
                                                                //选择代办人
                                                                if(that.agnterName !=""  || that.agnterCertno !="" ||that.agnterTel !="" ||that.agnterAddr !=""){
                                                                    var agentAddData =  agentAdd(that.agnterName,that.$refs.selectLabel.selectedLabel,that.$refs.selectLabel2.selectedLabel,that.agnterCertno,that.agnterTel,that.agnterAddr) ;
                                                                    setTimeout(function () {
                                                                        axios.post(that.$api,agentAddData)
                                                                            .then(res => {
                                                                                axios.post(that.$api,agentAddData)
                                                                                    .then(res => {

                                                                                    });
                                                                            });
                                                                    }, 500)
                                                                }
                                                                //选择机构
                                                                setTimeout(function () {
                                                                    var medinsCode = selectMedinsCode(that.ruleForm.medins_code);
                                                                    axios.post(that.$api, medinsCode)
                                                                        .then(res => {
                                                                            if (res.data.code == "0") {
                                                                                if (that.phar_code != "" && that.phar_code != null && that.phar_code != undefined) {
                                                                                    setTimeout(function () {
                                                                                        //选择药师
                                                                                        var pharCode = selectPharCode(that.phar_code);
                                                                                        axios.post(that.$api, pharCode)
                                                                                            .then(res => {
                                                                                                if (res.data.code == "0") {

                                                                                                }
                                                                                            });
                                                                                    }, 1000)
                                                                                }
                                                                                setTimeout(function () {
                                                                                    //选择时间
                                                                                    var timeData = time(that.dateFormatter(that.ruleForm.hospIdeDate), that.dateFormatter(that.ruleForm.begainDate), that.dateFormatter(that.ruleForm.enddate), that.dateFormatter(that.ruleForm.appyDate), that.$refs.dcla.selectedLabel);
                                                                                    axios.post(that.$api, timeData)
                                                                                        .then(res => {
                                                                                            setTimeout(function () {
                                                                                                for (var k = 0; k < that.tableData.length; k++) {
                                                                                                    //选择病种
                                                                                                    (function (k) {
                                                                                                        setTimeout(function () {
                                                                                                            var beginDate = '' ;
                                                                                                            var endDate = "" ;
                                                                                                            if(that.tableData[k].beginDate != "" && that.tableData[k].beginDate != null && that.tableData[k].beginDate!= undefined){
                                                                                                                beginDate = that.dateFormatter(that.tableData[k].beginDate) ;
                                                                                                            }
                                                                                                            if(that.tableData[k].endDate != "" && that.tableData[k].endDate != null && that.tableData[k].endDate!= undefined){
                                                                                                                endDate = that.dateFormatter(that.tableData[k].endDate) ;
                                                                                                            }
                                                                                                            var opspDiseData = opspDise(that.tableData[k].opsp_dise_code, that.tableData[k].dise_type_code, that.tableData[k].medins_code, beginDate, endDate);
                                                                                                            axios.post(that.$api, opspDiseData)
                                                                                                                .then(res => {
                                                                                                                    if(k ==that.tableData.length -1 ){
                                                                                                                        setTimeout(function () {
                                                                                                                            var saveDatas = saveData();
                                                                                                                            axios.post(that.$api, saveDatas)
                                                                                                                                .then(res => {

                                                                                                                                    setTimeout(function () {
                                                                                                                                        setTimeout(function () {
                                                                                                                                            //关闭错误提示
                                                                                                                                            var closeErrData = closeErrTip();
                                                                                                                                            axios.post(that.$api, closeErrData)
                                                                                                                                                .then(res => {
                                                                                                                                                    if(res.data.data !=""){
                                                                                                                                                        that.loading = false ;
                                                                                                                                                        that.$baseMessage(res.data.data, 'error');
                                                                                                                                                        //关掉门慢特页面
                                                                                                                                                        setTimeout(function () {
                                                                                                                                                            var closeAllData = closeAll();
                                                                                                                                                            axios.post(that.$api, closeAllData)
                                                                                                                                                                .then(res => {

                                                                                                                                                                });
                                                                                                                                                        }, 900)
                                                                                                                                                    }
                                                                                                                                                    else {
                                                                                                                                                        setTimeout(function () {
                                                                                                                                                            var tipData = tip();
                                                                                                                                                            //成功返回结果
                                                                                                                                                            axios.post(that.$api, tipData)
                                                                                                                                                                .then(res => {
                                                                                                                                                                    if(res.data.data ==""){
                                                                                                                                                                        that.loading = false ;
                                                                                                                                                                        that.$baseMessage("同步数据失败！", 'error') ;
                                                                                                                                                                        setTimeout(function () {
                                                                                                                                                                            var closeAllData = closeAll();
                                                                                                                                                                            axios.post(that.$api, closeAllData)
                                                                                                                                                                                .then(res => {

                                                                                                                                                                                });
                                                                                                                                                                        }, 1200)
                                                                                                                                                                    }else {
                                                                                                                                                                        that.loading = false ;
                                                                                                                                                                        that.$baseMessage(res.data.data, 'success') ;
                                                                                                                                                                        that.localhostAdd() ;
                                                                                                                                                                        //关掉门慢特页面
                                                                                                                                                                        setTimeout(function () {
                                                                                                                                                                            var closeAllData = closeAll();
                                                                                                                                                                            axios.post(that.$api, closeAllData)
                                                                                                                                                                                .then(res => {

                                                                                                                                                                                });
                                                                                                                                                                        }, 1200)
                                                                                                                                                                    }
                                                                                                                                                                });
                                                                                                                                                        }, 1500)
                                                                                                                                                    }

                                                                                                                                                });
                                                                                                                                        }, 2000)

                                                                                                                                    }, 500)

                                                                                                                                    // that.localhostAdd() ;
                                                                                                                                });
                                                                                                                        }, 7000)
                                                                                                                    }

                                                                                                                });
                                                                                                        }, 5000 * k);
                                                                                                    })(k);
                                                                                                }

                                                                                            }, 3000)
                                                                                        });
                                                                                }, 2000)

                                                                                if (that.appyRea != "" && that.appyRea != null && that.appyRea != undefined) {
                                                                                    setTimeout(function () {
                                                                                        //申请理由
                                                                                        var reasonData = reason(that.appyRea);
                                                                                        axios.post(that.$api, reasonData)
                                                                                            .then(res => {

                                                                                            });
                                                                                    }, 2000)
                                                                                }

                                                                            }
                                                                        });
                                                                }, 2000)
                                                            }
                                                        });
                                                }, 900)
                                            }, 900)
                                        }
                                    });
                            }
                        })
                    }
                });
            },
            reseat() {
                var that = this ;
                // that.ruleForm.medins_code = "" ;
                //  that.medins_name = "" ;
                that.phar_code = "" ;
                that.phar_name = "" ;
                that.ruleForm.begainDate = "" ;
                that.ruleForm.hospIdeDate = new Date() ;
                var date  = new Date() ;
                date.setDate(date.getDate()+1) ;
                that.ruleForm.appyDate = date ;
                that.ruleForm.enddate = '2999-12-31' ;
                that.ruleForm.dclaSouc = '0' ;
                that.appyRea = '' ;
                that.ruleForm.opsp_dise_code = '' ;
                that.opsp_dise_name = '' ;
                that.dise_type_code = '' ;
                // that.medins.medins_code = '' ;
                // that.medins.medins_name = '' ;
                that.ruleForm.beginDate = '' ;
                that.ruleForm.endDate = '' ;
                that.tableData = [] ;
                that.fileList = [] ;
            },
            handleClose(done) {
                // var that = this;
                // if (that.loading) {
                //     return
                // }
                // that.$confirm('确定要提交表单吗？')


                //
                // .then((_) => {
                //     this.loading = true
                //     this.timer = setTimeout(() => {
                //         done()
                //         // 动画关闭需要一定的时间
                //         setTimeout(() => {
                //             this.loading = false
                //         }, 400)
                //     }, 2000)
                // })
                // .catch((_) => {
                // })
            },
            cancelForm() {
                this.loading = false
                this.dialog = false
                clearTimeout(this.timer)
            },
            hospital(data) {
                var that = this;
                that.ruleForm.medins_code = data.medins_code;
                that.medins_name = data.medins_name;
            },
            doctor(data) {
                var that = this;
                that.phar_code = data.phar_code;
                that.phar_name = data.phar_name;
            },
            bingzhong(data) {
                var that = this;
                that.ruleForm.opsp_dise_code = data.opsp_dise_code;
                that.opsp_dise_name = data.opsp_dise_name;
                that.dise_type_code = data.dise_type_code;
                that.insutype = data.insutype;
            },
            getmedins() {
                var that = this;
                medinsInfoBVoPage(that.medins).then((res) => {
                    console.log(res.data.records);
                    if (res.data.records.length > 0) {
                        that.medins.medins_name = res.data.records[0].medins_name;
                        that.medins.medinslv = res.data.records[0].medinslv;
                        that.medins.fixmedins_type = res.data.records[0].fixmedins_type;
                    } else {
                        that.medins.medins_name = "";
                        that.medins.medinslv = "";
                        that.medins.fixmedins_type = "";
                    }

                })
            },
            getIndex($index) {
                //表格序号
                return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
            },
            addOpsp() {
                var that = this;
                for (var i = 0; i < that.tableData.length; i++) {
                    if (that.tableData[i].medins_code == that.ruleForm.opsp_dise_code) {
                        that.$baseMessage('当前医疗机构病种已存在!', 'error');
                        return;
                    }
                }
                var dise_type_code = ""
                if (that.dise_type_code == 10) {
                    dise_type_code = "普通门慢门特病种";
                }
                if (that.dise_type_code == 11) {
                    dise_type_code = "门诊慢性病";
                }
                if (that.dise_type_code == 12) {
                    dise_type_code = "门诊特殊病";
                }
                if (that.dise_type_code == 3) {
                    dise_type_code = "特殊病种";
                }
                that.opspData = {
                    medins_code: that.medins.medins_code,
                    medins_name: that.medins.medins_name,
                    beginDate: that.ruleForm.beginDate,
                    endDate: that.ruleForm.endDate,
                    medinslv: that.medins.medinslv,
                    opsp_dise_code: that.ruleForm.opsp_dise_code,
                    opsp_dise_name: that.opsp_dise_name,
                    insutype: that.insutype,
                    dise_type_code: dise_type_code,
                    certNo: that.queryForm.certno
                }
                that.tableData.push(that.opspData);
            },
            formatDate: function (row, column) {
                let data = row[column.property]
                console.log(data);
                if (data == null || data =="") {
                    return null
                }
                let date = new Date(data);
                var o = {
                    "M+": date.getMonth() + 1,                 //月份
                    "d+": date.getDate(),                    //日
                    "h+": date.getHours(),                   //小时
                    "m+": date.getMinutes(),                 //分
                    "s+": date.getSeconds(),                 //秒
                    "q+": Math.floor((date.getMonth() + 3) / 3), //季度
                    "S": date.getMilliseconds()             //毫秒
                };
                var fmt = "yyyy-MM-dd";
                if (/(y+)/.test(fmt)) {
                    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
                }
                for (var k in o) {
                    if (new RegExp("(" + k + ")").test(fmt)) {
                        fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
                    }
                }
                return fmt;
            },
            selectCompany() {
                var that = this;
                selectCompanyByIdCard(that.queryForm).then((res) => {
                    that.queryForm.emp_name = res.data.emp_name;
                    that.queryForm.insu_admdvs = res.data.insu_admdvs;
                    that.queryForm.insutype = res.data.insutype;
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
                }else {
                    return [year, month, day].join('-');
                }
            },
            handlechuli(row) {
                var that = this;
                for (var i = 0; i < that.tableData.length; i++) {
                    if (that.tableData[i].medins_code == row.medins_code) {
                        that.tableData.splice(i, 1);
                    }
                }
            },
            localhostAdd(){
                var that = this ;
                that.saveData.psn_cert_type = that.queryForm.psn_cert_type;
                that.saveData.gend = that.queryForm.gend;
                that.saveData.naty_name = that.queryForm.naty_name;
                that.saveData.naty = that.queryForm.naty;
                that.saveData.brdy = that.queryForm.brdy;
                that.saveData.mob = that.queryForm.mob;
                that.saveData.certno = that.queryForm.certno;
                that.saveData.psn_name = that.queryForm.psn_name;
                that.saveData.emp_name = that.queryForm.emp_name;
                that.saveData.live_addr = that.queryForm.live_addr;
                that.saveData.insutype = that.queryForm.insutype;
                that.saveData.insu_admdvs = that.queryForm.insu_admdvs;
                that.saveData.medins_code = that.ruleForm.medins_code;
                that.saveData.medins_name = that.medins_name;
                that.saveData.hospIdeDate = that.ruleForm.hospIdeDate;
                that.saveData.phar_code = that.phar_code;
                that.saveData.phar_name = that.phar_name;
                that.saveData.begainDate = that.ruleForm.begainDate;
                that.saveData.enddate = that.ruleForm.enddate;
                that.saveData.appyDate = that.ruleForm.appyDate;
                that.saveData.dclaSouc = that.ruleForm.dclaSouc;
                that.saveData.appyRea = that.appyRea;
                that.saveData.informationList = that.tableData;
                that.saveData.agnterName = that.agnterName;
                that.saveData.agnterCertType = that.agnterCertType;
                that.saveData.agnterCertno = that.agnterCertno;
                that.saveData.agnterTel = that.agnterTel;
                that.saveData.agnterRlts = that.agnterRlts;
                that.saveData.agnterAddr = that.agnterAddr;
                add(that.saveData).then((res) => {
                    //刷新父级页面
                    that.$emit('fetch-data');
                    if (res.data != "" && res.data != null) {
                        //图片上传
                        that.formDate = new FormData();
                        that.formDate.append("id", res.data);
                        that.$refs.dataFormFile.submit()
                        that.fileData.forEach((item) => {
                            //文件信息中raw才是真的文件
                            that.formDate.append('file', item.raw)
                        })
                        console.log(that.fileList);
                        console.log(that.fileData);
                        if (null != that.formDate.get('file') && "" != that.formDate.get('file') && undefined != that.formDate.get('file')) {
                            uploadPic(that.formDate).then((res) => {

                            })
                        } else {
                            that.cancelForm();
                        }
                    }
                })
            },
            org(){
                var that = this ;
                var userinfo = JSON.parse(localStorage.getItem("userinfo"));
                console.log(userinfo);
                that.ruleForm.medins_code = userinfo.org_code ;
                that.medins_name = userinfo.org_name ;
                that.medins.medins_code = userinfo.org_code ;
                that.medins.medins_name = userinfo.org_name ;
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