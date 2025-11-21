package com.jsdc.ybpt.assessment;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
*剔除的机构
* Author wzn
* Date 2022/12/6 14:35
*/
@Data
@Entity(name = "blacklist")
@TableName("blacklist")
public class Blacklist extends Model<Blacklist> {
    @TableId
    @Id
    private String id;

    //考核任务管理Id
    private String taskManageId;
    //定点医药机构编号
    private String fixmedins_code;




}
