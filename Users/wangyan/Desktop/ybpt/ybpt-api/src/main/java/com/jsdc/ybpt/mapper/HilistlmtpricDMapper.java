package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.HilistlmtpricDao;
import com.jsdc.ybpt.model_query.HilistlmtpricD;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface HilistlmtpricDMapper extends BaseMapper<HilistlmtpricD> {

    @SelectProvider(type = HilistlmtpricDao.class,method = "hilistlmtpricQuery")
    Page<HilistlmtpricD> hilistlmtpricQuery(Page page,HilistlmtpricD hilistlmtpricD);
}
