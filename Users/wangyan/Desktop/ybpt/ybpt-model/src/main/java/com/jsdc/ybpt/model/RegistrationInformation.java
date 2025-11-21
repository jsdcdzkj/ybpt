package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
*门慢门特-登记信息子表
* Author wzn
* Date 2022/4/27 17:04
*/
@Data
@Entity(name = "reg_info")
@TableName("reg_info")
public class RegistrationInformation extends Model<RegistrationInformation> {
    @TableId
    @Id
    private String id;

    //门慢慢特主表ID
    private String opsp_id;

    //门慢门特病种目录编码
    private String opsp_dise_code;

    //门慢门特病种目录名称
    private String opsp_dise_name;

    //定点医药机构编号
    private String medins_code;

    //定点医药机构名称
    private String medins_name;

    //定点医药机构等级
    private String medinslv;
    //医疗机构开始日期
    private String beginDate;
    //医疗机构结束日期
    private String endDate;
    //病种类型代码
    private String dise_type_code;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志

}
