package com.jsdc.ybpt.service;


import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.SbBedDeclarationMapper;
import com.jsdc.ybpt.price.declare.SbApply;
import com.jsdc.ybpt.price.declare.SbBedDeclaration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SbBedDeclarationService extends BaseService<SbBedDeclaration> {

    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private SbBedDeclarationMapper sbBedDeclarationMapper ;


    public Integer verify(String project_code) {

        Integer count = sbBedDeclarationMapper.verify(sysUserService.getUser().getOrg_code(),project_code) ;
        return count;
    }


    public BigDecimal verify2(String project_code) {

        BigDecimal count = sbBedDeclarationMapper.verify2(sysUserService.getUser().getOrg_code(),project_code) ;
        return count;
    }


    public List<SbApply> verify3(String project_code,String type) {

        List<SbApply> sbApplyList = sbBedDeclarationMapper.verify3(sysUserService.getUser().getOrg_code(),project_code,type) ;
        return sbApplyList;
    }
}
