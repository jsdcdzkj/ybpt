package com.jsdc.ybpt.abnormal;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity(name = "settle_error_data")
@TableName("settle_error_data")
public class SettleErrorData extends Model<SettleErrorData> {
    @TableId(value = "id")
    @Id
    private String id ;

    private String serial_number;//流水号
    private String org_code;//机构编码
    private String org_name;//机构名称
    private String area;//统筹区
    private String quantity;//数量
    private String price;//单价
    private String fixmedins_code;//国家码
    private String drug_name;//药品名称


    private String if_upload;//机构是否修改上传
    private String upload_time;//上传时间
    private String upload_no;//上传批次

    private String createUser;      //创建人

}
