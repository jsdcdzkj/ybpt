<template>
  <el-dialog
      :show-close="false"
      :close-on-click-modal="false"
      :visible.sync="dialogFormVisible"
      top="0"
      width="1000px"
      append-to-body
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="110px">
      <h4 class="inform-title">公立医疗机构医疗服务项目定价测算表</h4>
      <el-row :gutter="20">
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="单位名称：">
            {{form.sbMedicalCalculate.org_code}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="单位医保编码：">
            {{form.sbMedicalCalculate.org_name}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="项目编码：">
            {{form.sbMedicalCalculate.project_code}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item label="项目名称：">
            {{form.sbMedicalCalculate.project_name}}
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item label="本市公立医疗机构价格" prop="sbMedicalCalculate.org_price" class="custemitem">
            {{form.sbMedicalCalculate.org_price}}
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item label="外地同级医院参考价格" prop="sbMedicalCalculate.reference_price" class="custemitem">
            <el-input v-model="form.sbMedicalCalculate.reference_price" type="number" min="0" placeholder="请输入" autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
          <el-form-item label="拟定价" prop="sbMedicalCalculate.protocol_price">
            {{form.sbMedicalCalculate.protocol_price}}
          </el-form-item>
        </el-col>

      </el-row>

      <div class="box_card">
        <h5>一、劳务支出</h5>
        <el-table ref="listTable" :data="form.sbLabour"
                  :element-loading-text="elementLoadingText"
                  highlight-current-row
                  border
        >
          <template slot="empty">
            <el-empty :image-size="200"></el-empty>
          </template>
          <el-table-column show-overflow-tooltip prop="name" label="参加人员" align="center">
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="quantity" label="人数" align="center">
            <template slot-scope="scope">
              <div v-if="!scope.row.isEdit">{{ scope.row.quantity }}</div>
              <div v-else>
                <el-input type="number" min="0" v-model="scope.row.quantity"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="hour" label="工时(小时)" align="center">
            <template slot-scope="scope">
              <div v-if="!scope.row.isEdit">{{ scope.row.hour }}</div>
              <div v-else>
                <el-input type="number" min="0" v-model="scope.row.hour"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="hour_price" label="小时工资、福利额(元)" align="center">
            <template slot-scope="scope">
              <div v-if="!scope.row.isEdit">{{ scope.row.hour_price }}</div>
              <div v-else>
                <el-input type="number" min="0" v-model="scope.row.hour_price"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="amount" label="应计金额（元）" align="center">
            <template slot-scope="scope">
              <div v-if="!scope.row.isEdit">{{ scope.row.amount }}</div>
              <div v-else>
                <el-input type="number" min="0" v-model="scope.row.amount" @input="addPrice()"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="140">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" style="color: #E6A23C;"
                         v-if="scope.row.isEdit==true" size="mini">完成
              </el-button>
              <el-button @click="handleClick(scope.row)" type="text"
                         v-if="scope.row.isEdit==false && scope.row.name != '小计'" size="mini">编辑
              </el-button>
            </template>
          </el-table-column>
        </el-table>

      </div>
      <div class="box_card mt2">
        <h5>二、材料消耗支出</h5>
        <div class="right-add-btn">
          <el-button type="success" icon="el-icon-plus" size="mini" @click="handleAddBtn">添加</el-button>
        </div>

        <el-table ref="tb" :data="form.sbMaterialsConsumption" :header-cell-style="{background:'rgb(113 167 228)',color:'#fff'}"
                  :row-class-name="rowClassName" border style="width: 100%; cursor: pointer;"
                  @selection-change="handleDetailSelectionChange">
          <el-table-column prop="name" align="center" :required="true" label="品名">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.name }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.sbMaterialsConsumption[row.xh-1].name" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="unit" align="center" :required="true" label="单位">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.unit }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.sbMaterialsConsumption[row.xh-1].unit" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="quantity" align="center" :required="true" label="数量">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.quantity }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.sbMaterialsConsumption[row.xh-1].quantity" type="number" min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="price" align="center" :required="true" label="单价（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.price }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.sbMaterialsConsumption[row.xh-1].price" type="number" min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="amount" align="center" :required="true" label="应计金额（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.amount }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.sbMaterialsConsumption[row.xh-1].amount" type="number"
                        min="0" placeholder="请输入" @input="materialPrice()">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column header-align="center" align="center" width="140" label="操作">
            <template slot-scope="{row,$index}">
              <el-button v-if="!showEdit[$index] && row.name != '小计'" type="text" size="small"
                         @click="showUpdate($index,row)">编辑
              </el-button>
              <el-button v-if="showEdit[$index]" type="text" size="small" style="color: #85ce61;"
                         @click="submit($index,row)">确定
              </el-button>
              <!--              <el-button v-if="showEdit[$index]" type="text" size="small" style="color: #E6A23C;"-->
              <!--                         @click="cancelUpdate($index)">取消-->
              <!--              </el-button>-->
              <el-button v-if="row.name != '小计'" type="text" size="mini" style="color: #F56C6C;"
                         @click="handleDeleteBtn($index,row)">删除
              </el-button>

            </template>
          </el-table-column>
        </el-table>

      </div>
      <div class="box_card mt2">
        <h5>三、固定资产折旧</h5>
        <div class="right-add-btn">
          <el-button type="success" icon="el-icon-plus" size="mini" @click="handleAddBtnG">添加</el-button>
        </div>

        <el-table ref="tb2" :data="form.sbFixedAssets"
                  :header-cell-style="{background:'rgb(113 167 228)',color:'#fff'}" :row-class-name="rowClassName"
                  border style="width: 100%; cursor: pointer;" @selection-change="handleDetailSelectionChange">
          <el-table-column prop="name" align="center" :required="true" label="设备名称">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index] || row.name == '小计'">{{ row.name }}</span>
              <el-input v-if="showEditG[$index] && row.name != '小计'" v-model="form.sbFixedAssets[row.xh-1].name" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="price" align="center" :required="true" label="原值（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index]">{{ row.price }}</span>
              <el-input v-if="showEditG[$index]" v-model="form.sbFixedAssets[row.xh-1].price" type="number" min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="year" align="center" :required="true" label="使用年限（年）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index]">{{ row.year }}</span>
              <el-input v-if="showEditG[$index]" v-model="form.sbFixedAssets[row.xh-1].year" type="number" min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="hour" align="center" :required="true" label="使用时间（小时）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index]">{{ row.hour }}</span>
              <el-input v-if="showEditG[$index]" v-model="form.sbFixedAssets[row.xh-1].hour" type="number" min="0"
                        placeholder="请输入" @input="regularPrice()">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="amount" align="center" :required="true" label="应计金额（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index]">{{ row.amount }}</span>
              <el-input v-if="showEditG[$index]" v-model="form.sbFixedAssets[row.xh-1].amount" type="number" min="0"
                        placeholder="请输入" @input="regularPrice()">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column header-align="center" align="center" width="140" label="操作">
            <template slot-scope="{row,$index}">
              <el-button v-if="!showEditG[$index] && row.name != '小计'" type="text" size="mini"
                         @click="showUpdateG($index,row)">编辑
              </el-button>
              <el-button v-if="showEditG[$index]" type="text" size="mini" style="color: #85ce61;"
                         @click="submitG($index,row)">确定
              </el-button>
              <!--              <el-button v-if="showEditG[$index]" type="text" size="mini" style="color: #E6A23C;"-->
              <!--                         @click="cancelUpdateG($index)">取消-->
              <!--              </el-button>-->
              <el-button v-if="row.name != '小计'" type="text" size="mini" style="color: #F56C6C;"
                         @click="handleDeleteBtnG($index,row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

      </div>

      <div class="box_card mt2">
        <h5>四、管理费及其他</h5>
        <el-table ref="listTable4" :data="tableData4"
                  :element-loading-text="elementLoadingText"
                  highlight-current-row
                  border
                  :show-header="false"
        >
          <el-table-column show-overflow-tooltip prop="aaa1" align="center">
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="aaa2" align="center">
            <template slot-scope="scope">
              <div v-if="!scope.row.isEdit">{{ scope.row.aaa2 }}</div>
              <div v-else>
                <el-input type="number" min="0" v-model="scope.row.aaa2" @input="managePrice()"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="140">
            <template slot-scope="scope">
              <el-button @click="handleClick(scope.row)" type="text" style="color: #E6A23C;"
                         v-if="scope.row.isEdit==true" size="mini">完成
              </el-button>
              <el-button @click="handleClick(scope.row)" type="text"
                         v-if="scope.row.isEdit==false && scope.row.aaa1 != '小计'" size="mini">编辑
              </el-button>
            </template>
          </el-table-column>
        </el-table>

      </div>

      <div class="box_card mt2">
        <h5>五、项目成本合计</h5>
        <el-row :gutter="20">
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-form-item label="项目成本合计" prop="sbMedicalCalculate.project_total">
              <el-input v-model="form.sbMedicalCalculate.project_total" type="number" min="0"
                        autocomplete="off" disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
<!--        <div class="total-num">-->
<!--          <p>项目成本合计</p>-->
<!--          <p><span>{{ form.project_total }}</span>(元)</p>-->
<!--        </div>-->

      </div>

      <div class="explain-text">
        <p>说明：</p>
        <p>1.小时工资是指申报医院的平均小时工资（含福利、社保）;</p>
        <p>2.工时是指参与完成医疗服务项目人员的实际用时；</p>
        <p>3.使用年限为折旧年限。</p>
      </div>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">保存</el-button>
    </div>

  </el-dialog>
</template>

<script>
export default {
  name: 'definite',
  components: {},
  data() {
    return {
      elementLoadingText: '正在加载...',
      form: {
        sbLabour: [
          {
            name: '技术员',
            quantity: '',
            hour: '',
            hour_price: '',
            amount: '',
            sort: '',
            isEdit: false
          }, {
            name: '护士',
            quantity: '',
            hour: '',
            hour_price: '',
            amount: '',
            sort: '1',
            isEdit: false
          }, {
            name: '医师',
            quantity: '',
            hour: '',
            hour_price: '',
            amount: '',
            sort: '2',
            isEdit: false
          }, {
            name: '小计',
            quantity: '',
            hour: '',
            hour_price: '',
            amount: '',
            sort: '3',
            isEdit: false
          }
        ],
        sbMaterialsConsumption:[
          {
            name: '',
            unit: '',
            quantity: '',
            price: '',
            amount: '',
          }
        ],
        sbFixedAssets:[
          {
            name: '',
            price: '',
            year: '',
            hour: '',
            amount: '',
          },{
            name: '小计',
            price: '',
            year: '',
            hour: '',
            amount: '',
          }
        ],
        sbMedicalCalculate:{
          org_code: '',
          org_name: '',
          project_code: '',
          project_name: '',
          resource: '',
          org_price: '',
          reference_price: '',
          protocol_price: '',
          project_total: '',
        },
      },
      rules: {
        // 'sbMedicalCalculate.org_price': [{required: true, message: '请填写本市公立医疗机构价格', trigger: 'blur'}],
        'sbMedicalCalculate.protocol_price': [{required: true, message: '请填写拟定价', trigger: 'blur'}],
        'sbMedicalCalculate.project_total': [{required: true, message: '请填写项目成本合计', trigger: 'blur'}],
      },
      title: '',
      dialogFormVisible: false,
      checkedDetail: [],
      showEdit: [],
      showEditG: [],
      tableData4: [{
        aaa1: '（一）管理费分摊',
        aaa2: '',
        isEdit: false
      }, {
        aaa1: '（二）其它',
        aaa2: '',
        isEdit: false
      }, {
        aaa1: '小计',
        aaa2: '',
        isEdit: false
      }],

    }
  },
  created() {
  },
  methods: {
    showDia(row) {
      if(row.sbLabour){
        this.form.sbLabour = row.sbLabour
      }else{
        this.form.sbLabour=[
          {
            name: '技术员',
            quantity: '',
            hour: '',
            hour_price: '',
            amount: '',
            sort: '',
            isEdit: false
          }, {
            name: '护士',
            quantity: '',
            hour: '',
            hour_price: '',
            amount: '',
            sort: '1',
            isEdit: false
          }, {
            name: '医师',
            quantity: '',
            hour: '',
            hour_price: '',
            amount: '',
            sort: '2',
            isEdit: false
          }, {
            name: '小计',
            quantity: '',
            hour: '',
            hour_price: '',
            amount: '',
            sort: '3',
            isEdit: false
          }
        ]
      }
      if(row.sbMaterialsConsumption){
        this.form.sbMaterialsConsumption = row.sbMaterialsConsumption
      }else{
        this.form.sbMaterialsConsumption = [
          {
            name: '',
            unit: '',
            quantity: '',
            price: '',
            amount: '',
          }, {
            name: '小计',
            unit: '',
            quantity: '',
            price: '',
            amount: '',
          }
        ]
      }
      if(row.sbFixedAssets){
        this.form.sbFixedAssets = row.sbFixedAssets
      }else{
        this.form.sbFixedAssets = [
          {
            name: '',
            price: '',
            year: '',
            hour: '',
            amount: '',
          },{
            name: '小计',
            price: '',
            year: '',
            hour: '',
            amount: '',
          }
        ]
      }
      if(row.sbMedicalCalculate){
        this.form.sbMedicalCalculate = row.sbMedicalCalculate
      }else{
        this.form.sbMedicalCalculate = {
          org_code: row.org_code,
          org_name: row.org_name,
          project_code: row.project_code,
          project_name: row.project_name,
          resource: '',
          org_price: row.org_price,
          reference_price: '',
          protocol_price: row.price,
          project_total: '',
        }
      }
      if (row.sbMedicalCalculate && row.sbMedicalCalculate.manager_price) {
        this.tableData4[0].aaa2 = row.sbMedicalCalculate.manager_price
      } else {
        this.tableData4[0].aaa2 = ''
      }
      if (row.sbMedicalCalculate && row.sbMedicalCalculate.other) {
        this.tableData4[1].aaa2 = row.sbMedicalCalculate.other
      } else {
        this.tableData4[1].aaa2 = ''
      }
      if (row.sbMedicalCalculate && row.sbMedicalCalculate.subtotal) {
        this.tableData4[2].aaa2 = row.sbMedicalCalculate.subtotal
      } else {
        this.tableData4[2].aaa2 = ''
      }
      if(row.project_code){
        this.form.index = row.index
        this.form.org_code = row.org_code
        this.form.org_name = row.org_name
        this.form.project_code = row.project_code
        this.form.project_name = row.project_name
        this.form.unit = row.unit
        this.form.declare_type = row.declare_type
        this.form.price = row.price
        this.form.cost_type = row.cost_type
        this.form.org_price = row.org_price
        this.form.departments = row.departments
        this.form.high_price = row.high_price
        this.form.sign = row.sign
      }
      // this.form = Object.assign({}, row)
      this.title = '明细'
      this.dialogFormVisible = true
    },
    handleClick(row) {
      // 动态设置数据并通过这个数据判断显示方式
      if (row.isEdit) {
        if (row.amount == '' ) {
          this.$baseMessage('请填写数据信息', 'error')
        } else if (row.amount != '' ) {
          row.isEdit = false
        } else if (this.tableData4[0].aaa2 == '') {
          this.tableData4[0].isEdit = true
          this.$baseMessage('请填写数据信息', 'error')
        } else if (this.tableData4[0].aaa2 != '') {
          this.tableData4[0].isEdit = false
        } else if (this.tableData4[1].aaa2 == '') {
          this.tableData4[1].isEdit = true
          this.$baseMessage('请填写数据信息', 'error')
        } else {
          this.tableData4[1].isEdit = false
        }
      } else {
        this.$set(row, 'isEdit', true);
      }
    },

    /**
     * 劳务支付小计
     */
    addPrice() {
      this.form.sbLabour[3].amount = this.accAdd(Number(this.form.sbLabour[0].amount), Number(this.form.sbLabour[1].amount), Number(this.form.sbLabour[2].amount), 2)
      this.sumPrice()
    },
    /**
     * 管理费及其他
     */
    managePrice() {
      this.tableData4[2].aaa2 = this.accAdd1(Number(this.tableData4[0].aaa2), Number(this.tableData4[1].aaa2), 2)
      this.sumPrice()
    },
    /**
     * 总计
     */
    sumPrice() {
      this.form.sbMedicalCalculate.project_total = this.accAdd2(Number(this.form.sbLabour[3].amount), Number(this.tableData4[2].aaa2),
          Number(this.form.sbMaterialsConsumption[this.form.sbMaterialsConsumption.length - 1].amount), Number(this.form.sbFixedAssets[this.form.sbFixedAssets.length - 1].amount), 2)
    },
    /**
     ** 加法函数，用来得到精确的加法结果
     **/
    accAdd(arg1, arg2, arg3, num) {//num为保留位数,不传时不四舍五入
      let r1, r2, r3, m
      try {
        r1 = arg1.toString().split('.')[1].length
      } catch (e) {
        r1 = 0
      }
      try {
        r2 = arg2.toString().split('.')[1].length
      } catch (e) {
        r2 = 0
      }
      try {
        r3 = arg3.toString().split('.')[1].length
      } catch (e) {
        r3 = 0
      }
      m = Math.pow(10, Math.max(r1, r2, r3))
      if (num || num === 0) {
        return ((arg1 * m + arg2 * m + arg3 * m) / m).toFixed(num) //注意：fixed四舍五入大于5会进1 5并不会进1
      }
      return Math.round((arg1 * m + arg2 * m + arg3 * m) / m)
    },
    /**
     * 加法函数
     */
    accAdd1(arg1, arg2, num) {//num为保留位数,不传时不四舍五入
      let r1, r2, m
      try {
        r1 = arg1.toString().split('.')[1].length
      } catch (e) {
        r1 = 0
      }
      try {
        r2 = arg2.toString().split('.')[1].length
      } catch (e) {
        r2 = 0
      }
      m = Math.pow(10, Math.max(r1, r2))
      if (num || num === 0) {
        return ((arg1 * m + arg2 * m) / m).toFixed(num) //注意：fixed四舍五入大于5会进1 5并不会进1
      }
      return Math.round((arg1 * m + arg2 * m) / m)
    },

    accAdd2(arg1, arg2, arg3, arg4, num) {
      let r1, r2, r3, r4, m
      try {
        r1 = arg1.toString().split('.')[1].length
      } catch (e) {
        r1 = 0
      }
      try {
        r2 = arg2.toString().split('.')[1].length
      } catch (e) {
        r2 = 0
      }
      try {
        r3 = arg3.toString().split('.')[1].length
      } catch (e) {
        r3 = 0
      }
      try {
        r4 = arg4.toString().split('.')[1].length
      } catch (e) {
        r4 = 0
      }
      m = Math.pow(10, Math.max(r1, r2, r3, r4))
      if (num || num === 0) {
        return ((arg1 * m + arg2 * m + arg3 * m + arg4 * m) / m).toFixed(num) //注意：fixed四舍五入大于5会进1 5并不会进1
      }
      return Math.round((arg1 * m + arg2 * m + arg3 * m + arg4 * m) / m)
    },

    // 表格的新增
    rowClassName({row, rowIndex}) {
      row.xh = rowIndex + 1
    },
    // 单选框选中数据
    handleDetailSelectionChange(selection) {
      this.checkedDetail = selection
    },
    // 点击新增更多
    handleAddBtn() {
      const obj2 = {}
      obj2.name = ''
      obj2.unit = ''
      obj2.quantity = ''
      obj2.price = ''
      obj2.amount = ''
      // this.form.sbMaterialsConsumption.push(obj2)
      this.form.sbMaterialsConsumption.splice(this.form.sbMaterialsConsumption.length - 1, 0, obj2)
    },
    // 删除
    handleDeleteBtn(index, row) {

      this.$confirm('请是否确认删除该属性?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        callback: (action) => {
          if (action === 'confirm') {
            if (this.form.sbMaterialsConsumption > 2) {
              this.form.sbMaterialsConsumption.splice(index, 1);
            } else {
              this.$baseMessage('请至少保留一条数据', 'error')
              return;
            }
            this.$baseMessage('删除成功', 'error')
            return
          } else {
            this.$message({
              message: '已取消删除操作',
              type: 'warning'
            })
            return
          }
        }
      })
    },
    // 点击修改
    showUpdate(index, row) {
      console.log('index')
      this.showEdit[index] = true
      this.$set(this.showEdit, index, true) // 这里要用$set方法，否则页面状态不更新
    },
    // 取消修改
    cancelUpdate(index) {
      this.$confirm('取消修改？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
          .then(() => {
            this.form.sbMaterialsConsumption[index].name = ''
            this.form.sbMaterialsConsumption[index].unit = ''
            this.form.sbMaterialsConsumption[index].quantity = ''
            this.form.sbMaterialsConsumption[index].price = ''
            this.form.sbMaterialsConsumption[index].amount = ''
            this.$set(this.showEdit, index, false)
          })
          .catch(() => {
          })
    },
    // 提交修改
    submit(index, row) {
      if ( this.form.sbMaterialsConsumption[index].amount == '') {
        this.$baseMessage('请填写数据信息', 'error')
        return
      }
      // 发送请求，隐藏输入框
      this.$set(this.showEdit, index, false) // vue添加属性的方法
    },
    /**
     * 材料消耗小计
     */
    materialPrice() {
      this.form.sbMaterialsConsumption[this.form.sbMaterialsConsumption.length - 1].amount = '0'
      var amountCount = 0
      for (var i = 0; i < this.form.sbMaterialsConsumption.length - 1; i++) {
        var amount = this.form.sbMaterialsConsumption[i].amount
        if (amount != '') {
          amountCount = Number(amountCount)
          amount = Number(amount)
          amountCount = this.accAdd1(amount, amountCount, 2)
        }
      }
      if (amountCount != 0) {
        this.form.sbMaterialsConsumption[this.form.sbMaterialsConsumption.length - 1].amount = amountCount
      }
      this.sumPrice()
    },
    // 点击新增更多
    handleAddBtnG() {
      const obj3 = {}
      obj3.name = ''
      obj3.price = ''
      obj3.year = ''
      obj3.hour = ''
      obj3.amount = ''
      // this.form.sbFixedAssets.push(obj3)
      this.form.sbFixedAssets.splice(this.form.sbFixedAssets.length - 1, 0, obj3)
    },
    // 删除
    handleDeleteBtnG(index, row) {

      this.$confirm('请是否确认删除该属性?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
        callback: (action) => {
          if (action === 'confirm') {
            if (this.form.sbFixedAssets.length > 2) {
              this.$baseMessage('请至少保留一条数据', 'error')
              return;
            } else {
              this.form.sbFixedAssets.splice(index, 1);
            }
            this.$baseMessage('删除成功', 'error')
            return
          } else {
            this.$message({
              message: '已取消删除操作',
              type: 'warning'
            })
            return
          }
        }
      })
    },
    // 点击修改
    showUpdateG(index, row) {
      console.log('index')
      this.showEditG[index] = true
      this.$set(this.showEditG, index, true) // 这里要用$set方法，否则页面状态不更新
    },
    // 取消修改
    cancelUpdateG(index) {
      this.$confirm('取消修改？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
          .then(() => {
            if(this.form.sbFixedAssets[index].name != '小计'){
              this.form.sbFixedAssets[index].name = ''
            }
            this.form.sbFixedAssets[index].price = ''
            this.form.sbFixedAssets[index].year = ''
            this.form.sbFixedAssets[index].hour = ''
            this.form.sbFixedAssets[index].amount = ''
            this.$set(this.showEditG, index, false)
          })
          .catch(() => {
          })
    },
    // 提交修改
    submitG(index, row) {
      if ( this.form.sbFixedAssets[index].amount == '') {
        this.$baseMessage('请填写数据信息', 'error')
        return
      }
      // 发送请求，隐藏输入框
      this.$set(this.showEditG, index, false) // vue添加属性的方法
    },
    /**
     * 固定资产折旧小计
     */
    regularPrice() {
      this.form.sbFixedAssets[this.form.sbFixedAssets.length - 1].amount = '0'
      var amountCount = 0
      for (var i = 0; i < this.form.sbFixedAssets.length - 1; i++) {
        var amount = this.form.sbFixedAssets[i].amount
        if (amount != '') {
          amountCount = Number(amountCount)
          amount = Number(amount)
          amountCount = this.accAdd1(amount, amountCount, 2)
        }
      }
      if (amountCount != 0) {
        this.form.sbFixedAssets[this.form.sbFixedAssets.length - 1].amount = amountCount
      }
      this.sumPrice()
    },
    close() {
      this.$confirm('取消后将丢失已填信息！确定要取消表单吗？', {
        confirmButtonText: '是',
        cancelButtonText: '否'
      })
          .then((_) => {
            // this.$refs['form'].resetFields()
            //this.form = this.$options.data().form
            this.dialogFormVisible = false
          })
          .catch((_) => {
          })


    },
    save() {
      this.$refs['form'].validate(async (valid) => {
        if (valid) {
          let sign = false;
          this.form.sbLabour.forEach(x => {
            if (x.isEdit == true || x.amount == '') {
              sign = true
            }
          })
          if (sign) {
            this.$baseMessage('请完善劳务支出', 'error')
            return
          }
          this.form.sbMaterialsConsumption.forEach(x => {
            if (x.isEdit == true || x.amount == '') {
              sign = true
            }
          })
          if (sign) {
            this.$baseMessage('请完善材料消耗支出', 'error')
            return
          }
          this.form.sbFixedAssets.forEach(x => {
            if (x.isEdit == true || x.amount == '') {
              sign = true
            }
          })
          if (sign) {
            this.$baseMessage('请完善固定资产折旧', 'error')
            return
          }
          if (this.tableData4[0].isEdit == true || this.tableData4[0].aaa2 == '') {
            sign = true
          }
          if (this.tableData4[1].isEdit == true || this.tableData4[1].aaa2 == '') {
            sign = true
          }
          if (sign) {
            this.$baseMessage('请完善管理费及其他', 'error')
            return
          }
          this.form.sbMedicalCalculate.manager_price = this.tableData4[0].aaa2
          this.form.sbMedicalCalculate.other = this.tableData4[1].aaa2
          this.form.sbMedicalCalculate.subtotal = this.tableData4[2].aaa2
          this.form.detail_status = 1
          this.form.sign = 'detail'
          this.$emit('fetch-data', this.form)
          this.dialogFormVisible = false
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
  .el-dialog__header {
    padding: 0;
  }

  .inform-title {
    width: 90%;
    margin: -10px auto 20px;
    padding: 0 0 10px;
    line-height: 24px;
    font-size: 18px;
    font-weight: bold;
    text-align: center;
  }

  .custemitem {
    label {
      line-height: 16px !important;
    }
  }

  .box_card {
    h5 {
      font-size: 14px;
      font-weight: bold;
      border-left: 3px solid #1890ff;
      padding-left: 10px;
      color: #666;
      display: flex;
      flex-direction: row;
      margin: 0 0 14px;
    }

    .right-add-btn {
      margin-top: -40px;
      margin-bottom: 6px;
      text-align: right;
    }

  }

  .box_card.mt2 {
    margin-top: 20px;
  }

  .total-num {
    display: flex;
    justify-content: space-between;
    align-items: center;
    font-size: 14px;
    color: #666;
    padding: 10px 16px;
    background-color: #f5f7fa;

    p {
      padding: 0;
      margin: 0;

      span {
        font-size: 20px;
        font-weight: bold;
        color: #1890ff;
        text-decoration: underline;
        margin: 0 6px;
      }
    }
  }

  .explain-text {
    margin-top: 14px;
    padding: 10px 0;
    font-size: 14px;
    color: #999;
    line-height: 20px;

    p {
      padding: 0;
      margin: 0;
    }
  }
}
</style>