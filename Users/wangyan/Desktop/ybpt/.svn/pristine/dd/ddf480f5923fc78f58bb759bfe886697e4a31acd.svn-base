package com.jsdc.ybpt.appropNotice.entity;


import cn.hutool.core.annotation.Alias;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 拨付通知发送记录(APPROP_NOTICE_SEND)数据库映射实体类
 *
 * @author wangxiao
 * @date 2024-05-17 15:10:37
 */
@Data
@TableName("APPROP_NOTICE_SEND")
public class AppropNoticeSend {

    @TableId(value = "ID")
    @Id
    private String id;

    @TableField("APPROP_NOTICE_ID")
    //@ApiModelProperty(value = "拨付通知主键")
    private String appropNoticeId;

    @TableField("ORG_CODE")
    //@ApiModelProperty(value = "定点机构编码")
    @Alias("定点机构编码")
    private String orgCode;

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

    
}

