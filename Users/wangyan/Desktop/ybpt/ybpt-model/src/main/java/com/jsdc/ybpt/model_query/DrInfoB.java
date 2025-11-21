package com.jsdc.ybpt.model_query;

import lombok.Data;

/**
 * 医师信息表 DR_INFO_B
 */
@Data
public class DrInfoB {
  private Integer pageNo;
  private Integer pageSize;

  /**
   * 医师代码
   */
  private String dr_code;
  /**
   * 人员编号
   */
  private String psn_no;
  /**
   * 医师姓名
   */
  private String dr_name;
  /**
   * 个人能力简介
   */
  private String psn_itro;
  /**
   * 医师执业类别名称
   */
  private String dr_prac_type_name;
  /**
   * 医师执业类别
   */
  private String dr_prac_type;
  /**
   * 医师执业范围代码
   */
  private String dr_prac_scp_code;
  /**
   * 医师执业范围名称
   */
  private String dr_prac_scp_name;
  /**
   * 执业地区
   */
  private String prac_regn;
  /**
   * 多点执业标志
   */
  private String mul_prac_flag;
  /**
   * 主执业机构编号
   */
  private String main_pracins_no;
  /**
   * 主执业机构名称
   */
  private String main_pracins_name;
  /**
   * 主执业机构地址
   */
  private String main_pracins_addr;
  /**
   * 离职时间
   */
  private String nemp_time;
  /**
   * 有效标志
   */
  private String vali_flag;
  /**
   * 医师专业技术职务
   */
  private String dr_pro_tech_duty;
  /**
   * 医师专业技术职务名称
   */
  private String dr_pro_tech_duty_name;
  /**
   * 医师执业证书编号
   */
  private String dr_prac_cert_no;
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
   * 医疗机构代码
   */
  private String medins_code;
  /**
   * 执业机构名称
   */
  private String pracins_name;
  /**
   * 执业机构地址
   */
  private String pracins_addr;
  /**
   * 医疗机构名称
   */
  private String medins_name;
  /**
   * 信用等级
   */
  private String CRED_LV;
  /**
   * 违规行为
   */
  private String vola_bhvr;
  /**
   * 执业状态
   */
  private String prac_stas;
  /**
   * 版本名称
   */
  private String ver_name;
  /**
   * 医师id
   */
  private String dr_id;
  /**
   * 人员状态
   */
  private String psn_stas;
  /**
   * 合同起始时间
   */
  private String cntr_begntime;
  /**
   * 合同截止时间
   */
  private String cntr_endtime;
  /**
   * 医师执业级别
   */
  private String dr_prac_lv;
  /**
   * 医师执业证书电子文档
   */
  private String dr_praccert_elec_doc;
  /**
   * 医师资格证书编码
   */
  private String dr_cert_codg;
}
