<template>
  <el-dialog
      :title="title"
      :visible.sync="dialogFormVisible"
      width="800px"
      @close="close"
      append-to-body
      :close-on-click-modal="false"
  >
    <el-scrollbar style="height: 53vh; overflow: hidden;">
      <el-form ref="form" :model="form" :rules="rules" label-width="190px" style="margin: 0 15px;">
        <el-form-item label="是否在医保制剂目录" prop="isInCategory" class="">
          <el-radio-group v-model="form.isInCategory" @input="handleChildPrice(form.isInCategory)">
            <el-radio :label="'1'">是</el-radio>
            <el-radio :label="'0'">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="国家医疗机构制剂代码" prop="nationalFormulaCode">
          <el-input v-model.trim="form.nationalFormulaCode" placeholder="请选择" v-if="form.isInCategory == 1" @click.native="openwin(form)" :readonly="form.isInCategory==1">
            <el-button
                slot="append"
                icon="el-icon-search"
                @click="openwin"
            ></el-button>
          </el-input>
          <el-input v-model.trim="form.nationalFormulaCode" placeholder="请输入" v-else  :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="制剂名称" prop="formulaName">
          <el-input v-model="form.formulaName" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="分类编码" prop="categoryCode"  v-if="form.isInCategory == 0">
          <el-input v-model="form.categoryCode" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="药品通用名编码" prop="genericNameCode"  v-if="form.isInCategory == 0">
          <el-input v-model="form.genericNameCode" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="产品名编码" prop="productNameCode"  v-if="form.isInCategory == 0">
          <el-input v-model="form.productNameCode" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="商品名" prop="goodsName"  v-if="form.isInCategory == 0">
          <el-input v-model="form.goodsName" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="支付类别" prop="payType"  v-if="form.isInCategory == 0">
          <el-input v-model="form.payType" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="批准文号" prop="approvalNo">
          <el-input v-model="form.approvalNo" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="制剂注册单位" prop="registerCompanyName" >
          <el-input v-model="form.registerCompanyName" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="剂型" prop="dosageForm">
          <el-input v-model="form.dosageForm" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="规格" prop="specs"  v-if="form.isInCategory == 0">
          <el-input v-model="form.specs" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="最小计价单位" prop="minPriceUnit"  v-if="form.isInCategory == 0">
          <el-input v-model="form.minPriceUnit" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="最小包装" prop="minPackage"  v-if="form.isInCategory == 0">
          <el-input v-model="form.minPackage" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="单位" prop="unit">
          <el-input v-model="form.unit" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="目录编号" prop="catalogCode"  v-if="form.isInCategory == 0">
          <el-input v-model="form.catalogCode" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="个人先行自付比例" prop="selfPayRatio"  v-if="form.isInCategory == 0">
          <el-input v-model="form.selfPayRatio" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="备注" prop="remark"  v-if="form.isInCategory == 0">
          <el-input v-model="form.remark" placeholder="请输入" autocomplete="off" :readonly="form.isInCategory==1"></el-input>
        </el-form-item>
        <el-form-item label="价格（元）" prop="price">
          <el-input v-model.trim="form.price" placeholder="请输入" type="number" autocomplete="off" min="0"
                    @input="handlePrice(form.price)"></el-input>
        </el-form-item>
        <el-form-item label="本市同级公立医疗机构价格" prop="localPmPrice">
          <el-input v-model.trim="form.localPmPrice" placeholder="请输入" type="number" autocomplete="off" min="0"
                    @input="handlePrice(form.localPmPrice)"></el-input>
        </el-form-item>
      </el-form>

    </el-scrollbar>
    <div>
      <div>说明</div>
      <div>1.某个制剂申请成功后，需要六个月后（生成受理书时间算起）才可再次申请此制剂。</div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>

    <medicinal ref="medicinal" @fetch-data="fetchData"></medicinal>

  </el-dialog>
</template>

<script>
  import Medicinal from './medicinal'
import {checkSave} from '@/api/prepareApply'

  export default {
    name: 'examine',
    components: {
      Medicinal
    },
    data() {
      return {
        options: [
          {label: "11 一般医疗服务", value: "11 一般医疗服务"},
          {label: "12 一般检查治疗", value: "12 一般检查治疗 "},
          {label: "13 社区卫生及预防保健项目", value: "13 社区卫生及预防保健项目"},
          {label: "14 其它医疗服务项目", value: "14 其它医疗服务项目 "},
          {label: "15 非医疗服务项目", value: "15 非医疗服务项目 "},
          {label: "16 家庭医生签约服务费", value: "16 家庭医生签约服务费 "},
          {label: "17 特需服务项目", value: "17 特需服务项目"},
          {label: "21 医学影像", value: "21 医学影像"},
          {label: "22 超声检查", value: "22 超声检查"},
          {label: "23 核医学", value: "23 核医学"},
          {label: "24 放射治疗", value: "24 放射治疗"},
          {label: "25 检验", value: "25 检验"},
          {label: "26 血型与配血", value: "26 血型与配血"},
          {label: "27 病理检查", value: "27 病理检查 "},
          {label: "31 临床各系统诊疗", value: "31 临床各系统诊疗"},
          {label: "32 经血管介入诊疗", value: "32 经血管介入诊疗"},
          {label: "33 手术治疗", value: "33 手术治疗"},
          {label: "34 物理治疗与康复", value: "34 物理治疗与康复"},
          {label: "36 疼痛诊疗类", value: "36 疼痛诊疗类"},
          {label: "41 中医外治", value: "41 中医外治"},
          {label: "42 中医骨伤", value: "42 中医骨伤"},
          {label: "43 针刺", value: "43 针刺"},
          {label: "44 灸法", value: "44 灸法"},
          {label: "45 推拿疗法", value: "45 推拿疗法"},
          {label: "46 中医肛肠", value: "46 中医肛肠"},
          {label: "47 中医特殊疗法", value: "47 中医特殊疗法"},
          {label: "48 中医综合", value: "48 中医综合"},
          {label: "70 单病种项目", value: "70 单病种项目"},
          {label: "7 日间手术病", value: "7 日间手术病"},
        ],
        form: {
          nationalFormulaCode: undefined,//国家医疗机构制剂代码
          isInCategory: '1',//是否在医保制剂目录         1 是 0 否
          formulaName: undefined,//制剂名称
          categoryCode: undefined,//分类编码
          genericNameCode: undefined,//药品通用名编码
          productNameCode: undefined,//产品名编码
          goodsName: undefined,//商品名
          payType: undefined,//支付类别
          approvalNo: undefined,//批准文号
          registerCompanyName: undefined,//制剂注册单位
          dosageForm: undefined,//剂型
          specs: undefined,//规格
          minPriceUnit: undefined,//最小计价单位
          minPackage: undefined,//最小包装
          unit: undefined,//单位
          catalogCode: undefined,//目录编号
          selfPayRatio: undefined,//个人先行自付比例
          remark: undefined,//备注        (元)
          price: undefined,//价格        (元)
          localPmPrice: undefined,//本市同级公立医疗机构价格
          priceCalculate: {//测算
            nonlocalPmPrice: "",//外地同级医院参考价格
            projectTotalCost: ""//项目成本合计
          },
          //劳务支出
          // "laborList": [
          //   {
          //     //参加人员
          //     "participantName": "1",
          //     //人数
          //     "participantNum": "1",
          //     //工时
          //     "workHours": "1",
          //     //小时工资
          //     "hourWage": "1",
          //     //应付金额
          //     "payPrice": "1",
          //     //是否是小计数据：0-否，1-是
          //     "isSubtotal": "0"
          //   }
          // ],
          //材料消耗指出
          // "materialConsumeList": [
          //   {
          //     //品名
          //     "productName": "1",
          //     //单位
          //     "unit": "1",
          //     //数量
          //     "productNum": "1",
          //     //单价
          //     "unitPrice": "1",
          //     //应付金额
          //     "payPrice": "1",
          //     //是否是小计数据：0-否，1-是
          //     "isSubtotal": "0"
          //   }
          // ],
          // //固定资产折旧
          // "fixedAssetsDepreList": [
          //   {
          //     //设备名称
          //     "deviceName": "",
          //     //原值
          //     "originalValue": "",
          //     //使用年限
          //     "useLife": "",
          //     //使用时间
          //     "useTime": "",
          //     //应付金额
          //     "payPrice": "",
          //     //是否是小计数据：0-否，1-是
          //     "isSubtotal": "0"
          //   }
          // ],
          // //检验费
          // "checkFeeList": [
          //   {
          //     //品名
          //     "productName": "1",
          //     //单位
          //     "unit": "1",
          //     //数量
          //     "productNum": "1",
          //     //单价
          //     "unitPrice": "1",
          //     //应付金额
          //     "payPrice": "1",
          //     //是否是小计数据：0-否，1-是
          //     "isSubtotal": "0"
          //   }
          // ],
          //管理费损耗费
          // "manageLossOtherFee": {
          //   //管理费分摊
          //   "managFee": "1",
          //   //损耗及其它
          //   "lossOtherFee": "1",
          //   //小计
          //   "subtotal": "1"
          // },
          //附件列表
          // "fileInfoList": [
          //   {
          //     "id": "2a2d8ea0cc9b450897877a7c90cfe75e",
          //     "fileName": "标准管理-20240429155851.xlsx",
          //     "contentType": "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
          //     "fileType": "2",
          //     "fileSize": "10527",
          //     "fileUrl": "/file/标准管理-20240429155851xlsx/789aa2c6b19844f58ca68d4f7cc893a8.xlsx",
          //     "uploadTime": "2024-05-16 16:11:38",
          //     "bizType": "25",
          //     "bizId": "",
          //     "fileMd5": "893e1b87241c7aaf41296a19097a81c7"
          //   }
          // ]
        },
        rules: {
          'nationalFormulaCode': [
            { validator: (rule, value, callback)=>{
              if(this.form.isInCategory == 1){
                if(value == '' || !value){
                  callback(new Error('请选择国家医疗机构制剂代码'));
                }else{
                  callback();
                }
              }else{
                if(value == ''|| !value){
                  callback(new Error('请输入国家医疗机构制剂代码'));
                }else{
                  let userinfo = JSON.parse(localStorage.getItem('userinfo'))
                  let obj = {isInCategory:'0'
                    ,nationalFormulaCode:value
                    ,id:this.form.id?this.form.id:undefined
                    ,orgCode:userinfo.org_code
                  }
                  checkSave(obj).then(res =>{
                    console.log(res);
                    if(res.code=='-1'){
                      this.$message.error(res.msg)
                      callback(new Error(res.msg))
                    }else{
                      callback()
                    }
                  })

                }
              }
              }, trigger: 'blur',required: true }],
          'isInCategory': [{required: true, trigger: 'blur', message: '请确认是否在医保制剂目录'}],
          'formulaName': [{required: true, trigger: 'blur', message: '请输入制剂名称'}],
          // 'categoryCode': [{required: true, trigger: 'blur', message: '请输入分类编码'}],
          // 'genericNameCode': [{required: true, trigger: 'blur', message: '请输入药品通用名编码'}],
          // 'productNameCode': [{required: true, trigger: 'blur', message: '请输入产品名编码'}],
          // 'goodsName': [{required: true, trigger: 'blur', message: '请输入商品名'}],
          // 'payType': [{required: true, trigger: 'blur', message: '请输入支付类别'}],
          'approvalNo': [{required: true, trigger: 'blur', message: '请输入批准文号'}],
          'registerCompanyName': [{required: true, trigger: 'blur', message: '请输入制剂注册单位'}],
          'dosageForm': [{required: true, trigger: 'blur', message: '请输入剂型'}],
          'specs': [{required: true, trigger: 'blur', message: '请输入规格'}],
          // 'minPriceUnit': [{required: true, trigger: 'blur', message: '请输入最小计价单位'}],
          // 'minPackage': [{required: true, trigger: 'blur', message: '请输入最小包装'}],
          'unit': [{required: true, trigger: 'blur', message: '请输入单位'}],
          // 'catalogCode': [{required: true, trigger: 'blur', message: '请输入目录编号'}],
          // 'selfPayRatio': [{required: true, trigger: 'blur', message: '请输入个人先行自付比例'}],
          // 'remark': [{required: true, trigger: 'blur', message: '备注'}],
          'price': [{required: true, trigger: 'blur', message: '请输入价格'}],
          // 'localPmPrice': [{required: true, trigger: 'blur', message: '请输入本市同级公立医疗机构价格'}],
          // 'priceCalculate.nonlocalPmPrice': [{required: true, trigger: 'blur', message: '请输入外地同级医院参考价格'}],
        },
        title: '',
        dialogFormVisible: false,
      }
    },
    created() {
    },
    methods: {
      showEdit(row) {
      console.log(row,'examine');
        if (row.actionType != 'edit') {
          this.title = '添加'
          this.form = {
            nationalFormulaCode: undefined,//国家医疗机构制剂代码
            isInCategory: '1',//是否在医保制剂目录         1 是 0 否
            formulaName: undefined,//制剂名称
            categoryCode: undefined,//分类编码
            genericNameCode: undefined,//药品通用名编码
            productNameCode: undefined,//产品名编码
            goodsName: undefined,//商品名
            payType: undefined,//支付类别
            approvalNo: undefined,//批准文号
            registerCompanyName: undefined,//制剂注册单位
            dosageForm: undefined,//剂型
            specs: undefined,//规格
            minPriceUnit: undefined,//最小计价单位
            minPackage: undefined,//最小包装
            unit: undefined,//单位
            catalogCode: undefined,//目录编号
            selfPayRatio: undefined,//个人先行自付比例
            remark: undefined,//备注        (元)
            price: undefined,//价格        (元)
            localPmPrice: undefined,//本市同级公立医疗机构价格
            priceCalculate: {//测算
              nonlocalPmPrice: "",//外地同级医院参考价格
              projectTotalCost: ""//项目成本合计
            }
          }
        } else {
          this.title = '编辑'
          this.form = Object.assign({}, row)
          this.form.isInCategory = String(this.form.isInCategory);
        }
        this.form.detail_status = 0;
        this.dialogFormVisible = true
        console.log('看看你的lian',this.form);
      },
      openwin(form) {
        form.index_type = this.form.index_type
        // console.log('openWin',form);
        // console.log('openWin11231233',this.form);
        this.$refs['medicinal'].showDia(form);
        this.$refs['form'].clearValidate();
      },
      checkNationalFormulaCode(form){
        let userinfo = JSON.parse(localStorage.getItem('userinfo'))
        console.log(form.nationalFormulaCode,'nationalFormulaCodess');
        let obj = {isInCategory:'0'
          ,nationalFormulaCode:form.nationalFormulaCode
          ,orgCode:userinfo.org_code
          }
          if(form.nationalFormulaCode.length>0){
            checkSave(obj).then(res =>{
              console.log(res);
            })
          }
      },
      handlePrice() {
        // if (this.form.org_price == undefined || this.form.org_price == null || this.form.org_price == '') {
        //   this.form.high_price = -1
        //   return
        // }
        // if (Number(this.form.price) > this.form.org_price) {
        //   this.form.high_price = 1
        // } else {
        //   this.form.high_price = 0
        // }
      },
      handleChildPrice(childPrice) {
        this.$refs['form'].clearValidate()
        this.form = {
          ...this.form,
          nationalFormulaCode: undefined,//国家医疗机构制剂代码
          formulaName: undefined,//制剂名称
          categoryCode: undefined,//分类编码
          genericNameCode: undefined,//药品通用名编码
          productNameCode: undefined,//产品名编码
          goodsName: undefined,//商品名
          payType: undefined,//支付类别
          approvalNo: undefined,//批准文号
          registerCompanyName: undefined,//制剂注册单位
          dosageForm: undefined,//剂型
          specs: undefined,//规格
          minPriceUnit: undefined,//最小计价单位
          minPackage: undefined,//最小包装
          unit: undefined,//单位
          catalogCode: undefined,//目录编号
          selfPayRatio: undefined,//个人先行自付比例
          remark: undefined,//备注        (元)
          price: undefined,//价格        (元)
          localPmPrice: undefined,//本市同级公立医疗机构价格
          priceCalculate: {//测算
            nonlocalPmPrice: "",//外地同级医院参考价格
            projectTotalCost: ""//项目成本合计
          },
        }
        // this.handlePrice()
      },
      close() {
        this.$refs['form'].resetFields()
        //this.form = this.$options.data().form
        this.dialogFormVisible = false
      },
      fetchData(row) {
      //console.log('fetchData',row,'fetchData');
        let {
          id:categoryId,
          nationalFormulaCode,
          formulaName,
          categoryCode,
          genericNameCode,
          productNameCode,
          goodsName,
          payType,
          approvalNo,
          registerCompanyName,
          dosageForm,
          specs,
          minPriceUnit,
          minPackage,
          unit,
          catalogCode,
          selfPayRatio,
          remark,
          price,
          localPmPrice,
        } = row;
        this.form = {...this.form,nationalFormulaCode,
          categoryId,
          formulaName,
          categoryCode,
          genericNameCode,
          productNameCode,
          goodsName,
          payType,
          approvalNo,
          registerCompanyName,
          dosageForm,
          specs,
          minPriceUnit,
          minPackage,
          unit,
          catalogCode,
          selfPayRatio,
          remark,
          price,
          localPmPrice,}
        // if (this.form.child_price == '0' || this.form.child_price == '1') {
        //   this.handleChildPrice(this.form.child_price)
        // }

        // var code  = this.form.project_code.substring(0,2);
        // for(var i = 0;i<this.options.length;i++){
        //   if(this.options[i].value.replace(/[^0-9]/g,"") == code){
        //   //console.log(this.options[i].value)
        //     this.form.cost_type = this.options[i].value
        //   }
        // }
        this.$refs['form'].resetFields()
      },
      save() {
        if (this.title == '添加') {
          this.form.sign = 'add'
        }
        if (this.title == '编辑') {
          this.form.sign = 'edit'
        }
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            this.$emit('fetch-data', this.form)
            this.close()
          } else {
            return false
          }
        })
      },
    },
  }
</script>
<style lang="scss" scoped>
  ::v-deep {
    .custemitem {
      label {
        line-height: 16px !important;
      }
    }
  }
</style>
