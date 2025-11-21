package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 机构对账数据
 */
@Data
@Entity(name = "biz_reconciliation_month")
@TableName("biz_reconciliation_month")
public class BizReconciliationMonth extends Model {
    @TableId
    @Id
    private String id;                  //主键
    private String fixmedins_code;      //定点机构编号
    private String fixmedins_name;      //定点机构名称
    private String settle_date;         //结算日期
    private String reconciliation_type; //对账类型
    private String insu_admdvs;         //统筹区
    private String medfee_sumamt;       //医疗费总额
    private String hifp_pay;            //统筹基金指出
    private String cvlserv_pay;         //公务员医疗补助
    private String hifes_pay;           //补充医疗保险基金
    private String hifmi_pay;           //大病补充医疗保险基金
    private String hifob_pay;           //大额医疗不住基金
    private String maf_pay;             //医疗救助基金
    private String othfund_pay;         //其他基金--倾斜救助基金
    private String acct_pay;            //个人账户支出
    private String cash_payamt;         //现金支付金额
    private String ownpay_hosp_part;    //自费医院负担部分
    private String hifdm_pay;           //伤残军人医疗保障基金
    private String acct_mulaid_pay;     //账户共济支付金额
    private String person_num;          //人数
    private String person_count;        //人次


    private String createUser;      //创建人
    private String createTime;      //创建时间
    private String updateUser;      //创建用户
    private String updateTime;      //创建时间
    private String is_del;          //删除标志

    @Transient
    @TableField(exist = false)
    private Integer pageNo;
    @Transient
    @TableField(exist = false)
    private Integer pageSize;

    @Transient
    @TableField(exist = false)
    private String admdvsName;          //统筹区名称
    @Transient
    @TableField(exist = false)
    private String reconciliationName;  //对账类型名称

}
