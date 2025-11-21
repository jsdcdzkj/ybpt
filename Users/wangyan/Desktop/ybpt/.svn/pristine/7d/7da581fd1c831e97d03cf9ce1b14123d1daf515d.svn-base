export const AynsCommonDialog = {
  data() {
    return {
      // 按钮加载中
      btnLoading: false,
      // 是否是异步
      isAsync: false,
      // 显示dialog
      show: true,
      title: '标题',
      // 回调函数
      callback: null,
      // 是否显示取消按钮
      cancelButton: true,
      // 是否显示确认按钮
      confirmButton: true,
      width: '1000px',
    }
  },
  methods: {
    done() {
      this.btnLoading = !this.btnLoading
      this.show = false
      this.callback && this.callback(false)
    },
    /**
     * 确认按钮取消事件
     */
    onCancel() {
      this.show = false
      this.callback && this.callback(false)
    },
    /**
     * 确认按钮点击事件
     */
    onConfirm() {
      if (this.$refs.form) {
        this.$refs.form.validate((valid) => {
          if (valid) {
            this._onConfirm()
          }
        })
      } else {
        this._onConfirm()
      }
    },
    _onConfirm() {
      if (this.isAsync) {
        this.btnLoading = true
        this.callback && this.callback(this.form, this.done.bind(this))
      } else {
        this.show = false
        this.callback && this.callback(this.form, true)
      }
    },
  },
}
