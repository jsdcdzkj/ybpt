package com.jsdc.ybpt.model_query;

import lombok.Data;

@Data
public class EmpPayInfo {
    private String emp_clct_detl_id;//单位缴费明细iD
    private String emp_no;//单位编号
    private String emp_name;//单位名称
    private String cashym;//费款所属期
    private String accrym_begn;//对应费款所属期起始
    private String accrym_end;//对应费款所属期结束
    private String insutype;//险种类型
    private String emp_clct_paraval;//单位缴费比例或定额标准
    private String emp_clctstd_sumamt;//单位缴费基数总额
    private String emp_clct_amt;//单位缴费金额
    private String psn_clct_paraval;//个人缴费比例或定额标准
    private String psn_clctstd_sumamt;//个人缴费基数总额
    private String psn_clct_amt;//个人缴费金额
    private String oth_clct_amt;//其他缴费金额
    private String into_acct_amt;//划入个人账户金额
    private String clct_flag;//缴费标志
    private String clct_type;//缴费类型
    private String clct_psncnt;//缴费人数
    private String staf_totlwag;//职工工资总额
    private String amt;//金额
    private String inte;//利息
    private String latefee;//滞纳金
    private String revs_flag;//核销标志
    private String arvl_date;//到账日期
    private String arvl_opter;//到账操作员
    private String arvl_bchno;//到账批次号
    private String intsury_time;//入国库时间
    private String ursn_time;//上解时间
    private String elec_taxrpt_no;//电子税票号码
    private String dcla_prd;//申报周期
    private String poolarea_no;//统筹区编号
    private String taxdept_code;//主管税务部门代码
    private String clct_bill_id;//征集通知单id
    private String bill_flag;//征集标志
    private String plan_bchno;//计划执行批次号
    private String crte_optins_no;//创建机构编号
    private String crter_id;//创建人id
    private String crter_name;//创建人姓名
    private String crte_time;//数据创建时间
    private String optins_no;//经办机构编号
    private String opter_id;//经办人id
    private String opter_name;//经办人姓名
    private String opt_time;//经办时间
    private String rid;//数据唯一记录号
    private String updt_time;//数据更新时间

    private Integer pageNo;
    private Integer pageSize;

}
