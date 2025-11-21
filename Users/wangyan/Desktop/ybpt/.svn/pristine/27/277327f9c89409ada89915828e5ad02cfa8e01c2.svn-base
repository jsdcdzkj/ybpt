package com.jsdc.ybpt.abnormal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "settle_abnormal")
@TableName("settle_abnormal")
public class SettleAbnormal extends Model<SettleAbnormal> {
    @TableId(value = "id")
    @Id
    private String id ;

    private String rid;
    private String org_code;//机构编码
    private String org_name;//机构名称
    private String area;//统筹区
    private String carno;//身份证号
    private String name;//人员姓名
    private String settle_time;//费用发生时间
    private String settle_type;//结算类别
    private String med_type;//医疗类别
    private String catalogue_type;//目录大类
    private String catalogue_code;//目录编码
    private String catalogue_code_new;//修改后编码
    private String reason_one;//原因1
    private String reason_two;//原因2
    private String reason_three;//原因3
    private String reason_four;//原因4
    private String reason_five;//原因5
    private String insured_persons_no;//参保人编号
    //就诊id;
    private String mdtrt_id;
    //结算ID
    private String setl_id;
    //项目名称
    private String project_name;


    private String if_upload;//机构是否修改上传
    private String upload_time;//上传时间
    private String upload_no;//上传批次
    private String update_time;//机构修改时间

    private String createUser;      //创建人
    private Date createTime;      //创建时间


}
