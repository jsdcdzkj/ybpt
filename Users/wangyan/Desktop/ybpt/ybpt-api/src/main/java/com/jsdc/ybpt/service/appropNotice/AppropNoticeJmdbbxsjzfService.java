package com.jsdc.ybpt.service.appropNotice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeJmdbbxsjzf;
import com.jsdc.ybpt.mapper.appropNotice.AppropNoticeJmdbbxsjzfMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拨付通知-居民大病保险实际支付(APPROP_NOTICE_JMDBBXSJZF)业务实现类
 *
 * @author wangxiao
 * @date 2024-05-17 16:36:34
 */
@Service
@Slf4j
public class AppropNoticeJmdbbxsjzfService extends ServiceImpl<AppropNoticeJmdbbxsjzfMapper, AppropNoticeJmdbbxsjzf> {
    @Resource
    private AppropNoticeJmdbbxsjzfMapper appropNoticeJmdbbxsjzfMapper;

    
}

