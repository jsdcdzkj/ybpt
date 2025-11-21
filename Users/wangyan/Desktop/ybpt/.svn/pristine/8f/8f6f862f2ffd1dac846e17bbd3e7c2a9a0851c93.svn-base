<template>
    <!-- <el-dialog :title="title" :visible.sync="dialogFormVisible" width="60%" @close="close" :close-on-click-modal="false"> -->
    <el-drawer :title="title" :before-close="handleClose" :visible.sync="dialogFormVisible" direction="rtl"
               custom-class="box_drawer" size="40%" ref="drawer" :close-on-click-modal="true"
               append-to-body>
        <div class="drawer_content">
            <div class="drawer_main">
                <el-form ref="form" :model="form" :rules="rules" label-width="100px">
                    <el-row :gutter="20">
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                            <el-form-item label="通知标题" prop="title">
                                <el-input v-model.trim="form.title" autocomplete="off"></el-input>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                            <el-form-item label="通知内容" prop="content">
                                <vab-quill v-model="form.content" :min-height="300" :options="options"></vab-quill>
                            </el-form-item>
                        </el-col>
                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                            <el-form-item label="通知附件" prop="fileIdList">
                                <!--                <el-upload ref="uploadPicture" class="upload-demo" action="" accept=".docx" :auto-upload="false"-->
                                <!--                  :on-remove="handleRemovePicture" :before-remove="beforeRemove" :on-change="onChangePicture"-->
                                <!--                  :multiple="true" :limit="1" :on-exceed="handleExceed" :file-list="fileList">-->
                                <!--                  <el-button size="small" icon="el-icon-upload2" type="primary">-->
                                <!--                    文件上传-->
                                <!--                  </el-button>-->
                                <!--                  <span slot="tip" class="el-upload__tip ml-1">-->
                                <!--&lt;!&ndash;                    ( 请上传.docx文件 )&ndash;&gt;-->
                                <!--                  </span>-->
                                <!--                </el-upload>-->
                                <el-upload
                                    class="upload-demo"
                                    action=""
                                    :on-change="handleChange"
                                    :on-remove="handleRemove"
                                    :on-success="handleSuccess"
                                    :before-remove="beforeRemove"
                                    :on-preview="handlePreview"
                                    :before-upload="beforeUpload"
                                    multiple
                                    :limit="10"
                                    :on-exceed="handleExceed"
                                    :file-list="sysFileList">
                                    <el-button size="small" type="primary">点击上传</el-button>
                                    <!--                                    <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div>-->
                                </el-upload>
                            </el-form-item>
                        </el-col>
                        <!--                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                        <!--                            <el-form-item label="通知范围" prop="rangtype">-->
                        <!--                                <el-radio-group v-model="form.rangtype" @change="swichuser">-->
                        <!--                                    <el-radio :label="0">按机构类型</el-radio>-->
                        <!--                                    &lt;!&ndash;                  <el-radio :label="1">按用户</el-radio>&ndash;&gt;-->
                        <!--                                </el-radio-group>-->
                        <!--                            </el-form-item>-->
                        <!--                        </el-col>-->
                        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                            <el-form-item label="通知范围" prop="rangeList">
                                <el-select v-model="form.rangeList" style="width: 100%"
                                           clearable
                                           multiple
                                >
                                    <el-option v-for="item in seloptions" :key="item.value" :label="item.label"
                                               :value="item.value"></el-option>
                                </el-select>
                            </el-form-item>
                        </el-col>
                        <!--                        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="form.rangtype == 1">-->
                        <!--                            <el-form-item label="机构名称">-->
                        <!--                                <el-select v-model="form.jgmc" style="width: 100%" clearable>-->
                        <!--                                    <el-option label="第一人民医院" value="1"></el-option>-->
                        <!--                                    <el-option label="第二人民医院" value="2"></el-option>-->
                        <!--                                </el-select>-->
                        <!--                            </el-form-item>-->
                        <!--                        </el-col>-->
                        <!--                        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="form.rangtype == 1">-->
                        <!--                            <el-form-item label="选择用户">-->
                        <!--                                <el-select v-model="form.user" style="width: 100%" clearable-->
                        <!--                                           @change="handleInputConfirmUser($event)">-->
                        <!--                                    <el-option label="admin" value="1"></el-option>-->
                        <!--                                    <el-option label="user1" value="2"></el-option>-->
                        <!--                                </el-select>-->
                        <!--                            </el-form-item>-->
                        <!--                        </el-col>-->
                        <!--                        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
                        <!--                            <el-form-item prop="alreadyrangtype">-->
                        <!--                                <el-alert title="已选范围" type="success" :closable="false">-->
                        <!--                                    <el-tag type="primary" :key="tag" v-for="tag in dynamicTags" closable-->
                        <!--                                            :disable-transitions="false"-->
                        <!--                                            @close="handleCloseTag(tag)"-->
                        <!--                                            style="margin-left:0!important;margin-right:5px!important;">-->
                        <!--                                        {{ tag }}-->
                        <!--                                    </el-tag>-->
                        <!--                                </el-alert>-->
                        <!--                            </el-form-item>-->
                        <!--                        </el-col>-->
                    </el-row>
                </el-form>
            </div>
            <div class="drawer_footer">

                <el-button @click="close">取 消</el-button>
                <el-button type="primary" @click="save" :loading="loading">
                    {{ loading ? '确定中 ...' : '确 定' }}
                </el-button>
            </div>
        </div>
        <!-- <div slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
        </div> -->
    </el-drawer>
    <!-- </el-dialog> -->
</template>

<script>
import vabQuill from '@/plugins/vabQuill'
import {importData, saveApi, getEntityByIdApi, editApi} from "@/api_check/notice";
import {fileURL} from "@/config/setting.config";

export default {
    name: 'noticesedit',
    components: {vabQuill},
    data() {
        return {
            sysFileList: [
                // {
                //     name: 'food.jpeg',
                //     url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
                // },
                // {
                //     name: 'food2.jpeg',
                //     url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'
                // }
            ],
            selectRows: '',
            options: {
                theme: 'snow',
                bounds: document.body,
                debug: 'warn',
                modules: {
                    toolbar: [
                        ['bold', 'italic', 'underline', 'strike'],
                        [{header: [1, 2, 3, 4, 5, 6, false]}],
                        [{size: ['small', false, 'large', 'huge']}],
                        [{color: []}, {background: []}],
                        ['blockquote', 'code-block'],
                        [{list: 'ordered'}, {list: 'bullet'}],
                        [{script: 'sub'}, {script: 'super'}],
                        [{indent: '-1'}, {indent: '+1'}],
                        [{align: []}],
                        [{direction: 'rtl'}],
                        // [{ font: [] }],
                        ['clean'],
                        // ['link', 'image'],
                    ],
                },
                placeholder: '内容...',
                readOnly: false,
            },
            seloptions: [
                {
                    value: 't',
                    label: '体检机构'
                },
                {
                    value: 'y',
                    label: '用人单位'
                }

            ],
            dynamicTags: [],
            inputValue: '',
            form: {
                title: "",
                content: "",
                rangeList: [],
            },
            rules: {
                title: [
                    {required: true, trigger: 'blur', message: '请输入标题'},
                ],
                content: [
                    {required: true, trigger: 'blur', message: '请输入内容'},
                ],
                rangeList: [
                    {type: 'array', required: true, message: '请至少选择一个通知范围', trigger: 'change'}
                ],
            },
            title: 'www',
            dialogFormVisible: false,
            id: "",
            isDisabled: false,
            loading: false,
            fileList: [],
            fileIdList: [],
            delFileIdList: []

        }
    },
    created() {

    },
    methods: {
        async handleChange(file, fileList) {
            this.dialog = true
            this.loading = true
            this.fileList = fileList
            let fd = new FormData()
            this.fileList.forEach((item) => {
                //文件信息中raw才是真的文件
                fd.append('file', item.raw)
            })
            importData(fd, (params) => {
                if (params == -1) {
                    this.$baseMessage("上传错误", 'error')
                    this.loading = false
                    return;
                }
                let d = params.data.data;
                let item = {
                    'name': d.fileName,
                    'url': fileURL + d.fileUrl,
                    'id': d.id

                }
                this.sysFileList.push(item)
                this.$baseMessage('上传成功', 'success')
                this.dialog = false
                this.loading = false

            })
        },
        beforeUpload(file) {
            // 阻止默认行为，不发送请求
            file.preventDefault();
        },

        handleSuccess(response, file, fileList) {
            debugger
            console.log(response)
            // this.fetchData()
        },
        handleRemove(file, fileList) {
            this.delFileIdList.push(file.id)
            this.sysFileList = fileList;
        },
        handlePreview(file) {
            console.log("preview")
            console.log(file);
            this.generateAndClickLink(file.url, file.name)
        },
        handleExceed(files, fileList) {
            this.$message.warning(`当前限制选择 10 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
        },
        beforeRemove(file, fileList) {
            return this.$confirm(`确定移除 ${file.name}？`);
        },
        setSelectRows(val) {
            this.selectRows = val
        },

        generateAndClickLink(url, fileName) {
            // const link = document.createElement('a');
            // link.href = url;
            // link.setAttribute('download', fileName);
            //
            // // 模拟点击事件
            // const event = new MouseEvent('click', {
            //     view: window,
            //     bubbles: true,
            //     cancelable: true,
            // });
            // link.dispatchEvent(event);


            debugger;

            var eleLink = document.createElement('a');
            eleLink.download = url;
            eleLink.style.display = 'none';
// // 字符内容转变成blob地址
            eleLink.href = url;
// // 触发点击
            eleLink.setAttribute("download", fileName);  // 设置下载文件的名称
            document.body.appendChild(eleLink);
            eleLink.click();
// // 然后移除
            document.body.removeChild(eleLink);

        },

        showDia(row) {
            this.initPage();
            this.sysFileList = [],
                this.loading = false;
            //console.log(row)
            if (!row) {
                this.title = '添加';
                this.id = "";
                this.isDisabled = false;
            } else {
                this.id = row.id;
                this.getEntityById(this.id);
                this.title = '编辑';
                this.isDisabled = true;

            }
            this.dialogFormVisible = true
        },


        async getEntityById(id) {
            const {data} = await getEntityByIdApi(id);
            console.log(data)
            this.form.title = data.title
            this.form.content = data.content
            this.form.rangeList = data.rangeList;
            for (let i = 0; i < data.fileInfoList.length; i++) {
                let f = data.fileInfoList[i];
                let item = {
                    'id': f.id,
                    'name': f.fileName,
                    'url': fileURL + f.fileUrl
                }
                this.sysFileList.push(item)
            }
        },

        handleClose() {
            let that = this
            if (this.loading) {
                return
            }
            this.$confirm('确定要提交吗？')
                .then((_) => {
                    this.loading = true
                    this.timer = setTimeout(() => {
                        that.save();
                        // 动画关闭需要一定的时间
                        setTimeout(() => {
                            this.loading = false
                        }, 400)
                    }, 2000)
                })
                .catch((_) => {
                })
        },
        handleCloseTag(tag) {
            this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
        },
        handleInputConfirm(event) {
            var that = this;
            that.form.jglx = event;
            let inputValue = that.form.jglx;
            var arr = that.dynamicTags;

            if (inputValue) {
                if (arr.length > 0) {
                    if (arr.indexOf(inputValue) == -1) {
                        that.dynamicTags.push(inputValue);
                    } else {
                        that.$message({
                            message: '此机构类型已存在',
                            type: 'warning'
                        });
                    }
                } else {
                    that.dynamicTags.push(inputValue);
                }
            }
        },
        handleInputConfirmUser(event) {
            var that = this;
            // console.log(event);
            // that.dynamicTags=[];
            that.form.user = event;
            let inputValue = that.form.user;
            var arr = that.dynamicTags;

            if (inputValue) {
                if (arr.length > 0) {
                    if (arr.indexOf(inputValue) == -1) {
                        that.dynamicTags.push(inputValue);
                    } else {
                        that.$message({
                            message: '此用户已存在',
                            type: 'warning'
                        });
                    }
                } else {
                    that.dynamicTags.push(inputValue);
                }
            }
        },
        async onChangePicture(file, fileList) {
            this.fileList = fileList
        },
        handleRemovePicture(file, fileList) {
            this.fileList = fileList
        },
        async close() {
            this.initPage();
            this.dialogFormVisible = false
            this.loading = false

        },
        initPage() {
            this.sysFileList = [];
            this.form.title = '';
            this.form.content = '';
            this.form.rangeList = [];
            this.fileIdList = [];
            this.delFileIdList = [];
        },
        save() {
            let that = this;
            if (that.loading) {
                return
            }
            console.log(1111111111)
            debugger
            that.$refs['form'].validate(async (valid) => {
                if (valid) {
                    that.loading = true;
                    // 动画关闭需要一定的时间
                    setTimeout(() => {
                        this.loading = false
                    }, 1000)

                    for (let i = 0; i < this.sysFileList.length; i++) {
                        this.fileIdList.push(this.sysFileList[i].id);
                    }
                    let vo = {
                        'fileIdList': this.fileIdList,
                        'delFileIdList': this.delFileIdList,
                        'title': this.form.title,
                        'content': this.form.content,
                        'rangeList': this.form.rangeList,
                        'id': this.id
                    }


                    let res;
                    debugger
                    if (this.id == '' || this.id == null) {
                        res = await saveApi(vo)
                    } else {
                        res = await editApi(vo);
                    }
                    debugger
                    if (res.code == 0) {
                        that.$message({
                            message: res.data,
                            type: 'success',
                        });
                        this.$emit('fetch-data')
                        that.close();
                    }
                    if (res.code == -1) {
                        this.$message.error(res.msg);
                    }

                } else {
                    return false
                }
            })
        },
    },
}
</script>
