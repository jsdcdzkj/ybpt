package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @Author ：苹果
 * @Description：单位预约记录表
 * @Date ：2022/5/26 11:09
 * @Modified By：
 */
@Data
@TableName("emp_subscribe_record")
@Entity(name = "emp_subscribe_record")
public class EmpSubscribeRecord extends Model {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private String id;//主键

    private String uo_id;//外键-用人单位信息

    private String pack_id;//外键-套餐信息

    private String create_time;

    private Integer subscribe_num;//预约人数

    private String money;

    private String start_time;

    private String end_time;

    private String org_name;

    private String is_del;

    private String is_personal; // 是否为个人预约记录，默认为0，如果为1，则为个人预约记录

    @Transient
    @TableField(exist = false)
    private String uo_name;

    @Transient
    @TableField(exist = false)
    private String org_id;

    @TableField(exist = false)
    @Transient
    private String pack_name;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    private String pack_year;

    @TableField(exist = false)
    @Transient
    private String oname;

    @TableField(exist = false)
    @Transient
    private String eid;

    private String sub_org;
}
