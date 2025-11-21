package com.jsdc.ybpt.agreementsignModel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 协议网签-补充协议模板表
 */
@Data
@Entity(name = "net_tag_supp")
@TableName("net_tag_supp")
public class NetTagSupp extends Model<NetTagSupp> {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    //标题
    private String title;
    //附件
    private String file_id;
    //失效日期
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date invalid_date;

    //医保机构编码
    private String medical_code;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态 0有效 1无效

    @Transient
    @TableField(exist = false)
    //医药机构
    private List<String> medical = new ArrayList<>();


    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    //医疗机构名称
    private String medicalName;

    @Transient
    @TableField(exist = false)
    //创建人名称
    private String createName;

    /** 甲方机构名称 **/
    private String party_a_name;
    /** 甲方机构地址 **/
    private String party_a_address;
    /** 甲方签章 **/
    private String party_a_seal;
    /** 甲方法定代表人 **/
    private String party_a_legal_person;

    /** 乙方机构名称 **/
    private String party_b_name;
    /** 乙方机构地址 **/
    private String party_b_address;
    /** 乙方机构编码 **/
    private String party_b_code;
    /** 乙方签章 **/
    private String party_b_seal;
    /** 乙方法定代表人 **/
    private String party_b_legal_person;

    /** 附件 word */
    private String file_word;

    /** 附件 pdf */
    private String file_pdf;
    @Transient
    @TableField(exist = false)
    private String pdf_path;
    @Transient
    @TableField(exist = false)
    private String address;


}
