package com.jsdc.ybpt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.SysUserRoleDao;
import com.jsdc.ybpt.model.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    @SelectProvider(type= SysUserRoleDao.class,method = "getRoleNameByUser")
    List<String> getRoleNameByUser(String userId);
    @SelectProvider(type= SysUserRoleDao.class,method = "getRoleSymbolByUser")
    List<String> getRoleSymbolByUser(String userId);
}
