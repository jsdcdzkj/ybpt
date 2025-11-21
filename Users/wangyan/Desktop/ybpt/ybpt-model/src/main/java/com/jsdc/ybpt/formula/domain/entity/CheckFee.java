package com.jsdc.ybpt.formula.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Id;

/**
 * 制剂定价测算-检验费(CheckFee)数据库映射实体类
 *
 * @author yc
 * @since 2024-05-14 11:21:44
 */
@Data
@TableName("FORMULA_CHECK_FEE")
public class CheckFee{

    @TableId
    @Id
//    @ApiModelProperty(value = "")
    private String id;
    
    @TableField("PRICE_CALCULATE_ID")
//    @ApiModelProperty(value = "制剂定价测算主键")
    private String priceCalculateId;
    
    @TableField("PRODUCT_NAME")
//    @ApiModelProperty(value = "品名")
    private String productName;
    
    @TableField("UNIT")
//    @ApiModelProperty(value = "单位")
    private String unit;
    
    @TableField("PRODUCT_NUM")
//    @ApiModelProperty(value = "数量")
    private Object productNum;
    
    @TableField("UNIT_PRICE")
//    @ApiModelProperty(value = "单价（元）")
    private Object unitPrice;
    
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

