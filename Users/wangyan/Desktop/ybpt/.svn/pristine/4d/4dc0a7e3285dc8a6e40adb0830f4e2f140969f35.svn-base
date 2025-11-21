package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.abnormal.MedicalCareAbnormal;
import com.jsdc.ybpt.dao.MedicalCareAbnormalDao;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MedicalCareAbnormalMapper extends BaseMapper<MedicalCareAbnormal> {
    @InsertProvider(type = MedicalCareAbnormalDao.class,method = "importDataSql")
    void importDataSql(String sql);
}
