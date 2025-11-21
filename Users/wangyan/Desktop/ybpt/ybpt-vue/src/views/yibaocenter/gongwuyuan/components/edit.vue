<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="660px"
    @close="close"
    :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="姓名" prop="name">
            <el-input v-model.trim="form.name" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <!--<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
        <!--<el-form-item label="性别" prop="sex">-->
        <!--<el-select v-model.trim="form.sex" class="w">-->
        <!--<el-option value="0" label="男"></el-option>-->
        <!--<el-option value="1" label="女"></el-option>           -->
        <!--</el-select>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <!--<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
        <!--<el-form-item label="年龄" prop="age">-->
        <!--<el-input-->
        <!--v-model.trim="form.age"-->
        <!--autocomplete="off"-->
        <!--&gt;</el-input>-->
        <!--</el-form-item>-->
        <!--</el-col>-->
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="证件类型" prop="cardType">
            <el-select v-model="form.cardType" clearable class="w">
              <el-option label="居民身份证" value="1"></el-option>
              <el-option
                label="澳门特区护照/港澳居民来往内地通行证"
                value="2"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="证件号" prop="certno">
            <el-input
              v-model.trim="form.certno"
              autocomplete="off"
              :disabled="is_outside_flag"
            ></el-input>
          </el-form-item>
        </el-col>
        <!-- 出生日期 -->
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="出生日期" prop="birthday">
            <el-date-picker
              v-model="form.birthday"
              type="date"
              value-format="yyyy-MM-dd"
              placeholder="选择日期"
              :disabled="form.cardType == 1"
            ></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="手机号" prop="telephone">
            <el-input
              v-model.trim="form.telephone"
              autocomplete="off"
              :disabled="is_outside_flag"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="参保统筹区" prop="insu_admdvs">
            <el-select v-model.trim="form.insu_admdvs" class="w">
              <el-option
                v-for="item in admdvs"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="所属统筹区" prop="admdvs">
            <el-select v-model.trim="form.admdvs" class="w">
              <el-option
                v-for="item in admdvs"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-if="isCheck">
          <el-form-item label="单位名称" prop="emp_name">
            <el-input
              v-model.trim="form.emp_name"
              @click.native="openwin"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-else="isCheck">
          <el-form-item label="单位名称" prop="emp_name">
            <el-input
              v-model.trim="form.emp_name"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="单位编号" prop="emp_code">
            <el-input
              v-model.trim="form.emp_code"
              autocomplete="off"
              disabled
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="单位类型" prop="emp_type">
            <el-select v-model.trim="form.emp_type" class="w" disabled>
              <el-option
                v-for="item in emp_types"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="参保险种" prop="insutype">
            <el-select v-model.trim="form.insutype" class="w">
              <el-option
                v-for="item in insutypes"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="参保状态" prop="insu_state">
            <el-select v-model.trim="form.insu_state" class="w">
              <el-option
                v-for="item in psn_insu_stas"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="异地就医" prop="outside_flag">
            <el-select
              v-model.trim="form.outside_flag"
              class="w"
              :disabled="is_outside_flag"
            >
              <el-option label="否" value="0"></el-option>
              <el-option label="是" value="1"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="体检资格" prop="outside_flag">
            <el-select v-model.trim="form.qualifications" class="w">
              <el-option label="有" value="1"></el-option>
              <el-option label="无" value="0"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="生存状态" prop="outside_flag">
            <el-select
              v-model.trim="form.death_flag"
              class="w"
              :disabled="is_outside_flag"
            >
              <el-option
                v-for="item in natyList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="所在部门" prop="outside_flag">
            <el-select
              v-model="form.dept_id"
              placeholder="选择所在部门"
              class="w"
              @change="selectOrg()"
            >
              <el-option
                v-for="it in deptList"
                :key="it.id"
                :label="it.dept_name"
                :value="it.id"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button
        type="primary"
        @click="save"
        :loading="loading"
        :disabled="form.disabled"
      >
        {{ loading ? '确定中 ...' : '确定' }}
      </el-button>
    </div>
    <hospital ref="userhospital" @fetch-data="hospital"></hospital>
  </el-dialog>
</template>

<script>
  import { getDicts } from '@/api/dictManagement'

  import {
    addCiviWorkerInfo,
    info,
    updateCiviWorkerInfo,
  } from '@/api_check/civilworker_info.js'
  import { findDeptInfo } from '@/api_check/dept_info.js'
  import { selectByEmpCode } from '@/api_check/EmployingInfo.js'
  import Hospital from '@/components/userhospital'
  import { CodeToText, regionDataPlus } from 'element-china-area-data'
  export default {
    name: 'UserManagementEdit',
    components: { Hospital },
    data() {
      return {
        options: regionDataPlus,
        form: {
          loading: false,
          disabled: false,
          name: '',
          sex: '',
          age: '',
          certno: '',
          birthday: '',
          cardType: '',
          insu_admdvs: '',
          admdvs: '',
          emp_name: '',
          emp_code: '',
          telephone: '',
          emp_type: '',
          insutype: '320',
          insu_state: '1',
          outside_flag: '0',
          qualifications: '1',
          death_flag: '1',
          administrative_unit: '',
          usertype: '',
          dept_id: '',
          dept_name: '',
        },
        rules: {
          name: [
            { required: true, trigger: 'submit', message: '请输入正确的信息' },
          ],
          birthday: [
            { required: true, trigger: 'submit', message: '请选择出生日期' },
          ],
          sex: [
            { required: true, trigger: 'submit', message: '请输入正确的信息' },
          ],
          age: [
            { required: true, trigger: 'submit', message: '请输入正确的信息' },
          ],
          cardType: [
            { required: true, trigger: 'submit', message: '请选择证件类型' },
          ],
          certno: [
            { required: true, trigger: 'submit', message: '请输入证件号' },
            {
              validator: (rule, value, callback) => {
                if (
                  this.form.cardType === '1' &&
                  !this.validateIDNumber(value)
                ) {
                  callback(new Error('身份证号格式错误'))
                } else {
                  callback()
                }
              },
            },
            // {
            //   required: true,
            //   trigger: 'submit',
            //   pattern: /^\d{6}(18|19|20)?\d{2}(0[1-9]|1[0-2])(([0-2][1-9])|10|20|30|31)\d{3}(\d|X|x)$/,
            //   message: '身份证号格式不正确',
            // },
          ],
          telephone: [
            {
              required: false,
              trigger: 'blur',
              pattern: /^1[3-9]\d{9}$/,
              message: '手机号格式错误',
            },
          ],
          emp_name: [
            { required: true, trigger: 'submit', message: '请输入正确的信息' },
          ],
        },
        title: '',
        dialogFormVisible: false,
        admdvs: [],
        emp_types: [],
        insutypes: [],
        psn_insu_stas: [],
        id: '',
        is_outside_flag: false,
        isCheck: false,
        natyList: [],
        showDeath: false,
        dept: {
          dept_no: '',
          emp_code: '',
        },
        deptList: [],
        loading: false,
      }
    },
    created() {
      this.getAdmdvs()
      this.getEmpType()
      this.getInsuType()
      this.getPsnInsuStas()
    },
    watch: {
      'form.certno'(newVal) {
        if (this.form.cardType === '1') {
          if (this.validateIDNumber(newVal)) {
            this.parseBirthdayFromID(newVal)
          } else {
            this.form.birthday = '' // 验证失败清空生日
          }
        }
      },
    },
    methods: {
      // 验证身份证号合法性
      validateIDNumber(idNumber) {
        if (!idNumber) return false

        // 基础校验
        const reg = /^\d{17}[\dXx]$/
        if (!reg.test(idNumber)) {
          return false
        }

        // 校验码验证
        const factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2]
        const parity = ['1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2']
        const codes = idNumber.toUpperCase().split('')

        let sum = 0
        for (let i = 0; i < 17; i++) {
          sum += parseInt(codes[i]) * factor[i]
        }

        return parity[sum % 11] === codes[17]
      },

      // 解析出生日期
      parseBirthdayFromID(idNumber) {
        const birthdayStr = idNumber.slice(6, 14)
        const pattern = /^(\d{4})(\d{2})(\d{2})$/

        if (pattern.test(birthdayStr)) {
          const formatted = birthdayStr.replace(pattern, '$1-$2-$3')
          const date = new Date(formatted)

          // 验证日期有效性
          if (!isNaN(date.getTime())) {
            this.form.birthday = formatted
          }
        }
      },
      selectOrg() {},
      showDia(row) {
        this.loading = false
        this.natyData()
        var userinfo = JSON.parse(localStorage.getItem('userinfo'))
        this.user_type = userinfo.user_type
        if (this.user_type == 1) {
          this.isCheck = true
        } else {
          this.dept.emp_code = userinfo.org_code
          this.isCheck = false
        }
        findDeptInfo(this.dept).then((res) => {
          this.deptList = res.data
        })
        if (!row) {
          this.is_outside_flag = false
          this.title = '添加'
          this.getEmpInfo()
          this.id = ''
        } else {
          this.id = row.id
          this.title = '编辑'
          this.info(this.id)
          // this.form = Object.assign({}, row)
          if (row.info_source == 1) {
            this.is_outside_flag = true
          } else {
            this.is_outside_flag = false
          }
        }

        this.dialogFormVisible = true
      },
      handleChange(value) {
        let cityNames = []
        value.forEach((e) => {
          cityNames.push(CodeToText[e])
        })
        this.citys = cityNames.join('/')
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        // clearValidate()  清理某个字段的表单验证信息
        this.dialogFormVisible = false
      },
      save() {
        var that = this
        if (this.loading) {
          return
        }

        that.$refs['form'].validate(async (valid) => {
          if (valid) {
            // console.log(that.id);
            this.loading = true
            this.form.disabled = true
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false
              this.form.disabled = false
            }, 1000)
            if (that.id == '') {
              addCiviWorkerInfo(that.form).then((res) => {
                if (res.code == 0) {
                  that.$emit('fetch-data')
                  that.$baseMessage('操作成功', 'success')
                } else {
                  that.$baseMessage(res.msg, 'error')
                }
              })
            } else {
              updateCiviWorkerInfo(that.form).then((res) => {
                if (res.code == 0) {
                  that.$emit('fetch-data')
                  that.$baseMessage('操作成功', 'success')
                } else {
                  that.$baseMessage(res.msg, 'error')
                }
              })
            }
            that.close()
          } else {
            return false
          }
        })
      },
      //获取统筹区
      async getAdmdvs() {
        const res = await getDicts({ type: 'ADMDVS' })
        if (res.code == '0') {
          this.admdvs = res.data
        }
      },
      //获取单位类型
      async getEmpType() {
        const res = await getDicts({ type: 'EMP_TYPE' })
        if (res.code == '0') {
          this.emp_types = res.data
        }
      },
      //获取参保险种
      async getInsuType() {
        const res = await getDicts({ type: 'INSUTYPE' })
        if (res.code == '0') {
          this.insutypes = res.data
        }
      },
      //获取参保状态
      async getPsnInsuStas() {
        const res = await getDicts({ type: 'PSN_INSU_STAS' })
        if (res.code == '0') {
          this.psn_insu_stas = res.data
        }
      },
      getEmpInfo() {
        var that = this

        selectByEmpCode().then((res) => {
          var data = res.data
          if (null != data) {
            that.form.emp_type = data.emp_type
            that.form.insu_admdvs = data.admdvs
            that.form.admdvs = data.admdvs
            that.form.emp_name = data.emp_name
            that.form.emp_code = data.emp_no
          }
        })
      },
      info(id) {
        var that = this
        info(id).then((res) => {
          var data = res.data
          if (null != data) {
            that.form = data
          }
        })
      },
      async natyData() {
        var that = this
        const res = await getDicts({ type: 'SURV_STAS' })
        if (res.code == '0') {
          this.natyList = res.data
        }
      },
      openwin() {
        this.$refs['userhospital'].showDia('4')
      },
      hospital(data) {
        var that = this
        console.log(data)
        that.form.emp_name = data.medins_name
        that.form.emp_code = data.medins_code
        that.form.emp_type = data.fixmedins_type
        // that.medins_name = data.medins_name;
      },
    },
  }
</script>
