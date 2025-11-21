package com.jsdc.ybpt.model_query.medicalOrg;

import lombok.Data;

@Data
public class FixmedinsCntrRegD {
    private Integer pageNo = 1;
    private Integer pageSize = 10;
    private String fix_cntr_id;//定点协议ID
    private String fixmedins_code;//定点医药机构编号
    private String fix_cntr_type;//定点协议类型
    private String begntime;//开始时间
    private String fix_blng_admdvs;//定点归属医保区划
    private String fix_cntr_name;//定点协议名称
    private String cntr_sign_year;//协议签订年度
    private String endtime;//结束时间
    private String cntr_sign_time;//协议签订时间
    private String vali_flag;//有效标志
    private String memo;//备注
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
    private String hi_sys_emp_code;//医保系统单位代码
    private String fixmedins_chrg_lv;//定点医疗机构收费等级
    private String fix_cntr_serv_obj;//定点协议服务对象
    private String fix_cntr_serv_scp;//定点协议服务范围
    private String fix_cntr_elec_doc;//定点协议电子文档
    private String ver_name;//版本名称
    private String isu_flag;//下发标志
    private String tram_data_id;//传输数据ID
    private String cntr_stas;//协议状态
    private String efft_time;//生效时间
    private String invd_time;//失效时间

    private String medins_name;//定点医药机构名称
    private String insutype;//险种
    private String med_type;//医疗类别

}
