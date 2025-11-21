<template>
    <el-dialog :close-on-click-modal="false" :title="title" :visible.sync="dialog" size="80%" width="1000px"
               append-to-body top="2vh">
        <div class="drawer_content">
            <h4 class="h4">徐州市医疗服务项目成本和价格调查表</h4>
            <el-form :model="form" :label-width="formLabelWidth">
                <el-row :gutter="20">
                    <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
                        <el-form-item label="项目名称">
                            {{ form.project_code }}
                        </el-form-item>
                    </el-col>
                    <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
                        <el-form-item label="项目编码">
                            {{ form.project_name }}
                        </el-form-item>
                    </el-col>
                    <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
                        <el-form-item label="预计年开展服务例数" class="custemitem">
                            <el-input
                                    placeholder="请输入内容"
                                    v-model.trim="form.expect"
                                    autocomplete="off"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="12" :sm="12" :md="12" :lg="12" :xl="12">
                        <el-form-item label="实际年开展服务例数" class="custemitem">
                            <el-input
                                    placeholder="请输入内容"
                                    v-model.trim="form.actual"
                                    autocomplete="off"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
                <h5 class="h5">一、业务费用</h5>
                <div class="text-small">
                    <h6 class="h6">1. 内涵一次性耗材消耗（按实际购进价计）</h6>
                    <el-button type="primary" @click="add('tableData')" size="mini" icon="el-icon-circle-plus-outline"
                               plain>新增
                    </el-button>
                </div>
                <el-table :data="tableData" style="width: 100%" :key="mainTableKey" border>
                    <el-table-column fixed prop="aaa" label="品名" min-width="150" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.aaa"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.aaa }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="bbb" label="单位" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.bbb"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.bbb }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ccc" label="单价" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ccc"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ccc }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ddd" label="实际使用数量" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ddd"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ddd }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="eee" label="应摊金额（元）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input type="number" min="0" @input="price('tableData')" v-if="scope.row.isEdit"
                                      v-model="scope.row.eee"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.eee }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="100" align="center">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" v-if="!scope.row.isEdit"
                                       @click="edit(scope.row,'tableData')">编辑
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="saveEdit(scope.row,'tableData')">
                                保存
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="cancel(scope.row, scope.$index, 'tableData')">取消
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <p class="num-text">小计：应摊金额（{{ form.one_cost }}元）</p>
                <div class="text-small">
                    <h6 class="h6">2. 电、水、气等常规消耗</h6>
                    <el-button type="primary" @click="add('tableData2')" size="mini" icon="el-icon-circle-plus-outline"
                               plain>
                        新增
                    </el-button>
                </div>
                <el-table :data="tableData2" style="width: 100%" :key="mainTableKey2" border>
                    <el-table-column fixed prop="aaa" label="名称" min-width="150" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.aaa"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.aaa }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="bbb" label="单位" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.bbb"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.bbb }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ccc" label="单价" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ccc"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ccc }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ddd" label="消耗数量（次）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ddd"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ddd }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="eee" label="应摊金额（元）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input type="number" min="0" @input="price('tableData2')" v-if="scope.row.isEdit"
                                      v-model="scope.row.eee"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.eee }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="100" align="center">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" v-if="!scope.row.isEdit"
                                       @click="edit(scope.row,'tableData2')">
                                编辑
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="saveEdit(scope.row,'tableData2')">
                                保存
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="cancel(scope.row, scope.$index, 'tableData2')">取消
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <p class="num-text">小计：应摊金额（{{ form.conventional_cost }}元）</p>
                <div class="text-small">
                    <h6 class="h6">3. 劳务费用</h6>
                    <el-button type="primary" @click="add('tableData3')" size="mini" icon="el-icon-circle-plus-outline"
                               plain>
                        新增
                    </el-button>
                </div>
                <el-table :data="tableData3" style="width: 100%" :key="mainTableKey3" border>
                    <el-table-column fixed prop="aaa" label="参加人员" min-width="150" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.aaa"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.aaa }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="bbb" label="人数" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.bbb"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.bbb }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ccc" label="工时（小时）" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ccc"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ccc }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ddd" label="平均工资（元/小时）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ddd"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ddd }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="eee" label="应摊金额（元）"
                                     width="160" align="center">
                        <template slot-scope="scope">
                            <el-input type="number" min="0" @input="price('tableData3')" v-if="scope.row.isEdit"
                                      v-model="scope.row.eee"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.eee }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="100" align="center">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" v-if="!scope.row.isEdit"
                                       @click="edit(scope.row,'tableData3')">
                                编辑
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="saveEdit(scope.row,'tableData3')">
                                保存
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="cancel(scope.row, scope.$index, 'tableData3')">取消
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <p class="num-text">小计：应摊金额（{{ form.labor_cost }}元）</p>
                <h5 class="h5">二、固定资产折旧及维护</h5>
                <div class="text-small">
                    <h6 class="h6">1. 仪器设备折旧费</h6>
                    <el-button type="primary" @click="add('tableData4')" size="mini" icon="el-icon-circle-plus-outline"
                               plain>
                        新增
                    </el-button>
                </div>
                <el-table :data="tableData4" style="width: 100%" :key="mainTableKey4" border>
                    <el-table-column fixed prop="aaa" label="资产名称" min-width="150" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.aaa"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.aaa }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="bbb" label="原值（元）" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.bbb"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.bbb }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ccc" label="使用年限" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ccc"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ccc }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ddd" label="折旧费（元）" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ddd"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ddd }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="eee" label="平均每次占用时间（分钟）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.eee"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.eee }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="fff" label="应摊金额（元）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input type="number" min="0" @input="price('tableData4')" v-if="scope.row.isEdit"
                                      v-model="scope.row.fff"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.fff }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="100" align="center">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" v-if="!scope.row.isEdit"
                                       @click="edit(scope.row,'tableData4')">
                                编辑
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="saveEdit(scope.row,'tableData4')">
                                保存
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="cancel(scope.row, scope.$index, 'tableData4')">取消
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <p class="num-text">小计：应摊金额（{{ form.depreciation_cost }}元）</p>
                <div class="text-small">
                    <h6 class="h6">2. 仪器设备维修费</h6>
                    <el-button type="primary" @click="add('tableData5')" size="mini" icon="el-icon-circle-plus-outline"
                               plain>
                        新增
                    </el-button>
                </div>
                <el-table :data="tableData5" style="width: 100%" :key="mainTableKey5" border>
                    <el-table-column fixed prop="aaa" label="资产名称" min-width="150" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.aaa"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.aaa }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="bbb" label="年维修费（元）" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.bbb"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.bbb }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ccc" label="平均每次占用时间（分钟）" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ccc"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ccc }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ddd" label="应摊金额（元）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input type="number" min="0" @input="price('tableData5')" v-if="scope.row.isEdit"
                                      v-model="scope.row.ddd"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ddd }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="100" align="center">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" v-if="!scope.row.isEdit"
                                       @click="edit(scope.row,'tableData5')">
                                编辑
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="saveEdit(scope.row,'tableData5')">
                                保存
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="cancel(scope.row, scope.$index, 'tableData5')">取消
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <p class="num-text">小计：应摊金额（{{ form.maintenance_cost }}元）</p>
                <div class="text-small">
                    <h6 class="h6">3. 专用房屋折旧及维修</h6>
                    <el-button type="primary" @click="add('tableData6')" size="mini" icon="el-icon-circle-plus-outline"
                               plain>
                        新增
                    </el-button>
                </div>
                <el-table :data="tableData6" style="width: 100%" :key="mainTableKey6" border>
                    <el-table-column fixed prop="aaa" label="房屋总造价" min-width="150" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.aaa"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.aaa }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="bbb" label="房屋总面积（m2）" width="110" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.bbb"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.bbb }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ccc" label="使用年限" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ccc"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ccc }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="ddd" label="项目使用面积" width="120" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.ddd"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ddd }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="eee" label="平均每次占用时间（分钟）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.eee"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.eee }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="fff" label="应摊金额（元）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input type="number" min="0" @input="price('tableData6')" v-if="scope.row.isEdit"
                                      v-model="scope.row.fff"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.fff }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="100" align="center">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" v-if="!scope.row.isEdit"
                                       @click="edit(scope.row,'tableData6')">
                                编辑
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="saveEdit(scope.row,'tableData6')">
                                保存
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="cancel(scope.row, scope.$index, 'tableData6')">取消
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <!--                <p class="num-text">小计：应摊金额（{{ form.special_cost }}元）</p>-->
                <div class="text-small" style="margin-top: 10px;">
                    <span>&nbsp;</span>
                    <el-button type="primary" @click="add('tableData7')" size="mini" icon="el-icon-circle-plus-outline"
                               plain>
                        新增
                    </el-button>
                </div>
                <el-table :data="tableData7" style="width: 100%" :key="mainTableKey7" border>
                    <el-table-column fixed prop="aaa" label="房屋大修理费（按原值的年2%计）" min-width="150"
                                     align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.aaa"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.aaa }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="bbb" label="平均每次占用时间（分钟）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input v-if="scope.row.isEdit" v-model="scope.row.bbb"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.bbb }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="fff" label="应摊金额（元）" width="160" align="center">
                        <template slot-scope="scope">
                            <el-input type="number" min="0" @input="price('tableData7')" v-if="scope.row.isEdit"
                                      v-model="scope.row.ccc"
                                      placeholder="请输入内容"></el-input>
                            <span v-else>{{ scope.row.ccc }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column fixed="right" label="操作" width="100" align="center">
                        <template slot-scope="scope">
                            <el-button type="text" size="small" v-if="!scope.row.isEdit"
                                       @click="edit(scope.row,'tableData7')">
                                编辑
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="saveEdit(scope.row,'tableData7')">
                                保存
                            </el-button>
                            <el-button type="text" size="small" v-if="scope.row.isEdit"
                                       @click="cancel(scope.row, scope.$index, 'tableData7')">取消
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <p class="num-text">小计：应摊金额（{{ form.housing_cost }}元）</p>
                <h5 class="h5" style="margin-bottom: 20px;">三、建议调整后价格情况</h5>
                <el-row :gutter="20" style="margin-bottom: 60px;">
                    <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                        <el-form-item label="总成本合计（元）" class="custemitem">
                            <el-input
                                    placeholder="请输入内容"
                                    v-model.trim="form.total_cost"
                                    autocomplete="off"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                        <el-form-item label="调整后价格（元）" class="custemitem">
                            <el-input
                                    placeholder="请输入内容"
                                    v-model.trim="form.adjust_cost"
                                    autocomplete="off"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                    <el-col :xs="8" :sm="8" :md="8" :lg="8" :xl="8">
                        <el-form-item label="外省市价格（元）" class="custemitem">
                            <el-input
                                    placeholder="请输入内容"
                                    v-model.trim="form.provincial_cost"
                                    autocomplete="off"
                            ></el-input>
                        </el-form-item>
                    </el-col>
                </el-row>
            </el-form>
            <div class="drawer_footer">
                <el-button @click="cancelForm">关 闭</el-button>
                <el-button type="primary" @click="save" :loading="loading">
                    {{ loading ? '提交中 ...' : '提 交' }}
                </el-button>
            </div>
        </div>

    </el-dialog>
</template>

<script>

export default {
    name: 'edit',
    components: {},
    data() {
        return {
            title: '',
            dialog: false,
            loading: false,
            form: {
                project_code: '',// 项目名称
                project_name: '',//项目编码
                projectConnotation: '',//项目内涵
                excludedContent: '',//除外内容
                unit: '',//计价单位
                levelOfChargeItem: '',//等级
                explain: '',//项目说明
                calculate: '',//测算成本（元）
                price: '',//建议调整价格（元）
                workload: '',//工作量
                memo: '',//备注
                expect: '',//预计年开展服务例数
                actual: '',//实际年开展服务例数
                one_cost: '',//内涵一次性耗材消耗 小计
                one_cost_list: '',
                conventional_cost: '',//电、水、气等常规消耗 小计
                conventional_cost_list: '',
                labor_cost: '',//劳务费用 小计
                labor_cost_list: '',
                depreciation_cost: '',//仪器设备折旧费 小计
                depreciation_cost_list: '',
                maintenance_cost: '',//仪器设备维修费 小计
                maintenance_cost_list: '',
                special_cost: '',//专用房屋折旧及维修 小计
                special_cost_list: '',
                housing_cost: '',// 房屋大修理费 小计
                housing_cost_list: '',
                total_cost: '',//总成本合计（元）
                adjust_cost: '',//调整后价格（元）
                provincial_cost: '',//外省市价格（元）
            },
            rules: {},
            formLabelWidth: '100px',
            timer: null,
            tableData: [],
            tableData2: [],
            tableData3: [],
            tableData4: [],
            tableData5: [],
            tableData6: [],
            tableData7: [],
            // table绑定key，更新key可以重新渲染table
            mainTableKey: 1,
            mainTableKey2: 2,
            mainTableKey3: 3,
            mainTableKey4: 4,
            mainTableKey5: 5,
            mainTableKey6: 6,
            mainTableKey7: 7,

        }
    },
    mounted() {
        // 第1个表格数据
        let da = [{
            aaa: '',
            bbb: '',
            ccc: '',
            ddd: '',
            eee: '',
        }]
        // 给数据添加标志
        da.forEach(row => {
            // 是否标记
            row['isEdit'] = false
            // 是否新增
            row['isAdd'] = false
        })
        this.tableData = da

        // 第2个表格数据
        let da2 = [{
            aaa: '',
            bbb: '',
            ccc: '',
            ddd: '',
            eee: '',
        }]
        // 给数据添加标志
        da2.forEach(row => {
            // 是否标记
            row['isEdit'] = false
            // 是否新增
            row['isAdd'] = false
        })
        this.tableData2 = da2

        // 第3个表格数据
        let da3 = [{
            aaa: '',
            bbb: '',
            ccc: '',
            ddd: '',
            eee: '',
        }]
        // 给数据添加标志
        da3.forEach(row => {
            // 是否标记
            row['isEdit'] = false
            // 是否新增
            row['isAdd'] = false
        })
        this.tableData3 = da3

        // 第4个表格数据
        let da4 = [{
            aaa: '',
            bbb: '',
            ccc: '',
            ddd: '',
            eee: '',
            fff: '',
        }]
        // 给数据添加标志
        da4.forEach(row => {
            // 是否标记
            row['isEdit'] = false
            // 是否新增
            row['isAdd'] = false
        })
        this.tableData4 = da4

        // 第5个表格数据
        let da5 = [{
            aaa: '',
            bbb: '',
            ccc: '',
            ddd: '',
        }]
        // 给数据添加标志
        da5.forEach(row => {
            // 是否标记
            row['isEdit'] = false
            // 是否新增
            row['isAdd'] = false
        })
        this.tableData5 = da5

        // 第6个表格数据
        let da6 = [{
            aaa: '',
            bbb: '',
            ccc: '',
            ddd: '',
            eee: '',
            fff: '',
        }]
        // 给数据添加标志
        da6.forEach(row => {
            // 是否标记
            row['isEdit'] = false
            // 是否新增
            row['isAdd'] = false
        })
        this.tableData6 = da6

        // 第7个表格数据
        let da7 = [{
            aaa: '',
            bbb: '',
            ccc: '',
        }]
        // 给数据添加标志
        da7.forEach(row => {
            // 是否标记
            row['isEdit'] = false
            // 是否新增
            row['isAdd'] = false
        })
        this.tableData7 = da7

    },
    methods: {
        showDia(index, row) {
            this.title = '成本/价格'
            this.form = Object.assign({}, row)
            this.form.index = index;
            //子表赋值
            if (this.form.one_cost_list) {
                this.tableData = JSON.parse(this.form.one_cost_list);
            } else {
                this.tableData = [];
            }
            if (this.form.conventional_cost_list) {
                this.tableData2 = JSON.parse(this.form.conventional_cost_list);
            } else {
                this.tableData2 = [];
            }
            if (this.form.labor_cost_list) {
                this.tableData3 = JSON.parse(this.form.labor_cost_list);
            } else {
                this.tableData3 = [];
            }
            if (this.form.depreciation_cost_list) {
                this.tableData4 = JSON.parse(this.form.depreciation_cost_list);
            } else {
                this.tableData4 = [];
            }
            if (this.form.maintenance_cost_list) {
                this.tableData5 = JSON.parse(this.form.maintenance_cost_list);
            } else {
                this.tableData5 = [];
            }
            if (this.form.special_cost_list) {
                this.tableData6 = JSON.parse(this.form.special_cost_list);
            } else {
                this.tableData6 = [];
            }
            if (this.form.housing_cost_list) {
                this.tableData7 = JSON.parse(this.form.housing_cost_list);
            } else {
                this.tableData7 = [];
            }
            this.dialog = true
        },
        // 新增
        add(type) {
            switch (type) {
                case "tableData":
                    this.tableData.push({
                        aaa: '',
                        bbb: '',
                        ccc: '',
                        ddd: '',
                        eee: '',
                        isEdit: true,
                        isAdd: true
                    })
                    break
                case "tableData2":
                    this.tableData2.push({
                        aaa: '',
                        bbb: '',
                        ccc: '',
                        ddd: '',
                        eee: '',
                        isEdit: true,
                        isAdd: true
                    })
                    break
                case "tableData3":
                    this.tableData3.push({
                        aaa: '',
                        bbb: '',
                        ccc: '',
                        ddd: '',
                        eee: '',
                        isEdit: true,
                        isAdd: true
                    })
                    break
                case "tableData4":
                    this.tableData4.push({
                        aaa: '',
                        bbb: '',
                        ccc: '',
                        ddd: '',
                        eee: '',
                        fff: '',
                        isEdit: true,
                        isAdd: true
                    })
                    break
                case "tableData5":
                    this.tableData5.push({
                        aaa: '',
                        bbb: '',
                        ccc: '',
                        ddd: '',
                        isEdit: true,
                        isAdd: true
                    })
                    break
                case "tableData6":
                    this.tableData6.push({
                        aaa: '',
                        bbb: '',
                        ccc: '',
                        ddd: '',
                        eee: '',
                        fff: '',
                        isEdit: true,
                        isAdd: true
                    })
                    break
                case "tableData7":
                    this.tableData7.push({
                        aaa: '',
                        bbb: '',
                        ccc: '',
                        isEdit: true,
                        isAdd: true
                    })
                    break
            }

        },
        // 编辑
        edit(row, type) {
            row['oldRow'] = JSON.parse(JSON.stringify(row))
            row.isEdit = true
            switch (type) {
                case "tableData":
                    this.mainTableKey = Math.random()
                    break
                case "tableData2":
                    this.mainTableKey2 = Math.random()
                    break
                case "tableData3":
                    this.mainTableKey3 = Math.random()
                    break
                case "tableData4":
                    this.mainTableKey4 = Math.random()
                    break
                case "tableData5":
                    this.mainTableKey5 = Math.random()
                    break
                case "tableData6":
                    this.mainTableKey6 = Math.random()
                    break
                case "tableData7":
                    this.mainTableKey7 = Math.random()
                    break
            }
        },
        // 取消
        cancel(row, index, type) {
            // 如果是新增的数据
            if (row.isAdd) {
                switch (type) {
                    case "tableData":
                        this.tableData.splice(index, 1)
                        break
                    case "tableData2":
                        this.tableData2.splice(index, 1)
                        break
                    case "tableData3":
                        this.tableData3.splice(index, 1)
                        break
                    case "tableData4":
                        this.tableData4.splice(index, 1)
                        break
                    case "tableData5":
                        this.tableData5.splice(index, 1)
                        break
                    case "tableData6":
                        this.tableData6.splice(index, 1)
                        break
                    case "tableData7":
                        this.tableData7.splice(index, 1)
                        break
                }
            } else {
                // 不是新增的数据  还原数据
                for (const i in row.oldRow) {
                    row[i] = row.oldRow[i]
                }
            }

            switch (type) {
                case "tableData":
                    this.mainTableKey = Math.random()
                    break
                case "tableData2":
                    this.mainTableKey2 = Math.random()
                    break
                case "tableData3":
                    this.mainTableKey3 = Math.random()
                    break
                case "tableData4":
                    this.mainTableKey4 = Math.random()
                    break
                case "tableData5":
                    this.mainTableKey5 = Math.random()
                    break
                case "tableData6":
                    this.mainTableKey6 = Math.random()
                    break
                case "tableData7":
                    this.mainTableKey7 = Math.random()
                    break
            }
            this.price(type)
        },
        // 保存
        saveEdit(row, type) {
            row.isEdit = true
            switch (type) {
                case "tableData":
                    if (row.aaa == '') {
                        this.$baseMessage('请完善内涵一次性耗材消耗（按实际购进价计）【品名】', 'error')
                        return
                    }
                    if (row.eee == '') {
                        this.$baseMessage('请完善内涵一次性耗材消耗（按实际购进价计）【应摊金额】', 'error')
                        return
                    }
                    break
                case "tableData2":
                    if (row.aaa == '') {
                        this.$baseMessage('请完善写电、水、气等常规消耗【名称】', 'error')
                        return
                    }
                    if (row.eee == '') {
                        this.$baseMessage('请完善写电、水、气等常规消耗【应摊金额】', 'error')
                        return
                    }
                    break
                case "tableData3":
                    if (row.aaa == '') {
                        this.$baseMessage('请完善劳务费用【参加人员】', 'error')
                        return
                    }
                    if (row.eee == '') {
                        this.$baseMessage('请完善劳务费用【应摊金额】', 'error')
                        return
                    }
                    break
                case "tableData4":
                    if (row.aaa == '') {
                        this.$baseMessage('请完善仪器设备折旧费【资产名称】', 'error')
                        return
                    }
                    if (row.fff == '') {
                        this.$baseMessage('请完善仪器设备折旧费【应摊金额】', 'error')
                        return
                    }
                    break
                case "tableData5":
                    if (row.aaa == '') {
                        this.$baseMessage('请完善仪器设备维修费【资产名称】', 'error')
                        return
                    }
                    if (row.ddd == '') {
                        this.$baseMessage('请完善仪器设备维修费【应摊金额】', 'error')
                        return
                    }
                    break
                case "tableData6":
                    if (row.aaa == '') {
                        this.$baseMessage('请完善专用房屋折旧及维修【房屋总造价】', 'error')
                        return
                    }
                    if (row.fff == '') {
                        this.$baseMessage('请完善专用房屋折旧及维修【应摊金额】', 'error')
                        return
                    }
                    break
                case "tableData7":
                    if (row.aaa == '' || row.ccc == '') {
                        this.$baseMessage('请完善专用房屋折旧及维修【房屋大修理费-应摊金额】', 'error')
                        return
                    }
                    break
            }
            row.isEdit = false

        },
        // 输入应摊金额 计算小计
        price(type) {
            let amount;
            let amountCount = 0;
            switch (type) {
                case "tableData":
                    for (let i = 0; i < this.tableData.length; i++) {
                        amount = this.tableData[i].eee;
                        if (amount != '') {
                            amountCount = Number(amountCount)
                            amount = Number(amount)
                            amountCount = this.accAdd(2, amount, amountCount)
                        }
                    }
                    if (amountCount != 0) {
                        this.$set(this.form, 'one_cost', amountCount)
                    } else {
                        this.$set(this.form, 'one_cost', '')
                    }
                    break
                case "tableData2":
                    for (let i = 0; i < this.tableData2.length; i++) {
                        amount = this.tableData2[i].eee;
                        if (amount != '') {
                            amountCount = Number(amountCount)
                            amount = Number(amount)
                            amountCount = this.accAdd(2, amount, amountCount)
                        }
                    }
                    if (amountCount != 0) {
                        this.$set(this.form, 'conventional_cost', amountCount)
                    } else {
                        this.$set(this.form, 'conventional_cost', '')
                    }
                    break
                case "tableData3":
                    for (let i = 0; i < this.tableData3.length; i++) {
                        amount = this.tableData3[i].eee;
                        if (amount != '') {
                            amountCount = Number(amountCount)
                            amount = Number(amount)
                            amountCount = this.accAdd(2, amount, amountCount)
                        }
                    }
                    if (amountCount != 0) {
                        this.$set(this.form, 'labor_cost', amountCount)
                    } else {
                        this.$set(this.form, 'labor_cost', '')
                    }
                    break
                case "tableData4":
                    for (let i = 0; i < this.tableData4.length; i++) {
                        amount = this.tableData4[i].fff;
                        if (amount != '') {
                            amountCount = Number(amountCount)
                            amount = Number(amount)
                            amountCount = this.accAdd(2, amount, amountCount)
                        }
                    }
                    if (amountCount != 0) {
                        this.$set(this.form, 'depreciation_cost', amountCount)
                    } else {
                        this.$set(this.form, 'depreciation_cost', '')
                    }
                    break
                case "tableData5":
                    for (let i = 0; i < this.tableData5.length; i++) {
                        amount = this.tableData5[i].ddd;
                        if (amount != '') {
                            amountCount = Number(amountCount)
                            amount = Number(amount)
                            amountCount = this.accAdd(2, amount, amountCount)
                        }
                    }
                    if (amountCount != 0) {
                        this.$set(this.form, 'maintenance_cost', amountCount)
                    } else {
                        this.$set(this.form, 'maintenance_cost', '')
                    }
                    break
                case "tableData6":
                case "tableData7":
                    for (let i = 0; i < this.tableData6.length; i++) {
                        amount = this.tableData6[i].fff;
                        if (amount != '') {
                            amountCount = Number(amountCount)
                            amount = Number(amount)
                            amountCount = this.accAdd(2, amount, amountCount)
                        }
                    }
                    let amountCount2 = 0;
                    for (let i = 0; i < this.tableData7.length; i++) {
                        amount = this.tableData7[i].ccc;
                        if (amount != '') {
                            amountCount2 = Number(amountCount2)
                            amount = Number(amount)
                            amountCount2 = this.accAdd(2, amount, amountCount2)
                        }
                    }
                    let amountCountSum = this.accAdd(2, amountCount, amountCount2);
                    if (amountCountSum != 0) {
                        this.$set(this.form, 'housing_cost', amountCountSum)
                    } else {
                        this.$set(this.form, 'housing_cost', '')
                    }
                    break
                // case "tableData6":
                //     for (let i = 0; i < this.tableData6.length; i++) {
                //         amount = this.tableData6[i].fff;
                //         if (amount != '') {
                //             amountCount = Number(amountCount)
                //             amount = Number(amount)
                //             amountCount = this.accAdd(2, amount, amountCount)
                //         }
                //     }
                //     if (amountCount != 0) {
                //         this.$set(this.form, 'special_cost', amountCount)
                //     } else {
                //         this.$set(this.form, 'special_cost', '')
                //     }
                //     break
                // case "tableData7":
                //     for (let i = 0; i < this.tableData7.length; i++) {
                //         amount = this.tableData7[i].ccc;
                //         if (amount != '') {
                //             amountCount = Number(amountCount)
                //             amount = Number(amount)
                //             amountCount = this.accAdd(2, amount, amountCount)
                //         }
                //     }
                //     if (amountCount != 0) {
                //         this.$set(this.form, 'housing_cost', amountCount)
                //     } else {
                //         this.$set(this.form, 'housing_cost', '')
                //     }
                //     break
            }
        },
        //关闭
        cancelForm() {
            this.loading = false
            this.dialog = false
            clearTimeout(this.timer)
        },
        // 提交
        save() {
            var that = this;
            let sign = false;
            this.tableData.forEach(x => {
                if (x.aaa == '' || x.eee == '') {
                    sign = true
                }
            })
            if (sign || this.tableData.length == 0) {
                that.$baseMessage('请完善内涵一次性耗材消耗', 'error')
                return
            }
            this.tableData2.forEach(x => {
                if (x.aaa == '' || x.eee == '') {
                    sign = true
                }
            })
            if (sign || this.tableData2.length == 0) {
                that.$baseMessage('请完善电、水、气等常规消耗', 'error')
                return
            }
            this.tableData3.forEach(x => {
                if (x.aaa == '' || x.eee == '') {
                    sign = true
                }
            })
            if (sign || this.tableData3.length == 0) {
                that.$baseMessage('请完善劳务费用', 'error')
                return
            }
            that.form.sign = 'detail';
            that.form.detail_status = 1;
            that.form.one_cost_list = JSON.stringify(that.tableData);
            that.form.conventional_cost_list = JSON.stringify(that.tableData2);
            that.form.labor_cost_list = JSON.stringify(that.tableData3);
            that.form.depreciation_cost_list = JSON.stringify(that.tableData4);
            that.form.maintenance_cost_list = JSON.stringify(that.tableData5);
            that.form.special_cost_list = JSON.stringify(that.tableData6);
            that.form.housing_cost_list = JSON.stringify(that.tableData7);
            that.$emit('fetch-data', this.form)
            that.cancelForm()
            // this.$refs['form'].validate(async (valid) => {
            //     if (valid) {
            // let sign = false;
            // this.form.sbLabour.forEach(x => {
            //     if (x.isEdit == true || x.amount == '') {
            //         sign = true
            //     }
            // })
            // if (sign) {
            //     this.$baseMessage('请完善劳务支出', 'error')
            //     return
            // }
            // this.form.sbMaterialsConsumption.forEach(x => {
            //     if (x.isEdit == true || x.amount == '') {
            //         sign = true
            //     }
            // })
            // if (sign) {
            //     this.$baseMessage('请完善材料消耗支出', 'error')
            //     return
            // }
            // this.form.sbFixedAssets.forEach(x => {
            //     if (x.isEdit == true || x.amount == '') {
            //         sign = true
            //     }
            // })
            // if (sign) {
            //     this.$baseMessage('请完善固定资产折旧', 'error')
            //     return
            // }
            // if (this.tableData4[0].isEdit == true || this.tableData4[0].aaa2 == '') {
            //     sign = true
            // }
            // if (this.tableData4[1].isEdit == true || this.tableData4[1].aaa2 == '') {
            //     sign = true
            // }
            // if (sign) {
            //     this.$baseMessage('请完善管理费及其他', 'error')
            //     return
            // }
            // this.form.sbMedicalCalculate.manager_price = this.tableData4[0].aaa2
            // this.form.sbMedicalCalculate.other = this.tableData4[1].aaa2
            // this.form.sbMedicalCalculate.subtotal = this.tableData4[2].aaa2
            // this.form.detail_status = 1

            // } else {
            //     return false
            // }
            // })
        },

        /**
         ** 加法函数，用来得到精确的加法结果
         **/
        accAdd(num, arg1, arg2) {
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
    },
}
</script>
<style scoped lang="scss">
.drawer_content {
  // height: calc(100vh - 5vh - 140px);
  overflow-y: auto;
}

.h4 {
  font-size: 24px;
  text-align: center;
  margin: 0 auto 40px;
  padding: 0;
  letter-spacing: 1px;
}

.h5 {
  font-size: 18px;
  font-weight: normal;
  text-align: center;
  margin: 6px auto 10px;
  padding: 0;
  letter-spacing: 1px;
  color: #333;
}

.text-small {
  margin-bottom: 8px;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.num-text {
  text-align: right;
  color: #5e768c;
  margin: 6px 0 14px;
}

.h6 {
  font-size: 16px;
  font-weight: normal;
  text-align: left;
  margin: 0;
  padding: 0;
  letter-spacing: 1px;
  color: #333;

  &::before {
    content: "";
    display: inline-block;
    width: 4px;
    height: 14px;
    background-color: #1890ff;
    margin-right: 6px;
    margin-top: -4px;
    vertical-align: middle;
  }
}
</style>