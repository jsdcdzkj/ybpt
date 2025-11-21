package com.jsdc.ybpt.formula.domain.entity;

import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import java.util.Date;

/**
 * 制剂目录(Catalog)数据库映射实体类
 *
 * @author yc
 * @since 2024-05-14 11:21:38
 */
@Data
@TableName("FORMULA_CATALOG")
public class Catalog {

    @TableId
    @Id
    private String id;
    
    @TableField("CATEGORY_CODE")
//    @ApiModelProperty(value = "分类编码")
    @Alias("分类编码")
    private String categoryCode;
    
    @TableField("GENERIC_NAME_CODE")
    @Alias("药品通用名编码")
//    @ApiModelProperty(value = "药品通用名编码")
    private String genericNameCode;
    
    @TableField("PRODUCT_NAME_CODE")
    @Alias("产品名编码")
//    @ApiModelProperty(value = "产品名编码")
    private String productNameCode;
    
    @TableField("FORMULA_NAME")
    @Alias("制剂名称")
//    @ApiModelProperty(value = "制剂名称")
    private String formulaName;
    
    @TableField("GOODS_NAME")
    @Alias("商品名")
//    @ApiModelProperty(value = "商品名")
    private String goodsName;
    
    @TableField("PAY_TYPE")
    @Alias("支付类别")
//    @ApiModelProperty(value = "支付类别")
    private String payType;
    
    @TableField("DOSAGE_FORM")
    @Alias("剂型")
//    @ApiModelProperty(value = "剂型")
    private String dosageForm;
    
    @TableField("SPECS")
    @Alias("规格")
//    @ApiModelProperty(value = "规格")
    private String specs;
    
    @TableField("MIN_PRICE_UNIT")
    @Alias("最小计价单位")
//    @ApiModelProperty(value = "最小计价单位")
    private String minPriceUnit;
    
    @TableField("UNIT")
    @Alias("单位")
//    @ApiModelProperty(value = "单位")
    private String unit;
    
    @TableField("MIN_PACKAGE")
    @Alias("最小包装")
//    @ApiModelProperty(value = "最小包装")
    private String minPackage;
    
    @TableField("APPROVAL_NO")
    @Alias("制剂批准文号")
//    @ApiModelProperty(value = "制剂批准文号")
    private String approvalNo;
    
    @TableField("REMARK")
    @Alias("备注")
//    @ApiModelProperty(value = "备注")
    private String remark;
    
    @TableField("CATALOG_CODE")
    @Alias("目录编号")
//    @ApiModelProperty(value = "目录编号")
    private String catalogCode;
    
    @TableField("REGISTER_COMPANY_NAME")
    @Alias("制剂注册单位")
//    @ApiModelProperty(value = "制剂注册单位")
    private String registerCompanyName;
    
    @TableField("SELF_PAY_RATIO")
    @Alias("个人先行自付比例")
//    @ApiModelProperty(value = "个人先行自付比例")
    private String selfPayRatio;
    
    @TableField("NATIONAL_FORMULA_CODE")
    @Alias("国家医疗机构制剂代码")
//    @ApiModelProperty(value = "国家医疗机构制剂代码")
    private String nationalFormulaCode;
    
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

