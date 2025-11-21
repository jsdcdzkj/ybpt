package com.jsdc.ybpt.model_query;

import lombok.Data;

/*
 * 数据字典类型表
 * */
@Data
public class DatadictypeA {

    private Integer pageNo;
    private Integer pageSize;

    private String nat_data_dic_id;//国家数据字典ID
    private String nat_dic_val_code;//国家字典值代码
    private String nat_dic_val_name;//国家字典值名称
    private String prnt_dic_val_code;//父级字典值代码
    private String dic_type_code;//字典类型代码
    private String ext_flag;//可扩展标志
    private String dic_souc_admdvs;//字典来源医保区划
    private String pinyin;//拼音助记码
    private String wubi;//五笔助记码
    private String seq;//顺序号
    private String memo;//备注
    private String vali_flag;//有效标志
    private String rid;//数据唯一记录号
    private String crte_time;//数据创建时间
    private String updt_time;//数据更新时间
    private String crter_id;//创建人id
    private String crter_name;//创建人姓名
    private String crte_optins_no;//创建机构编号
    private String opter_id;//经办人id
    private String opter_name;//经办人姓名
    private String opt_time;//经办时间
    private String optins_no;//经办机构编号
    private String ver;//版本号


}
