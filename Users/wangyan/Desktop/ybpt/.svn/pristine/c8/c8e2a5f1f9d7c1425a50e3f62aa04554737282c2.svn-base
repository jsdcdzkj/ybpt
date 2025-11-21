<template>
  <div class="index-container">
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
            <span class="tips">异地就医待审核列表</span>
            <el-button type="primary" class="right" icon="el-icon-document-checked" @click="handleSh">
              批量审核
            </el-button>
          </div>
          <el-table v-loading="listLoading" ref="listTable" stripe :data="list"
            :element-loading-text="elementLoadingText" highlight-current-row border @selection-change="setSelectRows"
            height="420px">
            <template slot="empty">
              <el-empty :image-size="200"></el-empty>
            </template>
            <el-table-column show-overflow-tooltip type="selection" align="center" fixed="left" width="80px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="id" label="序号" align="center" fixed="left" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip label="审核状态" width="120px" align="center" prop="username">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="truename" width="220px" label="业务流水号" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="bumen" label="当前环节" width="120px" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="phone" label="医保门诊门特病种名称" width="200px" align="center">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="email" label="安置地(就医地)医保机构名称" align="center" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="weixin" label="参保险种" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="weixin" label="人员名称" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="leixing" label="证件类型" align="center" width="120px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="证件号码" width="180px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="参保所属医保区划	" width="220px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="安置地(就医地)所属行政区" width="220px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="单位名称" width="200px">
            </el-table-column>
            <el-table-column show-overflow-tooltip prop="zhuangtai" align="center" label="申报来源" width="120px">
            </el-table-column>

            <el-table-column show-overflow-tooltip label="操作" width="200" align="center" fixed="right">
              <template #default="{ row }">
                <el-button plain @click="handleAdd(row)" type="primary" size="mini">
                  审核
                </el-button>
                <el-button plain @click="openwin1(row)" type="primary" size="mini">
                  查看档案
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
    <shenhe ref="shenhe" @fetch-data="fetchData"></shenhe>
    <views ref="views" @fetch-data="fetchData"></views>
  </div>
</template>

<script>
import { getList } from '@/api/userManagement'
import Shenhe from '@/components/allshenhe'
import Cardnum from '@/components/cardno'
import Views from '@/components/fileview'
import Edit from './components/edit'
export default {
  name: 'Index',
  components: { Cardnum, Edit,Shenhe,Views },
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
    openwin() {
      this.$refs['cardnum'].showDia()
    },
    openwin1(row) {
      this.$refs['views'].showDia(row)
    },
    handleAdd(row) {
      this.$refs['edit'].showDia(row)
    },
    setSelectRows(val) {
      this.selectRows = val
    },
    handleSh() {
      if (this.selectRows.length > 0) {
        const ids = this.selectRows.map((item) => item.id).join()
        this.$baseConfirm('你确定要审核选中项吗', null, async () => {
          // const { msg } = await doDelete({ ids })
          this.$refs['shenhe'].showDia()
          //this.$baseMessage('模拟审核成功', 'success')
          //this.fetchData()
        })
      } else {
        this.$baseMessage('未选中任何行', 'error')
        return false
      }
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange2(val) {
      this.queryForm.pageNo = val
      this.fetchData()
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
<style lang="scss" scoped>
.index-container {
  padding: 0 !important;
  margin: 0 !important;
  background: #f5f7f8 !important;

  .bottom {
    padding-top: 20px;
    margin-top: 5px;
    color: #595959;
    text-align: left;
    border-top: 1px solid $base-border-color;
  }

  .bottom-btn {
    button {
      margin: 5px 10px 15px 0;
    }
  }
}
</style>
