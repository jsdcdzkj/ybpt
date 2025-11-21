package com.jsdc.ybpt.vo;

import lombok.Data;

@Data
public class ReimbursementQuery_org {
    private String beginTime;//起始时间
    private String endTime;//终止时间
    private String insu_admdvs;//参保人统筹区
    private String[] psn_type;//人员类型
    private String[] matn_type;//生育类别
    private String[] insutype;//险种类型
    private String hosp_lv;//医院等级
    private String fixmedins_code;//医院编码
    private Integer pageNo;
    private Integer pageSize;
}
