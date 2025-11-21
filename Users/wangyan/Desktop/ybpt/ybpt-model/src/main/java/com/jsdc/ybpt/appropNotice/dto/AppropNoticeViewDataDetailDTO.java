package com.jsdc.ybpt.appropNotice.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @Description 医保拨付通知查看数据明细请求参数
 * @Author WangXiao
 * @Date 2024/5/23
 */
@Data
public class AppropNoticeViewDataDetailDTO {

    //    @ApiModelProperty(value = "拨付通知主键")
    @NotBlank(message = "拨付通知主键不能为空")
    private String appropNoticeId;

    //    @ApiModelProperty(value = "统筹区(来自字典：admdvs-area字典值)")
    @NotBlank(message = "统筹区不能为空")
    private String tcq;

//    @ApiModelProperty(value = "年份")
    @NotNull(message = "年份不能为空")
    private Integer year;

//    @ApiModelProperty(value = "月份")
    @NotNull(message = "月份不能为空")
    private Integer month;

    //    @ApiModelProperty(value = "医疗机构编码")
    private String orgCode;

    //    @ApiModelProperty(value = "医疗机构名称")
    private String orgName;

    private Integer pageNo;

    private Integer pageSize;

}
