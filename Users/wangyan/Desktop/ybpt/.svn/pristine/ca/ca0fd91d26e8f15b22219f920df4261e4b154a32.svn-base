package com.jsdc.ybpt.assessment;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * kh_assessment_content(机构考核内容)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "kh_assessment_content")
@TableName("kh_assessment_content")
public class KHAssessmentContent extends Model<KHAssessmentContent> {
    //主键
    @TableId
    @Id
    private String id;
    //考核管理ID
    private String manage_id;
    //考核项ID
    private String assess_detail_id;
    //得分
    private String scorel;
    //考核回答内容
    private String assess_contentl;
    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志
}
