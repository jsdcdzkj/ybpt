package com.jsdc.ybpt.price.declare;

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
 * 民办非营利医疗服务申报和药品耗材申报
 * 药品耗材
 *
 */
@Data
@Entity(name = "Sb_civilian_materials")
@TableName("Sb_civilian_materials")
public class SbCivilianMaterials extends Model<SbCivilianMaterials> {
    @TableId
    @Id
    private String id;
    //申诉id
    private String sb_apply_id;
    //名称
    private String project_name;
    //编码
    private String project_code;
    //单位
    private String unit;
    //实际销售价格
    private String sale_price;
    //实际采购价格
    private String purchase_price;
    //加成率
    private String rate;
    //公立医疗机构价格
    private String org_price;
    //是否在省平台上采购
    private String is_purchase;
    //备注
    private String remark;

    @Transient
    @TableField(exist = false)
    private int index;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
}
