package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.HilistselfpaypropBDao;
import com.jsdc.ybpt.model_query.HilistselfpaypropB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface HilistselfpaypropBMapper extends BaseMapper<HilistselfpaypropB> {

    @SelectProvider(type = HilistselfpaypropBDao.class,method = "paypropQuery")
    Page<HilistselfpaypropB> paypropQuery(Page page,HilistselfpaypropB hilistselfpaypropB);
}
