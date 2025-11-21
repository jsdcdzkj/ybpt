package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.CivilworkerVirfyDao;
import com.jsdc.ybpt.dao.OpspDiseListBDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.CivilworkerVirfy;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface CivilworkerVirfyMapper extends BaseMapper<CivilworkerVirfy> {
    @SelectProvider(type= CivilworkerVirfyDao.class,method = "civilworkerVirfyList")
    Page<CivilworkerVirfy> civilworkerVirfyList(Page page, CivilworkerVirfy civilworkerVirfy, SysUser sysUser);


}
