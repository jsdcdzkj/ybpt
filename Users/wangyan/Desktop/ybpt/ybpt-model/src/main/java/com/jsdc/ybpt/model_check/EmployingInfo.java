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
 * 用人单位信息表
 */
@Data
@Entity(name = "employing_info")
@TableName("employing_info")
public class EmployingInfo extends Model<EmployingInfo> {
    @TableId
    @Id
    private String id;

    //单位名称
    private String emp_name;
    //单位编码
    private String emp_no;
    //单位类型
    private String emp_type;
    //单位地址
    private String emp_address;
    //所属统筹区
    private String admdvs;
    //上级行政单位
    private String parentOrgCode;
    //0.市直属/1.省部属
    private String isDirectly;


    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;
    //数据标志

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

}
