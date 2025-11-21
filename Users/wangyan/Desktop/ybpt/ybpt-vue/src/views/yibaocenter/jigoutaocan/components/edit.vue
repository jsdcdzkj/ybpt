<template>
    <el-dialog
            :title="title"
            :visible.sync="dialogFormVisible"
            width="705px"
            @close="close"
            :close-on-click-modal = "false"
    >
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
            <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="套餐名称" prop="pack_name">
                        <el-input
                                v-model.trim="form.pack_name"
                                autocomplete="off"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="套餐年份" prop="pack_year">
                        <el-date-picker
                                v-model.trim="form.pack_year"
                                type="year"
                                value-format="yyyy"
                                @change="getListUnion"
                        ></el-date-picker>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="套餐金额" prop="pack_money">
                        <el-input-number
                                type="number"
                                v-model.trim="form.pack_money"
                                autocomplete="off"
                                :min="0"
                        ></el-input-number>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="适用范围" prop="applicable_scope">
                        <el-select v-model.trim="form.applicable_scope"  @change="applicable_scope"  clearable class="w">
                            <el-option
                                    v-for="item in applicable_scope_options"
                                    :key="item.value"
                                    :label="item.label"
                                    :value="item.value">
                            </el-option>
                        </el-select>
                    </el-form-item>
                </el-col>
                <!--                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">-->
                <!--                    <el-form-item label="当前状态" prop="item_state">-->
                <!--                        <el-switch-->
                <!--                                v-model="form.if_use"-->
                <!--                                active-text="启用"-->
                <!--                                inactive-text="禁用"-->
                <!--                                active-value="1"-->
                <!--                                inactive-value="0"-->
                <!--                        ></el-switch>-->
                <!--                    </el-form-item>-->
                <!--                </el-col>-->
                <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                    <el-form-item label="是否上架" prop="item_state">
                        <el-switch
                                v-model="form.if_open"
                                active-text="是"
                                inactive-text="否"
                                active-value="1"
                                inactive-value="0"
                        ></el-switch>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="套餐描述" prop="memo">
                        <el-input
                                v-model.trim="form.memo"
                                type="textarea"
                                :rows="5"
                        ></el-input>
                    </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                    <el-form-item label="体检项目" prop="item_to_meal" class="item_to_meal">
                        <el-transfer   :titles="['可选择列表', '已选择列表']" v-model="form.item_to_meal"
                                     :data="medical_item"></el-transfer>
                    </el-form-item>
                </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
            <el-button @click="close">取 消</el-button>
            <el-button type="primary" @click="save" v-show="form.status != 2" :loading="loading">{{ loading ? '确定中 ...' : '确定' }}</el-button>
            <el-button type="primary" @click="save" v-show="form.status == 2" :loading="loading">{{ loading ? '确定提交审核 ...' : '提交审核' }}</el-button>
        </div>
    </el-dialog>
</template>

<script>
import { getDicts } from '@/api/dictManagement'
import {getListUnion} from '@/api_check/tijianguanli'
import {edit} from '@/api_check/taocan'

export default {
    name: 'edit',
    data() {
        return {
            loading: false,
            applicable_scope_options: [],//适用范围
            medical_item: [],
            right_default: ['1542770878681362434','1542771038698254338'],
            form: {
                pack_year: '',//套餐年份 (取年份)
                pack_name: '',//套餐名称
                pack_money: '',//套餐金额
                applicable_scope: '', //适用范围
                memo: '', //套餐描述
                applicable_scope_name: '', //适用范围-转义
                pack_source: '1',//套餐来源 (机构:1，通用:2)
                status: '0',//审核状态 (待审:0，通过:1， 驳回:2)
                if_open: '1',//是否上架 (不上架:0 ， 上架：1)
                if_use: '1',//启用禁用 (禁用:0 ， 启用：1)
                item_to_meal: [],//体检项目
                is_del: '0',//删除标志
            },
            rules: {
                pack_year: [{required: true, trigger: 'blur', message: '请选择套餐年份'}],
                pack_name: [{required: true, trigger: 'blur', message: '请输入套餐名称'}],
                applicable_scope: [{ required: true, trigger: 'blur', message: '请选择适用范围' }],
                pack_money: [{required: true, trigger: 'blur', message: '请输入套餐金额'}],
                item_to_meal: [{required: true, trigger: 'blur', message: '请选择体检项目'}],
            },
            title: '',
            year:'',
            is_title:false,
            dialogFormVisible: false,
        }
    },
    created() {
    },
    methods: {

        showDia(row) {
            if (!row) {
                this.title = '添加'
                this.year=new Date().getFullYear();
                this.is_title=true;
            } else {
                this.title = '编辑'
                this.form = Object.assign({}, row)
                this.year=this.form.pack_year;
                this.is_title=false;
            }
            this.getDict()
            this.getMedicalItem()
            this.dialogFormVisible = true
        },

        async getDict() {
            const res = await getDicts({ "type": "applicable_scope" });
            this.applicable_scope_options = res.data
        },
        applicable_scope(val){
            const dic = this.applicable_scope_options.find((item) => {
                return item.value == val
            })
            this.form.applicable_scope = dic.value
            this.form.applicable_scope_name = dic.label
        },
        async getListUnion(){
          this.form.item_to_meal=[];
          const {data} = await getListUnion({year:this.form.pack_year})
          this.medical_item = []

          if (data) {
            for (let i = 0; i < data.length; i++) {
              if(data[i].is_generic == 1){

                this.form.item_to_meal.push(data[i].id)
              }
              this.medical_item.push({
                key: data[i].id,
                label: data[i].item_name,
                disabled: data[i].is_generic == 1
              })
            }
          }
        },
        async getMedicalItem() {

            const {data} = await getListUnion({year:this.year})
            this.medical_item = []

            if (data) {
                for (let i = 0; i < data.length; i++) {
                    if(data[i].is_generic == 1&&this.is_title){
                        this.form.item_to_meal.push(data[i].id)
                    }
                    this.medical_item.push({
                        key: data[i].id,
                        label: data[i].item_name,
                        disabled: data[i].is_generic == 1
                    })
                }
            }
        },
        close() {
            this.$refs['form'].resetFields()
            this.form = this.$options.data().form
            this.dialogFormVisible = false
        },
        save() {
            if (this.loading) {
                return
            }
            this.$refs['form'].validate(async (valid) => {
                if (valid) {
                    this.loading = true
                    // 动画关闭需要一定的时间
                    setTimeout(() => {
                        this.loading = false
                    }, 400)
                  console.log(this.medical_item);


                    const {code, msg} = await edit(this.form)
                    if (code == -1) {
                        this.$baseMessage(msg, 'error')
                    } else {
                        this.$baseMessage(msg, 'success')
                    }
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
