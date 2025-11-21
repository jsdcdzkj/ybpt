package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 2.2.2用药范围查询
 * Author wzn
 * Date 2022/8/19 9:57
 */
@Data
public class ScopeOfMedication {

    //门慢门特病种目录代码、
    private String dssdcCode;
    //业务申请类型、
    private String typeOfBusinessApplication;
    //险种类型、
    private String typeOfInsurancep;
    //参保所属医保区划、
    private String medicalInsuranceZoning;
    //医保目录编码、
    private String medicalInsuranceDirectoryCode;
    //医保目录名称、
    private String medicalInsuranceDirectoryName;
    //开始日期、
    private String startDate;
    //结束日期
    private String endDate;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;


}
