package com.jsdc.ybpt.service.appropNotice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeSettle;
import com.jsdc.ybpt.mapper.appropNotice.AppropNoticeSettleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拨付通知-应结算(APPROP_NOTICE_SETTLE)业务实现类
 *
 * @author wangxiao
 * @date 2024-05-17 16:36:34
 */
@Service
@Slf4j
public class AppropNoticeSettleService extends ServiceImpl<AppropNoticeSettleMapper, AppropNoticeSettle> {
    @Resource
    private AppropNoticeSettleMapper appropNoticeSettleMapper;

}

