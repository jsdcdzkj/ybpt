package com.jsdc.ybpt.model;

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
 * 未定点机构
 */
@Data
@Entity(name = "unfixedMechanism")
@TableName("UnfixedMechanism")
public class UnfixedMechanism extends Model<UnfixedMechanism> {
    @TableId
    @Id
    private String id;

    //定点医药机构编号
    private String fixmedins_code;
    //定点医药机构名称
    private String fixmedins_name;

    //定点归属医保区划
    private String fix_blng_admdvs;

    //定点医疗服务机构类型 2:机构 3：药店
    private String fixmedins_type;

    //协议等级1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级
    private String aggrement_lv;

    /**
     * 经营性质（本地字段）
     */
    private String manage_quality;
    //联系电话
    private String legrep_mobile;
    //地址
    private String address;

    /**
     * 联系人
     */
    private String legrep_person;

    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;

    //经营性质
    private String biznet;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

    
}
