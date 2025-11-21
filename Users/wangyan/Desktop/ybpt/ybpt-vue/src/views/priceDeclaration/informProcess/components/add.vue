<template>
  <el-drawer
    ref="drawer"
    :before-close="handleClose"
    :title="title"
    :visible.sync="dialog"
    :with-header="false"
    append-to-body
    :close-on-click-modal="false"
    custom-class="box_drawer"
    direction="rtl"
    size="70%"
  >
    <div class="drawer_content">
      <el-form :label-width="formLabelWidth" :model="form">
        <div class="drawer_main">
          <h5 class="inform-title">{{ form.title }}</h5>
          <div class="box_card base-info">
            <el-row :gutter="20">
              <el-col :lg="12" :md="12" :sm="12" :xl="12" :xs="12">
                <el-form-item label="单位名称:" prop="org_name">
                  {{ form.org_name }}
                </el-form-item>
              </el-col>
              <el-col :lg="12" :md="12" :sm="12" :xl="12" :xs="12">
                <el-form-item label="单位医保编码:" prop="org_code">
                  {{ form.org_code }}
                </el-form-item>
              </el-col>
            </el-row>
            <div class="right-add-btn">
              <el-button
                class="right"
                icon="el-icon-plus"
                type="success"
                @click="handleAdd"
              >
                新增
              </el-button>
            </div>
            <el-table
              ref="listTable"
              :data="tableData"
              :element-loading-text="elementLoadingText"
              border
              highlight-current-row
            >
              <el-table-column
                align="center"
                label="序号"
                show-overflow-tooltip
                type="index"
                width="50px"
              ></el-table-column>
              <el-table-column
                align="center"
                label="项目编码"
                min-width="200"
                prop="project_code"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                label="项目名称"
                min-width="200"
                prop="project_name"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                label="申报类型"
                prop="declare_type"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                label="计价单位"
                prop="unit"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                label="价格"
                prop="price"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                label="费用类型"
                prop="cost_type"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                label="本市同级公立医疗机构价格"
                prop="org_price"
                show-overflow-tooltip
                width="110"
              ></el-table-column>
              <el-table-column
                align="center"
                label="项目执行科室"
                min-width="120"
                prop="departments"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                align="center"
                fixed="right"
                label="明细"
                prop="aaa3"
                show-overflow-tooltip
                width="90"
              >
                <template slot-scope="scope">
                  <el-button
                    v-if="scope.row.detail_status == 1"
                    icon="el-icon-document"
                    plain
                    size="mini"
                    type="success"
                    @click="handleDefinite(scope.row, scope.$index)"
                  ></el-button>
                  <el-button
                    v-if="scope.row.detail_status == 0"
                    plain
                    size="mini"
                    type="warning"
                    @click="handleDefinite(scope.row, scope.$index)"
                  >
                    未填写
                  </el-button>
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                fixed="right"
                label="附件"
                prop="aaa3"
                show-overflow-tooltip
                width="90"
              >
                <template slot-scope="scope">
                  {{ scope.row.file }}
                </template>
              </el-table-column>
              <el-table-column
                align="center"
                fixed="right"
                label="操作"
                show-overflow-tooltip
                width="180"
              >
                <template slot-scope="scope">
                  <div class="button-con">
                    <el-upload
                      style="margin-right: 12px"
                      action=""
                      :auto-upload="false"
                      :show-file-list="false"
                      :multiple="false"
                      :on-change="
                        (file, fileList) => {
                          fileChange(file, fileList, scope.row)
                        }
                      "
                    >
                      <el-button
                        icon="el-icon-upload"
                        plain
                        size="mini"
                        type="primary"
                      ></el-button>
                    </el-upload>
                    <el-button
                      icon="el-icon-edit"
                      plain
                      size="mini"
                      type="success"
                      @click="handleAdd(scope.row, scope.$index, 'edit')"
                    ></el-button>
                    <el-button
                      icon="el-icon-delete"
                      plain
                      size="mini"
                      type="danger"
                      @click="handleDelete(scope.$index, tableData)"
                    ></el-button>
                  </div>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <div class="explain-text">
            <p>注：</p>
            <p>
              1.申报类型：市场调节价项目、新增医疗服务项目、市管未定价项目、其他病房床位、单人间、套房床位。
            </p>
            <p>
              2.项目名称、项目编码、计价单位、价格、费用类型：根据《江苏省基本医疗保险诊疗项目和医疗服务设施范围及支付标准》填写。
            </p>
            <p>3.本表一式两份。</p>
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">取 消</el-button>
        <el-button type="primary" @click="save" :loading="loading">
          {{ loading ? '提交中 ...' : '提 交' }}
        </el-button>
      </div>
    </div>
    <examine ref="examine" @fetch-data="fetchData"></examine>
    <definite ref="definite" @fetch-data="fetchData"></definite>
  </el-drawer>
</template>

<script>
  import Examine from './examine'
  import Definite from './definite'
  import { insertGovernmentMedical, uploadDetailFile } from '@/api/sbApply'

  export default {
    name: 'infoAdd',
    components: {
      Examine,
      Definite,
    },
    data() {
      return {
        value3: '',
        elementLoadingText: '正在加载...',
        title: '',
        dialog: false,
        loading: false,
        queryForm: {
          pageNo: 1,
          pageSize: 10,
          username: '',
        },
        form: {},
        formLabelWidth: '100px',
        timer: null,
        tableData: [],
      }
    },
    mounted() {},
    methods: {
      showDia(row) {
        this.tableData = []
        this.form = Object.assign({}, row)
        console.log(row)
        this.dialog = true
      },
      fetchData(row) {
        if (row.sign == 'edit') {
          // if(row.high_price == 0){
          //   row.detail_status = -1
          // }
          this.tableData.splice(row.index, 1, Object.assign({}, row))
        }
        if (row.sign == 'add') {
          // if(row.high_price == 0){
          //   row.detail_status = -1
          // }
          this.tableData.push(Object.assign({}, row))
        }
        if (row.sign == 'detail') {
          this.tableData.splice(row.index, 1, Object.assign({}, row))
        }
        console.log(row)
      },
      async handleDelete(index, rows) {
        let fd = new FormData()
        if (rows[index].fileInfoId) {
          fd.append('fileInfoId', rows[index].fileInfoId)
          await uploadDetailFile(fd)
        }
        rows.splice(index, 1)
      },
      async fileChange(file, fileList, row) {
        let fd = new FormData()
        //替换
        if (row.fileInfoId) {
          fd.append('fileInfoId', row.fileInfoId)
        }
        row.file = file.name
        fd.append('file', file.raw)
        var result = await uploadDetailFile(fd)
        if (result.data.code == 0) {
          row.fileInfoId = result.data.data.id
          this.tableData.splice(row.index, 1, Object.assign({}, row))
          this.$baseMessage('上传成功', 'success')
        } else {
          this.$baseMessage(result.data.msg, 'error')
        }
      },
      handleAdd(row, index, type) {
        if ('edit' != type) {
          if (this.tableData.length > 0) {
            this.$baseMessage('告知手续申请只可一次申请一条', 'error')
            return
          }
        }

        row.aggrement_lv = this.form.aggrement_lv
        row.medical_type = this.form.medical_type
        row.index = index
        console.log(row)
        this.$refs['examine'].showEdit(row)
      },
      handleDefinite(row, index) {
        row.index = index
        row.org_code = this.form.org_code
        row.org_name = this.form.org_name
        console.log(row)
        this.$refs['definite'].showDia(row)
      },
      async save() {
        if (this.tableData.length <= 0) {
          this.$baseMessage('请至少保留一条数据！', 'error')
          return
        }
        let sign = false
        this.tableData.forEach((x) => {
          if (x.detail_status == 0) {
            sign = true
          }
        })
        if (sign) {
          this.$baseMessage('明细未填写,请完善信息！', 'error')
          return
        }
        this.form.sbGovernmentMedical = this.tableData
        // const loading = this.$loading({
        //   lock: true,
        //   text: '提交中，请稍等...',
        //   spinner: 'el-icon-loading',
        //   background: 'rgba(0, 0, 0, 0.7)',
        // })
        this.loading = true
        setTimeout(() => {
          this.loading = false
        }, 2000)
        const res = await insertGovernmentMedical(this.form)
        if (res.code == 0) {
          // loading.close()
          this.loading = false
          this.$emit('fetch-data')
          this.$baseMessage('操作成功', 'success')
          this.dialog = false
        } else {
          this.$baseMessage(res.msg, 'error')
          this.loading = false
        }
      },
      handleClose(done) {
        if (this.loading) {
          return
        }
        let sign = false
        this.tableData.forEach((x) => {
          if (x.detail_status == 0) {
            sign = true
          }
        })
        if (sign) {
          this.$baseMessage('明细未填写,请完善信息！', 'error')
          return
        }
        // this.$confirm('点击确认将提交到医保审核，是否执行提交操作？')
        //     .then((_) => {
        //       this.save()
        //     })
        //     .catch((_) => {
        //     })
      },
      cancelForm() {
        if (this.loading) {
          return
        }
        this.$confirm('取消后将丢失已填信息！确定要取消表单吗？', {
          confirmButtonText: '是',
          cancelButtonText: '否',
        })
          .then((_) => {
            this.loading = false
            this.dialog = false
            clearTimeout(this.timer)
          })
          .catch((_) => {})
      },
    },
  }
</script>
<style lang="scss" scoped>
  .button-con {
    display: flex;
  }
  .mb2 {
    margin-bottom: 20px;
  }

  .right-add-btn {
    margin-bottom: 10px;
    text-align: right;
  }

  .inform-title {
    width: 90%;
    margin: 0 auto 20px;
    padding: 10px 0;
    line-height: 24px;
    font-size: 18px;
    font-weight: bold;
    text-align: center;
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

  ::v-deep {
    .base-info {
      .el-form-item--small.el-form-item {
        margin-bottom: 0;
      }
    }
  }
</style>
