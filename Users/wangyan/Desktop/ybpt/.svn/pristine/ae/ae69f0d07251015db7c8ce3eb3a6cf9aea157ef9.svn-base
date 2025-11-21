package com.jsdc.ybpt.model;

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
 * 集采地区带量执行数据下发模块
 */
@Data
@Entity(name = "data_distribution")
@TableName("data_distribution")
public class DataDistribution extends Model<DataDistribution> {
    @TableId
    @Id
    private String id;

    /**医疗机构*/
    private String name;
    /**医疗机构编码*/
    private String fixedPointNumber;
    /**地区*/
    private String addr;
    /**批次名称*/
    private String batch_name;
    /**批次开始时间*/
    private String batch_start;
    /**批次结束时间*/
    private String batch_end;
    /**序时进度*/
    private String progress;
    /**签约产品总数*/
    private String contract_total;
    /**零采购产品数*/
    private String purchase_num;
    /**零采购产品数占比*/
    private String purchase_percentage;
    /**低于序时进度产品数*/
    private String index_num;
    /**低于序时进度产品数占比*/
    private String index_percentage;
    /**超量采购产品数*/
    private String excess_num;
    /**超量采购产品数占比*/
    private String excess_percentage;
    /**正常采购产品数*/
    private String normal_num;
    /**正常采购产品数占比*/
    private String normal_percentage;
    /**对应非中选产品总数*/
    private String unselected_total;
    /**采购非中选产品数*/
    private String unselected_num;
    /**非中选超量产品数*/
    private String selected_num;
    /**非中选超额产品数*/
    private String selectedExcess_num;
    /**对应可替代产品总数*/
    private String fungible_total;
    /**采购可替代产品数*/
    private String fungible_numbr;
    /**低于序时进度且非中选超量的产品数*/
    private String unselected_excess_number;


    /**批次号*/
    private String upload_no;
    /**统筹区*/
    private String admdvs;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

    @Transient
    @TableField(exist = false)
    //定点编号数据权限
    private String fixedPointNumberPermission;
    @Transient
    @TableField(exist = false)
    private Integer pageNo = 1;
    @Transient
    @TableField(exist = false)
    private Integer pageSize = 10;
    //开始时间
    @Transient
    @TableField(exist = false)
    private String start_time;
    //结束时间
    @Transient
    @TableField(exist = false)
    private String end_time;

}
