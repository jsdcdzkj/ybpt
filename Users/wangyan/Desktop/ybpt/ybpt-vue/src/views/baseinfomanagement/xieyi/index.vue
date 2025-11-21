<template>
  <div class="main-container">

    <el-card class="card reg-con" shadow="never">
      <div slot="header">
        <span class="tips">免责协议</span>
      </div>
      <div class="editor-container">
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">

          <el-form-item label="免责协议" prop="content" class="vab-quill-content">
            <vab-quill v-model="form.content" :options="options" style="height:calc(100vh - 400px)"></vab-quill>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="handleSee">预览效果</el-button>
            <el-button type="primary" @click="handleSave">保存</el-button>
          </el-form-item>
        </el-form>
        <el-dialog title="预览效果" :visible.sync="dialogTableVisible">
          <div style="min-height: 60vh">
            <h1 class="news-title">{{ form.title }}</h1>
            <div class="news-content" v-html="form.content"></div>
          </div>
        </el-dialog>
      </div>
    </el-card>

  </div>

</template>

<script>
import { getAgreement, saveOrUpdate } from '@/api/agreement';
import vabQuill from '@/plugins/vabQuill';
export default {
  name: 'Editor',
  components: { vabQuill },
  data() {
    return {
      options: {
        theme: 'snow',
        bounds: document.body,
        debug: 'warn',
        modules: {
          toolbar: [
            ['bold', 'italic', 'underline', 'strike'],
            [{ header: [1, 2, 3, 4, 5, 6, false] }],
            [{ size: ['small', false, 'large', 'huge'] }],
            [{ color: [] }, { background: [] }],
            ['blockquote', 'code-block'],
            [{ list: 'ordered' }, { list: 'bullet' }],
            [{ script: 'sub' }, { script: 'super' }],
            [{ indent: '-1' }, { indent: '+1' }],
            [{ align: [] }],
            [{ direction: 'rtl' }],
            // [{ font: [] }],
            ['clean'],
            ['link', 'image'],
          ],
        },
        placeholder: '内容...',
        readOnly: false,
      },
      borderColor: '#dcdfe6',
      dialogTableVisible: false,
      form: {
        content: '',
        id: '',
      },
      rules: {
        content: [
          {
            required: true,
            message: '请输入内容',
            trigger: 'blur',
          },
        ],
      },
    }
  },
  created() {
    this.fetchData();
  },
  methods: {
    async fetchData() {
      const { data } = await getAgreement();
      this.form.content = data.content;
      this.form.id = data.id;
    },
    handleSee() {
      this.$refs['form'].validate((valid) => {
        this.$refs.form.validateField('content', (errorMsg) => { })
        if (valid) {
          this.dialogTableVisible = true
        } else {
          return false
        }
      })
    },
    handleSave() {
      this.$refs['form'].validate((valid) => {
        this.$refs.form.validateField('content', (errorMsg) => {
          this.borderColor = '#dcdfe6'
          if (errorMsg) {
            this.borderColor = '#F56C6C'
          }
        })
        if (valid) {
          // 查询免责协议
          console.log(this.form)
          const { data } = saveOrUpdate(this.form).then((res) => {
            if (res.code === 0) {
              this.$baseMessage('保存成功', 'success')
            }
          }).catch((_) => { });
        } else {
          return false
        }
      })
    },
  },
}
</script>
<style lang="scss" scoped>
.editor-container {
  .news {
    &-title {
      text-align: center;
    }

    &-content {
      ::v-deep {
        p {
          line-height: 30px;

          img {
            display: block;
            margin-right: auto;
            margin-left: auto;
          }
        }
      }
    }
  }

  .vab-quill-content {
    ::v-deep {
      .el-form-item__content {
        line-height: normal;
      }
    }
  }
}
::v-deep{
  .el-card{height:calc(100vh - 250px);}
  .el-card__body{height:100%;}
  .ql-container{height:calc(100% - 40px);}
}
</style>
