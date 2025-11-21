package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.service.AutonomousMedicalService;
import com.jsdc.ybpt.vo.AutonomousMedicalVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 自主体检申请
 */
@RestController
@RequestMapping("/AutonomousMedical")
public class AutonomousMedicalController {
  @Autowired
  AutonomousMedicalService service;

  @RequestMapping("/getList")
  public ResultInfo getList(AutonomousMedicalVo vo) {
    return ResultInfo.success(service.getList(vo));
  }

  @RequestMapping("/toEdit")
  public ResultInfo toEdit(AutonomousMedicalVo vo) {
    return ResultInfo.success(service.toEdit(vo));
  }

  @RequestMapping("/edit")
  public ResultInfo edit(AutonomousMedicalVo vo) {
    return service.edit(vo);
  }
}
