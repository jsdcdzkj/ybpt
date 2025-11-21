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
 * 营利性医院医疗服务申报和药品耗材申报和自设项目自主定价申报
 * 自设项目
 *
 */
@Data
@Entity(name = "Sb_business_project")
@TableName("Sb_business_project")
public class SbBusinessProject extends Model<SbBusinessProject> {
    @TableId
    @Id
    private String id;
    @Transient
    @TableField(exist = false)
    private int index;
    //单位名称
    private String dept_name;
    //单位医保编码
    private String dept_code;
    //经营性质
    private String natures;
    //项目名称
    private String project_name;
    //项目编码
    private String project_code;
    //项目内涵
    private String project_content;
    //除外内容
    private String except_content;

    //是否儿童0否1是
    private String child_or_not;

    //计价单位
    private String unit;
    //说明
    private String explain;
    //价格
    private String price;
    //费用类型
    private String type;
    private String typeName;
    //是否在国家和省医疗服务价格项目规范之内 0 否  1是
    private String service_price;
    //是否有明细0否1是
    private String is_detail;

    private String apply_id;

    @Transient
    @TableField(exist = false)
    private String fileInfoId;
    @Transient
    @TableField(exist = false)
    private SbBusinessExplain sbBusinessExplain ;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
}
