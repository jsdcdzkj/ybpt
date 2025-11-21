package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 异地就医费用明细表
 */
@Data
public class OutmedFeeListVo {

  //定点医药机构编号
  private String    fixmedins_code;
  private String     mdtrt_id;
  //定点医药机构名称	;
  private String       fixmedins_name;
  //人员编号	;
  private String     psn_no;
  //人员参保关系id	;
  private String     psn_insu_rlts_id;
  //参保所属医保区划	;
  private String    insu_admdvs;
  //支付地点类别;
  private String     pay_loc;
  //医疗类别	;
  private String     med_type;
  //数量	;
  private String      cnt;
  //单价
  private String pric;
  //明细项目费用总额	;
  private String      det_item_fee_sumamt;
  //定价上限金额;
  private String     pric_uplmt_amt;
  //自付比例	;
  private String      selfpay_prop;
  //全自费金额	;
  private String     fulamt_ownpay_amt;
  //超限价自费费用	;
  private String      overlmt_selfpay;
  //先行自付金额	;
  private String     preselfpay_amt;
  //符合范围金额	;
  private String    inscp_amt;
  //公务员床位费金额;
  private String     cvlserv_bedfee_amt;
  //医院减免金额	;
  private String    medins_disc_amt;
  // 收费项目等级	;
  private String     chrgitm_lv;
  //医保目录编码	;
  private String     hilist_code;
  //医保目录名称	;
  private String     hilist_name;
  //目录类别	;
  private String    list_type;
  //医疗目录编码;
  private String    med_list_codg;
  // 医药机构目录编码	;
  private String    medins_list_codg;
  //医药机构目录名称;
  private String     medins_list_name;
  //医疗收费项目类别	;
  private String     med_chrgitm_type;
  //商品名;
  private String    prodname;
  // 规格	;
  private String    spec;
  //剂型名称	;
  private String    dosform_name;
  // 开单科室编码;
  private String     bilg_dept_codg;



  @Transient
  @TableField(exist = false)
  private Integer pageNo ;

  @Transient
  @TableField(exist = false)
  private Integer pageSize ;

}
