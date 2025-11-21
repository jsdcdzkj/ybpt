package com.jsdc.ybpt.model_query;

import lombok.Data;

/**
 * 单位参保信息表 EMP_INSU_D
 */
@Data
public class EmpInsuD {
  private Integer pageNo;
  private Integer pageSize;

  private String insutype_name;//险种类型-转义
  private String hi_type_name;//医保类型-转义
  private String emp_insu_stas_name;//单位参保状态-转义
  private String clct_way_name;//征收方式-转义

  /**
   * 单位参保关系id
   */
  private String emp_insu_rlts_id;
  /**
   * 单位编号
   */
  private String emp_no;
  /**
   * 险种类型
   */
  private String insutype;
  /**
   * 医保类型
   */
  private String hi_type;
  /**
   * 单位参保状态
   */
  private String emp_insu_stas;
  /**
   * 征收方式
   */
  private String clct_way;
  /**
   * 单位参保日期
   */
  private String emp_insu_date;
  /**
   * 征缴规则类型编码
   */
  private String clct_rule_type_codg;
  /**
   * 基数核定规则类型编码
   */
  private String clctstd_crtf_rule_codg;
  /**
   * 税务启征年月
   */
  private String tax_begn_clct_ym;
  /**
   * 最大做账期号
   */
  private String max_acctprd;
  /**
   * 参保所属医保区划
   */
  private String insu_admdvs;
  /**
   * 统筹区编号
   */
  private String poolarea_no;
  /**
   * 备注
   */
  private String memo;
  /**
   * 创建人id
   */
  private String crter_id;
  /**
   * 创建人姓名
   */
  private String crter_name;
  /**
   * 创建机构编号
   */
  private String crte_optins_no;
  /**
   * 数据创建时间
   */
  private String crte_time;
  /**
   * 数据更新时间
   */
  private String updt_time;
  /**
   * 经办机构编号
   */
  private String optins_no;
  /**
   * 经办时间
   */
  private String opt_time;
  /**
   * 经办人姓名
   */
  private String opter_name;
  /**
   * 经办人id
   */
  private String opter_id;
  /**
   * 数据唯一记录号
   */
  private String rid;
  /**
   * 参保单位管理事件id
   */
  private String insu_emp_mgt_eid;
  /**
   * 经办渠道
   */
  private String opt_chnl;

}
