package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.model_check.AdministrativeUnit;
import com.jsdc.ybpt.model_check.DeptInfo;
import com.jsdc.ybpt.model_check.EmployingInfo;
import com.jsdc.ybpt.service.AdministrativeUnitService;
import com.jsdc.ybpt.service.EmployingInfoService;
import com.jsdc.ybpt.service.SysDictService;
import com.jsdc.ybpt.service.SysUserService;
import com.jsdc.ybpt.util.AjaxResult;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/employing_info")
public class EmployingInfoController {
    @Autowired
    private EmployingInfoService employingInfoService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysDictService sysDictService;

    @Autowired
    private AdministrativeUnitService administrativeUnitService;


    /**
     * 用人单位修改接口
     * Author wzn
     * Date 2022/5/24 10:14
     */
    @PostMapping("/updateEmployingInfo")
    @ResponseBody
    public ResultInfo updateCiviWorkerInfo(@RequestBody EmployingInfo employingInfo) {
        employingInfoService.updateEmployingInfo(employingInfo);
        return ResultInfo.success();
    }


    /**
     * 用人单位列表接口
     * Author wzn
     * Date 2022/5/24 10:47
     */
    @PostMapping("/selectList")
    @ResponseBody
    public ResultInfo selectList(@RequestBody EmployingInfo employingInfo) {
        Page<EmployingInfo> employingInfoPage = employingInfoService.selectList(employingInfo);
        if (null != employingInfoPage.getRecords() && employingInfoPage.getRecords().size() > 0) {
            for (EmployingInfo e : employingInfoPage.getRecords()) {
                SysDict sysDict2 = new SysDict();
                sysDict2.setDict_type("ADMDVS");
                sysDict2.setValue(e.getAdmdvs());
                SysDict sysDict3 = sysDictService.selectByValue(sysDict2);
                if (null != sysDict3) {
                    e.setAdmdvs(sysDict3.getLabel());
                }

                if (!"".equals(e.getEmp_type()) && null != e.getEmp_type()) {
                    SysDict sysDict = new SysDict();
                    sysDict.setDict_type("EMP_TYPE");
                    sysDict.setValue(e.getEmp_type());
                    SysDict sysDict1 = sysDictService.selectByValue(sysDict);
                    if (null != sysDict1) {
                        e.setEmp_type(sysDict1.getLabel());
                    }
                }
                if (!"".equals(e.getParentOrgCode()) && null != e.getParentOrgCode()) {
                    AdministrativeUnit administrativeUnit = administrativeUnitService.selectByEmpNo(e.getParentOrgCode());
                    if (null != administrativeUnit) {
                        e.setParentOrgCode(administrativeUnit.getEmp_name());
                    }
                }

            }
        }
        return ResultInfo.success(employingInfoPage);
    }

    /**
     * 用人单位详情接口
     * Author wzn
     * Date 2022/5/27 10:17
     */
    @PostMapping("/info")
    @ResponseBody
    public ResultInfo info(String id) {
        EmployingInfo employingInfo = employingInfoService.info(id);
        return ResultInfo.success(employingInfo);
    }

    @PostMapping("/selectByEmpCode")
    @ResponseBody
    public ResultInfo selectByEmpCode() {
        EmployingInfo employingInfo = employingInfoService.selectByEmpCode();
        return ResultInfo.success(employingInfo);
    }

    @RequestMapping("/getDepartmentListByEmpCode")
    @ResponseBody
    public ResultInfo getDepartmentListByEmpCode(@RequestParam("empCode") String empCode) {
        List<DeptInfo> departmentListByEmpCode = this.employingInfoService.getDepartmentListByEmpCode(empCode);
        return ResultInfo.success(departmentListByEmpCode);
    }

    @RequestMapping("/saveDepartment")
    @ResponseBody
    public ResultInfo saveDepartment(@RequestBody DeptInfo deptInfo) {
        if (employingInfoService.saveOrUpdateDept(deptInfo)) {
            return ResultInfo.success();
        }
        return ResultInfo.error("保存失败");
    }
}
