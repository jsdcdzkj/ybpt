package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * PSN_FIX_MAIN_DETL_D（人员定点登记主明细信息表）
 */
@Data
public class PsnFixMainDetlDVo {

  /**
   * 险种类型
   */
  private String insutype  ;

    /**
     * 险种类型名称
     */
  private String insutypename  ;

  /**
   * 业务申请类型
   */
  private String biz_appy_type  ;
    /**
     * 业务申请类型名称
     */
  private String biz_appy_type_name  ;

  /**
   * 定点医药机构编号
   */
  private String fixmedins_code  ;

  /**
   * 定点医药机构名称
   */
  private String fixmedins_name  ;

  /**
   * 开始日期
   */
  private String begndate  ;

  /**
   * 结束日期
   */
  private String enddate  ;

  /**
   * 统筹区编号
   */
  private String poolarea_no  ;

  /**
   * 人员编号
   */
  private String psn_no  ;

  /**
   * 证件号码
   */
  private String certno  ;
    /**
     * 姓名
     */
  private String psn_name  ;
  /**
   * 性别
   */
  private String gend  ;
  /**
   * 出生日期
   */
  private String brdy  ;
  /**
   * 人员证件类型
   */
  private String psn_cert_Type  ;
  /**
   * 手机号码
   */
  private String mob  ;
  /**
   * 民族
   */
  private String naty  ;
  /**
   * 居住地址
   */
  private String live_addr  ;

  @Transient
  @TableField(exist = false)
  private Integer pageNo ;

  @Transient
  @TableField(exist = false)
  private Integer pageSize ;

  //电子凭证号
  private String hsecfc ;



}
