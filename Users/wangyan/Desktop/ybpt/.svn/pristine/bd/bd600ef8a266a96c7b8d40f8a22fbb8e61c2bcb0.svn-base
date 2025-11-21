<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card
          class="card"
          shadow="never"
          style="min-height: calc(100vh - 250px)"
        >
          <div slot="header">
            <span class="tips">协助预约</span>
          </div>
          <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="证件类型" prop="cardType">
                  <el-select
                    v-model="form.cardType"
                    clearable
                    style="width: 400px"
                  >
                    <el-option label="居民身份证" value="1"></el-option>
                    <el-option
                      label="澳门特区护照/港澳居民来往内地通行证"
                      value="2"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="证件号" prop="certno">
                  <el-input
                    v-model.trim="form.certno"
                    placeholder="请输入证件号"
                    style="width: 400px"
                  >
                    <vab-icon
                      :icon="['fas', 'id-card']"
                      slot="suffix"
                    ></vab-icon>
                  </el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="套餐年份" prop="year">
                  <el-date-picker
                    v-model.trim="form.year"
                    placeholder="请选择年份"
                    style="width: 400px"
                    format="yyyy"
                    value-format="yyyy"
                    type="year"
                    @change="changeYear"
                  ></el-date-picker>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="机构名称">
                  <el-select
                    v-model="form.orgId"
                    disabled
                    style="width: 400px"
                    filterable
                    placeholder="请选择机构名称"
                    @change="changeOrgSelect"
                  >
                    <el-option
                      v-for="item in orgList"
                      :key="item.medical_insurance_num"
                      :label="item.org_name"
                      :value="item.medical_insurance_num"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="套餐类型">
                  <el-radio-group
                    v-model="form.type"
                    id="formtype"
                    @change="changeTypeVal"
                  >
                    <el-radio :label="0">通用套餐</el-radio>
                    <el-radio :label="1">通用套餐+赠送项目</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-col>

              <el-col
                v-show="form.type == 0"
                :xs="24"
                :sm="24"
                :md="24"
                :lg="24"
                :xl="24"
              >
                <el-form-item label="通用套餐" prop="commonMenuId">
                  <!--:rules="form.type == '0' ? rules.commonMenuId : [{ required: true }]"-->

                  <el-col
                    class="tc-con"
                    :xs="4"
                    :sm="4"
                    :md="4"
                    :lg="3"
                    :xl="3"
                    v-for="(o, index) in commonMenu"
                    :key="o"
                  >
                    <el-card :body-style="{ padding: '0px' }" shadow="hover">
                      <div
                        class="tc-box"
                        id="commonDiv"
                        :class="{ active: index == active }"
                        @click="selectName(index, o.id)"
                        :data-index="index"
                      >
                        <p>{{ o.pack_name }}</p>
                        <!--<span>{{o.pack_money}}(元)</span>-->
                        <el-button
                          class="view-btn"
                          size="mini"
                          @click.stop="tcView(o)"
                        >
                          查看
                        </el-button>
                        <div class="check-btn">
                          <i class="el-icon-check"></i>
                        </div>
                      </div>
                    </el-card>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col
                v-show="form.type == 1"
                :xs="24"
                :sm="24"
                :md="24"
                :lg="24"
                :xl="24"
              >
                <el-form-item label="通用套餐+赠送项目" prop="orgMenuId">
                  <!--:rules="form.type == '1' ? rules.orgMenuId : [{ required: true }]"-->

                  <el-col
                    class="tc-con new-tc-con"
                    :xs="4"
                    :sm="4"
                    :md="4"
                    :lg="3"
                    :xl="3"
                    v-for="(o, index) in orgMenu"
                    :key="o"
                  >
                    <el-card :body-style="{ padding: '0px' }" shadow="hover">
                      <div
                        class="tc-box"
                        :class="{ active: index + 100 == active }"
                        @click="selectName(index + 100, o.id)"
                        :data-index="index"
                      >
                        <p>{{ o.pack_name }}</p>
                        <!--<span>{{o.pack_money}}(元)</span>-->
                        <el-button
                          class="view-btn"
                          size="mini"
                          @click.stop="tcView(o)"
                        >
                          查看
                        </el-button>
                        <div class="check-btn">
                          <i class="el-icon-check"></i>
                        </div>
                      </div>
                    </el-card>
                  </el-col>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="预约时间" prop="time">
                  <el-date-picker
                    v-model="form.time"
                    type="date"
                    style="width: 400px !important"
                    placeholder="请选择预约时间"
                    @change="getBookingNum"
                    format="yyyy-MM-dd"
                    value-format="yyyy-MM-dd"
                    :picker-options="pickerOptions"
                  ></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item label="剩余预约名额">
                  <el-input
                    placeholder="剩余预约名额"
                    v-model="form.num"
                    style="width: 400px !important"
                    :readonly="true"
                  ></el-input>
                </el-form-item>
              </el-col>

              <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-form-item>
                  <el-button
                    type="primary"
                    @click="save"
                    :loading="loading"
                    :disabled="disabled"
                    icon="el-icon-check"
                  >
                    {{ loading ? '确定中 ...' : '确定' }}
                  </el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
    <views ref="views"></views>
  </div>
</template>

<script>
  import {
    commitBook,
    getAllOrgList,
    getBookingNum,
    setLoginOrg,
  } from '@/api_check/physicalAst'
  import { getList, getSelectOrgList } from '@/api_check/taocan'
  import Views from './components/views'

  export default {
    name: 'order',
    components: { Views },
    data() {
      return {
        // pickerOptions: {
        //   disabledDate(time) {
        //     return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
        //   },
        // },
        pickerOptions: {
          disabledDate: this.disabledBeforeToday,
        },
        pickerOptions2: {
          shortcuts: [
            {
              text: '最近一周',
              onClick(picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
                picker.$emit('pick', [start, end])
              },
            },
            {
              text: '最近一个月',
              onClick(picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
                picker.$emit('pick', [start, end])
              },
            },
            {
              text: '最近三个月',
              onClick(picker) {
                const end = new Date()
                const start = new Date()
                start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
                picker.$emit('pick', [start, end])
              },
            },
          ],
        },
        dynamicTags: ['项目一', '项目二', '项目三'],
        inputVisible: false,
        inputValue: '',
        value1: '',
        orgList: [],
        orgMenu: [],
        commonMenu: [],
        form: {
          org_id: '',
          orgId: '',
          org_name: '',
          type: 0,
          time: '',
          certno: '',
          cardType: '',
          year: '',
          num: '0',
          email: '',
          permissions: [],
          commonMenuId: '',
          orgMenuId: '',
        },
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          pack_year: '',
          pack_source: '2', //套餐来源 (机构:1，通用:2)
        },
        queryNumForm: {
          org_id: '',
          time: '',
        },
        orgQueryForm: {
          pageNo: 1,
          pageSize: 10,
          pack_year: '',
          ids: {},
          org_id: '',
          verify_ids: {},
          if_open: '1', //机构上架的数报
          pack_source: '1', //套餐来源 (机构:1，通用:2)
        },
        rules: {
          cardType: [
            { required: true, trigger: 'blur', message: '请选择证件类型' },
          ],
          certno: [
            { required: true, trigger: 'submit', message: '请输入证件号' },
            // { min: 18, max: 18, message: '请输入身份证号', trigger: 'submit' },
            // {
            //   required: false,
            //   trigger: 'submit',
            //   pattern: /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[0-2])(([0-2][1-9])|10|20|30|31)\d{3}(\d|X|x)$/,
            //   message: '身份证号格式不正确',
            // },
          ],

          year: [
            { required: true, trigger: 'submit', message: '请输入套餐年份' },
          ],
          // orgId: [{ required: true, trigger: 'blur', message: '请输入机构名称' },],
          time: [
            { required: true, trigger: 'submit', message: '请输入预约时间' },
          ],
        },
        title: '',
        dialogFormVisible: false,
        dialogVisible: false,
        active: -1,
        options: [
          {
            value: '选项1',
            label: '黄金糕',
          },
          {
            value: '选项2',
            label: '双皮奶',
          },
        ],
        value: '',
        spanIndex: [],
        selectedIds: [],
        loading: false,
        disabled: false,
      }
    },
    created() {
      this.fetchOrglist()
      this.fetchData()
      this.fetchOrgData()
      if (this.$route.query.certno != undefined) {
        this.form.certno = this.$route.query.certno
      }

      // this.setLoginOrg();
    },
    methods: {
      disabledBeforeToday(time) {
        return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
      },
      updatePickerOptions(value) {},
      tcView(row) {
        this.$refs['views'].showDia(row)
      },
      selectName(index, packId) {
        this.active = index //this指向app
        let arrIndex = this.selectedIds.indexOf(packId)
        if (arrIndex > -1) {
          this.selectedIds.splice(arrIndex, 1)
        } else {
          //选中
          this.selectedIds = []
          this.selectedIds.push(packId)
        }
      },
      changeTypeVal(val) {},
      save() {
        this.$refs['form'].validate(async (valid) => {
          this.loading = true
          this.disabled = true
          if (this.selectedIds.length != 1) {
            this.$message('请选择您要体检的套餐')
            setTimeout(() => {
              this.loading = false
              this.disabled = false
            }, 1000)
            return false
          }
          if (valid) {
            this.form.org_id = this.form.orgId
            this.form.pid = this.selectedIds[0]
            this.form.apply_date = this.form.time
            // const orgLabel = this.orgList.find(org => org.value == this.form.orgId).label
            // this.form.org_name = orgLabel
            try {
              const { msg } = await commitBook(this.form)
              this.$baseMessage(msg, 'success')
            } catch (e) {
              setTimeout(() => {
                this.loading = false
                this.disabled = false
              }, 2000)
            }

            // this.$emit('fetch-data')
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false
              this.disabled = false
            }, 2000)
          } else {
            setTimeout(() => {
              this.loading = false
              this.disabled = false
            }, 1000)
            return false
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

      changeYear(value) {
        this.fetchData()
        this.fetchOrgData()
        const startOfYear = new Date(value, 0, 1)
        const endOfYear = new Date(value, 11, 31, 23, 59, 59)

        this.pickerOptions = {
          disabledDate: (time) => {
            return (
              time.getTime() < startOfYear.getTime() || // 禁用选择年份之前的日期
              time.getTime() > endOfYear.getTime() || // 禁用选择年份之后的日期
              time.getTime() < Date.now() - 24 * 60 * 60 * 1000 // 禁用当前日期之前的日期
            )
          },
        }
      },
      changeOrgSelect() {
        this.fetchOrgData()
      },
      async getBookingNum() {
        this.queryNumForm.org_id = this.form.orgId
        this.queryNumForm.time = this.form.time
        const { data, msg, code } = await getBookingNum(this.queryNumForm)
        if (code == 0 && data != null && data.isbook == '1') {
          this.form.num = data.limit_person - data.booking_person
        } else {
          if (code == -1) {
            this.$message.error(msg)
          }
          this.form.num = 0
        }
      },
      async fetchOrglist() {
        this.listLoading = true
        const { data } = await getAllOrgList()
        this.orgList = data
        this.setLoginOrg()
        setTimeout(() => {
          this.listLoading = false
        }, 200)
      },
      async fetchData() {
        this.listLoading = true
        this.queryForm.pack_year = this.form.year
        const { data } = await getList(this.queryForm)

        this.commonMenu = data.records
        this.total = data.total
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      async fetchOrgData() {
        this.listLoading = true
        this.orgQueryForm.pack_year = this.form.year
        this.orgQueryForm.org_id = this.form.orgId
        const { data } = await getSelectOrgList(this.orgQueryForm)
        this.orgMenu = data.records
        setTimeout(() => {
          this.listLoading = false
        }, 200)
      },
      async setLoginOrg() {
        const { data } = await setLoginOrg()
        this.form.orgId = data.org_code
      },
    },
  }
</script>
<style lang="scss" scoped>
  .tc-con {
    border-radius: 4px;
    text-align: center;
    padding-left: 0 !important;
    padding-right: 20px !important;

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

    .active {
      .check-btn {
        border-bottom: 36px solid #1890ff;
      }
    }
  }

  .new-tc-con .el-card {
    border: 1px dashed #82a6fb;
  }
</style>
