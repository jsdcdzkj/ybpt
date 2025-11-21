<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="1000px"
      @close="close"
      append-to-body
      :close-on-click-modal="false"
  >

    <p class="preview-name">省平台采购配送截图</p>
    <div class="img-preview-con">
      <div class="img-preview-item" v-for="(url, index) in fileList" :key="index">
        <el-card shadow="hover">
          <el-image
              style="width: 100px; height: 100px"
              :src="url"
              :preview-src-list="fileList">
          </el-image>
        </el-card>

      </div>

    </div>
<!--    <div v-for="(url, index) in fileList" :key="index">-->
<!--      <img :src="url" alt="" style="max-width: 100%">-->
<!--    </div>-->
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>


import {fileURL} from "@/config/setting.config";

export default {
  name: 'examine',
  components: {},
  data() {
    return {
      fileList: [],
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {
  },
  methods: {
    showEdit(row) {
      const that = this;
      that.title = '预览'
      row.forEach(function (item,index){
        const url = fileURL + item.fileUrl + '?n=' + item.fileName + '&download=0';
        that.fileList.push(url)
      })
      console.log(that.fileList)
      that.dialogFormVisible = true
    },
    close() {
      this.fileList = []
      this.dialogFormVisible = false
    },
    fetchData(row) {

    },

  },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .custemitem {
    label {
      line-height: 16px !important;
    }
  }
}
.preview-name{
  font-size: 14px;
  color: #606266;
}
.img-preview-con{
  margin-top: 20px;
  display: flex;
  flex-wrap: wrap;
  .img-preview-item{
    margin: 0 10px 10px 0;
    cursor: pointer;
  }
}
</style>
