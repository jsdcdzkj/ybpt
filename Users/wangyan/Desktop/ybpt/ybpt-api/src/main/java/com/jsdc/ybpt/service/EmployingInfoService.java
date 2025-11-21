package com.jsdc.ybpt.service;


import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.CivilworkerInfoMapper;
import com.jsdc.ybpt.mapper.DeptInfoMapper;
import com.jsdc.ybpt.mapper.EmployingInfoMapper;
import com.jsdc.ybpt.model.SysUser;
import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.DeptInfo;
import com.jsdc.ybpt.model_check.EmployingInfo;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.util.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class EmployingInfoService extends BaseService<EmployingInfo> {
    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private EmployingInfoMapper employingInfoMapper;

    @Autowired
    private CivilworkerInfoMapper civilworkerInfoMapper;

    @Autowired
    private DeptInfoMapper deptInfoMapper;


    /**
     * 用人单位信息新增
     * Author wzn
     * Date 2022/5/24 9:57
     */
    public void addEmployingInfo(EmployingInfo employingInfo) {
        employingInfo.setCreateUser("wzn");
        employingInfo.setCreateTime(new Date());
        employingInfo.setIs_del("0");
        employingInfo.insert();
    }

    /**
     * 用人单位信息修改
     * Author wzn
     * Date 2022/5/24 10:10
     */
    public void updateEmployingInfo(EmployingInfo employingInfo) {
        SysUser sysUser = sysUserService.getUser();
        employingInfo.setUpdateUser(sysUser.getUsername());
        employingInfo.setUpdateTime(new Date());
        if (StringUtils.isEmpty(employingInfo.getParentOrgCode())){
            employingInfo.setParentOrgCode("");
        }
        employingInfo.updateById();
        CivilworkerInfo info = new CivilworkerInfo();
        info.setAdministrative_unit(employingInfo.getParentOrgCode());
        LambdaQueryWrapper<CivilworkerInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(CivilworkerInfo::getEmp_code, employingInfo.getEmp_no());
        this.civilworkerInfoMapper.update(info, wrapper);
    }


    /**
     * 用人单位列表接口
     * Author wzn
     * Date 2022/5/25 16:03
     */
    public Page<EmployingInfo> selectList(EmployingInfo employingInfo) {
        Page<EmployingInfo> page = new Page<>(employingInfo.getPageNo(), employingInfo.getPageSize());
        QueryWrapper<EmployingInfo> queryWrapper = new QueryWrapper<>();
        if (!"".equals(employingInfo.getEmp_name()) && null != employingInfo.getEmp_name()) {
            queryWrapper.like("emp_name", employingInfo.getEmp_name());
        }
        if (!"".equals(employingInfo.getEmp_no()) && null != employingInfo.getEmp_no()) {
            queryWrapper.like("emp_no", employingInfo.getEmp_no());
        }

        if (!"".equals(employingInfo.getAdmdvs()) && null != employingInfo.getAdmdvs()) {
            queryWrapper.eq("admdvs", employingInfo.getAdmdvs());
        }
        Page<EmployingInfo> employingInfoPage = employingInfoMapper.selectPage(page, queryWrapper);
        return employingInfoPage;
    }

    /**
     * 用人单位详情接口
     * Author wzn
     * Date 2022/5/27 10:17
     */
    public EmployingInfo info(String id) {
        return employingInfoMapper.selectById(id);
    }


    public EmployingInfo selectByEmpCode() {
        SysUser sysUser = sysUserService.getUser();
        QueryWrapper<EmployingInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("emp_no", sysUser.getOrg_code());
        return employingInfoMapper.selectOne(queryWrapper);
    }

    public List<EmployingInfo> getAllEmploying() {
        QueryWrapper<EmployingInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("emp_name");
        return employingInfoMapper.selectList(queryWrapper);
    }

    public List<EmployingInfo> getAllEmploying2() {
        return employingInfoMapper.yrdwList();
    }

    @DS("reflowData")
    public List<EmployingInfo> otherSelect() {
        return employingInfoMapper.otherSelect();
    }

    public List<EmployingInfo> getEmployingInfoListCheckedByOrg(String medicalInsuranceNum) {
        List<EmployingInfo> employingInfoList = this.employingInfoMapper.getEmployingInfoListCheckedByOrg(medicalInsuranceNum);
        return employingInfoList;
    }

    public List<DeptInfo> getDepartmentListByEmpCode(String empCode) {
        List<DeptInfo> departmentListByEmpCode = this.employingInfoMapper.getDepartmentListByEmpCode(empCode);
        return departmentListByEmpCode;
    }

    public Boolean saveOrUpdateDept(DeptInfo deptInfo) {
        // 对于有不同id的部门，如果编号或名称相同则不允许插入或修改

        // 修改
        if (!StringUtils.isEmpty(deptInfo.getId())) {
            LambdaQueryWrapper<DeptInfo> deptInfoWrapper = new LambdaQueryWrapper<>();
            deptInfoWrapper.eq(DeptInfo::getEmp_code, deptInfo.getEmp_code());
            deptInfoWrapper.eq(DeptInfo::getIs_del, "0");
            deptInfoWrapper.ne(DeptInfo::getId, deptInfo.getId()).and(wrapper -> {
                wrapper.eq(DeptInfo::getDept_no, deptInfo.getDept_no()).or().eq(DeptInfo::getDept_name, deptInfo.getDept_name());
            });
            Long aLong = this.deptInfoMapper.selectCount(deptInfoWrapper);
            if (aLong > 0) {
                throw new CustomException("存在重复的部门名称或编号");
            } else {
                return deptInfo.updateById();
            }
        } else {
            // 插入
            LambdaQueryWrapper<DeptInfo> deptInfoWrapper = new LambdaQueryWrapper<>();
            deptInfoWrapper.eq(DeptInfo::getEmp_code, deptInfo.getEmp_code());
            deptInfoWrapper.eq(DeptInfo::getIs_del, "0").and(wrapper -> {
                wrapper.eq(DeptInfo::getDept_no, deptInfo.getDept_no()).or().eq(DeptInfo::getDept_name, deptInfo.getDept_name());
            });

            Long aLong = this.deptInfoMapper.selectCount(deptInfoWrapper);
            if (aLong > 0) {
                throw new CustomException("请勿新增重复的部门名称或编号");
            } else {
                return deptInfo.insert();
            }
        }
    }
}
