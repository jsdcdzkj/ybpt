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
 * 拨付通知(APPROP_NOTICE)数据库映射实体类
 *
 * @author wangxiao
 * @date 2024-05-17 15:10:37
 */
@Data
@TableName("APPROP_NOTICE")
public class AppropNotice {

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
    @TableField("SEND_TIME")
//    @ApiModelProperty(value = "发送时间")
    private Date sendTime;
    
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

    @TableField("IS_GENER_ANALY")
//    @ApiModelProperty(value = "是否生成分析：0-否，1-是")
    private String isGenerAnaly;

    @TableField("TCQ")
//    @ApiModelProperty(value = "统筹区(来自字典：admdvs-area字典值)")
    private String tcq;
    
}

