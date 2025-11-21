package com.jsdc.ybpt.abnormal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
*稽核疑点
* Author wzn
* Date 2022/11/15 14:53
*/
@Data
@Entity(name = "check_suspicions")
@TableName("check_suspicions")
public class CheckSuspicions extends Model<CheckSuspicions> {
    @TableId
    @Id
    private String rid;
    //人员姓名
    private String psn_name;
    //身份证号
    private String cert_no;

    //性别
    private String gend;
    //年龄
    private String age;
    //结算时间
    private String setl_time;

    //定点医药机构名称
    private String fixmedins_name;
    //定点医药机构编号
    private String fixmedins_code;
    //药品通用名名称
    private String genericNameOfTheDrug;
    //药品省编码
    private String drugProvinceCode;
    //国家药品码
    private String nationalDrugCode;
    //就诊id;
    private String mdtrt_id;
    //结算ID
    private String setl_id;
    //单价
    private String pric;
    //数量	;
    private String cnt;
    //明细项目费用总额	;
    private String det_item_fee_sumamt;
    //个人自付比例
    private String psn_selfpay_amt;
    private String lmt_used_flag;//限制使用标志
    //医疗类别
    private String med_type;






    //医疗费总额
    private String medfee_sumamt;
    //统筹基金支出
    private String hifp_pay;
    private String pool_prop_selfpay;//基本医疗统筹支付比例
    private String cvlserv_pay;//公务员医疗补助资金支出
    // 补充医疗保险基金支出
    private String hifes_pay;
    // 大病补充医疗保险基金支出
    private String hifmi_pay;
    //大额医疗补助基金支出
    private String hifob_pay;
    // 医疗救助基金支出
    private String maf_pay;
    // 个人账户支出
    private String acct_pay;
    //现金支付金额
    private String cash_payamt;
    //参保所属医保区划
    private String insu_admdvs_name;
    //通用名编码
    private String common_name_code;

    //限制使用范围	;
    private String lmt_usescp;
    //REG
    private String reg;
    //人员类别
    private String psn_type;
    //处方时间
    private String timeOfPrescription;
    private String dept_code;//所属科室
    private String inpatientWard;//病区
    private String doctorSCode;//医生编码
    private String nameOfDoctor;//医生姓名
    private String admissionTime;//入院时间
    private String numberOfAdmittedDiseases;//入院病种编码
    private String admittingDiagnosis;//入院诊断
    private String timeOfDischarge;//出院时间
    private String numberOfDischargedDiseases;//出院病种编码
    private String dischargeDiagnosis;//出院诊断




    //审核状态 0待审核  1审核通过  2审核驳回
    private String status  ;

    private String upload_time;//上传时间









    private String isUpload;//是否上传举证文件 0：未上传 1:已上传
    //批次号
    private String upload_no ;

    private String describe;//备注

    private String verify_reason;//审核意见





}
