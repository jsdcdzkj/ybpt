package com.jsdc.ybpt.appropNotice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 生成拨付分析请求参数
 * @Author WangXiao
 * @Date 2024/5/24
 */
@Data
public class GenerateApproAnalyseDTO {

    //    @ApiModelProperty(value = "拨付通知主键")
    @NotBlank(message = "拨付通知主键不能为空")
    private String appropNoticeId;

    //    @ApiModelProperty(value = "年份")
    @NotNull(message = "年份不能为空")
    private Integer year;

    //    @ApiModelProperty(value = "月份")
    @NotNull(message = "月份不能为空")
    private Integer month;
}
