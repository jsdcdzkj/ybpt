package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
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
@Entity(name = "expense_detail")
@TableName("expense_detail")
public class ExpenseDetail extends Model<ExpenseDetail> {
    @TableId
    @Id
    private String id;//主键
    //直属
    private String admdvs;
    //直属
    private String admdvsId;
    private String name;
    //单位id
    private  String unitId;
    //单位名
    private  String unitName;
    //财务帐号
    private String account;
    //开户行
    private String bank;
    //卡号
    private String cardNo;
    //人数
    private String personNum;
    //金额
    private String money;
    //联系人
    private String linkman;
    //联系方式
    private String phone;
    private String isDel;
    private Date createTime;
    private String createUser;
    //备注
    private  String remark;
//    private String isUpload;
    //省属记录id
    private String expenseId;
    //是否为省属  0省属1区级
    private String Isexpense;
    private String year;

    @TableField(exist = false)
    @Transient
    private FileInfo files;

    @TableField(exist = false)
    @Transient
    private Integer index;
}
