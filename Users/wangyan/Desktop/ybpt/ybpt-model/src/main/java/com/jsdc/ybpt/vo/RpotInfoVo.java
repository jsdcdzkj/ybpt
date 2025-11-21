package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 病理检查报告信息表
 */
@Data
public class RpotInfoVo {

  //人员证件类型
  private String psn_cert_type ;
  //证件号码
  private String certno;
  //人员姓名
  private String psn_name;
  //性别
  private String gend;
  //出生日期
  private String brdy;
  //联系地址
  private String LIVE_ADDR;
  //人员编号
   private String       psn_no;
  //病理号
  private String         palg_no;
  //冻结号
  private String        frez_no;
  //送检日期
  private String        cma_date;
  //报告日期
  private String        rpt_date;
  //送检材料
  private String        cma_matl;
  //临床诊断
  private String        clnc_diag;
  //检查所见
  private String        exam_fnd;
  //免疫组化
  private String       sabc;
  //病理诊断
  private String        palg_diag;
  //报告医师
  private String        rpot_doc;

  //户口所在地医保区划
  private String resd_loc_admdvs ;




  @Transient
  @TableField(exist = false)
  private Integer pageNo ;

  @Transient
  @TableField(exist = false)
  private Integer pageSize ;

}
