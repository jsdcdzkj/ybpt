<template>
  <el-drawer
    :title="title"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="1400px"
    ref="drawer"
    :wrapperClosable="true"
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
                  <el-form-item label="区域" prop="area">
                    <el-select
                      v-model="form.area"
                      style="width: 100%"
                      clearable
                      disabled
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
                  <el-form-item label="所在街道">
                    <el-input
                      v-model.trim="!form.address ? '无' : form.address"
                      clearable
                      placeholder="请输入"
                      disabled
                    ></el-input>
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
                  <el-form-item label="运营商" prop="operator">
                    <el-select
                      v-model="form.operator"
                      placeholder="请选择"
                      style="width: 100%"
                      disabled
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
                      disabled
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
                      v-model.trim="
                        !form.dedicated_line ? '无' : form.dedicated_line
                      "
                      disabled
                      placeholder="请输入"
                      class="input-with-select"
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="访问的服务">
                    <el-input
                      v-model.trim="
                        !form.services_accessed ? '无' : form.services_accessed
                      "
                      disabled
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
          </el-table>
          <!-- 终端情况 -- 第二个 -->
          <div class="sub-header">
            <p class="tips-nums">
              同时访问医保网与其他网络的终端数量：{{ tableList3.length }}
            </p>
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
          </el-table>
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="哑终端数量(无法安装程序)" class="more-label">
                <el-input-number
                  disabled
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
                  v-model.trim="!form.antivirus ? '无' : form.antivirus"
                  disabled
                  placeholder="请输入"
                  class="input-with-select"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
              <el-form-item label="上传文件">
                <el-upload
                  class="upload-demo"
                  ref="upload"
                  action=""
                  disabled
                  :show-file-list="true"
                  :auto-upload="false"
                  :file-list="form.files"
                  :on-preview="handlePreview"
                ></el-upload>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="close">关 闭</el-button>
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
          fileList: [],
        },
        flag: false,
        rules: {
          area: [{ required: true, message: '请选择区域', trigger: 'change' }],
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
      async showDiaView(row) {
        this.title = '详情'
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
            }
          } else {
            this.$baseMessage(res.data.msg, 'error')
          }
        })

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
          files: [],
        }
        this.dialog = false
      },

      handlePreview(file) {
        const url = fileURL + file.url
        this.generateAndClickLink(url, file.name)
      },
      async generateAndClickLink(url, fileName) {
        try {
          // 发起请求获取文件内容
          const response = await fetch(url)
          const blob = await response.blob()

          // 创建Blob URL并触发下载
          const blobUrl = window.URL.createObjectURL(blob)
          const eleLink = document.createElement('a')
          eleLink.href = blobUrl
          eleLink.download = fileName
          eleLink.style.display = 'none'
          document.body.appendChild(eleLink)
          eleLink.click()

          // 清理资源
          window.URL.revokeObjectURL(blobUrl)
          document.body.removeChild(eleLink)
        } catch (error) {
          console.error('文件下载失败:', error)
        }
      },

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
      editRow(index) {
        const row = this.tableList[index]
        // 保存原始数据快照（深拷贝避免引用问题）
        row.originalData = { ...row }
        delete row.originalData.originalData // 防止递归引用
        row.isNew = true
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
        // 保存原始数据快照（深拷贝避免引用问题）
        row.originalData = { ...row }
        delete row.originalData.originalData // 防止递归引用
        row.isNew = true
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
        // 保存原始数据快照（深拷贝避免引用问题）
        row.originalData = { ...row }
        delete row.originalData.originalData // 防止递归引用
        row.isNew = true
      },

      // 删除行
      deleteRow3(index) {
        this.tableList3.splice(index, 1)
      },
      // 保存信息
      async saveInfo() {
        this.$refs.form.validate(async (valid) => {
          if (valid) {
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
              }
            })
            // 处理上传文件
            // const uploadFiles = this.fileList.map((file) => {
            //   return {
            //     name: file.name,
            //     url: fileURL + file.response.data,
            //   }
            // })

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
              this.dialog = false
              this.$emit('fetch-data')
            } catch (error) {
              this.$message.error('保存失败')
              this.loading = false
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
    .el-upload.el-upload--text {
      display: none;
    }
    .el-upload-list__item:first-child {
      margin-top: 4px;
    }
  }
</style>
