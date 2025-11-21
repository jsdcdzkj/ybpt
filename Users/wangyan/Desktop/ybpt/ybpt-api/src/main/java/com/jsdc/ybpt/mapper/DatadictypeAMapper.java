package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.DatadictypeADao;
import com.jsdc.ybpt.model_query.DatadictypeA;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface DatadictypeAMapper extends BaseMapper<DatadictypeA> {

    @SelectProvider(type = DatadictypeADao.class,method = "datadictypeQuery")
    Page<DatadictypeA> datadictypeQuery(Page page,DatadictypeA datadictypeA);
}
