package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.service.MedinsInfoBService;
import com.jsdc.ybpt.service.ReflAppyEvtCService;
import com.jsdc.ybpt.vo.MedinsInfoBVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 医疗机构信息
 */
@RestController
@RequestMapping("/medinsInfo")
public class MedinsInfoBController {

  @Autowired
  private MedinsInfoBService medinsInfoBService;
  /**
   *  获取医疗机构信息
   * @param
   * @return
   */
  @RequestMapping("/medinsInfoBVoPage")
  public ResultInfo medinsInfoBVoPage(@RequestBody MedinsInfoBVo medinsInfoBVo){
    Page<MedinsInfoBVo> medinsInfoBVoPage =  medinsInfoBService.medinsInfoBVoPage(medinsInfoBVo) ;
    return ResultInfo.success(medinsInfoBVoPage);
  }

  @RequestMapping("/selectMedinsInfoType")
  public ResultInfo selectMedinsInfoType(@RequestBody MedinsInfoBVo medinsInfoBVo){
    Page<MedinsInfoBVo> medinsInfoBVoPage =  medinsInfoBService.selectMedinsInfoType(medinsInfoBVo) ;
    return ResultInfo.success(medinsInfoBVoPage);
  }



}
