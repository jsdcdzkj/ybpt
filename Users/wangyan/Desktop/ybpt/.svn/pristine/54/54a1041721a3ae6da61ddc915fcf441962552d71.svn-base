package com.jsdc.ybpt.abnormal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "medical_care_abnormal")
@TableName("medical_care_abnormal")
public class MedicalCareAbnormal extends Model<MedicalCareAbnormal> {

    @TableId
    @Id
    private String id;
    //表名
    private String table_name;
    //机构编码
    private String org_code;
    //机构名称
    private String org_name;

    private String area;//统筹区
    //费用发生时间
    private String settle_time;
    //就诊id;
    private String mdtrt_id;
    //记账流水号
    private String account_seria_number;
    //结算ID
    private String setl_id;
    //医院传医师药师护师编码
    private String nurse_code;
    // 医院传医师药师护师名称
    private String nurse_name;
    // 修改后编码
    private String content;
    // 备注
    private String appeal_reason;

    private String insured_persons_no;//参保人编号

    private String if_upload;//机构是否修改上传
    private String upload_time;//上传时间
    private String upload_no;//上传批次
    private String update_time;//机构修改时间

    private String createUser;      //创建人
    private Date createTime;      //创建时间

}
