package com.jsdc.ybpt.service.appropNotice;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jsdc.ybpt.appropNotice.dto.AppropNoticeDataDetailMergeDTO;
import com.jsdc.ybpt.appropNotice.entity.AppropNoticeDataDetail;
import com.jsdc.ybpt.mapper.appropNotice.AppropNoticeDataDetailMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 拨付通知-数据明细(APPROP_NOTICE_DATA_DETAIL)业务实现类
 *
 * @author wangxiao
 * @date 2024-05-17 16:36:34
 */
@Service
@Slf4j
public class AppropNoticeDataDetailService extends ServiceImpl<AppropNoticeDataDetailMapper, AppropNoticeDataDetail> {
    @Resource
    private AppropNoticeDataDetailMapper appropNoticeDataDetailMapper;

    public AppropNoticeDataDetail mergeData(AppropNoticeDataDetailMergeDTO appropNoticeDataDetailMergeDTO){
        return appropNoticeDataDetailMapper.mergeData(appropNoticeDataDetailMergeDTO);
    }
}

