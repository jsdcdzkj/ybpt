package com.jsdc.ybpt.model_query.reimbursement;

import lombok.Data;

@Data
public class BirthSettlement {
    private String psn_name;//姓名
    private String certno;//身份证
    private String insu_admdvs;//参保人统筹区
    private String psn_type;//人员类型
    private String med_type;//医疗类别
    private String setl_time;//生育日期/结算日期
    private String fixmedins_code;//医院编码
    private String fixmedins_name;//医院名称
    private String hosp_lv;//医院级别
    private String medfee_sumamt;//总费用
    private String reimbursement;//报销金额
}
