<template>
  <el-dialog
    :close-on-click-modal="false"
    :title="title"
    :visible.sync="dialogFormVisible"
    width="500px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="110px">
      <el-form-item label="医疗许可证" prop="url">
        <el-image
          :preview-src-list="form.srcList"
          :src="form.url"
          style="width: 200px; height: 200px"
        ></el-image>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
  import { getLicence } from '@/api/sbApply'
  import { fileURL } from '@/config/setting.config'
  import Add from './add'
  import Add2 from './add2'
  import Add3 from './add3'
  import Add4 from './add4'
  import Bed from './edit2'
  import Edit3 from './edit3'

  export default {
    name: 'First',
    components: {
      Add,
      Add2,
      Add3,
      Add4,
      Bed,
      Edit3,
    },
    data() {
      return {
        userinfo: '',
        fixmedinsB: '',
        form: {
          resource: 0,
          drugs: '',
          medical_type: '',
          url: '',
          srcList: [],
        },
        rules: {
          url: [
            { required: true, message: '请上传医疗许可证', trigger: 'change' },
          ],
          resource: [
            { required: true, message: '请选择申报类型', trigger: 'change' },
          ],
          drugs: [
            { required: true, message: '请选择药品类型', trigger: 'change' },
          ],
          medical_type: [
            { required: true, message: '请选择医疗类型', trigger: 'change' },
          ],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {},
    methods: {
      async showEdit(row) {
        const res = await getLicence({ org_code: row.orgCode })
        if (res.data == null) {
          this.$baseMessage('请上传医疗许可证', 'error')
          return
        }
        const url = fileURL + res.data.fileUrl
        this.form.url = ''
        this.form.url = url
        this.form.srcList = []
        this.form.srcList.push(url)
        this.title = '查看医疗许可证'
        this.userinfo = JSON.parse(localStorage.getItem('userinfo'))
        this.dialogFormVisible = true
      },
      fetchData() {
        this.$emit('fetch-data')
      },
      close() {
        this.$refs['form'].resetFields()
        this.dialogFormVisible = false
      },
      async save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            const row = {
              org_code: this.fixmedinsB.fixmedins_code,
              org_name: this.fixmedinsB.fixmedins_name,
              aggrement_lv: this.fixmedinsB.aggrement_lv,
              natures: this.biznet_typeFormat(),
              type: '',
              title: '',
              status: '0',
              drugs: this.form.drugs,
              medical_type: this.form.medical_type,
            }

            let e = ''
            row.type = this.form.medical_type
            /*政府非营利*/
            if (this.fixmedinsB.biznet == 3) {
              e = 1
            }
            /*营利性 民办非营利*/
            if (this.fixmedinsB.biznet == 1 || this.fixmedinsB.biznet == 2) {
              e = 2
            }

            const title = this.fixmedinsB.biznet == 3 ? '公立' : '非公立'
            switch (this.form.medical_type) {
              case 4:
                row.title =
                  title + '医疗机构实行市场调节价管理医疗服务项目价格明细表'
                break
              case 6:
                e = 4
                row.title = title + '医疗机构医疗服务自设项目自主定价明细表'
                break
              case 7:
                row.title = title + '医疗机构新增医疗服务项目价格明细表'
                break
              case 8:
                row.title =
                  title + '医疗机构市管未定价项目医疗服务项目价格明细表'
                break
              case 9:
                e = 9
                row.title = title + '医院病房床位明细'
                break
              case 10:
                e = 10
                row.title = title + '医院单人间、套间病房床位明细'
                break
            }
            console.log(row)

            if (e == 1) {
              this.$refs['add'].showDia(row)
            } else if (e == 2) {
              this.$refs['add2'].showDia(row)
            } else if (e == 3) {
              this.$refs['add3'].showDia(row)
            } else if (e == 4) {
              this.$refs['add4'].showDia(row)
            } else if (e == 9) {
              this.$refs['bed'].showDia(row)
            } else if (e == 10) {
              this.$refs['edit3'].showDia(row)
            }
            this.dialogFormVisible = false
          } else {
            return false
          }
        })
      },
      biznet_typeFormat() {
        let statusW = ''
        switch (this.fixmedinsB.biznet) {
          case '1':
            statusW = '营利性'
            break
          case '2':
            statusW = '民办非营利'
            break
          case '3':
            statusW = '政府非营利'
            break
        }
        return statusW
      },
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .drugs-type {
      .el-radio {
        width: 80px;
        line-height: 2;
      }
    }
  }
  ::v-deep {
    .el-dialog__header {
      padding-bottom: 20px;
    }
  }
  ::v-deep {
    .medical_type-type {
      .el-radio {
        width: 120px;
        line-height: 2;
      }
    }
  }
</style>
