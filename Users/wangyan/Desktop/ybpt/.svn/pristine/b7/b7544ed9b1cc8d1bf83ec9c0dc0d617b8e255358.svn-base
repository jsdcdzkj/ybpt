package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * FIXMEDINS_OTP_TRT_RX_INFO_C（定点医疗机构门急诊诊疗处方信息表）
 */
@Data
public class RxInfoVo {
    //定点医药机构编号
    private String fixmedins_code;
    //就诊流水号;
    private String mdtrt_sn;
    //处方id;
    private String rx_id;
    //处方号;
    private String rxno;
    //处方开方时间;
    private String rx_prsc_time;
    //处方类别代码;
    private String rx_type_code;
    //处方项目分类代码;
    private String rx_item_type_code;
    //处方项目分类名称;
    private String rx_item_type_name;
    //处方明细id;
    private String rx_detl_id;
    //处方明细名称;
    private String rx_detl_name;
    // 中药类别名称;
    private String tcmdrug_type_name;
    // 中药类别代码;
    private String tcmdrug_type_code;
    //草药脚注;
    private String tcmherb_foote;
    //药物类型代码;
    private String medn_type_code;
    // 药物类型;
    private String medn_type_name;
    // 药品剂型;
    private String drug_dosform;
    //药品剂型名称;
    private String drug_dosform_name;
    //药品规格;
    private String drug_spec;
    //药物使用-频率;
    private String drug_used_frqu;
    //药物使用-总剂量;
    private String drug_used_idose;
    // 药物使用-次剂量;
    private String drug_used_sdose;
    //药物使用-剂量单位;
    private String drug_used_dosunt;
    //药物使用-途径代码;
    private String drug_used_way_code;
    //药物使用-途径;
    private String drug_medc_way;
    //皮试判别;
    private String skintst_dicm;
    // 用药开始时间;
    private String medc_begntime;
    //用药停止日期时间;
    private String medc_endtime;
    //用药天数;
    private String medc_days;
    //主要用药标志;
    private String main_medc_flag;
    // 加急标志;
    private String urgt_flag;
    //统一采购药品;
    private String unif_purc_drug;
    //药品采购代码;
    private String drug_purc_code;
    //药品管理平台代码;
    private String drug_mgt_plaf_code;
    //基本药物标志;
    private String bas_medn_flag;

    //住院/门诊号
    private String ipt_otp_no ;
    //人员姓名
    private String psn_name ;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

}
