<template>
  <el-dialog
          :title="title"
          :visible.sync="dialogFormVisible"
          width="660px"
          @close="close"
          append-to-body
          v-loading="loading" element-loading-text="上传中······"
          element-loading-spinner="el-icon-loading"
          element-loading-background="rgba(0, 0, 0, 0.8)"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="生效时间" prop="year" >
            <el-date-picker clearable type="date" format="yyyy-MM-dd" value-format="yyyy-MM-dd"
                            v-model="form.year"></el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
          <el-form-item >
            <el-upload ref="upfile"
                       :auto-upload="false" :show-file-list=false accept=".xlsx"
                       :on-change="handleChange2"
                       :on-success="handleSuccess" action="#">
              <el-button icon="el-icon-upload2" type="success">文件上传</el-button>

            </el-upload>
          </el-form-item>
        </el-col>
        <el-col >
          <el-form-item >
          <span style="color: red">(请上传07版的Excel文件)</span>
          </el-form-item>
        </el-col>

      </el-row>
    </el-form>
    <!--<div slot="footer" class="dialog-footer">-->
    <!--<el-button @click="close">关 闭</el-button>-->
    <!--<el-button type="primary" @click="save" :loading="loading" :disabled="form.disabled">-->
    <!--{{ loading ? '确定中 ...' : '确定' }}-->
    <!--</el-button>-->
    <!--</div>-->
  </el-dialog>
</template>

<script>
  import { importData } from '@/api/drug'
  import { regionDataPlus, CodeToText } from 'element-china-area-data'
  import {getDicts} from "@/api/dictManagement";
  export default {
    name: 'UserManagementEdit',
    data() {
      return {
        options: regionDataPlus,
        admdvs: null,
        form: {
          admdvs: '',
          year: '',

        },
        rules: {
          year: [{ required: true, trigger: 'blur', message: '请选择生效时间' }],
        },
        title: '',
        dialogFormVisible: false,
        loading: false,
      }
    },
    created() {

    },
    methods: {
      showDia(row) {
        if (!row) {
          this.title = '上传数据'
          this.getAdmdvs() ;
        } else {
          this.title = '修改'
          console.log(row);
          oneInfo(row).then((res) => {
            if (res.code == 0) {
              this.form = res.data ;
            }
          })
        }
        this.dialogFormVisible = true
      },
      close() {
        this.$refs['form'].resetFields()
        this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      save() {

      },
      getAdmdvs() {
        getDicts({"type": "admdvs-area"}).then((res) => {
          if (res.code == 0) {
            this.admdvs = res.data;
            console.log(this.admdvs);
          }
        })

      },
      async handleChange2(file, fileList) {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.loading = true
            this.fileList = fileList;
            let fd = new FormData();
            this.fileList.forEach(item => {
              //文件信息中raw才是真的文件
              fd.append("file", item.raw);
            })
            fd.append("year", this.form.year);
            var result = await importData(fd);
            if (result.data.code == 0) {
              this.$emit('fetch-data')
              this.$refs.upfile.clearFiles();
              this.$baseMessage("上传成功", 'success')
            } else {
              this.$refs.upfile.clearFiles();
              this.$baseMessage(result.data.msg, 'error')
            }
            this.dialogFormVisible = false
            this.loading = false
          } else {
            return false
          }
        })

      },
      handleSuccess() {
        this.$emit('fetch-data')
      },
    },
  }
</script>
