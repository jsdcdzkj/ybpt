package com.jsdc.ybpt.model_query;

import lombok.Data;

/**
 * 护士信息表 NURS_INFO_B
 */
@Data
public class NursInfoB {
  private Integer pageNo;
  private Integer pageSize;

  /**
   * 护士代码
   */
  private String nurs_code;
  /**
   * 人员编号
   */
  private String psn_no;
  /**
   * 护士姓名
   */
  private String nurs_name;
  /**
   * 护士执业证书编号
   */
  private String nurs_prac_cert_no;
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
   * 护士专业技术职务
   */
  private String nurs_pro_tech_duty;
  /**
   * 护士专业技术职务名称
   */
  private String nurs_pro_tech_duty_name;
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
   * 医保区划
   */
  private String admdvs;
  /**
   * 同步上级标志
   */
  private String sync_prnt_flag;
  /**
   * 同步上级标志
   */
  private String sync_prnt_flag_name;
  /**
   * 信用等级
   */
  private String cred_lv;
  /**
   * 信用等级
   */
  private String cred_lv_name;
  /**
   * 违规行为
   */
  private String vola_bhvr;
  /**
   * 执业状态
   */
  private String prac_stas;
  /**
   * 执业状态
   */
  private String prac_stas_name;
  /**
   * 版本唯一编号
   */
  private String ver_rid;
  /**
   * 版本名称
   */
  private String ver_name;
  /**
   * 护士id
   */
  private String nurs_id;
  /**
   * 人员状态
   */
  private String psn_stas;
  /**
   * 人员状态
   */
  private String psn_stas_name;
  /**
   * 合同起始时间
   */
  private String cntr_begntime;
  /**
   * 合同截止时间
   */
  private String cntr_endtime;
  /**
   * 执业类别
   */
  private String prac_type;
  /**
   * 执业类别
   */
  private String prac_type_name;
  /**
   * 医保护士注册证电子文档
   */
  private String hi_nurs_regcert_elec_doc;

}
