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
 * @ClassName SysRole
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 12:54
 * @Version 1.0
 */
@Data
@Entity(name = "sys_role")
@TableName("sys_role")
public class SysRole extends Model<SysRole> {
    @TableId
    @Id
    private String id;
    private String role_name;//名称
    private String role_symbol;//角色标志
    private String memo;//描述
    @Transient
    @TableField(exist = false)
    private String[] menuIds;


    //所属组织
    private String tissue;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志
}
