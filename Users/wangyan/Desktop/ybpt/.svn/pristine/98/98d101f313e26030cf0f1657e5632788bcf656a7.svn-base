package com.jsdc.ybpt.appropNotice.entity;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 拨付通知汇总分析(APPROP_NOTICE_SUMMARY_ANALYSE)数据库映射实体类
 *
 * @author wangxiao
 * @date 2024-05-24 10:42:39
 */
@Data
public class AppropNoticeSummaryAnalyse {

    @TableId(value = "ID")
    @Id
    private String id;

    @TableField("APPROP_NOTICE_ID")
    //@ApiModelProperty(value = "拨付通知主键")
    private String appropNoticeId;

    @TableField("TCQ")
    //@ApiModelProperty(value = "统筹区(医疗机构归属地)")
    private String tcq;

    @TableField("RYLB")
    //@ApiModelProperty(value = "人员类型:1-职工，2-居民")
    private String rylb;

    @TableField("YLZFY_DQLJFSE")
    //@ApiModelProperty(value = "医疗总费用-当期累计发生额")
    private BigDecimal ylzfyDqljfse;

    @TableField("YLZFY_TQLJFSE")
    //@ApiModelProperty(value = "医疗总费用-上年同期累计发生额")
    private BigDecimal ylzfyTqljfse;

    @TableField("YLZFY_TB")
    //@ApiModelProperty(value = "医疗总费用-同比")
    private BigDecimal ylzfyTb;

    @TableField("YLZFY_BNZB")
    //@ApiModelProperty(value = "医疗总费用-本年占比")
    private BigDecimal ylzfyBnzb;

    @TableField("TCJJ_DQLJFSE")
    //@ApiModelProperty(value = "统筹基金-当期累计发生额")
    private BigDecimal tcjjDqljfse;

    @TableField("TCJJ_TQLJFSE")
    //@ApiModelProperty(value = "统筹基金-上年同期累计发生额")
    private BigDecimal tcjjTqljfse;

    @TableField("TCJJ_TB")
    //@ApiModelProperty(value = "统筹基金-同比")
    private BigDecimal tcjjTb;

    @TableField("TCJJ_BNZB")
    //@ApiModelProperty(value = "统筹基金-本年占比")
    private BigDecimal tcjjBnzb;

    @TableField("YJFJE")
    //@ApiModelProperty(value = "应结付金额")
    private BigDecimal yjfje;

    @TableField("YJFE")
    //@ApiModelProperty(value = "已结付额")
    private BigDecimal yjfe;

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
    //@ApiModelProperty(value = "年份")
    private Integer year;

    @TableField("MONTH")
    //@ApiModelProperty(value = "月份")
    private Integer month;

}

