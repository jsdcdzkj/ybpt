<template>
  <el-drawer :title="title" :visible.sync="dialog" direction="rtl" custom-class="box_drawer" size="80%" ref="drawer"
    append-to-body>
    <div class="drawer_content" v-loading="loading" element-loading-text="下载中。。。"
      element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.6)">

      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="汇总数据" name="first">
          <div>
            <div style="font-size: 18px;font-weight: bold;text-align: center;">
              职工医保基金拨付情况
            </div>
            <div style="font-size: 14px;float: right;margin-right:10px ;">单位：万元</div>
            <el-table :data="zgSummaries" border stripe class="w" v-loading="listLoading"
              :element-loading-text="elementLoadingText" height="33vh" ref="myTableZg">
              <el-table-column show-overflow-tooltip prop="tcq" label="医疗机构归属地" width="160px" align="center"
                fixed="left">
              </el-table-column>
              <el-table-column :label="table_title_add" align="center" :class-name="'add-table-title'">
                <el-table-column show-overflow-tooltip prop="ljFszfy" label="发生总费用" width="160px" align="center"
                  :class-name="'add-table-title'"></el-table-column>
                <el-table-column show-overflow-tooltip prop="ljTcjjfsje" label="统筹基金发生金额" align="center" width="160px"
                  :class-name="'add-table-title'"></el-table-column>
                <el-table-column prop="ljDbjjfsje" label="大病基金发生金额" width="160px" align="center"
                  :class-name="'add-table-title'"></el-table-column>
                <el-table-column prop="ljGwyjjfsje" label="公务员基金发生金额" width="160px" align="center"
                  :class-name="'add-table-title'"></el-table-column>
                <el-table-column show-overflow-tooltip prop="ljScjjfsje" label="伤残基金发生金额" align="center" width="160px"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljJzjjfsje" label="救助基金发生金额" align="center" width="160px"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljGzjjfsje" label="个账基金发生金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljXj" label="现金" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljJsxj" label="结算小计" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljTcjjjsje" label="统筹基金结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljDbjjjsje" label="大病基金结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljGwyjjjsje" label="公务员基金结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljScjjjsje" label="伤残基金结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljJzjjjsje" label="救助基金结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljGzjjjsje" label="个账基金结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>

                <el-table-column show-overflow-tooltip prop="ljJjbfxj" label="基金拨付小计" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljJjbfje" label="基金拨付金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljKhbzj" label="考核保证金累计" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljKk" label="累计扣款" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>

              </el-table-column>

              <el-table-column :label="table_title_current" align="center" :class-name="'current-table-title'">

                <el-table-column show-overflow-tooltip prop="dyFszfy" label="发生总费用" width="160px" align="center"
                  :class-name="'current-table-title'"></el-table-column>
                <el-table-column show-overflow-tooltip prop="dyTcjjfsje" label="统筹基金发生金额" align="center" width="160px"
                  :class-name="'current-table-title'"></el-table-column>
                <el-table-column prop="dyDbjjfsje" label="大病基金发生金额" width="160px" align="center"
                  :class-name="'current-table-title'"></el-table-column>
                <el-table-column prop="dyGwyjjfsje" label="公务员基金发生金额" width="160px" align="center"
                  :class-name="'current-table-title'"></el-table-column>
                <el-table-column show-overflow-tooltip prop="dyScjjfsje" label="伤残基金发生金额" align="center" width="160px"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyJzjjfsje" label="救助基金发生金额" align="center" width="160px"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyGzjjfsje" label="个账基金发生金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyXj" label="现金" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>

                <el-table-column show-overflow-tooltip prop="dyJsxj" label="结算小计" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyTcjjjsje" label="统筹基金结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyDbjjjsje" label="大病基金结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyGwyjjjsje" label="公务员基金结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyScjjjsje" label="伤残基金结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyJzjjjsje" label="救助基金结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>

                <el-table-column show-overflow-tooltip prop="dyGzjjjsje" label="个账基金结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>

                <el-table-column show-overflow-tooltip prop="dyJjbfxj" label="基金拨付小计" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyJjbfje" label="基金拨付金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyKhbzj" label="当月考核保证金" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyKk" label="当月扣款" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
              </el-table-column>

            </el-table>
            <div style="margin-top: 30px;font-size: 18px;font-weight: bold;text-align: center;">居民医保基金拨付情况</div>
            <div style="font-size: 14px;float: right;margin-right:10px ;">单位：万元</div>
            <el-table :data="jmSummaries" border stripe class="w" v-loading="listLoading"
              :element-loading-text="elementLoadingText" height="33vh" ref="myTableLj">
              <el-table-column show-overflow-tooltip prop="tcq" label="医疗机构归属地" width="160px" align="center"
                fixed="left">
              </el-table-column>
              <el-table-column :label="table_title_add" align="center" :class-name="'add-table-title'">
                <el-table-column show-overflow-tooltip prop="ljFszfy" label="发生总费用" width="160px" align="center"
                  :class-name="'add-table-title'"></el-table-column>
                <el-table-column show-overflow-tooltip prop="ljTcjjfsje" label="统筹基金发生金额" align="center" width="160px"
                  :class-name="'add-table-title'"></el-table-column>
                <el-table-column prop="ljDbjjfsje" label="大病基金发生金额" width="160px" align="center"
                  :class-name="'add-table-title'"></el-table-column>
                <el-table-column show-overflow-tooltip prop="ljJzjjfsje" label="救助基金发生金额" align="center" width="160px"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljGjzhfsje" label="共济账户发生金额" align="center" width="160px"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljXj" label="现金" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>

                <el-table-column show-overflow-tooltip prop="ljJsxj" label="结算小计" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljTcjjjsje" label="统筹基金结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljDbjjjsje" label="大病基金结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljJzjjjsje" label="救助基金结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljGjzhjsje" label="共济账户结算金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>

                <el-table-column show-overflow-tooltip prop="ljJjbfxj" label="基金拨付小计" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljJjbfje" label="基金拨付金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljDbsbbfje" label="大病商保拨付金额" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljKhbzj" label="考核保证金累计" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="ljKk" label="累计扣款" width="160px" align="center"
                  :class-name="'add-table-title'">
                </el-table-column>

              </el-table-column>

              <el-table-column :label="table_title_current" align="center" :class-name="'current-table-title'">

                <el-table-column show-overflow-tooltip prop="dyFszfy" label="发生总费用" width="160px" align="center"
                  :class-name="'current-table-title'"></el-table-column>
                <el-table-column show-overflow-tooltip prop="dyTcjjfsje" label="统筹基金发生金额" align="center" width="160px"
                  :class-name="'current-table-title'"></el-table-column>
                <el-table-column prop="dyDbjjfsje" label="大病基金发生金额" width="160px" align="center"
                  :class-name="'current-table-title'"></el-table-column>
                <el-table-column show-overflow-tooltip prop="dyJzjjfsje" label="救助基金发生金额" align="center" width="160px"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyGjzhfsje" label="共济账户发生金额" align="center" width="160px"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyXj" label="现金" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>

                <el-table-column show-overflow-tooltip prop="dyJsxj" label="结算小计" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyTcjjjsje" label="统筹基金结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyDbjjjsje" label="大病基金结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyJzjjjsje" label="救助基金结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyGjzhjsje" label="共济账户结算金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>

                <el-table-column show-overflow-tooltip prop="dyJjbfxj" label="基金拨付小计" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyJjbfje" label="基金拨付金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyDbsbbfje" label="大病商保拨付金额" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyKhbzj" label="当月考核保证金" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
                <el-table-column show-overflow-tooltip prop="dyKk" label="当月扣款" width="160px" align="center"
                  :class-name="'current-table-title'">
                </el-table-column>
              </el-table-column>


            </el-table>
            <div style="height: 60px;"></div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="数据明细" name="second">
          <div class="drawer_main">
            <vab-query-form>
              <vab-query-form-left-panel :span="24">
                <el-form :inline="true" :model="queryForm" @submit.native.prevent label-width="120px">
                  <el-form-item label="医疗机构编码">
                    <el-input v-model.trim="queryForm.orgCode" placeholder="请输入医疗机构编码" clearable
                      @keyup.enter.native="queryData" />
                  </el-form-item>
                  <el-form-item label="医疗机构名称">
                    <el-input v-model.trim="queryForm.orgName" placeholder="请输入医疗机构名称" clearable
                      @keyup.enter.native="queryData" />
                  </el-form-item>
                  <el-form-item>
                    <el-button icon="el-icon-search" type="primary" @click="queryData">查 询</el-button>
                    <el-button icon="el-icon-refresh-left" @click="reseat">重 置</el-button>
                  </el-form-item>
                </el-form>
              </vab-query-form-left-panel>
            </vab-query-form>

            <el-table :data="list" border stripe class="w" v-loading="listLoading"
              :element-loading-text="elementLoadingText" height="calc(100vh - 320px)">
              <el-table-column show-overflow-tooltip label="序号" align="center" width="80px" fixed="left">
                <template slot-scope="scope">
                  <span v-text="getIndex(scope.$index)"> </span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="tcq" label="所属地区" width="160px" align="center"
                fixed="left"></el-table-column>
              <el-table-column show-overflow-tooltip prop="orgCode" label="医疗机构编码" width="160px" align="center"
                fixed="left">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="orgName" label="医疗机构名称" width="200px" align="center"
                fixed="left">
              </el-table-column>
              <el-table-column show-overflow-tooltip prop="jb" label="级别" width="80px" align="center" fixed="left">
              </el-table-column>
              <el-table-column label="职工医保（万元）" align="center">
                <el-table-column :label="table_title_add" align="center" :class-name="'add-table-title'">
                  <el-table-column show-overflow-tooltip prop="zgLjFszfy" label="发生总费用" width="160px" align="center"
                    :class-name="'add-table-title'"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjTcjjfsje" label="统筹基金发生金额" align="center"
                    width="160px" :class-name="'add-table-title'"></el-table-column>
                  <el-table-column prop="zgLjDbjjfsje" label="大病基金发生金额" width="160px" align="center"
                    :class-name="'add-table-title'"></el-table-column>
                  <el-table-column prop="zgLjGwyjjfsje" label="公务员基金发生金额" width="160px" align="center"
                    :class-name="'add-table-title'"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjScjjfsje" label="伤残基金发生金额" align="center"
                    width="160px" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjJzjjfsje" label="救助基金发生金额" align="center"
                    width="160px" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjGzjjfsje" label="个账基金发生金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjXj" label="现金" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>

                  <el-table-column show-overflow-tooltip prop="zgLjJsxj" label="结算小计" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjTcjjjsje" label="统筹基金结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjDbjjjsje" label="大病基金结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjGwyjjjsje" label="公务员基金结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjScjjjsje" label="伤残基金结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjJzjjjsje" label="救助基金结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjGzjjjsje" label="个账基金结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>

                  <el-table-column show-overflow-tooltip prop="zgLjJjbfxj" label="基金拨付小计" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjJjbfje" label="基金拨付金额" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjKhbzj" label="考核保证金累计" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgLjKk" label="累计扣款" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>

                </el-table-column>

                <el-table-column :label="table_title_current" align="center" :class-name="'current-table-title'">

                  <el-table-column show-overflow-tooltip prop="zgDyFszfy" label="发生总费用" width="160px" align="center"
                    :class-name="'current-table-title'"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyTcjjfsje" label="统筹基金发生金额" align="center"
                    width="160px" :class-name="'current-table-title'"></el-table-column>
                  <el-table-column prop="zgDyDbjjfsje" label="大病基金发生金额" width="160px" align="center"
                    :class-name="'current-table-title'"></el-table-column>
                  <el-table-column prop="zgDyGwyjjfsje" label="公务员基金发生金额" width="160px" align="center"
                    :class-name="'current-table-title'"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyScjjfsje" label="伤残基金发生金额" align="center"
                    width="160px" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyJzjjfsje" label="救助基金发生金额" align="center"
                    width="160px" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyGzjjfsje" label="个账基金发生金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyXj" label="现金" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>

                  <el-table-column show-overflow-tooltip prop="zgDyJsxj" label="结算小计" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyTcjjjsje" label="统筹基金结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyDbjjjsje" label="大病基金结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyGwyjjjsje" label="公务员基金结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyScjjjsje" label="伤残基金结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyJzjjjsje" label="救助基金结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyGzjjjsje" label="个账基金结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>

                  <el-table-column show-overflow-tooltip prop="zgDyJjbfxj" label="基金拨付小计" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyJjbfje" label="基金拨付金额" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyKhbzj" label="当月考核保证金" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="zgDyKk" label="当月扣款" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                </el-table-column>
              </el-table-column>

              <el-table-column label="居民医保（万元）" align="center" :class-name="'current-table-title-parent'">

                <el-table-column :label="table_title_add" align="center" :class-name="'add-table-title'">
                  <el-table-column show-overflow-tooltip prop="jmLjFszfy" label="发生总费用" width="160px" align="center"
                    :class-name="'add-table-title'"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjTcjjfsje" label="统筹基金发生金额" align="center"
                    width="160px" :class-name="'add-table-title'"></el-table-column>
                  <el-table-column prop="jmLjDbjjfsje" label="大病基金发生金额" width="160px" align="center"
                    :class-name="'add-table-title'"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjJzjjfsje" label="救助基金发生金额" align="center"
                    width="160px" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjGjzhfsje" label="共济账户发生金额" align="center"
                    width="160px" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjXj" label="现金" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjJsxj" label="结算小计" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjTcjjjsje" label="统筹基金结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjDbjjjsje" label="大病基金结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjJzjjjsje" label="救助基金结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjGjzhjsje" label="共济账户结算金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>

                  <el-table-column show-overflow-tooltip prop="jmLjJjbfxj" label="基金拨付小计" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjJjbfje" label="基金拨付金额" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjDbsbbfje" label="大病商保拨付金额" width="160px"
                    align="center" :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjKhbzj" label="考核保证金累计" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmLjKk" label="累计扣款" width="160px" align="center"
                    :class-name="'add-table-title'">
                  </el-table-column>

                </el-table-column>

                <el-table-column :label="table_title_current" align="center" :class-name="'current-table-title'">

                  <el-table-column show-overflow-tooltip prop="jmDyFszfy" label="发生总费用" width="160px" align="center"
                    :class-name="'current-table-title'"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyTcjjfsje" label="统筹基金发生金额" align="center"
                    width="160px" :class-name="'current-table-title'"></el-table-column>
                  <el-table-column prop="jmDyDbjjfsje" label="大病基金发生金额" width="160px" align="center"
                    :class-name="'current-table-title'"></el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyJzjjfsje" label="救助基金发生金额" align="center"
                    width="160px" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyGjzhfsje" label="共济账户发生金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyXj" label="现金" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>

                  <el-table-column show-overflow-tooltip prop="jmDyJsxj" label="结算小计" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyTcjjjsje" label="统筹基金结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyDbjjjsje" label="大病基金结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyJzjjjsje" label="救助基金结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyGjzhjsje" label="共济账户结算金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyJjbfxj" label="基金拨付小计" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyJjbfje" label="基金拨付金额" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyDbsbbfje" label="大病商保拨付金额" width="160px"
                    align="center" :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyKhbzj" label="当月考核保证金" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                  <el-table-column show-overflow-tooltip prop="jmDyKk" label="当月扣款" width="160px" align="center"
                    :class-name="'current-table-title'">
                  </el-table-column>
                </el-table-column>
              </el-table-column>

            </el-table>
            <el-pagination background :current-page="queryForm.pageNo" :page-size="queryForm.pageSize" :layout="layout"
              :total="total" @size-change="handleSizeChange" @current-change="handleCurrentChange"></el-pagination>

          </div>
        </el-tab-pane>
      </el-tabs>

      <div class="drawer_footer">
        <el-button type="primary" @click="handleDownLoad">下载数据</el-button>
        <el-button @click="close">关 闭</el-button>
      </div>
    </div>

  </el-drawer>
</template>

<script>
import { approNoticeViewSummary, approNoticeViewDataDetail, approNoticeDownloadSummary, approNoticeDownloadDataDetail } from '@/api/disburse.js';

export default {
  name: 'dataInfo',
  components: {},
  data() {
    return {
      list: [],
      zgSummaries: [], //职工医保
      jmSummaries: [],//居民医保
      listLoading: true,
      layout: 'total, sizes, prev, pager, next, jumper',
      total: 0,
      elementLoadingText: '正在加载...',
      queryForm: {
        pageNo: 1,
        pageSize: 10,
        orgCode: "",
        orgName: '',
        appropNoticeId: '',
        tcq: '',
        year: '',
        month: ''
      },
      title: '',
      dialog: false,
      loading: false,
      formLabelWidth: '100px',
      timer: null,
      activeName: 'first',
      table_title_add: '',
      table_title_current: '',
      rowData: {}
    }
  },
  created() {

  },
  mounted() {
    window.addEventListener('resize', this.handleResize);
  },
  beforeDestroy() {
    window.removeEventListener('resize', this.handleResize);
  },
  methods: {

    handleResize() {

      this.$nextTick(() => {
        if (this.$refs.myTableZg) {
          console.log('333');
          this.$refs.myTableZg.doLayout();
        }
        if (this.$refs.myTableLj) {
          console.log('444');
          this.$refs.myTableLj.doLayout();
        }
      });
    },
    showDia(row) {
      var str = '1-'
      if (row.month == 1) {
        str = ''
      }
      this.title = '数据查看(' + row.year + '年' + str + row.month + '月累计及' + row.month + '月当月)'
      this.activeName = 'first'
      this.table_title_add = row.year + '年' + str + row.month + '月累计'
      this.table_title_current = row.year + '年' + row.month + '月当月'
      console.log('87777');
      this.rowData = row
      console.log('222222');
      this.fetchData()
      this.dialog = true
    },

    getIndex($index) {
      //表格序号
      return (this.queryForm.pageNo - 1) * this.queryForm.pageSize + $index + 1
    },
    handleSizeChange(val) {
      this.queryForm.pageSize = val
      this.fetchData()
    },
    handleCurrentChange(val) {
      this.queryForm.pageNo = val
      this.fetchData()
    },
    queryData() {
      this.queryForm.pageNo = 1
      this.fetchData()
    },
    fetchData() {
      var that = this
      this.listLoading = true
      if (this.activeName == 'first') {//汇总数据
        approNoticeViewSummary({ appropNoticeId: this.rowData.id, tcq: this.rowData.tcq, year: this.rowData.year, month: this.rowData.month }).then((res) => {
          that.listLoading = false
          console.log('9999');
          if (res.code == 0) {
            that.zgSummaries = res.data.zgSummaries ? res.data.zgSummaries : []
            that.jmSummaries = res.data.jmSummaries ? res.data.jmSummaries : []
          }
        })
      } else {//数据明细
        this.queryForm.appropNoticeId = this.rowData.id
        this.queryForm.tcq = this.rowData.tcq
        this.queryForm.year = this.rowData.year
        this.queryForm.month = this.rowData.month
        approNoticeViewDataDetail(that.queryForm).then((res) => {
          that.listLoading = false
          console.log('8888');
          if (res.code == 0) {
            that.list = res.data.records ? res.data.records : []
            that.total = res.data.total
          }
        })
      }


    },

    //关闭查看
    close() {
      this.dialog = false
    },

    //重置数据明细查询
    reseat() {
      this.queryForm = this.$options.data().queryForm
      this.fetchData()
    },

    //切换tab
    handleClick(tab, event) {
      this.fetchData()
    },
    handleDownLoad() {
      this.loading = true
      if (this.activeName == 'first') {//汇总数据下载
        approNoticeDownloadSummary({ appropNoticeId: this.rowData.id, tcq: this.rowData.tcq, year: this.rowData.year, month: this.rowData.month }).then((res) => {
          let fileNames = res.headers['content-disposition'] // 获取到Content-Disposition;filename
          let regFileNames = fileNames.match(/=(.*)$/)[1] // 文件名称  截取=后面的文件名称
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(regFileNames)
          link.href = objectUrl
          link.click()
          this.loading = false;

        })
      } else {//数据明细下载
        approNoticeDownloadDataDetail({ appropNoticeId: this.rowData.id, tcq: this.rowData.tcq, year: this.rowData.year, month: this.rowData.month, orgCode: this.queryForm.orgCode, orgName: this.queryForm.orgName }).then((res) => {
          let fileNames = res.headers['content-disposition'] // 获取到Content-Disposition;filename
          let regFileNames = fileNames.match(/=(.*)$/)[1] // 文件名称  截取=后面的文件名称
          let objectUrl = URL.createObjectURL(new Blob([res.data]))
          const link = document.createElement('a')
          link.download = decodeURI(regFileNames)
          link.href = objectUrl
          link.click()
          this.loading = false;

        })
      }


    },
  },
}
</script>
<style lang="scss" scoped>
.drawer_content {
  padding: 20px;
}

.drawer_main {
  padding: 20px 0;
}

::v-deep {
  .el-table {
    th.el-table__cell {
      background-color: #f2f8fd !important;
    }
  }

  .el-table th.add-table-title {
    background-color: #fff1e5 !important;
  }

  .el-table th.current-table-title {
    background-color: #fffee5 !important;
  }

  .el-table th.current-table-title-parent {
    background-color: #e6ecf9 !important;
  }

  // tab切换时出现蓝色边框问题解决
  .el-tabs__item:focus.is-active.is-focus:not(:active) {
    box-shadow: none !important; //切换阴影
    border-radius: 0 !important;
  }

  .box_drawer .el-drawer__header {
    margin-bottom: 0px;
    font-size: 18px;
  }
}

::v-deep .el-table__fixed {
  height: auto !important;
  bottom: 16px; // 不加这个会看不到表头
}
</style>