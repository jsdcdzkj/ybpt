package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * sys_department（科室表）
 */
@Data
@Entity(name = "sys_department")
@TableName("sys_department")
public class SysDepartment extends Model<SysDepartment> {
    @TableId
    @Id
    private Integer id;

    //所属定点医疗机构
    private String medical_unit_id;

    //科室名称
    private String dept_name;

    //科室编号
    private String dept_code;

    //备注信息
    private String memo ;

    //删除标记
    private String del_flag ;

    //创建者
    private String create_by ;

    //创建时间
    private String create_date ;

    //更新者
    private String update_by ;

    //更新时间
    private String update_date ;




}
