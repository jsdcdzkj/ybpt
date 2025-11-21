<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="700px"
    @close="close"
  >
    <h5 class="text-title">医疗服务项目（药品、医用耗材）自主定价告知受理书</h5>
	<div class="cen-des">
		<p>
			各级医保经办机构、医保基金监督部门、XX医疗机构：
			根据XX医疗机构对XXX项目（产品）自主定价的告知，现予以受理。
		</p>
		<p>医保经办机构应在收到《受理书》5个工作日内完成相关的信息系统维护，并按照相关支付标准文件及定点协议严格执行。</p>
	</div>
	
	<div class="bot-des">
		<p>徐州市医疗保障局</p>
        <p>2023年02月02日</p>
	</div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { doEdit } from '@/api/menuManagement'

export default {
  name: 'MenuManagementEdit',
  data() {
    return {
      title: '',
      dialogFormVisible: false,
    }
  },
  created() {},
  methods: {
    showEdit(row) {
      this.title = '告知受理书'
      // this.form = Object.assign({}, row)
      this.dialogFormVisible = true
    },
    close() {
      //this.form = this.$options.data().form
      this.dialogFormVisible = false
    },
    save() {
      this.$baseMessage('已提交确认！', 'success')
      this.$emit('fetch-data')
      this.close()
    },
  },
}
</script>
<style lang="scss" scoped>
	
	.text-title{
		width: 90%;
		margin: 10px auto 20px;
		padding: 0 0 10px;
		line-height: 24px;
		font-size: 18px;
		font-weight: bold;
		text-align: center;
	}
	.cen-des{
		margin-bottom: 60px;
		p{
			padding: 12px 0 0;
			line-height: 24px;
			margin: 0;
			text-indent: 24px;
		}
	}
	.bot-des{
		text-align: right;
	}
</style>