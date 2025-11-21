package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 种植医疗服务
 */
@Data
@Entity(name = "Sb_dental_medical")
@TableName("Sb_dental_medical")
public class SbDentalMedical {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    //账号类型  1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行 7非定点机构 8非定点药店
    private String user_type;
    //单位名称
    private String org_name;
    //单位医保编码
    private String org_code;
    //申报价格
    private String price;
    //同级价格
    private String limit_price;
    //经营性质
    private String natures;
    //状态 0 待初审 1.待复审 2.待终审  3.待生成受理书 4.完成 5.驳回
    private String status;
    //驳回原因
    private String reason;
    //协议等级1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级
    private String aggrement_lv;


    //定点归属医保区划
    private String fix_blng_admdvs;

    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs_name;
    @Transient
    @TableField(exist = false)
    private String isAudit;
    //初审负责人
    private String first_trialer;
    private Date first_time;

    //复审
    private String second_trialer;
    private Date second_time;

    //终审负责人
    private String end_trialer;
    private Date end_time;

    //驳回负责人
    private String rejecter;
    private Date reject_time;


    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
}
