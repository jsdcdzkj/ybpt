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
import java.util.Date;
import java.util.List;

/**
 * 考核任务详情日志表
 */
@Data
@Entity(name = "eval_org_detail_log")
@TableName("eval_org_detail_log")
public class EvalOrgDetailLog extends Model<EvalOrgDetailLog> {
    @TableId
    @Id
    private String id;

    /**
     * 考核任务详情表ID
     */
    private String EvalOrgDetail;

    /**
     * 分值
     */
    private String score;

    private String createUser;      //创建人
    private String createUserName;      //创建人
    private Date createTime;      //创建时间
}
