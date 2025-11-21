package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

/**
 * 定点机构新增耗材申报入参
 */
@Data
public class MaterialDeclarationListVo extends Model {

    private String mcs_code;   //耗材国家编码
    private String hi_genname;          //医保通用名
    private String province_code;      //省收费编码
    private String charge_lv;  //收费等级
    private String charge_lv_memo; //收费等级使用说明
    private String product_price;     //支付标准
    private String dcla_stas;         //申报处理状态
}
