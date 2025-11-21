package com.jsdc.ybpt.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.UnfixedMechanismMapper;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model.UnfixedMechanism;
import com.jsdc.ybpt.util.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UnfixedMechanismService extends BaseService<UnfixedMechanism> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private UnfixedMechanismMapper unfixedMechanismMapper;

    @Autowired
    private FixmedinsBService fixmedinsBService ;

    /**
    *非定点机构新增
    * Author wzn
    * Date 2023/4/10 10:07
    */
    public void addUnfixedMechanism(UnfixedMechanism unfixedMechanism) {
        //唯一性校验
        boolean check = this.checkOnly(unfixedMechanism) ;
        if(!check){
            throw new CustomException("机构编码已存在,禁止重复添加") ;
        }

        //校验是否唯一
        FixmedinsB fixmedinsB = new FixmedinsB() ;
        fixmedinsB.setFixmedins_code(unfixedMechanism.getFixmedins_code());
        Boolean isOnly = fixmedinsBService.checkOnly(fixmedinsB) ;
        if(!isOnly){
            throw new CustomException("该机构已存在,禁止重复添加！") ;
        }
        SysUser sysUser = sysUserService.getUser();
        unfixedMechanism.setCreateUser(sysUser.getUsername());
        unfixedMechanism.setCreateTime(new Date());
        unfixedMechanism.setIs_del("0");
        unfixedMechanism.insert();
    }

    /**
     * 银行修改
     * Author wzn
     * Date 2022/7/4 11:44
     */
    public void updateUnfixedMechanism(UnfixedMechanism unfixedMechanism) {
        boolean check = this.checkOnly(unfixedMechanism) ;
        if(!check){
            throw new CustomException("机构编码已存在,禁止重复添加") ;
        }
        SysUser sysUser = sysUserService.getUser();
        unfixedMechanism.setUpdateUser(sysUser.getUsername());
        unfixedMechanism.setUpdateTime(new Date());
        unfixedMechanism.updateById();
    }


    /**
    *非定点机构列表
    * Author wzn
    * Date 2023/4/10 10:09
    */
    public Page<UnfixedMechanism> selectList(UnfixedMechanism unfixedMechanism) {
        Page<UnfixedMechanism> page = new Page<>(unfixedMechanism.getPageNo(), unfixedMechanism.getPageSize());
        QueryWrapper<UnfixedMechanism> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_del", "0");
        if (!"".equals(unfixedMechanism.getFixmedins_name()) && null != unfixedMechanism.getFixmedins_name()) {
            queryWrapper.like("fixmedins_name", unfixedMechanism.getFixmedins_name());
        }
        if (!"".equals(unfixedMechanism.getFixmedins_code()) && null != unfixedMechanism.getFixmedins_code()) {
            queryWrapper.eq("fixmedins_code", unfixedMechanism.getFixmedins_code());
        }
        if (!"".equals(unfixedMechanism.getFixmedins_type()) && null != unfixedMechanism.getFixmedins_type()) {
            queryWrapper.eq("fixmedins_type", unfixedMechanism.getFixmedins_type());
        }
        Page<UnfixedMechanism> unfixedMechanismPage = unfixedMechanismMapper.selectPage(page, queryWrapper);
        return unfixedMechanismPage;
    }



    /**
    *非定点详情接口
    * Author wzn
    * Date 2023/4/10 10:10
    */
    public UnfixedMechanism info(String id) {
        return unfixedMechanismMapper.selectById(id);
    }


    /**
    *非定点机构唯一编码校验
    * Author wzn
    * Date 2023/4/10 10:06
    */
    public boolean checkOnly(UnfixedMechanism unfixedMechanism){
        boolean checkOnly = true ; //没有重复
        QueryWrapper<UnfixedMechanism> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("fixmedins_code",unfixedMechanism.getFixmedins_code()) ;
        queryWrapper.eq("is_del","0") ;
        List<UnfixedMechanism> unfixedMechanismList = unfixedMechanismMapper.selectList(queryWrapper) ;
        if(!unfixedMechanismList.isEmpty()){
            checkOnly = false ;
            if(null!=unfixedMechanism.getId()){
                if(unfixedMechanism.getId().equals(unfixedMechanismList.get(0).getId())){
                    checkOnly = true ;
                }
            }
        }
        return checkOnly ;
    }





}
