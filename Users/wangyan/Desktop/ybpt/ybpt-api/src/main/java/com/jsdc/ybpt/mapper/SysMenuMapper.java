package com.jsdc.ybpt.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jsdc.ybpt.dao.SysMenuDao;
import com.jsdc.ybpt.model.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectProvider;

import java.util.List;


@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    @SelectProvider(type= SysMenuDao.class,method = "getMenusByUser")
    List<SysMenu> getMenusByUser(String userId, String parentId);
}
