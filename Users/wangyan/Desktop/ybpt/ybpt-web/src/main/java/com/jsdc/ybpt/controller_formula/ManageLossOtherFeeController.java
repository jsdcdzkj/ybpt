package com.jsdc.ybpt.controller_formula;


import com.jsdc.ybpt.service.formula.ManageLossOtherFeeService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

/**
 * 制剂定价测算-管理费、损耗及其他(ManageLossOtherFee)控制层
 *
 * @author yc
 * @since 2024-05-14 11:25:27
 */
@RestController
@RequestMapping("/manageLossOtherFee")
//@Api(tags = "制剂定价测算-管理费、损耗及其他(FORMULA_MANAGE_LOSS_OTHER_FEE)APi")
public class ManageLossOtherFeeController   {

    @Resource
    private ManageLossOtherFeeService manageLossOtherFeeService;

}

