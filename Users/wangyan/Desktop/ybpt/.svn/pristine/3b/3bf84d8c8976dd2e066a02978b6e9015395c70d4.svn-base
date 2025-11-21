package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.service.MedinsInfoBService;
import com.jsdc.ybpt.service.PharInfoBService;
import com.jsdc.ybpt.vo.MedinsInfoBVo;
import com.jsdc.ybpt.vo.PharInfoBVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 医疗机构信息
 */
@RestController
@RequestMapping("/pharInfo")
public class PharInfoBController {

  @Autowired
  private PharInfoBService pharInfoBService;
  /**
   *  获取证件类型
   * @param
   * @return
   */
  @RequestMapping("/selectPharInfo")
  public ResultInfo medinsInfoBVoPage(@RequestBody PharInfoBVo pharInfoBVo){
    Page<PharInfoBVo> pharInfoBVoPage =  pharInfoBService.selectPharInfo(pharInfoBVo) ;
    return ResultInfo.success(pharInfoBVoPage);
  }


}
