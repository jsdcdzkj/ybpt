package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.service.OrganizationInfoService;
import com.jsdc.ybpt.vo.OrganizationInfoVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 体检机构信息
 */
@RestController
@RequestMapping("/OrganizationInfo")
public class OrganizationInfoController {
  @Autowired
  OrganizationInfoService service;

  @RequestMapping("/getList")
  public ResultInfo getList(OrganizationInfoVo vo) {
    return ResultInfo.success(service.getList(vo));
  }

  @RequestMapping("/getListAll")
  public ResultInfo getListAll(OrganizationInfoVo vo) {
    return ResultInfo.success(service.getListAll(vo));
  }


  @RequestMapping("/getAll")
  public ResultInfo getAll(OrganizationInfoVo vo) {
    return ResultInfo.success(service.getAll(vo));
  }


  @RequestMapping("/toEdit")
  public ResultInfo toEdit(OrganizationInfoVo vo) {
    return ResultInfo.success(service.toEdit(vo));
  }

  @RequestMapping("/edit")
  public ResultInfo edit(OrganizationInfoVo vo) {
    return service.edit(vo);
  }
}
