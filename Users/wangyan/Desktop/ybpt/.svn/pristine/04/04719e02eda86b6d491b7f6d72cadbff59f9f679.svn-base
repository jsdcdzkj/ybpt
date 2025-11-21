package com.jsdc.ybpt.service;


import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.SbProjectBackUpMapper;
import com.jsdc.ybpt.mapper.SbZlProjectMapper;
import com.jsdc.ybpt.price.declare.SbApply;
import com.jsdc.ybpt.price.zlproject.SbZlProject;
import com.jsdc.ybpt.service.declare.SbZlProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SbProjectService extends BaseService<SbZlProject> {

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SbZlProjectMapper sbZlProjectMapper;


    public Integer verify(String project_code, String type) {
        Integer count = sbZlProjectMapper.verify(sysUserService.getUser().getOrg_code(), project_code, type);
        return count;
    }


    public BigDecimal verify2(String project_code, String type) {
        BigDecimal count = sbZlProjectMapper.verify2(sysUserService.getUser().getOrg_code(), project_code, type);
        return count;
    }

    public List<SbApply> verify3(String project_code, String type) {
        List<SbApply> sbApplyList = sbZlProjectMapper.verify3(sysUserService.getUser().getOrg_code(), project_code, type);
        return sbApplyList;
    }
}
