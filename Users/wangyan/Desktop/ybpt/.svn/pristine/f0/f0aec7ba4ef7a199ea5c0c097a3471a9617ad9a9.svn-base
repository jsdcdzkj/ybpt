package com.jsdc.ybpt.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.Date;

/**
 * 机构文件下发
 */
@Data
@Entity(name = "fileDelivery")
@TableName("fileDelivery")
public class FileDelivery extends Model<FileDelivery> {
    @TableId
    @Id
    private String id;

    //定点医药机构编号
    private String fixmedins_code;


    //定点医药机构名称
    private String fixmedins_name;

    //下发文件地址
    private String path ;

    //0 匹配成功   1匹配失败
    private String status ;

    //文件上传时间
    private String upload_time ;

    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //修改时间
    private String is_del;          //删除标志、有效状态


}
