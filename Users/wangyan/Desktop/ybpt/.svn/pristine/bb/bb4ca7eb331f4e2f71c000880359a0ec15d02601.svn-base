<template>
  <div class="main-container" v-loading="loading" element-loading-text="数据同步中" element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">
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
                    <!--<vab-icon :icon="['fas', 'search']" slot="suffix" style="margin-right:10px;" @click="openwin"></vab-icon>-->
                    <!--<vab-icon :icon="['fas', 'id-card']" slot="suffix" style="margin-right:10px;"></vab-icon>-->
                    <!--<vab-icon :icon="['fas', 'barcode']" slot="suffix" style="margin-right:10px;"></vab-icon>-->
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
                  <el-input v-model.trim="queryForm.naty_name" disabled />
                </el-form-item>
                <!--<el-form-item label="民族">-->
                <!--<el-select-->
                <!--v-model="queryForm.naty_name"-->
                <!--style="width: 100%"-->
                <!--disabled-->
                <!--&gt;-->
                <!--<el-option label="汉族" value="1"></el-option>-->
                <!--<el-option label="少数民族" value="2"></el-option>-->
                <!--</el-select>-->
                <!--</el-form-item>-->
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
            <vab-query-form>
              <vab-query-form-right-panel :span="24">
                <el-form-item class="mr0">
                  <el-button icon="el-icon-refresh-left" @click="reseat()">重 置</el-button>
                  <el-button icon="el-icon-search" type="primary" @click="querry()">
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
            <span class="tips">门特门慢登记列表</span>
            <el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">新增</el-button>
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row
            border @current-change="handleCurrentChange" height="calc(100% - 50px)">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" width="80px"><template
                slot-scope="scope">
                <span v-text="getIndex(scope.$index)"> </span>
              </template></el-table-column>
            <el-table-column show-overflow-tooltip label="审核状态" align="center" prop="approvalStatus" width="120px"
              :formatter="statusFormat">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="is_del" width="120px" label="有效状态" align="center"
              :formatter="delFormat"></el-table-column>
            <el-table-column show-overflow-tooltip prop="psn_name" label="人员姓名" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="psn_cert_type" label="证件类型" align="center" width="120px"
              :formatter="certFormat"></el-table-column>
            <el-table-column show-overflow-tooltip prop="certno" label="证件号码" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="opsp_DISE_NAME" label="医保门慢门特病种名称" align="center"
              width="180px"></el-table-column>
            <el-table-column show-overflow-tooltip prop="insutypeName" label="参保险种" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="emp_name" align="center" label="单位名称"></el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handlechuli(row)" type="primary" size="mini">
                  查看
                </el-button>
                <el-button plain @click="handlecancel(row)" type="primary" size="mini" v-if="row.approvalStatus == 0 ">
                  撤消
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum"></cardnum>
    <edit ref="edit" @fetch-data="selectData"></edit>
    <views ref="views"></views>
  </div>
</template>

<script>
import { revoke, selectList } from '@/api/opspdise.js'
import { selectByIdCard } from '@/api/opspregd.js'
import { closeAll, opspDiseRabit, revokeRabit, selectCxIdCard, tip } from '@/api/rabat.js'
import Cardnum from '@/components/cardno'
import axios from 'axios'
import Edit from './components/edit'
import Views from './components/edit'
export default {
  name: 'Index',
  components: {Cardnum,Edit,Views},
  data() {
    return {
      value1: '',
      checked: false,
      loading: false,
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
        psn_name: '',
        gend: '',
        naty: '',
        naty_name: '',
        brdy: '',
        mob: '',
        emp_name: ''
      },
      isadd:false
    }
  },
  created() {
    // this.fetchData()
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin(){
      this.$refs['cardnum'].showDia()
    },
    handleAdd(){
      if(this.isadd){
        this.$refs['edit'].showDia(this.queryForm)
      }else {
        this.$baseMessage('请先查询人员信息', 'error')
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
    handlechuli(row){
      console.log(row);
      this.$refs['views'].showDia(row.id)
    },
    handlecancel(row){
      var that  = this ;
      that.$baseConfirm('确认进行撤消？', null, async () => {
        that.loading = true ;
        var opspDiseRabitData = opspDiseRabit();
        axios.post(that.$api, opspDiseRabitData)
                .then(res => {
                  setTimeout(function () {
                    var selectCxIdCardData = selectCxIdCard(that.queryForm.certno);
                    axios.post(that.$api, selectCxIdCardData)
                            .then(res => {
                              setTimeout(function () {
                                var revokeData = revokeRabit('未审核','有效',that.queryForm.certno,row.opsp_DISE_NAME);
                                axios.post(that.$api, revokeData)
                                        .then(res => {
                                          setTimeout(function () {
                                            var tipData =  tip() ;
                                            axios.post(that.$api, tipData)
                                                    .then(res => {
                                                      if(res.data.data ==""){
                                                        that.loading = false ;
                                                        that.$baseMessage("同步数据失败！", 'error') ;
                                                        setTimeout(function () {
                                                          var closeAllData = closeAll();
                                                          axios.post(that.$api, closeAllData)
                                                                  .then(res => {

                                                                  });
                                                        }, 900)
                                                      }else {
                                                        that.loading = false ;
                                                        that.$baseMessage(res.data.data, 'success') ;
                                                        that.saveData(row) ;

                                                        //关掉门慢特页面
                                                        setTimeout(function () {
                                                          var closeAllData = closeAll();
                                                          axios.post(that.$api, closeAllData)
                                                                  .then(res => {

                                                                  });
                                                        }, 900)
                                                      }
                                                    });
                                          }, 1500);
                                        });
                              }, 1000);
                            });
                  }, 2000);
                });
        // that.$baseMessage(msg, 'success')
        // that.fetchData()
      })
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    // async fetchData() {
    //   this.listLoading = true
    //   const { data, totalCount } = await getList(this.queryForm)
    //   this.list = data
    //   this.total = totalCount
    //   setTimeout(() => {
    //     this.listLoading = false
    //   }, 300)
    // },
    selectByIdCards(){
      var that = this ;
      selectByIdCard(that.queryForm).then((res) => {
        if(null != res.data){
          that.$baseMessage('查询成功', 'success')
          that.isadd = true ;
          that.queryForm.psn_name = res.data.psn_name ;
          that.queryForm.gend = res.data.gend ;
          that.queryForm.naty = res.data.naty ;
          that.queryForm.brdy = res.data.brdy ;
          that.queryForm.mob = res.data.mob ;
          that.queryForm.naty_name = res.data.naty_name ;
          that.queryForm.emp_name = res.data.emp_name ;
          that.queryForm.live_addr = res.data.live_addr;
        }else {
          that.reseat();
          that.$baseMessage('未查询到此人信息', 'error');
          that.isadd = false ;
        }
      })
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
    querry(){
      var that = this ;
      if(that.queryForm.psn_name == "" && that.queryForm.psn_name != null && that.queryForm.psn_name !=undefined){
        that.$baseMessage('请先查询人员信息', 'error') ;
      }else {
        selectList(that.queryForm).then((res) => {
          that.list = res.data.records;
          that.total = res.data.total;
        })
      }
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    statusFormat(row,column){
      console.log(row.approvalStatus);
      var statusW;
      switch (row.approvalStatus) {
        case "0":statusW= "未审核";break;
        case "1":statusW= "审核成功";break;
        case "2":statusW= "已撤销";break;
        case "3":statusW= "审核不通过";break;
      }
      return statusW
    },
    delFormat(row,column){
      console.log(row.is_del);
      var statusW;
      switch (row.is_del) {
        case "0":statusW= "有效";break;
        case "1":statusW= "失效";break;
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
    selectData(){
      var that = this ;
      that.querry() ;
    },
    saveData(row){
      console.log(row);
      var that = this ;
      revoke(row.id).then((res) => {
        that.querry() ;
      })
    }



  },
}
</script>
<style lang="scss" scoped>
.main-container{
::v-deep{  
  .table_card{  
      .el-card__body{
       height:calc(100vh - #{$base-nav-bar-height} - #{$base-tabs-bar-height} - #{$base-padding} - #{$base-padding} - 390px)
       
      }  
      
    }
    }
    }
</style>
