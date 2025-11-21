<template>
  <el-drawer :title="title" :visible.sync="dialog" direction="rtl" :with-header="false" custom-class="box_drawer"
    size="1660px" ref="drawer" :close-on-click-modal="true" append-to-body>
    <!-- excel 内嵌 -->
    <!-- <div id="luckysheet" style="width: 100%; height: 94%;padding: 10px;"></div> -->
    <!-- <div style="position: relative;height: 100vh;;">
      <div id="luckysheet" style="padding: 0px; position: absolute; width: 100%; left: 0px; top: 10px; bottom: 10px">
      </div>
    </div> -->
    <div class="home xlsx">
      <div v-html="excelView" id="excelView" style="width: 100%; height: 94%;padding: 10px;"></div>
    </div>

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

import { appropDocumentPreviewFile } from '@/api/disburse.js'

const XLSX = require('xlsx')

// 定义blob对应的type
const fileTypeMap = {
  "xls": "application/vnd.ms-excel",
  "xlsx": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
}


export default {
  name: 'onlinePreview',
  data() {
    return {
      title: '',
      dialog: false,
      timer: null,
      excelView: undefined,
      execlArraybufferData: null, //Excelblob转换为arraybuff数据
      sheetNames: null, //从数据中获取到的sheet页数组
    }
  },
  mounted() {
  },
  methods: {
    showEdit(row) {
      console.log('row', row);
      this.title = row.docName
      this.handleShowDetail(row)
      this.dialog = true
    },
    close() {
      this.dialog = false
      this.$emit('update:visible', false)
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      this.close()
      this.$emit('closeChildDialog')
      this.$emit('fetch-data')
      this.PDFsrc = "";
      clearTimeout(this.timer)
    },

    renderedHandler() {
      console.log("渲染完成")
    },

    handleShowDetail(row) {


      appropDocumentPreviewFile(row).then((res) => {
        console.log(XLSX, 'XLSX');
        console.log(res, 'res');
        this.XLSX = XLSX
        let reader = new FileReader()
        reader.readAsArrayBuffer(res.data) // blob类型转换为ArrayBuffer类型
        this.tabChange(0, reader)
      })
    },

    tabChange(index, reader) {
      this.excelView = ''
      let XLSX = this.XLSX
      let _this = this

      // 如果第一次进来
      if (!this.sheetNames) {
        // 文件转换加载完成后
        reader.onload = function (event) {
          let arraybufferData = event.target.result;
          this.execlArraybufferData = arraybufferData
          let data = new Uint8Array(arraybufferData) // es2017的方法
          let workbook = XLSX.read(data, { type: "array" })  // 得到表格的array数据
          _this.workbooks = workbook  // 赋值到此组件最外面，一会要用
          console.log('workbook', workbook);
          let sheetNames = workbook.SheetNames; // 得到execl工作表名称集合，结果类似这样['sheet1','sheet2']
          _this.sheetNames = sheetNames  // 赋值到此组件最外面，一会要用
          let worksheet = workbook.Sheets[sheetNames[index]]  // 获取第几个工作表0就是'sheet1'，以此类推
          this.excelView = XLSX.utils.sheet_to_html(worksheet) // 把表格的array数据转换成html数据
          _this.$nextTick(function () {
            // DOM加载完毕后执行，解决HTMLConnection有内容但是length为0问题。
            _this.setStyle4ExcelHtml();
          })
        }
      } else {
        // 已经有数据了的时候直接获取对应sheet里的内容
        let worksheet = this.workbooks.Sheets[this.sheetNames[index]];
        this.excelView = XLSX.utils.sheet_to_html(worksheet)
      }


    },

    // 设置Excel转成HTML后的样式
    setStyle4ExcelHtml() {
      const excelViewDOM = document.getElementById("excelView");
      if (excelViewDOM) {
        const excelViewTDNodes = excelViewDOM.getElementsByTagName("td"); // 获取的是HTMLConnection
        if (excelViewTDNodes) {
          const excelViewTDArr = Array.prototype.slice.call(excelViewTDNodes);
          for (const i in excelViewTDArr) {
            const id = excelViewTDArr[i].id; // 默认生成的id格式为sjs-A1、sjs-A2......
            if (id) {
              const idNum = id.replace(/[^0-9]/gi, ""); // 提取id中的数字，即行号
              if (idNum && (idNum === "1" || idNum === 1)) {
                // 第一行标题行
                excelViewTDArr[i].classList.add("class4Title");
              }
              if (idNum && (idNum === "2" || idNum === 2)) {
                // 第二行表头行
                excelViewTDArr[i].classList.add("class4TableTh");
              }
            }
          }
        }
      }
    },





  }
}
</script>