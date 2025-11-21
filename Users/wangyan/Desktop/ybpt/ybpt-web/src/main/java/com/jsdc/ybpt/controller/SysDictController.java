package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @ClassName SysUserController
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 13:01
 * @Version 1.0
 */
@RestController
@RequestMapping("/sysdict")
public class SysDictController {
    @Autowired
    private SysDictService sysDictService;

    @PostMapping("/getDictsByType")
    public ResultInfo getDicts(String type){
        List<SysDict> sysDicts = sysDictService.list(new QueryWrapper<SysDict>().eq("dict_type",type).eq("is_del","0"));
        return ResultInfo.success(sysDicts);
    }


}
