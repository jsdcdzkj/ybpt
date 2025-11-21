package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.directory.CatalogItem;
import com.jsdc.ybpt.mapper.DirectoryMapper;
import com.jsdc.ybpt.mapper.SpecialDrugFilingMapper;
import com.jsdc.ybpt.specialDrugFiling.SpecialDrugFiling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialDrugFilingService extends BaseService<CatalogItem> {

    @Autowired
    private SpecialDrugFilingMapper  specialDrugFilingMapper ;



    /**
    *特药备案人员信息查询
    * Author wzn
    * Date 2022/7/28 10:18
    */
    @DS("reflowData")
    public Page<SpecialDrugFiling> selectSpecialDrugFiling(SpecialDrugFiling specialDrugFiling) {
        Page<SpecialDrugFiling> page = new Page<>(specialDrugFiling.getPageNo(), specialDrugFiling.getPageSize());
        Page<SpecialDrugFiling> specialDrugFilingPage = specialDrugFilingMapper.selectSpecialDrugFiling(page,specialDrugFiling) ;
        return specialDrugFilingPage ;
    }





}
