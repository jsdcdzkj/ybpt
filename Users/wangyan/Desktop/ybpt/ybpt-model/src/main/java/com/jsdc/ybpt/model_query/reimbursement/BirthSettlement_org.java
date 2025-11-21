package com.jsdc.ybpt.model_query.reimbursement;

import lombok.Data;

@Data
public class BirthSettlement_org {
    private String fixmedins_code;//医院编码
    private String fixmedins_name;//医院名称
    private String matn_type;//生育类别
    private String hosp_lv;//医院等级
    private String insutype;//险种类别
    private String count;//人次
    private String num;//人数
    private String medfee_sumamt;//医疗费总额
    private String hifp_pay;//统筹基金支出
    private String hifob_pay;//大额医疗补助基金支出
    private String cvlserv_pay;//公务员医疗补助资金支出
    private String acct_pay;//个人账户支出
    private String cash_payamt;//现金支付金额
    private String ownpay_hosp_part;//自费中医院负担部分
    private String maf_pay;//医疗救助
    private String insu_admdvs;//统筹区

}
