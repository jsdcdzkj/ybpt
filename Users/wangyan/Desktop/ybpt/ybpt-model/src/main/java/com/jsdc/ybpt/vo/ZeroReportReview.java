package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 2.1.2零报待复核数据查询
 * Author wzn
 * Date 2022/8/17 9:47
 */
@Data
public class ZeroReportReview {

    //姓名、
    private String fullName;
    //身份证号、
    private String identityNumber;
    //经办人、
    private String manager;
    //结算时间、
    private String settlementTime;
    //途径、
    private String way;
    //参保所属统筹区
    private String overallPlanningArea;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;


}
