<template>
  <el-drawer
    :title="title"    
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="50%"
    ref="drawer"
    :close-on-click-modal="true"
    append-to-body
  >
    <iframe :src="PDFsrc" frameborder="0" style="width: 100%; height: 100%"></iframe>
    <div class="drawer_content">
      <div class="drawer_footer">
          
        <el-button @click="cancelForm" type="primary">关 闭</el-button>       
      </div>
    </div>

  </el-drawer>
</template>
<style lang="scss" scoped>
  .file-main{padding:0 30px;
   h2{font-size:20px;font-weight:bold;}
   .jj{font-size:16px;line-height:25px;margin-top:30px;p{text-align: center;}}
   .doc-content{font-size:16px;margin-top:30px;padding:0 0px;line-height:30px;text-indent: 2em;}
   .doc-footer{margin-top:30px;display:flex;flex-direction: row;justify-content:space-between;.box{flex:1;line-height:36px;font-size:16px;}}
  }
</style>
<script>

import {getSupById} from "@/api_net/netTagSupp";

export default {
  name: 'edit',
  components: {},
  data() {
    return {
      PDFsrc:'',
      title: '',
      dialog: false,
      loading: false,      
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
      },
      formLabelWidth: '100px',
      timer: null,
    }
  },
  mounted() {},
  methods: {
    showDia(row) {
      this.getById(row.id);
      this.dialog = true
    },
    async getById(val){
      var x = {};
      x.id = val;
      const { data } = await getSupById(x);
      console.log(data.pdf_path);
      this.PDFsrc = data.pdf_path;
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialog = false
      this.$emit("update:visible", false);
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      // this.$emit('fetch-data')
      this.close()
    },    
    handleClose(done) {
      if (this.loading) {
        return
      }
      this.$confirm('确定要申请吗？')
        .then((_) => {
          this.loading = true
          this.timer = setTimeout(() => {
            done()
            // 动画关闭需要一定的时间
            setTimeout(() => {
              this.loading = false
            }, 400)
          }, 2000)
        })
        .catch((_) => {})
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      this.close();
      this.$emit('closeChildDialog')
      clearTimeout(this.timer)
    },
  },
}
</script>