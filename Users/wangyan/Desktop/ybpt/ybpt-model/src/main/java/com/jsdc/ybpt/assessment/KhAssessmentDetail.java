package com.jsdc.ybpt.assessment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

/**
 * 考核单
 */
@Data
@Entity(name = "kh_assessment_detail")
@TableName("kh_assessment_detail")
public class KhAssessmentDetail extends Model<KhAssessmentDetail> {
    //主键
    @TableId
    @Id
    private String id;
    private String assessment_id;//考核单ID
    private String assess_question;//考核问题
    private String full_score;//分值
    private String is_text;//是否有文本答复
    private String is_file;//是否有文件答复
    private Integer sort;//序号


    //考核内容ID
    @Transient
    @TableField(exist = false)
    private String assessment_content_id;
    //得分
    @Transient
    @TableField(exist = false)
    private String scorel;
    //考核回答内容
    @Transient
    @TableField(exist = false)
    private String assess_contentl;
    //考核类型
    @Transient
    @TableField(exist = false)
    private String[] checkList;
    //文件
    @Transient
    @TableField(exist = false)
    private List<Map> files;


    @Transient
    @TableField(exist = false)
    private List<String> formOfReply;
}
