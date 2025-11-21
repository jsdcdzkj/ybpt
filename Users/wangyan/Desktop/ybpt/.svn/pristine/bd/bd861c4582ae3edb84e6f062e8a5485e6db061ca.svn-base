package com.jsdc.ybpt.price.drug;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 中药饮片
 */
@Data
@Entity(name = "sb_decoction_piece")
@TableName("sb_decoction_piece")
public class SbDecoctionPiece extends Model<SbDecoctionPiece> {
    @TableId
    @Id
    private String id;

    //分类编码
    private String sortingCodeNumber;
    //药品通用名编码
    private String common_name_code;
    //支付类别
    private String paymentCategory;
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

    //来源
    private String source;
    //限定支付范围
    private String limitPayment;
    //国家中药饮片代码
    private String sliceCode;
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
