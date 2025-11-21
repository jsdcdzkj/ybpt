package com.jsdc.ybpt.formula.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 制剂定价测算-管理费、损耗及其他(ManageLossOtherFee)数据库映射实体类
 *
 * @author yc
 * @since 2024-05-14 11:21:44
 */
@Data
@TableName("FORMULA_MANAGE_LOSS_OTHER_FEE")
public class ManageLossOtherFee{

    @TableId
    @Id
//    @ApiModelProperty(value = "")
    private String id;
    
    @TableField("PRICE_CALCULATE_ID")
//    @ApiModelProperty(value = "制剂定价测算主键")
    private String priceCalculateId;
    
    @TableField("MANAG_FEE")
//    @ApiModelProperty(value = "管理费分摊")
    private BigDecimal managFee;
    
    @TableField("LOSS_OTHER_FEE")
//    @ApiModelProperty(value = "损耗及其它")
    private BigDecimal lossOtherFee;
    
    @TableField("IS_DEL")
//    @ApiModelProperty(value = "是否删除：0-否，1-是")
    private String isDel;
    
    @TableField("CREATETIME")
//    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    
    @TableField("CREATEUSER")
//    @ApiModelProperty(value = "创建人主键")
    private String createuser;
    
    @TableField("UPDATETIME")
//    @ApiModelProperty(value = "修改时间")
    private Date updatetime;
    
    @TableField("UPDATEUSER")
//    @ApiModelProperty(value = "修改人主键")
    private String updateuser;
    
    @TableField("SUBTOTAL")
//    @ApiModelProperty(value = "小计")
    private BigDecimal subtotal;
    
}

