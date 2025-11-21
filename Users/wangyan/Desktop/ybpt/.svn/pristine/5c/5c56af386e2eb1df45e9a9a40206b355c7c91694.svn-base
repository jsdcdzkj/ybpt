<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      append-to-body
      width="70%"
      @close="close"
  >
    <vab-query-form>
      <el-form :inline="true" :model="queryForm">
        <vab-query-form-left-panel :span="20">
          <!--          <el-row>-->
          <!--            <el-col :span="8">-->
          <el-form-item label="国家医疗机构制剂代码">
            <el-input v-model.trim="queryForm.nationalFormulaCode" @keyup.enter.native="queryData" placeholder="请输入" clearable/>
          </el-form-item>
          <!--            </el-col>-->
          <!--            <el-col :span="8">-->
          <el-form-item label="制剂名称">
            <el-input v-model.trim="queryForm.formulaName" @keyup.enter.native="queryData" placeholder="请输入" clearable/>
          </el-form-item>
          <!--            </el-col>-->
          <!--            <el-col :span="8">-->
          <el-form-item label="制剂注册单位">
            <el-input v-model.trim="queryForm.registerCompanyName" @keyup.enter.native="queryData" placeholder="请输入" clearable/>
          </el-form-item>
          <!--            </el-col>-->
          <!--          </el-row>-->
        </vab-query-form-left-panel>
        <vab-query-form-right-panel :span="4">
          <el-form-item>
            <el-button type="primary" @click="queryData">查 询</el-button>
            <el-button @click="reSet">重 置</el-button>
          </el-form-item>
        </vab-query-form-right-panel>
      </el-form>
    </vab-query-form>

    <el-card class="card" shadow="never">
      <div slot="header">
        <span class="tips">制剂目录列表</span>
        <div class="right">
        </div>
      </div>
      <el-table
          ref="listTable"
          v-loading="listLoading"
          :data="list"
          :element-loading-text="elementLoadingText"
          border
          height="260px"
          highlight-current-row
          stripe
          @current-change="handleCurrentChange"
      >
        <template slot="empty">
          <el-empty :image-size="150"></el-empty>
        </template>
        <el-table-column label="选择" width="80" align="center">
          <template slot-scope="scope">
            <el-radio
                v-model="radio"
                :label="scope.row.nationalFormulaCode"
                class="radio"
            ></el-radio>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip prop="categoryCode" label="分类编码" align="center"
                         width="250px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="genericNameCode" label="药品通用名编码" align="center"
                         width="250px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="productNameCode" label="产品名编码" align="center"
                         width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="formulaName" label="制剂名称" align="center"
                         width="300px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="goodsName" label="商品名" align="center"
                         width="300px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="payType" label="支付类别" align="center" width="300px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="dosageForm" label="剂型" align="center"
                         width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="specs" label="规格" align="center" width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="minPriceUnit" label="最小计价单位" align="center"
                         width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="unit" label="单位" align="center" width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="minPackage" label="最小包装" align="center"
                         width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="approvalNo" label="制剂批准文号" align="center"
                         width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="remark" label="备注" align="center" width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="catalogCode" label="目录编号" align="center"
                         width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="registerCompanyName" label="制剂注册单位" align="center"
                         width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="selfPayRatio" label="个人先行自付比例" align="center"
                         width="120px"></el-table-column>
        <el-table-column show-overflow-tooltip prop="nationalFormulaCode" label="国家医疗机构制剂代码" align="center"
                         width="120px"></el-table-column>
      </el-table>
      <el-pagination
          :current-page="queryForm.pageNo"
          :layout="layout"
          :page-size="queryForm.pageSize"
          :total="total"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange2"
      ></el-pagination>

    </el-card>


    <div slot="footer" class="dialog-footer">
      <el-button @click="close">关 闭</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>
<script>
  import {catalogPage,checkSave} from "@/api/prepareApply";

  export default {
    name: 'Index',
    components: {},
    data() {
      return {
        currentRow: null,
        radio: '',
        form: {},
        tableData: [],
        title: '',
        dialogFormVisible: false,
        value1: '',
        checked: false,
        isShow: false,
        list: null,
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: {},
        elementLoadingText: '正在加载...',
        queryForm: {
          // "nationalFormulaCode":"国家医疗机构制剂代码",
          // "formulaName":"制剂名称",
          // "registerCompanyName":"制剂注册单位"
          //
          pageNo: 1,
          pageSize: 10,
        },
        userinfo:{}
      }
    },
    created() {
      this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
    },
    porps: {
      //父组件传递过来的初始选中值，根据自己项目需求设置
      chooseData: {
        type: Object,
      },
    },
    watch: {
      //观察是否有父组件传递的初始值或者变化，重新选中
      chooseData(val) {
        if (val) {
          this.radio = false
          this.getInitChoose()
        }
      },
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
      handleCurrentChange(val) {
        if (val) {
          // console.log('为你写诗', val);
          this.radio = val.nationalFormulaCode
          this.selectRows = val
        }
      },
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      getCurrentRow(index) {
        this.radio = index
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      reSet() {
        this.queryForm = {
          // "nationalFormulaCode":"国家医疗机构制剂代码",
          // "formulaName":"制剂名称",
          // "registerCompanyName":"制剂注册单位"
          //
          pageNo: 1,
          pageSize: 10,
        },
          this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      //设置单选框选择状态
      getInitChoose() {
        if (this.chooseData) {
          let index = this.tableData.findIndex(
            (item) => item.userUuid == this.chooseData.id
          )
        }
        if (index > -1) {
          this.radio = index
        }
      },
      async fetchData() {
        this.listLoading = true
        if ('政府非营利' != this.form.index_natures && '4' == this.form.index_type) {
          this.queryForm.addType = '0'
        }
        if ('政府非营利' != this.form.index_natures && '7' == this.form.index_type) {
          this.queryForm.addType = '1'
        }

        const res = await catalogPage(this.queryForm)
        this.list = res.data.records
        this.total = res.data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      showDia(form) {
        // console.log('medicinal',form)
        // this.form.index_natures = form.index_natures
        // this.form.index_type = form.index_type
        // if(form.project_code){
        //   this.radio = form.project_code
        // }
        this.radio = form.nationalFormulaCode;
        this.selectRows = form;
        this.title = '制剂查询'
        this.dialogFormVisible = true
        this.fetchData()
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialogFormVisible = false;
        this.reSet();
      },
      save() {
        // console.log(this.userinfo);
        if(this.selectRows.nationalFormulaCode){
          let obj = {isInCategory:1
            ,nationalFormulaCode:this.selectRows.nationalFormulaCode
            ,orgCode:this.userinfo.org_code
            ,id:this.selectRows.id?this.selectRows.id:undefined}
          checkSave(obj).then(res =>{
            // console.log(res);
            if(res.code=='-1'){
              this.$message.error(res.msg)
            }else{
              this.$emit('fetch-data', this.selectRows)
              this.close()
            }

          })
        }


      },
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .el-radio__label {
      display: none;
    }
  }
</style>
