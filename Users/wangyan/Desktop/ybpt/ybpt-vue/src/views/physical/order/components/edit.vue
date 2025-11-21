<template>
  <el-dialog
    :title="title"
    :visible.sync="dialogFormVisible"
    width="805px"
    @close="close"
  >
    <el-form ref="form" :model="form" :rules="rules" label-width="100px">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="体检年份" prop="password" :style="{ padding: '10px 10px' }">
            <el-date-picker @change="queryData"
              v-model.trim="queryForm.pack_year"
              type="year" value-format="yyyy"
            ></el-date-picker>
          </el-form-item>
        </el-col>

        <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
          <el-form-item label="通用套餐+赠送项目">

            <el-col class="tc-con" :span="8"  v-for="(o, index) in orgMenu" :key="o" >
              <el-card :body-style="{ padding: '0px' }" shadow="hover">
                <el-tooltip class="item" effect="dark" :content="o.pack_name" placement="top">
                <div class="tc-box" v-bind:id="o.id" ref="{{o.id}}"
                     :class="{ active: spanIndex.indexOf(index) > -1 }"
                     @click="selectName(index,o.id)"
                     :data-index="index"
                >
                  <p>{{o.pack_name}}</p>
                  <span>{{o.pack_money}}(元)</span>
                  <el-button class="view-btn" size="mini" @click="tcView(o)">查看</el-button>
                  <div class="check-btn"><i class="el-icon-check"></i></div>
                </div>
                </el-tooltip>
              </el-card>
            </el-col>
           <!-- <el-col class="tc-con" :span="8">
              <el-card :body-style="{ padding: '0px' }" shadow="hover">
                <div class="tc-box active">
                  <p>套餐名称一</p>
                  <span>234.0(元)</span>
                  <el-button class="view-btn" size="mini" @click="tcView($event)">查看</el-button>
                  <div class="check-btn"><i class="el-icon-check"></i></div>
                </div>
              </el-card>
            </el-col>-->
          </el-form-item>
        </el-col>


      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="save">确 定</el-button>
    </div>
    <views ref="views"></views>
  </el-dialog>

</template>

<script>
  import Views from './views'
  // import { doEdit } from '@/api/userManagement'
  import {edit, getList, setOrgOption,getSelectOrgList} from "@/api_check/taocan";

  export default {
    //  name: 'UserManagementEdit',
      components: {Views},
      data() {
          return {
              currentClass: 1,
              current: 0,
              inputVisible: false,
              inputValue: '',
              form: {
                  username: '',
              },
              rules: {
                  username: [
                      {required: true, trigger: 'blur', message: '请输入用户名'},
                  ],
              },
              title: '',
              dialogFormVisible: false,
              orgMenu: [],
              selectids: {},
              queryForm: {
                  pageNo: 1,
                  pageSize: 10,
                  pack_year: '',
                  ids: "",
                  verify_ids: {},
                  if_open: 0,//机构未上架的数报
                  pack_source: '1',//套餐来源 (机构:1，通用:2)
              },

              spanIndex: [],
              selectedIds:[],

          }
      },
      created() {
          this.queryForm.pack_year=new Date().getFullYear()+"";
          this.fetchData()
      },
      methods: {
          async fetchData() {
              this.listLoading = true
              // console.log(this.queryForm);
              const {data} = await getSelectOrgList(this.queryForm)
              this.orgMenu = data.records
              this.total = data.total
              setTimeout(() => {
                  this.listLoading = false
              }, 300)
          },
          showDia() {
              this.title = '添加'
              this.dialogFormVisible = true
              this.spanIndex =[];
              this.selectedIds=[]
              this.fetchData();
          },
          tcView(row) {
            
              this.$refs['views'].showDia(row)
          },
          selectName (index,packId) {
              let arrIndex = this.spanIndex.indexOf(index);
              console.log(arrIndex)
              if (arrIndex > -1) {
                  this.spanIndex.splice(arrIndex, 1);//取消
                  this.selectedIds.splice(arrIndex, 1);
              } else {//选中
                  console.log("index-111-" + index)
                  this.spanIndex.push(index);
                  this.selectedIds.push(packId);
              }
              console.log("---s->"+this.selectedIds);
          },
          handleClose(tag) {
              this.dynamicTags.splice(this.dynamicTags.indexOf(tag), 1);
          },

          showInput() {
              this.inputVisible = true;
              this.$nextTick(_ => {
                  this.$refs.saveTagInput.$refs.input.focus();
              });
          },

          handleInputConfirm() {
              let inputValue = this.inputValue;
              if (inputValue) {
                  this.dynamicTags.push(inputValue);
              }
              this.inputVisible = false;
              this.inputValue = '';
          },
          selTc(index) {
              this.current = index
          },
          close() {
              this.$refs['form'].resetFields()
              this.form = this.$options.data().form
              this.dialogFormVisible = false
          },
          save() {
              this.$refs['form'].validate(async (valid) => {
                  if (valid) {
                      console.log("spanIndex" + this.spanIndex);
                      this.queryForm.ids=this.selectedIds.join(",");
                      if(this.queryForm.ids == "" || this.queryForm.ids == null || this.queryForm.ids == undefined){
                          return false
                      }
                      this.queryForm.flag = "T";
                      console.log("queryForm.ids" + this.queryForm);
                      const { msg } = await setOrgOption(this.queryForm)
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
<style lang="scss" scoped>
  .el-card {
    border-radius: 4px;
    text-align: center;
    .tc-box{
      padding: 14px;
      position: relative;
      p{
        padding: 0;
        margin: 0;
        width: 100%;
        height: 24px;
        line-height: 24px;
        font-weight: bold;
        overflow: hidden;
        text-overflow: ellipsis;
        white-space: nowrap;
      }
      span{
        display: inline-block;
        width: 100%;
      }
      .tc-delete{
        position: absolute;
        top: 6px;
        right: 6px;
        color: #E6A23C;
        font-size: 16px;
        cursor: pointer;
      }
      .check-btn{
        position: absolute;
        right: 0px;
        bottom: 0px;
        font-size: 24px;
        width: 0px;
        height: 0px;
        border-left: 46px solid transparent;
        border-bottom: 36px solid #EBEEF5;
        i{
          color: #fff;
          position: absolute;
          top: 14px;
          right: 2px;
        }
      }
    }
    .tc-plus{
      height: 116px;
      line-height: 116px;
      font-size: 32px;
      color: #1890ff;
    }
    .active{
      .check-btn{
        border-bottom: 36px solid #1890ff;
      }
    }

  }

</style>