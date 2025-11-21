<template>
    <el-drawer
            ref="drawer"
            :before-close="handleClose"
            :title="title"
            :visible.sync="dialog"
            :with-header="false"
            custom-class="box_drawer"
            direction="rtl"
            size="80%"
    >
        <div class="drawer_content">
            <el-form :label-width="formLabelWidth" :model="form">
                <div class="drawer_main">
                    <div class="box_card">
                        <div class="box_header">
                            <span>单病种费用查询</span>
                            <vab-icon
                                    v-if="isShow"
                                    :icon="['fas', 'angle-up']"
                                    @click="moreQuery"
                            ></vab-icon>
                            <vab-icon
                                    v-else
                                    :icon="['fas', 'angle-down']"
                                    @click="moreQuery"
                            ></vab-icon>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="定点机构编码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="国家医院编号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="受理医疗类别">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="结算医疗类别">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="定点机构名称">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="国家医院名称">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="结算日期">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="医疗费总额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="账户支付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="现金支付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="统筹支付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="大额支付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="二次补助支付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="医疗救助支付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="自费金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="起付标准自付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="公务员补助支付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="参照公务员基金支付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="医院垫付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="其他基金支付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="总报销金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="产前检查费">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="自理费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="范围内费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="符合基本医疗费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="进入统筹费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="统筹分段自付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="进入大额费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="大额自付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="超封顶线个人自付金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="医院等级">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="参保组织管理码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="单位名称">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="个人管理编码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="社会保障">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="姓名">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="性别">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="年龄">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="就诊流水号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="单据号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="医疗人员类别">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="病区">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="科室">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="床位号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="病案号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="住院号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="电话号码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="常住所在地地址">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="入院日期">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="入院病种识别码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="入院病种名称">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="入院国家病种编码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="入院国家病种名称">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="出院日期">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="出院原因">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="出院病种识别码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="出院病种名称">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="出院国家病种编码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="出院国家病种名称">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本地政策参数">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="主治医师编码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="主治医师姓名">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="基本药物费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="药品费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="诊疗项目费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="材料费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="单病种限额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="统筹报销比例">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="进入公补费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="公补自付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="进入二次补助费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="二次补助自付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="乙类药品">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="乙类诊疗自理">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="乙类材料">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="灵活就业人员报销减少金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="灵活就业人员超统筹封顶后进入费用">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="灵活就业人员超统筹自付">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="超标准自费床位费">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="特殊材料自理">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="账户余额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="审核扣款金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年医疗费用累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年现金支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年账户支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年统筹支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年大额支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年公补支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年参照公补支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年二次补助支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年符合基本医疗费用累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年门诊起付标准累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年住院起付标准累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年进入统筹医疗用累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年进入大额医疗费用累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年进入二次报销费用累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年门诊报销支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年门慢公补支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年门特报销支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年门特公补支付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年超封顶线个人自付累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本年住院次数累计">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="基本药物统筹支付金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="基本药物大额支付金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="基本药物公务员补助支付金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="基本药物参照公务员补助支付金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="基本药物账户支付金额">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="本次住院次数">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="就医地行政区划代码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="出院描述">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="离院方式">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="按责任支付比例">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="结算信息ID">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="就诊信息ID">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="结算年度">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="报销标志">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="业务周期号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="发送方交易流水号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="跨年报销标志">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="支付状态">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="财务支付流水号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="受理号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="有效标志">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="备注">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="说明">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="中途结算标志">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="住院天数">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="跨年结算方式">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="计划生育手术或生育日期">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="就医地行政区划代码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="统筹区编码">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="PSAM编号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="读卡器编号">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="经办人">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="经办时间">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="医疗困难类型">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                                    <el-form-item label="建档立卡人员标志">
                                        <el-input v-model.trim="form.psn_cert_type" class="input-with-select" clearable
                                                  disabled></el-input>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                </div>
            </el-form>
            <div class="drawer_footer">
                <el-button @click="cancelForm">关 闭</el-button>
            </div>
        </div>
    </el-drawer>
</template>

<script>
export default {
    name: 'edit',
    data() {
        return {
            tableData: [],
            title: '',
            dialog: false,
            loading: false,
            isShow: true,
            isShow1: true,
            isShow2: true,
            isShow3: true,
            layout: 'total, sizes, prev, pager, next, jumper',
            form: {},
            formLabelWidth: '100px',
            timer: null,
            dialogImageUrl: '',
            dialogVisible: false,
            disabled: false,
            list: [],
            total: '',
        }
    },
    created() {
    },
    mounted() {
    },
    methods: {
        showDia(row) {
            this.form = row;
            this.fetchData();
            if (!row) {
                this.title = '详情';
            }
            this.dialog = true
        },
        close() {
            this.dialog = false
        },
        save() {
            this.$baseMessage('模拟保存成功', 'success')
            this.$emit('fetch-data')
            this.close()
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
        handleClose(done) {

        },
        cancelForm() {
            this.loading = false
            this.dialog = false
            clearTimeout(this.timer)
        },
        fetchData() {
            var that = this;
            that.loading = false;
        },
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