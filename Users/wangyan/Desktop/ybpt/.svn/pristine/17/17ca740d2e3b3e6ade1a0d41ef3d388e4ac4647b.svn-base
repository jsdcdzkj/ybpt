<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <div class="right">
              <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
              <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
            </div>
          </div>
          <el-form label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="userinfo.user_type == '1'">
                <el-form-item label="单位名称">
                  <el-input v-model.trim="queryForm.org_name" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="userinfo.user_type == '1'">
                <el-form-item label="单位医保编码">
                  <el-input v-model.trim="queryForm.org_code" @keyup.enter.native="queryData"/>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="审核状态">
                  <el-select v-model="queryForm.status" style="width: 100%" @change="queryData()" clearable>
                    <el-option label="待初审" value="0"></el-option>
                    <el-option label="待复审" value="1"></el-option>
                    <el-option label="待终审" value="2"></el-option>
                    <el-option label="完成" value="4"></el-option>
                    <el-option label="驳回" value="5"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="userinfo.user_type == '1'">
                <el-form-item label="药品类别">
                  <el-select v-model="queryForm.type" style="width: 100%" @change="queryData()" clearable>
                    <el-option label="西药" value="西药"></el-option>
                    <el-option label="中成药" value="中成药"></el-option>
                    <el-option label="中药饮片" value="中药饮片"></el-option>
                    <el-option label="中药配方颗粒" value="中药配方颗粒"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
<!--              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">-->
<!--                <el-form-item label="定点类型">-->
<!--                  <el-select v-model="queryForm.user_type" style="width: 100%" @change="queryData()" clearable>-->
<!--                    <el-option label="定点" value="定点"></el-option>-->
<!--                    <el-option label="非定点" value="非定点"></el-option>-->
<!--                  </el-select>-->
<!--                </el-form-item>-->
<!--              </el-col>-->
              <el-col :xs="24" :sm="24" :md="6" :lg="6" :xl="6" v-if="userinfo.user_type == '1' && (userinfo.org_code ==='320399' || userinfo.org_name =='admin')">
                <el-form-item label="统筹区">
                  <el-select v-model="queryForm.fix_blng_admdvs_sbApply" clearable
                             style="width: 100%" @change="queryData()">
                    <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                               :value="item.value">
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">药品加价率告知手续列表</span>
            <div class="right">
              <el-button type="success" icon="el-icon-plus" v-if="user_type == '2' || user_type == '3' || user_type == '7' || user_type == '8'"
                         @click="handleAdd">
                新增
              </el-button>
              <el-button icon="el-icon-upload2" type="primary" @click="handleExport" v-if="isAdmin">
                导出
              </el-button>
              <el-button icon="el-icon-s-check" type="primary" @click="batch" v-if="isAdmin">
                批量通过
              </el-button>
            </div>
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText"
                    highlight-current-row
                    border @current-change="handleCurrentChange"
                    @selection-change="handleSelectionChange"
                    height="calc(100% - 50px)">
            <el-table-column
                type="selection"
                :selectable = "selectable"
                width="55" :reserve-selection="true">
            </el-table-column>
            <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="org_name" width="220px" label="单位名称"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="org_code" width="220px" label="单位医保编码"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="fix_blng_admdvs_sbApply_name" width="220px" label="统筹区"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="natures" width="220px" label="经营性质"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="type" width="220px" label="药品类别"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="审核状态" align="center">
              <template #default="{ row }">
                <el-tag type="info" v-if="row.status==0">待初审</el-tag>
                <el-tag v-else-if="row.status==1">待复审</el-tag>
                <el-tag type="warning" v-else-if="row.status==2">待终审</el-tag>
                <el-tag type="info" v-else-if="row.status==3">待生成受理书</el-tag>
                <el-tag type="success" v-else-if="row.status==4">完成</el-tag>
                <el-tag type="danger" v-else-if="row.status==5">驳回</el-tag>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="reason" width="220px" label="驳回原因"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="createTime" width="220px" label="创建时间"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="first_time" width="220px" label="初审时间" v-if="user_type == 1"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="first_trialer" width="220px" label="初审负责人" v-if="user_type == 1"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="second_time" width="220px" label="复审时间" v-if="user_type == 1"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="second_trialer" width="220px" label="复审负责人" v-if="user_type == 1"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="end_time" width="220px" label="终审时间" v-if="user_type == 1"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="end_trialer" width="220px" label="终审负责人" v-if="user_type == 1"
                             align="center"></el-table-column>
            <el-table-column show-overflow-tooltip label="操作" width="250" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain
                           v-if="roles.indexOf('examine-first') > -1 && row.status == '0' && row.isAudit == 'true'"
                           @click="handleView(row)" type="primary" size="mini">
                  初审
                </el-button>
                <el-button plain
                           v-if="roles.indexOf('examine-second') > -1 && row.status == '1' && row.isAudit == 'true'"
                           type="primary" size="mini" @click="handleView(row)">
                  复审
                </el-button>
                <el-button plain
                           v-if="roles.indexOf('examine-third') > -1 && row.status == '2' && row.isAudit == 'true'"
                           @click="handleView(row)" type="primary" size="mini">
                  终审
                </el-button>
                <el-button plain @click="handleLook(row,'1')" type="primary" size="mini">
                  查看
                </el-button>

                <el-button plain @click="handleSls(row)" type="primary" size="mini"
                           v-if="row.status == '3' && roles.indexOf('examine-first') > -1 && row.user_type != '非定点'">
                  生成受理书
                </el-button>
                <el-button plain @click="handleLook(row,'2')" v-if="row.status == '4'  && row.user_type != '非定点'" type="primary"
                           size="mini">
                  查看受理书
                </el-button>
                <!--                <el-button plain @click="handleAdd(row)" type="primary" size="mini" if="row.status == '0'">编辑</el-button>-->
                <!--                <el-button plain @click="handleDelete(row)" type="danger" size="mini" if="row.status == '0'">删除</el-button>-->
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize"
                         :layout="layout"
                         :total="total" @size-change="handleSizeChange"
                         @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <look ref="look" @fetch-data="fetchData"></look>


  </div>
</template>

<script>
import Edit from './components/edit'
import {fileURL} from "@/config/setting.config";
import Look from './components/look';
import {page, update,bedViewPdf,drugExport,batchAudit} from "@/api/sbApplyDrug";
import {getDicts} from "@/api/dictManagement";

export default {
  name: 'Index',
  components: {Edit,Look},
  data() {
    return {
      value1: '',
      admdvs: null,
      checked: false,
      isShow: false,
      isAdmin: false,
      userinfo: {},
      user_type: '',
      username: '',
      list: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      tableHeight: '100%',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        org_code: '',
        status: '',
        user_type: '',
      },
      saveData: {
        reason: "",
        status: ""
      },
      roles: [],
      multipleSelection: []
    }
  },
  async created() {
    this.roles = JSON.parse(localStorage.getItem("roles"));
    this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
    this.user_type = this.userinfo.user_type;
    this.username = this.userinfo.username;
    console.log(this.userinfo);
    if (this.userinfo.user_type == '1') {
      this.queryForm.fix_blng_admdvs = this.userinfo.org_code;
    } else {
      this.queryForm.org_code = this.userinfo.org_code;
    }
    if (this.user_type == "1") {
      this.isAdmin = true;
      this.isShow = true;
    } else {
      this.isAdmin = false;
    }
    this.getAdmdvs()
    // await getFixmedinsB().then((res) => {
    //   // this.fixmedinsB = res.data
    //   if (this.userinfo.user_type == '1') {
    //     this.queryForm.fix_blng_admdvs = this.userinfo.org_code;
    //     this.queryForm.fix_blng_admdvs = res.data.fix_blng_admdvs
    //   }
    //
    // })
    this.fetchData()
  },
  beforeDestroy() {
  },
  mounted() {
    // getHeight()
    // //增加监听事件，窗口变化时得到高度。
    // window.addEventListener('resize',this.getHeight,false)
  },
  methods: {
    // getHeight(){
    // //获取浏览器高度并计算得到表格所用高度。
    //     this.tableHeight=document.documentElement.clientHeight-140
    //   },
    handleExport(row) {
      this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
        this.listLoading = true
        await drugExport(this.queryForm).then((res) => {
          let fileName = "药品加价率列表.xls";
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(fileName)
          link.href = objectUrl
          link.click()
          this.listLoading = false;
          this.$baseMessage("导出成功！", 'success')
        })

      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection)
    },
    batch(){
      if(this.multipleSelection.length==0 ){
        this.$baseMessage('请先勾选', 'error')
      }else {
        this.$baseConfirm('确认批量通过？', null, async () => {
          var newWeightArr = this.multipleSelection.map((item, index) => {
            return item.id;
          });

          batchAudit(newWeightArr.toString()).then((res) => {
            if (res.code == 0) {
              this.queryData();
              this.$refs.listTable.clearSelection();
              this.$baseMessage('成功', 'success')
            } else {
              this.$baseMessage(res.msg, 'error')
            }
          })
        })
      }
    },
    async getAdmdvs() {
      const res = await getDicts({"type": "admdvs-area"});
      if (res.code == "0") {
        this.admdvs = res.data;
      }
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    handleAdd(row) {
      if (row.id) {
        this.$refs['edit'].showDia(row)
      } else {
        this.$refs['edit'].showDia()
      }
    },
    handleLook(row, type) {
      if (row.user_type == '非定点') {
        row.pdf_path = fileURL + row.pdf_path + '?n=' + row.pdf_path.substring(row.pdf_path.lastIndexOf('/'), row.pdf_path.lastIndexOf('.')) + '&download=0'
        row.down_pdf_path = fileURL + row.down_pdf_path + '?n=' + row.down_pdf_path.substring(row.down_pdf_path.lastIndexOf('/'), row.down_pdf_path.lastIndexOf('.')) + '&download=1'
      }
      this.$refs['look'].showEdit(row, type)
    },
    handleSls(row) {
      // this.$refs['sls'].showDia(row)
      this.$baseConfirm('确认生成受理书？', null, async () => {
        this.listLoading = true;
        bedViewPdf(row.id).then((res) => {
          if (res.code == 0) {
            this.listLoading = false;
            this.queryData();
            this.$baseMessage('成功', 'success')
          } else {
            this.$baseMessage(res.msg, 'error')
          }
        })

      })

    },
    handleView(row) {
      this.saveData.reason = ""
      this.saveData.status = ""
      if (row.id) {
        this.$confirm('审核通过此条记录, 是否继续?', '提示', {
          distinguishCancelAndClose: true,
          confirmButtonText: '通过',
          cancelButtonText: '驳回',
          type: 'warning',
          center: true,
        })
            .then(() => {
              switch (row.status) {
                case "0" :
                  this.saveData.status = 1
                  break
                case "1" :
                  this.saveData.status = 2
                  break
                case "2" :
                  this.saveData.status = 3
                  break
              }
              this.saveData.id = row.id
              this.saveData.reason = ""
              update(this.saveData).then((res) => {
                if (res.code == 0) {
                  this.fetchData();
                  this.$emit('fetch-data');
                }
              })
            })
            .catch(action => {
              if (action === 'cancel') {
                this.$prompt('请输入驳回原因', '提示', {
                  confirmButtonText: '确定',
                  cancelButtonText: '取消',
                  inputErrorMessage: '格式不正确',
                })
                    .then(({value}) => {
                      this.saveData.id = row.id
                      this.saveData.reason = value
                      this.saveData.status = '5'
                      update(this.saveData).then((res) => {
                        if (res.code == 0) {
                          this.fetchData();
                          this.$emit('fetch-data');
                        }
                      })
                      this.$message({
                        type: 'success',
                        message: '已驳回，驳回原因是: ' + value,
                      })
                    })
                    .catch(() => {
                      this.$message({
                        type: 'info',
                        message: '取消输入',
                      })
                    })
              }


            })
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
    handlechuli(row) {
      this.$refs['views'].showDia(row.id)
    },
    handleDelete(row) {
      var that = this;
      if (row.id) {
        that.$baseConfirm('你确定要删除当前项吗', null, async () => {
          update({id: row.id, is_del: 1}).then((res) => {
            that.fetchData()
          })

        })
      } else {
        if (that.selectRows != '' && that.selectRows != null) {
          const ids = that.selectRows.map((item) => item.id).join()
          that.$baseConfirm('你确定要删除选中项吗', null, async () => {
            const {msg} = await update({ids})
            that.$baseMessage(msg, 'success')
            that.fetchData()
          })
        } else {
          that.$baseMessage('未选中任何行', 'error')
          return false
        }
      }
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    fetchData() {
      var that = this;
      page(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = res.data.records;
          console.log(that.list);
          that.total = res.data.total
        }
      })
    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    reseat() {
      this.queryForm.org_name = "";
      this.queryForm.org_code = "";
      this.queryForm.status = "";
      this.queryForm.type = "";
      this.queryForm.user_type = "";
      this.queryForm.fix_blng_admdvs_sbApply = "";
      this.queryData();
    },
    selectable(row, index) {
      if(row.status>=3){
        return false ;
      }else {
        return true ;
      }

    }
  },
}
</script>
