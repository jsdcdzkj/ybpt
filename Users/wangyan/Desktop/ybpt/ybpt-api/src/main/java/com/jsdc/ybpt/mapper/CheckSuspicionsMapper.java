package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.abnormal.CheckSuspicions;
import com.jsdc.ybpt.dao.CheckSuspicionsDao;
import org.apache.ibatis.annotations.InsertProvider;
import com.jsdc.ybpt.vo.CheckSuspicionsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface CheckSuspicionsMapper extends BaseMapper<CheckSuspicions> {
    @InsertProvider(type = CheckSuspicionsDao.class,method = "importDataSql")
    void importDataSql(String sql);

    @SelectProvider(method = "getList", type = CheckSuspicionsDao.class)
    List<CheckSuspicionsVo> getList(CheckSuspicions checkSuspicions);



    
}
