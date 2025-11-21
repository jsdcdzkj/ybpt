package com.jsdc.ybpt.formula.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Id;

/**
 * 制剂定价测算-固定资产折旧(FixedAssetsDepre)数据库映射实体类
 *
 * @author yc
 * @since 2024-05-14 11:21:44
 */
@Data
@TableName("FORMULA_FIXED_ASSETS_DEPRE")
public class FixedAssetsDepre{

    @TableId
    @Id
//    @ApiModelProperty(value = "")
    private String id;
    
    @TableField("PRICE_CALCULATE_ID")
//    @ApiModelProperty(value = "制剂定价测算主键")
    private String priceCalculateId;
    
    @TableField("DEVICE_NAME")
//    @ApiModelProperty(value = "设备名称")
    private String deviceName;
    
    @TableField("ORIGINAL_VALUE")
//    @ApiModelProperty(value = "原值（元）")
    private Object originalValue;
    
    @TableField("USE_LIFE")
//    @ApiModelProperty(value = "使用年限（年）")
    private Object useLife;
    
    @TableField("USE_TIME")
//    @ApiModelProperty(value = "使用时间（小时）")
    private Object useTime;
    
    @TableField("PAY_PRICE")
//    @ApiModelProperty(value = "应付金额（元）")
    private Object payPrice;
    
    @TableField("IS_DEL")
//    @ApiModelProperty(value = "是否删除：0-否，1-是")
    private String isDel;
    
    @TableField("CREATETIME")
//    @ApiModelProperty(value = "创建时间")
    private Object createtime;
    
    @TableField("CREATEUSER")
//    @ApiModelProperty(value = "创建人主键")
    private String createuser;
    
    @TableField("UPDATETIME")
//    @ApiModelProperty(value = "修改时间")
    private Object updatetime;
    
    @TableField("UPDATEUSER")
//    @ApiModelProperty(value = "修改人主键")
    private String updateuser;
    
    @TableField("IS_SUBTOTAL")
//    @ApiModelProperty(value = "是否是小计数据：0-否，1-是")
    private String isSubtotal;
    
}

