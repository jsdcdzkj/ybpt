package com.jsdc.ybpt.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.HilistselfpaypropBMapper;
import com.jsdc.ybpt.model_query.HilistselfpaypropB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HilistselfpaypropBService extends BaseService<HilistselfpaypropB> {

    @Autowired
    private HilistselfpaypropBMapper mapper;

    @DS("reflowData")
    public Page<HilistselfpaypropB> paypropQuery(HilistselfpaypropB hilistselfpaypropB){
        Page page=new Page(hilistselfpaypropB.getPageNo(),hilistselfpaypropB.getPageSize());
        QueryWrapper<HilistselfpaypropB> queryWrapper=new QueryWrapper<>();
        if(!"".equals(hilistselfpaypropB.getHilist_code()) && null != hilistselfpaypropB.getHilist_code()){
            queryWrapper.like("hilist_code",hilistselfpaypropB.getHilist_code());
        }
        Page<HilistselfpaypropB> hilistselfpaypropBPage=mapper.paypropQuery(page,hilistselfpaypropB);
        return  hilistselfpaypropBPage;
    }

}
