package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model_check.DeptInfo;
import com.jsdc.ybpt.service.DeptInfoService;
import com.jsdc.ybpt.vo.ResultInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ：苹果
 * @Description：
 * @Date ：2022/7/9 14:15
 * @Modified By：
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/dept_info")
public class DeptInfoController {

    private final DeptInfoService deptInfoService;

    /**
     * @Description: 条件查询部门
     * @param: [info]
     * @return: com.jsdc.ybpt.vo.ResultInfo
     * @author: 苹果
     * @date: 2022/7/9
     * @time: 14:41
     */
    @PostMapping("/findDeptInfo")
    public ResultInfo findDeptInfo(@RequestBody DeptInfo info) {
        return this.deptInfoService.findDeptInfo(info);
    }
}
