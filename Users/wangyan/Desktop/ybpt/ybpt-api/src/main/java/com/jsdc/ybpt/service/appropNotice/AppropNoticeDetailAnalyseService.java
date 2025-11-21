package com.jsdc.ybpt.service.appropNotice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeDetailAnalyse;
import com.jsdc.ybpt.mapper.appropNotice.AppropNoticeDetailAnalyseMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拨付通知-数据明细分析(APPROP_NOTICE_DETAIL_ANALYSE)业务实现类
 *
 * @author wangxiao
 * @date 2024-05-17 16:36:34
 */
@Service
@Slf4j
public class AppropNoticeDetailAnalyseService extends ServiceImpl<AppropNoticeDetailAnalyseMapper, AppropNoticeDetailAnalyse> {
    @Resource
    private AppropNoticeDetailAnalyseMapper appropNoticeDetailAnalyseMapper;

    
}

