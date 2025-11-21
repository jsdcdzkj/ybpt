package com.jsdc.ybpt.mapper.declare;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.declare.SbApplyDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.declare.SbApply;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * (SbApply)表数据库访问层
 *
 * @author wangYan
 * @since 2023-02-01 16:22:07
 */
@Mapper
public interface SbApplyMapper extends BaseMapper<SbApply> {

    @SelectProvider(type = SbApplyDao.class, method = "getApplyPage")
    Page<SbApply> getApplyPage(Page page, SbApply sbApply, SysUser sysUser);

    @SelectProvider(type = SbApplyDao.class, method = "getApplyList")
    List<SbApply> getApplyList(SbApply sbApply, SysUser sysUser);
}

