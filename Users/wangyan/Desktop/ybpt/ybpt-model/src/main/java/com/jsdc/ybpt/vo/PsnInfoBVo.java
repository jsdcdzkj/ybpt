package com.jsdc.ybpt.vo;

import lombok.Data;

@Data
public class PsnInfoBVo {
  private String PSN_NO;//人员编号
  private String PSN_NAME;//人员姓名
  private String GEND;//性别
  private String BRDY;//出生日期
  private String PSN_CERT_TYPE;//人员证件类型
  private String CERTNO;//证件号码
  private String HSECFC;//电子凭证号
  private String TEL;//联系电话
  private String MOB;//手机号码
  private String NATY;//民族
  private String SURV_STAS;//生存状态
}
