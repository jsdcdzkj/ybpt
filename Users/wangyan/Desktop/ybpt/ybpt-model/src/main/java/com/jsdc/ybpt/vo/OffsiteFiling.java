package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 2.5.4异地就医备案情况查询
 */
@Data
public class OffsiteFiling {

    //姓名、
    private String fullName;
    //身份证号、
    private String identityNumber;
    //医保区划、
    private String medicalInsuranceZoning;
    //人员类型、
    private String personType;
    //备案类型、
    private String filingType;
    //险种类型、
    private String typeOfInsurance;
    //安置地（就医地）
    private String placeOfPlacement;
    //所属行政区、
    private String administrativeRegion;
    //开始日期、
    private String  startDate;
    //结束日期、
    private String endDate;
    //申请时间、
    private String applicationTime;
    //经办人、
    private String  manager;
    //有效状态、
    private String validState;
    //离退休标志、
    private String retirementSign;
    //申报来源。
    private String declarationSource;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;


}
