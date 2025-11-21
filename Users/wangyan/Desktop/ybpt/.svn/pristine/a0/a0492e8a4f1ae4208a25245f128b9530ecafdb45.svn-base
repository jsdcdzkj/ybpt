package com.jsdc.ybpt.service.appropNotice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.ybpt.appropNotice.dto.AppropNoticeSummaryAnalyseMergeDTO;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeSummaryAnalyse;
import com.jsdc.ybpt.mapper.appropNotice.AppropNoticeSummaryAnalyseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 拨付通知-汇总数据分析(APPROP_NOTICE_SUMMARY_ANALYSE)业务实现类
 *
 * @author wangxiao
 * @date 2024-05-17 16:36:34
 */
@Service
@Slf4j
public class AppropNoticeSummaryAnalyseService extends ServiceImpl<AppropNoticeSummaryAnalyseMapper, AppropNoticeSummaryAnalyse> {
    @Resource
    private AppropNoticeSummaryAnalyseMapper appropNoticeSummaryAnalyseMapper;

    public List<AppropNoticeSummaryAnalyse> mergeData(AppropNoticeSummaryAnalyseMergeDTO appropNoticeSummaryAnalyseMergeDTO){
        return appropNoticeSummaryAnalyseMapper.mergeData(appropNoticeSummaryAnalyseMergeDTO);
    }
}

