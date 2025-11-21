package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 数据治理非标数据
 */
@Data
@Entity(name = "data_governance")
@TableName("data_governance")
public class DataGovernance extends Model<DataGovernance> {
    @TableId
    @Id
    private String id;

    //定点编号
    private String fixedPointNumber;
    //所处表名
    private String nameTable;
    //定点名称
    private String fixedPointName;
    //个人编号
    private String personal_number;
    //姓名
    private String name;
    //身份证号
    private String IdNumber;
    //创建时间
    private String dataTime;
    //就诊ID
    private String visitID;
    //结算ID
    private String SettlementID;
    //错误代码
    private String errorCode;
    //规则名称
    private String ruleName;
    //医疗类别
    private String medicalCategory;
    //下发时间
    private String deliveryTime;

    //批次号
    private String upload_no ;
    //统筹区
    private String admdvs;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

    @Transient
    @TableField(exist = false)
    //定点编号数据权限
    private String fixedPointNumberPermission;
    @Transient
    @TableField(exist = false)
    private Integer pageNo = 1;
    @Transient
    @TableField(exist = false)
    private Integer pageSize = 10;
    //开始时间
    @Transient
    @TableField(exist = false)
    private String start_time;
    //结束时间
    @Transient
    @TableField(exist = false)
    private String end_time;

}
