package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

/**
 * 非公立医疗服务自设项目自主定价
 * 说明表
 *
 */
@Data
@Entity(name = "Sb_business_explain")
@TableName("Sb_business_explain")
public class SbBusinessExplain extends Model<SbBusinessExplain> {
    @TableId
    @Id
    private String id;
    //
    private String business_id;

    //单位名称
    private String org_name;
    //单位医保编码
    private String org_code;
    //项目名称
    private String project_name;
    //项目编码
    private String project_code;
    //项目内涵
    private String project_content;
    //除外内容
    private String except_content;
    //计价单位
    private String unit;
    //说明
    private String explain;
    //价格
    private String price;

    //是否为《全国医疗服务价格项目规范2012年版》项目（如“是”，请在此填写《全国医疗服务价格项目规范2012年版》项目编码及名称）
    private String is_norm;
    private String norm_code;
    private String norm_name;
    //项目临床意义
    private String sense;
    //项目基本人力消耗及耗时
    private String base;
    //项目操作规范
    private String norm;
    //项目适用范围及可能产生的技术风险
    private String risk;
    //应用单位
    private String apply_unit;
    //结果
    private String result;
    //申报科室负责人
    private String declare;
    //医务科室负责人
    private String matters;
    //价格管理科室负责人
    private String price_manager;
    //法定代表人签字
    private String legal;
    //单位公章日期
    private String dept_date;

    @Transient
    @TableField(exist = false)
    private String year;

    @Transient
    @TableField(exist = false)
    private String month;

    @Transient
    @TableField(exist = false)
    private String day;


    //经营性质
    @Transient
    @TableField(exist = false)
    private String natures;
}
