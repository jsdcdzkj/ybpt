package com.jsdc.ybpt.service;

import cn.hutool.core.util.IdUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jsdc.ybpt.base.BaseService;
import com.jsdc.ybpt.mapper.FixmedinsBMapper;
import com.jsdc.ybpt.mapper.ReflAppyEvtCMapper;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.ReflAppyEvtC;
import com.jsdc.ybpt.util.FileUtils;
import com.jsdc.ybpt.vo.NatDataDicAByAdmdvsVo;
import com.jsdc.ybpt.vo.ReflAppyEvtCVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class ReflAppyEvtCService extends BaseService<ReflAppyEvtC> {
  @Autowired
  ReflAppyEvtCMapper reflAppyEvtCMapper;
  @Autowired
  private FixmedinsBMapper fixmedinsBMapper;
  @Value("${reflAppyEvtCPrintPath}")
  private String printPath;

  @DS("master")
  public Page<ReflAppyEvtC> getReflAppyEvtC(ReflAppyEvtCVo vo) {
    return reflAppyEvtCMapper.getReflAppyEvtC(new Page(vo.getPageNo(), vo.getPageSize()), vo);
  }

  @DS("master")
  public ResultInfo getMedinsInfoBPage(FixmedinsB fixmedinsB) {
    Page<FixmedinsB> page = new Page<>(fixmedinsB.getPageNo(), fixmedinsB.getPageSize());
    QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
    if (!"".equals(fixmedinsB.getFixmedins_code()) && null != fixmedinsB.getFixmedins_code()) {
      queryWrapper.like("fixmedins_code", fixmedinsB.getFixmedins_code());
    }
    if (!"".equals(fixmedinsB.getFixmedins_type()) && null != fixmedinsB.getFixmedins_type()) {
      queryWrapper.eq("fixmedins_type", fixmedinsB.getFixmedins_type());
    }
    if (!"".equals(fixmedinsB.getFixmedins_name()) && null != fixmedinsB.getFixmedins_name()) {
      queryWrapper.like("fixmedins_name", fixmedinsB.getFixmedins_name());
    }
    Page<FixmedinsB> fixmedinsBPage = fixmedinsBMapper.selectPage(page, queryWrapper);
    fixmedinsBPage.getRecords().forEach(x -> {
      x.setCred_lv_name(StringUtils.equals(x.getFixmedins_type(), "1")?"医疗机构":"药店");
      if (StringUtils.isNotEmpty(x.getCategory())) {
        x.setCategory_name(StringUtils.equals(x.getCategory(), "1") ? "门诊" : "住院");
      }
      if(StringUtils.isNotEmpty(x.getAggrement_lv())){
        x.setAggrement_lv_name(getLevelList().get(x.getAggrement_lv()));
      }
    });
    return ResultInfo.success(fixmedinsBPage);
  }

  /**
   * 机构等级（1一级 2二级 3三级  4A级别 5B级别 6C级别 9未定级）
   */
  public Map<String, String> getLevelList() {
    Map<String, String> map = new HashMap<>();
    map.put("1", "一级");
    map.put("2", "二级");
    map.put("3", "三级");
    map.put("4", "A级别");
    map.put("5", "B级别");
    map.put("6", "C级别");
    map.put("9", "未定级");
    return map;
  }

  @DS("master")
  public ResultInfo getMedinsInfoBOne(FixmedinsB fixmedinsB) {
    QueryWrapper<FixmedinsB> queryWrapper = new QueryWrapper<>();
    if (!"".equals(fixmedinsB.getFixmedins_code()) && null != fixmedinsB.getFixmedins_code()) {
      queryWrapper.like("fixmedins_code", fixmedinsB.getFixmedins_code());
    }
    if (!"".equals(fixmedinsB.getFixmedins_type()) && null != fixmedinsB.getFixmedins_type()) {
      queryWrapper.eq("fixmedins_type", fixmedinsB.getFixmedins_type());
    }
    if (!"".equals(fixmedinsB.getFixmedins_name()) && null != fixmedinsB.getFixmedins_name()) {
      queryWrapper.like("fixmedins_name", fixmedinsB.getFixmedins_name());
    }
    return ResultInfo.success(fixmedinsBMapper.selectOne(queryWrapper));
  }



  @DS("master")
  public ResultInfo subReflAppyEvt(ReflAppyEvtC reflAppyEvtC) {
//    ReflAppyEvtCVo vo = new ReflAppyEvtCVo();
//    vo.setCERTNO(reflAppyEvtC.getCertno());
//    vo.setRCHK_FLAG("0");//未审核
//    Page<ReflAppyEvtC> page = getReflAppyEvtC(vo);
//    if(page.getRecords().size() > 0){
//      return ResultInfo.error("当前申请人有未完成的业务，请等待业务结束");
//    }
    String UUID = IdUtil.simpleUUID();
    reflAppyEvtC.setId(UUID);
    reflAppyEvtC.insert();
    return ResultInfo.success(UUID);
  }

  @DS("master")
  public ResultInfo cancelReflAppyEvtC(ReflAppyEvtC reflAppyEvtC) {
    reflAppyEvtC.setRchk_flag("3");//复核标志
    reflAppyEvtC.setRchk_flag_name("已撤销");
    return ResultInfo.success(reflAppyEvtC.updateById());
  }

  @DS("master")
  public ResultInfo checkReflAppyEvtC(ReflAppyEvtC reflAppyEvtC) {
    if ("审核通过".equals(reflAppyEvtC.getCheckRusult())) {
      reflAppyEvtC.setRchk_flag("1");//复核标志
      reflAppyEvtC.setRchk_flag_name("审核通过");
    } else if ("审核不通过".equals(reflAppyEvtC.getCheckRusult())) {
      reflAppyEvtC.setRchk_flag("2");//复核标志
      reflAppyEvtC.setRchk_flag_name("审核不通过");
    }
    return ResultInfo.success(reflAppyEvtC.updateById());
  }

  @DS("reflowData")
  public ReflAppyEvtC reflAppyEvtC(ReflAppyEvtCVo vo) {
    ReflAppyEvtC reflAppyEvtC = new ReflAppyEvtC();
    if (StringUtils.isNotEmpty(vo.getCERTNO())) {
      reflAppyEvtC = reflAppyEvtCMapper.reflAppyEvtC(vo);
    }
    return reflAppyEvtC;
  }

  @DS("reflowData")
  public ResultInfo getNatDataDicA(ReflAppyEvtCVo vo) {
    List<Map> list = reflAppyEvtCMapper.getNatDataDicA(vo);
    return ResultInfo.success(list);
  }

  @DS("reflowData")
  public List<ReflAppyEvtCVo> getDictListBycode(ReflAppyEvtCVo vo) {
    List<ReflAppyEvtCVo> list = reflAppyEvtCMapper.getDictListBycode(vo);
    return list;
  }



  @DS("reflowData")
  public ResultInfo getNatDataDicAByAdmdvs(ReflAppyEvtCVo vo) {
    List<NatDataDicAByAdmdvsVo> list = reflAppyEvtCMapper.getNatDataDicAByAdmdvs(vo);
    for (int i = 0; i < list.size(); i++) {
//      if (!list.get(i).getValue().equals("100000")) {
        vo.setPRNT_DIC_VAL_CODE(list.get(i).getValue());
        List<NatDataDicAByAdmdvsVo> list_2 = reflAppyEvtCMapper.getNatDataDicAByAdmdvs(vo);
        list.get(i).setChildren(list_2);
        for (int j = 0; j < list_2.size(); j++) {
          vo.setPRNT_DIC_VAL_CODE(list_2.get(j).getValue());
          List<NatDataDicAByAdmdvsVo> list_3 = reflAppyEvtCMapper.getNatDataDicAByAdmdvs(vo);
          if (list_3.size() > 0) {
            list.get(i).getChildren().get(j).setChildren(list_3);
          }
        }
//      }
    }
    return ResultInfo.success(list);
  }

  @DS("reflowData")
  public ResultInfo getPsnInfoB(ReflAppyEvtCVo vo) {
    Map map = new HashMap();
    if (StringUtils.isNotEmpty(vo.getPSN_CERT_TYPE()) || StringUtils.isNotEmpty(vo.getCERTNO()) || StringUtils.isNotEmpty(vo.getHSECFC())) {
      map = reflAppyEvtCMapper.getPsnInfoB(vo);
    }
    return ResultInfo.success(map);
  }

  @DS("reflowData")
  public ResultInfo getMedinsInfoB(ReflAppyEvtCVo vo) {
    Page<Map> page = new Page<>();
    if (StringUtils.isNotEmpty(vo.getMEDINS_CODE()) || StringUtils.isNotEmpty(vo.getMEDINS_NAME())) {
      page = reflAppyEvtCMapper.getMedinsInfoB(new Page(vo.getPageNo(), vo.getPageSize()), vo);
    }
    return ResultInfo.success(page);
  }

  @DS("reflowData")
  public ResultInfo getMdtrtD(ReflAppyEvtCVo vo) {
    Page<Map> page = new Page<>();
    if (StringUtils.isNotEmpty(vo.getFIXMEDINS_CODE())) {
      page = reflAppyEvtCMapper.getMdtrtD(new Page(vo.getPageNo(), vo.getPageSize()), vo);
    }
    return ResultInfo.success(page);
  }

  @DS("reflowData")
  public ResultInfo getDiagList(ReflAppyEvtCVo vo) {
    Page<Map> page = new Page<>();
    if (StringUtils.isNotEmpty(vo.getDIAG_CODE()) || StringUtils.isNotEmpty(vo.getDIAG_NAME())) {
      page = reflAppyEvtCMapper.getDiagList(new Page(vo.getPageNo(), vo.getPageSize()), vo);
    }
    return ResultInfo.success(page);
  }

  public String print(ReflAppyEvtCVo vo) {
    String file_name = "";
//    TemplateExportParams params = new TemplateExportParams("print/徐州市基本医疗保险转诊转院登记备案表.xlsx");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("vo", vo);
//    Workbook workbook = ExcelExportUtil.exportExcel(params, map);
    File savefile = new File(printPath);
    if (!savefile.exists()) {
      savefile.mkdirs();
    }
    FileOutputStream fos = null;
    try {
      file_name = UUID.randomUUID().toString().replaceAll("-", "") + ".xlsx";
      fos = new FileOutputStream(printPath + "/" + file_name);
//      workbook.write(fos);
      fos.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return file_name;
  }

  public ResultInfo download(String fileName, Boolean delete, HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
    try {
      String localPath = printPath;
      // 文件路径
      String downloadPath = localPath + "/" + fileName;
      response.setCharacterEncoding("utf-8");
      response.setContentType("multipart/form-data");
      response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtils.setFileDownloadHeader(request, name));
      FileUtils.writeBytes(downloadPath, response.getOutputStream());
      if (delete) {
        FileUtils.deleteFile(downloadPath);
      }
    }catch (Exception e){
      System.out.println(e);
      return ResultInfo.error("数据异常，请刷新重试！");
    }

    return ResultInfo.success();
  }
}
