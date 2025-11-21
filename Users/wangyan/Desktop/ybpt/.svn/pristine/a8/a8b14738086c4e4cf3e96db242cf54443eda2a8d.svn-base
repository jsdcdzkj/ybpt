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
 * 耗材收费标识库
 */
@Data
@Entity(name = "consumables_sign")
@TableName("consumables_sign")
public class ConsumablesSign extends Model {
    @TableId
    @Id
    private String id;

    //1、现27位国家编码：填报最新版医保耗材收费标识库中的“现27位码”，必填
    private String mcs_code_new;
    //2、原27位国家编码：填报前20位在《江苏省医保耗材分类与代码映射样本V2.1》的27位国家编码，必填
    private String mcs_code;
    //3、国家编码调整变动时间：填报国家医保局发布版本时间，如：20220419，必填
    private String mcs_code_time;
    //4、注册备案号：填报最新版医保耗材收费标识库中的相关信息，必填
    private String reg_fil_no;
    //5、注册备案产品名称：填报最新版医保耗材收费标识库中的相关信息，必填
    private String reg_fil_prod_name;
    //6、单件产品名称：填报最新版医保耗材收费标识库中的相关信息，必填
    private String name_individual_product;
    //7、生产企业：填报最新版医保耗材收费标识库中的相关信息，必填
    private String mcs_entp;
    //8、规格：填报最新版医保耗材收费标识库中的相关信息，必填
    private String spec;
    //9、型号：填报最新版医保耗材收费标识库中的相关信息，必填
    private String mol;
    //10、省平台挂网编码：如有，非必填
    private String product_num;
    //11、申诉说明：简要描述申诉情形，必填
    private String verify_reason;
    //12、定点机构联系人：必填
    private String fixmedins_contacts;
    //13、定点机构联系电话：必填
    private String fixmedins_phone;
    //0 待确认 1通过 2驳回
    private String status;
    //驳回原因
    private String reason;
    //统筹区
    private String admdvs;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    private String createUser;      //创建人
    private String createUserName;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志
}
