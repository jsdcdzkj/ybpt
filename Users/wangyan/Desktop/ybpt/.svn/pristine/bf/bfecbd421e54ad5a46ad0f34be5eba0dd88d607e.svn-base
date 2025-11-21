package com.jsdc.ybpt.priceBackUp.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 血液及血液成分
 */
@Data
@Entity(name = "sbbf_blood_constituent")
@TableName("sbbf_blood_constituent")
public class SbBloodConstituentBackUp extends Model<SbBloodConstituentBackUp> {
    @TableId
    @Id
    private String id;

    //收费 项目 编码"
    private String chargeItemCode;
    //收费项目名称
    private String nameOfChargeItem;
    //国家医疗服务项目代码;
    private String projectCode;
    //国家医疗服务项目名称;
    private String projectName;
    //收费项目等级
    private String projectLevel;
    //    "计价单位";
    private String chargeUnit;
    //供应价格（元）
    private String supplyPrice ;
    //说明;
    private String explain;
    //变更类别;
    private String changeCategory;
    //变更内容;
    private String contentOfChange;
    //文件依据;
    private String documentBasis;
    //本期发文依据;
    private String publicationBasis;
    //执行范围;
    private String scopeOfExecution;
    //批次号
    private String batch;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

}
