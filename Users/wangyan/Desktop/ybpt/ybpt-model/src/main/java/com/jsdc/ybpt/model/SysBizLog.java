package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Data
@Entity(name = "sys_biz_log")
@TableName("sys_biz_log")
public class SysBizLog extends Model<SysBizLog> {
    @TableId
    @Id
    private String id;

    //操作人用户ID
    private String operatId;

    //操作类型 1：新增 2：修改 3：删除
    private String operatType;

    // 功能模块
    private String modelName;

    // 入参
    private String params;

    // 出参
    private String resultParams;

    // 请求地址
    private String requestUrl;

    //描述
    private String memo;

    // 创建时间
    private Date createTime;

}
