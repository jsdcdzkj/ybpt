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
          <el-form-item label="年份" prop="year" >
            <el-date-picker clearable type="year" format="yyyy" value-format="yyyy"
                            v-model="form.year"></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
          <el-form-item label="统筹区" prop="admdvs">
            <el-select v-model="form.admdvs" disabled
                       style="width: 100%">
              <el-option v-for="item in admdvs" :key="item.value" :label="item.label"
                         :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="模版下载">
            <el-button icon="el-icon-download" type="primary" @click="dowmb">下载</el-button>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
          <el-form-item label="文件上传" required>
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
import {importData, oneInfo} from '@/api/qs'
import {regionDataPlus} from 'element-china-area-data'
import {getDicts} from "@/api/dictManagement";
import {getFixmedinsB} from "@/api/sbApply";
import {fileURL} from "@/config/setting.config";

export default {
    name: 'UserManagementEdit',
    data() {
      return {
        options: regionDataPlus,
        admdvs: null,
        fixmedinsB: '',
        form: {
          admdvs: '',
          year: '',

        },
        rules: {
          admdvs: [
            { required: true, trigger: 'blur', message: '请选择统筹区' },
          ],
          year: [{ required: true, trigger: 'blur', message: '请选择年份' }],

        },
        title: '',
        dialogFormVisible: false,
        loading: false,
        fileList:""
      }
    },
    created() {

    },
    methods: {
      async showDia(row) {
        if (!row) {
          this.title = '上传数据'
          this.getAdmdvs();
          this.userinfo = JSON.parse(localStorage.getItem("userinfo"));
          this.form.admdvs = this.userinfo.org_code
          // await getFixmedinsB().then((res) => {
          //   this.form.admdvs = res.data.fixmedins_code
          // })
        } else {
          this.title = '修改'
          console.log(row);
          oneInfo(row).then((res) => {
            if (res.code == 0) {
              this.form = res.data;
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
          }
        })

      },
      async handleChange2(file, fileList) {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.dialogFormVisible = true
            this.loading = true
            this.fileList = fileList;
            let fd = new FormData();
            fd.append("admdvs", this.form.admdvs);
            fd.append("year", this.form.year);
            this.fileList.forEach(item => {
              //文件信息中raw才是真的文件
              fd.append("file", item.raw);
            })

            var result = await importData(fd);
            if (result.data.code == 0) {
              this.$emit('fetch-data')
              this.$refs.upfile.clearFiles();
              this.$baseMessage("上传成功", 'success')
            } else {
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
      dowmb(){
        self.location.href = fileURL + "/file/template/清算确认书数据模板.xlsx" ;
      }
    },
  }
</script>
