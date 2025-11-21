package com.jsdc.ybpt.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.CivilworkerInfoMapper;
import com.jsdc.ybpt.mapper.CivilworkerVirfyMapper;
import com.jsdc.ybpt.mapper.EmployingInfoMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.CivilworkerVirfy;
import com.jsdc.ybpt.model_check.EmployingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CivilworkerVirfyService extends BaseService<CivilworkerVirfy> {
    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private CivilworkerVirfyMapper civilworkerVirfyMapper ;

    @Autowired
    private EmployingInfoMapper employingInfoMapper ;

    /**
    *公务员申请审核新增
    * Author wzn
    * Date 2022/5/24 9:57
    */
    public void addCiviVirfy(CivilworkerVirfy civilworkerVirfy){
        SysUser sysUser =  sysUserService.getUser();
        civilworkerVirfy.setCreateUser(sysUser.getUsername());
        civilworkerVirfy.setCreateTime(new Date());
        civilworkerVirfy.setStatus("0");
        civilworkerVirfy.insert() ;
    }

    public Page<CivilworkerVirfy> selectList(CivilworkerVirfy civilworkerVirfy){

        Page<CivilworkerVirfy> page = new Page<>(civilworkerVirfy.getPageNo(), civilworkerVirfy.getPageSize());
        SysUser sysUser = sysUserService.getUser() ;
        Page<CivilworkerVirfy> civilworkerVirfyList = civilworkerVirfyMapper.civilworkerVirfyList(page,civilworkerVirfy,sysUser) ;
        return civilworkerVirfyList ;
    }

    public boolean check(String certNo,String type){
        boolean isCheck =false ;
        QueryWrapper<CivilworkerVirfy> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("certno",certNo);
        queryWrapper.eq("verify_type",type);
        queryWrapper.eq("status","0") ;
        List<CivilworkerVirfy> civilworkerVirfies = civilworkerVirfyMapper.selectList(queryWrapper) ;
        if(null != civilworkerVirfies && civilworkerVirfies.size()>0){
            isCheck = true ;
        }
        return isCheck ;
    }

}
