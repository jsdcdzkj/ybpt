package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Transient;

/**
 * WM_TCMPAT_INFO_B（西药中成药信息表）
 */
@Data
public class MedicineInfo {
    //医疗目录编码	;
    private String med_list_codg;
    //药品商品名	;
    private String drug_prodname;
    //药品通用名	;
    private String drug_genname;
    //药品剂型	;
    private String drug_dosform;
    //药品规格	;
    private String drug_spec;
    //包装规格	;
    private String pacspec;
    //开始日期	;
    private String begndate;
    //结束日期	;
    private String enddate;
    //生产企业名称	;
    private String prodentp_name;
    //限制使用范围	;
    private String lmt_usescp;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;


}
