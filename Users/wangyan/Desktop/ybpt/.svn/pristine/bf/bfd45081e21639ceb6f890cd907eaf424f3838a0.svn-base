package com.jsdc.ybpt.service;


import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.SysUserRoleMapper;
import com.jsdc.ybpt.model.SysUserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class SysUserRoleService extends BaseService<SysUserRole> {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    public List<String> getRoleNameByUser(String userId){
        return sysUserRoleMapper.getRoleNameByUser(userId);
    };

    public List<String> getRoleSymbolByUser(String userId){
        return sysUserRoleMapper.getRoleSymbolByUser(userId);
    };

}
