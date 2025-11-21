package com.jsdc.ybpt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.ItemToMealMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.ItemToMeal;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author libin
 * @create 2022/5/26 20:20
 */
@Service
public class ItemToMealService extends BaseService<ItemToMeal> {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    ItemToMealMapper itemToMealMapper;

    public void addItemToMeal(ItemToMeal itemToMeal) {
        SysUser sysUser = sysUserService.getUser();
        itemToMeal.insert();
    }

    public void updateItemToMeal(ItemToMeal itemToMeal) {
        itemToMeal.updateById() ;
    }

    public Page<ItemToMeal> selectList(ItemToMeal itemToMeal) {
        Page<ItemToMeal> page = new Page<>(itemToMeal.getPageNo(), itemToMeal.getPageSize());
        QueryWrapper<ItemToMeal> queryWrapper = new QueryWrapper<>() ;

//        queryWrapper.like("name",civilworkerInfo.getName()) ;
//        queryWrapper.eq("certno",civilworkerInfo.getCertno()) ;
//        queryWrapper.eq("is_del","0") ;

        Page<ItemToMeal> itemToMealPage = itemToMealMapper.selectPage(page,queryWrapper) ;
        return itemToMealPage ;
    }


}
