<template>
  <el-drawer :title="title" :visible.sync="dialog" direction="rtl" :with-header="false" custom-class="box_drawer"
    size="80%" ref="drawer" append-to-body>
    <div class="drawer_main">
      <div class="box_card">
        <div class="box_title">
          <span>拨付分析（{{ queryForm.year }}年1-{{ queryForm.month }}月累计分析）</span>
        </div>
      </div>
    </div>
    <div class="drawer_content">
      <div class="box_card">
        <div class="box_header">
          <span>基本情况</span>
        </div>
      </div>
      <el-form ref="form" label-width="180px">
        <el-row :gutter="20">
          <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
            <el-form-item prop="project_name" label="所属地区：">
              {{ baseInfo.tcq }}
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
            <el-form-item prop="specs" label="医疗机构编码：">
              {{ baseInfo.orgCode }}
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
            <el-form-item prop="unit" label="医疗机构名称：">
              {{ baseInfo.orgName }}
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
            <el-form-item prop="isInCategory" label="级别：">
              {{ baseInfo.jb }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="box_card">
        <div class="box_header">
          <span>职工医保（单位：万元）</span>
        </div>
      </div>
      <!--      </el-card>-->
      <div class="drawer_main">
        <el-table :data="list" border stripe class="w" :element-loading-text="elementLoadingText" ref="table"
          height="calc(50vh - 280px)">
          <el-table-column label="医疗总费用" align="center">
            <el-table-column show-overflow-tooltip prop="zgYlzfyDqljfse" label="当期累计发生额" width="160px"
              align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="zgYlzfyTqljfse" label="上年同期累计发生额" align="center"
              width="160px"></el-table-column>
            <el-table-column prop="zgYlzfyTb" label="同比" width="160px" align="center">
              <template #default="{ row }">
                <span v-if="row.zgYlzfyTb != null">{{ row.zgYlzfyTb }}%</span>
              </template>
            </el-table-column>
            <el-table-column prop="zgYlzfyBnzb" label="本年占比" width="160px" align="center">
              <template #default="{ row }">
                <span v-if="row.zgYlzfyBnzb != null">{{ row.zgYlzfyBnzb }}%</span>
              </template>
            </el-table-column>

          </el-table-column>
          <el-table-column label="统筹基金" align="center">
            <el-table-column show-overflow-tooltip prop="zgTcjjDqljfse" label="当期累计发生额" width="160px"
              align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="zgTcjjTqljfse" label="上年同期累计发生额" align="center"
              width="160px"></el-table-column>
            <el-table-column prop="zgTcjjTb" label="同比" width="160px" align="center">
              <template #default="{ row }">
                <span v-if="row.zgTcjjTb != null">{{ row.zgTcjjTb }}%</span>
              </template>
            </el-table-column>
            <el-table-column prop="zgTcjjBnzb" label="本年占比" width="160px" align="center">
              <template #default="{ row }">
                <span v-if="row.zgTcjjBnzb != null">{{ row.zgTcjjBnzb }}%</span>
              </template>
            </el-table-column>

          </el-table-column>
          <el-table-column label="应结付金额" align="center" prop="zgYjfje" width="160px"></el-table-column>
          <el-table-column label="已结付额" align="center" prop="zgYjfe" width="160px"></el-table-column>
        </el-table>

      </div>
      <div class="box_card">
        <div class="box_header">
          <span>居民医保（单位：万元）</span>
        </div>
      </div>
      <div class="drawer_main">
        <el-table :data="list" border stripe class="w" :element-loading-text="elementLoadingText" ref="table"
          height="calc(50vh - 280px)">
          <el-table-column label="医疗总费用" align="center">
            <el-table-column show-overflow-tooltip prop="jmYlzfyDqljfse" label="当期累计发生额" width="160px"
              align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="jmYlzfyTqljfse" label="上年同期累计发生额" align="center"
              width="160px"></el-table-column>
            <el-table-column prop="jmYlzfyTb" label="同比" width="160px" align="center">
              <template #default="{ row }">
                <span v-if="row.jmYlzfyTb != null">{{ row.jmYlzfyTb }}%</span>
              </template>
            </el-table-column>
            <el-table-column prop="jmYlzfyBnzb" label="本年占比" width="160px" align="center">
              <template #default="{ row }">
                <span v-if="row.jmYlzfyBnzb != null">{{ row.jmYlzfyBnzb }}%</span>
              </template>
            </el-table-column>
          </el-table-column>
          <el-table-column label="统筹基金" align="center">
            <el-table-column show-overflow-tooltip prop="jmTcjjDqljfse" label="当期累计发生额" width="160px"
              align="center"></el-table-column>
            <el-table-column show-overflow-tooltip prop="jmTcjjTqljfse" label="上年同期累计发生额" align="center"
              width="160px"></el-table-column>
            <el-table-column prop="jmTcjjTb" label="同比" width="160px" align="center">
              <template #default="{ row }">
                <span v-if="row.jmTcjjTb != null">{{ row.jmTcjjTb }}%</span>
              </template>
            </el-table-column>
            <el-table-column prop="jmTcjjBnzb" label="本年占比" width="160px" align="center">
              <template #default="{ row }">
                <span v-if="row.jmTcjjBnzb != null">{{ row.jmTcjjBnzb }}%</span>
              </template>
            </el-table-column>
          </el-table-column>
          <el-table-column label="应结付金额" align="center" prop="jmYjfje" width="160px"></el-table-column>
          <el-table-column label="已结付额" align="center" prop="jmYjfe" width="160px"></el-table-column>
        </el-table>
      </div>

      <div class="drawer_footer">
        <el-button type="primary" @click="handleDownLoad">下载数据</el-button>
        <el-button @click="cancelForm">关 闭</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
import { detailInfo } from '@/api/qs.js';
import { approNoticeViewDetailAnalyse, approNoticeDownloadDetailAnalyse } from '@/api/disburse.js';

export default {
  name: 'useredit',
  components: {},
  data() {
    return {
      list: [],
      form: [],
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      selectRows: '',
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        appropNoticeId: "",
        tcq: "",
        year: "",
        month: "",
        orgCode: "",
      },
      title: '拨付分析',
      user_type: '',
      dialog: false,
      loading: false,
      isCheck: false,
      formLabelWidth: '100px',
      timer: null,
      multipleSelection: [],
      saveData: {},
      baseInfo: {},
    }
  },
  created() {

  },
  mounted() {
  },
  methods: {
    showDia(row) {
      this.title = '拨付分析' + '(2024年1-12月累计)'
      // this.table_title_add = row.year + '年1-' + row.month + '月累计'
      // this.table_title_add = row.year + '年' + row.month + '月当月'

      this.queryForm.appropNoticeId = row.id;
      this.queryForm.tcq = row.tcq;
      this.queryForm.year = row.year;
      this.queryForm.month = row.month;
      this.fetchData()
      this.dialog = true
      this.$refs['table'].doLayout();
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    fetchData() {
      var that = this
      approNoticeViewDetailAnalyse(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.list = res.data.records
          that.total = res.data.total
          if (that.list.length > 0) {
            that.baseInfo = that.list[0];
          }
        } else {
          this.$message.error(res.msg)
        }
      })
      // {
      //   zgYlzfyDqljfse:'当期累计发生额',
      //   zgYlzfyTqljfse:'上年同期累计发生额',
      //   zgYlzfyTb:'同比',
      //   zgYlzfyBnzb:'本年占比',
      //   zgTcjjDqljfse:'当期累计发生额',
      //   zgTcjjTqljfse:'上年同期累计发生额',
      //   zgTcjjTb:'同比',
      //   zgTcjjBnzb:'上年同期累计发生额',
      //   zgYjfje:'应结付金额',
      //   zgYjfe:'已结付额',
      // }
      // let zgArr = [
      //   'zgYlzfyDqljfse',
      //   'zgYlzfyTqljfse',
      //   'zgYlzfyTb',
      //   'zgYlzfyBnzb',
      //   'zgTcjjDqljfse',
      //   'zgTcjjTqljfse',
      //   'zgTcjjTb',
      //   'zgTcjjBnzb',
      //   'zgYjfje',
      //   'zgYjfe',
      // ]
      // let jmArr = [
      //   'jmYlzfyDqljfse',
      //   'jmYlzfyTqljfse',
      //   'jmYlzfyTb',
      //   'jmYlzfyBnzb',
      //   'jmTcjjDqljfse',
      //   'jmTcjjTqljfse',
      //   'jmTcjjTb',
      //   'jmTcjjBnzb',
      //   'jmYjfje',
      //   'jmYjfe',
      // ]


      // {key:'zgYlzfyDqljfse',label:'当期累计发生额',value:'',}
      // {key:'zgYlzfyTqljfse',label:'上年同期累计发生额',value:'',}
      // {key:'zgYlzfyTb',label:'同比',value:'',}
      // {key:'zgYlzfyBnzb',label:'本年占比',value:'',}
      // {key:'zgTcjjDqljfse',label:'当期累计发生额',value:'',}
      // {key:'zgTcjjTqljfse',label:'上年同期累计发生额',value:'',}
      // {key:'zgTcjjTb',label:'同比',value:'',}
      // {key:'zgTcjjBnzb',label:'上年同期累计发生额',value:'',}
      // {key:'zgYjfje',label:'应结付金额',value:'',}
      // {key:'zgYjfe',label:'已结付额',value:'',}


      // {key:'jmYlzfyDqljfse',label:'当期累计发生额',value:'',}
      // {key:'jmYlzfyTqljfse',label:'上年同期累计发生额',value:'',}
      // {key:'jmYlzfyTb',label:'同比',value:'',}
      // {key:'jmYlzfyBnzb',label:'本年占比',value:'',}
      // {key:'jmTcjjDqljfse',label:'当期累计发生额',value:'',}
      // {key:'jmTcjjTqljfse',label:'上年同期累计发生额',value:'',}
      // {key:'jmTcjjTb',label:'同比',value:'',}
      // {key:'jmTcjjBnzb',label:'本年占比',value:'',}
      // {key:'jmYjfje',label:'应结付金额',value:'',}
      // {key:'jmYjfe',label:'已结付额',value:'',}
    },
    cancelForm() {
      // this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
    },
    reseat() {
      this.queryForm.type = ''
      this.queryForm.org_code = ''
      this.queryForm.org_name = ''
      this.fetchData()
    },
    handleDownLoad() {
      console.log('approNoticeDownloadDetailAnalyse');
      approNoticeDownloadDetailAnalyse({
        appropNoticeId: this.queryForm.appropNoticeId,
        tcq: this.queryForm.tcq,
        year: this.queryForm.year,
        month: this.queryForm.month
      }).then((res) => {
        this.loading = true
        const contentDisposition = res.headers['content-disposition']// 从response的headers中获取filename, 后端response.setHeader("Content-disposition", "attachment; filename=xxxx.docx") 设置的文件名;
        if (contentDisposition) {
          var fileName = contentDisposition.split('=')[1]
          fileName = fileName.substr(0, fileName.length)
          window.localStorage.setItem('fileName', decodeURI(fileName))
        }
        const link = document.createElement("a");
        let blob = new Blob([res.data], {
          type: "application/vnd.ms-excel;charset=utf-8",
        });
        link.style.display = "none";
        link.href = URL.createObjectURL(blob);
        link.download = window.localStorage.getItem('fileName'); // 下载的文件名
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
        this.loading = false;
      })
    }
  },
}
</script>
<style lang="scss" scoped>
.drawer_content {
  padding: 20px;
}

.drawer_main {
  padding: 20px 0;
}


::v-deep {

  .el-table {
    th.el-table__cell {
      background-color: #f2f8fd !important;
    }
  }

  .el-table th.add-table-title {
    background-color: #fff1e5 !important;
  }

  .el-table th.current-table-title {
    background-color: #fffee5 !important;
  }

  .el-table th.current-table-title-parent {
    background-color: #e6ecf9 !important;
  }

  // tab切换时出现蓝色边框问题解决
  .el-tabs__item:focus.is-active.is-focus:not(:active) {
    box-shadow: none !important; //切换阴影
    border-radius: 0 !important;
  }

  .el-table__fixed-right,
  .el-table__fixed {
    height: auto !important;
    bottom: 15px !important;
  }
}

/*span.tips{*/
/*  font-size: 16px;*/
/*  color: #000;*/
/*  font-weight: bold;*/
/*  position: relative;*/
/*  padding-left: 15px;*/
/*  &:before{*/
/*    position: absolute;*/
/*    left: 0px;*/
/*    top: 3px;*/
/*    height: 17px;*/
/*    width: 5px;*/
/*    border-radius: 5px;*/
/*    background: #1890ff;*/
/*    content: "";*/
/*  }*/

/*}*/
.new-tc-con .el-card {
  border: 1px dashed #82a6fb;
}

.box_title {
  font-size: 16px;
  font-weight: bold;
  /* border-left: 4px solid $base-color-default;*/
  padding-left: 10px;
  color: #000;
  display: flex;
  flex-direction: row;
  justify-content: space-between;
}
</style>
