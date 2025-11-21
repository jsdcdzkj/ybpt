package com.jsdc.ybpt.eval_;

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
 * 考核任务指标负责人关系表(eval_stardard_user)
 */
@Data
@Entity(name = "eval_stardard_user")
@TableName("eval_stardard_user")
public class EvalStardardUser extends Model<EvalStardardUser> {
    @TableId
    @Id
    private String id;

    //任务ID
    private String taskManageId ;

    //考核设计表ID
    private String designId ;

    //考核任务指标ID
    private String stardardId;

    //负责人
    private String userId;

    @Transient
    @TableField(exist = false)
    private String userName ;


}
