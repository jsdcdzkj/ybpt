package com.jsdc.ybpt.appropNotice.entity;


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
 * 拨付通知-汇总数据(APPROP_NOTICE_SUMMARY)数据库映射实体类
 *
 * @author wangxiao
 * @date 2024-05-17 15:32:04
 */
@Data
@TableName("APPROP_NOTICE_SUMMARY")
public class AppropNoticeSummary {

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

    @TableField("LJ_FSZFY")
    //@ApiModelProperty(value = "累计-发生总费用")
    private BigDecimal ljFszfy;

    @TableField("LJ_TCJJFSJE")
    //@ApiModelProperty(value = "累计-统筹基金发生金额")
    private BigDecimal ljTcjjfsje;

    @TableField("LJ_DBJJFSJE")
    //@ApiModelProperty(value = "累计-大病基金发生金额")
    private BigDecimal ljDbjjfsje;

    @TableField("LJ_GWYJJFSJE")
    //@ApiModelProperty(value = "累计-公务员基金发生金额")
    private BigDecimal ljGwyjjfsje;

    @TableField("LJ_JZJJFSJE")
    //@ApiModelProperty(value = "累计-救助基金发生金额")
    private BigDecimal ljJzjjfsje;

    @TableField("LJ_SCJJFSJE")
    //@ApiModelProperty(value = "累计-伤残基金发生金额")
    private BigDecimal ljScjjfsje;

    @TableField("LJ_GZJJFSJE")
    //@ApiModelProperty(value = "累计-个账基金发生金额")
    private BigDecimal ljGzjjfsje;

    @TableField("LJ_GJZHFSJE")
    //@ApiModelProperty(value = "累计-共济账户发生金额")
    private BigDecimal ljGjzhfsje;

    @TableField("LJ_XJ")
    //@ApiModelProperty(value = "累计-现金")
    private BigDecimal ljXj;

    @TableField("LJ_JSXJ")
    //@ApiModelProperty(value = "累计-结算小计")
    private BigDecimal ljJsxj;

    @TableField("LJ_TCJJJSJE")
    //@ApiModelProperty(value = "累计-统筹基金结算金额")
    private BigDecimal ljTcjjjsje;

    @TableField("LJ_DBJJJSJE")
    //@ApiModelProperty(value = "累计-大病基金结算金额")
    private BigDecimal ljDbjjjsje;

    @TableField("LJ_GWYJJJSJE")
    //@ApiModelProperty(value = "累计-公务员基金结算金额")
    private BigDecimal ljGwyjjjsje;

    @TableField("LJ_JZJJJSJE")
    //@ApiModelProperty(value = "累计-救助基金结算金额")
    private BigDecimal ljJzjjjsje;

    @TableField("LJ_SCJJJSJE")
    //@ApiModelProperty(value = "累计-伤残基金结算金额")
    private BigDecimal ljScjjjsje;

    @TableField("LJ_GZJJJSJE")
    //@ApiModelProperty(value = "累计-个账基金结算金额")
    private BigDecimal ljGzjjjsje;

    @TableField("LJ_GJZHJSJE")
    //@ApiModelProperty(value = "累计-共济账户结算金额")
    private BigDecimal ljGjzhjsje;

    @TableField("LJ_JJBFXJ")
    //@ApiModelProperty(value = "累计-基金拨付小计")
    private BigDecimal ljJjbfxj;

    @TableField("LJ_JJBFJE")
    //@ApiModelProperty(value = "累计-基金拨付金额")
    private BigDecimal ljJjbfje;

    @TableField("LJ_DBSBBFJE")
    //@ApiModelProperty(value = "累计-大病商保拨付金额")
    private BigDecimal ljDbsbbfje;

    @TableField("LJ_KHBZJ")
    //@ApiModelProperty(value = "累计-考核保证金")
    private BigDecimal ljKhbzj;

    @TableField("LJ_KK")
    //@ApiModelProperty(value = "累计-扣款")
    private BigDecimal ljKk;

    @TableField("DY_FSZFY")
    //@ApiModelProperty(value = "当月-发生总费用")
    private BigDecimal dyFszfy;

    @TableField("DY_TCJJFSJE")
    //@ApiModelProperty(value = "当月-统筹基金发生金额")
    private BigDecimal dyTcjjfsje;

    @TableField("DY_DBJJFSJE")
    //@ApiModelProperty(value = "当月-大病基金发生金额")
    private BigDecimal dyDbjjfsje;

    @TableField("DY_GWYJJFSJE")
    //@ApiModelProperty(value = "当月-公务员基金发生金额")
    private BigDecimal dyGwyjjfsje;

    @TableField("DY_JZJJFSJE")
    //@ApiModelProperty(value = "当月-救助基金发生金额")
    private BigDecimal dyJzjjfsje;

    @TableField("DY_SCJJFSJE")
    //@ApiModelProperty(value = "当月-伤残基金发生金额")
    private BigDecimal dyScjjfsje;

    @TableField("DY_GZJJFSJE")
    //@ApiModelProperty(value = "当月-个账基金发生金额")
    private BigDecimal dyGzjjfsje;

    @TableField("DY_GJZHFSJE")
    //@ApiModelProperty(value = "当月-共济账户发生金额")
    private BigDecimal dyGjzhfsje;

    @TableField("DY_XJ")
    //@ApiModelProperty(value = "当月-现金")
    private BigDecimal dyXj;

    @TableField("DY_JSXJ")
    //@ApiModelProperty(value = "当月-结算小计")
    private BigDecimal dyJsxj;

    @TableField("DY_TCJJJSJE")
    //@ApiModelProperty(value = "当月-统筹基金结算金额")
    private BigDecimal dyTcjjjsje;

    @TableField("DY_DBJJJSJE")
    //@ApiModelProperty(value = "当月-大病基金结算金额")
    private BigDecimal dyDbjjjsje;

    @TableField("DY_GWYJJJSJE")
    //@ApiModelProperty(value = "当月-公务员基金结算金额")
    private BigDecimal dyGwyjjjsje;

    @TableField("DY_JZJJJSJE")
    //@ApiModelProperty(value = "当月-救助基金结算金额")
    private BigDecimal dyJzjjjsje;

    @TableField("DY_SCJJJSJE")
    //@ApiModelProperty(value = "当月-伤残基金结算金额")
    private BigDecimal dyScjjjsje;

    @TableField("DY_GZJJJSJE")
    //@ApiModelProperty(value = "当月-个账基金结算金额")
    private BigDecimal dyGzjjjsje;

    @TableField("DY_GJZHJSJE")
    //@ApiModelProperty(value = "当月-共济账户结算金额")
    private BigDecimal dyGjzhjsje;

    @TableField("DY_JJBFXJ")
    //@ApiModelProperty(value = "当月-基金拨付小计")
    private BigDecimal dyJjbfxj;

    @TableField("DY_JJBFJE")
    //@ApiModelProperty(value = "当月-基金拨付金额")
    private BigDecimal dyJjbfje;

    @TableField("DY_DBSBBFJE")
    //@ApiModelProperty(value = "当月-大病商保拨付金额")
    private BigDecimal dyDbsbbfje;

    @TableField("DY_KHBZJ")
    //@ApiModelProperty(value = "当月-考核保证金")
    private BigDecimal dyKhbzj;

    @TableField("DY_KK")
    //@ApiModelProperty(value = "当月-扣款")
    private BigDecimal dyKk;

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

