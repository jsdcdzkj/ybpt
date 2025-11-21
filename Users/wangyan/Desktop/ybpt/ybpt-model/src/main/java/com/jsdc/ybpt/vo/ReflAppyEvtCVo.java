package com.jsdc.ybpt.vo;

import com.jsdc.ybpt.model.ReflAppyEvtC;
import lombok.Data;

@Data
public class ReflAppyEvtCVo extends ReflAppyEvtC {
  private String id;
  private String gend_name;//性别转义
  private String mob;//性别转义

  private Integer pageNo = 1;
  private Integer pageSize = 10;

  private String PSN_CERT_TYPE;//人员证件类型
  private String CERTNO;//证件号码
  private String HSECFC;//电子凭证号

  private String MEDINS_CODE;//医疗机构代码
  private String MEDINS_NAME;//医疗机构名称

  private String FIXMEDINS_CODE;//定点机构编号
  private String DEPT_NAME;//科室名称

  private String DIC_TYPE_CODE;//字典类型代码
  private String PRNT_DIC_VAL_CODE;//字典类型代码父类
  private String DIC_SOUC_ADMDVS;//字典来源医保区划

  private String DIAG_CODE;//诊断代码
  private String DIAG_NAME;//诊断名称
  private String DIAG_TYPE;//诊断类型
  private String nat_dic_val_code;//
  private String nat_dic_val_name;//

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
  private String RCHK_FLAG;//复核标志
}
