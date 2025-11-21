package com.jsdc.ybpt.agreementsignModel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.capitalSettlement.QsInfo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Data
@Entity(name = "net_tag_register_log")
@TableName("net_tag_register_log")
public class NetTagRegisterLog extends Model<NetTagRegisterLog> {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    private String id;


    /**
     * 用户类型 1：个人 2:企业
     */
    private String user_type;

    /**
     * 企业实名认证地址返回的交易号
     */
    private String company_transaction_no;

    /**
     * 个人实名认证地址返回的交易号
     */
    private String personal_transaction_no;

    private String jsonString ;
    private String resString ;

}
