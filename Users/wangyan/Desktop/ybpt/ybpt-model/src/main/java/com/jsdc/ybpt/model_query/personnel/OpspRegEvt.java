package com.jsdc.ybpt.model_query.personnel;

import lombok.Data;

/**
 *2.5.2门慢、门特审批信息查询
 */
@Data
public class OpspRegEvt {
    //申报来源
    private String dcla_souc;
    //险种类型
    private String insutype;
    //病种类型代码
    private String dise_type_code;
    //人员证件类型
    private String psn_cert_type;
    //证件号码
    private String certno;
    //人员姓名
    private String psn_name;
    //联系电话
    private String tel;
    //单位编号
    private String emp_no;
    //单位名称
    private String emp_name;
    //鉴定定点医药机构编号
    private String ide_fixmedins_no;
    //鉴定定点医药机构名称
    private String ide_fixmedins_name;
    //医院鉴定日期
    private String hosp_ide_date;
    //诊断医师代码
    private String diag_dr_code;
    //诊断医师姓名
    private String diag_dr_name;
    //申请日期
    private String appy_date;
    //申请理由
    private String appy_rea;
    //开始日期
    private String begndate;
    //结束日期
    private String enddate;
    //有效标志
    private String vali_flag;
    //复核标志
    private String rchk_flag;
    //备注
    private String memo;
    //经办人姓名
    private String opter_name;
    //经办时间
    private String opt_time;
    //统筹区编号
    private String poolarea_no;
    //生存状态
    private String surv_stas;
    //离退休类型
     private String retr_type;

    //门慢门特病种名称
    private String	opsp_dise_name;

    private Integer pageNo;
    private Integer pageSize;
}
