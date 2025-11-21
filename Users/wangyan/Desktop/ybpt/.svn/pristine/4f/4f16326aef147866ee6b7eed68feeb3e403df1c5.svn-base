package com.jsdc.ybpt.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.SysDictMapper;
import com.jsdc.ybpt.model.SysDict;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;


@Service
public class SysDictService extends BaseService<SysDict> {
    @Autowired
    private SysDictMapper sysDictMapper;

    /**
     * 通过类型和值查
     * Author wzn
     * Date 2022/5/24 16:02
     */
    public SysDict selectByValue(SysDict sysDict) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", sysDict.getDict_type());
        queryWrapper.eq("value", sysDict.getValue());
        return sysDictMapper.selectOne(queryWrapper);
    }

    public List<SysDict> select(SysDict sysDict) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", sysDict.getDict_type());
        return sysDictMapper.selectList(queryWrapper);
    }

    public List<SysDict> select(String dict_type) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", dict_type);
        return sysDictMapper.selectList(queryWrapper);
    }

    public SysDict selectByValueAndType(SysDict sysDict) {
        QueryWrapper<SysDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("dict_type", sysDict.getDict_type());
        queryWrapper.eq("value", sysDict.getValue());
        queryWrapper.eq("is_del", sysDict.getIs_del());
        return sysDictMapper.selectOne(queryWrapper);
    }
}
