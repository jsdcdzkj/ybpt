package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.model.ReflAppyEvtC;
import com.jsdc.ybpt.model.SysDict;
import com.jsdc.ybpt.service.DiagListBService;
import com.jsdc.ybpt.service.OpspDiseListBService;
import com.jsdc.ybpt.service.ReflAppyEvtCService;
import com.jsdc.ybpt.vo.DiagListBVo;
import com.jsdc.ybpt.vo.OpspDiseListBVo;
import com.jsdc.ybpt.vo.ReflAppyEvtCVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 特殊病种
 */
@RestController
@RequestMapping("/diag")
public class DiagListBController {

  @Autowired
  private DiagListBService diagListBService;
  @Autowired
  ReflAppyEvtCService reflAppyEvtCService;
  /**
   *  特殊病种列表
   * @param
   * @return
   */
  @RequestMapping("/diagList")
  public ResultInfo diagList(@RequestBody DiagListBVo diagListBVo){
    Page<DiagListBVo> diagListBVoPage =  diagListBService.diagList(diagListBVo) ;
    Map<String,String> map = new HashMap<>() ;
    ReflAppyEvtCVo vo = new ReflAppyEvtCVo() ;
    vo.setNat_dic_val_code("DISE_LMT_TYPE");
    List<ReflAppyEvtCVo> reflAppyEvtCS = reflAppyEvtCService.getDictListBycode(vo);
    for(ReflAppyEvtCVo s:reflAppyEvtCS){
      map.put(s.getNat_dic_val_code(),s.getNat_dic_val_name()) ;
    }

    if(null != diagListBVoPage.getRecords() && diagListBVoPage.getRecords().size()>0){
        for(DiagListBVo d:diagListBVoPage.getRecords()){
            d.setInsutype("职工基本医疗保险");
            d.setMed_type("特药(省编码)");
            d.setPsn_type("");
            d.setDise_lmt_type(map.get(d.getDise_lmt_type()));
        }
    }
    return ResultInfo.success(diagListBVoPage);
  }


}
