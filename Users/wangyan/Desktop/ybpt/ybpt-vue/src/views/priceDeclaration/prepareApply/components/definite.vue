<template>
  <el-dialog
      :show-close="false"
      :close-on-click-modal="false"
      :visible.sync="dialogFormVisible"
      top="0"
      width="1000px"
      append-to-body
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="180px">
      <h4 class="inform-title">医疗机构制剂定价测算表</h4>
      <el-row :gutter="20">
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="org_code" label="单位名称：">
            {{form.orgName}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="org_name" label="单位医保编码：">
            {{form.orgCode}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="nationalFormulaCode" label="国家医疗机构制剂代码：">
            {{form.nationalFormulaCode}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="nationalFormulaCode" label="制剂名称：">
            {{form.formulaName}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="nationalFormulaCode" label="制剂注册单位：">
            {{form.registerCompanyName }}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="approvalNo" label="制剂批准文号：">
            {{form.approvalNo}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="localPmPrice" label="本市公立医疗机构价格：">
            {{form.localPmPrice}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <!--          <el-form-item prop="" label="外地同级医院参考价格：">-->
          <!--            {{form.project_name}}-->
          <!--          </el-form-item>-->

          <el-form-item label="外地同级医院参考价格：" prop="priceCalculate.nonlocalPmPrice" class="">
            <el-input v-model="form.priceCalculate.nonlocalPmPrice" type="number" min="0" placeholder="请输入"
                      autocomplete="off"></el-input>
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="project_name" label="拟定价：">
            {{form.price}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="specs" label="规格：">
            {{form.specs}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="unit" label="计价单位：">
            {{form.unit}}
          </el-form-item>
        </el-col>
        <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
          <el-form-item prop="isInCategory" label="是否在医保制剂项目录：">
            {{['否','是'][form.isInCategory]}}
          </el-form-item>
        </el-col>

      </el-row>

      <div class="box_card">
        <h5>一、劳务支出</h5>
        <div class="right-add-btn">
          <el-button type="success" icon="el-icon-plus" size="mini" @click="handleAddBtnL">添加</el-button>
        </div>
        <el-table ref="listTable" :data="form.laborList"
                  :element-loading-text="elementLoadingText"
                  highlight-current-row
                  border
        >
          <template slot="empty">
            <el-empty :image-size="200"></el-empty>
          </template>
          <el-table-column show-overflow-tooltip prop="participantName" label="参加人员" align="center">
            <template slot-scope="scope">
              <div v-if="!showEditL[scope.$index]">{{ scope.row.isSubtotal==1?'小计':scope.row.participantName }}</div>
              <div v-else>
                <el-input type="text" v-model="scope.row.participantName"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="participantNum" label="人数" align="center">
            <template slot-scope="scope">
              <div v-if="!showEditL[scope.$index]">{{ scope.row.participantNum }}</div>
              <div v-else>
                <el-input type="number" min="0" v-model="scope.row.participantNum"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="workHours" label="工时(小时)" align="center">
            <template slot-scope="scope">
              <div v-if="!showEditL[scope.$index]">{{ scope.row.workHours }}</div>
              <div v-else>
                <el-input type="number" min="0" v-model="scope.row.workHours"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="hourWage" label="小时工资、福利额(元)" align="center">
            <template slot-scope="scope">
              <div v-if="!showEditL[scope.$index]">{{ scope.row.hourWage }}</div>
              <div v-else>
                <el-input type="number" min="0" v-model="scope.row.hourWage"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip prop="payPrice" label="应计金额（元）" align="center">
            <template slot-scope="scope">
              <div v-if="!showEditL[scope.$index]">{{ scope.row.payPrice }}</div>
              <div v-else>
                <el-input type="number" min="0" v-model="scope.row.payPrice" @input="addPrice()"></el-input>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="140">
            <template slot-scope="{row,$index}">
              <!--              <el-button @click="handleClick(scope.row)" type="text" style="color: #E6A23C;"-->
              <!--                         v-if="scope.row.isEdit==true" size="mini">完成-->
              <!--              </el-button>-->
              <!--              <el-button @click="handleClick(scope.row)" type="text"-->
              <!--                         v-if="scope.row.isEdit==false && scope.row.name != '小计'" size="mini">编辑-->
              <!--              </el-button>-->
              <el-button v-if="!showEditL[$index] && row.isSubtotal != '1'" type="text" size="small"
                         @click="showUpdateL($index,row)">编辑
              </el-button>
              <el-button v-if="showEditL[$index]" type="text" size="small" style="color: #85ce61;"
                         @click="submitL($index,row)">确定
              </el-button>
              <!--              <el-button v-if="showEdit[$index]" type="text" size="small" style="color: #E6A23C;"-->
              <!--                         @click="cancelUpdate($index)">取消-->
              <!--              </el-button>-->
              <el-button v-if="row.participantName != '小计'" type="text" size="mini" style="color: #F56C6C;"
                         @click="handleDeleteBtnL($index,row)">删除
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

        <el-table ref="tb" :data="form.materialConsumeList"
                  :header-cell-style="{background:'rgb(113 167 228)',color:'#fff'}"
                  :row-class-name="rowClassName" border style="width: 100%; cursor: pointer;"
                  @selection-change="handleDetailSelectionChange">
          <el-table-column prop="productName" align="center" :required="true" label="品名">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.productName }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.materialConsumeList[row.xh-1].productName"
                        placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="unit" align="center" :required="true" label="单位">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.unit }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.materialConsumeList[row.xh-1].unit" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="productNum" align="center" :required="true" label="数量">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.productNum }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.materialConsumeList[row.xh-1].productNum" type="number"
                        min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="unitPrice" align="center" :required="true" label="单价（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.unitPrice }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.materialConsumeList[row.xh-1].unitPrice" type="number"
                        min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="payPrice" align="center" :required="true" label="应计金额（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEdit[$index]">{{ row.payPrice }}</span>
              <el-input v-if="showEdit[$index]" v-model="form.materialConsumeList[row.xh-1].payPrice" type="number"
                        min="0" placeholder="请输入" @input="materialPrice()">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column header-align="center" align="center" width="140" label="操作">
            <template slot-scope="{row,$index}">
              <el-button v-if="!showEdit[$index] && row.productName != '小计'" type="text" size="small"
                         @click="showUpdate($index,row)">编辑
              </el-button>
              <el-button v-if="showEdit[$index]" type="text" size="small" style="color: #85ce61;"
                         @click="submit($index,row)">确定
              </el-button>
              <!--              <el-button v-if="showEdit[$index]" type="text" size="small" style="color: #E6A23C;"-->
              <!--                         @click="cancelUpdate($index)">取消-->
              <!--              </el-button>-->
              <el-button v-if="row.productName != '小计'" type="text" size="mini" style="color: #F56C6C;"
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

        <el-table ref="tb2" :data="form.fixedAssetsDepreList"
                  :header-cell-style="{background:'rgb(113 167 228)',color:'#fff'}" :row-class-name="rowClassName"
                  border style="width: 100%; cursor: pointer;" @selection-change="handleDetailSelectionChange">
          <el-table-column prop="deviceName" align="center" :required="true" label="设备名称">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index] || row.deviceName == '小计'">{{ row.deviceName }}</span>
              <el-input v-if="showEditG[$index] && row.deviceName != '小计'"
                        v-model="form.fixedAssetsDepreList[row.xh-1].deviceName" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="originalValue" align="center" :required="true" label="原值（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index]">{{ row.originalValue }}</span>
              <el-input v-if="showEditG[$index]" v-model="form.fixedAssetsDepreList[row.xh-1].originalValue"
                        type="number" min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="useLife" align="center" :required="true" label="使用年限（年）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index]">{{ row.useLife }}</span>
              <el-input v-if="showEditG[$index]" v-model="form.fixedAssetsDepreList[row.xh-1].useLife" type="number"
                        min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="useTime" align="center" :required="true" label="使用时间（小时）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index]">{{ row.useTime }}</span>
              <el-input v-if="showEditG[$index]" v-model="form.fixedAssetsDepreList[row.xh-1].useTime" type="number"
                        min="0"
                        placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="payPrice" align="center" :required="true" label="应计金额（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditG[$index]">{{ row.payPrice }}</span>
              <el-input v-if="showEditG[$index]" v-model="form.fixedAssetsDepreList[row.xh-1].payPrice" type="number"
                        min="0"
                        placeholder="请输入" @input="regularPrice()">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column header-align="center" align="center" width="140" label="操作">
            <template slot-scope="{row,$index}">
              <el-button v-if="!showEditG[$index] && row.deviceName != '小计'" type="text" size="mini"
                         @click="showUpdateG($index,row)">编辑
              </el-button>
              <el-button v-if="showEditG[$index]" type="text" size="mini" style="color: #85ce61;"
                         @click="submitG($index,row)">确定
              </el-button>
              <!--              <el-button v-if="showEditG[$index]" type="text" size="mini" style="color: #E6A23C;"-->
              <!--                         @click="cancelUpdateG($index)">取消-->
              <!--              </el-button>-->
              <el-button v-if="row.deviceName != '小计'" type="text" size="mini" style="color: #F56C6C;"
                         @click="handleDeleteBtnG($index,row)">删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>

      </div>
      <div class="box_card mt2">
        <h5>四、检验费</h5>
        <div class="right-add-btn">
          <el-button type="success" icon="el-icon-plus" size="mini" @click="handleAddBtnC">添加</el-button>
        </div>


        <el-table ref="tb3" :data="form.checkFeeList"
                  :header-cell-style="{background:'rgb(113 167 228)',color:'#fff'}"
                  :row-class-name="rowClassName" border style="width: 100%; cursor: pointer;"
                  @selection-change="handleDetailSelectionChange">
          <el-table-column prop="productName" align="center" :required="true" label="品名">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditC[$index]">{{ row.productName }}</span>
              <el-input v-if="showEditC[$index]" v-model="form.checkFeeList[row.xh-1].productName"
                        placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="unit" align="center" :required="true" label="单位">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditC[$index]">{{ row.unit }}</span>
              <el-input v-if="showEditC[$index]" v-model="form.checkFeeList[row.xh-1].unit" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="productNum" align="center" :required="true" label="数量">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditC[$index]">{{ row.productNum }}</span>
              <el-input v-if="showEditC[$index]" v-model="form.checkFeeList[row.xh-1].productNum" type="number"
                        min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="unitPrice" align="center" :required="true" label="单价（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditC[$index]">{{ row.unitPrice }}</span>
              <el-input v-if="showEditC[$index]" v-model="form.checkFeeList[row.xh-1].unitPrice" type="number"
                        min="0" placeholder="请输入">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column prop="payPrice" align="center" :required="true" label="应计金额（元）">
            <template slot-scope="{row,$index}">
              <span v-if="!showEditC[$index]">{{ row.payPrice }}</span>
              <el-input v-if="showEditC[$index]" v-model="form.checkFeeList[row.xh-1].payPrice" type="number"
                        min="0" placeholder="请输入" @input="checkFeePrice()">
              </el-input>
            </template>
          </el-table-column>
          <el-table-column header-align="center" align="center" width="140" label="操作">
            <template slot-scope="{row,$index}">
              <el-button v-if="!showEditC[$index] && row.productName != '小计'" type="text" size="small"
                         @click="showUpdateC($index,row)">编辑
              </el-button>
              <el-button v-if="showEditC[$index]" type="text" size="small" style="color: #85ce61;"
                         @click="submitC($index,row)">确定
              </el-button>
              <!--              <el-button v-if="showEdit[$index]" type="text" size="small" style="color: #E6A23C;"-->
              <!--                         @click="cancelUpdate($index)">取消-->
              <!--              </el-button>-->
              <el-button v-if="row.productName != '小计'" type="text" size="mini" style="color: #F56C6C;"
                         @click="handleDeleteBtnC($index,row)">删除
              </el-button>

            </template>
          </el-table-column>
        </el-table>

      </div>
      <div class="box_card mt2">
        <h5>五、管理费、损耗及其他</h5>
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
        <h5>六、项目成本合计</h5>
        <el-row :gutter="20">
          <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
            <el-form-item prop="" label="项目成本合计" prop="priceCalculate.projectTotalCost">
              <el-input v-model="form.priceCalculate.projectTotalCost" type="number" min="0"
                        autocomplete="off" disabled="disabled"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <!--        <div class="total-num">-->
        <!--          <p>项目成本合计</p>-->
        <!--          <p><span>{{ form.projectTotalCost }}</span>(元)</p>-->
        <!--        </div>-->

      </div>

      <div class="explain-text">
        <p>说明：</p>
        <p>1.小时工资是指申报医院的平均小时工资（含福利、社保）;</p>
        <p>2.工时是指参与完成医院制剂人员的实际用时；</p>
        <p>3.使用年限为折旧年限。</p>
      </div>
<!--      1.小时工资是指申报医院的平均小时工资(含福利、社保)2.工时是指参与完成医院制剂人员的实际用时;-->
<!--      3.使用年限为折旧年限。-->
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
          priceCalculate: {//测算
            nonlocalPmPrice: "",//外地同级医院参考价格
            projectTotalCost: ""//项目成本合计
          },
          laborList: [
            {
              participantName: '',
              participantNum: '',
              workHours: '',
              hourWage: '',
              payPrice: '',
              isEdit: false,
              isSubtotal: "0"

            }, , {
              participantName: '小计',
              participantNum: '',
              workHours: '',
              hourWage: '',
              payPrice: '',
              sort: '3',
              isEdit: false,
              isSubtotal: "1"
            }
          ],
          materialConsumeList: [
            {
              //品名
              productName: "",
              //单位
              unit: "",
              //数量
              productNum: "",
              //单价
              unitPrice: "",
              //应付金额
              payPrice: "",
              //是否是小计数据：0-否，1-是
              isSubtotal: "0",
              isEdit: false,
            },
            {
              //品名
              productName: "小计",
              //单位
              unit: "",
              //数量
              productNum: "",
              //单价
              unitPrice: "",
              //应付金额
              payPrice: "",
              //是否是小计数据：0-否，1-是
              isSubtotal: "1",
              isEdit: false,
            }
          ],
          fixedAssetsDepreList: [
            {
              //设备名称
              deviceName: "",
              //原值
              originalValue: "",
              //使用年限
              useLife: "",
              //使用时间
              useTime: "",
              //应付金额
              payPrice: "",
              isSubtotal: '0',
              isEdit: false,

            }, {
              //设备名称
              deviceName: "小计",
              //原值
              originalValue: "",
              //使用年限
              useLife: "",
              //使用时间
              useTime: "",
              //应付金额
              payPrice: "",
              isSubtotal: '1',
              isEdit: false,

            }
          ],
          //检验费
          checkFeeList: [
            {
              //品名
              productName: "",
              //单位
              unit: "",
              //数量
              productNum: "",
              //单价
              unitPrice: "",
              //应付金额
              payPrice: "",
              //是否是小计数据：0-否，1-是
              isSubtotal: "0",
              isEdit: false,
            },
            {
              //品名
              productName: "小计",
              //单位
              unit: "",
              //数量
              productNum: "",
              //单价
              unitPrice: "",
              //应付金额
              payPrice: "",
              //是否是小计数据：0-否，1-是
              isSubtotal: "1",
              isEdit: false,
            }
          ],
        },
        rules: {
          // 'sbMedicalCalculate.org_price': [{required: true, message: '请填写本市公立医疗机构价格', trigger: 'blur'}],
          // 'priceCalculate.nonlocalPmPrice': [{required: true, message: '请输入外地同级医院参考价格', trigger: 'blur'}],
          'priceCalculate.projectTotalCost': [{required: true, message: '请填写项目成本合计', trigger: 'blur'}],
        },
        title: '',
        dialogFormVisible: false,
        checkedDetail: [],
        showEditL: [],
        showEdit: [],
        showEditG: [],
        showEditC: [],
        tableData4: [{
          aaa1: '（一）管理费分摊',
          aaa2: '',
          isEdit: false
        }, {
          aaa1: '（二）损耗及其它',
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
        this.showEditL = [];
        this.showEdit = [];
        this.showEditG = [];

        if (row) {
          this.form = row
        } else {
          this.form = {
            ...row
          }
        }
        if (row.laborList) {
          this.form.laborList = row.laborList
        } else {
          this.form.laborList = [
            {
              participantName: '',
              participantNum: '',
              workHours: '',
              hourWage: '',
              payPrice: '',
              sort: '',
              isEdit: false,
              isSubtotal: "0"
            }, {
              participantName: '小计',
              participantNum: '',
              workHours: '',
              hourWage: '',
              payPrice: '',
              sort: '3',
              isEdit: false,
              isSubtotal: "1"
            }
          ]
        }
        if (row.materialConsumeList) {
          this.form.materialConsumeList = row.materialConsumeList
        } else {
          this.form.materialConsumeList = [
            {
              productName: '',
              unit: '',
              productNum: '',
              unitPrice: '',
              payPrice: '',
              isEdit: false,
              isSubtotal: 0
            }, {
              productName: '小计',
              unit: '',
              productNum: '',
              unitPrice: '',
              payPrice: '',
              isEdit: false,
              isSubtotal: 1
            }
          ]
        }
        if (row.fixedAssetsDepreList) {
          this.form.fixedAssetsDepreList = row.fixedAssetsDepreList
        } else {
          this.form.fixedAssetsDepreList = [
            {
              deviceName: '',
              originalValue: '',
              useLife: '',
              useTime: '',
              payPrice: '',
              isEdit: false,
              isSubtotal: '0',
            }, {
              deviceName: '小计',
              originalValue: '',
              useLife: '',
              useTime: '',
              payPrice: '',
              isEdit: false,
              isSubtotal: '1',
            }
          ]
        }
        if (row.checkFeeList) {
          this.form.checkFeeList = row.checkFeeList
        } else {
          this.form.checkFeeList = [
            {
              productName: '',
              unit: '',
              productNum: '',
              unitPrice: '',
              payPrice: '',
              isEdit: false,
              isSubtotal: '0',
            }, {
              productName: '小计',
              unit: '',
              productNum: '',
              unitPrice: '',
              payPrice: '',
              isEdit: false,
              isSubtotal: '1',
            }
          ]
        }
        if (row.manageLossOtherFee) {
          this.form.manageLossOtherFee = row.manageLossOtherFee
        } else {
          this.form.manageLossOtherFee = {
            managFee: '',
            lossOtherFee: '',
            subtotal: '',
          }
          this.tableData4 = [{
            aaa1: '（一）管理费分摊',
            aaa2: '',
            isEdit: false
          }, {
            aaa1: '（二）损耗及其它',
            aaa2: '',
            isEdit: false
          }, {
            aaa1: '小计',
            aaa2: '',
            isEdit: false
          }];
        }


        // this.form = Object.assign({}, row)
        console.log(this.form, '什么东西啊');
        this.sumPrice()
        this.title = '明细'
        this.dialogFormVisible = true
      },
      handleClick(row) {
        // 动态设置数据并通过这个数据判断显示方式
        if (row.isEdit) {
          if (row.payPrice == '') {
            this.$baseMessage('请填写数据信息', 'error')
          } else if (row.payPrice != '') {
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
        this.form.laborList[this.form.laborList.length - 1].payPrice = '0'
        var amountCount = 0
        for (var i = 0; i < this.form.laborList.length - 1; i++) {
          var amount = this.form.laborList[i].payPrice
          if (amount != '') {
            amountCount = Number(amountCount)
            amount = Number(amount)
            amountCount = this.accAdd1(amount, amountCount, 3)
          }
        }
        if (amountCount != 0) {
          this.form.laborList[this.form.laborList.length - 1].payPrice = amountCount
        }
        this.sumPrice()
      },
      /**
       * 管理费及其他
       */
      managePrice() {
        this.tableData4[2].aaa2 = this.accAdd1(Number(this.tableData4[0].aaa2), Number(this.tableData4[1].aaa2), 3)
        this.sumPrice()
      },
      /**
       * 总计
       */
      sumPrice() {
        this.form.priceCalculate.projectTotalCost = this.accAdd2(Number(this.form.laborList[this.form.laborList.length - 1].payPrice), Number(this.tableData4[2].aaa2),
          Number(this.form.materialConsumeList[this.form.materialConsumeList.length - 1].payPrice), Number(this.form.fixedAssetsDepreList[this.form.fixedAssetsDepreList.length - 1].payPrice), Number(this.form.checkFeeList[this.form.checkFeeList.length - 1].payPrice), 3)
      },
      /**
       ** 加法函数，用来得到精确的加法结果
       **/
      accAdd(arg1, arg2, num) {//num为保留位数,不传时不四舍五入
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
        m = Math.pow(10, Math.max(r1, r2, r3))
        if (num || num === 0) {
          return ((arg1 * m + arg2 * m) / m).toFixed(num) //注意：fixed四舍五入大于5会进1 5并不会进1
        }
        return Math.round((arg1 * m + arg2 * m) / m)
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

      accAdd2(arg1, arg2, arg3, arg4, arg5, num) {
        let r1, r2, r3, r4, r5, m
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
        try {
          r5 = arg5.toString().split('.')[1].length
        } catch (e) {
          r5 = 0
        }
        m = Math.pow(10, Math.max(r1, r2, r3, r4, r5))
        if (num || num === 0) {
          return ((arg1 * m + arg2 * m + arg3 * m + arg4 * m + arg5 * m) / m).toFixed(num) //注意：fixed四舍五入大于5会进1 5并不会进1
        }
        return Math.round((arg1 * m + arg2 * m + arg3 * m + arg4 * m + arg5 * m) / m)
      },

      // 表格的新增
      rowClassName({row, rowIndex}) {
        row.xh = rowIndex + 1
      },
      // 单选框选中数据
      handleDetailSelectionChange(selection) {
        this.checkedDetail = selection
      },
      handleAddBtnL() {
        const obj4 = {}
        obj4.participantName = ''
        obj4.participantNum = ''
        obj4.workHours = ''
        obj4.hourWage = ''
        obj4.payPrice = ''
        obj4.isEdit = false
        obj4.isSubtotal = '0'
        // this.form.materialConsumeList.push(obj2)
        // console.log(this.form.laborList, '11111111111111111111111');
        this.form.laborList.splice(this.form.laborList.length - 1, 0, obj4)
      },
      // 删除
      handleDeleteBtnL(index, row) {

        this.$confirm('请是否确认删除该属性?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          callback: (action) => {
            if (action === 'confirm') {
              if (this.form.laborList.length > 2) {
                this.form.laborList.splice(index, 1);
                this.showEditL.splice(index, 1);
                this.$nextTick(()=>{
                  this.addPrice();
                })
              } else {
                this.$baseMessage('请至少保留一条数据', 'error')
                return;
              }
              this.$baseMessage('删除成功', 'error');
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
      showUpdateL(index, row) {
        // console.log('index', row)
        this.showEditL[index] = true
        this.$set(this.showEditL, index, true) // 这里要用$set方法，否则页面状态不更新
        // if (row.isEdit) {
        //     if (row.amount == '') {
        //       this.$baseMessage('请填写数据信息', 'error')
        //     } else if (row.amount != '') {
        //       row.isEdit = false
        //     } else if (this.tableData4[0].aaa2 == '') {
        //       this.tableData4[0].isEdit = true
        //       this.$baseMessage('请填写数据信息', 'error')
        //     } else if (this.tableData4[0].aaa2 != '') {
        //       this.tableData4[0].isEdit = false
        //     } else if (this.tableData4[1].aaa2 == '') {
        //       this.tableData4[1].isEdit = true
        //       this.$baseMessage('请填写数据信息', 'error')
        //     } else {
        //       this.tableData4[1].isEdit = false
        //     }
        // } else {
        //   this.$set(this.showEditL, index, true) // 这里要用$set方法，否则页面状态不更新
        //   this.$set(row, 'isEdit', true);
        // }
      },
      // 取消修改
      cancelUpdateL(index) {
        this.$confirm('取消修改？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            this.form.laborList[index].participantName = ''
            this.form.laborList[index].participantNum = ''
            this.form.laborList[index].workHours = ''
            this.form.laborList[index].hourWage = ''
            this.form.laborList[index].payPrice = ''
            this.$set(this.showEdit, index, false)
          })
          .catch(() => {
          })
      },
      // 提交修改
      submitL(index, row) {
        if (this.form.laborList[index].payPrice == '') {
          this.$baseMessage('请填写数据信息', 'error')
          return
        }
        // 发送请求，隐藏输入框
        this.$set(this.showEditL, index, false) // vue添加属性的方法
      },
      // 点击新增更多
      handleAddBtn() {
        const obj2 = {}
        obj2.productName = ''
        obj2.unit = ''
        obj2.productNum = ''
        obj2.unitPrice = ''
        obj2.payPrice = ''
        obj2.isEdit = false;
        obj2.isSubtotal = '0'
        // this.form.materialConsumeList.push(obj2)
        this.form.materialConsumeList.splice(this.form.materialConsumeList.length - 1, 0, obj2)
      },
      // 删除
      handleDeleteBtn(index, row) {

        this.$confirm('请是否确认删除该属性?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          callback: (action) => {
            if (action === 'confirm') {
              if (this.form.materialConsumeList.length > 2) {
                this.form.materialConsumeList.splice(index, 1);
                this.showEdit.splice(index, 1);
                this.$nextTick(()=>{
                  this.materialPrice();
                })
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
        // console.log('index')
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
            this.form.materialConsumeList[index].productName = ''
            this.form.materialConsumeList[index].unit = ''
            this.form.materialConsumeList[index].productNum = ''
            this.form.materialConsumeList[index].unitPrice = ''
            this.form.materialConsumeList[index].payPrice = ''
            this.$set(this.showEdit, index, false)
          })
          .catch(() => {
          })
      },
      // 提交修改
      submit(index, row) {
        if (this.form.materialConsumeList[index].payPrice == '') {
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
        this.form.materialConsumeList[this.form.materialConsumeList.length - 1].payPrice = '0'
        var amountCount = 0
        for (var i = 0; i < this.form.materialConsumeList.length - 1; i++) {
          var amount = this.form.materialConsumeList[i].payPrice
          if (amount != '') {
            amountCount = Number(amountCount)
            amount = Number(amount)
            amountCount = this.accAdd1(amount, amountCount, 3)
          }
        }
        if (amountCount != 0) {
          this.form.materialConsumeList[this.form.materialConsumeList.length - 1].payPrice = amountCount
        }
        this.sumPrice()
      },
      // 点击新增更多
      handleAddBtnG() {
        const obj3 = {}
        obj3.deviceName = ''
        obj3.originalValue = ''
        obj3.useLife = ''
        obj3.useTime = ''
        obj3.payPrice = ''
        obj3.isSubtotal = '0'
        obj3.isEdit = false

        // this.form.fixedAssetsDepreList.push(obj3)
        this.form.fixedAssetsDepreList.splice(this.form.fixedAssetsDepreList.length - 1, 0, obj3)
      },
      // 删除
      handleDeleteBtnG(index, row) {

        this.$confirm('请是否确认删除该属性?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          callback: (action) => {
            if (action === 'confirm') {
              if (this.form.fixedAssetsDepreList.length > 2) {
                this.form.fixedAssetsDepreList.splice(index, 1);
                this.showEditG.splice(index, 1);
                this.$nextTick(()=>{
                  this.regularPrice();
                })
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
      showUpdateG(index, row) {
        // console.log('index')
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
            if (this.form.fixedAssetsDepreList[index].deviceName != '小计') {
              this.form.fixedAssetsDepreList[index].deviceName = ''
            }
            // deviceName
            // originalValue
            // useLife
            // useTime
            // payPrice
            // isSubtotal
            this.form.fixedAssetsDepreList[index].originalValue = ''
            this.form.fixedAssetsDepreList[index].useLife = ''
            this.form.fixedAssetsDepreList[index].useTime = ''
            this.form.fixedAssetsDepreList[index].payPrice = ''
            this.$set(this.showEditG, index, false)
          })
          .catch(() => {
          })
      },
      // 提交修改
      submitG(index, row) {
        if (this.form.fixedAssetsDepreList[index].payPrice == '') {
          this.$baseMessage('请填写数据信息', 'error')
          return
        }
        // 发送请求，隐藏输入框
        this.$set(this.showEditG, index, false) // vue添加属性的方法
      },

      // 点击新增更多
      handleAddBtnC() {
        const obj5 = {}
        obj5.productName = ''
        obj5.unit = ''
        obj5.productNum = ''
        obj5.unitPrice = ''
        obj5.payPrice = ''
        obj5.isSubtotal = '0'
        obj5.isEdit = false
        // this.form.fixedAssetsDepreList.push(obj3)
        this.form.checkFeeList.splice(this.form.checkFeeList.length - 1, 0, obj5)
      },
      // 删除
      handleDeleteBtnC(index, row) {
        this.$confirm('请是否确认删除该属性?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning',
          callback: (action) => {
            if (action === 'confirm') {
              if (this.form.checkFeeList.length > 2) {
                this.form.checkFeeList.splice(index, 1);
                this.showEditC.splice(index, 1);
                this.$nextTick(()=>{
                  this.checkFeePrice();
                })
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
      showUpdateC(index, row) {
        // console.log('index')
        this.showEditC[index] = true
        this.$set(this.showEditC, index, true) // 这里要用$set方法，否则页面状态不更新
      },
      // 取消修改
      cancelUpdateC(index) {
        this.$confirm('取消修改？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        })
          .then(() => {
            if (this.form.checkFeeList[index].deviceName != '小计') {
              this.form.checkFeeList[index].deviceName = ''
            }
            // deviceName
            // originalValue
            // useLife
            // useTime
            // payPrice
            // isSubtotal
            this.form.checkFeeList[index].originalValue = ''
            this.form.checkFeeList[index].useLife = ''
            this.form.checkFeeList[index].useTime = ''
            this.form.checkFeeList[index].payPrice = ''
            this.$set(this.showEditC, index, false)
          })
          .catch(() => {
          })
      },
      // 提交修改
      submitC(index, row) {
        if (this.form.checkFeeList[index].payPrice == '') {
          this.$baseMessage('请填写数据信息', 'error')
          return
        }
        // 发送请求，隐藏输入框
        this.$set(this.showEditC, index, false) // vue添加属性的方法
      },
      /**
       * 固定资产折旧小计
       */
      checkFeePrice() {
        this.form.checkFeeList[this.form.checkFeeList.length - 1].payPrice = '0'
        var amountCount = 0
        for (var i = 0; i < this.form.checkFeeList.length - 1; i++) {
          var amount = this.form.checkFeeList[i].payPrice
          if (amount != '') {
            amountCount = Number(amountCount)
            amount = Number(amount)
            amountCount = this.accAdd1(amount, amountCount, 3)
          }
        }
        if (amountCount != 0) {
          this.form.checkFeeList[this.form.checkFeeList.length - 1].payPrice = amountCount
        }
        this.sumPrice()
      },

      /**
       * 固定资产折旧小计
       */
      regularPrice() {
        this.form.fixedAssetsDepreList[this.form.fixedAssetsDepreList.length - 1].payPrice = '0'
        var amountCount = 0
        for (var i = 0; i < this.form.fixedAssetsDepreList.length - 1; i++) {
          var amount = this.form.fixedAssetsDepreList[i].payPrice
          if (amount != '') {
            amountCount = Number(amountCount)
            amount = Number(amount)
            amountCount = this.accAdd1(amount, amountCount, 3)
          }
        }
        if (amountCount != 0) {
          this.form.fixedAssetsDepreList[this.form.fixedAssetsDepreList.length - 1].payPrice = amountCount
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
            // this.form = this.$options.data().form
            this.dialogFormVisible = false
          })
          .catch((_) => {
          })


      },
      save() {
        this.$refs['form'].validate(async (valid) => {
          if (valid) {
            let sign = false;
            this.form.laborList.forEach(x => {
              if (x.isEdit == true || x.payPrice == '') {
                sign = true
              }
            })
            if (sign) {
              this.$baseMessage('请完善劳务支出', 'error')
              return
            }
            this.form.materialConsumeList.forEach(x => {
              if (x.isEdit == true || x.payPrice == '') {
                sign = true
              }
            })
            if (sign) {
              this.$baseMessage('请完善材料消耗支出', 'error')
              return
            }
            this.form.fixedAssetsDepreList.forEach(x => {
              if (x.isEdit == true || x.payPrice == '') {
                sign = true
              }
            })
            if (sign) {
              this.$baseMessage('请完善固定资产折旧', 'error')
              return
            }
            this.form.checkFeeList.forEach(x => {
              if (x.isEdit == true || x.payPrice == '') {
                sign = true
              }
            })
            if (sign) {
              this.$baseMessage('请完善检验费', 'error')
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
            this.form.manageLossOtherFee.managFee = this.tableData4[0].aaa2
            this.form.manageLossOtherFee.lossOtherFee = this.tableData4[1].aaa2
            this.form.manageLossOtherFee.subtotal = this.tableData4[2].aaa2
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
