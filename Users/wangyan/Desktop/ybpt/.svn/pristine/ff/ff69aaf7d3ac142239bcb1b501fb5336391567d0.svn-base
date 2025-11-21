package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * MED_DFR_D（医疗拨付信息表）
 */
@Data
public class MedDfrDVo {

    //拨付通知类型
    private String       dfr_notc_type;
    //待遇发放方式
    private String      trt_issu_way;
    //拨付对象类型
    private String      dfr_obj;
    //拨付对象编码
    private String      dfr_obj_code;
    //拨付对象名称
    private String      dfr_obj_name;
    //人员参保关系id
    private String     psn_insu_rlts_id;
    //拨付总金额
    private String     dfr_sumamt;
    //银行行别代码
    private String     bank_type_code;
    //银行行号
    private String     bankcode;
    //银行同城异地标志
    private String    bank_samecity_out_flag;
    //银行账号
    private String     bankacct;
    //银行卡户名
    private String    acctname;
    //备注
    private String     memo;
    //联系电话
    private String    tel;
    //联系人姓名
    private String    coner_name;
    // 财务经办机构
    private String     fin_optins;
    //拨付来源
    private String     dfr_souc;
    //来源流水号
    private String    souc_sn;
    //单据号
    private String      docno;
    //登帐事件id
    private String   acen_evt_id;
    // 财务拨付id
    private String     fin_dfr_id;
    //财务处理标志
    private String      fin_dspo_flag;
    //支付失败描述
    private String     pay_fail_desc;
    //财务登账时间
    private String    fin_acen_time;
    //人员类别
    private String     psn_type;
    //特殊人员类型
    private String    sp_psn_type;
    //灵活就业标志
    private String   flxempe_flag;
    //参保所属医保区划;
    private String     insu_admdvs;
    //单位类型;
    private String    emp_type;
    // 经济类型	;
    private String    econ_type;
    //所属行业;
    private String    afil_indu;
    //隶属关系;
    private String     afil_rlts;
    //单位管理类型;
    private String    emp_mgt_type;
    //清算经办机构;
    private String    clr_optins;
    //险种类型;
    private String    insutype;
    //医疗类别	;
    private String      med_type;
    //清算类别	;
    private String    clr_type;
    //二级清算类别	;
    private String    clr_type_lv2;
    //编制类型
    private String    quts_type;



    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

}
