package com.jsdc.ybpt.assessment;

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
 * kh_manage(考核管理)
 */
@Data
@Entity(name = "kh_manage")
@TableName("kh_manage")
public class KHManage extends Model<KHManage> {
    //主键
    @TableId
    @Id
    private String id;
    //任务ID
    private String task_manage_id;
    //机构类型
    private String org_type;
    //类别 1.门诊 2.住院
    private String category;
    //协议等级
    private String aggrement_lv;
    //考核年度
    private String year;
    //机构名称
    private String fixmedins_name;
    //机构编码
    private String fixmedins_code;
    //考核状态 待填报 填报中、待初审、待复审  审核通过（0、1、2）
    private String status;
    //是否生成考核明细 0：未生成 1:已生成
    private String if_detail;
    //审批意见 驳回理由
    private String approval_opinion;
    //得分
    private String score;

    //统筹区
    private String admdvs;

    //过期时间
    private String expiration_time ;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志


    /**
     * 类别
     */
    @Transient
    @TableField(exist = false)
    private String category_name;
}
