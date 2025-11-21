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
    <div class="drawer_content">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <div class="file-main">
            <h2 style="text-align:center;">徐州市基本医疗保险定点零售药店服务协议（A级）</h2>
            <div class="jj">
               <p>甲方机构名称：徐州市医疗保障局</p>
               <p>地址：徐州市医疗保障局</p>
               <p>乙方国家机构名称：XXXXXXX</p>
               <p>乙方国家医保编码：XXXXXXX</p>
               <p>乙方地址：XXXXXXX</p>
            </div>
            <div class="doc-content">
              <p>甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下</p>
              <p>甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下甲方双方经过协商，就基本医疗保险医疗服务有关事宣明确如下</p>
            </div>
            <div class="doc-footer">
              <div class="box">
                <p>甲方：</p>
                <p>（签章）</p>
                <p>法定代表人：（签名）</p>
                <p>      年    月    日</p>
              </div>
              <div class="box">
                <p>乙方：</p>
                <p>（签章）</p>
                <p>法定代表人：（签名）</p>
                <p>      年    月    日</p>
              </div>
              
            </div>
            
          </div>
        </div>
      </el-form>
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

export default {
  name: 'edit',
  components: {},
  data() {
    return {     
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
      if (!row) {
        this.title = '新增'
      } else {
        this.title = '查看'
        this.form = Object.assign({}, row)
      }
      this.dialog = true
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