package com.jsdc.ybpt.model_query.settlement;

import lombok.Data;

@Data
public class MzSettlement {
    private String psn_name;//姓名
    private String certno;//身份证号
    private String setl_id;//结算ID
    private String year;//年度
    private String insutype;//险种类型
    private String med_type;//医疗类别
    private String insu_admdvs;//医保区划
    private String pay_loc;//支付地点类别
    private String fixmedins_code;//定点医药机构代码
    private String fixmedins_name;//定点医药机构名称
    private String hosp_lv;//医药机等级
    private String begndate;//就医开始时间
    private String enddate;//就医结束时间
    private String setl_time;//结算时间
    private String medfee_sumamt;//医疗费总额
    private String fulamt_ownpay_amt;//全自费金额
    private String overlmt_selfpay;//超限价自付金额
    private String preselfpay_amt;//先行自付金额
    private String inscp_amt;//符合范围金额
    private String dedc_std;//起付标准
    private String crt_dedc;//本次起付线
    private String act_pay_dedc;//实际支付起付线
    private String hifp_pay;//基本医疗基金支出
    private String pool_prop_selfpay;//基本医疗统筹支付比例
    private String cvlserv_pay;//公务员医疗补助基金支出
    private String hifmi_pay;//大病补充医疗保险基金支出
    private String hifob_pay;//大额医疗补助基金支出
    private String hifdm_pay;//伤残人员医疗保障基金支出
    private String maf_pay;//医疗救助基金支出
    private String othfund_pay;//其他基金支出
    private String fund_pay_sumamt;//基金支付总额
    private String psn_pay;//个人支付总额
    private String refd_setl_flag;//退费结算标志
    private String crter_name;//创建人
    private String opter_name;//经办人
    private String opt_time;//经办时间
}
