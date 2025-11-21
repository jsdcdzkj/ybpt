package com.jsdc.ybpt.price.zlproject;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 诊疗项目
 */
@Data
@Entity(name = "sb_project")
@TableName("sb_project")
public class SbZlProject extends Model<SbZlProject> {
    @TableId
    @Id
    private String id;

    //省项目编码"
    private String provincialProjectCode;

    //医保目录编码
    private String directoryCoding;
    //医保目录名称
    private String directoryName;
    //    "计价单位";
    private String chargeUnit;
    //项目内涵;
    private String projectConnotation;
    //除外内容;
    private String excludedContent;
    //说明
    private String explain;
    //收费类别
    private String chargeType;
    //收费项目等级
    private String levelOfChargeItem;
    //限制使用标志
    private String restrictedUseSign;
    //非儿童一级限价
    private String nonChildOne;
    //非儿童二级限价
    private String nonChildTwo;
    //非儿童三级限价
    private String nonChildThree;
    //儿童一级限价（六周岁及以下）
    private String childOne;
    //儿童二级限价（六周岁及以下）
    private String childTwo;
    //儿童三级限价（六周岁及以下）
    private String childThree;
    //一至六级残疾军人二次限价
    private String disabledSoldier;

    //变更类别;
    private String changeCategory;
    //变更内容;
    private String contentOfChange;
    //是否自主项目  0 否  1是
    private String is_autonomy;
    //自助项目 归属机构
    private String belongToOrg;

    //申报类型
    private String addType ;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    @Transient
    @TableField(exist = false)
    private String status ;//0  已生效  1待生效




    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

}
