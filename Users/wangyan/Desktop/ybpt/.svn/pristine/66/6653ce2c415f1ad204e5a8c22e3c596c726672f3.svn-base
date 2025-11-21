<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">查询条件</span>
                        <div class="right">
                            <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
                            <el-button icon="el-icon-refresh-left" @click="reset">重 置</el-button>
                        </div>
                    </div>
                    <el-form label-width="100px">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="通知标题">
                                    <el-input v-model.trim="queryForm.title" @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="创建时间">
                                    <el-date-picker
                                        v-model="queryForm.startEndTime"
                                        type="daterange"
                                        value-format="yyyy-MM-dd"
                                        @change='printValue1'
                                        range-separator="至"
                                        start-placeholder="创建开始日期"
                                        end-placeholder="创建结束日期">
                                    </el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="发布状态">
                                    <el-select v-model="queryForm.is_launch" style="width: 100%" clearable>
                                        <el-option label="已发布" value="1"></el-option>
                                        <el-option label="未发布" value="0"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <!--                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
                            <!--                                <el-form-item label="范围类型">-->
                            <!--                                    <el-radio-group v-model="queryForm.rangtype">-->
                            <!--                                        <el-radio :label="0">体检机构</el-radio>-->
                            <!--                                        <el-radio :label="1">用人单位</el-radio>-->
                            <!--                                    </el-radio-group>-->
                            <!--                                </el-form-item>-->
                            <!--                            </el-col>-->
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="通知范围">
                                    <el-select
                                        v-model="queryForm.rangeList"
                                        style="width: 100%"
                                        clearable
                                        multiple>
                                        <el-option label="用人单位" value="y"></el-option>
                                        <el-option label="体检机构" value="t"></el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                            <!--                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="queryForm.rangtype == 1">-->
                            <!--                                <el-form-item label="机构名称">-->
                            <!--                                    <el-select v-model="queryForm.bankNo" style="width: 100%" clearable>-->
                            <!--                                        <el-option label="机构名称1" value="1"></el-option>-->
                            <!--                                        <el-option label="机构名称2" value="2"></el-option>-->
                            <!--                                    </el-select>-->
                            <!--                                </el-form-item>-->
                            <!--                            </el-col>-->
                            <!--                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="queryForm.rangtype == 1">-->
                            <!--                                <el-form-item label="接收用户">-->
                            <!--                                    <el-select v-model="queryForm.bankNo" style="width: 100%" clearable>-->
                            <!--                                        <el-option label="接收用户1" value="1"></el-option>-->
                            <!--                                        <el-option label="接收用户2" value="2"></el-option>-->
                            <!--                                    </el-select>-->
                            <!--                                </el-form-item>-->
                            <!--                            </el-col>-->
                        </el-row>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card tablecard" shadow="never">
                    <div slot="header">
                        <span class="tips">通知列表</span>
                        <div class="right">
                            <el-button type="success" icon="el-icon-plus" @click="handleEdit">
                                新增
                            </el-button>
                        </div>
                    </div>
                    <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
                              highlight-current-row border @current-change="handleCurrentChange"
                              @selection-change="setSelectRows"
                              height="calc(100% - 50px)">
                        <template slot="empty">
                            <el-empty :image-size="200"></el-empty>
                        </template>
                        <el-table-column
                            label="序号"
                            width="80"
                            align="center"
                            show-overflow-tooltip
                        >
                            <template #default="scope">
                                {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="通知名称" prop="title" align="center"
                                         ></el-table-column>
<!--                        <el-table-column show-overflow-tooltip prop="content" label="通知摘要" align="center">-->
<!--                        </el-table-column>-->
                        <el-table-column show-overflow-tooltip prop="range" label="通知范围" width="300px"
                                         align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="createTime" label="创建时间" width="160px"
                                         align="center"></el-table-column>
                        <!--                        <el-table-column show-overflow-tooltip prop="rangetype" label="范围类型" width="120px"-->
                        <!--                            align="center"></el-table-column>-->

                        <el-table-column show-overflow-tooltip prop="is_launch" label="发布状态" width="120px"
                                         align="center">
                            <template v-slot="{ row }">
                                {{ row.is_launch == 1 ? '已发布' : '未发布' }}
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="launchTime" label="发布时间" width="160px"
                                         align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip label="操作" width="300" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="launch(row)" v-if="row.is_launch == 0"  :loading="loading"
                                           type="success"
                                           size="mini">
                                    {{ loading ? '发布中 ...' : '发布' }}
                                </el-button>
                                <el-button plain @click="handleEdit(row)" v-if="row.is_launch == 0"
                                           type="primary"
                                           size="mini">编辑
                                </el-button>
                                <el-button plain @click="handleDelete(row)" v-if="row.is_launch == 0" :loading="loading"
                                           type="danger"
                                           size="mini">
                                    {{ loading ? '删除中 ...' : '删除' }}
                                </el-button>
                                <el-button plain @click="handleRecall(row)" v-if="row.is_launch == 1" :loading="loading"
                                           type="danger"
                                           size="mini">
                                    {{ loading ? '撤销中 ...' : '撤销' }}
                                </el-button>

                                <el-button plain @click="handleView(row)" type="primary" size="mini">详情</el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                                   :layout="layout" :total="total" @size-change="handleSizeChange"
                                   @current-change="handleCurrentChange2"></el-pagination>
                </el-card>
            </el-col>
        </el-row>

        <edit ref="edit" @fetch-data="fetchData"></edit>
        <dktj ref="dktj"></dktj>


    </div>
</template>

<script>

import Edit from './components/edit'
import Dktj from './components/views'
import {deleteById, selectPageList, launchApi, recallApi} from "@/api_check/notice";

export default {
    name: 'noticeIndex',
    components: {Edit, Dktj},

    data() {
        return {
            loading: false,
            checked: false,
            isShow: false,
            tableData: [],
            list: [],
            listLoading: true,
            layout: 'total, sizes, prev, pager, next, jumper',
            total: 0,
            selectRows: '',
            tableHeight: '100%',
            elementLoadingText: '正在加载...',
            queryForm: {
                pageNo: 1,
                pageSize: 10,
                title: '',
                rangeList: [],
                startEndTime: [],
                is_launch: '',
            },
            userinfo: {},
            isShowEdit: true
        }
    },
    created() {
        // this.toolBarInit()
        this.fetchData();
    },
    beforeDestroy() {
    },
    mounted() {
        // getHeight()
        // //增加监听事件，窗口变化时得到高度。
        // window.addEventListener('resize',this.getHeight,false)
    },
    methods: {
        reset() {
            this.queryForm.pageIndex = 0;
            this.queryForm.startEndTime = [];
            this.queryForm.title = '';
            this.queryForm.rangeList = [];
            this.queryForm.is_launch = '';
            this.fetchData();

        },
        printValue1() {
            if (null != this.queryForm.startEndTime) {
                this.queryForm.startTime = this.queryForm.startEndTime[0] + " 00:00:00";
                this.queryForm.endTime = this.queryForm.startEndTime[1] + " 23:59:59";
            } else {
                this.queryForm.startTime = "";
                this.queryForm.endTime = "";

            }
        },

        toolBarInit() {
            // this.userinfo = JSON.parse(localStorage.getItem('userinfo'));
            // console.log(this.userinfo)
            // if (this.userinfo.name === '管理员') {
            //     this.isShowEdit = true;
            // }
        },

        async fetchData() {
            this.listLoading = true
            const {data} = await selectPageList(this.queryForm)
            this.list = data.records
            this.total = data.total
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },

        // getHeight(){
        // //获取浏览器高度并计算得到表格所用高度。
        //     this.tableHeight=document.documentElement.clientHeight-140
        //   },
        setSelectRows(val) {
            this.selectRows = val
        },
        handleCurrentChange(val) {
            this.selectRows = val
        },
        handleEdit(row) {
            if (row.id) {
                this.$refs['edit'].showDia(row)
            } else {
                this.$refs['edit'].showDia()
            }
        },
        handleView(row) {
            //console.log(row.id)
            if (row.id) {
                this.$refs['dktj'].showDia(row)
            } else {
                this.$refs['dktj'].showDia()
            }
        },
        launch(row) {
            let that = this
            this.$baseConfirm('你确定要发布吗', null, async () => {
                const loading = that.$loading({
                    lock: true,
                    text: '请稍等...',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                let id = row.id;
                let res = await launchApi(id);

                if (res.code ==0 ) {
                    this.$baseMessage('成功发布', 'success')
                    this.fetchData();
                    loading.close();
                }

                if (res.code == -1) {
                    this.$baseMessage(res.msg, 'error')
                    loading.close();
                }

            })
        },
        handleRecall(row) {
            let that = this
            this.$baseConfirm('你确定要撤消发布吗', null, async () => {
                const loading = that.$loading({
                    lock: true,
                    text: '请稍等...',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                let id = row.id;
                let res = await recallApi(id);
                if (res.code ==0 ) {
                    this.$baseMessage('成功撤销', 'success')
                    this.fetchData();
                    loading.close();
                }

                if (res.code == -1) {
                    this.$baseMessage(res.msg, 'error')
                    loading.close();
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
        handlechuli(row) {
            this.$refs['views'].showDia(row.id)
        },
        async handleDelete(row) {
            var that = this;
            if (row.id) {
                that.$baseConfirm('你确定要删除当前项吗', null, async () => {
                    const loading = that.$loading({
                        lock: true,
                        text: '请稍等...',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    let res = await deleteById(row.id);
                    if (res.code == 0) {
                        that.$baseMessage('成功删除', 'success')
                        this.fetchData()
                        loading.close();
                    }
                    if (res.code == -1) {
                        that.$baseMessage(res.msg, 'error')
                        loading.close();
                    }

                })
            } else {
                if (that.selectRows != '' && that.selectRows != null) {
                    const ids = that.selectRows.map((item) => item.id).join()
                    that.$baseConfirm('你确定要删除选中项吗', null, async () => {
                        const {msg} = await doDelete({ids})
                        that.$baseMessage(msg, 'success')
                    })
                } else {
                    that.$baseMessage('未选中任何行', 'error')
                    return false
                }
            }
        },
        queryData() {
            this.queryForm.pageNo = 1
            this.fetchData();
        },

    },
}
</script>
