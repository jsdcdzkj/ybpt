package com.jsdc.ybpt.service;


import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.model.SysMenu;
import com.jsdc.ybpt.model.SysRole;
import com.jsdc.ybpt.model.SysRoleMenu;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysRoleService extends BaseService<SysRole> {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    public ResultInfo saveRole(SysRole sysRole){
        SysUser currUser = sysUserService.getUser();
        if(StringUtils.hasText(sysRole.getId())){
            //修改
            sysRole.setUpdateUser(currUser.getId());
            sysRole.setUpdateTime(new Date());
            //删除老的菜单关联数据
            boolean flag = sysRoleMenuService.remove(new QueryWrapper<SysRoleMenu>().eq("role_id", sysRole.getId()));

        }else{
            //新增
            sysRole.setId(IdUtil.simpleUUID());
            sysRole.setTissue(currUser.getOrg_code());
            sysRole.setCreateUser(currUser.getId());
            sysRole.setCreateTime(new Date());
        }
        Map<String,String> parentMap = new HashMap<>();
        //新增角色菜单关联数据
        for (String menuId:sysRole.getMenuIds()){
            SysMenu sysMenu = sysMenuService.getById(menuId);
            if(sysMenu.getVue_url().indexOf("views") > 0){
                SysRoleMenu sysRoleMenu = new SysRoleMenu();
                sysRoleMenu.setId(IdUtil.simpleUUID());
                sysRoleMenu.setRole_id(sysRole.getId());
                sysRoleMenu.setIs_del("0");
                sysRoleMenu.setMenu_id(menuId);
                sysRoleMenu.insert();
                //插入父类
                parentMap = this.insertPmenu(parentMap,sysRole.getId(),sysMenu.getParent_id());
            }
        }
        if(StringUtils.isEmpty(sysRole.getRole_symbol())){
            sysRole.setRole_symbol("");
        }
        sysRole.setIs_del("0");
        sysRole.insertOrUpdate();
        return ResultInfo.success();
    }

    private Map<String,String> insertPmenu(Map<String,String> parentMap,String roleId,String parentId){
        if(!"0".equals(parentId)){
            SysMenu pMenu = sysMenuService.getById(parentId);
            if(!parentMap.containsKey(pMenu.getId())){
                SysRoleMenu pRoleMenu = new SysRoleMenu();
                pRoleMenu.setId(IdUtil.simpleUUID());
                pRoleMenu.setRole_id(roleId);
                pRoleMenu.setIs_del("1");
                pRoleMenu.setMenu_id(pMenu.getId());
                pRoleMenu.insert();
                parentMap.put(pMenu.getId(),pMenu.getId());
                this.insertPmenu(parentMap,roleId,pMenu.getParent_id());
            }
        }
        return parentMap;
    }

    public List<SysRole> queryByRoleIds(List<String> roleIds){
        QueryWrapper<SysRole> qw = new QueryWrapper();
        qw.eq("is_del","0");
        qw.in("id",roleIds);
        return this.list(qw);
    }

}
