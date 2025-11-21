package com.jsdc.ybpt.agreementsignModel;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @projectName: ybpt
 * @className: NetTagLog
 * @author: wp
 * @description:
 * @date: 2022/9/30 10:55
 */
@Data
@Entity(name = "net_tag_log")
@TableName("net_tag_log")
public class NetTagLog {
    @TableId
    @Id
    private String id;

    private String operate_user;

    private Date operate_time;

    private String title;

    private String content;

    public NetTagLog(){

    }

    public NetTagLog(String operate_user, String title, String content){
        this.id = IdUtil.simpleUUID();
        this.operate_user = operate_user;
        this.operate_time = new Date();
        this.title = title;
        this.content = content;
    }


}
