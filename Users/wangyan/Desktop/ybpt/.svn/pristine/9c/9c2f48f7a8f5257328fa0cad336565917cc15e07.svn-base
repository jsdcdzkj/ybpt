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
 * 拨付通知-居民大病保险实际支付(APPROP_NOTICE_JMDBBXSJZF)数据库映射实体类
 *
 * @author wangxiao
 * @date 2024-05-17 15:32:03
 */
@Data
@TableName("APPROP_NOTICE_JMDBBXSJZF")
public class AppropNoticeJmdbbxsjzf {

    @TableId(value = "ID")
    @Id
    private String id;

    @TableField("APPROP_NOTICE_ID")
    //@ApiModelProperty(value = "拨付通知主键")
    private String appropNoticeId;

    @TableField("ORG_CODE")
    //@ApiModelProperty(value = "定点机构编码")
    @Alias("机构编码")
    private String orgCode;

    @TableField("ORG_NAME")
    //@ApiModelProperty(value = "定点机构名称")
    @Alias("机构名称")
    private String orgName;

    @TableField("ZFJE")
    //@ApiModelProperty(value = "支付金额")
    @Alias("支付金额")
    private BigDecimal zfje;

    @TableField("TCQ")
    //@ApiModelProperty(value = "统筹区")
    @Alias("统筹区")
    private String tcq;

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

