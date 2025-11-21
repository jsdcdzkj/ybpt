package com.jsdc.ybpt.model_query;

import lombok.Data;

/**
 * 专家信息表 PROF_INFO_B
 */
@Data
public class ProfInfoB {
  private Integer pageNo;
  private Integer pageSize;

  /**
   * 专家编号
   */
  private String prof_no;
  /**
   * 人员编号
   */
  private String psn_no;
  /**
   * 专家姓名
   */
  private String prof_name;
  /**
   * 办公电话
   */
  private String off_tel;
  /**
   * 性别
   */
  private String gend;
  /**
   * 现任职务
   */
  private String curr_duty;
  /**
   * 专家类型
   */
  private String prof_type;
  /**
   * 医疗机构科室
   */
  private String medins_dept;
  /**
   * 专家领域
   */
  private String prof_doma;
  /**
   * 专业方向
   */
  private String profarea;
  /**
   * 专业技术职称
   */
  private String pro_tech_profttl;
  /**
   * 统一社会信用代码
   */
  private String uscc;
  /**
   * 单位名称
   */
  private String emp_name;
  /**
   * 职称工作开始日期
   */
  private String profttl_job_begntime;
  /**
   * 简介
   */
  private String itro;
  /**
   * 单位地址
   */
  private String emp_addr;
  /**
   * 开始时间
   */
  private String begntime;
  /**
   * 结束时间
   */
  private String endtime;
  /**
   * 有效标志
   */
  private String vali_flag;
  /**
   * 医保区划
   */
  private String admdvs;
  /**
   * 工作证明文件地址
   */
  private String empprof_file_addr;
  /**
   * 手机号码
   */
  private String mob;
  /**
   * 电子邮箱
   */
  private String email;
  /**
   * 邮政编码
   */
  private String poscode;
  /**
   * 申报方式
   */
  private String dcla_way;
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
