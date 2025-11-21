package com.jsdc.ybpt.agreementsignModel;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;


@Data
@Entity(name = "net_tag_file")
@TableName("net_tag_file")
public class NetTagFile extends Model<NetTagFile> {
    @TableId
    @Id
    private String id;
    private String oldFileName;//原文件名
    private String newFileName;//新文件名
    private String filePath;//父类
    private String associationId;//关联ID

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志

}
