package com.jsdc.ybpt.eval_;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @Author ：xujian
 * @Description：考核公式配置
 * @Date ：2023/11/16 13:39
 * @Modified By：
 */
@Data
@Entity(name = "eval_formula_config")
@TableName("eval_formula_config")
public class EvalFormulaConfig extends Model<EvalFormulaConfig> {
    @TableId
    @Id
    private String id;

    //范围开始
    private String indexBegain;
    //结束范围
    private String indexEnd;
    //公式
    private String formula;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String isDel;          //删除标志、有效状态
}
