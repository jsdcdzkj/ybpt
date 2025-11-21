<template>
  <div class="main-container">
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">信息查询</span>
            <vab-icon :icon="['fas', 'angle-up']" v-if="isShow" @click="moreQuery"></vab-icon>
            <vab-icon :icon="['fas', 'angle-down']" v-else @click="moreQuery"></vab-icon>
          </div>
          <el-form label-width="160px">
            <el-row :gutter="20">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="证件类型">
                  <el-select v-model="queryForm.username" placeholder="证件类型" style="width: 100%">
                    <el-option label="居民身份证（户口薄）" value="1"></el-option>
                    <el-option label="中国人民解放军军官证" value="2"></el-option>
                    <el-option label="中国人民武装警察警官证" value="3"></el-option>
                    <el-option label="香港特区护照/港澳居民来往内地通行证" value="4"></el-option>
                    <el-option label="奥门特区护照/港澳居民来往内地通行证" value="5"></el-option>
                    <el-option label="台湾居民来往内地通行证" value="6"></el-option>
                    <el-option label="外国人永久居留证" value="7"></el-option>
                    <el-option label="外国人护照" value="8"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="医保电子凭证/身份证号">
                  <el-input v-model.trim="queryForm.username" placeholder="身份证号" clearable class="input-with-icon">
                    <vab-icon :icon="['fas', 'search']" slot="suffix" style="margin-right:10px;" @click="openwin">
                    </vab-icon>
                    <vab-icon :icon="['fas', 'id-card']" slot="suffix" style="margin-right:10px;"></vab-icon>
                    <vab-icon :icon="['fas', 'barcode']" slot="suffix" style="margin-right:10px;"></vab-icon>
                  </el-input>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="姓名">
                  <el-input v-model.trim="queryForm.username" placeholder="姓名" disabled />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row :gutter="20" v-if="isShow">
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="性别">
                  <el-select v-model="queryForm.username" style="width: 100%" disabled>
                    <el-option label="男" value="1"></el-option>
                    <el-option label="女" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="民族">
                  <el-select v-model="queryForm.username" style="width: 100%" disabled>
                    <el-option label="汉族" value="1"></el-option>
                    <el-option label="少数民族" value="2"></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="出生日期">
                  <el-date-picker v-model.trim="queryForm.username" disabled type="date" class="w"></el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                <el-form-item label="电话号码">
                  <el-input v-model.trim="queryForm.username" disabled />
                </el-form-item>
              </el-col>
            </el-row>
            <vab-query-form>
              <vab-query-form-right-panel :span="24">
                <el-form-item class="mr0">
                  <el-button icon="el-icon-refresh-left">重 置</el-button>
                  <el-button icon="el-icon-search" type="primary">
                    查 询
                  </el-button>
                </el-form-item>
              </vab-query-form-right-panel>
            </vab-query-form>
          </el-form>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
        <el-card class="card" shadow="never">
          <div slot="header">
            <span class="tips">异地就医登记列表</span>
            <el-button type="success" class="right" icon="el-icon-plus" @click="handleAdd">新增</el-button>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" highlight-current-row border
            @current-change="handleCurrentChange" height="420px">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <!-- <el-table-column show-overflow-tooltip type="selection"></el-table-column> -->
            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" width="80px"></el-table-column>
            <el-table-column show-overflow-tooltip label="审核状态" align="center" prop="username" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="truename" width="120px" label="有效状态" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="bumen" label="业务流水号" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="phone" label="安置地(就医地)所属行政区" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="phone" label="安置地(就医地)医保机构名称" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="人员姓名" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="证件类型" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="证件号码" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="leixing" label="参保险种" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="单位名称" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="经办人员工号" width="160px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="经办人员姓名" width="160px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="异地备案上报状态" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="异地安置类别" width="160px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="经办时间" width="120px">
            </el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="160" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handlechuli(row)" type="primary" size="mini">
                  查看
                </el-button>
                <el-button plain @click="handlecancel(row)" type="primary" size="mini">
                  撤消
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
            :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange2"></el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <cardnum ref="cardnum" @fetch-data="fetchData"></cardnum>
    <edit ref="edit" @fetch-data="fetchData"></edit>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import { doDelete, getList } from '@/api/userManagement'
import Cardnum from '@/components/cardno'
import Edit from './components/edit'
import Views from './components/view'
export default {
  name: 'Index',
  components: {Cardnum,Edit,Views},
  data() {
    return {
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
        pageNo: 1,
        pageSize: 10,
        username: '',
      },
    }
  },
  created() {
    this.fetchData()
  },
  beforeDestroy() {},
  mounted() {},
  methods: {
    handleCurrentChange(val) {
      this.selectRows = val
    },
    openwin(){
      this.$refs['cardnum'].showDia()
    },
    handleAdd(){
      this.$refs['edit'].showDia()
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    handlechuli(row){
      this.$refs['views'].showDia(row.id)
    },
    handlecancel(row){
      if (row.id) {
          this.$baseConfirm('确认进行撤消？', null, async () => {
            const { msg } = await doDelete({ ids: row.id })
            this.$baseMessage(msg, 'success')
            this.fetchData()
          })
        } else {
          
        }
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    async fetchData() {
      this.listLoading = true
      const { data, totalCount } = await getList(this.queryForm)
      this.list = data
      this.total = totalCount
      setTimeout(() => {
        this.listLoading = false
      }, 300)
    },
  },
}
</script>
