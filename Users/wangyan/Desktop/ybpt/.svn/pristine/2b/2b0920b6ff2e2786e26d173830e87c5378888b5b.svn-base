<template>
  <el-drawer :visible.sync="dialog" direction="rtl" :with-header="false" custom-class="box_drawer" size="80%"
             ref="drawer">
    <div class="drawer_content">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <div class="box_card">
            <div class="box_title">
              <span>{{ form.year }}{{ form.orgName }}</span>
              <div>
                机构类型:{{ form.orgType == 1 ? '医疗机构' : '零售药店' }}
                协议等级:<span v-if="form.aggrementLv==1">一级</span>
                <span v-else-if="form.aggrementLv==2">二级</span>
                <span v-else-if="form.aggrementLv==3">三级</span>
                <span v-else-if="form.aggrementLv==4">A级</span>
                <span v-else-if="form.aggrementLv==5">B级</span>
                <span v-else-if="form.aggrementLv==6">C级</span>
                <span v-else-if="form.aggrementLv==9">未定级</span>
                年度: {{ form.year }}
              </div>
            </div>
            <div class="box_content">
              <el-table :data="tableList" border style="margin-top: 20px;" height="calc(100vh - 240px)"
                        :span-method="arraySpanMethod" show-summary :summary-method="getSummaries" ref="orderTable">
                <el-table-column label="评价项目" min-width="200px">
                  <template #default="{ row }">
                    <span v-if="row.level==1">{{ row.name }}</span>
                    <span style="margin-left: 20px;width: calc(100% - 20px);"
                          v-else>{{ row.title }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="分值" align="center"
                                 width="80px">
                  <template #default="{ row }">
                    <span v-if="row.level==1">{{ row.score }}</span>
                    <span style="margin-left: 20px;width: calc(100% - 20px);"
                          v-else>{{ row.categoryScore }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="content" label="评价内容" min-width="200px"
                                 align=""></el-table-column>
                <el-table-column label="评分办法" align="" width="500px">
                  <template #default="{ row }">
                    <span v-if="row.level!==1">{{ row.name }}({{ row.methodsScore }}分)</span>
                  </template>
                </el-table-column>
                <el-table-column label="模版下载" align="center"
                                 width="120px">
                  <template #default="{ row }">
                    <el-button type="primary" size="mini" icon="el-icon-download" v-if="row.level!==1 && null != row.fileId"
                               @click="downTemplate(row)">下载</el-button>
                  </template>
                </el-table-column>
                <el-table-column label="指标值" align="center"
                                 width="120px">
                  <template #default="{ row }">
                    <el-input v-if="row.level!==1&&row.indicator==1&&type === 0"
                              v-model="row.targetScore"></el-input>
                    <span v-if="type === 1">{{ row.targetScore }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="评分" align="center" width="200px">
                  <template #default="{ row }">
                    <div v-if="row.level!==1">
                      <el-input v-if="row.autoScore==1&&type === 0" type="number" min="0" width="200px"
                                v-model="row.evalOrgDetail.score"></el-input>
                      <span v-if="type === 1">{{ row.evalOrgDetail.score }}</span>
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="附件" align="center" width="200px">
                  <template #default="{ row }">
                    <div style="width:200px;overflow: hidden;">
                      <el-upload
                              v-if="row.level!==1&&row.isUpload==1&&type === 0"
                              class="upload-demo"
                              ref="upload"
                              action=""
                              :show-file-list="true"
                              :on-change="(file, fileList) => {fileChange(file, fileList, row)}"
                              :auto-upload="false"
                              :file-list="row.files"
                              :on-remove="(file, fileList) => {handleRemove(file, fileList, row)}"
                      >
                        <el-button type="primary">选择文件</el-button>
                      </el-upload>

                      <el-dropdown style="cursor: pointer;" @command="downloadFile($event,row.files)"
                                   v-if="row.files&&row.files.length">
					    <span class="el-dropdown-link">
					      下载文件<i class="el-icon-arrow-down el-icon--right"></i>
					    </span>
                        <el-dropdown-menu slot="dropdown">
                          <el-dropdown-item icon="el-icon-download" :command="index" v-for="item,index in row.files"
                                            :key="index">{{ item.name }}
                          </el-dropdown-item>
                        </el-dropdown-menu>
                      </el-dropdown>
                      <!-- <el-link type="primary" style="margin-right:10px" v-for="item in row.files" :key="item.id" @click.prevent="downloadFile(item)">{{ item.name }}</el-link> -->
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="描述" align="center" width="200px">
                  <template #default="{ row }">
                    <div v-if="row.level!==1">
                      <el-input v-if="type === 0&&row.isDescribe==1" type="textarea" maxlength="200" rows="4"
                                resize="none"
                                show-word-limit v-model="row.memo"></el-input>
                      <span v-if="type === 1">{{ row.memo }}</span>
                    </div>
                  </template>
                </el-table-column>
				<el-table-column label="反馈文件" align="center" width="200">
					<template #default="{ row }">
						<el-link type="primary" v-if="row.appealFiles&&row.appealFiles[0]" @click="downloadFile(0,row.appealFiles)">{{row.appealFiles[0].name}}</el-link>
					</template>
				</el-table-column>
				<el-table-column label="反馈描述" align="center" width="200" prop="appealCount"></el-table-column>
              </el-table>
            </div>
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
        <el-button v-if="type === 0" type="primary" @click="submitAck" :loading="loading">
          {{ loading ? '提交中 ...' : '提 交' }}
        </el-button>
        <el-button v-if="type === 0" type="success" @click="saveAck" :loading="loading">
          {{ loading ? '暂存中 ...' : '暂 存' }}
        </el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
  import {
    getEvalOrgTask, removeUploadEvalOrgDetail,
    setEvalOrgDetail, uploadEvalOrgDetail
  } from "@/api/eval";
  import {fileURL} from "@/config/setting.config";

  export default {
    name: 'edit',
    data() {
      return {
        fileMap: new Map,
        tableList: [],
        dialog: false,
        loading: false,
        title: '',
        type: 0,
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: '',
        },
        formLabelWidth: '100px'
      }
    },
    watch: {
      dialog(val) {
        if (!val) {
          this.tableList = [];
        }
      }
    },
    mounted() {
      this.tableList = this.flatData(this.tableList);
    },
    methods: {
      // 上传开始
      async fileChange(file, fileList, row) {
        const that = this
        let fd = new FormData();
        // fd.append("evalStardardMethodId", row.id);
        fd.append("year", this.form.year);
        fd.append("orgCode", this.form.orgCode);
        fileList.forEach(item => {
          //文件信息中raw才是真的文件
          fd.append("file", item.raw);
        })

        await uploadEvalOrgDetail(fd).then((res) => {
          console.log('file %o', res)
          if (res.data.code == 0) {
            file.id = res.data.data.id
            const fileIds = []
            fileList.forEach(item => {
              fileIds.push(item.id)
            })
            that.fileMap.set(row.id, fileIds)
            console.log('fileMap %o', that.fileMap)
            that.$baseMessage('上传成功', 'success')
          } else {
            that.$baseMessage('网络异常，请刷新重新！', 'error')
          }
        })
      },
      handleRemove(file, fileList, row) {
        const that = this
        removeUploadEvalOrgDetail({id: file.id}).then(res => {
          const fileIds = []
          fileList.forEach(item => {
            fileIds.push(item.id)
          })
          that.fileMap.set(row.id, fileIds)
          console.log('fileMap %o', that.fileMap)
          this.$baseMessage('删除成功', 'success')
        })
      },
      flatData(data) {
        var arr = [];
        data.map((el, index) => {
          console.log('项目 %o', el)
          const source = {
            indexs: [index],
            level: 1
          }
          arr.push(Object.assign(el, source));
          console.log('项目source %o', source)
          if (el.evalCategoryStardards) {
            el.evalCategoryStardards.map((item, idx) => {
              console.log('指标 %o', item)
              const evalStardardId = item.id;
              const source = {
                indexs: [index, idx],
                level: 2,
                evalStardardId,
                categoryScore: item.score == null ? '' : item.score,
                methodsScore: item.evalStardardMethods[0].score == null ? '' : item.evalStardardMethods[0].score,
                score: item.evalStardardMethods[0].evalOrgDetail == null ? '' : item.evalStardardMethods[0].evalOrgDetail.score,
                memo: item.evalStardardMethods[0].evalOrgDetail == null ? '' : item.evalStardardMethods[0].evalOrgDetail.memo,
                targetScore: item.evalStardardMethods[0].evalOrgDetail == null ? '' : item.evalStardardMethods[0].evalOrgDetail.targetScore,
                files: item.evalStardardMethods[0].evalOrgDetail == null || item.evalStardardMethods[0].evalOrgDetail.files == null ? [] : item.evalStardardMethods[0].evalOrgDetail.files,
				appealCount:item.evalStardardMethods[0].evalOrgDetail == null ? '':item.evalStardardMethods[0].evalOrgDetail.appealCount,
				appealFiles:item.evalStardardMethods[0].evalOrgDetail == null||!item.evalStardardMethods[0].evalOrgDetail.appealFiles ? []:item.evalStardardMethods[0].evalOrgDetail.appealFiles,
              }
              console.log('指标source %o', source)
              arr.push(Object.assign(item, source, item.evalStardardMethods.shift()));
              if (item.evalStardardMethods) {
                item.evalStardardMethods.map((itm, idxs) => {
                  console.log('办法 %o', itm)
                  const source = {
                    indexs: [index, idx, idxs],
                    evalStardardId,
                    level: 3,
					appealCount:itm.evalOrgDetail.appealCount||'',
					appealFiles:itm.evalOrgDetail.appealFiles||[],
                    methodsScore: itm.score == null ? '' : itm.score,
                    score: itm.evalOrgDetail == null ? '' : itm.evalOrgDetail.score,
                    memo: itm.evalOrgDetail == null ? '' : itm.evalOrgDetail.memo,
                    targetScore: itm.evalOrgDetail == null ? '' : itm.evalOrgDetail.targetScore,
                    files: itm.evalOrgDetail == null || itm.evalOrgDetail.files == null ? [] : itm.evalOrgDetail.files,
                  }
                  arr.push(Object.assign(itm, source));
                  console.log('办法source %o', source)
                })
              }
            })
          }
        })
        return arr;
      },
      // 合并单元格
      arraySpanMethod({
                        row,
                        column,
                        rowIndex,
                        columnIndex
                      }) {
        if (row.level === 1) {
          return [1, 1];
        }
        if (row.level === 2) {
          if ([0, 1, 2].includes(columnIndex)) {
            return {
              colspan: 1,
              rowspan: row.evalStardardMethods.length + 1
            };
          } else {
            return [1, 1];
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
      showDia(row, type) {
        this.type = type
        this.fileMap = new Map()
        if (type === 0) {
          this.title = '新增'
        } else if (type === 1) {
          this.title = '详情'
        }
        this.form = Object.assign({}, row)
        this.dialog = true
        this.getDetail()
      },
      async getDetail() {
        await getEvalOrgTask({
          taskManageId: this.form.taskManageId,
          evalOrgTaskId: this.form.id,
          designId: this.form.designId
        }).then(res => {
          const data = JSON.parse(JSON.stringify(res.data));
          this.tableList = this.flatData(data.evalDesign.evalDesignCategorys);
		  this.$nextTick(()=>{
		  	this.$refs.orderTable.doLayout();
		  })
        })
      },
      //提交信息
      submitAck() {
        var that = this;
        that.$confirm('确定要提交表单吗？')
                .then((_) => {
                  that.saveInfo(1)//提交
                })
      },
      saveAck() {
        var that = this;
        that.$confirm('确定要暂存表单吗？')
                .then((_) => {
                  that.saveInfo(-1)//暂存
                })
      },
      //提交信息
      saveInfo(status) {
        var that = this;
        let list = JSON.parse(JSON.stringify(that.tableList));
        var arr = [];
        list.map(el => {
          if (el.level != 1) {
            arr.push({
              status: status,
              evalOrgTaskId: that.form.id,
              fileIds: that.fileMap.get(el.id),
              evalStardardId: el.evalStardardId,
              evalStardardMethodId: el.id,
              score: el.evalOrgDetail.score,
              memo: el.memo,
              targetScore: el.targetScore,
              fileInfoList: el.fileInfoList
            })
          }
        })

        //全屏遮罩
        const loading = this.$loading({
          lock: true,
          text: status == 1 ? '提交' : '暂存' + '中，请稍等...',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        setEvalOrgDetail({
          evalOrgDetailList: arr,
          evalOrgTaskId: this.form.id,
          status: status
        }).then(res => {
          that.$emit('fetch-data')
          that.cancelForm()
          that.$baseMessage("操作成功", 'success')

          setTimeout(() => {
            loading.close();
          }, 1000);
        });
        console.log(arr);
      },
      downTemplate(row) {
        const templateUrl = row.fileTemplate.fileUrl
        window.open(fileURL + templateUrl + '?n=' + templateUrl.substring(templateUrl.lastIndexOf('/'), templateUrl.lastIndexOf('.')) + '&download=1', "_blank");
        // self.location.href = fileURL + templateUrl + '?n=' + templateUrl.substring(templateUrl.lastIndexOf('/'), templateUrl.lastIndexOf('.')) + '&download=1';
      },
      //下载文件
      downloadFile(index, row) {
        window.open(fileURL + row[index].url, "_blank")
        // self.location.href = fileURL + row[index].url
      },
      cancelForm() {
        this.loading = false
        this.dialog = false
        this.$refs.upload.clearFiles()
        clearTimeout(this.timer)
      }
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .el-dialog__body {
      border-top: 0;
    }
  }

  .box_title {
    font-size: 18px;
    text-align: center;
    font-weight: bold;
  }
</style>