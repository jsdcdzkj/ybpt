package com.jsdc.ybpt.agreementsignModel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @projectName: ybpt
 * @className: NetTagRegister
 * @author: wp
 * @description: 用户注册信息表（用于缓存回流库用户数据的注册状态、用户类型 等）
 * @date: 2022/9/6 14:48
 */
@Data
@Entity(name = "net_tag_register")
@TableName("net_tag_register")
public class NetTagRegister {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    /**
     * 关联回流库的用户id
     */
    private String user_id;

    /**
     * 用户类型 1：个人 2:企业
     */
    private String user_type;
    /**
     * 法大大id
     */
    private String customerId;

    /**
     * 企业customerId
     */
    private String company_customer_id;

    /**
     * 企业认证url
     */
    private String company_authentication_url;

    /**
     * 个人认证url
     */
    private String person_authentication_url;

    /**
     * 个人customerId
     */
    private String personal_customer_id;

    /**
     * 企业印章id
     */
    private String company_seal_id;

    /**
     * 个人印章id
     */
    private String personal_seal_id;

    /**
     * 告知手续印章id
     */
    private String procedures_seal_id;

    /**
     * 是否认证 0：否  1： 是
     */
    private String is_regist;

    /**
     * 认证状态 0：未认证 1：企业已认证，个人未认证 2.企业已认证，个人未认证，企业已签署自动签章 3：企业、个人均已认证
     */
    private String register_status;

    /**
     * 是否上传印章 0：否  1：是
     */
    private String is_upload_seal;

    private String seal_id;

    private String file_path;
    /**
     * 企业实名认证地址返回的交易号，在实名证书申请时用到
     */
    private String transaction_no;

    /**
     * 个人实名认证地址返回的交易号，在实名证书申请时用到
     */
    private String p_transaction_no;

    /**
     * 授权自动签章返回的交易号，在签署完成回调接口中能用到，用于区分手动签章回调和授权自动签章回调
     */
    private String auth_transaction_no;

    /**
     * 授权自动签章pdf回显
     */
    private String auth_pdf_view;

    /**
     * 授权自动签章pdf下载
     */
    private String auth_pdf_download;

    /**
     * 企业实名认证地址返回的交易号
     */
    private String company_transaction_no;

    /**
     * 个人实名认证地址返回的交易号
     */
    private String personal_transaction_no;

    /**
     * 企业印章内容
     */
    private String company_seal_content;

    /**
     * 注册结果
     */
    private String register_result;


    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态


}
