<template>
    <div class="main-container" v-loading="loading" element-loading-text="数据上传中"
         element-loading-spinner="el-icon-loading"
         element-loading-background="rgba(0, 0, 0, 0.8)">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never" v-show="userinfo.user_type == 1 && userinfo.org_code == 320399">
                    <div slot="header">
                        <span class="tips">信息查询</span>
                        <div class="right">
                            <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
                            <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
                        </div>
                    </div>
                    <el-form label-width="100px">
                        <el-row :gutter="20">
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="机构编码">
                                    <el-input v-model.trim="queryForm.fixmedins_code" @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>
                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="机构名称">
                                    <el-input v-model.trim="queryForm.fixmedins_name" @keyup.enter.native="queryData"/>
                                </el-form-item>
                            </el-col>

                            <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                <el-form-item label="是否匹配">
                                    <el-select clearable
                                               @change="queryData"
                                               v-model.trim="queryForm.status"
                                               class="w">
                                        <el-option label="匹配成功" value="0"></el-option>
                                        <el-option label="匹配失败" value="1"></el-option>
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
                        <span class="tips">秘钥列表</span>
                        <el-upload ref="upfile" v-show="userinfo.user_type == 1 && userinfo.org_code == 320399"
                                   :auto-upload="false" :show-file-list=false accept=".zip,.rar,.7z"
                                   :on-change="handleChange"
                                   :on-success="handleSuccess" action="#">
                            <el-button icon="el-icon-upload2" type="success">上传文件</el-button>
                        </el-upload>
                    </div>

                    <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
                              highlight-current-row
                              border @current-change="handleCurrentChange" @selection-change="setSelectRows"
                              height="calc(100% - 50px)">
                        <template slot="empty">
                            <el-empty :image-size="200"></el-empty>
                        </template>
                        <el-table-column show-overflow-tooltip label="机构编码" prop="fixmedins_code" align="center"
                                         width="280px"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="fixmedins_name" width="220px" label="机构名称" align="center">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip prop="path" label="文件地址"
                                         align="center"></el-table-column>
                        <el-table-column show-overflow-tooltip prop="status" label="状态" align="center" width="180px">
                            <template #default="{ row }">
                                <span v-if="row.status==0">匹配成功</span>
                                <span v-else-if="row.status==1">匹配失败</span>
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="操作" width="250" align="center" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="handleAdd(row)" type="primary" size="mini" v-if="row.status == 1">编辑</el-button>
                                <el-button plain

                                           @click="dow(row)" type="primary" size="mini">
                                    下载文件
                                </el-button>
                            </template>


                        </el-table-column>
                    </el-table>
                    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                                   :layout="layout"
                                   :total="total" @size-change="handleSizeChange"
                                   @current-change="handleCurrentChange2"></el-pagination>
                </el-card>
            </el-col>
        </el-row>
        <edit ref="edit" @fetch-data="fetchData"></edit>

    </div>
</template>

<script>
    import {fileDeliveryList,importData} from '@/api/fileDelivery.js'
    import Edit from './components/edit'
    import {fileURL} from "@/config/setting.config";
    export default {
        name: 'Index',
        components: {Edit},
        data() {
            return {
                value1: '',
                loading: false,
                userinfo: '',
                checked: false,
                isShow: false,
                list: [],
                fileList: [],
                listLoading: true,
                layout: 'total, sizes, prev, pager, next, jumper',
                total: 0,
                selectRows: '',
                tableHeight: '100%',
                elementLoadingText: '正在加载...',
                queryForm: {
                    pageNo: 1,
                    pageSize: 10,
                    bankName: '',
                    bankNo: '',
                },
            }
        },
        created() {
            this.fetchData();
            this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
        },
        beforeDestroy() {
        },
        mounted() {
            // getHeight()
            // //增加监听事件，窗口变化时得到高度。
            // window.addEventListener('resize',this.getHeight,false)
        },
        methods: {
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
            openwin() {
                this.$refs['cardnum'].showDia()
            },
            handleAdd(row) {
                if (row.id) {
                    this.$refs['edit'].showDia(row)
                } else {
                    this.$refs['edit'].showDia()
                }
            },
            opendktj(row) {
                this.$refs['dktj'].showDia(row)
            },
            handleUser(row) {
                this.$refs['usermana'].showDia(row.id)
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
            handleDelete(row) {
                var that = this;
                if (row.id) {
                    that.$baseConfirm('你确定要删除当前项吗', null, async () => {
                        delBank(row).then((res) => {
                            that.fetchData()
                        })

                    })
                } else {
                    if (that.selectRows != '' && that.selectRows != null) {
                        const ids = that.selectRows.map((item) => item.id).join()
                        that.$baseConfirm('你确定要删除选中项吗', null, async () => {
                            const {msg} = await doDelete({ids})
                            that.$baseMessage(msg, 'success')
                            that.fetchData()
                        })
                    } else {
                        that.$baseMessage('未选中任何行', 'error')
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
            fetchData() {
                var that = this;
                fileDeliveryList(that.queryForm).then((res) => {
                    if (res.code == 0) {
                        that.list = res.data.records;
                        console.log(that.list);
                        that.total = res.data.total
                    }
                })
            },
            //获取表格序号
            getIndex($index) {
                //表格序号
                return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
            },
            reseat() {
                this.queryForm.fixmedins_name = "";
                this.queryForm.fixmedins_code = "";
                this.queryForm.status = "";
                this.fetchData();
            },
            dow(row){
                self.location.href=fileURL+"/"+row.path ;
            },
            async handleChange(file, fileList) {
                this.loading = true
                this.fileList = fileList;
                let fd = new FormData();
                this.fileList.forEach(item => {
                    //文件信息中raw才是真的文件
                    fd.append("file", item.raw);
                })

                var result = await importData(fd);
                if (result.data.code == 0) {
                    this.$refs.upfile.clearFiles();
                    this.$baseMessage("上传成功", 'success')
                    this.queryData()
                } else {
                    this.$refs.upfile.clearFiles();
                    this.$baseMessage(result.data.msg, 'error')
                }
                this.dialog = false
                this.loading = false
            },
            handleSuccess() {
                this.queryData()
            },
        },
    }
</script>
