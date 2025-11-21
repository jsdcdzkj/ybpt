<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="500px"
      @close="close"
      :close-on-click-modal="false"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="项目名称" prop="item_name">
            <el-input
                v-model.trim="form.item_name"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="项目编号" prop="item_no">
            <el-input
                v-model.trim="form.item_no"
                autocomplete="off"
            ></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="所属年份" prop="year" >
            <el-date-picker
                v-model="form.year"
                value-format="yyyy"
                type="year"
                placeholder="选择年" :disabled="is_use">
            </el-date-picker>
          </el-form-item>
        </el-col>
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="项目类型" prop="item_type">
            <el-select clearable v-model="form.item_type" @change="item_type">
              <el-option v-for="item in item_type_option" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="是否启用" prop="item_state">
            <el-switch
                v-model="form.item_state"
                active-text="是"
                inactive-text=""
                active-value="1"
                inactive-value="0"
            ></el-switch>
          </el-form-item>
        </el-col>

      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading">
        {{ loading ? '确定中 ...' : '确定' }}
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import {edit} from "@/api_check/tijianguanli";
import {getDicts} from "@/api/dictManagement";

export default {
  name: 'edit',
  data() {
    return {
      loading: false,
      item_type_option: '',
      form: {
        item_name: '',//项目名称
        item_no: '',//项目编码
        item_type: '',//项目类型
        year:'',// 所属年份
        item_type_name: '',//项目类型名称
        item_state: '1',////启用禁用 （禁用:0，启用:1）
        is_del: '0',//删除标志
      },
      rules: {
        item_name: [{required: true, trigger: 'blur', message: '请输入项目名称'},],
        item_no: [{required: true, trigger: 'blur', message: '请输入项目编码'},],
        year: [{required: true, trigger: 'blur', message: '请选择所属年份'},],
        item_type: [{required: true, trigger: 'blur', message: '请选择项目类型'},],
      },
      title: '',
      dialogFormVisible: false,
      is_use:true,
    }
  },
  created() {
  },
  methods: {
    showDia(row) {
      this.getItemTypeOptions()
      if (!row) {
        this.title = '添加'
        this.is_use=false;
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
        this.is_use=true;
      }
      this.dialogFormVisible = true
    },
    async getItemTypeOptions() {
      const res = await getDicts({"type": "item_type"});
      this.item_type_option = res.data;
    },
    item_type(val) {
      this.$refs['form'].validate()
      var dic = this.item_type_option.find((item) => {
        return item.value == val
      })
      this.form.item_type = dic.value
      this.form.item_type_name = dic.label
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
          this.loading = true
          // 动画关闭需要一定的时间
          setTimeout(() => {
            this.loading = false
          }, 400)

          const {msg} = await edit(this.form)
          this.$baseMessage(msg, 'success')
          this.$emit('fetch-data')
          this.close()
        } else {
          return false
        }
      })
    },
  },
}
</script>
