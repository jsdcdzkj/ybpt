<template>
  <el-drawer
    :title="title"
    :before-close="handleClose"
    :visible.sync="dialog"
    direction="rtl"
    :with-header="false"
    custom-class="box_drawer"
    size="80%"
    ref="drawer"
  >
    <div class="drawer_content" v-loading="loading" element-loading-text="请稍后" element-loading-spinner="el-icon-loading"
         element-loading-background="rgba(0, 0, 0, 0.8)">
      <el-form :model="form" :label-width="formLabelWidth">
        <div class="drawer_main">
          <div class="box_card">
            <div class="box_header">
              <span>医药机构信息</span>
              <vab-icon
                :icon="['fas', 'angle-up']"
                v-if="isShow"
                @click="moreQuery"
              ></vab-icon>
              <vab-icon
                :icon="['fas', 'angle-down']"
                v-else
                @click="moreQuery"
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医药机构编号" class="custemitem">
                    <el-input v-model.trim="saveData.fixmedins_code" >
                      <el-button
                              slot="append"
                              icon="el-icon-refresh"
                              @click="querry"
                      ></el-button>
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医药机构名称">
                    <el-input
                      v-model.trim="saveData.fixmedins_name"
                      clearable
                      class="input-with-select"
                      disabled
                    ></el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医药机构简称">
                    <el-input
                      v-model.trim="saveData.fixmedins_name"
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="box_card">
            <div class="box_header">
              <span>机构属性</span>
              <vab-icon
                :icon="['fas', 'angle-up']"
                v-if="isShow1"
                @click="moreQuery1"
              ></vab-icon>
              <vab-icon
                :icon="['fas', 'angle-down']"
                v-else
                @click="moreQuery1"
              ></vab-icon>
            </div>
            <div class="box_content">
              <el-row :gutter="20" v-if="isShow1">
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="协议所属行政区划" class="custemitem">
                    <el-select v-model="saveData.fix_blng_admdvs" class="w" disabled="">
                      <el-option label="徐州市市本级" value="320399"></el-option>
                      <el-option label="铜山区" value="320312"></el-option>
                      <el-option label="丰县" value="320321"></el-option>
                      <el-option label="沛县" value="320322"></el-option>
                      <el-option label="睢宁县" value="320324"></el-option>
                      <el-option label="新沂市" value="320381"></el-option>
                      <el-option label="邳州市" value="320382"></el-option>
                      <el-option label="贾汪区" value="320305"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="医院等级" v-if="saveData.fixmedins_type == 1">
                    <el-select v-model="saveData.hosp_lv" class="w" disabled="">
                      <el-option label="三级特等" value="01"></el-option>
                      <el-option label="三级甲等" value="02"></el-option>
                      <el-option label="三级乙等" value="03"></el-option>
                      <el-option label="三级丙等" value="04"></el-option>
                      <el-option label="二级甲等" value="05"></el-option>
                      <el-option label="二级乙等" value="06"></el-option>
                      <el-option label="二级丙等" value="07"></el-option>
                      <el-option label="一级甲等" value="08"></el-option>
                      <el-option label="一级乙等" value="09"></el-option>
                      <el-option label="一级丙等" value="10"></el-option>
                      <el-option label="无等级" value="11"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="限价医院等级" v-if="saveData.fixmedins_type == 1">
                    <el-select v-model="saveData.lmtpric_hosp_lv" class="w" disabled="">
                      <el-option label="一级及以下" value="1"></el-option>
                      <el-option label="二级" value="2"></el-option>
                      <el-option label="三级" value="3"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="起付线医院等级" class="custemitem" v-if="saveData.fixmedins_type == 1">
                    <el-select v-model="saveData.dedc_hosp_lv" class="w" disabled="">
                      <el-option label="一级及以下" value="1"></el-option>
                      <el-option label="二级" value="2"></el-option>
                      <el-option label="三级" value="3"></el-option>
                      <el-option label="未定级" value="9"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="国家异地平台机构编号" class="custemitem" >
                    <el-input v-model.trim="saveData.nat_plaf_code" disabled="">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="省内异地平台机构编号" class="custemitem" >
                    <el-input v-model.trim="saveData.prov_plaf_code"  disabled="">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="异地医药机构类型" class="custemitem">
                    <el-select v-model="saveData.out_fixmedins_type" class="w" disabled="">
                      <el-option label="中心零星报销医药机构" value="1"></el-option>
                      <el-option label="本地定点医药机构" value="2"></el-option>
                      <el-option label="省内异地医药机构" value="3"></el-option>
                      <el-option label="省外异地医药机构" value="4"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="定点联网开通标志" class="custemitem">
                    <el-select v-model="saveData.fix_onln_open_flag" class="w" disabled="">
                      <el-option label="暂停联网" value="0"></el-option>
                      <el-option label="正常联网" value="1"></el-option>
                      <el-option label="取消定点" value="2"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="异地联网开通类型" class="custemitem">
                    <el-select v-model="saveData.out_onln_open_type" class="w" disabled="">
                      <el-option label="非异地定点" value="01"></el-option>
                      <el-option label="省内异地定点" value="02"></el-option>
                      <el-option label="国家异地定点" value="03"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="负责人姓名" class="custemitem">
                    <el-input v-model.trim="saveData.hi_resper_name" disabled="">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="负责人证件类型" class="custemitem">
                    <el-select v-model="saveData.hi_resper_cert_type" class="w" disabled="">
                      <el-option label="居民身份证（户口簿）" value="01"></el-option>
                      <el-option label="中国人民解放军军官证" value="02"></el-option>
                      <el-option label="中国人民武装警察警官证" value="03"></el-option>
                      <el-option label="香港特区护照/港澳居民来往内地通行证" value="04"></el-option>
                      <el-option label="澳门特区护照/港澳居民来往内地通行证" value="05"></el-option>
                      <el-option label="台湾居民来往大陆通行证" value="06"></el-option>
                      <el-option label="外国人永久居留证" value="07"></el-option>
                      <el-option label="外国人护照" value="08"></el-option>
                      <el-option label="残疾人证" value="09"></el-option>
                      <el-option label="军烈属证明" value="10"></el-option>
                      <el-option label="外国人就业证" value="11"></el-option>
                      <el-option label="外国专家证" value="12"></el-option>
                      <el-option label="外国人常驻记者证" value="13"></el-option>
                      <el-option label="台港澳人员就业证" value="14"></el-option>
                      <el-option label="中国护照" value="16"></el-option>
                      <el-option label="港澳台居民居住证" value="17"></el-option>
                      <el-option label="回国（来华）定居专家证" value="15"></el-option>
                      <el-option label="社会保障卡" value="90"></el-option>
                      <el-option label="其他身份证件" value="99"></el-option>
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="负责人证件号码" class="custemitem">
                    <el-input v-model.trim="saveData.hi_resper_certno" disabled="">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="负责人联系电话" class="custemitem">
                    <el-input v-model.trim="saveData.hi_resper_tel" disabled="">
                    </el-input>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="开始日期" class="custemitem">
                    <el-date-picker
                            v-model="saveData.begntime"
                            type="date"
                            disabled=""
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col :xs="24" :sm="24" :md="8" :lg="8" :xl="8">
                  <el-form-item label="结束日期" class="custemitem">
                    <el-date-picker
                            v-model="saveData.endtime"
                            type="date"
                            disabled=""
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </el-form>
      <div class="drawer_footer">
        <el-button @click="cancelForm">关 闭</el-button>
        <el-button @click="reseat">重 置</el-button>
        <el-button type="primary" @click="sub" >提 交</el-button>
        <!--<el-button-->
          <!--type="primary"-->
          <!--@click="$refs.drawer.closeDrawer()"-->
          <!--:loading="loading"-->
        <!--&gt;-->
          <!--{{ loading ? '提交中 ...' : '提 交' }}-->
        <!--</el-button>-->
      </div>
    </div>
    <hospital ref="hospital" @fetch-data="form.name"></hospital>
    <doctor ref="doctor" @fetch-data="form.name"></doctor>
    <medical ref="medical" @fetch-data="form.name"></medical>
    <bingzhong ref="bingzhong" @fetch-data="form.name"></bingzhong>
  </el-drawer>
</template>

<script>
import Hospital from '@/components/hospital'
import Doctor from '@/components/doctor'
import Medical from '@/components/medical'
import Bingzhong from '@/components/bingzhong'
import {selectByCode,add,selectList} from '@/api/fixmedins.js'
export default {
  name: 'edit',
  components: { Hospital,Doctor,Medical,Bingzhong },
  data() {
    return {
      tableData: [],
      title: '',
      dialog: false,
      loading: false,
      isShow: false,
      isShow1: true,
      isShow2: false,
      saveData: {
        fixmedins_code: '',
      },
      queryForm:{

      },
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
      },
      formLabelWidth: '100px',
      timer: null,
      loading:false
    }
  },
  mounted() {},
  methods: {
    showDia(row) {
      if (!row) {
        this.title = '新增'
      } else {
        this.title = '编辑'
        this.form = Object.assign({}, row)
      }
      this.dialog = true
    },
    close() {
      // this.$refs['form'].resetFields()
      // this.form = this.$options.data().form
      this.dialog = false
    },
    save() {
      this.$baseMessage('模拟保存成功', 'success')
      this.$emit('fetch-data')
      this.close()
    },
    openwin(){
      this.$refs['hospital'].showDia()
    },
    openwin1(){
      this.$refs['doctor'].showDia()
    },
    openwin2(){
      this.$refs['bingzhong'].showDia()
    },
    openwin3(){
      this.$refs['medical'].showDia()
    },
    moreQuery() {
      this.isShow = !this.isShow
    },
    moreQuery1() {
      this.isShow1 = !this.isShow1
    },
    moreQuery2() {
      this.isShow2 = !this.isShow2
    },
    handleClose(done) {
      // if (this.loading) {
      //   return
      // }
      // this.$confirm('确定要提交表单吗？')
      //   .then((_) => {
      //     this.loading = true
      //     this.timer = setTimeout(() => {
      //       done()
      //       // 动画关闭需要一定的时间
      //       setTimeout(() => {
      //         this.loading = false
      //       }, 400)
      //     }, 2000)
      //   })
      //   .catch((_) => {})
    },
    cancelForm() {
      this.loading = false
      this.dialog = false
      clearTimeout(this.timer)
    },
    querry(){
      var that = this ;
      if(that.saveData.fixmedins_code =="" || that.saveData.fixmedins_code==null || that.saveData.fixmedins_code ==undefined){
        that.$baseMessage('请输入医药机构编号,再进行同步操作！', 'error')
      }else {
        selectByCode(that.saveData).then((res) => {
          if(res.data != null ){
            that.saveData = res.data ;
            that.$baseMessage('查询成功', 'success')
          }else {
            that.reseat() ;
            that.$baseMessage('未查询到该机构', 'error')
          }
        })
      }
    },
    reseat(){
      var that = this ;
      that.saveData = {} ;
    },
    sub(){
      var that = this ;
      if(that.saveData.fixmedins_name =="" || that.saveData.fixmedins_name ==null || that.saveData.fixmedins_name ==undefined){
        that.$baseMessage('请先查询医疗机构！', 'error');
      }else {
        that.loading = true ;
        add(that.saveData).then((res) => {
          that.loading = false ;
          if(res.code == 0){
            that.$baseMessage('操作成功！', 'success') ;
            that.cancelForm() ;
            that.$emit('fetch-data');
          }else {
            that.reseat() ;
            that.$baseMessage(res.msg, 'error') ;
          }
        })
      }
    }
  },
}
</script>