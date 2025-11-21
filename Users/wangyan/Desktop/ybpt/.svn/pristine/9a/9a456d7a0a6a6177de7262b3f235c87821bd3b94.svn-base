package com.jsdc.ybpt.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jsdc.ybpt.model.SysMenu;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.service.SysMenuService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * @ClassName MainController
 * @Description TODO
 * @Author xujian
 * @Date 2022/4/20 17:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    /**
     * 查询菜单
     *
     * @return
     */
    @RequestMapping("/getMenus")
    public ResultInfo getMenus() {
        return sysMenuService.getMenus();
    }

    /**
     * 查询菜单
     *
     * @return
     */
    @RequestMapping("/getLayoutMenus")
    public ResultInfo getLayoutMenus() {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("is_del", "0");
        qw.like("vue_url", "Layout");
        List<SysMenu> sysMenus = sysMenuService.list(qw);
        return ResultInfo.success(sysMenus);
    }

    /**
     * 查询菜单
     *
     * @return
     */
    @RequestMapping("/getMenusByUser")
    public ResultInfo getMenusByUser() {

        return sysMenuService.getMenusByUser();
    }

    /**
     * 保存菜单
     *
     * @return
     */
    @RequestMapping("/saveMenu")
    public ResultInfo saveMenu(@RequestBody SysMenu sysMenu) {
        return sysMenuService.saveMenu(sysMenu);
    }

    /**
     * 删除菜单
     *
     * @return
     */
    @RequestMapping("/delMenu")
    public ResultInfo delMenu(String menuId) {
        SysMenu sysMenu = sysMenuService.getById(menuId);
        if (sysMenu != null) {
            //判断是不是有子类 子类无法删除
            QueryWrapper qw = new QueryWrapper();
            qw.eq("is_del", "0");
            qw.eq("parent_id", sysMenu.getId());
            List<SysMenu> children = sysMenuService.list(qw);
            if (children != null && children.size() > 0) {
                return ResultInfo.error("此菜单存在子级菜单，无法删除！");
            }
            sysMenu.setIs_del("1");
            sysMenu.updateById();
        } else {
            return ResultInfo.error("无此菜单");
        }
        return ResultInfo.success();
    }

}
