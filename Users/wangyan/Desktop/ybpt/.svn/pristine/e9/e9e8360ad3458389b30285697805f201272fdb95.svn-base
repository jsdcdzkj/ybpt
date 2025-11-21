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
                                                    label="台湾居民来往内地通行证"
                                                    value="6"
                                            ></el-option>
                                            <el-option label="外国人永久居留证" value="7"></el-option>
                                            <el-option label="外国人护照" value="8"></el-option>
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
                                                v-model="queryForm.naty"
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
                                    <el-form-item label="认定定点医药机构编号" class="custemitem">
                                        <el-input v-model.trim="queryForm.medins_code" disabled>
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
                                                v-model.trim="queryForm.medins_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医院鉴定日期">
                                        <el-date-picker
                                                v-model="queryForm.hospIdeDate"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="诊断医师编码">
                                        <el-input v-model.trim="queryForm.phar_code" disabled>
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="诊断医师名称">
                                        <el-input
                                                v-model.trim="queryForm.phar_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="开始日期">
                                        <el-date-picker
                                                v-model="queryForm.begainDate"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="结束日期">
                                        <el-date-picker
                                                v-model="queryForm.enddate"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="申请日期">
                                        <el-date-picker
                                                v-model="queryForm.appyDate"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="申报来源">
                                        <el-select v-model="queryForm.dclaSouc" class="w" disabled>
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
                                                v-model.trim="queryForm.appyRea"
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
                            <span>登记信息</span>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="病种代码">
                                        <el-input v-model.trim="diseData.opsp_dise_code" disabled>
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="病种名称" class="custemitem">
                                        <el-input
                                                v-model.trim="diseData.opsp_dise_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="病种类型代码">
                                        <el-select v-model="diseData.dise_type_code" class="w" disabled>
                                            <el-option label="普通门慢门特病种" value="10"></el-option>
                                            <el-option label="门诊慢性病" value="11"></el-option>
                                            <el-option label="门诊特殊病" value="12"></el-option>
                                            <el-option label="特殊病种" value="3"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医药机构编号">
                                        <el-input v-model.trim="diseData.medins_code" disabled>
                                            <el-button
                                                    slot="append"
                                                    icon="el-icon-search"
                                            ></el-button>
                                        </el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医药机构名称">
                                        <el-input
                                                v-model.trim="diseData.medins_name"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医药机构等级">
                                        <el-select v-model="diseData.medinslv" class="w" disabled>
                                            <el-option label="中心经办系统" value="0"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗机构类型">
                                        <el-select class="w" disabled>
                                            <el-option label="" value="0"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗机构开始日期" class="custemitem">
                                        <el-date-picker
                                                v-model="diseData.beginDate"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="医疗机构结束日期" class="custemitem">
                                        <el-date-picker
                                                v-model="diseData.endDate"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                            </el-row>
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
                                        prop="date"
                                        label="序号"
                                        width="80"
                                        show-overflow-tooltip
                                >
                                    <template slot-scope="scope">
                                        <span v-text="getIndex(scope.$index)"> </span>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="opsp_dise_code" label="病种代码"></el-table-column>
                                <el-table-column
                                        prop="opsp_dise_name"
                                        label="病种名称"
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
                                        <!--<el-button-->
                                        <!--plain-->
                                        <!--@click="handlechuli(row)"-->
                                        <!--type="primary"-->
                                        <!--size="mini"-->
                                        <!--&gt;-->
                                        <!--删除-->
                                        <!--</el-button>-->
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
                                        <el-input
                                                v-model.trim="queryForm.agnterName"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人证件类型" class="custemitem">
                                        <el-select v-model="queryForm.agnterCertType" class="w" disabled>
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
                                                    label="台湾居民来往内地通行证"
                                                    value="6"
                                            ></el-option>
                                            <el-option label="外国人永久居留证" value="7"></el-option>
                                            <el-option label="外国人护照" value="8"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人证件号码" class="custemitem">
                                        <el-input
                                                v-model.trim="queryForm.agnterCertno"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人联系方式" class="custemitem">
                                        <el-input
                                                v-model.trim="queryForm.agnterTel"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="代办人关系">

                                        <el-select class="w" v-model="queryForm.agnterRlts" ref="selectLabel2" disabled="">
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
                                                v-model.trim="queryForm.agnterAddr"
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
                            <span>经办信息</span>
                            <vab-icon
                                    :icon="['fas', 'angle-up']"
                                    v-if="isShow3"
                                    @click="moreQuery3"
                            ></vab-icon>
                            <vab-icon
                                    :icon="['fas', 'angle-down']"
                                    v-else
                                    @click="moreQuery3"
                            ></vab-icon>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20" v-if="isShow3">
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="审核人姓名">
                                        <el-input
                                                v-model.trim="queryForm.username"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="审核时间">
                                        <el-date-picker
                                                v-model="queryForm.username"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="经办人姓名">
                                        <el-input
                                                v-model.trim="queryForm.username"
                                                disabled
                                        ></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                    <el-form-item label="经办时间">
                                        <el-date-picker
                                                v-model="queryForm.username"
                                                type="date"
                                                disabled
                                        ></el-date-picker>
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
            </el-form>
            <div class="drawer_footer">
                <el-button @click="cancelForm">关 闭</el-button>
                <el-button @click="print">打 印</el-button>
                <!--<el-button-->
                <!--type="primary"-->
                <!--@click="$refs.drawer.closeDrawer()"-->
                <!--:loading="loading"-->
                <!--&gt;-->
                <!--{{ loading ? '打印中 ...' : '打 印' }}-->
                <!--</el-button>-->
            </div>
        </div>
        <hospital ref="hospital" @fetch-data="form.name"></hospital>
        <doctor ref="doctor" @fetch-data="form.name"></doctor>
        <medical ref="medical" @fetch-data="form.name"></medical>
        <bingzhong ref="bingzhong" @fetch-data="form.name"></bingzhong>
    </el-drawer>
</template>

<script>
    import Hospital from '@/components/hospital'
    import Doctor from '@/components/doctor'
    import Medical from '@/components/medical'
    import Bingzhong from '@/components/bingzhong'
    import {info, diseasesList, picList, opspDisePrint} from '@/api/opspdise.js'
    import {readImg} from '@/api/common.js'
    import axios from 'axios';
    import { baseURL } from '@/config'

    export default {
        name: 'edit',
        components: {Hospital, Doctor, Medical, Bingzhong},
        data() {
            return {
                tableData: [],
                title: '',
                dialog: false,
                loading: false,
                isShow: false,
                isShow1: true,
                isShow2: true,
                isShow3: true,
                queryForm: {
                    pageNo: 1,
                    pageSize: 10,
                    username: '',
                },
                form: {
                    name: '',
                    region: '',
                    date1: '',
                    date2: '',
                    delivery: false,
                    type: [],
                    resource: '',
                    desc: '',
                },
                diseData: {},
                formLabelWidth: '100px',
                timer: null,
                dialogImageUrl: '',
                dialogVisible: false,
                disabled: false,
                fileList: [],
                id: ""
            }
        },
        mounted() {
        },
        methods: {
            showDia(row) {
                this.id = row;
                this.infoMed(row);
                this.getDiseases(row);
                this.getImg(row);
                this.dialog = true
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
                this.$refs['doctor'].showDia()
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
            moreQuery3() {
                this.isShow3 = !this.isShow3
            },
            handlePictureCardPreview(file) {
                this.dialogImageUrl = file.url
                this.dialogVisible = true
            },
            handleDownload(file) {
                console.log(file)
            },
            handleClose(done) {
                // if (this.loading) {
                //   return
                // }
                // this.$confirm('确定要打印吗？')
                //   .then((_) => {
                //     this.loading = true
                //     this.timer = setTimeout(() => {
                //       done()
                //       // 动画关闭需要一定的时间
                //       setTimeout(() => {
                //         this.loading = false
                //       }, 400)
                //     }, 2000)
                //   })
                //   .catch((_) => {})
            },
            cancelForm() {
                this.loading = false
                this.dialog = false
                clearTimeout(this.timer)
            },
            infoMed(id) {
                var that = this;
                info(id).then((res) => {
                    that.queryForm = res.data;
                })
            },
            getDiseases(id) {
                var that = this;
                diseasesList(id).then((res) => {
                    that.tableData = res.data;
                    console.log(res.data.length);
                    console.log(res.data[0]);
                    if (res.data.length > 0) {
                        that.diseData.opsp_dise_code = res.data[0].opsp_dise_code;
                        that.diseData.opsp_dise_name = res.data[0].opsp_dise_name;
                        that.diseData.medins_code = res.data[0].medins_code;
                        that.diseData.medins_name = res.data[0].medins_name;
                        that.diseData.medinslv = res.data[0].medinslv;
                        that.diseData.beginDate = res.data[0].beginDate;
                        that.diseData.endDate = res.data[0].endDate;
                    }
                })
            },
            getIndex($index) {
                //表格序号
                return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
            },
            formatDate: function (row, column) {
                let data = row[column.property]
                if (data == null) {
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
            getImg(data) {
                var that = this;
                picList(data).then((res) => {
                    that.fileList = res.data;
                })
                // readImg(data).then((res) => {
                //   that.fileList = [{name: 'food.jpeg',url: 'http://localhost:8080/common/readImg?filePath=46fa5c0673a243ccb19d58108d7586ab.jpg'}]
                // })

            },
            print() {
                var that = this;
                opspDisePrint(that.id).then((res) => {
                    that.$baseMessage('操作成功!', 'success');
                    window.location.href = baseURL+"/common/exceldownload?fileName=" + encodeURI(res.data) + "&delete=" + false + "&name=" + "门慢特评估表.xlsx";
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



