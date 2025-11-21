package com.jsdc.ybpt.price.advice;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.price.advice.vo.AdviceVo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 徐州市医疗服务项目价格调整建议汇总表
 *
 * @author wangYan
 * @since 2023/5/9
 */
@Data
@Entity(name = "advice_summary")
@TableName("advice_summary")
public class AdviceSummary extends Model<AdviceSummary> {
    @TableId
    @Id
    private String id;
    private String advice_id ;
    private String org_name ;
    //项目编码
    private String project_name;
    // 项目名称
    private String project_code;
    //项目内涵
    private String projectConnotation;
    //除外内容
    private String excludedContent;
    //计价单位
    private String unit;
    //等级
    private String levelOfChargeItem;
    //项目说明
    private String explain;
    //测算成本（元）
    private String calculate;
    //建议调整价格（元）
    private String price;
    //工作量
    private String workload;
    //备注
    private String memo;

    //预计年开展服务例数
    private String expect;
    //实际年开展服务例数
    private String actual;
    //内涵一次性耗材消耗 小计
    private String one_cost;
    private String one_cost_list;

    //电、水、气等常规消耗 小计
    private String conventional_cost;
    private String conventional_cost_list;

    //劳务费用 小计
    private String labor_cost;
    private String labor_cost_list;
    //仪器设备折旧费 小计
    private String depreciation_cost;
    private String depreciation_cost_list;
    //仪器设备维修费 小计
    private String maintenance_cost;
    private String maintenance_cost_list;
    //专用房屋折旧及维修 小计
    private String special_cost;
    private String special_cost_list;
    // 房屋大修理费 小计
    private String housing_cost;
    private String housing_cost_list;
    //总成本合计（元）
    private String total_cost;
    //调整后价格（元）
    private String adjust_cost;
    //外省市价格（元）
    private String provincial_cost;

    //jsonObject 具体明细
    private String detail;

    private Date createTime;      //创建时间
    @Transient
    @TableField(exist = false)
    private int index;


    @Transient
    @TableField(exist = false)
    private List<AdviceVo> one_cost_list1 ;
    @Transient
    @TableField(exist = false)
    private List<AdviceVo> conventional_cost_list1 ;

    @Transient
    @TableField(exist = false)
    private List<AdviceVo> labor_cost_list1 ;

    @Transient
    @TableField(exist = false)
    private List<AdviceVo> depreciation_cost_list1 ;

    @Transient
    @TableField(exist = false)
    private List<AdviceVo> maintenance_cost_list1 ;

    @Transient
    @TableField(exist = false)
    private List<AdviceVo> special_cost_list1 ;

    @Transient
    @TableField(exist = false)
    private List<AdviceVo> housing_cost_list1 ;

    @Transient
    @TableField(exist = false)
    private String filling_time ;



}
