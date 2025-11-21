package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 2.5.7零星报销受理情况查询
 * Author wzn
 * Date 2022/8/25 9:27
 */
@Data
public class SporadicReimbursement {

    //姓名、
    private String name;
    //身份证号、
    private String identityNumber;
    //险种类型、
    private String typeOfInsurance;
    //医疗类别、
    private String medicalCategory;
    //零星报销受理号、
    private String acceptanceNumber;
    //就医开始时间、
    private String startingTime;
    //就医结束时间、
    private String endTime;
    //登记时间、
    private String checkInTime;
    //登记经办人、
    private String registrar;
    //单位名称、
    private String companyName;
    //公务员标志。
    private String civilServantSign;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;


}
