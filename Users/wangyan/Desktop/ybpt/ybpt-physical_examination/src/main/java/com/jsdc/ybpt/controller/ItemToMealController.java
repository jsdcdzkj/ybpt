package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.ItemToMeal;
import com.jsdc.ybpt.service.ItemToMealService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author libin
 * @create 2022/5/26 20:17
 */
@RestController
@RequestMapping("/physExamConfig")
public class ItemToMealController {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private ItemToMealService itemToMealService;

    @PostMapping("/addItemToMeal")
    public ResultInfo addCiviWorkerInfo(@RequestBody ItemToMeal itemToMeal) {
        SysUser sysUser = sysUserService.getUser();

        itemToMealService.addItemToMeal(itemToMeal);
        return ResultInfo.success();
    }

    @PostMapping("/updateItemToMeal")
    public ResultInfo updateItemToMeal(@RequestBody ItemToMeal itemToMeal) {
        itemToMealService.updateItemToMeal(itemToMeal);
        return ResultInfo.success();
    }

    @PostMapping("/selectList")
    public ResultInfo selectItemToMealList(@RequestBody ItemToMeal itemToMeal) {
        Page<ItemToMeal> page = itemToMealService.selectList(itemToMeal);
        return ResultInfo.success(page);
    }

    @PostMapping("/delItemToMeal")
    public ResultInfo delItemToMeal(@RequestBody ItemToMeal itemToMeal) {
        return ResultInfo.success();
    }
}
