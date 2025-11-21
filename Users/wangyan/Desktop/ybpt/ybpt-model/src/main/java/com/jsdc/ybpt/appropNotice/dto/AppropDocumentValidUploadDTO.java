package com.jsdc.ybpt.appropNotice.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AppropDocumentValidUploadDTO {

    //    @ApiModelProperty(value = "年份")
    @NotNull(message = "年份不能为空")
    private Integer year;

    //    @ApiModelProperty(value = "月份")
    @NotNull(message = "月份不能为空")
    private Integer month;

    @NotNull(message = "文档类型不能为空")
    private String docType;

    private String docFile;

    private String docName;
}
