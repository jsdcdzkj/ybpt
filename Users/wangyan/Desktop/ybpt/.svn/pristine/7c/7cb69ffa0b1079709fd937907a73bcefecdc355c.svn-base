<template>
    <div class="index-container">
        <el-row :gutter="20">

            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <el-card class="card" shadow="never" closable="true">
                    <div slot="header">
                        <span class="tips">套餐占比</span>
                        <div class="rbox">
                            <el-select v-model="packInfoRatioForm.year" class="w120" placeholder="选择年份"
                                @change="getPackInfoRatioData" clearable>
                                <el-option v-for="(item, index) in packInfoYearList" :key="index" :label="item"
                                    :value="item">
                                </el-option>
                            </el-select>
                        </div>
                    </div>
                    <!-- <div class="sanjiao"></div> -->
                    <vab-chart ref="tczb" theme="vab-echarts-theme" :options="packageChart" />
                </el-card>
            </el-col>
            <el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <el-card class="cardbox" shadow="never">
                    <div slot="header">
                        <span class="tips">体检占比</span>
                        <div class="rbox">
                            <el-select v-model="physicalRatioForm.empCode" placeholder="选择用人单位"
                                @change="getEmployingInfoPhysicalRatioCheckedByOrgData" clearable>
                                <el-option v-for="(item, index) in employUnitList" :key="index" :label="item.emp_name"
                                    :value="item.emp_no">
                                </el-option>

                            </el-select>
                            <el-select v-model="physicalRatioForm.year" style="margin-left: 10px" class="w120"
                                placeholder="选择年份" clearable>
                                <!--                                <el-option label="2022年" value="1"></el-option>-->
                                <!--                                <el-option label="2021年" value="2"></el-option>-->
                                <!--                                <el-option label="2020年" value="3"></el-option>-->
                                <el-option v-for="(item, index) in physicalRatioYearList" :key="index" :label="item"
                                    :value="item">
                                </el-option>
                            </el-select>
                        </div>
                    </div>
                    <div>
                        <vab-chart ref="tjzb" theme="vab-echarts-theme" :options="physicalChart" />
                    </div>
                </el-card>
            </el-col>
            <!--<el-col :xs="24" :sm="24" :md="12" :lg="12" :xl="12">
                <el-card class="cardbox" shadow="never">
                    <div slot="header">
                        <span class="tips">组织占比</span>
                        <div class="rbox">
                            <el-select v-model="organizationTypeChart.year" class="w120" placeholder="选择年份">
                                <el-option label="2021年" value="2"></el-option>
                                <el-option label="2022年" value="1"></el-option>
                                <el-option label="2023年" value="3"></el-option>
                                <el-option label="2024年" value="3"></el-option>
                                <el-option label="2025年" value="3"></el-option>
                                <el-option label="2026年" value="3"></el-option>
                                &lt;!&ndash;                                <el-option&ndash;&gt;
                                &lt;!&ndash;                                    v-for="item in organizationTypeChart.year"&ndash;&gt;
                                &lt;!&ndash;                                    :key="item.value"&ndash;&gt;
                                &lt;!&ndash;                                    :label="item.label"&ndash;&gt;
                                &lt;!&ndash;                                    :value="item.value">&ndash;&gt;
                                &lt;!&ndash;                                </el-option>&ndash;&gt;
                            </el-select>
                        </div>
                    </div>

                    <div>
                        <vab-chart ref="zzzb" theme="vab-echarts-theme" :options="organizationTypeChart"/>
                    </div>
                </el-card>
            </el-col>-->
            <el-col :xs="24" :sm="24" :md="24" :lg="24" :xl="24">
                <el-card class="cardbox" shadow="never">
                    <div slot="header">
                        <span class="tips">本周预约人数及体检人数趋势</span>
                    </div>
                    <div>
                        <vab-chart ref="zzzb" theme="vab-echarts-theme" :options="examinationCountWeeklyChart" />
                    </div>
                </el-card>
            </el-col>
        </el-row>
    </div>
</template>

<script>
import {
    eChat, getEmployingInfoListCheckedByOrg,
    getEmployingInfoPhysicalRatioCheckedByOrg,
    getPackageChartYearList,
    getPackInfoRatio,
    getYearListPhysicalRatioCheckedByOrg
} from "@/api_check/statistic";
import VabChart from '@/plugins/echarts';

export default {
    name: 'Index',
    components: {
        VabChart,
    },
    data() {
        return {
            listLoading: true,
            packInfoYearList: [], //套餐年份列表
            packInfoRatioForm: {
                year: "",
            },

            employUnitList: [],//用人单位
            physicalRatioForm: {
                empCode: "",
                year: "",
            },

            physicalRatioYearList: [], // 体检状态占比

            timer: 0,
            //套餐占比
            packageChart: {
                year: [],
                tooltip: {
                    show: true,
                    formatter: '{b} : {c}',
                },

                legend: {
                    show: true,
                    icon: 'circle',
                    top: '10%',
                    left: '10%',
                    width: 50,
                    padding: [0, 5],
                    itemGap: 25,
                    data: ['通用套餐', '通用套餐+赠送项目'],
                    selectedMode: true,
                    orient: 'vertical',
                },
                series: [
                    {
                        name: 'Line 4',
                        type: 'pie',
                        clockWise: true,
                        hoverAnimation: false,
                        radius: ['65%', '75%'],

                        data: [
                            {
                                value: 100,
                                name: '通用套餐',
                            },
                            {
                                value: 600,
                                name: '总数',
                                tooltip: {
                                    show: false,
                                },
                                itemStyle: {
                                    normal: {
                                        color: 'rgba(0,0,0,0)',
                                        label: {
                                            show: false,
                                        },
                                        labelLine: {
                                            show: false,
                                        },
                                    },
                                    emphasis: {
                                        color: 'rgba(0,0,0,0)',
                                    },
                                },
                            },
                        ],
                    },
                    {
                        name: 'Line 3',
                        type: 'pie',
                        clockWise: true,
                        radius: ['50%', '60%'],
                        // itemStyle: {
                        //     normal: {
                        //         label: {
                        //             show: false,
                        //         },
                        //         labelLine: {
                        //             show: false,
                        //         },
                        //         // shadowBlur: 15,
                        //         // shadowColor: 'white',
                        //     },
                        // },
                        hoverAnimation: false,

                        data: [
                            {
                                value: 300,
                                name: '通用套餐+赠送项目',
                            },
                            {
                                value: 600,
                                name: '总数',
                                tooltip: {
                                    show: false,
                                },
                                itemStyle: {
                                    normal: {
                                        color: 'rgba(0,0,0,0)',
                                        label: {
                                            show: false,
                                        },
                                        labelLine: {
                                            show: false,
                                        },
                                    },
                                    emphasis: {
                                        color: 'rgba(0,0,0,0)',
                                    },
                                },
                            },
                        ],
                    },
                ],
            },
            physicalChart: {
                year: '',
                title: {
                    text: '体检占比',
                    left: 'center',
                    show: false,
                },
                tooltip: {
                    trigger: 'item',
                },
                legend: {
                    orient: 'horizontal',
                    left: 'center',
                },
                series: [
                    {
                        name: '体检占比',
                        type: 'pie',
                        radius: '50%',
                        data: [
                            { value: 1048, name: '待体检' },
                            { value: 735, name: '已体检' },
                            { value: 735, name: '已上传报告' },
                            { value: 735, name: '已过期' },
                        ],
                        emphasis: {
                            itemStyle: {
                                shadowBlur: 10,
                                shadowOffsetX: 0,
                                shadowColor: 'rgba(0, 0, 0, 0.5)',
                            },
                        },
                    },
                ],
            },
            /*organizationTypeChart: {
                year: '',
                tooltip: {
                    trigger: 'item',
                },
                legend: {
                    top: '5%',
                    left: 'center',
                },
                series: [
                    {
                        name: '组织占比',
                        type: 'pie',
                        radius: ['40%', '70%'],
                        avoidLabelOverlap: false,
                        itemStyle: {
                            borderRadius: 10,
                            borderColor: '#fff',
                            borderWidth: 2,
                        },
                        label: {
                            show: false,
                            position: 'center',
                        },
                        emphasis: {
                            label: {
                                show: true,
                                fontSize: '20',
                                fontWeight: 'bold',
                            },
                        },
                        labelLine: {
                            show: false,
                        },
                        data: [
                            {value: 1048, name: '自主方式'},
                            {value: 735, name: '非自主方式'},
                        ],
                    },
                ],
            },*/
            examinationCountWeeklyChart: {
                year: '',
                tooltip: {
                    trigger: 'axis'
                },
                legend: {
                    data: ['预约人数', '体检人数',]
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },

                xAxis: {
                    type: 'category',
                    boundaryGap: false,
                    data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
                },
                yAxis: {
                    type: 'value'
                },
                series: [
                    {
                        name: '预约人数',
                        type: 'line',
                        stack: 'Total',
                        data: [120, 132, 101, 134, 90, 230, 210]
                    },
                    {
                        name: '体检人数',
                        type: 'line',
                        stack: 'Total',
                        data: [220, 182, 191, 234, 290, 330, 310]
                    },

                ]
            },
            //卡片图标
            iconList: [
                {
                    icon: 'user-edit',
                    num: '30658',
                    title: '预约人数',
                    link: '#',
                    color: '#b37feb',
                },
                {
                    icon: 'user-md',
                    num: '1512',
                    title: '体检人数',
                    link: '',
                    color: '#69c0ff',
                },
            ],
        }
    },

    created() {
        this.getPackYearListData();
        this.getEmployUnitData();
        this.getPhysicalRatioYearList();
        this.getEmployingInfoPhysicalRatioCheckedByOrgData();
        this.eChat();
        this.getPackInfoRatioData();
    },
    beforeDestroy() {
    },
    mounted() {
    },
    methods: {
        async getPhysicalRatioYearList() {
            const { data } = await getYearListPhysicalRatioCheckedByOrg();
            this.physicalRatioYearList = data;
        },

        async getEmployUnitData() {
            const { data } = await getEmployingInfoListCheckedByOrg();
            this.employUnitList = data;
        },

        //体检占比
        async getEmployingInfoPhysicalRatioCheckedByOrgData() {
            this.listLoading = true;
            const { data } = await getEmployingInfoPhysicalRatioCheckedByOrg(this.physicalRatioForm);
            this.physicalChart.series[0].data[0].value = data.un;
            this.physicalChart.series[0].data[1].value = data.ed;
            this.physicalChart.series[0].data[2].value = data.scbg;
            this.physicalChart.series[0].data[3].value = data.gq;

        },

        async getPackYearListData() {
            const { data } = await getPackageChartYearList();
            this.packInfoYearList = data;
        },
        //套餐占比
        async getPackInfoRatioData() {
            this.listLoading = true;
            const { data } = await getPackInfoRatio(this.packInfoRatioForm);
            //套餐来源 (机构:1，通用:2 , 总计:3)

            this.$data.packageChart.series[0].data[0].value = data[2] // 通用套餐
            this.$data.packageChart.series[1].data[0].value = data[1] // 机构套餐
            this.$data.packageChart.series[0].data[1].value = data[3] // 套餐总数1
            this.$data.packageChart.series[1].data[1].value = data[3] // 套餐总数2
        },
        //折线图
        async eChat() {
            // this.listLoading = true;
            const { data } = await eChat();
            let res = data
            let day = []
            let count = []
            let count1 = []
            for (let index = res.length - 1; index >= 0; index--) {
                day.push(res[index].today);
                count.push(res[index].count)
                count1.push(res[index].count1)
            }
            this.examinationCountWeeklyChart.xAxis.data = day;
            this.examinationCountWeeklyChart.series[0].data = count;
            this.examinationCountWeeklyChart.series[1].data = count1;
        },

    },
}
</script>
<style lang="scss" scoped>
.index-container {
    padding: 0 !important;
    margin: 0 !important;
    background: #f5f7f8 !important;

    ::v-deep {
        .sanjiao {
            width: 0px;
            height: 0px;
            border-top: 10px solid transparent;
            border-right: 10px solid transparent;
            border-bottom: 10px solid red;
            border-left: 10px solid transparent;
        }

        .w120 {
            width: 120px;
        }

        .tips {
            margin-top: 8px;
        }

        .el-alert {
            padding: $base-padding;

            &--info.is-light {
                min-height: 82px;
                padding: $base-padding;
                margin-bottom: 15px;
                color: #909399;
                background-color: $base-color-white;
                border: 1px solid #ebeef5;
            }
        }

        .el-card__body {
            .echarts {
                width: 100%;
                height: 125px;
            }
        }
    }

    .card {
        min-height: 400px;

        ::v-deep {
            .el-card__header {
                div {
                    display: flex;
                    flex-direction: row;
                    justify-content: space-between;

                    svg {
                        margin: 0 5px;
                        color: #666;
                        cursor: pointer;
                    }
                }
            }

            .el-card__body {
                .echarts {
                    width: 100%;
                    height: 405px;
                }
            }
        }
    }

    .cardbox {
        ::v-deep {
            .el-card__header {
                div {
                    display: flex;
                    flex-direction: row;
                    justify-content: space-between;

                    svg {
                        margin: 0 5px;
                        color: #666;
                        cursor: pointer;
                    }
                }
            }

            .el-card__body {
                .echarts {
                    width: 100%;
                    height: 405px;
                }
            }
        }
    }

    .bottom {
        padding-top: 20px;
        margin-top: 5px;
        color: #595959;
        text-align: left;
        border-top: 1px solid $base-border-color;
    }

    .table {
        width: 100%;
        color: #666;
        border-collapse: collapse;
        background-color: #fff;

        td {
            position: relative;
            min-height: 20px;
            padding: 9px 15px;
            font-size: 14px;
            line-height: 20px;
            border: 1px solid #e6e6e6;

            &:nth-child(odd) {
                width: 20%;
                text-align: right;
                background-color: #f7f7f7;
            }
        }
    }

    .icon-panel {
        height: auto;
        text-align: left;
        cursor: pointer;
        padding-left: 20px;

        ::v-deep {
            .el-card__body {
                display: flex;
                padding: 10px;
            }
        }

        .box_num {
            display: block;
            margin-left: 30px;
            text-align: center;
            border-left: 1px solid #eee;
            padding-left: 30px;
        }

        .numbox {
            font-size: 40px;
            position: relative;
            font-weight: bold;
        }

        .numbox:after {
            display: inline;
            font-size: 12px;
            margin-left: 0px;
            color: #333;
            content: '个';
        }

        svg {
            padding-top: 20px;
            font-size: 36px;
            width: 40px;
        }

        p {
            margin-top: 10px;
        }
    }

    .icon-panel1 {
        height: auto;
        text-align: center;
        color: #fff;
        cursor: pointer;

        ::v-deep {
            .el-card__body {
                padding: 10px 5px;
            }
        }

        .numbox {
            font-size: 40px;
            position: relative;
            font-weight: bold;
        }

        svg {
            padding-top: 20px;
            font-size: 40px;
            width: 60px;
        }

        p {
            margin-top: 10px;
        }
    }

    .bottom-btn {
        button {
            margin: 5px 10px 15px 0;
        }
    }
}
</style>
