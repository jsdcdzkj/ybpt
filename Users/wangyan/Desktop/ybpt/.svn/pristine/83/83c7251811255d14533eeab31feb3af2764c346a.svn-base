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
*考核任务管理
* Author wzn
* Date 2022/11/21 14:19
*/
@Data
@Entity(name = "kh_task_manage")
@TableName("kh_task_manage")
public class KhTaskManage extends Model<KhTaskManage> {
    @TableId
    @Id
    private String id;
    //任务名
    private String task_name;
    //定点医疗服务机构类型  1:机构 2：药店
    private String org_type;
    //定点医疗服务机构类型 1.门诊 2.住院
    private String category;

    //协议等级 map.put("1", "一级");
    //        map.put("2", "二级");
    //        map.put("3", "三级");
    //        map.put("4", "A级别");
    //        map.put("5", "B级别");
    //        map.put("6", "C级别");
    //        map.put("9", "未定级");
    private String aggrement_lv;
    //考核年度
    private String year_of_assessment;
    //发布状态0 待发布 1更新发布
    private String publish_status;
    //考核单ID
    private String assessment_id;


    //过期时间
    private String expiration_time ;


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
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;


    @Transient
    @TableField(exist = false)
    private List<String> medicalCodeList ;



    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志





}
