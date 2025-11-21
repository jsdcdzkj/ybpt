package com.jsdc.ybpt.agreementsignModel;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 协议网签-申诉表
 */
@Data
@Entity(name = "net_tag_appeal")
@TableName("net_tag_appeal")
public class NetTagAppeal extends Model<NetTagAppeal> {

    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    //医药机构编码、国家编码
    private String mechanism_code;
    //医药机构网签表id
    private String mechanism_id;
    //机构类别type(1-医疗机构 2-零售药店) 注:目前就这两种 此数据选择机构时必填
    private String medical_type;
    //机构等级（0一级 1二级三级 2未定级 3A级别 4B级别 5C级别）字典表获取 */
    private String net_grade_id;
    //医保编码
    private String medical_code;
    //内容
    private String content;
    //状态 0未确认 1确认
    private Integer status;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

    @Transient
    @TableField(exist = false)
    private String fixmedins_name;//定点医疗机构名称

    @Transient
    @TableField(exist = false)
    private String dept_resper_name;//联系人

    @Transient
    @TableField(exist = false)
    private String dept_resper_tel;//联系人电话

    @Transient
    @TableField(exist = false)
    private String legrep_name;//法定代表人

    @Transient
    @TableField(exist = false)
    private String net_grade_name;//机构等级

    /**
     * 分页pageNo
     */
    @Transient
    @TableField(exist = false)
    private Integer pageIndex ;

    /**
     * 分页pageSize
     */
    @Transient
    @TableField(exist = false)
    private Integer pageSize ;

}
