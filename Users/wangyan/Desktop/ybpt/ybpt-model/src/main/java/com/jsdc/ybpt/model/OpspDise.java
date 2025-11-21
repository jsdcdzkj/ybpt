package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
*门慢门特
* Author wzn
* Date 2022/4/27 17:04
*/
@Data
@Entity(name = "opsp_dise")
@TableName("opsp_dise")
public class OpspDise extends Model<OpspDise> {
    @TableId
    @Id
    private String id;

    //证件类型
    private String psn_cert_type;

    //证件号码
    private String certno;

    //姓名
    private String psn_name;
    //性别
    private String gend;
    //民族代码
    private String naty;
    //民族
    private String naty_name;
    //出生日期
    private String brdy;
    //手机号
    private String mob;
    //公司名称
    private String emp_name;
    //联系地址
    private String live_addr;
    //统筹区
    private String insu_admdvs;

    //参保所属医保区划名称
    private String insu_admdvs_name ;

    //参保险种code
    private String insutype ;

    //参保险种名称
    private String insutypeName ;

    //认定定点医药机构编码
    private String medins_code;

    //认定定点医药机构名称
    private String medins_name;

    //统一社会信用代码
    private String uscc;

    //定点医药机构等级
    private String medinslv;

    //医院鉴定日期
    private String hospIdeDate;

    //医师编码
    private String phar_code;

    //医师名称
    private String phar_name;

    //开始日期
    private String begainDate;

    //结束日期
    private String enddate;

    //申请日期
    private String appyDate;

    //申报来源
    private String dclaSouc;

    //申请理由
    private String appyRea;
    //代办人姓名
    private String agnterName;

    //代办人证件类型
    private String agnterCertType;

    //代办人证件号码
    private String agnterCertno;

    //代办人联系方式
    private String agnterTel;

    //代办人关系
    private String agnterRlts;
    //代办人联系地址
    private String agnterAddr;

    //审核状态0  未审核  1审核通过  2已撤销 3审核不通过
    private String approvalStatus;

    //病种名称
    @Transient
    @TableField(exist = false)
    private String OPSP_DISE_NAME;

    //业务流水号
    @Transient
    @TableField(exist = false)
    private String serv_matt_inst_id ;

    @Transient
    @TableField(exist = false)
    List<RegistrationInformation> informationList ;

    @Transient
    @TableField(exist = false)
    List<OpspDiseListBVo> opspDiseListBVos ;

    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;

    private String associationId;//回流库关联ID

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
    //经办人姓名
    private String opter_name ;
    //经办时间
    private String opt_time ;

    @Transient
    @TableField(exist = false)
    private String opsp_dise_name ;

    @Transient
    @TableField(exist = false)
    private String dise_type_code ;

    //审核建议
    private String proposal ;
    //审核结果
    private String checkRusult ;
}
