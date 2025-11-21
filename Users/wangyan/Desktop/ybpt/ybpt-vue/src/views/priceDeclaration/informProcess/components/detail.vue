<template>
  <el-drawer :title="title" :visible.sync="dialog" append-to-body direction="rtl" :with-header="false"
             custom-class="box_drawer" size="70%" ref="drawer">
    <div class="drawer_content">
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
export default {
  name: 'detail',
  components: {},
  data() {
    return {
      value3: '',
      title: '',
      dialog: false,
    }
  },
  mounted() {
  },
  methods: {
    showDia(row) {
      this.title = '详情'
      this.dialog = true
    },
    cancelForm() {
      this.dialog = false
    },
  },
}
</script>
<style lang="scss" scoped>

</style>