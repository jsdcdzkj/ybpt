<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogFormVisible"
            width="60%"
            @close="close"
            append-to-body
    >
        <el-table
                v-loading="listLoading"
                ref="listTable"
                stripe
                :data="list"
                :element-loading-text="elementLoadingText"
                highlight-current-row
                border
                @current-change="handleCurrentChange"
                height="260px"
        >
            <template slot="empty">
                <el-empty :image-size="150"></el-empty>
            </template>
            <el-table-column label="选择" width="80" align="center">
                <template slot-scope="scope">
                    <el-radio
                            class="radio"
                            v-model="radioId"
                            :label="scope.row.MDTRT_ID"
                    ></el-radio>
                </template>
            </el-table-column>
            <el-table-column label="序号" width="150px" align="center">
                <template #default="scope">
                    {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column
                    label="就诊ID"
                    align="center"
                    width="140px"
                    show-overflow-tooltip
                    prop="MDTRT_ID"
            ></el-table-column>
            <el-table-column
                    label="证件号"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="CERTNO"
            ></el-table-column>
            <el-table-column
                    label="人员姓名"
                    align="center"
                    show-overflow-tooltip
                    width="200px"
                    prop="PSN_NAME"
            ></el-table-column>

            <el-table-column
                    label="定点医疗机构名称"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="FIXMEDINS_NAME"
            ></el-table-column>
            <el-table-column
                    label="病历号"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="MEDRCDNO"
            ></el-table-column>
            <el-table-column
                    label="主诊医师姓名"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="CHFPDR_NAME"
            ></el-table-column>
            <el-table-column
                    label="病种名称"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="DISE_NAME"
            ></el-table-column>
            <el-table-column
                    label="手术操作名称"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="OPRN_OPRT_NAME"
            ></el-table-column>
            <el-table-column
                    label="计划生育服务证号"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="FPSC_NO"
            ></el-table-column>
        </el-table>

        <el-pagination
                background
                :current-page="queryForm.pageNo"
                :page-size="queryForm.pageSize"
                :layout="layout"
                :total="total"
                @size-change="handleSizeChange"
                @current-change="handleCurrentChange2"
        ></el-pagination>

        <div slot="footer" class="dialog-footer">
            <el-button @click="close">关 闭</el-button>
            <el-button type="primary" @click="save">确 定</el-button>
        </div>
    </el-dialog>
</template>
<script>
import {getMdtrtD} from '@/api/reflAppyEvtC'

export default {
    name: 'Index',
    components: {},
    data() {
        return {
            currentRow: null,
            radio: '',
            radioId: '',
            tableData: [],
            title: '',
            dialogFormVisible: false,
            value1: '',
            checked: false,
            isShow: false,
            list: null,
            listLoading: true,
            layout: 'total, sizes, prev, pager, next, jumper',
            total: 0,
            selectRows: '',
            elementLoadingText: '正在加载...',
            queryForm: {
                CERTNO: '',
                FIXMEDINS_CODE: '',
                pageNo: 1,
                pageSize: 10,
                username: '',
            },
        }
    },
    created() {
        this.fetchData()
    },
    porps: {
        //父组件传递过来的初始选中值，根据自己项目需求设置
        chooseData: {
            type: Object,
        },
    },
    watch: {
        //观察是否有父组件传递的初始值或者变化，重新选中
        chooseData(val) {
            if (val) {
                this.radio = false
                this.getInitChoose()
            }
        },
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
        handleCurrentChange(val) {
            this.radioId = val.MDTRT_ID
            this.selectRows = val
        },
        handleSizeChange(val) {
            this.queryForm.pageSize = val
            this.fetchData()
        },
        getCurrentRow(index) {
            this.radio = index
        },
        handleCurrentChange2(val) {
            this.queryForm.pageNo = val
            this.fetchData()
        },
        queryData() {
            this.queryForm.pageNo = 1
            this.fetchData()
        },
        moreQuery() {
            this.isShow = !this.isShow
        },
        //设置单选框选择状态
        getInitChoose() {
            if (this.chooseData) {
                let index = this.tableData.findIndex(
                    (item) => item.userUuid == this.chooseData.id
                )
            }
            if (index > -1) {
                this.radio = index
            }
        },
        async fetchData() {
            this.listLoading = true
            const {data} = await getMdtrtD(this.queryForm)
            this.list = data.records
            this.total = data.total
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        showDia(CERTNO,MEDINS_CODE) {
            this.queryForm.CERTNO = CERTNO
            this.queryForm.FIXMEDINS_CODE = MEDINS_CODE
            this.title = '就诊查询'
            this.dialogFormVisible = true
            this.fetchData()
        },
        close() {
            // this.$refs['form'].resetFields()
            // this.form = this.$options.data().form
            this.dialogFormVisible = false
        },
        save() {
            // this.$baseMessage('模拟保存成功', 'success')
            this.$emit('fetch-data',this.selectRows)
            this.close()
        },
    },
}
</script>
<style lang="scss" scoped>
::v-deep {
  .el-radio__label {
    display: none;
  }
}
</style>