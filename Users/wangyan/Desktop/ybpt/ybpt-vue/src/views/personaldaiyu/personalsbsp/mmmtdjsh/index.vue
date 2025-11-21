<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <vab-icon :icon="['fas', 'angle-up']" v-if="isShow" @click="moreQuery"></vab-icon>
            <vab-icon :icon="['fas', 'angle-down']" v-else @click="moreQuery"></vab-icon>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="证件类型">
                  <el-select v-model="queryForm.psn_cert_type" placeholder="证件类型" style="width: 100%">
                    <el-option label="居民身份证（户口薄）" value="01"></el-option>
                    <el-option label="中国人民解放军军官证" value="2"></el-option>
                    <el-option label="中国人民武装警察警官证" value="3"></el-option>
                    <el-option label="香港特区护照/港澳居民来往内地通行证" value="4"></el-option>
                    <el-option label="奥门特区护照/港澳居民来往内地通行证" value="5"></el-option>
                    <el-option label="台湾居民来往内地通行证" value="6"></el-option>
                    <el-option label="外国人永久居留证" value="7"></el-option>
                    <el-option label="外国人护照" value="8"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="医保电子凭证/身份证号">
                  <el-input v-model.trim="queryForm.certno" placeholder="身份证号" clearable class="input-with-icon"
                    @keyup.enter.native="selectByIdCards()">
                    <vab-icon :icon="['fas', 'search']" slot="suffix" style="margin-right:10px;" @click="openwin">
                    </vab-icon>
                    <vab-icon :icon="['fas', 'id-card']" slot="suffix" style="margin-right:10px;"></vab-icon>
                    <vab-icon :icon="['fas', 'barcode']" slot="suffix" style="margin-right:10px;"></vab-icon>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="姓名">
                  <el-input v-model.trim="queryForm.psn_name" placeholder="姓名" disabled />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20" v-if="isShow">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="性别">
                  <el-select v-model="queryForm.gend" style="width: 100%" disabled>
                    <el-option label="男" value="1"></el-option>
                    <el-option label="女" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="民族">
                  <el-select v-model="queryForm.naty_name" style="width: 100%" disabled>
                    <el-option label="汉族" value="1"></el-option>
                    <el-option label="少数民族" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="出生日期">
                  <el-date-picker v-model.trim="queryForm.brdy" disabled type="date" class="w"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="电话号码">
                  <el-input v-model.trim="queryForm.mob" disabled />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="病种类型代码">
                  <el-select v-model="queryForm.dise_type_code" style="width: 100%">
                    <el-option label="门慢门特病种" value="10"></el-option>
                    <el-option label="门诊慢性病" value="11"></el-option>
                    <el-option label="门诊特殊疾病" value="12"></el-option>
                    <el-option label="按病种结算病种" value="3"></el-option>

                  </el-select>
                </el-form-item>
              </el-col>
              <!--<el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
              <!--<el-form-item label="事件类型">-->
              <!--<el-select v-model="queryForm.username" style="width: 100%">-->
              <!--<el-option-->
              <!--label="居民身份证（户口薄）"-->
              <!--value="1"-->
              <!--&gt;</el-option>-->
              <!--<el-option-->
              <!--label="中国人民解放军军官证"-->
              <!--value="2"-->
              <!--&gt;</el-option>-->
              <!--<el-option-->
              <!--label="中国人民武装警察警官证"-->
              <!--value="3"-->
              <!--&gt;</el-option>-->
              <!--<el-option-->
              <!--label="香港特区护照/港澳居民来往内地通行证"-->
              <!--value="4"-->
              <!--&gt;</el-option>-->
              <!--<el-option-->
              <!--label="奥门特区护照/港澳居民来往内地通行证"-->
              <!--value="5"-->
              <!--&gt;</el-option>-->
              <!--<el-option-->
              <!--label="台湾居民来往内地通行证"-->
              <!--value="6"-->
              <!--&gt;</el-option>-->
              <!--<el-option label="外国人永久居留证" value="7"></el-option>-->
              <!--<el-option label="外国人护照" value="8"></el-option>-->
              <!--</el-select>-->
              <!--</el-form-item>-->
              <!--</el-col>-->
            </el-row>
            <vab-query-form>
              <vab-query-form-right-panel :span="24">
                <el-form-item class="mr0">
                  <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
                  <el-button icon="el-icon-search" type="primary" @click="queryData">
                    查 询
                  </el-button>
                </el-form-item>
              </vab-query-form-right-panel>
            </vab-query-form>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card table_card" shadow="never">
          <div slot="header">
            <span class="tips">门慢门特待审核列表</span>
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row
            border @selection-change="setSelectRows"  height="calc(100% - 50px)">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!--<el-table-column-->
            <!--show-overflow-tooltip-->
            <!--type="selection"-->
            <!--align="center"-->
            <!--fixed="left"-->
            <!--width="80px"-->
            <!--&gt;</el-table-column>-->
            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" fixed="left" width="180px">
              <template slot-scope="scope">
                <span v-text="getIndex(scope.$index)"> </span>
              </template></el-table-column>
            <el-table-column show-overflow-tooltip label="审核状态" width="120px" align="center" prop="approvalStatus"
              :formatter="approvalFormat"></el-table-column>
            <el-table-column show-overflow-tooltip prop="associationId" width="220px" label="业务流水号" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="opsp_DISE_NAME" label="医保门诊门特病种名称" width="200px"
              align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="medins_name" label="鉴定定点医药机构名称" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="psn_name" label="人员姓名" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="psn_cert_type" label="证件类型" align="center" width="120px"
              :formatter="certFormat"></el-table-column>
            <el-table-column show-overflow-tooltip prop="certno" align="center" label="证件号码" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="insu_admdvs_name" align="center" label="参保所属医保区划"
              width="180px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="insu_admdvs_name" align="center" label="参保险种" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="mob" align="center" label="联系电话" width="220px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="live_addr" align="center" label="联系地址" width="220px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="emp_name" align="center" label="单位名称" width="220px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="dise_type_code" align="center" label="病种类型代码" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="opter_name" align="center" label="经办人姓名" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="opt_time" align="center" label="经办时间" width="120px">
            </el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="100" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handleAdd(row)" type="primary" size="mini" v-if="row.approvalStatus == 0">
                  审核
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <shenhe ref="shenhe" @fetch-data="fetchData"></shenhe>
  </div>
</template>

<script>
import { opspCheckList } from '@/api/opspdise.js'
import { selectByIdCard } from '@/api/opspregd.js'
import Shenhe from '@/components/allshenhe'
import Cardnum from '@/components/cardno'
import Edit from './components/edit'
export default {
  name: 'Index',
  components: { Cardnum, Edit,Shenhe },
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        psn_cert_type: '01',
        certno: '',
        dise_type_code: '10',
        psn_name: '',
        gend: '',
        naty: '',
        naty_name: '',
        brdy: '',
        mob: ''
      },
    }
  },
  created() {
    this.fetchData()
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    handleAdd(row) {
      this.$refs['edit'].showDia(row.id,row.dise_type_code,row.associationId)
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleSh() {
      if (this.selectRows.length > 0) {
        const ids = this.selectRows.map((item) => item.id).join()
        this.$baseConfirm('你确定要审核选中项吗', null, async () => {
          // const { msg } = await doDelete({ ids })
          this.$refs['shenhe'].showDia()
          //this.$baseMessage('模拟审核成功', 'success')
          //this.fetchData()
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async fetchData() {
      var that = this ;
      if(that.queryForm.dise_type_code=='10'){
        that.queryForm.dise_type_code = '普通门慢门特病种';
      }else if(that.queryForm.dise_type_code=='11'){
        that.queryForm.dise_type_code = '门诊慢性病';
      }else if(that.queryForm.dise_type_code=='12'){
        that.queryForm.dise_type_code = '门诊特殊病';
      }else if(that.queryForm.dise_type_code=='3'){
        that.queryForm.dise_type_code = '特殊病种';
      }
      opspCheckList(that.queryForm).then((res) => {
          if(res.code == 0 ){
            that.$baseMessage('查询成功', 'success') ;
            that.list = res.data.records ;
          }
      })
    },
    selectByIdCards(){
      var that = this ;
      selectByIdCard(that.queryForm).then((res) => {
        if(null != res.data){
          that.$baseMessage('查询成功', 'success')
          that.queryForm.psn_name = res.data.psn_name ;
          that.queryForm.gend = res.data.gend ;
          that.queryForm.naty = res.data.naty ;
          that.queryForm.brdy = res.data.brdy ;
          that.queryForm.mob = res.data.mob ;
          that.queryForm.naty_name = res.data.naty_name ;
        }else {
          that.reseat();
          that.$baseMessage('未查询到此人信息', 'error')
        }
      })
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    approvalFormat(row,column){
      var statusW;
      switch (row.approvalStatus) {
        case "0":statusW= "未审核";break;
        case "1":statusW= "审核成功";break;
        case "2":statusW= "已撤销";break;
        case "3":statusW= "审核不通过";break;
      }
      return statusW
    },
    certFormat(row,column){
      console.log(row.psn_cert_type);
      var statusW;
      switch (row.psn_cert_type) {
        case "01":statusW= "居民身份证（户口薄）";break;
        case "1":statusW= "失效";break;
      }
      return statusW
    },
    reseat(){
      var that = this ;
      that.queryForm.psn_name = "" ;
      that.queryForm.gend = "" ;
      that.queryForm.naty = "" ;
      that.queryForm.brdy = "" ;
      that.queryForm.mob ="" ;
      that.queryForm.naty_name = "" ;
      // that.queryForm.certno= "" ;
    },
    save(){

    }
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  padding: 0 !important;
  margin: 0 !important;
  background: #f5f7f8 !important;

  .bottom {
    padding-top: 20px;
    margin-top: 5px;
    color: #595959;
    text-align: left;
    border-top: 1px solid $base-border-color;
  }

  .bottom-btn {
    button {
      margin: 5px 10px 15px 0;
    }
  }
}
</style>
<style lang="scss" scoped>
.main-container{
::v-deep{  
  .table_card{  
      .el-card__body{
       height:calc(100vh - #{$base-nav-bar-height} - #{$base-tabs-bar-height} - #{$base-padding} - #{$base-padding} - 440px)
      }  
      .el-table__body-wrapper{
        height: calc(100% - 40px)!important;
      }    
    }
    }
    }
</style>
