package com.jsdc.ybpt.priceBackUp.project;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 非医疗诊疗项目
 */
@Data
@Entity(name = "sbbf_non_project")
@TableName("sbbf_non_project")
public class SbNonProjectBackUp extends Model<SbNonProjectBackUp> {
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
    //项目内涵;
    private String projectConnotation;
    //除外内容;
    private String excludedContent;
    //收费项目等级
    private String     projectLevel ;
    //    "计价单位";
    private String chargeUnit;
    //"三类 医院 苏南";
    private String threeSouth;
    // "三类 医院 苏中";
    private String threeCentre;
    //"三类 医院 苏北";
    private String threeNorth;
    //"二类 医院 苏南";
    private String twoSouth;
    //"二类 医院 苏中";
    private String twoCentre;
    //"二类 医院 苏北";
    private String twoNorth;
    // "一类 医院 苏南";
    private String oneSouth;
    //"一类 医院苏中";
    private String oneCentre;
    //"一类 医院 苏北";
    private String oneNorth;
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
