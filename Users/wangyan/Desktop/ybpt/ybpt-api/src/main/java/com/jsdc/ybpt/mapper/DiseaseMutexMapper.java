package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.DiseaseMutexDao;
import com.jsdc.ybpt.model.DiseaseMutex;
import com.jsdc.ybpt.vo.MmmtDisease;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

@Mapper
public interface DiseaseMutexMapper extends BaseMapper<DiseaseMutex> {


    @SelectProvider(type= DiseaseMutexDao.class,method = "getMmmtDisease")
    Page<MmmtDisease> getMmmtDisease(Page page,String diseCode, String diseName);
}
