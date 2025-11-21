package com.jsdc.ybpt.capitalSettlement;

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
 * 资金清算明细(QsInfoDetails)
 */
@Data
@Entity(name = "qs_info_details")
@TableName("qs_info_details")
public class QsInfoDetails extends Model<QsInfoDetails> {
    @TableId
    @Id
    private String id;
    //导入类型0 汇总；1职工门诊（含单病种）；"2";//职工住院（含单病种含家床含按床日）；"3";//居民门诊（含单病种）；"4";//居民住院（含单病种含家床含按床日）
    //"5";//伤残门诊;"6";//伤残住院;"7";//职工、灵活就业人员生育;"8";//居民生育;"9";//离休
    private String type;
    //统筹区
    private String admdvs;
    //主表外键
    private String qs_info_id;
    //年份
    private String year;
    //本地码
    private String base_code;
    //国家码
    private String org_code;
    //名称
    private String org_name;
    //应付金额
    private String payable_amount;
    //已付金额
    private String paid_amount;
    //年度清算需结付金额
    private String payment_amont;
    //医疗机构承担金额
    private String org_borne_amount;
    //发生总金额
    private String sum_amount;
    //总额预算
    private String budget_amount;
    //不予支付
    private String withhold_payment;

    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;
    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态


}
