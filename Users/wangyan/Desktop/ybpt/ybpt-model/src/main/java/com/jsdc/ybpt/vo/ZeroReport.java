package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 2.1零报数据查询
 * Author wzn
 * Date 2022/8/17 9:47
 */
@Data
public class ZeroReport {


    //医疗类别、
    private String medicalCategory;
    //医疗费总额、
    private String totalMedicalExpenses;

    //账户支付、
    private String accountPayment;
    //基金支付总额、
    private String totalFundPayments;
    //支付金额、
    private String paymentAmount;
    //基金支付、
    private String fundPayment;
    //大额基金支付、
    private String largeFundPayment;
    //公务员补助、
    private String civilServiceGrant;
    //大病基金支付、
    private String criticalIllnessFundPayments;
    //补充基金支付、
    private String supplementaryFundPayments;
    //个人管理编码、
    private String personalManagementCode;
    //社会保障号、
    private String socialSecurityNumber;
    //姓名、
    private String fullName;
    //入院日期、
    private String admissionDate;
    //出院日期、
    private String dischargeDate;
    //受理号、
    private String acceptanceNumber;
    //结算时间、
    private String settlementTime;
    //复核时间、
    private String reviewTime;
    //经办人、
    private String manager;
    //人员类别、
    private String personnelCategory;
    //参保所属统筹区。
    private String overallPlanningArea;
    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;


}
