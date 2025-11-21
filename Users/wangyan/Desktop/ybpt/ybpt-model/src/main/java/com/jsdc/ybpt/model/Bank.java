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
 * 马上贷-银行信息表
 */
@Data
@Entity(name = "bank")
@TableName("Bank")
public class Bank extends Model<Bank> {
    @TableId
    @Id
    private String id;

    //银行名称
    private String bankName;
    //银行编码
    private String bankNo;

    //详细地址
    private String address;
    @Transient
    @TableField(exist = false)
    private String admdvsCode;
    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;
    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态


}
