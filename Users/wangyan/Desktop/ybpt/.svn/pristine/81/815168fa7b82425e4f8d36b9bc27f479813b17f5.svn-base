<template>
  <el-drawer
    :title="title"
    :before-close="handleClose"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="80%"
    ref="drawer"
    append-to-body
  >
    <div class="drawer_content">
      <div class="drawer_form_box">
        <el-form
          :model="form"
          :label-width="formLabelWidth"
          label-position="left"
        >
          <div class="drawer_main">
            <div class="box_card">
              <div class="box_content">
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="套餐年份" v-if="flag">
                      <el-date-picker
                        v-model.trim="year"
                        type="year"
                        value-format="yyyy"
                        class="w"
                        @change="selectOrg()"
                      ></el-date-picker>
                    </el-form-item>
                    <el-form-item label="套餐年份" v-else>
                      <el-date-picker
                        v-model.trim="year"
                        type="year"
                        value-format="yyyy"
                        class="w"
                        @change="selectOrg()"
                        disabled
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-if="flag">
                    <el-form-item label="体检机构">
                      <el-select
                        v-model="name"
                        placeholder="选择体检机构"
                        class="w"
                        @change="selectOrg1()"
                      >
                        <el-option
                          v-for="it in names"
                          :key="it.medical_insurance_num"
                          :label="it.org_name"
                          :value="it.medical_insurance_num"
                          :style="
                            it.is_select > 0
                              ? 'color: #009933'
                              : 'color: #000000'
                          "
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8" v-else>
                    <el-form-item label="体检机构">
                      <el-select
                        v-model="name"
                        placeholder="选择体检机构"
                        class="w"
                        @change="selectOrg1()"
                        disabled
                      >
                        <el-option
                          v-for="it in names"
                          :key="it.medical_insurance_num"
                          :label="it.org_name"
                          :value="it.medical_insurance_num"
                          :style="
                            it.is_select > 0
                              ? 'color: #009933'
                              : 'color: #000000'
                          "
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通用套餐">
                      <!-- <ul class="list">
                      <li :class="currentActive==items.id?'active':''" @click="classSel(items.id)" v-for="(items,index) in tytcList" :key="index">
                        <div class="tc-box">
                          <p>{{items.name}}</p>
                          <span>{{items.price}}</span>
                          <el-button
                            class="view-btn"
                            size="mini"
                            @click="handleView($event)"
                          >
                            查看
                          </el-button>
                          <div class="check-btn">
                            <i class="el-icon-check"></i>
                          </div>
                        </div>
                      </li>
                    </ul>     -->
                      <el-col
                        :span="3"
                        :class="
                          currentActive == items.id ? 'tc-con active' : 'tc-con'
                        "
                        @click.native="classSel(items)"
                        v-for="(items, index) in tytcList"
                        :key="index"
                      >
                        <el-card
                          :body-style="{ padding: '0px' }"
                          shadow="hover"
                        >
                          <el-tooltip
                            class="item"
                            effect="dark"
                            :content="items.pack_name"
                            placement="top"
                          >
                            <div class="tc-box">
                              <p>{{ items.pack_name }}</p>
                              <!-- <span>{{ items.pack_money }}</span> -->
                              <el-button
                                class="view-btn"
                                size="mini"
                                @click="handleView(items)"
                              >
                                查看
                              </el-button>
                              <div class="check-btn">
                                <i class="el-icon-check"></i>
                              </div>
                            </div>
                          </el-tooltip>
                        </el-card>
                      </el-col>
                    </el-form-item>
                  </el-col>
                  <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="通用套餐+赠送项目">
                      <!-- <ul class="list">
                      <li :class="currentActive==items.id?'active':''" @click="classSel(items.id)" v-for="(items,index) in jgtcList" :key="index">
                        <div class="tc-box">
                          <p>{{items.name}}</p>
                          <span>{{items.price}}</span>
                          <el-button
                            class="view-btn"
                            size="mini"
                            @click="handleView($event)"
                          >
                            查看
                          </el-button>
                          <div class="check-btn">
                            <i class="el-icon-check"></i>
                          </div>
                        </div>
                      </li>
                    </ul>             -->
                      <el-col
                        :span="3"
                        :class="
                          currentActive == items.id ? 'tc-con active' : 'tc-con'
                        "
                        @click.native="classSel(items)"
                        v-for="(items, index) in list2"
                        :key="index"
                      >
                        <el-card
                          :body-style="{ padding: '0px' }"
                          shadow="hover"
                        >
                          <el-tooltip
                            class="item"
                            effect="dark"
                            :content="items.pack_name"
                            placement="top"
                          >
                            <div class="tc-box">
                              <p>{{ items.pack_name }}</p>
                              <!-- <span>{{ items.pack_money }}</span> -->
                              <el-button
                                class="view-btn"
                                size="mini"
                                @click="handleView(items)"
                              >
                                查看
                              </el-button>
                              <div class="check-btn">
                                <i class="el-icon-check"></i>
                              </div>
                            </div>
                          </el-tooltip>
                        </el-card>
                      </el-col>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row :gutter="20">
                  <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                    <el-form-item label="体检负责人电话">
                      <el-input
                        v-model.trim="queryForm1.phone"
                        placeholder="负责人电话"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-row>
                  <div style="width: 100%; display: flex">
                    <div style="width: 140px"></div>
                    <div style="flex: 1; display: flex">
                      <div style="flex: 1; margin-right: 16px">
                        <el-form-item class="label-no">
                          <vab-query-form>
                            <vab-query-form-left-panel :span="18">
                              <!-- <el-form :inline="true" :model="queryForm" @submit.native.prevent> -->
                              <el-form-item>
                                <label
                                  class="el-form-item__label"
                                  style="padding-bottom: 0; width: 130px"
                                >
                                  未预约的人员信息
                                </label>
                                <el-input
                                  v-model.trim="find1"
                                  placeholder="姓名"
                                  clearable
                                  style="width: 160px; display: inline-block"
                                />
                              </el-form-item>
                              <el-form-item
                                style="margin-left: 14px; width: 160px"
                              >
                                <el-select
                                  v-model="typeNum"
                                  clearable
                                  placeholder="请选择人员类型"
                                >
                                  <el-option
                                    v-for="item in optionsType"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value"
                                  ></el-option>
                                </el-select>
                              </el-form-item>
                              <!-- <el-form-item>
                            <el-input v-model.trim="queryForm.username" placeholder="身份证号" clearable />
                          </el-form-item>
                        </el-form> -->
                            </vab-query-form-left-panel>
                            <vab-query-form-right-panel :span="6">
                              <el-form-item>
                                <el-button
                                  icon="el-icon-search"
                                  type="primary"
                                  @click="selectCivi()"
                                >
                                  查询
                                </el-button>
                              </el-form-item>
                            </vab-query-form-right-panel>
                          </vab-query-form>
                          <pl-table
                            ref="multipleTable"
                            :data="sub"
                            border
                            stripe
                            class="w"
                            :height="760 + 'px'"
                            :row-height="38"
                            @selection-change="setSelectWidRows"
                            use-virtual
                          >
                            <!-- <template slot="empty">
                        <pl-empty :image-size="150"></pl-empty>
                      </template> -->
                            <pl-table-column
                              prop="id"
                              type="selection"
                              align="center"
                              fixed="left"
                            ></pl-table-column>
                            <!-- <el-table-column type="index" label="序号" width="80" align="center">
                      </el-table-column> -->
                            <pl-table-column
                              label="姓名"
                              align="center"
                              prop="name"
                              width="120px"
                            ></pl-table-column>
                            <!-- <el-table-column    prop="sex" width="120px" label="性别" align="center"
                        :formatter="sex">
                      </el-table-column> -->
                            <!-- <el-table-column   prop="age" label="年龄" align="center" width="120px"> -->
                            <!-- </el-table-column> -->
                            <pl-table-column
                              prop="certno"
                              label="身份证号"
                              align="center"
                            ></pl-table-column>
                            <!-- <el-table-column    prop="insu_admdvs" label="统筹区" align="center"
                        width="120px">
                      </el-table-column> -->
                            <!-- <el-table-column    prop="admdvs" label="所属区" align="center" width="120px">
                      </el-table-column> -->
                            <!-- <el-table-column    prop="emp_name" label="单位名称" align="center">
                      </el-table-column>
                      <el-table-column    prop="emp_code" align="center" label="单位编码" width="200px">
                      </el-table-column>
                      <el-table-column    prop="emp_type" align="center" label="单位类型" width="120px">
                      </el-table-column> -->
                          </pl-table>
                          <!-- <el-pagination
                            background
                            :current-page="indexN"
                            :page-size="siezN"
                            :layout="layout"
                            :total="totalN"
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                          ></el-pagination> -->
                        </el-form-item>
                      </div>
                      <div style="flex: 1; margin-left: 16px">
                        <el-form-item class="label-no">
                          <vab-query-form>
                            <vab-query-form-left-panel :span="18">
                              <!-- <el-form :inline="true" :model="queryForm" @submit.native.prevent> -->
                              <el-form-item>
                                <label
                                  class="el-form-item__label"
                                  style="padding-bottom: 0; width: 130px"
                                >
                                  已预约的人员信息
                                </label>
                                <el-input
                                  v-model.trim="find2"
                                  placeholder="姓名"
                                  clearable
                                  style="width: 160px; display: inline-block"
                                />
                              </el-form-item>
                              <!-- <el-form-item>
                            <el-input v-model.trim="queryForm.username" placeholder="身份证号" clearable />
                          </el-form-item> -->
                              <!-- </el-form> -->
                            </vab-query-form-left-panel>
                            <vab-query-form-right-panel :span="6">
                              <el-form-item>
                                <el-button
                                  icon="el-icon-search"
                                  type="primary"
                                  @click="selectCivi1()"
                                >
                                  查询
                                </el-button>
                              </el-form-item>
                            </vab-query-form-right-panel>
                          </vab-query-form>
                          <pl-table
                            ref=""
                            :data="subOver"
                            border
                            stripe
                            class="w"
                            :height="760 + 'px'"
                            :row-height="38"
                            use-virtual
                            @selection-change="setSelectWidRows"
                          >
                            <!-- <template slot="empty">
                        <el-empty :image-size="150"></el-empty>
                      </template>
                      <el-table-column    type="selection" align="center" fixed="left">
                      </el-table-column> -->
                            <pl-table-column
                              type="index"
                              label="序号"
                              width="80"
                              align="center"
                            ></pl-table-column>
                            <pl-table-column
                              label="姓名"
                              align="center"
                              prop="name"
                              width="120px"
                            ></pl-table-column>
                            <!-- <el-table-column    prop="sex" width="120px" label="性别" align="center"
                        :formatter="sex">
                      </el-table-column> -->
                            <pl-table-column
                              prop="apply_date"
                              label="预约时间"
                              align="center"
                              width="120px"
                            ></pl-table-column>
                            <pl-table-column
                              prop="certno"
                              label="身份证号"
                              align="center"
                            ></pl-table-column>
                            <!-- <el-table-column    prop="insu_admdvs" label="统筹区" align="center"
                        width="120px">
                      </el-table-column> -->
                            <!-- <el-table-column    prop="admdvs" label="所属区" align="center" width="120px">
                      </el-table-column> -->
                            <!-- <el-table-column    prop="emp_name" label="单位名称" align="center">
                      </el-table-column>
                      <el-table-column    prop="emp_code" align="center" label="单位编码" width="200px">
                      </el-table-column>
                      <el-table-column    prop="emp_type" align="center" label="单位类型" width="120px">
                      </el-table-column> -->
                          </pl-table>
                          <!-- <el-pagination
                            background
                            :current-page="indexY"
                            :page-size="siezY"
                            :layout="layout"
                            :total="totalY"
                            @size-change="handleSizeChangeY"
                            @current-change="handleCurrentChangeY"
                          ></el-pagination> -->
                        </el-form-item>
                      </div>
                    </div>
                  </div>
                </el-row>
                <!-- import Hospital from './components/views'; -->
                <el-form-item label="预约日期">
                  <div class="border-ebeef5">
                    <p class="default-box">
                      未预约人数：{{ ytjNum }} 人，已预约人数：
                      {{ subOver.length }}
                      人
                    </p>
                    <el-form-item label="">
                      <el-calendar v-model="value">
                        <template slot="dateCell" slot-scope="{ date, data }">
                          <div
                            class="date-content"
                            @click="calItem(date, data.day)"
                          >
                            <span class="text">{{ getDay(date) }}</span>
                            <el-tag
                              type="success"
                              v-if="dealMyDate(date, data.day).grade == '0'"
                            >
                              可预约{{ dealMyDate(date, data.day).number }}人
                            </el-tag>
                            <el-tag
                              type="info"
                              v-if="dealMyDate(date, data.day).grade == '1'"
                            >
                              未开放预约
                            </el-tag>
                            <el-tag
                              type="warning"
                              v-if="dealMyDate(date, data.day).grade == '2'"
                            >
                              人满
                            </el-tag>
                          </div>
                        </template>
                      </el-calendar>
                    </el-form-item>
                  </div>
                </el-form-item>
              </div>
            </div>
          </div>
        </el-form>
      </div>
      <div class="drawer_footer">
        <el-button @click="cancelForm" type="primary">关 闭</el-button>
        <el-button @click="resetLoginForm" type="primary">重 置</el-button>
        <!-- <el-button type="primary" @click="$refs.drawer.closeDrawer()" :loading="loading">
          {{ loading ? '提交中 ...' : '提 交' }}
        </el-button> -->
      </div>
    </div>
    <el-dialog
      :title="editForm.day"
      :visible.sync="dialogVisible"
      width="400px"
      append-to-body
    >
      <el-form
        @submit.native.prevent
        :model="editForm"
        label-width="180px"
        ref="editForm"
      >
        <!--<el-form-item label="确定选择当前日期吗？" prop="grade">-->
        <!-- <el-radio-group v-model="editForm.grade"> -->
        <!-- <el-radio :label="0">可预约</el-radio> -->
        <!-- <el-radio :label="1">不可预约</el-radio> -->
        <!-- </el-radio-group> -->
        <!--</el-form-item>-->
        <p
          style="
            padding-left: 30px;
            margin: 0 0 16px;
            font-size: 16px;
            color: #303133;
          "
        >
          确定选择当前日期吗？
        </p>
        <span style="padding-left: 30px; color: #e6a23c">
          可预约人数：{{ editForm.number }}人，已选择人数：{{
            queryForm1.wids.length
          }}人
        </span>
        <!-- <el-form-item v-show="editForm.grade == 0" label="安排人数：" prop="number">
          <el-input v-model="editForm.number" placeholder="人数"></el-input>
        </el-form-item> -->
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="callOf('editForm')">取 消</el-button>
        <el-button
          type="primary"
          @click="
            dialogVisible = false
            initSubOver()
          "
        >
          确 定
        </el-button>
      </span>
    </el-dialog>

    <hospital ref="hospital" :list4="list4"></hospital>
    <views ref="views"></views>
  </el-drawer>
</template>

<script>
  import {
    findCivilworkerIn,
    findCivilworkerNotIn,
    saveEmpSubscribeRecord,
  } from '@/api_check/empSubscribeRecord'
  import { findCiviWorkerByRid } from '@/api_check/personSubscribeRecord'
  import {
    findSubRules,
    getList,
    getOrgListPack,
    getPackInfoList,
  } from '@/api_check/taocan'
  // import Edit from './components/edit';
  import Hospital from './components/views'
  import Views from './view'
  export default {
    name: 'edit',
    components: { Hospital, Views },
    // props: {
    //   tytcList: [],
    //   list3: [],
    // },
    data() {
      return {
        list2: [],
        list4: [],
        subOver: [],
        sub: [],
        addRules: [],
        sumNum: 0,
        ytjNum: 0,
        yppNum: 0,
        find2: '',
        find1: '',
        typeNum: '',
        siezN: 10,
        indexN: 1,
        totalN: 0,
        siezY: 10,
        indexY: 1,
        totalY: 0,
        tableData: [],
        year: '',
        name: '',
        names: [],
        orgName: [],
        jgtcList: [],
        radio: '上午',
        title: '',
        dialog: false,
        loading: false,
        isShow: false,
        isShow1: true,
        isShow2: false,
        layout: 'total, sizes, prev, pager, next, jumper',
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
          oid: '',
          rid: '',
          time: '',
          code: '',
        },
        queryForm1: {
          org_id: '',
          org_name: '',
          money: '',
          year: '',
          time: '',
          times: [],
          usertype: '',
          uid: '',
          wids: [],
          pid: '',
          start: '',
          end: '',
          id: '',
          num: '',
          state: '',
          phone: '',
        },
        sourceForm: {
          source: 1,
          if_open: 1,
          year: '',
          orgId: '',
          status: 1,
        },
        orgQueryForm1: {
          year: '',
          source: '', //套餐来源 (机构:1，通用:2)
        },
        form: {
          start_time: '',
          end_time: '',
          dateArray: '',
          state: '',
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
        formLabelWidth: '140px',
        timer: null,
        currentActive: 10000,
        dialogImageUrl: '',
        dialogVisible: false,
        flag: true,
        disabled: false,
        resDate: [
          { date: '2022-05-23', grade: '0', number: '200' },
          { date: '2022-05-22', grade: '1', number: '0' },
          { date: '2022-05-21', grade: '2', number: '0' },
        ],
        value: new Date(),
        rcdata: [],
        editForm: {
          grade: '0',
          number: 0,
        },
        optionsType: [
          {
            value: 1,
            label: '退休',
          },
          {
            value: 2,
            label: '在职',
          },
        ],
        list3: [],
        tytcList: [],
      }
    },
    created() {
      this.getOrgSubscribeRulesData() //回显规则
    },
    mounted() {
      // this.$nextTick(() => {
      // });
    },
    methods: {
      handleSizeChange(val) {
        this.siezN = val
        this.fetchData4()
      },
      handleCurrentChange(val) {
        this.indexN = val
        this.fetchData4()
      },
      handleSizeChangeY(val) {
        this.siezY = val
        this.fetchData2()
      },
      handleCurrentChangeY(val) {
        this.indexY = val
        this.fetchData2()
      },
      selectCivi() {
        this.fetchData4()
      },
      selectCivi1() {
        this.fetchData2()
      },
      toggleAllSelection() {
        this.$refs.multipleTable.toggleAllSelection()
        // console.log(this.$refs.multipleTable);
      },
      initSubOver() {
        if (this.loading) {
          return
        }
        let obj = {}
        obj = this.orgName.find((item) => {
          return item.medical_insurance_num === this.name
        })
        console.log(obj.org_name)
        this.queryForm1.org_name = obj.org_name
        this.$refs['editForm'].validate(async (valid) => {
          if (valid) {
            this.loading = true
            setTimeout(() => {
              this.loading = false
            }, 400)

            this.add()
            if (this.flag) {
              console.log('新增')
              var userinfo = JSON.parse(localStorage.getItem('userinfo'))
              this.queryForm1.usertype = userinfo.user_type
              this.queryForm1.uid = userinfo.org_code
              // this.getDuringDate(this.queryForm1.start, this.queryForm1.end)
              console.log(this.queryForm1)
              saveEmpSubscribeRecord(this.queryForm1).then((res) => {
                this.$parent.fetchData()
                this.fetchData4()
                // this.allPerson()
                this.fetchData2()
                if (res.code == -1) {
                  this.resDate = [
                    { date: '2022-05-23', grade: '0', number: '200' },
                  ]
                  this.getOrgSubscribeRulesData()
                  this.$baseMessage(res.msg, 'error')
                  this.$emit('fetch-data')
                }
              })
            } else {
              var userinfo = JSON.parse(localStorage.getItem('userinfo'))
              this.queryForm1.usertype = userinfo.user_type
              this.queryForm1.uid = userinfo.org_code
              // this.getDuringDate(this.queryForm1.start, this.queryForm1.end)
              saveEmpSubscribeRecord(this.queryForm1).then((res) => {
                this.$parent.fetchData()
                this.fetchData4()
                // this.allPerson()
                this.fetchData2()
                if (res.code == -1) {
                  this.resDate = [
                    { date: '2022-05-23', grade: '0', number: '200' },
                  ]
                  this.getOrgSubscribeRulesData()
                  this.$baseMessage(res.msg, 'error')
                  this.$emit('fetch-data')
                }
              })
            }
          }
        })
      },
      allPerson() {
        for (let index = 0; index < this.list3.length; index++) {
          this.queryForm1.wids[index] = this.list3[index].certno
        }
      },
      // choose(o, index) {
      //   if (o != null) {
      //     this.queryForm1.money = o.pack_money
      //     this.queryForm1.pid = o.id
      //   }
      // },
      // choose1(o, index) {
      //   if (o != null) {
      //     this.queryForm1.money = o.pack_money
      //     this.queryForm1.pid = o.id
      //     this.spanIndex = []
      //     this.selectedIds = []
      //     let arrIndex = this.spanIndex1.indexOf(index);
      //     if (arrIndex > -1) {
      //       this.spanIndex1.splice(arrIndex, 1);//取消
      //       this.selectedIds1.splice(arrIndex, 1);
      //     } else {//选中
      //       console.log("index-111-" + index)
      //       this.spanIndex1.push(index);
      //       this.selectedIds1.push(o.id);
      //     }
      //   }
      // },
      selectOrg() {
        if (this.year != null && this.year != '') {
          this.orgQueryForm1.year = this.year
          this.orgQueryForm1.source = '1'
          getOrgListPack().then((res) => {
            for (let index = 0; index < res.data.length; index++) {
              if (res.data[index].is_select == '1') {
                res.data[index].org_name =
                  res.data[index].org_name + '---有通用套餐+赠送项目'
              }
            }
            this.names = res.data
          })
          getOrgListPack().then((res) => {
            this.orgName = res.data
          })
          this.queryForm1.year = this.year
          this.fetchData2()
          this.fetchData4()

          var tempSourceForm = {
            pack_source: 2, //通用
            if_open: 1, //上架
            status: 1, //通过
          }
          // tempSourceForm.pack_year = this.year
          getList(tempSourceForm).then((res) => {
            this.tytcList = res.data.records
          })

          this.name = '' //体检机构已选择 套餐年份变动体检机构重选
          this.pid = '' //体检机构已选择 套餐年份变动体检机构重选
          this.queryForm1.pid = ''
          this.currentActive = ''
          this.list2 = []
        }
      },
      selectOrg1() {
        for (let index = 0; index < this.names.length; index++) {
          if (this.names[index].org_id == this.name)
            this.queryForm1.org_name = this.names[index].org_name
        }
        this.sourceForm.year = this.year
        this.sourceForm.orgId = this.name
        this.queryForm1.org_id = this.name
        getPackInfoList(this.sourceForm).then((res) => {
          this.list2 = res.data
        })
        this.resDate = [{ date: '2022-05-23', grade: '0', number: '200' }]
        this.getOrgSubscribeRulesData()
      },
      sex(row, index) {
        if (row.sex == 1) {
          return '男'
        } else {
          return '女'
        }
      },
      setSelectWidRows(val) {
        this.queryForm1.wids = []
        if (val.length > 0) {
          for (let index = 0; index < val.length; index++) {
            this.queryForm1.wids[index] = val[index].certno
          }
        }
        this.ytjNum = this.queryForm1.wids.length
        this.editForm.number = this.queryForm1.wids.length
      },
      classSel(e) {
        if (e != null) {
          this.queryForm1.money = e.pack_money
          // this.queryForm1.org_name = e.org_name
          this.queryForm1.pid = e.id
          console.log(this.queryForm1.pid)
          // console.log("---s->" + this.queryForm1.org_name);
        }
        // this.tytcList.forEach(function(obj){
        //   obj.isActive=false;
        // })
        // this.jgtcList.forEach(function(obj){
        //   obj.isActive=false;
        // })
        // e.isActive=!e.isActive;
        this.currentActive = e.id
        // console.log(this.currentActive);
      },
      showDia(row) {
        this.sub = this.list3
        // this.allPerson()
        this.fetchData2()
        setTimeout(() => {
          this.toggleAllSelection()
        }, 0)
        // this.fetchData4()
        // this.getOrgSubscribeRulesData()
        // this.setSelectWidRows()

        if (row.id == undefined) {
          this.title = '新增'
          this.flag = true
        } else {
          console.log(row)
          this.title = '编辑'
          this.flag = false
          console.log('编辑')
          this.year = row.pack_year
          this.selectOrg()
          this.name = row.sub_org
          this.selectOrg1()
          this.queryForm1.id = row.id
          this.currentActive = row.pack_id
          this.queryForm1.pid = this.currentActive
        }
        this.dialog = true
      },
      close() {
        console.log('关闭')
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialog = false
      },
      save() {
        // this.$refs.multipleTable.toggleAllSelection();
        this.$baseMessage('模拟保存成功', 'success')
        this.$emit('fetch-data')
        this.close()
      },
      handleView(row) {
        this.$refs['hospital'].showDia(row)
      },
      calItem(date, d) {
        this.fetchData1(d)
        let pitchDay = new Date(d).getTime()
        let today = new Date().getTime()

        if (this.year == '') {
          this.$alert('请选择年份', '提示', {
            confirmButtonText: '确定',
          })
        } else if (this.name == '') {
          this.$alert('请选择机构', '提示', {
            confirmButtonText: '确定',
          })
          // alert('请选择机构')
        } else if (this.queryForm1.pid == '') {
          this.$alert('请选择套餐', '提示', {
            confirmButtonText: '确定',
          })
          // alert('请选择机构')
        } else if (this.queryForm1.phone == '') {
          this.$alert('请填写负责人电话', '提示', {
            confirmButtonText: '确定',
          })
          // alert('请选择机构')
        } else {
          // 将时间戳转换回 Date 对象
          let pitchDate = new Date(pitchDay)

          // 获取年份
          let pitchYear = pitchDate.getFullYear()

          // 比较年份
          if (pitchYear != this.year) {
            this.$alert('当前时间与选择套餐年份不符', '提示', {
              confirmButtonText: '确定',
            })
            return false
          }

          if (pitchDay < today) {
            if (this.tableData.length > 0) {
              this.$refs['views'].showDia()
            } else {
              this.$alert('当前时间已经过期', '提示', {
                confirmButtonText: '确定',
              })
              // alert(' 当前时间已经过期')
            }
          } else {
            this.editForm.day = d
            this.rcdata = this.dealMyDate(date, d).grade
            this.rcnum = this.dealMyDate(date, d).number
            this.editForm.grade = this.rcdata
            this.editForm.number = this.rcnum
            console.log(this.rcdata)
            if (this.rcnum > 0 && this.rcdata == 0) {
              if (this.queryForm1.wids.length > 0) {
                this.dialogVisible = true
              } else {
                this.$alert('请先选择需要预约的人员', '提示', {
                  confirmButtonText: '确定',
                  callback: (action) => {
                    // this.$message({
                    //   type: 'info',
                    //   message: `action: ${action}`
                    // });
                  },
                })
              }
            } else {
              if (this.rcdata == 1) {
                this.$alert('当天未开放', '提示', {
                  confirmButtonText: '确定',
                })
                // alert('当天名额已满')
                if (this.tableData.length > 0) {
                  this.$refs['views'].showDia()
                }
              } else {
                this.$alert('当天名额已满', '提示', {
                  confirmButtonText: '确定',
                })
                // alert('当天名额已满')
                if (this.tableData.length > 0) {
                  this.$refs['views'].showDia()
                }
              }
            }
          }
        }
      },

      // 标注今天日期
      getDay(date) {
        return date.getDate()
      },

      dealMyDate(date, v) {
        let len = this.resDate.length
        let res = []
        res.grade = 1 //排班0，休息1
        res.number = this.form.num - this.editForm.number
        for (let i = 0; i < len; i++) {
          if (this.resDate[i].date == v) {
            res.number = this.resDate[i].number
            if (res.number > 0) {
              res.grade = this.resDate[i].grade
            } else {
              res.grade = 2
            }
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
        if (this.resDate.length > 0) {
          //find方法查询是否存在相同的数据
          let isAdd = this.resDate.find((item) => {
            if (item.date == a.date) {
              if (new Date(a.date).getTime() > new Date().getTime()) {
                //存在相同的数据调用vue的$set方法替换value值
                if (a.number <= this.queryForm1.wids.length) {
                  this.$set(item, 'grade', 2)
                  this.$set(item, 'number', 0)
                } else {
                  this.$set(item, 'grade', 0)
                  this.$set(
                    item,
                    'number',
                    a.number - this.queryForm1.wids.length
                  )
                }
              } else {
                this.$set(item, 'grade', 1)
                this.$set(item, 'number', 0)
              }
              this.queryForm1.num = a.number
              this.queryForm1.times[0] = a.date

              // if (this.ytjNum == 0) {
              //   this.$set(item, 'grade', item.grade)
              //   this.$set(item, 'number', item.number)
              // } else if (this.ytjNum < item.number) {
              //   this.$set(item, 'grade', item.grade)
              //   this.$set(item, 'number', item.number - this.ytjNum)
              //   this.yppNum = this.yppNum + this.ytjNum
              //   this.queryForm1.times.push(item.date)
              // } else if (this.ytjNum == item.number) {
              //   a.grade = 1
              //   this.$set(item, 'grade', a.grade)
              //   this.$set(item, 'number', a.number - this.ytjNum)
              //   this.yppNum = this.yppNum + this.ytjNum
              //   this.queryForm1.times.push(item.date)
              // } else {
              //   alert('请填入正确数字')
              //   this.$set(item, 'grade', item.grade)
              //   this.$set(item, 'number', item.number)
              // }
              //数组中的元素在测试条件时返回 true 时, find()返回符合条件的元素
              return true
            }
            //如果不存在find方法会返回一个undefined
          })
          // 判断isAdd是否为undefined
          if (
            typeof isAdd == 'undefined' &&
            new Date(a.date).getTime() > new Date().getTime()
          ) {
            //当isAdd为undefined添加新的对象到数组中
            this.resDate.push(a)
            this.addRules.push(a)
          }
        }
      },
      callOf(formName) {
        // console.log("sadad",formName)
        // this.$refs.formName.resetFields();
        // this.$refs['formName'].clearValidate()
        this.dialogVisible = false
      },
      handleClose(done) {
        if (this.loading) {
          return
        }
        // this.$confirm('确定要提交表单吗？')
        //   .then((_) => {
        //     this.loading = true
        //     this.timer = setTimeout(() => {
        //       done()
        //       // 动画关闭需要一定的时间
        //       setTimeout(() => {
        //         this.loading = false
        //       }, 400)
        //     }, 2000)
        //   })
        //   .catch((_) => { })
      },
      resetLoginForm() {
        // console.log(this)
        this.year = ''
        this.names = []
        this.name = ''
        this.resDate = [{ date: '2022-05-23', grade: '0', number: '200' }]
        this.list2 = []
        this.queryForm1.id = ''
      },
      cancelForm() {
        this.year = ''
        this.names = []
        this.name = ''
        this.resDate = [{ date: '2022-05-23', grade: '0', number: '200' }]
        this.list2 = []
        this.sub = []
        this.loading = false
        this.dialog = false
        this.queryForm1.id = ''
        this.queryForm1.phone = ''
        clearTimeout(this.timer)
      },
      calItemBackfill(date, d, index, num) {
        this.editForm.day = d
        this.rcdata = 0
        this.rcnum = num
        this.editForm.grade = this.rcdata
        this.editForm.number = this.rcnum
        this.add()
      },
      async getOrgSubscribeRulesData() {
        //回显数据
        this.listLoading = true
        this.queryForm1.state = 0
        const { data } = await findSubRules(this.queryForm1)
        if (data != null && data.length > 0) {
          var arr = new Array()
          arr[0] = data[0].start_time
          arr[1] = data[0].end_time
          this.value1 = arr
          if (data[0].state == '0') {
            this.form.state = '启用'
          } else {
            this.form.state = '禁用'
          }
          for (let i = 0; i < data.length; i++) {
            let num = data[i].limit_person - data[i].booking_person
            this.calItemBackfill(new Date(data[i].time), data[i].time, i, num)
          }
        }

        // this.orgMenu = data.records
        setTimeout(() => {
          this.listLoading = false
        }, 200)
      },
      // onSubmit() {

      //   // debugger
      //   // console.log(this.resDate);

      //   // this.form.start_time = this.value1[0]
      //   // this.form.end_time = this.value1[1]
      //   // this.form.dateArray = JSON.stringify(this.resDate);
      //   // // const { msg } = await doEdit(this.form)
      //   // // this.$baseMessage(msg, 'success')
      //   // // this.$emit('fetch-data')
      //   // console.log(this.form);

      // },

      async fetchData2() {
        this.listLoading = true
        const a = {
          id: JSON.parse(localStorage.getItem('userinfo')).org_code,
          year: this.year,
          name: this.find2,
          index: this.indexY,
          size: this.siezY,
        }
        console.log(a)
        const { data } = await findCivilworkerIn(a)
        this.subOver = data
        // this.totalY = data.total
      },
      async fetchData4() {
        this.listLoading = true
        let type = ''
        if (this.typeNum == '') {
          type = 0
        } else {
          type = this.typeNum
        }
        const a = {
          id: JSON.parse(localStorage.getItem('userinfo')).org_code,
          year: this.year,
          name: this.find1,
          type: type,
          index: this.indexN,
          size: this.siezN,
        }
        console.log('type', a)
        const { data } = await findCivilworkerNotIn(a)
        console.log(data)
        this.sub = data
        // this.totalN = data.total
        this.toggleAllSelection()
      },
      async fetchData1(d) {
        this.queryForm.code = JSON.parse(
          localStorage.getItem('userinfo')
        ).org_code
        this.queryForm.oid = this.queryForm1.uid
        this.queryForm.time = d
        const { data } = await findCiviWorkerByRid(this.queryForm)
        this.tableData = data
        // this.$refs['views'].showDia()
      },
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .el-dialog__body {
      border-top: 0;
    }

    .el-table__virtual-wrapper {
      table td {
        height: auto !important;
      }
    }
  }
</style>
<style lang="scss" scoped>
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
      border-bottom: 36px solid #1890ff !important;
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
    top: 0;
    z-index: 999;
    left: 10px;
  }

  .drawer_form_box {
    height: calc(100vh - 10px);
    overflow-y: auto;
  }
  ::v-deep .label-no .el-form-item__content {
    margin-left: 0 !important;
  }
</style>
