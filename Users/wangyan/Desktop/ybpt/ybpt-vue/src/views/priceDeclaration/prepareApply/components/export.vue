<template>
    <el-drawer
            :title="title"
            :visible.sync="dialog"
            direction="rtl"
            :with-header="false"
            :before-close="handleClose"
            custom-class="box_drawer"
            size="1060px"
            ref="drawer"
            :close-on-click-modal="true"
            append-to-body
    >
        <!-- pdf 内嵌 -->
        <iframe
                :src="PDFsrc"
                frameborder="0"
                style="width: 100%; height: 100%"
        ></iframe>
        <div class="drawer_content">
            <div class="drawer_footer">
                <el-button type="primary" v-if="showFlag" @click="examineStatus">签章</el-button>
                <el-button type="primary" v-if="!showFlag" @click="downPdf">下载PDF</el-button>
                <el-button @click="cancelForm">关 闭</el-button>
            </div>
        </div>
        <el-dialog
                title="驳回理由"
                :visible.sync="remarkVisible"
                append-to-body
                width="700px"
        >
            <el-form ref="form" :model="form" label-width="100px">
                <el-row :gutter="20">
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                        <el-form-item label="说明" prop="regeditType">
                            <el-input
                                    type="textarea"
                                    v-model="form.approval_opinion"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div slot="footer" class="dialog-footer">
                <el-button @click="closeRemark">取 消</el-button>
                <el-button type="primary" @click="reject">确 定</el-button>
            </div>
        </el-dialog>
    </el-drawer>
</template>
<style lang="scss" scoped>
    .file-main {
        padding: 0 30px;
        h2 {
            font-size: 20px;
            font-weight: bold;
        }
        .jj {
            font-size: 16px;
            line-height: 25px;
            margin-top: 30px;
            p {
                text-align: center;
            }
        }
        .doc-content {
            font-size: 16px;
            margin-top: 30px;
            padding: 0 0px;
            line-height: 30px;
            text-indent: 2em;
        }
        .doc-footer {
            margin-top: 30px;
            display: flex;
            flex-direction: row;
            justify-content: space-between;
            .box {
                flex: 1;
                line-height: 36px;
                font-size: 16px;
            }
        }
    }
</style>
<script>
import {zzPdf} from "@/api/drug";
import {fileURL} from "@/config/setting.config";

export default {
        name: 'edit',
        components: {},
        data() {
            return {
                status: '',
                remark: '',
                qsId: '',
                remarkVisible: false,
                downPath: "",
                PDFsrc: '',
                filePath: '',
                title: '',
                dialog: false,
                showFlag:true,
                queryForm: {
                    pageNo: 1,
                    pageSize: 10,
                    id: '',
                },
                form: {
                    id: '',
                    status: '',
                    contract_id: '',
                },
                formLabelWidth: '100px',
                timer: null,
            }
        },
        mounted() {
        },
        methods: {
            showDia(row) {
                if (!row) {
                    this.title = '新增'
                } else {
                    this.title = '查看'
                    this.form = Object.assign({}, row)
                    this.form.id = row.id
                    this.qsId = row.id;
                    this.status = row.status
                    if(this.status != "3"){
                        this.showFlag = true;
                    }else{
                        this.showFlag = false;
                    }
                    this.details(row)
                }
            },
            close() {
                this.dialog = false
                this.$emit('update:visible', false)
            },
            closeRemark() {
                this.remarkVisible = false
            },
            handleClose(done) {
            },
            downPdf(){
                self.location.href=this.downPath;
            },
            // async examineStatus(status) {
            //     var that = this;
            //     await this.$confirm('确定要签章吗？')
            //         .then((_) => {
            //             const loading = that.$loading({
            //                 lock: true,
            //                 text: '签章中，请稍等...',
            //                 spinner: 'el-icon-loading',
            //                 background: 'rgba(0, 0, 0, 0.7)'
            //             });
            //             this.timer = setTimeout(() => {
            //                  signConfirmation({"qsId":that.qsId,"filePath":that.filePath}).then((res) => {
            //                      loading.close();
            //                     if (res.code == 0) {
            //                         that.$baseMessage('签章成功！', 'success')
            //                         that.PDFsrc = res.data;
            //                         that.showFlag = false;
            //                         that.$emit('fetch-data')
            //                     }else{
            //                         this.$baseMessage(res.msg, 'error')
            //                     }
            //                 })
            //                 // 动画关闭需要一定的时间
            //
            //             }, 2000)
            //         })
            //         .catch((_) => {})
            // },

            cancelForm() {
                this.loading = false
                this.dialog = false
                this.close()
                this.$emit('closeChildDialog')
                this.$emit('fetch-data')
                this.PDFsrc = "";
                clearTimeout(this.timer)
            },
            async details(row) {
                var that = this
                this.dialog = true
                await setTimeout(function () {
                    const loading = that.$loading({
                        lock: true,
                        text: '正在生成确认书，请稍等...',
                        spinner: 'el-icon-loading',
                        background: 'rgba(0, 0, 0, 0.7)'
                    });
                    if(row.type == 6 ){
                        zzPdf(row.id).then((res) => {
                            loading.close();
                            if (res.code == 0) {
                                that.filePath = res.data.fileUrl
                                that.PDFsrc = fileURL + res.data.fileUrl + '?n=' + res.data.fileName

                            }else{
                                this.$baseMessage(res.msg, 'error')
                            }
                        })
                    }


                },500)

            }

        }
    }
</script>