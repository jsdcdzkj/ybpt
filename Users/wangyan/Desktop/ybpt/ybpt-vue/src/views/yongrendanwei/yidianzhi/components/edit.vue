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
          <el-form-item label="证件号" prop="num">
            <el-input
              v-model.trim="form.num"
              autocomplete="off"
              @blur="matching()"
              @input="matching()"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="姓名" prop="">
            <el-input
              v-model.trim="form.name"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="开户行" prop="account_bank">
            <el-input
              v-model.trim="form.account_bank"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="年龄" prop="age">
            <el-input
              v-model.trim="form.age"
              autocomplete="off"
              :disabled="true"
            ></el-input>
          </el-form-item>
        </el-col>
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="统筹区" prop="username">
            <el-cascader :options="options" class="w" placeholder="可搜索" filterable clearable @change="handleChange">
            </el-cascader>
          </el-form-item>
        </el-col> -->
        <el-col
          :xs="24"
          :sm="24"
          :md="12"
          :lg="12"
          :xl="12"
          style="display: none"
        >
          <el-form-item label="就医地" prop="medplace">
            <el-input
              v-model.trim="form.medplace"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="性别" prop="sex" :disabled="true">
            <el-select v-model.trim="form.sex" class="w" :disabled="true">
              <el-option value="1" label="男"></el-option>
              <el-option value="2" label="女"></el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="负责人电话" prop="phone">
            <el-input v-model.trim="form.phone" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="银行卡号" prop="account_no">
            <el-input
              v-model.trim="form.account_no"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="安置年度" prop="relocated_year">
            <el-select v-model.trim="form.relocated_year" class="w">
              <el-option
                value="2022"
                label="2022"
                :disabled="opptionDisabled"
              ></el-option>
              <el-option
                v-for="index in 20"
                :key="index"
                :label="2022 + index"
                :value="2022 + index"
                :disabled="opptionDisabled"
              ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12" v-show="ishow">
          <el-form-item label="">
            特殊添加
            <el-radio v-model="form.checked" label="1">是</el-radio>
            <el-radio v-model="form.checked" label="2">否</el-radio>
          </el-form-item>
        </el-col>
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="单位编号" prop="username">
            <el-input v-model.trim="form.username" autocomplete="off"></el-input>
          </el-form-item>
        </el-col> -->
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="单位类型" prop="username">
            <el-select v-model.trim="form.username" class="w">
              <el-option value="0" label="男"></el-option>
              <el-option value="1" label="女"></el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="参保险种" prop="username">
            <el-select v-model.trim="form.username" class="w">
              <el-option value="0" label="男"></el-option> 
              <el-option value="1" label="女"></el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="参保状态" prop="username">
            <el-select v-model.trim="form.username" class="w">
              <el-option value="0" label="男"></el-option>
              <el-option value="1" label="女"></el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="异地就医" prop="username">
            <el-select v-model.trim="form.username" class="w">
              <el-option value="0" label="男"></el-option>
              <el-option value="1" label="女"></el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="死记标志" prop="username">
            <el-select v-model.trim="form.username" class="w">
              <el-option value="0" label="有"></el-option>
              <el-option value="1" label="无"></el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
        <!-- <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="体检资格" prop="username">
            <el-select v-model.trim="form.username" class="w">
              <el-option value="0" label="有"></el-option>
              <el-option value="1" label="无"></el-option>
            </el-select>
          </el-form-item>
        </el-col> -->
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { selectCivInfo } from '@/api_check/civilworker_info.js'
  import {
    saveRelocatedInfo,
    updateRelocatedInfo,
  } from '@/api_check/relocatedInfo'
  import { CodeToText, regionDataPlus } from 'element-china-area-data'
  export default {
    name: 'UserManagementEdit',
    data() {
      return {
        ishow: false,
        opptionDisabled: false,
        options: regionDataPlus,
        form: {
          account_no: '',
          account_bank: '',
          relocated_year: '',
          medplace: '',
          num: '',
          name: '',
          age: '',
          sex: '',
          phone: '',
          administrative_unit: '',
          org_code: '',
          checked: '2',
        },
        flag: '',
        loading: false,
        rules: {
          account_no: [
            { required: true, trigger: 'blur', message: '请输入银行卡号' },
          ],
          account_bank: [
            { required: true, trigger: 'blur', message: '请输入开户行' },
          ],
          num: [{ required: true, trigger: 'blur', message: '请输入身份证号' }],
          relocated_year: [
            { required: true, trigger: 'blur', message: '请输入安置年度' },
          ],
          phone: [
            { required: true, trigger: 'blur', message: '请输入负责人电话' },
          ],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {},
    methods: {
      showDia(row, add) {
        var userinfo = JSON.parse(localStorage.getItem('userinfo'))
        console.log(userinfo)
        userinfo.user_type == '1' ? (this.ishow = true) : false
        this.form.org_code = userinfo.org_code
        this.form.administrative_unit = userinfo.org_code
        if (row == undefined || add == 'add') {
          this.title = '添加'
          if (add == 'add') {
            this.form.name = row.name
            this.form.age = row.age
            this.form.sex = row.sex
            this.form.num = row.certno
          }
          this.flag = 'add'
          this.opptionDisabled = false
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form.num = row.num
          this.ishow = false
          this.flag = 'edit'
          console.log(row, this.form)
          this.opptionDisabled = true
        }
        this.dialogFormVisible = true
      },
      sex(row, index) {
        console.log(1)
        if (this.form.sex == 1) {
          return '男'
        } else {
          return '女'
        }
      },
      matching() {
        if (this.calculatebyte(this.form.num, this.form.org_code) == 18) {
          selectCivInfo(this.form.num, this.form.org_code).then((res) => {
            this.form.name = res.data.name
            this.form.age = res.data.age
            this.form.sex = res.data.sex
          })
        }
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
        this.dialogFormVisible = false
      },
      save() {
        if (this.loading) {
          return
        }
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.loading = true
            setTimeout(() => {
              this.loading = false
            }, 400)
            if (this.form.phone.length != 11) {
              this.$baseMessage('手机号输入有误', 'warning')
              return false
            }
            if (this.flag == 'add') {
              console.log('添加')
              var medplaceTemp = this.form.medplace
              if (
                medplaceTemp == null ||
                medplaceTemp == undefined ||
                medplaceTemp == ''
              ) {
                this.form.medplace = '无' //此字段按医保要求移除
              }
              const { msg } = await saveRelocatedInfo(this.form)
              this.$baseMessage(msg, 'success')
              this.$emit('fetch-data')
              this.close()
            } else {
              console.log('修改')
              const { msg } = await updateRelocatedInfo(this.form)
              this.$baseMessage(msg, 'success')
              this.$emit('fetch-data')
              this.close()
            }
          } else {
            return false
          }
        })
      },
      calculatebyte(sTargetStr) {
        var sTmpStr, sTmpChar
        var nOriginLen = 0
        var nStrLength = 0

        sTmpStr = new String(sTargetStr)
        nOriginLen = sTmpStr.length

        for (var i = 0; i < nOriginLen; i++) {
          sTmpChar = sTmpStr.charAt(i)

          if (escape(sTmpChar).length > 4) {
            nStrLength += 2
          } else if (sTmpChar != '/r') {
            nStrLength++
          }
        }
        return nStrLength
      },
    },
  }
</script>
