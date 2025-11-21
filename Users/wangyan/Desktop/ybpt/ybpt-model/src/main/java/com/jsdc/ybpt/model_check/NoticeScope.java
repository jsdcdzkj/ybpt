package com.jsdc.ybpt.model_check;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity(name = "notice_scope")
@TableName("notice_scope")
public class NoticeScope {
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @Id
    private String id;  //主键

    private String notice_id;  // notice外键

    private String type;   // 1: 体检机构  2.用人单位  3: 个人(保留暂时不做)
}
