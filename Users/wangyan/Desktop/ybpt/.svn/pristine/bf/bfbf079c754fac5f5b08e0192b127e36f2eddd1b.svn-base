package com.jsdc.ybpt.eval_;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.vo.EvalMethodInfoVo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;

/**
 * @Author ：苹果
 * @Description：考核设计评价项目内容办法表
 * @Date ：2023/11/16 13:42
 * @Modified By：
 */
@Data
@Entity(name = "eval_stardard_method")
@TableName("eval_stardard_method")
public class EvalStardardMethod extends Model<EvalStardardMethod> {
    @TableId
    @Id
    private String id;
    //创建时间
    private Date createTime;
    //办法名称
    @Column(length = 512 )
    private String name;
    //分数
    private String score;
    //指标id
    private String stardardId;
    //自定义配置
    private String  configuration;
    //模板id
    private String fileId;
    //填写描述
    private String isDescribe;
    //文件上传
    private String isUpload;
    private String fillIn;    //机构上传

    //计算方式
    private String type;
    //机构自主评分
    private String autoScore;
    //填写指标值
    private String indicator;


    @Transient
    @TableField(exist = false)
    private FileInfo fileTemplate;

    @Transient
    @TableField(exist = false)
    private List<EvalMethodInfo> evalMethodInfos;

    @Transient
    @TableField(exist = false)
    private EvalOrgDetail evalOrgDetail;

    @Transient
    @TableField(exist = false)
    private List<FileInfo> fileInfoList;
}




