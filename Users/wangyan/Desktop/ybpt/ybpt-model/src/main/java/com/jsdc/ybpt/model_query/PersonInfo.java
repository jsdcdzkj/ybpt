package com.jsdc.ybpt.model_query;

import lombok.Data;

@Data
public class PersonInfo {
    private String psn_no;          //人员编号
    private String psn_mgtcode;         //人员管理码
    private String psn_name;            //人员姓名
    private String alis;            //别名
    private String gend;            //性别
    private String brdy;            //出生日期
    private String file_brdy;           //档案出生日期
    private String psn_cert_type;           //人员证件类型
    private String certno;          //证件号码
    private String hsecfc;          //电子凭证号
    private String tel;         //联系电话
    private String mob;         //手机号码
    private String naty;            //民族
    private String nat_regn_code;           //国家地区代码
    private String email;           //电子邮箱
    private String polstas;         //政治面貌
    private String fst_patc_job_date;           //首次参加工作日期
    private String resd_natu;           //户口性质
    private String resd_loc_admdvs;         //户口所在地医保区划
    private String hsreg_addr;          //户籍地址
    private String hsreg_addr_poscode;          //户籍地址邮政编码
    private String live_admdvs;         //居住地医保区划
    private String live_addr;           //居住地址
    private String live_addr_poscode;           //居住地邮政编码
    private String resdbook_no;         //户口簿编号
    private String mrg_stas;            //婚姻状态
    private String hlcon;           //健康状况
    private String memo;            //备注
    private String surv_stas;           //生存状态
    private String mul_prov_mnt_flag;           //多省维护标志
    private String admdut;          //行政职务
    private String retr_type;           //离退休类型
    private String retr_type_code;           //离退休类型
    private String grad_schl;           //毕业院校
    private String educ;            //学历
    private String pro_tech_duty_lv;            //专业技术职务等级
    private String nat_prfs_qua_lv;         //国家职业资格等级
    private String vali_flag;           //有效标志
    private String rid;         //数据唯一记录号
    private String crte_time;           //数据创建时间
    private String updt_time;           //数据更新时间
    private String crter_id;            //创建人id
    private String crter_name;          //创建人姓名
    private String crte_optins_no;          //创建机构编号
    private String opter_id;            //经办人id
    private String opter_name;          //经办人姓名
    private String opt_time;            //经办时间
    private String optins_no;           //经办机构编号
    private String ver;         //版本号

}
