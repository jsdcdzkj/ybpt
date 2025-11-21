package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.CivilworkerInfo;
import com.jsdc.ybpt.model_check.DeptInfo;
import com.jsdc.ybpt.service.CivilworkerInfoService;
import com.jsdc.ybpt.service.DeptService;
import com.jsdc.ybpt.util.StringUtils;
import com.jsdc.ybpt.vo.DepartmentVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {
    @Autowired
    private DeptService deptService;
    @Autowired
    private CivilworkerInfoService civilworkerInfoService;

    @RequestMapping("toDepartment")
    public String department(Model model, @RequestParam(name = "cardId") String cardId) {
        model.addAttribute("decryptedCardId", cardId);
        return "department";
    }

    @PostMapping("save")
    @ResponseBody
    public ResultInfo save(@RequestParam(name="cardId") String cardId, String deptId) {
        if (StringUtils.isEmpty(deptId)) {
            return ResultInfo.error("请选择部门");
        }
        CivilworkerInfo civilworkerInfo = this.civilworkerInfoService.selectQualifiedCivil(cardId);
        if (civilworkerInfo != null) {
            DeptInfo deptInfo = this.deptService.getDeptInfoById(deptId);
            if (deptInfo != null) {
                civilworkerInfo.setDept_id(deptId);
                civilworkerInfo.setDept_name(deptInfo.getDept_name());
                this.civilworkerInfoService.updateById(civilworkerInfo);
                return ResultInfo.success();
            }
        }
        return ResultInfo.error("保存失败");
    }

    @GetMapping("getDepartmentList")
    @ResponseBody
    public ResultInfo getDepartmentList(@RequestParam(name = "cardId") String cardId ) {
        CivilworkerInfo civilworkerInfo = this.civilworkerInfoService.selectQualifiedCivil(cardId);
        if (civilworkerInfo != null) {
                List<DepartmentVo> deptInfoListByCertNo = this.deptService.getDeptInfoListByCertNo(civilworkerInfo.getCertno());
                deptInfoListByCertNo.forEach(departmentVo -> {
                    if (departmentVo.getId().equals(civilworkerInfo.getDept_id())) {
                        departmentVo.setIsCheck("1");
                    }
                });
                return ResultInfo.success(deptInfoListByCertNo);

        } else {
            return ResultInfo.error("错误");
        }
    }
}
