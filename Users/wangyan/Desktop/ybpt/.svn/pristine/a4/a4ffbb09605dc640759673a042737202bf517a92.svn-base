package com.jsdc.ybpt.eval_;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.assessment.KhTaskManage;
import com.jsdc.ybpt.vo.EvalDesignCategoryVo;
import lombok.Data;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：考核设计表
 * @Date ：2023/11/16 11:55
 * @Modified By：
 */
@Data
@Entity(name = "eval_design")
@TableName("eval_design")
public class EvalDesign extends Model<EvalDesign> {
    @TableId
    @Id
    private String id;

    //考核单标题
    private String title;

    //机构类型
    private String orgType;
    //协议等级
    private String aggrementLv;
    //类别
    private String category;
    //考核年度
    private String year;
    //状态
    private String status;
    //创建人
    private String createUser;
    //创建时间
    private Date createTime;
    //修改人
    private String updateUser;
    //修改时间
    private Date updateTime;
    //删除
    private String isDel;
    //经营性质
    private String natures;
    //精神科  0：非精神专科  1精神专科
    private String spirit;
    /**
     * 分值
     */
    private String score;
    @Transient
    @TableField(exist = false)
    private List<EvalDesignCategory> evalDesignCategorys;

    @Transient
    @TableField(exist = false)
    private Long isFlag;
}
