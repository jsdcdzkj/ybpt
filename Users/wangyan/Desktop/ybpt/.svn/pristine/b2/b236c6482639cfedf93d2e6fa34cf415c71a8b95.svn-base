package com.jsdc.ybpt.eval_;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;

/**
 * 机构考核任务(eval_org_task)
 */
@Data
@Entity(name = "eval_org_task")
@TableName("eval_org_task")
public class EvalOrgTask extends Model<EvalOrgTask> {
    @TableId
    @Id
    private String id;

    //任务ID
    private String taskManageId;

    //任务名
    private String taskName ;
    //考核设计表ID
    private String designId;
    //机构类型
    private String orgType;

    @Transient
    @TableField(exist = false)
    private String orgTypeName ;
    //类别
    private String category;
    @Transient
    @TableField(exist = false)
    private String categoryName ;

    //经营性质 1:公立 2:私立
    private String natures;
    @Transient
    @TableField(exist = false)
    private String naturesName ;

    //协议等级
    private String aggrementLv;

    @Transient
    @TableField(exist = false)
    private String aggrementLvName ;
    //考核年度
    private String year;
    //机构名称
    private String orgName;
    //机构编码
    private String orgCode;
    //考核状态 0填报中  1 初审中 2 待复审 3  待计算保证金 4完成   -1  暂存  5待反馈

    private String status;

    @Transient
    @TableField(exist = false)
    private String statusName ;

    //统筹区
    private String admdvs;
    //过期时间
    private String expirationTime;
    //得分
    private String score;
    //任务总分
    private String totalScore;
    //百分制得分：得分/（任务总分/100）
    @Transient
    @TableField(exist = false)
    private String totalPercent;
    //百分制平均分：平均分/（任务总分/100）
    @Transient
    @TableField(exist = false)
    private String averagePercent;
    //平均数
    private String averageScore;
    //指标值
    private String targetScore;
    //保证金额
    private String margin;

    //职工保证金
    private String marginStaff ;

    //居民保证金
    private String marginResident ;

    //医疗保证金

    private String marginMedical ;

    //机构指数
    private String orgRate;
    //返回金额
    private String refund;

    //返回金额职工
    private String refundStaff ;

    //返回金额居民

    private String refundResident ;

    //返回金额医疗

    private String refundMedical ;

    //奖惩金额
    private String rewards;


    //职工奖励金
    private String     staffRewards;
    //居民奖励金
    private String     residentRewards;
    //医疗救助奖励金
    private String     medRewards;

    //精神专科
    private String spirit;

    //职工占比
    private String staffProportion ;

    //居民占比
    private String residentProportion ;

    //医疗占比
    private String medicalTreatmentProportion ;


    @Transient
    @TableField(exist = false)
    private String isExpiration ;

    @Transient
    @TableField(exist = false)
    private String spiritName ;

    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String isDel;          //删除标志、有效状态


    //百分制得分：得分/（任务总分/100）
    public String getTotalPercent() {
        if(null == this.score || null == this.totalScore){
            return "";
        }
        BigDecimal totalScore = new BigDecimal(this.totalScore);
        BigDecimal score = totalScore.divide(new BigDecimal(100), 1, RoundingMode.HALF_UP);
        BigDecimal totalPercent = new BigDecimal(this.score).divide(score, 2, RoundingMode.HALF_UP);
        return String.valueOf(totalPercent);
    }

    //百分制平均分：平均分/（任务总分/100）
    public String getAveragePercent() {
        if(null == this.averageScore || null == this.totalScore){
            return "";
        }
        BigDecimal totalScore = new BigDecimal(this.totalScore);
        BigDecimal score = totalScore.divide(new BigDecimal(100), 1, RoundingMode.HALF_UP);
        BigDecimal averagePercent = new BigDecimal(this.averageScore).divide(score, 2, RoundingMode.HALF_UP);
        return String.valueOf(averagePercent);
    }
}
