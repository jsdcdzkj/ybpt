package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.abnormal.SettleAbnormal;
import com.jsdc.ybpt.dao.SettleAbnormalDao;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SettleAbnormalMapper extends BaseMapper<SettleAbnormal> {
    @InsertProvider(type = SettleAbnormalDao.class,method = "importDataSql")
    void importDataSql(String sql);
}
