package com.jsdc.ybpt.assessment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * kh_log(考核日志)
 */
@Data
@Entity(name = "kh_log")
@TableName("kh_log")
public class KHLog extends Model<KHLog> {
    //主键
    @TableId
    @Id
    private String id;
    //考核管理ID
    private String kh_manage_id;
    //任务发布、填报、提交、审核通过、审核驳回
    private String title;
    //提交时间
    private String submit_time;
    //审核驳回内容
    private String content;

    @Transient
    @TableField(exist = false)
    private String type;
}
