package com.jsdc.ybpt.controller;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model.SysUserRole;
import com.jsdc.ybpt.service.FixmedinsBService;
import com.jsdc.ybpt.service.SysRoleService;
import com.jsdc.ybpt.service.SysUserRoleService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;


/**
 * @ClassName SysUserController
 * @Description TODO
 * @Author xujian
 * @Date 2022/3/28 13:01
 * @Version 1.0
 */
@RestController
@RequestMapping("/sysuser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysUserRoleService sysUserRoleService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private FixmedinsBService fixmedinsBService;

    /**
     * 列表分页查询
     * @param username
     * @param pageNo
     * @param pageSize
     * @return
     */
    @PostMapping("/getList")
    private ResultInfo getList(String username,String idNumber,String user_type,String org_code,Integer pageNo,Integer pageSize){
        SysUser sysUser = sysUserService.getUser();
        SysUser user = new SysUser();

        if(StringUtils.hasText(username)){
            user.setUsername(username);
        }
        if(StringUtils.hasText(user_type)){
            user.setUser_type(user_type);
        }
        if(StringUtils.hasText(idNumber)){
            user.setIdNumber(idNumber);
        }


        if (("1".equals(sysUser.getUser_type()) && !"320399".equals(sysUser.getOrg_code())) || ("2".equals(sysUser.getUser_type()) || "3".equals(sysUser.getUser_type()))) {
            user.setOrg_code(sysUser.getOrg_code());
        }else{
            user.setOrg_code(org_code);

        }
        Page<SysUser> pageinfo = sysUserService.selectByPage(new Page<>(pageNo, pageSize), user);
        pageinfo.getRecords().forEach(x->x.setPermissions(sysUserRoleService.getRoleNameByUser(x.getId())));
        return ResultInfo.success(pageinfo);
    }

    /**
     * 根据用户查询角色
     * @param userId
     * @return
     */
    @PostMapping("/getRolesByUserId")
    private ResultInfo getRolesByUserId(String userId){
        List<SysUserRole> sysUserRoles = sysUserRoleService.list(new QueryWrapper<SysUserRole>().eq("user_id",userId));
        List<String> roleIds = new ArrayList<>();
        sysUserRoles.forEach(x->roleIds.add(x.getRole_id()));
        return ResultInfo.success(roleIds);
    }

    /**
     * 保存
     * @param sysUser
     * @return
     */
    @PostMapping("/saveUser")
    private ResultInfo saveUser(@RequestBody SysUser sysUser){
        return sysUserService.saveUser(sysUser);
    }


    @PostMapping("/updateCert")
    private ResultInfo updateCert(@RequestBody SysUser sysUser){
        return sysUserService.updateCert(sysUser);
    }

    @PostMapping("/isCert")
    private ResultInfo isCert(){
        boolean isId = false ;
        SysUser sysUser = sysUserService.getUser() ;
        if(!StringUtils.isEmpty(sysUser.getIdNumber())){
            isId = true ;
        }
        return ResultInfo.success(isId);
    }

    /**
     * 修改密码
     * @param sysUser
     * @return
     */
    @PostMapping("/pass")
    private ResultInfo pass(@RequestBody SysUser sysUser){
        return sysUserService.pass(sysUser);
    }
    /**
     * 删除用户
     * @param userId
     * @return
     */
    @PostMapping("/delUser")
    private ResultInfo delUser(String userId){
        SysUser sysUser = sysUserService.getById(userId);
        if(sysUser != null){
            sysUser.setIs_del("1");
            sysUser.setSynchronousState("0");
            sysUser.updateById();
        }else {
            return ResultInfo.error("无此用户数据！");
        }
        return ResultInfo.success();
    }

    @RequestMapping({"/getAuth"})
    public ResultInfo getDetail(String mac, String cdkey) {
        return this.sysUserService.getDetail(mac, cdkey);
    }

    /**
    *授权激活
    * Author wzn
    * Date 2022/6/25 14:57
    */
    @RequestMapping({"/activeCode"})
    public ResultInfo activeCode(String mac, String cdkey) {
        return this.sysUserService.activeCode(mac, cdkey);
    }


    /**
    *批量导入用人单位
    * Author wzn
    * Date 2022/6/29 10:42
    */
    @PostMapping("bulkRegistration")
    public ResultInfo bulkRegistration() {
        sysUserService.bulkRegistration();
        return ResultInfo.success();
    }


    @PostMapping("getEmplof")
    public ResultInfo getEmplof() {
        sysUserService.getEmplof();
        return ResultInfo.success();
    }


    /**
    *用户同步-医保统一门户
    * Author wzn
    * Date 2023/3/21 9:43
    */
    @PostMapping("/synchronization")
    public ResultInfo synchronization() {

        return sysUserService.synchronization();
    }
    /**
     * 验证身份证合法性
     * Author xj
     * Date 2023/3/21 9:43
     */
//    @PostMapping("/validCarNo")
//    public ResultInfo validCarNo(){
//        return sysUserService.validCarNo();
//    }

    @PostMapping("/authOrgUser")
    public ResultInfo authOrgUser(@RequestBody SysUser user)  {
        String loginName = user.getUsername();
        String password = user.getPass();
        if(StrUtil.isEmpty(loginName) || StrUtil.isEmpty(password)){
            return ResultInfo.error("账号或密码不得为空！");
        }
        String md5pass = SaSecureUtil.md5(password);
        QueryWrapper<SysUser> qw = new QueryWrapper<SysUser>();
        qw.eq("username",loginName);
        qw.eq("pass",md5pass);
        qw.eq("is_del","0");
        List<String> types = new ArrayList<>();
        types.add("2");
        types.add("3");
        qw.in("user_type",types);
        SysUser sysUser = sysUserService.getOne(qw);
        if(sysUser != null){
            return ResultInfo.success(sysUser.getOrg_code());
        }else {
            return ResultInfo.error("账号或密码错误！");
        }
    }

}
