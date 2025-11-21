package com.jsdc.ybpt.eval_;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.model.FileInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

/**
 * 考核任务详情表
 */
@Data
@Entity(name = "eval_org_detail")
@TableName("eval_org_detail")
public class EvalOrgDetail extends Model<EvalOrgDetail> {
    @TableId
    @Id
    private String id;

    /**
     * -1暂存 1待初审 2已初审 3已复审
     */
    private String status;

    /**
     * 机构考核任务ID
     */
    private String evalOrgTaskId;
    //任务ID
    private String taskManageId;

    //机构编码
    private String orgCode;

    /**
     * 指标ID
     */
    private String evalStardardId;
    /**
     * 内容办法表ID
     */
    private String evalStardardMethodId;
    /**
     * 描述
     */
    private String memo;
    /**
     * 分值
     */
    private String score;
    //分值
    @Transient
    @TableField(exist = false)
    private String detailScore;
    /**
     * 指标值
     */
    private String targetScore;
    /**
     * 排名
     */
    private String rank;
    /**
     * 排名百分比
     */
    private String rankRate;
    /**
     * 申诉内容
     */
    private String appealCount;

    @Transient
    @TableField(exist = false)
    private List<String> fileIds;

    @Transient
    @TableField(exist = false)
    private List<Map<String,String>> files;


    @Transient
    @TableField(exist = false)
    private List<String> appealFileIds;
    @Transient
    @TableField(exist = false)
    private List<Map<String,String>> appealFiles;
}
