<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    :close-on-click-modal = "false"
    width="500px"
    @close="close"
    top="100px"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="80px">
      <el-form-item label="角色名称" prop="role_name">
        <el-input v-model="form.role_name" autocomplete="off"></el-input>
      </el-form-item>
      <el-form-item label="角色标志" prop="role_symbol">
        <el-input v-model="form.role_symbol" autocomplete="off"></el-input>
      </el-form-item>

      <el-form-item label="权限选择">
        <el-input
          placeholder="输入关键字进行过滤"
          v-model="filterText"
        ></el-input>
        <div class="treeborder">
          <el-tree
            class="filter-tree"
            show-checkbox
            :data="data"
            :props="defaultProps"
            default-expand-all
            node-key="id"
            :filter-node-method="filterNode"
            ref="tree"
          ></el-tree>
        </div>
      </el-form-item>
      <el-form-item label="描述">
        <el-input
          v-model.trim="form.memo"
          autocomplete="off"
          type="textarea"
          :rows="5"
        ></el-input>
      </el-form-item>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save" :loading="loading" :disabled="disabled">
        {{ loading ? '确定中 ...' : '确定' }}
      </el-button>
    </div>
  </el-dialog>
</template>

<script>
import { saveRole,getMenusByRoleId } from '@/api/roleManagement'
import { getRouterList as getList } from '@/api/router'

export default {
  name: 'RoleManagementEdit',
  data() {
    return {
      loading: false,
      disabled:false,
      filterText: '',
      data: [],
      defaultProps: {
        children: 'children',
        label: 'label',
      },
      form: {
        id: '',
        role_name: '',
        memo: '',
        role_symbol: '',
      },
      rules: {
        role_name: [
          { required: true, trigger: 'blur', message: '请输入角色名称' },
        ],
      },
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {
    this.getMenus();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val)
    },
  },
  methods: {
    showEdit(row,nodeArray) {
      var that = this;
      if (!row) {
        this.title = '添加'
        setTimeout(function () {
          that.setCheckedKeys(["1"]);
        },200)
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
        setTimeout(function () {
          that.getMenuIds();
        },200)

      }
      this.dialogFormVisible = true
    },
    async getMenus(){
      const res = await getList()
      if(res.code=="0"){
        this.data = res.data;
      }
    },
    filterNode(value, data) {
      if (!value) return true
      return data.label.indexOf(value) !== -1
    },
    getCheckedKeys() {
      this.$refs.tree.getCheckedKeys();
    },
    setCheckedKeys(data) {
      this.$refs.tree.setCheckedKeys(data);
    },
    close() {
      this.$refs['form'].resetFields()
      this.form = this.$options.data().form
      this.setCheckedKeys([]);
      this.dialogFormVisible = false
    },
    async getMenuIds() {
      const res = await getMenusByRoleId({"roleId":this.form.id})
      if(res.code=="0"){
        this.setCheckedKeys(res.data)
      }
    },
    save() {
      this.$refs['form'].validate(async (valid) => {
        if (this.loading) {
          return
        }
        if (valid) {
          this.loading = true
          this.disabled = true
          
          var menuIds = this.$refs.tree.getCheckedKeys();
          this.form.menuIds = menuIds;
          const res = await saveRole(this.form)
          if(res.code=="0"){
            // 动画关闭需要一定的时间
          setTimeout(() => {
            this.loading = false
            this.disabled = false
          }, 400)
            this.$baseMessage("保存成功！", 'success')
            this.$emit('fetch-data')
            this.close()
          }else{
            this.$baseMessage(res.msg, 'error')
          }

        } else {
          return false
        }
      })
    },
  },
}
</script>
<style lang="scss" scoped>
  .filter-tree{margin-top:0px;}
  .treeborder {
  padding: 20px;
  height: 300px;
  margin-top:20px;
  border: 1px solid #dcdfe6;
  border-radius: 2.5px;
  overflow-y: auto;
}
</style>
