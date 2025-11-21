<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="730px"
    @close="close"
    :close-on-click-modal = "false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="更改状态" prop="value">
            <el-radio-group v-model="form.status">
              <el-radio :label="0">上线</el-radio>
              <el-radio :label="1">下线</el-radio>
              <el-radio :label="2">部分下线</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item v-show="form.status == 2" label="选择状态" prop="value">
            <el-transfer
                filterable
                :filter-method="filterMethod"
                filter-placeholder="请输入关键字"
                :titles="['上线', '下线']"
                v-model="value"
                @mouseover.native="addTitle"
                :data="data">
            </el-transfer>
          </el-form-item>

        </el-col>

      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { regionDataPlus, CodeToText } from 'element-china-area-data'
import {updateNetTagAgreementStatus, getJgYdList} from '@/api_net/tagAgreement'
export default {
  name: 'UserManagementEdit',
  data() {
    const generateData = _ => {
      const data = [];
      const cities = ['上海', '北京', '广州', '深圳', '南京', '西安', '成都'];
      const pinyin = ['shanghai', 'beijing', 'guangzhou', 'shenzhen', 'nanjing', 'xian', 'chengdu'];
      cities.forEach((city, index) => {
        data.push({
          label: city,
          key: index,
          pinyin: pinyin[index]
        });
      });
      return data;
    };
    return {
      data: generateData(),
      value: [],
      filterMethod(query, item) {
        return item.pinyin.indexOf(query) > -1;
      },
      form: {
        id: '',
        status: 0,
        net_grade_id: '',
        category_id: 1,
      },
      rules: {
      },
      title: '',
      dialogFormVisible: false,
      id: "",
      isDisabled: false,
      loading: false
    }
  },
  created() {

  },
  methods: {
    addTitle(e) {
      const target = e.target
      if (target.title) return
      target.title = target.innerText
    },

    showDia(row) {
      console.log(row.org_ids)
      this.value = row.org_ids;
      this.loading = false ;
      this.title = '协议状态控制';
      this.dialogFormVisible = true;
      this.form.status = row.status;
      this.form.id = row.id;
      this.form.net_grade_id = row.net_grade_id;
      this.form.category_id = row.category_id;
      this.getOrData();
    },
    handleChange(value) {
      let cityNames = []
      value.forEach((e) => {
        cityNames.push(CodeToText[e])
      })
      this.citys = cityNames.join('/')
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      if (this.loading) {
        return
      }
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          this.loading = true ;
          let params = {
            id: this.form.id,
            status: this.form.status + "",
            org_ids: this.value
          }
          updateNetTagAgreementStatus(params).then(res => {
            this.loading = false ;
            if (res.code == 0) {
              this.$message({message: '操作成功', type: 'success'})
              this.close()
              this.$emit('fetch-data')
            } else {
              this.$message({message: res.msg, type: 'error'})
            }
          })
          // 动画关闭需要一定的时间
          setTimeout(() => {
            this.loading = false
          }, 1000)
        }
      })
    },
    getOrData() {
      var queryForm = {
        fixmedins_type: this.form.category_id,
        aggrement_lv: this.form.net_grade_id,
      };
      getJgYdList(queryForm).then(res => {
        console.log(res)
        if (res.code == 0) {
          this.data = [];
          var list = res.data;

          for (let i = 0; i < list.length; i++) {
            this.data.push({
              label: list[i].label,
              key: list[i].key,
              pinyin: list[i].label
            })
          }
        }
      })
    }
  },
}
</script>
<style scoped>
::v-deep .el-input{
  width: auto !important;
}
::v-deep .el-tree-node__label{
  overflow: hidden;
  word-break: keep-all;
  white-space: nowrap;
  text-overflow: ellipsis;

}

</style>