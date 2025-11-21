package com.jsdc.ybpt.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.jsdc.ybpt.eval_.EvalDesign;
import com.jsdc.ybpt.eval_.EvalOrgDetail;
import com.jsdc.ybpt.eval_.EvalStardardUser;
import com.jsdc.ybpt.model.FileInfo;
import lombok.Data;

import javax.persistence.Transient;
import java.util.List;
import java.util.Map;

@Data
public class EvalVo {
    private EvalDesign evalDesign;
    private List<EvalStardardUser> evalStardardUserList;
    private List<EvalOrgDetail> evalOrgDetailList;
    private List<FileInfo> fileInfoList;

    private List<Map<String,String>> files;

    private String appealCount;//申诉内容
    private List<Map<String,String>> appealFiles;//申诉文件

    private String id;
    //负责人
    private String userName;
    //负责人用户id
    private String userId;
    //描述
    private String memo;
    //排名
    private String rank;
    //排名百分比
    private String rankRate;
    private String fileIds;

    //机构考核任务
    private String evalOrgTaskId;
    //任务ID
    private String taskManageId;
    //考核设计表ID
    private String designId;
    //办法ID
    private String earnestMoneyId;
    //机构类型
    private String orgType;
    //类别
    private String category;
    //经营性质 1:公立 2:私立
    private String natures;
    //协议等级
    private String aggrementLv;
    //考核年度
    private String year;
    //机构名称
    private String orgName;
    //机构编码
    private String orgCode;
    //考核状态
    private String status;
    //统筹区
    private String admdvs;
    //过期时间
    private String expirationTime;
    //得分
    private String score;
    //平均数
    private String averageScore;
    //指标值
    private String targetScore;
    //保证金额
    private String margin;
    //机构指数
    private String orgRate;
    //返回金额
    private String refund;
    //奖励金
    private String rewards;
    //精神专科
    private String spirit;
    //任务名
    private String taskName;

    //发布状态 0未发布  1已发布
    private String publishStatus;
    //考核单ID
    private String assessmentId;
    //保证金展示状态 0否 1是
    private String earnestMoneyShow;
    //保证金文件
    private String earnestMoneyFile;

    private String title ;
    private String evalStardardId ;
    private String name ;

    private Integer pageNo;
    private Integer pageSize;
}
