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
 * 拨付通知数据明细分析(APPROP_NOTICE_DETAIL_ANALYSE)数据库映射实体类
 *
 * @author wangxiao
 * @date 2024-05-24 10:42:37
 */
@Data
public class AppropNoticeDetailAnalyse {

    @TableId(value = "ID")
    @Id
    private String id;

    @TableField("APPROP_NOTICE_ID")
    //@ApiModelProperty(value = "拨付通知主键")
    private String appropNoticeId;

    @TableField("TCQ")
    //@ApiModelProperty(value = "统筹区(医疗机构归属地)")
    private String tcq;

    @TableField("ORG_CODE")
    //@ApiModelProperty(value = "定点机构编码")
    private String orgCode;

    @TableField("ORG_NAME")
    //@ApiModelProperty(value = "定点机构名称")
    private String orgName;

    @TableField("JB")
    //@ApiModelProperty(value = "级别")
    private String jb;

    @TableField("ZG_YLZFY_DQLJFSE")
    //@ApiModelProperty(value = "职工-医疗总费用-当期累计发生额")
    private BigDecimal zgYlzfyDqljfse;

    @TableField("ZG_YLZFY_TQLJFSE")
    //@ApiModelProperty(value = "职工-医疗总费用-上年同期累计发生额")
    private BigDecimal zgYlzfyTqljfse;

    @TableField("ZG_YLZFY_TB")
    //@ApiModelProperty(value = "职工-医疗总费用-同比")
    private BigDecimal zgYlzfyTb;

    @TableField("ZG_YLZFY_BNZB")
    //@ApiModelProperty(value = "职工-医疗总费用-本年占比")
    private BigDecimal zgYlzfyBnzb;

    @TableField("ZG_TCJJ_DQLJFSE")
    //@ApiModelProperty(value = "职工-统筹基金-当期累计发生额")
    private BigDecimal zgTcjjDqljfse;

    @TableField("ZG_TCJJ_TQLJFSE")
    //@ApiModelProperty(value = "职工-统筹基金-上年同期累计发生额")
    private BigDecimal zgTcjjTqljfse;

    @TableField("ZG_TCJJ_TB")
    //@ApiModelProperty(value = "职工-统筹基金-同比")
    private BigDecimal zgTcjjTb;

    @TableField("ZG_TCJJ_BNZB")
    //@ApiModelProperty(value = "职工-统筹基金-本年占比")
    private BigDecimal zgTcjjBnzb;

    @TableField("ZG_YJFJE")
    //@ApiModelProperty(value = "职工-应结付金额")
    private BigDecimal zgYjfje;

    @TableField("ZG_YJFE")
    //@ApiModelProperty(value = "职工-已结付额")
    private BigDecimal zgYjfe;

    @TableField("JM_YLZFY_DQLJFSE")
    //@ApiModelProperty(value = "居民-医疗总费用-当期累计发生额")
    private BigDecimal jmYlzfyDqljfse;

    @TableField("JM_YLZFY_TQLJFSE")
    //@ApiModelProperty(value = "居民-医疗总费用-上年同期累计发生额")
    private BigDecimal jmYlzfyTqljfse;

    @TableField("JM_YLZFY_TB")
    //@ApiModelProperty(value = "居民-医疗总费用-同比")
    private BigDecimal jmYlzfyTb;

    @TableField("JM_YLZFY_BNZB")
    //@ApiModelProperty(value = "居民-医疗总费用-本年占比")
    private BigDecimal jmYlzfyBnzb;

    @TableField("JM_TCJJ_DQLJFSE")
    //@ApiModelProperty(value = "居民-统筹基金-当期累计发生额")
    private BigDecimal jmTcjjDqljfse;

    @TableField("JM_TCJJ_TQLJFSE")
    //@ApiModelProperty(value = "居民-统筹基金-上年同期累计发生额")
    private BigDecimal jmTcjjTqljfse;

    @TableField("JM_TCJJ_TB")
    //@ApiModelProperty(value = "居民-统筹基金-同比")
    private BigDecimal jmTcjjTb;

    @TableField("JM_TCJJ_BNZB")
    //@ApiModelProperty(value = "居民-统筹基金-本年占比")
    private BigDecimal jmTcjjBnzb;

    @TableField("JM_YJFJE")
    //@ApiModelProperty(value = "居民-应结付金额")
    private BigDecimal jmYjfje;

    @TableField("JM_YJFE")
    //@ApiModelProperty(value = "居民-已结付额")
    private BigDecimal jmYjfe;

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

