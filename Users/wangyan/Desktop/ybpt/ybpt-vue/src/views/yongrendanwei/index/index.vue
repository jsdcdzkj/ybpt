<template>
  <div class="index-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="5" :xl="5">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">机构列表</span>
            <!-- <div class="rightul">
              <ul>
                <li>一般</li>
                <li>重要</li>
                <li>紧急</li>
              </ul>
              <vab-icon :icon="['fas', 'sync']"></vab-icon>
            </div> -->
          </div>
          <div class="divcontent">
            <el-row :gutter="20">
              <el-col :span="24">
                <!-- <el-form :inline="true" label-width="80px">
                  <el-form-item label="">
                    <el-input v-model="queryForm.title"></el-input>
                  </el-form-item>
                  <el-form-item label-width="0">
                    <el-button
                      native-type="submit"
                      type="primary"
                      @click="queryData"
                    >
                      搜索
                    </el-button>
                  </el-form-item>
                </el-form> -->
                <el-input
                    placeholder="输入关键字进行过滤"
                    v-model="filterText"
                ></el-input>
                <div class="treeborder" style="height:calc(100vh - 350px)">
                  <el-tree
                      class="filter-tree"
                      :data="dept"
                      :props="defaultProps"
                      default-expand-all
                      :filter-node-method="filterNode"
                      @node-click="handleNodeClick"
                      ref="tree"
                  >
                  <span slot-scope="{data}" :style="data.is_select == '0'?'color: #000000':'color: #009933'">
                    {{data.org_name}}
                  </span>

                  </el-tree>
                </div>

              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="19" :xl="19">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">预约规则</span>
          </div>
          <el-form :model="form" :label-width="formLabelWidth">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="预约规则">
                  <div class="border-ebeef5">

                    <el-form-item label="">
                      <el-calendar v-model="value">
                        <template
                            slot="dateCell"
                            slot-scope="{ date, data }"
                        >
                          <div
                              class="date-content"
                          >
                            <span class="text">{{ getDay(date) }}</span>

                             <span v-if="dealMyDate(date,data.day).grade == '0'" :class="{'work':true, 'disabled': timeDisabled(date)}">可预约{{dealMyDate(date,data.day).number}}人</span>
                              <span v-else-if="dealMyDate(date,data.day).grade == '1'" :class="{'rest':true, 'disabled': timeDisabled(date)}">休息</span>

                          </div>
                        </template>
                      </el-calendar>
                    </el-form-item>

                  </div>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="通用套餐">
                  <el-col
                      :span="3"
                      class="tc-con"
                      v-for="(items, index) in commonpack"
                      :class="{active:index === typepack}"
                      :key="index"
                  >
                    <el-card :body-style="{ padding: '0px' }" shadow="hover" >
                      <div class="tc-box" @click="getEntityPack(items.id,index)">
                        <el-tooltip class="item" effect="dark" :content="items.pack_name" placement="top">
                          <p>{{ items.pack_name }}</p>
                        </el-tooltip>
                        <!--<span>{{ items.pack_money }}(元)</span>-->
                        <!-- <el-button
                          class="view-btn"
                          size="mini"
                          @click="handleView($event)"
                        >
                          查看
                        </el-button> -->
                        <div class="check-btn">
                          <i class="el-icon-check"></i>
                        </div>
                      </div>
                    </el-card>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="通用套餐+赠送项目">
                  <el-col
                      :span="3"
                      class="tc-con new-tc-con"
                      v-for="(items, index) in deptpack"
                      :class="{active:index === type}"
                      :key="index"
                  >
                    <el-card :body-style="{ padding: '0px' }" shadow="hover" >
                      <div class="tc-box" @click="getEntity(items.id,index)">
                        <el-tooltip class="item" effect="dark" :content="items.pack_name" placement="top">
                          <p>{{ items.pack_name }}</p>
                        </el-tooltip>

                        <!--<span>{{ items.pack_money }}(元)</span>-->
                        <!-- <el-button
                          class="view-btn"
                          size="mini"
                          @click="handleView($event)"
                        >
                          查看
                        </el-button> -->
                        <div class="check-btn">
                          <i class="el-icon-check"></i>
                        </div>
                      </div>
                    </el-card>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="检查项目">
                  <el-tag  v-for="(item, i) in entitys" :key="i" :type="item.is_generic =='0' ?'danger':''">{{item.item_name}}</el-tag>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {getAll} from "@/api_check/yiliaojigou";
import {getRulesListByOrgId,getDeptList,getList,getEntity} from "@/api_check/taocan";

export default {
  name: 'Index',
  components: {},
  data() {
    return {
      timer: 0,
      queryForm: {
        pageNo: 1,
        pageSize: 72,
      },
      currentActive: false,
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        num: '50',
      },
      formLabelWidth: '100px',
      tytcList: [
        {
          id: 1,
          name: '套餐1',
          price: '253.22(元)',
          isActive: true,
        },
        {
          id: 2,
          name: '套餐2',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 3,
          name: '套餐3',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 4,
          name: '套餐4',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 5,
          name: '套餐5',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 6,
          name: '套餐6',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 7,
          name: '套餐7',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 8,
          name: '套餐8',
          price: '253.22(元)',
          isActive: false,
        },
      ],
      jgtcList: [
        {
          id: 10,
          name: '套餐1',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 11,
          name: '套餐2',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 12,
          name: '套餐3',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 13,
          name: '套餐4',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 14,
          name: '套餐5',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 15,
          name: '套餐6',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 16,
          name: '套餐7',
          price: '253.22(元)',
          isActive: false,
        },
        {
          id: 17,
          name: '套餐8',
          price: '253.22(元)',
          isActive: false,
        },
      ],
      resDate: [
        {date: '1999-05-23', grade: '1', number: '0'}
      ],
      value: new Date(),
      rcdata: [],
      editForm: {
        grade: '0',
        number: '0',
      },
      filterText: '',
      dept: [],
      commonpack:[],
      typepack: -1,
      deptpack:[],
      type: -1,
      entitys:[],
      defaultProps: {
        children: 'children',
        label: 'org_name',

      },

    }
  },
  created() {
    this.fetchData()
  },
  beforeDestroy() {
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    },
  },
  mounted() {
  },
  methods: {
    timeDisabled(date){
        const nowDate = new Date();
        if( date <= new Date()||date.getFullYear()>nowDate.getFullYear() ){
          return true;
        }else {
          return false;
        }
      },
    filterNode(value, data) {
      console.log(value, data);
      if (!value) return true
      return data.org_name.indexOf(value) !== -1
    },
    async getEntity(id,index){
      this.type=index;
      this.typepack= '';
      const res=await getEntity({id:id});
      console.log(res);
      this.entitys=res.data;
      console.log(this.entitys);
    },
    async getEntityPack(id,index){
      this.typepack=index;
      this.type='';
      const res=await getEntity({id:id});
      console.log(res);
      this.entitys=res.data;
      console.log(this.entitys);
    },
    async handleNodeClick(data) {
      this.type='';
      this.typepack='';
      this.resDate= [
       {date: '1999-05-23', grade: '1', number: '0'}
      ];
      // 日历
      this.getRulesList(data);
      // 机构套餐
      this.getDeptPack(data);
      // 清空项目
      this.entitys=[];
    },
    async getDeptPack(data){
      const ress=await getDeptList({pack_source:"1",org_id:data.medical_insurance_num,if_open:"1"});
      console.log(ress);
      this.deptpack=ress.data;
    },
    async getRulesList(data){
      const pp = {org_id: data.medical_insurance_num,isbook:'1',state:'0'};
      const res = await getRulesListByOrgId(pp);

      if (res.data != null && res.data.length > 0) {
        console.log(res.data);
        var arr = [];
        arr[0] = res.data[0].start_time;
        arr[1] = res.data[0].end_time;
        this.value1 = arr;
        if (res.data[0].state == "0") {
          this.form.state = "0"
        } else {
          this.form.state = "1"
        }
        for (let i = 0; i < res.data.length; i++) {
          this.calItemBackfill(new Date(res.data[i].time), res.data[i].time, i, res.data[i].limit_person-res.data[i].booking_person);
        }
      }
    },
    calItemBackfill(date, d, index, num) {
      this.editForm.day = d;

      this.rcdata = 0;
      this.rcnum = num;
      this.editForm.grade = this.rcdata;
      this.editForm.number = this.rcnum;
      this.add()
    },
    queryData() {
      this.queryForm.pageNo = 1
    },
    calItem(date, d) {
      console.log(data, d);
      this.editForm.day = d
      this.rcdata = this.dealMyDate(date, d).grade
      this.rcnum = this.dealMyDate(date, d).number
      this.editForm.grade = this.rcdata
      this.editForm.number = this.rcnum
      this.dialogVisible = true
    },

    // 标注今天日期
    getDay(date) {
      return date.getDate()
    },
    async fetchData() {
      // 机构
      const res = await getAll();
      this.dept = res.data;
      // console.log(this.dept);
      const ress=await getList({pack_source:"2",if_open:"1",if_use:"1"});
      this.commonpack=ress.data.records;

    },
    dealMyDate(date, v) {


      let len = this.resDate.length
      let res = []
      res.grade = 1 //排班0，休息1

      if(new Date(new Date().toISOString().slice(0,10))>=new Date(v)){
        return res
      }
      console.log();
      res.number = this.form.num;
      for (let i = 0; i < len; i++) {
        if (this.resDate[i].date == v) {
          res.grade = this.resDate[i].grade
          res.number = this.resDate[i].number
          res.index = i
          break
        }
      }
      return res
    },

    add() {
      var a = {
        date: this.editForm.day,
        grade: this.editForm.grade,
        number: this.editForm.number,
      }
      console.log(a);

      if (this.resDate.length > 0) {
        //find方法查询是否存在相同的数据
        let isAdd = this.resDate.find((item) => {
          if (item.date == a.date) {
            //存在相同的数据调用vue的$set方法替换value值
            this.$set(item, 'grade', a.grade)
            this.$set(item, 'number', a.number)
            //数组中的元素在测试条件时返回 true 时, find()返回符合条件的元素
            return true
          }
          //如果不存在find方法会返回一个undefined
        })
        // 判断isAdd是否为undefined
        if (typeof isAdd == 'undefined') {
          //当isAdd为undefined添加新的对象到数组中
          this.resDate.push(a)
        }
      }

    },
    callOf(formName) {
      // console.log("sadad",formName)
      // this.$refs.formName.resetFields();
      // this.$refs['formName'].clearValidate()
      this.dialogVisible = false
    },
  },
}
</script>
<style lang="scss" scoped>
.index-container {
  padding: 0 !important;
  margin: 0 !important;
  background: #f5f7f8 !important;

  ::v-deep {
    .el-tree {
      .el-tree-node {
        height: 40px;
        line-height: 40px;

        .el-tree-node__content {
          height: 40px;
        }
      }
    }

    .el-alert {
      padding: $base-padding;

      &--info.is-light {
        min-height: 82px;
        padding: $base-padding;
        margin-bottom: 15px;
        color: #909399;
        background-color: $base-color-white;
        border: 1px solid #ebeef5;
      }
    }

    .el-card__body {
      .echarts {
        width: 100%;
        height: 125px;
      }
    }
  }

  .card {
    min-height: 400px;
  }

  .cardfirst {
    min-height: 816px;
  }

  .bottom {
    padding-top: 20px;
    margin-top: 5px;
    color: #595959;
    text-align: left;
    border-top: 1px solid $base-border-color;
  }

  .table {
    width: 100%;
    color: #666;
    border-collapse: collapse;
    background-color: #fff;

    td {
      position: relative;
      min-height: 20px;
      padding: 9px 15px;
      font-size: 14px;
      line-height: 20px;
      border: 1px solid #e6e6e6;

      &:nth-child(odd) {
        width: 20%;
        text-align: right;
        background-color: #f7f7f7;
      }
    }
  }

  .icon-panel {
    height: 100px;
    text-align: center;
    cursor: pointer;

    svg {
      font-size: 40px;
    }

    p {
      margin-top: 10px;
    }
  }

  .bottom-btn {
    button {
      margin: 5px 10px 15px 0;
    }
  }

  .list {
    display: flex;
    direction: row;
    list-style: none;
    margin: 0;
    padding: 0;
    margin-left: -0.5%;
    flex-flow: row wrap;
    box-sizing: border-box;

    li {
      width: 11.5%;
      margin: 0 0.5%;
      border: 1px solid #dfdfdf;
      padding: 0px;
      list-style: none;
      border-radius: 4px;
      margin-bottom: 10px;
      box-sizing: border-box;

      .tc-box {
        padding: 14px;
        position: relative;
        text-align: center;

        p {
          padding: 0;
          margin: 0;
          width: 100%;
          height: 24px;
          text-align: center;
          line-height: 24px;
          font-weight: bold;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        span {
          display: inline-block;
          width: 100%;
          text-align: center;
        }

        .tc-delete {
          position: absolute;
          top: 6px;
          right: 6px;
          color: #e6a23c;
          font-size: 16px;
          cursor: pointer;
        }

        .check-btn {
          position: absolute;
          right: 0px;
          bottom: 0px;
          font-size: 24px;
          width: 0px;
          height: 0px;
          border-left: 46px solid transparent;
          border-bottom: 36px solid #ebeef5;

          i {
            color: #fff;
            position: absolute;
            top: 14px;
            right: 2px;
          }
        }
      }
    }

    li.active {
      border: 1px solid #1890ff !important;
      background: #f6f9fe !important;

      .check-btn {
        border-bottom: 36px solid #1890ff !important;
      }
    }
  }

  .tc-con {
    padding-left: 0 !important;

    .el-card {
      border-radius: 4px;
      text-align: center;

      .tc-box {
        padding: 14px;
        position: relative;

        p {
          padding: 0;
          margin: 0;
          width: 100%;
          height: 24px;
          line-height: 24px;
          font-weight: bold;
          overflow: hidden;
          text-overflow: ellipsis;
          white-space: nowrap;
        }

        span {
          display: inline-block;
          width: 100%;
        }

        .tc-delete {
          position: absolute;
          top: 6px;
          right: 6px;
          color: #e6a23c;
          font-size: 16px;
          cursor: pointer;
        }

        .check-btn {
          display: none;
          position: absolute;
          right: 0px;
          bottom: 0px;
          font-size: 24px;
          width: 0px;
          height: 0px;
          border-left: 26px solid transparent;
          border-bottom: 26px solid #ebeef5;

          i {
            font-size: 16px;
            color: #fff;
            position: absolute;
            top: 10px;
            right: 0px;
          }
        }
      }

      .tc-plus {
        height: 116px;
        line-height: 116px;
        font-size: 32px;
        color: #1890ff;
      }
    }
  }

  .active {
    ::v-deep {
      .el-card {
        border: 1px solid #1890ff !important;
        background: #f6f9fe !important;
      }
    }

    .check-btn {
      display: block !important;
      border-bottom: 26px solid #1890ff !important;
    }
  }

  ::v-deep {
    .prev {
      cursor: not-allowed;
      pointer-events: none;

      .el-calendar-day {
        display: none;
      }
    }

    .next {
      cursor: not-allowed;
      pointer-events: none;

      .el-calendar-day {
        display: none;
      }
    }
  }

  ::v-deep .el-calendar-day {
    height: auto;
  }

  ::v-deep .el-calendar__header {
    justify-content: flex-end;

    .el-calendar__title {
      margin-right: 60px;
    }
  }

  ::v-deep
  .el-calendar-table__row
  td::v-deep
  .el-calendar-table
  tr
  td:first-child,
  ::v-deep .el-calendar-table__row td.prev {
    border: none;
  }

  .date-content {
    height: 70px;
    text-align: center;
    line-height: 32px;
    font-size: 20px;
  }

  .date-content .rest {
    color: #fff;
    width: 100%;
    height: 30px;
    line-height: 30px;
    background: rgb(250, 124, 77);
    display: inline-block;
    font-size: 14px;
  }

  .date-content .work {
    color: #fff;
    width: 100%;
    height: 30px;
    line-height: 30px;
    background: #409eff;
    display: inline-block;
    font-size: 14px;
  }

  .date-content .text {
    width: 100%;
    height: 30px;
    line-height: 30px;
    display: inline-block;
  }

  .text-left {
    text-align: left;
  }

  .el-form-item.text-left {
    margin-left: 10px;
  }

  .pd10 {
    padding: 10px;
  }

  .border-ebeef5 {
    padding: 0px 0px 0;
    border: 1px solid #ebeef5;
  }

  .default-box {
    padding-left: 10px;
    padding-right: 10px;
    position: absolute;
    top: 30px;
    z-index: 999;
    left: 10px;
  }
  .new-tc-con{
    .el-card{
      border: 1px dashed #82a6fb;
    }
  }
  ::v-deep .el-tree-node:focus > .el-tree-node__content{
    background-color: #e6eefb;
  }
}
  .treeborder{
    overflow-x: auto;
  }
</style>

