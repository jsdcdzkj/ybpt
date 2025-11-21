package com.jsdc.ybpt.capitalSettlement;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;


/**
 * 资金清算确认书(QsOrgConfirmation )
 */
@Data
@Entity(name = "qs_org_confirmation")
@TableName("qs_org_confirmation")
public class QsOrgConfirmation extends Model<QsOrgConfirmation> {
    @TableId
    @Id
    private String id;
    //机构编码
    private String org_code;
    //机构名称
    private String org_name;
    //年份
    private String year;
    //统筹区
    private String admdvs;
    //机构签章时间
    private String org_sign_time;
    //医保中心签章时间
    private String centre_sign_time;
    //确认书状态 1:待提交 2：已提交（机构签章） 3：已审核（医保签章）
    private String status;
    //签章PDF下载地址
    @Column(length = 1000)
    private String down_pdf_path;
    //医保签章PDF地址
    @Column(length = 1000)
    private String centre_pdf_path;
    //法大大文件合同ID
    private String fddContractId;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

    @Transient
    @TableField(exist = false)
    private Integer pageIndex ;
    @Transient
    @TableField(exist = false)
    private Integer pageSize ;
}
