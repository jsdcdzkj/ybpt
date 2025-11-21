<template>
  <div class="index-container">  
    <el-row :gutter="20">
      <!-- <el-col :xs="24" :sm="24" :md="8" :lg="4" :xl="4">
        <el-tree
          :data="data"
          :props="defaultProps"
          node-key="id"
          :default-expanded-keys="['root']"
          @node-click="handleNodeClick"
        ></el-tree>
      </el-col> -->
      <el-col :xs="24" :sm="24" :md="16" :lg="20" :xl="24">
        <vab-query-form>
          <vab-query-form-top-panel :span="12">
            <el-button icon="el-icon-plus" type="primary" @click="handleEdit">
              添加
            </el-button>
          </vab-query-form-top-panel>
        </vab-query-form>
        <el-table
          v-loading="listLoading"
          :data="list"
          :element-loading-text="elementLoadingText"
          row-key="path"
          border
          default-expand-all
          :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        >
          <el-table-column
            show-overflow-tooltip            
            label="标题"
            width="240px"
          >
            <template #default="{ row }">
              <span v-if="row.meta">
                {{ row.meta.title }}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="name"
            label="名称"
            width="180px"
          ></el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="path"
            label="路径"
          ></el-table-column>
           
          <el-table-column
            show-overflow-tooltip
            prop="component"
            label="vue文件路径"
          ></el-table-column>
          <el-table-column show-overflow-tooltip label="是否显示" width="120px" align="center">
            <template #default="{ row }">
              <span>
                {{ row.is_show == '1' ? '是' : '否' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column
            show-overflow-tooltip
            prop="redirect"
            label="重定向"
            width="120px"
            align="center"
          ></el-table-column>
         
          <el-table-column show-overflow-tooltip label="图标" width="120px" align="center">
            <template #default="{ row }">
              <span v-if="row.meta">
                <vab-icon
                  v-if="row.meta.icon"
                  :icon="['fas', row.meta.icon]"
                ></vab-icon>
              </span>
            </template>
          </el-table-column>
          <el-table-column
                  show-overflow-tooltip
                  prop="sort"
                  label="排序"
                  width="120px"
          ></el-table-column>
          <el-table-column show-overflow-tooltip label="操作" width="150" align="center" fixed="right">
            <template #default="{ row }">
              <el-button type="text" @click="handleEdit(row)">编辑</el-button>
              <el-button type="text" @click="handleDelete(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <edit ref="edit" @fetch-data="fetchData"></edit>
  </div>
</template>

<script>
  import { getRouterList as getList } from '@/api/router'
  import { doDelete } from '@/api/menuManagement'
  import Edit from './components/MenuManagementEdit'

  export default {
    name: 'MenuManagement',
    components: { Edit },
    data() {
      return {
        defaultProps: {
          children: 'children',
          label: 'label',
        },
        list:[],
        listLoading: true,
        elementLoadingText: '正在加载...',
      }
    },
    async created() {
      // const roleData = await getTree()
      // this.data = roleData.data
      this.fetchData()
    },
    methods: {
      handleEdit(row) {
        if (row.id) {
          this.$refs['edit'].showEdit(row)
        } else {
          this.$refs['edit'].showEdit()
        }
      },
      handleDelete(row) {
        console.log(row.id);
        if (row.id) {
          this.$baseConfirm('你确定要删除当前项吗', null, async () => {
            const res = await doDelete({ "menuId": row.id })
            if (res.code == "0") {
              this.$baseMessage("操作成功！", 'success')
            } else {
              this.$baseMessage(res.msg, 'error')
            }
            this.fetchData()
          })
        }
      },
      async fetchData() {
        this.listLoading = true
        const res = await getList()
        if(res.code =="0"){
          this.list = res.data;
        }
        setTimeout(() => {
          this.listLoading = false
        }, 300)
      },
      handleNodeClick(data) {
        this.fetchData()
      },
    },
  }
</script>
