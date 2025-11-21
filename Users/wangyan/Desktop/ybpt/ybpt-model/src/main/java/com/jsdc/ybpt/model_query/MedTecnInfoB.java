package com.jsdc.ybpt.model_query;

import lombok.Data;

/**
 * 医疗技师信息表 MED_TECN_INFO_B
 */
@Data
public class MedTecnInfoB {
  private Integer pageNo;
  private Integer pageSize;

  /**
   * 医疗技师代码
   */
  private String med_tecn_code;
  /**
   * 人员编号
   */
  private String psn_no;
  /**
   * 技师姓名
   */
  private String med_tecn_name;
  /**
   * 个人能力简介
   */
  private String psn_itro;
  /**
   * 执业类别
   */
  private String prac_type;
  /**
   * 执业类别
   */
  private String prac_type_name;
  /**
   * 执业范围
   */
  private String prac_scp;
  /**
   * 执业范围
   */
  private String prac_scp_name;
  /**
   * 执业地区医保区划
   */
  private String prac_regn_admdvs;
  /**
   * 技师专业技术职务
   */
  private String tecn_pro_tech_duty;
  /**
   * 技师专业技术职务
   */
  private String tecn_pro_tech_duty_name;
  /**
   * 执业机构编号
   */
  private String pracins_no;
  /**
   * 执业机构名称
   */
  private String pracins_name;
  /**
   * 执业机构地址
   */
  private String pracins_addr;
  /**
   * 离职时间
   */
  private String nemp_time;
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
