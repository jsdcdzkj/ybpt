<template>
  <el-drawer ref="drawer" :before-close="handleClose" :title="title" :visible.sync="dialog" :with-header="false"
             append-to-body
             :close-on-click-modal="false"
             custom-class="box_drawer" direction="rtl" size="70%">
    <div class="drawer_content">
      <el-form :label-width="formLabelWidth" ref="form" :model="form" :rules="rules">
        <div class="drawer_main">
          <h5 class="inform-title">阳光采购药品（耗材）</h5>
          <div class="box_card base-info">
            <el-row :gutter="20">
              <el-col :lg="8" :md="8" :sm="8" :xl="8" :xs="8">
                <el-form-item label="医药机构名称:" prop="org_name">
                  {{ form.org_name }}
                </el-form-item>
              </el-col>
              <el-col :lg="8" :md="8" :sm="8" :xl="8" :xs="8">
                <el-form-item label="医药机构省平台编码:" prop="org_code" class="custemitem">
                  {{ form.org_code }}
                </el-form-item>
              </el-col>
              <el-col :lg="8" :md="8" :sm="8" :xl="8" :xs="8">
                <el-form-item label="缺货分类:" prop="stockout_type">
                  {{ form.stockout_type == '1' ? '药品' : '耗材' }}
                </el-form-item>
              </el-col>

            </el-row>
            <el-row :gutter="20" v-if="isShow == false">
              <el-col :lg="8" :md="8" :sm="8" :xl="8" :xs="8">
                &nbsp;
              </el-col>
              <el-col :lg="8" :md="8" :sm="8" :xl="8" :xs="8">
                &nbsp;
              </el-col>
              <el-col :lg="8" :md="8" :sm="8" :xl="8" :xs="8">
                <el-form-item label="线索类型:" prop="clues">
                  {{ form.clues }}
                </el-form-item>
              </el-col>
            </el-row>
            <div class="right-add-btn" v-if="isShow == true">

              <div class="reason-box">
                <el-form-item label="线索类型:" prop="clues" class="w">
                  <el-select v-model="form.clues" style="width: 100%">
                    <el-option v-for="(item, index) in options" :key="index" :label="item.label"
                               :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </div>
              <div>
                <el-button class="right" icon="el-icon-plus" type="success" @click="handleImport">
                  导入
                </el-button>
                <el-button class="right" icon="el-icon-plus" type="success" @click="handleAdd">
                  新增
                </el-button>
              </div>


            </div>
            <el-table ref="listTable" :data="tableData"
                      :element-loading-text="elementLoadingText" border highlight-current-row
            >
              <el-table-column align="center" label="序号" show-overflow-tooltip type="index" width="50px">
              </el-table-column>
              <el-table-column align="center" label="药品(耗材)名称" min-width="200" prop="name"
                               show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="规格" prop="unit" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="省阳光采购平台产品编码" min-width="200" prop="code"
                               show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="缺货开始采购日期" prop="start_date" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="缺货后最近一次采购订单日期" prop="end_date" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="缺货订单提交次数" prop="sub_quantity" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="数量" prop="quantity" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="金额" prop="price" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="生产企业" prop="enterprise" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="配送公司" prop="company" show-overflow-tooltip>
              </el-table-column>
<!--              <el-table-column align="center" label="线索类型" prop="note" show-overflow-tooltip>-->
<!--              </el-table-column>-->
              <el-table-column align="center" fixed="right" label="省平台采购配送截图" prop="file" min-width="200"
                               show-overflow-tooltip v-if="isShow == true">
                <template slot-scope="scope">
                  {{ scope.row.fileName }}
                </template>
              </el-table-column>
              <el-table-column align="center" fixed="right" label="省平台采购配送截图" prop="file" min-width="200"
                               show-overflow-tooltip v-if="isShow == false">
                <template slot-scope="scope">
                  <el-button class="right" type="success" @click="handleFile(scope.row)">
                    预览
                  </el-button>
                </template>
              </el-table-column>

              <el-table-column align="center" fixed="right" label="操作" show-overflow-tooltip width="180"
                               v-if="isShow">
                <template slot-scope="scope">
                  <div class="button-con">
                    <el-upload
                        action="#"
                        :ref="'upload-' + scope.$index"
                        style="margin-right: 12px"
                        accept=".jpg, .png, .JPG, .PNG"
                        :auto-upload="false"
                        :show-file-list="false"
                        :multiple="true"
                        :on-success="handleSuccess(scope.$index, scope.row)"
                        :on-change="(file, fileList) => {fileChange(file, fileList, scope.$index, scope.row)}"
                    >
                      <el-button icon="el-icon-upload" plain size="mini" type="primary"></el-button>
                    </el-upload>
                    <el-button icon="el-icon-edit" plain size="mini" type="success"
                               @click="handleAdd(scope.row,scope.$index)"></el-button>
                    <el-button icon="el-icon-delete" plain size="mini" type="danger"
                               @click="handleDelete(scope.$index, tableData)"></el-button>
                  </div>
                </template>
              </el-table-column>


            </el-table>
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">取 消</el-button>
        <el-button type="primary" @click="save" :loading="loading" v-if="isShow">
          {{ loading ? '提交中 ...' : '提 交' }}
        </el-button>
      </div>
    </div>
    <editDetail ref="editDetail" @fetch-data="fetchData"></editDetail>
    <importExcel ref="importExcel" @fetch-data="fetchData"></importExcel>
    <viewFile ref="viewFile" @fetch-data="fetchData"></viewFile>
  </el-drawer>
</template>

<script>
import EditDetail from './editDetail.vue'
import ImportExcel from './importExcel.vue'
import ViewFile from './viewFile.vue'
import {getFile, insertOrUpdate} from "@/api/purStockout";
import {fileURL} from "@/config/setting.config";

export default {
  name: 'infoAdd',
  components: {
    EditDetail,
    ImportExcel,
    ViewFile,
  },
  data() {
    return {
      options:[
        {label: "无正当理由，不响应采购订单", value:"无正当理由，不响应采购订单"},
        {label: "以高于挂网价线下供应或配送", value:"以高于挂网价线下供应或配送"},
        {label: "不接受网上议价，但实际供货价低于挂网价", value:"不接受网上议价，但实际供货价低于挂网价"},
        {label: "同一企业以过评后价格销售过评前同一产品", value:"同一企业以过评后价格销售过评前同一产品"},
        {label: "未按规定签订带量采购合同", value:"未按规定签订带量采购合同"},
        {label: "未按合同约定及时供应产品", value:"未按合同约定及时供应产品"},
        {label: "不按中选清单供应产品", value:"不按中选清单供应产品"},
        {label: "未按带量采购合同约定提供伴随服务", value:"未按带量采购合同约定提供伴随服务"},
      ],
      fileListTemp: [],//临时数据
      isShow: true,
      elementLoadingText: '正在加载...',
      title: '',
      fileMap: new Map(),
      dialog: false,
      loading: false,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
      form: {
        org_name: '',
        org_code: '',
        stockout_type: '',
        fix_blng_admdvs: '',
        clues: '',
      },
      rules: {
        clues: [
          { required: true, trigger: 'blur', message: '请选择线索类型' },
        ],
      },
      formLabelWidth: '100px',
      timer: null,
      tableData: []
    }
  },
  mounted() {
  },
  methods: {
    showEdit(row) {
      const that = this;
      that.tableData = []
      if (row.sign == 'add') {
        that.title = '新增'
        that.isShow = true
      }
      if (row.sign == 'detail') {
        that.title = '详情'
        for (let i = 0; i < row.purStockoutDetail.length; i++) {
          row.purStockoutDetail[i].fileUrl = fileURL + row.purStockoutDetail[i].fileUrl + '?n=' + row.purStockoutDetail[i].fileName + '&download=0'
          that.tableData.push(Object.assign({}, row.purStockoutDetail[i]))
        }
        that.isShow = false
      }
      that.form = Object.assign({}, row)
      that.dialog = true
    },
    fetchData(row) {
      const that = this;
      if (row.sign == 'edit') {
        that.tableData.splice(row.index, 1, Object.assign({}, row));
      }
      if (row.sign == 'add') {
        that.tableData.push(Object.assign({}, row))
      }
      if (row.sign == 'upload') {
        that.tableData = []
        let signReg = false
        let signName = false
        let signUnit = false
        let signCode = false
        let signSubQuantity = false
        let signQuantity = false
        let signPrice = false
        let signEnterprise = false
        let signCompany = false
        row.arr.forEach(function (item, index) {
          const reg = /^(\d{1,4})(-|\/)(\d{1,2})\2(\d{1,2})/
          if (item.start_date == undefined || item.start_date == null || item.start_date == '' || reg.test(item.start_date) == false ||
              item.end_date == undefined || item.end_date == null || item.end_date == '' || reg.test(item.end_date) == false) {
            signReg = true
          }
          if (item.name == undefined || item.name == null || item.name == '') {
            signName = true
          }
          if (item.unit == undefined || item.unit == null || item.unit == '') {
            signUnit = true
          }
          if (item.code == undefined || item.code == null || item.code == '') {
            signCode = true
          }
          if (item.sub_quantity == undefined || item.sub_quantity == null || item.sub_quantity == '') {
            signSubQuantity = true
          }
          if (item.quantity == undefined || item.quantity == null || item.quantity == '') {
            signQuantity = true
          }
          if (item.price == undefined || item.price == null || item.price == '') {
            signPrice = true
          }
          if (item.enterprise == undefined || item.enterprise == null || item.enterprise == '') {
            signEnterprise = true
          }
          if (item.company == undefined || item.company == null || item.company == '') {
            signCompany = true
          }
          that.tableData.push(Object.assign({}, item))
        });
        if (signReg) {
          that.$baseMessage('附件上传日期格式错误,正确格式为【yyyy-MM-dd】请修改模版后重新上传！', 'error')
          that.tableData = []
          return
        }
        if (signName) {
          that.$baseMessage('【药品(耗材)名称】必填项，请修改模版后重新上传！', 'error')
          that.tableData = []
          return
        }
        if (signUnit) {
          that.$baseMessage('【规格】必填项，请修改模版后重新上传！', 'error')
          that.tableData = []
          return
        }
        if (signCode) {
          that.$baseMessage('【省阳光采购平台产品编码】必填项，请修改模版后重新上传！', 'error')
          that.tableData = []
          return
        }
        if (signSubQuantity) {
          that.$baseMessage('【缺货订单提交次数】必填项，请修改模版后重新上传！', 'error')
          that.tableData = []
          return
        }
        if (signQuantity) {
          that.$baseMessage('【数量】必填项，请修改模版后重新上传！', 'error')
          that.tableData = []
          return
        }
        if (signPrice) {
          that.$baseMessage('【金额】必填项，请修改模版后重新上传！', 'error')
          that.tableData = []
          return
        }
        if (signEnterprise) {
          that.$baseMessage('【生产企业】必填项，请修改模版后重新上传！', 'error')
          that.tableData = []
          return
        }
        if (signCompany) {
          that.$baseMessage('【配送公司】必填项，请修改模版后重新上传！', 'error')
          that.tableData = []
          return
        }


        console.log(that.tableData)
      }
      console.log(row)
    },
    async handleFile(row) {
      await getFile(row.id).then((res) => {
        this.$refs['viewFile'].showEdit(res.data)
      })
    },
    // async fileChange(file, fileList, index, row) {
    //   let fd = new FormData();
    //   if (row.fileInfoId) {
    //     for (let i = 0; i < row.fileInfoId.length; i++) {
    //       fd.append("fileInfoId", row.fileInfoId[i]);
    //     }
    //   }
    //   for (let i = 0; i < fileList.length; i++) {
    //     fd.append("file", fileList[i].raw);
    //   }
    //
    //   const result = await uploadFile(fd);
    //   if (result.data.code == 0) {
    //     row.fileInfoId = result.data.data.fileId
    //     row.file = result.data.data.fileName.join(",")
    //     this.tableData.splice(index, 1, Object.assign({}, row));
    //   }
    //   this.fileList = []
    // },
    async fileChange(file, fileList, index, row) {
      // 获取所有已选择的文件
      const files = fileList.map(file => file.raw);
      this.fileMap.set(index, files)
      // row.files = files
      const fileNames = fileList.map(file => file.raw.name);
      row.fileName = fileNames.join(",")
      this.tableData.splice(index, 1, Object.assign({}, row));
    },
    handleSuccess(index, row, response, file, fileList) {
      if (row.fileName) {
        console.log(row.fileName)
        this.$refs['upload-' + index].clearFiles();
      }
    },
    beforeUpload(file) {
      console.log(123)
    },
    async handleDelete(index, rows) {
      rows.splice(index, 1);
    },
    handleImport() {
      this.$refs['importExcel'].showEdit()
    },
    handleAdd(row, index) {
      row.index = index
      console.log(row)
      this.$refs['editDetail'].showEdit(row)
    },
    async save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          if (this.tableData.length <= 0) {
            this.$baseMessage('请至少保留一条数据！', 'error')
            return
          }
          let sign = false

          this.tableData.forEach(x => {
            if (x.fileName == undefined || x.fileName == null || x.fileName == '') {
              sign = true
            }
          })
          if (sign) {
            this.$baseMessage('省平台采购配送截图未上传,请完善信息！', 'error')
            return
          }

          this.form.purStockoutDetail = this.tableData
          const loading = this.$loading({
            lock: true,
            text: '提交中，请稍等...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          });
          let fd = new FormData();
          // fd.append("purStockout",this.form)
          let applicationData = JSON.stringify(this.form)
          fd.append("purStockout", new Blob([applicationData], {type: 'application/json'}));
          // fd.append("files",this.fileMap)
          //文件
          for (let [key] of this.fileMap) {
            for (let i = 0; i < this.fileMap.get(key).length; i++) {
              if (!this.fileMap.get(key)[i].id) {
                fd.append('files', this.fileMap.get(key)[i], key + '&' + this.fileMap.get(key)[i].name)
              }
            }
          }
          await insertOrUpdate(fd)
          this.timer = setTimeout(async () => {
            loading.close();
            this.loading = false
            this.dialog = false
            this.$emit('fetch-data')
            this.$baseMessage('操作成功', 'success')
          }, 2000)
        }
      })

    },
    handleClose(done) {
      if (this.loading) {
        return
      }
    },
    cancelForm() {
      if (this.loading) {
        return
      }
      if (this.isShow == false) {
        this.loading = false
        this.dialog = false
      } else {
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

      }

    },
  },
}
</script>
<style lang="scss" scoped>
.button-con {
  display: flex;
}

.mb2 {
  margin-bottom: 20px;
}

.right-add-btn {
  width: 100%;
  display: flex;
  margin-bottom: 10px;
  justify-content: flex-end;
  .reason-box{
    width: 400px;
    margin-right: 20px;
  }
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