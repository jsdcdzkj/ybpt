package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.AdviceDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.price.advice.Advice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * (Advice)表数据库访问层
 *
 * @author wangYan
 * @since 2023-06-21
 */
@Mapper
public interface AdviceMapper extends BaseMapper<Advice> {

    @SelectProvider(type = AdviceDao.class, method = "page")
    Page<Advice> page(Page page, Advice advice, SysUser sysUser);
    @SelectProvider(type = AdviceDao.class, method = "list")
    List<Advice> list(Advice advice, SysUser sysUser);

}

