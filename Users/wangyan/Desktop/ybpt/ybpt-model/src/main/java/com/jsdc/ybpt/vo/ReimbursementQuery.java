package com.jsdc.ybpt.vo;

import lombok.Data;

@Data
public class ReimbursementQuery {
    private String beginTime;//起始时间
    private String endTime;//终止时间
    private String insu_admdvs;//参保人统筹区
    private String psn_type;//人员类型
    private String med_type;//医疗类别

    private Integer pageNo;
    private Integer pageSize;
}
