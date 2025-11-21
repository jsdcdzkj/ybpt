package com.jsdc.ybpt.model_query;

import lombok.Data;

/**
 * 人员参保信息表 PSN_INSU_D
 */
@Data
public class PsnInsuD {
  private Integer pageNo;
  private Integer pageSize;

  /**
   * 人员参保关系id
   */
  private String psn_insu_rlts_id;
  /**
   * 人员姓名
   */
  private String psn_name;
  /**
   * 电子凭证
   */
  private String hsecfc;
  /**
   * 证件号
   */
  private String certno;
  /**
   * 单位编号
   */
  private String emp_no;
  /**
   * 人员编号
   */
  private String psn_no;
  /**
   * 险种类型
   */
  private String insutype;
  /**
   * 险种类型
   */
  private String insutype_name;
  /**
   * 本次参保日期
   */
  private String crt_insu_date;
  /**
   * 暂停参保日期
   */
  private String paus_insu_date;
  /**
   * 人员参保状态
   */
  private String psn_insu_stas;
  /**
   * 人员参保状态
   */
  private String psn_insu_stas_name;
  /**
   * 险种离退休标志
   */
  private String insutype_retr_flag;
  /**
   * 险种离退休标志
   */
  private String insutype_retr_flag_name;
  /**
   * 人员类别
   */
  private String psn_type;
  /**
   * 人员类别
   */
  private String psn_type_name;
  /**
   * 征收方式
   */
  private String clct_way;
  /**
   * 征收方式
   */
  private String clct_way_name;
  /**
   * 用工形式
   */
  private String emp_fom;
  /**
   * 用工形式
   */
  private String emp_fom_name;
  /**
   * 编制类型
   */
  private String quts_type;
  /**
   * 编制类型
   */
  private String quts_type_name;
  /**
   * 最大做账期号
   */
  private String max_acctprd;
  /**
   * 账户建立年月
   */
  private String acct_crtn_ym;
  /**
   * 首次参保年月
   */
  private String fst_insu_ym;
  /**
   * 本系统首次参保日期
   */
  private String psn_insu_date;
  /**
   * 征缴规则类型编码
   */
  private String clct_rule_type_codg;
  /**
   * 基数核定规则类型编码
   */
  private String clctstd_crtf_rule_codg;
  /**
   * 医保类型
   */
  private String hi_type;
  /**
   * 医保类型
   */
  private String hi_type_name;
  /**
   * 参保所属医保区划
   */
  private String insu_admdvs;
  /**
   * 统筹区编号
   */
  private String poolarea_no;
  /**
   * 经办渠道
   */
  private String opt_chnl;
  /**
   * 经办渠道
   */
  private String opt_chnl_name;
  /**
   * 经办机构编号
   */
  private String optins_no;
  /**
   * 经办人id
   */
  private String opter_id;
  /**
   * 经办人姓名
   */
  private String opter_name;
  /**
   * 经办时间
   */
  private String opt_time;
  /**
   * 创建机构编号
   */
  private String crte_optins_no;
  /**
   * 创建人id
   */
  private String crter_id;
  /**
   * 创建人姓名
   */
  private String crter_name;
  /**
   * 数据创建时间
   */
  private String crte_time;
  /**
   * 数据更新时间
   */
  private String updt_time;
  /**
   * 数据唯一记录号
   */
  private String rid;
  /**
   * 参保人员管理事件id
   */
  private String psn_insu_mgt_eid;
  /**
   * 退休待遇开始日期
   */
  private String retr_trt_begn_date;
  /**
   * 退休待遇享受标志
   */
  private String retr_trt_enjymnt_flag;
  /**
   * 退休待遇享受标志
   */
  private String retr_trt_enjymnt_flag_name;

}
