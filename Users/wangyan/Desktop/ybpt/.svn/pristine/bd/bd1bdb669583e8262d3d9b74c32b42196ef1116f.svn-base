package com.jsdc.ybpt.mapper.appropNotice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.appropNotice.dto.AppropNoticeSummaryAnalyseMergeDTO;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeSummaryAnalyse;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 拨付通知-汇总数据分析(APPROP_NOTICE_SUMMARY_ANALYSE)数据库访问层
 *
 * @author wangxiao
 * @date 2024-05-17 16:31:10
 */
@Mapper
public interface AppropNoticeSummaryAnalyseMapper extends BaseMapper<AppropNoticeSummaryAnalyse> {

    List<AppropNoticeSummaryAnalyse> mergeData(AppropNoticeSummaryAnalyseMergeDTO appropNoticeSummaryAnalyseMergeDTO);

}

