package com.jsdc.ybpt.service;


import cn.hutool.core.util.IdUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.SysMenuMapper;
import com.jsdc.ybpt.model.SysMenu;
import com.jsdc.ybpt.model.SysRole;
import com.jsdc.ybpt.model.SysRoleMenu;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class SysMenuService extends BaseService<SysMenu> {
    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysRoleService sysRoleService;

    @Autowired
    private SysRoleMenuService sysRoleMenuService;

    public ResultInfo getMenus(){
        SysUser sysUser = sysUserService.getUser() ;

        if(!"320399".equals(sysUser.getOrg_code())){
            return getMenusByUser2();
        }else {
            QueryWrapper qw = new QueryWrapper();


            qw.eq("is_del","0");
            qw.eq("parent_id","0");
            qw.orderByAsc("sort");
            List<SysMenu> list = this.list(qw);
            List<JSONObject> result = genMenuData(list);
            return ResultInfo.success(result);
        }




    }


    private List<JSONObject> genMenuData(List<SysMenu> menus){
        List<JSONObject> result = new ArrayList<>();
        for (SysMenu m:menus){
            JSONObject json = new JSONObject();
            json.put("id",m.getId());
            json.put("name",m.getRouter_name());
            json.put("path",m.getRouter_url());
            json.put("parent_id",m.getParent_id());
            json.put("redirect",m.getRedirect_type());
            json.put("component",m.getVue_url());
            json.put("label",m.getTitle());
            json.put("sort",m.getSort());
            json.put("is_show",m.getIs_show());
            JSONObject meta = new JSONObject();
            meta.put("title",m.getTitle());
            meta.put("icon",m.getIcon());
            json.put("meta",meta);
            //查询子节点
            QueryWrapper qw = new QueryWrapper();
            qw.eq("is_del","0");
            qw.eq("parent_id",m.getId());
            qw.orderByAsc("sort");
            List<SysMenu> children = this.list(qw);
            if(children != null && children.size()>0){
                json.put("children",genMenuData(children));
            }
            result.add(json);
        }
        return result;
    }

    /**
     * 查询用户的菜单
     * @return
     */
    public ResultInfo getMenusByUser(){
        String userId = sysUserService.getUser().getId();
        List<SysMenu> list = sysMenuMapper.getMenusByUser(userId,"0");
        List<JSONObject> result = genMenuDataByUser(userId,list);
        return ResultInfo.success(result);
    }

    public ResultInfo getMenusByUser2(){
        String userId = sysUserService.getUser().getId();
        List<SysMenu> list = sysMenuMapper.getMenusByUser(userId,"0");
        List<JSONObject> result = genMenuDataByUser2(userId,list);
        return ResultInfo.success(result);
    }


    private List<JSONObject> genMenuDataByUser(String userId,List<SysMenu> menus){
        List<JSONObject> result = new ArrayList<>();
        for (SysMenu m:menus){
            JSONObject json = new JSONObject();
            json.put("name",m.getRouter_name());
            json.put("path",m.getRouter_url());
            json.put("redirect",m.getRedirect_type());
            json.put("component",m.getVue_url());
            if(!"首页".equals(m.getTitle()) && m.getVue_url().indexOf("Layout")>=0){
                json.put("alwaysShow",true);
            }
//            json.put("hidden",m.getIs_show());
            JSONObject meta = new JSONObject();
            meta.put("title",m.getTitle());
            meta.put("icon",m.getIcon());
            if("index".equals(m.getRouter_name())){
                meta.put("affix",true);
            }
            json.put("meta",meta);
            //查询子节点
            List<SysMenu> children = sysMenuMapper.getMenusByUser(userId,m.getId());
            if(children != null && children.size()>0){
                json.put("children",genMenuDataByUser(userId,children));
            }
            result.add(json);
        }
        return result;
    }


    private List<JSONObject> genMenuDataByUser2(String userId,List<SysMenu> menus){
        List<JSONObject> result = new ArrayList<>();
        for (SysMenu m:menus){
            JSONObject json = new JSONObject();
            json.put("id",m.getId());
            json.put("name",m.getRouter_name());
            json.put("path",m.getRouter_url());
            json.put("parent_id",m.getParent_id());
            json.put("redirect",m.getRedirect_type());
            json.put("component",m.getVue_url());
            json.put("label",m.getTitle());
            json.put("sort",m.getSort());
            json.put("is_show",m.getIs_show());
            JSONObject meta = new JSONObject();
            meta.put("title",m.getTitle());
            meta.put("icon",m.getIcon());
            json.put("meta",meta);
            //查询子节点
            List<SysMenu> children = sysMenuMapper.getMenusByUser(userId,m.getId());
            if(children != null && children.size()>0){
                json.put("children",genMenuDataByUser2(userId,children));
            }
            result.add(json);
        }
        return result;
    }
    /**
     * 保存菜单
     * @param sysMenu
     * @return
     */
    public ResultInfo saveMenu(SysMenu sysMenu){
        SysUser currUser = sysUserService.getUser();
        sysMenu.setIs_del("0");
        if(StringUtils.hasText(sysMenu.getId())){
            //修改
            sysMenu.setUpdateUser(currUser.getId());
            sysMenu.setUpdateTime(new Date());
        }else{
            //新增
            sysMenu.setId(IdUtil.simpleUUID());
            sysMenu.setCreateUser(currUser.getId());
            sysMenu.setCreateTime(new Date());
        }
        sysMenu.insertOrUpdate();
        return ResultInfo.success();
    }
}
