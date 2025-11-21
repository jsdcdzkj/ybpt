package com.jsdc.ybpt.controller_formula;

import com.jsdc.ybpt.service.formula.CheckFeeService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 制剂定价测算-检验费(CheckFee)控制层
 *
 * @author yc
 * @since 2024-05-14 11:25:26
 */
@RestController
@RequestMapping("/checkFee")
//@Api(tags = "制剂定价测算-检验费(FORMULA_CHECK_FEE)APi")
public class CheckFeeController   {

    @Resource
    private CheckFeeService checkFeeService;

}

