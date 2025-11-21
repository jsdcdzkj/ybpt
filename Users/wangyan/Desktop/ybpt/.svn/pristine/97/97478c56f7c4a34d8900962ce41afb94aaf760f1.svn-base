<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogFormVisible"
            width="60%"
            @close="close"
            append-to-body
    >
        <vab-query-form>
            <el-form :inline="true" :model="queryForm" ref="queryForm" :rules="rules">
                <vab-query-form-left-panel :span="20">
                    <el-form-item label="诊断代码">
                        <el-input v-model.trim="queryForm.DIAG_CODE" clearable/>
                    </el-form-item>
                    <el-form-item label="诊断名称">
                        <el-input v-model.trim="queryForm.DIAG_NAME" clearable/>
                    </el-form-item>
                    <el-form-item label="诊断类型" prop="DIAG_TYPE">
                        <el-select v-model="queryForm.DIAG_TYPE" style="width:150px;">
                            <el-option
                                    v-for="item in DIAG_TYPE_OPTIOINS"
                                    :key="item.NAT_DIC_VAL_NAME"
                                    :label="item.NAT_DIC_VAL_NAME"
                                    :value="item.NAT_DIC_VAL_NAME">
                            </el-option>
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
                            :label="scope.row.DIAG_CODE"
                    ></el-radio>
                </template>
            </el-table-column>
            <el-table-column
                    label="序号"
                    width="80"
                    align="center"
                    show-overflow-tooltip
            >
                <template #default="scope">
                    {{ (queryForm.pageNo - 1) * queryForm.pageSize + scope.$index + 1 }}
                </template>
            </el-table-column>
            <el-table-column
                    label="诊断代码"
                    align="center"
                    show-overflow-tooltip
                    prop="DIAG_CODE"
            ></el-table-column>
            <el-table-column
                    label="诊断名称"
                    align="center"
                    show-overflow-tooltip
                    prop="DIAG_NAME"
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
import {getDiagList, getNatDataDicA} from '@/api/reflAppyEvtC'

export default {
    name: 'Index',
    components: {},
    data() {
        return {
            radio: '',
            radioId: '',
            tableData: [],
            title: '',
            dialogFormVisible: false,
            checked: false,
            isShow: false,
            list: null,
            listLoading: true,
            layout: 'total, sizes, prev, pager, next, jumper',
            total: 0,
            selectRows: '',
            elementLoadingText: '正在加载...',
            queryNatDataDicA: {
                DIC_TYPE_CODE: '',//字典类型代码
                PRNT_DIC_VAL_CODE: '',//字典类型代码父类
            },
            DIAG_TYPE_OPTIOINS: null,
            queryForm: {
                DIAG_CODE: '',
                DIAG_NAME: '',
                DIAG_TYPE: '',
                pageNo: 1,
                pageSize: 10,
            },
            rules: {
                DIAG_TYPE: [{required: true, trigger: 'blur', message: '请选择诊断类型'}],
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
        async getNatDataDicA() {
            this.queryNatDataDicA.DIC_TYPE_CODE = 'DIAG_TYPE'
            const DIAG_TYPE = await getNatDataDicA(this.queryNatDataDicA)
            this.DIAG_TYPE_OPTIOINS = DIAG_TYPE.data
        },
        handleCurrentChange(val) {
            this.radioId = val.DIAG_CODE
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
        reseat() {
            this.queryForm.DIAG_CODE = "";
            this.queryForm.DIAG_NAME = "";
            this.queryData()
        },
        queryData() {
            this.queryForm.pageNo = 1
            this.$refs['queryForm'].validate((valid) => {
                if (valid){
                    this.fetchData()
                }
            });
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
            const {data} = await getDiagList(this.queryForm)
            this.list = data.records
            this.total = data.total
            setTimeout(() => {
                this.listLoading = false
            }, 300)
        },
        showDia() {
            this.getNatDataDicA()
            this.title = '诊断查询'
            this.dialogFormVisible = true
        },
        close() {
            this.dialogFormVisible = false
        },
        save() {
            this.$emit('fetch-data', this.selectRows,this.queryForm.DIAG_TYPE)
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