<template>
  <el-dialog
      :title="title"
      :visible.sync="visible"
      width="500px"
      @close="close"
      append-to-body
      :close-on-click-modal="false"
  >
<!--    <el-upload :file-list="fileList" :multiple="false"-->
<!--               :before-upload="beforeUpload" :remove="removeFile">-->
<!--      <p class="ant-upload-drag-icon">-->
<!--        <a-icon type="inbox"/>-->
<!--      </p>-->
<!--      <p class="ant-upload-hint">-->
<!--        仅允许导入xls、xlsx格式文件-->
<!--      </p>-->
<!--    </el-upload>-->

    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-form-item label="选择文件" prop="fileUpdate" ref="uploadElement">
        <el-upload
            class="upload-demo"
            ref="upload"
            action=""
            :show-file-list="false"
            :before-upload="beforeUpload"
            :file-list="form.fileList"
            accept=".xls,.xlsx"
            :auto-upload="false"
            :multiple="false"
            :on-change="fileChange"
        >
<!--          :on-change="fileChange"-->
          <div slot="tip" class="el-upload__tip">仅支持.xls和.xlsx文件，且不超过2M</div>
          <el-button size="small" type="primary">点击上传</el-button>

        </el-upload>
      </el-form-item>

    </el-form>

    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
<!--      <el-button type="primary" @click="save">确 定</el-button>-->
    </div>
  </el-dialog>
</template>

<script>
export default {
  name: 'Index',
  components: {},
  data() {
    return {
      visible: false,
      title: '',
      data: {
        sign:'upload',
        arr:[]
      },
      form: {
        fileList: [] // 上传的文件列表
      },
      rules: {
        fileList: [{required: true, trigger: 'blur', message: '请选择文件！'}],
      },
    }
  },
  mounted() {
  },
  methods: {
    showEdit(row) {
      this.title = '导入数据'
      this.visible = true
    },
    async handleOk() {
      if (this.data.length === 0) {
        this.$baseMessage("请选择要上传的文件", 'error')
      } else {
        // const data = await WelfareApi.importExcelData(this.data)
        this.$baseMessage(data.message, 'success')
        this.visible = false;
        this.fileList = []
        // this.$emit('fatherMethod');
      }
    },
    handleCancel() {
      this.fileList = []
      this.visible = false
    },
    // 文件上传前的钩子
    beforeUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$baseMessage("上传文件大小不能超过 2MB!", 'error')
        return false
      }
      this.fileChange(file);
    },
    removeFile(file) {
      const index = this.fileList.indexOf(file);
      const newFileList = this.fileList.slice();
      newFileList.splice(index, 1);
      this.fileList = newFileList;
    },
    fileChange(file) {
      let _this = this;
      let rABS = false; //是否将文件读取为二进制字符串
      let f = file.raw
      let reader = new FileReader();
      FileReader.prototype.readAsBinaryString = function (f) {
        let binary = "";
        let rABS = false; //是否将文件读取为二进制字符串
        let pt = this;
        let wb; //读取完成的数据
        let outdata;
        let reader = new FileReader();
        reader.onload = function (e) {
          let bytes = new Uint8Array(reader.result);
          let length = bytes.byteLength;
          for (let i = 0; i < length; i++) {
            binary += String.fromCharCode(bytes[i]);
          }
          //此处引入，用于解析excel
          let XLSX = require("xlsx");
          if (rABS) {
            wb = XLSX.read(btoa(fixdata(binary)), {
              //手动转化
              type: "base64"
            });
          } else {
            wb = XLSX.read(binary, {
              type: "binary"
            })
          }
          outdata = XLSX.utils.sheet_to_json(wb.Sheets[wb.SheetNames[0]]);
          //outdata就是读取的数据（不包含标题行即表头，表头会作为对象的下标）
          //此处可对数据进行处理
          let arr = [];
          outdata.map(v => {
            let obj = {}
            obj.project_code = v['编码']
            obj.project_name = v['名称']
            obj.unit = v['单位']
            obj.sale_price = v['实际销售价格']
            obj.purchase_price = v['实际采购价格']
            obj.rate = v['加成率']
            obj.org_price = v['公立医疗机构价格']
            if(v['是否在省平台上采购'] == '是'){
              obj.is_purchase = 1
            }else {
              obj.is_purchase = 0
            }
            obj.remark = v['备注']
            obj.detail_status =  '0'
            arr.push(obj)
          });
          _this.data.arr = []
          _this.data.arr = arr;
          _this.save()
          return arr;
        };
        reader.readAsArrayBuffer(f);
      };
      if (rABS) {
        reader.readAsArrayBuffer(f);
      } else {
        reader.readAsBinaryString(f);
      }
    },
    showDia() {
      this.title = '导入数据'
      this.visible = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.visible = false
    },
    save() {
      // this.$baseMessage('上传成功', 'success')
      this.$emit('fetch-data',this.data)
      this.close()
    },
  },
}
</script>