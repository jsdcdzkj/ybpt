package com.jsdc.ybpt.capitalSettlement;

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
 * 资金清算主表(QsInfo)
 */
@Data
@Entity(name = "qs_info")
@TableName("qs_info")
public class QsInfo extends Model<QsInfo> {
    @TableId
    @Id
    private String id;
    //年份
    private String year;
    //统筹区
    private String admdvs;
    //上传时间
    private String upload_time;
    //发布状态1:待发布 2：已发布
    private String pub_status;
    @Transient
    @TableField(exist = false)
    private Integer pageNo ;

    @Transient
    @TableField(exist = false)
    private Integer pageSize ;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态


}
