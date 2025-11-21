package com.jsdc.ybpt.price.drug;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 医保范围外药品
 */
@Data
@Entity(name = "sb_non_medical_insurance")
@TableName("sb_non_medical_insurance")
public class SbNonMedicalInsurance extends Model<SbNonMedicalInsurance> {
    @TableId
    @Id
    private String id;

    //药品代码
    private String drugCode;
    //数据来源;
    private String dataSources;
    //注册名称;
    private String registeredName;
    //商品名称;
    private String productName;
    //注册剂型;
    private String registeredDosageForm;
    //实际剂型;
    private String actualDosageForm;
    //注册规格;
    private String registrationSpecification;
    //实际规格;
    private String actualSpecification;
    //包装材质;
    private String packagingMaterial;
    //最小包装数量;
    private String minNumber;
    //最小制剂单位;
    private String minPreparationUnit;
    //最小包装单位;
    private String minimumPackingUnit;
    //药品企业;
    private String medicineEnterprise;
    //上市药品持有人;
    private String possessor;
    //批准文号;
    private String approvalNumber;
    //原批准文号;
    private String originalApprovalNumber;
    //药品本位码;
    private String drugStandardCode;
    //分包装企业名称;
    private String nospep;
    //生产单位;
    private String productionUnit;
    //市场状态;
    private String stateOfMarket;
    //医保药品名称;
    private String drugNames;
    //    2021版甲乙类;
    private String classAB;
    //医保剂型;
    private String theFormOfADrug;
    //编号;
    private String serialNumber;
    //        备注;
    private String remark;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

}
