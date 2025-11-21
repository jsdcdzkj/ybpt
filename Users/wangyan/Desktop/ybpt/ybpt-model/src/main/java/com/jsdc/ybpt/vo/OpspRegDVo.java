package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 *门慢门特 查询条件显示
 */
@Data
public class OpspRegDVo extends Model {


    //证件类型
    private String psn_cert_type;

    //证件号码
    private String certno;

    //姓名
    private String psn_name ;

    //性别
    private String gend ;

    //民族
    private String naty ;

    @Transient
    @TableField(exist = false)
    private String naty_name ;

    @Transient
    @TableField(exist = false)
    private String dise_type_code;

    //出生日期
    private String brdy ;
    //电话
    private String mob ;
    //公司名称
    private String emp_name ;
    //居住地址
    private String live_addr ;
    //参保所属医保区划code
    private String insu_admdvs ;


    //参保险种code
    private String insutype ;
    //参保险种名称
    private String insutypeName ;

    //机构编号
    private String medins_code ;


    private Integer pageNo ;


    private Integer pageSize ;








}
