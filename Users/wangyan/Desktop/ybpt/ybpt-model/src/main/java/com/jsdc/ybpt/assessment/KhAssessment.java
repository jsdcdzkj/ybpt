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
import java.util.List;

/**
 * 考核单
 */
@Data
@Entity(name = "kh_assessment")
@TableName("kh_assessment")
public class KhAssessment extends Model<KhAssessment> {
    //主键
    @TableId
    @Id
    private String id;
    private String assess_name;//考核单名称
    private String org_type;//1:机构 2：药店
    private String category;//类别 1.门诊 2.住院
    private String aggrement_lv;//协议等级
    private String year_of_assessment;//年份

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志
    //统筹区
    private String admdvs;

    /**
     * 类别
     */
    @Transient
    @TableField(exist = false)
    private String category_name;

    @Transient
    @TableField(exist = false)
    private List<KhAssessmentDetail> details;
    @Transient
    @TableField(exist = false)
    private Integer pageNo;
    @Transient
    @TableField(exist = false)
    private Integer pageSize;
    @Transient
    @TableField(exist = false)
    private String type;//copy:复制功能
}
