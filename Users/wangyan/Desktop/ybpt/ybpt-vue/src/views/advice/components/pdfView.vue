<template>
    <el-drawer
            :title="title"
            :visible.sync="dialog"
            direction="rtl"
            :with-header="false"
            custom-class="box_drawer"
            size="1300px"
            ref="drawer"
    >
        <!-- pdf 内嵌 -->
        <iframe
                :src="PDFsrc"
                frameborder="0"
                style="width: 100%; height: 100%"
        ></iframe>
        <div class="drawer_content">
            <div class="drawer_footer">
                <el-button type="primary" @click="dcb">下载调查表</el-button>
                <el-button type="primary" @click="hz">下载汇总表</el-button>
                <el-button type="primary" @click="downPdf">下载PDF</el-button>
                <el-button @click="cancelForm">关 闭</el-button>
            </div>
        </div>

    </el-drawer>
</template>

<script>

import {fileURL} from "@/config/setting.config";
import {tcmWord} from '@/api/advice'
import { baseURL } from '@/config'
export default {
    name: 'pdfView',
    components: {},
    data() {
        return {
            PDFsrc: '',
            id: '',
            downPath: '',
            title: '',
            dialog: false,
            loading: false,
            timer: null,
        }
    },
    mounted() {
    },
    methods: {
        showDia(row) {
            this.id = "" ;
            if (row.pdf_path) {
                // this.PDFsrc = fileURL + row.pdf_path + '?n=' + row.pdf_path.substring(row.pdf_path.lastIndexOf('/'), row.pdf_path.lastIndexOf('.')) + '&download=0'
                this.PDFsrc = row.pdf_path
            }
            if (row.down_pdf_path) {
                // this.downPath = fileURL + row.pdf_path + '?n=' + row.pdf_path.substring(row.pdf_path.lastIndexOf('/'), row.pdf_path.lastIndexOf('.')) + '&download=1'
                this.downPath = row.down_pdf_path
            }
            this.title = '查看'
            this.id = row.id ;
            // this.form = Object.assign({}, row)
            this.dialog = true
        },
        downPdf() {
            self.location.href = this.downPath;
        },
        dcb(){
           window.location.href = baseURL+"drugPrice/tcmWord?id="+this.id

        },
        hz(){
            window.location.href = baseURL+"drugPrice/tcmExcel?id="+this.id
        },
        cancelForm() {
            this.loading = false
            this.dialog = false
            this.PDFsrc = "";
            this.downPath = "";
            clearTimeout(this.timer)
        },
    },
}
</script>
<style scoped lang="scss">

</style>