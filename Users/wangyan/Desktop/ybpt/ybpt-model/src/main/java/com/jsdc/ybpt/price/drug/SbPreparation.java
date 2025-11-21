package com.jsdc.ybpt.price.drug;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 医院制剂
 */
@Data
@Entity(name = "sb_preparation")
@TableName("sb_preparation")
public class SbPreparation extends Model<SbPreparation> {
    @TableId
    @Id
    private String id;

    //分类编码
    private String sortingCodeNumber;
    //药品通用名编码
    private String common_name_code;
    //产品名编码
    private String productNameCoding;
    //产品名称
    private String productName;
    //商品名
    private String tradeName;
    //剂型
    private String dosageForm;
    //规格
    private String spec;
    //单位
    private String unit;

    //处方药标志
    private String prescriptionDrugMark;
    //药品批准文号
    private String approvalNumber;
    //制剂配制地址
    private String configurationAddress;
    //生产单位
    private String  productionUnit ;
    //生产单位所在地
    private String location;
    //生产地自付比例'
    private String outOfPocketRatio;
    //        国家医疗机构制剂代码
    private String preparationCode;
    //变更类型
    private String changeType;
    //        变更原因
    private String reasonsForChange;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
    
}
