package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;

@Data
@TableName("person_subscribe_record")
public class ReviewVo {
    private String id;
    private String service_star;        // 服务星级
    private String professional_star;   // 专业性星级
    private String service_status_star; // 服务态度星级
    private String react_star;          // 反应星级
    private String service_label;       // 服务标签
    private String service_review;      // 评论
    private String sched;//待体检:0 , 已体检:1，已过期:2， 已上传报告:3 ，已撤销：4
    private String civilworker_id;//外键-公务员信息
}
