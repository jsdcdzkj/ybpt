<script>
  import { AynsCommonDialog } from '@/common/dialog'
  import { saveOrUpdate } from '@/api_check/expense'
  import upload from '../upload'
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
        isDisabled: false,
        form: {
          admdvs: '',
          name: '',
          unitId: '',
          account: '',
          bank: '',
          cardNo: '',
          linkman: '',
          phone: '',
          year: '',
          files: [],
        },
        rules: {
          admdvs: [{ required: true, message: '请选择!', trigger: 'change' }],
          name: [{ required: true, message: '请输入!', trigger: 'blur' }],
          unitId: [{ required: true, message: '请输入!', trigger: 'blur' }],
          account: [{ required: true, message: '请输入!', trigger: 'blur' }],
          bank: [{ required: true, message: '请输入!', trigger: 'blur' }],
          phone: [{ required: true, message: '请输入!', trigger: 'blur' }],
          cardNo: [{ required: true, message: '请输入!', trigger: 'blur' }],
          linkman: [{ required: true, message: '请输入!', trigger: 'blur' }],
          year: [{ required: true, message: '请输入!', trigger: 'blur' }],
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
              message: '请上传文件!',
              trigger: 'blur',
            },
          ],
        },
      }
    },
    methods: {},
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
    <el-form label-width="80px" ref="form" inline :model="form" :rules="rules">
      <el-form-item label="年份" prop="year">
        <el-date-picker
          v-model="form.year"
          type="year"
          :disabled="isDisabled"
          placeholder="选择年"
          value-format="yyyy"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="单位名称" prop="name">
        <el-input
          style="width: 100%"
          placeholder="请输入单位名称"
          v-model="form.name"
          :disabled="isDisabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="财务账户" prop="account">
        <el-input
          placeholder="请输入单位名称"
          style="width: 100%"
          v-model="form.account"
          :disabled="isDisabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="开户银行" prop="bank">
        <el-input
          style="width: 100%"
          v-model="form.bank"
          :disabled="isDisabled"
          placeholder="请输入开户银行"
        ></el-input>
      </el-form-item>
      <el-form-item label="账号" prop="cardNo">
        <el-input
          style="width: 100%"
          v-model="form.cardNo"
          :disabled="isDisabled"
          placeholder="请输入账号"
        ></el-input>
      </el-form-item>
      <el-form-item label="联系人" prop="linkman">
        <el-input
          style="width: 100%"
          v-model="form.linkman"
          placeholder="请输入联系人"
          :disabled="isDisabled"
        ></el-input>
      </el-form-item>
      <el-form-item label="联系方式" prop="phone">
        <el-input
          placeholder="请输入联系方式"
          style="width: 100%"
          v-model="form.phone"
          :disabled="isDisabled"
        ></el-input>
      </el-form-item>
      <el-form-item
        label="附件上传"
        label-position="top"
        prop="files"
        style="width: 400px"
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
          :file-list="form.files"
        >
          <el-button size="small" icon="el-icon-upload2" type="primary">
            文件上传
          </el-button>
        </el-upload>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer" v-if="!isDisabled">
      <el-button @click="onCancel">取 消</el-button>
      <el-button type="primary" @click="onConfirm">确 定</el-button>
    </div>
  </el-dialog>
</template>

<style scoped lang="scss">
  ::v-deep .el-dialog__footer {
    border-top: 1px solid #dcdfe6;
  }
</style>
