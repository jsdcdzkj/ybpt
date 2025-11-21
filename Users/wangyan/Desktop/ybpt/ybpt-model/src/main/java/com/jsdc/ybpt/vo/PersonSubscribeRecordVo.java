package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.model.SysUser;
import lombok.Data;

import javax.persistence.Transient;
import java.util.Date;

/**
 * 人员预约关联信息表
 * Author wzn
 * Date 2022/5/31 15:09
 */
@Data
public class PersonSubscribeRecordVo extends Model {


    private String year;//取年份
    private String id;//

    private String apply_date;//预约时间
    private String upload_time;//上传报告时间

    private String sched;//待体检:0 , 已体检:1，已过期:2， 已上传报告:3

    private String checkup_time;//体检时间

    private String begin_upload_time;//上传时间
    private String[] times;//上传时间

    private String pack_name;//套餐名称

    private String name;//人员名称

    private String certno;//人员身份证

    private String phone;//手机号

    private String chargePhone;

    private String emp_name;//单位名称
    private String emp_code;//单位编码

    private String org_name;//体检单位名称

    private String org_code;//体检单位编码
    private Date settlementTime; //结算时间
    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    @Transient
    @TableField(exist = false)
    private SysUser sysUser;


    private String org_id;//外键-体检机构信息

    @Transient
    @TableField(exist = false)
    private String dept_no;

    @Transient
    @TableField(exist = false)
    private String dept_name;

    @Transient
    @TableField(exist = false)
    private String dept_id;

    @Transient
    @TableField(exist = false)
    private String count;//count


    @Transient
    @TableField(exist = false)
    private String sex;//性别

    @Transient
    @TableField(exist = false)
    private String isSettlement;

    @Transient
    @TableField(exist = false)
    private String civil_is_del;

    private String cardType;//证件类型   1 身份证  2 港澳台  3其他

}
