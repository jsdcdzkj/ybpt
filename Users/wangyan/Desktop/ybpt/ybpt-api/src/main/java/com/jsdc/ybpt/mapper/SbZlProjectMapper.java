package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.SbZlProjectDao;
import com.jsdc.ybpt.price.declare.SbApply;
import com.jsdc.ybpt.price.zlproject.SbZlProject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface SbZlProjectMapper extends BaseMapper<SbZlProject> {

    @SelectProvider(type= SbZlProjectDao.class,method = "verify")
    Integer verify(String org_code,String project_code,String type);

    @SelectProvider(type= SbZlProjectDao.class,method = "verify2")
    BigDecimal verify2(String org_code,String project_code,String type);

    @SelectProvider(type= SbZlProjectDao.class,method = "verify3")
    List<SbApply> verify3(String org_code, String project_code, String type);


}
