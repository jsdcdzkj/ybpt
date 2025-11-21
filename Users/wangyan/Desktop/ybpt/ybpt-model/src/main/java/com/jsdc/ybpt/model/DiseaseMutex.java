package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Data
@Entity(name = "disease_mutex")
@TableName("disease_mutex")
public class DiseaseMutex extends Model {
    @TableId
    @Id
    private String id;

    private String dise_code;//病种编码

    private String dise_name;//病种名称

    private String insured_type;//参保类型

    private String dise_mutex_code;//病种互斥编码

    private String dise_mutex_name;//病种互斥名称

    private String insured_mutex_type;//参保类型-互斥

    private String type;//互斥类型 1：门慢门特互斥  2：单病种互斥

    private String createUser;      //创建人
    private String createTime;      //创建时间
    private String updateUser;      //创建用户
    private String updateTime;      //创建时间
    private String is_del;          //删除标志
    @Transient
    @TableField(exist = false)
    private Integer pageNo;
    @Transient
    @TableField(exist = false)
    private Integer pageSize;
}
