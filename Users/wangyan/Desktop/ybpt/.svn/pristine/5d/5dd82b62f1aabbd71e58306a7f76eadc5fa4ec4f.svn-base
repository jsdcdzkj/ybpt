package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 2.5.9生育津贴受理情况查询
 * Author wzn
 * Date 2022/8/26 14:16
 */
@Data
public class MaternityBenefits {


    //姓名、
    private String fullName;
    //性别、
    private String gender;
    //身份证号、
    private String identityNumber;

    //险种类型、
    private String typeOfInsurance;
    //生育登记类别、
    private String birthRegistrationCategory;
    //计划生育手术或生育日期、
    private String birthDate;
    //胎次、
    private String nextToTheWomb;
    //胎儿数、
    private String numberOfFetuses;
    //登记时间、
    private String checkInTime;
    //登记经办人、
    private String registrar;
    //单位名称。
    private String companyName;
    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;


}
