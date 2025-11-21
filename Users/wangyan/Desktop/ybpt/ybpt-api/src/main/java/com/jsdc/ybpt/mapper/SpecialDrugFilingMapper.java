package com.jsdc.ybpt.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.dao.DirectoryDao;
import com.jsdc.ybpt.dao.SpecialDrugFilingDao;
import com.jsdc.ybpt.directory.CatalogItem;
import com.jsdc.ybpt.specialDrugFiling.SpecialDrugFiling;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

@Mapper
public interface SpecialDrugFilingMapper extends BaseMapper<SpecialDrugFiling> {

    @SelectProvider(type= SpecialDrugFilingDao.class,method = "selectSpecialDrugFiling")
    Page<SpecialDrugFiling> selectSpecialDrugFiling(Page page, SpecialDrugFiling specialDrugFiling);
}
