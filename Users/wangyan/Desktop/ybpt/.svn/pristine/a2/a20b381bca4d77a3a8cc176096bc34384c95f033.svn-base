package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 2.5.11生育待遇结算进度查询
 */
@Data
public class FertilitySettlementProgress {

    //姓名、
    private String fullName;
    //身份证号、
    private String identityNumber;
    //性别、
    private String gender;
    //单位名称、
    private String companyName;
    //待遇类型、
    private String treatmentType;
    //生育类别、
    private String fertilityCategory;
    //计划生育手术或生育日期、
    private String birthDate;
    //医疗费用总额、
    private String totalMedicalExpenses;
    //医疗费支付金额、
    private String amountOfMedicalExpensesPaid;
    //生育津贴、
    private String maternityBenefits;
    //一次性营养费、
    private String  oneTimeNutritionFee;
    //产前检查费、
    private String prenatalCheckupFee;
    //男职工护理津贴、
    private String  maleEmployeeCareAllowance;
    //当前业务环节、
    private String currentBusinessSegment;
    //后续业务环节、
    private String  followUpBusiness;
    //审核结果、
    private String  auditResults;
    //审核意见、
    private String  auditOpinion;
    //经办人、
    private String manager;
    //经办时间，
    private String handlingTime;
    //超时天数。
    private String timeoutDays;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    
}
