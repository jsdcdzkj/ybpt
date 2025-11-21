package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "notice_to_accepter")
@TableName("notice_to_accepter")
public class NoticeToAccepter {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private String id;  //主键

    private String notice_id;

    private String accepter_code;  // 1: username   2: certno

    private String is_read;        //  0: 未读(默认)   1: 已读
}
