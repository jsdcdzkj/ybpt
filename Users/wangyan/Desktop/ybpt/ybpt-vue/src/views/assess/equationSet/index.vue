<template>
    <div class="main-container">
        <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="card" shadow="never">
                    <div slot="header">
                        <span class="tips">公式配置信息</span>
                        <el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">新增公式</el-button>
                    </div>
                    <div class="params_tips">参数说明：机构指数（P） 待分配奖励金（M） 待分配系数（X） 机构保证金（G）</div>
                    <div v-for="el,index in list" :key="index" class="item">
                        <span class="label">指数范围：</span>
                        <el-input v-model="el.indexBegain" style="width: 100px;"></el-input>
                        <span class="to">~</span>
                        <el-input v-model="el.indexEnd" style="width: 100px;"></el-input>
                        <span class="label ml100">返还公式：</span>
                        <el-input v-model="el.formula" style="width: 200px;margin-right: 10px;"></el-input>
                        <el-button type="danger" plain @click="del(index,el)">删除</el-button>
                    </div>
                    <div style="margin-top: 40px;">
                        <el-button type="primary" size="medium" @click="addData">保存</el-button>
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
    import {selectList, addConfigs, delConfig} from '@/api/evalFormulaConfig'
    export default {
        name: 'Index',
        data() {
            return {
                list: [],
            }
        },
        created() {

        },
        mounted() {
            this.getData();
        },
        methods: {
            handleCurrentChange(val) {
                this.selectRows = val
            },
            handleAdd() {
                this.list.push({
                    indexBegain: '',
                    indexEnd: '',
                    formula: ''
                })
            },
            del(index,item) {
                var that = this;
                if(item.id == null){
                    that.list.splice(index, 1);
                }else{
                    this.$baseConfirm('确定要删除吗？', null, async () => {
                        delConfig(item).then((res) => {
                            if (res.code == 0) {
                                that.list.splice(index, 1);
                                this.$baseMessage("刪除成功！", 'success')
                            }else{
                                this.$baseMessage(res.msg, 'error')
                            }
                        })
                    })
                }
            },
            getData() {
                var that = this;
                selectList({}).then((res) => {
                    if (res.code == 0) {
                        var resData = res.data
                        that.list = resData;
                        if(resData == null || resData.length == 0){
                            that.handleAdd();
                        }
                    }
                })
            },
            addData() {
                var that = this;
                this.$baseConfirm('确定要保存吗？', null, async () => {
                    addConfigs({"configs":that.list}).then((res) => {
                        if (res.code == 0) {
                            that.getData();
                            this.$baseMessage("保存成功！", 'success')
                        }else{
                            this.$baseMessage(res.msg, 'error')
                        }
                    })
                })
            },

        }
    }
</script>
<style lang="scss" scoped>
    .params_tips {
        font-weight: bold;
        font-size: 20px;
        margin-bottom: 20px;
    }

    .item {
        margin-bottom: 20px;

        .label {
            margin-right: 4px;
        }

        .to {
            margin: 0 10px;
        }

        .ml100 {
            margin-left: 100px;
        }
    }
</style>