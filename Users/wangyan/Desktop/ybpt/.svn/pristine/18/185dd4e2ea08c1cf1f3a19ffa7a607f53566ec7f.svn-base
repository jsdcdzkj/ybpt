<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="500px"
      :close-on-click-modal="false"
      @close="close"
      append-to-body
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="考核内容" prop="assess_question">
        <el-input v-model="form.assess_question" placeholder="" type="textarea" rows="3" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="答复方式" prop="checkList">
        <el-checkbox-group v-model="form.checkList">
          <el-checkbox label="文本描述">文本描述</el-checkbox>
          <el-checkbox label="文件上传">文件上传</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="分值" prop="full_score">
        <el-input v-model.trim="form.full_score" type="number" autocomplete="off"></el-input>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
// import { doEdit } from '@/api/menuManagement'

export default {
  name: 'MenuManagementEdit',
  data() {
    return {
      form: {
        assess_question: '',
        checkList: [],
        full_score: 0,
        if_edit:"",
        index:""
      },
      rules: {
        'assess_question': [{required: true, trigger: 'blur', message: '请输入考核内容'}],
        'checkList': [{required: true, trigger: 'blur', message: '请选择答复方式'}],
        'full_score': [{required: true, trigger: 'blur', message: '请输入分值'}],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {
  },
  methods: {
    showEdit(row,index) {
      if (!row) {
        this.title = '添加'
        this.dialogFormVisible = true
        this.form = {
          assess_question: '',
              checkList: [],
              full_score: 0,
              if_edit:"1",//新增
              index:""
        };
      } else {
        this.title = '编辑'
        this.dialogFormVisible = true
        this.form = Object.assign({}, row)
        this.form.if_edit = "2";//修改
        this.form.index = index;
        //this.form.mtitle=
        //console.log(this.form)
      }

    },
    close() {
      // this.form = this.$options.data().form
      // this.$refs['form'].resetFields();
      this.dialogFormVisible = false
    },
    save() {
      var that = this;
      that.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.$baseMessage("已添加", 'success')
          this.$emit('fetch-data', that.form)
          that.close()
        } else {
          return false
        }
      })
    },
  },
}
</script>
