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
 * 行政单位信息表
 */
@Data
@Entity(name = "administrative_unit")
@TableName("administrative_unit")
public class AdministrativeUnit extends Model<AdministrativeUnit> {
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
    //告知手续名称
    private String notificationProcedureName ;


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
