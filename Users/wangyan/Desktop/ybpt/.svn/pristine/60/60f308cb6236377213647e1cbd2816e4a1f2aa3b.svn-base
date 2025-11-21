<script>
  import { AynsCommonDialog } from '@/common/dialog'
  import upload from '../../provincialInstitutions/upload'
  import { getDict, uploadFileBatch } from '@/api_check/expense'

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
        tableData: [], // 用于存储表格数据
        uploadFiles: [], // 用于存储待上传的文件
      }
    },
    created() {
      getDict().then((res) => {
        this.selectList = [res.data]
      })
    },
    methods: {
      async handlerConfirm() {
        try {
          await this.$refs.form.validate()
          await this.$refs.tableRules.validate()
          this.onConfirm()
        } catch (e) {}
      },
      // 自定义上传方法
      customUpload({ file, onSuccess, onError }) {
        const formData = new FormData()
        formData.append('file', file)

        uploadFileBatch(formData)
          .then((res) => {
            this.form.admdvsName = res.data.data.admdvsName
            this.form.linkman = res.data.data.linkman
            this.form.phone = res.data.data.phone
            this.form.bank = res.data.data.bank
            this.form.cardNo = res.data.data.cardNo
            this.form.account = res.data.data.account
            this.form.personNum = res.data.data.personNum
            this.form.money = res.data.data.money
            this.form.expenseDetails = res.data.data.expenseDetails
            this.$message({
              message: '导入成功！',
              type: 'success',
            })
          })
          .catch((error) => {
            this.$message.error('导入失败！')
          })
      },

      // 上传前处理
      beforeUpload(file) {
        // 这里可以添加一些额外的处理逻辑，比如验证文件大小、类型等
        const isExcel =
          file.type ===
            'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' ||
          file.type === 'application/vnd.ms-excel'
        if (!isExcel) {
          this.$message.error('上传文件只能是 Excel 格式!')
          return false
        }
        return true
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
      label-width="100px"
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
            :readonly="isDisabled"
            placeholder="选择年"
            value-format="yyyy"
          ></el-date-picker>
        </el-form-item>
        <el-form-item v-if="!isDisabled">
          <el-upload
            class="upload-demo"
            action=""
            :http-request="customUpload"
            :show-file-list="false"
            :before-upload="beforeUpload"
          >
            <el-button size="mini" type="primary">选择Excel文件上传</el-button>
          </el-upload>
        </el-form-item>
      </div>
      <el-row :gutter="20">
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item label="所属区" prop="admdvsName">
            <el-input
              v-model.trim="form.admdvsName"
              readonly
              placeholder="所属区"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item label="联系人" prop="linkman">
            <el-input
              v-model.trim="form.linkman"
              readonly
              placeholder="联系人"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item label="联系方式" prop="phone">
            <el-input
              v-model.trim="form.phone"
              readonly
              placeholder="联系方式"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item label="开户银行" prop="bank">
            <el-input
              v-model.trim="form.bank"
              readonly
              placeholder="开户银行"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item label="银行账号" prop="cardNo">
            <el-input
              v-model.trim="form.cardNo"
              readonly
              placeholder="银行账号"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item label="财政专户" prop="account">
            <el-input
              v-model.trim="form.account"
              readonly
              placeholder="财政专户"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item prop="personNum">
            <span slot="label">
              <span class="span-box">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="所属区内各单位享受公务员医疗补助人员总数"
                  placement="top"
                >
                  <i class="el-icon-warning" style="color: #999" />
                </el-tooltip>

                <span>人员总数</span>
              </span>
            </span>
            <el-input
              v-model.trim="form.personNum"
              readonly
              placeholder="人员总数"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item prop="money">
            <span slot="label">
              <span class="span-box">
                <el-tooltip
                  class="item"
                  effect="dark"
                  content="所属区内各单位享受公务员医疗补助人员总数*600元/每人"
                  placement="top"
                >
                  <i class="el-icon-warning" style="color: #999" />
                </el-tooltip>

                <span>总金额</span>
              </span>
            </span>

            <el-input
              v-model.trim="form.money"
              readonly
              placeholder="总金额"
              autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
      </el-row>
      <el-form-item prop="expenseDetails">
        <el-form :model="form" ref="tableRules">
          <el-table border :data="form.expenseDetails" max-height="500">
            <el-table-column
              label="下涉单位编码（缴纳社会保险费时生成的单位编码）"
              align="center"
              prop="unitId"
            ></el-table-column>
            <el-table-column
              label="单位名称"
              align="center"
              prop="unitName"
            ></el-table-column>
            <el-table-column
              label="单位人数"
              align="center"
              width="90"
              prop="personNum"
            ></el-table-column>
            <el-table-column
              label="备注"
              width="170"
              align="center"
              prop="remark"
            ></el-table-column>
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
