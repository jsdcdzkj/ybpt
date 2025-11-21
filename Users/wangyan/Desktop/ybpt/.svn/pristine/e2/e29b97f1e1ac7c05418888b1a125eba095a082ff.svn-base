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
 * 机构保证金(eval_earnestMoney)
 */
@Data
@Entity(name = "eval_earnestMoney")
@TableName("eval_earnestMoney")
public class EvalEarnestMoney extends Model<EvalEarnestMoney> {
    @TableId
    @Id
    private String id;

    //考核任务管理ID
    private String taskManageId;

    //机构编码
    private String orgCode;


    //职工保证金
    private String     staffEarnestMoney;
    //居民保证金
    private String     residentEarnestMoney;
    //医疗救助保证金
    private String     medEarnestMoney;

}
