package com.jsdc.ybpt.appropNotice.dto;

import lombok.Data;

/**
 * @Description 拨付通知分页请求参数
 * @Author WangXiao
 * @Date 2024/5/21
 */
@Data
public class AppropNoticePageDTO {
    //    @ApiModelProperty(value = "年份")
    private Integer year;

//    @ApiModelProperty(value = "月份")
    private Integer month;

//    @ApiModelProperty(value = "生成时间开始")
    private String beginCreateTime;

//    @ApiModelProperty(value = "生成时间开始")
    private String endCreateTime;

//    @ApiModelProperty(value = "发送时间开始")
    private String beginSendTime;

//    @ApiModelProperty(value = "发送时间开始")
    private String endSendTime;

    //    @ApiModelProperty(value = "状态:0-未发送，1-已发送")
    private String status;

    //    @ApiModelProperty(value = "机构代码")
    private String orgCode;

    private Integer pageNo;

    private Integer pageSize;

}
