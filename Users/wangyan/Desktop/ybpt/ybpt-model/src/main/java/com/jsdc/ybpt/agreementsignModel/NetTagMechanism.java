package com.jsdc.ybpt.agreementsignModel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 协议网签-医药机构网签表(医药机构关联网签表)(医药机构关联补充协议表)
 */
@Data
@Entity(name = "net_tag_mechanism")
@TableName("net_tag_mechanism")
public class NetTagMechanism extends Model<NetTagMechanism> {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    //国家编码（机构编码）
    private String mechanism_code;
    //医保编码
    private String medical_code;
    //网签状态(0未签章/未确认 1已签章/已确认 2已解约 3已过期 4、驳回 5、撤销)
    private Integer status;
    //解约时间
    private Date rescind;
    //网签年份
    private String year;
    //协议表ID
    private String agreement_id;
    //合同pdf
    private String pdf_id;
    //类型(0协议 1补充协议)
    private String type;
    //合同查看地址
    private String review_url;
    //合同下载地址
    private String download_url;
    //合同编码（系统自动生成，唯一，签章使使用）
    private String contract_id;

    //授权协议合同编码
    private String licensing_id;

    //是否已签章 1：甲乙方均未签章 2：乙方已签章，甲方未签章 3：甲方已签章，乙方未签章 4：甲乙方均已签章
    private String has_sign;
    //甲方手动签章的交易号
    private String company_transaction_id;

    private String operate_user;

    //所属行政区划
    private String admdvs;

    /**
     * 审批意见
     */
    private String approval_opinion;

    /**
     * 审批时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date approval_time;

    /**
     * 撤销原因
     */
    private String revoke_reason;

    private String createUser;      //创建人
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

    //机构类别type(1-医疗机构 2-零售药店) 注:目前就这两种 此数据选择机构时必填
    private String medical_type;

    //签署日期
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date signDate;

    //q起始日期
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date startDate;

    //过期时间
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd")
    private Date invalid_date;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private String fixmedins_name;//定点医疗机构名称

    @Transient
    @TableField(exist = false)
    private String dept_resper_name;//联系人

    @Transient
    @TableField(exist = false)
    private String dept_resper_tel;//联系人电话

    @Transient
    @TableField(exist = false)
    private String legrep_name;//法定代表人

    @Transient
    @TableField(exist = false)
    private String start_time;


    @Transient
    @TableField(exist = false)
    private String end_time;

    //机构编码集合
    @Transient
    @TableField(exist = false)
    private String medicalCodes;

    //机构编码集合
    @Transient
    @TableField(exist = false)
    private List<String> medicalCodeList;



    //补充协议名称
    @Transient
    @TableField(exist = false)
    private String title;

    @Transient
    @TableField(exist = false)
    private String is_agreement;

    @Transient
    @TableField(exist = false)
    private String operate_user_name;

    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
    private Date revoke_time;


}
