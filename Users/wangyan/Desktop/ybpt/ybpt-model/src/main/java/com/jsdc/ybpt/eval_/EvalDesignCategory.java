package com.jsdc.ybpt.eval_;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.vo.EvalCategoryStardardVo;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：考核设计类目表
 * @Date ：2023/11/16 12:00
 * @Modified By：
 */
@Data
@Entity(name = "eval_design_category")
@TableName("eval_design_category")
public class EvalDesignCategory extends Model<EvalDesignCategory> {
    @TableId
    @Id
    private String id;

    //类目名称
    private String name;
    //分数
    private String score;
    //设计id
    private String designId;
    //创建时间
    private Date createTime;
    @Transient
    @TableField(exist = false)
    private List<EvalCategoryStardard> evalCategoryStardards;
}
