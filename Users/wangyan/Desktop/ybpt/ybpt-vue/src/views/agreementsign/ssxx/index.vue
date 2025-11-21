<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">查询条件</span>
                        <div class="right">
                            <el-button icon="el-icon-search" type="primary" @click="queryData">
                                查 询
                            </el-button>
                            <!--<el-button icon="el-icon-refresh-left">-->
                                <!--重 置-->
                            <!--</el-button>-->
                        </div>

                    </div>
                    <el-form label-width="160px">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="国家编码">
                                    <el-input v-model="queryForm.mechanism_code" @keydown.enter.native="seachEnterFun"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="机构类别">
                                    <el-select v-model.trim="queryForm.medical_type" style="width: 100%"
                                               @change="getLevelList">
                                        <el-option value="" label="请选择"></el-option>
                                        <el-option value="1" label="医疗机构"></el-option>
                                        <el-option value="2" label="零售药店"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="机构等级">
                                    <el-select v-model="queryForm.net_grade_id" style="width: 100%">
                                        <el-option v-for="item in options" :key="item.cred_lv"
                                                   :label="item.cred_lv_name"
                                                   :value="item.cred_lv">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
<!--                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                                <el-form-item label="医保编码">-->
<!--                                    <el-input v-model="queryForm.medical_code"></el-input>-->
<!--                                </el-form-item>-->
<!--                            </el-col>-->
                        </el-row>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">申诉信息</span>
                        <!-- <div class="right">
                          <el-button type="success" icon="el-icon-plus" @click="handleAdd">
                          审核
                        </el-button>
                        </div> -->

                    </div>
                    <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
                              :element-loading-text="elementLoadingText" highlight-current-row border
                              @current-change="handleCurrentChange" height="calc(100vh - 540px)">
                        <template slot="empty">
                            <el-empty :image-size="200"></el-empty>
                        </template>
<!--                        <el-table-column show-overflow-tooltip type="selection" align="center"></el-table-column>-->
                        <el-table-column show-overflow-tooltip label="机构名称" align="center" prop="fixmedins_name">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="国家编码" align="center" prop="medical_code">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="机构类别" align="center" prop="medical_type">
                            <template #default="{ row }">
                                <span v-if="row.medical_type==1">医疗机构</span>
                                <span v-else-if="row.medical_type==2">零售药店</span>
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip width="120px" label="机构等级" align="center"
                                         prop="net_grade_name">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="legrep_name" label="法定代表人" align="center"
                                         width="120px">
                        </el-table-column>
                        <!--<el-table-column show-overflow-tooltip prop="dept_resper_name" label="药店联系人" align="center"-->
                                         <!--width="120px">-->
                        <!--</el-table-column>-->
                        <el-table-column show-overflow-tooltip prop="dept_resper_tel" label="联系电话" align="center"
                                         width="200px">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="content" label="申诉理由" align="center" width="180px">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="status" label="状态" align="center" width="180px">
                            <template #default="{ row }">
                                <span v-if="row.status==0">待确认</span>
                                <span v-else-if="row.status==1">已确认</span>
                            </template>
                        </el-table-column>

                        <el-table-column show-overflow-tooltip label="操作" width="160" align="center"
                                         fixed="right">
                            <template #default="{ row }">
                              <div v-if="row.status == 0">
                                <el-button plain @click="handleView(row)" type="primary" size="mini">
                                    确认
                                </el-button>
                              </div>
                              <div v-if="row.status == 1">

                              </div>
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
    import Views from './components/view'
    import {getList, getSure} from '@/api_net/netTagAppeal'
    import {selectOrganizationLevel} from '@/api_net/netTagMechanism'

    export default {
        name: 'netTagAppealIndex',
        components: {Views},
        data() {
            return {
                value1: '',
                checked: false,
                isShow: false,
                list: null,
                listLoading: true,
                layout: 'total, sizes, prev, pager, next, jumper',
                total: 0,
                selectRows: '',
                elementLoadingText: '正在加载...',
                queryForm: {
                    pageIndex: 1,
                    pageSize: 10,
                    mechanism_code: '',
                    medical_type: '',
                    net_grade_id: '',
                    medical_code: '',
                },
                options: [],
            }
        },
        created() {
            this.fetchData()
        },
        beforeDestroy() {
        },
        mounted() {
        },
        methods: {
            handleCurrentChange(val) {
                this.selectRows = val
            },
            handleSizeChange(val) {
                this.queryForm.pageSize = val;
                this.fetchData()
            },
            handleCurrentChange2(val) {
                this.queryForm.pageIndex = val;
                this.fetchData()
            },
            handleView(row) {
                getSure(row.id).then((res) => {
                    console.log(res)
                    if (res.code == 0) {
                        this.$baseMessage("确认成功", 'success');
                        this.fetchData()
                    }
                });
            },
            queryData() {
                this.queryForm.pageIndex = 1;
                this.fetchData()
            },
            async fetchData() {
                this.listLoading = true;
                const data = await getList(this.queryForm);
                console.log(data)
                this.list = data.data.records;
                this.total = data.data.total;
                console.log(this.list)
                setTimeout(() => {
                    this.listLoading = false
                }, 300)
            },
            //机构类别联动机构等级
            getLevelList() {
                if (this.queryForm.medical_type) {
                    selectOrganizationLevel(this.queryForm.medical_type).then((res) => {
                        console.log(res)
                        if (res.code == 0) {
                            this.options = res.data
                        }
                    });
                } else {
                    console.log("机构类别联动机构等级空");
                }
            },
            seachEnterFun(){
              this.fetchData();
            }
        },
    }
</script>
