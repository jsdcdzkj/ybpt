package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 固定资产
 */
@Data
@Entity(name = "Sb_fixed_assets")
@TableName("Sb_fixed_assets")
public class SbFixedAssets extends Model<SbFixedAssets> {
    @TableId
    @Id
    private String id;
    //医疗服务 明细
    private String sb_medical_calculate_id;
    //设备名称
    private String name;
    //原值（元）
    private String price;
    //使用年限（年）
    private String year;
    //使用时间（小时）
    private String hour;
    //应计金额（元）
    private String amount;
    //排序
    private String sort;
}
