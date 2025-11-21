<template>
    <!-- <el-dialog :title="title" :visible.sync="dialogFormVisible" width="60%" @close="close" :close-on-click-modal="false"> -->
    <el-drawer :title="title" :visible.sync="dialogFormVisible" direction="rtl"
               :with-header="false" custom-class="box_drawer" size="40%" ref="drawer" :close-on-click-modal="true"
               append-to-body>
        <div class="drawer_content">
            <div class="drawer_main">
                <div class="newsbox">
                    <!--                    <div class="title">{{form.title}}</div>-->
                    <div class="title" v-html="form.title"></div>
                    <div class="news-content" v-html="form.content"></div>
          <div :key="item.id" class="fujian" v-for="item in form.fileInfoList">
                        <el-link icon="el-icon-link" :href="fileURL+ item.fileUrl">{{ item.fileName }}</el-link>
          </div>

                </div>
            </div>
            <div class="drawer_footer">

                <el-button @click="close">关 闭</el-button>

            </div>
        </div>
        <!-- <div slot="footer" class="dialog-footer">
          <el-button @click="close">取 消</el-button>
          <el-button type="primary" @click="save" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
        </div> -->
    </el-drawer>
    <!-- </el-dialog> -->
</template>

<script>
import vabQuill from '@/plugins/vabQuill'
import {importData, saveApi, getEntityByIdApi, editApi, setNoticeIsReadApi} from "@/api_check/notice";
import {fileURL} from "@/config/setting.config";

export default {
    name: 'noticesview',
    data() {
        return {
            dynamicTags: [],
            inputValue: '',
            form: {
                title: "",
                content: "",
                rangeList: [],
                fileInfoList: []
            },
            admdvs: [],
            title: '',
            dialogFormVisible: false,
            id: "",
            isDisabled: false,
            loading: false,
            fileList: [],
            fileURL
        }
    },
    methods: {
        showDia(row) {
            debugger
            this.loading = false;
            if (!row) {
                this.title = '添加';
                this.id = "";
                this.isDisabled = false;
            } else {
                this.id = row.id;
                this.title = '查看';
                this.getEntityById(this.id)
                this.setNoticeIsRead(this.id)

                this.isDisabled = true;
            }
            this.dialogFormVisible = true
        },

        async getEntityById(id) {
            const {data} = await getEntityByIdApi(id);
            console.log(data)
            this.form.title = data.title
            this.form.content = data.content
            this.form.fileInfoList = data.fileInfoList;
        },

        async setNoticeIsRead(noticeId) {
            let res = await setNoticeIsReadApi(noticeId);
            if (res.code == 0) {
                this.$emit('fetch-data')
                window.top2()
            }
        },

        close() {

            this.dialogFormVisible = false
            this.loading = false
        },
        save() {
            var that = this;
            if (that.loading) {
                return
            }
            that.$refs['form'].validate(async (valid) => {
                if (valid) {
                    that.loading = true;
                    // 动画关闭需要一定的时间
                    setTimeout(() => {
                        this.loading = false
                    }, 1000)

                    // that.$baseMessage(msg, 'success')

                    that.close()
                } else {
                    return false
                }
            })
        },
    },
}
</script>
<style scoped lang="scss">
.newsbox {
    padding-top: 20px;

    .title {
        font-size: 16px;
        font-weight: bold;
        text-align: center;
    }

    .content {
        line-height: 30px;
        font-size: 12px;
        margin-top: 20px;
    }

    .fujian {
        margin-top: 20px;
    }
}

</style>
