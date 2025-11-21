package com.jsdc.ybpt.model_query.settlement;

import lombok.Data;

/**
 * SETL_D（结算信息表）
 */
@Data
public class SetlD {
  //人员姓名
  private String psn_name;
  //人员证件类型
  private String psn_cert_type;
  //证件号码
  private String certno;
  //性别
  private String gend;
  // 民族
  private String naty;
  //出生日期
  private String brdy;
  //年龄
  private String age;
  // 险种类型
  private String insutype;
  //人员类别
  private String psn_type;
  //参保所属医保区划
  private String insu_admdvs;
  //定点医药机构编号
  private String fixmedins_code;
  //定点医药机构名称
  private String fixmedins_name;
  //结算时间
  private String setl_time;
  // 医疗类别
  private String med_type;
  //结算类别
  private String setl_type;
  //医疗费总额
  private String medfee_sumamt;
  //统筹基金支出
  private String hifp_pay;
  //公务员医疗补助资金支出
  private String cvlserv_pay;
  // 补充医疗保险基金支出
  private String hifes_pay;
  // 大病补充医疗保险基金支出
  private String hifmi_pay;
  //大额医疗补助基金支出
  private String hifob_pay;
  // 伤残人员医疗保障基金支出
  private String hifdm_pay;
  // 医疗救助基金支出
  private String maf_pay;
  // 其它基金支付
  private String othfund_pay;
  // 基金支付总额
  private String fund_pay_sumamt;
  // 个人支付金额
  private String psn_pay;
  // 个人账户支出
  private String acct_pay;
  //现金支付金额
  private String cash_payamt;
  //自费中医院负担部分
  private String ownpay_hosp_part;
  //余额
  private String balc;
    // 账户共济支付金额
    private String acct_mulaid_pay;
    //退费结算标志
    private String refd_setl_flag;
    // 统筹区编号
    private String poolarea_no;
    // 基本医疗基金支出
    private String pool_prop_selfpay;

    private String age_flag;//是否70岁以上
    private String dise_no;//病种编号
    private String dise_name;//病种名称

    private String person_num;          //人数
    private String person_count;        //人次

    private Integer pageNo;
    private Integer pageSize;

    private String begntime;
    private String endtime;

}
