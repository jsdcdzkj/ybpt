package com.jsdc.ybpt.appropNotice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 医保拨付通知查看汇总数据请求参数
 * @Author WangXiao
 * @Date 2024/5/23
 */
@Data
public class AppropNoticeViewSummaryDTO {

    //    @ApiModelProperty(value = "拨付通知主键")
    @NotBlank(message = "拨付通知主键不能为空")
    private String appropNoticeId;

    //    @ApiModelProperty(value = "统筹区(来自字典：admdvs-area字典值)")
    private String tcq;

//    @ApiModelProperty(value = "年份")
    @NotNull(message = "年份不能为空")
    private Integer year;

//    @ApiModelProperty(value = "月份")
    @NotNull(message = "月份不能为空")
    private Integer month;
}
