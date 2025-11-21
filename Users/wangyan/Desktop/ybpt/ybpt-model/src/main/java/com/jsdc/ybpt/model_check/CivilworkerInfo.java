package com.jsdc.ybpt.model_check;

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
 * 公务员信息表
 */
@Data
@Entity(name = "civilworker_info")
@TableName("civilworker_info")
public class CivilworkerInfo extends Model<CivilworkerInfo> {
    @TableId
    @Id
    private String id;
    //姓名
    private String name;

    //性别男:1 ，女:2
    @Transient
    @TableField(exist = false)
    private String sex;
    //年龄
    @Transient
    @TableField(exist = false)
    private String age;

    //身份证号码
    private String certno;

    // 手机号
    private String telephone;
    //参保统筹区
    private String insu_admdvs;
    //所属统筹区
    private String admdvs;
    //单位ID
    private String emp_id;
    //单位名称
    private String emp_name;
    //单位编码
    private String emp_code;
    //单位类型
    private String emp_type;
    //单位所属统筹区
    private String emp_admdvs;
    //单位地址
    private String emp_addr;
    //参保险种
    private String insutype;
    //参保状态
    private String insu_state;
    //异地就医
    private String outside_flag;
    //是否异地安置人员否:0，是:1
    private String outside_put;
    //死亡标志
    private String death_flag;

    //体检资格 0无  1有
    private String qualifications ;

    //同步:1， 医保:2，用人单位:3
    private String info_source;

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

    private String login_Name;          //登陆账号,现在此字段的作用为，只要不为空，即为注册
    private String pwd;          //登陆密码
    private String open_id;          //微信open_id

    @Transient
    @TableField(exist = false)
    private String passwordConfirm; // 确认密码

    @Transient
    @TableField(exist = false)
    private String newPassword; // 新修改密码

    @Transient
    @TableField(exist = false)
    private String apply_date ;

    @Transient
    @TableField(exist = false)
    private String year ;


    //行政单位
    private String administrative_unit;

    //所属部门id
    private String dept_id;
    //所属部门
    private String dept_name;
    @Transient
    @TableField(exist = false)
    private String usertype;
    /**
     *  公务员维护-部门管理专用
     */
    @Transient
    @TableField(exist = false)
    private String is_dept;


    private String cardType;//证件类型   1 身份证  2 港澳台  3其他


    private String birthday; //出生日期
}
