<script>
  import { AynsCommonDialog } from '@/common/dialog'
  import upload from '../../provincialInstitutions/upload'
  import { getDict } from '@/api_check/expense'

  export default {
    name: 'ConfirmDialog',
    mixins: [AynsCommonDialog, upload],
    data() {
      return {
        // 类型
        type: 'confirm',
        title: '标题',
        content: '这是一段内容',
        isHtml: false,
        show: true,
        cancelButton: true,
        confirmButton: true,
        callback: null,
        fileList: [],
        isDisabled: false,
        label: '',
        form: {
          year: '',
          expenseDetails: [],
          files: [],
        },
        selectList: [],
        rules: {
          year: [{ required: true, message: '请选择年!', trigger: 'change' }],
          files: [
            {
              required: true,
              validator: (rules, value, callback) => {
                if (value.length > 0) {
                  return callback()
                } else {
                  return callback(new Error('请上传文件'))
                }
              },
              message: '请上传文件',
              trigger: 'change',
            },
          ],
          expenseDetails: [
            {
              required: true,
              validator: (rules, value, callback) => {
                if (value.length > 0) {
                  return callback()
                } else {
                  return callback(new Error('请添加信息'))
                }
              },
              message: '请添加信息',
              trigger: 'change',
            },
          ],
        },
        tableRules: {
          admdvs: [{ required: true, message: '请选择!', trigger: 'change' }],
          name: [{ required: true, message: '请输入!', trigger: 'blur' }],
          account: [{ required: true, message: '请输入!', trigger: 'blur' }],
          bank: [{ required: true, message: '请输入!', trigger: 'blur' }],
          cardNo: [{ required: true, message: '请输入!', trigger: 'blur' }],
          linkman: [{ required: true, message: '请输入!', trigger: 'blur' }],
          phone: [{ required: true, message: '请输入!', trigger: 'blur' }],
        },
      }
    },
    created() {
      getDict().then((res) => {
        this.selectList = [res.data]
      })
    },
    methods: {
      handlerAdd() {
        this.form.expenseDetails.push({
          admdvs: '',
          name: '',
          unitId: 1,
          account: '',
          bank: '',
          cardNo: '',
          linkman: '',
          phone: '',
          year: '',
          name: '',
        })
      },
      async handlerConfirm() {
        try {
          await this.$refs.form.validate()
          await this.$refs.tableRules.validate()
          this.onConfirm()
        } catch (e) {}
      },
    },
  }
</script>

<template>
  <el-dialog
    modal-append-to-body
    append-to-body
    :close-on-click-modal="false"
    :close-on-press-escape="false"
    :visible.sync="show"
    :title="title"
    width="1100px"
    @close="onCancel"
  >
    <el-form
      label-position="right"
      label-width="80px"
      :model="form"
      :rules="rules"
      ref="form"
    >
      <div
        style="
          display: flex;
          align-items: center;
          justify-content: space-between;
        "
      >
        <el-form-item label="年份" prop="year">
          <el-date-picker
            v-model="form.year"
            type="year"
            :disabled="isDisabled"
            placeholder="选择年"
            value-format="yyyy"
          ></el-date-picker>
        </el-form-item>
        <el-form-item v-if="!isDisabled">
          <el-button type="primary" icon="el-icon-plus" @click="handlerAdd" />
        </el-form-item>
      </div>
      <el-form-item prop="expenseDetails">
        <el-form :model="form" ref="tableRules">
          <el-table border :data="form.expenseDetails">
            <el-table-column label="所属区域" align="center" prop="admdvs">
              <template slot-scope="scope">
                <el-form-item
                  v-if="!isDisabled"
                  :prop="'expenseDetails.' + scope.$index + '.admdvs'"
                  :rules="tableRules.admdvs"
                >
                  <el-select v-model="scope.row.admdvs" clearable>
                    <el-option
                      v-for="i in selectList"
                      :label="i.label"
                      :key="i.id"
                      :value="i.id"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <span v-else>{{ scope.row.admdvs }}</span>
              </template>
            </el-table-column>
            <el-table-column label="单位名称" align="center" prop="name">
              <template slot-scope="scope">
                <el-form-item
                  v-if="!isDisabled"
                  :prop="'expenseDetails.' + scope.$index + '.name'"
                  :rules="tableRules.name"
                >
                  <el-input placeholder="" v-model="scope.row.name"></el-input>
                </el-form-item>
                <span v-else>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column label="财务专户" align="center" prop="account">
              <template slot-scope="scope">
                <el-form-item
                  v-if="!isDisabled"
                  :prop="'expenseDetails.' + scope.$index + '.account'"
                  :rules="tableRules.account"
                >
                  <el-input
                    placeholder=""
                    v-model="scope.row.account"
                  ></el-input>
                </el-form-item>
                <span v-else>{{ scope.row.account }}</span>
              </template>
            </el-table-column>
            <el-table-column label="开户银行" align="center" prop="bank">
              <template slot-scope="scope">
                <el-form-item
                  v-if="!isDisabled"
                  :prop="'expenseDetails.' + scope.$index + '.bank'"
                  :rules="tableRules.bank"
                >
                  <el-input placeholder="" v-model="scope.row.bank"></el-input>
                </el-form-item>
                <span v-else>{{ scope.row.bank }}</span>
              </template>
            </el-table-column>
            <el-table-column label="账号" align="center" prop="cardNo">
              <template slot-scope="scope">
                <el-form-item
                  v-if="!isDisabled"
                  :prop="'expenseDetails.' + scope.$index + '.cardNo'"
                  :rules="tableRules.cardNo"
                >
                  <el-input
                    placeholder=""
                    v-model="scope.row.cardNo"
                  ></el-input>
                </el-form-item>
                <span v-else>{{ scope.row.cardNo }}</span>
              </template>
            </el-table-column>
            <el-table-column label="联系人" align="center" prop="linkman">
              <template slot-scope="scope">
                <el-form-item
                  v-if="!isDisabled"
                  :prop="'expenseDetails.' + scope.$index + '.linkman'"
                  :rules="tableRules.linkman"
                >
                  <el-input
                    placeholder=""
                    v-model="scope.row.linkman"
                  ></el-input>
                </el-form-item>
                <span v-else>{{ scope.row.linkman }}</span>
              </template>
            </el-table-column>
            <el-table-column label="联系方式" align="center" prop="phone">
              <template slot-scope="scope">
                <el-form-item
                  v-if="!isDisabled"
                  :prop="'expenseDetails.' + scope.$index + '.phone'"
                  :rules="tableRules.phone"
                >
                  <el-input placeholder="" v-model="scope.row.phone"></el-input>
                </el-form-item>
                <span v-else>{{ scope.row.phone }}</span>
              </template>
            </el-table-column>
          </el-table>
        </el-form>
      </el-form-item>
      <el-form-item
        label="附件上传"
        label-position="top"
        prop="files"
        v-if="!isDisabled"
      >
        <el-upload
          ref="uploadPicture"
          class="upload-demo"
          action=""
          :auto-upload="false"
          :on-remove="handleRemovePicture"
          :before-remove="beforeRemove"
          :on-change="onChangePicture"
          :multiple="true"
          :limit="1"
          :on-exceed="handleExceed"
        >
          <el-button size="small" icon="el-icon-upload2" type="primary">
            文件上传
          </el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="onCancel">取 消</el-button>
      <el-button type="primary" @click="handlerConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<style scoped lang="scss">
  ::v-deep .el-dialog__footer {
    border-top: 1px solid #dcdfe6;
  }
</style>
