package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.model.MedinsDeptB;
import com.jsdc.ybpt.service.MedinsDeptBService;
import com.jsdc.ybpt.vo.MedinsDeptBVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 可科室管理
 */
@RestController
@RequestMapping("/medinsDeptB")
public class MedinsDeptBController {
  @Autowired
  private MedinsDeptBService medinsDeptBService;

  @RequestMapping("/getList")
  public ResultInfo getList(MedinsDeptBVo vo) {
    return ResultInfo.success(medinsDeptBService.getList(vo));
  }

  @RequestMapping("/getListAll")
  public ResultInfo getListAll(MedinsDeptBVo vo) {
    return ResultInfo.success(medinsDeptBService.getListAll(vo));
  }

  @RequestMapping("/toEdit")
  public ResultInfo toEdit(MedinsDeptBVo vo) {
    return ResultInfo.success(medinsDeptBService.toEdit(vo));
  }

  @RequestMapping("/edit")
  public ResultInfo edit(MedinsDeptB medinsDeptB) {
    return medinsDeptBService.edit(medinsDeptB);
  }
}
