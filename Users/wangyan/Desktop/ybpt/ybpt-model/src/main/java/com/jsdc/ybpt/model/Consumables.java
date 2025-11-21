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
 * 定点机构新增耗材申报表
 */
@Data
@Entity(name = "consumables")
@TableName("consumables")
public class Consumables extends Model {
    @TableId
    @Id
    private String id;

    private String fixmedins_no;  //定点机构编码
    private String medins_name;     //定点机构名称
    private String mcs_code;    //耗材国家编码
    private String reg_fil_prod_name;  //注册备案产品名称
    private String reg_fil_no; //注册备案号
    private String matl;  //材质
    private String characteristics; //特征
    private String spec;              //规格
    private String mol;             //型号
    private String mcs_entp;        //耗材企业
    private String product_num;    //产品编号
    private String status;    //0 待确认 1通过 2驳回 3已同步
    private String verify_reason;    //审核意见
    private String opter_name;    //经办人
    private String opt_time;    //经办时间
    private String verify_msg;    //申诉说明


    @Transient
    @TableField(exist = false)
    private String ver; // 版本号


    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志
}
