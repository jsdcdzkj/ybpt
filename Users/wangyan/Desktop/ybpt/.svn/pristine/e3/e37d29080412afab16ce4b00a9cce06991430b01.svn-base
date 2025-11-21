<template>
    <el-drawer :title="title" :before-close="handleClose" :visible.sync="dialog" append-to-body direction="rtl"
               :with-header="false"
               :close-on-click-modal="false"
               custom-class="box_drawer" size="70%" ref="drawer">
        <div class="drawer_content">
            <el-form :model="form" :label-width="formLabelWidth">
                <div class="drawer_main">
                    <h5 class="inform-title">{{ form.title }}</h5>
                    <div class="box_card base-info">
                        <el-row :gutter="20">
                            <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                                <el-form-item label="单位名称:" prop="username">
                                    {{ form.org_name }}
                                </el-form-item>
                            </el-col>
                            <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                                <el-form-item label="单位医保编码:" prop="username">
                                    {{ form.org_code }}
                                </el-form-item>
                            </el-col>
                            <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                                <el-form-item label="经营性质:" prop="username">
                                    {{form.natures}}
                                </el-form-item>
                            </el-col>

                        </el-row>
                        <div class="right-add-btn">
                            <el-button type="success" class="right" icon="el-icon-plus" @click="showAdd">
                                新增
                            </el-button>
                        </div>
                        <el-table ref="listTable" :data="tableData"
                                  :element-loading-text="elementLoadingText" highlight-current-row border
                        >
                            <template slot="empty">
                                <el-empty :image-size="200"></el-empty>
                            </template>
                            <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="50px">
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="project_name" label="项目名称" min-width="200"
                                             align="center">
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="project_code" label="项目编码" min-width="200" align="center">
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="project_content" label="项目内涵" min-width="120"
                                             align="center">
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="except_content" label="除外内容" min-width="120"
                                             align="center">
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="unit" label="计价单位" align="center">
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="explain" label="说明" min-width="120"
                                             align="center">
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="child_or_not" label="是否儿童"
                                             width="170" align="center">
                                <template #default="scope">
                                    <el-tag type="danger" v-show="scope.row.child_or_not == 0">否</el-tag>
                                    <el-tag v-show="scope.row.child_or_not == 1" type="success">是</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="price" label="价格" align="center" >
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="typeName" label="费用类型" align="center">
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="service_price" label="是否在国家和省医疗服务价格项目规范之内"
                                             width="170" align="center">
                                <template #default="scope">
                                    <el-tag type="danger" v-show="scope.row.service_price == '0'">否</el-tag>
                                    <el-tag v-show="scope.row.service_price == '1'" type="success">是</el-tag>
                                </template>
                            </el-table-column>
                            <el-table-column show-overflow-tooltip prop="aaa3" fixed="right" label="明细" width="90"
                                             align="center">
                                <template slot-scope="scope">
                                    <el-button v-if="scope.row.is_write==1" type="success" plain
                                               @click="handleDefinite(scope.row,scope.$index)" size="mini"
                                               icon="el-icon-document"></el-button>
                                    <el-button v-else="scope.row.is_write==0" type="warning"
                                               @click="handleDefinite(scope.row,scope.$index)" plain size="mini">未填写
                                    </el-button>
                                </template>
                            </el-table-column>
                          <el-table-column align="center" fixed="right" label="附件" prop="aaa3" show-overflow-tooltip width="90">
                            <template slot-scope="scope">
                              {{scope.row.file}}
                            </template>
                          </el-table-column>
                            <el-table-column show-overflow-tooltip label="操作" fixed="right" width="180" align="center">
                                <template slot-scope="scope">
                                  <div class="button-con">
                                    <el-upload
                                        style="margin-right: 12px"
                                        action=""
                                        :auto-upload="false"
                                        :show-file-list="false"
                                        :multiple="false"
                                        :on-change="(file, fileList) => {fileChange(file, fileList, scope.row)}"
                                    >
                                      <el-button icon="el-icon-upload" plain size="mini" type="primary"></el-button>
                                    </el-upload>
                                    <el-button plain type="success" size="mini" icon="el-icon-edit"
                                               @click="handleAdd(scope.row,scope.$index)"></el-button>
                                    <el-button plain size="mini" icon="el-icon-delete"
                                               @click="handleDelete(scope.$index, tableData)"
                                               type="danger"></el-button>
                                  </div>
                                </template>
                            </el-table-column>

                        </el-table>
                    </div>
                    <div class="explain-text">
                        <p>注：</p>
                        <p>1. 按医疗服务价格项目在国家和省医疗服务价格项目规范之内或之外分开填写。</p>
                        <p>
                            2．项目在国家和省医疗服务价格项目规范之内，根据《江苏省基本医疗保险诊疗项目和医疗服务设施范围及支付标准》填写；项目在国家和省医疗服务价格项目规范之外，项目编码按徐医保发﹝2021﹞46号文规定设立并填写。</p>
                        <p>3. 经营性质：营利性。</p>
                        <p>4. 本表一式两份。</p>
                    </div>
                </div>
            </el-form>
            <div class="drawer_footer">
                <el-button @click="cancelForm">取 消</el-button>
                <el-button type="primary" @click="save" :loading="loading">
                    {{ loading ? '提交中 ...' : '提 交' }}
                </el-button>
            </div>
        </div>

        <examine4 ref="examine4" @fetch-data="fetchData"></examine4>
        <definite3 ref="definite3" @fetch-data="fetchData"></definite3>
    </el-drawer>
</template>



<script>

import Examine4 from './examine4'
import Definite3 from './definite3'
import {addBusinessProject} from "@/api/drug";
import {uploadDetailFile} from '@/api/sbApply'

export default {
        name: 'infoAdd4',
        components: {
            Examine4,
            Definite3
        },
        data() {
            return {

                value3: '',
                elementLoadingText: '正在加载...',
                title: '',
                dialog: false,
                loading: false,
                queryForm: {
                    pageNo: 1,
                    pageSize: 10,
                    username: '',
                },
                form: {
                },
                subData: {},

                formLabelWidth: '100px',
                timer: null,
                tableData: [],
                tableData2: [],
            }
        },
        mounted() {
        },
        methods: {
            showDia(row) {
                this.title = '考核单详情'
                this.form = Object.assign({}, row)
                this.dialog = true
                this.tableData = []

            },
            fetchData(row) {
                console.log("fetchData");
                console.log(this.tableData);
                console.log(Object.assign({}, row));

                if (row.sign == 'edit') {
                    this.tableData.splice(row.index, 1, Object.assign({}, row));
                    this.tableData[row.index].project_code = "903"+this.tableData[row.index].type+this.tableData[row.index].fourcode;
                }
                if (row.sign == 'add') {
                    this.tableData.push(Object.assign({}, row))
                    this.tableData[this.tableData.length-1].project_code = "903"+this.tableData[this.tableData.length-1].type+this.tableData[this.tableData.length-1].fourcode;
                    this.tableData[this.tableData.length-1].is_write = 0 ;
                    this.tableData[this.tableData.length-1].is_norm = "" ;
                    // this.tableData[this.tableData.length-1].service_price = "" ;
                    this.tableData[this.tableData.length-1].norm_code = "" ;
                    this.tableData[this.tableData.length-1].norm_name = "" ;
                    this.tableData[this.tableData.length-1].sense = "" ;
                    this.tableData[this.tableData.length-1].base = "" ;
                    this.tableData[this.tableData.length-1].norm = "" ;
                    this.tableData[this.tableData.length-1].risk = "" ;
                    this.tableData[this.tableData.length-1].apply_unit = "" ;
                    this.tableData[this.tableData.length-1].result = "" ;
                    this.tableData[this.tableData.length-1].declare = "" ;
                    this.tableData[this.tableData.length-1].matters = "" ;
                    this.tableData[this.tableData.length-1].price_manager = "" ;
                    this.tableData[this.tableData.length-1].legal = "" ;
                }
                if (row.sign == 'sm') {
                    if (row.sm) {
                        this.tableData.splice(row.sm.index, 1, Object.assign({}, row.sm));
                        this.tableData[row.sm.index].is_detail = row.is_detail;
                        // this.tableData[row.sm.index].project_code = "903"+this.tableData[row.sm.index].two_code+this.tableData[row.sm.index].fourcode;
                    }
                    if (row.sm2) {
                        console.log("this.tableData[row.sm.index].is_norm");
                        this.tableData[row.sm.index].is_norm = row.sm2.is_norm;
                        this.tableData[row.sm.index].legal = row.sm2.legal;

                        this.tableData[row.sm.index].norm_code = row.sm2.norm_code;
                        this.tableData[row.sm.index].is_write = row.sm2.is_write;
                        this.tableData[row.sm.index].norm_name = row.sm2.norm_name;
                        this.tableData[row.sm.index].sense = row.sm2.sense;
                        this.tableData[row.sm.index].base = row.sm2.base;
                        this.tableData[row.sm.index].norm = row.sm2.norm;
                        this.tableData[row.sm.index].risk = row.sm2.risk;
                        this.tableData[row.sm.index].apply_unit = row.sm2.apply_unit;
                        this.tableData[row.sm.index].result = row.sm2.result;
                        this.tableData[row.sm.index].declare = row.sm2.declare;
                        this.tableData[row.sm.index].matters = row.sm2.matters;
                        this.tableData[row.sm.index].price_manager = row.sm2.price_manager;
                    }
                    console.log(this.tableData[row.sm.index].is_norm);
                }
                if (row.sign == 'detail') {
                    this.tableData.splice(row.index, 1, Object.assign({}, row));
                }
            },
            async handleDelete(index, rows) {
              let fd = new FormData();
              if (rows[index].fileInfoId) {
                fd.append("fileInfoId", rows[index].fileInfoId);
                await uploadDetailFile(fd)
              }
              rows.splice(index, 1);
            },
            async fileChange(file, fileList, row) {
              let fd = new FormData();
              //替换
              if (row.fileInfoId) {
                fd.append("fileInfoId", row.fileInfoId);
              }
              row.file = file.name;
              fd.append("file", file.raw);
              var result = await uploadDetailFile(fd);
              if (result.data.code == 0) {
                row.fileInfoId = result.data.data.id
                this.tableData.splice(row.index, 1, Object.assign({}, row));
                this.$baseMessage("上传成功", 'success')
              } else {
                this.$baseMessage(result.data.msg, 'error')
              }
            },
            handleAdd(row, index) {
                row.index = index;
                this.$refs['examine4'].showEdit(row)
            },
            showAdd() {
              if(this.tableData.length > 0){
                this.$baseMessage('告知手续申请只可一次申请一条', 'error')
                return
              }
                this.$refs['examine4'].showEdit()
            },
            handleDefinite(row, index) {
                row.index = index
                row.sign = "sm"
                row.org_code = this.form.org_code
                row.org_name = this.form.org_name
                row.natures = this.form.natures
                console.log(this.tableData);

                this.$refs['definite3'].showDia(row)
            },

            async save() {
              if(this.tableData.length <= 0){
                this.$baseMessage('请至少保留一条数据！', 'error')
                return
              }

                var that = this;
                let sign = false;
                console.log(that.tableData);
                that.tableData.forEach(x => {
                    if (x.is_write == 0 || x.is_write == "" || x.is_write == null || x.is_write == undefined) {
                        sign = true
                    }
                })

                if (sign) {
                    that.$baseMessage('明细未填写,请完善信息！', 'error')
                    return
                }
                const loading = that.$loading({
                    lock: true,
                    text: '请稍等...',
                    spinner: 'el-icon-loading',
                    background: 'rgba(0, 0, 0, 0.7)'
                });
                addBusinessProject({
                    sbApplyVo: that.tableData
                }).then((res) => {
                    if (res.code == 0) {
                        that.dialog = false
                        loading.close();
                        setTimeout(() => {
                            that.$baseMessage('操作成功！', 'success')
                            that.$emit('fetch-data')
                        }, 300)

                    } else {
                        // that.dialog = false

                        that.$baseMessage(res.msg, 'error')
                        loading.close();
                    }
                })
            },
            handleClose(done) {
                if (this.loading) {
                    return
                }

                // this.$confirm('点击确认将提交到医保审核，是否执行提交操作？')
                //     .then((_) => {
                //         this.save()
                //         this.timer = setTimeout(() => {
                //             done()
                //             // 动画关闭需要一定的时间
                //             setTimeout(() => {
                //                 this.loading = false
                //             }, 100)
                //         }, 100)
                //     })
                //     .catch((_) => {
                //     })
            },
            cancelForm() {
              if (this.loading) {
                return
              }
              this.$confirm('取消后将丢失已填信息！确定要取消表单吗？', {
                confirmButtonText: '是',
                cancelButtonText: '否'
              })
                  .then((_) => {
                    this.loading = false
                    this.dialog = false
                    clearTimeout(this.timer)
                  })
                  .catch((_) => {
                  })
            },
        },
    }
</script>
<style lang="scss" scoped>
    .button-con{
      display: flex;
    }
    .mb2 {
        margin-bottom: 20px;
    }

    .right-add-btn {
        margin-bottom: 10px;
        text-align: right;
    }

    .inform-title {
        width: 90%;
        margin: 0 auto 20px;
        padding: 10px 0;
        line-height: 24px;
        font-size: 18px;
        font-weight: bold;
        text-align: center;
    }

    .explain-text {
        margin-top: 14px;
        padding: 10px 0;
        font-size: 14px;
        color: #999;
        line-height: 20px;

        p {
            padding: 0;
            margin: 0;
        }
    }

    ::v-deep {
        .base-info {
            .el-form-item--small.el-form-item {
                margin-bottom: 0;
            }
        }
    }
</style>