package com.jsdc.ybpt.mapper.pur;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.pur.PurStockoutDao;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.pur.PurStockout;
import com.jsdc.ybpt.vo.PurStockoutDetailVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * (PurStockout)表数据库访问层
 *
 * @author wangYan
 * @since 2023-04-03 14:59:51
 */
@Mapper
public interface PurStockoutMapper extends BaseMapper<PurStockout> {

    @SelectProvider(method = "getList",type = PurStockoutDao.class)
    List<PurStockoutDetailVo> getList(SysUser sysUser, PurStockout purStockout);
}

