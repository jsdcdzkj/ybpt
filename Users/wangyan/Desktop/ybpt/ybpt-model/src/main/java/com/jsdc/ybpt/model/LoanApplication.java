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
 * 码上贷-医药机构-贷款申请
 */
@Data
@Entity(name = "loanApply")
@TableName("loanApply")
public class LoanApplication extends Model<LoanApplication> {
    @TableId
    @Id
    private String id;

    //定点医药机构编号
    private String fixmedins_code;
    //定点医药机构名称
    private String fixmedins_name;
    //申请银行
    private String apply_bank_id;
    @Transient
    @TableField(exist = false)
    private String apply_bank_id_name;
    //申请额度
    private String application_quota;
    //申请状态  0 医保待审核  1医保审核通过  2医保审核驳回 3已撤销
    private String apply_satus;
    //医保驳回原因
    private String reason;
    //医保审核人
    private String ybreviewer;
    //医保审核时间
    private Date ybreviewTime;
    //0 银行待审核  1 银行审核通过 2 银行审驳回  3 超时,医保重新审核
    private String bank_satus;
    //银行驳回原因
    private String bank_reason;
    //申请时间
    private Date apply_time;
    //确定额度
    private String determineTheAmount;
    //银行审核人
    private String reviewer;
    //银行审核时间
    private Date reviewTime;
    //统一社会信用代码
    private String uscc;
    //信用等级名称
    private String cred_lv_name;
    //国家异地平台机构编号
    private String nat_plaf_code;
    //定点归属医保区划
    private String fix_blng_admdvs;
    //地址
        private String addr;
    //经营性质
    private String biznat;
    //医疗机构执业许可证登记号
    private String medins_prac_lic_regno;
    //法人名称
    private String legent_name;
    //法定代表人姓名
    private String legrep_name;
    //主要负责人
    private String main_resper;
    //传真号码
    private String fax_no;
    //药品经营许可证号
    private String drug_biz_lic_no;
    //医疗器械经营许可证号
    private String equ_biz_lic_no;
    //经营状态
    private String biz_stas;
    //开户银行
    private String depositaryBank;
    //户名
    private String bankName;
    //结算账户
    private String settlementAccount;
    //定点类型
    private String medins_type;
    //医疗机构等级
    private String medinslv;
    //医院等级
    private String hosp_lv;
    //分管医疗机构负责人姓名
    private String inchg_hosp_resper_name;
    //分管医疗机构负责人电话
    private String inchg_hosp_resper_tel;
    //违规记录
    private String violationRecord;






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
