<template>
	<el-dialog :title="title" :visible.sync="dialogFormVisible" width="500px" @close="close">
		<el-form ref="form" :model="form" :rules="rules" label-width="100px">
			<el-form-item label="药品类型" prop="resource">
				<el-radio-group v-model="form.resource">
				    <el-radio :label="3">中药</el-radio>
				    <el-radio :label="6">西药</el-radio>
				    <el-radio :label="9">中成药</el-radio>
				</el-radio-group>
			</el-form-item>
			<el-form-item label="选择文件" prop="fileUpdate">
				<el-upload
					class="upload-demo"
					ref="upload"
					action="" 
					:show-file-list="true"
					:before-upload="beforeUpload"
					:on-change="fileChange"
					:file-list="fileList"
					accept=".xls,.xlsx" 
					:auto-upload="false"
					>
					<div slot="tip" class="el-upload__tip">仅支持.xls和.xlsx文件，且不超过10M</div>
				  <el-button size="small" type="primary">点击上传</el-button>
				  
				</el-upload>
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
		doEdit
	} from '@/api/menuManagement'

	export default {
		name: 'drugsManageEdit',
		data() {
			return {
				form: {
					resource: '',
				},
				fileList: [],
				dialogFormVisible: false,
				rules: {
				  fileUpdate: [{ required: true, trigger: 'blur', message: '请选择文件！'}],
				  resource: [{ required: true, message: '请选择药品类型', trigger: 'change' }],
				},
			}
		},
		created() {},
		methods: {
			showEdit(row) {
				this.title = '添加'
				this.dialogFormVisible = true
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
						const {
							msg
						} = await doEdit(this.form)
						this.$baseMessage(msg, 'success')
						this.$emit('fetch-data')
						this.close()
					} else {
						return false
					}
				})
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