<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button
                icon="el-icon-search"
                type="primary"
                @click="queryData"
              >
                查 询
              </el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat">
                重 置
              </el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                <el-form-item label="耗材国家编码">
                  <el-input
                    v-model.trim="queryForm.mcs_code"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                <el-form-item label="省平台挂网产品编号" class="custemitem">
                  <el-input
                    v-model.trim="queryForm.product_num"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
              <el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">
                <el-form-item label="版本号">
                  <el-input
                    v-model.trim="queryForm.ver"
                    @keyup.enter.native="queryData"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">耗材收费标识</span>
          </div>
          <el-table
            ref="listTable"
            stripe
            :data="list"
            :element-loading-text="elementLoadingText"
            highlight-current-row
            border
            @current-change="handleCurrentChange"
            @selection-change="setSelectRows"
            height="calc(100% - 50px)"
          >
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column
              align="center"
              label="耗材国家编码"
              prop="MCS_CODE"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="产品名称"
              prop="PRODUCT_NAME"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="收费等级"
              prop="CHARGE_LV"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="收费等级使用说明"
              prop="CHARGE_LV_MEMO"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="医保耗材分类代码"
              prop="MED_MCS_TYPE_CODE"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="一级分类"
              prop="LV1_TYPE_CODE"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="二级分类"
              prop="LV2_TYPE_CODE"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="三级分类"
              prop="LV3_TYPE_CODE"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="医保通用名"
              prop="HI_GENNAME"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="规格"
              prop="SPEC"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="型号"
              prop="MOL"
              align="center"
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="材质"
              prop="MATL"
              align="center"
            ></el-table-column>
            <el-table-column
              align="center"
              label="特征"
              prop="CHARACTERISTICS"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="注册备案号"
              prop="REG_FIL_NO"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="注册备案产品名称"
              prop="REG_FIL_PROD_NAME"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="单件产品名称"
              prop="SIN_PROD_NAME"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="耗材企业"
              prop="MCS_ENTP"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="注册备案人"
              prop="REGER_NAME"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              show-overflow-tooltip
              label="UDI"
              prop="UDI"
              align="center"
            ></el-table-column>
            <el-table-column
              align="center"
              label="原27位码"
              prop="OLD_MCS_CODE"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="原码状态"
              prop="CODE_STAS"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="类型"
              prop="MCS_TYPE"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="省收费编码"
              prop="PROVINCE_CODE"
              show-overflow-tooltip
            ></el-table-column>
            <el-table-column
              align="center"
              label="省平台挂网产品编号"
              prop="PRODUCT_NUM"
              show-overflow-tooltip
            ></el-table-column>
          </el-table>
          <!--                    <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"-->
          <!--                                   :layout="layout"-->
          <!--                                   :total="total" @size-change="handleSizeChange"-->
          <!--                                   @current-change="handleCurrentChange2"></el-pagination>-->
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import { consumables9002 } from '@/api/consumables'

  export default {
    name: 'Index',
    data() {
      return {
        value1: '',
        checked: false,
        isShow: false,
        list: [],
        listLoading: true,
        layout: 'total, sizes, prev, pager, next, jumper',
        total: 0,
        selectRows: '',
        tableHeight: '100%',
        elementLoadingText: '正在加载...',
        queryForm: {
          mcs_code: '',
          product_num: '',
          ver: '',
          pageNo: 1,
          pageSize: 10,
        },
      }
    },
    created() {
      // this.fetchData()
    },
    beforeDestroy() {},
    mounted() {},
    methods: {
      handleSizeChange(val) {
        this.queryForm.pageSize = val
        this.fetchData()
      },
      handleCurrentChange2(val) {
        this.queryForm.pageNo = val
        this.fetchData()
      },
      setSelectRows(val) {
        this.selectRows = val
      },
      handleCurrentChange(val) {
        this.selectRows = val
      },
      queryData() {
        this.queryForm.pageNo = 1
        this.fetchData()
      },
      moreQuery() {
        this.isShow = !this.isShow
      },
      fetchData() {
        var that = this
        if (
          that.queryForm.mcs_code == undefined ||
          that.queryForm.mcs_code == '' ||
          that.queryForm.mcs_code == null
        ) {
          that.$baseMessage('请输入耗材国家编码', 'error')
        }
        // if (that.queryForm.ver == undefined || that.queryForm.ver == '' || that.queryForm.ver == null) {
        //     that.$baseMessage('请输入版本号', 'error')
        // }
        consumables9002(this.queryForm).then((res) => {
          if (res.code == 0) {
            that.list = []
            let data = JSON.parse(res.data)
            if (data.length > 0) {
              if (
                data[0].errinfo != null &&
                data[0].errinfo != undefined &&
                data[0].errinfo != ''
              ) {
                that.$baseMessage(data[0].errinfo, 'error')
                return
              }
            }
            that.list = data
            that.total = 1
          } else {
            that.$baseMessage(res.msg, 'error')
          }
        })
      },
      //获取表格序号
      getIndex($index) {
        //表格序号
        return (
          (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
        )
      },
      statusFormat(row, column) {
        var statusW
        switch (row.emp_type) {
          case '0':
            statusW = '医保中心'
            break
        }
        return statusW
      },
      reseat() {
        this.queryForm.mcs_code = ''
        this.queryForm.product_num = ''
        this.fetchData()
      },
    },
  }
</script>
