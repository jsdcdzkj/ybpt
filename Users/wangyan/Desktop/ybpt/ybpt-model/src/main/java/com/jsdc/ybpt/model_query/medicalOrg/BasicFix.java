package com.jsdc.ybpt.model_query.medicalOrg;

import lombok.Data;

@Data
public class BasicFix {
    //定点医药机构编号
    private String fixmedins_code;
    //统筹区编号;
    private String poolarea_no;
    //定点医药机构名称;
    private String fixmedins_name;
    //医疗机构简称;
    private String medins_abbr;
    //零售药店简称;
    private String rtal_phac_abbr;
    //定点医疗服务机构类型;
    private String fixmedins_type;
    //联系地址;
    private String yyaddr;
    //联系地址;
    private String ydaddr;
    //定点归属医保区划;
    private String fix_blng_admdvs;
    //医院等级;
    private String hosp_lv;
    //限价医院等级;
    private String lmtpric_hosp_lv;
    //起付线医院等级;
    private String dedc_hosp_lv;
    //医保办负责人姓名;
    private String hi_resper_name;
    //医保办负责人联系电话;
    private String hi_resper_tel;
    //医保办负责人证件类型;
    private String hi_resper_cert_type;
    //医保办负责人证件号码;
    private String hi_resper_certno;
    //国家异地平台机构编号;
    private String nat_plaf_code;
    //省内异地平台机构编号;
    private String prov_plaf_code;
    //定点联网开通标志;
    private String fix_onln_open_flag;
    //异地联网开通类型;
    private String out_onln_open_type;
    //异地医药机构类型;
    private String out_fixmedins_type;
    //开始时间;
    private String begntime;
    //结束时间;
    private String endtime;


    private Integer pageNo;
    private Integer pageSize;

}
