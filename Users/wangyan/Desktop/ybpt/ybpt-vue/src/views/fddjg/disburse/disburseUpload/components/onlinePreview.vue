<template>
  <el-drawer :title="title" :visible.sync="dialog" direction="rtl" :with-header="false" custom-class="box_drawer"
    size="1660px" ref="drawer" :close-on-click-modal="true" append-to-body v-loading="loading"
    element-loading-text="加载中,文件过大，请稍后" element-loading-spinner="el-icon-loading"
    element-loading-background="rgba(0, 0, 0, 0.8)">
    <!-- excel 内嵌 -->
    <!-- <div id="luckysheet" style="width: 100%; height: 94%;padding: 10px;"></div> -->
    <!-- <div style="position: relative;height: 100vh;;">
      <div id="luckysheet" style="padding: 0px; position: absolute; width: 100%; left: 0px; top: 10px; bottom: 10px">
      </div>
    </div> -->
    <vue-office-excel :src="excel" style="width: 100%; height: 94%;padding: 10px;" @rendered="renderedHandler"
      @error="errorHandler" />

    <div class="drawer_content">
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
      </div>
    </div>
  </el-drawer>
</template>
<style lang="scss" scoped>
.file-main {
  padding: 0 30px;

  h2 {
    font-size: 20px;
    font-weight: bold;
  }

  .jj {
    font-size: 16px;
    line-height: 25px;
    margin-top: 30px;

    p {
      text-align: center;
    }
  }

  .doc-content {
    font-size: 16px;
    margin-top: 30px;
    padding: 0 0px;
    line-height: 30px;
    text-indent: 2em;
  }

  .doc-footer {
    margin-top: 30px;
    display: flex;
    flex-direction: row;
    justify-content: space-between;

    .box {
      flex: 1;
      line-height: 36px;
      font-size: 16px;
    }
  }
}
</style>
<script>

import { appropDocumentPreviewFile, appropDocumentDownLoad } from '@/api/disburse.js'
import { Base64 } from 'js-base64'

//引入VueOfficeExcel组件
import VueOfficeExcel from '@vue-office/excel'
//引入相关样式
import '@vue-office/excel/lib/index.css'

export default {
  name: 'onlinePreview',
  data() {
    return {
      title: '',
      dialog: false,
      timer: null,
      excel: undefined,
      loading: false,
    }
  },
  components: {
    VueOfficeExcel
  },
  mounted() {
  },
  methods: {
    showEdit(row) {
      console.log('row', row);
      this.title = row.docName
      this.excel = ''
      this.handleShowDetail(row)
      // window.open('http://192.168.1.241:11111/onlinePreview?url=' + encodeURIComponent(Base64.encode('http://192.168.1.21:8080/file/approp_notice/occurFile/70e8858bdc4a41dea0bda528e3405c22.xlsx')));
      this.dialog = true
    },
    close() {
      this.dialog = false
      this.excel = ''
      this.$emit('update:visible', false)
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      this.close()
      this.$emit('closeChildDialog')
      this.$emit('fetch-data')
    },

    renderedHandler() {
      console.log("渲染完成")
      this.loading = false
    },
    errorHandler() {
      console.log("渲染失败")
      // setTimeout(() => {
      //   this.loading = false
      // }, 1000)

    },

    handleShowDetail(row) {
      appropDocumentPreviewFile(row).then((res) => {

        this.loading = true
        //第一种方式(转为url)
        //  this.excel = window.URL.createObjectURL(new Blob([res.data]))
        //第二种方式(转为base64编码)
        // const fileReader = new FileReader()
        // fileReader.readAsDataURL(res.data)
        // fileReader.onload = e => {
        //   this.excel = e.target.result
        // }
        //第三种方式(获取到buffer)
        res.data.arrayBuffer().then(buffer => {
          console.log('buffer', buffer);
          this.excel = buffer
        })
      })

      // appropDocumentPreviewFile(row).then((res) => {
      //   // this.excel = window.URL.createObjectURL(new Blob([res.data]))
      //   window.open('http://192.168.1.241:11111/onlinePreview?url=' + encodeURIComponent(Base64.encode('http://58.218.188.178:9548/1.pdf')));
      // })


    },




  }
}
</script>