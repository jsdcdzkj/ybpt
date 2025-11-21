package com.jsdc.ybpt.controller_formula;


import com.jsdc.ybpt.formula.domain.dto.CatalogPageDTO;
import com.jsdc.ybpt.formula.domain.entity.Catalog;
import com.jsdc.ybpt.service.formula.CatalogService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @description //TODO  制剂目录(Catalog)控制层
 * @author wangxiao
 * @date 2024/5/14n
 */
@RestController
@RequestMapping("/catalog")
public class CatalogController {

    @Resource
    private CatalogService catalogService;

    /**
     *
     * @description //TODO  分页查询
     * @author wangxiao
     * @date 2024/5/15
     * @param catalogPageDTO
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/page")
    public ResultInfo getPage(@RequestBody CatalogPageDTO catalogPageDTO) {
        return ResultInfo.success(catalogService.getPage(catalogPageDTO));
    }

    /**
     *
     * @description //TODO  导入数据
     * @author wangxiao
     * @date 2024/5/15
     * @param file
     * @return com.jsdc.ybpt.vo.ResultInfo
     */
    @PostMapping("/importData")
    public ResultInfo importData(MultipartFile file) {
        try {
            catalogService.importData(file);
        } catch (Exception e) {
            return ResultInfo.error(e.getMessage());
        }
        return ResultInfo.success();
    }

    /**
     *
     * @description //TODO  导出数据
     * @author wangxiao
     * @date 2024/5/15
     * @param catalog
     * @param response
     * @return void
     */
    @PostMapping("/exportData")
    public void exportData(@RequestBody Catalog catalog, HttpServletResponse response) {
        catalogService.exportData(catalog,response);
    }


}

