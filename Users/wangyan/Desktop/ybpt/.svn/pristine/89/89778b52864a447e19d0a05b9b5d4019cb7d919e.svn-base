<template>
  <el-drawer
    :before-close="handleClose"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    append-to-body
    size="70%"
    ref="drawer"
  >
    <div class="drawer_content" style="padding: 20px">
      <el-form ref="form" :model="apply" :rules="rules" label-width="110px">
        <h4 class="inform-title">
          {{ headline }}医院单人间、套间病房床位申报表
        </h4>
        <el-row :gutter="20">
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-form-item label="单位名称">
              <el-input
                v-model="apply.org_name"
                placeholder="请输入"
                autocomplete="off"
                disabled=""
                readonly
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-form-item label="医院等级">
              <el-input
                v-model="apply.aggrement_lv"
                placeholder="请输入"
                autocomplete="off"
                disabled=""
                readonly
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-form-item label="床位总数" prop="beds_all_count">
              <el-input
                v-model="apply.beds_all_count"
                placeholder="请输入"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-form-item label="联系人" prop="linkman">
              <el-input
                v-model="apply.linkman"
                placeholder="请输入"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-form-item label="联系电话" prop="phone">
              <el-input
                v-model="apply.phone"
                placeholder="请输入"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-form-item
              label="各等级病房床位所占比例"
              prop="account_for"
              class="custemitem"
            >
              <el-input
                v-model="apply.account_for"
                placeholder="请输入"
                autocomplete="off"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :xs="14" :sm="14" :md="14" :lg="14" :xl="14">
            <el-form-item label="附件上传">
              <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
                <el-upload
                  class="upload-demo"
                  accept=".pdf, .doc, .docx"
                  action=""
                  :auto-upload="false"
                  :on-remove="handleRemove"
                  :on-change="handleChange"
                  :file-list="fileList2"
                >
                  <el-button size="small" type="primary">点击上传</el-button>
                </el-upload>
              </el-col>
            </el-form-item>
          </el-col>
        </el-row>

        <div class="box_card mt2">
          <div class="right-add-btn">
            <el-button
              type="success"
              icon="el-icon-plus"
              size="mini"
              @click="handleAddBtn"
            >
              添加
            </el-button>
          </div>
          <el-table
            ref="tb"
            :data="tableData2"
            :header-cell-style="{
              background: 'rgb(113 167 228)',
              color: '#fff',
            }"
            :row-class-name="rowClassName"
            border
            style="width: 100%; cursor: pointer"
            @selection-change="handleDetailSelectionChange"
          >
            <el-table-column
              prop="project_code"
              align="center"
              :required="true"
              label="项目编码"
            >
              <template slot-scope="{ row, $index }">
                <span v-if="!showEdit[$index]">{{ row.project_code }}</span>
                <el-input
                  v-model="tableData2[row.xh - 1].project_code"
                  v-if="showEdit[$index]"
                  placeholder="请选择"
                  @click.native="openwin(row.xh - 1, row.project_code)"
                  readonly
                >
                  <el-button
                    slot="append"
                    icon="el-icon-search"
                    @click="openwin"
                  ></el-button>
                </el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="inpatientWard"
              align="center"
              :required="true"
              label="项目名称"
            >
              <template slot-scope="{ row, $index }">
                <span v-if="!showEdit[$index]">{{ row.project_name }}</span>
                <el-input
                  v-if="showEdit[$index]"
                  v-model="tableData2[row.xh - 1].project_name"
                  readonly=""
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="inpatientWard"
              align="center"
              :required="true"
              label="病区"
            >
              <template slot-scope="{ row, $index }">
                <span v-if="!showEdit[$index]">{{ row.inpatientWard }}</span>
                <el-input
                  v-if="showEdit[$index]"
                  v-model="tableData2[row.xh - 1].inpatientWard"
                  placeholder="请输入"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="bed_count"
              align="center"
              :required="true"
              label="床位数（张）"
            >
              <template slot-scope="{ row, $index }">
                <span v-if="!showEdit[$index]">{{ row.bed_count }}</span>
                <el-input
                  v-if="showEdit[$index]"
                  v-model="tableData2[row.xh - 1].bed_count"
                  type="number"
                  placeholder="请输入"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="bed_number"
              align="center"
              :required="true"
              label="床位号"
            >
              <template slot-scope="{ row, $index }">
                <span v-if="!showEdit[$index]">{{ row.bed_number }}</span>
                <el-input
                  v-if="showEdit[$index]"
                  v-model="tableData2[row.xh - 1].bed_number"
                  placeholder="请输入"
                ></el-input>
              </template>
            </el-table-column>

            <el-table-column
              prop="price"
              align="center"
              :required="true"
              label="价格（元/床/日）"
            >
              <template slot-scope="{ row, $index }">
                <span v-if="!showEdit[$index]">{{ row.price }}</span>
                <el-input
                  v-if="showEdit[$index]"
                  v-model="tableData2[row.xh - 1].price"
                  type="number"
                  placeholder="请输入"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              prop="remark"
              align="center"
              :required="true"
              label="备注"
            >
              <template slot-scope="{ row, $index }">
                <span v-if="!showEdit[$index]">{{ row.remark }}</span>
                <el-input
                  v-if="showEdit[$index]"
                  v-model="tableData2[row.xh - 1].remark"
                  placeholder="请输入"
                ></el-input>
              </template>
            </el-table-column>
            <el-table-column
              header-align="center"
              align="center"
              width="140"
              label="操作"
            >
              <template slot-scope="{ row, $index }">
                <el-button
                  v-if="!showEdit[$index]"
                  type="text"
                  size="small"
                  @click="showUpdate($index, row)"
                >
                  编辑
                </el-button>
                <el-button
                  v-if="showEdit[$index]"
                  type="text"
                  size="small"
                  style="color: #85ce61"
                  @click="submit($index, row)"
                >
                  确定
                </el-button>
                <el-button
                  v-if="showEdit[$index]"
                  type="text"
                  size="small"
                  style="color: #e6a23c"
                  @click="cancelUpdate($index)"
                >
                  取消
                </el-button>
                <el-button
                  type="text"
                  size="mini"
                  style="color: #f56c6c"
                  @click="handleDeleteBtn($index, row)"
                >
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </el-form>

      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
        <el-button type="primary" @click="save" :loading="loading">
          {{ loading ? '提交中 ...' : '提 交' }}
        </el-button>
      </div>
    </div>
    <medicinal ref="medicinal" @fetch-data="fetchData"></medicinal>
  </el-drawer>
</template>

<script>
  import { getInfo, soloBedDeclaration, verify } from '@/api/drug'
  import { uploadDetailFile } from '@/api/sbApply'
  import Medicinal from './medicinal2'
  export default {
    name: 'kaohe',
    components: { Medicinal },
    data() {
      return {
        value3: '',
        headline: '',
        title: '',
        dialog: false,
        loading0: false,
        loading: false,
        form: {
          info: '',
        },
        apply: {
          org_code: '',
          org_name: '',
          natures: '',
          aggrement_lv: '',
          beds_all_count: '',
          account_for: '',
          linkman: '',
          phone: '',
          as: {
            cc: '',
            dd: '',
          },
        },
        rules: {
          beds_all_count: [
            { required: true, message: '请输入', trigger: 'blur' },
          ],
          account_for: [{ required: true, message: '请输入', trigger: 'blur' }],
          linkman: [{ required: true, message: '请输入', trigger: 'blur' }],
          phone: [{ required: true, message: '请输入', trigger: 'blur' }],
        },
        timer: null,
        tableData2: [],
        checkedDetail: [],
        showEdit: [],
        fileList2: [],
        ids: [
          {
            uid: '',
            fileInfoId: '',
          },
        ],
      }
    },
    mounted() {},
    methods: {
      showDia(row) {
        this.info()
        this.tableData2 = []
        this.ids = []
        this.apply = ''
        this.fileList2 = []
        this.form = Object.assign({}, row)
        this.dialog = true
      },
      async handleChange(file, fileList2) {
        let fd = new FormData()
        fd.append('file', file.raw)
        var result = await uploadDetailFile(fd)
        if (result.data.code == 0) {
          const obj3 = {}
          obj3.uid = file.uid
          obj3.fileInfoId = result.data.data.id
          this.ids.push(obj3)
          console.log(this.ids)
          this.$baseMessage('上传成功', 'success')
        } else {
          this.$baseMessage(result.data.msg, 'error')
        }
      },
      async handleRemove(file, fileList2) {
        for (var i = 0; i < this.ids.length; i++) {
          if (this.ids[i].uid == file.uid) {
            let fd = new FormData()
            fd.append('fileInfoId', this.ids[i].fileInfoId)
            await uploadDetailFile(fd)
            this.ids.splice(i, 1)
          }
        }
        console.log(this.ids)
      },
      handleClick(row) {
        // 动态设置数据并通过这个数据判断显示方式
        if (row.isEdit) {
          row.isEdit = false
        } else {
          this.$set(row, 'isEdit', true)
        }
      },
      // 表格的新增
      rowClassName({ row, rowIndex }) {
        row.xh = rowIndex + 1
      },
      // 单选框选中数据
      handleDetailSelectionChange(selection) {
        this.checkedDetail = selection
      },
      // 点击新增更多
      handleAddBtn() {
        const obj2 = {}
        obj2.project_code = ''
        obj2.project_name = ''
        obj2.inpatientWard = ''
        obj2.bed_count = ''
        obj2.bed_number = ''
        obj2.bed_class = ''
        obj2.price = ''
        obj2.remark = ''
        this.tableData2.push(obj2)
      },
      // 删除
      handleDeleteBtn(index, row) {
        this.$confirm('请是否确认删除该属性?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          callback: (action) => {
            if (action === 'confirm') {
              this.tableData2.splice(index, 1)
              this.$message({
                message: '删除成功，记得保存修改喔！',
                type: 'success',
              })
              return
            } else {
              this.$message({
                message: '已取消删除操作',
                type: 'warning',
              })
              return
            }
          },
        })
      },
      // 点击修改
      showUpdate(index, row) {
        console.log('index')
        this.showEdit[index] = true
        this.$set(this.showEdit, index, true) // 这里要用$set方法，否则页面状态不更新
      },
      // 取消修改
      cancelUpdate(index) {
        this.$confirm('取消修改？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
        })
          .then(() => {
            this.$set(this.showEdit, index, false)
          })
          .catch(() => {})
      },
      // 提交修改
      submit(index, row) {
        // 发送请求，隐藏输入框
        this.$set(this.showEdit, index, false) // vue添加属性的方法
      },
      close() {
        // this.$refs['form'].resetFields()
        // this.form = this.$options.data().form
        this.dialog = false
      },
      save() {
        var that = this
        that.$refs['form'].validate(async (valid) => {
          if (valid) {
            var fileInfoIds = []
            if (that.tableData2.length == 0) {
              that.$baseMessage('请填写明细', 'error')
              return
            } else {
              for (var i = 0; i < that.tableData2.length; i++) {
                if (
                  that.tableData2[i].project_code == '' ||
                  that.tableData2[i].inpatientWard == '' ||
                  that.tableData2[i].bed_count == '' ||
                  that.tableData2[i].bed_number == '' ||
                  that.tableData2[i].price == '' ||
                  that.tableData2[i].remark == ''
                ) {
                  that.$baseMessage('请填写明细', 'error')
                  return
                }
              }
            }
            for (var i = 0; i < that.ids.length; i++) {
              fileInfoIds.push(that.ids[i].fileInfoId)
            }
            if (fileInfoIds.length == 0) {
              that.$baseMessage('请上传附件!', 'error')
              return
            }
            var sbBedDeclaration = {
              dept_name: that.apply.org_name,
              aggrement_lv: that.apply.aggrement_lv,
              beds_all_count: that.apply.beds_all_count,
              account_for: that.apply.account_for,
              linkman: that.apply.linkman,
              phone: that.apply.phone,
              fileInfoIds: fileInfoIds,
            }
            that.apply.sbBedDeclaration = sbBedDeclaration
            that.apply.sbBedDetailsList = that.tableData2
            that.loading = true

            setTimeout(() => {
              that.loading = false
            }, 2000)
            const res = await soloBedDeclaration(that.apply)
            if (res.code == 0) {
              that.loading = false
              that.$baseMessage('保存成功', 'success')
              that.close()
              that.$emit('fetch-data')
            } else {
              that.loading = false
            }
          }
        })
      },

      handleClose(done) {},
      cancelForm() {
        this.loading = false
        this.dialog = false
        clearTimeout(this.timer)
      },
      info() {
        var that = this
        getInfo().then((res) => {
          if (res.code == 0) {
            that.apply = res.data
            if (that.apply.natures == '政府非营利') {
              that.headline = '公立'
            } else {
              that.headline = '非公立'
            }
          } else {
            // that.dialog = false
            that.$baseMessage(res.msg, 'error')
          }
        })
      },
      openwin(project_code) {
        this.$refs['medicinal'].showDia(project_code)
      },
      fetchData(row, i) {
        verify(row.provincialProjectCode).then((res) => {
          if (res.code == 0) {
            if (res.data != '') {
              this.$baseMessage(res.data, 'error')
              return
            } else {
              this.tableData2[i].project_code = row.provincialProjectCode
              this.tableData2[i].project_name = row.directoryName
            }
          } else {
            // that.dialog = false
            this.$baseMessage(res.msg, 'error')
          }
        })
      },
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    /*.el-dialog__header{*/
    /*padding: 0;*/
    /*}*/
    .inform-title {
      width: 90%;
      margin: 20px auto 20px;
      padding: 0 0 10px;
      line-height: 24px;
      font-size: 18px;
      font-weight: bold;
      text-align: center;
    }
    .custemitem {
      label {
        line-height: 16px !important;
      }
    }
    .box_card {
      h5 {
        font-size: 14px;
        font-weight: bold;
        border-left: 3px solid #1890ff;
        padding-left: 10px;
        color: #666;
        display: flex;
        flex-direction: row;
        margin: 0 0 14px;
      }
      .right-add-btn {
        margin-top: -40px;
        margin-bottom: 6px;
        text-align: right;
        position: relative;
        z-index: 888;
      }
    }
    .box_card.mt2 {
      margin-top: 20px;
    }
    .total-num {
      display: flex;
      justify-content: space-between;
      align-items: center;
      font-size: 14px;
      color: #666;
      padding: 10px 16px;
      background-color: #f5f7fa;
      p {
        padding: 0;
        margin: 0;
        span {
          font-size: 20px;
          font-weight: bold;
          color: #1890ff;
          text-decoration: underline;
          margin: 0 6px;
        }
      }
    }
    .explain-text {
      margin-top: 14px;
      padding: 10px 0;
      font-size: 14px;
      color: #999;
      line-height: 20px;
      p {
        padding: 0;
        margin: 0;
      }
    }
  }
</style>
