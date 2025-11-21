package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.DataGovernanceDao;
import com.jsdc.ybpt.model.DataGovernance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface DataGovernanceMapper extends BaseMapper<DataGovernance> {

    @SelectProvider(type = DataGovernanceDao.class, method = "queryPage")
    Page<DataGovernance> queryPage(Page page, DataGovernance bean);
    @SelectProvider(type = DataGovernanceDao.class, method = "queryList")
    List<DataGovernance> queryList(DataGovernance bean);
}
