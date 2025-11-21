<template>
  <el-drawer ref="drawer" :before-close="handleClose" :title="title" :visible.sync="dialog" :with-header="false"
             append-to-body
             :close-on-click-modal="false"
             custom-class="box_drawer" direction="rtl" size="70%">
    <div class="drawer_content">
      <el-form :label-width="formLabelWidth" :model="form">
        <div class="drawer_main">
          <h5 class="inform-title">非公立医疗机构药品（医用耗材）自主定价明细表</h5>
          <div class="box_card base-info">
            <el-row :gutter="20">
              <el-col :lg="8" :md="8" :sm="8" :xl="8" :xs="8">
                <el-form-item label="单位名称:" prop="org_name">
                  {{ form.org_name }}
                </el-form-item>
              </el-col>
              <el-col :lg="8" :md="8" :sm="8" :xl="8" :xs="8">
                <el-form-item label="单位医保编码:" prop="org_code">
                  {{ form.org_code }}
                </el-form-item>
              </el-col>
              <el-col :lg="8" :md="8" :sm="8" :xl="8" :xs="8">
                <el-form-item label="经营性质:" prop="natures">
                  {{ form.natures }}
                </el-form-item>
              </el-col>

            </el-row>
            <div class="right-add-btn">
              <el-button class="right" icon="el-icon-plus" type="success" @click="handleImport">
                导入
              </el-button>
              <el-button class="right" icon="el-icon-plus" type="primary" @click="handleAdd">
                新增
              </el-button>
            </div>
            <el-table ref="listTable" :data="tableData"
                      :element-loading-text="elementLoadingText" border highlight-current-row
            >
              <el-table-column align="center" label="序号" show-overflow-tooltip type="index" width="50px">
              </el-table-column>
              <el-table-column align="center" label="编码" min-width="200" prop="project_code" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="名称" min-width="200" prop="project_name" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="单位" prop="unit" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="实际销售价格" prop="sale_price" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="实际采购价格" prop="purchase_price" show-overflow-tooltip>
              </el-table-column>
              <el-table-column align="center" label="加成率" prop="rate" show-overflow-tooltip width="110">
              </el-table-column>
              <el-table-column align="center" label="公立医疗机构价格" min-width="120" prop="org_price" show-overflow-tooltip>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="is_purchase" label="是否在省平台上采购" width="170" align="center">
                <template #default="scope">
                  <el-tag type="danger" v-show="scope.row.is_purchase == 0">否</el-tag>
                  <el-tag v-show="scope.row.is_purchase == 1" type="success">是</el-tag>
                </template>
              </el-table-column>
              <el-table-column align="center" label="备注" min-width="120" prop="remark" show-overflow-tooltip>
              </el-table-column>
              <!--              <el-table-column align="center" fixed="right" label="明细" prop="aaa3" show-overflow-tooltip width="90">-->
              <!--                <template slot-scope="scope">-->
              <!--                  <el-button v-if="scope.row.detail_status==1" icon="el-icon-document" plain size="mini" type="success"-->
              <!--                             @click="handleDefinite(scope.row,scope.$index)"></el-button>-->
              <!--                  <el-button v-if="scope.row.detail_status==0" plain size="mini" type="warning" @click="handleDefinite(scope.row,scope.$index)">未填写-->
              <!--                  </el-button>-->
              <!--                </template>-->
<!--              </el-table-column>-->
              <el-table-column align="center" fixed="right" label="操作" show-overflow-tooltip width="120">
                <template slot-scope="scope">
                  <el-button icon="el-icon-edit" plain size="mini" type="success" @click="handleAdd(scope.row,scope.$index)"></el-button>
                  <el-button icon="el-icon-delete" plain size="mini" type="danger"
                             @click="handleDelete(scope.$index, tableData)"></el-button>
                </template>
              </el-table-column>


            </el-table>
          </div>
          <div class="explain-text">
            <p>注：</p>
            <p>1.按药品、医用耗材分开填写；按加成率高于或不高于0分开填写。</p>
            <p>2.类型：填写药品、医用耗材。</p>
            <p>
              3.编码：药品根据《江苏省基本医疗保险、工伤保险和生育保险药品目录数据库》（包含《医保范围外药品数据库》）国家代码填写（中药饮片备注产品名编码）；耗材根据《江苏省医疗保险特殊医用材料目录》收费项目编码填写。</p>
            <p>4. 公立医疗机构价格：省阳光采购平台上采购的非公立医疗机构填写省平台挂网价格。</p>
            <p>5．经营性质：营利性或非营利性。</p>
            <p>6．本表一式两份</p>
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
    <examine3 ref="examine3" @fetch-data="fetchData"></examine3>
    <definite ref="definite" @fetch-data="fetchData"></definite>
    <importExcel ref="importExcel" @fetch-data="fetchData"></importExcel>
  </el-drawer>
</template>

<script>
import Examine3 from './examine3'
import Definite from './definite'
import ImportExcel from '@/components/importExcel'
import {insertCivilianMaterial} from "@/api/sbApply";
import {chinesePatentMedicineDrugCode, westernMedicineDrugCode} from "@/api/drug";


export default {
  name: 'infoAdd',
  components: {
    Examine3,
    Definite,
    ImportExcel,
  },
  data() {
    return {
      value3: '',
      elementLoadingText: '正在加载...',
      title: '',
      dialog: false,
      loading: false,
      drugCode:{
        sbCivilianMedical:[]
      },
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

      this.dialog = true
    },
    async fetchData(row){
      const that = this;
      if(row.sign == 'edit'){
        that.tableData.splice(row.index, 1,Object.assign({}, row));
      }
      if(row.sign == 'add'){
        that.tableData.push(Object.assign({}, row))
      }
      if(row.sign == 'detail'){
        that.tableData.splice(row.index, 1,Object.assign({}, row));
      }
      if(row.sign == 'upload'){
        let list = row.arr;
        let arr = list.map(item => item.project_code);
        let new_arr = Array.from(new Set(arr));
        if (new_arr.length < arr.length) {
          this.$baseMessage('编码不能重复', 'error')
          return
        }
        that.tableData = []
        that.drugCode.sbCivilianMedical = []
        list.forEach(function (item, index) {
          that.drugCode.sbCivilianMedical.push(Object.assign({}, item))
        });
        let res = ''
        //西药
        if(this.form.drugs == 1){
          res = await westernMedicineDrugCode(that.drugCode)
        }
        //中成药
        if(this.form.drugs == 2){
          res = await chinesePatentMedicineDrugCode(that.drugCode)
        }
        if(res.code == 0){
          list.forEach(function (item, index) {
            if (list.length == res.data.length) {
              item.project_name = res.data[index].drugNames
              item.unit = res.data[index].unit
              item.org_price = res.data[index].purchaseCeilingPrice
            }
            that.tableData.push(Object.assign({}, item))
          });
        }else{
          this.$baseMessage(res.msg, 'error')
        }


        console.log(that.tableData)
      }
      console.log(row)
    },
    handleDelete(index, rows) {
      rows.splice(index, 1);
    },
    handleAdd(row,index) {
      row.index = index
      row.drugs = this.form.drugs
      console.log(row)
      this.$refs['examine3'].showEdit(row)
    },
    handleImport(){
      this.$refs['importExcel'].showEdit()
    },
    handleDefinite(row,index) {
      row.index = index
      row.org_code = this.form.org_code
      row.org_name = this.form.org_name
      console.log(row)
      this.$refs['definite'].showDia(row)
    },
    async save() {
      if(this.tableData.length <= 0){
        this.$baseMessage('请至少保留一条数据！', 'error')
        return
      }

      this.form.sbCivilianMaterial = this.tableData
      const loading = this.$loading({
        lock: true,
        text: '提交中，请稍等...',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });

      const res = await insertCivilianMaterial(this.form)
      loading.close();
      console.log(res);
      if (res.code == 0) {
        this.loading = false
        this.dialog = false
        this.$emit('fetch-data')
        this.$baseMessage('操作成功', 'success')
      }else{
        loading.close();
        this.$baseMessage(res.msg, 'error')
      }
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
      // if (sign) {
      //   this.$baseMessage('明细未填写,请完善信息！', 'error')
      //   return
      // }
      // this.$confirm('点击确认将提交到医保审核，是否执行提交操作？')
      //     .then((_) => {
      //       this.save()
      //       this.timer = setTimeout(() => {
      //         done()
      //         // 动画关闭需要一定的时间
      //         setTimeout(() => {
      //           this.loading = false
      //         }, 100)
      //       }, 100)
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