package com.jsdc.ybpt.appropNotice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @Description 拨付通知预览请求参数
 * @Author WangXiao
 * @Date 2024/5/21
 */
@Data
public class AppropNoticePreviewDTO {
    //    @ApiModelProperty(value = "年份")
    @NotNull(message = "年份不能为空")
    private Integer year;

    //    @ApiModelProperty(value = "月份")
    @NotNull(message = "月份不能为空")
    private Integer month;

    String occurFileId;

    String settleFileId;

    String jmdbbxsjzfFileId;

    String monthSettleFileId;

    String drgFileId;
}
