<template>
  <el-drawer :title="title" :visible.sync="dialog" direction="rtl" :with-header="false" custom-class="box_drawer"
             size="50%" ref="drawer" append-to-body>

    <div class="drawer_main">
      <div class="box_card">
        <div class="box_title">
          <span>数据查看（{{queryForm.year}}年{{queryForm.month==1?'1':'1-'+queryForm.month}}月累计及{{queryForm.month}}月当月）</span>
        </div>
      </div>
    </div>
    <div class="drawer_content">
      <div class="box_card">
        <div class="box_header">
          <span>基本情况</span>
        </div>
      </div>
      <el-form ref="form"  label-width="180px">
        <el-row :gutter="20">
          <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
            <el-form-item prop="project_name" label="所属地区：">
              {{baseInfo.tcq}}
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
            <el-form-item prop="specs" label="医疗机构编码：">
              {{baseInfo.orgCode}}
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
            <el-form-item prop="unit" label="医疗机构名称：">
              {{baseInfo.orgName}}
            </el-form-item>
          </el-col>
          <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
            <el-form-item prop="isInCategory" label="级别：">
              {{baseInfo.jb}}
            </el-form-item>
          </el-col>
        </el-row></el-form>
      <div class="box_card">
        <div class="box_header">
          <span>职工医保</span>
          <span>单位：万元</span>
        </div>
      </div>
      <div class="drawer_main">
        <el-table :data="list_zg" border stripe class="w" :element-loading-text="elementLoadingText" ref="table_zg"
                  height="50vh">
          <el-table-column label="" align="center" prop="key"></el-table-column>
          <el-table-column :label="tableTitle.total" align="center" prop="total"></el-table-column>
          <el-table-column :label="tableTitle.current" align="center" prop="current"></el-table-column>
        </el-table>
      </div>
      <div class="box_card">
        <div class="box_header">
          <span>居民医保</span>
          <span>单位：万元</span>
        </div>
      </div>
      <div class="drawer_main">
        <el-table :data="list_jm" border stripe class="w" :element-loading-text="elementLoadingText" ref="table_jm"
                  height="50vh">
          <el-table-column label="" align="center" prop="key"></el-table-column>
          <el-table-column :label="tableTitle.total" align="center" prop="total"></el-table-column>
          <el-table-column :label="tableTitle.current" align="center" prop="current"></el-table-column>
        </el-table>
      </div>
      <div style="height: 36px " class=""></div>
      <div class="drawer_footer">
        <el-button type="primary" @click="handleDownLoad">下载数据</el-button>
        <el-button @click="cancelForm">关 闭</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
  import { detailInfo } from '@/api/qs.js';
  import { approNoticeViewDataDetail,approNoticeDownloadDataDetail } from '@/api/disburse.js';
  export default {
    name: 'useredit',
    components: { },
    data() {
      return {
        list_zg: [
          {key:'发生总费用',total:'12',current:"12"},
          {key:'统筹基金发生金额',total:'12',current:"12"},
          {key:'大病基金发生金额',total:'12',current:"12"},
          {key:'公务员基金发生金额',total:'12',current:"12"},
          {key:'救助基金发生金额',total:'12',current:"12"},
          {key:'伤残基金发生金额',total:'12',current:"12"},
          {key:'个账基金发生金额',total:'12',current:"12"},
          {key:'现金',total:'12',current:"12"},
          {key:'结算小计',total:'12',current:"12"},
          {key:'统筹基金结算金额',total:'12',current:"12"},
          {key:'大病基金结算金额',total:'12',current:"12"},
          {key:'公务员基金结算金额',total:'12',current:"12"},
          {key:'救助基金结算金额',total:'12',current:"12"},
          {key:'伤残基金结算金额',total:'12',current:"12"},
          {key:'个账基金结算金额',total:'12',current:"12"},
          {key:'基金拨付小计',total:'12',current:"12"},
          {key:'基金拨付金额',total:'12',current:"12"},
          {key:'考核保证金',total:'12',current:"12"},
          {key:'扣款',total:'12',current:"12"},
        ],
        list_jm: [
          {key:'发生总费用',total:'12',current:"12"},
          {key:'统筹基金发生金额',total:'12',current:"12"},
          {key:'大病基金发生金额',total:'12',current:"12"},
          {key:'救助基金发生金额',total:'12',current:"12"},
          {key:'共济账户发生金额',total:'12',current:"12"},
          {key:'现金',total:'12',current:"12"},
          {key:'结算小计',total:'12',current:"12"},
          {key:'统筹基金结算金额',total:'12',current:"12"},
          {key:'大病基金结算金额',total:'12',current:"12"},
          {key:'救助基金结算金额',total:'12',current:"12"},
          {key:'共济账户结算金额',total:'12',current:"12"},
          {key:'基金拨付小计',total:'12',current:"12"},
          {key:'基金拨付金额',total:'12',current:"12"},
          {key:'大病商保拨付金额',total:'12',current:"12"},
          {key:'考核保证金',total:'12',current:"12"},
          {key:'扣款',total:'12',current:"12"},
        ],
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
        title: '数据查看',
        user_type: '',
        dialog: false,
        loading: false,
        isCheck: false,
        formLabelWidth: '100px',
        timer: null,
        multipleSelection: [],
        saveData: {},
        baseInfo:{},
        label_data:{
          zgLjFszfy:'发生总费用',
          zgLjTcjjfsje:'统筹基金发生金额',
          zgLjDbjjfsje:'大病基金发生金额',
          zgLjGwyjjfsje:'公务员基金发生金额',
          zgLjJzjjfsje:'救助基金发生金额',
          zgLjScjjfsje:'伤残基金发生金额',
          zgLjGzjjfsje:'个账基金发生金额',
          zgLjXj:'现金',
          zgLjJsxj:'结算小计',
          zgLjTcjjjsje:'统筹基金结算金额',
          zgLjDbjjjsje:'大病基金结算金额',
          zgLjGwyjjjsje:'公务员基金结算金额',
          zgLjJzjjjsje:'救助基金结算金额',
          zgLjScjjjsje:'伤残基金结算金额',
          zgLjGzjjjsje:'个账基金结算金额',
          zgLjJjbfxj:'基金拨付小计',
          zgLjJjbfje:'基金拨付金额',
          zgLjKhbzj:'考核保证金',
          zgLjKk:'扣款',

          zgDyFszfy:'发生总费用',
          zgDyTcjjfsje:'统筹基金发生金额',
          zgDyDbjjfsje:'大病基金发生金额',
          zgDyGwyjjfsje:'公务员基金发生金额',
          zgDyJzjjfsje:'救助基金发生金额',
          zgDyScjjfsje:'伤残基金发生金额',
          zgDyGzjjfsje:'个账基金发生金额',
          zgDyXj:'现金',
          zgDyJsxj:'结算小计',
          zgDyTcjjjsje:'统筹基金结算金额',
          zgDyDbjjjsje:'大病基金结算金额',
          zgDyGwyjjjsje:'公务员基金结算金额',
          zgDyJzjjjsje:'救助基金结算金额',
          zgDyScjjjsje:'伤残基金结算金额',
          zgDyGzjjjsje:'个账基金结算金额',
          zgDyJjbfxj:'基金拨付小计',
          zgDyJjbfje:'基金拨付金额',
          zgDyKhbzj:'考核保证金',
          zgDyKk:'扣款',

          jmLjFszfy:'发生总费用',
          jmLjTcjjfsje:'统筹基金发生金额',
          jmLjDbjjfsje:'大病基金发生金额',
          jmLjJzjjfsje:'救助基金发生金额',
          jmLjGjzhfsje:'共济账户发生金额',
          jmLjXj:'现金',
          jmLjJsxj:'结算小计',
          jmLjTcjjjsje:'统筹基金结算金额',
          jmLjDbjjjsje:'大病基金结算金额',
          jmLjJzjjjsje:'救助基金结算金额',
          jmLjGjzhjsje:'共济账户结算金额',
          jmLjJjbfxj:'基金拨付小计',
          jmLjJjbfje:'基金拨付金额',
          jmLjDbsbbfje:'大病商保拨付金额',
          jmLjKhbzj:'考核保证金',
          jmLjKk:'扣款',

          jmDyFszfy:'发生总费用',
          jmDyTcjjfsje:'统筹基金发生金额',
          jmDyDbjjfsje:'大病基金发生金额',
          jmDyJzjjfsje:'救助基金发生金额',
          jmDyGjzhfsje:'共济账户发生金额',
          jmDyXj:'现金',
          jmDyJsxj:'结算小计',
          jmDyTcjjjsje:'统筹基金结算金额',
          jmDyDbjjjsje:'大病基金结算金额',
          jmDyJzjjjsje:'救助基金结算金额',
          jmDyGjzhjsje:'共济账户结算金额',
          jmDyJjbfxj:'基金拨付小计',
          jmDyJjbfje:'基金拨付金额',
          jmDyDbsbbfje:'大病商保拨付金额',
          jmDyKhbzj:'考核保证金',
          jmDyKk:'扣款',
        },
        obj_zg:{
          发生总费用:{zgLjFszfy:'', zgDyFszfy:'',total:'',current:''},
          统筹基金发生金额:{zgLjTcjjfsje:"", zgDyTcjjfsje:"",total:'',current:''},
          大病基金发生金额:{zgLjDbjjfsje:'',zgDyDbjjfsje:'',total:'',current:''},
          公务员基金发生金额:{zgLjGwyjjfsje:'',zgDyGwyjjfsje:'',total:'',current:''},
          救助基金发生金额:{zgLjJzjjfsje:'',zgDyJzjjfsje:'',total:'',current:''},
          伤残基金发生金额:{zgLjScjjfsje:'',zgDyScjjfsje:'',total:'',current:''},
          个账基金发生金额:{zgLjGzjjfsje:'',zgDyGzjjfsje:'',total:'',current:''},
          现金:{zgLjXj:'',zgDyXj:'',total:'',current:''},
          结算小计:{zgLjJsxj:'',zgDyJsxj:'',total:'',current:''},
          统筹基金结算金额:{zgLjTcjjjsje:'',zgDyTcjjjsje:'',total:'',current:''},
          大病基金结算金额:{zgLjDbjjjsje:'',zgDyDbjjjsje:'',total:'',current:''},
          公务员基金结算金额:{zgLjGwyjjjsje:'',zgDyGwyjjjsje:'',total:'',current:''},
          救助基金结算金额:{zgLjJzjjjsje:'',zgDyJzjjjsje:'',total:'',current:''},
          伤残基金结算金额:{zgLjScjjjsje:'',zgDyScjjjsje:'',total:'',current:''},
          个账基金结算金额:{zgLjGzjjjsje:'',zgDyGzjjjsje:'',total:'',current:''},
          基金拨付小计:{zgLjJjbfxj:'',zgDyJjbfxj:'',total:'',current:''},
          基金拨付金额:{zgLjJjbfje:'',zgDyJjbfje:'',total:'',current:''},
          考核保证金:{zgLjKhbzj:'',zgDyKhbzj:'',total:'',current:''},
          扣款:{zgLjKk:'',zgDyKk:'',total:'',current:''},
        } ,
        obj_jm:{
          发生总费用:{jmLjFszfy:'', jmDyFszfy:'',total:'',current:''},
          统筹基金发生金额:{jmLjTcjjfsje:"", jmDyTcjjfsje:"",total:'',current:''},
          大病基金发生金额:{jmLjDbjjfsje:'',jmDyDbjjfsje:'',total:'',current:''},
          救助基金发生金额:{jmLjJzjjfsje:'',jmDyJzjjfsje:'',total:'',current:''},
          共济账户发生金额:{jmLjGjzhfsje:'',jmDyGjzhfsje:'',total:'',current:''},
          现金:{jmLjXj:'',jmDyXj:'',total:'',current:''},
          结算小计:{jmLjJsxj:'',jmDyJsxj:'',total:'',current:''},
          统筹基金结算金额:{jmLjTcjjjsje:'',jmDyTcjjjsje:'',total:'',current:''},
          大病基金结算金额:{jmLjDbjjjsje:'',jmDyDbjjjsje:'',total:'',current:''},
          救助基金结算金额:{jmLjJzjjjsje:'',jmDyJzjjjsje:'',total:'',current:''},
          共济账户结算金额:{jmLjGjzhjsje:'',jmDyGjzhjsje:'',total:'',current:''},
          基金拨付小计:{jmLjJjbfxj:'',jmDyJjbfxj:'',total:'',current:''},
          基金拨付金额:{jmLjJjbfje:'',jmDyJjbfje:'',total:'',current:''},
          大病商保拨付金额:{jmLjDbsbbfje:'',jmDyDbsbbfje:'',total:'',current:''},
          考核保证金:{jmLjKhbzj:'',jmDyKhbzj:'',total:'',current:''},
          扣款:{jmLjKk:'',jmDyKk:'',total:'',current:''},
        },
        tableTitle:{
          total:"",
          current:"",
        }
      }
    },
    created() {

    },
    mounted() { },
    methods: {
      showDia(row) {
        this.title = '数据查看' + '(2024年1-12月累计)'
        this.queryForm.appropNoticeId = row.id;
        this.queryForm.tcq = row.tcq;
        this.queryForm.year = row.year;
        this.queryForm.month = row.month;
        this.tableTitle.total = `${row.year}年${row.month==1?'1':'1-'+row.month}月累计`;
        this.tableTitle.current = `${row.year}年${row.month}月当月`;
        this.fetchData();
        this.dialog = true
        this.$refs['table_zg'].doLayout();
        this.$refs['table_jm'].doLayout();
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
        approNoticeViewDataDetail(that.queryForm).then((res) => {
          if (res.code == 0) {
            that.list = res.data.records
            that.total = res.data.total;
            that.baseInfo = res.data.records[0];
            let objZglj = {};
            // let objZglj = {};
            let total = [],current=[];
            for(let o in res.data.records[0]){
              if(o.includes('zg')){
                this.obj_zg[this.label_data[o]][o] = res.data.records[0][o]
                if(o.includes('Lj')){
                  this.obj_zg[this.label_data[o]].total = res.data.records[0][o];
                }
                if(o.includes('Dy')){
                  this.obj_zg[this.label_data[o]].current = res.data.records[0][o];
                  // console.log(this.obj_zg[this.label_data[o]][o],'this.obj_zg[this.label_data[o]][o]');
                }
              }
              if(o.includes('jm')){
                this.obj_jm[this.label_data[o]][o] = res.data.records[0][o]
                if(o.includes('Lj')){
                  this.obj_jm[this.label_data[o]].total = res.data.records[0][o];
                }
                if(o.includes('Dy')){
                  this.obj_jm[this.label_data[o]].current = res.data.records[0][o];
                  // console.log(this.obj_jm[this.label_data[o]][o],'this.obj_zg[this.label_data[o]][o]');
                }
              }
            }
            // console.log(this.obj_zg,'obj_zg');
            // console.log(this.obj_jm,'obj_jm');
            this.list_zg.map(item =>{
              item.total = this.obj_zg[item.key].total;
              item.current = this.obj_zg[item.key].current;
            })
            this.list_jm.map(item =>{
              item.total = this.obj_jm[item.key].total;
              item.current = this.obj_jm[item.key].current;
              // console.log(item.key,item.total,item.current,)
            })
          }
        })

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
        approNoticeDownloadDataDetail({ appropNoticeId: this.queryForm.appropNoticeId,
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
          console.log(res);
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
      },
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
      height:auto !important;
      bottom:15px !important;
    }
  }
  .box_title {
    font-size: 16px;
    font-weight: bold;
    /* border-left: 4px solid $base-color-default;*/
    padding-left: 10px;
    color: #000;
    display:flex;
    flex-direction: row;
    justify-content:space-between;
  }
</style>
