package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

@Data
@Entity(name = "notice")
@TableName("notice")
public class Notice {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private String id;  //主键

    private String title;  // 通知标题

    @Column(columnDefinition = "CLOB")
    private String content;  // 通知内容

    private String createUser;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")//创建人
    private Date createTime;      //创建时间

    private String updateUser;      //创建用户

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    private String is_launch; //    0:不发布(默认)  1:发布

    private String is_del;          // 0: 不删除(默认) 1: 删除

    private Date launchTime;  // 发送时间

    private String range;   // t: 体检机构  y:用人单位  p: 个人(保留暂时不做)  'j' '医疗机构';'d''零售药店'


    @Transient
    @TableField(exist = false)
    private String rangeStr;

    @Transient
    @TableField(exist = false)
    private Integer pageNo = 1;
    @Transient
    @TableField(exist = false)
    private Integer pageSize = 10;
    @Transient
    @TableField(exist = false)
    private String is_read;


}
