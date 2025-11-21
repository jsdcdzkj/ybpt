package com.jsdc.ybpt.directory;

import lombok.Data;

/**
 * 2.2.2按目录项目查询使用情况
 */
@Data
public class CatalogItem {
    //医保目录编码
    private String hilist_code;
    //医疗目录编码;
    private String med_list_codg;
    //费用发生时间;
    private String fee_ocur_time;
    //单价;
    private String pric;
    //数量;
    private String cnt;
    //明细项目费用总额;
    private String det_item_fee_sumamt;

    //定点医药机构编号
    private String fixmedins_code;
    //定点医药机构名称;
    private String fixmedins_name;
    //医疗类别;
    private String med_type;
    //参保所属医保区划;
    private String insu_admdvs;
    //支付地点类别;
    private String pay_loc;
    //符合范围金额;
    private String inscp_amt;
    //大病补充医疗保险基金支出;
    private String hifmi_pay;
    //大额医疗补助基金支出;
    private String hifob_pay;
    //医疗救助基金支出;
    private String maf_pay;
    //基金支付总额;
    private String fund_pay_sumamt;
    //全自费金额;
    private String fulamt_ownpay_amt;
    //个人支付金额
    private String psn_pay;
    //个人账户支出;
    private String acct_pay;
    //现金支付金额;
    private String cash_payamt;
    //开单科室名称;
    private String bilg_dept_name;
    //开单医师姓名;
    private String bilg_dr_name;
    //剂型名称;
    private String dosform_name;
    //规格;
    private String spec;
    //医疗收费项目类别;
    private String med_chrgitm_type;
    //收费项目等级;
    private String chrgitm_lv;
    //证件号码;
    private String certno;
    //人员姓名;
    private String psn_name;

    private Integer pageNo ;

    private Integer pageSize ;

    private String[] times;//上传时间
}
