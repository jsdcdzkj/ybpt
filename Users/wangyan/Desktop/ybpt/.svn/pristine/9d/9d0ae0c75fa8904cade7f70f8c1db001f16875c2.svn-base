package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.model.FileInfo;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：费用明细
 * @Date ：2024/6/11 11:05
 * @Modified By：
 */
@Data
@Entity(name = "expense")
@TableName("expense")
public class Expense extends Model<Expense> {
    @TableId
    @Id
    private String id;//主键
    //统筹区
    private String admdvs;
    private String year;
    private String admdvsName;
    private String createUser;
    private Date createTime;
    private String linkman;
    private String phone;
    private String bank;
    private String cardNo;
    private String account;
    private String personNum;
    private String money;

    @TableField(exist = false)
    @Transient
    private List<ExpenseDetail> expenseDetails;

    @TableField(exist = false)
    @Transient
    private FileInfo files;
    @TableField(exist = false)
    @Transient
    private String createName;
}
