package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 费医疗保障基金结算清单信息表
 */
@Data
public class MdcsFundSetlListVo {

  //定点医药机构名称
     private String      fixmedins_name;
  //定点医药机构编号
  private String   fixmedins_code;

  //申报时间
  private String      dcla_time;
          //人员姓名
          private String      psn_name;
  //性别
  private String      gend;
          //出生日期
          private String       brdy;
  //年龄
  private String      age;

          //民族
          private String      naty;
  //人员证件类型
  private String       psn_cert_type;
          //证件号码
          private String      certno;
  //职业
  private String       prfs;
          //现住址
          private String        curr_addr;
          //险种类型
          private String        insutype;
          //特殊人员类型
          private String  sp_psn_type;
  //单位名称
  private String      emp_name;
          //联系人电话
          private String     coner_tel;

  //新生儿入院类型
  private String      nwb_adm_type;

          //门诊慢特病就诊时间
          private String      opsp_mdtrt_time;
  //住院医疗类型
  private String      ipt_med_type;

          //治疗类别
          private String       trt_type;
  //入院时间
  private String       adm_time;
          //出院时间
          private String       dscg_time;

  //门急诊西医诊断
  private String       otp_wm_diag;
          //门急诊西医诊断疾病代码
          private String       otp_wm_diag_dise_code;
  //门急诊中医诊断
  private String      otp_tcm_diag;

          //主诊医师姓名
          private String      chfpdr_name;
  //主诊医师代码
  private String     chfpdr_code;
          //个人自付金额
          private String       psn_selfpay_amt;
  //个人账户支付金额
  private String    acct_payamt;
          //个人现金支付
          private String      psn_cashpay;
          //个人自费费用
          private String      psn_ownpay_fee;
          //医保结算等级
          private String      hi_setl_lv;
          //人员编号
          private String      psn_no;


  @Transient
  @TableField(exist = false)
  private Integer pageNo ;

  @Transient
  @TableField(exist = false)
  private Integer pageSize ;

}
