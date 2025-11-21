package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.service.PhysExamConfigService;
import com.jsdc.ybpt.vo.PhysExamConfigVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 医保体检规则设置
 */
@RestController
@RequestMapping("/PhysExamConfig")
public class PhysExamConfigController {
  @Autowired
  PhysExamConfigService service;

  @RequestMapping("/getList")
  public ResultInfo getList(PhysExamConfigVo vo) {
    return ResultInfo.success(service.getList(vo));
  }

  @RequestMapping("/toEdit")
  public ResultInfo toEdit(PhysExamConfigVo vo) {
    return ResultInfo.success(service.toEdit(vo));
  }

  @RequestMapping("/edit")
  public ResultInfo edit(PhysExamConfigVo vo) {
    return service.edit(vo);
  }
}
