package com.jsdc.ybpt.mapper.appropNotice;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.appropNotice.dto.AppropNoticePageDTO;
import com.jsdc.ybpt.appropNotice.entity.AppropNotice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 拨付通知(APPROP_NOTICE)数据库访问层
 *
 * @author wangxiao
 * @date 2024-05-17 16:31:08
 */
@Mapper
public interface AppropNoticeMapper extends BaseMapper<AppropNotice> {

    IPage<AppropNotice> getPage(Page<AppropNotice> page, AppropNoticePageDTO appropNoticePageDTO);

    IPage<AppropNotice> getPageByOrgCode(Page<AppropNotice> page, @Param("appropNoticePageDTO")AppropNoticePageDTO appropNoticePageDTO);


}

