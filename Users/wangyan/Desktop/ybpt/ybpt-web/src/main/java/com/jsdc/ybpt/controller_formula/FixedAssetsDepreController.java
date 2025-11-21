package com.jsdc.ybpt.controller_formula;

import com.jsdc.ybpt.service.formula.FixedAssetsDepreService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 制剂定价测算-固定资产折旧(FixedAssetsDepre)控制层
 *
 * @author yc
 * @since 2024-05-14 11:25:26
 */
@RestController
@RequestMapping("/fixedAssetsDepre")
//@Api(tags = "制剂定价测算-固定资产折旧(FORMULA_FIXED_ASSETS_DEPRE)APi")
public class FixedAssetsDepreController   {

    @Resource
    private FixedAssetsDepreService fixedAssetsDepreService;

}

