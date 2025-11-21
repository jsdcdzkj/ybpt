package com.jsdc.ybpt.appropNotice.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 拨付文档(APPROP_DOCUMENT)数据库映射实体类
 *
 * @author hanch
 * @date 2024-07-15
 */
@Data
@TableName("APPROP_DOCUMENT")
public class AppropDocument {

    @TableId(value = "ID")
    @Id
    private String id;

    @TableField("YEAR")
//    @ApiModelProperty(value = "年份")
    private Integer year;

    @TableField("MONTH")
//    @ApiModelProperty(value = "月份")
    private Integer month;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("PUB_TIME")
//    @ApiModelProperty(value = "发布时间")
    private Date pubTime;

    @TableField("STATUS")
//    @ApiModelProperty(value = "状态:0-未发送，1-已发送")
    private String status;

    @TableField("IS_DEL")
//    @ApiModelProperty(value = "是否删除：0-否，1-是")
    private String isDel;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("CREATE_TIME")
//    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @TableField("CREATE_USER")
//    @ApiModelProperty(value = "创建人主键")
    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField("UPDATE_TIME")
//    @ApiModelProperty(value = "修改时间")
    private Date updateTime;

    @TableField("UPDATE_USER")
//    @ApiModelProperty(value = "修改人主键")
    private String updateUser;

    @TableField("TCQ")
//    @ApiModelProperty(value = "统筹区(来自字典：admdvs-area字典值)")
    private String tcq;

    @TableField("DEPT_CODE")
//    @ApiModelProperty(value = "所属科室")
    private String deptCode;

    @TableField("DOC_TYPE")
//    @ApiModelProperty(value = "文档类型")
    private String docType;

    @TableField("DOC_NAME")
//    @ApiModelProperty(value = "文档名称")
    private String docName;

    @TableField("DOC_FILE")
//    @ApiModelProperty(value = "文档名称")
    private String docFile;

    @TableField(value = "NOTICE_ID",exist = false)
//    @ApiModelProperty(value = "文档名称")
    private String noticeId;
}
