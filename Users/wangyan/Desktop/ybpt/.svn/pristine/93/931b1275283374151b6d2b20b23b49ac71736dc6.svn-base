package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 转院申请事件记录表
 */
@Data
@Entity(name = "refl_appy_evt_c")
@TableName("refl_appy_evt_c")
public class ReflAppyEvtC extends Model {
  @TableId(value = "id", type = IdType.AUTO)
  @Id
  private String id;//主键
  private String psn_cert_type;//人员证件类型
  private String certno;//证件号码
  private String psn_name;//人员姓名
  private String gend;//性别

  private String evtsn;//事件流水号
  private String trt_dcla_detl_sn;//待遇申报明细流水号
  private String serv_matt_inst_id;//服务事项实例ID
  private String serv_matt_node_inst_id;//服务事项环节实例ID
  private String evt_inst_id;//事件实例ID
  private String psn_no;//人员编号
  private String psn_insu_rlts_id;//人员参保关系ID
  private String brdy;//出生日期
  private String tel;//联系电话
  private String addr;//联系地址
  private String emp_no;//单位编号
  private String emp_name;//单位名称
  private String fixmedins_code;//定点医药机构编号
  private String fixmedins_name;//定点医药机构名称
  private String hosp_lv;//医院等级
  private String fix_blng_admdvs;//定点归属医保区划
  private String diag_code;//诊断代码
  private String diag_name;//诊断名称
  private String drord;//医嘱
  private String dise_cond_dscr;//疾病病情描述
  private String reflin_medins_no;//转往定点医药机构编号
  private String reflin_medins_name;//转往医院名称
  private String out_flag;//异地标志
  private String refl_date;//转院日期
  private String refl_rea;//转诊转院原因
  private String refl_opnn;//转院意见
  private String memo;//备注
  private String agnter_name;//代办人姓名
  private String agnter_certno;//代办人证件号码
  private String agnter_tel;//代办人联系方式
  private String agnter_addr;//代办人联系地址
  private String begndate;//开始日期
  private String enddate;//结束日期
  private String setl_id;//结算ID
  private String mdtrt_id;//就诊ID
  private String rid;//数据唯一记录号
  private String updt_time;//数据更新时间
  private String crter_id;//创建人ID
  private String crter_name;//创建人姓名
  private String crte_time;//数据创建时间
  private String crte_optins_no;//创建机构编号
  private String opter_id;//经办人ID
  private String opter_name;//经办人姓名
  private String opt_time;//经办时间
  private String optins_no;//经办机构编号
  private String poolarea_no;//统筹区编号
  private String allo_setl_cnt;//允许结算次数
  private String insutype;//险种类型

  private String rchk_flag_name;//复核标志
  private String vali_flag_name;//有效标志
  private String psn_cert_type_name;//人员证件类型
  private String insutype_name;//险种类型

  private String mdtrtarea_admdvs;//就医地医保区划
  private String mdtrtarea_admdvs_name;//就医地医保区划

  private String insu_admdvs;//参保所属医保区划
  private String insu_admdvs_name;//参保所属医保区划

  private String out_fil_upld_stas;//异地备案上报状态
  private String out_fil_upld_stas_name;//异地备案上报状态

  private String refl_fil_type;//转院备案类别
  private String refl_fil_type_name;//转院备案类别

  private String dcla_souc;//申报来源
  private String dcla_souc_name;//申报来源

  private String refl_setl_flag;//是否转院结算
  private String refl_setl_flag_name;//是否转院结算

  private String hosp_agre_refl_flag;//医院同意转院标志
  private String hosp_agre_refl_flag_name;//医院同意转院标志

  private String agnter_cert_type;//代办人证件类型
  private String agnter_cert_type_name;//代办人证件类型

  private String agnter_rlts;//代办人关系
  private String agnter_rlts_name;//代办人关系

  /**
   * 01	新增
   * 02	修改
   * 03	停用
   * 04	冲正
   * 05	撤销
   * 06	冲撤销
   * 07	启用
   * 08	删除
   */
  private String evt_type;//事件类型
  private String evt_type_name;//事件类型
  /**
   * 无效 0
   * 有效 1
   */
  private String vali_flag;//有效标志
  /**
   * 其他 9
   * 未审核 0
   * 审核通过 1
   * 审核不通过 2
   * 已撤销 3
   * 已回退 4
   * 初审通过 5
   * 初审不通过 6
   * 审批通过 7
   * 审批不通过 8
   */
  private String rchk_flag;//复核标志

  //审核建议
  private String proposal ;
  //审核结果
  private String checkRusult ;

  private String createUser;      //创建人
  private Date createTime;      //创建时间
  private String updateUser;      //创建用户
  private Date updateTime;      //创建时间
  private String is_del;          //删除标志
}
