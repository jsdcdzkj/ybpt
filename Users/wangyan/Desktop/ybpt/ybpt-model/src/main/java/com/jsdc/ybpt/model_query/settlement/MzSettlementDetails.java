package com.jsdc.ybpt.model_query.settlement;

import lombok.Data;

@Data
public class MzSettlementDetails {
    private String fee_ocur_time;//费用发生时间
    private String rx_drord_no;//处方/医嘱号
    private String hilist_code;//医保目录编码
    private String hilist_name;//医保目录名称
    private String medins_list_name;//医疗机构目录名称
    private String list_type;//目录类别
    private String med_chrgitm_type;//医疗收费项目类别
    private String pric;//单价
    private String cnt;//数量
    private String det_item_fee_sumamt;//明细项目费用总额
    private String fulamt_ownpay_amt;//全自费金额
    private String pric_uplmt_amt;//定价上限金额
    private String overlmt_selfpay;//超限价自费费用
    private String selfpay_prop;//自付比例
    private String preselfpay_amt;//先行自付金额
    private String reim_prop;//报销比例
    private String inscp_amt;//符合范围金额
    private String cvlserv_bedfee_amt;//公务员床位费金额
    private String medins_disc_amt;//医院减免金额

}
