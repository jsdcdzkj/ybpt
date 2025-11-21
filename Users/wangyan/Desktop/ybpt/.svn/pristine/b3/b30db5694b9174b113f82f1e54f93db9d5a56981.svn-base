package com.jsdc.ybpt.formula.domain.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.jsdc.ybpt.model.FileInfo;
import lombok.Data;
import org.apache.ibatis.type.JdbcType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 制剂告知申请(NotifyApply)数据库映射实体类
 *
 * @author yc
 * @since 2024-05-14 11:21:44
 */
@Data
@TableName("FORMULA_NOTIFY_APPLY")
public class NotifyApply{

    @TableId
    @Id
//    @ApiModelProperty(value = "")
    private String id;
    
    @TableField("IS_IN_CATEGORY")
//    @ApiModelProperty(value = "是否在医保制剂目录")
    private String isInCategory;
    
    @TableField("CATEGORY_ID")
//    @ApiModelProperty(value = "制剂目录主键")
    private String categoryId;
    
    @TableField("NATIONAL_FORMULA_CODE")
//    @ApiModelProperty(value = "国家医疗机构制剂代码")
    private String nationalFormulaCode;
    
    @TableField("FORMULA_NAME")
//    @ApiModelProperty(value = "制剂名称")
    private String formulaName;
    
    @TableField("APPROVAL_NO")
//    @ApiModelProperty(value = "批准文号")
    private String approvalNo;
    
    @TableField("REGISTER_COMPANY_NAME")
//    @ApiModelProperty(value = "制剂注册单位")
    private String registerCompanyName;
    
    @TableField("DOSAGE_FORM")
//    @ApiModelProperty(value = "剂型")
    private String dosageForm;
    
    @TableField("SPECS")
//    @ApiModelProperty(value = "规格")
    private String specs;
    
    @TableField("UNIT")
//    @ApiModelProperty(value = "单位")
    private String unit;
    
    @TableField("PRICE")
//    @ApiModelProperty(value = "价格(元)")
    private BigDecimal price;
    
    @TableField("LOCAL_PM_PRICE")
//    @ApiModelProperty(value = "本市同级公立医疗机构价格(元)")
    private BigDecimal localPmPrice;
    
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
    
    @TableField("STATUS")
//    @ApiModelProperty(value = "状态: 0 待初审 1.待复审 2.待终审  3.待生成受理书 4.完成 5.驳回")
    private String status;
    
    @TableField(value = "REJECT_REASON"   )
//    @ApiModelProperty(value = "驳回原因")
    private String rejectReason;
    
    @TableField(value = "REJECT_TIME" , updateStrategy = FieldStrategy.IGNORED , jdbcType = JdbcType.TIMESTAMP)
//    @ApiModelProperty(value = "驳回时间")
    private Date rejectTime;
    
    @TableField("REJECT_USER")
//    @ApiModelProperty(value = "驳回人")
    private String rejectUser;
    
    @TableField(value = "FIRST_CHECK_TIME" , updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.TIMESTAMP)
//    @ApiModelProperty(value = "初审通过时间")
    private Date firstCheckTime;
    
    @TableField("FIRST_CHECK_USER")
//    @ApiModelProperty(value = "初审负责人")
    private String firstCheckUser;
    
    @TableField(value = "SECOND_CHECK_TIME",updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.TIMESTAMP)
//    @ApiModelProperty(value = "复审通过时间")
    private Date secondCheckTime;
    
    @TableField("SECOND_CHECK_USER")
//    @ApiModelProperty(value = "复审负责人")
    private String secondCheckUser;
    
    @TableField(value = "FINISH_CHECK_TIME" , updateStrategy = FieldStrategy.IGNORED, jdbcType = JdbcType.TIMESTAMP)
//    @ApiModelProperty(value = "终审通过时间")
    private Date finishCheckTime;
    
    @TableField("FINISH_CHECK_USER")
//    @ApiModelProperty(value = "终审负责人")
    private String finishCheckUser;
    
    @TableField("GENERAL_ACCEPT_LETTER_TIME")
//    @ApiModelProperty(value = "生成受理书时间")
    private Date generalAcceptLetterTime;

    @TableField("ORG_NAME")
//    @ApiModelProperty(value = "单位名称")
    private String orgName;

    @TableField("ORG_CODE")
//    @ApiModelProperty(value = "单位医保编码")
    private String orgCode;

    @TableField("CATEGORY_CODE")
//    @ApiModelProperty(value = "分类编码")
    private String categoryCode;

    @TableField("GENERIC_NAME_CODE")
//    @ApiModelProperty(value = "药品通用名编码")
    private String genericNameCode;

    @TableField("PRODUCT_NAME_CODE")
//    @ApiModelProperty(value = "产品名编码")
    private String productNameCode;

    @TableField("GOODS_NAME")
//    @ApiModelProperty(value = "商品名")
    private String goodsName;

    @TableField("PAY_TYPE")
//    @ApiModelProperty(value = "支付类别")
    private String payType;


    @TableField("MIN_PRICE_UNIT")
//    @ApiModelProperty(value = "最小计价单位")
    private String minPriceUnit;

    @TableField("MIN_PACKAGE")
//    @ApiModelProperty(value = "最小包装")
    private String minPackage;

    @TableField("SELF_PAY_RATIO")
//    @ApiModelProperty(value = "个人先行自付比例")
    private String selfPayRatio;


    @TableField("REMARK")
//    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField("CATALOG_CODE")
//    @ApiModelProperty(value = "目录编号")
    private String catalogCode;

    @TableField("LAST_APPLY_PRICE")
//    @ApiModelProperty(value = "上次申报价格")
    private BigDecimal lastApplyPrice;

    //签章pdf
    @TableField("PDF_PATH")
    private String pdfPath;
    //签章pdf下载
    @TableField("DOWN_PDF_PATH")
    private String downPdfPath;

    //受理书合同ID
    @TableField("ACCEPT_LETTER_CONTRACTID")
    private String acceptLetterContractId;
    //受理书pdf下载
    @TableField("ACCEPT_LETTER_DOWN_PDF_PATH")
    private String acceptLetterDownPdfPath;
    //受理书pdf
    @TableField("ACCEPT_LETTER_PDF_PATH")
    private String acceptLetterPdfPath;

    //合同ID
    @TableField("CONTRACTID")
    private String contractId;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    //统筹区名称
    @Transient
    @TableField(exist = false)
    private String fixBlngAdmdvsName;

    //统筹区
    @Transient
    @TableField(exist = false)
    private String fixBlngAdmdvs;

    @TableField(exist = false)
    //协议等级1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级
    private String aggrementLv;

    @TableField(exist = false)
    //是否在医保制剂目录 是 否
    private String isInCategoryDesc;


    @Transient
    @TableField(exist = false)
    private String isAudit;


    //附件
    @TableField(exist = false)
    private List<MultipartFile> files;
    //附件
    @TableField(exist = false)
    private List<FileInfo> fileInfoList;



    //测算
    @TableField(exist = false)
    private PriceCalculate priceCalculate;

    @TableField(exist = false)
    private String isExport;


    //劳务支出
    @TableField(exist = false)
    private List<Labor> laborList;

    //材料消耗指出
    @TableField(exist = false)
    private List<MaterialConsume> materialConsumeList;

    //固定资产折旧
    @TableField(exist = false)
    private List<FixedAssetsDepre> fixedAssetsDepreList;

    //检验费
    @TableField(exist = false)
    private List<CheckFee> checkFeeList;

    //管理费损耗费
    @TableField(exist = false)
    private ManageLossOtherFee manageLossOtherFee;

    //经营性质 1:营利性 2:民办非营利 3:政府非营利
    @TableField(exist = false)
    private String biznet;

    @Transient
    @TableField(exist = false)
    private String year;

    @Transient
    @TableField(exist = false)
    private String month;

    @Transient
    @TableField(exist = false)
    private String day;

    @Transient
    @TableField(exist = false)
    private String ids;

}

