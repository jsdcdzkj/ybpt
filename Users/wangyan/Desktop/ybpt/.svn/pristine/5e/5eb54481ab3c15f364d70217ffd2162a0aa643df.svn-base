package com.jsdc.ybpt.appropNotice.entity;


import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 拨付通知-月结算(APPROP_NOTICE_MONTH_SETTLE)数据库映射实体类
 *
 * @author wangxiao
 * @date 2024-05-17 15:32:03
 */
@Data
@TableName("APPROP_NOTICE_MONTH_SETTLE")
public class AppropNoticeMonthSettle {

    @TableId(value = "ID")
    @Id
    private String id;

    @TableField("APPROP_NOTICE_ID")
    //@ApiModelProperty(value = "拨付通知主键")
    private String appropNoticeId;

    @TableField("TCQ")
    //@ApiModelProperty(value = "统筹区(所属地区)")
    @Alias("所属地区")
    private String tcq;

    @TableField("ORG_CODE")
    //@ApiModelProperty(value = "定点机构编码")
    @Alias("医疗机构编码")
    private String orgCode;

    @TableField("ORG_NAME")
    //@ApiModelProperty(value = "定点机构名称")
    @Alias("医疗机构名称")
    private String orgName;

    @TableField("JB")
    //@ApiModelProperty(value = "级别（医保收费等级）")
    @Alias("级别（医保收费等级）")
    private String jb;

    @TableField("ZGJJBFJE")
    //@ApiModelProperty(value = "本月职工医保基金拨付金额")
    @Alias("基金拨付金额")
    private BigDecimal zgjjbfje;

    @TableField("ZGKHBZJLJ")
    //@ApiModelProperty(value = "本月职工医保预留考核保证金累计")
    @Alias("预留考核保证金累计")
    private BigDecimal zgkhbzjlj;

    @TableField("ZGBYZFLJ")
    //@ApiModelProperty(value = "本月职工医保基金不予支付累计")
    @Alias("不予支付累计")
    private BigDecimal zgbyzflj;

    @TableField("JMJJBFJE")
    //@ApiModelProperty(value = "本月居民医保基金拨付金额")
    @Alias("基金拨付金额")
    private BigDecimal jmjjbfje;

    @TableField("JMKHBZJLJ")
    //@ApiModelProperty(value = "本月居民医保预留考核保证金累计")
    @Alias("预留考核保证金累计")
    private BigDecimal jmkhbzjlj;

    @TableField("JMBYZFLJ")
    //@ApiModelProperty(value = "本月居民医保不予支付累计")
    @Alias("不予支付累计")
    private BigDecimal jmbyzflj;

    @TableField("IS_DEL")
    //@ApiModelProperty(value = "是否删除：0-否，1-是")
    private String isDel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
    //@ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField("CREATE_USER")
    //@ApiModelProperty(value = "创建人主键")
    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_TIME")
    //@ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @TableField("UPDATE_USER")
    //@ApiModelProperty(value = "修改人主键")
    private String updateUser;

    @TableField("YEAR")
//    @ApiModelProperty(value = "年份")
    private Integer year;

    @TableField("MONTH")
//    @ApiModelProperty(value = "月份")
    private Integer month;

}

