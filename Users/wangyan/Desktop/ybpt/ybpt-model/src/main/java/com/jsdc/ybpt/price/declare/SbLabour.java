package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 劳务支出
 */
@Data
@Entity(name = "Sb_labour")
@TableName("Sb_labour")
public class SbLabour extends Model<SbLabour> {
    @TableId
    @Id
    private String id;
    //医疗服务 明细
    private String sb_medical_calculate_id;
    //参加人员
    private String name;
    //人数
    private String quantity;
    //工时(小时)
    private String hour;
    //小时工资福利额(元)
    private String hour_price;
    //应计金额（元）
    private String amount;
    //排序
    private String sort;
}
