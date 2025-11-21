package com.jsdc.ybpt.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.jsdc.ybpt.mapper.FileInfoMapper;
import com.jsdc.ybpt.model.FileInfo;
import com.jsdc.ybpt.model.FixmedinsB;
import com.jsdc.ybpt.model.ReflAppyEvtC;
import com.jsdc.ybpt.service.ReflAppyEvtCService;
import com.jsdc.ybpt.service.SysFileService;
import com.jsdc.ybpt.util.FastDfs.FastDfsParams;
import com.jsdc.ybpt.util.FastDfs.FastDfsUtil;
import com.jsdc.ybpt.vo.ReflAppyEvtCVo;
import com.jsdc.ybpt.vo.ResultInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 转诊转院
 */
@RestController
@RequestMapping("/reflAppyEvtC")
public class ReflAppyEvtCController {

  @Autowired
  ReflAppyEvtCService reflAppyEvtCService;
  @Autowired
  private SysFileService sysFileService;
  private FileInfoMapper fileInfoMapper;
  @Autowired
  private FastDfsUtil fastDfsUtil;

  /**
   * 获取本地转院登记列表
   *
   * @param
   * @return
   */
  @RequestMapping("/getReflAppyEvtC")
  public ResultInfo getReflAppyEvtC(ReflAppyEvtCVo vo) {
    return ResultInfo.success(reflAppyEvtCService.getReflAppyEvtC(vo));
  }

  @GetMapping("/download")
  public ResultInfo download(String fileName, Boolean delete, HttpServletRequest request, HttpServletResponse response, String name) throws Exception {
    return reflAppyEvtCService.download(fileName, delete, request, response, name);
  }

  @RequestMapping("/print")
  public ResultInfo print(ReflAppyEvtCVo vo) {
    return ResultInfo.success(reflAppyEvtCService.print(vo));
  }

  /**
   * 诊断信息
   *
   * @param vo
   * @return
   */
  @RequestMapping("/getDiagList")
  public ResultInfo getDiagList(ReflAppyEvtCVo vo) {
    return reflAppyEvtCService.getDiagList(vo);
  }

  /**
   * 撤销
   *
   * @param
   * @return
   */
  @RequestMapping("/cancelReflAppyEvtC")
  public ResultInfo cancelReflAppyEvtC(ReflAppyEvtC reflAppyEvtC) {
    return reflAppyEvtCService.cancelReflAppyEvtC(reflAppyEvtC);
  }

  /**
   * 审核
   *
   * @param
   * @return
   */
  @RequestMapping("/checkReflAppyEvtC")
  public ResultInfo checkReflAppyEvtC(ReflAppyEvtC reflAppyEvtC) {
    return reflAppyEvtCService.checkReflAppyEvtC(reflAppyEvtC);
  }

  /**
   * 获取数据字典
   *
   * @param
   * @return
   */
  @RequestMapping("/getNatDataDicA")
  public ResultInfo getNatDataDicA(ReflAppyEvtCVo vo) {
    return reflAppyEvtCService.getNatDataDicA(vo);
  }

  /**
   * 就医地行政区划
   *
   * @param vo PRNT_DIC_VAL_CODE 字典类型代码父类
   * @return 省市级下拉数据
   */
  @RequestMapping("/getNatDataDicAByAdmdvs")
  public ResultInfo getNatDataDicAByAdmdvs(ReflAppyEvtCVo vo) {
    return reflAppyEvtCService.getNatDataDicAByAdmdvs(vo);
  }

  /**
   * 获取基本人员信息
   *
   * @param vo psnCertType 人员证件类型
   *           CERTNO      证件号码
   *           HSECFC      电子凭证号
   * @return
   */
  @RequestMapping("/getPsnInfoB")
  public ResultInfo getPsnInfoB(ReflAppyEvtCVo vo) {
    return reflAppyEvtCService.getPsnInfoB(vo);
  }

  /**
   * 医疗机构查询
   *
   * @param vo MEDINS_CODE 医疗机构编号
   *           MEDINS_NAME 医疗机构名称
   * @return
   */
  @RequestMapping("/getMedinsInfoB")
  public ResultInfo getMedinsInfoB(ReflAppyEvtCVo vo) {
    return reflAppyEvtCService.getMedinsInfoB(vo);
  }

  @RequestMapping("/getMedinsInfoBPage")
  public ResultInfo getMedinsInfoBPage(FixmedinsB vo) {
    return reflAppyEvtCService.getMedinsInfoBPage(vo);
  }

  @RequestMapping("/getMedinsInfoBOne")
  public ResultInfo getMedinsInfoBOne(FixmedinsB vo) {
    return reflAppyEvtCService.getMedinsInfoBOne(vo);
  }

  /**
   * 就诊查询
   *
   * @param vo CERTNO 证件号码
   *           MEDINS_CODE 医疗机构编号
   * @return
   */
  @RequestMapping("/getMdtrtD")
  public ResultInfo getMdtrtD(ReflAppyEvtCVo vo) {
    return reflAppyEvtCService.getMdtrtD(vo);
  }

  /**
   * 提交
   */
  @RequestMapping("/subReflAppyEvt")
  public ResultInfo subReflAppyEvt(ReflAppyEvtC reflAppyEvtC) {
    return reflAppyEvtCService.subReflAppyEvt(reflAppyEvtC);
  }

  /**
   * 文件上传
   *
   * @param files
   * @param id
   * @return
   */
  @RequestMapping("/upload")
  @ResponseBody
  public ResultInfo upload(@RequestParam(value = "file", required = false) List<MultipartFile> files, @RequestParam(value = "id", required = false) String id) {
    //清除文件
    List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
            .eq(FileInfo::getBizId, id)
            .eq(FileInfo::getBizType, "8")
    );
    for (FileInfo fileInfo : fileInfos) {
      fastDfsUtil.deleteFile(fileInfo);
    }
    for (MultipartFile file : files) {
      //上传文件服务器
      FastDfsParams params = new FastDfsParams("reflAppyEvtC", file, "8", id);
      params.setFileName(file.getOriginalFilename());
      fastDfsUtil.uploadFile(params);
    }

    return ResultInfo.success();
  }

  @RequestMapping("/picList")
  public ResultInfo picList(String id) {
    List<FileInfo> fileInfos = fileInfoMapper.selectList(Wrappers.<FileInfo>lambdaQuery()
            .eq(FileInfo::getBizId, id)
            .eq(FileInfo::getBizType, "8")
    );
    return ResultInfo.success(fileInfos);
  }
}
