<template>
    <el-drawer :title="title" :visible.sync="dialog" direction="rtl" :with-header="false"
               custom-class="box_drawer" size="80%" ref="drawer" :wrapperClosable="false">
        <div class="drawer_content">
            <el-form :model="form" label-width="100px" :rules="rules" ref="orderForm">
                <div class="drawer_main">
                    <div class="box_card">
                        <div class="box_header">
                            <span>基本信息</span>
                        </div>
                        <div class="box_content">
                            <el-row :gutter="20">
                                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                    <el-form-item label="考核单名称" prop="title">
                                        <el-input v-model.trim="form.title" clearable
                                            class="input-with-select" :disabled="flag"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                    <el-form-item label="机构类型" prop="orgType">
                                        <el-select v-model="form.orgType" style="width: 100%" @change="getLevelList"
                                                   :disabled="flag">
                                            <el-option value="1" label="医疗机构"></el-option>
                                            <el-option value="2" label="零售药店"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                    <el-form-item label="机构类别" prop="category" v-if="form.orgType==1">
                                        <el-select v-model="form.category" style="width: 100%" :disabled="flag">
                                            <el-option label="门诊" value="1"></el-option>
                                            <el-option label="住院" value="2"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                    <el-form-item label="协议等级" prop="aggrementLv">
                                        <el-select v-model="form.aggrementLv" style="width: 100%" :disabled="flag">
                                            <el-option
                                                    v-for="item in levelList"
                                                    :key="item.id"
                                                    :label="item.name"
                                                    :value="item.id"
                                            ></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                    <el-form-item label="考核年度" prop="year">
                                        <el-date-picker v-model="form.year" type="year" placeholder="选择年" format="yyyy"
                                                        value-format="yyyy" :disabled="flag">
                                        </el-date-picker>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                    <el-form-item label="经营性质" prop="natures">
                                        <el-select v-model="form.natures" style="width: 100%" :disabled="flag" clearable>
                                            <el-option label="公立" value="1"></el-option>
                                            <el-option label="私立" value="2"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                                <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6">
                                    <el-form-item label="精神专科" prop="spirit">
                                        <el-select v-model="form.spirit" style="width: 100%" :disabled="flag" clearable>
                                            <el-option label="非精神专科" value="0"></el-option>
                                            <el-option label="精神专科" value="1"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-col>
                            </el-row>
                        </div>
                    </div>
                    <div class="box_card">
                        <div class="box_header">
                            <span>考核单内容</span>
                            <el-button type="primary" class="right" icon="el-icon-plus" @click="addLevel1"
                                       :disabled="flag">新增评价项目
                            </el-button>
                        </div>
                    </div>
                    <el-table :data="tableList" border style="margin-top: 20px;" height="calc(100vh - 340px)"
                              :span-method="arraySpanMethod" show-summary :summary-method="getSummaries" ref="orderTable">
                        <el-table-column show-overflow-tooltip label="评价项目" min-width="200px">
                            <template #default="{ row }">
                                <el-input v-model="row.name" v-if="row.level==1" :disabled="flag"  maxlength="50" show-word-limit ></el-input>
                                <el-input v-model="row.title" v-if="row.level==2" type="textarea" maxlength="100"
                                          rows="5" resize="none" style="margin-left: 20px;width: calc(100% - 20px);"
                                          :disabled="flag" show-word-limit ></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="分值" prop="score" align="center" width="50px">
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="评价内容" min-width="200px">
                            <template #default="{ row }">
                                <el-input type="textarea" maxlength="200" v-if="row.level==2" rows="8" resize="none"
                                          show-word-limit v-model="row.content" :disabled="flag"></el-input>
                            </template>
                        </el-table-column>
                        <el-table-column  label="评分办法" width="500px">
                            <template #default="{ row }">
                                <span v-if="row.level==3">{{row.name}}({{row.score}}分)</span>
                            </template>
                        </el-table-column>
                        <el-table-column show-overflow-tooltip label="操作" width="240px" fixed="right">
                            <template #default="{ row }">
                                <el-button plain @click="delItem(row.indexs)" type="danger" size="mini"
                                           :disabled="flag">
                                    删除
                                </el-button>
                                <el-button v-if="row.level===1" plain @click="addLevel2(row.indexs)" type="success"
                                           size="mini" :disabled="flag">
                                    新增指标
                                </el-button>
                                <el-button plain @click="addLevel3(row.indexs,row)" type="primary" size="mini"
                                           v-if="row.level===3">
                                    编辑
                                </el-button>
                                <el-button plain @click="addLevel3(row.indexs)" type="success" size="mini"
                                           v-if="row.level===2" :disabled="flag">
                                    新增评分办法
                                </el-button>
                            </template>
                        </el-table-column>
                    </el-table>
                </div>
            </el-form>
            <div class="drawer_footer">
                <el-button @click="reClose">关 闭</el-button>
                <el-button @click="reStart" v-show="!flag">重 置</el-button>
                <el-button type="primary" @click="saveInfo" :loading="loading" v-show="!flag">
                    {{ loading ? '提交中 ...' : '提 交' }}
                </el-button>
                <!-- <el-button type="success" @click="$refs.drawer.closeDrawer()" :loading="loading">
                    {{ loading ? '暂存中 ...' : '暂 存' }}
                </el-button> -->
            </div>
            <!-- 新增评分办法 -->
            <el-dialog title="评分办法" width="600px" :visible.sync="methodsDialog" append-to-body
                       :close-on-click-modal="false">
                <el-form label-width="auto" style="max-height: 800px;overflow: auto;" :rules="rules">
                    <el-form-item label="选项">
                        <el-checkbox v-model="methodsForm.fillIn" :disabled="flag"  @change="changeFillIn">是否需要机构填写</el-checkbox>
                    </el-form-item>
                    <el-form-item label="基本内容" v-show="methodsForm.fillIn">
                        <el-checkbox v-model="methodsForm.isDescribe" :disabled="flag">填写描述</el-checkbox>
                        <el-checkbox v-model="methodsForm.isUpload" :disabled="flag">文件上传</el-checkbox>
                        <!-- <el-checkbox v-model="methodsForm.autoScore" :disabled="flag" @change="changeAutoScore">机构自主评分</el-checkbox> -->
                    </el-form-item>
                    <el-form-item label="自主评分方式" v-show="methodsForm.fillIn">
                        <el-checkbox v-model="methodsForm.autoScore" :disabled="flag" @change="changeAutoScore">机构自主评分</el-checkbox>
                        <el-checkbox v-model="methodsForm.indicator" :disabled="flag" @change="changeIndicator">填写指标值</el-checkbox>
                    </el-form-item>
                    <el-form-item label="评分办法" required>
                        <el-input v-model="methodsForm.name" clearable placeholder="请输入评分办法"
                                  :disabled="flag"></el-input>
                    </el-form-item>
                    <el-form-item label="分值" required>
                        <el-input v-model="methodsForm.score" clearable placeholder="请输入分值" :disabled="flag"></el-input>
                        <el-checkbox v-model="methodsForm.configuration" @change="changeConfiguration" :disabled="flag" v-show="!methodsForm.autoScore">
                            自定义配置
                        </el-checkbox>

                    </el-form-item>
                    <template v-if="methodsForm.configuration">
                        <el-form-item label="计分方式" v-show="!methodsForm.autoScore">
                            <el-radio-group v-model="methodsForm.type" :disabled="flag">
                                <el-radio label="1">按排名(升)</el-radio>
								<el-radio label="2">按排名(降)</el-radio>
                                <el-radio label="3">按比例</el-radio>
                                <el-radio label="4">按数值</el-radio>
                            </el-radio-group>
                            <div v-if="methodsForm.type!=4">
                                <div class="scoreDiyContainer" v-for="el,index in methodsForm.evalMethodInfos"
                                     :key="index">
                                    <el-input v-model="el.startRate" style="width: 80px" @change="startYanZheng(el.startRate,index)" :disabled="flag"></el-input>
                                    % ~
                                    <el-input v-model="el.endRate" style="width: 80px" @change="endYanZheng(el.endRate,index)" :disabled="flag"></el-input>
                                    %得
                                    <el-input v-model="el.score" :disabled="flag"></el-input>
                                    分
                                    <i class="el-icon-circle-plus" style="color: #1890ff;" @click="addScoreType"
                                       v-if="index===0" v-show="!flag" ></i>
                                    <i class="el-icon-delete-solid" style="color: #ff4d4f;" @click="delScoreType(index)"
                                       v-else v-show="!flag"></i>
                                </div>
                            </div>
                            <div v-else>
                                <div class="scoreDiyContainer" v-for="el,index in methodsForm.evalMethodInfos"
                                     :key="index">
                                    <el-input v-model="el.startRate" style="width: 80px" @change="startYanZheng(el.startRate,index)" :disabled="flag"></el-input>
                                    ~
                                    <el-input v-model="el.endRate" style="width: 80px" @change="endYanZheng(el.endRate,index)" :disabled="flag"></el-input>
                                    得
                                    <el-input v-model="el.score" :disabled="flag"></el-input>
                                    分
                                    <i class="el-icon-circle-plus" style="color: #1890ff;" @click="addScoreType"
                                       v-if="index===0" v-show="!flag"></i>
                                    <i class="el-icon-delete-solid" style="color: #ff4d4f;" @click="delScoreType(index)"
                                       v-else v-show="!flag"></i>
                                </div>
                            </div>

                        </el-form-item>
                    </template>
                    <el-form-item label="模板管理">
                        <el-upload
                                :on-success="uploadSuccess"
                                :on-change="onChangePicture"
                                :multiple="false"
                                action=""
                                :on-exceed="handleExceed"
								:before-remove="beforeRemove"
                                :auto-upload="false"
                                :file-list="fileList"
                                :limit="1" :disabled="flag">
                            <el-button size="small" type="primary" :disabled="flag">点击上传</el-button>
                        </el-upload>
						<a v-if="fileList.length>0" @click="downLoad">下载</a>
                    </el-form-item>
                </el-form>
                <div style="text-align: right;">
                    <el-button @click="methodsDialog=false">取消</el-button>
                    <el-button type="primary" @click="confirmMethods" v-show="!flag" :loading="loading1">确定</el-button>
                </div>
            </el-dialog>
        </div>
    </el-drawer>
</template>

<script>

    import { getEntity, getFileInfo, getTaskEntity, saveOrUploadEval, uploadFile } from '@/api/eval';
import { fileURL } from "@/config/setting.config";
    export default {
        name: 'edit',
        data() {
            return {
                levelList: [],
                aList: [
                    {id: '1', name: '一级'},
                    {id: '2', name: '二级'},
                    {id: '3', name: '三级'},
                    {id: '9', name: '未定级'},
                ],
                bList: [
                    {id: '4', name: 'A级'},
                    {id: '5', name: 'B级'},
                    {id: '6', name: 'C级'},
                ],
                title: '',
                dialog: false,
                loading: false,
                loading1: false,
                methodsDialog: false,
                form: {
                    title: '',
                    orgType: '',
                    category: '',
                    aggrementLv: '',
                    year: '',
                    natures: '',
                    spirit: ''
                },
                list: [],
                flag: false,
                tableList: [],
                methodsIndex: [],
                methodsForm: {
                    name: '',
                    score: '',
                    fileId: '',
                    type: '1',
                    autoScore: false,
                    indicator:false,
                    configuration: false,
                    isDescribe: false,
                    isUpload: false,
                    fillIn: false,
                    evalMethodInfos: []
                },
                fileList: [],
                rules: {
                    title: [{required: true, trigger: 'blur', message: '请输入考核单名称'}],
                    orgType: [{required: true, message: '请选择机构类型', trigger: 'change'}],
                    category: [{required: true, message: '请选择机构类别', trigger: 'change'}],
                    aggrementLv: [{required: true, message: '请选择协议等级', trigger: 'change'}],
                    year: [{required: true, message: '请选择考勤年度', trigger: 'change'}],
                    // natures: [{required: true, message: '请选择经营性质', trigger: 'change'}],
                    // spirit: [{required: true, message: '请选择是否为精神专科', trigger: 'change'}],
                }
            }
        },
        watch: {
            methodsDialog(val) {
                if (!val) {
                    this.fileList = [];
                }
            }
        },
        mounted() {

        },
    methods: {
        startYanZheng(start, i) { 
            let list=this.methodsForm.evalMethodInfos
            for (let j = 0; j < list.length; j++) {
               if (j!=i) {
                   if (list[j].startRate<=Number(start)&&Number(start)<list[j].endRate) {
                       this.$baseMessage('区间不能重复', 'warning');
                       this.methodsForm.evalMethodInfos[i].startRate= ''
                       return
                }
               }
            }
        },
        endYanZheng(end, i) { 
            let list=this.methodsForm.evalMethodInfos
            for (let j = 0; j < list.length; j++) {
               if (j!=i) {
                   if (list[j].startRate < Number(end) && Number(end) <= list[j].endRate) {
                       this.$baseMessage('区间不能重复', 'warning');
                       this.methodsForm.evalMethodInfos[i].endRate = ''
                       return
                }
               }
                
            }

        },
        getBlob(url, cb) {
      var xhr = new XMLHttpRequest()
      xhr.open('GET', url, true)
      xhr.responseType = 'blob'
      xhr.onload = function() {
        if (xhr.status === 200) {
          cb(xhr.response)
        }
      }
      xhr.send()
        },
        saveAs(blob, filename) {
      if (window.navigator.msSaveOrOpenBlob) {
        navigator.msSaveBlob(blob, filename)
      }
      else {
        var link = document.createElement('a')
        var body = document.querySelector('body')
        link.href = window.URL.createObjectURL(blob)
        link.download = filename
        link.style.display = 'none'
        body.appendChild(link)
        link.click()
        body.removeChild(link)
        window.URL.revokeObjectURL(link.href)
      }
},
        downLoad() { 
            this.getBlob(this.fileList[0].downLoad, (blob) => {
        this.saveAs(blob, this.fileList[0].name)
      })
        },
        changeFillIn() { 
            this.methodsForm.configuration = false
            this.methodsForm.indicator = false
            this.methodsForm.autoScore = false
              this.changeConfiguration()
        },
        changeAutoScore() { 
            this.methodsForm.configuration = false
           this.changeConfiguration()
           this.methodsForm.indicator=false
        },
        changeIndicator() { 
            if (this.methodsForm.indicator) {
                this.methodsForm.configuration = true
            } else { 
                this.methodsForm.configuration = false
            }
            this.methodsForm.autoScore = false
            this.changeConfiguration()
        },
		beforeRemove(file, fileList) {
            this.fileList=[]
      },
            async getFlag() {
                if (this.form.id) {
                    const res = await getTaskEntity({id: this.form.id})
                    if (res.code != 0) {
                        this.$baseMessage('设计单被引用无法修改', 'warning');
                        this.flag = true
                    } else {
                        this.flag = false
                    }
                }
            },
            handleExceed(files, fileList) {
                this.$message.warning(`请先删除原文件，当前限制选择 1 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
            },
            reStart() {
                this.$refs.orderForm.resetFields();
                this.list = []
                this.tableList = this.flatData(this.list)
            },
            reClose() {
                this.$refs.orderForm.resetFields();
                this.flag = false,
                    this.list = []
                this.tableList = this.flatData(this.list)
                this.dialog = false
            },
            async getFile() {
                this.fileList = []
                if (this.methodsForm.fileId) {
                    const {data} = await getFileInfo({id: this.methodsForm.fileId})
                    this.fileList = data
                }

            },
            onChangePicture(file, fileList) {
                this.fileList = fileList
                let fd = new FormData()
                this.fileList.forEach((item) => {
                    //文件信息中raw才是真的文件
                    fd.append('file', item.raw)
                    fd.append('id', this.methodsForm.id)
                    fd.append('year', this.form.year)
                })
                uploadFile(fd).then((result) => {
                    this.methodsForm.fileId = result.data.id
                    this.fileList[0].downLoad = fileURL + result.data.fileUrl
                })
            },
        changeConfiguration() {
                if (!this.methodsForm.configuration) {
                    this.methodsForm.indicator=false
                }
                if (this.methodsForm.configuration) {
                    this.methodsForm.evalMethodInfos.push({
                        score: '',
                        startRate: '',
                        endRate: ''
                    })
                } else {
                    this.methodsForm.evalMethodInfos = []
                }
            },
            getLevelList() {
                var that = this;
                if (that.form.orgType == 1) {
                    that.levelList = that.aList;
                    that.form.aggrementLv = "";
                } else if (that.form.orgType == 2) {
                    that.levelList = that.bList;
                    that.form.aggrementLv = "";
                } else {
                    that.levelList = []
                }

                if (that.form.aggrementLv != undefined) {
                    that.form.aggrementLv = "";
                }
                // this.queryData() ;
            },
            // 新增评价项目
            addLevel1() {
                this.list.push({
                    name: '',
                    score: 0,
                    evalCategoryStardards: []
                });
                this.tableList = this.flatData(this.list);
            },
            // 新增指标
            addLevel2(indexs) {
                this.list[indexs[0]].evalCategoryStardards.push({
                    title: '',
                    content: '',
                    score: 0,
                    evalStardardMethods: []
                })
                this.tableList = this.flatData(this.list);
            },
            //新增评分办法
            addLevel3(indexs, info) {
                this.methodsIndex = indexs;
                if (!info) {
                    this.methodsForm = this.$options.data().methodsForm;
                    this.getFile()
                } else {
                    var data = JSON.parse(JSON.stringify(info));
                    delete data.indexs;
                    this.methodsForm = Object.assign(data, {
                        configuration: Boolean(Number(data.configuration)),
                        isDescribe: Boolean(Number(data.isDescribe)),
                        isUpload: Boolean(Number(data.isUpload)),
                        autoScore: Boolean(Number(data.autoScore)),
                        indicator:Boolean(Number(data.indicator)),
                        fillIn: Boolean(Number(data.fillIn))
                    })
                    this.getFile()
                }
                this.methodsDialog = true;
            },
        confirmMethods() {
            this.loading1 = true
                let info = JSON.parse(JSON.stringify(this.methodsForm));
                if (!info.name) {
                    this.$baseMessage('请输入评分办法', 'error');
                    this.loading1 = false
                    return
                }
                if (!Number(info.score)) {
                    this.$baseMessage('请输入正确的分值', 'error');
                    this.loading1 = false
                    return
                }
                if (info.configuration) {
                    if (info.evalMethodInfos.some((el, index) => {
                        if (el.startRate || el.startRate === 0) {
                            if (Number(el.startRate) !== 0) {
                                if (!Number(el.startRate)) {
                                    this.$baseMessage(`请正确的输入第${index + 1}项的起始值`, 'error');
                                    this.loading1 = false
                                    return true
                                }
                            }
                        } else if (el.startRate === '') {
                            this.$baseMessage(`请正确的输入第${index + 1}项的起始值`, 'error');
                            this.loading1 = false
                            return true
                        }
                        if (!Number(el.endRate)) {
                            this.$baseMessage(`请正确的输入第${index + 1}项的结束值`, 'error');
                            this.loading1 = false
                            return true
                        }
                        if (!Number(el.score)&&Number(el.score) !== 0) {
                            this.$baseMessage(`请正确的输入第${index + 1}项的分值`, 'error');
                            this.loading1 = false
                            return true
                        } else if (Number(el.score) > info.score) {
                            this.$baseMessage(`第${index + 1}项的分值不能超过总分值`, 'error');
                            this.loading1 = false
                            return true
                        }
                    })) {
                        return
                    }
                }
                let result = Object.assign(info, {
                    configuration: Number(info.configuration),
                    isDescribe: Number(info.isDescribe),
                    isUpload: Number(info.isUpload),
                    autoScore: Number(info.autoScore),
                    indicator: Number(info.indicator),
                    fillIn: Number(info.fillIn)
                })
                let indexs = this.methodsIndex;
                if (indexs.length == 2) {
                    this.list[indexs[0]].evalCategoryStardards[indexs[1]].evalStardardMethods.push(result);
                } else {
                    this.list[indexs[0]].evalCategoryStardards[indexs[1]].evalStardardMethods[indexs[2]] = result;
                }
                this.computedScore();
            this.tableList = this.flatData(this.list);
            this.methodsDialog = false;
			this.$nextTick(()=>{
				this.$refs.orderTable.doLayout();
			})
            setTimeout(() => { 
                this.loading1 = false
            },500)  
            
            },
            //删除项
            delItem(indexs) {
                if (indexs.length == 1) {
                    this.$baseConfirm('操作后，其下的指标和方法也会一并删除，是否继续', null, () => {
                        this.list.splice(indexs[0], 1);
                        this.tableList = this.flatData(this.list);
                    })
                } else if (indexs.length == 2) {
                    this.$baseConfirm('操作后，其下的方法也会一并删除，是否继续', null, () => {
                        this.list[indexs[0]].evalCategoryStardards.splice(indexs[1], 1);
                        this.computedScore();
                        this.tableList = this.flatData(this.list);
                    })
                } else {
                    this.$baseConfirm('确定删除该方法吗', null, () => {
                        this.list[indexs[0]].evalCategoryStardards[indexs[1]].evalStardardMethods.splice(indexs[2], 1);
                        this.computedScore();
                        this.tableList = this.flatData(this.list);
                    })
                }
            },
            // 数据拆分重组
            flatData(data) {
                var arr = [];
                data.map((el, index) => {
                    arr.push(Object.assign(el, {indexs: [index], level: 1}));
                    if (el.evalCategoryStardards) {
                        el.evalCategoryStardards.map((item, idx) => {
                            arr.push(Object.assign(item, {indexs: [index, idx], level: 2}));
                            if (item.evalStardardMethods) {
                                item.evalStardardMethods.map((itm, idxs) => {
                                    arr.push(Object.assign(itm, {indexs: [index, idx, idxs], level: 3}));
                                })
                            }
                        })
                    }
                })
                return arr;
            },
            //动态计算分数
            computedScore() {
                var arr = JSON.parse(JSON.stringify(this.list));
                arr.map(el => {
                    el.score = el.evalCategoryStardards.reduce((a, b) => {
                        b.score = b.evalStardardMethods.reduce((c, d) => {
                            return c + Number(d.score);
                        }, 0)
                        return a + Number(b.score);
                    }, 0)
                })
                this.list = arr;
            },
            // 合并单元格
            arraySpanMethod({row, column, rowIndex, columnIndex}) {
                if (row.level == 1) {
                    return [1, 1];
                }
                if (row.level == 2) {
                    if ([0, 1, 2].includes(columnIndex)) {
                        return {
                            colspan: 1,
                            rowspan: row.evalStardardMethods.length + 1
                        };
                    } else if ([3, 4].includes(columnIndex)) {
                        return [1, 1];
                    } else {
                        return [1, 0];
                    }
                } else {
                    if ([0, 1, 2].includes(columnIndex)) {
                        return {
                            colspan: 1,
                            rowspan: 0
                        };
                    } else {
                        return [1, 1];
                    }
                }
            },
            //计算总分数
            getSummaries(params) {
                const {columns, data} = params;
                var sums = [];
                columns.forEach((column, index) => {
                    if (index === 0) {
                        sums[index] = '总计';
                        return;
                    } else if (index === 1) {
                        sums[index] = data.reduce((a, b) => {
                            if (b.level === 1) {
                                return a + Number(b.score);
                            } else {
                                return a;
                            }
                        }, 0)
                        sums[index] += '分';
                    } else {
                        sums[index] = '';
                    }
                })
                return sums;
            },
            showDia(row) {
                if (!row) {
                    this.list = [];
                    this.tableList = [];
                    this.form = this.$options.data().form;
                } else {
                    getEntity({id: row.id}).then(res => {
                        let data = JSON.parse(JSON.stringify(res.data));
                        if (data.orgType == 1) {
                            this.levelList = this.aList;
                        } else if (data.orgType == 2) {
                            this.levelList = this.bList;
                        } else {
                            this.levelList = []
                        }
                        this.form = data;
                        this.list = data.evalDesignCategorys;
                        this.tableList = this.flatData(this.list);
						this.$nextTick(()=>{
							this.$refs.orderTable.doLayout();
						})
                        if (row.isCopy == 0) {
                            this.getFlag()
                        } else { 
                            this.form.id=''
                        }
                    })

                }
                this.dialog = true
				this.$nextTick(()=>{
					this.$refs.orderForm.clearValidate();
				})
                this.loading = false
            },
            close() {
                this.dialog = false
            },
            //检查table信息
            checkTable() {
                const list = this.tableList;
                if (list.length === 0) {
                    this.$baseMessage(`请新增考核单内容`, 'error');
                    this.loading = false
                    return false
                }
                return list.every(el => {
                    if (el.level === 1) {
                        if (!el.name) {
                            this.$baseMessage(`请输入评价项目名称`, 'error');
                            this.loading = false
                            return false
                        }
                        if (el.evalCategoryStardards.length === 0) {
                            this.$baseMessage(`请至少增加一条指标`, 'error');
                            this.loading = false
                            return false
                        }
                    }
                    if (el.level === 2) {
                        if (!el.title) {
                            this.$baseMessage(`请输入指标名称`, 'error');
                            this.loading = false
                            return false
                        }
                        if (!el.content) {
                            this.$baseMessage(`请输入评价内容`, 'error');
                            this.loading = false
                            return false
                        }
                        if (el.evalStardardMethods.length === 0) {
                            this.$baseMessage(`请至少增加一条评分办法`, 'error');
                            this.loading = false
                            return false
                        }
                    }
                    return true
                })
            },
            //保存信息
            saveInfo() {
                this.$refs.orderForm.validate((valid) => {
                    this.loading = true
                    if (valid) {
                        if (this.checkTable()) {
                            //组装参数
                            let data = JSON.parse(JSON.stringify(this.form));
                            data.evalDesignCategorys = JSON.parse(JSON.stringify(this.list));
                            //请求新增或者编辑
                            //api
                            saveOrUploadEval(data).then(res => {
                                if (res.code == 0) {
                                    this.$baseMessage(res.msg, 'success');
                                    this.$emit('fetch-data')
                                    this.dialog = false
                                    setTimeout(() => { 
                this.loading = false
            },500) 
                                } else {
                                    this.$baseMessage(res.msg, 'error');
                                    this.loading = false
                                }
                            })
                        }
                    } else {
                        this.loading = false
                    }
                })
            },
            //新增计分方式
            addScoreType() {
                this.methodsForm.evalMethodInfos.push({
                    score: '',
                    startRate: '',
                    endRate: ''
                });
            },
            //删除计分方式
            delScoreType(index) {
                this.methodsForm.evalMethodInfos.splice(index, 1);
            },
            //上传模版成功回调
            uploadSuccess(res) {
                //this.methodsForm.fileId = res.id;
            }
        },
    }
</script>
<style lang="scss" scoped>
    .scoreDiyContainer {
        margin-bottom: 10px;
    }

    ::v-deep {
        .el-dialog__body {
            border-top: 0;
        }

        .scoreDiyContainer {
            .el-input {
                width: 50px;
                margin-right: 4px;
            }
        }
    }
</style>