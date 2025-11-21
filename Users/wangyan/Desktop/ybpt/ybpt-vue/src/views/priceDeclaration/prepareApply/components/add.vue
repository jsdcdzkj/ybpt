<template>
  <el-drawer ref="drawer" :before-close="handleClose" :title="title" :visible.sync="dialog" :with-header="false"
             append-to-body
             :close-on-click-modal="false"
             custom-class="box_drawer" direction="rtl" size="70%">
    <div class="drawer_content">
      <el-form :label-width="formLabelWidth" :model="form">
        <div class="drawer_main">
          <h5 class="inform-title">{{ form.title }}</h5>
          <div class="box_card base-info">
            <el-row :gutter="20">
              <el-col :lg="12" :md="12" :sm="12" :xl="12" :xs="12">
                <el-form-item label="单位名称:" prop="orgName">
                  {{ form.orgName }}
                </el-form-item>
              </el-col>
              <el-col :lg="12" :md="12" :sm="12" :xl="12" :xs="12">
                <el-form-item label="单位医保编码:" prop="orgCode">
                  {{ form.orgCode }}
                </el-form-item>
              </el-col>

            </el-row>
            <div class="right-add-btn">
              <el-button class="right" icon="el-icon-plus" type="success" @click="handleAdd">
                新增
              </el-button>
            </div>
            <el-table ref="listTable" :data="tableData"
                      :element-loading-text="elementLoadingText" border highlight-current-row
            >
              <el-table-column align="center" label="序号" show-overflow-tooltip type="index" width="50px">
              </el-table-column>
<!--              nationalFormulaCode: "11111",//国家医疗机构制剂代码-->
<!--              isInCategory: "0",//是否在医保制剂目录         1 是 0 否-->
<!--              formulaName: "2222",//制剂名称-->
<!--              categoryCode: "123",//分类编码-->
<!--              genericNameCode: "123",//药品通用名编码-->
<!--              productNameCode: "123",//产品名编码-->
<!--              goodsName: "123",//商品名-->
<!--              payType: "1",//支付类别-->
<!--              approvalNo: "1",//批准文号-->
<!--              registerCompanyName: "1",//制剂注册单位-->
<!--              dosageForm: "1",//剂型-->
<!--              specs: "1",//规格-->
<!--              minPriceUnit: "1",//最小计价单位-->
<!--              minPackage: "1",//最小包装-->
<!--              unit: "1",//单位-->
<!--              catalogCode: "1",//目录编号-->
<!--              selfPayRatio: "1",//个人先行自付比例-->
<!--              remark: "1",//备注        (元)-->
<!--              price: "1",//价格        (元)-->
<!--              localPmPrice: "1",//本市同级公立医疗机构价格-->
              <el-table-column align="center" label="国家医疗机构制剂代码" min-width="200" prop="nationalFormulaCode" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" label="制剂名称" min-width="200" prop="formulaName" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" label="批准文号" min-width="200" prop="approvalNo" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" label="制剂注册单位" min-width="200" prop="registerCompanyName" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" label="剂型" min-width="200" prop="dosageForm" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" label="规格" min-width="200" prop="specs" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" label="单位" min-width="200" prop="unit" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" label="价格(元)" min-width="200" prop="price" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" label="本市同级公立医疗机构价格" min-width="200" prop="localPmPrice" show-overflow-tooltip></el-table-column>
              <el-table-column align="center" label="是否在医保制剂目录" min-width="200" prop="isInCategory" show-overflow-tooltip>
                <template slot-scope="{$index,row}">
                  {{{1:'是',0:'否'}[row.isInCategory]}}
                </template>
              </el-table-column>
              <el-table-column align="center" fixed="right" label="测算明细" prop="aaa3" show-overflow-tooltip width="90">
                <template slot-scope="scope">
                  <el-button v-if="scope.row.detail_status==1" icon="el-icon-document" plain size="mini" type="success"
                             @click="handleDefinite(scope.row,scope.$index)"></el-button>
                  <el-button v-if="scope.row.detail_status==0" plain size="mini" type="warning" @click="handleDefinite(scope.row,scope.$index)">未填写
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column align="center" fixed="right" label="附件" prop="aaa3" show-overflow-tooltip width="90">
                <template slot-scope="scope">

                  <el-button size="mini" type="text" @click="handlePhoto(scope.row)">{{scope.row.fileInfoList?scope.row.fileInfoList.length:0}}</el-button>

                </template>
              </el-table-column>
              <el-table-column align="center" fixed="right" label="操作" show-overflow-tooltip width="180">
                <template slot-scope="scope">
                  <div class="button-con">
<!--                    <el-upload-->
<!--                        style="margin-right: 12px"-->
<!--                        action=""-->
<!--                        :auto-upload="false"-->
<!--                        :show-file-list="false"-->
<!--                        :multiple="false"-->
<!--                        :on-change="(file, fileList) => {fileChange(file, fileList, scope.row)}"-->
<!--                    >-->
                      <el-button icon="el-icon-upload" plain size="mini" type="primary" @click="handlePhoto(scope.row)"></el-button>
<!--                    </el-upload>-->
                    <el-button icon="el-icon-edit" plain size="mini" type="success" @click="handleAdd(scope.row,scope.$index,'edit')"></el-button>
                    <el-button icon="el-icon-delete" plain size="mini" type="danger"
                               @click="handleDelete(scope.$index, tableData)"></el-button>
                  </div>
                </template>
              </el-table-column>


            </el-table>
          </div>
          <div class="explain-text">
<!--            <p>注：</p>-->
<!--            <p>1.申报类型：市场调节价项目、新增医疗服务项目、市管未定价项目、其他病房床位、单人间、套房床位。</p>-->
<!--            <p>2.项目名称、项目编码、计价单位、价格、费用类型：根据《江苏省基本医疗保险诊疗项目和医疗服务设施范围及支付标准》填写。</p>-->
<!--            <p>3.本表一式两份。</p>-->
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button size="small" type="warning" @click="downloadTemplate">告知模板下载</el-button>
        <el-button @click="cancelForm">取 消</el-button>
        <el-button type="primary" @click="save" :loading="loading">
          {{ loading ? '提交中 ...' : '提 交' }}
        </el-button>
      </div>
    </div>
    <examine ref="examine" @fetch-data="fetchData"></examine>
    <definite ref="definite" @fetch-data="fetchData"></definite>
    <Photo ref="photo" @fetch-data="fetchData"></Photo>
  </el-drawer>
</template>

<script>
import Examine from './examine'
import Definite from './definite'
import {insertGovernmentMedical} from "@/api/sbApply";
import Photo from './uploadPhoto'

import {
  handleSave,
  uploadDetailFile,
  download,
  applyPage,
  handleUpdate,
} from '@/api/prepareApply'
export default {
  name: 'infoAdd',
  components: {
    Examine,
    Definite,
    Photo,
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
      form: {},
      formLabelWidth: '100px',
      timer: null,
      tableData: []
    }
  },
  mounted() {
  },
  methods: {
    showDia(row) {
      this.tableData = []
      this.form = Object.assign({}, row)
    //console.log(row);
      this.dialog = true
    },
    fetchData(row){
      console.log('add-fetchdata',row);
      if(row.sign == 'edit'){
        this.tableData.splice(row.index, 1,Object.assign({}, row));
      }
      if(row.sign == 'add'){
        this.tableData.push(Object.assign({}, row))
      }
      if(row.sign == 'detail'){
        this.tableData.splice(row.index, 1,Object.assign({}, row));
      }
      if(row.sign == 'photo'){
        this.tableData.splice(row.index, 1,Object.assign({}, row));
      }
      console.log('只有月亮经过',this.tableData[0]);
    },
    async handleDelete(index, rows) {
      let fd = new FormData();
      if(rows[index].fileInfoId){
        fd.append("fileInfoId", rows[index].fileInfoId);
        await uploadDetailFile(fd)
      }
      rows.splice(index, 1);
    },
    async fileChange(file, fileList, row) {
      let fd = new FormData();
      //替换
      if(row.fileInfoId){
        fd.append("fileInfoId", row.fileInfoId);
      }
      row.file = file.name;
      fd.append("file", file.raw);
      var result = await uploadDetailFile(fd);
      if (result.data.code == 0) {
        row.fileInfoId = result.data.data.id
        this.tableData.splice(row.index, 1,Object.assign({}, row));
        this.$baseMessage("上传成功", 'success')
      } else {
        this.$baseMessage(result.data.msg, 'error')
      }
    },
    handleAdd(row,index,type) {
      if('edit' != type){
        if(this.tableData.length > 0){
          this.$baseMessage('告知申请只可一次申请一条', 'error')
          return
        }
      }

      // row.aggrement_lv = this.form.aggrement_lv
      // row.medical_type = this.form.medical_type
      row.actionType = type;
      row.index = index
    //console.log(row)
      this.$refs['examine'].showEdit(row)
    },
    handleDefinite(row,index) {
      row.index = index
      row.orgCode = this.form.orgCode
      row.orgName = this.form.orgName
    //console.log(row)
      this.$refs['definite'].showDia(row)
    },
    async save() {

    //console.log(this.tableData);
      if(this.tableData.length <= 0){
        this.$baseMessage('请至少保留一条数据！', 'error')
        return
      }
      let sign = false;
      this.tableData.forEach(x => {
        if (x.detail_status == 0) {
          sign = true
        }
      })
      if (sign) {
        this.$baseMessage('明细未填写,请完善信息！', 'error')
        return
      }
      this.form = this.tableData[0];
      console.log('提交前confirm',this.form);
      const loading = this.$loading({
        lock: true,
        text: '提交中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      const res = await handleSave(this.form).catch(()=>{
        this.loading = false;
      })
      this.timer = setTimeout(() => {
        loading.close();
        if (res.code == 0) {
          this.loading = false
          this.dialog = false
          this.$emit('fetch-data')
          this.$baseMessage('操作成功', 'success')
        }else{
        //console.log('错了错了错了错了从');
          loading.close();
          this.$baseMessage(res.msg, 'error')
        }
      }, 2000)
    },
    handleClose(done) {
      if (this.loading) {
        return
      }
      let sign = false;
      this.tableData.forEach(x => {
        if (x.detail_status == 0) {
          sign = true
        }
      })
      if (sign) {
        this.$baseMessage('明细未填写,请完善信息！', 'error')
        return
      }
      // this.$confirm('点击确认将提交到医保审核，是否执行提交操作？')
      //     .then((_) => {
      //       this.save()
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
    handlePhoto(row) {
      this.$refs['photo'].showEdit(row)
    },
    downloadTemplate() {
      download().then(res => {
        let fileName = '医疗机构制剂价格告知报告.docx';
        let objectUrl = URL.createObjectURL(new Blob([res.data]));
        const link = document.createElement('a');
        link.download = decodeURI(fileName);
        link.href = objectUrl;
        link.click();
        this.listLoading = false;
        this.$baseMessage("下载成功！", 'success')
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
