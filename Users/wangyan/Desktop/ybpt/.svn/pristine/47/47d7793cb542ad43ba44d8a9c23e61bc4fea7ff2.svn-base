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
import java.util.List;

/**
 * @ClassName SysUser
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 12:54
 * @Version 1.0
 */
@Data
@Entity(name = "sys_user")
@TableName("sys_user")
public class SysUser extends Model<SysUser> {
    @TableId
    @Id
    private String id;
    private String username;//登录账号
    private String alias;//姓名
    private String idNumber;//身份证号
    private String pass;//登陆密码
    private String name;//用户名
    private String position;//职位
    private String location;//位置
    private String telephone;//电话
    private String landline;//座机
    private String mailbox;//邮箱
    private String user_type;//账号类型 1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行 7非定点机构 8非定点药店
    private String org_code;//所属机构
    private String org_name;//所属机构
    private String dept_code;//所属科室
    private String dept_name;//所属科室
    private String mac;//mac地址
    private String cdkey;//机器人激活码
    private String is_auth;//是否激活
    private String beginDate;//激活开始日期
    private String endDate;//结束日期
    private String lockFlag;//锁定标志

    @Transient
    @TableField(exist = false)
    private String lmtpric_hosp_lv;//限价医院等级

    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs;//定点归属医保区划

    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs_sbApply_name;

    @Transient
    @TableField(exist = false)
    private String tempPass;//旧密码
    @Transient
    @TableField(exist = false)
    private String[] roleIds;
    @Transient
    @TableField(exist = false)
    private List<String> permissions;
    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志

    private String synchronousState;          //同步状态0未同步  1已同步

    //是否是机构/药店管理员 0否1是
    private String isAdmin;
}
