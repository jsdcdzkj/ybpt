package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 医疗服务 明细
 *
 */
@Data
@Entity(name = "Sb_medical_calculate")
@TableName("Sb_medical_calculate")
public class SbMedicalCalculate extends Model<SbMedicalCalculate> {
    @TableId
    @Id
    private String id;
    //标题
    private String title;
    //公立医疗机构
    private String sb_government_medical_id;
    //单位名称
    private String org_code;
    //单位医保编码
    private String org_name;
    //项目编码
    private String project_code;
    //项目名称
    private String project_name;
    //本市公立医疗机构价格
    private String org_price;
    //外地同级医院参考价格
    private String reference_price;
    //拟定价
    private String protocol_price;
    //管理费分摊
    private String manager_price;
    //其它	　
    private String other;
    //小计
    private String subtotal;
    //项目成本合计	　
    private String project_total;
}
