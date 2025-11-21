package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @ClassName SysMenu
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 12:54
 * @Version 1.0
 */
@Data
@Entity(name = "sys_menu")
@TableName("sys_menu")
public class SysMenu extends Model<SysMenu> {
    @TableId
    @Id
    private String id;
    private String parent_id;//父级ID
    private String title;//标题(菜单名称)
    private String router_name;//路由名称
    private String router_url;//路由链接
    private String vue_url;//vue文件路径
    private String redirect_type;//重定向类型
    private String icon;//图标
    private String is_show;//是否在菜单中显示
    private Integer sort;//排序

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志
}
