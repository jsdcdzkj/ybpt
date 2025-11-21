package com.jsdc.ybpt.model_query.medicalOrg;

import lombok.Data;

@Data
public class MedicalDept {
    private String fixmedins_code;//定点医疗机构代码
    private String fixmedins_name;//定点医疗机构名称
    private String dept_no;//科室编码
    private String dept_name;//科室名称
    private String begntime;//开始时间
    private String endtime;//结束时间
    private String dept_resper_name;//科室负责人姓名
    private String dept_resper_tel;//科室负责人电话
    private String aprv_bed_cnt;//批准床位数量
    private String dr_psncnt;//医师人数
    private String phar_psncnt;//药师人数
    private String nurs_psncnt;//护士人数
    private String tecn_psncnt;//技师人数
    //用到的字段
    private String addr;//联系地址
    private String cred_lv;//医疗等级  1 一级 2	二级 3	三级 9	未定级
    private String cred_lv_name;//医疗等级名称
    private String legrep_name;//法人代表
    private String biz_way;//经营方式
    private String cred_lv_type;//机构区分（1、医疗机构2、零售药店）
    private String tel;//联系电话
    private String drug_biz_lic_no;//药品经营许可证号
    private String biznat;//经营性质
    private String uscc;//统一社会信用代码

}
