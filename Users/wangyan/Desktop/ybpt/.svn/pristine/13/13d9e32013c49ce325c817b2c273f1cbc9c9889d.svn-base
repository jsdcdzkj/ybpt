package com.jsdc.ybpt.service.appropNotice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.ybpt.appropNotice.dto.AppropNoticeSummaryMergeDTO;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeSummary;
import com.jsdc.ybpt.mapper.appropNotice.AppropNoticeSummaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 拨付通知-汇总数据(APPROP_NOTICE_SUMMARY)业务实现类
 *
 * @author wangxiao
 * @date 2024-05-17 16:36:34
 */
@Service
@Slf4j
public class AppropNoticeSummaryService extends ServiceImpl<AppropNoticeSummaryMapper, AppropNoticeSummary> {
    @Resource
    private AppropNoticeSummaryMapper appropNoticeSummaryMapper;

    public List<AppropNoticeSummary> mergeData(AppropNoticeSummaryMergeDTO appropNoticeSummaryMergeDTO){
       return appropNoticeSummaryMapper.mergeData(appropNoticeSummaryMergeDTO);
    }
    
}

