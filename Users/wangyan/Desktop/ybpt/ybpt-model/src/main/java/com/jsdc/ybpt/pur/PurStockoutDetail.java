package com.jsdc.ybpt.pur;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 采购 集采缺货药品(耗材) 明细
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "pur_stockout_detail")
@TableName("pur_stockout_detail")
public class PurStockoutDetail extends Model {
    @Id
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String pur_stockout_id;//主表ID
    private String stockout_type;//缺货分类 1.药品 2.耗材
    private String name;//药品 (耗材)名称
    private String unit;//规格
    private String code;//省阳光采购平台产品编码
    private String start_date;//缺货开始采购日期
    private String end_date;//缺货后最近一次采购订单日期
    private String sub_quantity;//缺货订单提交次数
    private String quantity;//数量
    private String price;//金额
    private String enterprise;//生产企业
    private String company;//配送公司
    private String note;//备注
    private String fileName;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态

}
