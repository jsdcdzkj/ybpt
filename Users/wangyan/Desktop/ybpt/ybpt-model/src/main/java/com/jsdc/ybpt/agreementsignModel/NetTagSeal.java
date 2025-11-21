package com.jsdc.ybpt.agreementsignModel;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @projectName: ybpt
 * @className: NetTagSeal
 * @author: wp
 * @description:
 * @date: 2022/9/6 14:59
 */
@Data
@Entity(name = "net_tag_seal")
@TableName("net_tag_seal")
public class NetTagSeal {
    @Id
    @TableId(value = "id", type = IdType.INPUT)
    private String id;

    private String signature_id;

    private Integer seal_type;

    private String createUser;      //创建人
    private Date createTime;      //创建时间
    private String updateUser;      //创建用户
    private Date updateTime;      //创建时间
    private String is_del;          //删除标志、有效状态
}
