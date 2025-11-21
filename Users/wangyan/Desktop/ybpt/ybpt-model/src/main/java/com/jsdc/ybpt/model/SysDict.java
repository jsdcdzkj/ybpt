package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName SysDict
 * @Description TODO
 * @Author xujian
 * @Date 2022/4/21 14:50
 * @Version 1.0
 */
@Data
@Entity(name = "sys_dict")
@TableName("sys_dict")
public class SysDict extends Model<SysDict> {
    @TableId
    @Id
    private String id;
    private String label;//字典名
    private String value;//字典值
    private String parent_id;//父类
    private String dict_type;//类别
    private String memo;//描述

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志

}
