package com.jsdc.ybpt.appropNotice.vo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 职工拨付通知汇总数据导出excel实体
 * @Author WangXiao
 * @Date 2024/5/23
 */
@Data
public class ZgAppropNoticeSummaryExcelVO {

    @Alias("医疗机构归属地")
    private String tcq;

    @Alias("发生总费用")
    private BigDecimal ljFszfy;

    @Alias("统筹基金发生金额")
    private BigDecimal ljTcjjfsje;

    @Alias("大病基金发生金额")
    private BigDecimal ljDbjjfsje;

    @Alias("公务员基金发生金额")
    private BigDecimal ljGwyjjfsje;

    @Alias("救助基金发生金额")
    private BigDecimal ljJzjjfsje;

    @Alias("伤残基金发生金额")
    private BigDecimal ljScjjfsje;

    @Alias("个账基金发生金额")
    private BigDecimal ljGzjjfsje;

    @Alias("现金")
    private BigDecimal ljXj;

    @Alias("结算小计")
    private BigDecimal ljJsxj;

    @Alias("统筹基金结算金额")
    private BigDecimal ljTcjjjsje;

    @Alias("大病基金结算金额")
    private BigDecimal ljDbjjjsje;

    @Alias("公务员基金结算金额")
    private BigDecimal ljGwyjjjsje;

    @Alias("救助基金结算金额")
    private BigDecimal ljJzjjjsje;

    @Alias("伤残基金结算金额")
    private BigDecimal ljScjjjsje;

    @Alias("个账基金结算金额")
    private BigDecimal ljGzjjjsje;

    @Alias("基金拨付小计")
    private BigDecimal ljJjbfxj;

    @Alias("基金拨付金额")
    private BigDecimal ljJjbfje;

    @Alias("考核保证金累计")
    private BigDecimal ljKhbzj;

    @Alias("累计扣款")
    private BigDecimal ljKk;

    @Alias("发生总费用")
    private BigDecimal dyFszfy;

    @Alias("统筹基金发生金额")
    private BigDecimal dyTcjjfsje;

    @Alias("大病基金发生金额")
    private BigDecimal dyDbjjfsje;

    @Alias("公务员基金发生金额")
    private BigDecimal dyGwyjjfsje;

    @Alias("救助基金发生金额")
    private BigDecimal dyJzjjfsje;

    @Alias("伤残基金发生金额")
    private BigDecimal dyScjjfsje;

    @Alias("个账基金发生金额")
    private BigDecimal dyGzjjfsje;

    @Alias("现金")
    private BigDecimal dyXj;

    @Alias("结算小计")
    private BigDecimal dyJsxj;

    @Alias("统筹基金结算金额")
    private BigDecimal dyTcjjjsje;

    @Alias("大病基金结算金额")
    private BigDecimal dyDbjjjsje;

    @Alias("公务员基金结算金额")
    private BigDecimal dyGwyjjjsje;

    @Alias("救助基金结算金额")
    private BigDecimal dyJzjjjsje;

    @Alias("伤残基金结算金额")
    private BigDecimal dyScjjjsje;

    @Alias("个账基金结算金额")
    private BigDecimal dyGzjjjsje;

    @Alias("基金拨付小计")
    private BigDecimal dyJjbfxj;

    @Alias("基金拨付金额")
    private BigDecimal dyJjbfje;

    @Alias("当月考核保证金")
    private BigDecimal dyKhbzj;

    @Alias("当月扣款")
    private BigDecimal dyKk;

}
