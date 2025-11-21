package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName SysUserRole
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 12:54
 * @Version 1.0
 */
@Data
@Entity(name = "sys_user_role")
@TableName("sys_user_role")
public class SysUserRole extends Model<SysUserRole> {
    @TableId
    @Id
    private String id;
    private String user_id;//用户id
    private String role_id;//角色id

}
