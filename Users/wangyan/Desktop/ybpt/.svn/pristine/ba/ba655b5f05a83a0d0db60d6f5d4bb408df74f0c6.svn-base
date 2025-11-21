package com.jsdc.ybpt.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.HilistMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.model_query.HilistB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HilistService extends BaseService<HilistB> {

    @Autowired
    private HilistMapper hilistMapper;

    @DS("reflowData")
    public List<HilistB> hilistBQueryAll(){
        List<HilistB> list=hilistMapper.hilistBQueryAll();
        return list;
    }
    @DS("reflowData")
    public Page<HilistB> queryPage(HilistB hilistB){
        Page page = new Page(hilistB.getPageNo(),hilistB.getPageSize());
        Page<HilistB> hilistBPage=hilistMapper.queryPage(page,hilistB);
        return hilistBPage;
    }
}
