package com.jsdc.ybpt.traceableCode;

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
 * 追溯码重复通知
 */
@Data
@Entity(name = "traceablecode")
@TableName("traceablecode")
public class TraceableCode extends Model<TraceableCode> {
    @TableId
    @Id
    private String id;

    //追溯流水号
    private String traceTheSerialNumber;

    //定点医药机构编号
    private String fixmedins_code;

    //定点医药机构名称
    private String fixmedins_name;

    //医疗目录编码
    private String med_list_codg;

    // 医药机构目录编码	;
    private String    medins_list_codg;

    //医药机构目录名称
    private String medins_list_name;

    //定点医药机构批次流水号
    private String batchSerialNumber;

    //定点医药机构商品销售流水号
    private String salesSerialNumber;

    //就诊id;
    private String mdtrt_id;

    //就诊结算类型
    private String settlementType;

    //记账流水号
    private String account_seria_number;

    //药品追溯码
    private String drugTracingCode;

    //经办人id
    private String opter_id;

    //经办人姓名
    private String opter_name;

    //经办时间
    private String opt_time;

    //经办机构编号
    private String optins_no;

    //统筹区编号
    private String poolarea_no;

    //拆零标志
    private String dismantlingMark;

    //人员编号
    private String psn_no;

    //人员证件类型
    private String psn_cert_type;

    //证件号码
    private String certno;

    //人员姓名
    private String name;




    @Transient
    @TableField(exist = false)
    private String startTime;

    @Transient
    @TableField(exist = false)
    private String endTime;



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
