package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.HilistBDao;
import com.jsdc.ybpt.model_query.HilistB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface HilistMapper extends BaseMapper<HilistB> {

    @SelectProvider(type = HilistBDao.class,method = "hilistBQueryAll")
    List<HilistB> hilistBQueryAll();
    @SelectProvider(type = HilistBDao.class,method = "queryPage")
    Page<HilistB> queryPage(Page page,HilistB hilistB);
}
