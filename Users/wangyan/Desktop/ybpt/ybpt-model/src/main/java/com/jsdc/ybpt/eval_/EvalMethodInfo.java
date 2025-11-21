package com.jsdc.ybpt.eval_;

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
 * @Author ：苹果
 * @Description：考核设计评价项目内容办法详情表
 * @Date ：2023/11/16 13:46
 * @Modified By：
 */
@Data
@Entity(name = "eval_method_info")
@TableName("eval_method_info")
public class EvalMethodInfo extends Model<EvalMethodInfo> {
    @TableId
    @Id
    private String id;

    //办法id
    private String methodId;
    //开始比例
    private String startRate;
    //结束比例
    private String endRate;
    //分数
    private String score;
    //创建时间
    private Date createTime;
}
