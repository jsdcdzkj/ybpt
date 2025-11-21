package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.OpspdiselistBQueryDao;
import com.jsdc.ybpt.model_query.OpspdiselistB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface OpspdiselistBQueryMapper extends BaseMapper<OpspdiselistB> {


    @SelectProvider(type = OpspdiselistBQueryDao.class,method = "opspQueryAll")
    List<OpspdiselistB> opspQueryAll();
    @SelectProvider(type = OpspdiselistBQueryDao.class,method = "opspQueryPage")
    Page<OpspdiselistB> opspQueryPage(Page page,OpspdiselistB opspdiselistB);
}
