<template>
    <div class="index-container">

        <vab-query-form>
            <vab-query-form-left-panel :span="12" style="min-height: 1px;">
                <el-button icon="el-icon-plus" type="primary" @click="handleEdit" v-if="userinfo.user_type == 1 || (userinfo.user_type == 2 && userinfo.isAdmin == 1)|| (userinfo.user_type == 3 && userinfo.isAdmin == 1)">
                    添加
                </el-button>
                <!--        <el-button icon="el-icon-delete" type="danger" @click="handleDelete">-->
                <!--          批量删除-->
                <!--        </el-button>-->
            </vab-query-form-left-panel>
            <vab-query-form-right-panel :span="12">
                <el-form :inline="true" :model="queryForm" @submit.native.prevent>
                    <el-form-item>
                        <el-input v-model.trim="queryForm.roleName" placeholder="请输入信息查询" clearable
                            @keyup.enter.native="queryData" />
                    </el-form-item>
                    <el-form-item>
                        <el-button icon="el-icon-search" type="primary" @click="queryData">
                            查询
                        </el-button>
                    </el-form-item>
                </el-form>
            </vab-query-form-right-panel>
        </vab-query-form>

        <el-table
                v-loading="listLoading"
                :data="list"
                border
                :element-loading-text="elementLoadingText"
                @selection-change="setSelectRows"
                height="calc(100vh - 360px)"
        >
            <el-table-column show-overflow-tooltip type="index" align="center"></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="role_name"
                    label="角色名称"
                    align="center"
            ></el-table-column>
            <el-table-column
                    show-overflow-tooltip
                    prop="memo"
                    label="描述"
                    align="center"
            ></el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="200" align="center" v-if="userinfo.user_type == 1 || (userinfo.user_type == 2 && userinfo.isAdmin == 1)|| (userinfo.user_type == 3 && userinfo.isAdmin == 1)">
                <template #default="{ row }">
                    <el-button size="mini" plain type="primary" @click="handleEdit(row)" >编辑</el-button>
                    <el-button size="mini" plain type="danger" @click="handleDelete(row)">删除</el-button>
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
                @current-change="handleCurrentChange"
        ></el-pagination>
        <edit ref="edit" @fetch-data="fetchData"></edit>
    </div>
</template>

<script>
    import {getList, doDelete} from '@/api/roleManagement'
    import Edit from './components/RoleManagementEdit'

    export default {
        name: 'RoleManagement',
        components: {Edit},
        data() {
            return {
                list: [],
                listLoading: true,
                layout: 'total, sizes, prev, pager, next, jumper',
                total: 0,
                selectRows: '',
              userinfo: {},
                elementLoadingText: '正在加载...',
                queryForm: {
                    pageNo: 1,
                    pageSize: 10,
                    roleName: '',
                },
            }
        },
        created() {
          this.userinfo = JSON.parse(localStorage.getItem("userinfo"))
            this.fetchData()
        },
        methods: {
            setSelectRows(val) {
                this.selectRows = val
            },
            handleEdit(row) {
                if (row.id) {
                    this.$refs['edit'].showEdit(row)
                } else {
                    this.$refs['edit'].showEdit()
                }
            },
            handleDelete(row) {
                if (row.id) {
                    this.$baseConfirm('你确定要删除当前项吗', null, async () => {
                        const res = await doDelete({"roleId": row.id})
                        if (res.code == "0") {
                            this.$baseMessage("操作成功！", 'success')
                        } else {
                            this.$baseMessage(res.msg, 'error')
                        }
                        this.fetchData()
                    })
                } else {
                    // if (this.selectRows.length > 0) {
                    //   const ids = this.selectRows.map((item) => item.id).join()
                    //   this.$baseConfirm('你确定要删除选中项吗', null, async () => {
                    //     const { msg } = await doDelete({ ids })
                    //     this.$baseMessage(msg, 'success')
                    //     this.fetchData()
                    //   })
                    // } else {
                    //   this.$baseMessage('未选中任何行', 'error')
                    //   return false
                    // }
                }
            },
            handleSizeChange(val) {
                this.queryForm.pageSize = val
                this.fetchData()
            },
            handleCurrentChange(val) {
                this.queryForm.pageNo = val
                this.fetchData()
            },
            queryData() {
                this.queryForm.pageNo = 1
                this.fetchData()
            },
            async fetchData() {
                this.listLoading = true
                const res = await getList(this.queryForm)
                if (res.code = "0") {
                    this.list = res.data.records;
                    this.total = res.data.total;
                }
                setTimeout(() => {
                    this.listLoading = false
                }, 300)
            },
        },
    }
</script>
<style lang="scss" scoped>
    .index-container {
      min-height: calc(#{$base-app-main-height} - 50px) !important;
    }

</style>