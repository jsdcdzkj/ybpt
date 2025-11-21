package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * @Author ：苹果
 * @Description：人员预约关联信息表
 * @Date ：2022/5/26 11:15
 * @Modified By：
 */
@Data
@TableName("person_subscribe_record")
@Entity(name = "person_subscribe_record")
public class PersonSubscribeRecord extends Model<PersonSubscribeRecord> {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private String id;//主键

    private String emp_sub_id;//外键-预约记录

    private String civilworker_id;//外键-公务员信息

    private String year;//取年份

    private String org_id;//外键-体检机构信息

    private String pack_id;//外键-套餐信息

    private String apply_date; // 预约时间

    private String sched;//待体检:0 , 已体检:1，已过期:2， 已上传报告:3 ，''已撤销''：4

    private String checkup_time;

    private String upload_time;

    private String phone; // 手机号

    private String service_star;

    private String professional_star;

    private String service_status_star;

    private String react_star;

    private String service_label;

    private String service_review;

    @ColumnDefault("0")
    private String isSettlement; //是否结算  默认0 结算为1

    private Date settlementTime; //结算时间


    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //更新时间
    private String is_del;          //删除标志、有效状态
    private String chargePhone; // 手机号
    @TableField(exist = false)
    @Transient
    private String oname;   // 体检机构的组织名称

    @TableField(exist = false)
    @Transient
    private String pname;   // 套餐名称

    @TableField(exist = false)
    @Transient
    private Integer pageNo;

    @TableField(exist = false)
    @Transient
    private Integer pageSize;

    @TableField(exist = false)
    @Transient
    private String packMoney; // 套餐金额

    private String cardType;//证件类型   1 身份证  2 港澳台  3其他

}
