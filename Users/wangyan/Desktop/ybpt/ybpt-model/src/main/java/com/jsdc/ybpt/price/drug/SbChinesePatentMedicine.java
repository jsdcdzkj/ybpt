package com.jsdc.ybpt.price.drug;

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
 * 中成药
 */
@Data
@Entity(name = "sb_chinese_patent_medicine")
@TableName("sb_chinese_patent_medicine")
public class SbChinesePatentMedicine extends Model<SbChinesePatentMedicine> {
    @TableId
    @Id
    private String id;

    //分类编码
    private String sortingCodeNumber;
    //药品通用名编码
    private String common_name_code;
    //医保药品名称
    private String drugNames;
    //医保支付类别
    private String paymentCategory;

    //产品名编码
    private String productNameCoding;
    //注册名称
    private String registeredName;
    //商品名称
    private String productName;
    //实际剂型
    private String actualDosageForm;
    //实际规格
    private String actualSpecification;
    //包装材质
    private String packagingMaterial;
    //最小包装数量
    private String minimumPackingQuantity;
    //单位
    private String unit;
    //政府定价（元）
    private String valorize;
    //省集中采购上限价
    private String purchaseCeilingPrice;
    //医保支付标准
    private String paymentCriteria;
    //批准文号
    private String approvalNumber;
    //药品企业
    private String medicineEnterprise;
    //医保限定支付范围
    private String limitPayment;
    //编号
    private String serialNumber;
    //招标申报编号
    private String bidDeclarationNumber;
    //备注
    private String remark;
    //国家药品代码
    private String nationalDrugCode;
    //变更类型0代表新增，1代表修改，2代表删除
    private String changeType;
    //    变更原因
    private String reasonsForChange;

    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;


    @Transient
    @TableField(exist = false)
    private String status ;//0  已生效  1待生效


    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
    
}
