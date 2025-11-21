package com.jsdc.ybpt.mapper.appropNotice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.appropNotice.dto.AppropNoticeSummaryMergeDTO;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeSummary;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 拨付通知-汇总数据(APPROP_NOTICE_SUMMARY)数据库访问层
 *
 * @author wangxiao
 * @date 2024-05-17 16:31:10
 */
@Mapper
public interface AppropNoticeSummaryMapper extends BaseMapper<AppropNoticeSummary> {

    List<AppropNoticeSummary> mergeData(AppropNoticeSummaryMergeDTO appropNoticeSummaryMergeDTO);

}

