package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 管理费及其他
 */
@Data
@Entity(name = "Sb_materials_consumption")
@TableName("Sb_materials_consumption")
public class SbMaterialsConsumption extends Model<SbMaterialsConsumption> {
    @TableId
    @Id
    private String id;
    //医疗服务 明细
    private String sb_medical_calculate_id;
    //品名
    private String name;
    //单位
    private String unit;
    //数量
    private String quantity;
    //单价（元）
    private String price;
    //应计金额（元）
    private String amount;
    //排序
    private String sort;
}
