package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * ClassName: SysLog
 * Description:
 * date: 2022/6/13 16:38
 *
 * @author bn
 */
@Data
@Entity(name = "sys_log")
@TableName("sys_log")
public class SysLog extends Model<SysLog> {
    @TableId
    @Id
    private String id;

    //机构编码
    private String orgCode;

    //机构类型
    private String orgType;

    // 入参
    private String params;

    // 请求地址
    private String requestUrl;

    // 类名
    private String className;

    // 方法名
    private String methodName;

    // 状态 1.前置 2.后置
    private Integer flag;

    // 请求结果状态 0成功 -1 失败
    private Integer code;

    // 请求结果
    private String result;

    // 创建时间
    private Date createTime;

}
