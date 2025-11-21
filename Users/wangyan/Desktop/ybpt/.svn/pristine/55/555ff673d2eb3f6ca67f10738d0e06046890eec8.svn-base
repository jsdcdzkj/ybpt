package com.jsdc.ybpt.infoAssessment;

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
 * 信息化标准考核奖惩
 */
@Data
@Entity(name = "infoassessment")
@TableName("infoassessment")
public class InfoAssessment extends Model<InfoAssessment> {
    @TableId
    @Id
    private String id;

    //类型
    private String type;

    //定点医药机构编号
    private String fixmedins_code;

    //定点医药机构名称
    private String fixmedins_name;

    //级别
    private String aggrement_lv;

    //地区
    private String admdvs;

    //月份
    private String month;

    @Transient
    @TableField(exist = false)
    private String startTime;

    @Transient
    @TableField(exist = false)
    private String endTime;

    //------医保码结算率------
    //医保码结算率
    private String medicareCodeSettlementRate;
    //奖惩金额（万元）
    private String medicareCodeMoney;


    //------医保码全流程应用------
    //全流程应用情况
    private String applyCondition;
    //奖惩金额（万元）
    private String applyMoney;


    //------移动支付应用------
    //奖惩金额（万元）
    private String mobilePaymentMoney;

    //------电子处方流转平台接入------
    //奖惩金额（万元）
    private String electronicPrescriptionMoney;

    //------国家编码贯标（非标率）------
    //开单医师
    private String nationBillingPhysician;
    //手术操作
    private String nationOperation;
    //西药中成药
    private String westernMedicine;
    //中药饮片
    private String chinesePiece;
    //自制剂
    private String selfPreparation;
    //医用耗材
    private String medicalConsumables;
    //医疗服务项目
    private String medicalServiceItem;
    //奖惩金额（万元）
    private String nationMoney;

    //------药品追溯码采集------
    //奖惩金额（万元）
    private String traceableCodeMoney;

    //------其他接口改造和数据上传任务------
    //奖惩金额（万元）
    private String uploadPortMoney;

    //------工作试点示范------
    //奖励金额（万元）
    private String workPilotMoney;
    //备注
    private String remark;







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
