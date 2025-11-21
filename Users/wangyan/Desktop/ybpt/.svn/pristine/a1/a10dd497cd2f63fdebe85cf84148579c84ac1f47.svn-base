package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * @Author ：苹果
 * @Description：异地安置人员信息表
 * @Date ：2022/5/26 9:52
 * @Modified By：
 */
@Data
@TableName("relocated_info")
@Entity(name = "relocated_info")
public class RelocatedInfo extends Model {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private String id;//主键

    private String civilworker_id;//外键-公务员id

    private String org_code;//所属机构

    private String medplace; //就医地

    private String account_bank;//开户行

    private String account_no;//银行账号

    private String is_del;//删除标识
    //行政单位
    private String administrative_unit;

    private String relocated_year;//异地安置年份 例如 2022

    private String phone;//负责人手机号

    @TableField(exist = false)
    @Transient
    private String name;

    @TableField(exist = false)
    @Transient
    private String num;

    @TableField(exist = false)
    @Transient
    private String SEX;

    @TableField(exist = false)
    @Transient
    private String AGE;

    @TableField(exist = false)
    @Transient
    private String ename;

    @TableField(exist = false)
    @Transient
    private String ecode;

    @TableField(exist = false)
    @Transient
    private String admdvs;

    @TableField(exist = false)
    @Transient
    private Integer pageNo;

    @TableField(exist = false)
    @Transient
    private Integer pageSize;

    @TableField(exist = false)
    @Transient
    private String checked;

    @TableField(exist = false)
    @Transient
   private String cardType;//证件类型   1 身份证  2 港澳台  3其他;

    @TableField(exist = false)
    @Transient
    private String birthday; //出生日期
}
