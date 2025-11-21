package com.jsdc.ybpt.vo;

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
 * 马上贷-银行统计
 */
@Data
public class BankVo extends Model<BankVo> {
    //审核通过数量
    private String passQuantity ;
    //驳回数量
    private String numberOfRejections ;
    //放贷总金额
    private String loanAmount ;


}
