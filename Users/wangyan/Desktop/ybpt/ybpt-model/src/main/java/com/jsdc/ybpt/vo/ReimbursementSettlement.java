package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 2.5.10零星报销结算进度查询
 */
@Data
public class ReimbursementSettlement {

    //姓名、
    private String fullName;
    //身份证号、
    private String identityNumber;
    //医疗类别、
    private String medicalCategory;
    //定点医药机构名称、
    private String institutionName;
    //就医开始日期、
    private String startDateOfMedicalTreatment;
    //就医结束日期、
    private String endDateOfMedicalTreatment;
    //费用总额、
    private String totalCost;
    //当前业务环节、
    private String currentBusinessSegment;
    //后续业务环节、
    private String followUpBusiness;
    //审核结果、
    private String auditResults;
    //审核意见、
    private String auditOpinion;
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
