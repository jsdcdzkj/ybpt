<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">信息查询</span>
                        <div class="right">
                            <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
                            <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
                        </div>
                    </div>
                    <el-form label-width="100px">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="套餐名称">
                                    <el-input v-model.trim="queryForm.pack_name" @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                                <el-form-item label="套餐年份">
                                    <el-date-picker v-model.trim="queryForm.pack_year" type="year"
                                                    value-format="yyyy"></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-show="sysUser.user_type == 1">
                                <el-form-item label="所属机构">
                                    <el-select v-model.trim="queryForm.org_id" clearable class="w">
                                        <el-option
                                                v-for="item in org_options"
                                                :key="item.medical_insurance_num"
                                                :label="item.org_name"
                                                :value="item.medical_insurance_num">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-col>
                        </el-row>
                    </el-form>
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card tablecard" shadow="never">
                    <div slot="header">
                        <span class="tips">通用套餐+赠送项目列表</span>
                        <div class="right">
                            <el-button type="success" icon="el-icon-plus" @click="handleAdd"
                                       v-show="sysUser.user_type == 5">
                                新增
                            </el-button>
                        </div>
                    </div>
                    <el-table
                            v-loading="listLoading"
                            ref="listTable"
                            stripe
                            :data="list"
                            :element-loading-text="elementLoadingText"
                            highlight-current-row
                            border
                            @current-change="handleCurrentChange"
                            height="calc(100% - 50px)"
                            @selection-change="setSelectRows"
                    >
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
                        <el-table-column show-overflow-tooltip prop="pack_name" label="套餐名称" align="center" width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="pack_year" label="套餐年份" align="center" width="100px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="pack_money" label="套餐金额" align="center" width="100px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="applicable_scope_name" label="适用范围" align="center" width="100px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="memo" label="套餐描述" align="center" width="200px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="org_name" label="机构名称" align="center" width="200px" ></el-table-column>
                        <el-table-column show-overflow-tooltip prop="pack_name" label="审核状态" align="center" width="100px">
                            <template #default="scope">
                                <el-tag type="info" v-if="scope.row.status == 0">待审</el-tag>
                                <el-tag type="success" v-else-if="scope.row.status == 1">通过</el-tag>
                                <el-tag type="danger" v-else>驳回</el-tag>
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="审核意见" align="center" min-width="200" prop="verify_reason"></el-table-column>
                        <el-table-column show-overflow-tooltip label="审核时间" align="center" prop="verify_time" width="180"></el-table-column>
                        <el-table-column show-overflow-tooltip label="审核人" align="center" prop="verify_user" width="120"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="createTime" label="创建时间" align="center" width="180px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="createUser" label="创建人" align="center" min-width="240px" ></el-table-column>

                        <el-table-column
                                show-overflow-tooltip
                                label="操作"
                                width="240px"
                                align="center"
                                fixed="right"
                        >
                            <template #default="{ row }">
                                <div style="text-align: right;padding-right: 16px">
                                <el-button
                                        plain
                                        @click="handleAdd(row)"
                                        type="primary"
                                        size="mini"
                                        v-show="row.status != 1 && sysUser.user_type == 5"
                                >编辑
                                </el-button>
                                <el-button
                                        plain
                                        @click="handleDelete(row)"
                                        type="danger"
                                        size="mini"
                                        v-show="row.status != 1 && sysUser.user_type == 5"
                                >删除
                                </el-button>
                                <el-button
                                        plain
                                        @click="handleSh(row,1)"
                                        type="success"
                                        size="mini"
                                        v-show="row.status == 0 && sysUser.user_type == 1"
                                >同意
                                </el-button>
                                <el-button
                                        plain
                                        @click="handleSh(row,2)"
                                        type="danger"
                                        size="mini"
                                        v-show="row.status == 0 && sysUser.user_type == 1"
                                >驳回
                                </el-button>
                                <el-button
                                        plain
                                        @click="handleView(row)"
                                        type="primary"
                                        size="mini"
                                >详情
                                </el-button>
                                </div>
                            </template>
                        </el-table-column>
                    </el-table>
                    <el-pagination
                            background
                            :current-page="queryForm.pageNo"
                            :page-size="queryForm.pageSize"
                            :layout="layout"
                            :total="total"
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange2"
                    ></el-pagination>
                </el-card>
            </el-col>
        </el-row>
        <edit ref="edit" @fetch-data="fetchData"></edit>
        <views ref="views" @fetch-data="fetchData"></views>
        <shenhe ref="shenhe" @fetch-data="fetchData"></shenhe>
    </div>
</template>

<script>
import Edit from './components/edit'
import Views from './components/views'
import Shenhe from './components/shenhe'
import {edit, getList, toEdit} from "@/api_check/taocan";
import {getListAll} from "@/api_check/yiliaojigou";

export default {
    name: 'Index',
    components: {Edit, Views, Shenhe},
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
            sysUser: [],
            org_options: '',
            formVerify: {
                status: '',//审核状态 (待审:0，通过:1， 驳回:2)
                verify_ids: '',//审核ids
            },
            queryForm: {
                pack_name: '',
                pack_year: '',
                org_id: '',
                pageNo: 1,
                pageSize: 10,
                pack_source: '1',//套餐来源 (机构:1，通用:2)
            },
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
        setSelectRows(val) {
            this.selectRows = val
        },
        handleCurrentChange(val) {
            this.selectRows = val
        },
        async handleAdd(row) {
            if (row.id) {
                row.item_to_meal = await this.toEditId(row.id)//体检项目
                this.$refs['edit'].showDia(row)
            } else {
                this.$refs['edit'].showDia()
            }
        },
        async handleView(row) {
            if (row.id) {
                row = await this.toEditName(row.id)
                this.$refs['views'].showDia(row)
            } else {
                this.$refs['views'].showDia()
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
        handleSh(row, type) {
            if (type == 1) {
                this.formVerify.verify_ids = row.id
                this.formVerify.status = '1'
                this.$baseConfirm('你确定要【审核通过】当前项吗', null, async () => {
                    const {code, msg} = await edit(this.formVerify)
                    if (code == -1) {
                        this.$baseMessage(msg, 'error')
                    } else {
                        this.$baseMessage(msg, 'success')
                    }
                    await this.fetchData()
                })
            } else {
                this.$refs['shenhe'].showDia(row.id)
            }
        },

        handleDelete(row) {
            if (row.id) {
                this.$baseConfirm('你确定要删除当前项吗', null, async () => {
                    const {msg} = await edit({ids: row.id, is_del: '1'})
                    this.$baseMessage(msg, 'success')
                    await this.fetchData()
                })
            } else {
                if (this.selectRows != '' && this.selectRows != null) {
                    const ids = this.selectRows.map((item) => item.id).join()
                    this.$baseConfirm('你确定要删除选中项吗', null, async () => {
                        const {msg} = await edit({ids})
                        this.$baseMessage(msg, 'success')
                        await this.fetchData()
                    })
                } else {
                    this.$baseMessage('未选中任何行', 'error')
                    return false
                }
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
            await this.getInit();
            this.listLoading = true
            const {data} = await getList(this.queryForm)
            this.list = data.records
            this.total = data.total
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        async getInit() {
            const {data} = await toEdit()
            this.sysUser = data.data.sysUser
            const res = await getListAll()
            this.org_options = res.data
        },
        async toEditId(id) {
            const {data} = await toEdit({id: id})
            const item_to_meal = []
            if (data.data.item_to_meal) {
                for (let i = 0; i < data.data.item_to_meal.length; i++) {
                    item_to_meal.push(data.data.item_to_meal[i].id)
                }
            }
            return item_to_meal
        },
        async toEditName(id) {
            const {data} = await toEdit({id: id})
            const item_to_meal = []
            if (data.data.item_to_meal) {
                for (let i = 0; i < data.data.item_to_meal.length; i++) {
                    item_to_meal.push({
                        name: data.data.item_to_meal[i].item_name,
                        code: data.data.item_to_meal[i].item_no,
                        is_generic: data.data.item_to_meal[i].is_generic
                    })
                }
            }
            return item_to_meal
        },
        reseat() {
            this.queryForm.pack_name = ''
            this.queryForm.pack_year = ''
            this.queryForm.org_id = ''
            this.queryData()
        }
    },
}
</script>
