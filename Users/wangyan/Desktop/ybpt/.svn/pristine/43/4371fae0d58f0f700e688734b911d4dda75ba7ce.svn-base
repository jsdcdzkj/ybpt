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
 * 拨付通知-发生数(APPROP_NOTICE_OCCUR)数据库映射实体类
 *
 * @author wangxiao
 * @date 2024-05-17 15:32:04
 */
@Data
@TableName("APPROP_NOTICE_OCCUR")
public class AppropNoticeOccur {

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

    @TableField("JSTCQ")
    //@ApiModelProperty(value = "结算统筹区")
    @Alias("结算统筹区")
    private String jstcq;

    @TableField("CBTCQ")
    //@ApiModelProperty(value = "参保统筹区")
    @Alias("参保统筹区")
    private String cbtcq;

    @TableField("XZLX")
    //@ApiModelProperty(value = "险种类型")
    @Alias("险种类型")
    private String xzlx;

    @TableField("YLLB")
    //@ApiModelProperty(value = "医疗类别")
    @Alias("医疗类别")
    private String yllb;

    @TableField("RYLB")
    //@ApiModelProperty(value = "人员类别")
    @Alias("人员类别")
    private String rylb;

    @TableField("SYLB")
    //@ApiModelProperty(value = "生育类别")
    @Alias("生育类别")
    private String sylb;

    @TableField("JHSYLB")
    //@ApiModelProperty(value = "计划生育类别")
    @Alias("计划生育类别")
    private String jhsylb;

    @TableField("BDZCCS")
    //@ApiModelProperty(value = "本地政策参数")
    @Alias("本地政策参数")
    private String bdzccs;

    @TableField("BGXE")
    //@ApiModelProperty(value = "病种限额")
    @Alias("病种限额")
    private BigDecimal bgxe;

    @TableField("ZJHM")
    //@ApiModelProperty(value = "证件号码")
    @Alias("证件号码")
    private String zjhm;

    @TableField("RC")
    //@ApiModelProperty(value = "人次")
    @Alias("人次")
    private BigDecimal rc;

    @TableField("RS")
    //@ApiModelProperty(value = "人数")
    @Alias("人数")
    private BigDecimal rs;

    @TableField("CRS")
    //@ApiModelProperty(value = "床日数")
    @Alias("床日数")
    private BigDecimal crs;

    @TableField("YLFZE")
    //@ApiModelProperty(value = "医疗费总额")
    @Alias("医疗费总额")
    private BigDecimal ylfze;

    @TableField("QZFJE")
    //@ApiModelProperty(value = "全自费金额")
    @Alias("全自费金额")
    private BigDecimal qzfje;

    @TableField("CXJZFJE")
    //@ApiModelProperty(value = "超限价自费金额")
    @Alias("超限价自费金额")
    private BigDecimal cxjzfje;

    @TableField("XXZFJE")
    //@ApiModelProperty(value = "先行自付金额")
    @Alias("先行自付金额")
    private BigDecimal xxzfje;

    @TableField("FHFWJE")
    //@ApiModelProperty(value = "符合范围金额")
    @Alias("符合范围金额")
    private BigDecimal fhfwje;

    @TableField("TCJJ")
    //@ApiModelProperty(value = "统筹基金")
    @Alias("统筹基金")
    private BigDecimal tcjj;

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

    @TableField("YLJZJJ")
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

    @TableField("XJ")
    //@ApiModelProperty(value = "现金")
    @Alias("现金")
    private BigDecimal xj;

    @TableField("SYJJ")
    //@ApiModelProperty(value = "生育基金")
    @Alias("生育基金")
    private BigDecimal syjj;

    @TableField("YYDF")
    //@ApiModelProperty(value = "医院垫付")
    @Alias("医院垫付")
    private BigDecimal yydf;

    @TableField("QXJZ")
    //@ApiModelProperty(value = "倾斜救助")
    @Alias("倾斜救助")
    private BigDecimal qxjz;

    @TableField("ZFDD")
    //@ApiModelProperty(value = "政府兜底")
    @Alias("政府兜底")
    private BigDecimal zfdd;

    @TableField("LXBXJJ")
    //@ApiModelProperty(value = "离休保险基金")
    @Alias("离休保险基金")
    private BigDecimal lxbxjj;

    @TableField("JDLKDBJJ")
    //@ApiModelProperty(value = "建档立卡大病基金")
    @Alias("建档立卡大病基金")
    private BigDecimal jdlkdbjj;

    @TableField("GWBCJJ")
    //@ApiModelProperty(value = "公卫补充基金")
    @Alias("公卫补充基金")
    private BigDecimal gwbcjj;

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

