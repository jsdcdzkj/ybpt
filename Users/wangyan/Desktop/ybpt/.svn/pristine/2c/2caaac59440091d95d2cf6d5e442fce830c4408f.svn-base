package com.jsdc.ybpt.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.HilistlmtpricDMapper;
import com.jsdc.ybpt.model_query.HilistlmtpricD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HilistlmtpricDService extends BaseService<HilistlmtpricD> {


    @Autowired
    private HilistlmtpricDMapper hilistlmtpricDMapper;
    @DS("reflowData")
    public Page<HilistlmtpricD> hilistlmtpricQuery(HilistlmtpricD hilistlmtpricD){
            Page page= new Page(hilistlmtpricD.getPageNo(),hilistlmtpricD.getPageSize());
            QueryWrapper<HilistlmtpricD> queryWrapper=new QueryWrapper<>();
        if(!"".equals(hilistlmtpricD.getHilist_code()) && null != hilistlmtpricD.getHilist_code()){
            queryWrapper.like("hilist_code",hilistlmtpricD.getHilist_code());
        }
            Page<HilistlmtpricD> hilistlmtpricDPage=hilistlmtpricDMapper.hilistlmtpricQuery(page,hilistlmtpricD);
        return hilistlmtpricDPage;
    }

}
