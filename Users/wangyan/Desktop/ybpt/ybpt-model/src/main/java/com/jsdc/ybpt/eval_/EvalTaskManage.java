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
import java.util.List;

/**
 * 考核任务管理表(eval_task_manage)
 */
@Data
@Entity(name = "eval_task_manage")
@TableName("eval_task_manage")
public class EvalTaskManage extends Model<EvalTaskManage> {
    @TableId
    @Id
    private String id;

    //任务名
    private String taskName ;

    //机构类型 1:机构 2：药店
    private String orgType ;

    @Transient
    @TableField(exist = false)
    private String orgTypeName ;

    //类别1.门诊 2.住院
    private String category;

    @Transient
    @TableField(exist = false)
    private String categoryName ;

    //经营性质 1:公立 2:私立
    private String natures;

    @Transient
    @TableField(exist = false)
    private String naturesName ;

    //协议等级 "1", "一级"  "2", "二级"  "3", "三级"  "4", "A级别"   "5", "B级别"   "6", "C级别"   "9", "未定级"
    private String aggrementLv;

    @Transient
    @TableField(exist = false)
    private String aggrementLvName ;

    //考核年度
    private String  year;

    //精神专科 0：非精神专科  1精神专科
    private String  spirit;

    @Transient
    @TableField(exist = false)
    private String spiritName ;





    //0未发布   2已发布(待初审) 3:结束初审-4复审开始 6待计算保证金 7完成
    //流程 初始0未发布，点击发布之后变成2 已发布，点击结束初审变成3  点击开始复审 变成4 结束复审变成6
    private String    status;

    @Transient
    @TableField(exist = false)
    private String statusName ;


    //过期时间
    private String   expirationTime;
    //考核单ID
    private String   assessmentId;

    @Transient
    @TableField(exist = false)
    private String   assessmentName;

    //统筹区
    private String    admdvs;

    @Transient
    @TableField(exist = false)
    private String   admdvsName;
    //保证金展示状态 0否 1是
    private String      earnestMoneyShow;
    //保证金文件
    private String     earnestMoneyFile;



    private String     sumEarnestMoney;

    private String     rewards;

    //职工保证金总额
    private String     staffSumEarnestMoney;
    //居民保证金总额
    private String     residentSumEarnestMoney;
    //医疗救助保证金总额
    private String     medSumEarnestMoney;

    //职工奖励金
    private String     staffRewards;
    //居民奖励金
    private String     residentRewards;
    //医疗救助奖励金
    private String     medRewards;


    //保证金文件是否上传 0否  1是
    private String moneyUpload ;




    @Transient
    @TableField(exist = false)
    private List<EvalStardardUser> evalStardardUserList;


    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String isDel;          //删除标志、有效状态


}
