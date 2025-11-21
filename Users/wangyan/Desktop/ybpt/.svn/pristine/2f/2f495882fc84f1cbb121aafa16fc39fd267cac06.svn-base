package com.jsdc.ybpt.controller;

import cn.hutool.core.util.ArrayUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.mapper.SysRoleMapper;
import com.jsdc.ybpt.mapper.SysUserRoleMapper;
import com.jsdc.ybpt.model.*;
import com.jsdc.ybpt.service.SysMenuService;
import com.jsdc.ybpt.service.SysRoleMenuService;
import com.jsdc.ybpt.service.SysRoleService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MainController
 * @Description TODO
 * @Author xujian
 * @Date 2022/4/20 17:29
 * @Version 1.0
 */
@RestController
@RequestMapping("/sysRole")
public class SysRoleController {
    @Autowired
    private SysRoleMenuService sysRoleMenuService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleMapper sysRoleMapper;
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    /**
     * 获取角色
     * @param roleName
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/getList")
    private ResultInfo getList(String roleName,Integer pageNo,Integer pageSize){
        SysUser sysUser = sysUserService.getUser() ;



        QueryWrapper qw = new QueryWrapper();
        qw.eq("is_del","0");
        if(StringUtils.hasText(roleName)){
            qw.like("role_name",roleName);
        }

        //账号类型 1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行 7非定点机构 8非定点药店
        if (("1".equals(sysUser.getUser_type()) && !"admin".equals(sysUser.getUsername())) || ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type()))) {
            qw.eq("tissue",sysUser.getOrg_code());
        }

        Page<SysRole> pageinfo = sysRoleService.page(new Page<>(pageNo,pageSize),qw);
        return ResultInfo.success(pageinfo);
    }

    /**
     * 获取所有角色
     * @return
     */
    @PostMapping("/getAllRoles")
    private ResultInfo getAllRoles(){
        SysUser sysUser = sysUserService.getUser() ;

        QueryWrapper qw = new QueryWrapper();
        qw.eq("is_del","0");
        //账号类型 1:行政管理单位 2:医疗机构 3:零售药店 4：用人单位 5：体检机构 6银行 7非定点机构 8非定点药店
        if (("1".equals(sysUser.getUser_type()) && !"admin".equals(sysUser.getUsername())) || ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type()))) {
            qw.eq("tissue",sysUser.getOrg_code());
        }
        List<SysRole> sysRoles = sysRoleService.list(qw);


        if (("1".equals(sysUser.getUser_type()) && !"320399".equals(sysUser.getOrg_code())) || ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type()))) {
           QueryWrapper<SysUserRole> sysUserRoleQueryWrapper = new QueryWrapper<>();
           sysUserRoleQueryWrapper.eq("user_id",sysUser.getId());
            List<SysUserRole> sysUserRoleList = sysUserRoleMapper.selectList(sysUserRoleQueryWrapper);
            for (SysUserRole sysUserRole : sysUserRoleList) {

                SysRole sysRole = sysRoleMapper.selectById(sysUserRole.getRole_id());
                sysRoles.add(sysRole);
            }
        }

        //市直单位单独增加  医疗机构f86fb69655f149219b6cb8b5bbf69d03    药店fb2a7a2d63e0453ab1e23e01e1822e39  这两个角色
        if("1".equals(sysUser.getUser_type())){
            boolean containsJohn = sysRoles.stream()
                    .anyMatch(sysRole -> "f86fb69655f149219b6cb8b5bbf69d03".equals(sysRole.getId()));
            if (!containsJohn) {
                sysRoles.add(sysRoleMapper.selectById("f86fb69655f149219b6cb8b5bbf69d03")) ;
            }

            boolean containsJohn2 = sysRoles.stream()
                    .anyMatch(sysRole -> "fb2a7a2d63e0453ab1e23e01e1822e39".equals(sysRole.getId()));
            if (!containsJohn2) {
                sysRoles.add(sysRoleMapper.selectById("fb2a7a2d63e0453ab1e23e01e1822e39")) ;
            }
        }

        return ResultInfo.success(sysRoles);
    }

    /**
     * 根据角色ID获取对应的菜单
     * @param roleId
     * @return
     */
    @PostMapping("/getMenusByRoleId")
    public ResultInfo getMenusByRoleId(String roleId) {
        QueryWrapper qw = new QueryWrapper();
        qw.eq("role_id", roleId);
        qw.eq("is_del", "0");
        List<SysRoleMenu> sysRoleMenus = sysRoleMenuService.list(qw);
        List<String> menuIds = new ArrayList<>();
        sysRoleMenus.forEach(x -> menuIds.add(x.getMenu_id()));
        return ResultInfo.success(menuIds);
    }

    /**
     * 保存角色
     *
     * @return
     */
    @PostMapping("/saveRole")
    public ResultInfo saveMenu(@RequestBody SysRole sysRole) {
        return sysRoleService.saveRole(sysRole);
    }
    /**
     * 删除角色
     * @return
     */
    @PostMapping("/delRole")
    public ResultInfo delRole(String roleId) {
        SysRole sysRole = sysRoleService.getById(roleId);
        if(sysRole != null){
            sysRole.setIs_del("1");
            sysRole.updateById();
        }else {
            return ResultInfo.error("无此角色！");
        }
        return ResultInfo.success();
    }
}
