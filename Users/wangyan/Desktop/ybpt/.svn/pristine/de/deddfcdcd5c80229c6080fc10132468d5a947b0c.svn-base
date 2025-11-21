package com.jsdc.ybpt.formula.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 制剂定价测算(PriceCalculate)数据库映射实体类
 *
 * @author yc
 * @since 2024-05-14 11:21:44
 */
@Data
@TableName("FORMULA_PRICE_CALCULATE")
public class PriceCalculate{

    @TableId
    @Id
//    @ApiModelProperty(value = "")
    private String id;
    
    @TableField("NOTIFY_APPLY_ID")
//    @ApiModelProperty(value = "制剂告知申请主键")
    private String notifyApplyId;
    
    @TableField("NONLOCAL_PM_PRICE")
//    @ApiModelProperty(value = "外地同级医院参考价格")
    private BigDecimal nonlocalPmPrice;
    
    @TableField("PROJECT_TOTAL_COST")
//    @ApiModelProperty(value = "项目成本合计")
    private BigDecimal projectTotalCost;
    
    @TableField("IS_DEL")
//    @ApiModelProperty(value = "是否删除：0-否，1-是")
    private String isDel;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATETIME")
//    @ApiModelProperty(value = "创建时间")
    private Date createtime;
    
    @TableField("CREATEUSER")
//    @ApiModelProperty(value = "创建人主键")
    private String createuser;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATETIME")
//    @ApiModelProperty(value = "修改时间")
    private Date updatetime;
    
    @TableField("UPDATEUSER")
//    @ApiModelProperty(value = "修改人主键")
    private String updateuser;
    
}

