package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.directory.CatalogItem;
import com.jsdc.ybpt.service.DirectoryService;
import com.jsdc.ybpt.service.SpecialDrugFilingService;
import com.jsdc.ybpt.specialDrugFiling.SpecialDrugFiling;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/specialDrugFiling")
public class SpecialDrugFilingController {
    @Autowired
    private SpecialDrugFilingService specialDrugFilingService;


    /**
    *2.2.2按目录项目查询使用情况
    * Author wzn
    * Date 2022/7/26 14:24
    */
    @RequestMapping("/selectSpecialDrugFiling")
    public ResultInfo selectCatalogItem(@RequestBody SpecialDrugFiling specialDrugFiling) {
        Page<SpecialDrugFiling> specialDrugFilingPage = specialDrugFilingService.selectSpecialDrugFiling(specialDrugFiling);
        return ResultInfo.success(specialDrugFilingPage);
    }
}
