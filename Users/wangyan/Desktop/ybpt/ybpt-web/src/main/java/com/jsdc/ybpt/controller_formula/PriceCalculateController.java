package com.jsdc.ybpt.controller_formula;


import com.jsdc.ybpt.service.formula.PriceCalculateService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 制剂定价测算(PriceCalculate)控制层
 *
 * @author yc
 * @since 2024-05-14 11:25:27
 */
@RestController
@RequestMapping("/priceCalculate")
//@Api(tags = "制剂定价测算(FORMULA_PRICE_CALCULATE)APi")
public class PriceCalculateController {

    @Resource
    private PriceCalculateService priceCalculateService;

}

