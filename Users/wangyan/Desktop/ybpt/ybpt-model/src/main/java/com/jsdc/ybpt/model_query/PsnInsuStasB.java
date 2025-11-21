package com.jsdc.ybpt.model_query;

import lombok.Data;

/**
 * 人员参保状态表
 */
@Data
public class PsnInsuStasB {
  private Integer pageNo;
  private Integer pageSize;

  /**
   * 人员参保关系id
   */
  private String psn_insu_rlts_id;
  /**
   * 人员编号
   */
  private String psn_no;
  /**
   * 人员参保状态
   */
  private String psn_insu_stas;
  /**
   * 人员类别
   */
  private String psn_type;
  /**
   * 险种类型
   */
  private String insutype;
  /**
   * 本次参保日期
   */
  private String crt_insu_date;
  /**
   * 暂停参保日期
   */
  private String paus_insu_date;
  /**
   * 参保所属医保区划
   */
  private String insu_admdvs;
  /**
   * 备注
   */
  private String memo;
  /**
   * 有效标志
   */
  private String vali_flag;
  /**
   * 数据唯一记录号
   */
  private String rid;
  /**
   * 数据创建时间
   */
  private String crte_time;
  /**
   * 数据更新时间
   */
  private String updt_time;
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
   * 经办机构编号
   */
  private String optins_no;
  /**
   * 版本号
   */
  private String ver;
  /**
   * 同步上级标志
   */
  private String sync_prnt_flag;

}
