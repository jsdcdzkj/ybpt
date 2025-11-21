package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.SbApplyDrugDao;
import com.jsdc.ybpt.dao.declare.SbApplyDao;
import com.jsdc.ybpt.price.declare.SbApplyDrug;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;

/**
 * (SbApplyDrug)表数据库访问层
 *
 * @author wangYan
 * @since 2023-08-22
 */
@Mapper
public interface SbApplyDrugMapper extends BaseMapper<SbApplyDrug> {

    @SelectProvider(type = SbApplyDrugDao.class, method = "sbApplyDrugPage")
    Page<SbApplyDrug> sbApplyDrugPage(Page page, SbApplyDrug sbApplyDrug);
    @SelectProvider(type = SbApplyDrugDao.class, method = "sbApplyDrugList")
    List<SbApplyDrug> sbApplyDrugList(SbApplyDrug sbApplyDrug);
}

