package com.jsdc.ybpt.appropNotice.vo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 居民拨付通知汇总数据导出excel实体
 * @Author WangXiao
 * @Date 2024/5/23
 */
@Data
public class JmAppropNoticeSummaryExcelVO {

    @Alias("医疗机构归属地")
    private String tcq;

    @Alias("发生总费用")
    private BigDecimal ljFszfy;

    @Alias("统筹基金发生金额")
    private BigDecimal ljTcjjfsje;

    @Alias("大病基金发生金额")
    private BigDecimal ljDbjjfsje;

    @Alias("救助基金发生金额")
    private BigDecimal ljJzjjfsje;

    @Alias("共济账户发生金额")
    private BigDecimal ljGjzhfsje;

    @Alias("现金")
    private BigDecimal ljXj;

    @Alias("结算小计")
    private BigDecimal ljJsxj;

    @Alias("统筹基金结算金额")
    private BigDecimal ljTcjjjsje;

    @Alias("大病基金结算金额")
    private BigDecimal ljDbjjjsje;

    @Alias("救助基金结算金额")
    private BigDecimal ljJzjjjsje;

    @Alias("共济账户结算金额")
    private BigDecimal ljGjzhjsje;

    @Alias("基金拨付小计")
    private BigDecimal ljJjbfxj;

    @Alias("基金拨付金额")
    private BigDecimal ljJjbfje;

    @Alias("大病商保拨付金额")
    private BigDecimal ljDbsbbfje;

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

    @Alias("救助基金发生金额")
    private BigDecimal dyJzjjfsje;

    @Alias("共济账户发生金额")
    private BigDecimal dyGjzhfsje;

    @Alias("现金")
    private BigDecimal dyXj;

    @Alias("结算小计")
    private BigDecimal dyJsxj;

    @Alias("统筹基金结算金额")
    private BigDecimal dyTcjjjsje;

    @Alias("大病基金结算金额")
    private BigDecimal dyDbjjjsje;

    @Alias("救助基金结算金额")
    private BigDecimal dyJzjjjsje;

    @Alias("共济账户结算金额")
    private BigDecimal dyGjzhjsje;

    @Alias("基金拨付小计")
    private BigDecimal dyJjbfxj;

    @Alias("基金拨付金额")
    private BigDecimal dyJjbfje;

    @Alias("大病商保拨付金额")
    private BigDecimal dyDbsbbfje;

    @Alias("当月考核保证金")
    private BigDecimal dyKhbzj;

    @Alias("当月扣款")
    private BigDecimal dyKk;

}
