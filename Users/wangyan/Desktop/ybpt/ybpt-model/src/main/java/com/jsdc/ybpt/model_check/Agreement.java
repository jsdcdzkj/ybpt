package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 免责声明
 */
@Data
@Entity(name = "agreement")
@TableName("agreement")
public class Agreement extends Model<Agreement> {
    @TableId
    @Id
    private String id;
    // 用户id
    private String userId;
    // 协议内容
    private String content;
}
