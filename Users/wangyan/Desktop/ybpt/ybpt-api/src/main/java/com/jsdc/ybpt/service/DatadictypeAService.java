package com.jsdc.ybpt.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.DatadictypeAMapper;
import com.jsdc.ybpt.model_query.DatadictypeA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatadictypeAService extends BaseService<DatadictypeA> {
    @Autowired
    private DatadictypeAMapper mapper;
    @DS("reflowData")
    public Page<DatadictypeA> datadictypeQuery(DatadictypeA datadictypeA){
        Page page=new Page(datadictypeA.getPageNo(),datadictypeA.getPageSize());
        Page<DatadictypeA> datadictypeAPage=mapper.datadictypeQuery(page,datadictypeA);
        return datadictypeAPage;
    }
}
