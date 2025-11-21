package com.jsdc.ybpt.model_query;

import lombok.Data;

/*
* 医保目录限价信息表
* */
@Data
public class HilistlmtpricD {

    private Integer pageNo;
    private Integer pageSize;

    private String hilist_code;//医保目录编码
    private String hilist_lmtpric_type;//医保目录限价类型
    private String begndate;//开始日期
    private String insu_admdvs;//参保所属医保区划
    private String insutype_name;//参保所属医保区划
    private String tabname;//表名
    private String overlmt_dspo_way;//医保目录超限处理方式
    private String enddate;//结束日期
    private String hilist_pric_uplmt_amt;//医保目录定价上限金额
    private String vali_flag;//有效标志
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

}
