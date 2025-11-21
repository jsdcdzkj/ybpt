package com.jsdc.ybpt.price.declare;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Entity(name = "Sb_bedDetails")
@TableName("Sb_bedDetails")
public class SbBedDetails extends Model<SbBedDetails> {
    @TableId
    @Id
    private String id;
    //主表ID
    private String bed_declaration_id;
    //病区
    private String inpatientWard;
    //床位数（张）
    private String bed_count;
    //床位号
    private String bed_number;
    //床位等级
    private String bed_class;
    //价格
    private String price;
    //备注
    private String remark;
    //项目编码
    private String project_code;
    //项目名称
    private String project_name;



}
