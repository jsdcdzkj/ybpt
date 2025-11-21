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
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="年份" prop="year">
                  <el-date-picker clearable type="year" format="yyyy" value-format="yyyy"
                                  v-model="queryForm.year"></el-date-picker>
                </el-form-item>
              </el-col>

              <!--<el-col :lg="8" :md="8" :sm="24" :xl="8" :xs="24">-->
                <!--<el-form-item label="统筹区">-->
                  <!--<el-select v-model="queryForm.admdvs" clearable-->
                             <!--style="width: 100%">-->
                    <!--<el-option v-for="item in admdvs" :key="item.value" :label="item.label"-->
                               <!--:value="item.value">-->
                    <!--</el-option>-->
                  <!--</el-select>-->
                <!--</el-form-item>-->
              <!--</el-col>-->

              <!--<el-upload ref="upfile"-->
                         <!--:auto-upload="false" :show-file-list=false accept=".xlsx"-->
                         <!--:on-change="handleChange2"-->
                         <!--:on-success="handleSuccess" action="#">-->
                <!--<el-button icon="el-icon-upload2" type="success">更新上传</el-button>-->
              <!--</el-upload>-->
            </el-row>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card tablecard" shadow="never">
          <div slot="header">
            <span class="tips">资金清算信息列表</span>
            <div class="right">
              <el-button type="primary"  @click="upload">
                上传数据
              </el-button>
            </div>
          </div>
          <el-table ref="listTable" stripe :data="list" :element-loading-text="elementLoadingText" highlight-current-row
            border @current-change="handleCurrentChange" @selection-change="setSelectRows" height="calc(100% - 50px)"
            v-loading="listLoading">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!--<el-table-column-->
            <!--show-overflow-tooltip-->
            <!--type="selection"-->
            <!--fixed="left"-->
            <!--&gt;</el-table-column>-->
            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" width="80px" fixed="left">
              <template slot-scope="scope">
                <span v-text="getIndex(scope.$index)"> </span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="年份" align="center" prop="year" >
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="admdvs"  label="统筹区" align="center" ></el-table-column>
            <el-table-column show-overflow-tooltip prop="pub_status" label="发布状态" align="center" >
              <template #default="{ row }">
                <el-tag type="success"  :key="index" v-if="row.pub_status == 1">
                  待发布
                </el-tag>
                <el-tag type="warning"  :key="index" v-else-if="row.pub_status == 2 ">
                  已发布
                </el-tag>
              </template>
            </el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="280" align="center" fixed="right">

              <template #default="{ row }">
                <el-button plain @click="handleAdd(row)" type="primary" size="mini"
                           style="left:0px ;">明细
                </el-button>
                <el-button plain @click="generate(row)" type="primary" size="mini"
                           style="left:0px ;" v-if="row.pub_status == 1">生成确认书
                </el-button>
                <el-button plain @click="handleDelete(row)" type="danger" size="mini" v-if="row.pub_status == 1">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <usermana ref="usermana" @fetch-data="fetchData"></usermana>
    <usershenhe ref="usershenhe" @fetch-data="fetchData"></usershenhe>
    <upload ref="upload" @fetch-data="fetchData"></upload>
  </div>

</template>

<script>
import {
  selectList,delQs,createConfirming
} from '@/api/qs.js'
import { relocatedConfig } from '@/api_check/relocatedInfo'
import upload from './components/components/upload'
import Usershenhe from './components/usershenhe'
import {getDicts} from "@/api/dictManagement";
export default {
  name: 'Index',
  components: { Usershenhe ,upload },
  data() {
    return {
      value1: '',
      checked: false,
      isShow: false,
      list: null,
      listLoading: false,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      admdvs: null,
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        admdvs: "",
      },
      user_type: "",
      isOpen: true,
      form: {
        isOpen: '0'
      },
      show: false,
      isCheck: false,
      userinfo: {},
      num: "",
    }
  },
  created() {
    var that = this;
    that.getAdmdvs();
    that.fetchData();
  },
  beforeDestroy() {
  },
  mounted() {
  },
  methods: {

    setSelectRows(val) {
      this.selectRows = val
    },
    handleCurrentChange(val) {
      this.selectRows = val
    },
    upload(row) {
      this.$refs['upload'].showDia()
    },
    handleAdd(row) {
      if (row.id) {
        this.$refs['usershenhe'].showDia(row)
      }
    },


    handleUser() {
      this.$refs['usermana'].showDia()
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handleDelete(row) {
      var that = this;
      if (row.id) {
        that.$baseConfirm('你确定要删除当前项吗', null, async () => {
          delQs(row).then((res) => {
            if (res.code == 0) {
              that.fetchData()
              that.$baseMessage('操作成功', 'success')
            } else {
              that.$baseMessage(res.msg, 'error')
            }
          })
        })
      }
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },

    fetchData() {
      var that = this;
      selectList(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = res.data.records;
          that.total = res.data.total
        }
      })

    },
     getAdmdvs() {
      getDicts({"type": "admdvs-area"}).then((res) => {
        if (res.code == 0) {
          this.admdvs = res.data;
          console.log(this.admdvs);
        }
      })

    },
    //获取表格序号
    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    reseat() {
      this.queryForm.admdvs = "";
      this.queryForm.year = "";
      this.fetchData();
    },
    generate(row){
      this.$baseConfirm('确定要生成确认书吗', null, async () => {
        const loading = this.$loading({
          lock: true,
          text: '正在生成确认书······',
          spinner: 'el-icon-loading',
          background: 'rgba(0, 0, 0, 0.7)'
        });
        createConfirming(row.id).then((res) => {
          if (res.code == 0) {
            loading.close();
            this.fetchData()
            this.$baseMessage('操作成功', 'success')
          }
        })
      })

    },
    async handleChange2(file, fileList) {
      this.dialog = true
      this.loading = true
      this.fileList = fileList;
      let fd = new FormData();
      fd.append("admdvs", this.selectRows.id);
      this.fileList.forEach(item => {
        //文件信息中raw才是真的文件
        fd.append("file", item.raw);
      })

      var result = await importData2(fd);
      if (result.data.code == 0) {
        this.$baseMessage("上传成功", 'success')
      } else {
        this.$baseMessage(result.data.msg, 'error')
      }
      this.dialog = false
      this.loading = false
    },
    handleSuccess() {
      this.fetchData()
    },

  },
}
</script>
<style>
.item {
  margin-right: 20px;
  margin-left: 20px;
}

.badge-item .el-badge__content.is-fixed {
  top: 2px;
}
</style>