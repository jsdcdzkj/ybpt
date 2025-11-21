package com.jsdc.ybpt.appropNotice.vo;

import cn.hutool.core.annotation.Alias;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @Description 数据明细分析导出excel实体
 * @Author WangXiao
 * @Date 2024/5/23
 */
@Data
public class DetailAnalyseExcelVO {

    @Alias("所属地区")
    private String tcq;

    @Alias("医疗机构编码")
    private String orgCode;

    @Alias("医疗机构名称")
    private String orgName;

    @Alias("级别")
    private String jb;

    @Alias("当期累计发生额")
    private BigDecimal zgYlzfyDqljfse;

    @Alias("上年同期累计发生额")
    private BigDecimal zgYlzfyTqljfse;

    @Alias("同比")
    private BigDecimal zgYlzfyTb;

    @Alias("本年占比")
    private BigDecimal zgYlzfyBnzb;

    @Alias("当期累计发生额")
    private BigDecimal zgTcjjDqljfse;

    @Alias("上年同期累计发生额")
    private BigDecimal zgTcjjTqljfse;

    @Alias("同比")
    private BigDecimal zgTcjjTb;

    @Alias("本年占比")
    private BigDecimal zgTcjjBnzb;

    @Alias("应结付金额")
    private BigDecimal zgYjfje;

    @Alias("已结付额")
    private BigDecimal zgYjfe;

    @Alias("当期累计发生额")
    private BigDecimal jmYlzfyDqljfse;

    @Alias("上年同期累计发生额")
    private BigDecimal jmYlzfyTqljfse;

    @Alias("同比")
    private BigDecimal jmYlzfyTb;

    @Alias("本年占比")
    private BigDecimal jmYlzfyBnzb;

    @Alias("当期累计发生额")
    private BigDecimal jmTcjjDqljfse;

    @Alias("上年同期累计发生额")
    private BigDecimal jmTcjjTqljfse;

    @Alias("同比")
    private BigDecimal jmTcjjTb;

    @Alias("本年占比")
    private BigDecimal jmTcjjBnzb;

    @Alias("应结付金额")
    private BigDecimal jmYjfje;

    @Alias("已结付额")
    private BigDecimal jmYjfe;

}
