package com.jsdc.ybpt.service;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.DiseaseMutexMapper;
import com.jsdc.ybpt.model.DiseaseMutex;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.exception.CustomException;
import com.jsdc.ybpt.vo.MmmtDisease;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class DiseaseMutexService extends BaseService<DiseaseMutex> {

    @Autowired
    private DiseaseMutexMapper diseaseMutexMapper;

    @Autowired
    private SysUserService sysUserService;

    /**
     * 保存互斥关系
     *
     * @param diseaseMutex
     * @return
     */
    public ResultInfo saveDiseaseMutex(DiseaseMutex diseaseMutex) {

        //唯一性校验
        boolean check = this.checkOnly(diseaseMutex) ;
        if(!check){
            throw new CustomException("该互斥关系已存在,禁止重复添加!") ;
        }

        SysUser user = sysUserService.getUser();
        if(StrUtil.isNotEmpty(diseaseMutex.getId())){
            diseaseMutex.setUpdateTime(DateUtil.formatDateTime(new Date()));
            diseaseMutex.setUpdateUser(user.getId());
            diseaseMutex.updateById();
        }else{
            diseaseMutex.setId(IdUtil.simpleUUID());
            diseaseMutex.setIs_del("0");
            diseaseMutex.setType("1");
            diseaseMutex.setCreateUser(user.getId());
            diseaseMutex.setCreateTime(DateUtil.formatDateTime(new Date()));
            diseaseMutex.insert() ;
        }
        return ResultInfo.success();
    }



    //唯一性校验
    public boolean checkOnly(DiseaseMutex diseaseMutex){
        boolean checkOnly = true ; //没有重复
        QueryWrapper<DiseaseMutex> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("dise_code",diseaseMutex.getDise_code()) ;
        queryWrapper.eq("dise_mutex_code",diseaseMutex.getDise_mutex_code()) ;
        queryWrapper.eq("insured_type",diseaseMutex.getInsured_type()) ;
        queryWrapper.eq("insured_mutex_type",diseaseMutex.getInsured_mutex_type()) ;
        queryWrapper.eq("is_del","0") ;
        List<DiseaseMutex> diseaseMutexList = diseaseMutexMapper.selectList(queryWrapper) ;
        if(CollectionUtils.isNotEmpty(diseaseMutexList)){
            checkOnly = false ;
            if(null!=diseaseMutex.getId()){
                if(diseaseMutex.getId().equals(diseaseMutexList.get(0).getId())){
                    checkOnly = true ;
                }
            }
        }
        return checkOnly ;
    }
    /**
     * 查询病种
     *
     * @param pageNo
     * @param pageSize
     * @param diseCode
     * @param diseName
     * @return
     */
//    @DS("reflowData")
    public ResultInfo selectDisease(Integer pageNo, Integer pageSize, String diseCode, String diseName) {
        Page<MmmtDisease> page = new Page<>(pageNo, pageSize);
        Page<MmmtDisease> pageinfo = diseaseMutexMapper.getMmmtDisease(page, diseCode, diseName);
        return ResultInfo.success(pageinfo);
    }
    
    

}
