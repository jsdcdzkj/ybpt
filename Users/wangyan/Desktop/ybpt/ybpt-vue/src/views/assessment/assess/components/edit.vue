<template>
  <el-drawer :title="title" :before-close="handleClose" :visible.sync="dialog" direction="rtl" :with-header="false"
             custom-class="box_drawer" size="70%" ref="drawer">
    <div class="drawer_content">
      <el-form ref="queryForm" :model="queryForm" :rules="rules" :label-width="formLabelWidth">
        <div class="drawer_main">
          <div class="box_card">
            <div class="box_header">
              <span>基本信息</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                  <el-form-item label="考核单名称" prop="assess_name">
                    <el-input v-model="queryForm.assess_name" placeholder="考核单名称" autocomplete="off"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="机构类型" prop="org_type">
                    <el-select v-model="queryForm.org_type" placeholder="请选择机构类型" class="w" @change="getLevelList"
                               clearable>
                      <el-option value="1" label="医疗机构"></el-option>
                      <el-option value="2" label="零售药店"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="协议等级" prop="aggrement_lv">
                    <el-select v-model="queryForm.aggrement_lv" style="width: 100%" clearable>
                      <el-option label="请选择" value=""></el-option>
                      <el-option
                          v-for="item in levelList"
                          :key="item.id"
                          :label="item.name"
                          :value="item.id"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                  <el-form-item label="考核年度" prop="year_of_assessment">
                    <el-date-picker v-model="queryForm.year_of_assessment" format="yyyy"
                                    value-format="yyyy" type="year" placeholder="选择年">
                    </el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8" v-if="categoryShow">
                  <el-form-item label="类别" prop="category">
                    <el-select v-model="queryForm.category" placeholder="请选择类别" class="w"
                               clearable>
                      <el-option value="1" label="门诊"></el-option>
                      <el-option value="2" label="住院"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>考核项</span>
            </div>
            <div class="right-add-btn">
              <el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd()">
                新增
              </el-button>
            </div>
            <el-table ref="listTable" stripe :data="queryForm.details"
                      highlight-current-row border
            >
              <el-table-column show-overflow-tooltip type="index" label="序号" align="center" width="80px">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="assess_question" label="考核内容" align="center">
              </el-table-column>
              <el-table-column show-overflow-tooltip label="答复方式" width="250" align="center">
                <template #default="{ row }">
                  <el-tag v-for="(item, index) in row.checkList" :key="index">
                    {{ item }}
                  </el-tag>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="full_score" label="分值" width="100" align="center">
              </el-table-column>

              <el-table-column show-overflow-tooltip label="操作" width="150" align="center">
                <template slot-scope="scope">
                  <el-button plain type="success" size="mini" icon="el-icon-edit"
                             @click="handleAdd(scope.row,scope.$index )"></el-button>
                  <el-button plain size="mini" icon="el-icon-delete" @click="handlecancel(scope.$index)"
                             type="danger"></el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
        <el-button type="primary" @click="$refs.drawer.closeDrawer(1)" :loading="loading">
          {{ loading ? '提交中 ...' : '提 交' }}
        </el-button>
      </div>
    </div>
    <examine ref="examine" @fetch-data="getDetail"></examine>
  </el-drawer>
</template>

<script>
import Examine from './examine'
import {saveAssess} from '@/api/assessment'

export default {
  name: 'kaohe',
  components: {
    Examine
  },
  data() {
    return {
      value3: '',
      title: '',
      dialog: false,
      categoryShow:false,
      loading: false,
      category: '',
      levelList: [],
      aList: [
        {id: "1", name: '一级'},
        {id: "2", name: '二级'},
        {id: "3", name: '三级'},
        {id: "9", name: '未定级'},
      ],
      bList: [
        {id: "4", name: 'A级'},
        {id: "5", name: 'B级'},
        {id: "6", name: 'C级'},
      ],
      rules: {
        'assess_name': [{required: true, trigger: 'blur', message: '请输入考核单名称'}],
        'org_type': [{required: true, trigger: 'blur', message: '请选择机构类型'}],
        'category': [{required: true, trigger: 'blur', message: '请选择类别'}],
        'aggrement_lv': [{required: true, trigger: 'blur', message: '请选择协议等级'}],
        'year_of_assessment': [{required: true, trigger: 'blur', message: '请选择考核年度'}]
      },
      queryForm: {
        assess_name: "",
        org_type: "",
        aggrement_lv: "",
        year_of_assessment: "",
        details: []
      },
      formLabelWidth: '100px',
      timer: null
    }
  },
  mounted() {
  },
  methods: {
    showDia(row,type) {
      this.categoryShow = false
      if (!row) {
        this.title = '新增';
        this.queryForm = {
          assess_name: "",
          org_type: "",
          aggrement_lv: "",
          year_of_assessment: "",
          details: [],
          type:""
        }
      } else {
        var that = this;
        this.title = '编辑'
        if ("1" == row.org_type) {
          that.categoryShow = true
          that.levelList = that.aList;
        } else if ("2" == row.org_type) {
          that.levelList = that.bList;
        }
        that.queryForm = Object.assign({}, row)
        if("copy" == type){
          that.queryForm.type = type;
        }
      }
      this.dialog = true
    },
    close() {
      this.$refs['queryForm'].resetFields()
      this.queryForm = this.$options.data().form
      this.dialog = false
    },
    async save() {
      var that = this;
      if(that.queryForm.org_type == 1 && (null == that.queryForm.category || '' == that.queryForm.category || undefined == that.queryForm.category)){
        that.$baseMessage("请选择类别！", 'error')
      }
      this.loading = true
      await saveAssess(that.queryForm).then((res) => {
        if (res.code == 0) {
          that.$emit('fetch-data');
          that.cancelForm();
        }else{
          this.$baseMessage(res.msg, 'error')
          this.loading = false;
        }
      })


    },
    handleAdd(row, index) {
      if (row) {
        this.$refs['examine'].showEdit(row, index)
      } else {
        this.$refs['examine'].showEdit()
      }
    },
    handlecancel(index) {
      this.queryForm.details.splice(index, 1)
    },
    handleClose(done) {
      var that = this;
      that.$refs['queryForm'].validate(async (valid) => {
        if (valid) {
          if(that.queryForm.details.length == 0){
            this.$baseMessage("请添加考核项明细！", 'error')
            return
          }
          if (this.loading) {
            return
          }
          this.$confirm('确定要提交表单吗？')
              .then((_) => {
                that.save()
              })
              .catch((_) => {
              })
        } else {
          return false
        }
      })

    },
    cancelForm() {
      // this.$refs['queryForm'].resetFields()
      this.$refs['queryForm'].clearValidate();
      this.loading = false
      this.dialog = false
    },
    getDetail(row) {
      console.info(row);
      var that = this;
      var checkList = row.checkList;
      if (checkList.length == 2) {
        row.is_text = "1";
        row.is_file = "1";
      } else {
        if ("文本描述" == checkList[0]) {
          row.is_text = "1";
          row.is_file = "0";
        } else if ("文件上传" == checkList[0]) {
          row.is_text = "0";
          row.is_file = "1";
        }
      }
      if ("1" == row.if_edit) {//添加
        that.queryForm.details.push(JSON.parse(JSON.stringify(row)));
      } else if ("2" == row.if_edit) {
        this.$set(that.queryForm.details, row.index, JSON.parse(JSON.stringify(row))) //因为数组也可以理解成key为索引的对象 {0:1,1:2,2:3,3:4}
      }

    },
    getLevelList() {
      var that = this;
      if (that.queryForm.org_type == "1") {
        that.categoryShow = true
        that.levelList = that.aList;
        that.queryForm.aggrement_lv = "";
      } else if (that.queryForm.org_type == "2") {
        that.categoryShow = false
        that.levelList = that.bList;
        that.queryForm.aggrement_lv = "";
      } else {
        that.levelList = []
      }

      if (that.queryForm.aggrement_lv != undefined) {
        that.queryForm.aggrement_lv = "";
      }
    }
  },
}
</script>
<style scoped>
.right-add-btn {
  margin-top: -26px;
  margin-bottom: 20px;
  text-align: right;
}
</style>