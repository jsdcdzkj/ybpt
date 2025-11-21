package com.jsdc.ybpt.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.OpspdiselistBQueryMapper;
import com.jsdc.ybpt.model_query.OpspdiselistB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpspdiselistBQueryService extends BaseService<OpspdiselistB> {
    @Autowired
    private OpspdiselistBQueryMapper opspdiselistBQueryMapper;
    @DS("reflowData")
    public List<OpspdiselistB> opspQueryAll(){
        List<OpspdiselistB> list=opspdiselistBQueryMapper.opspQueryAll();
        return list;
    }
    @DS("reflowData")
    public Page<OpspdiselistB> opspQueryPage(OpspdiselistB opspdiselistB){
        Page page = new Page(opspdiselistB.getPageNo(),opspdiselistB.getPageSize());
        QueryWrapper<OpspdiselistB> queryWrapper=new QueryWrapper<>();
        /*if(!"".equals() && null != ){
            queryWrapper.like();
        }*/
        if(!"".equals(opspdiselistB.getOpsp_dise_name()) && null != opspdiselistB.getOpsp_dise_name()){
            queryWrapper.like("opsp_dise_name",opspdiselistB.getOpsp_dise_name());
        }
        Page<OpspdiselistB> opspdiselistBPage=opspdiselistBQueryMapper.opspQueryPage(page,opspdiselistB);
        return opspdiselistBPage;
    }
}
