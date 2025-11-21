<template>
  <el-drawer
    :title="title"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="1400px"
    ref="drawer"
    :wrapperClosable="false"
  >
    <div class="drawer_content">
      <el-form :model="form" label-width="110px" :rules="rules" ref="form">
        <div class="drawer_main">
          <div class="box_card">
            <div class="box_header">
              <span>基本信息</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="机构名称">
                    <el-input v-model.trim="form.org_name" disabled></el-input>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医保编码">
                    <el-input
                      v-model.trim="form.medical_code"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="机构联系人">
                    <el-input
                      v-model.trim="!form.contacts ? '无' : form.contacts"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="区域">
                    <el-select
                      disabled
                      v-model="form.area"
                      style="width: 100%"
                      clearable
                    >
                      <el-option
                        v-for="item in admdvs"
                        :key="item.value"
                        :label="item.label"
                        :value="item.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>

                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="联系电话">
                    <el-input
                      v-model.trim="
                        !form.contact_number ? '无' : form.contact_number
                      "
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="机构等级">
                    <el-select
                      v-model="form.cred_lv"
                      placeholder="请选择"
                      style="width: 100%"
                      disabled
                    >
                      <el-option
                        v-for="item in nationalList"
                        :key="item.id"
                        :label="item.label"
                        :value="item.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="所在街道">
                    <el-input
                      v-model.trim="form.address"
                      clearable
                      placeholder="请输入"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="运营商" prop="operator">
                    <el-select
                      v-model="form.operator"
                      placeholder="请选择"
                      style="width: 100%"
                      clearable
                    >
                      <el-option
                        v-for="item in levelList"
                        :key="item.id"
                        :label="item.label"
                        :value="item.value"
                      ></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>拓扑情况</span>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="是否专网专线">
                    <el-switch
                      v-model="form.is_network_line"
                      :active-value="'1'"
                      :inactive-value="'0'"
                      active-color="#13ce66"
                      inactive-color="#dcdfe6"
                    ></el-switch>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="存在哪些专线">
                    <el-input
                      v-model.trim="form.dedicated_line"
                      placeholder="请输入"
                      class="input-with-select"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="访问的服务">
                    <el-input
                      v-model.trim="form.services_accessed"
                      placeholder="请输入"
                      class="input-with-select"
                    ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <!-- 用户信息 -->
          <div class="box_card">
            <div class="box_header">
              <span>用户信息</span>
            </div>
          </div>
          <div class="sub-header">
            <p class="tips-nums">需开通账号的数量：{{ tableList.length }}</p>
            <el-button
              type="primary"
              class="right"
              size="mini"
              icon="el-icon-plus"
              @click="addLevel"
            >
              新增
            </el-button>
          </div>
          <el-table :data="tableList" border style="margin-top: 8px">
            <!-- 各列定义保持不变 -->
            <el-table-column
              type="index"
              label="序号"
              align="center"
              width="50"
            ></el-table-column>
            <el-table-column label="姓名" width="180" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.name"
                  placeholder="请输入姓名"
                ></el-input>
                <span v-else>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column label="手机号" width="150" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.phone_number"
                  placeholder="请输入手机号"
                ></el-input>
                <span v-else>{{ scope.row.phone_number }}</span>
              </template>
            </el-table-column>
            <el-table-column label="身份证号" width="200" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.id_number"
                  placeholder="请输入身份证号"
                ></el-input>
                <span v-else>{{ scope.row.id_number }}</span>
              </template>
            </el-table-column>
            <el-table-column label="备注" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.remarks"
                  placeholder="请输入备注"
                ></el-input>
                <span v-else>{{ scope.row.remarks }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150" align="center">
              <template slot-scope="scope">
                <el-button
                  v-if="scope.row.isNew"
                  type="primary"
                  size="mini"
                  @click="saveRow(scope.$index)"
                >
                  保存
                </el-button>
                <el-button
                  v-if="scope.row.isNew"
                  type="danger"
                  size="mini"
                  @click="cancelRow(scope.$index)"
                >
                  取消
                </el-button>
                <el-button
                  v-if="!scope.row.isNew"
                  type="primary"
                  size="mini"
                  @click="editRow(scope.$index)"
                >
                  编辑
                </el-button>
                <el-button
                  v-if="!scope.row.isNew"
                  type="danger"
                  size="mini"
                  @click="deleteRow(scope.$index)"
                >
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 终端情况 -->
          <div class="box_card mt2">
            <div class="box_header">
              <span>终端情况</span>
            </div>
          </div>
          <div class="sub-header">
            <p class="tips-nums">
              访问医保网的终端数量：{{ tableList2.length }}
            </p>
            <el-button
              type="primary"
              class="right"
              size="mini"
              icon="el-icon-plus"
              @click="addLevel2"
            >
              新增
            </el-button>
          </div>
          <el-table
            :data="tableList2"
            border
            style="margin-top: 8px"
            min-height="200px"
          >
            <!-- 各列定义保持不变 -->
            <el-table-column
              type="index"
              label="序号"
              align="center"
              width="50"
            ></el-table-column>
            <el-table-column label="设备名称" width="180" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.device_name"
                  placeholder="请输入设备名称"
                ></el-input>
                <span v-else>{{ scope.row.device_name }}</span>
              </template>
            </el-table-column>
            <el-table-column label="IP地址" width="150" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.id_address"
                  placeholder="请输入IP地址"
                ></el-input>
                <span v-else>{{ scope.row.id_address }}</span>
              </template>
            </el-table-column>
            <el-table-column label="MAC地址" width="200" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.mac_address"
                  placeholder="请输入MAC地址"
                ></el-input>
                <span v-else>{{ scope.row.mac_address }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作系统" width="200" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.operating_system"
                  placeholder="请输入操作系统"
                ></el-input>
                <span v-else>{{ scope.row.operating_system }}</span>
              </template>
            </el-table-column>
            <el-table-column label="运行业务" width="200" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.business"
                  placeholder="请输入运行业务"
                ></el-input>
                <span v-else>{{ scope.row.business }}</span>
              </template>
            </el-table-column>
            <el-table-column label="备注" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.remarks"
                  placeholder="请输入备注"
                ></el-input>
                <span v-else>{{ scope.row.remarks }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150" align="center">
              <template slot-scope="scope">
                <el-button
                  v-if="scope.row.isNew"
                  type="primary"
                  size="mini"
                  @click="saveRow2(scope.$index)"
                >
                  保存
                </el-button>
                <el-button
                  v-if="scope.row.isNew"
                  type="danger"
                  size="mini"
                  @click="cancelRow2(scope.$index)"
                >
                  取消
                </el-button>
                <el-button
                  v-if="!scope.row.isNew"
                  type="primary"
                  size="mini"
                  @click="editRow2(scope.$index)"
                >
                  编辑
                </el-button>
                <el-button
                  v-if="!scope.row.isNew"
                  type="danger"
                  size="mini"
                  @click="deleteRow2(scope.$index)"
                >
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 终端情况 -- 第二个 -->
          <div class="sub-header">
            <p class="tips-nums">
              同时访问医保网与其他网络的终端数量：{{ tableList2.length }}
            </p>

            <el-button
              type="primary"
              class="right"
              size="mini"
              icon="el-icon-plus"
              @click="addLevel3"
            >
              新增
            </el-button>
          </div>
          <el-table
            :data="tableList3"
            border
            style="margin-top: 8px; margin-bottom: 18px"
            min-height="200px"
          >
            <!-- 各列定义保持不变 -->
            <el-table-column
              type="index"
              label="序号"
              align="center"
              width="50"
            ></el-table-column>
            <el-table-column label="设备名称" width="180" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.device_name"
                  placeholder="请输入设备名称"
                ></el-input>
                <span v-else>{{ scope.row.device_name }}</span>
              </template>
            </el-table-column>
            <el-table-column label="IP地址" width="150" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.id_address"
                  placeholder="请输入IP地址"
                ></el-input>
                <span v-else>{{ scope.row.id_address }}</span>
              </template>
            </el-table-column>
            <el-table-column label="MAC地址" width="200" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.mac_address"
                  placeholder="请输入MAC地址"
                ></el-input>
                <span v-else>{{ scope.row.mac_address }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作系统" width="200" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.operating_system"
                  placeholder="请输入操作系统"
                ></el-input>
                <span v-else>{{ scope.row.operating_system }}</span>
              </template>
            </el-table-column>
            <el-table-column label="运行业务" width="200" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.business"
                  placeholder="请输入运行业务"
                ></el-input>
                <span v-else>{{ scope.row.business }}</span>
              </template>
            </el-table-column>
            <el-table-column label="备注" align="center">
              <template slot-scope="scope">
                <el-input
                  v-if="scope.row.isNew"
                  v-model="scope.row.remarks"
                  placeholder="请输入备注"
                ></el-input>
                <span v-else>{{ scope.row.remarks }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150" align="center">
              <template slot-scope="scope">
                <el-button
                  v-if="scope.row.isNew"
                  type="primary"
                  size="mini"
                  @click="saveRow3(scope.$index)"
                >
                  保存
                </el-button>
                <el-button
                  v-if="scope.row.isNew"
                  type="danger"
                  size="mini"
                  @click="cancelRow3(scope.$index)"
                >
                  取消
                </el-button>
                <el-button
                  v-if="!scope.row.isNew"
                  type="primary"
                  size="mini"
                  @click="editRow3(scope.$index)"
                >
                  编辑
                </el-button>
                <el-button
                  v-if="!scope.row.isNew"
                  type="danger"
                  size="mini"
                  @click="deleteRow3(scope.$index)"
                >
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="哑终端数量(无法安装程序)" class="more-label">
                <el-input-number
                  v-model="form.dumb_number"
                  controls-position="right"
                  :min="0"
                  :max="999"
                ></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="使用的杀毒软件">
                <el-input
                  v-model.trim="form.antivirus"
                  placeholder="请输入"
                  class="input-with-select"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="上传文件" prop="files" class="must-start">
                <el-upload
                  class="upload-demo"
                  ref="upload"
                  action=""
                  :show-file-list="true"
                  :on-change="
                    (file, fileList) => {
                      fileChange(file, fileList)
                    }
                  "
                  :auto-upload="false"
                  :file-list="form.files"
                  :on-remove="handleRemove"
                >
                  <el-button type="primary">选择文件</el-button>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="close">关 闭</el-button>
        <el-button type="success" :loading="loading" @click="saveInfo(0)">
          保 存
        </el-button>
        <el-button type="primary" :loading="loading" @click="saveInfo(1)">
          {{ loading ? '提交中 ...' : '提交审核' }}
        </el-button>
      </div>
    </div>
  </el-drawer>
</template>

<script>
  import {
    addUpdateInfo,
    uploadEvalOrgDetail,
    removeUploadEvalOrgDetail,
    infoDetail,
  } from '@/api/terminal'
  import { fileURL } from '@/config/setting.config'
  export default {
    name: 'edit',
    components: {},
    props: {
      levelList: {
        type: Array,
        default: () => [],
      },
      nationalList: {
        type: Array,
        default: () => [],
      },
      admdvs: {
        type: Array,
        default: () => [],
      },
    },
    data() {
      return {
        title: '',
        dialog: false,
        loading: false,
        form: {
          id: '',
          area: '',
          operator: '',
          is_network_line: 0,
          dedicated_line: '',
          services_accessed: '',
          dumb_number: 0,
          antivirus: '',
          org_name: '',
          medical_code: '',
          contacts: '',
          contact_number: '',
          files: [],
          fileIds: [],
        },
        flag: false,
        rules: {
          // area: [{ required: true, message: '请选择区域', trigger: 'change' }],
          operator: [
            { required: true, message: '请选择运营商', trigger: 'change' },
          ],
        },
        tableList: [],
        tableList2: [],
        tableList3: [],
        fileList: [],
      }
    },
    watch: {},
    mounted() {},
    methods: {
      async showDia(row) {
        if (!row) {
          this.title = '新增'
          var userinfo = JSON.parse(localStorage.getItem('userinfo'))
          this.form.org_name = userinfo.org_name
          this.form.medical_code = userinfo.org_code
          this.form.contacts = userinfo.name
          this.form.contact_number = userinfo.telephone
          this.form.cred_lv = userinfo.lmtpric_hosp_lv
          this.form.area = userinfo.fix_blng_admdvs
        } else {
          this.title = '编辑'
          // 调用详情接口
          await infoDetail({
            id: row.id,
          }).then((res) => {
            if (res.code == 0) {
              console.log(res)
              this.form = res.data
              this.tableList = res.data.terminalUsers
              this.tableList2 = res.data.terminalNetworks
              this.tableList3 = res.data.terminalNetworksOther

              // // 初始化 isNew 和 originalData 属性
              // this.tableList.forEach((item) => {
              //   item.isNew = false
              //   item.originalData = { ...item }
              // })
              // this.tableList2.forEach((item) => {
              //   item.isNew = false
              //   item.originalData = { ...item }
              // })
              // this.tableList3.forEach((item) => {
              //   item.isNew = false
              //   item.originalData = { ...item }
              // })

              // 用户信息表格
              this.tableList = res.data.terminalUsers.map((item) => ({
                ...item,
                isNew: false,
                originalData: { ...item },
              }))

              // 终端情况表格1
              this.tableList2 = res.data.terminalNetworks.map((item) => ({
                ...item,
                isNew: false,
                originalData: { ...item },
              }))

              // 终端情况表格2
              this.tableList3 = res.data.terminalNetworksOther.map((item) => ({
                ...item,
                isNew: false,
                originalData: { ...item },
              }))

              if (res.data.files) {
                const files = res.data.files // 假设后台返回的数据结构如上所示

                // 将后台返回的文件数据转换为 <el-upload> 组件能够识别的格式
                const fileList = files.map((file) => ({
                  name: file.fileName,
                  url: file.fileUrl,
                  status: 'success', // 假设所有文件都是成功上传的状态
                }))

                // 更新 form.files
                this.form.files = fileList
                this.form.fileIds = res.data.fileIds
              } else {
                this.form.files = []
                this.form.fileIds = []
              }
            } else {
              this.$baseMessage(res.data.msg, 'error')
            }
          })

          // this.form = Object.assign({}, row)
          // console.log(this.form)
          // this.tableList = this.form.terminalUsers
          // this.tableList2 = this.form.terminalNetworks
          // this.tableList3 = this.form.terminalNetworksOther
        }

        // this.form.cred_lv = userinfo.fixmedinsB.lmtpric_hosp_lv
        // fixmedinsB.lmtpric_hosp_lv
        console.log(userinfo)
        // await getFixmedinsB().then((res) => {
        //   console.log(res)
        //   this.fixmedinsB = res.data
        // })

        this.dialog = true
      },
      close() {
        // 重置form表单 及 tableList
        this.tableList = []
        this.tableList2 = []
        this.tableList3 = []
        this.form = {
          id: '',
          area: '',
          operator: '',
          is_network_line: 0,
          dedicated_line: '',
          services_accessed: '',
          dumb_number: 0,
          antivirus: '',
          org_name: '',
          medical_code: '',
          contacts: '',
          contact_number: '',
          fileIds: [],
        }
        this.$refs.form.resetFields()
        this.dialog = false
      },

      // 上传开始
      async fileChange(file, fileList, row) {
        const that = this
        let fd = new FormData()
        fd.append('file', file.raw)
        await uploadEvalOrgDetail(fd).then((res) => {
          if (res.data.code == 0) {
            file.id = res.data.data.id
            // const fileIds = []
            // fileList.forEach((item) => {
            //   fileIds.push(item.id)
            // })
            // this.form.fileIds = fileIds
            // console.log(fileIds)

            // 如果是编辑页面，fileIds可能已经有值
            if (this.form.fileIds) {
              this.form.fileIds.push(file.id)
            } else {
              this.form.fileIds = [file.id]
            }
            console.log(this.form.fileIds)
            that.$baseMessage('上传成功', 'success')
          } else {
            that.$baseMessage('网络异常，请刷新重新！', 'error')
          }
        })
      },
      // 删除文件
      // handleRemove(file, fileList) {
      //   const that = this
      //   const fileId = file.id
      //   const fileIndex = fileList.findIndex((item) => item.id === fileId)
      //   if (fileIndex !== -1) {
      //     // 从 fileIds 数组中移除对应的文件 ID
      //     this.form.fileIds.splice(fileIndex, 1)
      //     // 从 files 数组中移除对应的文件对象
      //     this.form.files.splice(fileIndex, 1)
      //     that.$baseMessage('删除成功', 'success')
      //   } else {
      //     that.$baseMessage('文件不存在', 'error')
      //   }
      // },
      handleRemove(file, fileList) {
        const that = this
        // 根据 file.uid 找到文件在 this.form.files 中的索引
        const fileIndex = this.form.files.findIndex(
          (item) => item.uid === file.uid
        )
        if (fileIndex !== -1) {
          // 根据索引删除 fileIds 和 files 中的对应项
          this.form.fileIds.splice(fileIndex, 1)
          this.form.files.splice(fileIndex, 1)
          that.$baseMessage('删除成功', 'success')
        } else {
          that.$baseMessage('文件不存在', 'error')
        }
      },

      // handleRemove(file, fileList, row) {
      //   const that = this
      //   removeUploadEvalOrgDetail({
      //     id: file.id,
      //   }).then(() => {
      //     const fileIds = []
      //     fileList.forEach((item) => {
      //       fileIds.push(item.id)
      //     })
      //     this.$baseMessage('删除成功', 'success')
      //   })
      // },

      //   用户信息新增
      addLevel() {
        if (this.tableList.some((row) => row.isNew)) {
          this.$message.warning('请先保存当前编辑的行')
          return
        }
        this.tableList.push({
          name: '',
          phone_number: '',
          id_number: '',
          remarks: '',
          isNew: true,
        })
      },

      // 保存行数据
      saveRow(index) {
        const row = this.tableList[index]
        if (!row.name || !row.phone_number || !row.id_number) {
          this.$message.error('姓名、手机号和身份证号为必填项')
          return
        }

        // 验证手机号格式
        const phonePattern = /^1[3456789]\d{9}$/
        if (!phonePattern.test(row.phone_number)) {
          this.$message.error('请输入正确的手机号')
          return
        }

        // 验证身份证号格式
        const idCardPattern = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
        if (!idCardPattern.test(row.id_number)) {
          this.$message.error('请输入正确的身份证号')
          return
        }
        // 清除原始数据缓存
        if (row.originalData) delete row.originalData
        row.isNew = false
        console.log(this.tableList)
      },

      // 取消操作（新增行直接删除，编辑行恢复数据）
      cancelRow(index) {
        const row = this.tableList[index]
        if (row.originalData) {
          // 恢复编辑前的数据
          Object.assign(row, row.originalData)
          delete row.originalData // 清除原始数据缓存
          row.isNew = false
        } else {
          // 删除新增的临时数据
          this.tableList.splice(index, 1)
        }
      },

      // 进入编辑模式
      // editRow(index) {
      //   const row = this.tableList[index]
      //   // 保存原始数据快照（深拷贝避免引用问题）
      //   row.originalData = { ...row }
      //   delete row.originalData.originalData // 防止递归引用
      //   row.isNew = true
      //   console.log(row)
      // },
      editRow(index) {
        const row = this.tableList[index]
        row.originalData = { ...row }
        delete row.originalData.originalData
        this.$set(this.tableList, index, { ...row, isNew: true })
      },
      // 删除行
      deleteRow(index) {
        this.tableList.splice(index, 1)
      },

      // 终端情况新增
      addLevel2() {
        if (this.tableList2.some((row) => row.isNew)) {
          this.$message.warning('请先保存当前编辑的行')
          return
        }
        this.tableList2.push({
          device_name: '',
          id_address: '',
          mac_address: '',
          operating_system: '',
          business: '',
          remarks: '',
          isNew: true,
        })
      },

      // 保存行数据
      saveRow2(index) {
        const row = this.tableList2[index]
        if (!row.device_name || !row.id_address || !row.mac_address) {
          this.$message.error('设备名称、IP地址、MAC地址为必填项')
          return
        }
        // 清除原始数据缓存
        if (row.originalData) delete row.originalData
        row.isNew = false
      },

      // 取消操作（新增行直接删除，编辑行恢复数据）
      cancelRow2(index) {
        const row = this.tableList2[index]
        if (row.originalData) {
          // 恢复编辑前的数据
          Object.assign(row, row.originalData)
          delete row.originalData // 清除原始数据缓存
          row.isNew = false
        } else {
          // 删除新增的临时数据
          this.tableList2.splice(index, 1)
        }
      },

      // 进入编辑模式
      editRow2(index) {
        const row = this.tableList2[index]
        row.originalData = { ...row }
        delete row.originalData.originalData
        this.$set(this.tableList2, index, { ...row, isNew: true })
      },

      // 删除行
      deleteRow2(index) {
        this.tableList2.splice(index, 1)
      },

      // 终端情况 -- 其它 --新增
      addLevel3() {
        if (this.tableList3.some((row) => row.isNew)) {
          this.$message.warning('请先保存当前编辑的行')
          return
        }
        this.tableList3.push({
          device_name: '',
          id_address: '',
          mac_address: '',
          operating_system: '',
          business: '',
          remarks: '',
          isNew: true,
        })
      },

      // 保存行数据
      saveRow3(index) {
        const row = this.tableList3[index]
        if (!row.device_name || !row.id_address || !row.mac_address) {
          this.$message.error('设备名称、IP地址、MAC地址为必填项')
          return
        }
        // 清除原始数据缓存
        if (row.originalData) delete row.originalData
        row.isNew = false
      },

      // 取消操作（新增行直接删除，编辑行恢复数据）
      cancelRow3(index) {
        const row = this.tableList3[index]
        if (row.originalData) {
          // 恢复编辑前的数据
          Object.assign(row, row.originalData)
          delete row.originalData // 清除原始数据缓存
          row.isNew = false
        } else {
          // 删除新增的临时数据
          this.tableList3.splice(index, 1)
        }
      },

      // 进入编辑模式
      editRow3(index) {
        const row = this.tableList3[index]
        row.originalData = { ...row }
        delete row.originalData.originalData
        this.$set(this.tableList3, index, { ...row, isNew: true })
      },

      // 删除行
      deleteRow3(index) {
        this.tableList3.splice(index, 1)
      },
      // 保存信息
      async saveInfo(submitType) {
        this.$refs.form.validate(async (valid) => {
          if (valid) {
            // 如果文件列表为空，则提示用户上传文件
            if (this.form.fileIds.length === 0) {
              this.$message.error('请至少上传一个文件')
              return
            }
            this.loading = true
            // 处理表格数据
            const terminalUsers = this.tableList.map((row) => {
              return {
                name: row.name,
                phone_number: row.phone_number,
                id_number: row.id_number,
                remarks: row.remarks,
              }
            })
            const terminalNetworks = this.tableList2.map((row) => {
              return {
                device_name: row.device_name,
                id_address: row.id_address,
                mac_address: row.mac_address,
                operating_system: row.operating_system,
                business: row.business,
                remarks: row.remarks,
                type: 1,
              }
            })
            const terminalNetworksOther = this.tableList3.map((row) => {
              return {
                device_name: row.device_name,
                id_address: row.id_address,
                mac_address: row.mac_address,
                operating_system: row.operating_system,
                business: row.business,
                remarks: row.remarks,
                type: 2,
              }
            })
            // 处理上传文件
            // const uploadFiles = this.fileList.map((file) => {
            //   return {
            //     name: file.name,
            //     url: fileURL + file.response.data,
            //   }
            // })

            this.form.is_submit = submitType
            // 发送保存请求
            const saveData = {
              ...this.form,
              terminalUsers,
              terminalNetworks,
              terminalNetworksOther,
              // uploadFiles,
            }
            try {
              const response = await addUpdateInfo(saveData)
              this.$message.success('保存成功')
              this.loading = false
              // this.dialog = false
              this.close()
              this.$emit('fetch-data')
            } catch (error) {
              this.loading = false
              this.$message.error(error.msg)
            }
          } else {
            return false
          }
        })
      },

      // 下载文件
      downloadFile(url) {
        const link = document.createElement('a')
        link.href = url
        link.download = url.split('/').pop()
        link.click()
      },
    },
  }
</script>
<style lang="scss" scoped>
  .scoreDiyContainer {
    margin-bottom: 10px;
  }
  .tips-nums {
    display: inline-block;
    margin: 0 10px;
    font-size: 14px;
    color: #666;
    font-weight: normal;
  }
  .mt2 {
    margin-top: 20px;
  }
  .more-label {
    ::v-deep {
      .el-form-item__label {
        line-height: 18px;
      }
    }
  }
  .box_card .box_header {
    position: relative;
    border-left: none;
    padding-left: 14px;
    &::before {
      content: '';
      display: inline-block;
      position: absolute;
      left: 0;
      width: 4px;
      height: 24px;
      background-color: #1890ff;
    }
  }
  .sub-header {
    margin-top: 14px;
    display: flex;
    align-items: center;
    justify-content: space-between;
    .tips-nums {
      margin: 0;
    }
  }
  .must-start {
    ::v-deep {
      .el-form-item__label {
        &::before {
          content: '*';
          color: #ff4d4f;
          margin-right: 4px;
        }
      }
    }
  }
  ::v-deep {
    .el-dialog__body {
      border-top: 0;
    }

    .scoreDiyContainer {
      .el-input {
        width: 50px;
        margin-right: 4px;
      }
    }
  }
</style>
