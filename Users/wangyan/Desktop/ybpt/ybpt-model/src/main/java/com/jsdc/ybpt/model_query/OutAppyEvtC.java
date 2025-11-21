package com.jsdc.ybpt.model_query;

import lombok.Data;

/**
 * 异地申请事件记录表 OUT_APPY_EVT_C
 */
@Data
public class OutAppyEvtC {
  private Integer pageNo;
  private Integer pageSize;

  private String agnter_cert_type_name;//代办人证件类型-转义
  private String agnter_rlts_name;//代办人关系-转义
  private String psn_cert_type_name;//人员证件类型-转义
  private String evt_type_name;//事件类型-转义
  private String dcla_souc_name;//申报来源-转义
  private String gend_name;//性别-转义
  private String naty_name;//民族-转义
  private String insutype_name;//险种类型-转义
  private String rchk_flag_name;//复核标志-转义

  private String rloc_coty_type_name;//安置区类型-转义
  private String out_onln_way_name;//异地联网方式-转义
  private String rloc_rea_name;//异地安置原因-转义
  private String out_fil_upld_stas_name; // 异地备案上报状态-转义

  /**
   * 事件流水号
   */
  private String evtsn;
  /**
   * 待遇申报明细流水号
   */
  private String trt_dcla_detl_sn;
  /**
   * 服务事项实例id
   */
  private String serv_matt_inst_id;
  /**
   * 服务事项环节实例id
   */
  private String serv_matt_node_inst_id;
  /**
   * 事件实例id
   */
  private String evt_inst_id;
  /**
   * 事件类型
   */
  private String evt_type;
  /**
   * 险种类型
   */
  private String insutype;
  /**
   * 申报来源
   */
  private String dcla_souc;
  /**
   * 人员编号
   */
  private String psn_no;
  /**
   * 人员参保关系id
   */
  private String psn_insu_rlts_id;
  /**
   * 人员证件类型
   */
  private String psn_cert_type;
  /**
   * 证件号码
   */
  private String certno;
  /**
   * 人员姓名
   */
  private String psn_name;
  /**
   * 性别
   */
  private String gend;
  /**
   * 民族
   */
  private String naty;
  /**
   * 出生日期
   */
  private String brdy;
  /**
   * 联系电话
   */
  private String tel;
  /**
   * 参保所属医保区划
   */
  private String insu_admdvs;
  /**
   * 单位编号
   */
  private String emp_no;
  /**
   * 单位名称
   */
  private String emp_name;
  /**
   * 安置地所属行政区代码
   */
  private String rloc_admdvs;
  /**
   * 安置区类型
   */
  private String rloc_coty_type;
  /**
   * 安置地医保机构名称
   */
  private String rloc_hsorg_name;
  /**
   * 安置地医保机构联系人
   */
  private String rloc_hsorg_coner;
  /**
   * 安置地医保机构联系电话
   */
  private String rloc_hsorg_tel;
  /**
   * 异地联网方式
   */
  private String out_onln_way;
  /**
   * 异地安置原因
   */
  private String rloc_rea;
  /**
   * 居外地址
   */
  private String resout_addr;
  /**
   * 备注
   */
  private String memo;
  /**
   * 代办人姓名
   */
  private String agnter_name;
  /**
   * 代办人证件类型
   */
  private String agnter_cert_type;
  /**
   * 代办人证件号码
   */
  private String agnter_certno;
  /**
   * 代办人联系方式
   */
  private String agnter_tel;
  /**
   * 代办人联系地址
   */
  private String agnter_addr;
  /**
   * 代办人关系
   */
  private String agnter_rlts;
  /**
   * 开始日期
   */
  private String begndate;
  /**
   * 结束日期
   */
  private String enddate;
  /**
   * 异地备案上报状态
   */
  private String out_fil_upld_stas;
  /**
   * 附件数量
   */
  private String att_cnt;
  /**
   * 有效标志
   */
  private String vali_flag;
  /**
   * 复核标志
   */
  private String rchk_flag;
  /**
   * 数据唯一记录号
   */
  private String rid;
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
   * 数据创建时间
   */
  private String crte_time;
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
   * 统筹区编号
   */
  private String poolarea_no;
  /**
   * 转出地定点医药机构编号
   */
  private String trafout_fixmedins_code;
  /**
   * 转出地定点医药机构名称
   */
  private String trafout_fixmedins_name;

}
