package com.jsdc.ybpt.specialDrugFiling;

import lombok.Data;

/**
 * 特药审批备案事件记录表
 */
@Data
public class SpecialDrugFiling {
    //申报来源
    private String dcla_souc;
    //险种类型;
    private String insutype;
    //证件号码;
    private String certno;
    //人员姓名;
    private String psn_name;
    // 性别;
    private String gend;
    //联系电话;
    private String tel;
    //联系地址;
    private String addr;
    //参保所属医保区划;
    private String insu_admdvs;
    //诊断代码;
    private String diag_code;
    // 诊断名称;
    private String diag_name;


    private Integer pageNo;

    private Integer pageSize;

}
