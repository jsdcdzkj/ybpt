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
 * 拨付通知-应结算(APPROP_NOTICE_SETTLE)数据库映射实体类
 *
 * @author wangxiao
 * @date 2024-05-17 15:32:04
 */
@Data
@TableName("APPROP_NOTICE_SETTLE")
public class AppropNoticeSettle {

    @TableId(value = "ID")
    @Id
    private String id;

    @TableField("APPROP_NOTICE_ID")
    //@ApiModelProperty(value = "拨付通知主键")
    private String appropNoticeId;

    @TableField("ORG_CODE")
    //@ApiModelProperty(value = "定点机构编码")
    @Alias("定点机构编码")
    private String orgCode;

    @TableField("ORG_NAME")
    //@ApiModelProperty(value = "定点机构名称")
    @Alias("定点机构名称")
    private String orgName;

    @TableField("TCQ")
    //@ApiModelProperty(value = "统筹区")
    @Alias("统筹区")
    private String tcq;

    @TableField("JSZCMC")
    //@ApiModelProperty(value = "结算政策名称")
    @Alias("结算政策名称")
    private String jszcmc;

    @TableField("JJLB")
    //@ApiModelProperty(value = "基金类别")
    @Alias("基金类别")
    private String jjlb;

    @TableField("ZGTCJJ")
    //@ApiModelProperty(value = "职工统筹基金")
    @Alias("职工统筹基金")
    private BigDecimal zgtcjj;

    @TableField("JMTCJJ")
    //@ApiModelProperty(value = "居民统筹基金")
    @Alias("居民统筹基金")
    private BigDecimal jmtcjj;

    @TableField("DEJJ")
    //@ApiModelProperty(value = "大额基金")
    @Alias("大额基金")
    private BigDecimal dejj;

    @TableField("GWYBZJJ")
    //@ApiModelProperty(value = "公务员补助基金")
    @Alias("公务员补助基金")
    private BigDecimal gwybzjj;

    @TableField("ZGDBJJ")
    //@ApiModelProperty(value = "职工大病基金")
    @Alias("职工大病基金")
    private BigDecimal zgdbjj;

    @TableField("JMDBJJ")
    //@ApiModelProperty(value = "居民大病基金")
    @Alias("居民大病基金")
    private BigDecimal jmdbjj;

    @TableField("yljzjj")
    //@ApiModelProperty(value = "医疗救助基金")
    @Alias("医疗救助基金")
    private BigDecimal yljzjj;

    @TableField("CJJRBZJJ")
    //@ApiModelProperty(value = "一至六级残疾军人补助基金")
    @Alias("一至六级残疾军人补助基金")
    private BigDecimal cjjrbzjj;

    @TableField("GRZH")
    //@ApiModelProperty(value = "个人账户")
    @Alias("个人账户")
    private BigDecimal grzh;

    @TableField("GJZH")
    //@ApiModelProperty(value = "共济账户")
    @Alias("共济账户")
    private BigDecimal gjzh;

    @TableField("SYJJ")
    //@ApiModelProperty(value = "生育基金")
    @Alias("生育基金")
    private BigDecimal syjj;

    @TableField("QXJZJJ")
    //@ApiModelProperty(value = "倾斜救助基金")
    @Alias("倾斜救助基金")
    private BigDecimal qxjzjj;

    @TableField("LXBXJJ")
    //@ApiModelProperty(value = "离休保险基金")
    @Alias("离休保险基金")
    private BigDecimal lxbxjj;

    @TableField("LNGWYZH")
    //@ApiModelProperty(value = "历年公务员账户")
    @Alias("历年公务员账户")
    private BigDecimal lngwyzh;

    @TableField("GWYZH")
    //@ApiModelProperty(value = "公务员账户")
    @Alias("公务员账户")
    private BigDecimal gwyzh;

    @TableField("DBJJ")
    //@ApiModelProperty(value = "地补基金")
    @Alias("地补基金")
    private BigDecimal dbjj;

    @TableField("LNGH")
    //@ApiModelProperty(value = "历年个账")
    @Alias("历年个账")
    private BigDecimal lngh;

    @TableField("QTZH")
    //@ApiModelProperty(value = "其他账户")
    @Alias("其他账户")
    private BigDecimal qtzh;

    @TableField("YDZYJJ")
    //@ApiModelProperty(value = "异地专用基金")
    @Alias("异地专用基金")
    private BigDecimal ydzyjj;

    @TableField("HJ")
    //@ApiModelProperty(value = "合计")
    @Alias("合计")
    private BigDecimal hj;

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

