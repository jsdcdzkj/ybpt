package com.jsdc.ybpt.appropNotice.vo;

import com.jsdc.ybpt.appropNotice.entity.*;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Description 拨付通知预览返回实体
 * @Author WangXiao
 * @Date 2024/5/23
 */
@Data
public class AppropNoticePreviewVO {
    //    @ApiModelProperty(value = "年份")
    @NotNull(message = "年份不能为空")
    private Integer year;

    //    @ApiModelProperty(value = "月份")
    @NotNull(message = "月份不能为空")
    private Integer month;

    //    @ApiModelProperty(value = "发生数数据")
    private List<AppropNoticeOccur> occurData;

    //    @ApiModelProperty(value = "应结算数据")
    private List<AppropNoticeSettle> settleData;

    //    @ApiModelProperty(value = "居民大病保险实际支付数据")
    private List<AppropNoticeJmdbbxsjzf> jmdbbxsjzfData;

    //    @ApiModelProperty(value = "月结算数据")
    private List<AppropNoticeMonthSettle> monthSettleData;

    //    @ApiModelProperty(value = "DRG数据")
    private List<AppropNoticeDrg> drgData;

    //    @ApiModelProperty(value = "职工汇总数据")
    private List<AppropNoticeSummary> zgSummaries;

    //    @ApiModelProperty(value = "居民汇总数据")
    private List<AppropNoticeSummary> jmSummaries;

    //    @ApiModelProperty(value = "数据明细")
    private List<AppropNoticeDataDetail> dataDetail;

    //    @ApiModelProperty(value = "转换成万元的职工汇总数据")
    private List<AppropNoticeSummary> convertZgSummaries;

    //    @ApiModelProperty(value = "转换成万元的居民汇总数据")
    private List<AppropNoticeSummary> convertJmSummaries;

    //    @ApiModelProperty(value = "转换成万元的数据明细")
    private List<AppropNoticeDataDetail> convertDataDetail;

}
