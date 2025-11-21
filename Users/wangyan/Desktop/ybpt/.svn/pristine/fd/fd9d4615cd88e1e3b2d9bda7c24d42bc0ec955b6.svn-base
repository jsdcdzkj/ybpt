package com.jsdc.ybpt.price.declare;

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
 * 床位申报表
 */
@Data
@Entity(name = "Sb_bedDeclaration")
@TableName("Sb_bedDeclaration")
public class SbBedDeclaration extends Model<SbBedDeclaration> {
    @TableId
    @Id
    private String id;
    @Transient
    @TableField(exist = false)
    private int index;
    //单位名称
    private String dept_name;
    //医院等级
    private String aggrement_lv;
    //床位总数
    private String beds_all_count;
    //所占比例
    private String account_for;
    //联系人
    private String linkman;
    //联系电话
    private String phone;

    private String apply_id;

    @Transient
    @TableField(exist = false)
    private String[] fileInfoIds;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
}
