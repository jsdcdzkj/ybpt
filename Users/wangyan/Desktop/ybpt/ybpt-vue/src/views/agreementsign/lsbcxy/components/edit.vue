<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="570px"
    @closeChildDialog="close"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="110px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="补充协议" prop="agreement_id">
            <el-select
              v-model.trim="form.agreement_id"
              class="w"
              :disabled="this.isDisable"
            >
              <el-option
                v-for="item in bcxymbList"
                :key="item.id"
                :label="item.title"
                :value="item.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="机构类别" prop="medical_type">
            <el-select
              v-model.trim="form.medical_type"
              class="w"
              :disabled="this.isDisable"
              @change="getLevelList"
            >
              <el-option value="1" label="医疗机构"></el-option>
              <el-option value="2" label="零售药店"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="机构等级" prop="jgLevel">
            <el-select
              v-model="form.jgLevel"
              multiple
              placeholder="请选择"
              class="w"
              @change="getJgList"
            >
              <el-option
                v-for="item in options"
                :key="item.cred_lv"
                :label="item.cred_lv_name"
                :value="item.cred_lv"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <!--        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">-->
        <!--          <el-form-item label="发送指定机构">-->
        <!--            <el-checkbox v-model="checked" @change="ischecked"></el-checkbox>-->
        <!--          </el-form-item>-->
        <!--        </el-col>-->

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="签署日期" prop="signDate">
            <el-date-picker
              v-model="form.signDate"
              type="date"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24" v-if="isshow">
          <el-form-item label="" prop="jgIds" class="tsitem">
            <el-transfer
              filterable
              :filter-method="filterMethod"
              filter-placeholder="请输入关键字"
              v-model="form.medicalCodeList"
              @mouseover.native="addTitle"
              :titles="['未选机构', '已选机构']"
              :data="data"
            ></el-transfer>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :disabled="this.isDisableSave">
        发 送
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
//回流库接口方法
import { getJgYdList } from '@/api_net/tagAgreement'

import { saveBcxy, selectOrganizationLevel } from '@/api_net/netTagMechanism'
import { getNetTagSuppList } from '@/api_net/netTagSupp'
import { regionDataPlus, CodeToText } from 'element-china-area-data'

export default {
  name: 'ybbcxyEdit',
  components: {},
  data() {
    const generateData = (_) => {
      const data = []
      const cities = [
        { MEDINS_NAME: '徐州仁慈医院', MEDINS_CODE: 'H32037100027' },
        {
          MEDINS_NAME: '徐州市铜山区徐庄镇毛庄卫生院',
          MEDINS_CODE: 'H32031200745',
        },
        {
          MEDINS_NAME: '徐州市铜山区徐庄镇卫生院',
          MEDINS_CODE: 'H32031200206',
        },
      ]
      cities.forEach((city, index) => {
        data.push({
          label: city.MEDINS_NAME,
          key: city.MEDINS_CODE,
          pinyin: city.MEDINS_NAME,
        })
      })
      return data
    }
    return {
      isshow: false,
      checked: false,
      options: [],
      optionsJg: [
        {
          cred_lv: '1',
          cred_lv_name: '一级',
        },
        {
          cred_lv: '2',
          cred_lv_name: '二级三级',
        },
        {
          cred_lv: '3',
          cred_lv_name: '未定级',
        },
      ],
      optionsYd: [
        {
          cred_lv: '1',
          cred_lv_name: 'A级',
        },
        {
          cred_lv: '2',
          cred_lv_name: 'B级',
        },
        {
          cred_lv: '3',
          cred_lv_name: 'C级',
        },
      ],
      data: generateData(),
      value: [],
      filterMethod(query, item) {
        return item.pinyin.indexOf(query) > -1
      },
      form: {
        agreement_id: '',
        medical_type: '',
        jgLevel: [],
        medicalCodeList: [],
        type: '1',
        signDate: '',
      },
      rules: {
        agreement_id: [
          { required: true, trigger: 'blur', message: '请选择补充协议' },
        ],
        medical_type: [
          { required: true, trigger: 'blur', message: '请选择机构类别' },
        ],
        jgLevel: [
          { required: true, trigger: 'blur', message: '请选择机构等级' },
        ],
        signDate: [
          { required: true, trigger: 'blur', message: '请选择签署日期' },
        ],
      },
      title: '',
      dialogFormVisible: false,
      isDisable: false,
      isDisableSave: false,
      bcxymbList: [],
      jglbList: [],
      jgLevelList: [],
    }
  },
  created() {
    //补充协议模板列表
    this.getBcxymbList()
  },
  methods: {
    addTitle(e) {
      const target = e.target
      if (target.title) return
      target.title = target.innerText
    },
    ischecked() {
      if (!this.form.agreement_id) {
        this.$baseMessage('请选择补充协议', 'error')
        return false
      }
      if (!this.form.medical_type) {
        this.$baseMessage('请选择机构类别', 'error')
        return false
      }
      if (this.form.jgLevel.length == 0) {
        this.$baseMessage('请选择机构等级', 'error')
        return false
      }
      this.isshow = !this.isshow
      this.isDisable = true
    },
    showDia(row) {
      this.checked = false
      if (!row) {
        this.title = '补充协议添加'
      } else {
        this.title = '补充协议修改'
        this.form = Object.assign({}, row)
      }
      this.dialogFormVisible = true
    },
    // handleChange(value) {
    //     let cityNames = [];
    //     value.forEach((e) => {
    //         cityNames.push(CodeToText[e])
    //     });
    //     this.form.medicalCodeList = cityNames.join('/');
    //     console.log(this.form.medicalCodeList);
    // },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.isshow = false
      this.isDisable = false
      this.dialogFormVisible = false
      this.isDisableSave = false
    },
    save() {
      if (this.form.medicalCodeList.length == 0) {
        this.$baseMessage('请选择指定机构', 'error')
        return false
      }
      this.$refs['form'].validate(async (valid) => {
        this.isDisableSave = true
        if (valid) {
          const loading = this.$loading({
            lock: true,
            text: '此过程耗时较长，请耐心等待...',
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)',
          })
          const { msg, code, data } = await saveBcxy(this.form)
          loading.close()
          if (code == 0) {
            this.$baseMessage('新增成功', 'success')
            this.$emit('fetch-data')
            this.isDisableSave = false
            this.close()
          } else {
            this.$baseMessage(msg, 'error')
          }
        } else {
          this.isDisableSave = false
          return false
        }
      })
    },
    //补充协议模板列表
    getBcxymbList() {
      getNetTagSuppList({}).then((res) => {
        if (res.code == 0) {
          this.bcxymbList = res.data
        }
      })
    },
    //机构类别联动机构等级
    getLevelList() {
      if (this.form.medical_type) {
        selectOrganizationLevel(this.form.medical_type).then((res) => {
          console.log(res)
          this.form.jgLevel = []
          if (res.code == 0) {
            this.options = res.data
          }
        })
        // if (this.form.medical_type == "2") {
        //     //零售药店
        //     this.options = this.optionsYd;
        // } else if (this.form.medical_type == "1") {
        //     //医疗机构
        //     this.options = this.optionsJg;
        // }
        //机构列表
        this.getJgList()
      } else {
        console.log('机构类别联动机构等级空')
      }
    },
    //机构列表
    getJgList() {
      console.log(this.form)
      if (this.form.medical_type) {
        if (this.form.jgLevel.length > 0) {
          this.form.medicalCodeList = []
          var queryForm = {
            fixmedins_type: this.form.medical_type,
            aggrement_lvs: this.form.jgLevel,
          }
          console.log(this.form.jgLevel)
          //回流库数据查询
          getJgYdList(queryForm).then((res) => {
            console.log(res)
            if (res.code == 0) {
              const data = []
              const cities = res.data
              cities.forEach((city, index) => {
                data.push({
                  label: city.label,
                  key: city.key,
                  pinyin: city.label,
                })
              })
              this.data = data
            }
          })
          this.isshow = true
          this.isDisable = true
        } else {
          this.isshow = false
          this.isDisable = false
        }
      } else {
        this.isshow = false
        this.isDisable = false
        console.log('机构列表空')
      }
    },
  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-transfer {
    display: flex;
    align-items: center;
  }

  .el-transfer__buttons {
    padding: 0 10px;
  }
}
</style>