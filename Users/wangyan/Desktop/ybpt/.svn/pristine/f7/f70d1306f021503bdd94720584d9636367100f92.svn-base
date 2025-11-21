<template>
	<el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close" v-loading="loading" element-loading-text="正在生成数据······"
			   element-loading-spinner="el-icon-loading"
			   element-loading-background="rgba(0, 0, 0, 0.8)">
		<el-form ref="form" :model="form" :rules="rules" label-width="100px">
			<el-form-item label="批次号" prop="upload_no">
				<el-select
						v-model.trim="form.upload_no"
						clearable
						placeholder="请选择批次"
						class="w"
				>
					<el-option
							v-for="item in uploadNoList"
							:key="item"
							:label="item"
							:value="item"
					></el-option>
				</el-select>
			</el-form-item>
		</el-form>
		<div slot="footer" class="dialog-footer">
			<el-button @click="close">取 消</el-button>
			<el-button type="primary" @click="save">确 定</el-button>
		</div>
	</el-dialog>
</template>

<script>
	import {
		getUploadNo,westDrugExport
	} from '@/api/drug'

	export default {
		name: 'drugsManageEdit',
		data() {
			return {
				form: {
					upload_no: '',
				},
				fileList: [],
				uploadNoList: [],
				dialogFormVisible: false,
				loading: false,
				rules: {
					upload_no: [{ required: true, trigger: 'blur', message: '请选择批次号！'}],
				},
			}
		},
		created() {},
		methods: {
			showEdit(row) {
				this.title = '备份数据导出'
				this.dialogFormVisible = true
				this.getUploadNoList() ;
			},
			// 完成beforeUpload钩子，校验文件后缀有多种，此处仅展示一种，下面是文件大小
			beforeUpload(file) {
			 /* const FILE_NAME = file.name
			  if (FILE_NAME.substring(FILE_NAME.lastIndexOf('.')) !== '.xls' && FILE_NAME.substring(FILE_NAME.lastIndexOf('.')) !== '.xlsx') {
			    this.$message.warning('仅支持.xls和.xlsx文件')
			    return false
			  } */
			  const isLt1M = file.size / 1024 / 1024 < 10
			  if (isLt1M) {
			    this.file = file
			    return true
			  }
			  this.$message.warning('请上传不超过10M的文件.')
			  return false
			},
			
			 fileChange (file, fileList) {
			 	// 这是关键一句
			   if (fileList.length > 0) {
				 this.fileList = [fileList[fileList.length - 1]]
			   }
			 
			// 根据自己项目需求吧，我们这个要写入的，
			   //var reader = new FileReader()
			   //reader.readAsText(file.raw)
			   //reader.onload = function (e) {
				 //sessionStorage.setItem('files', reader.result)
			   //}
			 },
			
			uploadFile () {
				this.$refs.upload.submit()
			},
			
			// 上传结束

			close() {
				this.$refs['form'].resetFields()
				//this.form = this.$options.data().form
				this.dialogFormVisible = false
			},
			save() {
				this.$refs['form'].validate(async (valid) => {
					if (valid) {
						this.$baseConfirm('你确定要导出当前信息吗', null, async () => {
							this.loading = true
							await westDrugExport(this.form).then((res) => {
								let fileName = "西药备份数据导出.xlsx";
								let objectUrl = URL.createObjectURL(new Blob([res.data]))
								const link = document.createElement('a')
								link.download = decodeURI(fileName)
								link.href = objectUrl
								link.click()
								this.loading = false;
								this.$baseMessage("导出成功！", 'success')
								this.close()
							})

						})

					} else {
						return false
					}
				})
			},
			async getUploadNoList() {
				const res = await getUploadNo()
				if ((res.code = '0')) {
					this.uploadNoList = res.data
				}
			},
		},
	}
</script>
<style lang="scss" scoped>
::v-deep {
  .upload-demo{
  	position: relative;
  	.el-upload__tip{
  		position: absolute;
  		top: 0;
  		left: 90px;
		color: #e6a23c;
  	}
  }
}
</style>