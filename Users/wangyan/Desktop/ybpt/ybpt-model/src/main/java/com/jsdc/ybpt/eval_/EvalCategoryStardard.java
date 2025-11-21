package com.jsdc.ybpt.eval_;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.vo.EvalStardardMethodVo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：考核指标
 * @Date ：2023/11/16 13:39
 * @Modified By：
 */
@Data
@Entity(name = "eval_category_stardard")
@TableName("eval_category_stardard")
public class EvalCategoryStardard extends Model<EvalCategoryStardard> {
    @TableId
    @Id
    private String id;

    //指标标题
    @Column(length = 512 )
    private String title;
    //分数
    private String score;
    //类目id
    private String categoryId;
    //创建时间
    private Date createTime;
    //指标描述
    @Column(length = 512 )
    private String content;

    @Transient
    @TableField(exist = false)
    private List<EvalStardardMethod> evalStardardMethods;

    //负责人
    @Transient
    @TableField(exist = false)
    private String userId;
    //负责人
    @Transient
    @TableField(exist = false)
    private String userName;
}
