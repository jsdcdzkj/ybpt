package com.jsdc.ybpt.controller;

import com.jsdc.ybpt.service.MedicalItemService;
import com.jsdc.ybpt.vo.MedicalItemVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 体检项信息
 */
@RestController
@RequestMapping("/MedicalItem")
public class MedicalItemController {
  @Autowired
  MedicalItemService service;

  @RequestMapping("/getList")
  public ResultInfo getList(MedicalItemVo vo) {
    return ResultInfo.success(service.getList(vo));
  }

  @RequestMapping("/getListAll")
  public ResultInfo getListAll(MedicalItemVo vo) {
    return ResultInfo.success(service.getListAll(vo));
  }

  /**
   *  需要拼接
   * @param vo
   * @return
   */
  @RequestMapping("/getListUnion")
  public ResultInfo getListUnion(MedicalItemVo vo){
    return ResultInfo.success(service.getListUnion(vo));
  }

  @RequestMapping("/toEdit")
  public ResultInfo toEdit(MedicalItemVo vo) {
    return ResultInfo.success(service.toEdit(vo));
  }

  @RequestMapping("/edit")
  public ResultInfo edit(MedicalItemVo vo) {
    return service.edit(vo);
  }
}
