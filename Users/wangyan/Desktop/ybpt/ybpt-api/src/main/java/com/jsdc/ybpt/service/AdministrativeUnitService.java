package com.jsdc.ybpt.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.AdministrativeUnitMapper;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AdministrativeUnitService extends BaseService<AdministrativeUnit> {
    @Autowired
    private SysUserService sysUserService ;

    @Autowired
    private AdministrativeUnitMapper administrativeUnitMapper;

    /**
    *行政单位信息新增
    * Author wzn
    * Date 2022/5/24 9:57
    */
    public void addCiviWorkerInfo(AdministrativeUnit administrativeUnit){
        //唯一性校验
        boolean check = this.checkOnly(administrativeUnit) ;
        if(!check){
            throw new CustomException("单位编码已存在，禁止重复添加") ;
        }
        SysUser sysUser =  sysUserService.getUser();
        administrativeUnit.setCreateUser(sysUser.getUsername());
        administrativeUnit.setCreateTime(new Date());
        administrativeUnit.setIs_del("0");
        administrativeUnit.insert() ;
    }

    /**
    *行政单位信息修改
    * Author wzn
    * Date 2022/5/24 10:10
    */
    public void updateCiviWorkerInfo(AdministrativeUnit administrativeUnit){
        boolean check = this.checkOnly(administrativeUnit) ;
        if(!check){
            throw new CustomException("单位编码已存在，禁止重复添加") ;
        }
        SysUser sysUser =  sysUserService.getUser();
        administrativeUnit.setUpdateUser(sysUser.getUsername());
        administrativeUnit.setUpdateTime(new Date());
        administrativeUnit.updateById() ;
    }



    /**
    *行政单位列表接口
    * Author wzn
    * Date 2022/5/24 10:52
    */
    public Page<AdministrativeUnit> selectList(AdministrativeUnit administrativeUnit){
        Page<AdministrativeUnit> page = new Page<>(administrativeUnit.getPageNo(), administrativeUnit.getPageSize());
        QueryWrapper<AdministrativeUnit> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("is_del","0");
        if(!"".equals(administrativeUnit.getEmp_no()) && null !=administrativeUnit.getEmp_no()){
            queryWrapper.eq("emp_no",administrativeUnit.getEmp_no());
        }
        if(!"".equals(administrativeUnit.getEmp_name()) && null !=administrativeUnit.getEmp_name()){
            queryWrapper.like("emp_name",administrativeUnit.getEmp_name());
        }
        queryWrapper.eq(StringUtils.isNotEmpty(administrativeUnit.getAdmdvs()),"admdvs",administrativeUnit.getAdmdvs());
//        List<AdministrativeUnit> administrativeUnitList = administrativeUnitMapper.selectList(queryWrapper) ;
        Page<AdministrativeUnit> fixmedinsBPage = administrativeUnitMapper.selectPage(page,queryWrapper) ;
        return fixmedinsBPage;
    }

    public List<AdministrativeUnit> selectListAll(){
        QueryWrapper<AdministrativeUnit> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("is_del","0");
       List<AdministrativeUnit> administrativeUnitList = administrativeUnitMapper.selectList(queryWrapper) ;
//        Page<AdministrativeUnit> fixmedinsBPage = administrativeUnitMapper.selectPage(page,queryWrapper) ;
        return administrativeUnitList;
    }

    /**
    *行政单位详情接口
    * Author wzn
    * Date 2022/5/25 16:50
    */
    public AdministrativeUnit info(String id){
        return administrativeUnitMapper.selectById(id) ;
    }


    public boolean checkOnly(AdministrativeUnit administrativeUnit){
        boolean checkOnly = true ; //没有重复
        QueryWrapper<AdministrativeUnit> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("emp_no",administrativeUnit.getEmp_no()) ;
        queryWrapper.eq("is_del","0") ;
        List<AdministrativeUnit> administrativeUnits = administrativeUnitMapper.selectList(queryWrapper) ;
        if(!administrativeUnits.isEmpty()){
            checkOnly = false ;
            if(null!=administrativeUnit.getId()){
                if(administrativeUnit.getId().equals(administrativeUnits.get(0).getId())){
                    checkOnly = true ;
                }
            }
        }
        return checkOnly ;
    }

    public AdministrativeUnit selectByEmpNo(String emp_no){
        QueryWrapper<AdministrativeUnit> queryWrapper = new QueryWrapper<>() ;
        queryWrapper.eq("emp_no",emp_no) ;
        queryWrapper.eq("is_del","0") ;
        return administrativeUnitMapper.selectOne(queryWrapper) ;
    }


}
