package com.jsdc.ybpt.pur;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * 采购 集采缺货药品(耗材) 主表
 */
@Data
@Entity(name = "pur_stockout")
@TableName("pur_stockout")
public class PurStockout extends Model {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String org_name;//医药机构名称
    private String org_code;//医药机构省平台编码
    private String codeList;//省阳光采购平台产品编码
    private String fix_blng_admdvs;//统筹区
    private String stockout_type;//缺货分类 1.药品 2.耗材
    private String clues;//线索类型
    @Transient
    @TableField(exist = false)
    private String fix_blng_admdvs_name;
    @Transient
    @TableField(exist = false)
    private String stockout_type_name;
    @Transient
    @TableField(exist = false)
    private String isAudit;
    @Transient
    @TableField(exist = false)
    private String [] queryDate;
    @Transient
    @TableField(exist = false)
    private String startTime;
    @Transient
    @TableField(exist = false)
    private String endTime;
    @Transient
    @TableField(exist = false)
    @JsonPropertyDescription
    private List<PurStockoutDetail> purStockoutDetail;


    @Transient
    @TableField(exist = false)
    private Integer pageNo;

    @Transient
    @TableField(exist = false)
    private Integer pageSize;


    private String createUser;      //创建人
    private String createUserName;      //创建人
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" ,timezone = "GMT+8")
    private Date createTime;      //创建时间
    private String updateUser;      //更新用户
    private Date updateTime;      //更新时间
    private String is_del;          //删除标志、有效状态
}
