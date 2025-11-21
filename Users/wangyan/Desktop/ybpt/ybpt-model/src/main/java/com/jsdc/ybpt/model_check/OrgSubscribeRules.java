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
 * 体检中心预约配置表
 */

@Data
@TableName("org_subscribe_rules")
@Entity(name = "org_subscribe_rules")

public class OrgSubscribeRules extends Model {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private String id; //主键

    private String time;// 日期

    private String isbook;//0：休息日， 1:工作日

    private String limit_person;//当天可以体检的最大人数

    private String booking_person;//当天已预约人数

    private String org_id;//外键-体检机构信息

    private String state;//开启:0，关闭:1  本条规则启用或者禁用

    private String start_time;//当天体检开始时间

    private String end_time;//当天体检结束时间


    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    // 查询条件 用于vo传参 接收参数用
    @Transient
    @TableField(exist = false)
    private String ruleStartTime;
    @Transient
    @TableField(exist = false)
    private String ruleEndTime;
}
