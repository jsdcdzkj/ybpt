package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * 费用明细表
 */
@Data
public class FeeListDVo {

  //数量
  private String  cnt ;
  private String  MDTRT_ID ;
  //单价
  private String  pric ;
  //明细项目费用总额
  private String  det_item_fee_sumamt ;

  //医药机构目录编码
  private String medins_list_codg;
  //医药机构目录名称
  private String medins_list_name;

  //医疗类别
  private String     med_type;

  //商品名
  private String     prodname;

  //规格
  private String  SPEC;




  @Transient
  @TableField(exist = false)
  private Integer pageNo ;

  @Transient
  @TableField(exist = false)
  private Integer pageSize ;

}
