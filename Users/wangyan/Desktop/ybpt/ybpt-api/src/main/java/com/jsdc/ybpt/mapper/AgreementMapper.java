package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.AgreementDao;
import com.jsdc.ybpt.dao.AutonomousMedicalDao;
import com.jsdc.ybpt.model_check.Agreement;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface AgreementMapper extends BaseMapper<Agreement> {
    @SelectProvider(type = AgreementDao.class, method = "getFirstRecord")
    Agreement getFirstRecord();
}
