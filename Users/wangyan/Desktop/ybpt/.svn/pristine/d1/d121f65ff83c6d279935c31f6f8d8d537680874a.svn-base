<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogFormVisible"
            width="60%"
            @close="close"
            append-to-body
    >
        <vab-query-form>
            <el-form :inline="true" :model="queryForm">
                <vab-query-form-left-panel :span="20">
                    <el-form-item label="医疗机构编码">
                        <el-input v-model.trim="queryForm.MEDINS_CODE" clearable/>
                    </el-form-item>
                    <el-form-item label="医疗机构名称">
                        <el-input v-model.trim="queryForm.MEDINS_NAME" clearable/>
                    </el-form-item>
                    <el-form-item label="医疗机构类型">
                        <el-select v-model="queryForm.type" style="width: 150px" disabled>
                            <el-option label="定点医疗机构" value="0"></el-option>
                        </el-select>
                    </el-form-item>
                </vab-query-form-left-panel>
                <vab-query-form-right-panel :span="4">
                    <el-form-item>
                        <el-button @click="reseat">重 置</el-button>
                        <el-button type="primary" @click="queryData">查 询</el-button>
                    </el-form-item>
                </vab-query-form-right-panel>
            </el-form>
        </vab-query-form>
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
                            :label="scope.row.FIXMEDINS_CODE"
                    ></el-radio>
                </template>
            </el-table-column>
            <el-table-column label="序号" width="150px" align="center">
                <template #default="scope">
                    {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column
                    label="医疗机构编号"
                    align="center"
                    width="140px"
                    show-overflow-tooltip
                    prop="FIXMEDINS_CODE"
            ></el-table-column>
            <el-table-column
                    label="医疗机构名称"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="FIXMEDINS_NAME"
            ></el-table-column>
            <el-table-column
                    label="医疗服务机构类型"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
            >
                定点医疗机构
            </el-table-column>
            <el-table-column
                    label="医院等级"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="HOSP_LV_NAME"
            ></el-table-column>
            <el-table-column
                    label="医疗机构等级"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="MEDINSLV_NAME"
            ></el-table-column>
            <el-table-column
                    label="统一社会信用代码"
                    align="center"
                    show-overflow-tooltip
                    width="200px"
                    prop="USCC"
            ></el-table-column>
            <el-table-column
                    label="限价医院等级"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="LMTPRIC_HOSP_LV_NAME"
            ></el-table-column>
            <el-table-column
                    label="起付线医院等级"
                    align="center"
                    show-overflow-tooltip
                    width="140px"
                    prop="DEDC_HOSP_LV_NAME"
            ></el-table-column>
            <el-table-column
                    label="国家异地平台机构编号"
                    align="center"
                    show-overflow-tooltip
                    width="200px"
                    prop="NAT_PLAF_CODE"
            ></el-table-column>
            <el-table-column
                    label="异地医药机构类型"
                    align="center"
                    show-overflow-tooltip
                    width="160px"
                    prop="OUT_FIXMEDINS_TYPE_NAME"
            ></el-table-column>
            <el-table-column
                    label="定点联网开通标志"
                    align="center"
                    show-overflow-tooltip
                    width="160px"
                    prop="FIX_ONLN_OPEN_FLAG_NAME"
            ></el-table-column>
            <el-table-column
                    label="异地联网开通类型"
                    align="center"
                    show-overflow-tooltip
                    width="160px"
                    prop="OUT_ONLN_OPEN_TYPE_NAME"
            ></el-table-column>
            <el-table-column
                    label="医院责任人姓名"
                    align="center"
                    show-overflow-tooltip
                    width="160px"
                    prop="HI_RESPER_NAME"
            ></el-table-column>
            <el-table-column
                    label="医院责任人身份证号码"
                    align="center"
                    show-overflow-tooltip
                    width="200px"
                    prop="HI_RESPER_CERTNO"
            ></el-table-column>
            <el-table-column
                    label="医院责任人电话号码"
                    align="center"
                    show-overflow-tooltip
                    width="160px"
                    prop="HI_RESPER_TEL"
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
import {getMedinsInfoB} from '@/api/reflAppyEvtC'

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
                MEDINS_CODE: '',
                MEDINS_NAME: '',
                deptName: '',
                type: '定点医疗机构',
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
            this.radioId = val.FIXMEDINS_CODE
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
        reseat(){
            this.queryForm.MEDINS_NAME = ""
            this.queryForm.MEDINS_CODE = ""
            this.queryData()
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
            const { data } = await getMedinsInfoB(this.queryForm)
            this.list = data.records
            this.total = data.total
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        showDia() {
            this.title = '医疗机构查询'
            this.dialogFormVisible = true
        },
        close() {
            // this.$refs['form'].resetFields()
            // this.form = this.$options.data().form
            this.dialogFormVisible = false
        },
        save() {
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