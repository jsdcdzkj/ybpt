package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 民办非营利医疗服务申报和药品耗材申报
 * 医疗服务
 *
 */
@Data
@Entity(name = "Sb_civilian_medical")
@TableName("Sb_civilian_medical")
public class SbCivilianMedical extends Model<SbCivilianMedical> {
    @TableId
    @Id
    private String id;
    //标题
    private String title;
    //申诉id
    private String sb_apply_id;
    //项目名称
    private String project_name;
    //项目编码
    private String project_code;
    //计价单位
    private String unit;
    //价格
    private String price;
    //费用类型
    private String cost_type;
    //本市同级公立医疗机构价格
    private String org_price;
    //是否高于公立医疗机构价格
    private String high_price;
    //儿童疫苗  1 是 0 否
    private String child_price;

    @Transient
    @TableField(exist = false)
    private int index;
    @Transient
    @TableField(exist = false)
    private String fileInfoId;
    @Transient
    @TableField(exist = false)
    private SbMedicalCalculate sbMedicalCalculate;
    @Transient
    @TableField(exist = false)
    private List<SbLabour> sbLabour;
    @Transient
    @TableField(exist = false)
    private List<SbFixedAssets> SbFixedAssets;
    @Transient
    @TableField(exist = false)
    private List<SbMaterialsConsumption> sbMaterialsConsumption;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
}
