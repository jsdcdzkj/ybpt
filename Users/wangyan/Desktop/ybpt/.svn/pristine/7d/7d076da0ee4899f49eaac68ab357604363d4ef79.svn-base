package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 医疗服务项目信息查询
 * Author wzn
 * Date 2022/9/7 19:03
 */
@Data
public class MedicalService {

    //医疗目录编码
    private String med_list_codg;
    //计价单位	;
    private String prcunt;
    //诊疗除外内容;
    private String trt_exct_cont;
    //诊疗项目内涵	;
    private String trt_item_cont;
    //医疗服务项目名称;
    private String servitem_name;
    //开始日期	;
    private String begndate;
    //结束日期	;
    private String enddate;
    //自付比列类型
    private String selfpay_prop_psn_type;
    //自付比列
    private String selfpay_prop;
    //限价类型
    private String hilist_lmtpric_type;
    //医保目录定价上限金额
    private String hilist_pric_uplmt_amt;

    //医疗收费项目类别
    private String med_chrgitm_type;
    //收费项目等级
    private String chrgitm_lv;


    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;


}
