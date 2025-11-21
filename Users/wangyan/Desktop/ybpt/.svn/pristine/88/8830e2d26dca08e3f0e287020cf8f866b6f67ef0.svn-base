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
import java.util.List;

/**
*定点医药机构业务信息表
* Author wzn
* Date 2022/5/17 15:10
*/
@Data
@Entity(name = "fixmedins_b")
@TableName("fixmedins_b")
public class FixmedinsB extends Model<FixmedinsB> {
    @TableId
    @Id
    private String id;
    //定点医药机构编号
    private String fixmedins_code;
    //医药机构管理码
    private String medins_mgtcode;
    //统一社会信用代码
    private String uscc;
    //组织机构代码
    private String orgcode;
    //定点归属医保区划
    private String fix_blng_admdvs;
    //定点归属医保区划-告知手续
    private String fix_blng_admdvs_sbApply;
    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs_sbApply_name;
    //定点医药机构名称
    private String fixmedins_name;
    //定点医疗服务机构类型 1:机构 2：药店
    private String fixmedins_type;
    //定点医疗服务机构类型 1.门诊 2.住院
    private String category;
    //医院等级
    private String hosp_lv;
    //协议等级1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级
    private String aggrement_lv;
    //限价医院等级
    private String lmtpric_hosp_lv;
    //起付线医院等级
    private String dedc_hosp_lv;
    //开始时间
    private String begntime;
    //结束时间
    private String endtime;
    //医保办负责人姓名
    private String hi_resper_name;
    //医保办负责人联系电话
    private String hi_resper_tel;
    //医保办负责人证件类型
    private String hi_resper_cert_type;
    //医保办负责人证件号码
    private String hi_resper_certno;
    //异地医药机构类型
    private String out_fixmedins_type;
    //定点联网开通标志
    private String fix_onln_open_flag;
    //异地联网开通类型
    private String out_onln_open_type;
    //国家异地平台机构编号
    private String nat_plaf_code;
    //省内异地平台机构编号
    private String prov_plaf_code;

    /**
     * 以下字段是协议网签新增的
     * begin
     */
    //地址
    private String address;

    //经营方式
    private String bizway;

    //经营性质 1:营利性 2:民办非营利 3:政府非营利
    private String biznet;

    //药品经营许可证
    private String license;

    //法定代表人
    private String legrep_name;

    //联系电话
    private String legrep_mobile;
    /**
     * end
     */
    //授权码
    private String authorizationCode;

    /**
     * 是否已维护，0：未维护 1：已维护
     * 已维护过的数据无法二次维护
     */
    private String hasMaintained;

    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;
    @Transient
    @TableField(exist = false)
    private String cred_lv_type ;
    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志

    /**
     * 机构等级
     */
    @Transient
    @TableField(exist = false)
    private List<String> aggrement_lvs;

    /**
     * 机构等级名称
     */
    @Transient
    @TableField(exist = false)
    private String aggrement_lv_name;

    /**
     * 机构类型
     */
    @Transient
    @TableField(exist = false)
    private String cred_lv_name;
    /**
     * 类别
     */
    @Transient
    @TableField(exist = false)
    private String category_name;

    /**leg
     * todo
     * 医疗机构类型
     */
    private String medins_type;
    /**
     * todo
     * 医疗机构类型名称
     */
    private String medins_type_name;

    /**
     * todo
     * 药店连锁类型
     */
    private String phac_chan_type;

    /**
     * 所有制形式
     */
    private String ownership;

    /**
     * 属地
     */
    private String possession;

    /**
     * 所属街道
     */
    private String possession_street;

    /**
     * 联系人
     */
    private String legrep_person;

    /**
     * 经营性质（本地字段）
     */
    private String manage_quality;

    @Transient
    @TableField(exist = false)
    private String biznat_name ;



}
