package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.DataDistributionDao;
import com.jsdc.ybpt.model.DataDistribution;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface DataDistributionMapper extends BaseMapper<DataDistribution> {

    @SelectProvider(type = DataDistributionDao.class, method = "queryPage")
    Page<DataDistribution> queryPage(Page page, DataDistribution bean);
    @SelectProvider(type = DataDistributionDao.class, method = "queryList")
    List<DataDistribution> queryList(DataDistribution bean);
}
