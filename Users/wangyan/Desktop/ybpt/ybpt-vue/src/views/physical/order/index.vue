<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">预约设置</span>
          </div>
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" style="padding-bottom: 15px">
                <el-form-item label="预约状态" class="content-left">
                  <el-radio-group v-model="form.state">
                    <el-radio label="0">启用</el-radio>
                    <el-radio label="1">禁用</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="通用套餐">
                  <el-col class="tc-con" :xs="4" :sm="4" :md="4" :lg="3" :xl="3" v-for="(o, index) in commonMenu"
                    :key="index">
                    <el-card :body-style="{ padding: '0px' }" shadow="hover">
                      <el-tooltip class="item" effect="dark" :content="o.pack_name" placement="top">
                        <div class="tc-box">
                            <p>{{ o.pack_name }}</p>
                          <!--<span>{{o.pack_money}}(元)</span>-->
                          <el-button class="view-btn" size="mini" @click="tcView(o)">查看</el-button>
                        </div>
                      </el-tooltip>
                    </el-card>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="通用套餐+赠送项目" prop="password">
                  <el-col class="tc-con new-tc-con" :xs="4" :sm="4" :md="4" :lg="3" :xl="3"
                    v-for="(o, index) in orgMenu" :key="index">
                    <el-card :body-style="{ padding: '0px' }" shadow="hover">
                      <el-tooltip class="item" effect="dark" :content="o.pack_name" placement="top">
                      <div class="tc-box">
                        <i class="el-icon-delete tc-delete" @click="deltc(o)"></i>
                        <p>{{ o.pack_name }}</p>
                        <!--<span>{{o.pack_money}}(元)</span>-->
                        <el-button class="view-btn" size="mini" @click="tcView(o)">查看</el-button>
                      </div>
                      </el-tooltip>
                    </el-card>
                  </el-col>
                  <el-col class="tc-con" :xs="4" :sm="4" :md="4" :lg="3" :xl="3">
                    <el-card :body-style="{ padding: '0px' }" shadow="hover">
                      <div class="tc-plus" @click="handleAdd">
                        <i class="el-icon-circle-plus"></i>
                      </div>
                    </el-card>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="预约规则" class="pd10">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" class="border-ebeef5">
                    <el-col :xs="18" :sm="18" :md="18" :lg="18" :xl="18" class="default-box">
                      <el-form-item label="默认每天匹配" style="text-align: left">

                        <el-input v-model="form.num" placeholder="人数"
                          style="width: 100px; margin-right: 10px;vertical-align: middle"><template
                            slot="append">人</template></el-input>
                        <el-date-picker v-model="value1" type="datetimerange" range-separator="至"
                          start-placeholder="开始日期" end-placeholder="结束日期" format="yyyy-MM-dd HH:mm:ss"
                          value-format="yyyy-MM-dd HH:mm:ss" style="margin-right: 10px; vertical-align: middle"
                          :picker-options="expireTimeOption">
                          <!--@input="searchSelect"
                          >-->
                        </el-date-picker>

                        <el-button type="primary" style="vertical-align: middle" @click="changeDefaultNum()" plain>配置默认值
                        </el-button>

                      </el-form-item>


                    </el-col>
                    <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                      <el-form-item label="">
                        <el-calendar v-model="value" optionsmindate="new Date()">
                          <template slot="dateCell" slot-scope="{ date, data }">
                            <div class="date-content" @click="calItem(date, data.day)">
                              <span class="text">{{ getDay(date) }}</span>
                              <span v-if="dealMyDate(date, data.day).grade == '0'"
                                :class="{ 'work': true, 'disabled': timeDisabled(date) }">可预约{{ dealMyDate(date,data.day).number}}人,已预约{{ dealMyDate(date,data.day).remainNum}}人
                              </span>
                              <span v-else :class="{ 'rest': true, 'disabled': timeDisabled(date) }">休息</span>
                            </div>
                          </template>
                        </el-calendar>
                      </el-form-item>
                    </el-col>
                  </el-col>
                </el-form-item>
              </el-col>


              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item>
                  <el-button type="primary" :disabled="disabled" @click="onSubmit" icon="el-icon-check">确认</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>

        </el-card>
      </el-col>
    </el-row>
    <el-dialog :title="editForm.day" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
      <el-form @submit.native.prevent ref="editForm" :model="editForm" :rules="editRules" label-width="120px">
        <el-form-item label="日期性质：" prop="grade">
          <el-radio-group v-model="editForm.grade">
            <el-radio :label="0">工作日</el-radio>
            <el-radio :label="1">休息</el-radio>
          </el-radio-group>
        </el-form-item>

        <el-form-item v-if="editForm.grade == 0" label="安排人数：" prop="number">
          <el-input v-model.number="editForm.number" placeholder="人数"></el-input>
        </el-form-item>


      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="callOf('editForm')">取 消</el-button>
        <el-button type="primary" @click="addRe('editForm')">
          确 定
        </el-button>
      </span>
    </el-dialog>

    <edit ref="edit" @fetch-data="reloadOrg"></edit>
    <views ref="views"></views>
  </div>

</template>

<script>
import Edit from './components/edit';
import Views from './components/views';
// import { getList, doDelete } from '@/api/userManagement'

import { addCommit, getList, getOrgSubscribeRules, getSelectOrgList, setOrgOption } from "@/api_check/taocan";
export default {
  name: 'order',
  components: { Edit, Views },
  data() {
    return {
      expireTimeOption: {
        disabledDate(date) {
          return date.getTime() <= Date.now() ;
        }
      },
      // mindate:
      dynamicTags: ['项目一', '项目二', '项目三'],
      inputVisible: false,
      inputValue: '',
      value1: '',
      form: {
        state: '0',
        num: '200',
        // email: '',
        // permissions: [],
        start_time: '',
        end_time: '',
        dateArray: '',
      },
      rules: {
        value1: [
          { required: true, trigger: 'blur', message: '预约时间范围' },
        ],
        state: [
          { required: true, trigger: 'blur', message: '预约状态' },
        ],
      },
      title: '',
      dialogFormVisible: false,
      resDate: [
        { date: '2022-05-23', grade: '0', number: '200',remainNum:'0' },
        { date: '2022-05-22', grade: '1', number: '0',remainNum:'0' },
      ],
      value: new Date(),
      dialogVisible: false,
      rcdata: [],
      editForm: {
        grade: '0',
        number: '0'
      },
      disabled:false,
      editRules: {
        grade: [
          { required: true, trigger: 'blur', message: '必选项目' },
        ],
        number: [
          { required: true, message: "请输入内容", trigger: 'blur' },
          { pattern: /^\+?[1-9]\d*$/, message: '请输入大于0的正整数' }
        ],
      },
      commonMenu: [],
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        pack_source: '2',//套餐来源 (机构:1，通用:2)
      },
      orgMenu: [],
      orgQueryForm: {
        pageNo: 1,
        pageSize: 10,
        pack_year: "",
        ids: {},
        verify_ids: {},
        if_open: '1',//机构上架的数报
        pack_source: '1',//套餐来源 (机构:1，通用:2)
      },
    }
  },
  created() {
    this.fetchData();
    this.fetchOrgData();
    this.getOrgSubscribeRulesData() //回显规则
  },
  methods: {
    timeDisabled(date) {
      const nowDate = new Date();
      if (date <= new Date() ) {
        return true;
      } else {
        return false;
      }
    },
    deltc(e) {
      this.queryForm.ids = e.id;
      this.queryForm.flag = "F";
      console.log("queryForm.ids" + this.queryForm);
      setOrgOption(this.queryForm);
      setTimeout(() => {
        this.fetchOrgData();
        this.queryForm.flag = "";
        this.queryForm.ids == "";
      }, 1000)

    },
    reloadOrg() {
      this.fetchOrgData()
    },
    handleAdd() {
      this.$refs['edit'].showDia()
    },
    tcView(row) {
      this.$refs['views'].showDia(row)
    },

    changeDefaultNum() {
      this.searchSelect(this.value1);
    },
    searchSelect(val) {//修改每天默认人数
      var i = 0;
      for (var loopDate = new Date(val[0]).getTime(); loopDate <= new Date(val[1]).getTime(); loopDate += 86400000) {
        var dateCurrent = new Date(loopDate);

        var month = dateCurrent.getMonth() + 1;
        var strDate = dateCurrent.getDate();
        if (month >= 1 && month <= 9) {
          month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
          strDate = "0" + strDate;
        }
        var currentDateStr = dateCurrent.getFullYear() + "-" + month + "-" + strDate;

        this.calItemSet(dateCurrent, currentDateStr, i);
        i = i + 1;
      }
    },
    onSubmit() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          console.log(this.resDate);
          this.disabled = true;
          setTimeout(()=>{
            this.disabled = false;
          },5000)
          this.form.start_time = this.value1[0];
          this.form.end_time = this.value1[1];
          this.form.dateArray = JSON.stringify(this.resDate);
          // const { msg } = await doEdit(this.form)
          // this.$baseMessage(msg, 'success')
          // this.$emit('fetch-data')
          console.log(this.form);
          const { msg } = await addCommit(this.form);
          this.$baseMessage('保存成功', 'success')
          // this.close()
        } else {
          return false
        }
      })
    },

    calItem(date, d) {
      const aDate = new Date();
      if (aDate >= date ) return;
      this.editForm.day = d;
      this.rcdata = this.dealMyDate(date, d).grade;
      this.rcnum = this.dealMyDate(date, d).number;
      this.remainNum = this.dealMyDate(date, d).remainNum;
      this.editForm.grade = this.rcdata;
      this.editForm.number = this.rcnum;
      this.editForm.remainNum = this.remainNum;
      this.dialogVisible = true
    },

    // 标注今天日期
    getDay(date) {
      return date.getDate()
    },

    dealMyDate(date, v) {
      let len = this.resDate.length;
      let res = [];
      res.grade = 1; //排班0，休息1
      res.number = this.form.num;
      for (let i = 0; i < len; i++) {

        if (this.resDate[i].date == v) {
          res.grade = this.resDate[i].grade;
          res.number = this.resDate[i].number;
          res.remainNum = this.resDate[i].remainNum;
          res.index = i;
          break
        }
      }
      return res
    },
    calItemBackfill1(date, d, index, num) {
      this.editForm.day = d;
      this.rcdata = 0;
      this.rcnum = num;
      this.editForm.grade = this.rcdata;
      this.editForm.number = this.rcnum;
      this.editForm.remainNum = 0;
      this.add()
    },
    calItemBackfill(date, d, index, num, grade,remainNum) {
      this.editForm.day = d;
      this.rcdata = grade;
      this.rcnum = num;
      this.remainNum = remainNum;
      this.editForm.grade = this.rcdata;
      this.editForm.number = this.rcnum;
      this.editForm.remainNum =  this.remainNum;
      this.add()
    },

    calItemSet(date, d, index) { //填写设置时间范围
      this.calItemBackfill1(date, d, index, this.form.num);
    },

    add() {
      var a = {
        date: this.editForm.day,
        grade: this.editForm.grade,
        number: this.editForm.number,
        remainNum:this.editForm.remainNum,
      };
      if (this.resDate.length > 0) {
        //find方法查询是否存在相同的数据
        let isAdd = this.resDate.find(item => {
          if (item.date == a.date) {
            //存在相同的数据调用vue的$set方法替换value值
            this.$set(item, 'grade', a.grade);
            this.$set(item, 'number', a.number);
            this.$set(item, 'remainNum', a.remainNum);
            //数组中的元素在测试条件时返回 true 时, find()返回符合条件的元素
            return true
          }
          //如果不存在find方法会返回一个undefined
        });
        // 判断isAdd是否为undefined
        if (typeof (isAdd) == "undefined") {
          //当isAdd为undefined添加新的对象到数组中
          this.resDate.push(a);
        }
      }

    },

    addRe(editForm) {
      this.$refs['editForm'].validate((valid) => {
        if (valid) {
          this.dialogVisible = false
          this.add();
        }
      })


    },
    handleClose(done) {
      done()
    },
    callOf(formName) {
      // console.log("sadad",formName)
      // this.$refs.formName.resetFields();
      // this.$refs['formName'].clearValidate()
      this.dialogVisible = false
    },
    async fetchData() {
      this.listLoading = true;
      const { data } = await getList(this.queryForm);
      this.commonMenu = data.records;


      this.total = data.total;
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
    async fetchOrgData() {
      this.listLoading = true;
      const { data } = await getSelectOrgList(this.orgQueryForm);
      this.orgMenu = data.records;

      setTimeout(() => {
        this.listLoading = false
      }, 200)
    },
    async getOrgSubscribeRulesData() { //回显数据
      this.listLoading = true;

      const { data } = await getOrgSubscribeRules();
      if (data != null && data.length > 0) {
        var arr = [];
        arr[0] = data[0].start_time;
        arr[1] = data[0].end_time;
        this.value1 = arr;
        if (data[0].state == "0") {
          this.form.state = "0"
        } else {
          this.form.state = "1"
        }
        for (let i = 0; i < data.length; i++) {
          var tempGrade = 1;
          if (data[i].isbook == '1') {
            tempGrade = 0;
          }

          var bPerson = data[i].booking_person;
          if(bPerson == null || bPerson == undefined || bPerson == ''){
              bPerson = 0 ;
          }
          this.calItemBackfill(new Date(data[i].time), data[i].time, i, data[i].limit_person, tempGrade,bPerson);
        }
      }

      // this.orgMenu = data.records
      setTimeout(() => {
        this.listLoading = false
      }, 200)
    },

  },
}
</script>
<style lang="scss" scoped>
.el-card {
  border-radius: 4px;
  text-align: center;

  .tc-box {
    padding: 14px;
    position: relative;

    p {
      padding: 0;
      margin: 4px 0 8px 0;
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
      color: #E6A23C;
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
      border-bottom: 36px solid #EBEEF5;

      i {
        color: #fff;
        position: absolute;
        top: 14px;
        right: 2px;
      }
    }
  }

  .tc-plus {
    height: 98px;
    line-height: 98px;
    font-size: 32px;
    color: #1890ff;
    cursor: pointer;
  }

}

::v-deep {
  .el-form-item--small.el-form-item {
    margin-bottom: 0;
  }

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

::v-deep .el-input-group__append {
  padding: 0 10px;
}

::v-deep .el-calendar-table__row td::v-deep .el-calendar-table tr td:first-child,
::v-deep .el-calendar-table__row td.prev {
  border: none;
}

.date-content {
  height: 60px;
  text-align: center;
  line-height: 30px;
  font-size: 14px;
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
  background: #409EFF;
  display: inline-block;
  font-size: 14px;
}

.date-content .work.disabled,
.date-content .rest.disabled {
  background: #afafaf;
  cursor: no-drop;
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
  padding: 20px 10px 0;
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

::v-deep .content-left .el-form-item__content {
  text-align: left;
  margin-left: 110px !important;
}

.new-tc-con .el-card {
  border: 1px dashed #82a6fb;
}
  .yet{
    display: inline-block;
    background-color: #edf3f9;
    min-width: 16px;
    height: 20px;
    vertical-align: middle;
    line-height: 20px;
    border-radius: 4px;
    padding: 0 2px;
    color: #409eff;
    text-align: center;
    font-style: normal;
  }
</style>