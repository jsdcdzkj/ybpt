package com.jsdc.ybpt.vo;

import com.jsdc.ybpt.assessment.KHManage;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AssessmentVo extends KHManage{

    //组织机构代码
    private String org_code;
    //日志标题
    private String log_title;
    //批量审核ID
    private String verify_ids;
    //考核内容ID
    private String assessment_content_id;
    //任务名
    private String task_name;
    //得分
    private String scorel;
    //考核回答内容
    private String assess_contentl;
    //文件
    private MultipartFile files;

    private Integer pageNo = 1;
    private Integer pageSize = 10;
}
